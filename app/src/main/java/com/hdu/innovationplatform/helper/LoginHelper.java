package com.hdu.innovationplatform.helper;

import android.content.Context;

import com.hdu.innovationplatform.listener.LoginStatusChangedListener;
import com.hdu.innovationplatform.model.User;
import com.hdu.innovationplatform.utils.LogUtil;
import com.hdu.innovationplatform.utils.LoginCheck;
import com.hdu.innovationplatform.utils.UserStatus;

import static android.content.Context.MODE_PRIVATE;
import static com.hdu.innovationplatform.utils.UserStatus.EDITOR;
import static com.hdu.innovationplatform.utils.UserStatus.SP;

/**
 * com.hdu.innovationplatform.helper
 * Created by 73958 on 2017/5/24.
 */

public class LoginHelper {

    private static LoginHelper instance;

    public static LoginHelper getInstance(){
        if(instance == null){
            return new LoginHelper();
        }
        return instance;
    }

    public void login(Context context, LoginStatusChangedListener listener){
        SP = context.getSharedPreferences("USER_INFO", MODE_PRIVATE);
        // 自动登录
        if(SP.getString("USER_NAME", null) != null
                && SP.getString("PASSWORD", null) != null){
            String username = SP.getString("USER_NAME", "");
            String password = SP.getString("PASSWORD", "");
            User user = new User(username, password);
            LogUtil.i("SP  " + user.getUsername() + "  " + user.getPassword());

            LoginCheck loginCheck = new LoginCheck(context, listener, user);
            loginCheck.login();
        }
    }

    public void logout(Context context, LoginStatusChangedListener listener){
        LoginCheck loginCheck = new LoginCheck(context, listener);
        loginCheck.setOnLoginStatusChanged(listener);
        loginCheck.logout();
    }

    public void saveUserInfo(Context context){
        // 保存用户名和密码
        SP = context.getSharedPreferences("USER_INFO", MODE_PRIVATE);
        if(UserStatus.USER != null){
            EDITOR = SP.edit();
            EDITOR.putString("USER_NAME", UserStatus.USER.getUsername());
            EDITOR.putString("PASSWORD", UserStatus.USER.getPassword());
            EDITOR.commit();
            LogUtil.i("userInfo save sucessfully");
        }
    }
}