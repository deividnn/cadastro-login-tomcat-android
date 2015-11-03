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
import org.hibernate.validator.constraints.Email;
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
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "Nome_Usu", nullable = false, unique = true, length = 30)
    private String nomeUsuario;

    @NotEmpty
    @Size(max = 20)
    @Column(name = "Usuario", nullable = false, unique = true, length = 20)
    private String usuario;

    @NotEmpty
    @Size(max = 30)
    @Email
    @Column(name = "Email", nullable = false, unique = true, length = 30)
    private String email;

    @NotEmpty
    @Size(max = 10)
    @Column(name = "Senha", nullable = false, length = 10)
    private String senha;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "TipoUsuario_idTipoUsuario",nullable = false)
    private TipoUsuario tipoUsuario;

    public Usuario() {
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
        final Usuario other = (Usuario) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nomeUsuario=" + nomeUsuario + ", usuario=" + usuario + ", email=" + email + ", senha=" + senha + ", tipoUsuario=" + tipoUsuario + '}';
    }

    
}
