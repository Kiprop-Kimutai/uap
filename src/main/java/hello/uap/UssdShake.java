package hello.uap;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class UssdShake extends Thread {
    Client client;
    public UssdShake(Client client){
        this.client = client;
    }
    public void run()  {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()  {
                try {
                    client.sendBytes(new hello.uap.messages.UssdShake().encode());
                }
                catch (IOException e) {
                    e.getMessage();
                }
            }
        },0,5000);
    }
}
