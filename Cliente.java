package chatjson;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.*;


public class Cliente extends Thread { 

    private Socket cnx;  
    
            public Cliente(Socket socket) {
            this.cnx = socket;
    }
    public static void main(String args[])
    {                   
                      
        
        try {
                        
            Socket socket = new Socket("localhost",  7896);
            String msg;
            
            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
         
            OutputStream os = socket.getOutputStream();
			DataOutputStream dataOS = new DataOutputStream( os );
			
			
			
			
            System.out.print("Digite seu nome: ");         
            String nome = in.readLine();
            out.println(nome);
            
            Thread thread = new Cliente(socket);
            thread.start();
            
            
           while (true)
            {
               // System.out.print("Responda: ");
        	   Scanner scanner = new Scanner(System. in);
		        String inputString = scanner. nextLine();
		        
        	  
        	   
        	   Mensagem mensagem = new Mensagem( inputString, nome );
		        
		        Gson gson = new Gson();
				String contatoString = gson.toJson( mensagem );
				
				//Mandando a mensagem pela rede
				//out.println( contatoString );	
        	   
        	   dataOS.writeUTF(contatoString);
                //msg = in.readLine();
                //out.println(msg);
            }
          } catch (IOException ex) {
            System.out.println("Nao conectou!" + " IOException: " + ex);
          }
          
                                     

                        
                        
    }                  
                        
        
    
  // execução da thread
    public void run()
    {
        try {
            String msg;
            Gson gson = new Gson();
            BufferedReader entrada = new BufferedReader(new InputStreamReader(this.cnx.getInputStream()));
            while (true)
            {
            	msg = entrada.readLine();
            	
                if (msg == null) {
                    System.out.println("Fim de conexao!");
                    System.exit(0);
                }
             
         
                Mensagem resposta = gson.fromJson( msg, Mensagem.class );
                
                
                System.out.println(resposta.getnome() +" Escreveu :");
                System.out.println(resposta.getConteudo());
                
            }
        } catch (IOException ex) {
            System.out.println("Erro" + " IOException: " + ex);
        }
    }

}