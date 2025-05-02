package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */
import java.util.ArrayList;//lista que pode adicionar ou remover elementos sem ter que predefinir o tamanho da lista. 
import java.util.List;//Para trabalhar com listas.
import java.io.FileReader; // Para ler arquivos.
import java.io.FileWriter; // Para escrever arquivos
import java.io.IOException; // Para lidar com exceções de entrada e saída.
import com.google.gson.Gson; // Biblioteca Gson para manipular JSON.
import com.google.gson.GsonBuilder; //Usado para configurar a instancia do Gason.
import com.google.gson.reflect.TypeToken; // Para converter JSON em List<Funcionario>.

/**
 * Classe responsável pelo sistema de longin.
 * Gerencia o salvamento e exclusão dos usuários em um arquivo json.
 * Permite manipular dados dos usuários.
 **/
public class SistemaLogin {
   //Lista que armazena os usuários.
   //static faz o array pertencer a classe e não a um objeto.
   static List<Funcionario> listaUsuarios = new ArrayList<>();
   
    //Metodo usado para adicionar os usuarios dentro do array responsável.
    public static void adicionarUsuario(Funcionario funcionario){
        listaUsuarios.add(funcionario);
    }
    
    //Metodo usado para listar os usuários armazenados no array.
    public static void listarUsuarios(){
        //for para percorrer o array de usuários.
        for(Funcionario u : listaUsuarios){
            System.out.println(u);
        }
    }
    
    //Metodo para carregar os usuarios do arquivo json para o array.
    public static void carregarUsuarios(){
        //Cria um objeto do tipo Gson usado para converter dados entre JSON e objetos Java.
        Gson gson = new Gson();
        
        //Abre o arquivo para leitura
        try(FileReader reader = new FileReader("data/usuarios.json")){
            /**@param reader lê o conteudo do json
            *@return Lista de objeto Funcionario carregados do JSON.
            **/
            listaUsuarios = gson.fromJson(reader, new TypeToken<List<Funcionario>>(){}.getType());
        } catch(IOException e){
            //Captura erros de saída.
            System.out.print("Erro ao carregar os usuários: " + e);
        }}
    
    //Metodo para salvar o array de usuarios no arquivo json.
    public static void salvarUsuarios(){
        //Cria um objeto do tipo Gson usado para converter dados entre JSON e objetos Java.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();//Cria um objeto Gson com 'PrettyPrintin' deixa mais legivel com uma boa formatação.
        
        //"try" Abre o arquivo para escrita
        try (FileWriter write = new FileWriter("data/usuarios.json")){
        //Cria um FileWrite para escrever no arquivo usuarios.jason
            gson.toJson(listaUsuarios, write);//Conver a lista de usuarios para Json e escreve noa arquivo.
        } catch(IOException e){
            //Captura erros de saída.
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }
    
    //Metodo para verificar as credenciais no momento de login
    public static Funcionario verificarLogin(String login, String senha){
        //for percorrendo o array de usuários verificando as credencias fornecidas.
        for(Funcionario u : listaUsuarios){
            if(u.getLogin().equals(login) && u.getSenha().equals(senha)){
                System.out.println("Login bem Sucedido!");
                return u;//retorna "u" que armazena qual usuário esta logado no sistema
            }
        }
        System.out.println("Crencias inválidas, tente novamente");
        return null;
    }
}
