package com.hdu.innovationplatform;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.hdu.innovationplatform.Fragment.MineFragment;
import com.hdu.innovationplatform.adapter.ViewPagerAdapter;
import com.hdu.innovationplatform.helper.PermissionHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private MenuItem menuItem;
    private BottomNavigationView navigation;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        navigation.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(2);

        initViewPager(viewPager);
    }

    private void initViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);

        adapter.addFragment(new MineFragment());
        adapter.addFragment(new MineFragment());
        adapter.addFragment(new MineFragment());

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_discover:
                adapter.update(0);
                viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_order:
                adapter.update(1);
                viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_mine:
                adapter.update(2);
                viewPager.setCurrentItem(2);
                return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            navigation.getMenu().getItem(0).setChecked(false);
        }
        menuItem = navigation.getMenu().getItem(position);
        menuItem.setChecked(true);
        adapter.update(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
