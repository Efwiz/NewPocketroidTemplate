package com.reandroid.arsc.model;

import com.reandroid.common.Namespace;

/* JADX INFO: loaded from: classes10.dex */
public interface ResourceLibrary extends Namespace {
    int getId();

    String getName();

    boolean packageNameMatches(String str);

    static String toPrefix(String str) {
        if (str == null) {
            return null;
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf > 0 ? str.substring(iLastIndexOf + 1) : str;
    }

    static boolean packageNameMatches(ResourceLibrary resourceLibrary, String str) {
        if (str == null) {
            return false;
        }
        if (PREFIX_ANDROID.equals(resourceLibrary.getName())) {
            return PREFIX_ANDROID.equals(str);
        }
        return str.equals(resourceLibrary.getName()) || str.equals(resourceLibrary.getPrefix());
    }
}
