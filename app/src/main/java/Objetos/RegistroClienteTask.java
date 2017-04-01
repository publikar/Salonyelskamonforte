package Objetos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import publikar.salonyelskamonforte.RegistroActivity;

/**
 * Created by A on 01/04/2017.
 */

public class RegistroClienteTask extends AsyncTask<Void,Void,Void>{
private ProgressDialog progressDialog;
    private Context context;
    public RegistroClienteTask(ProgressDialog progressDialog,Context context)
    {
        this.context=context;
this.progressDialog=progressDialog;
    }
    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        RegistroActivity.enviarRegistro();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        if(RegistroActivity.respuesta==200 || RegistroActivity.respuesta==201) {
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context, "Ocurrió un error.Intente más tarde", Toast.LENGTH_SHORT).show();
        }
    }
}
