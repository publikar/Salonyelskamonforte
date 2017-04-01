package publikar.salonyelskamonforte;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    EditText etxtnombre,etxtapellidos,etxttelefono,etxtemail,etxtpassword;
    TextView txtcumple;
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
        txtcumple=(TextView)findViewById(R.id.textCumpleaños);
        txtcumple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(RegistroActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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
        txtcumple.setText(sdf.format(myCalendar.getTime()));
    }
}
