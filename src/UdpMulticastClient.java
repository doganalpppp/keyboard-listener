import java.io.IOException;
import java.net.*;
public class UdpMulticastClient {
  static InetAddress group;

  public static void main(String[] args) {
    try {
      group = InetAddress.getByName(UdpMulticastServer.IP_ADDR);
      MulticastSocket socket = new MulticastSocket(UdpMulticastServer.PORT_NUM);
      socket.joinGroup(group);
      while(true){
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        String message = new String(packet.getData(), 0 , packet.getLength());
        System.out.println("Received Message : " + message);

      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}



