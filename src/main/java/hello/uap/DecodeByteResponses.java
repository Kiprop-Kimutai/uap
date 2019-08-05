package hello.uap;

import hello.uap.messages.*;
import org.slf4j.Logger;

import java.io.IOException;

public class DecodeByteResponses extends MessageBase {
    public Logger logger;
    private Client client;
    public DecodeByteResponses(byte [] message,Logger logger, Client client) throws IOException {
        System.out.println("Init...");
        this.logger = logger;
        this.Message = message;
        this.client = client;
        this.dencode();
        processResponse(this.CommandID,message);
    }

    @Override
    protected boolean dencode() {
        super.dencode();
        /*this.CommandID = super.getCommandID();
        System.out.println(this.CommandID);
        this.CommandLength = super.getCommandLength();
        System.out.println("CommandLength"+this.CommandLength);
        this.CommandStatus = super.getCommandStatus();
        System.out.println("CommandStatus"+this.CommandStatus);
        this.SenderCB = super.getSenderCB();
        System.out.println("SenderCB"+this.SenderCB);
        this.ReceiverCB = super.getReceiverCB();
        System.out.println("Receiver CB"+this.ReceiverCB);*/
        return true;
    }

    protected void processResponse(CommandIDs commandID, byte [] message) throws IOException {
        System.out.println("passed...");
        switch(commandID) {
            case UssdBindResp:
                System.out.println(" this is ussd bind response");
                break;
            case UssdShakeResp:
                System.out.println("ussd shake response");
                break;
            case UssdBegin:
                UssdBegin ussdBegin = new UssdBegin(message);
                this.CommandID = super.getCommandID();
                System.out.println(this.CommandID);
                this.CommandLength = super.getCommandLength();
                System.out.println("CommandLength"+this.CommandLength);
                this.CommandStatus = super.getCommandStatus();
                System.out.println("CommandStatus"+this.CommandStatus);
                this.SenderCB = super.getSenderCB();
                System.out.println("SenderCB"+this.SenderCB);
                this.ReceiverCB = super.getReceiverCB();
                System.out.println("Receiver CB"+this.ReceiverCB);


                System.out.println("ussd begin messsage");
                System.out.println("USSD Version"+ussdBegin.getUssdVersion());
                System.out.println("UssdOpType "+ussdBegin.getUssdOpType());
                System.out.println("MsIsdn"+ussdBegin.getMsIsdn());
                System.out.println("ServiceCode "+ussdBegin.getServiceCode());
                System.out.println("CodeScheme "+ussdBegin.getCodeScheme());
                System.out.println("UssdString "+ussdBegin.getUssdString());

                UssdContinue ussdContinue = new UssdContinue(message);
                ussdContinue.setCodeScheme(CodeSchemes.eightDigit);
                ussdContinue.setMsIsdn(ussdBegin.getMsIsdn());
                ussdContinue.setServiceCode(0);
                ussdContinue.setUssdOpType(UssdOpTypes.Response);
                ussdContinue.setUssdString(ussdBegin.getUssdString().concat("Welcome to Nile Pay!"));
                ussdContinue.setUssdString("@*422@Hello,World!.");
                ussdContinue.setUssdVersion(ussdBegin.getUssdVersion());
                //ussdContinue.setReceiverCB( 0xFFFFFFFF);
                //ussdContinue.setSenderCB(ussdBegin.getSenderCB());

                ussdContinue.encode();
                System.out.println("---------------------------");
                System.out.println("MsIsdn" +ussdContinue.getMsIsdn());
                System.out.println("CodeScheme" +ussdContinue.getCodeScheme());
                System.out.println("UssdString" +ussdContinue.getUssdString());
                System.out.println("ServiceCode" +ussdContinue.getServiceCode());
                System.out.println("UssdOpType" +ussdContinue.getUssdOpType());
                System.out.println("ReceiverCB" +ussdContinue.getReceiverCB());
                System.out.println("SenderCB" +ussdContinue.getSenderCB());
                System.out.println("---------------------------");
                System.out.println("CommandLength" +ussdContinue.getCommandLength());
                System.out.println("CommandID" +ussdContinue.getCommandID());


                client.sendBytes(ussdContinue.encode());
                //client.sendBytes(new PrepareUSSDContinue(message).buildUssdString());
                break;
            case UssdContinue:
                System.out.println("USSD continue..");
            case UssdAbort:
                System.out.println("ussd abort");
                break;
            default:
                System.out.println("Invalid response...");

        }
    }

}
