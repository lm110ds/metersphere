package io.metersphere.testin.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    public static final Gson gson = new Gson();

    public static final Gson gsonDisableHtml = new GsonBuilder().disableHtmlEscaping().create();

    public static String toString(Object obj) {
        return gson.toJson(obj);
    }

    public static final Gson gsonBuilder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public static <T> T obj2pojo(Object obj, Class<T> clazz) {
        return gsonBuilder.fromJson(gsonBuilder.toJson(obj), clazz);
    }
}
