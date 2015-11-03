package dnn.login;

/**
 * Created by Magazine L on 02/11/2015.
 */

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class WebService {

    static String NAMESPACE = "http://webservice/";
    static String URL = "http://" + MainActivity.webservice + "/webserviceandroid/Servicos?wsdl";

    public static String ola(String nome) {
        try {

            SoapObject request = new SoapObject(NAMESPACE, "ola");

            request.addProperty("nome", nome);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.getServiceConnection().setRequestProperty(
                    "Connection", "close");
            System.setProperty("http.keepAlive", "false");
            ht.call(NAMESPACE + "ola", envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            return response.toString();
        } catch (Exception ex) {
            Log.e("", "Error: " + ex.getMessage());
        }
        return "erro";
    }

    public static String caadastrar(String usuario, String senha, String email) {
        try {

            SoapObject request = new SoapObject(NAMESPACE, "cadastrarUsuario");

            request.addProperty("usuario", usuario);
            request.addProperty("senha", senha);
            request.addProperty("email", email);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.getServiceConnection().setRequestProperty(
                    "Connection", "close");
            System.setProperty("http.keepAlive", "false");
            ht.call(NAMESPACE + "cadastrarUsuario", envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            return response.toString();
        } catch (Exception ex) {
            Log.e("", "Error: " + ex.getMessage());
        }
        return "erro";
    }

    public static String entrar(String usuario, String senha) {
        try {

            SoapObject request = new SoapObject(NAMESPACE, "entrar");

            request.addProperty("usuario", usuario);
            request.addProperty("senha", senha);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.getServiceConnection().setRequestProperty(
                    "Connection", "close");
            System.setProperty("http.keepAlive", "false");
            ht.call(NAMESPACE + "entrar", envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            return response.toString();
        } catch (Exception ex) {
            Log.e("", "Error: " + ex.getMessage());
        }
        return "erro";
    }
}
