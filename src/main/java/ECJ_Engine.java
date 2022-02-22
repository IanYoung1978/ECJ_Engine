import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class ECJ_Engine
    implements I_ECJ_CommandListener {

    // The window handle
    I_ECJ_Window window;
    ECJ_MessageSystem ecj_messageSystem;
    ECJ_ControlSystem ecj_controlSystem;
    boolean running = true;
    public void run() {
        init();
        loop();
    }

    private void init() {
        window = new ECJ_GL_Window();
        ecj_messageSystem = new ECJ_MessageSystem();
        ecj_controlSystem = new ECJ_ControlSystem(ecj_messageSystem);
        ECJ_WindowSettings[] settings = { ECJ_WindowSettings.WINDOW_BORDERED };
        if(ECJ_Error.NO_ERROR != window.open(800,600,settings,"ECJ!",ecj_messageSystem)) {
            throw new RuntimeException("Failed to open window!");
        }
        ECJ_CommandType[] commands = {ECJ_CommandType.SHUTDOWN};
        ecj_messageSystem.subscribe(this,commands);
        running = true;
    }

    private void loop() {
        while (running) {
            ecj_messageSystem.sendMessages();
            if (ECJ_Error.NO_ERROR != window.present()) {
                throw new RuntimeException("Window presentation error!");
            }
        }
    }

    public static void main(String[] args) {
        new ECJ_Engine().run();
    }

    @Override
    public void handleCommand(ECJ_Command ecj_command) {
        if (ecj_command.getType() == ECJ_CommandType.SHUTDOWN) {
            running = false;
            ecj_messageSystem.publish(new ECJ_Command(ECJ_CommandType.WINDOW_CLOSE,null,0));
        }
    }
}