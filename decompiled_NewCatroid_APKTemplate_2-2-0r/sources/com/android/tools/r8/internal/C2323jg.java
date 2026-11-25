package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2323jg extends QY {
    private final C0684j2 c;
    private final C0684j2 d;
    private final LinkedHashMap e;

    public C2323jg(C0767v2 c0767v2, C0684j2 c0684j2, C0684j2 c0684j3, LinkedHashMap linkedHashMap, C0702m c0702m) {
        super(c0702m, c0767v2);
        this.c = c0684j2;
        this.d = c0684j3;
        this.e = linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2284j7.a aVar) {
        Iterator<C0767v2> it = this.d.G0().iterator();
        while (it.hasNext()) {
            aVar.a((InterfaceC1348Si) InterfaceC1348Si.a(it.next()));
        }
    }

    private void c(ArrayList arrayList) {
        C0767v2[] c0767v2Arr = this.d.h.f.a;
        int length = c0767v2Arr.length;
        int i = 1;
        int i2 = 0;
        while (i2 < length) {
            arrayList.add(new O7(EnumC1812d20.a(c0767v2Arr[i2]), i));
            i2++;
            i++;
        }
    }

    public final C0753u b() {
        C0767v2 c0767v2K = this.c.k(0);
        ArrayList arrayList = new ArrayList();
        int size = this.e.size() + 1;
        M7[] m7Arr = new M7[size];
        for (int i = 0; i < size; i++) {
            m7Arr[i] = new M7();
        }
        C2284j7 c2284j7A = C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.a(c0767v2K)).a(new Consumer() { // from class: com.android.tools.r8.internal.jg$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C2284j7.a) obj);
            }
        }).a();
        arrayList.add(new O7(EnumC1812d20.a(c0767v2K), 0));
        arrayList.add(new A7(this.d.e));
        arrayList.add(new C3039t7(EnumC1076Il.EQ, EnumC1812d20.INT, m7Arr[0]));
        arrayList.add(new O7(EnumC1812d20.a((char) c0767v2K.e.e[0]), 0));
        arrayList.add(new M6(this.d.e, false));
        c(arrayList);
        arrayList.add(new I7(185, this.d, true));
        b(arrayList);
        int i2 = 0;
        for (Map.Entry entry : this.e.entrySet()) {
            int i3 = i2 + 1;
            arrayList.add(m7Arr[i2]);
            arrayList.add(c2284j7A);
            arrayList.add(new O7(EnumC1812d20.a((char) c0767v2K.e.e[0]), 0));
            arrayList.add(new A7((C0767v2) entry.getKey()));
            arrayList.add(new C3039t7(EnumC1076Il.EQ, EnumC1812d20.INT, m7Arr[i3]));
            arrayList.add(new O7(EnumC1812d20.a((char) c0767v2K.e.e[0]), 0));
            arrayList.add(new M6((C0767v2) entry.getKey(), false));
            c(arrayList);
            arrayList.add(new I7(184, (C0684j2) entry.getValue(), false));
            b(arrayList);
            i2 = i3;
        }
        arrayList.add(m7Arr[i2]);
        arrayList.add(c2284j7A.clone());
        arrayList.add(new O7(EnumC1812d20.a((char) c0767v2K.e.e[0]), 0));
        c(arrayList);
        arrayList.add(new I7(184, this.c, false));
        b(arrayList);
        return a(arrayList);
    }

    private void b(ArrayList arrayList) {
        if (this.d.h.e == this.a.a().D1) {
            arrayList.add(new C3186v8());
        } else {
            arrayList.add(new C3109u8(EnumC1812d20.a(this.d.h.e)));
        }
    }
}
