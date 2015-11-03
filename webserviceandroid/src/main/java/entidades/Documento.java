/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
public class Documento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Doc")
    private Integer id;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "Titulo", nullable = false,unique = true, length = 50)
    private String titulo;
    
    @NotEmpty
    @Size(max = 30)
    @Column(name = "Autor", nullable = false, length = 30)
    private String autor;
    
    @NotEmpty
    @Size(max = 100)
    @Column(name = "Descricao", nullable = false, length = 100)
    private String descricao;
    
    @NotNull
    @Column(name = "Arquivo", nullable = false)
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] arquivo;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "TipoDocumento_idTipoDocumento",nullable = false)
    private TipoDocumento tipoDocumento;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "Usuario_id_usuario",nullable = false)
    private Usuario usuario;

    public Documento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Documento other = (Documento) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Documento{" + "id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", descricao=" + descricao + ", arquivo=" + arquivo + ", tipoDocumento=" + tipoDocumento + ", usuario=" + usuario + '}';
    }

    
}
