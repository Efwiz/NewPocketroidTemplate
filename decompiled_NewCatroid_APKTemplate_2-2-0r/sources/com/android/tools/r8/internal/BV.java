package com.android.tools.r8.internal;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class BV extends AbstractMap {
    private final int a;
    private List b = Collections.emptyList();
    private Map c = Collections.emptyMap();
    private boolean d;
    private volatile HV e;

    BV(int i) {
        this.a = i;
    }

    static BV d(int i) {
        return new BV(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        if (this.d) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        e();
        if (!this.b.isEmpty()) {
            this.b.clear();
        }
        if (this.c.isEmpty()) {
            return;
        }
        this.c.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return a(comparable) >= 0 || this.c.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        if (this.e == null) {
            this.e = new HV(this, 0);
        }
        return this.e;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iA = a(comparable);
        return iA >= 0 ? ((FV) this.b.get(iA)).getValue() : this.c.get(comparable);
    }

    public final int k() {
        return this.b.size();
    }

    public final Iterable l() {
        return this.c.isEmpty() ? EV.b() : this.c.entrySet();
    }

    public final void m() {
        if (!this.d) {
            for (int i = 0; i < this.b.size(); i++) {
                Map.Entry entryA = a(i);
                if (((C0985Fj) ((InterfaceC1710bi) entryA.getKey())).c) {
                    entryA.setValue(Collections.unmodifiableList((List) entryA.getValue()));
                }
            }
            for (Map.Entry entry : l()) {
                if (((C0985Fj) ((InterfaceC1710bi) entry.getKey())).c) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        if (this.d) {
            return;
        }
        this.c = this.c.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.c);
        this.d = true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        e();
        Comparable comparable = (Comparable) obj;
        int iA = a(comparable);
        if (iA < 0) {
            if (this.c.isEmpty()) {
                return null;
            }
            return this.c.remove(comparable);
        }
        e();
        Object value = ((FV) this.b.remove(iA)).getValue();
        if (this.c.isEmpty()) {
            return value;
        }
        e();
        if (this.c.isEmpty() && !(this.c instanceof TreeMap)) {
            this.c = new TreeMap();
        }
        Iterator it = ((SortedMap) this.c).entrySet().iterator();
        List list = this.b;
        Map.Entry entry = (Map.Entry) it.next();
        list.add(new FV(this, (Comparable) entry.getKey(), entry.getValue()));
        it.remove();
        return value;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.c.size() + this.b.size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // java.util.AbstractMap, java.util.Map
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public Object put(Comparable comparable, Object obj) {
        e();
        int iA = a(comparable);
        if (iA >= 0) {
            return ((FV) this.b.get(iA)).setValue(obj);
        }
        e();
        if (this.b.isEmpty() && !(this.b instanceof ArrayList)) {
            this.b = new ArrayList(this.a);
        }
        int i = -(iA + 1);
        if (i >= this.a) {
            e();
            if (this.c.isEmpty() && !(this.c instanceof TreeMap)) {
                this.c = new TreeMap();
            }
            return ((SortedMap) this.c).put(comparable, obj);
        }
        int size = this.b.size();
        int i2 = this.a;
        if (size == i2) {
            FV fv = (FV) this.b.remove(i2 - 1);
            e();
            if (this.c.isEmpty() && !(this.c instanceof TreeMap)) {
                this.c = new TreeMap();
            }
            ((SortedMap) this.c).put(fv.b(), fv.getValue());
        }
        this.b.add(i, new FV(this, comparable, obj));
        return null;
    }

    public final Map.Entry a(int i) {
        return (Map.Entry) this.b.get(i);
    }

    static void a(int i, BV bv) {
        bv.e();
        ((FV) bv.b.remove(i)).getValue();
        if (bv.c.isEmpty()) {
            return;
        }
        bv.e();
        if (bv.c.isEmpty() && !(bv.c instanceof TreeMap)) {
            bv.c = new TreeMap();
        }
        Iterator it = ((SortedMap) bv.c).entrySet().iterator();
        List list = bv.b;
        Map.Entry entry = (Map.Entry) it.next();
        list.add(new FV(bv, (Comparable) entry.getKey(), entry.getValue()));
        it.remove();
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0026  */
    /* JADX WARN: Code duplicated, block: B:17:0x0041  */
    /* JADX WARN: Code duplicated, block: B:21:0x003f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:22:0x0044 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:23:0x003c A[SYNTHETIC] */
    private int a(Comparable comparable) {
        int i;
        int i2;
        int i3;
        int iCompareTo;
        int size = this.b.size();
        int i4 = size - 1;
        if (i4 < 0) {
            i = 0;
            while (i <= i4) {
                i3 = (i + i4) / 2;
                iCompareTo = comparable.compareTo(((FV) this.b.get(i3)).b());
                if (iCompareTo < 0) {
                    i4 = i3 - 1;
                } else {
                    if (iCompareTo > 0) {
                        return i3;
                    }
                    i = i3 + 1;
                }
            }
            i2 = i + 1;
        } else {
            int iCompareTo2 = comparable.compareTo(((FV) this.b.get(i4)).b());
            if (iCompareTo2 > 0) {
                i2 = size + 1;
            } else {
                if (iCompareTo2 == 0) {
                    return i4;
                }
                i = 0;
                while (i <= i4) {
                    i3 = (i + i4) / 2;
                    iCompareTo = comparable.compareTo(((FV) this.b.get(i3)).b());
                    if (iCompareTo < 0) {
                        i4 = i3 - 1;
                    } else {
                        if (iCompareTo > 0) {
                            return i3;
                        }
                        i = i3 + 1;
                    }
                }
                i2 = i + 1;
            }
        }
        return -i2;
    }
}
