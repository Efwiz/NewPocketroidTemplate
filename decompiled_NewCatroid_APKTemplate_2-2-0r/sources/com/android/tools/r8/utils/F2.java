package com.android.tools.r8.utils;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class F2 extends H2 {
    private F2(String str, H2 h2, Consumer consumer) {
        super(str, h2, consumer);
    }

    @Override // com.android.tools.r8.utils.H2
    public final Object a(Object obj) throws IllegalAccessException, NoSuchFieldException {
        Class<?> cls = this.a;
        if (cls == null) {
            cls = obj.getClass();
        }
        Field declaredField = cls.getDeclaredField(this.b);
        this.c.accept(declaredField);
        Object obj2 = declaredField.get(obj);
        H2 h2 = this.d;
        return h2 != null ? h2.a(obj2) : obj2;
    }
}
