package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3287wQ {
    public static final C2989sQ a = new C2989sQ("ELIGIBLE");
    public static final C2989sQ b = new C2989sQ("ANNOTATION");
    public static final C2989sQ c = new C2989sQ("PINNED");
    public static final C2989sQ d = new C2989sQ("DOWN_CAST");
    public static final C2989sQ e = new C2989sQ("INVALID_LIBRARY_SUPERTYPE");
    public static final C2989sQ f = new C2989sQ("SUBTYPES");
    public static final C2989sQ g;
    public static final C2989sQ h;
    public static final C2989sQ i;
    public static final C2989sQ j;
    public static final C2989sQ k;
    public static final C2989sQ l;
    public static final C2989sQ m;
    public static final C2989sQ n;
    public static final C2989sQ o;
    public static final C2989sQ p;
    public static final C2989sQ q;
    public static final C2989sQ r;
    public static final C2989sQ s;
    public static final C2989sQ t;
    public static final C2989sQ u;
    public static final C2989sQ v;
    public static final C2989sQ w;
    public static final C2989sQ x;
    public static final C2989sQ y;
    public static final C2989sQ z;

    static {
        new C2989sQ("SUBENUM_SUBTYPES");
        new C2989sQ("SUBENUM_INVALID_HIERARCHY");
        new C2989sQ("SUBENUM_INSTANCE_FIELDS");
        new C2989sQ("SUBENUM_STATIC_MEMBER");
        g = new C2989sQ("MANY_INSTANCE_FIELDS");
        h = new C2989sQ("DEFAULT_METHOD_INVOKE");
        i = new C2989sQ("UNRESOLVABLE_FIELD");
        j = new C2989sQ("CONST_CLASS");
        k = new C2989sQ("INVALID_PHI");
        l = new C2989sQ("INVALID_INIT");
        m = new C2989sQ("INVALID_CLINIT");
        n = new C2989sQ("INVALID_INVOKE");
        o = new C2989sQ("INVALID_INVOKE_CLASSPATH");
        p = new C2989sQ("INVALID_INVOKE_CUSTOM");
        q = new C2989sQ("INVALID_INVOKE_ON_ARRAY");
        r = new C2989sQ("IMPLICIT_UP_CAST_IN_RETURN");
        s = new C2989sQ("INVALID_FIELD_PUT");
        t = new C2989sQ("INVALID_ARRAY_PUT");
        u = new C2989sQ("INVALID_INVOKE_NEW_ARRAY");
        v = new C2989sQ("TYPE_MISMATCH_FIELD_PUT");
        w = new C2989sQ("INVALID_IF_TYPES");
        x = new C2989sQ("ASSIGNMENT_OUTSIDE_INIT");
        y = new C2989sQ("ENUM_METHOD_CALLED_WITH_NULL_RECEIVER");
        z = new C2989sQ("OTHER_UNSUPPORTED_INSTRUCTION");
    }

    public abstract Object a();
}
