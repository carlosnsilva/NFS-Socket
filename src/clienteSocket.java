import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class clienteSocket {



    public static void main(String [] args) throws IOException {
        menuIniciar();

        Scanner input = new Scanner(System.in);
        String inputUser = input.nextLine();

        executar(inputUser);
    }


    public static void menuIniciar(){
        System.out.println("------------Bem-vindo -----------");
        System.out.println("------------Escolha um comando abaixo -----------");
        System.out.println("-- readdir: devolve o conteúdo de um diretório --");
        System.out.println("-- rename: renomeia um arquivo --");
        System.out.println("-- create: cria um arquivo --");
        System.out.println("-- remove: remove um arquivo --");
        System.out.println("-- sair: sair do servidor --");
        System.out.println("OBS: SEMPRE PASSAR O ENDERECO ABSOLUTO DO ARQUIVO OU DIRETORIO.");
    }

    public static void executar(String input) throws IOException {
        Socket socket = new Socket("localhost", 6000);

        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        boolean funcionando = true;

        while (funcionando){
            if(input.equals("sair")){
                socket.close();
                funcionando = false;
                // mudança para fechar a conexão e parar a execução
            }else{
                outputStream.writeUTF(input);

                System.out.println(inputStream.readUTF());
            }
        }


    }


}
