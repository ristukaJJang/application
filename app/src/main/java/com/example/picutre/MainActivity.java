package com.example.picutre;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    private Button btn_sort;
    private Button btn_inappGallery;
    private ImageView mainPicture;
    private long backBtnTime = 0;
    private Button btn_test;
    
    final int GET_GALLERY_IMAGE = 200;
    
    //뒤로가기 버튼을 두 번 눌러야 어플 종료
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btn_sort = findViewById(R.id.btn_sort);
        btn_inappGallery = findViewById(R.id.btn_inappGallery);
        mainPicture = findViewById(R.id.mainPicture);
        btn_test = (Button)findViewById(R.id.btn_test);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setTitle("권한 허용");
                ad.setMessage("해당 앱에서 기기의 사진 및 미디어에 접근하도록 허용하시겠습니까?");
                ad.setCancelable(false); //뒤로가기 버튼으로 다이얼로그 종료 못함

                Button button = new Button(MainActivity.this);
                ad.setPositiveButton("허용", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //허용 버튼을 누르면 권한을 아예 풀어줌
                        dialog.dismiss();
                    }
                });

                ad.setNegativeButton("거부", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //앱을 강제 종료 해버린다
                        dialog.dismiss();
                        ActivityCompat.finishAffinity(MainActivity.this);
                        System.exit(0);
                    }
                });
                ad.show();
            }
        });

        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Filter.class);
                startActivity(intent);
            }
        });

        // 어플 내 갤러리 버튼을 눌렀을 때 실행되는 동작
        btn_inappGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, inAppGallery.class);
                startActivity(intent);
            }


        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}