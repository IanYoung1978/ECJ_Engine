public class ECJ_EventArg {
    private final ECJ_Variant data;
    String arg_name;

    public ECJ_EventArg(ECJ_Variant data, String arg_name) {
        this.data = data;
        this.arg_name = arg_name;
    }

    public ECJ_Variant getData() {
        return data;
    }

    public String getArg_name() {
        return arg_name;
    }
}
