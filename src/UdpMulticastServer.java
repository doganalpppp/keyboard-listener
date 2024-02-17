import java.net.*;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.GlobalScreen;

public class UdpMulticastServer implements NativeKeyListener {
    static InetAddress group;
    public static final int PORT_NUM = 3999;
    public static final String IP_ADDR = "239.0.0.1";

    public void nativeKeyPressed(NativeKeyEvent e) {
        try {
            group = InetAddress.getByName(IP_ADDR);

            try (DatagramSocket socket = new DatagramSocket()) {
                String userInput = NativeKeyEvent.getKeyText(e.getKeyCode());
                System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
                byte[] data = userInput.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, group, UdpMulticastServer.PORT_NUM);
                socket.send(packet);
                System.out.println("Data has been sent to Multicast Group  ! ");
            }
        } catch (Exception er) {
            er.printStackTrace();
        }


        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new UdpMulticastServer());
    }
}

