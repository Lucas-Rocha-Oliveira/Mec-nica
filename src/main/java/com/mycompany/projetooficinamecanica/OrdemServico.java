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
    private String dataEHora;
    private Veiculo veiculo;
    private List<Servico> servicosRealizados = new ArrayList<>();
    private List<Produto> produtosUtilizados = new ArrayList<>();
    
    static List<OrdemServico> ListaOrdensServico = new ArrayList<>();
    
    private OrdemServico(OrdemServicoBuilder builder){
        this.id = builder.id;
        this.cliente = builder.cliente;
        this.mecanico = builder.mecanico;
        this.descricaoProblema = builder.descricaoProblema;
        this.solucao = builder.solucao;
        this.status = builder.status;
        this.dataEHora = builder.dataEHora;
        this.veiculo = builder.veiculo;
    }
    
    public static class OrdemServicoBuilder{
        private int id;
        private Cliente cliente;
        private Mecanico mecanico;
        private String descricaoProblema;
        private String solucao;
        private String status;
        private String dataEHora;
        private Veiculo veiculo;
        private List<Servico> servicosRealizados = new ArrayList<>();
        private List<Produto> produtosUtilizados = new ArrayList<>();
        
        public OrdemServicoBuilder id(int id){
            this.id = id;
            return this;
        }
        
        public OrdemServicoBuilder cliente(Cliente cliente){
            this.cliente = cliente;
            return this;
        }
        
        public OrdemServicoBuilder mecanico(Mecanico mecanico){
            this.mecanico = mecanico;
            return this;
        }
        
        
        public OrdemServicoBuilder descricaoProblema(String descricaoProblema){
            this.descricaoProblema = descricaoProblema;
            return this;
        }
        
        public OrdemServicoBuilder solucao(String solucao){
            this.solucao = solucao;
            return this;
        }
        
        public OrdemServicoBuilder status(String status){
            this.status = status;
            return this;
        }
        
        public OrdemServicoBuilder dataEHora(String dataEHora){
            this.dataEHora = dataEHora;
            return this;
        }
        
        public OrdemServicoBuilder veiculo(Veiculo veiculo){
            this.veiculo = veiculo;
            return this;
        }
        
        public OrdemServico build(){
            return new OrdemServico(this);
        }
        
        public OrdemServicoBuilder adicionaServico(Servico servico){
            this.servicosRealizados.add(servico);
            return this;
        }
        
        public OrdemServicoBuilder adicionaProduto(Produto produto){
            this.produtosUtilizados.add(produto);
            return this;
        }
        
    }
    
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
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

    public String getDataEHora() {
        return dataEHora;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public List<Servico> getServicosRealizados() {
        return servicosRealizados;
    }

    public List<Produto> getProdutosUtilizados() {
        return produtosUtilizados;
    }
    
    
    
    @Override
    public String toString(){
        return "\nID: " + getId() + "\nCliente: " + getCliente() + "\nMecânico: " + getMecanico() + "\nDescrição do problema: " + getDescricaoProblema() + "\nSolução: " + getSolucao() + "\nStatus: " + getStatus()
                + "\nData e hora: " + getDataEHora() + "Veículo: " + getVeiculo();
    }
    
    public static void carregarOrdensServico(){
        Gson gson = GerenciadorJson.getGson(); // Usa o gerenciador
        try(FileReader reader = new FileReader("data/OrdensServico.json")){
            ListaOrdensServico = gson.fromJson(reader, new TypeToken<List<OrdemServico>>(){}.getType());
            if(ListaOrdensServico == null) { // Evita erro se o arquivo estiver vazio
                ListaOrdensServico = new ArrayList<>();
            }
        }catch(IOException e){
            System.out.println("Arquivo de Ordens de Serviço não encontrado. Criando nova lista.");
            ListaOrdensServico = new ArrayList<>();
        }
    }
    
    /**Método para salvar a lista de ordens de serviço no arquivo JSON
     * 
     */
    public static void salvarOrdensServico() {
        Gson gson = GerenciadorJson.getGson(); // Usa o gerenciador
        try (FileWriter writer = new FileWriter("data/OrdensServico.json")) {
            gson.toJson(ListaOrdensServico, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo JSON: " + e.getMessage());
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
    
    public void adicionarProduto(Produto produto){
        this.produtosUtilizados.add(produto);
    }
    
    public void adicionarServico(Servico servico){
        this.servicosRealizados.add(servico);
    }
    
}
