package fr.intech.tpapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "madb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // Sera appelé une et une seule fois au lancement de l'application - adb uninstall fr.intech.tpapp lors d'un changement (ou onUpgrade)
        db.execSQL("CREATE TABLE game(place INTEGER PRIMARY KEY, score INTEGER)");
        db.execSQL("INSERT INTO game(place, score) VALUES(1, 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
