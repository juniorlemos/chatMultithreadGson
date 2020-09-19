package chatjson;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import com.google.gson.*;

import br.ufc.quixada.sd.model.Mensagem;

public class Servidor extends Thread {
  
    private static Vector clientes;
    private Socket cnx;
    private String nome;
   
 
    public Servidor(Socket socket) {
        this.cnx = socket;
       
    }
  
    
    public static void main(String args[]) {
       clientes = new Vector();
        try {
            ServerSocket server = new ServerSocket( 7896);

            System.out.println("Servidor está no ar");
 
       
            while (true) {
                Socket conexao = server.accept();
                Servidor socket = new Servidor(conexao);
                Thread t = new Thread(socket);
                t.start();
               
          
            }
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }
    }
    public void run()
    {
        try {
            
    BufferedReader in = new BufferedReader(new InputStreamReader(this.cnx.getInputStream()));
    PrintStream on = new PrintStream(this.cnx.getOutputStream());
    
    InputStream is = cnx.getInputStream();
	DataInputStream dataIS = new DataInputStream( is );
    
	OutputStream os = cnx.getOutputStream();
	DataOutputStream dataOS = new DataOutputStream( os );
            this.nome = in.readLine();
    
            System.out.println(this.nome + " : Conectado");
            
            clientes.add(on);
            String msg = dataIS.readUTF();
            
            Gson g = new Gson();
			Mensagem mensagem = g.fromJson( msg, Mensagem.class );
			
            while (msg != null && !(msg.trim().equals("")))
            {
        inform(on, " escreveu: ", mensagem);                                       
                msg = dataIS.readUTF();
                 mensagem = g.fromJson( msg, Mensagem.class );
                
                
            }
           System.out.println(this.nome + " saiu ");
         //   inform(on, " saiu", " do bate-papo!");
           
       
            this.cnx.close();
        } catch (IOException e) {
            System.out.println("Erro"+" IOException: " + e);
        }
    }
   
    public void inform(PrintStream on, String status, Mensagem mss) throws IOException {
        
    	OutputStream os = cnx.getOutputStream();
		DataOutputStream dataOS = new DataOutputStream( os );
    	Gson g = new Gson();
    	Mensagem m = new Mensagem( mss.getConteudo(),mss.getnome() );
		String jsonMensagem = g.toJson( m );
    	
    	Enumeration e = clientes.elements();
        while (e.hasMoreElements()) {
            PrintStream chat = (PrintStream) e.nextElement();
            if (chat != on) {
            	          
            	chat.println(jsonMensagem);             
            	
            	//chat.println(this.nome +status + msg);
            }
        }
      }
}