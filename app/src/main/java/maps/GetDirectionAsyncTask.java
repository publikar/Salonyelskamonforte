package maps;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Map;

import publikar.salonyelskamonforte.MapsActivity;

/**
 * Created by A on 21/04/2016.
 */
public class GetDirectionAsyncTask extends AsyncTask<Map<String,String>,Object,
        ArrayList<LatLng>>{

    public static final String USER_CURRENT_LAT="user_current_lat";
    public static final String USER_CURRENT_LONG="user_current_long";
    public static final String DESTINATION_LAT="destination_lat";
    public static final String DESTINATION_LONG="destination_long";
    public static final String DIRECTIONS_MODE="directions_mode";

    private MapsActivity activity;
    private Exception exception;
    private ProgressDialog progressDialog;


    public GetDirectionAsyncTask(MapsActivity activity)
    {super();
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage("Calculando direcciones");
        progressDialog.show();
    }

    @Override
    protected ArrayList<LatLng> doInBackground(Map<String, String>... params) {
       Map<String,String> paramMap=params[0];
        try
        {
            LatLng fromPosition=new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)),
                    Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition=new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)),
                    Double.valueOf(paramMap.get(DESTINATION_LONG)));
            GMapV2Direction md=new GMapV2Direction();
            Document doc=md.getDocument(fromPosition,toPosition,paramMap.get(DIRECTIONS_MODE));

            ArrayList<LatLng> directionPoints=md.getDirection(doc);
            return directionPoints;
        }
        catch(Exception e)
        {
            exception =e;
            processException(e);
            return  null;

        }

    }

    @Override
    protected void onPostExecute(ArrayList<LatLng> latLngs) {
        progressDialog.dismiss();
        if(exception==null)
        {
           // activity.handleGetDirectionsResult(latLngs);
        }
        else
        {
            processException(exception);
        }
    }

    private void processException(Exception e)
    {
        Toast.makeText(activity,"Error recibiendo datos: "+e,Toast.LENGTH_LONG).show();
    }
}
