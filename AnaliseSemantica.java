
import java.util.ArrayList;

/**
 *
 * @author Gabriel Gomes e Nayara G. de Oliveira
 */
public class AnaliseSemantica {

    /**
     * Vetores que recebem a sequencia de caracteres.
     */
    private final ArrayList folhas = new ArrayList();
    private final ArrayList galho1 = new ArrayList();
    private final ArrayList galho2 = new ArrayList();
    private final ArrayList galho3 = new ArrayList();

    String arvore;

    public void adicionar(String token) {
        folhas.add(token);
    }

    public void adicionarGalho1(String token) {
        galho1.add(token);
    }

    public void adicionarGalho2(String token) {
        galho2.add(token);
    }

    public void adicionarGalho3(String token) {
        galho3.add(token);
    }

    public String getArvore() {
        return arvore;
    }

    /**
     * A cadeia de caracteres produzida pela análise semântica é colocada na String arvoreLocal.
     */
    public void gerarArvore() {
        String arvoreLocal = "";

        for (int i = 0; i < folhas.size(); i++) {
            arvoreLocal = arvoreLocal + folhas.get(i) + "         ";
        }

        arvoreLocal = arvoreLocal + "\n\n";

        for (int i = 0; i < galho1.size(); i++) {
            arvoreLocal = arvoreLocal + galho1.get(i) + "   ";
        }

        for (int i = 0; i < galho2.size(); i++) {            
            if (galho2.get(i).equals("#")) {
                arvoreLocal = arvoreLocal + "\n\n";
            } else {
                arvoreLocal = arvoreLocal + galho2.get(i) + "   ";
            }
        }

        for (int i = 0; i < galho3.size(); i++) {
            if (galho3.get(i).equals("#")) {
                arvoreLocal = arvoreLocal + "\n\n";
            } else {
                arvoreLocal = arvoreLocal + galho3.get(i) + "   ";
            }
        }
        
        String simboloInicial = "<STATEMENT>";
        arvoreLocal = arvoreLocal + "\n\n";
        for (int i = 0; i < galho3.size() - 1; i++) {
            arvoreLocal = arvoreLocal + simboloInicial;
        }
        
        arvoreLocal = arvoreLocal + "\n\n" + simboloInicial;
        this.arvore = normalizar(arvoreLocal);

        this.arvore = this.arvore + "\n";
        for (int i = 0; i < folhas.size(); i++) {
            this.arvore = this.arvore + folhas.get(i) + "           ";
        }

        System.out.println(arvoreLocal);
    }

    /**
     * Método para colocar a árvore em ordem.
     * @param s
     * @return 
     */
    public String normalizar(String s) {
        String resultado = "";
        String linha = "";
        int ct = s.length();
        while (ct > 0) {
            linha = linha + s.charAt(ct - 1);
            if (s.charAt(ct - 1) == '\n') {
                resultado = resultado + inverter(linha);
                linha = "";
            }
            ct--;
        }
        return resultado;
    }

    public String inverter(String linha) {
        String resultado = "";
        for (int i = linha.length() - 1; i >= 0; i--) {
            resultado += linha.charAt(i);
        }
        return resultado;
    }

}
