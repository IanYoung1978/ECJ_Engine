import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class ECJ_ControlSystem
    implements I_ECJ_EventListener {
    private final ECJ_MessageSystem ecj_messageSystem;
    public ECJ_ControlSystem(ECJ_MessageSystem ecj_messageSystem) {
        this.ecj_messageSystem = ecj_messageSystem;
        ECJ_EventType[] types = { ECJ_EventType.KEY_UP,ECJ_EventType.KEY_DOWN };
        ecj_messageSystem.subscribe(this,types);
    }
    @Override
    public void handleEvent(ECJ_Event ecj_event) {
        if (ecj_event.getType() == ECJ_EventType.KEY_UP) {
            if (ecj_event.getEventArgs()[0].getData().getIntData() == GLFW_KEY_ESCAPE) {
                ecj_messageSystem.publish(new ECJ_Command(ECJ_CommandType.SHUTDOWN,null,0));
            }
        }
    }
}
