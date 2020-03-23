package com.example.algorithm_0323;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    TextView text1, text2, text3, text4, resulttext;
    Button btn;
    SubwayController controller;
    SubwayBuilder builder;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //위젯연결
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        resulttext = (TextView) findViewById(R.id.result);
        btn = (Button) findViewById(R.id.btn);

        // 빌더를 생성한다. - 여기서 txt 파일 받아옴
        try {
            builder = SubwayBuilder.getInstance().readFile(getApplicationContext(), "station3.txt", "link2.txt");
        } catch (SubwayException ex) {
            ex.printStackTrace();
            Log.i("에러다", "에러남");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.i("에러다", "에러남");
        }

        // 지하철 클래스를 만든다.
        Subway subway = builder.build();

        // 검색을 위한 컨트롤러를 만든든다.
        controller = new SubwayController(subway);


        //버튼을 누르면 알고리즘 실행
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //키보드 숨기기 위한 객체 선언
                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(text4.getWindowToken(), 0);

                Log.i("에러다1", "에러남??????????");

                //경유지가 2개일 경우
                String starT = text1.getText().toString();
                String miD1 = text2.getText().toString();
                String miD2 = text3.getText().toString();
                String enD = text4.getText().toString();


                //한글로 받으면 code로 변환해주는 작업이 필요
//                String starT = new Subway().getnameStation(start);
//                Log.d("에러남~~~~", starT+"");
//                String miD1 = new Subway().getnameStation(mid1);
//                String miD2 = new Subway().getnameStation(mid2);
//                String enD = new Subway().getnameStation(end);


                //출발역에서 가까운 경유지를 비교해서 선택
                String ret1_1 = null;  //출발-경유1
                try {
                    Log.i("에러다1", "에러남?????");
                    ret1_1 = controller.search(starT, miD1);
                } catch (SubwayException e) {
                    e.printStackTrace();
                    Log.i("에러다1", "에러남");
                }
                String ret1_2 = null;
                try {
                    ret1_2 = controller.search(starT, miD2);
                } catch (SubwayException e) {
                    e.printStackTrace();
                    Log.i("에러다2", "에러남");
                }

                //첫번째 비교작업
                int result = Math.min(ret1_1.length(), ret1_2.length());

                //miD1이 첫번째 경유역 일 때
                if (result == ret1_1.length()) {
                    String ret2_1 = null;
                    try {
                        ret2_1 = controller.search(miD1, miD2);
                    } catch (SubwayException e) {
                        e.printStackTrace();
                        Log.i("에러다3", "에러남");
                    }
                    String ret3_1 = null;
                    try {
                        ret3_1 = controller.search(miD2, enD);
                    } catch (SubwayException e) {
                        e.printStackTrace();
                        Log.i("에러다4", "에러남");
                    }
                    resulttext.setText("첫번째 경로:" + ret1_1 + "\n" + "두번째 경로: " + ret2_1 + "\n" + "세번재 경로: " + ret3_1);
                }


                //miD2이 첫번재 경유역 일 때
                else if (result == ret1_2.length()) {
                    String ret2_1 = null;
                    try {
                        ret2_1 = controller.search(miD2, miD1);
                    } catch (SubwayException e) {
                        e.printStackTrace();
                        Log.i("에러다5", "에러남");
                    }
                    String ret3_1 = null;
                    try {
                        ret3_1 = controller.search(miD1, enD);
                    } catch (SubwayException e) {
                        e.printStackTrace();
                        Log.i("에러다6", "에러남");
                    }
                    resulttext.setText("첫번째 경로:" + ret1_2 + "\n" + "두번째 경로: " + ret2_1 + "\n" + "세번재 경로: " + ret3_1);
                }
            }

        });


    }

}

