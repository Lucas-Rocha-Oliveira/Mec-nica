package com.mycompany.projetooficinamecanica;

import java.util.Map;
import com.google.gson.Gson; // Biblioteca Gson para manipular JSON.
import com.google.gson.GsonBuilder; // Usado para configurar a instância do Gson.
import com.google.gson.reflect.TypeToken; // Para converter JSON em List<Veiculo>.
import java.io.FileReader; // Para ler arquivos
import java.io.FileWriter; // Para escrever arquivos.
import java.io.IOException; // Para lidar com exceções de entrada e saída.

public class GerenciamentoIDs {
    private static Map<String, Integer> contadores;

    // Carrega o arquivo JSON com os IDs e joga dentro do map
    public static void carregarContadorIDs() {
        try (FileReader reader = new FileReader("dados/ContadorID.json")) {
            Gson gson = new Gson();
            contadores = gson.fromJson(reader, new TypeToken<Map<String, Integer>>() {}.getType());
            if (contadores == null) {
                contadores = new java.util.HashMap<>(); // Caso o JSON esteja vazio
            }
        } catch (IOException e) {
            System.out.println("Arquivo JSON não encontrado ou erro ao ler. Criando novo mapa de IDs.");
            contadores = new java.util.HashMap<>(); // Caso o arquivo não exista
        }
}

    // Salva o map dentro do arquivo JSON
    public static void salvarContadorIDs() {
        try (FileWriter writer = new FileWriter("dados/ContadorID.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(contadores, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo JSON do gerenciador de ID: " + e);
        }
    }

    // Retorna o ID atual dependendo da classe
    public static int proximoID(String tipo) {
        if (!contadores.containsKey(tipo)) {
            contadores.put(tipo, 1); // Se não houver o tipo, inicializa o contador com 1
        }
        int idAtual = contadores.get(tipo);
        contadores.put(tipo, idAtual + 1);
        salvarContadorIDs();
        return idAtual;
    }
}
