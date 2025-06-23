/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lucas
 */


public class JsonUtil {
    private static final Gson gson = createGsonInstance();
    
    public static Gson createGsonInstance(){
        RuntimeTypeAdapterFactory<Funcionario> funcionarioFactory = RuntimeTypeAdapterFactory
                .of(Funcionario.class, "type")
                .registerSubtype(Gerente.class, "gerente")
                .registerSubtype(Mecanico.class, "mecanico")
                .registerSubtype(Caixa.class, "caixa");
        
        return new GsonBuilder()
            .registerTypeAdapterFactory(funcionarioFactory)
            .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter out, LocalDateTime value) throws IOException {
                    out.value(value.toString());
                }

                @Override
                public LocalDateTime read(JsonReader in) throws IOException {
                    return LocalDateTime.parse(in.nextString());
                }
            })
            .setPrettyPrinting()
            .create();
    }
    
    public static <T> void salvar(String caminho, T objeto)throws IOException{
        try(FileWriter writer = new FileWriter(caminho)){
            gson.toJson(objeto, writer);
        } 
        catch(Exception e){
            System.out.println("Erro ao salvar o arquivo Json: " + e);
        }
    }
    
    public static <T> T carregar(String caminho, Type tipo)throws IOException{
        try(FileReader reader = new FileReader(caminho)){
            return gson.fromJson(reader, tipo);
        }
    }
    
    public static <T> List<T> carregarLista(String caminho, Type tipo) throws IOException{
        try (FileReader reader = new FileReader(caminho)) {
            List<T> listaCarregada = gson.fromJson(reader, tipo);
            if (listaCarregada == null) {
                return new ArrayList<>();
            } else {
                return listaCarregada;
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + caminho + ". Criando nova lista.");
            return new ArrayList<>();
        }
    }
    
    public static void carregarJsons() throws IOException{
        SistemaLogin.setListaUsuarios(carregarLista("data/usuarios.json", new TypeToken<List<Funcionario>>(){}.getType()));
      
        Cliente.setListaClientes(carregarLista("data/clientes.json", new TypeToken<List<Cliente>>(){}.getType()));
        
        Estoque.setListaDeProdutos(carregarLista("data/produtos.json", new TypeToken<List<Produto>>(){}.getType()));
        
        GerenciamentoIDs.setContadores(carregar("data/ContadorID.json", new TypeToken<Map<String, Integer>>(){}.getType()));
        
        OrdemServico.setListaOrdensServico(carregarLista("data/OrdensServico.json", new TypeToken<List<OrdemServico>>(){}.getType()));
        
        Veiculo.setListaVeiculos(carregarLista("data/veiculos.json", new TypeToken<List<Veiculo>>(){}.getType()));
        
        Agenda.setCompromissos(carregarLista("data/Agenda.json", new TypeToken<List<Agendamento>>(){}.getType()));
    }
}
