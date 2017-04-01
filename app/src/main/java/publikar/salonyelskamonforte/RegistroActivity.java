package publikar.salonyelskamonforte;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Objetos.RegistroClienteTask;
import WebService.RequestMethod;
import WebService.RestClient;
import WebService.WebUrl;

public class RegistroActivity extends AppCompatActivity {

    EditText etxtnombre,etxtapellidos,etxttelefono,etxtemail,etxtpassword;
    TextView txtcumple;
    Button btnenviar;
  public static  int respuesta;
static RestClient restClient;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        etxtnombre=(EditText)findViewById(R.id.txtNombre);
        etxtapellidos=(EditText)findViewById(R.id.editApellido);
        etxttelefono=(EditText)findViewById(R.id.editMovil);
        etxtemail=(EditText)findViewById(R.id.editEmail);
        etxtpassword=(EditText)findViewById(R.id.editPassNum);
        btnenviar=(Button)findViewById(R.id.buttonENVIAR);
        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validar campos que no estén vacíos
                if (!(etxtnombre.getText().toString().matches("") || etxtapellidos.getText().toString().matches("")
                        || etxttelefono.getText().toString().matches("") || etxtemail.getText().toString().matches("")
                        || etxtpassword.getText().toString().matches("") ||
                        txtcumple.toString().matches("Fecha de Cumpleaños"))) {

                    request();
                    ProgressDialog progressDialog = new ProgressDialog(RegistroActivity.this);
                    progressDialog.setMessage("Enviando datos, por favor espere...");
                    RegistroClienteTask registroClienteTask =
                            new RegistroClienteTask(progressDialog, RegistroActivity.this);
                }
                else
                {
                    Toast.makeText(RegistroActivity.this,"Llene todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtcumple=(TextView)findViewById(R.id.textCumpleaños);
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

    private void actualizarTextView()
    {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Toast.makeText(RegistroActivity.this,sdf.format(myCalendar.getTime()),Toast.LENGTH_SHORT).show();
        txtcumple.setText(sdf.format(myCalendar.getTime()));
    }

    private void request()
    {
        restClient=new RestClient(WebUrl.webUrl+"registro.php");
        restClient.clearAddHeader();
        restClient.clearAddParam();
        restClient.AddParam("nombre",etxtnombre.getText().toString());
        restClient.AddParam("apellidos",etxtapellidos.getText().toString());
        restClient.AddParam("movil",etxttelefono.getText().toString());
        restClient.AddParam("email",etxtemail.getText().toString());
        restClient.AddParam("cumpleaños",txtcumple.getText().toString());
        restClient.AddParam("password",etxtpassword.getText().toString());



    }
    public static void enviarRegistro()
    {

        try {
            restClient.Execute(RequestMethod.POST);
            respuesta=restClient.getResponseCode();
            Log.d("Respuesta",Integer.toString(respuesta));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
