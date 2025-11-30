package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class LoginController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String usuario, String senha) {
        return usuarioDAO.autenticar(usuario, senha);
    }
}