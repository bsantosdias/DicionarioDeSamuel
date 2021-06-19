import java.io.*;

/**
 *
 * @author bsantosdias
 */

public class DicionarioDoSamuel {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String entrada = LerArquivo();
        String[] valorComSplit = entrada.split(" ");
        String[] valorCompleto = LimpaValorSplit(valorComSplit);
        String[] dicionario = OrdenaDicionario(valorCompleto);
        PrintDicionario(dicionario);

    }

    // Abre, lê o arquivo txt e guarda seu conteudo em uma variavel String.
    private static String LerArquivo() throws FileNotFoundException, IOException {
        String texto = "";
        FileReader arquivo = new FileReader("entrada.txt");

        // Gera um fluxo bufferizado para fazer a leitura de linhas.
        BufferedReader leBufferizado;
        leBufferizado = new BufferedReader(arquivo);

        // Le as linhas do arquivo e guarda na variavel texto, separando-as com um espaço. 
        //Leitura é interrompido quando chegar ao ".".
        String linha = leBufferizado.readLine().toLowerCase();
        while (linha != null) {
            texto += linha + " ";
            linha = leBufferizado.readLine().toLowerCase();
            if (linha.compareTo(".") == 0) {
                linha = null;
            }
        }
        arquivo.close();
        return texto;
    }

    /* Le o vetor já separado com o .split, verifica se possui valores com preenchidos com espaço nele
    e, caso possua, os joga para o final do vetor.*/  
    private static String[] LimpaValorSplit(String[] valorComSplit) {
        for (int i = 0; i < valorComSplit.length; i++) {
            if (valorComSplit[i].equals("")) {
                for (int j = i; j < valorComSplit.length - 1; j++) {
                    valorComSplit[j] = valorComSplit[j + 1];
                    valorComSplit[j + 1] = "";
                }
            }
        }

        return valorComSplit;
    }

    //Monta do dicionario, já de forma organizada e sem repetições. 
    private static String[] OrdenaDicionario(String[] valorCompleto) {

        String[] dicionario = new String[1000];
        String auxiliar1 = "";
        String auxiliar2 = "";
        boolean ordenador = false;

        //Preenche dicionario com " ", para que a busca binaria consiga rodar. 
        for (int i = 0; i < dicionario.length; i++) {
            dicionario[i] = "";
        }

        for (int i = 0; i < valorCompleto.length; i++) {
            for (int j = 0; j < dicionario.length - 1; j++) {
                if (!ordenador) {
                    /*Caso a casa atual do dicionario esteja "vazia" e palavra não tenha sido encontrada no vetor com a 
                    busca binaria, palavra será adicionado ao vetor final.*/
                    if (dicionario[j] == "") {
                        if (!BuscaBinaria(dicionario, valorCompleto[i])) {
                            dicionario[j] = valorCompleto[i];
                            break;
                        } else {
                            break;
                        }
                    } else {
                        /*Caso palavra não seja repetida, programa rodadará até que a palavra atual a ser adicionada
                        venha antes da palavra atual do dicionario e a adicionará, usando esquema de variavel auxiliar
                        que organizará proximas palavras do dicionario.*/
                        if (!BuscaBinaria(dicionario, valorCompleto[i])) {
                            if (dicionario[j].compareTo(valorCompleto[i]) > 0) {
                                auxiliar1 = dicionario[j + 1];
                                dicionario[j + 1] = dicionario[j];
                                dicionario[j] = valorCompleto[i];
                                ordenador = true;
                            }
                        } else {
                            break;
                        }
                    }
                } else {
                    if (auxiliar1 == "") {
                        break;
                    } else {
                        //Organizando restante do dicionario após inserção de nova palavra. 
                        auxiliar2 = dicionario[j + 1];
                        dicionario[j + 1] = auxiliar1;
                        auxiliar1 = auxiliar2;
                    }
                }
            }
            ordenador = false;
        }

        return dicionario;
    }
    
    // Busca binaria para verificar se palavra atual já consta no dicionario. 
    private static boolean BuscaBinaria(String dicionario[], String palavra) {
        int i, m, f;
        i = 0;
        f = -1;

        for (int j = 0; j < dicionario.length; j++) {
            if (dicionario[j] != "") {
                f++;
            }
        }

        while (i <= f) {
            m = (i + f) / 2; //Encontrar meio.
            if (palavra.compareTo(dicionario[m]) == 0) {
                return true; //Palavra foi encontrada no vetor. 
            }            
            if (palavra.compareTo(dicionario[m]) < 0) {
                f = m - 1; //Procurar na esquerda. 
            } else {
                i = m + 1; //Procurar na direita. 
            }
        }
        return false; //Palavra não encontrada.

    }
    
    // Imprime dicionario completo e quantidade de palavras. 
    private static void PrintDicionario(String[] dicionario) {
        
        int contador = 0; //Quantidade de palavras. 
        
        for (String palavra : dicionario) {
            //Imprimir valores que sejam diferentes de " ", pois todas as posições do vetor foram preenchidas com ele. 
            if (palavra != "") {
                System.out.println(palavra);
                contador++; 
            }
        }
        System.out.println("Total de palavras diferentes no dicionario = " + contador);
    }
}
