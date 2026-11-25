package com.android.tools.r8.internal;

import com.android.tools.r8.TextInputStream;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.startup.StartupProfileBuilder;
import com.android.tools.r8.startup.StartupProfileProvider;
import com.android.tools.r8.startup.diagnostic.MissingStartupProfileItemsDiagnostic;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3135uW implements InterfaceC1882e1 {
    protected AbstractC3135uW() {
    }

    public static a a() {
        return new a();
    }

    public static C2250ig b() {
        return new C2250ig();
    }

    public abstract AbstractC3135uW a(com.android.tools.r8.graph.u5 u5Var);

    public abstract AbstractC3135uW a(AbstractC1476Wj abstractC1476Wj);

    public abstract <E extends Exception> void a(InterfaceC3905h3<? super CW, E> interfaceC3905h3) throws Exception;

    public abstract AbstractC3135uW b(C0702m c0702m);

    public abstract boolean b(C0767v2 c0767v2);

    public abstract AbstractC3135uW c(C0702m c0702m);

    public abstract boolean c();

    /* JADX INFO: renamed from: com.android.tools.r8.internal.uW$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a implements InterfaceC1809d1, StartupProfileBuilder {
        private final C0722p1 a;
        private final MissingStartupProfileItemsDiagnostic.a b;
        private com.android.tools.r8.utils.O2 c;
        private final StartupProfileProvider d;
        private final LinkedHashMap e;

        a() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.e = new LinkedHashMap();
            this.d = null;
        }

        @Override // com.android.tools.r8.startup.StartupProfileBuilder
        public final StartupProfileBuilder addHumanReadableArtProfile(TextInputStream textInputStream, Consumer consumer) {
            C1269Pk.a aVarA = C1269Pk.a().a(this.c).a(C2824q3.a(this));
            consumer.accept(aVarA);
            aVarA.a().a(textInputStream, this.d.getOrigin());
            return this;
        }

        @Override // com.android.tools.r8.startup.StartupProfileBuilder
        public final StartupProfileBuilder addStartupClass(Consumer consumer) {
            C3293wW c3293wW = new C3293wW(this.a);
            consumer.accept(c3293wW);
            C3362xW c3362xWA = c3293wW.build();
            if (!this.b.a(c3362xWA)) {
                this.e.put(c3362xWA.c(), c3362xWA);
            }
            return this;
        }

        @Override // com.android.tools.r8.startup.StartupProfileBuilder
        public final StartupProfileBuilder addStartupMethod(Consumer consumer) {
            C3440yW c3440yW = new C3440yW(this.a);
            consumer.accept(c3440yW);
            C3510zW c3510zWB = c3440yW.d();
            if (!this.b.a(c3510zWB)) {
                this.e.put(c3510zWB.c(), c3510zWB);
            }
            return this;
        }

        public final void b() {
            this.c = null;
        }

        public final int c() {
            return this.e.size();
        }

        @Override // com.android.tools.r8.internal.InterfaceC1809d1
        public final InterfaceC1809d1 a(InterfaceC2275j1 interfaceC2275j1) {
            interfaceC2275j1.getClass();
            CW cw = (CW) interfaceC2275j1;
            this.e.put(cw.c(), cw);
            return this;
        }

        @Override // com.android.tools.r8.internal.InterfaceC1809d1
        public final a a(C3362xW c3362xW) {
            this.e.put(c3362xW.c(), c3362xW);
            return this;
        }

        a(int i) {
            this.a = null;
            this.b = null;
            this.c = null;
            this.e = new LinkedHashMap(i);
            this.d = null;
        }

        @Override // com.android.tools.r8.internal.InterfaceC1809d1
        public final a a(C3510zW c3510zW) {
            this.e.put(c3510zW.c(), c3510zW);
            return this;
        }

        public final a a(CW cw) {
            this.e.put(cw.c(), cw);
            return this;
        }

        @Override // com.android.tools.r8.internal.InterfaceC1809d1
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public AbstractC3135uW build() {
            if (this.e.isEmpty()) {
                return new C2250ig();
            }
            return new CH(this.e);
        }

        a(com.android.tools.r8.utils.A1 a1, MissingStartupProfileItemsDiagnostic.a aVar, StartupProfileProvider startupProfileProvider) {
            this.a = a1.j0();
            this.b = aVar;
            this.c = a1.e;
            this.e = new LinkedHashMap();
            this.d = startupProfileProvider;
        }
    }

    public static AbstractC3135uW a(ArrayList arrayList) throws Exception {
        final a aVar = new a();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((AbstractC3135uW) it.next()).a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.uW$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.InterfaceC3905h3
                public final void accept(Object obj) {
                    aVar.a((CW) obj);
                }
            });
        }
        return aVar.build();
    }

    public static a a(com.android.tools.r8.utils.A1 a1, MissingStartupProfileItemsDiagnostic.a aVar, StartupProfileProvider startupProfileProvider) {
        return new a(a1, aVar, startupProfileProvider);
    }

    public static AbstractC3135uW a(final C0702m c0702m) {
        return a(c0702m.Y(), new Function() { // from class: com.android.tools.r8.internal.uW$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3135uW.a(c0702m, (Origin) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ MissingStartupProfileItemsDiagnostic.a a(C0702m c0702m, Origin origin) {
        return new MissingStartupProfileItemsDiagnostic.a(c0702m).a(origin);
    }

    public static AbstractC3135uW a(AbstractC0681j0 abstractC0681j0) {
        return a(abstractC0681j0.d, new Function() { // from class: com.android.tools.r8.internal.uW$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3135uW.a((Origin) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ MissingStartupProfileItemsDiagnostic.a a(Origin origin) {
        return MissingStartupProfileItemsDiagnostic.a.c();
    }

    public static AbstractC3135uW a(com.android.tools.r8.utils.A1 a1, Function function) throws Exception {
        AbstractC3135uW abstractC3135uWA;
        if (a1.G0().b()) {
            Collection<StartupProfileProvider> collectionA = a1.G0().a();
            ArrayList arrayList = new ArrayList(collectionA.size());
            for (StartupProfileProvider startupProfileProvider : collectionA) {
                MissingStartupProfileItemsDiagnostic.a aVar = (MissingStartupProfileItemsDiagnostic.a) function.apply(startupProfileProvider.getOrigin());
                a aVarA = a(a1, aVar, startupProfileProvider);
                startupProfileProvider.getStartupProfile(aVarA);
                arrayList.add(aVarA.build());
                if (aVar.b()) {
                    a1.e.warning(aVar.a());
                }
            }
            abstractC3135uWA = a(arrayList);
        } else {
            abstractC3135uWA = null;
        }
        return abstractC3135uWA != null ? abstractC3135uWA : new C2250ig();
    }
}
