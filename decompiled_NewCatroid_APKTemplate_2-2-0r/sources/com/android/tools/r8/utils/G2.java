package com.android.tools.r8.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class G2 extends H2 {
    private G2(String str, H2 h2, Consumer consumer) {
        super(str, h2, consumer);
    }

    @Override // com.android.tools.r8.utils.H2
    public final Object a(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> cls = this.a;
        if (cls == null) {
            cls = obj.getClass();
        }
        Method declaredMethod = cls.getDeclaredMethod(this.b, new Class[0]);
        this.c.accept(declaredMethod);
        Object objInvoke = declaredMethod.invoke(obj, new Object[0]);
        H2 h2 = this.d;
        return h2 != null ? h2.a(objInvoke) : objInvoke;
    }
}
