/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import entidades.Comentario;
import java.util.List;
import util.HibernateDAO;
import util.Util;

/**
 *
 * @author Laercio
 */
public class ComentarioController {

    private static HibernateDAO dao;

    public ComentarioController() {
        ComentarioController.dao = new HibernateDAO();
    }

    public boolean salvar(Comentario comentario) {
        try {
            if (comentario.getId() == null) {
                ComentarioController.dao.salvar(comentario);
                Util.salvarAuditoria("comentarios", comentario.toString(), "Inserir");
                return true;
            } else {
                ComentarioController.dao.atualizar(comentario);
                Util.salvarAuditoria("comentarios", comentario.toString(), "Alterar");
                return true;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean atualizar(Comentario comentario) {
        try {
            ComentarioController.dao.atualizar(comentario);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean excluir(Comentario comentario) {
        try {
            Util.salvarAuditoria("comentarios", comentario.toString(), "Excluir");
            ComentarioController.dao.excluir(comentario);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public List<Comentario> listar(String hql) {
        return ComentarioController.dao.listar(hql);
    }

    public Comentario pegar(String hql) {
        return (Comentario) ComentarioController.dao.carregar(hql);
    }

}
