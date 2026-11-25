package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C3405y10;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.o3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3775o3 {
    private final List a;
    private final C3774o2 b;
    private final C3774o2 c;
    private final EnumC3791r3 d;
    private final G3 e;
    private final AbstractC3815v3 f;
    private final List g;
    private final C3786q3 h;

    /* JADX INFO: renamed from: com.android.tools.r8.shaking.o3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean i = true;
        private List a;
        private C3774o2 b;
        private C3774o2 c;
        private EnumC3791r3 d;
        private G3 e;
        private AbstractC3815v3 f;
        private List g;
        private C3786q3 h;

        private a() {
            this.a = Collections.emptyList();
            this.b = new C3774o2();
            this.c = new C3774o2();
        }

        public final void a(List list) {
            if (!i && list == null) {
                throw new AssertionError();
            }
            this.a = list;
        }

        public final C3774o2 b() {
            return this.b;
        }

        public final C3774o2 c() {
            return this.c;
        }

        public final boolean d() {
            return this.d != null;
        }

        public final void b(List list) {
            this.g = list;
        }

        public final a a(C3774o2 c3774o2) {
            this.b = c3774o2;
            return this;
        }

        public a a(EnumC3791r3 enumC3791r3) {
            this.d = enumC3791r3;
            return this;
        }

        public final a a(G3 g3) {
            this.e = g3;
            return this;
        }

        public final a a(N2.a aVar) {
            this.f = AbstractC3815v3.a(aVar);
            return this;
        }

        public final a a(C3786q3 c3786q3) {
            this.h = c3786q3;
            return this;
        }

        public C3775o3 a() {
            if (i || d()) {
                return new C3775o3(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
            }
            throw new AssertionError();
        }
    }

    private C3775o3(List list, C3774o2 c3774o2, C3774o2 c3774o3, EnumC3791r3 enumC3791r3, G3 g3, AbstractC3815v3 abstractC3815v3, List list2, C3786q3 c3786q3) {
        this.a = list;
        this.b = c3774o2;
        this.c = c3774o3;
        this.d = enumC3791r3;
        this.e = g3;
        this.f = abstractC3815v3;
        this.g = list2 != null ? Collections.unmodifiableList(list2) : null;
        this.h = c3786q3;
    }

    public static a a() {
        return new a();
    }

    public static C3775o3 b() {
        a aVar = new a();
        aVar.a(EnumC3791r3.ALL);
        return aVar.a();
    }

    public C3774o2 c() {
        return this.b;
    }

    public List<G3> d() {
        return this.a;
    }

    public List<G3> e() {
        return this.g;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C3775o3)) {
            return false;
        }
        C3775o3 c3775o3 = (C3775o3) obj;
        if (!this.a.equals(c3775o3.a) || !this.b.equals(c3775o3.b) || !this.c.equals(c3775o3.c) || this.d != c3775o3.d) {
            return false;
        }
        AbstractC3815v3 abstractC3815v3 = this.f;
        if (abstractC3815v3 == null ? c3775o3.f != null : !abstractC3815v3.equals(c3775o3.f)) {
            return false;
        }
        G3 g3 = this.e;
        if (g3 == null ? c3775o3.e != null : !g3.equals(c3775o3.e)) {
            return false;
        }
        List list = this.g;
        List list2 = c3775o3.g;
        if (list != null) {
            return list.equals(list2);
        }
        return list2 == null;
    }

    public AbstractC3815v3 f() {
        return this.f;
    }

    public C3774o2 g() {
        return this.c;
    }

    public C3786q3 h() {
        return this.h;
    }

    public final int hashCode() {
        int iHashCode = (this.c.hashCode() + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31)) * 31;
        EnumC3791r3 enumC3791r3 = this.d;
        int iHashCode2 = (iHashCode + (enumC3791r3 != null ? enumC3791r3.hashCode() : 0)) * 31;
        G3 g3 = this.e;
        int iHashCode3 = (iHashCode2 + (g3 != null ? g3.hashCode() : 0)) * 31;
        AbstractC3815v3 abstractC3815v3 = this.f;
        int iHashCode4 = (iHashCode3 + (abstractC3815v3 != null ? abstractC3815v3.hashCode() : 0)) * 31;
        List list = this.g;
        return iHashCode4 + (list != null ? list.hashCode() : 0);
    }

    public EnumC3791r3 i() {
        return this.d;
    }

    public G3 j() {
        return this.e;
    }

    final Iterable k() {
        Iterable a2$$ExternalSyntheticLambda4;
        ArrayList arrayListA = G3.a(this.a);
        Iterable iterableA = G3.a(this.e);
        Iterable iterableA2 = AbstractC3815v3.a(this.f);
        List list = this.g;
        if (list != null) {
            Stream streamFlatMap = list.stream().map(new A2$$ExternalSyntheticLambda2()).flatMap(new Function() { // from class: com.android.tools.r8.shaking.o3$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3775o3.a((Iterable) obj);
                }
            });
            Objects.requireNonNull(streamFlatMap);
            a2$$ExternalSyntheticLambda4 = new A2$$ExternalSyntheticLambda4(streamFlatMap);
        } else {
            a2$$ExternalSyntheticLambda4 = new Iterable() { // from class: com.android.tools.r8.shaking.o3$$ExternalSyntheticLambda2
                @Override // java.lang.Iterable
                public final Iterator iterator() {
                    return Collections.emptyIterator();
                }
            };
        }
        return AbstractC0984Fi.a(arrayListA, iterableA, iterableA2, a2$$ExternalSyntheticLambda4);
    }

    public boolean l() {
        return this.h != null;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x009f  */
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            C3732h3.a(sb, PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT, (G3) it.next());
        }
        C3732h3.a(sb, (String) null, this.b);
        C3732h3.a(sb, (String) null, this.c.toString().replace(" ", " !"));
        switch (AbstractC3770n3.a[i().ordinal()]) {
            case 1:
                sb.append("*");
                if (l()) {
                    sb.append(this.h.toString());
                }
                return sb.toString();
            case 2:
                sb.append("<fields>");
                if (l()) {
                    sb.append(this.h.toString());
                }
                return sb.toString();
            case 3:
                sb.append(j());
                sb.append(StringUtil.SPACE);
                sb.append(f());
                if (l()) {
                    sb.append(this.h.toString());
                }
                return sb.toString();
            case 4:
                sb.append("<methods>");
                if (l()) {
                    sb.append(this.h.toString());
                }
                return sb.toString();
            case 8:
                sb.append(j());
                sb.append(StringUtil.SPACE);
            case 5:
            case 6:
            case 7:
                sb.append(f());
                sb.append('(');
                sb.append(com.android.tools.r8.utils.Y2.a(",", e()));
                sb.append(')');
                if (l()) {
                    sb.append(this.h.toString());
                }
                return sb.toString();
            default:
                throw new C3405y10("Unknown kind of member rule");
        }
    }

    public final boolean a(AbstractC0735r0 abstractC0735r0, C0702m c0702m, Consumer consumer, B b) {
        com.android.tools.r8.graph.Y0 y0A = c0702m.E().a(abstractC0735r0.getReference());
        int i = AbstractC3770n3.a[i().ordinal()];
        if (i == 1 || i == 2) {
            if (c().a(abstractC0735r0.getAccessFlags()) && g().b(abstractC0735r0.getAccessFlags())) {
                return C3707d4.a(this.a, abstractC0735r0, consumer);
            }
            return false;
        }
        if (i != 3) {
            return false;
        }
        if (f().a(b.a(y0A.f)) && c().a(abstractC0735r0.getAccessFlags()) && g().b(abstractC0735r0.getAccessFlags()) && j().a(c0702m, y0A.h)) {
            return C3707d4.a(this.a, abstractC0735r0, consumer);
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:22:0x0075  */
    /* JADX WARN: Code duplicated, block: B:24:0x0082 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:28:0x0091  */
    /* JADX WARN: Code duplicated, block: B:31:0x0095  */
    /* JADX WARN: Code duplicated, block: B:34:0x00a4 A[LOOP:0: B:29:0x0092->B:34:0x00a4, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:39:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:47:0x00a3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:9:0x0032  */
    public final boolean a(AbstractC0748t0 abstractC0748t0, C0702m c0702m, Consumer consumer, B b) {
        List<G3> listE;
        C0767v2[] c0767v2Arr;
        int i;
        G3 g3;
        C0684j2 c0684j2A = c0702m.E().a((AbstractC1476Wj) null, abstractC0748t0.getReference());
        int i2 = AbstractC3770n3.a[i().ordinal()];
        if (i2 != 1) {
            switch (i2) {
                case 4:
                    if (!abstractC0748t0.e().u1()) {
                        if (c().a(abstractC0748t0.getAccessFlags()) && g().b(abstractC0748t0.getAccessFlags())) {
                            return C3707d4.a(this.a, abstractC0748t0, consumer);
                        }
                    }
                    break;
                case 5:
                case 6:
                case 7:
                    if (!f().a(b.a(c0684j2A.f)) || !c().a(abstractC0748t0.getAccessFlags()) || !g().b(abstractC0748t0.getAccessFlags()) || !C3707d4.a(this.a, abstractC0748t0, consumer)) {
                        return false;
                    }
                    listE = e();
                    if (listE.size() == 1) {
                        g3 = listE.get(0);
                        g3.getClass();
                        if (g3 instanceof I3) {
                            return true;
                        }
                    }
                    c0767v2Arr = c0684j2A.G0().a;
                    if (c0767v2Arr.length == listE.size()) {
                        for (i = 0; i < c0767v2Arr.length; i++) {
                            if (!listE.get(i).a(c0702m, c0767v2Arr[i])) {
                                return false;
                            }
                        }
                        return true;
                    }
                    break;
                case 8:
                    if (this.e.a(c0702m, c0684j2A.I0())) {
                        if (!f().a(b.a(c0684j2A.f))) {
                            listE = e();
                            if (listE.size() == 1) {
                                g3 = listE.get(0);
                                g3.getClass();
                                if (g3 instanceof I3) {
                                    return true;
                                }
                            }
                            c0767v2Arr = c0684j2A.G0().a;
                            if (c0767v2Arr.length == listE.size()) {
                                while (i < c0767v2Arr.length) {
                                    if (!listE.get(i).a(c0702m, c0767v2Arr[i])) {
                                        return false;
                                    }
                                }
                                return true;
                            }
                        }
                    }
                    break;
            }
        } else if (c().a(abstractC0748t0.getAccessFlags())) {
            return C3707d4.a(this.a, abstractC0748t0, consumer);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Stream a(Iterable iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    final C3775o3 a(final C0722p1 c0722p1) {
        return new C3775o3(G3.a(c0722p1, d()), c(), g(), i(), j() == null ? null : j().a(c0722p1), f() == null ? null : f().b(), e() == null ? null : (List) e().stream().map(new Function() { // from class: com.android.tools.r8.shaking.o3$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3775o3.a(c0722p1, (G3) obj);
            }
        }).collect(Collectors.toList()), h());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ G3 a(C0722p1 c0722p1, G3 g3) {
        return g3.a(c0722p1);
    }
}
