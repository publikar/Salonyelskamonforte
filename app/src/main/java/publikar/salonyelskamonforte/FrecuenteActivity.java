package publikar.salonyelskamonforte;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONArray;

import WebService.RequestMethod;
import WebService.RestClient;
import WebService.WebUrl;

public class FrecuenteActivity extends AppCompatActivity {

    CheckBox chk1,chk2,chk3,chk4,chk5,chk6;
    RestClient restClient;
    Boolean passwordok=false,check1=false,check2=false,check3=false,check4=false,check5=false,
    check6=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frecuente);
        chk1=(CheckBox)findViewById(R.id.checkBox1);
        chk2=(CheckBox)findViewById(R.id.checkBox2);
        chk3=(CheckBox)findViewById(R.id.checkBox3);
        chk4=(CheckBox)findViewById(R.id.checkBox4);
        chk5=(CheckBox)findViewById(R.id.checkBox5);
        chk6=(CheckBox)findViewById(R.id.checkBox6);

        chk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check1=true;

                showInputDialog();


            }
        });
        chk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check2=true;
                showInputDialog();
            }
        });
        chk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check3=true;
                showInputDialog();
            }
        });

        chk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check4=true;

                showInputDialog();


            }
        });
        chk5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check5=true;
                showInputDialog();
            }
        });
        chk6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check6=true;
                showInputDialog();
            }
        });


    }





    protected void showInputDialog() {


        LayoutInflater layoutInflater = LayoutInflater.from(FrecuenteActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FrecuenteActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText etxtpassword = (EditText) promptView.findViewById(R.id.editText);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      //Validar admin

                        if(!etxtpassword.getText().toString().matches("")) {
                            restClient = new RestClient(WebUrl.webUrl + "loginadmin.php");
                            restClient.clearAddHeader();
                            restClient.clearAddParam();
                            restClient.AddParam("password", etxtpassword.getText().toString());

                            ProgressDialog progressDialog = new ProgressDialog(FrecuenteActivity.this);
                            progressDialog.setMessage("Consultando, por favor espere...");
                            ConsultarPassTask consultarPassTask =
                                    new ConsultarPassTask(progressDialog, FrecuenteActivity.this);
                            consultarPassTask.execute();
                        }else
                        {
                            dialog.cancel();
                        }

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public boolean consultapassword() {

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
    public class ConsultarPassTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        private Context context;

        public ConsultarPassTask(ProgressDialog progressDialog, Context context) {
            this.context = context;
            this.progressDialog = progressDialog;
        }

        @Override
        protected Void doInBackground(Void... params) {
if(consultapassword())
{
    passwordok=true;
}else{passwordok=false;}

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(passwordok)
            {
                if(check1)
            {

                check1=false;
            }
                if(check2)
                {

                    check2=false;
                }
                if(check3)
                {

                    check3=false;
                }
                if(check4)
                {

                    check4=false;
                }
                if(check5)
                {

                    check5=false;
                }
                if(check6)
                {

                    check6=false;
                }


            }else
            {
                if(check1)
                {
                    chk1.setChecked(false);
                }
                if(check2)
                {

                    chk2.setChecked(false);
                }
                if(check3)
                {

                    chk3.setChecked(false);
                }
                if(check4)
                {

                    chk4.setChecked(false);
                }
                if(check5)
                {

                    chk5.setChecked(false);
                }
                if(check6)
                {

                    chk6.setChecked(false);
                }
            }
            progressDialog.dismiss();
        }
    }
}
