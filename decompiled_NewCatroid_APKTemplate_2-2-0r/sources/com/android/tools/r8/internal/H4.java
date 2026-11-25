package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class H4 {
    public final ArrayList a() {
        final HashSet hashSet = new HashSet();
        final ArrayList arrayList = new ArrayList();
        a(new Consumer() { // from class: com.android.tools.r8.internal.H4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(hashSet, arrayList, obj);
            }
        });
        return arrayList;
    }

    public abstract void a(Consumer consumer);

    public abstract void a(Consumer consumer, Object obj);

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, List list, Object obj) {
        if (set.contains(obj)) {
            return;
        }
        Set setA = a(obj);
        list.add(setA);
        set.addAll(setA);
    }

    public final Set a(Object obj) {
        com.android.tools.r8.utils.O3 o3G = com.android.tools.r8.utils.O3.g(obj);
        while (o3G.c()) {
            a(new H4$$ExternalSyntheticLambda0(o3G), o3G.f());
        }
        return o3G.b();
    }
}
