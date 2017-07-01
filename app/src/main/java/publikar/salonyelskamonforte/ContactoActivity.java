package publikar.salonyelskamonforte;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactoActivity extends AppCompatActivity {

    public static String FACEBOOK_URL = "https://www.facebook.com/ilkayelskamonfortesalontanlum";
    public static String FACEBOOK_PAGE_ID = "ilkayelskamonfortesalontanlum";

TextView txtfacebook,txtwhatsapp,txtdireccion,txtemail;
    ImageView imgface,imgwhatsapp,imgdireccion,imgemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        txtfacebook=(TextView)findViewById(R.id.txtface);
        txtwhatsapp=(TextView)findViewById(R.id.txttel);
        txtdireccion=(TextView)findViewById(R.id.txtdireccicon);
        txtemail=(TextView)findViewById(R.id.txtemail);

        imgface=(ImageView)findViewById(R.id.faceicon);
        imgwhatsapp=(ImageView)findViewById(R.id.whatsappicon);
        imgdireccion=(ImageView)findViewById(R.id.direccicon);
        imgemail=(ImageView)findViewById(R.id.emailicon);


        txtfacebook.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.textsize));
        txtwhatsapp.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.textsize));
        txtdireccion.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.textsize));
        txtemail.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.textsize));





    }


    public void abrirface(View v)
    {

        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }

    public void abrirwhatsapp(View v)
    {
        //Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
        //startActivity(launchIntent);

        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("content://com.android.contacts/data/9991010967"));
        i.setPackage("com.whatsapp");           // so that only Whatsapp reacts and not the chooser
        startActivity(i);
    }

    private String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
}
