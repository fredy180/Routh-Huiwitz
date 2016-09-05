package com.example.fredys.routh_huiwitz.LogicaNegocio;

import android.content.Context;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.fredys.routh_huiwitz.componentes.reutilizables.MensajesEnPantalla;

import java.math.BigDecimal;
import java.util.StringTokenizer;

/**
 * Created by Fredys on 26/06/2016.
 */
public class Routh {
    private static double polinomio[]=null;
    private static int fila; // esta tomaño del grado del polinomio+1, en la funcion obtenerPolinomio
    private static int columna;
    public static double Epsilon = 0.0001D;//ε
    public static String matriz[][];
    private static TextView tvInforme;
    private static   ConfigurarTabla configurarTabla;
    public static void iniciarRouth(Context context,String candenaPolinimio,TableLayout tablaDatos,TextView tvInformes){
        tvInforme = tvInformes;
        if(obtenerPolinomio(candenaPolinimio,context,tablaDatos)) {
            obtenerFila1Fila2DeCoeficientes(context);
            configurarTabla = new ConfigurarTabla();
            configurarTabla.agregarCabeceras(tablaDatos, columna, context);
            calcularCoeficientes(context);
            configurarTabla.agregarFilas(tablaDatos, context, matriz);
            esInestable();
        }


    }
    public static void calcularCoeficientes(Context context){
        try {
            double an, an_1, an_2, an_3, coeficiente;
            boolean caso3;
            for (int fil = 2; fil < polinomio.length; fil++) {
                if (caso3 = esCaso3((fil - 1))) // se comprueba si es caso 3
                    deriva(fil - 2);

                an = Double.parseDouble(matriz[fil - 2][0]);
                an_1 = Double.parseDouble(matriz[fil - 1][0]);
                if (an_1==0D && !caso3) {// se comprueba si es caso 2
                    matriz[fil - 1][0] = "" + Epsilon;
                    an_1 = Epsilon;
                    Log.i("este es un msj", "entro al epsilo " + Epsilon);
                }
                for (int j = 1; j < columna - 1; j++) {
                    if (matriz[fil - 2][j] != null) {
                        an_2 = Double.parseDouble(matriz[fil - 2][j]);
                        if (matriz[fil - 1][j] == null)
                            an_3 = 0D;// <--------
                        else
                            an_3 = Double.parseDouble(matriz[fil - 1][j]);
                        //calculamos el coeficinte
                        coeficiente = (((an_1 * an_2) - (an * an_3))) / an_1;
                        if (coeficiente == -0D || coeficiente == -0.0D)
                            matriz[fil][j] = "0";
                        else
                            matriz[fil][j - 1] = "" + coeficiente;
                    } else
                        matriz[fil][j - 1] = null;
                }
            }
            matriz[fila - 1][0] = "" + polinomio[polinomio.length - 1];
            if (polinomio[polinomio.length - 1] == 00)
                matriz[fila - 1][0] = "" + Epsilon;
        }catch (Exception e){
            MensajesEnPantalla.mensajeToast(context,e.getLocalizedMessage());
        }

    }
    public static void obtenerFila1Fila2DeCoeficientes(Context context) {
        if(fila%2 ==0)
            columna = Math.round((fila) / 2);
        else
            columna = Math.round((fila) / 2) + 1;

        columna++;// se agrega una columna mas, esta siempre será null
        matriz = new String[fila][columna];

        if(fila%2 !=0)
            matriz[1][columna-2]="0";

        int pares = 0, impares = 0;
        for (int i = 0; i < polinomio.length; i++) {
            if (i % 2 == 0) {
                //obtenenemos los  valorese de la S^n pares
                matriz[0][pares]=""+ polinomio[i];
                pares++;
            } else {
                //obtenenemos los  valorese de la S^n impares
                matriz[1][impares]= ""+polinomio[i];
                impares++;
            }
        }
    }
    private static void deriva( int banderaFilaDerivar) {

        double n=((fila)-(banderaFilaDerivar+1));// exponente del polinomio a derivar...
        double valorDerivado;
        for (int i = 0; i < columna-1; i++) {
            if(matriz[banderaFilaDerivar][i]!=null) {
                valorDerivado =Double.parseDouble(matriz[banderaFilaDerivar][i]) * n;
                valorDerivado = Math.abs(valorDerivado);
                matriz[banderaFilaDerivar + 1][i] =""+ valorDerivado;
                n = n - 2;
            }else break;
        }
    }
    private static boolean esCaso3(int fil) {
        /*metodo que confirma si es necesario aplicar el caso 3*/
        boolean esCaeo3 = true;
        int cantidad=0;
        for (int i = 0; i < columna-1; i++) {
            if(matriz[fil][i] != null) {
                if (Double.parseDouble(matriz[fil][i])!=0D ) {
                    return false;
                }else cantidad++;
            }
        }
        if(cantidad<=1)
            esCaeo3=false;
        return esCaeo3;
    }
    public static void esInestable() {
        int cambio = 0;
        int menos = 0, mas = 0;
        int auX = 0;
        int races = 0;
        for (int i = 0; i < fila; i++) {

            if (Double.parseDouble(matriz[i][0] )> 0D) {
                mas++;
                auX = 0;

            }
            if (Double.parseDouble(matriz[i][0]) < 0D) {
                menos++;
                auX = 1;
            }

            if (auX != cambio) {
                races++;
            }
            cambio = auX;
        }
        if (menos > 0) {
           // jlbInfo.setText("El Sistema es INESTABLE ");
            tvInforme.setText("INESTABLE >> Hay " + races + " cambios de signo en los coeficientes "
                    + "de la primera columna.  Esto significa que existen " + races + " raíces"
                    + " con partes reales positivas.\n");
        } else {
            //jlbInfo.setText("El Sistema es ESTABLE ");
            tvInforme.setText("ESTABLE>> El sistema Es completamente estable porque"
                    + " no tiene ningún cambio de signo en los coeficintes de la "
                    + "primera columna.\n ");

        }
    }
    /**
     * este metodo resibe el polinomio en una cadena String y lo separa por cada coma(2,4,5)
     * @param candenaPolinimio cadena donde viene el polinomio a separar
     * @return retorna un vector con los valores del polinomio
     */
    public static boolean obtenerPolinomio(String candenaPolinimio, Context context,TableLayout tablaDatos){
        StringTokenizer tokens ;
        String numero="";
        boolean correcto = true;

        try {
            //separamos los números, pero antes se eliminan los espacion vacio con replace(" ","")
            tokens = new StringTokenizer(candenaPolinimio.replace(" ", ""), ",");
            polinomio = new double[tokens.countTokens()];
            int cantidadTokens = tokens.countTokens();
            if(cantidadTokens > 1){
                fila = cantidadTokens;// los toque son los numeros del polinomion
                for (int i = 0; i < cantidadTokens; i++) {
                  numero = tokens.nextToken();
                  polinomio[i] = Double.parseDouble(numero);
                }
            }
            else {
               correcto = false;

            }
        }catch (Exception e){
            MensajesEnPantalla.mensajeToast(context,"" +
                    "Dato '"+numero+" ' incorrecto, corrijalos e intente de nuevo");
            correcto = false;
        }
        return correcto;
    }
}
