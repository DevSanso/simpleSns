package com.github.devsanso.sns.utils;

public class RandomDataUrl {
    public static String random() {
        return "data: image/png," + RandomString.random(16);
    }
}
