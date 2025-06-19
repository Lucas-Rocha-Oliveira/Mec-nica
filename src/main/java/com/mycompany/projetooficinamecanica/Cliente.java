package com.mycompany.projetooficinamecanica;

import java.io.IOException;
import java.util.List;//Permite manipular objetos do tipo list
import java.util.ArrayList;//Array dinâmico


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
    
    private static List<Cliente> listaClientes = new ArrayList<>();
    
    
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

    public static List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public static void setListaClientes(List<Cliente> listaClientes) {
        Cliente.listaClientes = listaClientes;
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
    
    /**Metodo responsável por apagar um cliente.
     * 
     * @param cpf 
     */
    public static void removerClientePorCpf(String cpf) throws IOException{
        boolean removido = listaClientes.removeIf(c -> c.getCpf().equals(cpf));
        
        if(removido){
            System.out.println("Cliente removido com sucesso!");
            JsonUtil.salvar("data/clientes.json", listaClientes);
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
    public static void editarNomeCliente(String cpf, String nome) throws IOException{
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                u.setNome(nome);
                break;
            }
        }
        JsonUtil.salvar("data/clientes.json", listaClientes);
        System.out.println("Nome alterado com sucesso!");
    }
    
    /**Metodo responsável por editar o cpf de um cliente que esta dentro do Array.
     * 
     * @param cpf
     * @param novocpf 
     */
    public static void editarCpfCliente(String cpf, String novocpf) throws IOException{
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                u.setCpf(novocpf);
                break;
            }
        }
        JsonUtil.salvar("data/clientes.json", listaClientes);
        System.out.println("Cpf alterado com sucesso!");
    }
    
    /**Metodo responsável por editar o Endereço de um cliente que esta dentro do Array.
     * 
     * @param cpf
     * @param endereco 
     */
    public static void editarEnderecoCliente(String cpf, String endereco) throws IOException{
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                u.setEndereco(endereco);
                break;
            }
        }
        JsonUtil.salvar("data/clientes.json", listaClientes);
        System.out.println("Endereço alterado com sucesso!");
    }
    
    /**Metodo responsável por editar o Endereço de um cliente que esta dentro do Array.
     * 
     * @param cpf
     * @param telefone 
     */
    public static void editarTelefoneCliente(String cpf, String telefone) throws IOException{
        for(Cliente u : listaClientes){
            if(u.getCpf().equals(cpf)){
                u.setTelefone(telefone);
                break;
            }
        }
        JsonUtil.salvar("data/clientes.json", listaClientes);
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
