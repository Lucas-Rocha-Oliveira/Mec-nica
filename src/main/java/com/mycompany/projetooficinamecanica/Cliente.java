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

//Classe responsável em gerênciar os clientes do sistema.
public class Cliente extends Pessoa{
    String endereco;
    String telefone;
    
    static List<Cliente> listaClientes = new ArrayList<>();
    
    //Construtor vazio para o funcionamento correto da conversão da instância para json.
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
    
    //Subescrição do metodo toString herdado de Pessoa, adicionando Enderço e Telefone.
    @Override 
    public String toString(){
        return super.toString() + "\nEndereço: " + getEndereco() + "\nTelefone: " + getTelefone() + "\n";
    }
    
    //Metodo usado para carregar os clientes do arquivo json para o array.
    public static void carregarClientes(){
        //Cria um objeto do tipo Gson usado para o processo de conversão entre java e json.
        Gson gson = new Gson();
        
        try(FileReader reader = new FileReader("data/clientes.json")){
            //Carregamento do aquivo json para o array de clientes.
            listaClientes = gson.fromJson(reader, new TypeToken<List<Cliente>>(){}.getType());
        } catch(IOException e){
            //Captura de erros no processo e ler o arquivo json.
            System.out.println("Erro ao ler o arquivo clients.json: " + e);
        }
        
    };
    //Metodo suado para listar os clientes armazenados no Array de clientes.
    public static void listarClientes(){
        //for percorrendo o array de clientes e printando.
        for(Cliente u : listaClientes){
            System.out.print(u);
        }
    }
    
    //Metodo usado para armazenar um cliente dentro do array de clientes.
    public static void adicionarCliente(Cliente cliente){
        listaClientes.add(cliente);
    }
    
    //Metodo usado para armazenar o array de clientes dentro do jason.
    public static void salvarClientes(){
        //Cria um objeto Gson usado na convertação de java para json e vice-versa, com 'PrettyPrintin' deixa mais legivel com uma boa formatação.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        //processo abrir o arquivo json para escrita.
        try(FileWriter writer = new FileWriter("data/clientes.json")){
        //converter a listaClientes em json.
        gson.toJson(listaClientes, writer);
        } catch(IOException e){
            //captura de erros no processo de abertura do arquivo para escrita.
            System.out.println("Erro ao escrever o arquivo json: " + e);
        }
    }
    
    //Metodo responsável por apagar um cliente.
    public static void removerClientePorCpf(String cpf){
        boolean removido = listaClientes.removeIf(c -> c.getCpf().equals(cpf));
        
        if(removido){
            System.out.println("Cliente removido com sucesso!");
            salvarClientes();
        }else{
            System.out.println("ERRO - Cliente não encontrado");
        }
        
        
    }
    
    //Metodo responsável por verificar a existencia de um cliente por cpf.
    public static boolean verificarClientePorCpf(String cpf){
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                return true;
            }
        }
        System.out.println("Esse cpf não pertence a nenhum cliente.");
        return false;
    }
    
    //Metodo responsável por editar o nome de um cliente que esta dentro do Array.
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
    
    //Metodo responsável por editar o cpf de um cliente que esta dentro do Array.
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
    
    //Metodo responsável por editar o Endereço de um cliente que esta dentro do Array.
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
    
    //Metodo responsável por editar o Endereço de um cliente que esta dentro do Array.
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
    
    //Metodo responsável por retornar um cliente, como busca o seu cpf.
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
