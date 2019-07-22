package com.snake.phonepay.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "USER_TABLE",indices = {@Index(value = {"UserName"},
        unique = true)})
public class UserTable {

    @Expose
    @PrimaryKey(autoGenerate = true)
    public int id;

    @Expose
    @SerializedName("username")
    @ColumnInfo(name = "UserName")
    public String userName;

}
