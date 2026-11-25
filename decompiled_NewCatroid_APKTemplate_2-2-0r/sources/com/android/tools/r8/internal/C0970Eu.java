package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import net.bytebuddy.asm.Advice;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Eu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0970Eu implements InterfaceC2600nG {
    private static final List e;
    private static final LinkedHashMap f;
    private final String[] a;
    private final Set b;
    private final List c;
    private final C1516Xu d;

    static {
        String strA = AbstractC0823Ab.a(AbstractC0823Ab.a('k', 'o', Character.valueOf(Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL), 'l', 'i', 'n'), "", null, null, null, 62);
        List listA = AbstractC0823Ab.a(strA.concat("/Any"), strA.concat("/Nothing"), strA.concat("/Unit"), strA.concat("/Throwable"), strA.concat("/Number"), strA.concat("/Byte"), strA.concat("/Double"), strA.concat("/Float"), strA.concat("/Int"), strA.concat("/Long"), strA.concat("/Short"), strA.concat("/Boolean"), strA.concat("/Char"), strA.concat("/CharSequence"), strA.concat("/String"), strA.concat("/Comparable"), strA.concat("/Enum"), strA.concat("/Array"), strA.concat("/ByteArray"), strA.concat("/DoubleArray"), strA.concat("/FloatArray"), strA.concat("/IntArray"), strA.concat("/LongArray"), strA.concat("/ShortArray"), strA.concat("/BooleanArray"), strA.concat("/CharArray"), strA.concat("/Cloneable"), strA.concat("/Annotation"), strA.concat("/collections/Iterable"), strA.concat("/collections/MutableIterable"), strA.concat("/collections/Collection"), strA.concat("/collections/MutableCollection"), strA.concat("/collections/List"), strA.concat("/collections/MutableList"), strA.concat("/collections/Set"), strA.concat("/collections/MutableSet"), strA.concat("/collections/Map"), strA.concat("/collections/MutableMap"), strA.concat("/collections/Map.Entry"), strA.concat("/collections/MutableMap.MutableEntry"), strA.concat("/collections/Iterator"), strA.concat("/collections/MutableIterator"), strA.concat("/collections/ListIterator"), strA.concat("/collections/MutableListIterator"));
        e = listA;
        C1947en c1947enB = AbstractC0823Ab.b(listA);
        int iA = SD.a(AbstractC0823Ab.a(c1947enB));
        LinkedHashMap linkedHashMap = new LinkedHashMap(iA >= 16 ? iA : 16);
        Iterator it = c1947enB.iterator();
        while (true) {
            C2024fn c2024fn = (C2024fn) it;
            if (!c2024fn.hasNext()) {
                f = linkedHashMap;
                return;
            } else {
                C1865dn c1865dn = (C1865dn) c2024fn.next();
                linkedHashMap.put((String) c1865dn.b(), Integer.valueOf(c1865dn.a()));
            }
        }
    }

    public C0970Eu(C1516Xu c1516Xu, String[] strArr) {
        AbstractC0936Ds.c(strArr, "strings");
        List listH = c1516Xu.h();
        Set setA = listH.isEmpty() ? C2172hg.a : AbstractC0823Ab.a(listH);
        List listI = c1516Xu.i();
        AbstractC0936Ds.b(listI, "types.recordList");
        ArrayList arrayListA = AbstractC0996Fu.a(listI);
        this.a = strArr;
        this.b = setA;
        this.c = arrayListA;
        this.d = c1516Xu;
    }

    public final String[] b() {
        return this.a;
    }

    public final C1516Xu c() {
        return this.d;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0032  */
    @Override // com.android.tools.r8.internal.InterfaceC2600nG
    public final String a(int i) {
        String strA;
        C1487Wu c1487Wu = (C1487Wu) this.c.get(i);
        if (c1487Wu.s()) {
            strA = c1487Wu.m();
        } else if (c1487Wu.q()) {
            List list = e;
            int size = list.size();
            int i2 = c1487Wu.i();
            if (i2 < 0 || i2 >= size) {
                strA = this.a[i];
            } else {
                strA = (String) list.get(c1487Wu.i());
            }
        } else {
            strA = this.a[i];
        }
        if (c1487Wu.n() >= 2) {
            List listO = c1487Wu.o();
            AbstractC0936Ds.b(listO, "substringIndexList");
            Integer num = (Integer) listO.get(0);
            Integer num2 = (Integer) listO.get(1);
            AbstractC0936Ds.b(num, "begin");
            if (num.intValue() >= 0) {
                int iIntValue = num.intValue();
                AbstractC0936Ds.b(num2, "end");
                if (iIntValue <= num2.intValue() && num2.intValue() <= strA.length()) {
                    strA = strA.substring(num.intValue(), num2.intValue());
                    AbstractC0936Ds.b(strA, "this as java.lang.String…ing(startIndex, endIndex)");
                }
            }
        }
        if (c1487Wu.k() >= 2) {
            List listL = c1487Wu.l();
            AbstractC0936Ds.b(listL, "replaceCharList");
            Integer num3 = (Integer) listL.get(0);
            Integer num4 = (Integer) listL.get(1);
            AbstractC0936Ds.b(strA, "string");
            strA = AbstractC2710oY.a(strA, (char) num3.intValue(), (char) num4.intValue());
        }
        EnumC1461Vu enumC1461VuH = c1487Wu.h();
        if (enumC1461VuH == null) {
            enumC1461VuH = EnumC1461Vu.b;
        }
        int iOrdinal = enumC1461VuH.ordinal();
        if (iOrdinal == 1) {
            AbstractC0936Ds.b(strA, "string");
            strA = AbstractC2710oY.a(strA, '$', '.');
        } else if (iOrdinal == 2) {
            if (strA.length() >= 2) {
                strA = strA.substring(1, strA.length() - 1);
                AbstractC0936Ds.b(strA, "this as java.lang.String…ing(startIndex, endIndex)");
            }
            strA = AbstractC2710oY.a(strA, '$', '.');
        }
        AbstractC0936Ds.b(strA, "string");
        return strA;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2600nG
    public final boolean b(int i) {
        return this.b.contains(Integer.valueOf(i));
    }

    @Override // com.android.tools.r8.internal.InterfaceC2600nG
    public final String c(int i) {
        return a(i);
    }
}
