/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.util;

import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author DeividnN implementacao da interface dao
 * @param <T>
 */
public class HibernateDAO<T> implements DAO<T> {
    //sessao atual
    private Session sessao;

    public HibernateDAO() {
        super();
    }
   
    
    //metodo que inicia uma nova transacao
     private void iniciarTransacao() {
        this.sessao = Util.pegarSessao();
        if (this.sessao.getTransaction() != null
                && this.sessao.getTransaction().isActive()) {
            this.sessao.getTransaction();
        } else {
            this.sessao.beginTransaction();
        }
    }

    //metodo que desfaz a transacao atual
    public void fazerRollback() {
        //caso ocorra um erro o roolback desfaz toda a transacao atual
        sessao.getTransaction().rollback();
    }

    //metodo que conclui uma transacao
    public void fazerCommit() {
        //apos todas as operacoes de persistencia o commit grava a transacao no bd
        sessao.getTransaction().commit();
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public boolean salvar(T t) {
        try {
            iniciarTransacao();        
            //salva o objeto no bd
            sessao.save(t);
            fazerCommit();
            return true;
        } catch (Exception e) {
            fazerRollback();
            //mostra o erro na pagina jsf
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public boolean atualizar(T t) {
        try {
            iniciarTransacao();
            //atualiza o objeto no bd
            sessao.saveOrUpdate(t);
            fazerCommit();
            return true;
        } catch (Exception e) {
            fazerRollback();
            //mostra o erro na pagina jsf
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public boolean excluir(T t) {
        try {
            iniciarTransacao();
            //exclui o objeto no bd
            sessao.delete(t);
            fazerCommit();
            return true;
        } catch (Exception e) {
            fazerRollback();
            //mostra o erro na pagina jsf
            Util.criarMensagemAviso(e.toString());
            return false;
        }
    }

    /**
     *
     * @param hql
     * @return
     */
    @Override
    public List<T> listar(String hql) {
        try {
            iniciarTransacao();
            //retorna uma lista de objeto do bd
            List<T> lista = sessao.createQuery(hql).setCacheable(true).list();
            fazerCommit();
            return lista;
        } catch (Exception e) {
            fazerRollback();
            //mostra o erro na pagina jsf
            Util.criarMensagemAviso(e.toString());
            return null;
        }

    }

    /**
     *
     * @param hql
     * @return
     */
    @Override
    public T carregar(String hql) {
        try {
            iniciarTransacao();
            //pega um objeto do bd
            T registro = (T) sessao.createQuery(hql).setCacheable(true).setMaxResults(1).uniqueResult();
            fazerCommit();
            return registro;
        } catch (Exception e) {
            fazerRollback();
            //mostra o erro na pagina jsf
            Util.criarMensagemAviso(e.toString());
            return null;
        }
    }

}