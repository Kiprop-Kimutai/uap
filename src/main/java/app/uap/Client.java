package app.uap;

import app.uap.messages.UssdBind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.Socket;

@Controller
public class Client {
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private Logger logger = LoggerFactory.getLogger(Client.class);

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip,port);
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        dataInputStream = new DataInputStream(clientSocket.getInputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void sendBindRequest() throws IOException{
        dataOutputStream.write(new UssdBind().encode());
        dataOutputStream.flush();
    }

    public void listenForResponses() throws IOException {
        byte [] resp = new byte[1024];
        while(dataInputStream.read(resp) >0) {
            System.out.println("<----server says--->"+new String(resp));
        }
    }

    public  void sendBytess(byte [] byteArray) throws IOException {
        dataOutputStream.write(byteArray);
        dataOutputStream.flush();
    }
    public void sendBytes(byte [] byteArray) throws IOException{
        //dataOutputStream.write(byteArray);
        //dataOutputStream.flush();
        out.println("hey server!");
        System.out.println("sent....");
        byte [] resp = new byte[1024];
        String inputLine;
        /*while(readResponse(resp) != null) {
            logger.info("server says--->"+new String(resp));
            dataOutputStream.write(new String(resp).getBytes());
        }*/


        /*while((inputLine = in.readLine())!=null) {
            System.out.println(inputLine);
            out.println(inputLine);
        }*/


        //dataInputStream.read(resp);
        //System.out.println("server says"+new String(resp));
        while(dataInputStream.read(resp) >0) {
            System.out.println("<----server says--->"+new String(resp));
            //out.println(new String(resp));
            dataOutputStream.write(byteArray);
            dataOutputStream.flush();
        }

    }

    public String readResponse(byte [] resp) throws IOException {
        dataInputStream.read(resp);

        return new String(resp);
    }
    public void setUp() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1",3030);
        //client.sendBytes(new UssdBind().encode());
        client.sendBindRequest();
        //client.sendBindRequest();
        client.listenForResponses();
    }
}
