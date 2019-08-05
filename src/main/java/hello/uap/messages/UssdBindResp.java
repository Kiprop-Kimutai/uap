package hello.uap.messages;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ghasem Fattahpour
 */
public class UssdBindResp extends MessageBase {

    public UssdBindResp(byte[] message) {
        this.Message = message;
        this.dencode();
        this.CommandID = CommandIDs.UssdUnBindResp;
    }

    @Override
    protected boolean dencode() {
        super.dencode(); //To change body of generated methods, choose Tools | Templates.
        //this.AccountName = StringUtility.GetCOctetStringFromBytes(this.Message, 20, 11);
        return true;
        
    }

    public String getAccountName() {
        return AccountName;
    }
    
    

}
