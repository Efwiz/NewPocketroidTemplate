package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class O4 implements I4 {
    private final Map a;
    private final Map b;

    public O4() {
        this(new IdentityHashMap(), new IdentityHashMap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set f(Object obj) {
        return new LinkedHashSet();
    }

    public Object c(Object obj) {
        return d(obj);
    }

    @Override // com.android.tools.r8.internal.I4
    public final boolean containsKey(Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.I4
    public final boolean containsValue(Object obj) {
        return this.b.containsKey(obj);
    }

    public final Object d(Object obj) {
        return this.b.get(obj);
    }

    public final Set e(Object obj) {
        return (Set) this.a.getOrDefault(obj, Collections.emptySet());
    }

    @Override // com.android.tools.r8.internal.I4
    public final void forEach(final BiConsumer biConsumer) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.O4$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                O4.a(biConsumer, obj, (Set) obj2);
            }
        });
    }

    public Object g(Object obj) {
        Object objRemove = this.b.remove(obj);
        if (objRemove != null) {
            Set set = (Set) this.a.get(objRemove);
            set.remove(obj);
            if (set.isEmpty()) {
                this.a.remove(objRemove);
            }
        }
        return objRemove;
    }

    private O4(IdentityHashMap identityHashMap, IdentityHashMap identityHashMap2) {
        this.a = identityHashMap;
        this.b = identityHashMap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final BiConsumer biConsumer, final Object obj, Set set) {
        set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.O4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                biConsumer.accept(obj, obj2);
            }
        });
    }

    public final Object a(C0684j2 c0684j2, C0684j2 c0684j3) {
        return this.b.getOrDefault(c0684j2, c0684j3);
    }

    public final void a(Object obj, Object obj2) {
        g(obj2);
        ((Set) this.a.computeIfAbsent(obj, new Function() { // from class: com.android.tools.r8.internal.O4$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj3) {
                return O4.f(obj3);
            }
        })).add(obj2);
        this.b.put(obj2, obj);
    }
}
