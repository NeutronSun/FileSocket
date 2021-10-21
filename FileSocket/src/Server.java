import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int portNumber = 77;
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();

            PrintWriter putIncliet = new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader getFromItSelf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String userInput;
            byte[] buffer = new byte[1000*1024];
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            int bytes = dataInputStream.read(buffer,0,buffer.length);
            FileOutputStream fileOutputStream = new FileOutputStream("clientFile.txt");
            fileOutputStream.write(buffer,0,bytes);
            System.out.println("nothing went wrong");
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port ");
        }
    }
}