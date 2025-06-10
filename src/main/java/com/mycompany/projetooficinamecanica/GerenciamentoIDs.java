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

    /** Carrega o mapa de contadores de ID do arquivo JSON.
     * Utiliza o GerenciadorJson para garantir consistência.
     */
    public static void carregarContadorIDs() {
        Gson gson = GerenciadorJson.getGson();
        try (FileReader reader = new FileReader("dados/ContadorID.json")) {
            contadores = gson.fromJson(reader, new TypeToken<Map<String, Integer>>() {}.getType());
            if (contadores == null) {
                contadores = new java.util.HashMap<>();
            }
        } catch (IOException e) {
            System.out.println("Arquivo de IDs não encontrado. Criando novo mapa de IDs.");
            contadores = new java.util.HashMap<>();
        }
    }

    /** Salva o mapa de contadores de ID no arquivo JSON.
     * Utiliza o GerenciadorJson para garantir uma serialização consistente.
     */
    public static void salvarContadorIDs() {
        Gson gson = GerenciadorJson.getGson();
        try (FileWriter writer = new FileWriter("dados/ContadorID.json")) {
            gson.toJson(contadores, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo JSON de IDs: " + e.getMessage());
        }
    }


    /** Retorna o ID atual dependendo da classe
     * 
     * @param tipo
     * @return id atual de acordo com a classe
     */
    public static int proximoID(String tipo) {
        if (!contadores.containsKey(tipo)) {
            contadores.put(tipo, 1); /** Se não houver o tipo, inicializa o contador com 1
             * 
             */
        }
        int idAtual = contadores.get(tipo);
        contadores.put(tipo, idAtual + 1);
        salvarContadorIDs();
        return idAtual;
    }
}
