/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import entidades.TipoDocumento;
import java.util.List;
import util.HibernateDAO;
import util.Util;

/**
 *
 * @author Laercio
 */
public class TipoDocumentoController {

    private static HibernateDAO dao;

    public TipoDocumentoController() {
        TipoDocumentoController.dao = new HibernateDAO();
    }

    public boolean salvar(TipoDocumento tipoDocumento) {
        try {
            if (tipoDocumento.getId() == null) {
                TipoDocumentoController.dao.salvar(tipoDocumento);
                return true;
            } else {
                TipoDocumentoController.dao.atualizar(tipoDocumento);
                return true;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean atualizar(TipoDocumento tipoDocumento) {
        try {
            TipoDocumentoController.dao.atualizar(tipoDocumento);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean excluir(TipoDocumento tipoDocumento) {
        try {
            TipoDocumentoController.dao.excluir(tipoDocumento);
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public List<TipoDocumento> listar(String hql) {
        return TipoDocumentoController.dao.listar(hql);
    }

    public TipoDocumento pegar(String hql) {
        return (TipoDocumento) TipoDocumentoController.dao.carregar(hql);
    }

}
