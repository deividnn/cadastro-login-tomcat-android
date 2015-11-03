/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Comentario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Coment")
    private Integer id;

    @NotEmpty
    @Size(max = 20)
    @Column(name = "Nome", nullable = false, length = 20)
    private String nome;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "Comentario", nullable = false, length = 100)
    private String comentario;

    @NotNull
    @Column(name = "Avaliacao", nullable = false,length = 9)
    private Integer avaliacao;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "Documentos_id_Doc",nullable = false)
    private Documento documento;

    public Comentario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
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
        final Comentario other = (Comentario) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Comentario{" + "id=" + id + ", nome=" + nome + ", comentario=" + comentario + ", avaliacao=" + avaliacao + ", documento=" + documento + '}';
    }

   
   
}
