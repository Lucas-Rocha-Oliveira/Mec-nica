package com.mycompany.projetooficinamecanica;

import java.util.List;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;

/**
 * Classe que representa um Veículo da oficina.
 * Contém dados de identificação e está associada a um cliente.
 * Também possui mecanismos para contagem de instâncias criadas.
 * 
 * @author Lucas
 */
public class Veiculo {
    private String placa;
    private String modelo;
    private String marca;
    private String cor;
    private int ano;
    private Cliente proprietario;

    /**
     * Lista estática que armazena todos os veículos cadastrados.
     */
    private static List<Veiculo> listaVeiculos = new ArrayList<>();

    /**
     * Contador privado com acesso via método getter (boa prática).
     */
    private static int totalVeiculosPrivado = 0;

    /**
     * Contador protegido com acesso direto (menos recomendado).
     */
    protected static int totalVeiculosProtegido = 0;

    /**
     * Construtor do veículo com todos os atributos.
     * Também realiza incremento das contagens de instância.
     * 
     * @param placa Placa do veículo
     * @param modelo Modelo do veículo
     * @param marca Marca do veículo
     * @param cor Cor do veículo
     * @param ano Ano de fabricação
     * @param cpf CPF do cliente proprietário
     */
    Veiculo(String placa, String modelo, String marca, String cor, int ano, String cpf){
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.ano = ano;
        this.proprietario = Cliente.buscarClientePorCpf(cpf);

        incrementarTotalPrivado();
        totalVeiculosProtegido++;
    }

    // Getters e Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }

    public static List<Veiculo> getListaVeiculos() {
        return listaVeiculos;
    }

    public static void setListaVeiculos(List<Veiculo> listaVeiculos) {
        Veiculo.listaVeiculos = listaVeiculos;
    }

    /**
     * Método responsável por cadastrar um veículo na lista.
     * @return true se o cadastro for bem-sucedido
     */
    public static boolean cadastrarVeiculo(String placa, String modelo, String marca, String cor, int ano, String cpf){
        Cliente proprietario = Cliente.buscarClientePorCpf(cpf);
        if(proprietario == null){
            System.out.print("Cliente não cadastrado");
            return false;
        }

        for(Veiculo v : listaVeiculos){
            if(v.getPlaca().equalsIgnoreCase(placa)){ 
                System.out.println("Veículo com a placa " + placa + " já cadastrado.");
                return false;
            }
        }

        listaVeiculos.add(new Veiculo(placa, modelo, marca, cor, ano, cpf));
        System.out.print("Veículo cadastrado com sucesso!");
        return true;
    }

    /**
     * Verifica se um veículo com a placa informada está registrado.
     */
    public static boolean buscarVeiculoPorPlaca(String placa){
        for(Veiculo v : listaVeiculos){
            if(v.getPlaca().equalsIgnoreCase(placa)){
                return true;
            }
        }
        System.out.println("Placa não registrada.");
        return false;
    }

    /**
     * Remove veículo por placa e salva no arquivo.
     */
    public static void removerVeiculoPorPlaca(String placa) throws IOException {
        Veiculo.setListaVeiculos(JsonUtil.carregarLista("data/veiculos.json", new TypeToken<List<Veiculo>>(){}.getType()));
        boolean removido = listaVeiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));

        if (removido) {
            System.out.println("Veículo removido com sucesso.");
            JsonUtil.salvar("data/veiculos.json", listaVeiculos);
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    /**
     * Lista todos os veículos cadastrados.
     */
    public static void listarVeiculos(){
        for(Veiculo v : listaVeiculos){
            System.out.println(v);
        }
    }

    /**
     * Retorna um veículo a partir da placa.
     */
    public static Veiculo retornaVeiculoPorPlaca(String placa){
        for(Veiculo v: listaVeiculos){
            if(v.getPlaca().equals(placa)){
                return v;
            }
        }
        return null;
    }

    /**
     * Incrementa contador privado (boa prática de encapsulamento).
     */
    private static void incrementarTotalPrivado() {
        totalVeiculosPrivado++;
    }

    /**
     * Retorna o total de veículos com encapsulamento.
     */
    public static int getTotalVeiculosPrivado() {
        return totalVeiculosPrivado;
    }

    /**
     * Retorna o total de veículos com acesso direto (protected).
     */
    public static int getTotalVeiculosProtegido() {
        return totalVeiculosProtegido;
    }

    /**
     * Exibe os dois contadores de veículos.
     */
    public static void mostrarContadores() {
        System.out.println("Total (privado): " + getTotalVeiculosPrivado());
        System.out.println("Total (protegido): " + totalVeiculosProtegido);
    }

    @Override
    public String toString(){
        return "Placa: " + getPlaca() + "\nModelo: " + getModelo() + "\nMarca: " + getMarca() +
                "\nCor: " + getCor() + "\nAno: " + getAno() + "\nProprietário: " + getProprietario();
    }
}
