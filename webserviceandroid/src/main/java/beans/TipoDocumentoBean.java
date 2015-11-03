/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controladores.TipoDocumentoController;
import entidades.TipoDocumento;
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
public class TipoDocumentoBean implements Serializable {
    
    private TipoDocumento tipoDocumento;
    private TipoDocumento tipoDocumentoc;
    private List<TipoDocumento> tipoDocumentos;
    private boolean novo;
    private boolean edicao;
    private String coluna;
    private String valor;
    
    @PostConstruct
    public void init() {
        novo = false;
        edicao = false;
        tipoDocumento = new TipoDocumento();
        listar();
        coluna = "descricao";
        
    }
    
    public Map<String, String> colunas() {
        Map<String, String> aux = new HashMap<>();
        aux.put("Descricao", "descricao");
               
        return aux;
    }
    
    public void setarTipoDocumento() {
        novo = true;
        edicao = false;
        tipoDocumento = tipoDocumentoc;
        Util.resetarComponente("tipoDocumento");
    }
    
    public void listar() {
        String hql = "select vo from TipoDocumento vo"
                + " order by vo.id desc";
        tipoDocumentos = new TipoDocumentoController().listar(hql);
        
    }
    
    public void novotipoDocumento() {
        novo = true;
        tipoDocumento = new TipoDocumento();
        Util.resetarComponente("tipoDocumento");
    }
    
    public void cancelar() {
        novo = false;
        tipoDocumento = new TipoDocumento();
        tipoDocumentoc = new TipoDocumento();
        listar();
        Util.resetarComponente("tipoDocumento");
    }
    
    public void editar() {
        edicao = true;
        Util.resetarComponente("tipoDocumento");
        
    }
    
    public void salvar() {
        try {
            if (new TipoDocumentoController().salvar(tipoDocumento)) {
                Util.criarMensagemAviso("TipoDocumento " + tipoDocumento.getDescricao() + " salvo com sucesso");
                listar();
                edicao = false;
            }
        } catch (Exception e) {
            Util.criarMensagemAviso("Erro ao salvar tipoDocumento");
        }
    }
    
    public void excluir() {
        try {
            new TipoDocumentoController().excluir(tipoDocumento);
            listar();
            novo = false;
            Util.resetarComponente("tipoDocumento");
            Util.criarMensagemAviso("tipoDocumento " + tipoDocumento.getDescricao() + " excluido com sucesso");
        } catch (Exception e) {
            Util.criarMensagemAviso("erro ao excluir tipoDocumento");
        }
        
    }
    
    public void pesquisar() {
        tipoDocumentos=new ArrayList<>();
        String hql = "select vo from TipoDocumento vo"
                + " where upper(vo." + coluna + ")"
                + " like '%" + valor.toUpperCase() + "%'"
                + " order by vo." + coluna + " asc";
     
        tipoDocumentos = new TipoDocumentoController().listar(hql);
        Util.atualizarComponente("tipoDocumento:lista");
    }
    
    public TipoDocumento getTipoDocumentoc() {
        return tipoDocumentoc;
    }
    
    public void setTipoDocumentoc(TipoDocumento tipoDocumentoc) {
        this.tipoDocumentoc = tipoDocumentoc;
    }
    
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public List<TipoDocumento> getTipoDocumentos() {
        return tipoDocumentos;
    }
    
    public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
        this.tipoDocumentos = tipoDocumentos;
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
