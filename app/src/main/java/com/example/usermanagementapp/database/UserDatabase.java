package com.example.usermanagementapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.usermanagementapp.model.User;


@Database(entities = {User.class}, version = 2, exportSchema = false)

public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE user_table ADD COLUMN new_column TEXT");
        }
    };

    public static UserDatabase getDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, "user_database")
                .addMigrations(MIGRATION_1_2)
                .build();
    }

}
