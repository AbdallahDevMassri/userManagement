package com.example.usermanagementapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.usermanagementapp.model.Category;


import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertCategory(Category... category);

    @Update
    void updateCategory(Category... category);

    @Delete
    void deleteCategory(Category... category);

    // delete category by Id
    @Query("delete from category_table where id  = :id")
    void deleteCategoryById(String id);

    // get list of the categories
    @Query("SELECT * FROM category_table ORDER BY id ASC")
    LiveData<List<Category>> getAllCategories();
    // search category by name
    @Query("select *from category_table where name like '%' || :name || '%' ")
    LiveData<List<Category>>searchCategory(String name);


    @Query(" select * from category_table order by rating desc ")
    LiveData<List<Category>> getCategoryByRating();


}
