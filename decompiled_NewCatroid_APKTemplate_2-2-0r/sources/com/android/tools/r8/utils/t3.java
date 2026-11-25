package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC2619nY;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
class t3 {
    static final /* synthetic */ boolean h = true;
    final String a;
    final boolean b;
    final LinkedHashMap c = new LinkedHashMap();
    long d = 0;
    long e;
    LinkedHashMap f;
    Map g;

    t3(String str, boolean z) {
        this.a = str;
        this.b = z;
        if (z) {
            this.f = y3.a();
        }
        this.e = System.nanoTime();
    }

    final long a() {
        return this.d;
    }

    final void b() {
        long jNanoTime = (System.nanoTime() - this.e) + this.d;
        this.d = jNanoTime;
        this.e = -1L;
        if (!h && jNanoTime < 0) {
            throw new AssertionError();
        }
        if (this.b) {
            this.g = y3.a();
        }
    }

    public String toString() {
        return this.a + ": " + y3.c(this.d);
    }

    public final String a(t3 t3Var) {
        if (this == t3Var) {
            return toString();
        }
        return "(" + y3.b(this.d, t3Var.d) + ") " + toString();
    }

    static void b(int i) {
        if (i > 0) {
            System.out.print(AbstractC2619nY.a(i, "  "));
            System.out.print("- ");
        }
    }

    public void a(final int i, final t3 t3Var) {
        long j = 0;
        if (!h && this.d < 0) {
            throw new AssertionError();
        }
        if (y3.a(this.d, t3Var.d) < 2) {
            return;
        }
        b(i);
        System.out.println(a(t3Var));
        if (this.b) {
            a(i);
        }
        if (this.c.isEmpty()) {
            return;
        }
        Collection collectionValues = this.c.values();
        Iterator it = collectionValues.iterator();
        while (it.hasNext()) {
            j += ((t3) it.next()).d;
        }
        long j2 = this.d;
        if (j < j2) {
            long j3 = j2 - j;
            if (y3.a(j3, t3Var.d) >= 2) {
                b(i + 1);
                System.out.println("(" + y3.b(j3, t3Var.d) + ") Unaccounted: " + y3.c(j3));
            }
        }
        collectionValues.forEach(new Consumer() { // from class: com.android.tools.r8.utils.t3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                t3.a(i, t3Var, (t3) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(int i, t3 t3Var, t3 t3Var2) {
        t3Var2.a(i + 1, t3Var);
    }

    final void a(int i) {
        for (Map.Entry entry : this.f.entrySet()) {
            if (((String) entry.getKey()).equals("Memory")) {
                for (int i2 = 0; i2 <= i; i2++) {
                    System.out.print("  ");
                }
                s3 s3Var = (s3) this.g.get(entry.getKey());
                s3 s3Var2 = (s3) entry.getValue();
                System.out.println(((String) entry.getKey()) + " start: " + y3.b(s3Var2.a) + ", end: " + y3.b(s3Var.a) + ", delta: " + y3.b(s3Var.a(s3Var2)));
            }
        }
    }
}
