package com.example.paging;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * 数据库操作接口
 */
@Dao
public interface StudentDao {
    @Insert
    void insertStudents(Student ...students);
    @Query("DELETE FROM student_table")
    void clear();
    @Query("SELECT * FROM student_table ORDER BY id")
    DataSource.Factory<Integer,Student> getAll();



}
