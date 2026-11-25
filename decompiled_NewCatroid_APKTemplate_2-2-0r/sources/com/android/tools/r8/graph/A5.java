package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0564k$$ExternalSyntheticLambda22;
import java.util.AbstractCollection;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A5 {
    static final /* synthetic */ boolean d = true;
    private final C0767v2 a;
    private int b = -1;
    private AbstractCollection c = (AbstractCollection) B5.e;

    A5(C0767v2 c0767v2) {
        this.a = c0767v2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (this.c == B5.e) {
            this.c = new ConcurrentSkipListSet(new C0564k$$ExternalSyntheticLambda22());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b() {
        boolean z = d;
        if (!z && this.b == -1) {
            throw new AssertionError("Program class missing: " + this);
        }
        if (z || this.a.O0()) {
            return this.b == -2;
        }
        throw new AssertionError();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof A5)) {
            return false;
        }
        A5 a5 = (A5) obj;
        return a5.a == this.a && a5.c.equals(this.c);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.c);
    }

    public final String toString() {
        return "TypeInfo{" + this.a + ", level:" + this.b + "}";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        int i2 = this.b;
        if (i == i2) {
            return;
        }
        if (i2 == -2) {
            if (!d && i != 1) {
                throw new AssertionError();
            }
        } else {
            if (i == -2) {
                if (!d && i2 != 1 && i2 != -1) {
                    throw new AssertionError();
                }
                this.b = -2;
                return;
            }
            if (!d && i2 != -1) {
                throw new AssertionError();
            }
            this.b = i;
        }
    }
}
