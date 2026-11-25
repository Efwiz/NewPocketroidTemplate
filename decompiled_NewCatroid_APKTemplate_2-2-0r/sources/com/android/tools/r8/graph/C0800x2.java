package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC3533zt;
import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.graph.x2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0800x2 extends AbstractC0594a1 implements Iterable<C0767v2>, com.android.tools.r8.utils.structural.x<C0800x2> {
    private static final C0800x2 b = new C0800x2();
    static final /* synthetic */ boolean c = true;
    public static final /* synthetic */ int d = 0;
    public final C0767v2[] a;

    private C0800x2() {
        this.a = C0767v2.g;
    }

    public static C0800x2 s0() {
        return b;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final void d(Consumer consumer) {
        for (int length = this.a.length - 1; length >= 0; length--) {
            consumer.accept(this.a[length]);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0800x2) && Arrays.equals(this.a, ((C0800x2) obj).a);
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super C0767v2> consumer) {
        for (C0767v2 c0767v2 : this.a) {
            consumer.accept(c0767v2);
        }
    }

    public final int hashCode() {
        return Arrays.hashCode(this.a);
    }

    public boolean isEmpty() {
        return this.a.length == 0;
    }

    @Override // java.lang.Iterable
    public Iterator<C0767v2> iterator() {
        return AbstractC3533zt.a((Object[]) this.a);
    }

    public C0767v2 j(int i) {
        return this.a[i];
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.x2$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0800x2.a(zVar);
            }
        };
    }

    public int size() {
        return this.a.length;
    }

    public Stream<C0767v2> stream() {
        return Stream.of((Object[]) this.a);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        C0767v2[] c0767v2Arr = this.a;
        if (c0767v2Arr.length > 0) {
            sb.append(c0767v2Arr[0]);
            for (int i = 1; i < this.a.length; i++) {
                sb.append(StringUtil.SPACE).append(this.a[i]);
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.d(new Function() { // from class: com.android.tools.r8.graph.x2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0800x2) obj).a;
            }
        });
    }

    public C0800x2(C0767v2[] c0767v2Arr) {
        if (!c && (c0767v2Arr == null || c0767v2Arr.length <= 0)) {
            throw new AssertionError();
        }
        this.a = c0767v2Arr;
    }

    public static C0800x2 a(C0767v2[] c0767v2Arr) {
        return c0767v2Arr.length == 0 ? s0() : new C0800x2(c0767v2Arr);
    }

    public final C0800x2 a(Function function) {
        if (isEmpty()) {
            return s0();
        }
        C0767v2[] c0767v2Arr = (C0767v2[]) com.android.tools.r8.utils.B.a(this.a, function, C0767v2.g);
        return c0767v2Arr != this.a ? a(c0767v2Arr) : this;
    }

    public boolean a(C0767v2 c0767v2) {
        return com.android.tools.r8.utils.B.b(c0767v2, this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        v.a(this);
    }
}
