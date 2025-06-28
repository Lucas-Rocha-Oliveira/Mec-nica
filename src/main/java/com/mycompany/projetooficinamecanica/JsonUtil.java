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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fornece métodos utilitários para serializar e deserializar objetos para/de JSON.
 * Esta classe utiliza a biblioteca Gson e inclui configurações personalizadas para
 * lidar com polimorfismo e tipos de data do Java 8+ (LocalDate e LocalDateTime),
 * garantindo compatibilidade com versões modernas do JDK.
 *
 * @author Lucas
 */
public class JsonUtil {

    /**
     * Instância estática e final do Gson, configurada para ser usada em toda a aplicação.
     * É inicializada de forma "lazy" pelo método createGsonInstance().
     */
    private static final Gson gson = createGsonInstance();

    /**
     * Configura e cria uma instância personalizada do Gson.
     * <p>
     * As customizações incluem:
     * <ul>
     * <li><b>RuntimeTypeAdapterFactory:</b> Para lidar com a herança da classe {@link Funcionario},
     * permitindo que subclasses (Gerente, Mecanico, Caixa) sejam corretamente salvas e carregadas.</li>
     * <li><b>TypeAdapter para LocalDateTime:</b> Define um formato padrão de texto para salvar e carregar
     * objetos {@link LocalDateTime}, evitando problemas de reflection com o sistema de módulos do Java.</li>
     * <li><b>TypeAdapter para LocalDate:</b> Define um formato padrão de texto para salvar e carregar
     * objetos {@link LocalDate}, resolvendo a exceção {@code InaccessibleObjectException} em JDKs modernos.</li>
     * <li><b>PrettyPrinting:</b> Formata o JSON de saída para ser legível por humanos.</li>
     * </ul>
     *
     * @return Uma instância do Gson configurada e pronta para uso.
     */
    public static Gson createGsonInstance() {
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
                // ADIÇÃO: Adaptador para LocalDate para garantir compatibilidade com JDK 9+
                .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                    @Override
                    public void write(JsonWriter out, LocalDate value) throws IOException {
                        out.value(value.toString());
                    }

                    @Override
                    public LocalDate read(JsonReader in) throws IOException {
                        return LocalDate.parse(in.nextString());
                    }
                })
                .setPrettyPrinting()
                .create();
    }

    /**
     * Serializa um objeto genérico para o formato JSON e o salva em um arquivo.
     *
     * @param <T>    O tipo do objeto a ser salvo.
     * @param caminho O caminho completo do arquivo onde o JSON será salvo (ex: "data/clientes.json").
     * @param objeto  O objeto a ser serializado.
     * @throws IOException se ocorrer um erro de I/O durante a escrita do arquivo.
     */
    public static <T> void salvar(String caminho, T objeto) throws IOException {
        try (FileWriter writer = new FileWriter(caminho)) {
            gson.toJson(objeto, writer);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o arquivo Json: " + e);
        }
    }

    /**
     * Deserializa um arquivo JSON para um objeto de um tipo específico.
     *
     * @param <T>     O tipo do objeto de destino.
     * @param caminho O caminho completo do arquivo JSON a ser lido.
     * @param tipo    O tipo do objeto para o qual o JSON será convertido (ex: {@code Map.class}).
     * @return O objeto deserializado, ou {@code null} se o arquivo estiver vazio ou ocorrer um erro.
     * @throws IOException se ocorrer um erro de I/O durante a leitura do arquivo.
     */
    public static <T> T carregar(String caminho, Type tipo) throws IOException {
        try (FileReader reader = new FileReader(caminho)) {
            return gson.fromJson(reader, tipo);
        }
    }

    /**
     * Deserializa um arquivo JSON para uma Lista de um tipo específico.
     * Se o arquivo não for encontrado, cria e retorna uma nova lista vazia.
     *
     * @param <T>     O tipo dos elementos na lista.
     * @param caminho O caminho completo do arquivo JSON a ser lido.
     * @param tipo    O tipo da lista para a qual o JSON será convertido (ex: {@code new TypeToken<List<Cliente>>(){}.getType()}).
     * @return Uma {@code List<T>} contendo os objetos do arquivo, ou uma lista vazia se o arquivo não existir ou estiver vazio.
     * @throws IOException se ocorrer um erro de I/O durante a leitura do arquivo.
     */
    public static <T> List<T> carregarLista(String caminho, Type tipo) throws IOException {
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

    /**
     * Carrega todos os arquivos de dados JSON necessários para a inicialização do sistema.
     * Este método é chamado no início da aplicação para popular as listas estáticas
     * das classes de gerenciamento (SistemaLogin, Cliente, Estoque, etc.).
     *
     * @throws IOException se ocorrer um erro de I/O durante a leitura de qualquer um dos arquivos.
     */
    public static void carregarJsons() throws IOException {
        SistemaLogin.setListaUsuarios(carregarLista("data/usuarios.json", new TypeToken<List<Funcionario>>() {}.getType()));
        Cliente.setListaClientes(carregarLista("data/clientes.json", new TypeToken<List<Cliente>>() {}.getType()));
        Estoque.setListaDeProdutos(carregarLista("data/produtos.json", new TypeToken<List<Produto>>() {}.getType()));
        GerenciamentoIDs.setContadores(carregar("data/ContadorID.json", new TypeToken<Map<String, Integer>>() {}.getType()));
        OrdemServico.setListaOrdensServico(carregarLista("data/OrdensServico.json", new TypeToken<List<OrdemServico>>() {}.getType()));
        Veiculo.setListaVeiculos(carregarLista("data/veiculos.json", new TypeToken<List<Veiculo>>() {}.getType()));
        Agenda.setCompromissos(carregarLista("data/Agenda.json", new TypeToken<List<Agendamento>>() {}.getType()));
        Relatorio.setListaDeRelatorios(carregarLista("data/Relatorios.json", new TypeToken<List<Relatorio>>() {}.getType()));
    }
}