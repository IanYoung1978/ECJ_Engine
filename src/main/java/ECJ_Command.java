public class ECJ_Command {
    private final ECJ_CommandType type;
    private final int arg_count;
    private final ECJ_EventArg[] eventArgs;

    public ECJ_Command(ECJ_CommandType type, ECJ_EventArg[] eventArgs, int arg_count) {
        this.type = type;
        this.arg_count = arg_count;
        this.eventArgs = eventArgs;
    }

    public int getArg_count() {
        return arg_count;
    }

    public ECJ_EventArg[] getEventArgs() {
        return eventArgs;
    }

    public ECJ_CommandType getType() {
        return type;
    }
}
