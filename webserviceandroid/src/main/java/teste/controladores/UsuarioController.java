/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.controladores;

import java.util.List;
import teste.entidades.Usuario;
import teste.util.HibernateDAO;
import teste.util.Util;

/**
 *
 * @author Laercio
 */
public class UsuarioController {

    private static HibernateDAO dao;

    public UsuarioController() {
        UsuarioController.dao = new HibernateDAO();
    }

    public boolean salvar(Usuario usuario) {
        try {
            if (usuario.getId() == null) {
                UsuarioController.dao.salvar(usuario);
               

                return true;
            } else {
                UsuarioController.dao.atualizar(usuario);
               
                return true;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean atualizar(Usuario usuario) {
        try {
            UsuarioController.dao.atualizar(usuario);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean excluir(Usuario usuario) {
        try {
           
            UsuarioController.dao.excluir(usuario);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public List<Usuario> listar(String hql) {
        return UsuarioController.dao.listar(hql);
    }

    public Usuario pegar(String hql) {
        return (Usuario) UsuarioController.dao.carregar(hql);
    }

}
