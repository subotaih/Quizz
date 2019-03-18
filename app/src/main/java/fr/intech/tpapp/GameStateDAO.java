package fr.intech.tpapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GameStateDAO {

    private SQLiteDatabase db;

    public GameStateDAO(SQLiteDatabase Db) {
        db = Db;
    }

    public GameState get() {
        GameState gameState = new GameState();
        Cursor cursor = db.query("game", new String[] {"score_p1, score_p2, place_p1, place_p2"}, null, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int score_p1 = cursor.getInt((cursor.getColumnIndex("score_p1")));
                gameState.setScore_p1(score_p1);
                int score_p2 = cursor.getInt((cursor.getColumnIndex("score_p2")));
                gameState.setScore_p2(score_p2);
                int place_p1 = cursor.getInt((cursor.getColumnIndex("place_p1")));
                gameState.setPlace_p1(place_p1);
                int place_p2 = cursor.getInt((cursor.getColumnIndex("place_p2")));
                gameState.setPlace_p2(place_p2);
            }
        } finally {
            cursor.close();
        }
        return gameState;
    }

    public void save(GameState gs) {
        ContentValues values = new ContentValues();
        values.put("place_p1", gs.getPlace_p1());
        values.put("place_p2", gs.getPlace_p2());
        values.put("score_p1", gs.getScore_p1());
        values.put("score_p2", gs.getScore_p2());
        db.update("game", values,null,null);
    }

    public void erase() {
        db.execSQL("DELETE FROM game");
    }

    public void insertNew() {
        db.execSQL("INSERT INTO game(id, place_p1, place_p2, score_p1, score_p2) VALUES(1, 0, 0, 0, 0)");
    }


}
