/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controladores.UsuarioController;
import entidades.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import util.Util;

/**
 *
 * @author Laercio
 */
@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

    private Usuario usuario;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public void fazerLogin() {
        String hql = "select vo from Usuario vo"
                + " where vo.usuario='" + usuario.getUsuario() + "'"
                + " and vo.senha='" + usuario.getSenha() + "'";
        Usuario user = new UsuarioController().pegar(hql);
        
        if (user != null) {
            Util.criarObjetoDeSessao(user, "usuarioLogado");
            if (user.getTipoUsuario().getDescricao().equals("ADMINISTRADOR")) {
                Util.redirecionarPagina("restrito/auditoria.jsf");
            } else {
                Util.redirecionarPagina("restrito/");
            }
        } else {
            Util.criarMensagemAviso("usuario/senha incorretos");
        }

    }

    public void fazerLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //redireciona para pagina login
        Util.redirecionarPagina("");
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
