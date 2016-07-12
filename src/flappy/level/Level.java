package flappy.level;

import flappy.graphics.Shader;
import flappy.graphics.Texture;
import flappy.graphics.VertexArray;
import flappy.math.Matrix4f;
import flappy.math.Vector3f;

import java.util.Random;

/**
 * Created by Felix on 12.07.2016.
 */
public class Level {

    private VertexArray background;

    private Texture bgTex;

    private int xScroll = 0;
    private int map = 0;

    private int index = 0;

    private Bird bird;
    private Pipe[] pipes = new Pipe[5 * 2];
    private Random random = new Random();

    private float OFFSET = 5.0f;

    public Level() {
        float[] vertices = new float[] {
                -10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
                -10.0f,  10.0f * 9.0f / 16.0f, 0.0f,
                0.0f,  10.0f * 9.0f / 16.0f, 0.0f,
                0.0f, -10.0f * 9.0f / 16.0f, 0.0f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        background = new VertexArray(vertices, indices, tcs);
        bgTex = new Texture("res/bg.jpeg");
        bird = new Bird();
        createPipes();
    }

    private void renderPipes() {
        Shader.PIPE.enable();
        Shader.PIPE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(xScroll * 0.05f, 0, 0)));
        Pipe.getTexture().bind();
        Pipe.getMesh().bind();
        for(int i = 0; i < 5 * 2; i++) {
            Shader.PIPE.setUniformMat4f("ml_matrix", pipes[i].getModelMatrix());
            Shader.PIPE.setUniform1i("top", i % 2 == 0 ? 1 : 0);

            Pipe.getMesh().draw();
        }
        Pipe.getTexture().unbind();
        Pipe.getMesh().unbind();
    }

    private void createPipes() {
        Pipe.create();
        for (int i = 0; i < 5 * 2; i += 2) {
            pipes[i] = new Pipe(OFFSET + index * 3.0f, 4.0f * random.nextFloat());
            pipes[i + 1] = new Pipe(pipes[i].getX(), pipes[i].getY() - 11.5f);
            index += 2;
        }
    }

    private void updatePipes() {
        pipes[index % 10] = new Pipe(OFFSET + index * 3.0f, 4.0f * random.nextFloat());
        pipes[(index + 1) % 10] = new Pipe(pipes[index % 10].getX(), pipes[index % 10].getY() - 11.5f);
        index += 2;
    }

    public void render() {
        bgTex.bind();
        Shader.BG.enable();
        background.bind();
        for(int i = map; i < map + 4; i++) {
            Shader.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0, 0)));
            background.draw();
        }

        Shader.BG.disable();
        bgTex.unbind();
        renderPipes();
        bird.render();
    }




    public void update() {
        xScroll--;
        if(-xScroll % 335 == 0) map++;
        if(-xScroll > 250 && -xScroll % 120 == 0) updatePipes();
        bird.update();
    }
}
