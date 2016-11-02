import java.util.ArrayList;

/**
 *
 * @autores Gabriel de Sousa Gomes e Nayara Gomes de Oliveira Pereira
 */
public class AnaliseLexica {

    /**
     * Texto digitado na janela é setado nesta variável.
     */
    private String programa;

    AnaliseLexica(String programa) {
        this.programa = programa;
    }

    /**
     * Este método remove os espaços em branco, quebras de linhas e tabulações
     * da string "programa".
     *
     * @return erro ou sucesso na compilação.
     */
    public String escandimento() {
        programa = programa.replace('\n', ';').replace('\t', ';').replace(' ', ';').replace('\r', ';') + ";";
        return lexemas();
    }

    /**
     * Este método quebra o texto palavra por palavra.
     *
     * @return erro ou sucesso na compilação.
     */
    public String lexemas() {
        System.out.println(programa);

        ArrayList lexemas = new ArrayList();
        String palavra = "";

        char vetor[] = programa.toCharArray();
        //Para cada ";" encontrado a string é quebrada e este pedaço é armazenado no ArrayList "lexemas".
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] != ';') {
                palavra = palavra + vetor[i];
                if (vetor[i + 1] == ';') {
                    lexemas.add(palavra);
                    palavra = "";
                }
            }
        }

//        for (int i = 0; i < lexemas.size(); i++) {
//            System.out.println(lexemas.get(i));
//        }
        System.out.println("***************************************************");
         return produzirTokens(lexemas);
    }

    /**
     * Este método verifica se cada índice do Array de lexemas existe na linguagem definida por nós.
     * @param lexemas (ArrayList) com os lexemas encontrados 
     * @return palavra inexistente na linguagem, caso haja alguma, caso conrário, retorna sucesso.
     */
    public String produzirTokens(ArrayList lexemas) {
        Linguagem linguagem = new Linguagem();
        String erro = "";
        String sucesso = "Compilou sem erros";
        for (int i = 0; i < lexemas.size(); i++) {
            //System.out.println(linguagem.fazerCasamento(lexemas.get(i).toString()));
            
            /*Se não for encontrado na linguagem um padrão que bata com o lexema, 
            o mesmo é retornado na variável "erro" para ser exibida numa pop-up como mensagem de erro.*/
            erro = linguagem.fazerCasamento(lexemas.get(i).toString());
            if (erro.charAt(erro.length() - 1) == '.') {
                return erro;
            }
        }
        Object tokens[] = linguagem.getTokens().toArray();
        AnaliseSintatica sintatica = new AnaliseSintatica(tokens);
        String bug = sintatica.parse();
        if (bug == null) {
            return sucesso;
        }
        return bug;
    }

}
