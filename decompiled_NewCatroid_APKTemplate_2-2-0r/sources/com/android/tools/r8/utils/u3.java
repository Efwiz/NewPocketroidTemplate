package com.android.tools.r8.utils;

import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class u3 extends t3 {
    static final /* synthetic */ boolean k = true;
    final /* synthetic */ int i;
    final /* synthetic */ w3 j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    u3(w3 w3Var, String str, boolean z, int i) {
        super(str, z);
        this.j = w3Var;
        this.i = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(int i, String str, t3 t3Var) {
        t3Var.a(i + 1, this);
    }

    @Override // com.android.tools.r8.utils.t3
    public final void a(final int i, t3 t3Var) {
        if (!k && a() < 0) {
            throw new AssertionError();
        }
        t3.b(i);
        System.out.print(toString());
        if (this.i <= 0) {
            System.out.println(" (unknown thread count)");
        } else {
            long jA = this.j.a.a();
            long jA2 = a();
            int i2 = this.i;
            System.out.println(", tasks: " + this.j.c + ", threads: " + i2 + ", utilization: " + y3.b(jA2 / ((long) i2), jA));
        }
        if (this.b) {
            a(i);
        }
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.u3$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(i, (String) obj, (t3) obj2);
            }
        });
        if (this.j.d.d > 0) {
            t3.b(i);
            System.out.println("SLOWEST " + this.j.d.a(this));
            this.j.d.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.u3$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.b(i, (String) obj, (t3) obj2);
                }
            });
        }
    }

    @Override // com.android.tools.r8.utils.t3
    public final String toString() {
        return "MERGE " + super.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, String str, t3 t3Var) {
        t3Var.a(i + 1, this);
    }
}
