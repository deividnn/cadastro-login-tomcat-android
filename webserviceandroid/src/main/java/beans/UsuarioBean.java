/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controladores.TipoUsuarioController;
import controladores.UsuarioController;
import entidades.TipoUsuario;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import util.Util;

/**
 *
 * @author Laercio
 */
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

    private Usuario usuario;
    private Usuario usuarioc;
    private List<Usuario> usuarios;
    private boolean novo;
    private boolean edicao;
    private String coluna;
    private String valor;

    @PostConstruct
    public void init() {
        novo = false;
        edicao = false;
        usuario = new Usuario();
        usuario.setTipoUsuario(new TipoUsuario());
        listar();
        coluna = "nomeUsuario";

    }

    public Map<String, String> colunas() {
        Map<String, String> aux = new HashMap<>();
        aux.put("Nome", "nomeUsuario");
        aux.put("Usuario", "usuario");
        aux.put("Email", "email");

        return aux;
    }

    public void setarUsuario() {
        novo = true;
        edicao = false;
        usuario = usuarioc;
        usuario.setTipoUsuario(new TipoUsuario());
        Util.resetarComponente("usuario");
    }

    public void listar() {
        String hql = "select vo from Usuario vo"
                + " order by vo.id desc";
        usuarios = new UsuarioController().listar(hql);

    }

    public void novousuario() {
        novo = true;
        usuario = new Usuario();
        Util.resetarComponente("usuario");
    }

    public void cancelar() {
        novo = false;
        usuario = new Usuario();
        usuarioc = new Usuario();
        listar();
        Util.resetarComponente("usuario");
    }

    public void editar() {
        edicao = true;
        Util.resetarComponente("usuario");

    }

    public void salvar() {
        try {
            String hqltd = "select vo from TipoUsuario vo "
                    + "where vo.id=" + usuario.getTipoUsuario().getId() + "";
            usuario.setTipoUsuario(new TipoUsuarioController().pegar(hqltd));
            if (new UsuarioController().salvar(usuario)) {
                Util.criarMensagemAviso("Usuario " + usuario.getNomeUsuario() + " salvo com sucesso");
                listar();
                edicao = false;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso("Erro ao salvar usuario");
        }
    }

    public void salvar2() {
        try {
             String hqltd = "select vo from TipoUsuario vo "
                    + "where vo.id=" + usuario.getTipoUsuario().getId() + "";
            usuario.setTipoUsuario(new TipoUsuarioController().pegar(hqltd));
            if (new UsuarioController().salvar(usuario)) {
                Util.criarMensagemAviso("Usuario " + usuario.getNomeUsuario() + " salvo com sucesso");
                listar();
                usuario = new Usuario();
                usuario.setTipoUsuario(new TipoUsuario());
                edicao = false;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso("Erro ao salvar usuario");
        }
    }

    public void excluir() {
        try {
            new UsuarioController().excluir(usuario);
            listar();
            novo = false;
            Util.resetarComponente("usuario");
            Util.criarMensagemAviso("usuario " + usuario.getNomeUsuario() + " excluido com sucesso");
        } catch (Exception e) {
            Util.criarMensagemAviso("erro ao excluir usuario");
        }

    }

    public void pesquisar() {
        usuarios = new ArrayList<>();
        String hql = "select vo from Usuario vo"
                + " where upper(vo." + coluna + ")"
                + " like '%" + valor.toUpperCase() + "%'"
                + " order by vo." + coluna + " asc";
        
        usuarios = new UsuarioController().listar(hql);
        Util.atualizarComponente("usuario:lista");
    }

    public Usuario getUsuarioc() {
        return usuarioc;
    }

    public void setUsuarioc(Usuario usuarioc) {
        this.usuarioc = usuarioc;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public String getColuna() {
        return coluna;
    }

    public void setColuna(String coluna) {
        this.coluna = coluna;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
