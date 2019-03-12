package fr.intech.tpapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState); //Obligatoire pour que l'application fonctionne
            setContentView(R.layout.activity_main); //Utilise ce fichier-là, veut dire : prends ce XML et plaque-le dans activity -- R.id.X pour accéder aux identifiants créés

            Button animesButton = findViewById(R.id.animes);
            Button gamesButton = findViewById(R.id.games);

            animesButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Quizz.class);
                    intent.putExtra("id", "animes");
                    startActivity(intent);
                }
            });

            gamesButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Quizz.class);
                    intent.putExtra("id", "video_games");
                    startActivity(intent);
                }
            });
        }
}
