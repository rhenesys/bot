package bot;

public class Adivinha {
    
    private int id;
    private String pergunta;
    private String resposta;

    public Adivinha(){}

    public Adivinha(int id, String pergunta, String resposta){
        this.id = id;
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPergunta() {
        return this.pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return this.resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

}