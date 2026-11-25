package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.OU;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class X {
    static final /* synthetic */ boolean c = true;
    private final Map a;
    private final AtomicReference b;

    X(ConcurrentHashMap concurrentHashMap, AbstractC3876c0 abstractC3876c0) {
        AtomicReference atomicReference = new AtomicReference();
        this.b = atomicReference;
        if (!c && abstractC3876c0 != null && abstractC3876c0.b() != e()) {
            throw new AssertionError();
        }
        this.a = concurrentHashMap == null ? new ConcurrentHashMap() : concurrentHashMap;
        atomicReference.set(abstractC3876c0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Supplier a(C0767v2 c0767v2, C0767v2 c0767v3) {
        if (this.b.get() == null) {
            return null;
        }
        return new W(this, (AbstractC3876c0) this.b.get(), c0767v2);
    }

    abstract AbstractC0727q0 a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1);

    abstract Supplier a(AbstractC0727q0 abstractC0727q0);

    public final AbstractC0727q0 b(final C0767v2 c0767v2) {
        Supplier supplier;
        if (this.b.get() == null) {
            Supplier supplier2 = (Supplier) this.a.get(c0767v2);
            if (supplier2 == null) {
                return null;
            }
            return (AbstractC0727q0) supplier2.get();
        }
        Supplier supplier3 = (Supplier) this.a.get(c0767v2);
        if (supplier3 != null) {
            return (AbstractC0727q0) supplier3.get();
        }
        synchronized (this) {
            supplier = (Supplier) this.a.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.utils.X$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(c0767v2, (C0767v2) obj);
                }
            });
        }
        if (supplier == null) {
            return null;
        }
        return (AbstractC0727q0) supplier.get();
    }

    public final AbstractC2178hm c() {
        if (this.b.get() != null) {
            throw new C3405y10("Getting all classes from not fully loaded collection.");
        }
        C1946em c1946emE = AbstractC2178hm.e();
        for (Map.Entry entry : this.a.entrySet()) {
            c1946emE.a((C0767v2) entry.getKey(), (AbstractC0727q0) ((Supplier) entry.getValue()).get());
        }
        return c1946emE.a();
    }

    public final Set d() {
        return this.a.keySet();
    }

    abstract com.android.tools.r8.graph.I e();

    public final boolean f() {
        return this.b.get() == null;
    }

    public String toString() {
        return this.a.size() + " loaded, provider: " + Objects.toString(this.b.get());
    }

    public final void a(C0767v2 c0767v2) {
        this.a.remove(c0767v2);
        AbstractC3876c0 abstractC3876c0 = (AbstractC3876c0) this.b.get();
        if (abstractC3876c0 == null) {
            return;
        }
        this.b.set(abstractC3876c0.a(AbstractC1048Hm.c(c0767v2)));
    }

    public final Collection a() {
        AbstractC3876c0 abstractC3876c0 = (AbstractC3876c0) this.b.get();
        if (abstractC3876c0 != null) {
            return abstractC3876c0.a();
        }
        throw new C1367Tb("Cannot access all types since the classProvider is no longer available");
    }

    public final void a(Predicate predicate) {
        AbstractC0727q0 abstractC0727q0;
        if (f()) {
            return;
        }
        AbstractC3876c0 abstractC3876c0 = (AbstractC3876c0) this.b.get();
        Set<C0767v2> setC = OU.c();
        setC.addAll(this.a.keySet());
        setC.addAll(abstractC3876c0.a());
        for (C0767v2 c0767v2 : setC) {
            if (predicate.test(c0767v2)) {
                b(c0767v2);
            }
        }
        synchronized (this) {
            if (this.b.get() == null) {
                return;
            }
            Iterator it = this.a.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (setC.contains(entry.getKey()) && (abstractC0727q0 = (AbstractC0727q0) ((Supplier) entry.getValue()).get()) != null) {
                    if (!c && abstractC0727q0.d != entry.getKey()) {
                        throw new AssertionError();
                    }
                    entry.setValue(a(abstractC0727q0));
                } else {
                    it.remove();
                }
            }
            this.b.set(null);
        }
    }

    public final ArrayList b() {
        if (this.b.get() == null) {
            ArrayList arrayList = new ArrayList();
            Iterator it = this.a.values().iterator();
            while (it.hasNext()) {
                AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) ((Supplier) it.next()).get();
                if (!c && abstractC0727q0 == null) {
                    throw new AssertionError();
                }
                arrayList.add(abstractC0727q0);
            }
            return arrayList;
        }
        throw new C3405y10("Getting all classes from not fully loaded collection.");
    }
}
