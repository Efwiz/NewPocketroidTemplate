package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class E0 implements VF {
    private transient Collection a;
    private transient Set b;
    private transient InterfaceC1600aG c;
    private transient Map d;

    E0() {
    }

    @Override // com.android.tools.r8.internal.VF
    public Collection a() {
        Collection collection = this.a;
        if (collection != null) {
            return collection;
        }
        Collection collectionE = e();
        this.a = collectionE;
        return collectionE;
    }

    @Override // com.android.tools.r8.internal.VF
    public Map b() {
        Map map = this.d;
        if (map != null) {
            return map;
        }
        Map mapD = d();
        this.d = mapD;
        return mapD;
    }

    @Override // com.android.tools.r8.internal.VF
    public InterfaceC1600aG c() {
        InterfaceC1600aG interfaceC1600aG = this.c;
        if (interfaceC1600aG != null) {
            return interfaceC1600aG;
        }
        InterfaceC1600aG interfaceC1600aGG = g();
        this.c = interfaceC1600aGG;
        return interfaceC1600aGG;
    }

    abstract Map d();

    abstract Collection e();

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof VF) {
            return b().equals(((VF) obj).b());
        }
        return false;
    }

    abstract Set f();

    abstract InterfaceC1600aG g();

    abstract Iterator h();

    abstract Spliterator i();

    @Override // com.android.tools.r8.internal.VF
    public Set keySet() {
        Set set = this.b;
        if (set != null) {
            return set;
        }
        Set setF = f();
        this.b = setF;
        return setF;
    }
}
