package com.android.tools.r8.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class GS implements FZ {
    private final C1854dd a;
    private final InterfaceC1373Th b;
    private final C2722oh c;
    private final C1195Mt d;

    public GS(C1854dd c1854dd, C2722oh c2722oh, C1195Mt c1195Mt) {
        EnumC1347Sh enumC1347Sh = EnumC1347Sh.a;
        this.a = c1854dd;
        this.b = enumC1347Sh;
        this.c = c2722oh;
        this.d = c1195Mt;
    }

    public final boolean a(Field field, boolean z) {
        C2722oh c2722oh = this.c;
        return (c2722oh.a(field.getType(), z) || c2722oh.a(field, z)) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:32:0x00be  */
    /* JADX WARN: Code duplicated, block: B:33:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:36:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:37:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:39:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:40:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:42:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:46:0x013b  */
    /* JADX WARN: Code duplicated, block: B:57:0x0179 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:60:0x0165 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:63:0x013c A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v3, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v5, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* JADX WARN: Type inference failed for: r23v1 */
    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        ?? arrayList;
        List listSingletonList;
        boolean z;
        ES es;
        int i;
        ?? r12;
        ES es2;
        int i2;
        int i3;
        Field[] fieldArr;
        Class cls;
        InterfaceC1833dK interfaceC1833dK;
        boolean z2;
        Type type;
        boolean z3;
        I00 i00A;
        InterfaceC1167Lt interfaceC1167Lt;
        ES es3;
        EZ ezA;
        boolean z4;
        GS gs = this;
        Class clsA = i00.a();
        if (!Object.class.isAssignableFrom(clsA)) {
            return null;
        }
        InterfaceC1833dK interfaceC1833dKA = gs.a.a(i00);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (!clsA.isInterface()) {
            Type typeB = i00.b();
            Class clsA2 = clsA;
            I00 i00A2 = i00;
            while (clsA2 != Object.class) {
                Field[] declaredFields = clsA2.getDeclaredFields();
                int length = declaredFields.length;
                boolean z5 = false;
                int i4 = 0;
                while (i4 < length) {
                    Field field = declaredFields[i4];
                    boolean zA = gs.a(field, true);
                    boolean zA2 = gs.a(field, z5);
                    if (zA || zA2) {
                        field.setAccessible(true);
                        Type typeA = AbstractC1879e.a(i00A2.b(), clsA2, field.getGenericType());
                        DU du = (DU) field.getAnnotation(DU.class);
                        if (du == null) {
                            listSingletonList = Collections.singletonList(gs.b.a(field));
                        } else {
                            String strValue = du.value();
                            String[] strArrAlternate = du.alternate();
                            if (strArrAlternate.length == 0) {
                                listSingletonList = Collections.singletonList(strValue);
                            } else {
                                arrayList = new ArrayList(strArrAlternate.length + 1);
                                arrayList.add(strValue);
                                for (String str : strArrAlternate) {
                                    arrayList.add(str);
                                }
                            }
                            z = zA;
                            es = null;
                            i = 0;
                            r12 = arrayList;
                            while (i < r12.size()) {
                                String str2 = (String) r12.get(i);
                                if (i != 0) {
                                    z3 = false;
                                } else {
                                    z3 = z;
                                }
                                i00A = I00.a(typeA);
                                boolean zA3 = YM.a(i00A.a());
                                interfaceC1167Lt = (InterfaceC1167Lt) field.getAnnotation(InterfaceC1167Lt.class);
                                es3 = es;
                                if (interfaceC1167Lt != null) {
                                    C1195Mt c1195Mt = gs.d;
                                    C1854dd c1854dd = gs.a;
                                    c1195Mt.getClass();
                                    ezA = C1195Mt.a(c1854dd, c1862dk, i00A, interfaceC1167Lt);
                                } else {
                                    ezA = null;
                                }
                                if (ezA != null) {
                                    z4 = true;
                                } else {
                                    z4 = false;
                                }
                                if (ezA == null) {
                                    ezA = c1862dk.a(i00A);
                                }
                                ?? r23 = r12;
                                Field field2 = field;
                                int i5 = i4;
                                int i6 = i;
                                boolean z6 = z4;
                                int i7 = length;
                                Field[] fieldArr2 = declaredFields;
                                Class cls2 = clsA2;
                                InterfaceC1833dK interfaceC1833dK2 = interfaceC1833dKA;
                                Type type2 = typeB;
                                es = (ES) linkedHashMap.put(str2, new ES(str2, z3, zA2, field2, z6, ezA, c1862dk, i00A, zA3));
                                if (es3 == null) {
                                    es = es3;
                                }
                                i = i6 + 1;
                                gs = this;
                                c1862dk = c1862dk;
                                clsA2 = cls2;
                                typeB = type2;
                                z = z3;
                                field = field2;
                                r12 = r23;
                                i4 = i5;
                                length = i7;
                                declaredFields = fieldArr2;
                                interfaceC1833dKA = interfaceC1833dK2;
                            }
                            es2 = es;
                            i2 = i4;
                            i3 = length;
                            fieldArr = declaredFields;
                            cls = clsA2;
                            interfaceC1833dK = interfaceC1833dKA;
                            z2 = false;
                            type = typeB;
                            if (es2 == null) {
                                throw new IllegalArgumentException(type + " declares multiple JSON fields named " + es2.a);
                            }
                        }
                        arrayList = listSingletonList;
                        z = zA;
                        es = null;
                        i = 0;
                        r12 = arrayList;
                        while (i < r12.size()) {
                            String str3 = (String) r12.get(i);
                            if (i != 0) {
                                z3 = false;
                            } else {
                                z3 = z;
                            }
                            i00A = I00.a(typeA);
                            boolean zA4 = YM.a(i00A.a());
                            interfaceC1167Lt = (InterfaceC1167Lt) field.getAnnotation(InterfaceC1167Lt.class);
                            es3 = es;
                            if (interfaceC1167Lt != null) {
                                C1195Mt c1195Mt2 = gs.d;
                                C1854dd c1854dd2 = gs.a;
                                c1195Mt2.getClass();
                                ezA = C1195Mt.a(c1854dd2, c1862dk, i00A, interfaceC1167Lt);
                            } else {
                                ezA = null;
                            }
                            if (ezA != null) {
                                z4 = true;
                            } else {
                                z4 = false;
                            }
                            if (ezA == null) {
                                ezA = c1862dk.a(i00A);
                            }
                            ?? r24 = r12;
                            Field field3 = field;
                            int i8 = i4;
                            int i9 = i;
                            boolean z7 = z4;
                            int i10 = length;
                            Field[] fieldArr3 = declaredFields;
                            Class cls3 = clsA2;
                            InterfaceC1833dK interfaceC1833dK3 = interfaceC1833dKA;
                            Type type3 = typeB;
                            es = (ES) linkedHashMap.put(str3, new ES(str3, z3, zA2, field3, z7, ezA, c1862dk, i00A, zA4));
                            if (es3 == null) {
                                es = es3;
                            }
                            i = i9 + 1;
                            gs = this;
                            c1862dk = c1862dk;
                            clsA2 = cls3;
                            typeB = type3;
                            z = z3;
                            field = field3;
                            r12 = r24;
                            i4 = i8;
                            length = i10;
                            declaredFields = fieldArr3;
                            interfaceC1833dKA = interfaceC1833dK3;
                        }
                        es2 = es;
                        i2 = i4;
                        i3 = length;
                        fieldArr = declaredFields;
                        cls = clsA2;
                        interfaceC1833dK = interfaceC1833dKA;
                        z2 = false;
                        type = typeB;
                        if (es2 == null) {
                            throw new IllegalArgumentException(type + " declares multiple JSON fields named " + es2.a);
                        }
                    } else {
                        i2 = i4;
                        z2 = z5;
                        i3 = length;
                        fieldArr = declaredFields;
                        cls = clsA2;
                        interfaceC1833dK = interfaceC1833dKA;
                        type = typeB;
                    }
                    i4 = i2 + 1;
                    gs = this;
                    c1862dk = c1862dk;
                    clsA2 = cls;
                    typeB = type;
                    length = i3;
                    declaredFields = fieldArr;
                    interfaceC1833dKA = interfaceC1833dK;
                    z5 = z2;
                }
                Class cls4 = clsA2;
                i00A2 = I00.a(AbstractC1879e.a(i00A2.b(), cls4, cls4.getGenericSuperclass()));
                clsA2 = i00A2.a();
                gs = this;
                interfaceC1833dKA = interfaceC1833dKA;
            }
        }
        return new FS(interfaceC1833dKA, linkedHashMap);
    }
}
