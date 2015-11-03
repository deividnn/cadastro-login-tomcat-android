/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Magazine L
 */
@Entity
@Table(schema = "biblioteca")
@DynamicInsert(true)
@DynamicUpdate(true)
@Cacheable
@Cache(region = "geral", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Auditoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Auditoria")
    private Integer id;

    @NotEmpty
    @Size(max = 20)
    @Column(name = "Tipo", nullable = false, length = 20)
    private String tipo;
    
    @NotEmpty
    @Size(max = 1000)
    @Column(name = "Valor", nullable = false, length = 1000)
    private String valor;
    
    @NotEmpty
    @Size(max = 30)
    @Column(name = "Tabela", nullable = false, length = 30)
    private String tabela;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "Data", nullable = false)
    private Date dataAud;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "Usuario_id_usuario", nullable = false)
    private Usuario usuario;

    public Auditoria() {
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    
    
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataAud() {
        return dataAud;
    }

    public void setDataAud(Date dataAud) {
        this.dataAud = dataAud;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Auditoria other = (Auditoria) obj;
        return true;
    }

   

}
