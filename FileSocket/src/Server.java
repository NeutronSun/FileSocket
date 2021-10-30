import java.net.*;
import java.io.*;

public class Server {

    public static PrintWriter putInClient;
    public static BufferedReader getFromItSelf;
    public static DataOutputStream dataOutputStream;
    public static void main(String[] args) throws IOException {
        int portNumber = 77;
        try{
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();

            putInClient = new PrintWriter(clientSocket.getOutputStream(), true);                   
            getFromItSelf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            String caseCase = "wgui";
            String userInput;
            while(true){
                caseCase = getFromItSelf.readLine();
                switch (caseCase){
                    case "sentToMe":
                    //send to the client all the file name
                    listFilesForFolder(new File("./CarpetaServer"));
                    putInClient.println("select a file");
                    userInput = getFromItSelf.readLine();
                    sendFile(userInput);
                    break;

                    case "byebye":
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port ");
        }
    }

    public static void listFilesForFolder(final File folder) {
        int cont = 0;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                putInClient.println("File " + cont + ":" + fileEntry.getName());
                System.out.println(fileEntry.getName());
                cont++;
            }
        }
        putInClient.println("end");
    }

    public static void sendFile(String fineName) throws IOException{
        byte[] buffer = new byte[1000*1024];
        FileInputStream fileInputStream = new FileInputStream("./CarpetaServer/" + fineName);
        int bytes = fileInputStream.read(buffer,0,buffer.length);
        dataOutputStream.write(buffer,0, bytes); 
    }
}