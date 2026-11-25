package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.utils.C3970u0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class G3 {

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    static final class a {
        public static final a a = new a("CLASS", 0);
        public static final a b = new a("TYPE", 1);

        private a(String str, int i) {
            super(str, i);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b extends G3 {
        public final C0767v2 a;

        private b(C0767v2 c0767v2) {
            super();
            this.a = c0767v2;
        }

        @Override // com.android.tools.r8.shaking.G3
        public final b a() {
            return this;
        }

        @Override // com.android.tools.r8.shaking.G3
        public final boolean b(C0767v2 c0767v2) {
            return this.a == c0767v2;
        }

        @Override // com.android.tools.r8.shaking.G3
        public C0767v2 c() {
            return this.a;
        }

        @Override // com.android.tools.r8.shaking.G3
        public final boolean equals(Object obj) {
            if (obj instanceof b) {
                return this.a.equals(((b) obj).a);
            }
            return false;
        }

        @Override // com.android.tools.r8.shaking.G3
        public final int hashCode() {
            return this.a.hashCode();
        }

        @Override // com.android.tools.r8.shaking.G3
        public final String toString() {
            return this.a.r0();
        }
    }

    private G3() {
    }

    public static G3 b() {
        return H3.b;
    }

    public b a() {
        return null;
    }

    protected G3 a(C0722p1 c0722p1) {
        return this;
    }

    public abstract boolean b(C0767v2 c0767v2);

    public C0767v2 c() {
        return null;
    }

    protected Iterable d() {
        return new G3$$ExternalSyntheticLambda1();
    }

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public abstract String toString();

    public final boolean a(C0702m c0702m, C0767v2 c0767v2) {
        if (b(c0767v2)) {
            return true;
        }
        if (c0702m.g0() != null) {
            return c0702m.g0().b(c0767v2).stream().anyMatch(new G3$$ExternalSyntheticLambda2(this));
        }
        return false;
    }

    static Iterable a(G3 g3) {
        return g3 == null ? new G3$$ExternalSyntheticLambda1() : g3.d();
    }

    static ArrayList a(List list) {
        final ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((G3) it.next()).d().forEach(new Consumer() { // from class: com.android.tools.r8.shaking.G3$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add((O3) obj);
                }
            });
        }
        return arrayList;
    }

    public static List a(C0722p1 c0722p1, List list) {
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            c1507XlG.a(((G3) it.next()).a(c0722p1));
        }
        return c1507XlG.a();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static G3 a(N2.a aVar, a aVar2, C0722p1 c0722p1) {
        String str;
        Object[] objArr = 0;
        if (aVar == null || (str = aVar.a) == null) {
            return null;
        }
        str.getClass();
        str.hashCode();
        switch (str) {
            case "%":
                return J3.b;
            case "*":
                return K3.d;
            case "**":
                return K3.c;
            case "***":
                return H3.b;
            case "...":
                return I3.a;
            default:
                if (aVar.b.isEmpty()) {
                    return new b(c0722p1.e(C3970u0.H(aVar.a)));
                }
                return new L3(aVar, aVar2);
        }
    }

    public static G3 a(C0767v2 c0767v2) {
        return new b(c0767v2);
    }
}
