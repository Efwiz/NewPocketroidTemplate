package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.position.TextPosition;
import com.android.tools.r8.position.TextRange;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B2 {
    static final /* synthetic */ boolean n = true;
    private final Origin a;
    private final Position b;
    private final String c;
    private final List d;
    private final C3774o2 e;
    private final C3774o2 f;
    private final boolean g;
    private final I2 h;
    private final AbstractC3833y2 i;
    private final List j;
    private final G3 k;
    private final boolean l;
    private final List m;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class a<C extends B2, B extends a<C, B>> {
        static final /* synthetic */ boolean o = true;
        protected Origin a;
        protected Position b;
        protected Position c;
        protected String d;
        private final C1507Xl e;
        protected C3774o2 f;
        protected C3774o2 g;
        protected boolean h;
        protected I2 i;
        protected AbstractC3833y2 j;
        private final C1507Xl k;
        protected G3 l;
        protected boolean m;
        protected List n;

        protected a() {
            this(Origin.unknown(), Position.UNKNOWN);
        }

        public B a(String str) {
            this.d = str;
            return (B) e();
        }

        public abstract B2 a();

        public final a b(List list) {
            if (!o && list == null) {
                throw new AssertionError();
            }
            this.k.a((Iterable) list);
            return e();
        }

        public B c(List<C3775o3> list) {
            this.n = list;
            return (B) e();
        }

        public final Position d() {
            Position position = this.b;
            if (position == null) {
                return Position.UNKNOWN;
            }
            Position position2 = this.c;
            return (position2 != null && (position instanceof TextPosition) && (position2 instanceof TextPosition)) ? new TextRange((TextPosition) position, (TextPosition) position2) : position;
        }

        public abstract a e();

        protected a(Origin origin, Position position) {
            this.e = AbstractC1639am.g();
            this.f = new C3774o2();
            this.g = new C3774o2();
            this.h = false;
            this.i = I2.e;
            this.k = AbstractC1639am.g();
            this.m = false;
            this.n = new LinkedList();
            this.a = origin;
            this.b = position;
        }

        public B a(AbstractC3833y2 abstractC3833y2) {
            this.j = abstractC3833y2;
            return (B) e();
        }

        public final AbstractC1639am c() {
            return this.k.a();
        }

        public final AbstractC1639am b() {
            return this.e.a();
        }

        public final void a(G3 g3) {
            this.e.a(g3);
            e();
        }

        public final a a(List list) {
            if (!o && list == null) {
                throw new AssertionError();
            }
            this.e.a((Iterable) list);
            return (X1) this;
        }
    }

    protected B2(Origin origin, Position position, String str, List list, C3774o2 c3774o2, C3774o2 c3774o3, boolean z, I2 i2, AbstractC3833y2 abstractC3833y2, List list2, G3 g3, boolean z2, List list3) {
        boolean z3 = n;
        if (!z3 && origin == null) {
            throw new AssertionError();
        }
        if (!z3 && position == null) {
            throw new AssertionError();
        }
        if (!z3 && str == null && origin == Origin.unknown()) {
            throw new AssertionError();
        }
        this.a = origin;
        this.b = position;
        this.c = str;
        this.d = list;
        this.e = c3774o2;
        this.f = c3774o3;
        this.g = z;
        this.h = i2;
        if (!z3 && i2 == null) {
            throw new AssertionError();
        }
        this.i = abstractC3833y2;
        this.j = list2;
        this.k = g3;
        this.l = z2;
        this.m = list3;
    }

    public C3774o2 a() {
        return this.e;
    }

    public List<G3> b() {
        return this.d;
    }

    public AbstractC3833y2 c() {
        return this.i;
    }

    public I2 d() {
        return this.h;
    }

    public final boolean e() {
        return this.g;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof B2)) {
            return false;
        }
        B2 b2 = (B2) obj;
        if (this.g == b2.g && this.l == b2.l && Objects.equals(this.d, b2.d) && this.e.equals(b2.e) && this.f.equals(b2.f) && this.h == b2.h && this.i.equals(b2.i) && Objects.equals(this.j, b2.j) && Objects.equals(this.k, b2.k)) {
            return this.m.equals(b2.m);
        }
        return false;
    }

    public final List f() {
        return this.j;
    }

    public G3 g() {
        return this.k;
    }

    public boolean h() {
        return this.l;
    }

    public int hashCode() {
        int iHashCode = (((this.f.hashCode() + ((this.e.hashCode() + (this.d.hashCode() * 3)) * 3)) * 3) + (this.g ? 1 : 0)) * 3;
        I2 i2 = this.h;
        int iHashCode2 = (this.j.hashCode() + ((this.i.hashCode() + ((iHashCode + (i2 != null ? i2.hashCode() : 0)) * 3)) * 3)) * 3;
        G3 g3 = this.k;
        return this.m.hashCode() + ((((iHashCode2 + (g3 != null ? g3.hashCode() : 0)) * 3) + (this.l ? 1 : 0)) * 3);
    }

    public final boolean i() {
        return !this.l;
    }

    public List<C3775o3> j() {
        return this.m;
    }

    public C3774o2 k() {
        return this.f;
    }

    public Origin l() {
        return this.a;
    }

    public final Position m() {
        return this.b;
    }

    public String n() {
        return this.c;
    }

    public final boolean o() {
        return this.k != null;
    }

    public String toString() {
        return a(new StringBuilder()).toString();
    }

    protected StringBuilder a(final StringBuilder sb) {
        List list = this.d;
        if (!list.isEmpty()) {
            Iterator it = list.iterator();
            sb.append('@').append(it.next());
            while (it.hasNext()) {
                sb.append(" @").append(it.next());
            }
            sb.append(StringUtil.SPACE);
        }
        boolean zA = com.android.tools.r8.utils.Y2.a(sb, (String) null, this.e);
        boolean zA2 = com.android.tools.r8.utils.Y2.a(sb, "!", this.f.toString().replace(" ", " !"));
        if (zA || zA2) {
            sb.append(StringUtil.SPACE);
        }
        if (this.g) {
            sb.append('!');
        }
        sb.append(this.h);
        sb.append(StringUtil.SPACE);
        this.i.a(sb);
        if (o()) {
            sb.append(StringUtil.SPACE).append(this.l ? PsiKeyword.EXTENDS : PsiKeyword.IMPLEMENTS).append(StringUtil.SPACE);
            List list2 = this.j;
            if (!list2.isEmpty()) {
                Iterator it2 = list2.iterator();
                sb.append('@').append(it2.next());
                while (it2.hasNext()) {
                    sb.append(" @").append(it2.next());
                }
                sb.append(StringUtil.SPACE);
            }
            sb.append(this.k);
        }
        if (!this.m.isEmpty()) {
            sb.append(" {").append(System.lineSeparator());
            this.m.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.B2$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    B2.a(sb, (C3775o3) obj);
                }
            });
            sb.append("}");
        }
        return sb;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, C3775o3 c3775o3) {
        sb.append("  ");
        sb.append(c3775o3);
        sb.append(";").append(System.lineSeparator());
    }
}
