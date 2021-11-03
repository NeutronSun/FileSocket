import java.net.*;
import java.io.*;

/**
 * @author Sanseverino Lorenzo
 * @author Valerio Gallo
 * Classe che istanzia il server, legge i file presenti nella sua cartella e passa i nomi al client per decidere quale copiare.
 */
public class Server {

	/**
	 * Oggetto che manda in output i dati al client.
	 */
    public static PrintWriter putInClient;
    /**
     * Oggetto che prende in input i dati dalla console.
     */
    public static BufferedReader getFromItSelf;
    /**
     * Oggetto che manda in output il file al client.
     */
    public static DataOutputStream dataOutputStream;
    
    /**
     * 
     * @throws IOException In caso di errori con il file.
     * Dopo aver accettato la connessione con il client e avviato il server, prende in input quale file il client vuole copiare, e restituisce al client il file selezionato.
     */
    public static void main(String[] args) throws IOException {
        int portNumber = 77;
        try{
        	/**
        	 * Socket del server.
        	 */
            ServerSocket serverSocket = new ServerSocket(portNumber);
            /**
             * Connessione con il client.
             */
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
                    listFilesForFolder(new File("./FolderServer"));
                    putInClient.println("select a file");
                    userInput = getFromItSelf.readLine();
                    sendFile(userInput);
                    break;

                    case "byebye":
                    serverSocket.close();
                    clientSocket.close();
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port ");
        }
    }

    /**
     * 
     * @param folder Cartella contenente i file del server.
     * Metodo che restituisce i nomi dei file presenti nella cartella del server.
     */
    public static void listFilesForFolder(File folder) {
        int cont = 0;
        File filesList[] = folder.listFiles();
        for(File file : filesList){
            putInClient.println("File " + (cont + 1) + ":" + file.getName());
            System.out.println(file.getName());
            cont++;
        }
        putInClient.println("end");
    }

    /**
     * 
     * @param fineName Nome del file da mandare.
     * @throws IOException In caso di errori a livello di file.
     * Metodo che manda il file al client utilizzando un buffer di tipo byte.
     */
    public static void sendFile(String fineName) throws IOException{
    	/**
    	 * Buffer di tipo byte.
    	 */
        byte[] buffer = new byte[1000*1024];
        FileInputStream fileInputStream = new FileInputStream("./FolderServer/" + fineName);
        /**
         * Bytes in cui vengono copiati i dati del file.
         */
        int bytes = fileInputStream.read(buffer,0,buffer.length);
        dataOutputStream.write(buffer,0, bytes); 
    }
}