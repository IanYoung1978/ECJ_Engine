public interface I_ECJ_Window
    extends I_ECJ_CommandListener {
    ECJ_Error open(int width, int height, ECJ_WindowSettings[] settings, String window_name, ECJ_MessageSystem ecj_messageSystem);
    ECJ_Error present();
}
