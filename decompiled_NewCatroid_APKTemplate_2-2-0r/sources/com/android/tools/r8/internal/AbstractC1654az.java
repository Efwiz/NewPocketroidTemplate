package com.android.tools.r8.internal;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.az, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1654az {
    public static final AbstractC1048Hm a;

    static {
        AbstractC1048Hm abstractC1048HmA = AbstractC1048Hm.g().a("Lkotlin/Boolean").a("Lkotlin/Char").a("Lkotlin/Byte").a("Lkotlin/UByte").a("Lkotlin/Short;").a("Lkotlin/UShort;").a("Lkotlin/Int;").a("Lkotlin/UInt;").a("Lkotlin/Float;").a("Lkotlin/Long;").a("Lkotlin/ULong;").a("Lkotlin/Double;").a();
        final C0862Bm c0862BmG = AbstractC1048Hm.g();
        abstractC1048HmA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.az$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC1654az.a(c0862BmG, (String) obj);
            }
        });
        c0862BmG.a("Lkotlin/Unit;");
        c0862BmG.a("Lkotlin/Any;");
        c0862BmG.a("Lkotlin/Array;");
        c0862BmG.a("Lkotlin/Function;");
        c0862BmG.a("Lkotlin/KFunction;");
        a = c0862BmG.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0862Bm c0862Bm, String str) {
        c0862Bm.a(str);
        c0862Bm.a(str.substring(0, str.length() - 1) + "Array;");
    }
}
