// echo server
import ecole.Annee;
import ecole.Ecole;
import ecole.Matiere;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {

    public static void main(String args[]){
        final Ecole isen = Ecole.getInstance();
        isen.genererNotes();

        Socket s=null;
        ServerSocket ss2=null;
        System.out.println("Server Listening......");
        try{
            ss2 = new ServerSocket(8080); // can also use static final PORT_NUM , when defined

        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Server error");

        }

        while(true){
            try{
                s= ss2.accept();
                System.out.println("connection Established");
                ServerThread st=new ServerThread(s);
                st.start();

            }

            catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }

    }

}

class ServerThread extends Thread{

    private final transient Ecole isen = Ecole.getInstance();
    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    OutputStreamWriter out = null;

    public ServerThread(Socket s){
        this.s=s;
    }

    public void run() {
        try{
            is= new BufferedReader(new InputStreamReader(s.getInputStream()));
            os=new PrintWriter(s.getOutputStream());
            out= new OutputStreamWriter(s.getOutputStream());

        }catch(IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            line=is.readLine();
            while(line.compareTo("QUIT")!=0){
                switch(line) {
                    case "getAnnee()":
                        JSONArray annees = new JSONArray(Annee.values());
                        line= annees.toString();
                        break;

                    case "getMatiere()":
                        JSONArray listeMatiere = new JSONArray(Matiere.values());
                        line= listeMatiere.toString();
                        break;

                    case "getClasse()":
                        JSONArray listeClasse = new JSONArray(isen.getListeDesClasse());
                        line= listeClasse.toString();
                        break;
                }
                os.println(line);
                os.flush();
                System.out.println("Response to Client  :  "+line);
                line=is.readLine();
            }
        } catch (IOException e) {

            line=this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        }
        catch(NullPointerException e){
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client "+line+" Closed");
        }

        finally{
            try{
                System.out.println("Connection Closing..");
                if (is!=null){
                    is.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if(os!=null){
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s!=null){
                    s.close();
                    System.out.println("Socket Closed");
                }

            }
            catch(IOException ie){
                System.out.println("Socket Close Error");
            }
        }//end finally
    }
}