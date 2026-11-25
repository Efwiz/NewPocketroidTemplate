package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ia, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1062Ia implements InterfaceC1955ev, J9 {
    private static final Map b;
    private final Class a;

    static {
        int i = 0;
        List listA = AbstractC0823Ab.a(InterfaceC1423Ui.class, InterfaceC1475Wi.class, InterfaceC2407kj.class, InterfaceC2483lj.class, InterfaceC2559mj.class, InterfaceC2635nj.class, InterfaceC2724oj.class, InterfaceC2800pj.class, InterfaceC2869qj.class, InterfaceC2941rj.class, InterfaceC1449Vi.class, InterfaceC1504Xi.class, InterfaceC1531Yi.class, InterfaceC1557Zi.class, InterfaceC1631aj.class, InterfaceC1711bj.class, InterfaceC1786cj.class, InterfaceC1861dj.class, InterfaceC1943ej.class, InterfaceC2020fj.class, InterfaceC2175hj.class, InterfaceC2253ij.class, InterfaceC2327jj.class);
        ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listA));
        for (Object obj : listA) {
            int i2 = i + 1;
            if (i < 0) {
                AbstractC0823Ab.a();
                throw null;
            }
            arrayList.add(new C2772pL((Class) obj, Integer.valueOf(i)));
            i = i2;
        }
        b = SD.a(arrayList);
        HashMap map = new HashMap();
        map.put("boolean", "kotlin.Boolean");
        map.put(PsiKeyword.CHAR, "kotlin.Char");
        map.put("byte", "kotlin.Byte");
        map.put("short", "kotlin.Short");
        map.put("int", "kotlin.Int");
        map.put("float", "kotlin.Float");
        map.put("long", "kotlin.Long");
        map.put("double", "kotlin.Double");
        HashMap map2 = new HashMap();
        map2.put(CommonClassNames.JAVA_LANG_BOOLEAN, "kotlin.Boolean");
        map2.put(CommonClassNames.JAVA_LANG_CHARACTER, "kotlin.Char");
        map2.put(CommonClassNames.JAVA_LANG_BYTE, "kotlin.Byte");
        map2.put(CommonClassNames.JAVA_LANG_SHORT, "kotlin.Short");
        map2.put(CommonClassNames.JAVA_LANG_INTEGER, "kotlin.Int");
        map2.put(CommonClassNames.JAVA_LANG_FLOAT, "kotlin.Float");
        map2.put(CommonClassNames.JAVA_LANG_LONG, "kotlin.Long");
        map2.put(CommonClassNames.JAVA_LANG_DOUBLE, "kotlin.Double");
        HashMap map3 = new HashMap();
        map3.put(CommonClassNames.JAVA_LANG_OBJECT, "kotlin.Any");
        map3.put(CommonClassNames.JAVA_LANG_STRING, "kotlin.String");
        map3.put(CommonClassNames.JAVA_LANG_CHAR_SEQUENCE, "kotlin.CharSequence");
        map3.put(CommonClassNames.JAVA_LANG_THROWABLE, "kotlin.Throwable");
        map3.put(CommonClassNames.JAVA_LANG_CLONEABLE, "kotlin.Cloneable");
        map3.put(CommonClassNames.JAVA_LANG_NUMBER, "kotlin.Number");
        map3.put(CommonClassNames.JAVA_LANG_COMPARABLE, "kotlin.Comparable");
        map3.put(CommonClassNames.JAVA_LANG_ENUM, "kotlin.Enum");
        map3.put(CommonClassNames.JAVA_LANG_ANNOTATION_ANNOTATION, "kotlin.Annotation");
        map3.put(CommonClassNames.JAVA_LANG_ITERABLE, "kotlin.collections.Iterable");
        map3.put(CommonClassNames.JAVA_UTIL_ITERATOR, "kotlin.collections.Iterator");
        map3.put(CommonClassNames.JAVA_UTIL_COLLECTION, "kotlin.collections.Collection");
        map3.put(CommonClassNames.JAVA_UTIL_LIST, "kotlin.collections.List");
        map3.put(CommonClassNames.JAVA_UTIL_SET, "kotlin.collections.Set");
        map3.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        map3.put(CommonClassNames.JAVA_UTIL_MAP, "kotlin.collections.Map");
        map3.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        map3.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        map3.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        map3.putAll(map);
        map3.putAll(map2);
        Collection<String> collectionValues = map.values();
        AbstractC0936Ds.b(collectionValues, "primitiveFqNames.values");
        for (String str : collectionValues) {
            StringBuilder sb = new StringBuilder("kotlin.jvm.internal.");
            AbstractC0936Ds.b(str, "kotlinName");
            C2772pL c2772pL = new C2772pL(sb.append(AbstractC2710oY.a(str, '.')).append("CompanionObject").toString(), str.concat(".Companion"));
            map3.put(c2772pL.c(), c2772pL.d());
        }
        for (Map.Entry entry : b.entrySet()) {
            map3.put(((Class) entry.getKey()).getName(), "kotlin.Function" + ((Number) entry.getValue()).intValue());
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(SD.a(map3.size()));
        for (Map.Entry entry2 : map3.entrySet()) {
            linkedHashMap.put(entry2.getKey(), AbstractC2710oY.a((String) entry2.getValue(), '.'));
        }
    }

    public C1062Ia(Class cls) {
        this.a = cls;
    }

    public final Class a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C1062Ia) && AbstractC0936Ds.a(AbstractC2116gu.a(this), AbstractC2116gu.a((InterfaceC1955ev) obj));
    }

    public final int hashCode() {
        return AbstractC2116gu.a(this).hashCode();
    }

    public final String toString() {
        return this.a.toString() + " (Kotlin reflection is not available)";
    }
}
