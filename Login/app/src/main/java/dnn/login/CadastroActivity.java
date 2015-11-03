package dnn.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CadastroActivity extends ActionBarActivity {

    Button button;
    Button buttoncadastro;
    EditText usuario, email, senha;
    static ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        usuario = (EditText) findViewById(R.id.usuario);
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        addListenerOnButtonvoltar();
        addListenerOnButtoncadastro();
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
    }


    public void addListenerOnButtoncadastro() {

        final Context context = this;

        buttoncadastro = (Button) findViewById(R.id.botaoCadastro);

        buttoncadastro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                StringBuilder msg = new StringBuilder();

                if (usuario.getText().toString().length() == 0) {
                    msg.append("digite o usuario \n");
                }
                if (email.getText().toString().length() == 0) {
                    msg.append("digite o email \n");
                } else {
                    if (!isValidEmail(email.getText().toString().subSequence
                            (0, email.getText().toString().length() - 1))) {
                        msg.append("digite um email valido \n");

                    }
                }
                if (senha.getText().toString().length() == 0) {
                    msg.append("digite a senha \n");
                }

                if (msg.toString().length() > 0) {
                    alert(msg.toString());
                } else {
                    progress();
                    Log.i("user",usuario.getText().toString());
                    Transacao transacao = new Transacao(usuario.getText().toString(),senha.getText().toString(),email.getText().toString());
                    transacao.execute();

                }


            }

        });

    }


    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private  void alert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(CadastroActivity.this).create();
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


    public void addListenerOnButtonvoltar() {

        final Context context = this;

        button = (Button) findViewById(R.id.botaoVoltar);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

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

        private String nome,senha,email;
        String resultado;
        boolean ok;

        public Transacao(String nome,String senha,String email) {
            super();
            this.nome = nome;
            this.senha= senha;
            this.email = email;
            Log.i("user2", this.nome);
            ok = false;
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i(nome,"");
            resultado = WebService.caadastrar(nome,senha,email);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();

            if(resultado.equals("ok")){
              final  Context context = CadastroActivity.this;
                Intent intent = new Intent(context, SucessoActivity.class);
                startActivity(intent);
            }else{

                Toast.makeText(CadastroActivity.this, resultado, Toast.LENGTH_LONG)
                        .show();
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
