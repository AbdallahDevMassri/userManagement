package com.example.usermanagementapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.usermanagementapp.model.Category;
import com.example.usermanagementapp.model.User;


@Database(entities = {User.class, Category.class}, version = 1, exportSchema = false)

public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();


    private static volatile UserDatabase INSTANCE;

    public static UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UserDatabase.class, "my_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
