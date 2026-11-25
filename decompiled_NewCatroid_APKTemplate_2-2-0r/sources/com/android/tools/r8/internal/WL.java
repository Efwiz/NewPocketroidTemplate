package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class WL {
    static final /* synthetic */ boolean a = true;

    public SF a() {
        return null;
    }

    public TF b() {
        return null;
    }

    public AbstractC2309jV c() {
        return null;
    }

    public void d() {
    }

    public abstract String e();

    public boolean f() {
        return this instanceof C2558mi;
    }

    public boolean g() {
        return false;
    }

    public boolean h() {
        return false;
    }

    public boolean i() {
        return false;
    }

    public boolean j() {
        return false;
    }

    protected static Collection a(Collection collection) {
        if (!a && (collection instanceof ArrayList)) {
            throw new AssertionError();
        }
        collection.removeIf(new Ek$$ExternalSyntheticLambda2());
        return collection;
    }

    final void a(boolean z, int i, Collection collection) {
        if (!a && i < 2) {
            throw new AssertionError();
        }
        int i2 = i - 1;
        Iterator it = collection.iterator();
        int size = 0;
        while (it.hasNext()) {
            C2688oE c2688oE = (C2688oE) it.next();
            if (!c2688oE.m()) {
                size += c2688oE.size() - 1;
            }
        }
        if (!a && i2 < size) {
            throw new AssertionError();
        }
    }
}
