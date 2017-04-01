package publikar.salonyelskamonforte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenugralActivity extends AppCompatActivity {

    Button btnofertas;
    Button btnpuntos;
    Button btncurso;
    Button btnllegar;
    Button btnexpe;
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

        Intent intent = new Intent(this, OfertasActivity.class);

        {
            btnpuntos = (Button) findViewById(R.id.buttonPuntos);
            btnpuntos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLog = new Intent(MenugralActivity.this, FrecuenteActivity.class);
                    startActivity(intentLog);


                }
            });

            Intent intentLog = new Intent(this, FrecuenteActivity.class);




        }

        {

            btncurso = (Button) findViewById(R.id.buttonCursos);
            btncurso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLog = new Intent(MenugralActivity.this, CursoActivity.class);
                    startActivity(intentLog);


                }
            });

            Intent intentLog = new Intent(this, CursoActivity.class);
        }

        {
            btnllegar = (Button) findViewById(R.id.buttonLlegar);
            btnllegar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLog = new Intent(MenugralActivity.this, LlegaActivity.class);
                    startActivity(intentLog);


                }
            });

            Intent intentLog = new Intent(this, LlegaActivity.class);

        }

        {
            btnexpe = (Button) findViewById(R.id.buttonExpediente);
            btnexpe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLog = new Intent(MenugralActivity.this, ExpedienteActivity.class);
                    startActivity(intentLog);


                }
            });

            Intent intentLog = new Intent(this, ExpedienteActivity.class);

        }



    }
}