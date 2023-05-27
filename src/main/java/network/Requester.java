package network;

import org.json.simple.JSONObject;
import network.protocol.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Requester {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 8080;
    private Socket socket;

    private DataInputStream dis;
    private DataOutputStream dos;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private JSONObject cookie;

    private void setConnection() {
        try {
            socket = new Socket(IP, PORT);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(dos);
            ois = new ObjectInputStream(dis);
        }
        catch(UnknownHostException e){
            System.err.println("서버를 찾지 못했습니다.");
        }
        catch(IOException e){
            System.err.println(e);
        }
    }

    public Response sendRequest(Request req) {
        Response res = null;

        try {
            setConnection();

            oos.writeObject(req);
            oos.flush();

            res = (Response) ois.readObject();
        }
        catch (Exception e) {

        }

        return res;
    }
}
