package hello.uap;

import hello.uap.messages.UssdBind;
import hello.uap.messages.UssdShake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
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
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public Client() {

    }
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

    public void listenForResponses(Client client) throws IOException {
        byte [] resp = new byte[1024];
        while(dataInputStream.read(resp) >0) {
            logger.info("<----server says--->"+new String(resp));
            DecodeByteResponses decodeByteResponses = new DecodeByteResponses(resp,logger,client);
            System.out.println("done...");
        }
    }

    public  void sendBytes(byte [] byteArray) throws IOException {
        dataOutputStream.write(byteArray);
        dataOutputStream.flush();
    }

    public void setUp() throws IOException {
        Client client = new Client();
        client.startConnection("172.29.127.177",9090);
        client.sendBindRequest();
        new hello.uap.UssdShake(client).run();
        client.listenForResponses(client);


    }
}
