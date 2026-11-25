package com.android.tools.r8.internal;

import androidx.core.view.InputDeviceCompat;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2807pq extends I implements Cloneable {
    protected transient C1718bq b;
    protected int c;
    protected transient C1718bq d;
    protected transient C1718bq e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC1357Sr g;
    protected transient InterfaceC2703oS h;
    protected transient boolean i;
    private transient boolean[] j;

    public C2807pq() {
        k();
        this.b = null;
        this.c = 0;
    }

    private void k() {
        this.j = new boolean[48];
    }

    /* JADX WARN: Code duplicated, block: B:85:0x019d  */
    /* JADX WARN: Code duplicated, block: B:86:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:88:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:89:0x01a7  */
    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object a(int i, Object obj) {
        C1718bq c1718bq;
        C1718bq c1718bq2;
        C1718bq c1718bq3;
        C1718bq c1718bq4;
        C1718bq c1718bq5;
        this.i = false;
        C1718bq c1718bq6 = this.b;
        if (c1718bq6 == null) {
            this.c++;
            c1718bq = new C1718bq(i, this.a);
            this.d = c1718bq;
            this.e = c1718bq;
            this.b = c1718bq;
            this.i = true;
        } else {
            int i2 = 0;
            C1718bq c1718bq7 = null;
            C1718bq c1718bq8 = null;
            C1718bq c1718bq9 = c1718bq6;
            while (true) {
                int iCompare = Integer.compare(i, c1718bq6.a);
                if (iCompare == 0) {
                    c1718bq = c1718bq6;
                } else {
                    if (((byte) c1718bq6.e) != 0) {
                        i2 = 0;
                        c1718bq9 = c1718bq6;
                        c1718bq7 = c1718bq8;
                    }
                    boolean[] zArr = this.j;
                    int i3 = i2 + 1;
                    boolean z = iCompare > 0;
                    zArr[i2] = z;
                    if (z) {
                        if (c1718bq6.e()) {
                            this.c++;
                            c1718bq2 = new C1718bq(i, this.a);
                            this.i = true;
                            C1718bq c1718bq10 = c1718bq6.d;
                            if (c1718bq10 == null) {
                                this.e = c1718bq2;
                            }
                            c1718bq2.c = c1718bq6;
                            c1718bq2.d = c1718bq10;
                            c1718bq6.e &= Integer.MAX_VALUE;
                            c1718bq6.d = c1718bq2;
                            break;
                        }
                        c1718bq3 = c1718bq6.d;
                        i2 = i3;
                        C1718bq c1718bq11 = c1718bq3;
                        c1718bq8 = c1718bq6;
                        c1718bq6 = c1718bq11;
                    } else {
                        if (c1718bq6.c()) {
                            this.c++;
                            c1718bq2 = new C1718bq(i, this.a);
                            this.i = true;
                            C1718bq c1718bq12 = c1718bq6.c;
                            if (c1718bq12 == null) {
                                this.d = c1718bq2;
                            }
                            c1718bq2.d = c1718bq6;
                            c1718bq2.c = c1718bq12;
                            c1718bq6.e &= -1073741825;
                            c1718bq6.c = c1718bq2;
                            break;
                        }
                        c1718bq3 = c1718bq6.c;
                        i2 = i3;
                        C1718bq c1718bq13 = c1718bq3;
                        c1718bq8 = c1718bq6;
                        c1718bq6 = c1718bq13;
                    }
                }
            }
            int i4 = 0;
            C1718bq c1718bq14 = c1718bq9;
            while (c1718bq14 != c1718bq2) {
                boolean z2 = this.j[i4];
                if (z2) {
                    int i5 = c1718bq14.e;
                    c1718bq14.e = ((((byte) i5) + 1) & 255) | (i5 & InputDeviceCompat.SOURCE_ANY);
                } else {
                    int i6 = c1718bq14.e;
                    c1718bq14.e = ((((byte) i6) - 1) & 255) | (i6 & InputDeviceCompat.SOURCE_ANY);
                }
                i4++;
                c1718bq14 = z2 ? c1718bq14.d : c1718bq14.c;
            }
            byte b = (byte) c1718bq9.e;
            if (b == -2) {
                c1718bq4 = c1718bq9.c;
                if (((byte) c1718bq4.e) == -1) {
                    if (c1718bq4.e()) {
                        c1718bq4.b(false);
                        c1718bq9.a(c1718bq4);
                    } else {
                        c1718bq9.c = c1718bq4.d;
                    }
                    c1718bq4.d = c1718bq9;
                    c1718bq4.a(0);
                    c1718bq9.a(0);
                } else {
                    c1718bq5 = c1718bq4.d;
                    c1718bq4.d = c1718bq5.c;
                    c1718bq5.c = c1718bq4;
                    c1718bq9.c = c1718bq5.d;
                    c1718bq5.d = c1718bq9;
                    byte b2 = (byte) c1718bq5.e;
                    if (b2 == -1) {
                        c1718bq4.a(0);
                        c1718bq9.a(1);
                    } else if (b2 == 0) {
                        c1718bq4.a(0);
                        c1718bq9.a(0);
                    } else {
                        c1718bq4.a(-1);
                        c1718bq9.a(0);
                    }
                    c1718bq5.a(0);
                    if (c1718bq5.c()) {
                        c1718bq4.b(c1718bq5);
                        c1718bq5.a(false);
                    }
                    if (c1718bq5.e()) {
                        c1718bq9.a(c1718bq5);
                        c1718bq5.b(false);
                    }
                    c1718bq4 = c1718bq5;
                }
                if (c1718bq7 == null) {
                    this.b = c1718bq4;
                } else if (c1718bq7.c == c1718bq9) {
                    c1718bq7.c = c1718bq4;
                } else {
                    c1718bq7.d = c1718bq4;
                }
            } else if (b == 2) {
                c1718bq4 = c1718bq9.d;
                if (((byte) c1718bq4.e) == 1) {
                    if (c1718bq4.c()) {
                        c1718bq4.a(false);
                        c1718bq9.b(c1718bq4);
                    } else {
                        c1718bq9.d = c1718bq4.c;
                    }
                    c1718bq4.c = c1718bq9;
                    c1718bq4.a(0);
                    c1718bq9.a(0);
                } else {
                    c1718bq5 = c1718bq4.c;
                    c1718bq4.c = c1718bq5.d;
                    c1718bq5.d = c1718bq4;
                    c1718bq9.d = c1718bq5.c;
                    c1718bq5.c = c1718bq9;
                    byte b3 = (byte) c1718bq5.e;
                    if (b3 == 1) {
                        c1718bq4.a(0);
                        c1718bq9.a(-1);
                    } else if (b3 == 0) {
                        c1718bq4.a(0);
                        c1718bq9.a(0);
                    } else {
                        c1718bq4.a(1);
                        c1718bq9.a(0);
                    }
                    c1718bq5.a(0);
                    if (c1718bq5.c()) {
                        c1718bq9.b(c1718bq5);
                        c1718bq5.a(false);
                    }
                    if (c1718bq5.e()) {
                        c1718bq4.a(c1718bq5);
                        c1718bq5.b(false);
                    }
                    c1718bq4 = c1718bq5;
                }
                if (c1718bq7 == null) {
                    this.b = c1718bq4;
                } else if (c1718bq7.c == c1718bq9) {
                    c1718bq7.c = c1718bq4;
                } else {
                    c1718bq7.d = c1718bq4;
                }
            }
            c1718bq = c1718bq2;
        }
        Object obj2 = c1718bq.b;
        c1718bq.b = obj;
        return obj2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr b(int i) {
        return new C2567mq(this, i, false, 0, true);
    }

    final int c(int i, int i2) {
        return Integer.compare(i, i2);
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        this.c = 0;
        this.b = null;
        this.f = null;
        this.h = null;
        this.g = null;
        this.e = null;
        this.d = null;
    }

    public final Object clone() {
        try {
            C2807pq c2807pq = (C2807pq) super.clone();
            c2807pq.g = null;
            c2807pq.h = null;
            c2807pq.f = null;
            c2807pq.k();
            if (this.c != 0) {
                C1718bq c1718bq = new C1718bq();
                C1718bq c1718bq2 = new C1718bq();
                C1718bq c1718bq3 = this.b;
                c1718bq.e &= -1073741825;
                c1718bq.c = c1718bq3;
                c1718bq2.a((C1718bq) null);
                C1718bq c1718bq4 = c1718bq2;
                loop0: while (true) {
                    if (c1718bq.c()) {
                        while (c1718bq.e()) {
                            c1718bq = c1718bq.d;
                            if (c1718bq == null) {
                                break loop0;
                            }
                            c1718bq4 = c1718bq4.d;
                        }
                        c1718bq = c1718bq.d;
                        c1718bq4 = c1718bq4.d;
                    } else {
                        C1718bq c1718bqClone = c1718bq.c.clone();
                        c1718bqClone.a(c1718bq4.c);
                        c1718bqClone.b(c1718bq4);
                        c1718bq4.e &= -1073741825;
                        c1718bq4.c = c1718bqClone;
                        c1718bq = c1718bq.c;
                        c1718bq4 = c1718bqClone;
                    }
                    if (!c1718bq.e()) {
                        C1718bq c1718bqClone2 = c1718bq.d.clone();
                        c1718bqClone2.b(c1718bq4.d);
                        c1718bqClone2.a(c1718bq4);
                        c1718bq4.e &= Integer.MAX_VALUE;
                        c1718bq4.d = c1718bqClone2;
                    }
                }
                c1718bq4.d = null;
                C1718bq c1718bq5 = c1718bq2.c;
                c2807pq.b = c1718bq5;
                c2807pq.d = c1718bq5;
                while (true) {
                    C1718bq c1718bq6 = c2807pq.d.c;
                    if (c1718bq6 == null) {
                        break;
                    }
                    c2807pq.d = c1718bq6;
                }
                c2807pq.e = c2807pq.b;
                while (true) {
                    C1718bq c1718bq7 = c2807pq.e.d;
                    if (c1718bq7 == null) {
                        break;
                    }
                    c2807pq.e = c1718bq7;
                }
            }
            return c2807pq;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        return null;
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        return null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        C2731oq c2731oq = new C2731oq(this, 0);
        int i = this.c;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (c2731oq.a().b == obj) {
                return true;
            }
            i = i2;
        }
    }

    final C1718bq d(int i) {
        C1718bq c1718bq = this.b;
        while (c1718bq != null) {
            int iCompare = Integer.compare(i, c1718bq.a);
            if (iCompare == 0) {
                break;
            }
            if (iCompare < 0) {
                c1718bq = (c1718bq.e & 1073741824) != 0 ? null : c1718bq.c;
            } else if ((c1718bq.e & Integer.MIN_VALUE) == 0) {
                c1718bq = c1718bq.d;
            }
        }
        return c1718bq;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0023  */
    final C1718bq e(int i) {
        C1718bq c1718bq;
        C1718bq c1718bq2 = this.b;
        int iCompare = 0;
        C1718bq c1718bq3 = c1718bq2;
        while (c1718bq2 != null) {
            iCompare = Integer.compare(i, c1718bq2.a);
            if (iCompare == 0) {
                break;
            }
            if (iCompare < 0) {
                if ((c1718bq2.e & 1073741824) != 0) {
                    c1718bq = null;
                } else {
                    c1718bq = c1718bq2.c;
                }
            } else if ((c1718bq2.e & Integer.MIN_VALUE) != 0) {
                c1718bq = null;
            } else {
                c1718bq = c1718bq2.d;
            }
            C1718bq c1718bq4 = c1718bq;
            c1718bq3 = c1718bq2;
            c1718bq2 = c1718bq4;
        }
        return iCompare == 0 ? c1718bq2 : c1718bq3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public final Object get(int i) {
        C1718bq c1718bqD = d(i);
        return c1718bqD == null ? this.a : c1718bqD.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.c == 0;
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.g == null) {
            this.g = new C1950eq(this, 0);
        }
        return this.g;
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object remove(int i) {
        C1718bq c1718bq;
        C1718bq c1718bq2;
        C1718bq c1718bq3;
        this.i = false;
        C1718bq c1718bq4 = this.b;
        if (c1718bq4 == null) {
            return this.a;
        }
        boolean z = false;
        C1718bq c1718bq5 = null;
        while (true) {
            int iCompare = Integer.compare(i, c1718bq4.a);
            if (iCompare == 0) {
                if (c1718bq4.c == null) {
                    C1718bq c1718bq6 = c1718bq4.d;
                    if ((c1718bq4.e & Integer.MIN_VALUE) == 0) {
                        while ((c1718bq6.e & 1073741824) == 0) {
                            c1718bq6 = c1718bq6.c;
                        }
                    }
                    this.d = c1718bq6;
                }
                if (c1718bq4.d == null) {
                    this.e = c1718bq4.d();
                }
                if (!c1718bq4.e()) {
                    C1718bq c1718bq7 = c1718bq4.d;
                    if (c1718bq7.c()) {
                        c1718bq7.c = c1718bq4.c;
                        c1718bq7.a(c1718bq4.c());
                        if (!c1718bq7.c()) {
                            c1718bq7.d().d = c1718bq7;
                        }
                        if (c1718bq5 == null) {
                            this.b = c1718bq7;
                        } else if (z) {
                            c1718bq5.d = c1718bq7;
                        } else {
                            c1718bq5.c = c1718bq7;
                        }
                        c1718bq7.a((byte) c1718bq4.e);
                        c1718bq5 = c1718bq7;
                        z = true;
                    } else {
                        while (true) {
                            c1718bq = c1718bq7.c;
                            if (c1718bq.c()) {
                                break;
                            }
                            c1718bq7 = c1718bq;
                        }
                        if (c1718bq.e()) {
                            c1718bq7.a(c1718bq);
                        } else {
                            c1718bq7.c = c1718bq.d;
                        }
                        c1718bq.c = c1718bq4.c;
                        if (!c1718bq4.c()) {
                            c1718bq4.d().d = c1718bq;
                            c1718bq.a(false);
                        }
                        c1718bq.d = c1718bq4.d;
                        c1718bq.b(false);
                        if (c1718bq5 == null) {
                            this.b = c1718bq;
                        } else if (z) {
                            c1718bq5.d = c1718bq;
                        } else {
                            c1718bq5.c = c1718bq;
                        }
                        c1718bq.a((byte) c1718bq4.e);
                        c1718bq5 = c1718bq7;
                        z = false;
                    }
                } else if (!c1718bq4.c()) {
                    c1718bq4.d().d = c1718bq4.d;
                    if (c1718bq5 == null) {
                        this.b = c1718bq4.c;
                    } else if (z) {
                        c1718bq5.d = c1718bq4.c;
                    } else {
                        c1718bq5.c = c1718bq4.c;
                    }
                } else if (c1718bq5 == null) {
                    this.b = z ? c1718bq4.d : c1718bq4.c;
                } else if (z) {
                    c1718bq5.b(c1718bq4.d);
                } else {
                    c1718bq5.a(c1718bq4.c);
                }
                while (c1718bq5 != null) {
                    if (c1718bq5 == this.b) {
                        c1718bq2 = null;
                    } else {
                        C1718bq c1718bq8 = c1718bq5;
                        C1718bq c1718bq9 = c1718bq8;
                        while (true) {
                            if (c1718bq8.e()) {
                                c1718bq2 = c1718bq8.d;
                                if (c1718bq2 != null && c1718bq2.c == c1718bq5) {
                                    break;
                                }
                                while (!c1718bq9.c()) {
                                    c1718bq9 = c1718bq9.c;
                                }
                                c1718bq2 = c1718bq9.c;
                                break;
                            }
                            if (c1718bq9.c()) {
                                C1718bq c1718bq10 = c1718bq9.c;
                                if (c1718bq10 == null || c1718bq10.d != c1718bq5) {
                                    while (!c1718bq8.e()) {
                                        c1718bq8 = c1718bq8.d;
                                    }
                                    c1718bq10 = c1718bq8.d;
                                }
                                c1718bq2 = c1718bq10;
                                break;
                            }
                            c1718bq9 = c1718bq9.c;
                            c1718bq8 = c1718bq8.d;
                        }
                    }
                    if (!z) {
                        z = (c1718bq2 == null || c1718bq2.c == c1718bq5) ? false : true;
                        int i2 = c1718bq5.e;
                        int i3 = ((((byte) i2) + 1) & 255) | (i2 & InputDeviceCompat.SOURCE_ANY);
                        c1718bq5.e = i3;
                        byte b = (byte) i3;
                        if (b == 1) {
                            break;
                        }
                        if (b == 2) {
                            C1718bq c1718bq11 = c1718bq5.d;
                            byte b2 = (byte) c1718bq11.e;
                            if (b2 == -1) {
                                C1718bq c1718bq12 = c1718bq11.c;
                                c1718bq11.c = c1718bq12.d;
                                c1718bq12.d = c1718bq11;
                                c1718bq5.d = c1718bq12.c;
                                c1718bq12.c = c1718bq5;
                                byte b3 = (byte) c1718bq12.e;
                                if (b3 == 1) {
                                    c1718bq11.a(0);
                                    c1718bq5.a(-1);
                                } else if (b3 == 0) {
                                    c1718bq11.a(0);
                                    c1718bq5.a(0);
                                } else {
                                    c1718bq11.a(1);
                                    c1718bq5.a(0);
                                }
                                c1718bq12.a(0);
                                if (c1718bq12.c()) {
                                    c1718bq5.b(c1718bq12);
                                    c1718bq12.a(false);
                                }
                                if (c1718bq12.e()) {
                                    c1718bq11.a(c1718bq12);
                                    c1718bq12.b(false);
                                }
                                if (c1718bq2 == null) {
                                    this.b = c1718bq12;
                                } else if (z) {
                                    c1718bq2.d = c1718bq12;
                                } else {
                                    c1718bq2.c = c1718bq12;
                                }
                            } else {
                                if (c1718bq2 == null) {
                                    this.b = c1718bq11;
                                } else if (z) {
                                    c1718bq2.d = c1718bq11;
                                } else {
                                    c1718bq2.c = c1718bq11;
                                }
                                if (b2 == 0) {
                                    c1718bq5.d = c1718bq11.c;
                                    c1718bq11.c = c1718bq5;
                                    c1718bq11.a(-1);
                                    c1718bq5.a(1);
                                    break;
                                }
                                if (c1718bq11.c()) {
                                    c1718bq5.b(true);
                                    c1718bq11.a(false);
                                } else {
                                    c1718bq5.d = c1718bq11.c;
                                }
                                c1718bq11.c = c1718bq5;
                                c1718bq5.a(0);
                                c1718bq11.a(0);
                            }
                        } else {
                            continue;
                        }
                        c1718bq5 = c1718bq2;
                    } else {
                        z = (c1718bq2 == null || c1718bq2.c == c1718bq5) ? false : true;
                        int i4 = c1718bq5.e;
                        int i5 = ((((byte) i4) - 1) & 255) | (i4 & InputDeviceCompat.SOURCE_ANY);
                        c1718bq5.e = i5;
                        byte b4 = (byte) i5;
                        if (b4 == -1) {
                            break;
                        }
                        if (b4 == -2) {
                            C1718bq c1718bq13 = c1718bq5.c;
                            byte b5 = (byte) c1718bq13.e;
                            if (b5 == 1) {
                                C1718bq c1718bq14 = c1718bq13.d;
                                c1718bq13.d = c1718bq14.c;
                                c1718bq14.c = c1718bq13;
                                c1718bq5.c = c1718bq14.d;
                                c1718bq14.d = c1718bq5;
                                byte b6 = (byte) c1718bq14.e;
                                if (b6 == -1) {
                                    c1718bq13.a(0);
                                    c1718bq5.a(1);
                                } else if (b6 == 0) {
                                    c1718bq13.a(0);
                                    c1718bq5.a(0);
                                } else {
                                    c1718bq13.a(-1);
                                    c1718bq5.a(0);
                                }
                                c1718bq14.a(0);
                                if (c1718bq14.c()) {
                                    c1718bq13.b(c1718bq14);
                                    c1718bq14.a(false);
                                }
                                if (c1718bq14.e()) {
                                    c1718bq5.a(c1718bq14);
                                    c1718bq14.b(false);
                                }
                                if (c1718bq2 == null) {
                                    this.b = c1718bq14;
                                } else if (z) {
                                    c1718bq2.d = c1718bq14;
                                } else {
                                    c1718bq2.c = c1718bq14;
                                }
                            } else {
                                if (c1718bq2 == null) {
                                    this.b = c1718bq13;
                                } else if (z) {
                                    c1718bq2.d = c1718bq13;
                                } else {
                                    c1718bq2.c = c1718bq13;
                                }
                                if (b5 == 0) {
                                    c1718bq5.c = c1718bq13.d;
                                    c1718bq13.d = c1718bq5;
                                    c1718bq13.a(1);
                                    c1718bq5.a(-1);
                                    break;
                                }
                                if (c1718bq13.e()) {
                                    c1718bq5.a(true);
                                    c1718bq13.b(false);
                                } else {
                                    c1718bq5.c = c1718bq13.d;
                                }
                                c1718bq13.d = c1718bq5;
                                c1718bq5.a(0);
                                c1718bq13.a(0);
                            }
                        } else {
                            continue;
                        }
                        c1718bq5 = c1718bq2;
                    }
                }
                this.i = true;
                this.c--;
                return c1718bq4.b;
            }
            z = iCompare > 0;
            if (z) {
                c1718bq3 = (c1718bq4.e & Integer.MIN_VALUE) != 0 ? null : c1718bq4.d;
                if (c1718bq3 == null) {
                    return this.a;
                }
            } else {
                c1718bq3 = (c1718bq4.e & 1073741824) != 0 ? null : c1718bq4.c;
                if (c1718bq3 == null) {
                    return this.a;
                }
            }
            C1718bq c1718bq15 = c1718bq3;
            c1718bq5 = c1718bq4;
            c1718bq4 = c1718bq15;
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC2703oS values() {
        if (this.h == null) {
            this.h = new C1643aq(this);
        }
        return this.h;
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2842qK c() {
        if (this.f == null) {
            this.f = new C1564Zp(this);
        }
        return this.f;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final int d() {
        if (this.b != null) {
            return this.e.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr c(int i) {
        return new C2567mq(this, 0, true, i, false);
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC3087tq
    public final boolean a(int i) {
        return d(i) != null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final int a() {
        if (this.b != null) {
            return this.d.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr a(int i, int i2) {
        return new C2567mq(this, i, false, i2, false);
    }
}
