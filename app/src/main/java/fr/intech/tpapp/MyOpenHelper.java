package fr.intech.tpapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "quizzdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // Sera appelé une et une seule fois au lancement de l'application - adb uninstall fr.intech.tpapp lors d'un changement (ou onUpgrade)
        db.execSQL("CREATE TABLE game(place_p1 PRIMARY KEY, place_p2 INTEGER, score_p1 INTEGER, score_p2 INTEGER)");
        db.execSQL("INSERT INTO game(place_p1, place_p2, score_p1, score_p2) VALUES(1, 0, 0, 0)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
