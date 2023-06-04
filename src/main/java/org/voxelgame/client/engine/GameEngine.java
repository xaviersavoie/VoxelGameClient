package org.voxelgame.client.engine;

public class GameEngine {

    public static final int TARGET_UPS = 30;
    private final IGameLogic gameLogic;
    public static Window window;
    private Render render;

    public static Scene scene;

    private boolean running;
    private int targetFps;
    private int targetUps;


    public GameEngine(String windowTitle, Window.WindowOptions windowOptions, IGameLogic gameLogic) {
        window = new Window(windowTitle, windowOptions, () -> {
            resize();
            return null;
        });
        targetFps = windowOptions.fps;
        targetUps = windowOptions.ups;
        this.gameLogic = gameLogic;
        render = new Render();
        scene = new Scene(window.getWidth(), window.getHeight());
        gameLogic.init(window, scene, render);
        running = true;
    }

    private void cleanup() {
        //TODO: Call the cleanup methods
    }

    private void resize() {
        scene.resize(window.getWidth(), window.getHeight());
    }
        private void run() {
            long initialTime = System.currentTimeMillis();
            float timeU = 1000.0f / targetUps;
            float timeR = targetFps > 0 ? 1000.0f / targetFps : 0;
            float deltaUpdate = 0;
            float deltaFps = 0;

            long updateTime = initialTime;
            while (running && !window.windowShouldClose()) {

                //Main.loopIteration++;
                //System.out.println("Iteration: " + Main.loopIteration);

                window.pollEvents();

                long now = System.currentTimeMillis();
                deltaUpdate += (now - initialTime) / timeU;
                deltaFps += (now - initialTime) / timeR;

                if (targetFps <= 0 || deltaFps >= 1) {
                    gameLogic.input(window, scene, now - initialTime);
                }

                if (deltaUpdate >= 1) {
                    long diffTimeMillis = now - updateTime;
                    gameLogic.update(window, scene, diffTimeMillis);
                    updateTime = now;
                    deltaUpdate--;
                }

                if (targetFps <= 0 || deltaFps >= 1) {
                    render.render(scene);
                    deltaFps--;
                    window.update();
                }
                initialTime = now;
            }
        }
        public void start() {
            running = true;
            run();
        }
        public void stop() {
            running = false;
        }

}
