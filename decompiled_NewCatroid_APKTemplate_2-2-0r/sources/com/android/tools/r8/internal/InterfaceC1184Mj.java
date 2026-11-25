package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC1184Mj<T> {

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.android.tools.r8.internal.Mj$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a {
        public static final a a = new a("CLASS_SUPER_OR_INTERFACE_ANNOTATION", 0);
        public static final a b = new a("ENCLOSING_INNER_OR_TYPE_ANNOTATION", 1);
        public static final a c = new a("MEMBER_ANNOTATION", 2);

        private a(String str, int i) {
            super(str, i);
        }
    }

    T a(T t, String str);

    T a(String str, a aVar);

    void a();

    void a(char c);

    void a(String str);

    void b();
}
