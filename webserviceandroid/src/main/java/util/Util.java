/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controladores.AuditoriaController;
import controladores.UsuarioController;
import entidades.Auditoria;
import entidades.Usuario;
import java.io.IOException;
import java.util.Calendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.primefaces.context.RequestContext;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 *
 * @author Laercio
 */
public class Util {

    public static Session pegarSessao() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    /**
     * *
     * cria uma mensagem do facesmessage, do tipo warning
     *
     * @param texto
     */
    public static void criarMensagemAviso(String texto) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, texto, texto);
        FacesContext.getCurrentInstance().addMessage(texto, msg);
    }

    /**
     * *
     * limpa valores do componente
     *
     * @param id
     */
    public static void resetarComponente(String id) {
        RequestContext.getCurrentInstance().reset(id);
    }

    public static void atualizarComponente(String id) {
        RequestContext.getCurrentInstance().update(id);
    }

    public static Set<Class<?>> listarClassesDeUmPacote(String pacote) {
        Reflections ref = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */),
                        new ResourcesScanner())
                .setUrls(ClasspathHelper.forPackage(pacote))
                .filterInputsBy(new FilterBuilder().
                        include(FilterBuilder.prefix(pacote))));
        Set<Class<?>> classes = ref.getSubTypesOf(Object.class);
        return classes;
    }

    public static void criarObjetoDeSessao(Object obj, String nome) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute(nome, obj);
    }

    public static Object pegarObjetoDaSessao(String nomeSessao) {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return sessao.getAttribute(nomeSessao);
    }

    public static void redirecionarPagina(String pagina) {
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url + "/" + pagina);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void executarJavascript(String comando) {
        RequestContext.getCurrentInstance().execute(comando);
    }

    public static void salvarAuditoria(String tabela, String valor, String tipo) {
        Auditoria aud = new Auditoria();
        Usuario user = (Usuario) Util.pegarObjetoDaSessao("usuarioLogado");
        aud.setUsuario(user);
        if (user == null) {
            String hql = "select vo from Usuario vo where vo.usuario='admin'";
            Usuario u = new UsuarioController().pegar(hql);
            aud.setUsuario(u);
        }
        aud.setDataAud(Calendar.getInstance().getTime());
        aud.setTabela(tabela);
        aud.setValor(valor);
        aud.setTipo(tipo);
     
        new AuditoriaController().salvar(aud);
    }

}
