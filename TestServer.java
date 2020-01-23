package ma_most_useful;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;

public class TestServer {
    private boolean started=false;
    private ServerSocket ss=null;

    public static void main(String[] args) {
        new TestServer().start();
    }

    public TestServer(){
        try {
            ss=new ServerSocket(8888);
            started=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
//System.out.println("1");
    }

    public void start(){//循环创建线程，多线程连接了
        Socket s= null;
        try {
            while (started) {
                s = ss.accept();
                Client c=new Client(s);
                new Thread(c).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable{
        private Socket s=null;
        private boolean Connected=false;
        private DataInputStream dis=null;

        public Client(Socket s){
            this.s=s;
            Connected=true;
            try {
//                s=ss.accept();
                dis = new DataInputStream(s.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            try {
                while (Connected) {
                    String str=dis.readUTF();
                    System.out.println(str);
                }
            } catch (EOFException e){
//                e.printStackTrace();
                System.out.println("Client closed");
//                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
//                System.exit(0);
            }finally {
                try {

                    dis.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}

