package flappy;


import flappy.graphics.Shader;
import flappy.input.Input;
import flappy.level.Level;
import flappy.math.Matrix4f;
import org.lwjgl.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.*;
import sun.java2d.opengl.OGLContext;


import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import static org.lwjgl.glfw.GLFW.*;


public class Main implements Runnable {

    /*
        PETER RENDL

        https://youtu.be/527bR2JHSR0?t=2h43m
     */

    private int width = 1280;
    private int height = 720;

    private int TICKRATE = 60;

    private Thread thread;
    private boolean running = false;

    private long window;

    private Level level;

    public void start() {
        running = true;
        thread = new Thread(this, "Game");
        thread.start();
    }

    private void init() {
        if(glfwInit() == false) {
            return;
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window = glfwCreateWindow(width, height, "Flappy", 0, 0);
        if(window == 0) {
            return;
        }
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width)/2, (vidmode.height() - height) / 2);

        glfwSetKeyCallback(window, new Input());

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        GL.createCapabilities();

        glClearColor(1, 1, 1, 1);
        glEnable(GL_DEPTH_TEST);
        glActiveTexture(GL_TEXTURE1);
        System.out.println("OpenGL" + glGetString(GL_VERSION));

        Shader.loadAll();
        Shader.BG.enable();
        Matrix4f pr_matrix = Matrix4f.orthographic(-10, 10, -10f * 9f / 16f, 10f * 9f / 16, -1, 1);

        Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.BG.setUniform1i("tex", 1);

        Shader.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.BIRD.setUniform1i("tex", 1);

        Shader.PIPE.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.PIPE.setUniform1i("tex", 1);

        Shader.BG.disable();
        level = new Level();
    }

    public void run() {
        init();

        long lastTime = System.nanoTime();
        double ns = 1000000000 / TICKRATE;
        long timer = System.currentTimeMillis();
        double delta = 0;
        int updates = 0;
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1) {
                updates++;
                delta --;
                update();

            }
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + "ups, " + frames + "fps");
                updates = 0;
                frames = 0;
            }
            render();


            if(glfwWindowShouldClose(window)) {
                running = false;
                glfwDestroyWindow(window);
                glfwTerminate();
            }

        }
    }

    private void update() {
        glfwPollEvents();
        level.update();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        level.render();
        int error = glGetError();
        if(error != GL_NO_ERROR) {
            System.err.println(error);
        }
        glfwSwapBuffers(window);
    }


    public static void main(String[] args) {
        new Main().start();
    }


}