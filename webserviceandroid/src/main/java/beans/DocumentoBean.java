/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controladores.ComentarioController;
import controladores.DocumentoController;
import controladores.TipoDocumentoController;
import entidades.Comentario;
import entidades.Documento;
import entidades.TipoDocumento;
import entidades.Usuario;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.Util;

/**
 *
 * @author Laercio
 */
@ManagedBean
@ViewScoped
public class DocumentoBean implements Serializable {

    private Documento documento;
    private Documento documentoc;
    private List<Documento> documentos;
    private boolean novo;
    private boolean edicao;
    private String coluna;
    private String valor;
    private String nomeArquivo;
    private boolean avaliardocumento;
    private List<Comentario> comentarios;
    private Comentario comentario;
    private double media;
    private int total;

    @PostConstruct
    public void init() {
        avaliardocumento = false;
        novo = false;
        edicao = false;
        documento = new Documento();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();
        if (currentPage.contains("restrito/documentos")) {
            listar2();
        } else {
            listar();
        }
        coluna = "titulo";

    }

    public void excluircomentario(Comentario com) {
        if (new ComentarioController().excluir(com)) {
            listarComentarios();
        }
    }

    public void verDoc(Documento doc) {
        try {
            String nome = doc.getTitulo() + "_" + doc.getAutor();
            nome = nome.replaceAll(" ", "-");
            nome = nome.toLowerCase();
            String text = Normalizer.normalize(nome, Normalizer.Form.NFD);
            text = text.replaceAll("[^\\p{ASCII}]", "");
            nomeArquivo = text + ".pdf";
           
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                    .getContext();
            String pasta = servletContext.getRealPath("")
                    + File.separator + "resources"
                    + File.separator + "arquivos";
            File vpasta = new File(pasta);
            File a = new File(vpasta + File.separator + nomeArquivo);
            //  a.createNewFile();
           
            FileUtils.writeByteArrayToFile(a, doc.getArquivo());
            Util.atualizarComponente("pdf");
            Util.executarJavascript("PF('pdf').show();");
        } catch (Exception e) {
            Util.criarMensagemAviso(e.toString());
        }

    }

    public void avaliarDoc(Documento doc) {
        avaliardocumento = true;
        documento = doc;
        comentario = new Comentario();
        comentario.setAvaliacao(1);
        Usuario user = (Usuario) Util.pegarObjetoDaSessao("usuarioLogado");
        if (user != null) {
            comentario.setNome(user.getNomeUsuario());
        }
        listarComentarios();

    }

    public void cancelarAvaliarDoc() {
        avaliardocumento = false;
        listar();
    }

    public void listarComentarios() {
        String hql = "select vo from Comentario vo"
                + " where vo.documento=" + documento.getId() + ""
                + " order by vo.id desc";
        comentarios = new ComentarioController().listar(hql);
        double aux = 0;
        double totalr = 0;
        total = 0;
        media = 0;

        if (!comentarios.isEmpty()) {
            totalr = comentarios.size();
            this.total = comentarios.size();
        }

        for (Comentario c : comentarios) {
            aux += c.getAvaliacao();
        }
        media = aux / totalr;
    }

