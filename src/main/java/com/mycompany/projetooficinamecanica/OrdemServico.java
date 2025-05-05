package com.mycompany.projetooficinamecanica;

import java.util.ArrayList;//lista que pode adicionar ou remover elementos sem ter que predefinir o tamanho da lista. 
import java.util.List;//Para trabalhar com listas.
import java.io.FileReader; // Para ler arquivos.
import java.io.FileWriter; // Para escrever arquivos
import java.io.IOException; // Para lidar com exceções de entrada e saída.
import com.google.gson.Gson; // Biblioteca Gson para manipular JSON.
import com.google.gson.GsonBuilder; //Usado para configurar a instancia do Gason.
import com.google.gson.reflect.TypeToken; // Para converter JSON em List<OrdensServico>.

/**
 *
 * @author Lucas
 */
public class OrdemServico {
    private int id;
    private Cliente cliente;
    private Mecanico mecanico;
    private String descricaoProblema;
    private String solucao;
    private String status;
    private String data;
    private Veiculo veiculo;
    
    static List<OrdemServico> ListaOrdensServico = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    OrdemServico(){
    
    }
    
    OrdemServico(Cliente cliente, Mecanico mecanico, String descricaoProblema, String solucao, String status, String data, Veiculo veiculo){
        this.id = GerenciamentoIDs.proximoID("OrdemServico");
        this.cliente = cliente;
        this.mecanico = mecanico;
        this.descricaoProblema = descricaoProblema;
        this.solucao = solucao;
        this.status = status;
        this.data = data;
        this.veiculo = veiculo;
    }
    
    @Override
    public String toString(){
        return "\nID: " + getId() + "\nCliente: " + getCliente() + "\nMecânico: " + getMecanico() + "\nDescrição do problema: " + getDescricaoProblema() + "\nSolução: " + getSolucao() + "\nStatus: " + getStatus()
                + "\nData: " + getData() + "Veículo: " + getVeiculo();
    }
    
    public static void carregarOrdensServico(){
        Gson gson = new Gson();
        try(FileReader reader = new FileReader("dados/OrdensServico.json")){
            ListaOrdensServico = gson.fromJson(reader, new TypeToken<List<OrdemServico>>(){}.getType());
        }catch(IOException e){
            System.out.println("Erro ao carregar o arquivo jason das ordens de serviço: " + e);
        }
    }
    
    /**Método para salvar a lista de ordens de serviço no arquivo JSON
     * 
     */
    public static void salvarOrdensServico() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("dados/OrdensServico.json")) {
            gson.toJson(ListaOrdensServico, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo JSON: " + e);
        }
    }
    
    public static void criarOrdemDeServico(OrdemServico ordemServico) {
        ListaOrdensServico.add(ordemServico);
        salvarOrdensServico();  
    }
    
    public static void listarOrdensServico(){
        for(OrdemServico o : ListaOrdensServico){
            System.out.println(o);
        }
    }
    
    public static void atualizarStatusOrdemPorId(int id, String novoStatus){
        for(OrdemServico o : ListaOrdensServico){
            if(o.getId() == id){
                o.setStatus(novoStatus);
                System.out.println("Status da ordem de seriço atualizado para: " + novoStatus);
                salvarOrdensServico();
                return;
            }
        }
        System.out.println("Ordem de serviço com ID " + id + "não encontrada");
    }
    
    public static void listarOrdensServicosPorCpf(String cpf){
        for(OrdemServico o : ListaOrdensServico){
            if(o.getMecanico().getCpf().equals(cpf)){
                System.out.println(o);
            }
        }
    }
    
    
    public static boolean verificarOrdemServico(int ID, String cpf){
      for(OrdemServico o : ListaOrdensServico){
            if(o.getId()==ID){
                if(o.getMecanico().getCpf().equals(cpf)){
                    return true;
                }else{
                   System.out.println("Ordem de serviço não pertence a você.");
                   return false;
                }
                
            }
        }
      System.out.println("Ordem de serviço não existe.");
      return false;
    }
    
}
