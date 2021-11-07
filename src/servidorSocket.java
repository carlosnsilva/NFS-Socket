import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class servidorSocket {

    public static void main(String[] args) throws IOException {
        System.out.println("------Iniciando Servidor----");
        ServerSocket socket = new ServerSocket(6000);
        System.out.println("------Servidor Iniciado----");

        executar(socket);

    }

    public static void executar(ServerSocket socket) throws IOException {
        boolean funcionando = true;

        while (funcionando){
            Socket cliente = socket.accept();
            String clientePath = cliente.getInetAddress().getHostAddress();

            System.out.println(clientePath+" conectado com sucesso.");

            DataOutputStream outputStream = new DataOutputStream(cliente.getOutputStream());
            DataInputStream inputStream = new DataInputStream(cliente.getInputStream());

            if (inputStream.readUTF().equals("sair")){
                cliente.close();
                funcionando = false;
            }else{
                String [] comando = inputStream.readUTF().split(" ");

                if(comando[0].equals("readdir")){
                    File arquivo = new File(comando[1]);
                    String mensagem = "";

                    if(arquivo.exists()){
                        //Lista todos os arquivos que estão no diretório
                        File[] arquivos = arquivo.listFiles();

                        for(File arq : arquivos){
                            mensagem = mensagem + arq.getName() + "  ";
                        }

                    }else{
                        mensagem = "Arquivo ou diretorio inexistente";
                    }
                    outputStream.writeUTF(mensagem);
                }

                if(comando[0].equals("rename")){
                    File nomeArquivoOriginal = new File(comando[1]);
                    File nomeNovoArquivo = new File(comando[2]);

                    if(!nomeArquivoOriginal.exists()){
                        outputStream.writeUTF("Arquivo ou diretorio inexistente");
                    }else{
                        nomeArquivoOriginal.renameTo(nomeNovoArquivo);
                        outputStream.writeUTF("Sucesso.");
                    }
                }

                if(comando[0].equals("create")){
                    File arquivo = new File(comando[1]);

                    //verificar se já existe esse arquivo
                    if(arquivo.exists()){
                        outputStream.writeUTF("Arquivo ja existente");
                    }else{
                        arquivo.createNewFile();
                        outputStream.writeUTF("Criado com sucesso.");
                    }
                }

                if(comando[0].equals("remove")){
                    File arquivo = new File(comando[1]);

                    //verificando se o arquivo já foi removido
                    if(!arquivo.exists()){
                        outputStream.writeUTF("Arquivo inexistente");
                    }else{
                        arquivo.delete();
                        outputStream.writeUTF("Removido com sucesso.");
                    }

                }

            }

        }
    }
}
