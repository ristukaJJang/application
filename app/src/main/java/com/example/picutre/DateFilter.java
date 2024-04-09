package com.example.picutre;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class DateFilter extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton oneday, during;
    private EditText one_year, one_month, one_day;
    private EditText start_year, start_month, start_day;
    private EditText end_year, end_month, end_day;
    private LinearLayout group_one, group_two;
    private Button btn_next;
    private TextView warring, warring2;

    java.util.Calendar calendar = java.util.Calendar.getInstance();
    int year = calendar.get(java.util.Calendar.YEAR);
    int month = calendar.get(java.util.Calendar.MONTH) + 1; // 월은 0부터 시작하므로 +1 해줍니다.
    int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

    int int_oneYear, int_oneMonth, int_oneDay;
    int int_startYear, int_startMonth, int_startDay;
    int int_endYear, int_endMonth, int_endDay;

    String str_oneYear, str_oneMonth, str_oneDay;
    String str_startYear, str_startMonth, str_startDay;
    String str_endYear, str_endMonth, str_endDay;

    LinearLayout group_title1, group_title2;

    EditText oneday_title, period_title;
    String str_onddayTitle, str_periodTitle;
    int titleNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datefilter);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        oneday = (RadioButton) findViewById(R.id.oneday);
        during = (RadioButton) findViewById(R.id.during);

        one_year = findViewById(R.id.one_year);
        one_month = findViewById(R.id.one_month);
        one_day = findViewById(R.id.one_day);

        start_year = findViewById(R.id.start_year);
        start_month = findViewById(R.id.start_month);
        start_day = findViewById(R.id.start_day);

        end_year = findViewById(R.id.end_year);
        end_month = findViewById(R.id.end_month);
        end_day = findViewById(R.id.end_day);

        group_one = findViewById(R.id.group_one);
        group_two = findViewById(R.id.group_two);
        btn_next = findViewById(R.id.btn_next);

        warring = findViewById(R.id.warring);
        warring2 = findViewById(R.id.warring2);

        group_title1 = findViewById(R.id.group_title1);
        group_title2 = findViewById(R.id.group_title2);

        oneday_title = findViewById(R.id.oneday_title);
        period_title = findViewById(R.id.period_title);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.oneday) {
                    group_one.setVisibility(View.VISIBLE);
                    group_two.setVisibility(View.INVISIBLE);
                    group_title1.setVisibility(View.VISIBLE);
                    group_title2.setVisibility(View.INVISIBLE);

                    one_year.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            warring.setVisibility(View.INVISIBLE);
                            if(int_oneYear > year) warring.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            str_oneYear = one_year.getText().toString();
                            if(str_oneYear.length() != 4) {
                                warring.setVisibility(View.INVISIBLE);
                            }else {
                                int_oneYear = Integer.parseInt(str_oneYear);
                                if(int_oneYear > year) warring.setVisibility(View.VISIBLE);
                                else warring.setVisibility(View.INVISIBLE);
                            }

                        }
                    });
                    one_month.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            warring.setVisibility(View.INVISIBLE);
                            if(int_oneYear >= year && int_oneMonth > month) warring.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if(int_oneYear > year) warring.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            str_oneMonth = one_month.getText().toString();
                            if(str_oneMonth.length() != 2) warring.setVisibility(View.VISIBLE);
                            else {
                                int_oneMonth = Integer.parseInt(str_oneMonth);
                                if((int_oneYear >= year && int_oneMonth > month) || int_oneYear > year) warring.setVisibility(View.VISIBLE);
                                else warring.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    one_day.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            warring.setVisibility(View.INVISIBLE);
                            if(int_oneYear >= year && int_oneMonth >= month && int_oneDay > day) warring.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            str_oneDay = one_day.getText().toString();
                            if(str_oneDay.length() != 2) warring.setVisibility(View.VISIBLE);
                            else {
                                int_oneDay = Integer.parseInt(str_oneDay);
                                if(int_oneYear >= year && int_oneMonth >= month && int_oneDay > day) warring.setVisibility(View.VISIBLE);
                                else if(int_oneYear >= year && int_oneMonth > month) warring.setVisibility(View.VISIBLE);
                                else warring.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                    str_onddayTitle = oneday_title.getText().toString();
                    if(str_onddayTitle.isEmpty()) titleNum = 0;  //사진 날짜로 폴더 생성
                    else titleNum = 1; // 사용자가 입력한 폴더 이름 사용

                }

                if (checkedId == R.id.during) {
                    group_one.setVisibility(View.INVISIBLE);
                    group_two.setVisibility(View.VISIBLE);
                    warring.setVisibility(View.INVISIBLE);
                    warring2.setVisibility(View.INVISIBLE);
                    group_title1.setVisibility(View.INVISIBLE);
                    group_title2.setVisibility(View.VISIBLE);

                    //안되는거 20240401 - 20240501 에러가 안뜸
                    
                    start_year.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            warring2.setVisibility(View.INVISIBLE);
                            if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear > year) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear == year && int_endMonth > month) warring2.setVisibility(View.VISIBLE);
                            else warring2.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            str_startYear = start_year.getText().toString();
                            if(str_startYear.length() != 4) warring2.setVisibility(View.VISIBLE);
                            else {
                                int_startYear = Integer.parseInt(str_startYear);
                                if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear > year) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear == year && int_endMonth > month) warring2.setVisibility(View.VISIBLE);
                                else warring2.setVisibility(View.INVISIBLE);

                            }
                        }
                    });
                    start_month.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            warring2.setVisibility(View.INVISIBLE);
                            if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear > year) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear == year && int_endMonth > month) warring2.setVisibility(View.VISIBLE);
                            else warring2.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            str_startMonth = start_month.getText().toString();
                            if(str_startMonth.length() != 2) warring2.setVisibility(View.VISIBLE);
                            else {
                                int_startMonth = Integer.parseInt(str_startMonth);
                                if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear > year) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear == year && int_endMonth > month) warring2.setVisibility(View.VISIBLE);
                                else warring2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    start_day.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            warring2.setVisibility(View.INVISIBLE);
                            if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear > year) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear == year && int_endMonth > month) warring2.setVisibility(View.VISIBLE);
                            else warring2.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            str_startDay = start_day.getText().toString();
                            if(str_startDay.length() != 2) warring2.setVisibility(View.VISIBLE);
                            else {
                                int_startDay = Integer.parseInt(str_startDay);
                                if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear > year) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear == year && int_endMonth > month) warring2.setVisibility(View.VISIBLE);
                                else warring2.setVisibility(View.INVISIBLE);

                            }
                        }
                    });

                    end_year.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            warring2.setVisibility(View.INVISIBLE);
                            if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                            else warring2.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            str_endYear = end_year.getText().toString();
                            if(str_endYear.length() != 4) warring2.setVisibility(View.VISIBLE);
                            else {
                                int_endYear = Integer.parseInt(str_endYear);
                                if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                                else warring2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    end_month.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            warring2.setVisibility(View.INVISIBLE);
                            if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                            else warring2.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            str_endMonth = end_month.getText().toString();
                            if(str_endMonth.length() != 2) warring2.setVisibility(View.VISIBLE);
                            else {
                                int_endMonth = Integer.parseInt(str_endMonth);
                                if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                                else warring2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    end_day.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            warring2.setVisibility(View.INVISIBLE);
                            if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                            else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                            else warring2.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            str_endDay = end_day.getText().toString();
                            if(str_endDay.length() != 2) warring2.setVisibility(View.VISIBLE);
                            else {
                                int_endDay = Integer.parseInt(str_endDay);
                                // 에러가 뜨는 모든 경우 정리
                                if(int_startYear > year || int_startYear > int_endYear) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth > month) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth > int_endMonth) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == year && int_startMonth == month && int_startDay == day) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay == int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_startYear == int_endYear && int_startMonth == int_endMonth && int_startDay > int_endDay) warring2.setVisibility(View.VISIBLE);
                                else if(int_endYear == year && int_endMonth == month && int_endDay > day) warring2.setVisibility(View.VISIBLE);
                                else warring2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                }else warring2.setVisibility(View.INVISIBLE);

                str_periodTitle = period_title.getText().toString();
                if(str_periodTitle.isEmpty()) titleNum = 0;
                else titleNum = 1;

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warring.getVisibility() == View.VISIBLE || warring2.getVisibility() == View.VISIBLE) {
                    Toast.makeText(DateFilter.this, "다시 날짜 입력 해주세요.", Toast.LENGTH_SHORT).show();
                }else if(!oneday.isChecked() && !during.isChecked()) {
                    Toast.makeText(DateFilter.this, "필터를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(DateFilter.this, GalleryList.class);
                    startActivity(intent);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v,insets)->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}