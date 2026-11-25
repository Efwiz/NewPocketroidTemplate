package com.huawei.hms.feature.dynamic.e;

import android.util.Log;
import dalvik.system.PathClassLoader;

/* JADX INFO: loaded from: classes10.dex */
public final class a extends PathClassLoader {
    public static final String a = "a";

    public a(String str, ClassLoader classLoader) {
        super(str, classLoader);
    }

    @Override // java.lang.ClassLoader
    public Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
        if (!str.startsWith("java.") && !str.startsWith("android.")) {
            try {
                return findClass(str);
            } catch (ClassNotFoundException unused) {
                Log.w(a, "Cannot find The class:" + str);
            }
        }
        return super.loadClass(str, z);
    }
}
