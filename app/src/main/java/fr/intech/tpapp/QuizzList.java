package fr.intech.tpapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class QuizzList extends Activity {

    public String gameType ;
    private Categories list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Obligatoire pour que l'application fonctionne
        setContentView(R.layout.activity_quizzlist); //Utilise ce fichier-là, veut dire : prends ce XML et plaque-le dans activity -- R.id.X pour accéder aux identifiants créés


        Bundle extra = getIntent().getExtras();
        gameType = extra.getString("id");

        ObjectMapper obj = new ObjectMapper();
        obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream in = getResources().openRawResource(R.raw.categorie);
        try {
            list = obj.readValue(in, Categories.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Categorie> catList = list.getList();
        ListView list = findViewById(R.id.list);
        final String[] stringList = new String[catList.size()];
        String[] nameList = new String[catList.size()];

        int i = 0;
        for  (final Categorie s : catList){
               stringList[i] = s.getJson();
               nameList[i] = s.getName();
               i++;
        }

        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuizzList.this, Quizz.class);
                Bundle extras = new Bundle();
                extras.putString("id",stringList[position]);
                extras.putString("gameType", gameType);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        Button random = findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizzList.this, Quizz.class);
                Bundle extras = new Bundle();
                extras.putString("id", "0");
                extras.putString("gameType", gameType);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }
}
