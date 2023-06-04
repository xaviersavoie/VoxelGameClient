package org.voxelgame.client;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.voxelgame.client.chunk.ChunkClient;

import org.voxelgame.client.packet.InboundPacketProcessor;
import org.voxelgame.client.packet.ServerEntityPosition;
import org.voxelgame.client.packet.ServerPacketChunkData;
import org.voxelgame.client.packet.PacketFactory;

import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientConnection {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;
    private ObjectMapper objectMapper;
    private JsonFactory jsonFactory;
    private JsonMapper mapper;

    public CharBuffer charBuffer;
    public CharBuffer charBufferToParse;
    private char [] prependChars;

    public List<String> extractJsonBlobs(String json) {

        JsonMapper mapper = new JsonMapper();
        List<String> jsonBlobs = new ArrayList<>();

        int lastSuccessfulParseCharLocation = 0;

        try (MappingIterator<JsonNode> it = mapper.readerFor(JsonNode.class)
                .readValues(json)) {
            while (it.hasNextValue()) {
                jsonBlobs.add(it.nextValue().toString());
                lastSuccessfulParseCharLocation = (int) it.getCurrentLocation().getCharOffset();
            }


        } catch (JsonEOFException e) {
            System.out.println("EOF");
            //e.printStackTrace();
            charBuffer.position(lastSuccessfulParseCharLocation);
            Arrays.fill(prependChars, '\0');
            charBuffer.get(prependChars, 0, (charBuffer.length()));

        } catch (JsonParseException e) {
            System.out.println("Nothing parsed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jsonBlobs;
    }

    public ClientConnection() throws IOException {
        socket = new Socket("localhost", 25569);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        objectMapper = new ObjectMapper();
        mapper = new JsonMapper();
        charBuffer = CharBuffer.allocate(16384);
        prependChars = new char[16384];
        jsonFactory = new JsonFactory(objectMapper);
    }

    public void putRawJSONIntoBuffer() throws IOException {
        int character = 0;
        int count = 0;
        charBuffer.clear();

        for (char prependChar : prependChars) {
            if (prependChar != 0) {
                charBuffer.put(prependChar);
            } else {
                break;
            }
        }

        //System.out.println(charBuffer.position());

        //System.out.println(prependChars);
        //System.out.println();
        while (charBuffer.hasRemaining() && bufferedReader.ready()) {
            character = bufferedReader.read();
            if (character == 0 || character == -1) continue; //NOTHING BEFORE THESE LINE

            System.out.print((char)character);

            charBuffer.put((char) character);
            count++;
        }

        charBuffer.clear(); //Resets position, doesn't erase contents

        charBufferToParse = charBuffer;
        if (!bufferedReader.ready()) {
            charBuffer = CharBuffer.allocate(16384);
         }

        //return charBuffer;
    }

    public List<SPacket> getPackets(List<String> stringPackets) throws IOException {
        List<SPacket> packets = new ArrayList<>();
        for (String packet: stringPackets) {
            packets.add(getPacket(packet));
        }
        return packets;
    }

    public SPacket getPacket(String jsonIn) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonIn);
        JsonNode packetTypeNode = jsonNode.get("packetType");
        System.out.println(jsonNode.isMissingNode());
        String packetType;

        if (packetTypeNode != null) {
            packetType = packetTypeNode.asText();
        } else {
            return null;
        }

        return switch (packetType) {
            case "ServerPacketChunkData" -> PacketFactory.createServerPacketChunkDataFromJSONData(jsonIn);
            case "ServerEntityPositionPacket" -> PacketFactory.createServerEntityPositionFromJSONData(jsonIn);
            default -> null;
        };
    }

    public void processPacketsInUpdateLoop() {
        List<SPacket> packets = null;

        try {
            putRawJSONIntoBuffer();
            if (!charBufferToParse.isEmpty()) {
                packets = getPackets(extractJsonBlobs(charBufferToParse.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (SPacket packetFromList : packets) {

            if (packetFromList == null) continue; //TODO: better alternative pls

            if (packetFromList.getClass() == ServerPacketChunkData.class) {
                ChunkClient chunkClient = ((ServerPacketChunkData) packetFromList).toChunkClient();
                Main.worldClient.addChunk(chunkClient.getChunkHash(), chunkClient);
            } else if (packetFromList.getClass() == ServerEntityPosition.class) {
                InboundPacketProcessor.processServerEntityPositionPacket((ServerEntityPosition) packetFromList);
            }
        }
    }

    public void sendPacket(CPacket packet) throws IOException {
        String s = packet.serialize();
        System.out.println( "out : " +  s);
        dataOutputStream.writeChars(packet.serialize());
    }

}
