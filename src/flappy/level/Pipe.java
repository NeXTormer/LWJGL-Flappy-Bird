package flappy.level;

import flappy.graphics.Texture;
import flappy.graphics.VertexArray;
import flappy.math.Matrix4f;
import flappy.math.Vector3f;

/**
 * Created by Felix on 12.07.2016.
 */
public class Pipe {

    private Vector3f position = new Vector3f();
    private Matrix4f ml_matrix;
    private static VertexArray mesh;
    private static Texture texture;
    private static float width = 1.5f;
    private static float height = 8.0f;


    public static void create() {
        float[] vertices = new float[] {
                0.0f, 0.0f, 0.1f,
                0.0f, height, 0.1f,
                width, height, 0.1f,
                width, 0.0f, 0.1f
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
        texture = new Texture("res/pipe.png");
    }

    public Pipe(float x, float y) {
        position.x = x;
        position.y = y;
        ml_matrix = Matrix4f.translate(position);
    }

    public Matrix4f getModelMatrix() {
        return ml_matrix;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public float getZ(){
        return position.z;
    }

    public static VertexArray getMesh() {
        return mesh;
    }

    public static Texture getTexture() {
        return texture;
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }
}
