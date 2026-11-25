package com.android.tools.r8.internal;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2377kL {
    private final String a;
    private final LinkedHashMap b;
    private final LinkedHashSet c;

    public C2377kL(String str) {
        AbstractC0936Ds.c(str, "packageFqName");
        this.a = str;
        this.b = new LinkedHashMap();
        this.c = new LinkedHashSet();
    }

    public final void a(C3534zu c3534zu) {
        int i;
        Set setKeySet = this.b.keySet();
        AbstractC0936Ds.b(setKeySet, "packageParts.keys");
        if (!setKeySet.isEmpty()) {
            C0910Cu c0910CuQ = C0938Du.q();
            c0910CuQ.d(this.a);
            String strI = c0910CuQ.i();
            AbstractC0936Ds.b(strI, "packageFqName");
            char c = '.';
            String strA = AbstractC2710oY.a(strI, '.', '/');
            Set setKeySet2 = this.b.keySet();
            AbstractC0936Ds.b(setKeySet2, "packageParts.keys");
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : setKeySet2) {
                if (AbstractC0936Ds.a((Object) AbstractC2710oY.b((String) obj), (Object) strA)) {
                    arrayList.add(obj);
                } else {
                    arrayList2.add(obj);
                }
            }
            C2772pL c2772pL = new C2772pL(arrayList, arrayList2);
            List list = (List) c2772pL.a();
            List list2 = (List) c2772pL.b();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (Object obj2 : list) {
                String str = (String) obj2;
                AbstractC0936Ds.c(str, "partInternalName");
                String str2 = (String) this.b.get(str);
                Object arrayList3 = linkedHashMap2.get(str2);
                if (arrayList3 == null) {
                    arrayList3 = new ArrayList();
                    linkedHashMap2.put(str2, arrayList3);
                }
                ((List) arrayList3).add(obj2);
            }
            Comparator comparatorA = AbstractC1095Jb.a();
            AbstractC0936Ds.c(comparatorA, "comparator");
            TreeMap treeMap = new TreeMap(comparatorA);
            treeMap.putAll(linkedHashMap2);
            Iterator it = treeMap.entrySet().iterator();
            while (true) {
                i = 1;
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                String str3 = (String) entry.getKey();
                List list3 = (List) entry.getValue();
                AbstractC0936Ds.b(list3, "partInternalNames");
                Iterator it2 = AbstractC0823Ab.a((Collection) list3).iterator();
                while (it2.hasNext()) {
                    c0910CuQ.c(AbstractC2710oY.a((String) it2.next(), '/'));
                    if (str3 != null) {
                        String strA2 = AbstractC2710oY.a(str3, '/');
                        Object objValueOf = linkedHashMap.get(strA2);
                        if (objValueOf == null) {
                            objValueOf = Integer.valueOf(linkedHashMap.size());
                            linkedHashMap.put(strA2, objValueOf);
                        }
                        c0910CuQ.b(((Number) objValueOf).intValue() + 1);
                    }
                }
            }
            ArrayList arrayList4 = new ArrayList();
            LinkedHashMap linkedHashMap3 = new LinkedHashMap();
            for (Object obj3 : list2) {
                String strB = AbstractC2710oY.b((String) obj3);
                Object arrayList5 = linkedHashMap3.get(strB);
                if (arrayList5 == null) {
                    arrayList5 = new ArrayList();
                    linkedHashMap3.put(strB, arrayList5);
                }
                ((List) arrayList5).add(obj3);
            }
            for (Map.Entry entry2 : new TreeMap(linkedHashMap3).entrySet()) {
                String str4 = (String) entry2.getKey();
                List list4 = (List) entry2.getValue();
                AbstractC0936Ds.b(str4, "packageInternalName");
                String strA3 = AbstractC2710oY.a(str4, '/', c);
                if (!((AbstractCollection) c3534zu.h()).contains(strA3)) {
                    c3534zu.a(strA3);
                }
                int iIndexOf = ((AbstractList) c3534zu.h()).indexOf(strA3);
                AbstractC0936Ds.b(list4, "partsInPackage");
                LinkedHashMap linkedHashMap4 = new LinkedHashMap();
                for (Object obj4 : list4) {
                    String str5 = (String) obj4;
                    AbstractC0936Ds.c(str5, "partInternalName");
                    String str6 = (String) this.b.get(str5);
                    Object arrayList6 = linkedHashMap4.get(str6);
                    if (arrayList6 == null) {
                        arrayList6 = new ArrayList();
                        linkedHashMap4.put(str6, arrayList6);
                    }
                    ((List) arrayList6).add(obj4);
                }
                Comparator comparatorA2 = AbstractC1095Jb.a();
                AbstractC0936Ds.c(comparatorA2, "comparator");
                TreeMap treeMap2 = new TreeMap(comparatorA2);
                treeMap2.putAll(linkedHashMap4);
                for (Map.Entry entry3 : treeMap2.entrySet()) {
                    String str7 = (String) entry3.getKey();
                    List list5 = (List) entry3.getValue();
                    AbstractC0936Ds.b(list5, "partInternalNames");
                    Iterator it3 = AbstractC0823Ab.a((Collection) list5).iterator();
                    while (it3.hasNext()) {
                        c0910CuQ.a(AbstractC2710oY.a((String) it3.next(), '/'));
                        if (str7 != null) {
                            String strA4 = AbstractC2710oY.a(str7, '/');
                            Object objValueOf2 = linkedHashMap.get(strA4);
                            if (objValueOf2 == null) {
                                objValueOf2 = Integer.valueOf(linkedHashMap.size());
                                linkedHashMap.put(strA4, objValueOf2);
                            }
                            c0910CuQ.a(((Number) objValueOf2).intValue() + 1);
                        }
                        arrayList4.add(Integer.valueOf(iIndexOf));
                    }
                }
                c = '.';
                i = 1;
            }
            int i2 = i;
            while (arrayList4.size() > i2) {
                if (((Number) arrayList4.get(arrayList4.size() - i2)).intValue() != ((Number) arrayList4.get(arrayList4.size() - 2)).intValue()) {
                    i2 = 1;
                    break;
                } else {
                    i2 = 1;
                    arrayList4.remove(arrayList4.size() - 1);
                }
            }
            c0910CuQ.a(arrayList4);
            Collection collectionValues = linkedHashMap.values();
            Set setKeySet3 = linkedHashMap.keySet();
            AbstractC0936Ds.c(collectionValues, "<this>");
            AbstractC0936Ds.c(setKeySet3, "other");
            Iterator it4 = collectionValues.iterator();
            Iterator it5 = setKeySet3.iterator();
            ArrayList arrayList7 = new ArrayList(Math.min(AbstractC0823Ab.a((Iterable) collectionValues), AbstractC0823Ab.a((Iterable) setKeySet3)));
            while (it4.hasNext() && it5.hasNext()) {
                arrayList7.add(new C2772pL(it4.next(), it5.next()));
            }
            for (C2772pL c2772pL2 : AbstractC0823Ab.a(arrayList7, new C2299jL())) {
                int iIntValue = ((Number) c2772pL2.a()).intValue();
                String str8 = (String) c2772pL2.b();
                int i3 = iIntValue == c0910CuQ.h() ? i2 : 0;
                if (Q30.a && i3 == 0) {
                    throw new AssertionError("Multifile facades are loaded incorrectly: " + linkedHashMap);
                }
                c0910CuQ.b(str8);
            }
            c3534zu.b(c0910CuQ);
        }
        if (this.c.isEmpty()) {
            return;
        }
        C0910Cu c0910CuQ2 = C0938Du.q();
        c0910CuQ2.d(this.a);
        c0910CuQ2.a(AbstractC0823Ab.a((Collection) this.c));
        c3534zu.a(c0910CuQ2);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof C2377kL) {
            C2377kL c2377kL = (C2377kL) obj;
            if (AbstractC0936Ds.a((Object) c2377kL.a, (Object) this.a) && AbstractC0936Ds.a(c2377kL.b, this.b) && AbstractC0936Ds.a(c2377kL.c, this.c)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.c.hashCode() + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31);
    }

    public final String toString() {
        Set setKeySet = this.b.keySet();
        AbstractC0936Ds.b(setKeySet, "packageParts.keys");
        return PU.a(setKeySet, this.c).toString();
    }

    public final void a(String str, String str2) {
        AbstractC0936Ds.c(str, "partInternalName");
        this.b.put(str, str2);
    }

    public final void a(String str) {
        AbstractC0936Ds.c(str, "shortName");
        LinkedHashSet linkedHashSet = this.c;
        AbstractC0936Ds.a((Object) linkedHashSet, "null cannot be cast to non-null type kotlin.collections.MutableSet<kotlin.String>");
        B00.a(linkedHashSet).add(str);
    }
}
