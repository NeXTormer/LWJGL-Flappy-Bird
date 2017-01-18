package flappy.level;

import flappy.graphics.Shader;
import flappy.graphics.Texture;
import flappy.graphics.VertexArray;
import flappy.input.Input;
import flappy.math.Matrix4f;
import flappy.math.Vector3f;
import static org.lwjgl.glfw.GLFW.*;



/**
 * Created by Felix on 12.07.2016.
 */
public class Bird {

    private float SIZE = 1f;
    private VertexArray mesh;
    private Texture texture;
    private Vector3f position = new Vector3f();
    private float rot;
    private float delta = 0.0f;
    private long lastFlap = 0;
    private boolean firstFlap = true; //true when the first flap has not been performed

    public Bird() {
        lastFlap = System.currentTimeMillis();

        float[] vertices = new float[] {
                -SIZE / 2.0f, -SIZE / 2.0f, 0.2f,
                -SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
                SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
                SIZE / 2.0f, -SIZE / 2.0f, 0.2f
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
        mesh = new VertexArray(vertices, indices, tcs);
        texture = new Texture("res/bird.png");
    }

    public void update() {
        position.y -= delta;
        if((Input.isKeyDown(GLFW_KEY_SPACE) && ((System.currentTimeMillis() - lastFlap > 350) || (firstFlap)))) {
                lastFlap = System.currentTimeMillis();
                firstFlap = false;
                delta = -0.15f;

        } else {
            delta += 0.01f;
        }

        rot = -delta * 90f;
    }

    public void fall() {
        delta = -0.15f;
    }

    public void render() {
        Shader.BIRD.enable();
        Shader.BIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
        texture.bind();
        mesh.render();


        Shader.BIRD.disable();
    }

    public float getY() {
        return position.y;
    }

    public float getSize() {
        return SIZE;
    }

}
