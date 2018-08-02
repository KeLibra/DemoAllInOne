package com.android.peter.aidlservicedemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String name;
    private boolean gender;
    private int age;
    private int score;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.gender ? (byte) 1 : (byte) 0);
        dest.writeInt(this.age);
        dest.writeInt(this.score);
    }

    public Student() {
    }

    public Student(String name, boolean gender, int age, int score) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.score = score;
    }

    protected Student(Parcel in) {
        this.name = in.readString();
        this.gender = in.readByte() != 0;
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
