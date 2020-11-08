package com.example.paging;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    /**
     * create
     */
    private Button mButtonAdd;
    /**
     * clear
     */
    private Button mButtonDeL;

    StudentDataBase dataBase;
    LiveData<PagedList<Student>> allStudentLiveData;
    private StudentDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        final MyPagerAdapter adapter = new MyPagerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        allStudentLiveData = new LivePagedListBuilder<>(dao.getAll(),2).build();

        allStudentLiveData.observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(PagedList<Student> students) {
                adapter.submitList(students);
            }
        });



    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mButtonAdd = findViewById(R.id.buttonAdd);
        mButtonAdd.setOnClickListener(this);
        mButtonDeL = findViewById(R.id.buttonDeL);
        mButtonDeL.setOnClickListener(this);
        dataBase = StudentDataBase.getInstance(this);
        dao = dataBase.getStudentDao();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.buttonAdd:
                Student[] students = new Student[1000];
                for (int i = 0; i < students.length; i++) {
                    Student student = new Student();
                    student.setStudentNumber(i);
                    students[i] = student;
                }
                new InsertAsyncTask(dao).execute(students);
                break;
            case R.id.buttonDeL:
                new ClearAsyncTask(dao).execute();
                break;
        }
    }

    static class InsertAsyncTask extends AsyncTask<Student,Void,Void>{
        StudentDao dao;
        public InsertAsyncTask(StudentDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Student... Students) {
            dao.insertStudents(Students);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Void,Void,Void>{
        StudentDao dao;
        public ClearAsyncTask(StudentDao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            dao.clear();
            return null;
        }
    }
















}
