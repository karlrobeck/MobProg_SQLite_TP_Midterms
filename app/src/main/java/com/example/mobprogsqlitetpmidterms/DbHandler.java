package com.example.mobprogsqlitetpmidterms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
public class DbHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "Filmarchive";

    public DbHandler(Context context) {
        super(context, DbHandler.DB_NAME, null, 1);
    }

    public static class ShowTbl {

        public static class TableInfo {
            public static final String tableName = "ShowTbl";
            public static final String movieID = "MovieID";
            public static final String movieName = "MovieName";
            public static final String movieDescription = "MovieDescription";
            public static final String movieDirector = "MovieDirector";
            public static final String releaseDate = "ReleaseDate";
        }
        public Integer movieID;
        public String movieName;
        public String movieDescription;
        public String movieDirector;
        public String releaseDate;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + ShowTbl.TableInfo.tableName + " ("
                + ShowTbl.TableInfo.movieID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShowTbl.TableInfo.movieName + " TEXT, "
                + ShowTbl.TableInfo.movieDescription + " TEXT, "
                + ShowTbl.TableInfo.movieDirector + " TEXT, "
                + ShowTbl.TableInfo.releaseDate + " TEXT "
                +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + ShowTbl.TableInfo.tableName);
            onCreate(db);
        }
    }

    // helper functions
    public SQLiteDatabase ConnectDb() {
        return this.getWritableDatabase();
    }

    // setters and getters class for listing movies and getting specific movie row
    public void addMovie(String name,String description, String director, String releaseDate) {
        SQLiteDatabase dbConnection = this.ConnectDb();
        ContentValues sqlData = new ContentValues();
        sqlData.put(ShowTbl.TableInfo.movieName,name);
        sqlData.put(ShowTbl.TableInfo.movieDescription,description);
        sqlData.put(ShowTbl.TableInfo.movieDirector,director);
        sqlData.put(ShowTbl.TableInfo.releaseDate,releaseDate);
        dbConnection.insert(ShowTbl.TableInfo.tableName,null,sqlData);
    }

    public List<ShowTbl> showMovies() {
        SQLiteDatabase dbConnection = this.ConnectDb();
        Cursor c = dbConnection.rawQuery("SELECT * FROM " + ShowTbl.TableInfo.tableName + ";",new String[] {});
        List<ShowTbl> movieList = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                ShowTbl sqlQueryOutput = new ShowTbl();
                sqlQueryOutput.movieID = c.getInt(0);
                sqlQueryOutput.movieName = c.getString(1);
                sqlQueryOutput.movieDescription = c.getString(2);
                sqlQueryOutput.movieDirector = c.getString(3);
                sqlQueryOutput.releaseDate = c.getString(4);
                movieList.add(sqlQueryOutput);
            }
        } catch (SQLException e) {
            throw new SQLException("Error occurred while retrieving data from the database", e);
        } finally {
            c.close(); // Always close the cursor after use
        }
        if (movieList.isEmpty()) {
            throw new SQLException("No data found in the result set");
        }
        return movieList;
    }

    public ShowTbl showMovie(String name) {
        SQLiteDatabase dbConnection = this.ConnectDb();
        Cursor c = dbConnection.rawQuery("SELECT * FROM " + ShowTbl.TableInfo.tableName + " WHERE " + ShowTbl.TableInfo.tableName + "." + ShowTbl.TableInfo.movieName + " =?;",new String[] {name});
        ShowTbl sqlQueryOutput = new ShowTbl();
        try {
            if (c.moveToLast()) {
                sqlQueryOutput.movieID = c.getInt(0);
                sqlQueryOutput.movieName = c.getString(1);
                sqlQueryOutput.movieDescription = c.getString(2);
                sqlQueryOutput.movieDirector = c.getString(3);
                sqlQueryOutput.releaseDate = c.getString(4);
            } else{
                throw new SQLException("No data found in the result set");
            }
        } catch (SQLException e) {
            throw new SQLException("Error occurred while retrieving data from the database", e);
        } finally {
            c.close(); // Always close the cursor after use
        }
        if (sqlQueryOutput.movieID.equals(0)) {
            throw new SQLException("No data found in the result set");
        }
        return sqlQueryOutput;
    }

}
