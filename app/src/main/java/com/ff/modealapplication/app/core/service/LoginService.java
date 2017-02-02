package com.ff.modealapplication.app.core.service;

import com.ff.modealapplication.andorid.network.JSONResult;
import com.ff.modealapplication.app.core.vo.UserVo;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.net.HttpURLConnection;

/**
 * Created by BIT on 2017-01-31.
 */

public class LoginService {

    // 일반로그인 & 페이스북로그인
    public UserVo login(UserVo userVo) {
        String url = "http://192.168.1.15:8088/modeal/userapp/login"; // 학원 로컬
//        String url = "http://192.168.0.17:8088/modeal/userapp/login"; // 집
        HttpRequest httpRequest = HttpRequest.post(url);

        httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.connectTimeout(3000);
        httpRequest.readTimeout(3000);

        httpRequest.send(toJson(userVo));

        int responseCode = httpRequest.code();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Http Response : " + responseCode);
        }

        JSONResultUser jsonResultUser = fromJson(httpRequest, JSONResultUser.class);
        return jsonResultUser.getData();
    }

    // 페이스북회원가입
    public void FBJoin(UserVo userVo) {
        String url = "http://192.168.1.15:8088/modeal/userapp/fbjoin"; // 학원 로컬
//        String url = "http://192.168.0.17:8088/modeal/userapp/fbjoin"; // 집
        HttpRequest httpRequest = HttpRequest.post(url);

        httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.connectTimeout(3000);
        httpRequest.readTimeout(3000);

        httpRequest.send(toJson(userVo));

        int responseCode = httpRequest.code();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Http Response : " + responseCode);
        }

//        JSONResultUser jsonResultUser = fromJson(httpRequest, JSONResultUser.class);
//        return jsonResultUser.getData();
    }

    private class JSONResultUser extends JSONResult<UserVo> {
    }

    // JSON → 타입에 맞게 변환
    protected <V> V fromJson(HttpRequest httpRequest, Class<V> target) {
        V v = null;
        try {
            Gson gson = new GsonBuilder().create();

            Reader reader = httpRequest.bufferedReader();
            v = gson.fromJson(reader, target);
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return v;
    }

    // 타입에 해당하는 OBJECT → JSON
    protected String toJson(Object o) {
        String json = "";
        try {
            Gson gson = new GsonBuilder().create();
            json = gson.toJson(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
