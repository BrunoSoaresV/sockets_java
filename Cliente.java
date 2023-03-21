import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Cliente {

	private Socket soquete;
	private ObjectOutputStream saida;
	private ObjectInputStream entrada;
	private ArrayList<Mensagem> mensagens;

	public Cliente(String endereco, int porta) throws Exception {
		super();
		this.soquete = new Socket(endereco, porta);
		this.saida = new ObjectOutputStream(this.soquete.getOutputStream());
		this.entrada = new ObjectInputStream(this.soquete.getInputStream());
	}

	public void enviar_mensagem(Object mensagem) throws Exception {
		this.saida.writeObject(mensagem);
	}

	public Object receber_mensagem() throws Exception {
		return this.entrada.readObject();
	}

	public void finalizar() throws IOException {
		this.soquete.close();
	}

	public static void main(String[] args) throws Exception {
		Scanner leia = new Scanner(System.in);
		double x = 0.0;
		String denomina;
		String citacao = "Descarte";
		System.out.println("Por favor, diga o seu nome: ");
		denomina = leia.next();

		while (!citacao.equals("SAIR")) {

			Cliente cliente = new Cliente("127.0.0.1", 15500);

			cliente.mensagens = (ArrayList<Mensagem>) cliente.receber_mensagem();

			for (Mensagem mensagem : cliente.mensagens) {
				System.out.println(mensagem.toString());
			}

			System.out.println("Por favor, diga a sua mensagem: ");
			citacao = leia.next();
			if (citacao.equals("FINALIZAR")) {
				cliente.finalizar();
			}
			cliente.enviar_mensagem(new Mensagem(denomina, citacao));
			cliente.finalizar();
		}
	}
}