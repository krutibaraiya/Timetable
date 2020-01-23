package com.github.kruti.timetableviewdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kruti.timetableview.Schedule;
import com.github.kruti.timetableview.Sticker;
import com.github.kruti.timetableview.TimetableView;
import com.github.kruti.timetableview.SaveManager;

import java.util.ArrayList;
import java.util.Calendar;

import static com.github.kruti.timetableview.SaveManager.COL1;
import static com.github.kruti.timetableview.SaveManager.COL2;
import static com.github.kruti.timetableview.SaveManager.COL3;
import static com.github.kruti.timetableview.SaveManager.COL_ID;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    SaveManager myDB;
    private Context context;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;

    private Button addBtn;
    private Button clearBtn;
    private Button saveBtn;
    private Button loadBtn;

    private TimetableView timetable;





    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB= new SaveManager(this);
        //myDB.updateData(Integer.valueOf(COL_ID), COL1, COL2, COL3 );


        /*final Cursor cursor;*/
        /*Cursor cursor = new SaveManager(this).getAllContent();
        String [] columns = new String[] {
                COL1,
                COL2,
                COL3
        };*/

        final Cursor cursor= myDB.getAllContent();
        String [] columns = new String[]{
                COL1,
                COL2,
                COL3
        };
        init();

        if(savedInstanceState != null){


            String savedsubject = savedInstanceState.getString(COL1);

            String savedclassroom= savedInstanceState.getString(COL2);
            String savedprofessor= savedInstanceState.getString(COL3);

            new SaveManager(this).insertData(savedsubject,savedclassroom, savedprofessor);

        }else{
            Toast.makeText(this,"Make Entry", Toast.LENGTH_SHORT).show();
        }
    }

    private void init(){
        this.context = this;
        addBtn = findViewById(R.id.add_btn);
        clearBtn = findViewById(R.id.clear_btn);
        saveBtn = findViewById(R.id.save_btn);
        loadBtn = findViewById(R.id.load_btn);

        timetable = findViewById(R.id.timetable);
        timetable.setHeaderHighlight(2);
        initView();
    }

    private void initView(){
        addBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);

        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Intent i = new Intent(context, EditActivity.class);
                i.putExtra("mode",REQUEST_EDIT);
                i.putExtra("idx", idx);
                i.putExtra("schedules", schedules);
                startActivityForResult(i,REQUEST_EDIT);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                Intent i = new Intent(this, EditActivity.class);
                i.putExtra("mode", REQUEST_ADD);
                startActivityForResult(i, REQUEST_ADD);
                break;
            case R.id.clear_btn:
                timetable.removeAll();
                break;
            case R.id.save_btn:

                //The following code didn't work for saving

                //Integer id = COL_ID;
                String COL1 = SaveManager.COL1;
                String COL2 = SaveManager.COL2;
                String COL3 = SaveManager.COL3;

                new SaveManager(this).insertData(COL1, COL2, COL3);

                /* SaveManager.insertData(COL1,COL2,COL3);*/


                Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                break;


            case R.id.load_btn:

                //for viewing the timetable even after app is killed (didn't work)

                Cursor cursor = new SaveManager(this).getAllContent();


                Toast.makeText(this, "Data Successfully Loaded!", Toast.LENGTH_LONG).show();
                break;



            case R.id.switch1:

                Calendar calendar= Calendar.getInstance();
                Intent intent = new Intent (getApplicationContext(),NotificationReceiver.class);
                PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 24*7*60*60*1000, pendingIntent);


        }

        }
        @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        savedInstanceState.putString(COL_ID,"_id");
        savedInstanceState.putString(COL1, "subject");
        savedInstanceState.putString(COL2, "classroom");
        savedInstanceState.putString(COL3, "professor");

        super.onSaveInstanceState(savedInstanceState);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ADD:
                if(resultCode == EditActivity.RESULT_OK_ADD){
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    timetable.add(item);
                }
                break;
            case REQUEST_EDIT:
                /** Edit -> Submit */
                if(resultCode == EditActivity.RESULT_OK_EDIT){
                    int idx = data.getIntExtra("idx",-1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    timetable.edit(idx,item);
                }
                /** Edit -> Delete */
                else if(resultCode == EditActivity.RESULT_OK_DELETE){
                    int idx = data.getIntExtra("idx",-1);
                    timetable.remove(idx);
                }
                break;
        }
    }

}
