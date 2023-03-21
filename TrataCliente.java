

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TrataCliente implements Runnable {
		
		private Socket soquete_cliente;
		private ObjectOutputStream saida;
		private ObjectInputStream entrada;
		private ArrayList<Mensagem> mensagens;
		private ServerSocket soquete_servidor;
		public void finalizars() throws IOException {
			this.soquete_servidor.close();
		}
		
		public TrataCliente(Socket soquete_cliente, ArrayList<Mensagem> mensagens) throws Exception {
			super();
			this.soquete_cliente = soquete_cliente;
			this.saida = new ObjectOutputStream(this.soquete_cliente.getOutputStream()); 
			this.entrada = new ObjectInputStream(this.soquete_cliente.getInputStream());
			this.mensagens = mensagens;
		}
		
		public void enviar_mensagem(Object mensagem) throws Exception {
			this.saida.writeObject(mensagem);
		}
		
		public Object receber_mensagem() throws Exception {
			return this.entrada.readObject();
		}
		
		public void finalizar() throws IOException {
			this.soquete_cliente.close();
		}
		
		@Override
		public void run() {
			try {
				enviar_mensagem(this.mensagens);
				Mensagem mensagem = (Mensagem)receber_mensagem();
				System.out.println(mensagem.toString());
				this.mensagens.add(mensagem);
				if(!mensagem.equals("FINALIZAR")) 
				{
					finalizar();
				}
				finalizar();
			} catch (Exception e) 
			      {
				e.printStackTrace();
				}
			}
	}