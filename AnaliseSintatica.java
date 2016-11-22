import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Gabriel Gomes e Nayara G. de Oliveira
 */
public class AnaliseSintatica {

    /**
     * Vetor que irá armazenar os tokens advindos da análise léxica.
     */
    Object terminais[];
    /**
     * Pilha para auxiliar na análise sintatica.
     */
    Stack pilha = new Stack();
    /**
     * Vetores que irão armazenar a derivação temporariamente.
     */
    ArrayList aux = new ArrayList();
    ArrayList temp = new ArrayList();
    ArrayList precedencia = new ArrayList();
    AnaliseSemantica semantica = new AnaliseSemantica();

    private final String inicial = "<STATEMENT>";
    
    private boolean arvore;
    private String arvoreGerada;
    /**
     * Árvore de parse.
     */
    private final String regrasProducao[][] = {
        {"<STATEMENT>", "<ESCREVA>"},
        {"<STATEMENT>", "<ATRIBUICAO>"},
        {"<ESCREVA>", "<ID><ESCR>"},
        {"<ESCREVA>", "<NUM><ESCR>"},
        {"<ESCREVA>", "<OPERACAO><ESCR>"},
        {"<ATRIBUICAO>", "<ID><SETA><ID>"},
        {"<ATRIBUICAO>", "<NUM><SETA><ID>"},
        {"<ATRIBUICAO>", "<OPERACAO><SETA><ID>"},
        {"<OPERACAO>", "<ID><OP_ADD><ID>"},
        {"<OPERACAO>", "<ID><OP_MUL><ID>"},
        {"<OPERACAO>", "<ID><OP_ADD><NUM>"},
        {"<OPERACAO>", "<ID><OP_MUL><NUM>"},
        {"<OPERACAO>", "<NUM><OP_ADD><ID>"},
        {"<OPERACAO>", "<NUM><OP_MUL><ID>"},
        {"<OPERACAO>", "<NUM><OP_ADD><NUM>"},
        {"<OPERACAO>", "<NUM><OP_MUL><NUM>"},
        {"<OPERACAO>", "<OPERACAO><OP_ADD><ID>"},
        {"<OPERACAO>", "<OPERACAO><OP_MUL><ID>"},
        {"<OPERACAO>", "<OPERACAO><OP_ADD><NUM>"},
        {"<OPERACAO>", "<OPERACAO><OP_MUL><NUM>"},
        {"<OPERACAO>", "<OPERACAO><OP_ADD><OPERACAO>"},
        {"<OPERACAO>", "<OPERACAO><OP_MUL><OPERACAO>"},
        {"<ESCR>", "35CR3V4"},
        {"<ID>", "#[A-Z|a-z|0-9]+"},
        {"<NUM>", "[0-9]+"},
        {"<SETA>", "<-"},
        {"<OP_ADD>", "M415"},
        {"<OP_ADD>", "M3N05"},
        {"<OP_MUL>", "V3Z35"},
        {"<OP_MUL>", "D1V1D1D0"}
    };

    /**
     * Construtor que recebe os tokens e armazena no Array de símbolos
     * terminais.
     *
     * @param tokens
     */
    public AnaliseSintatica(Object tokens[]) {
        terminais = tokens;
    }

    public String getArvoreGerada() {
        return arvoreGerada;
    }
    
    

    /**
     * Efetua o parse dos elementos do vetor "terminais".
     *
     * @return mensagem de sucesso ou erro.
     */
    public String parse() {
        /**
         * Verifica a existência das palavras reservadas "1N1C10" e "F1M" que
         * delimitam o escopo do algoritmo.
         */
        for (int i = 1; i < terminais.length - 1; i++) {
            semantica.adicionar(terminais[i].toString());
        }
        
        if (!terminais[0].equals("1N1C10")) {
            return "O programa deve iniciar com '1N1C10'";
        }
        if (!terminais[terminais.length - 1].equals("F1M")) {
            return "O programa deve terminar com 'F1M'";
        }
        /**
         * Para cada símbolo terminal, realiza o "shift-reduce".
         */
        for (Object terminai : terminais) {
            shift_reduce(terminai.toString());
        }
        //aplicarPrecedencia(); Criar método precedência
        for (int i = 0; i < aux.size(); i++) {
            semantica.adicionarGalho1(aux.get(i).toString());
        }
        
        
        /**
         * Faz derivação dos símbolos não terminais obtidos no shift-reduce.
         */
        inverteAux();

        for (int i = 0; i < aux.size() * 2; i++) {
            derivarOperacoes3();
            aux = temp;
            if (arvore) {
                semantica.adicionarGalho2("#");
                for (int j = aux.size() - 1; j >= 0; j--) {
                    semantica.adicionarGalho2(aux.get(j).toString());
                }
            }
            temp = new ArrayList();
        }

        /**
         * Faz a derivação final para encontrar o símbolo inicial. Caso não seja
         * encontrado, retorna erro.
         */
        for (int i = 0; i < aux.size() * 2; i++) {
            derivarOperacoes2();
            aux = temp;
            if (arvore) {
                semantica.adicionarGalho3("#");
                for (int j = aux.size() - 1; j >= 0; j--) {
                    semantica.adicionarGalho3(aux.get(j).toString());
                }
            }
            temp = new ArrayList();
        }
        
        String erro;
        for (int i = 0; i < aux.size(); i++) {
            if (!aux.get(i).equals("<ESCREVA>") && !aux.get(i).equals("<ATRIBUICAO>")) {
                erro = aux.get(i).toString();
                return erro(erro);
            }
        }
        
        semantica.gerarArvore();
        arvoreGerada = semantica.getArvore();
        return null;
    }

