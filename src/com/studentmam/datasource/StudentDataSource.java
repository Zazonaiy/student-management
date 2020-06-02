package com.studentmam.datasource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.studentmam.model.Student;

public class StudentDataSource  implements Serializable{
	public static final List<Student> STUDENT_LIST = new ArrayList();
}
