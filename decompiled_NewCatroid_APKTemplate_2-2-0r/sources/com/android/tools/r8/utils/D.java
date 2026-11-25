package com.android.tools.r8.utils;

import com.android.tools.r8.AssertionsConfiguration;
import com.android.tools.r8.internal.AbstractC1639am;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class D {
    static final /* synthetic */ boolean d = true;
    public final AssertionsConfiguration a;
    public final List b;
    private final AbstractCollection c;

    public D(List list, AssertionsConfiguration assertionsConfiguration) {
        this.a = assertionsConfiguration;
        if (!d && list == null) {
            throw new AssertionError();
        }
        this.b = list;
        this.c = (AbstractCollection) a();
    }

    private List a() {
        if (!d && this.a.isAssertionHandler()) {
            throw new AssertionError();
        }
        if (this.b.isEmpty()) {
            return AbstractC1639am.i();
        }
        final ArrayList arrayList = new ArrayList();
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.utils.D$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D.a(arrayList, (AssertionsConfiguration) obj);
            }
        });
        return arrayList;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.AbstractCollection, java.util.List] */
    public final List b() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, AssertionsConfiguration assertionsConfiguration) {
        if (!assertionsConfiguration.isAssertionHandler() || list.contains(assertionsConfiguration.getAssertionHandler())) {
            return;
        }
        list.add(assertionsConfiguration.getAssertionHandler());
    }
}
