package com.android.peter.contentproviderclient;

/**
 * Created by peter on 2018/4/8.
 */

/**
 * student table
 * @id primary key
 * @name student's name. e.g:peter.
 * @gender student's gender. e.g: 0 male; 1 female.
 * @number student's number. e.g: 201804081702.
 * @score student's score. more than 0 and less than 100. e.g:90.
 * */

public class Student {
    private final static String TAG = "Student";

    public Integer id;
    public String name;
    public Integer gender;
    public String number;
    public Integer score;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", number='" + number + '\'' +
                ", score=" + score +
                '}';
    }
}
