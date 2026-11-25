package com.android.tools.r8.internal;

import com.android.tools.r8.utils.C3892f0;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ud, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1397Ud {
    static final /* synthetic */ boolean c = true;
    private final int a;
    private C1303Qq b;

    private C1397Ud(int i) {
        this.b = new C1303Qq();
        if (!c && i < 0) {
            throw new AssertionError();
        }
        this.a = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, int i2) {
        if (!c && i < 0) {
            throw new AssertionError();
        }
        C1499Xd c1499Xd = (C1499Xd) this.b.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.internal.Ud$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new C1499Xd(((Integer) obj).intValue());
            }
        });
        c1499Xd.getClass();
        if (!C1499Xd.d && i2 < 0) {
            throw new AssertionError();
        }
        c1499Xd.c++;
        c1499Xd.b += i2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("params:");
        sb.append(this.a).append('\n');
        Iterator it = C3892f0.a(this.b.keySet(), new Comparator() { // from class: com.android.tools.r8.internal.Ud$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((Integer) obj).compareTo((Integer) obj2);
            }
        }).iterator();
        while (it.hasNext()) {
            sb.append(this.b.get(((Integer) it.next()).intValue())).append('\n');
        }
        return sb.toString();
    }
}