    /**
     * Efetua o shift-reduce.
     *
     * @param s
     */
    public void shift_reduce(String s) {
        pilha.push(s);
        for (String[] regrasProducao1 : regrasProducao) {
            if (s.matches(regrasProducao1[1])) {
                pilha.pop();
                aux.add(regrasProducao1[0]);
            }
        }
    }

    public String shift_reduce(String s1, String s2, String s3) {
        String s = s1 + s2 + s3;
        //pilha.push(s);
        for (String[] regrasProducao1 : regrasProducao) {
            if (s.matches(regrasProducao1[1])) {
                //pilha.pop();
                return regrasProducao1[0];
            }
        }
        return null;
    }

    public String shift_reduce(String s1, String s2) {
        String s = s1 + s2;
        pilha.push(s);
        for (String[] regrasProducao1 : regrasProducao) {
            if (s.matches(regrasProducao1[1])) {
                pilha.pop();
                return regrasProducao1[0];
            }
        }
        return null;
    }

    /**
     * Ordena o vetor auxiliar.
     */
    public void inverteAux() {
        pilha = new Stack();
        for (int i = 0; i < aux.size(); i++) {
            pilha.push(aux.get(i));
        }
        aux = new ArrayList();
        while (!pilha.isEmpty()) {
            aux.add(pilha.pop());
        }
    }
    
     
    /**
     * Faz a derivação seguindo a árvore de parse.
     */
    public void derivarOperacoes3() {
        
        String result = null;

        for (int i = 0; i < aux.size(); i++) {
            try {
                result = shift_reduce(aux.get(i).toString(), aux.get(i + 1).toString(), aux.get(i + 2).toString());
            } catch (Exception e) {

            }
            if (result != null) {
                arvore = true;
                temp.add(result);
                i = i + 3;
                for (int j = i; j < aux.size(); j++) {
                    temp.add(aux.get(j));
                }
                break;
            } else {
                arvore = false;
                try {
                    temp.add(aux.get(i));
                } catch (Exception e) {

                }
            }
        }
        //oto.add(aux.get(aux.size() - 1));
    }

    /**
     * Faz a derivação seguindo a árvore de parse.
     */
    public void derivarOperacoes2() {
        
        String result = null;

        for (int i = 0; i < aux.size(); i++) {
            try {
                result = shift_reduce(aux.get(i).toString(), aux.get(i + 1).toString());
            } catch (Exception e) {

            }
            if (result != null) {
                arvore = true;
                temp.add(result);
                i = i + 2;
                for (int j = i; j < aux.size(); j++) {
                    temp.add(aux.get(j));
                }
                break;
            } else {
                arvore = false;
                try {
                    //System.out.println("o null: " + aux.get(i));
                    temp.add(aux.get(i));
                } catch (Exception e) {

                }
            }
            
        }
    }
    
    public String erro(String s) {
        switch (s) {
            case "<ESCR>":
                return "Falta um número ou identificador  após '35CR3V4'";
            case "<ID>":
                return "Indentificador fora de '35CR3V4', sem atribuição ou fora de operação";
            case "<NUM>":
                return "Número fora de '35CR3V4', sem atribuição ou fora de operação";
            case "<OPERACAO>":
                return "Operação não atribuida a nenhum valor";
            case "<OP_ADD>": {
                return "Há operador sem operando";
            }
            case "<OP_MUL>": {
                return "Há operador sem operando";
            }
        }
        return "Erro";
    }

}
