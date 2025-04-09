import java.net.*;
import java.util.*;
import java.io.*;

public class Server{
    ServerSocket serverSocket;
    List <ClientHandler>clientList = new ArrayList<>();
    int activeClients = 0;

    public void createServer() throws IOException{
        System.out.println("Application Started!");
        serverSocket = new ServerSocket(5000, 50, InetAddress.getByName("0.0.0.0"));
        while(true){
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            activeClients++;
            System.out.println("Client Connected , Active Clients: "+activeClients);
            Thread clientThread = new Thread(clientHandler);
            clientList.add(clientHandler);
            clientThread.start();
        }
    }

    public void sendMessage(List<ClientHandler> List , String message){
        synchronized(List){
            for(ClientHandler handler : List){
                handler.sendStream.println(message);
            }
        }
    }

    class ClientHandler implements Runnable{
        BufferedReader readStream;
        PrintWriter sendStream;
        Socket mainSocket;

        ClientHandler(Socket mainSocket){
            this.mainSocket = mainSocket;
        }

        @Override
        public void run(){
            try{
                readStream = new BufferedReader(new InputStreamReader(mainSocket.getInputStream()));
                sendStream = new PrintWriter(mainSocket.getOutputStream() , true);

                String message;
                while((message = readStream.readLine())!=null){
                    System.out.println(message);
                    sendMessage(clientList, message);
                }
            }
            catch(IOException e){
                System.out.println("Client disconnected, Active Clients: " + (activeClients-1));
            }
            finally{
                try{
                    if (readStream != null) readStream.close();
                    if (sendStream != null) sendStream.close();
                    if (mainSocket != null && !mainSocket.isClosed()) mainSocket.close();
                }
                catch(IOException e){
                    System.out.println("Error closing client resources.");
                }

                activeClients--;
                if(activeClients==0){
                    System.out.println("Closing Server!");
                    System.exit(0);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        Server server = new Server();
        server.createServer();
    }
}