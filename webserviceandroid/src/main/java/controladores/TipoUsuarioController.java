/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import entidades.TipoUsuario;
import java.util.List;
import util.HibernateDAO;
import util.Util;

/**
 *
 * @author Laercio
 */
public class TipoUsuarioController {

    private static HibernateDAO dao;

    public TipoUsuarioController() {
        TipoUsuarioController.dao = new HibernateDAO();
    }

    public boolean salvar(TipoUsuario tipoUsuario) {
        try {
            if (tipoUsuario.getId() == null) {
                TipoUsuarioController.dao.salvar(tipoUsuario);
                return true;
            } else {
                TipoUsuarioController.dao.atualizar(tipoUsuario);
                return true;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean atualizar(TipoUsuario tipoUsuario) {
        try {
            TipoUsuarioController.dao.atualizar(tipoUsuario);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean excluir(TipoUsuario tipoUsuario) {
        try {
            TipoUsuarioController.dao.excluir(tipoUsuario);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public List<TipoUsuario> listar(String hql) {
        return TipoUsuarioController.dao.listar(hql);
    }

    public TipoUsuario pegar(String hql) {
        return (TipoUsuario) TipoUsuarioController.dao.carregar(hql);
    }

}
