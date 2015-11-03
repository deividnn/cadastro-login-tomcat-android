package dnn.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    Button button, buttonEntrar;
    EditText usuario, senha, uwebservice;
    static String webservice = "";
    static ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        addListenerOnButtonentrar();
        usuario = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.senha);
        uwebservice = (EditText) findViewById(R.id.webservice);
        uwebservice.setText("10.0.2.2:8084");
        webservice = uwebservice.getText().toString().replaceAll("http://", "");
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
    }

    public void addListenerOnButtonentrar() {

        final Context context = this;

        buttonEntrar = (Button) findViewById(R.id.botaoEntrar);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                StringBuilder msg = new StringBuilder();

                if (uwebservice.getText().toString().length() == 0) {
                    msg.append("digite o webservice \n");
                }

                if (usuario.getText().toString().length() == 0) {
                    msg.append("digite o usuario \n");
                }
                if (senha.getText().toString().length() == 0) {
                    msg.append("digite a senha \n");
                }

                if (msg.toString().length() > 0) {
                    alert(msg.toString());
                } else {
                    webservice = uwebservice.getText().toString().replaceAll("http://", "");
                    progress();
                    Log.i("user", usuario.getText().toString());
                    Transacao transacao = new Transacao(usuario.getText().toString(), senha.getText().toString());
                    transacao.execute();
                }


            }

        });

    }

    private void alert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alerta");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public void addListenerOnButton() {

        final Context context = this;

        button = (Button) findViewById(R.id.botaoCadastro);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (uwebservice.getText().toString().length() > 0) {
                    webservice = uwebservice.getText().toString().replaceAll("http://", "");
                    Intent intent = new Intent(context, CadastroActivity.class);
                    startActivity(intent);
                }


            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class Transacao extends AsyncTask<String, Void, Void> {

        private String nome, senha;
        String resultado;
        boolean ok;

        public Transacao(String nome, String senha) {
            super();
            this.nome = nome;
            this.senha = senha;
            Log.i("user2", this.nome);
            ok = false;
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i(nome, "");
            resultado = WebService.entrar(nome, senha);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();
            String msg = "";
            if (resultado.equals("3")) {
                msg = "usuario/senha incorretos";
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG)
                        .show();
            } else if (resultado.equals("2")) {
                msg = "usuario bloqueado";
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG)
                        .show();
            } else {
                final  Context context =MainActivity.this;
                Intent intent = new Intent(context, MenuActivity.class);
                startActivity(intent);

            }


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

    }


    public void progress() {

        progress.setMessage("Processando ,Aguarde ... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        final int totalProgressTime = 100;

        final Thread t = new Thread() {

            @Override
            public void run() {

                int jumpTime = 0;
                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();
    }
}
