package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q4 implements R4, Map {
    private final G4 a;

    public Q4() {
        this(C3011sk.l());
    }

    @Override // com.android.tools.r8.internal.I4
    public final Set a(Object obj) {
        return this.a.containsValue(obj) ? Collections.singleton(c(obj)) : Collections.emptySet();
    }

    @Override // com.android.tools.r8.internal.J4
    public final Object b(Object obj) {
        return this.a.get(obj);
    }

    @Override // com.android.tools.r8.internal.J4
    public final Object c(Object obj) {
        return this.a.g().get(obj);
    }

    @Override // java.util.Map
    public final void clear() {
        this.a.clear();
    }

    @Override // com.android.tools.r8.internal.I4
    public final boolean containsKey(Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.I4
    public final boolean containsValue(Object obj) {
        return this.a.containsValue(obj);
    }

    @Override // com.android.tools.r8.internal.L4
    public final Map e() {
        return this.a;
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return this.a.entrySet();
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
        Object obj3 = this.a.get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    @Override // com.android.tools.r8.internal.I4
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // com.android.tools.r8.internal.L4
    public final Set keySet() {
        return this.a.keySet();
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        return this.a.a(obj, obj2);
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        this.a.putAll(map);
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        return this.a.remove(obj);
    }

    @Override // java.util.Map
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.Map
    public final Collection values() {
        return this.a.values();
    }

    public Q4(C3011sk c3011sk) {
        this.a = c3011sk;
    }

    @Override // com.android.tools.r8.internal.L4
    /* JADX INFO: renamed from: values, reason: collision with other method in class */
    public final Set mo1078values() {
        return this.a.values();
    }

    @Override // com.android.tools.r8.internal.L4
    public final void a(final BiConsumer biConsumer) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Q4$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Q4.a(biConsumer, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(Collections.singleton(obj), obj2);
    }

    public final void a(Q4 q4) {
        q4.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Q4$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.put(obj, obj2);
            }
        });
    }
}
