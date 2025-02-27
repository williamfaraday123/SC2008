import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Rfc865UdpClient {
	public static void main(String[] argv) {
        DatagramSocket socket = null;
        try {
            // 1. Open UDP Socket
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("10.96.189.96"); // Send to Server IP: 10.96.189.96
            
            byte[] buffer = new byte[512];
            
            // 2. Send UDP Request to Server
            String message = "Isaac Lim Jia Le, TCE1, 10.96.179.48";
            buffer = message.getBytes();
            DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, 17); // Server port: 17 
            socket.send(request);
            
            // 3. Receive UDP Reply from Server
            buffer = new byte[65535];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            String received = new String(reply.getData(), 0, reply.getLength());
            System.out.println("Received: " + received);
        } catch (SocketException e) {
            System.err.println("Socket error: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println("Unknown host error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Socket closed.");
            }
        }
    }
}

