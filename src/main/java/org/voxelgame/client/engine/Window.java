package org.voxelgame.client.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;

import java.util.concurrent.Callable;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

//A Java class implementation of how a GLFW window should look like

public class Window {
    private final long windowHandle;
    private int height, width;
    private Callable<Void> resizeFunc;
    private MouseInput mouseInput;
    private boolean ESC_MODE;

    public Window(String title, WindowOptions opts, Callable<Void> resizeFunc) {
        this.resizeFunc = resizeFunc;
        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);


        if (opts.width > 0 && opts.height > 0) {
            this.width = opts.width;
            this.height = opts.height;
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GL_TRUE);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            width = vidMode.width();
            height = vidMode.height();
        }

        glfwSetErrorCallback((int errorCode, long msgPtr) -> System.err.println("Error code " + errorCode + ", msg: " + MemoryUtil.memUTF8(msgPtr) + '\n'));


        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create GLFW Window");
        }

        glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> resized(w, h));


        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                //glfwSetWindowShouldClose(window, true);
                if (!ESC_MODE) {
                    glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                    ESC_MODE = true;
                } else {
                    glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                    ESC_MODE = false;
                }
            }
        });

        glfwMakeContextCurrent(windowHandle);

        if (opts.fps > 0) {
            glfwSwapInterval(0);
        } else {
            glfwSwapInterval(1);
        }

        glfwShowWindow(windowHandle);


        //The integer is contained in an array of size one for memory purposes. The original C/C++ function parameters are references, so in Java we'll use an array. We're getting the framebuffer size from the GLFW method and putting it into these arrays.
        int[] arrWidth = new int[1];
        int[] arrHeight = new int[1];

        glfwGetFramebufferSize(windowHandle, arrWidth, arrHeight);

        width = arrWidth[0];
        height = arrHeight[0];

        mouseInput = new MouseInput(windowHandle);

        glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        this.ESC_MODE = false;

    }

    public MouseInput getMouseInput() {
        return mouseInput;
    }

    public void cleanup() {
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public void pollEvents() {
        glfwPollEvents();
        mouseInput.input();
    }

    protected void resized(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            resizeFunc.call();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void update() { //change function name to something that applies more
        glfwSwapBuffers(windowHandle);
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public boolean isESC_MODE() {
        return ESC_MODE;
    }

    public static class WindowOptions {
        public boolean compatibleProfile;
        public int fps;
        public int width = 500, height = 500;
        public int ups = GameEngine.TARGET_UPS;
    }

}
