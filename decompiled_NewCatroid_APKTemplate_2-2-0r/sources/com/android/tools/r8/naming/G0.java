package com.android.tools.r8.naming;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.dex.C0560k$$ExternalSyntheticLambda19;
import com.android.tools.r8.internal.AbstractC2465lT;
import com.android.tools.r8.internal.C1333Rt;
import com.android.tools.r8.internal.C1386Tt;
import com.android.tools.r8.internal.C1542Yt;
import com.android.tools.r8.internal.C2307jT;
import com.android.tools.r8.internal.C2385kT;
import com.android.tools.r8.internal.C2431l10;
import com.android.tools.r8.naming.mappinginformation.MappingInformationDiagnostics;
import com.android.tools.r8.utils.AbstractC3916j1;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.Y2;
import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class G0 implements AutoCloseable {
    static final /* synthetic */ boolean p = true;
    private final M a;
    private final DiagnosticsHandler c;
    private final boolean d;
    private final boolean e;
    private String k;
    private MapVersion l;
    private final C1386Tt b = new C1386Tt();
    private boolean f = false;
    private final C3651p0 g = AbstractC3661s0.a();
    private final C3658q0 h = AbstractC3661s0.b();
    private int i = 0;
    private int j = 0;
    private final String[] m = new String[64];
    private final HashMap n = new HashMap();
    private final HashMap o = new HashMap();

    public static /* synthetic */ ArrayList $r8$lambda$aw4WkQINtNlXlsGxYEbzatsgkDc() {
        return new ArrayList();
    }

    G0(DiagnosticsHandler diagnosticsHandler, M m, MapVersion mapVersion, boolean z, boolean z2) {
        this.a = m;
        this.c = diagnosticsHandler;
        this.d = z;
        this.e = z2;
        this.l = mapVersion;
        if (!p && diagnosticsHandler == null) {
            throw new AssertionError();
        }
    }

    private char a(int i) {
        if (this.j + i < this.k.length()) {
            return this.k.charAt(this.j + i);
        }
        return '\n';
    }

    private void b(final AbstractC3665u0 abstractC3665u0) {
        while (this.k != null) {
            h();
            if (c()) {
                if (!a(new Consumer() { // from class: com.android.tools.r8.naming.G0$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(abstractC3665u0, (com.android.tools.r8.naming.mappinginformation.e) obj);
                    }
                }) && !this.f) {
                    abstractC3665u0.a(this.k);
                }
                this.j = this.k.length();
                if (this.k.length() != this.j) {
                    throw new F0(this, "Expected end of line", false);
                }
                c(abstractC3665u0);
            } else {
                String strA = a(false);
                h();
                if (!p && !AbstractC3916j1.a(45)) {
                    throw new AssertionError();
                }
                if (strA.endsWith(Parameters.DEFAULT_OPTION_PREFIXES) && a(0) == ">".charAt(0)) {
                    d();
                    strA = AbstractC0402a.a(strA, 1, 0);
                } else {
                    h();
                    if (a(0) == '-' && a(1) == '>') {
                        d();
                        d();
                    }
                }
                h();
                String strA2 = a(false);
                h();
                a(':');
                this.f = true;
                AbstractC3625g abstractC3625gA = abstractC3665u0.a(strA2, strA, new E0(this.i));
                h();
                if (this.k.length() != this.j) {
                    throw new F0(this, "Expected end of line", false);
                }
                if (c(abstractC3665u0)) {
                    a(abstractC3665u0, abstractC3625gA);
                }
            }
        }
    }

    private boolean c() {
        char cCharAt;
        if (this.k == null) {
            return false;
        }
        int i = 0;
        while (i < this.k.length()) {
            char cCharAt2 = this.k.charAt(i);
            if (cCharAt2 == '#') {
                String str = this.k;
                do {
                    i++;
                    if (i >= str.length()) {
                        return false;
                    }
                    cCharAt = str.charAt(i);
                    if (cCharAt == '{') {
                        return true;
                    }
                } while (Character.isWhitespace(cCharAt));
                return false;
            }
            if (!Character.isWhitespace(cCharAt2)) {
                return false;
            }
            i++;
        }
        return false;
    }

    private char d() {
        if (!p && this.j >= this.k.length()) {
            throw new AssertionError();
        }
        try {
            String str = this.k;
            int i = this.j;
            this.j = i + 1;
            return str.charAt(i);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new F0(this, "Unexpected end of line", false);
        }
    }

    private void e() {
        try {
            this.j += Character.charCount(this.k.codePointAt(this.j));
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new F0(this, "Unexpected end of line", false);
        }
    }

    private N0 f() {
        char cA;
        char cA2;
        char cA3 = a(0);
        if ('0' > cA3 || cA3 > '9') {
            return null;
        }
        char cA4 = a(0);
        if ('0' > cA4 || cA4 > '9') {
            throw new F0(this, "Number expected", false);
        }
        int numericValue = 0;
        do {
            numericValue = (numericValue * 10) + Character.getNumericValue(d());
            cA = a(0);
            if ('0' > cA) {
                break;
            }
        } while (cA <= '9');
        h();
        if (a(0) != ':') {
            return this.g.a(numericValue);
        }
        a(':');
        h();
        char cA5 = a(0);
        if ('0' > cA5 || cA5 > '9') {
            throw new F0(this, "Number expected", false);
        }
        int numericValue2 = 0;
        do {
            numericValue2 = (numericValue2 * 10) + Character.getNumericValue(d());
            cA2 = a(0);
            if ('0' > cA2) {
                break;
            }
        } while (cA2 <= '9');
        if (numericValue <= numericValue2) {
            return this.h.a(numericValue, numericValue2);
        }
        throw new F0(this, "From is larger than to in range: " + numericValue + ":" + numericValue2);
    }

    private int g() {
        if (this.j < this.k.length()) {
            return this.k.codePointAt(this.j);
        }
        return 10;
    }

    private void h() {
        while (this.j < this.k.length() && Y2.b(g())) {
            e();
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.a.close();
    }

    private void a(char c) {
        if (this.j < this.k.length()) {
            if (d() != c) {
                throw new F0(this, "Expected '" + c + FormatHelper.QUOTE);
            }
            return;
        }
        throw new F0(this, "Expected '" + c + FormatHelper.QUOTE, true);
    }

    final void a(AbstractC3665u0 abstractC3665u0) {
        c(abstractC3665u0);
        b(abstractC3665u0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC3665u0 abstractC3665u0, com.android.tools.r8.naming.mappinginformation.e eVar) {
        if (!p) {
            eVar.getClass();
            if (!(eVar instanceof com.android.tools.r8.naming.mappinginformation.b) && !(eVar instanceof C2431l10)) {
                throw new AssertionError();
            }
        }
        eVar.getClass();
        if (eVar instanceof com.android.tools.r8.naming.mappinginformation.b) {
            abstractC3665u0.a(eVar.b());
        } else {
            if (this.f) {
                return;
            }
            abstractC3665u0.a(this.k);
        }
    }

    private boolean c(AbstractC3665u0 abstractC3665u0) {
        boolean z;
        String str;
        boolean z2;
        this.j = 0;
        do {
            String strA = this.a.a();
            this.k = strA;
            this.i++;
            if (strA != null) {
                int i = 0;
                while (true) {
                    if (i >= strA.length()) {
                        z = true;
                        break;
                    }
                    char cCharAt = strA.charAt(i);
                    if (cCharAt == '#') {
                        while (true) {
                            i++;
                            if (i < strA.length()) {
                                char cCharAt2 = strA.charAt(i);
                                if (cCharAt2 == '{') {
                                    z2 = true;
                                    break;
                                }
                                if (!Character.isWhitespace(cCharAt2)) {
                                }
                            }
                            z2 = false;
                            break;
                        }
                        z = !z2;
                        break;
                    }
                    if (!Y2.b(cCharAt)) {
                        z = false;
                        break;
                    }
                    i++;
                }
            } else {
                z = true;
                break;
            }
            if (!this.f && z) {
                abstractC3665u0.a(this.k);
            }
            str = this.k;
            if (str == null) {
                break;
            }
        } while (z);
        return str != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, com.android.tools.r8.naming.mappinginformation.e eVar) {
        com.android.tools.r8.naming.mappinginformation.b bVarB = eVar.b();
        if (bVarB != null) {
            MapVersion mapVersionQ = bVarB.q();
            MapVersion mapVersion = MapVersion.MAP_VERSION_EXPERIMENTAL;
            if (mapVersionQ.equals(mapVersion)) {
                if (!this.e) {
                    mapVersion = MapVersion.MAP_VERSION_NONE;
                }
                this.l = mapVersion;
            } else {
                this.l = bVarB.q();
            }
        }
        consumer.accept(eVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(com.android.tools.r8.naming.mappinginformation.e eVar, com.android.tools.r8.naming.mappinginformation.e eVar2) {
        this.c.warning(MappingInformationDiagnostics.notAllowedCombination(eVar, eVar2, this.i));
    }

    /* JADX WARN: Code duplicated, block: B:128:0x02aa  */
    /* JADX WARN: Code duplicated, block: B:132:0x02c0  */
    /* JADX WARN: Code duplicated, block: B:138:0x02d6 A[LOOP:0: B:3:0x0013->B:138:0x02d6, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:150:0x02d9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:151:0x02c6 A[SYNTHETIC] */
    private void a(AbstractC3665u0 abstractC3665u0, final AbstractC3625g abstractC3625g) {
        N0 n0;
        int i;
        boolean z;
        Object aVar;
        N0 n0F;
        C3634k.b bVar;
        int i2;
        String[] strArr;
        Q q;
        Q.c cVar;
        String str;
        int i3;
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        final com.android.tools.r8.utils.N n2 = new com.android.tools.r8.utils.N();
        int i4 = -1;
        C3634k.b bVar2 = null;
        N0 n1 = null;
        Q qA = null;
        Q.c cVar2 = null;
        String str2 = null;
        while (true) {
            if (c()) {
                final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K(false);
                final String str3 = str2;
                i = i4;
                z = false;
                final C3634k.b bVar3 = bVar2;
                C3634k.b bVar4 = bVar2;
                n0 = n1;
                a(new Consumer() { // from class: com.android.tools.r8.naming.G0$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(k, str3, abstractC3625g, bVar3, n2, n, (com.android.tools.r8.naming.mappinginformation.e) obj);
                    }
                });
                if (k.e()) {
                    q = qA;
                    cVar = cVar2;
                    str = str2;
                    i3 = i;
                    n1 = n0;
                    break;
                }
                this.j = this.k.length();
                i2 = i;
                bVar2 = bVar4;
                n1 = n0;
                if (this.k.length() == this.j) {
                    if (!c(abstractC3665u0)) {
                        i3 = i2;
                        q = qA;
                        cVar = cVar2;
                        str = str2;
                        break;
                    }
                    i4 = i2;
                } else {
                    throw new F0(this, "Expected end of line", z);
                }
            } else {
                C3634k.b bVar5 = bVar2;
                n0 = n1;
                i = i4;
                z = false;
                if (!Y2.b(g())) {
                    q = qA;
                    cVar = cVar2;
                    str = str2;
                    i3 = i;
                    n1 = n0;
                    break;
                }
                h();
                N0 n0F2 = f();
                if (n0F2 != null) {
                    if (!n0F2.c) {
                        h();
                        a(':');
                    } else {
                        throw new F0(this, String.format("Invalid obfuscated line number range (%s).", n0F2));
                    }
                }
                h();
                boolean z2 = true;
                String strA = a(true);
                boolean z3 = false;
                while (this.j < this.k.length() && Y2.b(g())) {
                    z3 = z3 || !Y2.a(g());
                    e();
                }
                if (z3) {
                    int i5 = this.j;
                    b(true);
                    while (a(0) == '.') {
                        d();
                        b(true);
                    }
                    int i6 = this.j - i5;
                    int i7 = i6 % 64;
                    String str4 = this.m[i7];
                    if (str4 == null || str4.length() != i6 || !this.k.regionMatches(i5, str4, 0, i6)) {
                        String strSubstring = this.k.substring(i5, this.j);
                        String[] strArr2 = this.m;
                        str4 = (String) this.n.computeIfAbsent(strSubstring, Function.identity());
                        strArr2[i7] = str4;
                    }
                    h();
                    if (a(0) == '(') {
                        d();
                        h();
                        if (a(0) == ')') {
                            strArr = Y2.b;
                        } else {
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(a(true));
                            h();
                            while (a(0) != ')') {
                                h();
                                a(',');
                                h();
                                arrayList.add(a(true));
                            }
                            strArr = (String[]) arrayList.toArray(Y2.b);
                        }
                        a(')');
                        aVar = new Q.b(str4, strA, strArr);
                    } else {
                        aVar = new Q.a(str4, strA);
                    }
                    Q.c cVar3 = (Q.c) this.o.computeIfAbsent(aVar, Function.identity());
                    h();
                    if (a(0) == ':') {
                        d();
                        h();
                        n0F = f();
                        if (n0F == null) {
                            throw new F0(this, "No number follows the colon after the method signature.");
                        }
                    } else {
                        n0F = null;
                    }
                    if (!this.d && n0F2 == null && n0F != null) {
                        throw new F0(this, "No mapping for original range " + n0F + ".");
                    }
                    h();
                    a('-');
                    a('>');
                    h();
                    int i8 = this.j;
                    b(true);
                    while (a(0) == '.') {
                        d();
                        b(true);
                    }
                    int i9 = this.j - i8;
                    int i10 = i9 % 64;
                    String str5 = this.m[i10];
                    if (str5 == null || str5.length() != i9 || !this.k.regionMatches(i8, str5, 0, i9)) {
                        String strSubstring2 = this.k.substring(i8, this.j);
                        String[] strArr3 = this.m;
                        str5 = (String) this.n.computeIfAbsent(strSubstring2, Function.identity());
                        strArr3[i10] = str5;
                    }
                    String str6 = str5;
                    if (cVar3.d()) {
                        Q.b bVarD = (bVar5 != null && bVar5.b == cVar3 && bVar5.d.equals(str6)) ? bVar5.d() : null;
                        C3634k.b bVarA = abstractC3625g.a(n0F2, cVar3.b(), n0F, str6);
                        if (bVarA != null) {
                            if (bVarD != null) {
                                bVarA.a(bVarD);
                            } else if (qA != null && qA.b().equals(bVarA.b())) {
                                bVarA.a(qA.d().b());
                            }
                        }
                        bVar = bVarA;
                    } else {
                        bVar = bVar5;
                    }
                    if (!p && n0F2 != null && !cVar3.d()) {
                        throw new AssertionError();
                    }
                    if (cVar2 != null) {
                        boolean zEquals = str2.equals(str6);
                        boolean z4 = n0 == null || !n0.equals(n0F2);
                        if (n0F != null && n0F.c) {
                            z2 = false;
                        }
                        if (!zEquals || z4 || z2) {
                            qA = a(qA, cVar2, str2, i, n, n2, n0, abstractC3625g);
                        }
                    }
                    i2 = this.i;
                    n1 = n0F2;
                    bVar2 = bVar;
                    str2 = str6;
                    cVar2 = cVar3;
                    if (this.k.length() == this.j) {
                        if (!c(abstractC3665u0)) {
                            i3 = i2;
                            q = qA;
                            cVar = cVar2;
                            str = str2;
                            break;
                        }
                        i4 = i2;
                    } else {
                        throw new F0(this, "Expected end of line", z);
                    }
                } else {
                    throw new F0(this, "Expected whitespace", true);
                }
            }
        }
        if (cVar != null) {
            a(q, cVar, str, i3, n, n2, n1, abstractC3625g);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.utils.K k, String str, AbstractC3625g abstractC3625g, C3634k.b bVar, com.android.tools.r8.utils.N n, com.android.tools.r8.utils.N n2, final com.android.tools.r8.naming.mappinginformation.e eVar) {
        eVar.getClass();
        k.c(eVar instanceof com.android.tools.r8.naming.mappinginformation.b);
        if (str == null) {
            abstractC3625g.a(new Consumer() { // from class: com.android.tools.r8.naming.G0$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(eVar, (com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            }, eVar);
            return;
        }
        if (bVar != null) {
            bVar.a(new Consumer() { // from class: com.android.tools.r8.naming.G0$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(eVar, (com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            }, eVar);
        }
        if (eVar.m()) {
            com.android.tools.r8.naming.mappinginformation.e.a((List) n.a(new Supplier() { // from class: com.android.tools.r8.naming.G0$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return G0.$r8$lambda$aw4WkQINtNlXlsGxYEbzatsgkDc();
                }
            }), eVar.e(), new Consumer() { // from class: com.android.tools.r8.naming.G0$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.c(eVar, (com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            });
            if (eVar.n()) {
                AbstractC2465lT abstractC2465lTH = eVar.h();
                if (!abstractC2465lTH.q()) {
                    this.c.warning(MappingInformationDiagnostics.invalidResidualSignature(this.k.trim(), this.i));
                    return;
                }
                Q.c cVarA = a(eVar.h(), str);
                n2.a(cVarA);
                if (bVar != null) {
                    if (!(abstractC2465lTH instanceof C2385kT)) {
                        this.c.warning(MappingInformationDiagnostics.invalidResidualSignatureType(eVar.p(), this.i));
                        n2.a((Object) null);
                    } else {
                        bVar.a(cVarA.b());
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.naming.mappinginformation.e eVar, com.android.tools.r8.naming.mappinginformation.e eVar2) {
        this.c.warning(MappingInformationDiagnostics.notAllowedCombination(eVar, eVar2, this.i));
    }

    private Q a(Q q, Q.c cVar, String str, int i, com.android.tools.r8.utils.N n, com.android.tools.r8.utils.N n2, N0 n0, AbstractC3625g abstractC3625g) {
        Q.c cVar2;
        if (n0 != null && q != null && q.a().equals(str) && q.b().equals(cVar)) {
            if (n2.c()) {
                List list = (List) n2.a();
                Consumer consumerB = C3912j0.b();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    q.a((com.android.tools.r8.naming.mappinginformation.d) it.next(), consumerB);
                }
                n2.a((Object) null);
            }
            n.a((Object) null);
            return q;
        }
        if (n.c()) {
            if (((Q.c) n.a()).f() != cVar.f()) {
                this.c.warning(MappingInformationDiagnostics.invalidResidualSignatureType(((Q.c) n.a()).toString(), this.i));
                cVar2 = (Q.c) this.o.computeIfAbsent(cVar.a(str), Function.identity());
            } else {
                cVar2 = (Q.c) n.a();
            }
        } else {
            cVar2 = (Q.c) this.o.computeIfAbsent(cVar.a(str), Function.identity());
        }
        Q qA = abstractC3625g.a(cVar2);
        if (qA == null) {
            qA = new Q(cVar, cVar2, new E0(i));
        }
        if (n2.c()) {
            List list2 = (List) n2.a();
            Consumer consumerB2 = C3912j0.b();
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                qA.a((com.android.tools.r8.naming.mappinginformation.d) it2.next(), consumerB2);
            }
        }
        abstractC3625g.a(qA);
        n.a((Object) null);
        n2.a((Object) null);
        return qA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(com.android.tools.r8.naming.mappinginformation.e eVar, com.android.tools.r8.naming.mappinginformation.e eVar2) {
        this.c.warning(MappingInformationDiagnostics.notAllowedCombination(eVar, eVar2, this.i));
    }

    private void b(boolean z) {
        boolean z2 = false;
        if (z && a(0) == '<') {
            d();
            z2 = true;
        }
        if (!AbstractC3916j1.b(g()) && !AbstractC3916j1.c(g())) {
            throw new F0(this, "Identifier expected");
        }
        e();
        while (true) {
            if (!AbstractC3916j1.a(g()) && !AbstractC3916j1.c(g())) {
                break;
            } else {
                e();
            }
        }
        if (z2) {
            a('>');
        }
        if (AbstractC3916j1.a(g())) {
            throw new F0(this, "End of identifier expected (was 0x" + Integer.toHexString(g()) + Constant.AFTER_QUTO);
        }
    }

    private Q.c a(AbstractC2465lT abstractC2465lT, String str) {
        abstractC2465lT.getClass();
        if (abstractC2465lT instanceof C2385kT) {
            C2385kT c2385kTG = abstractC2465lT.g();
            return ((Q.c) this.o.computeIfAbsent(new Q.b(str, C3970u0.b(c2385kTG.t()), com.android.tools.r8.utils.B.a(c2385kTG.s(), new C0560k$$ExternalSyntheticLambda19())), Function.identity())).b();
        }
        if (!p && !(abstractC2465lT instanceof C2307jT)) {
            throw new AssertionError();
        }
        return ((Q.c) this.o.computeIfAbsent(new Q.a(str, C3970u0.b(abstractC2465lT.f().s())), Function.identity())).a();
    }

    private String a(boolean z) {
        int i = this.j;
        b(false);
        while (a(0) == '.') {
            d();
            b(false);
        }
        if (z) {
            while (a(0) == '[') {
                d();
                a(']');
            }
        }
        int i2 = this.j - i;
        int i3 = i2 % 64;
        String str = this.m[i3];
        if (str != null && str.length() == i2 && this.k.regionMatches(i, str, 0, i2)) {
            return str;
        }
        String strSubstring = this.k.substring(i, this.j);
        String[] strArr = this.m;
        String str2 = (String) this.n.computeIfAbsent(strSubstring, Function.identity());
        strArr[i3] = str2;
        return str2;
    }

    private boolean a(final Consumer consumer) {
        C1333Rt c1333RtD;
        if (!p && !c()) {
            throw new AssertionError();
        }
        int i = 0;
        while (this.k.charAt(i) != '{') {
            try {
                i++;
            } catch (C1542Yt unused) {
                c1333RtD = null;
            }
        }
        C1386Tt c1386Tt = this.b;
        String strSubstring = this.k.substring(i);
        c1386Tt.getClass();
        c1333RtD = C1386Tt.a(strSubstring).d();
        if (c1333RtD == null) {
            return false;
        }
        com.android.tools.r8.naming.mappinginformation.e.a(this.l, c1333RtD, this.c, this.i, new Consumer() { // from class: com.android.tools.r8.naming.G0$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (com.android.tools.r8.naming.mappinginformation.e) obj);
            }
        });
        return true;
    }
}
