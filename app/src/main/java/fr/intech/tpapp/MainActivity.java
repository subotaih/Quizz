package fr.intech.tpapp;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private PendingIntent pendingIntent;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState); //Obligatoire pour que l'application fonctionne
            setContentView(R.layout.activity_main); //Utilise ce fichier-là, veut dire : prends ce XML et plaque-le dans activity -- R.id.X pour accéder aux identifiants créés

            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent , 0);
            AlarmManager alrm = (AlarmManager) getSystemService(this.ALARM_SERVICE);
            alrm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000000, pendingIntent);

            Button solo = findViewById(R.id.solo);
            Button twoPlayers = findViewById(R.id.vs);

            solo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, QuizzList.class);
                    intent.putExtra("id", "1");
                    startActivity(intent);
                }
            });

            twoPlayers.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, QuizzList.class);
                    intent.putExtra("id", "2");
                    startActivity(intent);
                }
            });

        }

}
