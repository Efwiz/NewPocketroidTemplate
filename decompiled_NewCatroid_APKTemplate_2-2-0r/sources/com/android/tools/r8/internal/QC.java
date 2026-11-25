package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class QC extends Y implements Cloneable {
    protected transient long[] a;
    protected transient Object[] b;
    protected transient int c;
    protected transient boolean d;
    protected transient int e;
    protected transient int f;
    protected int g;
    protected transient HC h;
    protected transient InterfaceC1897eD i;
    protected transient InterfaceC2703oS j;

    public QC() {
        int iA = AbstractC3307wk.a(16, 0.75f);
        this.e = iA;
        this.c = iA - 1;
        this.f = AbstractC3307wk.b(iA, 0.75f);
        int i = this.e + 1;
        this.a = new long[i];
        this.b = new Object[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object e(int i) {
        long j;
        int i2;
        Object[] objArr = this.b;
        Object obj = objArr[i];
        objArr[i] = null;
        this.g--;
        long[] jArr = this.a;
        loop0: while (true) {
            int i3 = (i + 1) & this.c;
            while (true) {
                j = jArr[i3];
                if (j != 0) {
                    int iB = (int) AbstractC3307wk.b(j);
                    int i4 = this.c;
                    int i5 = iB & i4;
                    if (i > i3) {
                        if (i >= i5 && i5 > i3) {
                            break;
                        }
                        i3 = (i3 + 1) & i4;
                    } else {
                        if (i >= i5 || i5 > i3) {
                            break;
                        }
                        i3 = (i3 + 1) & i4;
                    }
                } else {
                    break loop0;
                }
            }
            jArr[i] = j;
            Object[] objArr2 = this.b;
            objArr2[i] = objArr2[i3];
            i = i3;
        }
        jArr[i] = 0;
        this.b[i] = null;
        if (this.g < this.f / 4 && (i2 = this.e) > 16) {
            d(i2 / 2);
        }
        return obj;
    }

    public final boolean b(long j) {
        long j2;
        if (j == 0) {
            return this.d;
        }
        long[] jArr = this.a;
        int iB = ((int) AbstractC3307wk.b(j)) & this.c;
        long j3 = jArr[iB];
        if (j3 == 0) {
            return false;
        }
        if (j == j3) {
            return true;
        }
        do {
            iB = (iB + 1) & this.c;
            j2 = jArr[iB];
            if (j2 == 0) {
                return false;
            }
        } while (j != j2);
        return true;
    }

    public final Object c(long j) {
        long j2;
        if (j == 0) {
            if (this.d) {
                return this.b[this.e];
            }
            return null;
        }
        long[] jArr = this.a;
        int iB = ((int) AbstractC3307wk.b(j)) & this.c;
        long j3 = jArr[iB];
        if (j3 == 0) {
            return null;
        }
        if (j == j3) {
            return this.b[iB];
        }
        do {
            iB = (iB + 1) & this.c;
            j2 = jArr[iB];
            if (j2 == 0) {
                return null;
            }
        } while (j != j2);
        return this.b[iB];
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        if (this.g == 0) {
            return;
        }
        this.g = 0;
        this.d = false;
        Arrays.fill(this.a, 0L);
        Arrays.fill(this.b, (Object) null);
    }

    public final Object clone() {
        try {
            QC qc = (QC) super.clone();
            qc.i = null;
            qc.j = null;
            qc.h = null;
            qc.d = this.d;
            qc.a = (long[]) this.a.clone();
            qc.b = (Object[]) this.b.clone();
            return qc;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        Object[] objArr = this.b;
        long[] jArr = this.a;
        if (this.d && objArr[this.e] == obj) {
            return true;
        }
        int i = this.e;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (jArr[i2] != 0 && objArr[i2] == obj) {
                return true;
            }
            i = i2;
        }
    }

    protected final void d(int i) {
        long j;
        long[] jArr = this.a;
        Object[] objArr = this.b;
        int i2 = i - 1;
        int i3 = i + 1;
        long[] jArr2 = new long[i3];
        Object[] objArr2 = new Object[i3];
        int i4 = this.e;
        int i5 = this.d ? this.g - 1 : this.g;
        while (true) {
            int i6 = i5 - 1;
            if (i5 == 0) {
                objArr2[i] = objArr[this.e];
                this.e = i;
                this.c = i2;
                this.f = AbstractC3307wk.b(i, 0.75f);
                this.a = jArr2;
                this.b = objArr2;
                return;
            }
            do {
                i4--;
                j = jArr[i4];
            } while (j == 0);
            int iB = ((int) AbstractC3307wk.b(j)) & i2;
            if (jArr2[iB] != 0) {
                do {
                    iB = (iB + 1) & i2;
                } while (jArr2[iB] != 0);
            }
            jArr2[iB] = jArr[i4];
            objArr2[iB] = objArr[i4];
            i5 = i6;
        }
    }

    @Override // java.util.Map
    public final int hashCode() {
        long j;
        int i = this.d ? this.g - 1 : this.g;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = i - 1;
            if (i == 0) {
                break;
            }
            while (true) {
                j = this.a[i2];
                if (j != 0) {
                    break;
                }
                i2++;
            }
            int iA = AbstractC3307wk.a(j);
            Object obj = this.b[i2];
            if (this != obj) {
                iA ^= obj == null ? 0 : System.identityHashCode(obj);
            }
            i3 += iA;
            i2++;
            i = i4;
        }
        if (!this.d) {
            return i3;
        }
        Object obj2 = this.b[this.e];
        return i3 + (obj2 != null ? System.identityHashCode(obj2) : 0);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.g == 0;
    }

    @Override // java.util.Map
    public final Set keySet() {
        if (this.i == null) {
            this.i = new LC(this, 0);
        }
        return this.i;
    }

    @Override // com.android.tools.r8.internal.Y, java.util.Map
    public final void putAll(Map map) {
        if (0.75f <= 0.5d) {
            int iA = AbstractC3307wk.a(map.size(), 0.75f);
            if (iA > this.e) {
                d(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((map.size() + this.g) / 0.75f), 2L, 1073741824L);
            if (iA2 > this.e) {
                d(iA2);
            }
        }
        super.putAll(map);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.g;
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.j == null) {
            this.j = new IC(this);
        }
        return this.j;
    }

    static void a(QC qc) {
        qc.d = false;
        Object[] objArr = qc.b;
        int i = qc.e;
        Object obj = objArr[i];
        objArr[i] = null;
        int i2 = qc.g - 1;
        qc.g = i2;
        if (i2 >= qc.f / 4 || i <= 16) {
            return;
        }
        qc.d(i / 2);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004b  */
    @Override // com.android.tools.r8.internal.Y
    public final Object a(Object obj, long j) {
        int i;
        int i2;
        int i3;
        if (j == 0) {
            if (this.d) {
                i2 = this.e;
            } else {
                this.d = true;
                i = this.e;
                this.a[i] = j;
                this.b[i] = obj;
                i3 = this.g;
                this.g = i3 + 1;
                if (i3 >= this.f) {
                    d(AbstractC3307wk.a(i3 + 2, 0.75f));
                }
                i2 = -1;
            }
        } else {
            long[] jArr = this.a;
            int iB = ((int) AbstractC3307wk.b(j)) & this.c;
            long j2 = jArr[iB];
            if (j2 != 0) {
                if (j2 != j) {
                    while (true) {
                        iB = (iB + 1) & this.c;
                        long j3 = jArr[iB];
                        if (j3 != 0) {
                            if (j3 == j) {
                            }
                        }
                    }
                }
                i2 = iB;
            }
            i = iB;
            this.a[i] = j;
            this.b[i] = obj;
            i3 = this.g;
            this.g = i3 + 1;
            if (i3 >= this.f) {
                d(AbstractC3307wk.a(i3 + 2, 0.75f));
            }
            i2 = -1;
        }
        if (i2 < 0) {
            return null;
        }
        Object[] objArr = this.b;
        Object obj2 = objArr[i2];
        objArr[i2] = obj;
        return obj2;
    }

    @Override // com.android.tools.r8.internal.Y
    public final Object a(long j) {
        long j2;
        if (j == 0) {
            if (!this.d) {
                return null;
            }
            this.d = false;
            Object[] objArr = this.b;
            int i = this.e;
            Object obj = objArr[i];
            objArr[i] = null;
            int i2 = this.g - 1;
            this.g = i2;
            if (i2 < this.f / 4 && i > 16) {
                d(i / 2);
            }
            return obj;
        }
        long[] jArr = this.a;
        int iB = ((int) AbstractC3307wk.b(j)) & this.c;
        long j3 = jArr[iB];
        if (j3 == 0) {
            return null;
        }
        if (j == j3) {
            return e(iB);
        }
        do {
            iB = (iB + 1) & this.c;
            j2 = jArr[iB];
            if (j2 == 0) {
                return null;
            }
        } while (j != j2);
        return e(iB);
    }
}
