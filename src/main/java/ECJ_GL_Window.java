import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ECJ_GL_Window
    implements I_ECJ_Window {

    // The window handle
    private long window;

    @Override
    public ECJ_Error open(int width, int height, ECJ_WindowSettings[] settings, String window_name, ECJ_MessageSystem ecj_messageSystem) {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        ECJ_CommandType[] commands = { ECJ_CommandType.WINDOW_CLOSE,ECJ_CommandType.WINDOW_RESIZE, ECJ_CommandType.WINDOW_MAXIMISE };
        ecj_messageSystem.subscribe(this, commands);
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        // Create the window
        window = glfwCreateWindow(width, height, window_name, NULL, NULL);
        if ( window == NULL )
            return ECJ_Error.WINDOW_ERROR;
        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
                ECJ_EventArg[] args = new ECJ_EventArg[3];
                args[0] = new ECJ_EventArg(new ECJ_Variant(key),"Key");
                args[1] = new ECJ_EventArg(new ECJ_Variant(key),"scancode");
                args[2] = new ECJ_EventArg(new ECJ_Variant(key),"mod");
                ECJ_Event e = null;
                if (action == GLFW_PRESS) {
                    e = new ECJ_Event(ECJ_EventType.KEY_DOWN, args);
                }
                else if (action == GLFW_RELEASE) {
                    e = new ECJ_Event(ECJ_EventType.KEY_UP, args);
                }
                ecj_messageSystem.publish(e);
        });
        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode video_mode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            assert video_mode != null;
            glfwSetWindowPos(
                    window,
                    (video_mode.width() - pWidth.get(0)) / 2,
                    (video_mode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        return ECJ_Error.NO_ERROR;
    }

    @Override
    public ECJ_Error present() {


        glfwSwapBuffers(window); // swap the color buffers
        // TODO: Remove this once GFS subsystem is in place.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
        return ECJ_Error.NO_ERROR;
    }

    @Override
    public void handleCommand(ECJ_Command ecj_command) {
        if (ecj_command.getType() == ECJ_CommandType.WINDOW_CLOSE) {
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            if ( glfwWindowShouldClose(window) ) {
                // Free the window callbacks and destroy the window
                glfwFreeCallbacks(window);
                glfwDestroyWindow(window);

                // Terminate GLFW and free the error callback
                glfwTerminate();
                Objects.requireNonNull(glfwSetErrorCallback(null)).free();
            }
        }
    }
}
