package com.mycompany.projetooficinamecanica;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class OrdemServicoTypeAdapter extends TypeAdapter<OrdemServico>{
    
    private final Gson gson;
    
    public OrdemServicoTypeAdapter(Gson gson){
        this.gson = gson;
    }
    
    @Override
    public void write(JsonWriter out, OrdemServico ordem) throws IOException {
        out.beginObject();
        
        out.name("id").value(ordem.getId());
        out.name("descricaoProblema").value(ordem.getDescricaoProblema());
        out.name("solucao").value(ordem.getSolucao());
        out.name("status").value(ordem.getStatus());
        out.name("dataEHora").value(ordem.getDataEHora());
        out.name("cliente");
        
        gson.toJson(ordem.getCliente(), Cliente.class, out);
        out.name("mecanico");
        gson.toJson(ordem.getMecanico(), Mecanico.class, out);
        out.name("veiculo");
        gson.toJson(ordem.getVeiculo(), Veiculo.class, out);
        
        out.name("servicosRealizados");
        gson.toJson(ordem.getServicosRealizados(), new TypeToken<List<Servico>>(){}.getType(), out);
        out.name("produtosUtilizados");
        gson.toJson(ordem.getProdutosUtilizados(), new TypeToken<List<Produto>>(){}.getType(), out);
        
        out.endObject();
    }
    
    @Override
    public OrdemServico read(JsonReader in) throws IOException {
        
        final OrdemServico.OrdemServicoBuilder builder = new OrdemServico.OrdemServicoBuilder();
        
        in.beginObject();
        
        while(in.hasNext()){
            String nomeDoCampo = in.nextName();
            switch (nomeDoCampo) {
                case "id":
                    builder.id(in.nextInt());
                    break;
                case "descricaoProblema":
                    builder.descricaoProblema(in.nextString());
                    break;
                case "solucao":
                    builder.solucao(in.nextString());
                    break;
                case "status":
                    builder.status(in.nextString());
                    break;
                case "dataEHora":
                    builder.dataEHora(in.nextString());
                    break;
                case "cliente":
                    builder.cliente(gson.fromJson(in, Cliente.class));
                    break;
                case "mecanico":
                    builder.mecanico(gson.fromJson(in, Mecanico.class));
                    break;
                case "veiculo": 
                    builder.veiculo(gson.fromJson(in, Veiculo.class));
                    break;
                case "servicosRealizados":
                    Type tipoDaListaDeServicos = new TypeToken<List<Servico>>(){}.getType();
                    List<Servico> servicosLidos = gson.fromJson(in, tipoDaListaDeServicos);
                    if (servicosLidos != null) {
                        servicosLidos.forEach(builder::adicionaServico);
                    }
                    break;
                case "produtosUtilizados":
                    Type tipoDaListaDeProduto = new TypeToken<List<Produto>>(){}.getType();
                    List<Produto> produtosLidos = gson.fromJson(in, tipoDaListaDeProduto);
                    if (produtosLidos != null) {
                        produtosLidos.forEach(builder::adicionaProduto);
                    }
                    break;
                default:
                    in.skipValue();
                    break;
            }
            
        }
        
        in.endObject();
       
        return builder.build();
    }
}
