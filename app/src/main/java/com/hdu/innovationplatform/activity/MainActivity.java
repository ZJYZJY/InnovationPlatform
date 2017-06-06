package com.hdu.innovationplatform.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.fragment.BlogListFragment;
import com.hdu.innovationplatform.fragment.FollowFragment;
import com.hdu.innovationplatform.fragment.MineFragment;
import com.hdu.innovationplatform.adapter.ViewPagerAdapter;
import com.hdu.innovationplatform.helper.LoginHelper;
import com.hdu.innovationplatform.listener.LoginStatusChangedListener;

import static com.hdu.innovationplatform.utils.UserStatus.LOGIN_STATUS;
import static com.hdu.innovationplatform.utils.UserStatus.USER;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, LoginStatusChangedListener {

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
        // 自动登录
        LoginHelper.getInstance().login(getApplicationContext(), this);
    }

    private void initViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new BlogListFragment());
        adapter.addFragment(new FollowFragment());
        adapter.addFragment(new MineFragment());

        viewPager.setAdapter(adapter);
    }

    public void startLoginActivity() {
        LoginActivity.setOnLoginStatusChanged(this);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_blog_list:
                adapter.update(0);
                viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_follow:
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

    /**
     * 登录状态监听
     *
     * @param loginStatus 用户登录状态
     */
    @Override
    public void onLoginStatusChanged(boolean loginStatus) {
        adapter.update(0);
        adapter.update(1);
        adapter.update(2);
        if (USER != null && loginStatus) {
            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_blog:
                if (LOGIN_STATUS) {
                    startActivity(new Intent(MainActivity.this, EditBlogActivity.class));
                } else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                    startLoginActivity();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
