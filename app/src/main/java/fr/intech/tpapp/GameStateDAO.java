package fr.intech.tpapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;


public class GameStateDAO {

    private SQLiteDatabase db;

    public GameStateDAO(SQLiteDatabase Db) {
        db = Db;
    }

    public GameState get(SQLiteDatabase db) {
        db.execSQL("SELECT score_p1, score_p2, place_p1, place_p2 FROM game");
        return new GameState(db);
    }

    public void save(GameState gs) {
        ContentValues values = new ContentValues();
        values.put("place_p1", gs.getPlace_p1());
        values.put("place_p2", gs.getPlace_p2());
        values.put("score_p1", gs.getPlace_p1());
        values.put("score_p1", gs.getPlace_p2());
        db.update("game", values,null,null);
    }

    public void delete(int id) {
        //db.delete(id)
    }
}
