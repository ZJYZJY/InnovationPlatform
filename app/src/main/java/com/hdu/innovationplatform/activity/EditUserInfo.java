package com.hdu.innovationplatform.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.hdu.innovationplatform.base.AppCompatPreferenceActivity;
import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.utils.HttpUtil;
import com.hdu.innovationplatform.utils.LogUtil;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hdu.innovationplatform.utils.UserStatus.USER;

public class EditUserInfo extends AppCompatPreferenceActivity {

    private static String REAL_NAME = "";

    private static String SEX = "";

    private static String SCHOOL_NUM = "";

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {

            if (preference instanceof EditTextPreference) {

                final String stringValue = o.toString();
                switch (preference.getKey()) {
                    case "user_real_name":
                        REAL_NAME = stringValue;
                        break;
                    case "user_sex":
                        SEX = stringValue;
                        break;
                    case "user_school_number":
                        SCHOOL_NUM = stringValue;
                        break;
                }
                preference.setSummary(stringValue);

                String request = "{" + "\"id\":\"" + USER.getUserId() + "\"," +
                        "\"name\":\"" + REAL_NAME + "\"," +
                        "\"sex\":\"" + SEX + "\"," +
                        "\"school_num\":\"" + SCHOOL_NUM + "\"}";
                LogUtil.d(request);
                RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), request);
                HttpUtil.create().modifyUserInfo(requestBody).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String res = response.body().string();
                            if (HttpUtil.stateCode(res) == HttpUtil.SUCCESS) {
                                LogUtil.i(stringValue + " 修改成功");
                            } else {
                                LogUtil.e("修改失败");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        LogUtil.e("连接失败");
                    }
                });
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        REAL_NAME = getIntent().getStringExtra("user_real_name");
        SEX = getIntent().getStringExtra("user_sex");
        SCHOOL_NUM = getIntent().getStringExtra("user_school_number");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserInfoPreference preUserInfo = new UserInfoPreference();
        fragmentTransaction.add(R.id.modify_user_info_fragment, preUserInfo);
        fragmentTransaction.commit();
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class UserInfoPreference extends PreferenceFragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_edit_user_info);
            initPreferences();
        }

        public void initPreferences() {
            bindPreferenceSummaryToValue(findPreference("user_real_name"));
            bindPreferenceSummaryToValue(findPreference("user_sex"));
            bindPreferenceSummaryToValue(findPreference("user_school_number"));
        }
    }
}
