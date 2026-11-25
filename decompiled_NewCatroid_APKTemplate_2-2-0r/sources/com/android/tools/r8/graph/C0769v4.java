package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3988x2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.v4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0769v4 extends AbstractC0802x4 {
    static final /* synthetic */ boolean c = true;
    private W0[] a;
    private W0[] b;

    private C0769v4(W0[] w0Arr, W0[] w0Arr2) {
        this.a = w0Arr;
        this.b = w0Arr2;
    }

    private void k() {
        final Set setC = OU.c();
        a(new Consumer() { // from class: com.android.tools.r8.graph.v4$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0769v4.a(setC, (W0) obj);
            }
        }, CM.b());
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(W0[] w0Arr) {
        W0[] w0Arr2 = W0.s;
        if (w0Arr == null) {
            w0Arr = w0Arr2;
        }
        this.a = w0Arr;
        if (c) {
            return;
        }
        k();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void b(W0[] w0Arr) {
        W0[] w0Arr2 = W0.s;
        if (w0Arr == null) {
            w0Arr = w0Arr2;
        }
        this.b = w0Arr;
        if (c) {
            return;
        }
        k();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final Iterable c() {
        if (c || this.a != null) {
            return Arrays.asList(this.a);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final String d() {
        return "<method-arraybacking>";
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final Iterable e() {
        return AbstractC0984Fi.a(Arrays.asList(this.a), Arrays.asList(this.b));
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final int f() {
        return this.a.length;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final int g() {
        return this.b.length;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final int h() {
        return this.a.length + this.b.length;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void i() {
        if (c) {
            return;
        }
        k();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final Iterable j() {
        if (c || this.b != null) {
            return Arrays.asList(this.b);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(W0[] w0Arr) {
        this.b = w0Arr;
    }

    private ArrayList h(Function function) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            W0[] w0Arr = this.a;
            if (i >= w0Arr.length) {
                if (!arrayList.isEmpty()) {
                    this.a = (W0[]) com.android.tools.r8.utils.B.a(this.a, new v4$$ExternalSyntheticLambda2(), W0.s, this.a.length - arrayList.size());
                }
                return arrayList;
            }
            W0 w0 = w0Arr[i];
            W0 w1 = (W0) function.apply(w0);
            if (!c && w1 == null) {
                throw new AssertionError();
            }
            if (w0 != w1) {
                if (w1.f.N()) {
                    this.a[i] = w1;
                } else {
                    this.a[i] = null;
                    arrayList.add(w1);
                }
            }
            i++;
        }
    }

    private ArrayList i(Function function) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            W0[] w0Arr = this.b;
            if (i >= w0Arr.length) {
                break;
            }
            W0 w0 = w0Arr[i];
            W0 w1 = (W0) function.apply(w0);
            if (w0 != w1) {
                if (w1.R0()) {
                    this.b[i] = w1;
                } else {
                    this.b[i] = null;
                    arrayList.add(w1);
                }
            }
            i++;
        }
        if (!arrayList.isEmpty()) {
            this.b = (W0[]) com.android.tools.r8.utils.B.a(this.b, new v4$$ExternalSyntheticLambda2(), W0.s, this.b.length - arrayList.size());
        }
        return arrayList;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final void f(Function function) {
        a(i(function));
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final com.android.tools.r8.utils.D3 g(Function function) {
        for (W0 w0 : this.a) {
            com.android.tools.r8.utils.D3 d3 = (com.android.tools.r8.utils.D3) function.apply(w0);
            if (d3.f()) {
                return d3;
            }
        }
        for (W0 w1 : this.b) {
            com.android.tools.r8.utils.D3 d4 = (com.android.tools.r8.utils.D3) function.apply(w1);
            if (d4.f()) {
                return d4;
            }
        }
        return com.android.tools.r8.utils.D3.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(W0[] w0Arr) {
        this.a = w0Arr;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 c(C0684j2 c0684j2) {
        W0 w0;
        W0 w1;
        W0[] w0Arr = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.graph.v4$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((W0[]) obj);
            }
        };
        int i = 0;
        while (true) {
            w0 = null;
            if (i >= w0Arr.length) {
                w1 = null;
                break;
            }
            if (c0684j2.a(w0Arr[i])) {
                w1 = w0Arr[i];
                W0[] w0Arr2 = new W0[w0Arr.length - 1];
                System.arraycopy(w0Arr, 0, w0Arr2, 0, i);
                System.arraycopy(w0Arr, i + 1, w0Arr2, i, (w0Arr.length - i) - 1);
                consumer.accept(w0Arr2);
                break;
            }
            i++;
        }
        if (w1 != null) {
            if (c || w1.f.N()) {
                return w1;
            }
            throw new AssertionError();
        }
        W0[] w0Arr3 = this.b;
        Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.graph.v4$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d((W0[]) obj);
            }
        };
        for (int i2 = 0; i2 < w0Arr3.length; i2++) {
            if (c0684j2.a(w0Arr3[i2])) {
                w0 = w0Arr3[i2];
                W0[] w0Arr4 = new W0[w0Arr3.length - 1];
                System.arraycopy(w0Arr3, 0, w0Arr4, 0, i2);
                System.arraycopy(w0Arr3, i2 + 1, w0Arr4, i2, (w0Arr3.length - i2) - 1);
                consumer2.accept(w0Arr4);
                break;
            }
        }
        if (c || w0 == null || w0.R0()) {
            return w0;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final void d(Function function) {
        b(h(function));
    }

    public static C0769v4 a(W0[] w0Arr, W0[] w0Arr2) {
        return new C0769v4(w0Arr, w0Arr2);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void b(Collection collection) {
        W0[] w0Arr = new W0[collection.size() + this.b.length];
        W0[] w0Arr2 = this.b;
        System.arraycopy(w0Arr2, 0, w0Arr, 0, w0Arr2.length);
        int length = this.b.length;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            w0Arr[length] = (W0) it.next();
            length++;
        }
        this.b = w0Arr;
        if (c) {
            return;
        }
        k();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final void e(Function function) {
        ArrayList arrayListH = h(function);
        a(i(function));
        b(arrayListH);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, W0 w0) {
        boolean zAdd = set.add(w0.getReference());
        if (c || zAdd) {
            return;
        }
        throw new AssertionError("Duplicate method `" + w0.getReference().r0() + "`");
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(Collection collection) {
        W0[] w0Arr = new W0[collection.size() + this.a.length];
        W0[] w0Arr2 = this.a;
        System.arraycopy(w0Arr2, 0, w0Arr, 0, w0Arr2.length);
        int length = this.a.length;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            w0Arr[length] = (W0) it.next();
            length++;
        }
        this.a = w0Arr;
        if (c) {
            return;
        }
        k();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void b() {
        this.b = W0.s;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 b(C0684j2 c0684j2) {
        for (W0 w0 : this.b) {
            if (c0684j2.a(w0)) {
                return w0;
            }
        }
        return null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a() {
        this.a = W0.s;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 b(Predicate predicate) {
        return (W0) C3988x2.a(this.b, predicate);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(Set set) {
        W0[] w0Arr = this.a;
        ArrayList arrayList = new ArrayList(w0Arr.length);
        for (W0 w0 : w0Arr) {
            if (!set.contains(w0)) {
                arrayList.add(w0);
            }
        }
        this.a = (W0[]) arrayList.toArray(W0.s);
        W0[] w0Arr2 = this.b;
        ArrayList arrayList2 = new ArrayList(w0Arr2.length);
        for (W0 w1 : w0Arr2) {
            if (!set.contains(w1)) {
                arrayList2.add(w1);
            }
        }
        this.b = (W0[]) arrayList2.toArray(W0.s);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final W0 b(C0684j2 c0684j2, Function function) {
        int i = 0;
        while (true) {
            W0[] w0Arr = this.a;
            if (i >= w0Arr.length) {
                return null;
            }
            W0 w0 = w0Arr[i];
            if (c0684j2.a(w0)) {
                W0 w1 = (W0) function.apply(w0);
                if (!c && !w1.R0()) {
                    throw new AssertionError();
                }
                W0[] w0Arr2 = this.a;
                Consumer consumer = new Consumer() { // from class: com.android.tools.r8.graph.v4$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.e((W0[]) obj);
                    }
                };
                W0 w2 = w0Arr2[i];
                W0[] w0Arr3 = new W0[w0Arr2.length - 1];
                System.arraycopy(w0Arr2, 0, w0Arr3, 0, i);
                System.arraycopy(w0Arr2, i + 1, w0Arr3, i, (w0Arr2.length - i) - 1);
                consumer.accept(w0Arr3);
                c(w1);
                return w1;
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(W0[] w0Arr) {
        this.a = w0Arr;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void c(W0 w0) {
        if (!c && !w0.R0()) {
            throw new AssertionError();
        }
        this.b = (W0[]) com.android.tools.r8.utils.B.a(w0, this.b);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final void b(Function function) {
        W0[] w0Arr = this.a;
        this.a = W0.s;
        W0[] w0Arr2 = new W0[w0Arr.length];
        for (int i = 0; i < w0Arr.length; i++) {
            w0Arr2[i] = (W0) function.apply(w0Arr[i]);
        }
        this.a = w0Arr2;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final void c(Function function) {
        W0[] w0Arr = this.b;
        this.b = W0.s;
        W0[] w0Arr2 = new W0[w0Arr.length];
        for (int i = 0; i < w0Arr.length; i++) {
            w0Arr2[i] = (W0) function.apply(w0Arr[i]);
        }
        this.b = w0Arr2;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(LinkedHashSet linkedHashSet) {
        int length = this.b.length;
        int length2 = this.a.length;
        int size = linkedHashSet.size();
        if (!c && size > length2) {
            throw new AssertionError();
        }
        int i = length2 - size;
        W0[] w0Arr = new W0[i];
        int i2 = 0;
        for (int i3 = 0; i3 < length2; i3++) {
            W0 w0 = this.a[i3];
            if (!linkedHashSet.contains(w0)) {
                w0Arr[i2] = w0;
                i2++;
            }
        }
        if (!c && i2 != i) {
            throw new AssertionError();
        }
        a(w0Arr);
        W0[] w0Arr2 = new W0[size + length];
        System.arraycopy(this.b, 0, w0Arr2, 0, length);
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            w0Arr2[length] = (W0) it.next();
            length++;
        }
        b(w0Arr2);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void b(W0 w0) {
        if (w0.f.N()) {
            a(w0);
        } else {
            c(w0);
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 a(C0684j2 c0684j2) {
        for (W0 w0 : this.a) {
            if (c0684j2.a(w0)) {
                return w0;
            }
        }
        return null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 a(Predicate predicate) {
        return (W0) C3988x2.a(this.a, predicate);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 a(C0737r2 c0737r2, C0759u2 c0759u2) {
        W0 w0;
        W0[] w0Arr = this.a;
        int length = w0Arr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                w0 = null;
                break;
            }
            w0 = w0Arr[i];
            C0684j2 reference = w0.getReference();
            if (reference.h == c0737r2 && reference.f == c0759u2) {
                break;
            }
            i++;
        }
        if (w0 != null) {
            return w0;
        }
        for (W0 w1 : this.b) {
            C0684j2 reference2 = w1.getReference();
            if (reference2.h == c0737r2 && reference2.f == c0759u2) {
                return w1;
            }
        }
        return null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(W0 w0) {
        if (!c && !w0.f.N()) {
            throw new AssertionError();
        }
        this.a = (W0[]) com.android.tools.r8.utils.B.a(w0, this.a);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final W0 a(C0684j2 c0684j2, Function function) {
        W0 w0;
        W0[] w0Arr = this.a;
        int i = 0;
        while (true) {
            if (i >= w0Arr.length) {
                w0 = null;
                break;
            }
            W0 w1 = w0Arr[i];
            if (c0684j2.a(w1)) {
                w0 = (W0) function.apply(w1);
                w0Arr[i] = w0;
                break;
            }
            i++;
        }
        if (c || w0 == null || w0.f.N()) {
            return w0;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final AbstractC0802x4 a(Function function) {
        W0[] w0Arr = new W0[this.a.length];
        W0[] w0Arr2 = new W0[this.b.length];
        int i = 0;
        int i2 = 0;
        while (true) {
            W0[] w0Arr3 = this.a;
            if (i2 < w0Arr3.length) {
                W0 w0 = (W0) function.apply(w0Arr3[i2]);
                w0Arr[i2] = w0;
                if (!c && !w0.f.N()) {
                    throw new AssertionError();
                }
                i2++;
            } else {
                while (true) {
                    W0[] w0Arr4 = this.b;
                    if (i < w0Arr4.length) {
                        W0 w1 = (W0) function.apply(w0Arr4[i]);
                        w0Arr2[i] = w1;
                        if (!c && !w1.R0()) {
                            throw new AssertionError();
                        }
                        i++;
                    } else {
                        return new C0769v4(w0Arr, w0Arr2);
                    }
                }
            }
        }
    }
}
