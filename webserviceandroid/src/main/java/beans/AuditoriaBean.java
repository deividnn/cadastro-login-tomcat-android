/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controladores.AuditoriaController;
import entidades.Auditoria;
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
public class AuditoriaBean implements Serializable {
    
    private Auditoria auditoria;
    private Auditoria auditoriac;
    private List<Auditoria> auditorias;
    private boolean novo;
    private boolean edicao;
    private String coluna;
    private String valor;
    
    @PostConstruct
    public void init() {
        novo = false;
        edicao = false;
        auditoria = new Auditoria();
        listar();
        coluna = "tipo";
        
    }
    
    public Map<String, String> colunas() {
        Map<String, String> aux = new HashMap<>();
        aux.put("Tipo", "tipo");
        return aux;
    }
    
    public void setarAuditoria() {
        novo = true;
        edicao = false;
        auditoria = auditoriac;
        Util.resetarComponente("auditoria");
    }
    
    public void listar() {
        String hql = "select vo from Auditoria vo"
                + " order by vo.id desc";
        auditorias = new AuditoriaController().listar(hql);
        
    }
    
    public void novoauditoria() {
        novo = true;
        auditoria = new Auditoria();
        Util.resetarComponente("auditoria");
    }
    
    public void cancelar() {
        novo = false;
        auditoria = new Auditoria();
        auditoriac = new Auditoria();
        listar();
        Util.resetarComponente("auditoria");
    }
    
    public void editar() {
        edicao = true;
        Util.resetarComponente("auditoria");
        
    }
    
    
    public void pesquisar() {
        auditorias=new ArrayList<>();
        String hql = "select vo from Auditoria vo"
                + " where upper(vo." + coluna + ")"
                + " like '%" + valor.toUpperCase() + "%'"
                + " order by vo." + coluna + " asc";
    
        auditorias = new AuditoriaController().listar(hql);
        Util.atualizarComponente("auditoria:lista");
    }
    
    public Auditoria getAuditoriac() {
        return auditoriac;
    }
    
    public void setAuditoriac(Auditoria auditoriac) {
        this.auditoriac = auditoriac;
    }
    
    public Auditoria getAuditoria() {
        return auditoria;
    }
    
    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }
    
    public List<Auditoria> getAuditorias() {
        return auditorias;
    }
    
    public void setAuditorias(List<Auditoria> auditorias) {
        this.auditorias = auditorias;
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