    public void enviarArquivo(FileUploadEvent event) {
        try {
            nomeArquivo = event.getFile().getFileName();
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                    .getContext();
            String pasta = servletContext.getRealPath("")
                    + File.separator + "resources"
                    + File.separator + "temp";
            File vpasta = new File(pasta);
            if (!vpasta.exists()) {
                vpasta.setWritable(true);
                vpasta.mkdirs();
            }
            String novoarquivo = pasta + File.separator + event.getFile().getFileName();
            File a = new File(novoarquivo);
            a.createNewFile();
            FileUtils.copyInputStreamToFile(event.getFile().getInputstream(), a);
            byte[] fotobyte = FileUtils.readFileToByteArray(a);
            documento.setArquivo(fotobyte);
            a.delete();
            Util.executarJavascript("PF('statusDialog').hide();");
        } catch (IOException ex) {
            Logger.getLogger(DocumentoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void trocarArquivo() {
        nomeArquivo = "";
    }

    public StreamedContent fazerDownload() {
        String nome = documento.getTitulo() + "_" + documento.getAutor();
        nome = nome.replaceAll(" ", "-");
        nome = nome.toLowerCase();
        String text = Normalizer.normalize(nome, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");
        return new DefaultStreamedContent(
                new ByteArrayInputStream(documento.getArquivo()),
                "application/octet-stream",
                text + ".pdf");
    }

    public StreamedContent fazerDownload(Documento doc) {
        String nome = doc.getTitulo() + "_" + doc.getAutor();
        nome = nome.replaceAll(" ", "-");
        nome = nome.toLowerCase();
        String text = Normalizer.normalize(nome, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");
        return new DefaultStreamedContent(
                new ByteArrayInputStream(doc.getArquivo()),
                "application/octet-stream",
                text + ".pdf");
    }

    public Map<String, String> colunas() {
        Map<String, String> aux = new HashMap<>();
        aux.put("Tipo Documento", "tipoDocumento.descricao");
        aux.put("Titulo", "titulo");
        aux.put("Autor", "autor");

        return aux;
    }

    public void setarDocumento() {
        novo = true;
        edicao = false;
        documento = documentoc;
        Util.resetarComponente("documento");
    }

    public void listar() {
        listartodos();

    }

    public void listar2() {
        listartodos2();

    }

    private void listartodos() {
        String hql = "select vo from Documento vo"
                + " order by vo.id desc";
        documentos = new DocumentoController().listar(hql);
    }

    private void listartodos2() {
        Usuario user = (Usuario) Util.pegarObjetoDaSessao("usuarioLogado");
        if (user.getUsuario().equals("admin")) {
            String hql = "select vo from Documento vo"
                    + " order by vo.id desc";
            documentos = new DocumentoController().listar(hql);
        } else {
            String hql = "select vo from Documento vo"
                    + " where vo.usuario=" + user.getId() + " "
                    + "order by vo.id desc";
            documentos = new DocumentoController().listar(hql);
        }
    }

    public void novodocumento() {
        novo = true;
        nomeArquivo = "";
        documento = new Documento();
        documento.setTipoDocumento(new TipoDocumento());
        Util.resetarComponente("documento");
    }

    public void cancelar() {
        novo = false;
        documento = new Documento();
        documentoc = new Documento();
        listar();
        Util.resetarComponente("documento");
    }

    public void editar() {
        Usuario logado = (Usuario) Util.pegarObjetoDaSessao("usuarioLogado");
        if (!Objects.equals(logado.getId(), documento.getUsuario().getId())
                && !logado.getUsuario().equals("admin")) {
            Util.criarMensagemAviso("voce nao pode editar documento de outro usuario");
        } else {
            edicao = true;
        }
   

        Util.resetarComponente("documento");

    }

    public void salvar() {

        try {
            boolean ok = true;
            Usuario logado = (Usuario) Util.pegarObjetoDaSessao("usuarioLogado");
            if (documento.getId() != null) {
                if (!Objects.equals(logado.getId(), documento.getUsuario().getId())
                        && !logado.getUsuario().equals("admin")) {
                    Util.criarMensagemAviso("voce nao pode salvar documento de outro usuario");
                    ok = false;
                } else {
                    ok = true;
                }
            }

            if (ok) {
                String hqltd = "select vo from TipoDocumento vo "
                        + "where vo.id=" + documento.getTipoDocumento().getId() + "";
                documento.setTipoDocumento(new TipoDocumentoController().pegar(hqltd));
                documento.setUsuario((Usuario) Util.pegarObjetoDaSessao("usuarioLogado"));
            
                if (new DocumentoController().salvar(documento)) {
                    Util.criarMensagemAviso("Documento " + documento.getTitulo() + " salvo com sucesso");
                    listar();
                    edicao = false;
                }
            }
        } catch (Exception e) {
            Util.criarMensagemAviso("Erro ao salvar documento " + e.toString());
        }
    }

    public void excluir() {
        try {
            boolean ok = true;
            Usuario logado = (Usuario) Util.pegarObjetoDaSessao("usuarioLogado");
            if (!Objects.equals(logado.getId(), documento.getUsuario().getId())
                    && !logado.getUsuario().equals("admin")) {
                Util.criarMensagemAviso("voce nao pode excluir documento de outro usuario");
                ok = false;
            } else {
                ok = true;
            }
            if (ok) {
                new DocumentoController().excluir(documento);
                listar();
                novo = false;
                Util.resetarComponente("documento");
                Util.criarMensagemAviso("documento " + documento.getTitulo() + " excluido com sucesso");
            }
        } catch (Exception e) {
            Util.criarMensagemAviso("erro ao excluir documento");
        }

    }

    public void pesquisar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();
        if (currentPage.contains("restrito/documentos")) {
            pesquisar2();
        } else {
            pesquisarTodos();
        }
    }

    public void pesquisar2() {
        pesquisarTodos2();
    }

    private void pesquisarTodos2() {

        Usuario user = (Usuario) Util.pegarObjetoDaSessao("usuarioLogado");
        if (user.getUsuario().equals("admin")) {

            String hql = "select vo from Documento vo"
                    + " where upper(vo." + coluna + ")"
                    + " like '%" + valor.toUpperCase() + "%'"
                    + " order by vo." + coluna + " asc";
           
            documentos = new DocumentoController().listar(hql);
            Util.atualizarComponente("documento:lista");
        } else {
            String hql = "select vo from Documento vo"
                    + " where upper(vo." + coluna + ")"
                    + " like '%" + valor.toUpperCase() + "%'"
                    + " and vo.usuario=" + user.getId() + ""
                    + " order by vo." + coluna + " asc";
        
            documentos = new DocumentoController().listar(hql);
            Util.atualizarComponente("documento:lista");
        }
    }

    private void pesquisarTodos() {
        String hql = "select vo from Documento vo"
                + " where upper(vo." + coluna + ")"
                + " like '%" + valor.toUpperCase() + "%'"
                + " order by vo." + coluna + " asc";
       
        documentos = new DocumentoController().listar(hql);
        Util.atualizarComponente("documento:lista");
    }

    public void salvarcomentario() {
        comentario.setDocumento(documento);
        new ComentarioController().salvar(comentario);
        listarComentarios();
        Util.atualizarComponente("documento:listacomentarios");
        Util.atualizarComponente("documento:media");
        Util.executarJavascript("PF('comentario').hide();");
        comentario = new Comentario();
        comentario.setAvaliacao(1);
        Usuario user = (Usuario) Util.pegarObjetoDaSessao("usuarioLogado");
        if (user != null) {
            comentario.setNome(user.getNomeUsuario());
        }
    }

    public void onrate(RateEvent rateEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor da avaliacao:" + ((Integer) rateEvent.getRating()), "Valor da avaliacao:" + ((Integer) rateEvent.getRating()));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Documento getDocumentoc() {
        return documentoc;
    }

    public void setDocumentoc(Documento documentoc) {
        this.documentoc = documentoc;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
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

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public boolean isAvaliardocumento() {
        return avaliardocumento;
    }

    public void setAvaliardocumento(boolean avaliardocumento) {
        this.avaliardocumento = avaliardocumento;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
