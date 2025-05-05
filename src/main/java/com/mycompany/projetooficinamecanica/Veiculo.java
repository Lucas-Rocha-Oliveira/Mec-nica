package com.mycompany.projetooficinamecanica;

import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson; // Biblioteca Gson para manipular JSON.
import com.google.gson.GsonBuilder; //Usado para configurar a instancia do Gason.
import com.google.gson.reflect.TypeToken; // Para converter JSON em List<Veiculo>.
import java.io.FileReader; //Para ler arquivos
import java.io.FileWriter; // para escrever arquivos.
import java.io.IOException; // Para lidar com exceções de entrada e saída.

/**
 *
 * @author Lucas
 */
public class Veiculo {
    String placa;
    String modelo;
    String marca;
    String cor;
    int ano;
    Cliente proprietario;

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
    
    static List<Veiculo> listaVeiculos = new ArrayList<>();
    
    Veiculo(){
        
    };
    
    Veiculo(String placa, String modelo, String marca, String cor, int ano, String cpf){
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.ano = ano;
        this.proprietario = Cliente.buscarClientePorCpf(cpf);
    }
    
    @Override
    public String toString(){
        return "Placa: " + getPlaca() + "\nModelo: " + getModelo() + "\nMarca: " + getMarca() + "\nCor: " + getCor() + 
                "\nAno: " + getAno() + "\nproprietário: " + getProprietario();
    }
    
    /**responsável por carregar os veiculos do jason para o array.
     * 
     */
    public static void carregarVeiculos(){ 
        Gson gson = new Gson();
        try(FileReader reader = new FileReader("dados/veiculos.json")){
            listaVeiculos = gson.fromJson(reader, new TypeToken<List<Veiculo>>(){}.getType());
        }catch(IOException e){
            System.out.print("Erro ao carregar o arquivo json de veículos: " + e);
        }
    }
    
    /**
     * Metodo responsável por salvar os veiculos do Array no arquivo json.
     */
    public static void salvarVeiculos(){
      Gson gson = new Gson();
        try(FileWriter writer = new FileWriter("data/veiculos.json")){
            gson.toJson(listaVeiculos, writer);
        }catch(IOException e){
            System.out.print("Erro ao escrever o arquivo json de veículos: " + e);
        }  
    }
    
    /**
     * Metodo responsável por adicionar um veiculo no Array de veiculos
     */
    public static boolean cadastrarVeiculo(String placa, String modelo, String marca, String cor, int ano, String cpf){
        Cliente proprietario = Cliente.buscarClientePorCpf(cpf);
        if(proprietario == null){
            System.out.print("Cliente não cadastrado");
            return false;
        }
        
        for(Veiculo v : listaVeiculos){
            if(v.getPlaca().equalsIgnoreCase(placa)){ 
                System.out.println("Veiculo com a placa " + placa +" ja cadastrado.");
                return false;
            }
        }
       
        listaVeiculos.add(new Veiculo(placa, modelo, marca, cor, ano, cpf));
        System.out.print("Veículo cadastrado com sucesso!");
        return true;
    }
    
    /**Metodo para verificar se um veiculo esta registrado no array de veiculos
     * 
     * @param placa
     * @return 
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
     * Metodo para remover um veiculo do array pela placa.
     */
            
            
    public static void removerVeiculoPorPlaca(String placa) {
        carregarVeiculos(); // Atualiza a lista de veículos

        boolean removido = listaVeiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));

        if (removido) {
            System.out.println("Veículo removido com sucesso.");
            salvarVeiculos(); // Salva após remover
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }
    
    /** metodo responsável por listas véiculo
     * 
     */
    
    
    public static void listarVeiculos(){
        for(Veiculo v : listaVeiculos){
            System.out.println(v);
        }
    }
    
}
