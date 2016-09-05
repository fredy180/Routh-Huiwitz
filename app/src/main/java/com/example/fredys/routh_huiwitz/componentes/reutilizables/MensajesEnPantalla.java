package com.example.fredys.routh_huiwitz.componentes.reutilizables;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Fredys on 26/06/2016.
 */
public class MensajesEnPantalla {
    public static void mensajeToast(Context context, String texto){
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, texto, duration);
        toast.show();

    }
}
