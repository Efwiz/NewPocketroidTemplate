package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.InterfaceC3205vM;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A2 extends AbstractC3833y2 {
    private final AbstractC1639am a;

    private A2(Collection collection) {
        this.a = AbstractC1639am.a(collection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(G3 g3) {
        return g3.c() != null;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    protected final Iterable c() {
        Stream streamFlatMap = this.a.stream().map(new A2$$ExternalSyntheticLambda2()).flatMap(new Function() { // from class: com.android.tools.r8.shaking.A2$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return A2.a((Iterable) obj);
            }
        });
        Objects.requireNonNull(streamFlatMap);
        return new A2$$ExternalSyntheticLambda4(streamFlatMap);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final int e() {
        return this.a.size();
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || A2.class != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.a, ((A2) obj).a);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final int hashCode() {
        return Objects.hash(this.a);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final void a(StringBuilder sb) {
        AbstractC2963s10 it = this.a.iterator();
        boolean z = true;
        while (it.hasNext()) {
            G3 g3 = (G3) it.next();
            if (!z) {
                sb.append(',');
            }
            sb.append(g3);
            z = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final A2 a(final C0722p1 c0722p1) {
        return new A2((Collection) this.a.stream().map(new Function() { // from class: com.android.tools.r8.shaking.A2$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return A2.a(c0722p1, (G3) obj);
            }
        }).collect(Collectors.toList()));
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final List a() {
        if (this.a.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.shaking.A2$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return A2.b((G3) obj);
            }
        })) {
            return (List) this.a.stream().map(new Function() { // from class: com.android.tools.r8.shaking.A2$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((G3) obj).c();
                }
            }).collect(Collectors.toList());
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2, G3 g3) {
        return g3.b(c0767v2);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3833y2
    public final boolean a(final C0767v2 c0767v2) {
        return AbstractC2734ot.b(this.a, new InterfaceC3205vM() { // from class: com.android.tools.r8.shaking.A2$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return A2.a(c0767v2, (G3) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Stream a(Iterable iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ G3 a(C0722p1 c0722p1, G3 g3) {
        return g3.a(c0722p1);
    }
}
