import java.io.*;
import java.net.*;

import java.io.IOException;

public class Client {
    public static void main(String[] args){
        //String hostName = Inet4Address.getLocalHost().getHostAddress();
        int portNumber = 77;
        boolean win = false;
        try{

            String hostName = Inet4Address.getLocalHost().getHostAddress();
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter putInServer = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream dataOutputStream = new DataOutputStream(echoSocket.getOutputStream());
            String userInput;
            System.out.println("create a file||Choose the name");
            String nameF = in.readLine();
            File file = new File((nameF + ".txt"));
            FileWriter writer = new FileWriter(file);
            for(int i = 0; i < 100; i++){
                writer.write(String.valueOf(i));
            }
            writer.flush();
            byte[] buffer = new byte[1000*1024];
            FileInputStream fileInputStream = new FileInputStream(file);
            int bytes = fileInputStream.read(buffer,0,buffer.length);
            dataOutputStream.write(buffer,0, bytes);        




            
            
            putInServer.close();
            in.close();
        } catch (IOException e) {
            System.out.println("eghiyt");
            return;
        }
       
    }
}
