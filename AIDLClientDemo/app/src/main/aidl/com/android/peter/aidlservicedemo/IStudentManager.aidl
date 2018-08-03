// IStudentManager.aidl
package com.android.peter.aidlservicedemo;
import com.android.peter.aidlservicedemo.bean.Student;

interface IStudentManager {
    /**
     * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
     */
    List<Student> getStudentList();
    long addStudent(in ContentValues contentValues);
    int deletedStudent(String whereClause, in String[] whereArgs);
    int updateStudent(in ContentValues contentValues, String whereClause, in String[] whereArgs);
    List<Student> queryStudent(in String[] columns, String selection,in String[] selectionArgs,
        String groupBy, String having,String orderBy, String limit);
}
