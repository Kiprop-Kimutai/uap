package app.uap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/client")
public class ClientController {
    @Autowired
    private Client client;

    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public String test() {
        return "ok...";
    }
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public void sendBytes(@RequestBody String request) throws IOException {
        client.sendBytess(request.getBytes());
    }
}
