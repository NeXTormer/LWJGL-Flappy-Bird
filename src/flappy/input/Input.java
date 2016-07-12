package flappy.input;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 * Created by Felix on 11.07.2016.
 */
public class Input extends GLFWKeyCallback {

    public static boolean[] keys = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action == GLFW.GLFW_RELEASE ? false : true;
    }

    public static boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }
}
