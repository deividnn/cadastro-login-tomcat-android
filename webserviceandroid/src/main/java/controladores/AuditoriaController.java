/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import entidades.Auditoria;
import java.util.List;
import util.HibernateDAO;
import util.Util;

/**
 *
 * @author Laercio
 */
public class AuditoriaController {

    private static HibernateDAO dao;

    public AuditoriaController() {
        AuditoriaController.dao = new HibernateDAO();
    }

    public boolean salvar(Auditoria auditoria) {
        try {
            if (auditoria.getId() == null) {
                AuditoriaController.dao.salvar(auditoria);
                return true;
            } else {
                AuditoriaController.dao.atualizar(auditoria);
                return true;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean atualizar(Auditoria auditoria) {
        try {
            AuditoriaController.dao.atualizar(auditoria);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean excluir(Auditoria auditoria) {
        try {
            AuditoriaController.dao.excluir(auditoria);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public List<Auditoria> listar(String hql) {
        return AuditoriaController.dao.listar(hql);
    }

    public Auditoria pegar(String hql) {
        return (Auditoria) AuditoriaController.dao.carregar(hql);
    }

}
