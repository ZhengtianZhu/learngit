package ma_most_useful;

import java.io.*;
import java.net.*;
import java.rmi.ConnectException;

public class TestClient {
    private boolean started=false;
    private DataOutputStream dos=null;
    private String str=null;
    private Socket s=null;

    public static void main(String[] args) {
        new TestClient().start();
    }

    public TestClient(){
        started=true;

    }

    public void connect(){
        try {
            s=new Socket("127.0.0.1",8888);
//System.out.println("connect");
            dos = new DataOutputStream(s.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String str){
        try {
            dos.writeUTF(str);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            str=br.readLine();
            System.out.println(str);
            send(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (started&&!str.equals("bye")) {
                str=br.readLine();
                System.out.println(str);
                send(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public void disconnect(){
        try {
            s.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


