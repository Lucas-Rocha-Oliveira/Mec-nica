package com.mycompany.projetooficinamecanica;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Gerencia a criação de uma única instância configurada do Gson para todo o projeto.
 */
public final class GerenciadorJson {

    private static Gson gsonInstance;

    private GerenciadorJson() {
        // Construtor privado para impedir instanciação.
    }

    public static Gson getGson() {
        if (gsonInstance == null) {
            
            RuntimeTypeAdapterFactory<Funcionario> funcionarioFactory = RuntimeTypeAdapterFactory
                    .of(Funcionario.class, "type") // O campo no JSON para identificar o tipo será "type".
                    .registerSubtype(Gerente.class, "gerente")   // Se type="gerente", cria um objeto Gerente.
                    .registerSubtype(Mecanico.class, "mecanico") // Se type="mecanico", cria um objeto Mecanico.
                    .registerSubtype(Caixa.class, "caixa");       // Se type="caixa", cria um objeto Caixa.

            GsonBuilder builder = new GsonBuilder();

            // 2. Registra a fábrica de Funcionario para que o Gson a utilize.
            builder.registerTypeAdapterFactory(funcionarioFactory);

            // 3. Mantém o registro da fábrica da OrdemServico que já tínhamos.
            builder.registerTypeAdapterFactory(new TypeAdapterFactory() {
                @Override
                public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                    if (type.getRawType() != OrdemServico.class) {
                        return null; 
                    }
                    return (TypeAdapter<T>) new OrdemServicoTypeAdapter(gson);
                }
            });
            
            builder.setPrettyPrinting(); // Deixa o arquivo JSON formatado.
            gsonInstance = builder.create(); // Cria a instância final do Gson com todas as regras.
        }
        return gsonInstance;
    }
}
