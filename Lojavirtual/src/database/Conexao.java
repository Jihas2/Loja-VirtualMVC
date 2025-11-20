package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {  

    private final String servidor;
    private final String banco;
    private final String usuario;
    private final String senha;
    private Connection conexao;       

    public Conexao() {
        this.servidor = "ww3.infc.srv.br";        // servidor remoto
        this.banco    = "grupo2_Loja_De_Roupas";  // SEU banco da loja
        this.usuario  = "grupo2";                 // seu usuário
        this.senha    = "WBb)qr0VtYNwf@/T";        // sua senha
    }

    public Connection conectar(){
        try {
            String url = "jdbc:mysql://" + this.servidor + ":3306/" + this.banco
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf8";
            this.conexao = DriverManager.getConnection(url, this.usuario, this.senha);
            return this.conexao;
        } catch(SQLException ex) {
            throw new RuntimeException("Falha na conexão com o MySQL: " + ex.getMessage(), ex);
        }
    }
}
