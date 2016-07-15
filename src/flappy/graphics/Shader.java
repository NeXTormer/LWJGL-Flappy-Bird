package flappy.graphics;

import flappy.math.Matrix4f;
import flappy.math.Vector3f;
import flappy.utils.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by Felix on 11.07.2016.
 */
public class Shader {

    public static final int VERTEX_ATTRIB = 0;
    public static final int TCOORD_ATTRIB = 1;
    public static Shader BG;
    public static Shader BIRD;
    public static Shader PIPE;
    public static Shader FADE;


    private boolean enabled = false;

    private final int id;
    private Map<String, Integer> locationCache = new HashMap<>();

    public Shader(String vertex, String fragment) {
        id = ShaderUtils.load(vertex, fragment);

    }

    public static void loadAll() {
        BG = new Shader("src/flappy/shader/bg.vert", "src/flappy/shader/bg.frag");
        BIRD = new Shader("src/flappy/shader/bird.vert", "src/flappy/shader/bird.frag");
        PIPE = new Shader("src/flappy/shader/pipe.vert", "src/flappy/shader/pipe.frag");
        FADE = new Shader("src/flappy/shader/fade.vert", "src/flappy/shader/fade.frag");
    }

    public int getUniform(String name) {
        if(locationCache.containsKey(name)) return locationCache.get(name);
        int result = glGetUniformLocation(id, name);
        if(result == -1) {
            System.err.println("Could not find uniform var: " + name + "!");
        }
        locationCache.put(name, result);

        return result;
    }

    public void setUniform1i(String name, int value) {
        if(!enabled) enable();
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value) {
        if(!enabled) enable();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        if(!enabled) enable();
        glUniform2f(getUniform(name), x, y);
    }

    public void setUniform3f(String name, Vector3f value) {
        if(!enabled) enable();
        glUniform3f(getUniform(name), value.x, value.y, value.z);
    }

    public void setUniformMat4f(String name, Matrix4f matrix) {
        if(!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    public void enable() {
        glUseProgram(id);
        enabled = true;

    }

    public void disable() {
        glUseProgram(0);
        enabled = false;
    }
}
