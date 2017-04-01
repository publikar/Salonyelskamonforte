package publikar.salonyelskamonforte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BienvenidaActivity extends AppCompatActivity {

    Button btnregistrar;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        btnregistrar = (Button) findViewById(R.id.btnInscribete);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BienvenidaActivity.this, RegistroActivity.class);
                startActivity(intent);


            }
        });

       //Intent intent = new Intent(this, RegistroActivity.class);

        //{
            btnlogin = (Button) findViewById(R.id.btnIngresa);
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLog = new Intent(BienvenidaActivity.this, LoginActivity.class);
                    startActivity(intentLog);


                }
            });

          //  Intent intentLog = new Intent(this, LoginActivity.class);




        //}



    }
}
