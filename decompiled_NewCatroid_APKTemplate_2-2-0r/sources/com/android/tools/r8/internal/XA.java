package com.android.tools.r8.internal;

import java.io.UnsupportedEncodingException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class XA extends AbstractList implements YA, RandomAccess {
    public static final C3178v10 b = new C3178v10(new XA());
    private final ArrayList a;

    public XA() {
        this.a = new ArrayList();
    }

    @Override // com.android.tools.r8.internal.YA
    public final void a(Y5 y5) {
        this.a.add(y5);
        ((AbstractList) this).modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        this.a.add(i, (String) obj);
        ((AbstractList) this).modCount++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        return addAll(this.a.size(), collection);
    }

    @Override // com.android.tools.r8.internal.YA
    public final List c0() {
        return Collections.unmodifiableList(this.a);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        this.a.clear();
        ((AbstractList) this).modCount++;
    }

    @Override // com.android.tools.r8.internal.YA
    public final Y5 g(int i) {
        Y5 c2904rC;
        Object obj = this.a.get(i);
        if (obj instanceof Y5) {
            c2904rC = (Y5) obj;
        } else if (obj instanceof String) {
            c2904rC = Y5.a((String) obj);
        } else {
            byte[] bArr = (byte[]) obj;
            int length = bArr.length;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, length);
            c2904rC = new C2904rC(bArr2);
        }
        if (c2904rC != obj) {
            this.a.set(i, c2904rC);
        }
        return c2904rC;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        String str;
        Object obj = this.a.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Y5) {
            Y5 y5 = (Y5) obj;
            y5.getClass();
            try {
                str = y5.f();
                if (y5.c()) {
                    this.a.set(i, str);
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UTF-8 not supported?", e);
            }
        } else {
            byte[] bArr = (byte[]) obj;
            byte[] bArr2 = AbstractC3019ss.a;
            try {
                str = new String(bArr, "UTF-8");
                if (O10.a(bArr, 0, bArr.length) == 0) {
                    this.a.set(i, str);
                }
            } catch (UnsupportedEncodingException e2) {
                throw new RuntimeException("UTF-8 not supported?", e2);
            }
        }
        return str;
    }

    @Override // com.android.tools.r8.internal.YA
    public final C3178v10 h() {
        return new C3178v10(this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        Object objRemove = this.a.remove(i);
        ((AbstractList) this).modCount++;
        if (objRemove instanceof String) {
            return (String) objRemove;
        }
        if (objRemove instanceof Y5) {
            Y5 y5 = (Y5) objRemove;
            y5.getClass();
            try {
                return y5.f();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UTF-8 not supported?", e);
            }
        }
        byte[] bArr = (byte[]) objRemove;
        byte[] bArr2 = AbstractC3019ss.a;
        try {
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("UTF-8 not supported?", e2);
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        Object obj2 = this.a.set(i, (String) obj);
        if (obj2 instanceof String) {
            return (String) obj2;
        }
        if (obj2 instanceof Y5) {
            Y5 y5 = (Y5) obj2;
            y5.getClass();
            try {
                return y5.f();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UTF-8 not supported?", e);
            }
        }
        byte[] bArr = (byte[]) obj2;
        byte[] bArr2 = AbstractC3019ss.a;
        try {
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("UTF-8 not supported?", e2);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.a.size();
    }

    public XA(YA ya) {
        this.a = new ArrayList(ya.size());
        addAll(ya);
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        if (collection instanceof YA) {
            collection = ((YA) collection).c0();
        }
        boolean zAddAll = this.a.addAll(i, collection);
        ((AbstractList) this).modCount++;
        return zAddAll;
    }
}
