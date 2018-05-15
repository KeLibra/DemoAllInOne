package com.example.peter.sqlitedatabasedemo;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
