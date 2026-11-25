package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.utils.C3970u0;
import okhttp3.HttpUrl;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.ta, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC3070ta {
    static final /* synthetic */ boolean f = true;
    public static final EnumC3070ta a = new EnumC3070ta("NONE", 0);
    public static final EnumC3070ta b = new EnumC3070ta("NAME", 1);
    public static final EnumC3070ta c = new EnumC3070ta("TYPE_NAME", 2);
    public static final EnumC3070ta d = new EnumC3070ta("CANONICAL_NAME", 3);
    public static final EnumC3070ta e = new EnumC3070ta("SIMPLE_NAME", 4);

    private EnumC3070ta(String str, int i) {
        super(str, i);
    }

    public final C0759u2 a(String str, AbstractC0727q0 abstractC0727q0, C0722p1 c0722p1, int i) {
        String strK;
        int iOrdinal = ordinal();
        boolean z = true;
        if (iOrdinal == 1) {
            strK = C3970u0.k(str);
            if (i > 0) {
                strK = AbstractC2619nY.a(i, "[") + "L" + strK + ";";
            }
        } else {
            if (iOrdinal == 2) {
                throw new C3405y10("Type#getTypeName not supported yet");
            }
            if (iOrdinal == 3) {
                strK = C3970u0.h(str);
                if (i > 0) {
                    strK = strK + AbstractC2619nY.a(i, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                }
            } else {
                if (iOrdinal != 4) {
                    throw new C3405y10("Unexpected ClassNameMapping: " + this);
                }
                if (!f && abstractC0727q0 == null) {
                    throw new AssertionError();
                }
                boolean zEquals = str.equals(abstractC0727q0.d.b1());
                if (!abstractC0727q0.F1() && !abstractC0727q0.E1()) {
                    z = false;
                }
                strK = (zEquals && z) ? abstractC0727q0.d1().c().toString() : C3970u0.w(str);
                if (i > 0) {
                    strK = strK + AbstractC2619nY.a(i, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                }
            }
        }
        return c0722p1.c(strK);
    }
}
