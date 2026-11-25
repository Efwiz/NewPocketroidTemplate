package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2432l2 extends AbstractC2509m2 {
    public String b;
    public List c;

    public C2432l2(String str) {
        this(str, 0);
        if (getClass() != C2432l2.class) {
            throw new IllegalStateException();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayList arrayList5;
        ArrayList arrayList6;
        ArrayList arrayList7;
        if (this.c == null) {
            this.c = new ArrayList(this.b != null ? 2 : 1);
        }
        if (this.b != null) {
            this.c.add(str);
        }
        int i = 0;
        if (obj instanceof byte[]) {
            List list = this.c;
            byte[] bArr = (byte[]) obj;
            if (bArr == null) {
                arrayList7 = new ArrayList();
            } else {
                ArrayList arrayList8 = new ArrayList(bArr.length);
                int length = bArr.length;
                while (i < length) {
                    arrayList8.add(Byte.valueOf(bArr[i]));
                    i++;
                }
                arrayList7 = arrayList8;
            }
            list.add(arrayList7);
            return;
        }
        if (obj instanceof boolean[]) {
            List list2 = this.c;
            boolean[] zArr = (boolean[]) obj;
            if (zArr == null) {
                arrayList6 = new ArrayList();
            } else {
                ArrayList arrayList9 = new ArrayList(zArr.length);
                int length2 = zArr.length;
                while (i < length2) {
                    arrayList9.add(Boolean.valueOf(zArr[i]));
                    i++;
                }
                arrayList6 = arrayList9;
            }
            list2.add(arrayList6);
            return;
        }
        if (obj instanceof short[]) {
            List list3 = this.c;
            short[] sArr = (short[]) obj;
            if (sArr == null) {
                arrayList5 = new ArrayList();
            } else {
                ArrayList arrayList10 = new ArrayList(sArr.length);
                int length3 = sArr.length;
                while (i < length3) {
                    arrayList10.add(Short.valueOf(sArr[i]));
                    i++;
                }
                arrayList5 = arrayList10;
            }
            list3.add(arrayList5);
            return;
        }
        if (obj instanceof char[]) {
            List list4 = this.c;
            char[] cArr = (char[]) obj;
            if (cArr == null) {
                arrayList4 = new ArrayList();
            } else {
                ArrayList arrayList11 = new ArrayList(cArr.length);
                int length4 = cArr.length;
                while (i < length4) {
                    arrayList11.add(Character.valueOf(cArr[i]));
                    i++;
                }
                arrayList4 = arrayList11;
            }
            list4.add(arrayList4);
            return;
        }
        if (obj instanceof int[]) {
            this.c.add(P10.a((int[]) obj));
            return;
        }
        if (obj instanceof long[]) {
            List list5 = this.c;
            long[] jArr = (long[]) obj;
            if (jArr == null) {
                arrayList3 = new ArrayList();
            } else {
                ArrayList arrayList12 = new ArrayList(jArr.length);
                int length5 = jArr.length;
                while (i < length5) {
                    arrayList12.add(Long.valueOf(jArr[i]));
                    i++;
                }
                arrayList3 = arrayList12;
            }
            list5.add(arrayList3);
            return;
        }
        if (obj instanceof float[]) {
            List list6 = this.c;
            float[] fArr = (float[]) obj;
            if (fArr == null) {
                arrayList2 = new ArrayList();
            } else {
                ArrayList arrayList13 = new ArrayList(fArr.length);
                int length6 = fArr.length;
                while (i < length6) {
                    arrayList13.add(Float.valueOf(fArr[i]));
                    i++;
                }
                arrayList2 = arrayList13;
            }
            list6.add(arrayList2);
            return;
        }
        if (!(obj instanceof double[])) {
            this.c.add(obj);
            return;
        }
        List list7 = this.c;
        double[] dArr = (double[]) obj;
        if (dArr == null) {
            arrayList = new ArrayList();
        } else {
            ArrayList arrayList14 = new ArrayList(dArr.length);
            int length7 = dArr.length;
            while (i < length7) {
                arrayList14.add(Double.valueOf(dArr[i]));
                i++;
            }
            arrayList = arrayList14;
        }
        list7.add(arrayList);
    }

    public C2432l2(String str, int i) {
        super(589824);
        this.b = str;
    }

    C2432l2(ArrayList arrayList) {
        super(589824);
        this.c = arrayList;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        if (this.c == null) {
            this.c = new ArrayList(this.b != null ? 2 : 1);
        }
        if (this.b != null) {
            this.c.add(str);
        }
        this.c.add(new String[]{str2, str3});
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str, String str2) {
        if (this.c == null) {
            this.c = new ArrayList(this.b != null ? 2 : 1);
        }
        if (this.b != null) {
            this.c.add(str);
        }
        C2432l2 c2432l2 = new C2432l2(str2);
        this.c.add(c2432l2);
        return c2432l2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        if (this.c == null) {
            this.c = new ArrayList(this.b != null ? 2 : 1);
        }
        if (this.b != null) {
            this.c.add(str);
        }
        ArrayList arrayList = new ArrayList();
        this.c.add(arrayList);
        return new C2432l2(arrayList);
    }

    public final void a(AbstractC2509m2 abstractC2509m2) {
        if (abstractC2509m2 != null) {
            List list = this.c;
            if (list != null) {
                int size = list.size();
                for (int i = 0; i < size; i += 2) {
                    a(abstractC2509m2, (String) this.c.get(i), this.c.get(i + 1));
                }
            }
            abstractC2509m2.a();
        }
    }

    static void a(AbstractC2509m2 abstractC2509m2, String str, Object obj) {
        if (abstractC2509m2 != null) {
            if (obj instanceof String[]) {
                String[] strArr = (String[]) obj;
                abstractC2509m2.a(str, strArr[0], strArr[1]);
                return;
            }
            if (obj instanceof C2432l2) {
                C2432l2 c2432l2 = (C2432l2) obj;
                c2432l2.a(abstractC2509m2.a(str, c2432l2.b));
                return;
            }
            if (obj instanceof List) {
                AbstractC2509m2 abstractC2509m2A = abstractC2509m2.a(str);
                if (abstractC2509m2A != null) {
                    List list = (List) obj;
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        a(abstractC2509m2A, (String) null, list.get(i));
                    }
                    abstractC2509m2A.a();
                    return;
                }
                return;
            }
            abstractC2509m2.a(str, obj);
        }
    }
}
