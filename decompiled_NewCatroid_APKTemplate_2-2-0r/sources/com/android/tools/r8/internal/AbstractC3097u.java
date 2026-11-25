package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.u, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3097u {
    protected int a;
    public List b;
    public List c;
    AbstractC3097u d;
    AbstractC3097u e;
    int f = -1;

    protected AbstractC3097u(int i) {
        this.a = i;
    }

    public final AbstractC3097u a() {
        return this.e;
    }

    public abstract AbstractC3097u a(Map map);

    public abstract void a(AbstractC1899eF abstractC1899eF);

    public final int b() {
        return this.a;
    }

    public abstract int c();

    protected final AbstractC3097u a(AbstractC3097u abstractC3097u) {
        if (abstractC3097u.b != null) {
            this.b = new ArrayList();
            int size = abstractC3097u.b.size();
            for (int i = 0; i < size; i++) {
                C3251w00 c3251w00 = (C3251w00) abstractC3097u.b.get(i);
                C3251w00 c3251w01 = new C3251w00(c3251w00.d, c3251w00.e, c3251w00.b);
                c3251w00.a(c3251w01);
                this.b.add(c3251w01);
            }
        }
        if (abstractC3097u.c != null) {
            this.c = new ArrayList();
            int size2 = abstractC3097u.c.size();
            for (int i2 = 0; i2 < size2; i2++) {
                C3251w00 c3251w02 = (C3251w00) abstractC3097u.c.get(i2);
                C3251w00 c3251w03 = new C3251w00(c3251w02.d, c3251w02.e, c3251w02.b);
                c3251w02.a(c3251w03);
                this.c.add(c3251w03);
            }
        }
        return this;
    }

    protected final void b(AbstractC1899eF abstractC1899eF) {
        List list = this.b;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                C3251w00 c3251w00 = (C3251w00) this.b.get(i);
                c3251w00.a(abstractC1899eF.a(c3251w00.d, c3251w00.e, c3251w00.b, true));
            }
        }
        List list2 = this.c;
        if (list2 != null) {
            int size2 = list2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                C3251w00 c3251w01 = (C3251w00) this.c.get(i2);
                c3251w01.a(abstractC1899eF.a(c3251w01.d, c3251w01.e, c3251w01.b, false));
            }
        }
    }
}
