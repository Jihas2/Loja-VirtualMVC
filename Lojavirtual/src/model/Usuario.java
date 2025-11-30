package model;

public class Usuario {
    private int id;
    private String usuario;
    private String senha;
    private String tipo;

    public Usuario(int id, String usuario, String senha, String tipo) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getSenha() { return senha; }
    public String getTipo() { return tipo; }
}
