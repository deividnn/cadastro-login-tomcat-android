/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import entidades.Documento;
import java.util.List;
import util.HibernateDAO;
import util.Util;

/**
 *
 * @author Laercio
 */
public class DocumentoController {

    private static HibernateDAO dao;

    public DocumentoController() {
        DocumentoController.dao = new HibernateDAO();
    }

    public boolean salvar(Documento documento) {
        try {
            if (documento.getId() == null) {
                DocumentoController.dao.salvar(documento);
                Util.salvarAuditoria("documentos", documento.toString(), "Inserir");
                return true;
            } else {
                DocumentoController.dao.atualizar(documento);
                Util.salvarAuditoria("documentos", documento.toString(), "Alterar");
                return true;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean atualizar(Documento documento) {
        try {
            DocumentoController.dao.atualizar(documento);
            Util.salvarAuditoria("documentos", documento.toString(), "Alterar");
            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public boolean excluir(Documento documento) {
        try {
            Util.salvarAuditoria("documentos", documento.toString(), "Excluir");
            DocumentoController.dao.excluir(documento);

            return true;
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    public List<Documento> listar(String hql) {
        return DocumentoController.dao.listar(hql);
    }

    public Documento pegar(String hql) {
        return (Documento) DocumentoController.dao.carregar(hql);
    }

}
