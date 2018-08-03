package com.android.peter.aidlservicedemo.bean;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.peter.aidlservicedemo.database.StudentTable;

public class Student implements Parcelable {
    private long id;
    private String name;
    private int gender;
    private int age;
    private int score;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Student(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(StudentTable.COLUMN_ID));
        this.name = cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_NAME));
        this.gender = cursor.getInt(cursor.getColumnIndex(StudentTable.COLUMN_GENDER));
        this.age = cursor.getInt(cursor.getColumnIndex(StudentTable.COLUMN_AGE));
        this.score = cursor.getInt(cursor.getColumnIndex(StudentTable.COLUMN_SCORE));
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.COLUMN_NAME,this.name);
        contentValues.put(StudentTable.COLUMN_GENDER,this.gender);
        contentValues.put(StudentTable.COLUMN_AGE,this.age);
        contentValues.put(StudentTable.COLUMN_SCORE,this.score);

        return contentValues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.gender);
        dest.writeInt(this.age);
        dest.writeInt(this.score);
    }

    protected Student(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.gender = in.readInt();
        this.age = in.readInt();
        this.score = in.readInt();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
