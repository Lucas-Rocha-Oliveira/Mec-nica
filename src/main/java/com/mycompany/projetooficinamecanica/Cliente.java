package com.mycompany.projetooficinamecanica;

import java.util.List;//Permite manipular objetos do tipo list
import java.util.ArrayList;//Array dinâmico
import com.google.gson.Gson; // Biblioteca Gson para manipular JSON.
import com.google.gson.GsonBuilder; //Usado para configurar a instancia do Gason.
import com.google.gson.reflect.TypeToken; // Para converter JSON em List<CLiente>.
import java.io.FileReader; //Para ler arquivos
import java.io.FileWriter; // para escrever arquivos.
import java.io.IOException; // Para lidar com exceções de entrada e saída.

/**
 *
 * @author Lucas
 */

/**Classe responsável em gerênciar os clientes do sistema.
 * 
 * @author jvict
 */
public class Cliente extends Pessoa{
    String endereco;
    String telefone;
    
    static List<Cliente> listaClientes = new ArrayList<>();
    
    /**Construtor vazio para o funcionamento correto da conversão da instância para json.
     * 
     */
    Cliente(){
        super("","");
    };
    
    Cliente(String nome, String cpf, String endereco, String telefone){
        super(nome, cpf);
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    /**Subescrição do metodo toString herdado de Pessoa, adicionando Enderço e Telefone.
     * 
     * @return 
     */
    @Override 
    public String toString(){
        return super.toString() + "\nEndereço: " + getEndereco() + "\nTelefone: " + getTelefone() + "\n";
    }
    
    /**Metodo usado para carregar os clientes do arquivo json para o array.
     * 
     */
     /**
     * Carrega a lista de clientes a partir de um arquivo JSON.
     * Utiliza o GerenciadorJson para obter uma instância configurada do Gson,
     * garantindo que todos os TypeAdapters necessários sejam utilizados.
     * Se o arquivo não for encontrado ou estiver vazio, uma nova lista de clientes
     * é criada para evitar erros de NullPointerException.
     */
    public static void carregarClientes(){
        // Obtém a instância customizada e centralizada do Gson.
        Gson gson = GerenciadorJson.getGson(); 
        
        // Usa um try-with-resources para garantir que o leitor de arquivo seja fechado automaticamente.
        try(FileReader reader = new FileReader("data/clientes.json")){
            // Converte o texto do JSON para uma lista de objetos Cliente.
            listaClientes = gson.fromJson(reader, new TypeToken<List<Cliente>>(){}.getType());
            
            // Verificação de segurança: se o arquivo JSON estiver vazio, gson.fromJson retorna null.
            if (listaClientes == null) {
                listaClientes = new ArrayList<>();
            }
        } catch(IOException e){
            // Informa ao usuário caso o arquivo não exista e inicializa uma lista vazia.
            System.out.println("Arquivo de clientes não encontrado. Criando nova lista.");
            listaClientes = new ArrayList<>();
        }
    }
    /**Metodo suado para listar os clientes armazenados no Array de clientes.
     * 
     */
    public static void listarClientes(){
        /**for percorrendo o array de clientes e printando.
         * 
         */
        for(Cliente u : listaClientes){
            System.out.print(u);
        }
    }
    
    /**Metodo usado para armazenar um cliente dentro do array de clientes.
     * 
     * @param cliente 
     */
    public static void adicionarCliente(Cliente cliente){
        listaClientes.add(cliente);
    }
    
    /**
     * Salva a lista de clientes atual em um arquivo JSON.
     * Este método utiliza uma instância do Gson fornecida pelo GerenciadorJson
     * para garantir que a serialização seja feita corretamente.
     */
    public static void salvarClientes(){
        Gson gson = GerenciadorJson.getGson(); // Usa o gerenciador
        try(FileWriter writer = new FileWriter("data/clientes.json")){
            gson.toJson(listaClientes, writer);
        } catch(IOException e){
            System.out.println("Erro ao escrever o arquivo de clientes: " + e.getMessage());
        }
    }
    
    /**Metodo responsável por apagar um cliente.
     * 
     * @param cpf 
     */
    public static void removerClientePorCpf(String cpf){
        boolean removido = listaClientes.removeIf(c -> c.getCpf().equals(cpf));
        
        if(removido){
            System.out.println("Cliente removido com sucesso!");
            salvarClientes();
        }else{
            System.out.println("ERRO - Cliente não encontrado");
        }
        
        
    }
    
    /**Metodo responsável por verificar a existencia de um cliente por cpf.
     * 
     * @param cpf
     * @return true se o cliente existir
     */
    public static boolean verificarClientePorCpf(String cpf){
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                return true;
            }
        }
        System.out.println("Esse cpf não pertence a nenhum cliente.");
        return false;
    }
    
    /**Metodo responsável por editar o nome de um cliente que esta dentro do Array.
     * 
     * @param cpf
     * @param nome 
     */
    public static void editarNomeCliente(String cpf, String nome){
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                u.setNome(nome);
                break;
            }
        }
        salvarClientes();
        System.out.println("Nome alterado com sucesso!");
    }
    
    /**Metodo responsável por editar o cpf de um cliente que esta dentro do Array.
     * 
     * @param cpf
     * @param novocpf 
     */
    public static void editarCpfCliente(String cpf, String novocpf){
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                u.setCpf(novocpf);
                break;
            }
        }
        salvarClientes();
        System.out.println("Cpf alterado com sucesso!");
    }
    
    /**Metodo responsável por editar o Endereço de um cliente que esta dentro do Array.
     * 
     * @param cpf
     * @param endereco 
     */
    public static void editarEnderecoCliente(String cpf, String endereco){
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                u.setEndereco(endereco);
                break;
            }
        }
        salvarClientes();
        System.out.println("Endereço alterado com sucesso!");
    }
    
    /**Metodo responsável por editar o Endereço de um cliente que esta dentro do Array.
     * 
     * @param cpf
     * @param telefone 
     */
    public static void editarTelefoneCliente(String cpf, String telefone){
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                u.setTelefone(telefone);
                break;
            }
        }
        salvarClientes();
        System.out.println("Telefone alterado com sucesso!");
    }
    
    /**Metodo responsável por retornar um cliente, como busca o seu cpf.
     * 
     * @param cpf
     * @return O objeto Cliente correspondente ao CPF, ou null se não for encontrado.
 */

    public static Cliente buscarClientePorCpf(String cpf){
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                return u;
            }
        }
        System.out.println("Cliente não registrado.");
        return null;
    }
}
