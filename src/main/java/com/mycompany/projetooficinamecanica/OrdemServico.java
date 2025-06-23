package com.mycompany.projetooficinamecanica;

import java.util.ArrayList; 
import java.util.List;
import java.io.IOException;

/**
 * Representa uma Ordem de Serviço na oficina. Utiliza o padrão Builder
 * para uma construção flexível e passo a passo.
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
    // As listas são inicializadas aqui para garantir que nunca sejam nulas.
    private List<Servico> servicosRealizados = new ArrayList<>();
    private List<Produto> produtosUtilizados = new ArrayList<>();
    
    private static List<OrdemServico> ListaOrdensServico = new ArrayList<>();

    // Getters e Setters para a lista estática, se necessário para o JsonUtil
    public static List<OrdemServico> getListaOrdensServico() {
        return ListaOrdensServico;
    }

    public static void setListaOrdensServico(List<OrdemServico> lista) {
        if (lista != null) {
            OrdemServico.ListaOrdensServico = lista;
        }
    }
    
    /**
     * Construtor privado. A única forma de criar uma OrdemServico é através do Builder.
     * Este construtor copia os dados do builder para o objeto final.
     */
    private OrdemServico(OrdemServicoBuilder builder){
        this.id = builder.id;
        this.cliente = builder.cliente;
        this.mecanico = builder.mecanico;
        this.descricaoProblema = builder.descricaoProblema;
        this.solucao = builder.solucao;
        this.status = builder.status;
        this.dataEHora = builder.dataEHora;
        this.veiculo = builder.veiculo;
        this.servicosRealizados = builder.servicosRealizados; 
        this.produtosUtilizados = builder.produtosUtilizados; 
    }
    
    /**
     * Classe interna estática que implementa o padrão Builder.
     */
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
        
        public OrdemServicoBuilder adicionaServico(Servico servico){
            this.servicosRealizados.add(servico);
            return this;
        }
        
        public OrdemServicoBuilder adicionaProduto(Produto produto){
            this.produtosUtilizados.add(produto);
            return this;
        }

        public OrdemServico build(){
            return new OrdemServico(this);
        }
    }
    
    // --- Getters e Setters para a OrdemServico ---
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Mecanico getMecanico() { return mecanico; }
    public String getDescricaoProblema() { return descricaoProblema; }
    public String getSolucao() { return solucao; }
    public void setSolucao(String solucao) { this.solucao = solucao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDataEHora() { return dataEHora; }
    public Veiculo getVeiculo() { return veiculo; }
    public List<Servico> getServicosRealizados() { return servicosRealizados; }
    public List<Produto> getProdutosUtilizados() { return produtosUtilizados; }
    
    @Override
    public String toString(){
        String mecanicoNome = (mecanico != null) ? mecanico.getNome() : "Não definido";
        return "\n--- ORDEM DE SERVIÇO #" + getId() + " ---\n" + 
               "Cliente: " + getCliente().getNome() + "\n" +
               "Veículo: " + getVeiculo().getPlaca() + "\n" +
               "Mecânico: " + mecanicoNome + "\n" + 
               "Descrição: " + getDescricaoProblema() + "\n" + 
               "Solução: " + getSolucao() + "\n" + 
               "Status: " + getStatus() + "\n" +
               "Data: " + getDataEHora() + "\n";
    }
    
    // --- Métodos de Gerenciamento Estático ---
    
    public static void criarOrdemDeServico(OrdemServico ordemServico) throws IOException {
        ListaOrdensServico.add(ordemServico);
        JsonUtil.salvar("data/OrdensServico.json", ListaOrdensServico);  
    }
    
    public static void listarOrdensServico(){
        for(OrdemServico o : ListaOrdensServico){
            System.out.println(o);
        }
    }
    
    public static void atualizarStatusOrdemPorId(int id, String novoStatus) throws IOException{
        for(OrdemServico o : ListaOrdensServico){
            if(o.getId() == id){
                o.setStatus(novoStatus);
                System.out.println("Status da ordem de serviço atualizado para: " + novoStatus);
                JsonUtil.salvar("data/OrdensServico.json", ListaOrdensServico);
                return;
            }
        }
        System.out.println("Ordem de serviço com ID " + id + " não encontrada");
    }
    
    public static void listarOrdensServicosPorCpf(String cpf){
        for(OrdemServico o : ListaOrdensServico){
            if(o.getMecanico() != null && o.getMecanico().getCpf().equals(cpf)){
                System.out.println(o);
            }
        }
    }
    
    public static boolean verificarOrdemServico(int ID, String cpf){
        for(OrdemServico o : ListaOrdensServico){
            if(o.getId() == ID){
                if(o.getMecanico() != null && o.getMecanico().getCpf().equals(cpf)){
                    return true;
                } else {
                   System.out.println("Ordem de serviço não pertence a você.");
                   return false;
                }
            }
        }
        System.out.println("Ordem de serviço não existe.");
        return false;
    }
    
    // --- Métodos de Instância ---
    
    public void adicionarProduto(Produto produto){
        this.produtosUtilizados.add(produto);
    }
    
    public void adicionarServico(Servico servico){
        this.servicosRealizados.add(servico);
    }
}
