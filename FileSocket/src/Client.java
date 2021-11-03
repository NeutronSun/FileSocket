import java.io.*;
import java.net.*;
import java.io.IOException;

/**
 * 
 * @author Lorenzo Sanseverino
 * @author Valerio Gallo
 * Classe che instanzia il client, può decidere quale file presente nella cartella del server copiare nella cartella del client.
 *
 */
public class Client {
	
	/**
	 * Oggetto che crea il socket del client.
	 */
    private static Socket echoSocket;
    /**
     * Oggetto di tipo PrintWriter che serve per mandare in output i dati al server.
     */
    private static PrintWriter putInServer;
    /**
     * Oggetto che prende in input i dati dalla console.
     */
    private static BufferedReader in;
    /**
     * Oggetto che prende in input i dati dal server.
     */
    private static BufferedReader read;
    
    /**
     * Nel main viene prima istanziato il socket, poi viene preso in input ciò che l'utente vuole fare, tra selezionare un file dalla cartella del server e uscire dal programma.
     * 
     */
    public static void main(String[] args){
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

    /**
     * 
     * @param fileName Nome del file da copiare nella cartella del client.
     * @throws IOException In caso ci siano dei problemi.
     * Viene utilizzato un buffer di tipo byte, nel quale vengono copiati tutti i byte del file del server, poi copiati nella cartella di destinazione.
     */
    public static void getFile(String fileName) throws IOException{
    	/**
    	 * Buffer di tipo byte.
    	 */
        byte[] buffer = new byte[1000*1024];
        DataInputStream dataInputStream = new DataInputStream(echoSocket.getInputStream());
        /**
         * Byte nei quali vanno copiati i dati del file originale.
         */
        int bytes = dataInputStream.read(buffer,0,buffer.length);
        FileOutputStream fileOutputStream = new FileOutputStream("./FolderClient/" + fileName);
        fileOutputStream.write(buffer,0,bytes);
        System.out.println("nothing went wrong");
    }
}