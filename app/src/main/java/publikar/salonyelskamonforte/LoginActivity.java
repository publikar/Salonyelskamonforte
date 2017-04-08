package publikar.salonyelskamonforte;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Objetos.Clientes;
import WebService.RequestMethod;
import WebService.RestClient;
import WebService.WebUrl;

public class LoginActivity extends AppCompatActivity {

    Button btnok;
    EditText editEmail;
    EditText editPass;
    RestClient restClient;
    Boolean existe=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPass = (EditText) findViewById(R.id.editPass);

        btnok = (Button) findViewById(R.id.buttonEnter);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(editEmail.getText().toString().matches("") ||
                        editPass.getText().toString().matches(""))) {
                    //Loguearse
                    request();
                    ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Consultando, por favor espere...");
                    LoginTask loginTask =
                            new LoginTask(progressDialog, LoginActivity.this);
                    loginTask.execute();

                }else
                {
                    Toast.makeText(LoginActivity.this,"Ingrese usuario y contraseña",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

        //  Intent intent = new Intent(this, MenugralActivity.class);
    }


    private void request() {
        restClient = new RestClient(WebUrl.webUrl + "login.php");
        restClient.clearAddHeader();
        restClient.clearAddParam();
        restClient.AddParam("email", editEmail.getText().toString());
        restClient.AddParam("password", editPass.getText().toString());


    }

    private void login() {
        try {
            Clientes clientes;
            restClient.Execute(RequestMethod.POST);
            JSONArray json = new JSONArray(restClient.getResponse());
            if (!json.isNull(0)) {
                try {
                    for (int i = 0; i < json.length(); i++) {
                        existe=true;
                        JSONObject job = null;
                        clientes = new Clientes();
                        job = json.getJSONObject(i);
                        clientes.setIdclientes(job.getInt("idclientes"));
                        clientes.setNombre(job.getString("email"));
                        clientes.setPassword(job.getString("password"));
                        clientes.setApellidos(job.getString("apellidos"));
                        clientes.setCumpleaños(job.getString("cumple"));
                        clientes.setMovil(job.getString("movil"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{existe=false;}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class LoginTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        private Context context;

        public LoginTask(ProgressDialog progressDialog, Context context) {
            this.context = context;
            this.progressDialog = progressDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            login();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if(existe) {
              //  existe=false;
                Intent intent = new Intent(LoginActivity.this, MenugralActivity.class);
                startActivity(intent);
            }else
            {
                Toast.makeText(LoginActivity.this,"Este usuario no existe",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
}
