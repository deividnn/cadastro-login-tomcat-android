/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controladores.TipoUsuarioController;
import entidades.TipoUsuario;
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
public class TipoUsuarioBean implements Serializable {

    private TipoUsuario tipoUsuario;
    private TipoUsuario tipoUsuarioc;
    private List<TipoUsuario> tipoUsuarios;
    private List<TipoUsuario> tipoUsuariosc;
    private boolean novo;
    private boolean edicao;
    private String coluna;
    private String valor;

    @PostConstruct
    public void init() {
        novo = false;
        edicao = false;
        tipoUsuario = new TipoUsuario();
        listar();
        listarc();
        coluna = "descricao";

    }

    public Map<String, String> colunas() {
        Map<String, String> aux = new HashMap<>();
        aux.put("Descricao", "descricao");

        return aux;
    }

    public void setarTipoUsuario() {
        novo = true;
        edicao = false;
        tipoUsuario = tipoUsuarioc;
        Util.resetarComponente("tipoUsuario");
    }

    public void listar() {
        String hql = "select vo from TipoUsuario vo"
                + " order by vo.id desc";
        tipoUsuarios = new TipoUsuarioController().listar(hql);

    }

    public void listarc() {
        String hql = "select vo from TipoUsuario vo"
                + " where vo.descricao!='ADMINISTRADOR' order by vo.id desc";
        tipoUsuariosc = new TipoUsuarioController().listar(hql);

    }

    public void novotipoUsuario() {
        novo = true;
        tipoUsuario = new TipoUsuario();
        Util.resetarComponente("tipoUsuario");
    }

    public void cancelar() {
        novo = false;
        tipoUsuario = new TipoUsuario();
        tipoUsuarioc = new TipoUsuario();
        listar();
        Util.resetarComponente("tipoUsuario");
    }

    public void editar() {
        edicao = true;
        Util.resetarComponente("tipoUsuario");

    }

    public void salvar() {
        try {
            if (new TipoUsuarioController().salvar(tipoUsuario)) {
                Util.criarMensagemAviso("TipoUsuario " + tipoUsuario.getDescricao() + " salvo com sucesso");
                listar();
                edicao = false;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso("Erro ao salvar tipoUsuario");
        }
    }

    public void excluir() {
        try {
            new TipoUsuarioController().excluir(tipoUsuario);
            listar();
            novo = false;
            Util.resetarComponente("tipoUsuario");
            Util.criarMensagemAviso("tipoUsuario " + tipoUsuario.getDescricao() + " excluido com sucesso");
        } catch (Exception e) {
            Util.criarMensagemAviso("erro ao excluir tipoUsuario");
        }

    }

    public void pesquisar() {
        tipoUsuarios = new ArrayList<>();
        String hql = "select vo from TipoUsuario vo"
                + " where upper(vo." + coluna + ")"
                + " like '%" + valor.toUpperCase() + "%'"
                + " order by vo." + coluna + " asc";
     
        tipoUsuarios = new TipoUsuarioController().listar(hql);
        Util.atualizarComponente("tipoUsuario:lista");
    }

    public TipoUsuario getTipoUsuarioc() {
        return tipoUsuarioc;
    }

    public void setTipoUsuarioc(TipoUsuario tipoUsuarioc) {
        this.tipoUsuarioc = tipoUsuarioc;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<TipoUsuario> getTipoUsuarios() {
        return tipoUsuarios;
    }

    public void setTipoUsuarios(List<TipoUsuario> tipoUsuarios) {
        this.tipoUsuarios = tipoUsuarios;
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

    public List<TipoUsuario> getTipoUsuariosc() {
        return tipoUsuariosc;
    }

    public void setTipoUsuariosc(List<TipoUsuario> tipoUsuariosc) {
        this.tipoUsuariosc = tipoUsuariosc;
    }
    
    

}
