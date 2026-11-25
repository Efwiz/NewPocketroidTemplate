package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class K4 implements InterfaceC2220iG {
    static final /* synthetic */ boolean c = true;
    private final Map a;
    private final Map b;

    protected K4(AbstractMap abstractMap, AbstractMap abstractMap2) {
        this.a = abstractMap;
        this.b = abstractMap2;
    }

    public static K4 b() {
        return new K4(new IdentityHashMap(), new IdentityHashMap());
    }

    public static K4 c() {
        return new K4(new LinkedHashMap(), new LinkedHashMap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set d(Object obj) {
        return new LinkedHashSet();
    }

    public void a() {
        this.a.clear();
        this.b.clear();
    }

    @Override // com.android.tools.r8.internal.I4
    public final boolean containsKey(Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.I4
    public final boolean containsValue(Object obj) {
        return this.b.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.L4
    public final Map e() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.I4
    public final void forEach(BiConsumer biConsumer) {
        this.a.forEach(biConsumer);
    }

    @Override // com.android.tools.r8.internal.L4
    public final Object get(Object obj) {
        return this.a.get(obj);
    }

    @Override // com.android.tools.r8.internal.L4
    public final Object getOrDefault(Object obj, Object obj2) {
        return this.a.getOrDefault(obj, obj2);
    }

    @Override // com.android.tools.r8.internal.I4
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // com.android.tools.r8.internal.L4
    public final Set keySet() {
        return this.a.keySet();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2220iG
    /* JADX INFO: renamed from: put, reason: merged with bridge method [inline-methods] */
    public final Object a(Object obj, Object obj2) {
        Object objE = e(obj);
        this.a.put(obj, obj2);
        ((Set) this.b.computeIfAbsent(obj2, new Function() { // from class: com.android.tools.r8.internal.K4$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj3) {
                return K4.d(obj3);
            }
        })).add(obj);
        return objE;
    }

    @Override // com.android.tools.r8.internal.L4
    /* JADX INFO: renamed from: values */
    public final Set mo1078values() {
        return this.b.keySet();
    }

    public Object b(Object obj) {
        return get(obj);
    }

    public Object e(Object obj) {
        Object objRemove = this.a.remove(obj);
        if (objRemove != null) {
            Set set = (Set) this.b.get(objRemove);
            set.remove(obj);
            if (set.isEmpty()) {
                this.b.remove(objRemove);
            }
        }
        return objRemove;
    }

    @Override // com.android.tools.r8.internal.L4
    public final void a(final BiConsumer biConsumer) {
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.K4$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                biConsumer.accept((Set) obj2, obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.I4
    public final Set a(Object obj) {
        return (Set) this.b.getOrDefault(obj, Collections.emptySet());
    }

    public final void a(Set set) {
        set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.K4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.e(obj);
            }
        });
    }

    public Set a(C0684j2 c0684j2) {
        Set set = (Set) this.b.remove(c0684j2);
        if (set == null) {
            return Collections.emptySet();
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object objRemove = this.a.remove(it.next());
            if (!c && objRemove != c0684j2) {
                throw new AssertionError();
            }
        }
        return set;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2220iG
    public final void a(Iterable iterable, final Object obj) {
        iterable.forEach(new Consumer() { // from class: com.android.tools.r8.internal.K4$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                this.f$0.a(obj, obj2);
            }
        });
    }
}
