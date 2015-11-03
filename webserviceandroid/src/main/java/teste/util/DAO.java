/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.util;


import java.util.List;

/**
 *
 * @author DeividnN
 * interface de operacoes de persistencia
 * @param <T>
 */
public interface DAO<T> {

    public boolean salvar(T t);

    public boolean atualizar(T t);

    public boolean excluir(T t);

    public List<T> listar(String hql);

    public T carregar(String hql);
}