package com.hdu.innovationplatform.utils;

import com.hdu.innovationplatform.model.Blog;
import com.hdu.innovationplatform.model.Comment;
import com.hdu.innovationplatform.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * com.hdu.innovationplatform.utils
 * Created by 73958 on 2017/5/24.
 */

public class HttpUtil {
    private static final String SERVER_IP = "www.hduhungrated.cn";

    private static final String PORT = "3000";

    private static final String PATH = "http://" + SERVER_IP + ":" + PORT + "/";

    public static final int SUCCESS = 200;

    public static final int FAIL = 400;

    public static final int EMPTY = 201;

    private static Retrofit instance;

    public static synchronized APIService create() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(PATH)
                    .addConverterFactory(GsonConverterFactory.create())  // Gson转换器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava转换器
                    .build();
        }
        return instance.create(APIService.class);
    }

    /**
     * 获取请求结果的状态码
     * @param str 服务器返回的数据
     */
    public static int stateCode(String str){
        try {
            JSONObject json = new JSONObject(str);
            return json.getInt("state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return FAIL;
    }

    /**
     * 将File对象转换成上传时用的Part对象
     * @param filePaths 图片文件路径
     * @return parts 上传用的parts数组
     */
    public static List<MultipartBody.Part> files2Parts(String key, List<String> filePaths) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.size());
        int count = 1;
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData(key, file.getName(), requestBody);
            count++;
            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     * @param parts Part集合
     * @param key 参数名（name属性）
     * @param value 文本内容
     * @param position 插入的位置
     */
    public static void addTextPart(List<MultipartBody.Part> parts,
                                   String key, String value, int position) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, null, requestBody);
        parts.add(position, part);
    }

    public interface APIService {

        /**
         * 用户登录接口
         * @param user 用户对象
         */
        @POST(APIPath.LOGIN)
        Call<ResponseBody> login(@Body User user);

        /**
         * 用户注册接口
         * @param user 用户对象
         */
        @POST(APIPath.SIGN_UP)
        Call<ResponseBody> signUp(@Body User user);

        /**
         * 获取文章列表
         * @param label 用户对象
         */
        @POST(APIPath.BLOG_LIST)
        Call<ResponseBody> getBlogs(@Body RequestBody label);

        /**
         * 用户发表新博客接口
         * @param blog 博客对象
         */
        @POST(APIPath.PUBLISH_BLOG)
        Call<ResponseBody> publishBlog(@Body Blog blog);

        /**
         * 用户发表评论接口
         * @param comment 评论
         */
        @POST(APIPath.SEND_COMMENT)
        Call<ResponseBody> sendComment(@Body RequestBody comment);

        /**
         * 获取评论列表
         * @param id 博客的id
         */
        @POST(APIPath.COMMENT_LIST)
        Call<ResponseBody> getComments(@Body RequestBody id);

        /**
         * 修改用户信息
         * @param info 用户的信息
         */
        @POST(APIPath.MODIFY_USER_INFO)
        Call<ResponseBody> modifyUserInfo(@Body RequestBody info);

        /**
         * 关注操作
         * @param info 关注信息
         */
        @POST(APIPath.FOLLOW_AUTHOR)
        Call<ResponseBody> followAuthor(@Body RequestBody info);

        /**
         * 获取关注作者的文章列表
         * @param userId 用户id
         */
        @POST(APIPath.FOLLOWED_BLOG_LIST)
        Call<ResponseBody> getFollowedBlogs(@Body RequestBody userId);
    }

    private class APIPath{
        
        private static final String LOGIN = "users/login";

        private static final String SIGN_UP = "users/reg";

        private static final String BLOG_LIST = "show/show";

        private static final String PUBLISH_BLOG = "blog/publish";

        private static final String SEND_COMMENT = "comment/save_comment";

        private static final String COMMENT_LIST = "comment/get_comment";

        private static final String MODIFY_USER_INFO = "users/save_information";

        private static final String FOLLOW_AUTHOR = "concern/save_concern";

        private static final String FOLLOWED_BLOG_LIST = "concern/get_concern";
    }
}
