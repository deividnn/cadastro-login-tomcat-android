package teste.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;
import teste.entidades.Usuario;

/**
 *
 * @author DeividnN classe responsavel por verificar a autenticacao do usuario
 * logado
 */
public class LoginFilter
        implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent pe) {
        FacesContext facesContext = pe.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        //apenas verifica se estiver dentro da pasta resitrito
        if (currentPage.contains("/restrito/")) {

            HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().
                    getExternalContext().getSession(false);
            //se o usuario for nulo ou sessao nula redirecionar para pagina inicial
            Usuario user = (Usuario) Util.pegarObjetoDaSessao("usuarioLogado");
            if ((sessao == null)
                    || (user == null)) {
                Util.redirecionarPagina("");
            } else {

                String pagina = currentPage.replaceAll(".xhtml", "");

                Util.redirecionarPagina("restrito");

            }

        }
    }

    @Override
    public void beforePhase(PhaseEvent pe) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
