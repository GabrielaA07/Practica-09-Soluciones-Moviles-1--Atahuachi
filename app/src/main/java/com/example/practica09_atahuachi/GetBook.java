package com.example.practica09_atahuachi;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class GetBook extends AsyncTask<String,Void,String> {
    private WeakReference<TextView> mTextoTitulo;
    private WeakReference<TextView> mTextoAutor;
    private WeakReference<TextView> mTextoAnio;
    private WeakReference<TextView> mTextoDescripcion;
    private WeakReference<ImageView> imgLibro;

    GetBook(TextView tituloTexto, TextView autorTexto, TextView anioLibro, TextView descripcionLibro){
        this.mTextoTitulo = new WeakReference<>(tituloTexto);
        this.mTextoAutor = new WeakReference<>(autorTexto);
        this.mTextoAnio = new WeakReference<>(anioLibro);
        this.mTextoDescripcion = new WeakReference<>(descripcionLibro);
    }


    @Override
    protected String doInBackground(String... strings) {
        return UtilidadesRed.obtenerInformacionLibro(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemArray = jsonObject.getJSONArray("items");
            int i=0;
            String titulo = null;
            String autores = null;
            String anio = null;
            String descripcion = null;
            int foto = 0;
            MainActivity.libros.clear();

            for (i = 0; i < itemArray.length(); i++) {
                JSONObject libro = itemArray.getJSONObject(i);
                JSONObject volumenInfo = libro.getJSONObject("volumeInfo");
                try {
                    titulo = volumenInfo.getString("title");
                    autores = volumenInfo.getString("authors");
                    anio = volumenInfo.getString("publishedDate");
                    descripcion = volumenInfo.getString("description");
                    MainActivity.libros.add(new Book(titulo,autores,anio,descripcion));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(titulo != null && autores != null) {
            }else {
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
