package com.android.tools.r8.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.av, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1648av {
    private final ArrayList a;
    private final ArrayList b;
    private final HashMap c;
    private final LinkedHashSet d;

    public /* synthetic */ C1648av() {
        this(null);
    }

    public final ArrayList a() {
        return this.a;
    }

    public C1648av(C0970Eu c0970Eu) {
        ArrayList arrayList = new ArrayList();
        this.a = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.b = arrayList2;
        this.c = new HashMap();
        this.d = new LinkedHashSet();
        if (c0970Eu != null) {
            AbstractC0823Ab.a(arrayList, c0970Eu.b());
            List listI = c0970Eu.c().i();
            AbstractC0936Ds.b(listI, "nameResolver.types.recordList");
            Iterator it = listI.iterator();
            while (it.hasNext()) {
                arrayList2.add(((C1487Wu) it.next()).v());
            }
            int size = this.a.size();
            for (int i = 0; i < size; i++) {
                this.c.put(c0970Eu.a(i), Integer.valueOf(i));
            }
            this.d.addAll(c0970Eu.c().h());
        }
    }

    public final int a(String str) {
        AbstractC0936Ds.c(str, "string");
        HashMap map = this.c;
        Object objValueOf = map.get(str);
        if (objValueOf == null) {
            int size = this.a.size();
            this.a.add(str);
            ArrayList arrayList = this.b;
            AbstractC0936Ds.c(arrayList, "<this>");
            C1435Uu c1435Uu = (C1435Uu) (arrayList.isEmpty() ? null : arrayList.get(arrayList.size() - 1));
            if (c1435Uu == null || c1435Uu.l() || c1435Uu.k() || c1435Uu.j() != 0 || c1435Uu.i() != 0) {
                this.b.add(C1487Wu.u());
            } else {
                c1435Uu.b(c1435Uu.h() + 1);
            }
            objValueOf = Integer.valueOf(size);
            map.put(str, objValueOf);
        }
        return ((Number) objValueOf).intValue();
    }

    public final int a(String str, boolean z) {
        AbstractC0936Ds.c(str, "className");
        Integer num = (Integer) this.c.get(str);
        if (num != null && z == this.d.contains(num)) {
            return num.intValue();
        }
        int size = this.a.size();
        if (z) {
            this.d.add(Integer.valueOf(size));
        }
        C1435Uu c1435UuU = C1487Wu.u();
        if (!z && !AbstractC2710oY.a((CharSequence) str)) {
            Integer num2 = (Integer) C0970Eu.f.get(str);
            if (num2 != null) {
                c1435UuU.a(num2.intValue());
                this.a.add("");
            } else {
                c1435UuU.a(EnumC1461Vu.d);
                this.a.add("L" + AbstractC2710oY.a(str, '.', '$') + ';');
            }
        } else {
            this.a.add(str);
        }
        this.b.add(c1435UuU);
        this.c.put(str, Integer.valueOf(size));
        return size;
    }

    public final void a(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        C1360Su c1360SuK = C1516Xu.k();
        ArrayList arrayList = this.b;
        ArrayList arrayList2 = new ArrayList(AbstractC0823Ab.a((Iterable) arrayList));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            C1487Wu c1487WuG = ((C1435Uu) it.next()).g();
            if (c1487WuG.a()) {
                arrayList2.add(c1487WuG);
            } else {
                throw new C1659b10();
            }
        }
        c1360SuK.a(arrayList2);
        c1360SuK.a(this.d);
        C1516Xu c1516XuG = c1360SuK.g();
        if (c1516XuG.a()) {
            c1516XuG.a(byteArrayOutputStream);
            return;
        }
        throw new C1659b10();
    }
}
