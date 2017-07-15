package publikar.salonyelskamonforte;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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


    private void requestpermission() {

        ActivityCompat.requestPermissions(ContactoActivity.this,
                new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS},
                0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0) {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    public void abrirwhatsapp(View v)
    {
        requestpermission();
String smsNumber="+5219991010967";
        if(contactExists(ContactoActivity.this,smsNumber)) {
            Uri uri = Uri.parse("smsto:" + smsNumber);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(i);
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            //startActivity(launchIntent);
        }else
        {
           addcontact(smsNumber);

        }

    }

    private void addcontact(String smsNumber)
    {
        ArrayList<ContentProviderOperation> operationList = new ArrayList<ContentProviderOperation>();
        operationList.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());


        operationList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, "Yelska Monforte Salon")

                .build());

        operationList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, smsNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());


        try{
            ContentProviderResult[] results = getContentResolver().applyBatch(ContactsContract.AUTHORITY, operationList);
            Toast.makeText(ContactoActivity.this,"Contacto agregado",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public boolean contactExists(Activity _activity, String number) {
        if (number != null) {
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME };
            Cursor cur = _activity.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
            try {
                if (cur.moveToFirst()) {
                    return true;
                }
            } finally {
                if (cur != null)
                    cur.close();
            }
            return false;
        } else {
            return false;
        }
    }

    public void abrirubicacion(View v)
    { Uri gmmIntentUri = Uri.parse("google.navigation:q=21.0025435,-89.6312312");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);


    }

    public void abriremail(View v)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Informes");
        intent.setData(Uri.parse("mailto:yelskamonfortesalon@gmail.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);
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
