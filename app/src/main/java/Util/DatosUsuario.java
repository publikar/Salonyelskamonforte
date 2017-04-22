package Util;

import android.content.Context;
import android.content.SharedPreferences;

import Objetos.Clientes;

/**
 * Created by A on 22/04/2017.
 */

public class DatosUsuario {


    private static final String PREFERENCE_NAME="user_preferences";
    private static final String USER_NAME="user_name";
    private static final String USER_LASTNAME="user_lastname";
    private static final String USER_EMAIL="user_email";
    private static final String USER_MOVIL="user_movil";
    private static final String USER_BIRTHDAY="user_birthday";
    private static final String USER_PASSWORD="user_password";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public DatosUsuario(Context ctx)
    {
        preferences=ctx.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        editor=preferences.edit();

    }

    public void setUserName(String username)
    {
        editor.putString(USER_NAME,username);
        editor.commit();

    }

    public String getUserName()
    {
        return preferences.getString(USER_NAME,"");
    }

    public void setUserLastname(String lastname)
    {
        editor.putString(USER_LASTNAME,lastname);
        editor.commit();
    }

    public String getUserLastname()
    {
        return preferences.getString(USER_LASTNAME,"");
    }

    public void setUserEmail(String email)
    {
        editor.putString(USER_EMAIL,email).commit();
    }

    public String getUserEmail()
    {
        return  preferences.getString(USER_EMAIL,"");
    }

    public void setUserMovil(String movil)
    {
        editor.putString(USER_MOVIL,movil).commit();
    }

    public String getUserMovil()
    {
        return preferences.getString(USER_MOVIL,"");
    }

    public void setUserBirthday(String birthday)
    {
        editor.putString(USER_BIRTHDAY,birthday).commit();
    }

    public String getUserBirthday()
    {
        return preferences.getString(USER_BIRTHDAY,"");
    }

    public void setUserPassword(String password)
    {
        editor.putString(USER_PASSWORD,password).commit();
    }

    public String getUserPassword()
    {
        return preferences.getString(USER_PASSWORD,"");
    }


    public void saveCliente(Clientes cliente)
    {
        setUserName(cliente.getNombre());
        setUserLastname(cliente.getApellidos());
        setUserEmail(cliente.getEmail());
        setUserBirthday(cliente.getCumpleaños());
        setUserMovil(cliente.getMovil());
        setUserPassword(cliente.getPassword());
    }

}
