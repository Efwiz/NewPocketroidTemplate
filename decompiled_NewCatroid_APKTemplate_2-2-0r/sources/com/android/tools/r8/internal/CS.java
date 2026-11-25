package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class CS {
    private static final DS a;

    static {
        DS ds;
        try {
            ds = (DS) Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
            ds = null;
        }
        if (ds == null) {
            ds = new DS();
        }
        a = ds;
    }

    public static C1062Ia a(Class cls) {
        a.getClass();
        return new C1062Ia(cls);
    }

    public static String a(AbstractC3416yA abstractC3416yA) {
        a.getClass();
        String string = abstractC3416yA.getClass().getGenericInterfaces()[0].toString();
        return string.startsWith("kotlin.jvm.functions.") ? string.substring(21) : string;
    }
}
