package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C2769pI;
import com.android.tools.r8.internal.InterfaceC1912eK;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.z2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3838z2 extends AbstractC3833y2 {
    private final com.android.tools.r8.internal.J0 a;

    private C3838z2(C2769pI c2769pI) {
        this.a = c2769pI;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final List a() {
        return null;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    protected final Iterable c() {
        Stream streamFlatMap = this.a.keySet().stream().map(new A2$$ExternalSyntheticLambda2()).flatMap(new Function() { // from class: com.android.tools.r8.shaking.z2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3838z2.a((Iterable) obj);
            }
        });
        Objects.requireNonNull(streamFlatMap);
        return new A2$$ExternalSyntheticLambda4(streamFlatMap);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final int e() {
        return ((C2769pI) this.a).size();
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3838z2.class != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.a, ((C3838z2) obj).a);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final int hashCode() {
        return Objects.hash(this.a);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final void a(StringBuilder sb) {
        InterfaceC1912eK it = this.a.k().iterator();
        boolean z = true;
        while (it.hasNext()) {
            com.android.tools.r8.internal.I0 i0 = (com.android.tools.r8.internal.I0) it.next();
            if (!z) {
                sb.append(',');
            }
            if (i0.getBooleanValue()) {
                sb.append('!');
            }
            sb.append(((G3) i0.getKey()).toString());
            z = false;
        }
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final boolean a(C0767v2 c0767v2) {
        InterfaceC1912eK it = this.a.k().iterator();
        boolean booleanValue = false;
        while (it.hasNext()) {
            com.android.tools.r8.internal.I0 i0 = (com.android.tools.r8.internal.I0) it.next();
            if (((G3) i0.getKey()).b(c0767v2)) {
                return !i0.getBooleanValue();
            }
            booleanValue = i0.getBooleanValue();
        }
        return booleanValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Stream a(Iterable iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    protected final AbstractC3833y2 a(final C0722p1 c0722p1) {
        final AbstractC3833y2.a aVarB = AbstractC3833y2.b();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.z2$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3838z2.a(aVarB, c0722p1, (G3) obj, (Boolean) obj2);
            }
        });
        return aVarB.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC3833y2.a aVar, C0722p1 c0722p1, G3 g3, Boolean bool) {
        aVar.a(bool.booleanValue(), g3.a(c0722p1));
    }
}
