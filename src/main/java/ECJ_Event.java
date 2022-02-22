public class ECJ_Event {

    private final ECJ_EventType type;
    private int arg_count;
    private final ECJ_EventArg[] eventArgs;

    public ECJ_EventType getType() {
        return type;
    }

    public int getArg_count() {
        return arg_count;
    }

    public ECJ_EventArg[] getEventArgs() {
        return eventArgs;
    }

    public ECJ_Event(ECJ_EventType type, ECJ_EventArg[] eventArgs) {
        this.type = type;
        this.eventArgs = eventArgs;
    }

    public ECJ_EventType event_id() {
        return type;
    }
}
