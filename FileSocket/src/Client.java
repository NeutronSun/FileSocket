import java.io.*;
import java.net.*;

import java.io.IOException;

public class Client {
    private static Socket echoSocket;
    private static PrintWriter putInServer;
    private static BufferedReader in;
    private static BufferedReader read;
    public static void main(String[] args){
        //String hostName = Inet4Address.getLocalHost().getHostAddress();
        int portNumber = 77;
        boolean win = false;
        try{

            String hostName = Inet4Address.getLocalHost().getHostAddress();
            echoSocket = new Socket(hostName, portNumber);
            putInServer = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(System.in));
            read = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            String userInput, s, caseCase = "ez";
            while(true){
                System.out.println("select --> to select a file to download");
                System.out.println("byebye --> to quit");
                switch(caseCase = in.readLine()){
                    case "select" : 
                        //say to the server to send all the files name
                        putInServer.println("sentToMe");
                        //read all the name
                        while(!(s = read.readLine()).equals("end")){System.out.println(s);};
                        System.out.println(read.readLine());
                        //choose the file
                        String fileName = in.readLine();
                        putInServer.println(fileName);
                        //recive the file
                        getFile(fileName);
                        break;

                    case "byebye":
                        putInServer.close();
                        in.close();
                        echoSocket.close();

                    default:
                    System.out.println("wrong case");

                }
            }
        } catch (IOException e) {
            System.out.println("eghiyt");
            return;
        }
    }

    public static void getFile(String fileName) throws IOException{
        byte[] buffer = new byte[1000*1024];
        DataInputStream dataInputStream = new DataInputStream(echoSocket.getInputStream());
        int bytes = dataInputStream.read(buffer,0,buffer.length);
        FileOutputStream fileOutputStream = new FileOutputStream("./FolderClient/" + fileName);
        fileOutputStream.write(buffer,0,bytes);
        System.out.println("nothing went wrong");
    }
}
