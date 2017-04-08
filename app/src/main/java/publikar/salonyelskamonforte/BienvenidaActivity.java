package publikar.salonyelskamonforte;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BienvenidaActivity extends AppCompatActivity {

    Button btnregistrar;
    Button btnlogin;
    Button btnllamar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        btnllamar = (Button) findViewById(R.id.buttonLlama);
        btnregistrar = (Button) findViewById(R.id.btnInscribete);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BienvenidaActivity.this, RegistroActivity.class);
                startActivity(intent);


            }
        });

        btnllamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
requestpermission();
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
    }

          //  Intent intentLog = new Intent(this, LoginActivity.class);




        //}

        private void requestpermission() {

            ActivityCompat.requestPermissions(BienvenidaActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    0);
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:999811007"));
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startActivity(intent);
            }

        }
    }
}
