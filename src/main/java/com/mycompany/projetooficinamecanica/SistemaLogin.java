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
   /**Lista que armazena os usuários.
    * 
    */
   //static faz o array pertencer a classe e não a um objeto.
   static List<Funcionario> listaUsuarios = new ArrayList<>();
   
   public static List<Funcionario> getListaUsuarios(){
        return listaUsuarios;
    }
   
    /**Metodo usado para adicionar os usuarios dentro do array responsável.
     * 
     * @param funcionario 
     */
    public static void adicionarUsuario(Funcionario funcionario){
        listaUsuarios.add(funcionario);
    }
    
    /**Metodo usado para listar os usuários armazenados no array.
     * 
     */
    public static void listarUsuarios(){
        /**for para percorrer o array de usuários.
         * 
         */
        for(Funcionario u : listaUsuarios){
            System.out.println(u);
        }
    }
    
    /**
     * Carrega a lista de todos os funcionários (usuários do sistema) a partir
     * de um arquivo JSON ("data/usuarios.json").
     * O método delega a desserialização para a instância do Gson gerenciada
     * pela classe {@link GerenciadorJson}, que lida com tipos complexos e herança.
     * Em caso de falha na leitura ou se o arquivo estiver vazio, inicializa
     * uma nova lista de funcionários para evitar erros no sistema.
     */
    public static void carregarUsuarios(){
        Gson gson = GerenciadorJson.getGson(); // Usa o gerenciador centralizado
        try(FileReader reader = new FileReader("data/usuarios.json")){
            listaUsuarios = gson.fromJson(reader, new TypeToken<List<Funcionario>>(){}.getType());
            if (listaUsuarios == null) {
                listaUsuarios = new ArrayList<>();
            }
        } catch(IOException e){
            System.out.println("Arquivo de usuários não encontrado. Criando nova lista.");
            listaUsuarios = new ArrayList<>();
        }
    }
    
    /**
     * Salva a lista de todos os funcionários (usuários do sistema) no arquivo
     * JSON ("data/usuarios.json").
     * Usa a instância configurada do Gson vinda do {@link GerenciadorJson} para
     * garantir que a serialização seja feita corretamente e com formatação legível.
     */
    public static void salvarUsuarios(){
        Gson gson = GerenciadorJson.getGson(); // Usa o gerenciador centralizado
        try (FileWriter write = new FileWriter("data/usuarios.json")){
            gson.toJson(listaUsuarios, write);
        } catch(IOException e){
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }
    
    /**Metodo para remover um usuário do Array.
     * 
     * @param cpf 
     */
    public static void removerUsuarioPorCpf(String cpf){
        boolean removido = listaUsuarios.removeIf(c -> c.getCpf().equals(cpf));
        salvarUsuarios();
        System.out.println("Usuário removido com sucesso.");
    }
    
    /**Metodo para verificar a existencia de um usuário, usando como parametro o cpf.
     * 
     * @param cpf
     * @return 
     */
    public static boolean verificarUsuarioPeloCpf(String cpf){
        for(Funcionario u : listaUsuarios){
            if(u.getCpf().equals(cpf)){
                return true;
            }
        }
        System.out.println("Cpf não pertence a nenhum usuário");
        return false;
    }
    
    /**Metodo para verificar as credenciais no momento de login
     * 
     * @param login
     * @param senha
     * @return 
     */
    public static Funcionario verificarLogin(String login, String senha){
        /**for percorrendo o array de usuários verificando as credencias fornecidas.
         * 
         */
        for(Funcionario u : listaUsuarios){
            if(u.getLogin().equals(login) && u.getSenha().equals(senha)){
                System.out.println("Login bem Sucedido!");
                return u;//retorna "u" que armazena qual usuário esta logado no sistema
            }
        }
        System.out.println("Crencias inválidas, tente novamente");
        return null;
    }
    
    /**Metodo para alterar o nome de um usuário do Array pelo cpf.
     * 
     * @param cpf
     * @param nome 
     */
    public static void alterarNomeUsuarioPorCpf(String cpf, String nome){
        for(Funcionario u : listaUsuarios){
            if(u.getCpf().equals(cpf)){
                u.setNome(nome);
                System.out.println("Nome alterado com sucesso!");
                salvarUsuarios();
                break;
            }
        }
    }
    
    /**Metodo para alterar o cpf de um usuário do Array pelo cpf.
     * 
     * @param cpf
     * @param novocpf 
     */
    public static void alterarCpfUsuarioPorCpf(String cpf, String novocpf){
        for(Funcionario u : listaUsuarios){
            if(u.getCpf().equals(cpf)){
                u.setCpf(novocpf);
                System.out.println("Cpf alterado com sucesso!");
                salvarUsuarios();
                break;
            }
        }
    }
    
    /**Metodo para alterar o login de um usuário do Array pelo cpf.
     * 
     * @param cpf
     * @param login 
     */
    public static void alterarLoginUsuarioPorCpf(String cpf, String login){
        for(Funcionario u : listaUsuarios){
            if(u.getCpf().equals(cpf)){
                u.setLogin(login);
                System.out.println("Login alterado com sucesso!");
                salvarUsuarios();
                break;
            }
        }
    }
    
    /**Metodo para alterar o senha de um usuário do Array pelo cpf.
     * 
     * @param cpf
     * @param senha 
     */
    public static void alterarSenhaUsuarioPorCpf(String cpf, String senha){
        for(Funcionario u : listaUsuarios){
            if(u.getCpf().equals(cpf)){
                u.setSenha(senha);
                System.out.println("Senha alterado com sucesso!");
                salvarUsuarios();
                break;
            }
        }
    }
    
    /**Metodo para alterar o senha de um usuário do Array pelo cpf.
     * 
     * @param cpf
     * @param cargo 
     */
    public static void alterarCargoUsuarioPorCpf(String cpf, String cargo){
        for(Funcionario u : listaUsuarios){
            if(u.getCpf().equals(cpf)){
                u.setCargo(cargo);
                System.out.println("Cargo alterado com sucesso!");
                salvarUsuarios();
                break;
            }
        }
    }
    
    public static Funcionario buscarFuncionarioPorCpf(String cpf) {
    for (Funcionario u : listaUsuarios) {
        if (u.getCpf().equals(cpf)) {
            return u;
        }
    }
    return null;
}
    
    
}
