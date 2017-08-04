package com.lacuz.cherish.ui.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lacuz.cherish.R;
import com.lacuz.cherish.greendao.entity.Birth;
import com.lacuz.cherish.greendao.gen.GreenDaoUtils;
import com.lacuz.cherish.utils.TimeUtils;
import com.lacuz.cherish.view.CustomTitlebar;
import com.lacuz.cherish.view.SimpleCalendarDialogFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddActivity extends AppCompatActivity implements SimpleCalendarDialogFragment.OnFragmentDateSelectedListener {

    @BindView(R.id.titleBar)
    CustomTitlebar titleBar;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.tvSolarTime)
    TextView tvSolarTime;
    @BindView(R.id.tvLunarTime)
    TextView tvLunarTime;
    @BindView(R.id.cbToday)
    CheckBox cbToday;
    @BindView(R.id.cbOneDay)
    CheckBox cbOneDay;
    @BindView(R.id.cbThreeDay)
    CheckBox cbThreeDay;
    @BindView(R.id.cbSevenDay)
    CheckBox cbSevenDay;
    @BindView(R.id.tvSave)
    TextView tvSave;
    long selectTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        titleBar.setAction(new CustomTitlebar.TitleBarOnClickListener() {
            @Override
            public void performAction(View view) {
                switch (view.getId()) {
                    case R.id.iv_left:
                        finish();
                        break;
                    case R.id.tv_right:
                        chooseVideo();
//                        readTxtFile(testPath);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static String testPath = "/storage/emulated/0/Log/log.txt";

    @Override
    public void onDateSelected(long timemillis, String solar, String lunar) {
        tvSolarTime.setText(solar);
        tvLunarTime.setText(lunar);
        selectTime = timemillis;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.rlly_time, R.id.tvSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlly_time:
                new SimpleCalendarDialogFragment(this).show(getSupportFragmentManager(), "test-simple-calendar");
                break;
            case R.id.tvSave:
                save();
                break;
        }
    }

    private void save() {
        if (TextUtils.isEmpty(etTitle.getText().toString().trim())){
            return;
        }
        if (selectTime==0){
            return;
        }
        GreenDaoUtils.getSession(this).getBirthDao().insert(new Birth(etTitle.getText().toString(),selectTime,true,"",""));
        finish();
    }

    // 选取图片按钮单击事件
    public void chooseVideo() {
        Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
        // intent.setType(“image/*”);
        // intent.setType(“audio/*”); //选择音频
        intent.setType("txt/*"); // 选择视频 （mp4 3gp 是android支持的视频格式）
        // intent.setType(“video/*;image/*”);//同时选择视频和图片
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 选取图片的返回值
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data!=null) {
                Uri uri = data.getData();
                readTxtFile(uri);
            }

        }
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public  void readTxtFile(Uri uri){
        try {
            GreenDaoUtils.getSession(this).getBirthDao().deleteAll();
            String encoding="UTF-8";
            File file=new File(new URI(uri.toString()));
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt ;
                while((lineTxt = bufferedReader.readLine()) != null){
                    System.out.println(lineTxt);
                    String[] temp = lineTxt.split("/");
                    Birth birth =  new Birth(temp[0], TimeUtils.getTimeMillis(temp[1],"yyyy-MM-dd","1".equals(temp[2])),"1".equals(temp[2]),"","");
                    GreenDaoUtils.getSession(this).getBirthDao().insert(birth);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }



}
