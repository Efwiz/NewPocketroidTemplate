package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3232vm extends AbstractC2896r4 implements Serializable {
    final transient AbstractC2178hm e;
    final transient int f;

    AbstractC3232vm(int i, AbstractC2178hm abstractC2178hm) {
        this.e = abstractC2178hm;
        this.f = i;
    }

    @Override // com.android.tools.r8.internal.VF
    public final boolean a(Object obj, Object obj2) {
        Collection collection = (Collection) b().get(obj);
        return collection != null && collection.contains(obj2);
    }

    @Override // com.android.tools.r8.internal.E0, com.android.tools.r8.internal.VF
    public final Map b() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.E0, com.android.tools.r8.internal.VF
    public final InterfaceC1600aG c() {
        return (AbstractC0836Am) super.c();
    }

    @Override // com.android.tools.r8.internal.VF
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.VF
    public final boolean containsKey(Object obj) {
        return this.e.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.E0
    final Map d() {
        throw new AssertionError("should never be called");
    }

    @Override // com.android.tools.r8.internal.E0
    final Collection e() {
        return new C3083tm(this);
    }

    @Override // com.android.tools.r8.internal.E0
    final Set f() {
        throw new AssertionError("unreachable");
    }

    @Override // com.android.tools.r8.internal.E0
    final InterfaceC1600aG g() {
        return new C3156um(this);
    }

    @Override // com.android.tools.r8.internal.E0
    final Iterator h() {
        return new C2944rm(this);
    }

    public final int hashCode() {
        return b().hashCode();
    }

    @Override // com.android.tools.r8.internal.E0
    final Spliterator i() {
        return AbstractC3142ub.a(this.e.entrySet().spliterator(), new Function() { // from class: com.android.tools.r8.internal.vm$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3232vm.a((Map.Entry) obj);
            }
        }, 64, this.f);
    }

    @Override // com.android.tools.r8.internal.E0, com.android.tools.r8.internal.VF
    public final Set keySet() {
        return this.e.keySet();
    }

    @Override // com.android.tools.r8.internal.VF
    public final boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.VF
    public final int size() {
        return this.f;
    }

    public final String toString() {
        return b().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map.Entry b(Object obj, Object obj2) {
        return new C1325Rl(obj, obj2);
    }

    @Override // com.android.tools.r8.internal.E0, com.android.tools.r8.internal.VF
    public final Collection a() {
        return (AbstractC1271Pl) super.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Spliterator a(Map.Entry entry) {
        final Object key = entry.getKey();
        return AbstractC3142ub.a(((Collection) entry.getValue()).spliterator(), new Function() { // from class: com.android.tools.r8.internal.vm$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3232vm.b(key, obj);
            }
        });
    }
}
