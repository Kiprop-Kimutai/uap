package hello.uap;

import hello.uap.messages.MessageBase;
import hello.uap.messages.UssdContinue;

public class PrepareUSSDContinue extends MessageBase {
    byte [] message;
    public PrepareUSSDContinue(byte [] message) {
        this.message = message;
    }

    public byte [] buildUssdString() {
        UssdContinue ussdContinue = new UssdContinue(message);
        ussdContinue.setUssdString("Hello world");
        return ussdContinue.encode();
    }

}
