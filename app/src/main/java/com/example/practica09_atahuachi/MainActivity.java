package com.example.practica09_atahuachi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import es.dmoral.coloromatic.ColorOMaticDialog;
import es.dmoral.coloromatic.IndicatorMode;
import es.dmoral.coloromatic.OnColorSelectedListener;
import es.dmoral.coloromatic.colormode.ColorMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mInputLibro;

    private TextView Titulo;
    private TextView Autor;
    private TextView Anio;
    private TextView Descripcion;
    private ImageView Imagen;

    public static List<Book> libros;

    private void initializaData(){
        libros = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputLibro = (EditText)findViewById(R.id.insertBook);
        Titulo = (TextView)findViewById(R.id.Title);
        Autor = (TextView)findViewById(R.id.Author);
        Anio = (TextView)findViewById(R.id.Year);
        Descripcion = (TextView)findViewById(R.id.Description);
        Imagen = (ImageView) findViewById(R.id.ImageBook);

        initializaData();

        final RecyclerView rv = (RecyclerView)findViewById(R.id.rvBooks);
        rv.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(false);
        rv.setLayoutManager(linearLayoutManager);

        Adapter adaptador = new Adapter(libros,this);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Detail.class);
                intent.putExtra("titulo", libros.get(rv.getChildAdapterPosition(v)).Title);
                intent.putExtra("autor", libros.get(rv.getChildAdapterPosition(v)).Author);
                intent.putExtra("anio", libros.get(rv.getChildAdapterPosition(v)).Year);
                intent.putExtra("descripcion", libros.get(rv.getChildAdapterPosition(v)).Description);
                startActivity(intent);
            }
        });

        rv.setAdapter(adaptador);

    }

    public void SearchBook(View view) {
        if (mInputLibro.getText().toString().equals("")) {
            libros.clear();
        } else {
            String Busqueda = mInputLibro.getText().toString();
            new GetBook(Titulo, Autor, Anio, Descripcion).execute(Busqueda);
            super.onRestart();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            cargarColor();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarColor() {
        new ColorOMaticDialog.Builder()
                .initialColor(Color.BLACK)
                .colorMode(ColorMode.ARGB)
                .indicatorMode(IndicatorMode.HEX)
                .onColorSelected(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color){
                        SharedPreferences sharedPreferences =
                                MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor =
                                sharedPreferences.edit();
                        editor.putInt(getString(R.string.spColor_bar),color);
                        editor.commit();
                        cambiarColor(color,MainActivity.this);
                    }
                })
                .showColorIndicator(true)
                .create()
                .show(getSupportFragmentManager(), "ColorOMaticDialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        int color = sharedPreferences.getInt(getString(R.string.spColor_bar),0);
        if (color != 0){
            cambiarColor(color, this);
        }
    }

    public void cambiarColor(int color, Activity activity){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(color);
        window.setNavigationBarColor(color);
    }
}