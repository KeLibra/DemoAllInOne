// IStudentManager.aidl
package com.android.peter.aidlservicedemo;
import com.android.peter.aidlservicedemo.bean.Student;

interface IStudentManager {
    /**
     * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
     */
    List<Student> getStudentList();
    void addStudent(in Student student);
    void deletedStudent(in Student student);
    void updateStudent(in Student student);
    Student queryStudent(String name);
}
