/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import teste.controladores.UsuarioController;
import teste.entidades.Usuario;

/**
 *
 * @author Magazine L
 */
@WebService(serviceName = "Servicos")
public class Servicos {

    /**
     * This is a sample web service operation
     *
     * @param nome
     * @return
     */
    @WebMethod(operationName = "ola")
    public String ola(@WebParam(name = "nome") String nome) {
        System.out.println(nome);
        return "Ola " + nome + " !";
    }

    /**
     * Operação de Web service
     *
     * @param usuario
     * @param email
     * @param senha
     * @return
     */
    @WebMethod(operationName = "cadastrarUsuario")
    public String cadastrarUsuario(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "email") String email,
            @WebParam(name = "senha") String senha) {
        //TODO write your implementation code here:
        try {
            Usuario u = new Usuario();
            u.setUsuario(usuario);
            u.setSenha(senha);
            u.setEmail(email);
            u.setStatus(true);
            if (new UsuarioController().salvar(u)) {
                return "ok";
            }
            return "erro";
        } catch (Exception e) {
            return "erro: " + e.toString();
        }

    }

    /**
     * Operação de Web service
     *
     * @param usuario
     * @param senha
     * @return
     */
    @WebMethod(operationName = "entrar")
    public String entrar(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "senha") String senha) {
        //TODO write your implementation code here:
        try {
            String hql = "select vo from Usuario vo"
                    + " where usuario='" + usuario + "' and senha='" + senha + "'";

            Usuario u = new UsuarioController().pegar(hql);
            if (u != null) {
                if (u.isStatus()) {
                    return "1";
                } else {
                    return "2";
                }
            }
            return "3";
        } catch (Exception e) {
            return "erro: " + e.toString();
        }
    }
}
