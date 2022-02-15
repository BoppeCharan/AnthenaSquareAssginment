package com.charan.anthenasquare.Helpers;

import com.charan.anthenasquare.Entites.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDataService {


    public static StudentDataService instance;
    FirebaseDatabase database;
    DatabaseReference usersDatabase;


    public static StudentDataService getInstance(){
        if(instance == null){
            instance = new StudentDataService();
        }
        return instance;
    }

    public StudentDataService(){
        database = FirebaseDatabase.getInstance();
        usersDatabase = database.getReference("Students");
    }


    public void updateStudent(Student student , String uid){
        usersDatabase.child(uid).setValue(student);
    }




}
