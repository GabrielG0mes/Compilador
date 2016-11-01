import java.util.ArrayList;


public class Linguagem {

    ArrayList tokens = new ArrayList();
    
    String identificador = "#[A-Z|a-z|0-9]+";
    String numero = "[0-9]+";

    String soma = "M415";
    String subtrai = "M3N05";
    String multiplica = "V3Z35";
    String divide = "D1V1D1D0";
    String atribui = "<-";

    
    String abreparentese ="[(]";
    String fechaparentese = "[)]";

    String escreva = "35CR3V4";

    String inicio = "1N1C10";
    String fim = "F1M";

    String padroes[] = {identificador, numero, soma, subtrai, multiplica, divide,
        atribui, abreparentese, fechaparentese, escreva, inicio, fim};

    public String fazerCasamento(String lexema) {
        boolean b;
        String token;
        for (int i = 0; i < padroes.length; i++) {
            b = lexema.matches(padroes[i]);
            if (b) {
                tokens.add(lexema);
                token = lexema + " - " + nomePadrao(i);
                return token;
            } 
        }
        return lexema + "-Símbolo não permitido nesta linguagem.";
    }
    
    public ArrayList getTokens() {
        return tokens;
    }

    public String nomePadrao(int index) {
        switch(index) {
            case 0: 
                return "identificador";
            case 1:
                return "número";
            case 2:
                return "operador";
            case 3:
                return "operador";
            case 4:
                return "operador";
            case 5:
                return "operador";
            case 6:
                return "atribuição";
            case 7:
                return "separador";
            case 8:
                return "separador";
            case 9:
                return "palavra reservada";
            case 10:
                return "palavra reservada";
            case 11:
                return "palavra reservada";
            default:
                return "Erro léxico";
        }
    }
}
