package publikar.salonyelskamonforte;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenugralActivity extends AppCompatActivity {

    Button btnofertas;
    Button btnpuntos;
    Button btncurso;
    Button btnllegar;
    Button btncontacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menugral);

        btnofertas = (Button) findViewById(R.id.buttonofertas);
        btnofertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenugralActivity.this, OfertasActivity.class);
                startActivity(intent);


            }
        });


            btnpuntos = (Button) findViewById(R.id.buttonPuntos);
            btnpuntos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLog = new Intent(MenugralActivity.this, FrecuenteActivity.class);
                    startActivity(intentLog);


                }
            });



            btncurso = (Button) findViewById(R.id.buttonCursos);
            btncurso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLog = new Intent(MenugralActivity.this, CursoActivity.class);
                    startActivity(intentLog);


                }
            });


            btnllegar = (Button) findViewById(R.id.buttonLlegar);
            btnllegar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 //Intent intentLog = new Intent(MenugralActivity.this, MapsActivity.class);
                  //startActivity(intentLog);
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=21.0025435,-89.6312312");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);


                }
            });

            btncontacto = (Button) findViewById(R.id.buttonContacto);
            btncontacto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLog = new Intent(MenugralActivity.this, ContactoActivity.class);
                    startActivity(intentLog);


                }
            });





    }
}