package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JB extends AbstractMap implements Serializable {
    private static final Comparator h = new CB();
    static final /* synthetic */ boolean i = true;
    Comparator a;
    IB b;
    int c;
    int d;
    final IB e;
    private EB f;
    private GB g;

    public JB(int i2) {
        Comparator comparator = h;
        this.c = 0;
        this.d = 0;
        this.e = new IB();
        this.a = comparator;
    }

    final IB a(Object obj, boolean z) {
        int iCompareTo;
        IB ib;
        Comparator comparator = this.a;
        IB ib2 = this.b;
        if (ib2 != null) {
            Comparable comparable = comparator == h ? (Comparable) obj : null;
            while (true) {
                iCompareTo = comparable != null ? comparable.compareTo(ib2.f) : comparator.compare(obj, ib2.f);
                if (iCompareTo == 0) {
                    return ib2;
                }
                IB ib3 = iCompareTo < 0 ? ib2.b : ib2.c;
                if (ib3 == null) {
                    break;
                }
                ib2 = ib3;
            }
        } else {
            iCompareTo = 0;
        }
        if (!z) {
            return null;
        }
        IB ib4 = this.e;
        if (ib2 != null) {
            ib = new IB(ib2, obj, ib4, ib4.e);
            if (iCompareTo < 0) {
                ib2.b = ib;
            } else {
                ib2.c = ib;
            }
            a(ib2, true);
        } else {
            if (comparator == h && !(obj instanceof Comparable)) {
                throw new ClassCastException(obj.getClass().getName().concat(" is not Comparable"));
            }
            ib = new IB(ib2, obj, ib4, ib4.e);
            this.b = ib;
        }
        this.c++;
        this.d++;
        return ib;
    }

    final void b(IB ib, boolean z) {
        IB ib2;
        IB ib3;
        int i2;
        if (z) {
            IB ib4 = ib.e;
            ib4.d = ib.d;
            ib.d.e = ib4;
        }
        IB ib5 = ib.b;
        IB ib6 = ib.c;
        IB ib7 = ib.a;
        int i3 = 0;
        if (ib5 == null || ib6 == null) {
            if (ib5 != null) {
                a(ib, ib5);
                ib.b = null;
            } else if (ib6 != null) {
                a(ib, ib6);
                ib.c = null;
            } else {
                a(ib, (IB) null);
            }
            a(ib7, false);
            this.c--;
            this.d++;
            return;
        }
        if (ib5.h > ib6.h) {
            IB ib8 = ib5.c;
            while (true) {
                IB ib9 = ib8;
                ib3 = ib5;
                ib5 = ib9;
                if (ib5 == null) {
                    break;
                } else {
                    ib8 = ib5.c;
                }
            }
        } else {
            IB ib10 = ib6.b;
            while (true) {
                ib2 = ib6;
                ib6 = ib10;
                if (ib6 == null) {
                    break;
                } else {
                    ib10 = ib6.b;
                }
            }
            ib3 = ib2;
        }
        b(ib3, false);
        IB ib11 = ib.b;
        if (ib11 != null) {
            i2 = ib11.h;
            ib3.b = ib11;
            ib11.a = ib3;
            ib.b = null;
        } else {
            i2 = 0;
        }
        IB ib12 = ib.c;
        if (ib12 != null) {
            i3 = ib12.h;
            ib3.c = ib12;
            ib12.a = ib3;
            ib.c = null;
        }
        ib3.h = Math.max(i2, i3) + 1;
        a(ib, ib3);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.b = null;
        this.c = 0;
        this.d++;
        IB ib = this.e;
        ib.e = ib;
        ib.d = ib;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        IB ibA;
        if (obj != null) {
            try {
                ibA = a(obj, false);
            } catch (ClassCastException unused) {
                ibA = null;
            }
        } else {
            ibA = null;
        }
        return ibA != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        EB eb = this.f;
        if (eb != null) {
            return eb;
        }
        EB eb2 = new EB(this);
        this.f = eb2;
        return eb2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        IB ibA;
        if (obj != null) {
            try {
                ibA = a(obj, false);
            } catch (ClassCastException unused) {
                ibA = null;
            }
        } else {
            ibA = null;
        }
        if (ibA != null) {
            return ibA.g;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        GB gb = this.g;
        if (gb != null) {
            return gb;
        }
        GB gb2 = new GB(this);
        this.g = gb2;
        return gb2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("key == null");
        }
        IB ibA = a(obj, true);
        Object obj3 = ibA.g;
        ibA.g = obj2;
        return obj3;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        IB ibA;
        if (obj != null) {
            try {
                ibA = a(obj, false);
            } catch (ClassCastException unused) {
                ibA = null;
            }
        } else {
            ibA = null;
        }
        if (ibA != null) {
            b(ibA, true);
        }
        if (ibA != null) {
            return ibA.g;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.c;
    }

    private void a(IB ib, IB ib2) {
        IB ib3 = ib.a;
        ib.a = null;
        if (ib2 != null) {
            ib2.a = ib3;
        }
        if (ib3 != null) {
            if (ib3.b == ib) {
                ib3.b = ib2;
                return;
            } else {
                if (!i && ib3.c != ib) {
                    throw new AssertionError();
                }
                ib3.c = ib2;
                return;
            }
        }
        this.b = ib2;
    }

    private void b(IB ib) {
        IB ib2 = ib.b;
        IB ib3 = ib.c;
        IB ib4 = ib2.b;
        IB ib5 = ib2.c;
        ib.b = ib5;
        if (ib5 != null) {
            ib5.a = ib;
        }
        a(ib, ib2);
        ib2.c = ib;
        ib.a = ib2;
        int iMax = Math.max(ib3 != null ? ib3.h : 0, ib5 != null ? ib5.h : 0) + 1;
        ib.h = iMax;
        ib2.h = Math.max(iMax, ib4 != null ? ib4.h : 0) + 1;
    }

    private void a(IB ib, boolean z) {
        while (ib != null) {
            IB ib2 = ib.b;
            IB ib3 = ib.c;
            int i2 = ib2 != null ? ib2.h : 0;
            int i3 = ib3 != null ? ib3.h : 0;
            int i4 = i2 - i3;
            if (i4 == -2) {
                IB ib4 = ib3.b;
                IB ib5 = ib3.c;
                int i5 = (ib4 != null ? ib4.h : 0) - (ib5 != null ? ib5.h : 0);
                if (i5 != -1 && (i5 != 0 || z)) {
                    if (!i && i5 != 1) {
                        throw new AssertionError();
                    }
                    b(ib3);
                    a(ib);
                } else {
                    a(ib);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 2) {
                IB ib6 = ib2.b;
                IB ib7 = ib2.c;
                int i6 = (ib6 != null ? ib6.h : 0) - (ib7 != null ? ib7.h : 0);
                if (i6 != 1 && (i6 != 0 || z)) {
                    if (!i && i6 != -1) {
                        throw new AssertionError();
                    }
                    a(ib2);
                    b(ib);
                } else {
                    b(ib);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 0) {
                ib.h = i2 + 1;
                if (z) {
                    return;
                }
            } else {
                if (!i && i4 != -1 && i4 != 1) {
                    throw new AssertionError();
                }
                ib.h = Math.max(i2, i3) + 1;
                if (!z) {
                    return;
                }
            }
            ib = ib.a;
        }
    }

    public JB() {
        this(0);
    }

    private void a(IB ib) {
        IB ib2 = ib.b;
        IB ib3 = ib.c;
        IB ib4 = ib3.b;
        IB ib5 = ib3.c;
        ib.c = ib4;
        if (ib4 != null) {
            ib4.a = ib;
        }
        a(ib, ib3);
        ib3.b = ib;
        ib.a = ib3;
        int iMax = Math.max(ib2 != null ? ib2.h : 0, ib4 != null ? ib4.h : 0) + 1;
        ib.h = iMax;
        ib3.h = Math.max(iMax, ib5 != null ? ib5.h : 0) + 1;
    }
}
