import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Rfc865UdpServer {
    public static void main(String[] argv) {
        DatagramSocket socket = null;
        try {
            // 1. Open UDP socket at well-known port 9876
            socket = new DatagramSocket(9876);
            System.out.println("Server started. Listening on port 9876.");

            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    // 2. Listen for UDP request from client
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    socket.receive(request);

                    String message = new String(request.getData(), 0, request.getLength());
                    System.out.println("Received: " + message);

                    // 3. Send UDP reply to client
                    String replyMessage = "Hello, client!";
                    byte[] replyBuffer = replyMessage.getBytes();
                    DatagramPacket reply = new DatagramPacket(replyBuffer, replyBuffer.length, request.getAddress(), request.getPort());
                    socket.send(reply);

                    System.out.println("Reply sent.");
                } catch (IOException e) {
                    System.err.println("Error processing request: " + e.getMessage());
                }
            }
        } catch (SocketException e) {
            System.err.println("Socket error: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
