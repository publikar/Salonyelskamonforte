package publikar.salonyelskamonforte;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Objetos.Clientes;
import Util.GMailSender;
import WebService.RequestMethod;
import WebService.RestClient;
import WebService.WebUrl;

public class RegistroActivity extends AppCompatActivity {
    Boolean existemail = false;
    EditText etxtnombre, etxtapellidos, etxttelefono, etxtemail, etxtpassword;
    String nombre, apellidos, telefono, email, cumple, contrasenia;
    TextView txtcumple;
    Button btnenviar;
    public int respuesta;
    RestClient restClient;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        etxtnombre = (EditText) findViewById(R.id.txtNombre);
        etxtapellidos = (EditText) findViewById(R.id.editApellido);
        etxttelefono = (EditText) findViewById(R.id.editMovil);
        etxtemail = (EditText) findViewById(R.id.editEmail);
        etxtpassword = (EditText) findViewById(R.id.editPassNum);
        btnenviar = (Button) findViewById(R.id.buttonENVIAR);
        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validar campos que no estén vacíos
                if (!(etxtnombre.getText().toString().matches("") || etxtapellidos.getText().toString().matches("")
                        || etxttelefono.getText().toString().matches("") || etxtemail.getText().toString().matches("")
                        || etxtpassword.getText().toString().matches("") ||
                        txtcumple.getText().toString().matches("Fecha de Cumpleaños"))) {
                    nombre = etxtnombre.getText().toString();
                    apellidos = etxtapellidos.getText().toString();
                    telefono = etxttelefono.getText().toString();
                    email = etxtemail.getText().toString();
                    cumple = txtcumple.getText().toString();
                    contrasenia = etxtpassword.getText().toString();

//Registro de clientes
                    ProgressDialog progressDialog = new ProgressDialog(RegistroActivity.this);
                    progressDialog.setMessage("Enviando datos, por favor espere...");
                    RegistroClienteTask registroClienteTask =
                            new RegistroClienteTask(progressDialog, RegistroActivity.this);
                    registroClienteTask.execute();



                    //   limpiarCampos();
                } else {
                    Toast.makeText(RegistroActivity.this, "Llene todos los campos", Toast.LENGTH_SHORT).show();

                }

            }
        });
        txtcumple = (TextView) findViewById(R.id.textCumpleaños);
        txtcumple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(RegistroActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                actualizarTextView();
            }

        };
    }

    private void limpiarCampos() {
        //Limpiar todos los controles
        etxtnombre.setText("");
        etxtapellidos.setText("");
        etxtemail.setText("");
        etxttelefono.setText("");
        etxtpassword.setText("");
        txtcumple.setText("Fecha de Cumpleaños");
    }

    private void actualizarTextView() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Toast.makeText(RegistroActivity.this, sdf.format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
        txtcumple.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean emailexist() {
        restClient = new RestClient(WebUrl.webUrl + "existe_email.php");
        restClient.clearAddHeader();
        restClient.clearAddParam();
        restClient.AddParam("email", etxtemail.getText().toString());
        try {
            restClient.Execute(RequestMethod.POST);
            JSONArray json = new JSONArray(restClient.getResponse());
            if (!json.isNull(0)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    public void enviarRegistro() {

        try {
            restClient = new RestClient(WebUrl.webUrl + "registro.php");
            restClient.clearAddHeader();
            restClient.clearAddParam();
            restClient.AddParam("nombre", etxtnombre.getText().toString());
            restClient.AddParam("apellidos", etxtapellidos.getText().toString());
            restClient.AddParam("movil", etxttelefono.getText().toString());
            restClient.AddParam("email", etxtemail.getText().toString());
            restClient.AddParam("cumpleaños", txtcumple.getText().toString());
            restClient.AddParam("password", etxtpassword.getText().toString());
            restClient.Execute(RequestMethod.POST);
            respuesta = restClient.getResponseCode();
            Log.d("Respuesta", Integer.toString(respuesta));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public class RegistroClienteTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        private Context context;

        public RegistroClienteTask(ProgressDialog progressDialog, Context context) {
            this.context = context;
            this.progressDialog = progressDialog;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (!emailexist()) {
                enviarRegistro();
                try {
                    GMailSender sender = new GMailSender("yelskamonfortesalon@gmail.com", "MonfortE2016AvilA");
                    sender.sendMail("Registro exitoso",
                            "Gracias por registrarse en la app Yelska Monforte Salon",
                            "yelskamonfortesalon@gmail.com", email
                    );
                    sender.sendMail("Nuevo usuario registrado",
                            "Se registró el siguiente usuario:\n"+
                                    "Nombre:"+nombre+"\n"+
                                    "Apellido:"+apellidos+"\n"+
                                    "Móvil:"+telefono+"\n"+
                                    "Email:"+email+"\n"+
                                    "Fecha de cumpleaños:"+cumple,"yelskamonfortesalon@gmail.com","yelskamonfortesalon@gmail.com"
                    );
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }

                existemail = false;
            } else {
                existemail = true;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();

           /* String mailto = "mailto:" +etxtemail.getText().toString()+
                    "?cc=" + "yelskamonfortesalon@gmail.com" +
                    "&subject=" + Uri.encode("Se ha suscrito a Yelska Monforte Salon") +
                    "&body=" + Uri.encode("Gracias por suscribirse");*/
            if (!existemail) {
                if (respuesta == 200 || respuesta == 201) {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    //enviar correo electrónico

               /* Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(mailto));
                startActivity(intent);*/

                    Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(context, "Ocurrió un error.Intente más tarde", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegistroActivity.this, "Este email ya está registrado", Toast.LENGTH_SHORT).show();


            }
        }
    }






    }

