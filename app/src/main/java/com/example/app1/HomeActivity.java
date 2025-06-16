package com.example.app1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNav;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.viewPager);
        bottomNav = findViewById(R.id.bottomNav);

        // 初始化Fragment列表
        fragments = new ArrayList<>();
        fragments.add(new MessageFragment());
        fragments.add(new FriendsFragment());
        fragments.add(new GamesFragment());
        fragments.add(new ProfileFragment());

        // 设置ViewPager2适配器
        viewPager.setAdapter(new FragmentAdapter(this, fragments));

        // 设置ViewPager2页面切换监听
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNav.setSelectedItemId(R.id.navigation_message);
                        break;
                    case 1:
                        bottomNav.setSelectedItemId(R.id.navigation_friends);
                        break;
                    case 2:
                        bottomNav.setSelectedItemId(R.id.navigation_games);
                        break;
                    case 3:
                        bottomNav.setSelectedItemId(R.id.navigation_profile);
                        break;
                }
            }
        });

        // 设置底部导航栏点击监听
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_message) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.navigation_friends) {
                viewPager.setCurrentItem(1);
                return true;
            } else if (itemId == R.id.navigation_games) {
                viewPager.setCurrentItem(2);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                viewPager.setCurrentItem(3);
                return true;
            }
            return false;
        });
    }
}