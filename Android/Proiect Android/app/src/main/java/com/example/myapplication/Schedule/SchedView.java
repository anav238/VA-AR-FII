package com.example.myapplication.Schedule;

import android.app.Activity;
import android.os.Bundle;

import com.example.myapplication.ApplicationData;
import com.example.myapplication.R;
import com.example.myapplication.problem.Classroom;
import com.example.myapplication.util.ThemeUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SchedView extends AppCompatActivity {

    Activity a;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        a=this;
        ThemeUtils.onActivityCreateSetTheme(a);
        // show calendar_view layout
        setContentView(R.layout.calendar_view);

        //hide action bar
        getSupportActionBar().hide();
        // identify resources
        TabLayout weekDaysLayout = findViewById(R.id.weekDays);
        ViewPager viewPager = findViewById(R.id.pagerCalendar);

        String room = getIntent().getStringExtra("room");
        Classroom clasroom = (Classroom) ApplicationData.getInstance().getCurrentBuilding().getLocation(room);


        // setup tabs
        //TODO pass argument to fragment the data (ArrayList) of the respective weekday
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SchedViewFragment(clasroom.getDaySchedule(1)), "luni");
        adapter.addFragment(new SchedViewFragment(clasroom.getDaySchedule(2)), "marti");
        adapter.addFragment(new SchedViewFragment(clasroom.getDaySchedule(3)), "miercuri");
        adapter.addFragment(new SchedViewFragment(clasroom.getDaySchedule(4)), "joi");
        adapter.addFragment(new SchedViewFragment(clasroom.getDaySchedule(5)), "vineri");
        viewPager.setAdapter(adapter);

        weekDaysLayout.setupWithViewPager(viewPager);

//        for(Lecture lecture : lectures) {
//            SchedViewFragment fragment = (SchedViewFragment) ((ViewPagerAdapter) viewPager.getAdapter())
//                    .getItem(lecture.getDayNumer()-1);
//
//            fragment.addLecture(lecture.getCourse(), lecture.getGroup(), lecture.getStartTime(), lecture.getFinishTime());
//        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
