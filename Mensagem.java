package chatjson;

public class Mensagem {
	
	private static int contador = 0;
	private int id;
	private String conteudo;
	private String nome;
	
	public Mensagem(String conteudo, String nome) {
		super();
		this.conteudo = conteudo;
		this.nome = nome;
	}

	
	public String getnome() {
		return nome;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	
	
	

}
