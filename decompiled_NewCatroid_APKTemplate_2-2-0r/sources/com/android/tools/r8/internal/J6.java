package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class J6 {
    int a = -1;
    int b = -1;
    int c = -1;
    final LinkedHashSet d = new LinkedHashSet();
    final ArrayList e = new ArrayList();
    final LinkedHashMap f = new LinkedHashMap();

    private String c() {
        return this.a + "->" + this.c;
    }

    public final int a() {
        return this.a;
    }

    public final int b() {
        return this.c;
    }

    public final String toString() {
        final ArrayList arrayList = new ArrayList();
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.internal.J6$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                J6.a(arrayList, (J6) obj);
            }
        });
        this.e.forEach(new Consumer() { // from class: com.android.tools.r8.internal.J6$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                J6.b(arrayList, (J6) obj);
            }
        });
        return "CfBlock(range=" + c() + ", predecessors=" + com.android.tools.r8.utils.Y2.a(", ", arrayList) + Constant.AFTER_QUTO;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, J6 j6) {
        list.add(j6.c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(List list, J6 j6) {
        list.add("*" + j6.c());
    }
}
