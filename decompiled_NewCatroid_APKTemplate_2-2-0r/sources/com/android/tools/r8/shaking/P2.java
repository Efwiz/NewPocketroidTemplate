package com.android.tools.r8.shaking;

import com.android.tools.r8.InputDependencyGraphConsumer;
import com.android.tools.r8.Version;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.position.TextPosition;
import com.android.tools.r8.position.TextRange;
import com.android.tools.r8.utils.AbstractC3916j1;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.StringDiagnostic;
import com.beust.jcommander.Parameters;
import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class P2 {
    static final /* synthetic */ boolean j = true;
    private final String a;
    private final String b;
    private int c = 0;
    private int d = 0;
    private int e = 1;
    private int f = 0;
    private Path g;
    private final Origin h;
    final /* synthetic */ N2 i;

    P2(N2 n2, T2 t2) {
        this.i = n2;
        this.b = com.android.tools.r8.utils.Y2.g(t2.get());
        this.g = t2.a();
        this.a = t2.getName();
        this.h = t2.getOrigin();
    }

    private void a(TextPosition textPosition, String str, String str2) {
        this.i.d.a(new StringDiagnostic("Unknown option \"-" + str + "\"" + str2, this.h, a(textPosition)));
        throw null;
    }

    private void b(char c) throws F3 {
        if (!a(c)) {
            throw b("Expected char '" + c + FormatHelper.QUOTE);
        }
    }

    private boolean c(TextPosition textPosition) throws F3 {
        if (!AbstractC2734ot.b(N2.f, new P2$$ExternalSyntheticLambda22(this)) && !AbstractC2734ot.b(N2.g, new P2$$ExternalSyntheticLambda23(this)) && !AbstractC2734ot.b(N2.h, new P2$$ExternalSyntheticLambda24(this)) && !AbstractC2734ot.b(N2.i, new P2$$ExternalSyntheticLambda25(this))) {
            if (!a("optimizations")) {
                return false;
            }
            a("optimizations", textPosition);
            do {
                r();
                q();
                r();
            } while (a(','));
        }
        return true;
    }

    private boolean d(TextPosition textPosition) {
        String str = (String) AbstractC2734ot.a((List) N2.m, (InterfaceC3205vM) new P2$$ExternalSyntheticLambda25(this));
        if (str == null && (str = (String) AbstractC2734ot.a(N2.l, (InterfaceC3205vM) new P2$$ExternalSyntheticLambda24(this))) == null && (str = (String) AbstractC2734ot.a(N2.j, (InterfaceC3205vM) new P2$$ExternalSyntheticLambda22(this))) == null && (str = (String) AbstractC2734ot.a(N2.k, (InterfaceC3205vM) new P2$$ExternalSyntheticLambda23(this))) == null) {
            return false;
        }
        c(str, textPosition);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e(char c) {
        return c == '\'' || c == '\"';
    }

    private boolean f(TextPosition textPosition) {
        String str = (String) AbstractC2734ot.a(N2.n, (InterfaceC3205vM) new P2$$ExternalSyntheticLambda24(this));
        if (str == null) {
            return false;
        }
        this.i.d.error(new StringDiagnostic("Unsupported option: -" + str, this.h, a(textPosition)));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean g(String str) {
        if (!a(str)) {
            return false;
        }
        r();
        while (!g() && !Character.isWhitespace(p())) {
            String str2 = this.b;
            int i = this.c;
            this.c = i + 1;
            str2.charAt(i);
        }
        return true;
    }

    private void h(String str) {
        if (!j && this.c < str.length()) {
            throw new AssertionError();
        }
        this.c -= str.length();
        for (int i = 0; i < str.length(); i++) {
            if (!j && str.charAt(i) != this.b.charAt(this.c + i)) {
                throw new AssertionError();
            }
        }
    }

    private boolean i() {
        return (g() || d('-') || d('@')) ? false : true;
    }

    private ArrayList k() throws F3 {
        ArrayList arrayList = new ArrayList();
        r();
        b('(');
        r();
        if (a(')')) {
            return arrayList;
        }
        if (a("...")) {
            arrayList.add(G3.a(N2.a.a("..."), G3.a.b, this.i.b));
        } else {
            O2 o2A = a(2, false);
            if (o2A == null) {
                throw b("Class name expected");
            }
            if (!j && o2A.b) {
                throw new AssertionError();
            }
            N2.a aVar = o2A.a;
            while (aVar != null) {
                arrayList.add(G3.a(aVar, G3.a.b, this.i.b));
                r();
                if (a(',')) {
                    O2 o2A2 = a(2, false);
                    if (o2A2 == null) {
                        throw b("Class name expected");
                    }
                    if (!j && o2A2.b) {
                        throw new AssertionError();
                    }
                    aVar = o2A2.a;
                } else {
                    aVar = null;
                }
            }
        }
        r();
        b(')');
        return arrayList;
    }

    private String l() throws F3 {
        TextPosition textPositionH = h();
        r();
        String strA = a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda21
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return P2.e(i);
            }
        });
        if (strA != null) {
            return strA;
        }
        throw b("file filter expected", textPositionH);
    }

    private void m() throws F3 {
        TextPosition textPositionH = h();
        final InputDependencyGraphConsumer inputDependencyGraphConsumer = this.i.e;
        Objects.requireNonNull(inputDependencyGraphConsumer);
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda17
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                inputDependencyGraphConsumer.acceptProguardInclude((Origin) obj, (Path) obj2);
            }
        };
        Path pathA = a(false);
        biConsumer.accept(this.h, pathA);
        try {
            new P2(this.i, new V2(pathA)).j();
        } catch (FileNotFoundException e) {
            e = e;
            F3 f3 = new F3("Included file '" + pathA.toString() + "' not found", g(textPositionH), this.h, a(textPositionH));
            f3.initCause(e);
            throw f3;
        } catch (NoSuchFileException e2) {
            e = e2;
            F3 f4 = new F3("Included file '" + pathA.toString() + "' not found", g(textPositionH), this.h, a(textPositionH));
            f4.initCause(e);
            throw f4;
        } catch (IOException e3) {
            F3 f5 = new F3("Failed to read included file '" + pathA.toString() + FormatHelper.QUOTE, g(textPositionH), this.h, a(textPositionH));
            f5.initCause(e3);
            throw f5;
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x04d2  */
    /* JADX WARN: Code duplicated, block: B:102:0x04dc  */
    /* JADX WARN: Code duplicated, block: B:104:0x04e5  */
    /* JADX WARN: Code duplicated, block: B:105:0x04f4  */
    /* JADX WARN: Code duplicated, block: B:106:0x04ff  */
    /* JADX WARN: Code duplicated, block: B:108:0x050a  */
    /* JADX WARN: Code duplicated, block: B:110:0x0520  */
    /* JADX WARN: Code duplicated, block: B:112:0x0525  */
    /* JADX WARN: Code duplicated, block: B:116:0x0546  */
    /* JADX WARN: Code duplicated, block: B:118:0x054b  */
    /* JADX WARN: Code duplicated, block: B:133:0x0579  */
    /* JADX WARN: Code duplicated, block: B:134:0x0584  */
    /* JADX WARN: Code duplicated, block: B:136:0x058b  */
    /* JADX WARN: Code duplicated, block: B:138:0x0595  */
    /* JADX WARN: Code duplicated, block: B:140:0x05ac  */
    /* JADX WARN: Code duplicated, block: B:143:0x05b6  */
    /* JADX WARN: Code duplicated, block: B:148:0x05e0  */
    /* JADX WARN: Code duplicated, block: B:149:0x05ed  */
    /* JADX WARN: Code duplicated, block: B:151:0x05f5  */
    /* JADX WARN: Code duplicated, block: B:152:0x0606  */
    /* JADX WARN: Code duplicated, block: B:154:0x060e  */
    /* JADX WARN: Code duplicated, block: B:155:0x0639  */
    /* JADX WARN: Code duplicated, block: B:157:0x0641  */
    /* JADX WARN: Code duplicated, block: B:158:0x0662  */
    /* JADX WARN: Code duplicated, block: B:160:0x066a  */
    /* JADX WARN: Code duplicated, block: B:162:0x068a  */
    /* JADX WARN: Code duplicated, block: B:163:0x0693  */
    /* JADX WARN: Code duplicated, block: B:164:0x069c  */
    /* JADX WARN: Code duplicated, block: B:166:0x06a4  */
    /* JADX WARN: Code duplicated, block: B:167:0x06b3  */
    /* JADX WARN: Code duplicated, block: B:169:0x06bb  */
    /* JADX WARN: Code duplicated, block: B:170:0x06e6  */
    /* JADX WARN: Code duplicated, block: B:172:0x06ee  */
    /* JADX WARN: Code duplicated, block: B:173:0x06f9  */
    /* JADX WARN: Code duplicated, block: B:175:0x0701  */
    /* JADX WARN: Code duplicated, block: B:177:0x070a  */
    /* JADX WARN: Code duplicated, block: B:178:0x070f  */
    /* JADX WARN: Code duplicated, block: B:180:0x0726  */
    /* JADX WARN: Code duplicated, block: B:182:0x072e  */
    /* JADX WARN: Code duplicated, block: B:183:0x0739  */
    /* JADX WARN: Code duplicated, block: B:185:0x0741  */
    /* JADX WARN: Code duplicated, block: B:186:0x074c  */
    /* JADX WARN: Code duplicated, block: B:188:0x0754  */
    /* JADX WARN: Code duplicated, block: B:190:0x0766  */
    /* JADX WARN: Code duplicated, block: B:191:0x0775  */
    /* JADX WARN: Code duplicated, block: B:193:0x077d  */
    /* JADX WARN: Code duplicated, block: B:194:0x0788  */
    /* JADX WARN: Code duplicated, block: B:196:0x0790  */
    /* JADX WARN: Code duplicated, block: B:197:0x079b  */
    /* JADX WARN: Code duplicated, block: B:199:0x07a3  */
    /* JADX WARN: Code duplicated, block: B:200:0x07ae  */
    /* JADX WARN: Code duplicated, block: B:202:0x07b6  */
    /* JADX WARN: Code duplicated, block: B:204:0x07cd  */
    /* JADX WARN: Code duplicated, block: B:207:0x07d7  */
    /* JADX WARN: Code duplicated, block: B:212:0x0801  */
    /* JADX WARN: Code duplicated, block: B:213:0x080e  */
    /* JADX WARN: Code duplicated, block: B:215:0x0816  */
    /* JADX WARN: Code duplicated, block: B:217:0x082d  */
    /* JADX WARN: Code duplicated, block: B:220:0x0837  */
    /* JADX WARN: Code duplicated, block: B:225:0x0861  */
    /* JADX WARN: Code duplicated, block: B:226:0x086e  */
    /* JADX WARN: Code duplicated, block: B:228:0x0876  */
    /* JADX WARN: Code duplicated, block: B:230:0x0884  */
    /* JADX WARN: Code duplicated, block: B:233:0x0894  */
    /* JADX WARN: Code duplicated, block: B:236:0x08a1  */
    /* JADX WARN: Code duplicated, block: B:238:0x08aa  */
    /* JADX WARN: Code duplicated, block: B:240:0x08b0  */
    /* JADX WARN: Code duplicated, block: B:241:0x08bb  */
    /* JADX WARN: Code duplicated, block: B:244:0x08c8  */
    /* JADX WARN: Code duplicated, block: B:246:0x08ce  */
    /* JADX WARN: Code duplicated, block: B:248:0x08d6  */
    /* JADX WARN: Code duplicated, block: B:250:0x08e4  */
    /* JADX WARN: Code duplicated, block: B:252:0x08f0  */
    /* JADX WARN: Code duplicated, block: B:258:0x090f  */
    /* JADX WARN: Code duplicated, block: B:260:0x091c  */
    /* JADX WARN: Code duplicated, block: B:263:0x0929  */
    /* JADX WARN: Code duplicated, block: B:265:0x0932  */
    /* JADX WARN: Code duplicated, block: B:267:0x0938  */
    /* JADX WARN: Code duplicated, block: B:268:0x0943  */
    /* JADX WARN: Code duplicated, block: B:271:0x0950  */
    /* JADX WARN: Code duplicated, block: B:273:0x0956  */
    /* JADX WARN: Code duplicated, block: B:275:0x095e  */
    /* JADX WARN: Code duplicated, block: B:276:0x0969  */
    /* JADX WARN: Code duplicated, block: B:278:0x0971  */
    /* JADX WARN: Code duplicated, block: B:280:0x0983  */
    /* JADX WARN: Code duplicated, block: B:281:0x0992  */
    /* JADX WARN: Code duplicated, block: B:283:0x099a  */
    /* JADX WARN: Code duplicated, block: B:285:0x09ac  */
    /* JADX WARN: Code duplicated, block: B:286:0x09bb  */
    /* JADX WARN: Code duplicated, block: B:288:0x09c3  */
    /* JADX WARN: Code duplicated, block: B:289:0x09e3  */
    /* JADX WARN: Code duplicated, block: B:291:0x09eb  */
    /* JADX WARN: Code duplicated, block: B:292:0x0a16  */
    /* JADX WARN: Code duplicated, block: B:294:0x0a1e  */
    /* JADX WARN: Code duplicated, block: B:295:0x0a49  */
    /* JADX WARN: Code duplicated, block: B:297:0x0a51  */
    /* JADX WARN: Code duplicated, block: B:298:0x0a72  */
    /* JADX WARN: Code duplicated, block: B:300:0x0a7a  */
    /* JADX WARN: Code duplicated, block: B:301:0x0a85  */
    /* JADX WARN: Code duplicated, block: B:303:0x0a95  */
    /* JADX WARN: Code duplicated, block: B:305:0x0ac5  */
    /* JADX WARN: Code duplicated, block: B:308:0x0ada A[LOOP:5: B:306:0x0ad4->B:308:0x0ada, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:311:0x0aeb  */
    /* JADX WARN: Code duplicated, block: B:312:0x0af3  */
    /* JADX WARN: Code duplicated, block: B:314:0x0af8  */
    /* JADX WARN: Code duplicated, block: B:318:0x0b0e  */
    /* JADX WARN: Code duplicated, block: B:320:0x0b20  */
    /* JADX WARN: Code duplicated, block: B:323:0x0b35 A[LOOP:7: B:321:0x0b2f->B:323:0x0b35, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:326:0x0b46  */
    /* JADX WARN: Code duplicated, block: B:329:0x0b53  */
    /* JADX WARN: Code duplicated, block: B:332:0x0b67  */
    /* JADX WARN: Code duplicated, block: B:334:0x0b6f  */
    /* JADX WARN: Code duplicated, block: B:336:0x0b9f  */
    /* JADX WARN: Code duplicated, block: B:339:0x0bb4 A[LOOP:8: B:337:0x0bae->B:339:0x0bb4, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:342:0x0bc5  */
    /* JADX WARN: Code duplicated, block: B:343:0x0bcd  */
    /* JADX WARN: Code duplicated, block: B:345:0x0bd2  */
    /* JADX WARN: Code duplicated, block: B:349:0x0be8  */
    /* JADX WARN: Code duplicated, block: B:351:0x0bfa  */
    /* JADX WARN: Code duplicated, block: B:354:0x0c0f A[LOOP:10: B:352:0x0c09->B:354:0x0c0f, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:357:0x0c20  */
    /* JADX WARN: Code duplicated, block: B:360:0x0c2d  */
    /* JADX WARN: Code duplicated, block: B:363:0x0c41  */
    /* JADX WARN: Code duplicated, block: B:365:0x0c49  */
    /* JADX WARN: Code duplicated, block: B:367:0x0c5b  */
    /* JADX WARN: Code duplicated, block: B:368:0x0c6a  */
    /* JADX WARN: Code duplicated, block: B:370:0x0c72  */
    /* JADX WARN: Code duplicated, block: B:371:0x0c92  */
    /* JADX WARN: Code duplicated, block: B:373:0x0c9a  */
    /* JADX WARN: Code duplicated, block: B:374:0x0cba  */
    /* JADX WARN: Code duplicated, block: B:376:0x0cc2  */
    /* JADX WARN: Code duplicated, block: B:377:0x0ce2  */
    /* JADX WARN: Code duplicated, block: B:379:0x0cea  */
    /* JADX WARN: Code duplicated, block: B:380:0x0cfb  */
    /* JADX WARN: Code duplicated, block: B:382:0x0d03  */
    /* JADX WARN: Code duplicated, block: B:384:0x0d1a  */
    /* JADX WARN: Code duplicated, block: B:387:0x0d24  */
    /* JADX WARN: Code duplicated, block: B:392:0x0d4e  */
    /* JADX WARN: Code duplicated, block: B:393:0x0d5b  */
    /* JADX WARN: Code duplicated, block: B:395:0x0d63  */
    /* JADX WARN: Code duplicated, block: B:397:0x0d7a  */
    /* JADX WARN: Code duplicated, block: B:398:0x0d83  */
    /* JADX WARN: Code duplicated, block: B:399:0x0d8c  */
    /* JADX WARN: Code duplicated, block: B:401:0x0d94  */
    /* JADX WARN: Code duplicated, block: B:403:0x0dab  */
    /* JADX WARN: Code duplicated, block: B:404:0x0db4  */
    /* JADX WARN: Code duplicated, block: B:405:0x0dbd  */
    /* JADX WARN: Code duplicated, block: B:407:0x0dc5  */
    /* JADX WARN: Code duplicated, block: B:408:0x0dfc  */
    /* JADX WARN: Code duplicated, block: B:410:0x0e04  */
    /* JADX WARN: Code duplicated, block: B:412:0x0e35  */
    /* JADX WARN: Code duplicated, block: B:413:0x0e5a  */
    /* JADX WARN: Code duplicated, block: B:415:0x0e71  */
    /* JADX WARN: Code duplicated, block: B:417:0x0e79  */
    /* JADX WARN: Code duplicated, block: B:418:0x0e83  */
    /* JADX WARN: Code duplicated, block: B:420:0x0e8b  */
    /* JADX WARN: Code duplicated, block: B:421:0x0e95  */
    /* JADX WARN: Code duplicated, block: B:423:0x0e9d  */
    /* JADX WARN: Code duplicated, block: B:430:0x0ec5  */
    /* JADX WARN: Code duplicated, block: B:441:0x0554 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:442:0x054d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:444:0x0531 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:450:0x05db A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:453:0x07fc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:455:0x085c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:461:0x0b4e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:467:0x0c28 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:472:0x0d49 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:94:0x046e  */
    /* JADX WARN: Code duplicated, block: B:97:0x0476  */
    /* JADX WARN: Code duplicated, block: B:99:0x047e  */
    private boolean n() throws F3 {
        String str;
        Integer numB;
        L2.a aVar;
        C3706d3 c3706d3;
        TextPosition textPositionH;
        Consumer consumer;
        Consumer consumer2;
        Consumer consumer3;
        AbstractC3833y2.a aVarB;
        O2 o2A;
        BiConsumer biConsumer;
        ArrayList arrayList;
        TextPosition textPositionH2;
        AbstractC1639am abstractC1639amI;
        AbstractC1639am abstractC1639amI2;
        C1507Xl c1507Xl;
        C1507Xl c1507Xl2;
        BiConsumer biConsumer2;
        ArrayList arrayList2;
        TextPosition textPositionH3;
        AbstractC1639am abstractC1639amI3;
        AbstractC1639am abstractC1639amI4;
        C1507Xl c1507Xl3;
        C1507Xl c1507Xl4;
        char cD;
        char cD2;
        Consumer consumer4;
        AbstractC3833y2.a aVarB2;
        O2 o2A2;
        Consumer consumer5;
        AbstractC3833y2.a aVarB3;
        O2 o2A3;
        Consumer consumer6;
        Consumer consumer7;
        AbstractC3833y2.a aVarB4;
        O2 o2A4;
        ArrayList arrayList3;
        char cD3;
        String strC;
        TextPosition textPositionH4;
        char cD4;
        String strC2;
        if (g()) {
            return false;
        }
        if (this.b.length() - this.c >= 2 && a('@')) {
            m();
            return true;
        }
        TextPosition textPositionH5 = h();
        b('-');
        if (!c(textPositionH5) && !d(textPositionH5)) {
            if (a("checkenumunboxed")) {
                B2.a aVarG = C3765n.G();
                aVarG.a = this.h;
                aVarG.b = textPositionH5;
                a(aVarG, false);
                TextPosition textPositionH6 = h();
                aVarG.a(a(this.b, textPositionH5, textPositionH6));
                aVarG.c = textPositionH6;
                C3765n c3765n = new C3765n(aVarG.a, aVarG.d(), aVarG.d, aVarG.b(), aVarG.f, aVarG.g, aVarG.h, aVarG.i, aVarG.j, aVarG.c(), aVarG.l, aVarG.m, aVarG.n);
                if (this.i.c.b()) {
                    this.i.a.a(c3765n);
                }
            } else if (a("convertchecknotnull")) {
                C3810v c3810vG = C3816w.G();
                c3810vG.a = this.h;
                c3810vG.b = textPositionH5;
                a((B2.a) c3810vG, false);
                TextPosition textPositionH7 = h();
                c3810vG.a(a(this.b, textPositionH5, textPositionH7));
                c3810vG.c = textPositionH7;
                C3816w c3816wF = c3810vG.f();
                if (this.i.c.c()) {
                    this.i.a.a(c3816wF);
                }
            } else if (this.i.c.d() && a("whyareyounotinlining")) {
                L2.a aVar2 = this.i.a;
                E4 e4G = F4.G();
                e4G.a = this.h;
                e4G.b = textPositionH5;
                a((B2.a) e4G, false);
                TextPosition textPositionH8 = h();
                e4G.a(a(this.b, textPositionH5, textPositionH8));
                e4G.c = textPositionH8;
                aVar2.a(e4G.f());
            } else if (this.i.c.e()) {
                if (a("assumemayhavesideeffects")) {
                    C3780p2 c3780p2G = C3785q2.G();
                    c3780p2G.a = this.h;
                    c3780p2G.b = textPositionH5;
                    a((B2.a) c3780p2G, false);
                    TextPosition textPositionH9 = h();
                    c3780p2G.a(a(this.b, textPositionH5, textPositionH9));
                    c3780p2G.c = textPositionH9;
                    this.i.a.a(c3780p2G.f());
                } else if (a("keepconstantarguments")) {
                    this.i.a.a((C3681a1) a(textPositionH5, C3681a1.G()));
                } else if (a("keepunusedarguments")) {
                    this.i.a.a((B1) a(textPositionH5, B1.G()));
                } else if (a("keepunusedreturnvalue")) {
                    int i = D1.r;
                    this.i.a.a((D1) a(textPositionH5, new C1()));
                } else if (a("alwaysclassinline")) {
                    this.i.a.a(a(r.ALWAYS, textPositionH5));
                } else if (a("neverclassinline")) {
                    this.i.a.a(a(r.NEVER, textPositionH5));
                } else if (a("neverinline")) {
                    this.i.a.a(a(Q0.NEVER, textPositionH5));
                } else if (a("neversinglecallerinline")) {
                    this.i.a.a(a(Q0.NEVER_SINGLE_CALLER, textPositionH5));
                } else if (a("nofieldtypestrengthening")) {
                    V1 v1G = W1.G();
                    v1G.a = this.h;
                    v1G.b = textPositionH5;
                    a((B2.a) v1G, false);
                    TextPosition textPositionH10 = h();
                    v1G.a(a(this.b, textPositionH5, textPositionH10));
                    v1G.c = textPositionH10;
                    this.i.a.a(v1G.f());
                } else if (a("nounusedinterfaceremoval")) {
                    C3741j2 c3741j2G = C3746k2.G();
                    c3741j2G.a = this.h;
                    c3741j2G.b = textPositionH5;
                    a((B2.a) c3741j2G, false);
                    TextPosition textPositionH11 = h();
                    c3741j2G.a(a(this.b, textPositionH5, textPositionH11));
                    c3741j2G.c = textPositionH11;
                    this.i.a.a(c3741j2G.f());
                } else if (a("noverticalclassmerging")) {
                    C3756l2 c3756l2G = C3763m2.G();
                    c3756l2G.a = this.h;
                    c3756l2G.b = textPositionH5;
                    a((B2.a) c3756l2G, false);
                    TextPosition textPositionH12 = h();
                    c3756l2G.a(a(this.b, textPositionH5, textPositionH12));
                    c3756l2G.c = textPositionH12;
                    this.i.a.a(c3756l2G.f());
                } else if (a("nohorizontalclassmerging")) {
                    X1 x1G = Y1.G();
                    x1G.a = this.h;
                    x1G.b = textPositionH5;
                    a((B2.a) x1G, false);
                    TextPosition textPositionH13 = h();
                    x1G.a(a(this.b, textPositionH5, textPositionH13));
                    x1G.c = textPositionH13;
                    this.i.a.a(x1G.f());
                } else if (a("nomethodstaticizing")) {
                    int i2 = C3683a2.r;
                    this.i.a.a(a(textPositionH5, new Z1()));
                } else if (a("noparameterreordering")) {
                    int i3 = C3711e2.r;
                    this.i.a.a(a(textPositionH5, new C3705d2()));
                } else if (a("noparametertypestrengthening")) {
                    int i4 = C3726g2.r;
                    this.i.a.a(a(textPositionH5, new C3717f2()));
                } else if (a("noreturntypestrengthening")) {
                    int i5 = C3736i2.r;
                    this.i.a.a(a(textPositionH5, new C3731h2()));
                } else if (a("neverpropagatevalue")) {
                    Q1 q1 = Q1.NEVER;
                    P1 p1G = R1.G();
                    p1G.a = this.h;
                    p1G.b = textPositionH5;
                    p1G.p = q1;
                    a((B2.a) p1G, false);
                    TextPosition textPositionH14 = h();
                    p1G.a(a(this.b, textPositionH5, textPositionH14));
                    p1G.c = textPositionH14;
                    this.i.a.a(p1G.f());
                } else if (a("neverreprocessclassinitializer")) {
                    L2.a aVar3 = this.i.a;
                    P3 p3G = Q3.G();
                    p3G.a = this.h;
                    p3G.b = textPositionH5;
                    P3 p3A = p3G.a(2);
                    a((B2.a) p3A, false);
                    TextPosition textPositionH15 = h();
                    p3A.a(a(this.b, textPositionH5, textPositionH15));
                    p3A.c = textPositionH15;
                    aVar3.a(p3A.f());
                } else if (a("neverreprocessmethod")) {
                    L2.a aVar4 = this.i.a;
                    R3 r3G = S3.G();
                    r3G.a = this.h;
                    r3G.b = textPositionH5;
                    R3 r3A = r3G.a(2);
                    a((B2.a) r3A, false);
                    TextPosition textPositionH16 = h();
                    r3A.a(a(this.b, textPositionH5, textPositionH16));
                    r3A.c = textPositionH16;
                    aVar4.a(r3A.f());
                } else if (a("reprocessclassinitializer")) {
                    L2.a aVar5 = this.i.a;
                    P3 p3G2 = Q3.G();
                    p3G2.a = this.h;
                    p3G2.b = textPositionH5;
                    P3 p3A2 = p3G2.a(1);
                    a((B2.a) p3A2, false);
                    TextPosition textPositionH17 = h();
                    p3A2.a(a(this.b, textPositionH5, textPositionH17));
                    p3A2.c = textPositionH17;
                    aVar5.a(p3A2.f());
                } else if (a("reprocessmethod")) {
                    L2.a aVar6 = this.i.a;
                    R3 r3G2 = S3.G();
                    r3G2.a = this.h;
                    r3G2.b = textPositionH5;
                    R3 r3A2 = r3G2.a(1);
                    a((B2.a) r3A2, false);
                    TextPosition textPositionH18 = h();
                    r3A2.a(a(this.b, textPositionH5, textPositionH18));
                    r3A2.c = textPositionH18;
                    aVar6.a(r3A2.f());
                } else if (!f(textPositionH5)) {
                    if (a("keepkotlinmetadata")) {
                        C3732h3 c3732h3A = AbstractC3764m3.a(this.h, textPositionH5, this.i.b.t3);
                        C3732h3 c3732h3A2 = AbstractC3764m3.a(this.h, textPositionH5, this.i.b.u3);
                        c3732h3A.D();
                        c3732h3A2.D();
                        this.i.a.a(c3732h3A);
                        this.i.a.a(c3732h3A2);
                        this.i.a.a(Collections.singletonList("RuntimeVisibleAnnotations"));
                        this.i.a.a(Collections.singletonList("RuntimeInvisibleAnnotations"));
                    } else {
                        str = "";
                        if (a("renamesourcefileattribute")) {
                            r();
                            if (i()) {
                                this.i.a.d(e());
                            } else {
                                this.i.a.d("");
                            }
                        } else if (a("keepattributes")) {
                            arrayList3 = new ArrayList();
                            r();
                            cD3 = d();
                            strC = c();
                            if (e(cD3)) {
                                c(cD3);
                            }
                            while (strC != null) {
                                arrayList3.add(strC);
                                r();
                                if (a(',')) {
                                    r();
                                    textPositionH4 = h();
                                    cD4 = d();
                                    strC2 = c();
                                    if (e(cD4)) {
                                        c(cD4);
                                    }
                                    if (strC2 != null) {
                                        throw b("Expected list element", textPositionH4);
                                    }
                                    strC = strC2;
                                } else {
                                    strC = null;
                                }
                            }
                            r();
                            if (g()) {
                            }
                            if (!arrayList3.isEmpty()) {
                                throw b("Expected attribute pattern list");
                            }
                            this.i.a.a((List<String>) arrayList3);
                        } else if (a("keeppackagenames")) {
                            final L2.a aVar7 = this.i.a;
                            Objects.requireNonNull(aVar7);
                            consumer7 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    aVar7.d((AbstractC3833y2) obj);
                                }
                            };
                            r();
                            if (i()) {
                                aVarB4 = AbstractC3833y2.b();
                                do {
                                    o2A4 = a(2, true);
                                    if (o2A4 != null) {
                                        throw b("Class name expected");
                                    }
                                    aVarB4.a(o2A4.b, G3.a(o2A4.a, G3.a.a, this.i.b));
                                    r();
                                } while (a(','));
                                consumer7.accept(aVarB4.a());
                            } else {
                                consumer7.accept(AbstractC3833y2.a(G3.b()));
                            }
                        } else if (a("keepparameternames")) {
                            this.i.a.a(this.h, a(textPositionH5));
                        } else if (a("checkdiscard")) {
                            C3814v2 c3814v2G = C3820w2.G();
                            c3814v2G.a = this.h;
                            c3814v2G.b = textPositionH5;
                            a((B2.a) c3814v2G, false);
                            TextPosition textPositionH19 = h();
                            c3814v2G.a(a(this.b, textPositionH5, textPositionH19));
                            c3814v2G.c = textPositionH19;
                            this.i.a.a(c3814v2G.f());
                        } else if (a("checkenumstringsdiscarded")) {
                            C3814v2 c3814v2G2 = C3820w2.G();
                            c3814v2G2.a = this.h;
                            c3814v2G2.b = textPositionH5;
                            a((B2.a) c3814v2G2, false);
                            TextPosition textPositionH20 = h();
                            c3814v2G2.a(a(this.b, textPositionH5, textPositionH20));
                            c3814v2G2.c = textPositionH20;
                            c3814v2G2.f();
                        } else if (a("keepdirectories")) {
                            this.i.a.f();
                            final L2.a aVar8 = this.i.a;
                            Objects.requireNonNull(aVar8);
                            consumer6 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda6
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    aVar8.c((E3) obj);
                                }
                            };
                            r();
                            if (i()) {
                                consumer6.accept(o());
                            } else {
                                consumer6.accept(E3.b());
                            }
                        } else if (a("keep")) {
                            this.i.a.a(e(textPositionH5));
                        } else if (a("whyareyoukeeping")) {
                            M3 m3G = N3.G();
                            m3G.a = this.h;
                            m3G.b = textPositionH5;
                            a((B2.a) m3G, false);
                            TextPosition textPositionH21 = h();
                            m3G.a(a(this.b, textPositionH5, textPositionH21));
                            m3G.c = textPositionH21;
                            this.i.a.a(m3G.f());
                        } else if (a("dontoptimize")) {
                            this.i.a.d();
                        } else if (a("optimizationpasses")) {
                            r();
                            if (b() != null) {
                                this.i.d.a(new StringDiagnostic("Missing n of \"-optimizationpasses n\"", this.h, a(textPositionH5)));
                                throw null;
                            }
                            a("optimizationpasses", textPositionH5);
                        } else if (a("dontobfuscate")) {
                            this.i.a.c();
                        } else if (a("dontshrink")) {
                            this.i.a.e();
                        } else if (a("printusage")) {
                            this.i.a.t();
                            r();
                            if (i()) {
                                this.i.a.g(a(false));
                            }
                        } else if (a("shrinkunusedprotofields")) {
                            this.i.a.g();
                        } else if (a("verbose")) {
                            this.i.a.u();
                        } else if (a("ignorewarnings")) {
                            this.i.a.b(true);
                        } else if (a("dontwarn")) {
                            final L2.a aVar9 = this.i.a;
                            Objects.requireNonNull(aVar9);
                            consumer5 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda7
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    aVar9.c((AbstractC3833y2) obj);
                                }
                            };
                            r();
                            if (i()) {
                                aVarB3 = AbstractC3833y2.b();
                                do {
                                    o2A3 = a(2, true);
                                    if (o2A3 != null) {
                                        throw b("Class name expected");
                                    }
                                    aVarB3.a(o2A3.b, G3.a(o2A3.a, G3.a.a, this.i.b));
                                    r();
                                } while (a(','));
                                consumer5.accept(aVarB3.a());
                            } else {
                                consumer5.accept(AbstractC3833y2.a(G3.b()));
                            }
                        } else if (a("dontnote")) {
                            final L2.a aVar10 = this.i.a;
                            Objects.requireNonNull(aVar10);
                            consumer4 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda8
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    aVar10.b((AbstractC3833y2) obj);
                                }
                            };
                            r();
                            if (i()) {
                                aVarB2 = AbstractC3833y2.b();
                                do {
                                    o2A2 = a(2, true);
                                    if (o2A2 != null) {
                                        throw b("Class name expected");
                                    }
                                    aVarB2.a(o2A2.b, G3.a(o2A2.a, G3.a.a, this.i.b));
                                    r();
                                } while (a(','));
                                consumer4.accept(aVarB2.a());
                            } else {
                                consumer4.accept(AbstractC3833y2.a(G3.b()));
                            }
                        } else if (a("repackageclasses")) {
                            if (this.i.a.k() == com.android.tools.r8.utils.A1.n.d) {
                                h(textPositionH5);
                            }
                            r();
                            cD2 = d();
                            if (e(cD2)) {
                                L2.a aVar11 = this.i.a;
                                String strA = a();
                                aVar11.c(strA != null ? strA : "");
                                c(cD2);
                            } else if (d('-')) {
                                this.i.a.c("");
                            } else {
                                L2.a aVar12 = this.i.a;
                                String strA2 = a();
                                aVar12.c(strA2 != null ? strA2 : "");
                            }
                        } else if (a("flattenpackagehierarchy")) {
                            if (this.i.a.k() == com.android.tools.r8.utils.A1.n.c) {
                                h(textPositionH5);
                                r();
                                if (i()) {
                                    r();
                                    while (!g()) {
                                        String str2 = this.b;
                                        int i6 = this.c;
                                        this.c = i6 + 1;
                                        str2.charAt(i6);
                                    }
                                }
                            } else {
                                r();
                                cD = d();
                                if (e(cD)) {
                                    L2.a aVar13 = this.i.a;
                                    String strA3 = a();
                                    aVar13.b(strA3 != null ? strA3 : "");
                                    c(cD);
                                } else if (d('-')) {
                                    this.i.a.b("");
                                } else {
                                    L2.a aVar14 = this.i.a;
                                    String strA4 = a();
                                    aVar14.b(strA4 != null ? strA4 : "");
                                }
                            }
                        } else if (a("allowaccessmodification")) {
                            this.i.a.p();
                        } else if (a("printconfiguration")) {
                            this.i.a.s();
                            r();
                            if (i()) {
                                this.i.a.e(a(false));
                            }
                        } else if (a("printmapping")) {
                            this.i.a.c(true);
                            r();
                            if (i()) {
                                this.i.a.f(a(false));
                            }
                        } else if (a("applymapping")) {
                            N2 n2 = this.i;
                            L2.a aVar15 = n2.a;
                            final InputDependencyGraphConsumer inputDependencyGraphConsumer = n2.e;
                            Objects.requireNonNull(inputDependencyGraphConsumer);
                            BiConsumer biConsumer3 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda9
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    inputDependencyGraphConsumer.acceptProguardApplyMapping((Origin) obj, (Path) obj2);
                                }
                            };
                            Path pathA = a(false);
                            biConsumer3.accept(this.h, pathA);
                            aVar15.a(pathA);
                        } else if (a("assumenosideeffects")) {
                            C3790r2 c3790r2G = C3795s2.G();
                            c3790r2G.a = this.h;
                            c3790r2G.b = textPositionH5;
                            a((B2.a) c3790r2G, true);
                            TextPosition textPositionH22 = h();
                            c3790r2G.a(a(this.b, textPositionH5, textPositionH22));
                            c3790r2G.c = textPositionH22;
                            this.i.a.a(c3790r2G.f());
                        } else if (a("assumevalues")) {
                            C3802t2 c3802t2G = C3808u2.G();
                            c3802t2G.a = this.h;
                            c3802t2G.b = textPositionH5;
                            a((B2.a) c3802t2G, true);
                            TextPosition textPositionH23 = h();
                            c3802t2G.a(a(this.b, textPositionH5, textPositionH23));
                            c3802t2G.c = textPositionH23;
                            this.i.a.a(c3802t2G.f());
                        } else if (a("include")) {
                            this.i.a.a(this.b.substring(this.d, this.c - 8));
                            r();
                            m();
                            this.d = this.c;
                        } else if (a("basedirectory")) {
                            r();
                            this.g = a(false);
                        } else if (a("injars")) {
                            N2 n3 = this.i;
                            L2.a aVar16 = n3.a;
                            final InputDependencyGraphConsumer inputDependencyGraphConsumer2 = n3.e;
                            Objects.requireNonNull(inputDependencyGraphConsumer2);
                            biConsumer2 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda10
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    inputDependencyGraphConsumer2.acceptProguardInJars((Origin) obj, (Path) obj2);
                                }
                            };
                            arrayList2 = new ArrayList();
                            r();
                            textPositionH3 = h();
                            Path pathA2 = a(true);
                            biConsumer2.accept(this.h, pathA2);
                            r();
                            if (a('(')) {
                                c1507Xl4 = new C1507Xl();
                                c1507Xl4.a(l());
                                r();
                                while (a(',')) {
                                    c1507Xl4.a(l());
                                    r();
                                }
                                if (p() != ';') {
                                    throw b("Only class file filters are supported in classpath");
                                }
                                b(')');
                                abstractC1639amI3 = c1507Xl4.a();
                            } else {
                                abstractC1639amI3 = AbstractC1639am.i();
                            }
                            arrayList2.add(new J0(pathA2, abstractC1639amI3, this.h, textPositionH3));
                            while (a(File.pathSeparatorChar)) {
                                Path pathA3 = a(true);
                                biConsumer2.accept(this.h, pathA3);
                                r();
                                if (a('(')) {
                                    c1507Xl3 = new C1507Xl();
                                    c1507Xl3.a(l());
                                    r();
                                    while (a(',')) {
                                        c1507Xl3.a(l());
                                        r();
                                    }
                                    if (p() != ';') {
                                        throw b("Only class file filters are supported in classpath");
                                    }
                                    b(')');
                                    abstractC1639amI4 = c1507Xl3.a();
                                } else {
                                    abstractC1639amI4 = AbstractC1639am.i();
                                }
                                arrayList2.add(new J0(pathA3, abstractC1639amI4, this.h, textPositionH3));
                            }
                            aVar16.a(arrayList2);
                        } else if (a("libraryjars")) {
                            N2 n4 = this.i;
                            L2.a aVar17 = n4.a;
                            final InputDependencyGraphConsumer inputDependencyGraphConsumer3 = n4.e;
                            Objects.requireNonNull(inputDependencyGraphConsumer3);
                            biConsumer = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda12
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    inputDependencyGraphConsumer3.acceptProguardLibraryJars((Origin) obj, (Path) obj2);
                                }
                            };
                            arrayList = new ArrayList();
                            r();
                            textPositionH2 = h();
                            Path pathA4 = a(true);
                            biConsumer.accept(this.h, pathA4);
                            r();
                            if (a('(')) {
                                c1507Xl2 = new C1507Xl();
                                c1507Xl2.a(l());
                                r();
                                while (a(',')) {
                                    c1507Xl2.a(l());
                                    r();
                                }
                                if (p() != ';') {
                                    throw b("Only class file filters are supported in classpath");
                                }
                                b(')');
                                abstractC1639amI = c1507Xl2.a();
                            } else {
                                abstractC1639amI = AbstractC1639am.i();
                            }
                            arrayList.add(new J0(pathA4, abstractC1639amI, this.h, textPositionH2));
                            while (a(File.pathSeparatorChar)) {
                                Path pathA5 = a(true);
                                biConsumer.accept(this.h, pathA5);
                                r();
                                if (a('(')) {
                                    c1507Xl = new C1507Xl();
                                    c1507Xl.a(l());
                                    r();
                                    while (a(',')) {
                                        c1507Xl.a(l());
                                        r();
                                    }
                                    if (p() != ';') {
                                        throw b("Only class file filters are supported in classpath");
                                    }
                                    b(')');
                                    abstractC1639amI2 = c1507Xl.a();
                                } else {
                                    abstractC1639amI2 = AbstractC1639am.i();
                                }
                                arrayList.add(new J0(pathA5, abstractC1639amI2, this.h, textPositionH2));
                            }
                            aVar17.b(arrayList);
                        } else if (a("printseeds")) {
                            this.i.a.d(true);
                            r();
                            if (i()) {
                                this.i.a.h(a(false));
                            }
                        } else if (a("obfuscationdictionary")) {
                            N2 n5 = this.i;
                            L2.a aVar18 = n5.a;
                            final InputDependencyGraphConsumer inputDependencyGraphConsumer4 = n5.e;
                            Objects.requireNonNull(inputDependencyGraphConsumer4);
                            BiConsumer biConsumer4 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda13
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    inputDependencyGraphConsumer4.acceptProguardObfuscationDictionary((Origin) obj, (Path) obj2);
                                }
                            };
                            Path pathA6 = a(false);
                            biConsumer4.accept(this.h, pathA6);
                            aVar18.c(pathA6);
                        } else if (a("classobfuscationdictionary")) {
                            N2 n6 = this.i;
                            L2.a aVar19 = n6.a;
                            final InputDependencyGraphConsumer inputDependencyGraphConsumer5 = n6.e;
                            Objects.requireNonNull(inputDependencyGraphConsumer5);
                            BiConsumer biConsumer5 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda14
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    inputDependencyGraphConsumer5.acceptProguardClassObfuscationDictionary((Origin) obj, (Path) obj2);
                                }
                            };
                            Path pathA7 = a(false);
                            biConsumer5.accept(this.h, pathA7);
                            aVar19.b(pathA7);
                        } else if (a("packageobfuscationdictionary")) {
                            N2 n7 = this.i;
                            L2.a aVar20 = n7.a;
                            final InputDependencyGraphConsumer inputDependencyGraphConsumer6 = n7.e;
                            Objects.requireNonNull(inputDependencyGraphConsumer6);
                            BiConsumer biConsumer6 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda15
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    inputDependencyGraphConsumer6.acceptProguardPackageObfuscationDictionary((Origin) obj, (Path) obj2);
                                }
                            };
                            Path pathA8 = a(false);
                            biConsumer6.accept(this.h, pathA8);
                            aVar20.d(pathA8);
                        } else if (a("alwaysinline")) {
                            this.i.a.a(a(Q0.ALWAYS, textPositionH5));
                        } else if (a("adaptclassstrings")) {
                            final L2.a aVar21 = this.i.a;
                            Objects.requireNonNull(aVar21);
                            consumer3 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda3
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    aVar21.a((AbstractC3833y2) obj);
                                }
                            };
                            r();
                            if (i()) {
                                aVarB = AbstractC3833y2.b();
                                do {
                                    o2A = a(2, true);
                                    if (o2A != null) {
                                        throw b("Class name expected");
                                    }
                                    aVarB.a(o2A.b, G3.a(o2A.a, G3.a.a, this.i.b));
                                    r();
                                } while (a(','));
                                consumer3.accept(aVarB.a());
                            } else {
                                consumer3.accept(AbstractC3833y2.a(G3.b()));
                            }
                        } else if (a("adaptresourcefilenames")) {
                            final L2.a aVar22 = this.i.a;
                            Objects.requireNonNull(aVar22);
                            consumer2 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda4
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    aVar22.b((E3) obj);
                                }
                            };
                            r();
                            if (i()) {
                                consumer2.accept(o());
                            } else {
                                consumer2.accept(E3.b());
                            }
                        } else if (a("adaptresourcefilecontents")) {
                            final L2.a aVar23 = this.i.a;
                            Objects.requireNonNull(aVar23);
                            consumer = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda5
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    aVar23.a((E3) obj);
                                }
                            };
                            r();
                            if (i()) {
                                consumer.accept(o());
                            } else {
                                consumer.accept(E3.b());
                            }
                        } else if (a("identifiernamestring")) {
                            L2.a aVar24 = this.i.a;
                            Z2 z2G = C3684a3.G();
                            z2G.a = this.h;
                            Z2 z2 = (Z2) z2G.e();
                            z2.b = textPositionH5;
                            Z2 z3 = (Z2) z2.e();
                            a((B2.a) z3, false);
                            TextPosition textPositionH24 = h();
                            z3.a(a(this.b, textPositionH5, textPositionH24));
                            z3.c = textPositionH24;
                            aVar24.a(z3.f());
                        } else if (a(PsiKeyword.IF)) {
                            aVar = this.i.a;
                            int i7 = C3712e3.y;
                            C3706d3 c3706d4 = new C3706d3();
                            c3706d4.a = this.h;
                            C3706d3 c3706d5 = (C3706d3) c3706d4.e();
                            c3706d5.b = textPositionH5;
                            c3706d3 = (C3706d3) c3706d5.e();
                            a((B2.a) c3706d3, false);
                            r();
                            textPositionH = h();
                            if (a("-keep")) {
                                this.i.d.a(new StringDiagnostic("Expecting '-keep' option after '-if' option.", this.h, a(textPositionH5)));
                                throw null;
                            }
                            c3706d3.r = e(textPositionH);
                            TextPosition textPositionH25 = h();
                            c3706d3.a(a(this.b, textPositionH5, textPositionH25));
                            c3706d3.c = textPositionH25;
                            C3712e3 c3712e3F = c3706d3.f();
                            a(c3712e3F.x());
                            aVar.a(c3712e3F);
                        } else if (a("addconfigurationdebugging")) {
                            this.i.a.q();
                        } else {
                            if (!a("dontusemixedcaseclassnames")) {
                                if (!a("maximumremovedandroidloglevel")) {
                                    r();
                                    numB = b();
                                    if (numB != null) {
                                    }
                                    throw b("Expected integer greater than or equal to 1", h());
                                }
                                String strF = f();
                                if (Version.isDevelopmentVersion()) {
                                    str = ", this option needs to be turned on explicitly if used for tests.";
                                }
                                a(textPositionH5, strF, str);
                                throw null;
                            }
                            this.i.a.r();
                        }
                    }
                }
            } else if (!f(textPositionH5)) {
                if (a("keepkotlinmetadata")) {
                    C3732h3 c3732h3A3 = AbstractC3764m3.a(this.h, textPositionH5, this.i.b.t3);
                    C3732h3 c3732h3A4 = AbstractC3764m3.a(this.h, textPositionH5, this.i.b.u3);
                    c3732h3A3.D();
                    c3732h3A4.D();
                    this.i.a.a(c3732h3A3);
                    this.i.a.a(c3732h3A4);
                    this.i.a.a(Collections.singletonList("RuntimeVisibleAnnotations"));
                    this.i.a.a(Collections.singletonList("RuntimeInvisibleAnnotations"));
                } else {
                    str = "";
                    if (a("renamesourcefileattribute")) {
                        r();
                        if (i()) {
                            this.i.a.d(e());
                        } else {
                            this.i.a.d("");
                        }
                    } else if (a("keepattributes")) {
                        arrayList3 = new ArrayList();
                        r();
                        cD3 = d();
                        strC = c();
                        if (e(cD3)) {
                            c(cD3);
                        }
                        while (strC != null) {
                            arrayList3.add(strC);
                            r();
                            if (a(',')) {
                                r();
                                textPositionH4 = h();
                                cD4 = d();
                                strC2 = c();
                                if (e(cD4)) {
                                    c(cD4);
                                }
                                if (strC2 != null) {
                                    throw b("Expected list element", textPositionH4);
                                }
                                strC = strC2;
                            } else {
                                strC = null;
                            }
                        }
                        r();
                        if (g() && !d('-') && !d('@')) {
                            throw b("Unexpected attribute");
                        }
                        if (!arrayList3.isEmpty()) {
                            throw b("Expected attribute pattern list");
                        }
                        this.i.a.a((List<String>) arrayList3);
                    } else if (a("keeppackagenames")) {
                        final L2.a aVar25 = this.i.a;
                        Objects.requireNonNull(aVar25);
                        consumer7 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                aVar25.d((AbstractC3833y2) obj);
                            }
                        };
                        r();
                        if (i()) {
                            aVarB4 = AbstractC3833y2.b();
                            do {
                                o2A4 = a(2, true);
                                if (o2A4 != null) {
                                    throw b("Class name expected");
                                }
                                aVarB4.a(o2A4.b, G3.a(o2A4.a, G3.a.a, this.i.b));
                                r();
                            } while (a(','));
                            consumer7.accept(aVarB4.a());
                        } else {
                            consumer7.accept(AbstractC3833y2.a(G3.b()));
                        }
                    } else if (a("keepparameternames")) {
                        this.i.a.a(this.h, a(textPositionH5));
                    } else if (a("checkdiscard")) {
                        C3814v2 c3814v2G3 = C3820w2.G();
                        c3814v2G3.a = this.h;
                        c3814v2G3.b = textPositionH5;
                        a((B2.a) c3814v2G3, false);
                        TextPosition textPositionH110 = h();
                        c3814v2G3.a(a(this.b, textPositionH5, textPositionH110));
                        c3814v2G3.c = textPositionH110;
                        this.i.a.a(c3814v2G3.f());
                    } else if (a("checkenumstringsdiscarded")) {
                        C3814v2 c3814v2G4 = C3820w2.G();
                        c3814v2G4.a = this.h;
                        c3814v2G4.b = textPositionH5;
                        a((B2.a) c3814v2G4, false);
                        TextPosition textPositionH26 = h();
                        c3814v2G4.a(a(this.b, textPositionH5, textPositionH26));
                        c3814v2G4.c = textPositionH26;
                        c3814v2G4.f();
                    } else if (a("keepdirectories")) {
                        this.i.a.f();
                        final L2.a aVar26 = this.i.a;
                        Objects.requireNonNull(aVar26);
                        consumer6 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda6
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                aVar26.c((E3) obj);
                            }
                        };
                        r();
                        if (i()) {
                            consumer6.accept(o());
                        } else {
                            consumer6.accept(E3.b());
                        }
                    } else if (a("keep")) {
                        this.i.a.a(e(textPositionH5));
                    } else if (a("whyareyoukeeping")) {
                        M3 m3G2 = N3.G();
                        m3G2.a = this.h;
                        m3G2.b = textPositionH5;
                        a((B2.a) m3G2, false);
                        TextPosition textPositionH27 = h();
                        m3G2.a(a(this.b, textPositionH5, textPositionH27));
                        m3G2.c = textPositionH27;
                        this.i.a.a(m3G2.f());
                    } else if (a("dontoptimize")) {
                        this.i.a.d();
                    } else if (a("optimizationpasses")) {
                        r();
                        if (b() != null) {
                            this.i.d.a(new StringDiagnostic("Missing n of \"-optimizationpasses n\"", this.h, a(textPositionH5)));
                            throw null;
                        }
                        a("optimizationpasses", textPositionH5);
                    } else if (a("dontobfuscate")) {
                        this.i.a.c();
                    } else if (a("dontshrink")) {
                        this.i.a.e();
                    } else if (a("printusage")) {
                        this.i.a.t();
                        r();
                        if (i()) {
                            this.i.a.g(a(false));
                        }
                    } else if (a("shrinkunusedprotofields")) {
                        this.i.a.g();
                    } else if (a("verbose")) {
                        this.i.a.u();
                    } else if (a("ignorewarnings")) {
                        this.i.a.b(true);
                    } else if (a("dontwarn")) {
                        final L2.a aVar27 = this.i.a;
                        Objects.requireNonNull(aVar27);
                        consumer5 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda7
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                aVar27.c((AbstractC3833y2) obj);
                            }
                        };
                        r();
                        if (i()) {
                            aVarB3 = AbstractC3833y2.b();
                            do {
                                o2A3 = a(2, true);
                                if (o2A3 != null) {
                                    throw b("Class name expected");
                                }
                                aVarB3.a(o2A3.b, G3.a(o2A3.a, G3.a.a, this.i.b));
                                r();
                            } while (a(','));
                            consumer5.accept(aVarB3.a());
                        } else {
                            consumer5.accept(AbstractC3833y2.a(G3.b()));
                        }
                    } else if (a("dontnote")) {
                        final L2.a aVar110 = this.i.a;
                        Objects.requireNonNull(aVar110);
                        consumer4 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda8
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                aVar110.b((AbstractC3833y2) obj);
                            }
                        };
                        r();
                        if (i()) {
                            aVarB2 = AbstractC3833y2.b();
                            do {
                                o2A2 = a(2, true);
                                if (o2A2 != null) {
                                    throw b("Class name expected");
                                }
                                aVarB2.a(o2A2.b, G3.a(o2A2.a, G3.a.a, this.i.b));
                                r();
                            } while (a(','));
                            consumer4.accept(aVarB2.a());
                        } else {
                            consumer4.accept(AbstractC3833y2.a(G3.b()));
                        }
                    } else if (a("repackageclasses")) {
                        if (this.i.a.k() == com.android.tools.r8.utils.A1.n.d) {
                            h(textPositionH5);
                        }
                        r();
                        cD2 = d();
                        if (e(cD2)) {
                            L2.a aVar111 = this.i.a;
                            String strA5 = a();
                            aVar111.c(strA5 != null ? strA5 : "");
                            c(cD2);
                        } else if (d('-')) {
                            this.i.a.c("");
                        } else {
                            L2.a aVar112 = this.i.a;
                            String strA6 = a();
                            aVar112.c(strA6 != null ? strA6 : "");
                        }
                    } else if (a("flattenpackagehierarchy")) {
                        if (this.i.a.k() == com.android.tools.r8.utils.A1.n.c) {
                            h(textPositionH5);
                            r();
                            if (i()) {
                                r();
                                while (!g() && !Character.isWhitespace(p())) {
                                    String str3 = this.b;
                                    int i8 = this.c;
                                    this.c = i8 + 1;
                                    str3.charAt(i8);
                                }
                            }
                        } else {
                            r();
                            cD = d();
                            if (e(cD)) {
                                L2.a aVar113 = this.i.a;
                                String strA7 = a();
                                aVar113.b(strA7 != null ? strA7 : "");
                                c(cD);
                            } else if (d('-')) {
                                this.i.a.b("");
                            } else {
                                L2.a aVar114 = this.i.a;
                                String strA8 = a();
                                aVar114.b(strA8 != null ? strA8 : "");
                            }
                        }
                    } else if (a("allowaccessmodification")) {
                        this.i.a.p();
                    } else if (a("printconfiguration")) {
                        this.i.a.s();
                        r();
                        if (i()) {
                            this.i.a.e(a(false));
                        }
                    } else if (a("printmapping")) {
                        this.i.a.c(true);
                        r();
                        if (i()) {
                            this.i.a.f(a(false));
                        }
                    } else if (a("applymapping")) {
                        N2 n8 = this.i;
                        L2.a aVar115 = n8.a;
                        final InputDependencyGraphConsumer inputDependencyGraphConsumer7 = n8.e;
                        Objects.requireNonNull(inputDependencyGraphConsumer7);
                        BiConsumer biConsumer7 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda9
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                inputDependencyGraphConsumer7.acceptProguardApplyMapping((Origin) obj, (Path) obj2);
                            }
                        };
                        Path pathA9 = a(false);
                        biConsumer7.accept(this.h, pathA9);
                        aVar115.a(pathA9);
                    } else if (a("assumenosideeffects")) {
                        C3790r2 c3790r2G2 = C3795s2.G();
                        c3790r2G2.a = this.h;
                        c3790r2G2.b = textPositionH5;
                        a((B2.a) c3790r2G2, true);
                        TextPosition textPositionH28 = h();
                        c3790r2G2.a(a(this.b, textPositionH5, textPositionH28));
                        c3790r2G2.c = textPositionH28;
                        this.i.a.a(c3790r2G2.f());
                    } else if (a("assumevalues")) {
                        C3802t2 c3802t2G2 = C3808u2.G();
                        c3802t2G2.a = this.h;
                        c3802t2G2.b = textPositionH5;
                        a((B2.a) c3802t2G2, true);
                        TextPosition textPositionH29 = h();
                        c3802t2G2.a(a(this.b, textPositionH5, textPositionH29));
                        c3802t2G2.c = textPositionH29;
                        this.i.a.a(c3802t2G2.f());
                    } else if (a("include")) {
                        this.i.a.a(this.b.substring(this.d, this.c - 8));
                        r();
                        m();
                        this.d = this.c;
                    } else if (a("basedirectory")) {
                        r();
                        this.g = a(false);
                    } else if (a("injars")) {
                        N2 n9 = this.i;
                        L2.a aVar116 = n9.a;
                        final InputDependencyGraphConsumer inputDependencyGraphConsumer8 = n9.e;
                        Objects.requireNonNull(inputDependencyGraphConsumer8);
                        biConsumer2 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda10
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                inputDependencyGraphConsumer8.acceptProguardInJars((Origin) obj, (Path) obj2);
                            }
                        };
                        arrayList2 = new ArrayList();
                        r();
                        textPositionH3 = h();
                        Path pathA10 = a(true);
                        biConsumer2.accept(this.h, pathA10);
                        r();
                        if (a('(')) {
                            c1507Xl4 = new C1507Xl();
                            c1507Xl4.a(l());
                            r();
                            while (a(',')) {
                                c1507Xl4.a(l());
                                r();
                            }
                            if (p() != ';') {
                                throw b("Only class file filters are supported in classpath");
                            }
                            b(')');
                            abstractC1639amI3 = c1507Xl4.a();
                        } else {
                            abstractC1639amI3 = AbstractC1639am.i();
                        }
                        arrayList2.add(new J0(pathA10, abstractC1639amI3, this.h, textPositionH3));
                        while (a(File.pathSeparatorChar)) {
                            Path pathA11 = a(true);
                            biConsumer2.accept(this.h, pathA11);
                            r();
                            if (a('(')) {
                                c1507Xl3 = new C1507Xl();
                                c1507Xl3.a(l());
                                r();
                                while (a(',')) {
                                    c1507Xl3.a(l());
                                    r();
                                }
                                if (p() != ';') {
                                    throw b("Only class file filters are supported in classpath");
                                }
                                b(')');
                                abstractC1639amI4 = c1507Xl3.a();
                            } else {
                                abstractC1639amI4 = AbstractC1639am.i();
                            }
                            arrayList2.add(new J0(pathA11, abstractC1639amI4, this.h, textPositionH3));
                        }
                        aVar116.a(arrayList2);
                    } else if (a("libraryjars")) {
                        N2 n10 = this.i;
                        L2.a aVar117 = n10.a;
                        final InputDependencyGraphConsumer inputDependencyGraphConsumer9 = n10.e;
                        Objects.requireNonNull(inputDependencyGraphConsumer9);
                        biConsumer = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda12
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                inputDependencyGraphConsumer9.acceptProguardLibraryJars((Origin) obj, (Path) obj2);
                            }
                        };
                        arrayList = new ArrayList();
                        r();
                        textPositionH2 = h();
                        Path pathA12 = a(true);
                        biConsumer.accept(this.h, pathA12);
                        r();
                        if (a('(')) {
                            c1507Xl2 = new C1507Xl();
                            c1507Xl2.a(l());
                            r();
                            while (a(',')) {
                                c1507Xl2.a(l());
                                r();
                            }
                            if (p() != ';') {
                                throw b("Only class file filters are supported in classpath");
                            }
                            b(')');
                            abstractC1639amI = c1507Xl2.a();
                        } else {
                            abstractC1639amI = AbstractC1639am.i();
                        }
                        arrayList.add(new J0(pathA12, abstractC1639amI, this.h, textPositionH2));
                        while (a(File.pathSeparatorChar)) {
                            Path pathA13 = a(true);
                            biConsumer.accept(this.h, pathA13);
                            r();
                            if (a('(')) {
                                c1507Xl = new C1507Xl();
                                c1507Xl.a(l());
                                r();
                                while (a(',')) {
                                    c1507Xl.a(l());
                                    r();
                                }
                                if (p() != ';') {
                                    throw b("Only class file filters are supported in classpath");
                                }
                                b(')');
                                abstractC1639amI2 = c1507Xl.a();
                            } else {
                                abstractC1639amI2 = AbstractC1639am.i();
                            }
                            arrayList.add(new J0(pathA13, abstractC1639amI2, this.h, textPositionH2));
                        }
                        aVar117.b(arrayList);
                    } else if (a("printseeds")) {
                        this.i.a.d(true);
                        r();
                        if (i()) {
                            this.i.a.h(a(false));
                        }
                    } else if (a("obfuscationdictionary")) {
                        N2 n11 = this.i;
                        L2.a aVar118 = n11.a;
                        final InputDependencyGraphConsumer inputDependencyGraphConsumer10 = n11.e;
                        Objects.requireNonNull(inputDependencyGraphConsumer10);
                        BiConsumer biConsumer8 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda13
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                inputDependencyGraphConsumer10.acceptProguardObfuscationDictionary((Origin) obj, (Path) obj2);
                            }
                        };
                        Path pathA14 = a(false);
                        biConsumer8.accept(this.h, pathA14);
                        aVar118.c(pathA14);
                    } else if (a("classobfuscationdictionary")) {
                        N2 n12 = this.i;
                        L2.a aVar119 = n12.a;
                        final InputDependencyGraphConsumer inputDependencyGraphConsumer11 = n12.e;
                        Objects.requireNonNull(inputDependencyGraphConsumer11);
                        BiConsumer biConsumer9 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda14
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                inputDependencyGraphConsumer11.acceptProguardClassObfuscationDictionary((Origin) obj, (Path) obj2);
                            }
                        };
                        Path pathA15 = a(false);
                        biConsumer9.accept(this.h, pathA15);
                        aVar119.b(pathA15);
                    } else if (a("packageobfuscationdictionary")) {
                        N2 n13 = this.i;
                        L2.a aVar28 = n13.a;
                        final InputDependencyGraphConsumer inputDependencyGraphConsumer12 = n13.e;
                        Objects.requireNonNull(inputDependencyGraphConsumer12);
                        BiConsumer biConsumer10 = new BiConsumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda15
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                inputDependencyGraphConsumer12.acceptProguardPackageObfuscationDictionary((Origin) obj, (Path) obj2);
                            }
                        };
                        Path pathA16 = a(false);
                        biConsumer10.accept(this.h, pathA16);
                        aVar28.d(pathA16);
                    } else if (a("alwaysinline")) {
                        this.i.a.a(a(Q0.ALWAYS, textPositionH5));
                    } else if (a("adaptclassstrings")) {
                        final L2.a aVar29 = this.i.a;
                        Objects.requireNonNull(aVar29);
                        consumer3 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                aVar29.a((AbstractC3833y2) obj);
                            }
                        };
                        r();
                        if (i()) {
                            aVarB = AbstractC3833y2.b();
                            do {
                                o2A = a(2, true);
                                if (o2A != null) {
                                    throw b("Class name expected");
                                }
                                aVarB.a(o2A.b, G3.a(o2A.a, G3.a.a, this.i.b));
                                r();
                            } while (a(','));
                            consumer3.accept(aVarB.a());
                        } else {
                            consumer3.accept(AbstractC3833y2.a(G3.b()));
                        }
                    } else if (a("adaptresourcefilenames")) {
                        final L2.a aVar210 = this.i.a;
                        Objects.requireNonNull(aVar210);
                        consumer2 = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                aVar210.b((E3) obj);
                            }
                        };
                        r();
                        if (i()) {
                            consumer2.accept(o());
                        } else {
                            consumer2.accept(E3.b());
                        }
                    } else if (a("adaptresourcefilecontents")) {
                        final L2.a aVar211 = this.i.a;
                        Objects.requireNonNull(aVar211);
                        consumer = new Consumer() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                aVar211.a((E3) obj);
                            }
                        };
                        r();
                        if (i()) {
                            consumer.accept(o());
                        } else {
                            consumer.accept(E3.b());
                        }
                    } else if (a("identifiernamestring")) {
                        L2.a aVar212 = this.i.a;
                        Z2 z2G2 = C3684a3.G();
                        z2G2.a = this.h;
                        Z2 z4 = (Z2) z2G2.e();
                        z4.b = textPositionH5;
                        Z2 z5 = (Z2) z4.e();
                        a((B2.a) z5, false);
                        TextPosition textPositionH210 = h();
                        z5.a(a(this.b, textPositionH5, textPositionH210));
                        z5.c = textPositionH210;
                        aVar212.a(z5.f());
                    } else if (a(PsiKeyword.IF)) {
                        aVar = this.i.a;
                        int i9 = C3712e3.y;
                        C3706d3 c3706d6 = new C3706d3();
                        c3706d6.a = this.h;
                        C3706d3 c3706d7 = (C3706d3) c3706d6.e();
                        c3706d7.b = textPositionH5;
                        c3706d3 = (C3706d3) c3706d7.e();
                        a((B2.a) c3706d3, false);
                        r();
                        textPositionH = h();
                        if (a("-keep")) {
                            this.i.d.a(new StringDiagnostic("Expecting '-keep' option after '-if' option.", this.h, a(textPositionH5)));
                            throw null;
                        }
                        c3706d3.r = e(textPositionH);
                        TextPosition textPositionH211 = h();
                        c3706d3.a(a(this.b, textPositionH5, textPositionH211));
                        c3706d3.c = textPositionH211;
                        C3712e3 c3712e3F2 = c3706d3.f();
                        a(c3712e3F2.x());
                        aVar.a(c3712e3F2);
                    } else if (a("addconfigurationdebugging")) {
                        this.i.a.q();
                    } else if (!a("dontusemixedcaseclassnames")) {
                        this.i.a.r();
                    } else {
                        if (!a("maximumremovedandroidloglevel")) {
                            String strF2 = f();
                            if (Version.isDevelopmentVersion() && strF2 != null && strF2.equals("neverinline")) {
                                str = ", this option needs to be turned on explicitly if used for tests.";
                            }
                            a(textPositionH5, strF2, str);
                            throw null;
                        }
                        r();
                        numB = b();
                        if (numB != null || numB.intValue() < 1) {
                            throw b("Expected integer greater than or equal to 1", h());
                        }
                        this.i.a.a(numB.intValue());
                    }
                }
            }
        }
        return true;
    }

    private E3 o() throws F3 {
        A3 a3A = E3.a();
        r();
        boolean zA = a('!');
        r();
        String strA = a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda16
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return this.f$0.a(Integer.valueOf(i));
            }
        });
        if (strA == null) {
            throw b("Path filter expected");
        }
        a3A.a(strA, zA);
        r();
        while (a(',')) {
            r();
            boolean zA2 = a('!');
            r();
            String strA2 = a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda16
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    return this.f$0.a(Integer.valueOf(i));
                }
            });
            if (strA2 == null) {
                throw b("Path filter expected");
            }
            a3A.a(strA2, zA2);
            r();
        }
        return a3A.b();
    }

    private char p() {
        return this.b.charAt(this.c);
    }

    private void q() throws F3 {
        char cD = d();
        if (e(cD)) {
            r();
        }
        if (a('!')) {
            r();
        }
        a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return P2.f(i);
            }
        });
        if (e(cD)) {
            r();
            c(cD);
        }
    }

    private void r() {
        while (!g() && com.android.tools.r8.utils.Y2.b(p())) {
            if (p() == '\n') {
                this.e++;
                this.f = this.c + 1;
            }
            this.c++;
        }
        if (!g() && p() == '#') {
            while (!g() && p() != '\n') {
                this.c++;
            }
            r();
        }
    }

    private String s() {
        String[] strArrSplit = this.b.split("\n", -1);
        int length = this.c;
        for (int i = 0; i < strArrSplit.length; i++) {
            String str = strArrSplit[i];
            if (length <= str.length() || i == strArrSplit.length - 1) {
                return this.a + ":" + (i + 1) + ":" + (length + 1) + "\n" + str + "\n" + (CharBuffer.allocate(length).toString().replace((char) 0, StringUtil.SPACE) + "^");
            }
            length -= str.length() + 1;
        }
        return this.a;
    }

    public final void j() throws F3 {
        do {
            r();
        } while (n());
        this.i.a.a("# The proguard configuration file for the following section is " + this.h.toString());
        this.i.a.a(this.b.substring(this.d));
        this.i.a.a("# End of content from " + this.h.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e(String str) {
        if (!a(str)) {
            return false;
        }
        try {
            a((B2.a) C3732h3.I(), true);
            return true;
        } catch (F3 e) {
            this.i.d.a(e);
            throw null;
        }
    }

    private Integer b() {
        String strA = a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda1
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return Character.isDigit(i);
            }
        });
        if (strA == null) {
            return null;
        }
        return Integer.valueOf(Integer.parseInt(strA));
    }

    private C3792s a(r rVar, TextPosition textPosition) throws F3 {
        C3782q c3782qG = C3792s.G();
        c3782qG.a = this.h;
        c3782qG.b = textPosition;
        c3782qG.p = rVar;
        a((B2.a) c3782qG, false);
        TextPosition textPositionH = h();
        c3782qG.a(a(this.b, textPosition, textPositionH));
        c3782qG.c = textPositionH;
        return new C3792s(c3782qG.a, c3782qG.d(), c3782qG.d, c3782qG.b(), c3782qG.f, c3782qG.g, c3782qG.h, c3782qG.i, c3782qG.j, c3782qG.c(), c3782qG.l, c3782qG.m, c3782qG.n, c3782qG.p);
    }

    private boolean g() {
        return this.c == this.b.length();
    }

    private void h(TextPosition textPosition) {
        this.i.d.warning(new StringDiagnostic("Option -repackageclasses overrides -flattenpackagehierarchy", this.h, a(textPosition)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean f(String str) {
        if (!a(str)) {
            return false;
        }
        r();
        if (!i()) {
            return true;
        }
        r();
        while (!g() && !Character.isWhitespace(p())) {
            String str2 = this.b;
            int i = this.c;
            this.c = i + 1;
            str2.charAt(i);
        }
        return true;
    }

    private String g(TextPosition textPosition) {
        String str = this.b.split("\n", -1)[textPosition.getLine() - 1];
        String str2 = CharBuffer.allocate(textPosition.getColumn() - 1).toString().replace((char) 0, StringUtil.SPACE) + "^";
        return this.a + ":" + (textPosition.getLine() + 1) + ":" + textPosition.getColumn() + "\n" + str + "\n" + str2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(int i) {
        return AbstractC3916j1.a(i) || i == 46 || i == 42 || i == 63 || i == 37 || i == 91 || i == 93;
    }

    private TextPosition h() {
        int i = this.c;
        return new TextPosition(i, this.e, (i - this.f) + 1);
    }

    private F3 b(String str) {
        return new F3(str, s(), this.h, h());
    }

    private C3732h3 e(TextPosition textPosition) throws F3 {
        C3732h3.a aVarI = C3732h3.I();
        aVarI.a = this.h;
        aVarI.b = textPosition;
        if (a("names")) {
            aVarI.a(EnumC3758l3.KEEP);
            aVarI.q.g();
        } else if (a("class")) {
            if (a("members")) {
                aVarI.a(EnumC3758l3.KEEP_CLASS_MEMBERS);
            } else if (a("eswithmembers")) {
                aVarI.a(EnumC3758l3.KEEP_CLASSES_WITH_MEMBERS);
            } else if (a("membernames")) {
                aVarI.a(EnumC3758l3.KEEP_CLASS_MEMBERS);
                aVarI.q.g();
            } else if (a("eswithmembernames")) {
                aVarI.a(EnumC3758l3.KEEP_CLASSES_WITH_MEMBERS);
                aVarI.q.g();
            } else {
                h("-keepclass");
                TextPosition textPositionH = h();
                a(Parameters.DEFAULT_OPTION_PREFIXES);
                a(textPositionH, f(), "");
                throw null;
            }
        } else {
            aVarI.a(EnumC3758l3.KEEP);
        }
        if (!g() && !Character.isWhitespace(p()) && p() != ',') {
            h("-keep");
            TextPosition textPositionH2 = h();
            a(Parameters.DEFAULT_OPTION_PREFIXES);
            a(textPositionH2, f(), "");
            throw null;
        }
        r();
        while (a(',')) {
            r();
            TextPosition textPositionH3 = h();
            if (a("allow")) {
                if (a("shrinking")) {
                    aVarI.q.g();
                } else if (a("optimization")) {
                    aVarI.q.e();
                } else if (a("obfuscation")) {
                    aVarI.q.d();
                } else if (a("accessmodification")) {
                    aVarI.q.b();
                } else if (a("repackage")) {
                    aVarI.q.f();
                } else if (this.i.c.e() && a("annotationremoval")) {
                    aVarI.q.c();
                }
            } else if (a("includedescriptorclasses")) {
                aVarI.q.h();
            } else if (a("includecode")) {
                b(textPositionH3);
            }
            r();
        }
        a((B2.a) aVarI, false);
        if (aVarI.n.isEmpty()) {
            C3775o3.a aVarA = C3775o3.a();
            aVarA.a(N2.a.a("<init>"));
            aVarA.a(EnumC3791r3.INIT);
            aVarA.b(Collections.emptyList());
            aVarI.n.add(aVarA.a());
        }
        TextPosition textPositionH4 = h();
        aVarI.a(a(this.b, textPosition, textPositionH4));
        aVarI.c = textPositionH4;
        return aVarI.f();
    }

    private F3 b(String str, TextPosition textPosition) {
        return new F3(str, g(textPosition), this.h, a(textPosition));
    }

    private void b(TextPosition textPosition) {
        this.i.d.info(new StringDiagnostic("Ignoring modifier: includecode", this.h, a(textPosition)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean f(int i) {
        return Character.isAlphabetic(i) || i == 47 || i == 42;
    }

    private String f() {
        return a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda19
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return P2.d(i);
            }
        });
    }

    private String c(String str) throws F3 {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = -1;
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (str.charAt(i3) == '<') {
                if (i < i3) {
                    sb.append((CharSequence) str, i, i3);
                    i = i3;
                }
                i2 = i3;
            } else if (str.charAt(i3) == '>' && i2 != -1 && i2 < i3) {
                String strSubstring = str.substring(i2 + 1, i3);
                String property = strSubstring.length() > 0 ? System.getProperty(strSubstring) : null;
                if (property != null) {
                    sb.append(property);
                    i = i3 + 1;
                    i2 = -1;
                } else {
                    throw b("Value of system property '" + strSubstring + "' not found");
                }
            }
        }
        if (i == 0) {
            return str;
        }
        sb.append(str.substring(i, str.length()));
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d(String str) {
        return a(str);
    }

    private boolean d(char c) {
        return !g() && p() == c;
    }

    private char d() {
        if (!(g() ? false : new Predicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.e(((Character) obj).charValue());
            }
        }.test(Character.valueOf(p())))) {
            return (char) 0;
        }
        String str = this.b;
        int i = this.c;
        this.c = i + 1;
        return str.charAt(i);
    }

    private AbstractC3699c2 a(TextPosition textPosition, AbstractC3693b2 abstractC3693b2) throws F3 {
        abstractC3693b2.a = this.h;
        AbstractC3693b2 abstractC3693b3 = (AbstractC3693b2) abstractC3693b2.e();
        abstractC3693b3.b = textPosition;
        abstractC3693b3.e();
        a((B2.a) abstractC3693b2, false);
        TextPosition textPositionH = h();
        abstractC3693b2.a(a(this.b, textPosition, textPositionH));
        abstractC3693b2.c = textPositionH;
        abstractC3693b2.e();
        return (AbstractC3699c2) abstractC3693b2.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean d(int i) {
        return !Character.isWhitespace(i);
    }

    private R0 a(Q0 q0, TextPosition textPosition) throws F3 {
        P0 p0G = R0.G();
        p0G.a = this.h;
        p0G.b = textPosition;
        p0G.p = q0;
        a((B2.a) p0G, false);
        TextPosition textPositionH = h();
        p0G.a(a(this.b, textPosition, textPositionH));
        p0G.c = textPositionH;
        return new R0(p0G.a, p0G.d(), p0G.d, p0G.b(), p0G.f, p0G.g, p0G.h, p0G.i, p0G.j, p0G.c(), p0G.l, p0G.m, p0G.n, p0G.p);
    }

    private void c(char c) throws F3 {
        if (!j && !e(c)) {
            throw new AssertionError();
        }
        if (d(c)) {
            a(c);
            return;
        }
        throw b("Missing closing quote");
    }

    private String c() {
        return a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda20
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return P2.c(i);
            }
        });
    }

    final void a(Iterable iterable) {
        ArrayList arrayList = new ArrayList();
        Iterator it = iterable.iterator();
        boolean z = false;
        while (it.hasNext()) {
            O3 o3 = (O3) it.next();
            if (o3.d()) {
                O3.a aVarA = o3.a();
                int size = arrayList.size();
                int i = aVarA.b;
                if (size >= i) {
                    aVarA.a((O3.b) arrayList.get(i - 1));
                    z = true;
                } else {
                    this.i.d.a(new StringDiagnostic("Wildcard <" + aVarA.b + "> is invalid (only seen " + arrayList.size() + " at this point).", this.h, h()));
                    throw null;
                }
            } else {
                if (!j && !o3.e()) {
                    throw new AssertionError();
                }
                if (!z) {
                    arrayList.add(o3.b());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(int i) {
        return AbstractC3916j1.a(i) || i == 33 || i == 42 || i == 46;
    }

    private void c(String str, TextPosition textPosition) {
        this.i.d.warning(new StringDiagnostic("Ignoring option: -" + str, this.h, a(textPosition)));
    }

    /* JADX WARN: Code duplicated, block: B:350:0x0656  */
    /* JADX WARN: Code duplicated, block: B:385:0x0707  */
    /* JADX WARN: Code duplicated, block: B:388:0x0715  */
    /* JADX WARN: Code duplicated, block: B:389:0x071a  */
    /* JADX WARN: Code duplicated, block: B:391:0x071d A[LOOP:3: B:108:0x01e1->B:391:0x071d, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:438:0x072e A[SYNTHETIC] */
    private void a(B2.a aVar, boolean z) throws F3 {
        G3 g3A;
        C3774o2 c3774o2;
        G3 g3A2;
        N2.a aVar2;
        String str;
        String str2;
        String str3;
        boolean z2;
        N2.a aVar3;
        String strSubstring;
        int iCharCount;
        Integer numB;
        C3775o3 c3775o3A;
        boolean zA;
        G3 g3A3;
        while (true) {
            r();
            int i = this.c;
            char c = '@';
            boolean zA2 = a('@');
            String str4 = PsiKeyword.INTERFACE;
            int i2 = 2;
            boolean z3 = false;
            if (zA2) {
                O2 o2A = a(2, false);
                if (o2A != null) {
                    if (!j && o2A.b) {
                        throw new AssertionError();
                    }
                    N2.a aVar4 = o2A.a;
                    if (aVar4.a.equals(PsiKeyword.INTERFACE)) {
                        this.c = i;
                        g3A = null;
                    } else {
                        g3A = G3.a(aVar4, G3.a.a, this.i.b);
                    }
                } else {
                    throw b("Class name expected");
                }
            } else {
                g3A = null;
            }
            if (g3A != null) {
                aVar.a(g3A);
            } else {
                int i3 = this.c;
                r();
                if (a('!')) {
                    c3774o2 = aVar.g;
                } else {
                    c3774o2 = aVar.f;
                }
                r();
                String str5 = "public";
                if (a("public")) {
                    c3774o2.w();
                } else {
                    String str6 = "final";
                    if (a("final")) {
                        c3774o2.s();
                    } else if (a("abstract")) {
                        c3774o2.p();
                    } else {
                        this.c = i3;
                        r();
                        TextPosition textPositionH = h();
                        boolean z4 = true;
                        if (a('!')) {
                            aVar.h = true;
                            aVar.e();
                        }
                        if (a('@')) {
                            r();
                            if (a(PsiKeyword.INTERFACE)) {
                                aVar.i = I2.a;
                                aVar.e();
                            } else {
                                this.i.d.a(new StringDiagnostic("Expected [!]interface|@interface|class|enum", this.h, a(textPositionH)));
                                throw null;
                            }
                        } else if (a(PsiKeyword.INTERFACE)) {
                            aVar.i = I2.d;
                            aVar.e();
                        } else if (a("class")) {
                            aVar.i = I2.b;
                            aVar.e();
                        } else if (a(PsiKeyword.ENUM)) {
                            aVar.i = I2.c;
                            aVar.e();
                        } else {
                            this.i.d.a(new StringDiagnostic("Expected [!]interface|@interface|class|enum", this.h, a(textPositionH)));
                            throw null;
                        }
                        AbstractC3833y2.a aVarB = AbstractC3833y2.b();
                        do {
                            O2 o2A2 = a(2, true);
                            if (o2A2 != null) {
                                aVarB.a(o2A2.b, G3.a(o2A2.a, G3.a.a, this.i.b));
                                r();
                            } else {
                                throw b("Class name expected");
                            }
                        } while (a(','));
                        aVar.a(aVarB.a());
                        r();
                        if (a(PsiKeyword.IMPLEMENTS)) {
                            aVar.m = false;
                            aVar.e();
                        } else {
                            if (a(PsiKeyword.EXTENDS)) {
                                aVar.m = true;
                                aVar.e();
                            }
                            r();
                            if (!g() || !a('{')) {
                                return;
                            }
                            while (true) {
                                C3775o3.a aVarA = C3775o3.a();
                                List listEmptyList = null;
                                while (true) {
                                    r();
                                    int i4 = this.c;
                                    if (a(c)) {
                                        O2 o2A3 = a(i2, z3);
                                        if (o2A3 != null) {
                                            if (!j && o2A3.b) {
                                                throw new AssertionError();
                                            }
                                            N2.a aVar5 = o2A3.a;
                                            if (aVar5.a.equals(str4)) {
                                                this.c = i4;
                                                g3A2 = null;
                                            } else {
                                                g3A2 = G3.a(aVar5, G3.a.a, this.i.b);
                                            }
                                        } else {
                                            throw b("Class name expected");
                                        }
                                    } else {
                                        g3A2 = null;
                                    }
                                    if (g3A2 != null) {
                                        if (listEmptyList == null) {
                                            listEmptyList = new ArrayList(i2);
                                        }
                                        listEmptyList.add(g3A2);
                                    }
                                }
                                if (listEmptyList == null) {
                                    listEmptyList = Collections.emptyList();
                                }
                                aVarA.a(listEmptyList);
                                boolean z5 = z4;
                                while (z5 && !g()) {
                                    r();
                                    boolean zA3 = a('!');
                                    C3774o2 c3774o2C = zA3 ? aVarA.c() : aVarA.b();
                                    r();
                                    char cP = p();
                                    if (cP == 'f') {
                                        zA = a(str6);
                                        if (zA) {
                                            c3774o2C.s();
                                        }
                                    } else if (cP == 'n') {
                                        zA = a("native");
                                        if (zA) {
                                            c3774o2C.t();
                                        }
                                    } else if (cP == 'p') {
                                        zA = a(str5);
                                        if (zA) {
                                            c3774o2C.w();
                                        } else {
                                            zA = a("private");
                                            if (zA) {
                                                c3774o2C.u();
                                            } else {
                                                zA = a("protected");
                                                if (zA) {
                                                    c3774o2C.v();
                                                }
                                            }
                                        }
                                    } else if (cP == 'v') {
                                        zA = a("volatile");
                                        if (zA) {
                                            c3774o2C.C();
                                        }
                                    } else if (cP == 's') {
                                        zA = a("synchronized");
                                        if (zA) {
                                            c3774o2C.z();
                                        } else {
                                            zA = a("static");
                                            if (zA) {
                                                c3774o2C.x();
                                            } else {
                                                zA = a("strictfp");
                                                if (zA) {
                                                    c3774o2C.y();
                                                } else {
                                                    zA = a("synthetic");
                                                    if (zA) {
                                                        c3774o2C.A();
                                                    }
                                                }
                                            }
                                        }
                                    } else if (cP == 't') {
                                        zA = a("transient");
                                        if (zA) {
                                            c3774o2C.B();
                                        }
                                    } else {
                                        switch (cP) {
                                            case 'a':
                                                zA = a("abstract");
                                                if (zA) {
                                                    c3774o2C.p();
                                                }
                                                break;
                                            case 'b':
                                                zA = a("bridge");
                                                if (zA) {
                                                    c3774o2C.q();
                                                }
                                                break;
                                            case 'c':
                                                zA = a(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME);
                                                if (zA) {
                                                    c3774o2C.r();
                                                }
                                                break;
                                            default:
                                                z5 = z3;
                                                break;
                                        }
                                        if (z5 && zA3) {
                                            h("!");
                                        }
                                    }
                                    z5 = zA;
                                    if (z5) {
                                    }
                                }
                                r();
                                if (!g() && p() == '!') {
                                    throw b("Unexpected character '!': The negation character can only be used to negate access flags");
                                }
                                if (a("<methods>")) {
                                    aVarA.a(EnumC3791r3.ALL_METHODS);
                                } else if (a("<fields>")) {
                                    aVarA.a(EnumC3791r3.ALL_FIELDS);
                                } else if (a("<init>")) {
                                    aVarA.a(EnumC3791r3.INIT);
                                    aVarA.a(N2.a.a("<init>"));
                                    aVarA.b(k());
                                } else {
                                    if (a(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                                        aVarA.a(EnumC3791r3.CLINIT);
                                        aVarA.a(N2.a.a(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME));
                                        aVarA.b(k());
                                    } else {
                                        TextPosition textPositionH2 = h();
                                        O2 o2A4 = a(3, z3);
                                        if (o2A4 == null) {
                                            aVar2 = null;
                                        } else {
                                            if (!j && o2A4.b) {
                                                throw new AssertionError();
                                            }
                                            aVar2 = o2A4.a;
                                        }
                                        if (aVar2 != null) {
                                            r();
                                            if (aVar2.a.equals("*") && d(';')) {
                                                aVarA.a(EnumC3791r3.ALL);
                                                str = str4;
                                                str2 = str5;
                                                str3 = str6;
                                                z2 = true;
                                                if (aVarA.d()) {
                                                    r();
                                                    b(';');
                                                }
                                            } else {
                                                str = str4;
                                                str2 = str5;
                                                if (d('(')) {
                                                    if (aVar2.a.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                                                        this.i.d.warning(new StringDiagnostic("Member rule for <clinit> has no effect.", this.h, textPositionH2));
                                                    } else if (!aVar2.a.contains("<")) {
                                                        if (aVar2.a.contains(">")) {
                                                            throw b("Unexpected character '>' in method name. The character '>' is only allowed in the method name '<init>'.", textPositionH2);
                                                        }
                                                    } else {
                                                        throw b("Unexpected character '<' in method name. The character '<' is only allowed in the method name '<init>'.", textPositionH2);
                                                    }
                                                    aVarA.a(EnumC3791r3.CONSTRUCTOR);
                                                    aVarA.a(aVar2);
                                                    aVarA.b(k());
                                                } else if (a("<init>")) {
                                                    G3 g3A4 = G3.a(aVar2, G3.a.b, this.i.b);
                                                    if ((g3A4.c() != null) && g3A4.c().Y0()) {
                                                        aVarA.a(EnumC3791r3.INIT);
                                                        aVarA.a(N2.a.a("<init>"));
                                                        aVarA.a(g3A4);
                                                        aVarA.b(k());
                                                    } else {
                                                        throw b("Expected [access-flag]* void <init>");
                                                    }
                                                } else if (a(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                                                    G3 g3A5 = G3.a(aVar2, G3.a.b, this.i.b);
                                                    if ((g3A5.c() != null) && g3A5.c().Y0()) {
                                                        aVarA.a(EnumC3791r3.CLINIT);
                                                        aVarA.a(N2.a.a(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME));
                                                        aVarA.a(g3A5);
                                                        aVarA.b(k());
                                                    } else {
                                                        throw b("Expected [access-flag]* void <clinit>");
                                                    }
                                                } else {
                                                    TextPosition textPositionH3 = h();
                                                    str3 = str6;
                                                    O2 o2A5 = a(3, false);
                                                    if (o2A5 == null) {
                                                        aVar3 = null;
                                                    } else {
                                                        if (!j && o2A5.b) {
                                                            throw new AssertionError();
                                                        }
                                                        aVar3 = o2A5.a;
                                                    }
                                                    if (aVar3 != null) {
                                                        r();
                                                        if (d('(')) {
                                                            if (!aVar3.a() || aVar3.b()) {
                                                                if (aVar3.a.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                                                                    this.i.d.warning(new StringDiagnostic("Member rule for <clinit> has no effect.", this.h, textPositionH3));
                                                                } else if (!aVar3.a.contains("<")) {
                                                                    if (aVar3.a.contains(">")) {
                                                                        throw b("Unexpected character '>' in method name. The character '>' is only allowed in the method name '<init>'.", textPositionH3);
                                                                    }
                                                                } else {
                                                                    throw b("Unexpected character '<' in method name. The character '<' is only allowed in the method name '<init>'.", textPositionH3);
                                                                }
                                                            }
                                                            aVarA.a(EnumC3791r3.METHOD);
                                                            aVarA.a(aVar3);
                                                            aVarA.a(G3.a(aVar2, G3.a.b, this.i.b));
                                                            aVarA.b(k());
                                                        } else {
                                                            if (aVar2.b()) {
                                                                a(ModuleXmlParser.TYPE, aVar2.a, textPositionH2);
                                                            }
                                                            if (aVar3.b()) {
                                                                a("field name", aVar3.a, textPositionH3);
                                                            }
                                                            aVarA.a(EnumC3791r3.FIELD);
                                                            aVarA.a(aVar3);
                                                            aVarA.a(G3.a(aVar2, G3.a.b, this.i.b));
                                                        }
                                                        r();
                                                        TextPosition textPositionH4 = h();
                                                        if (a("return")) {
                                                            if (z) {
                                                                r();
                                                                if (a("true")) {
                                                                    aVarA.a(new C3786q3(true));
                                                                } else if (a("false")) {
                                                                    aVarA.a(new C3786q3(false));
                                                                } else if (a("null")) {
                                                                    aVarA.a(new C3786q3(WH.c()));
                                                                } else {
                                                                    Integer numB2 = b();
                                                                    if (numB2 != null) {
                                                                        r();
                                                                        if (a("..")) {
                                                                            r();
                                                                            numB = b();
                                                                            if (numB == null) {
                                                                                throw b("Expected integer value");
                                                                            }
                                                                        } else {
                                                                            numB = numB2;
                                                                        }
                                                                        aVarA.a(new C3786q3(new com.android.tools.r8.utils.W1(numB2.intValue(), numB.intValue())));
                                                                    } else {
                                                                        WH whI = WH.i();
                                                                        if (a("_NONNULL_")) {
                                                                            whI = WH.b();
                                                                            r();
                                                                            if (a(';')) {
                                                                                aVarA.a(new C3786q3(whI));
                                                                                z3 = false;
                                                                                z2 = true;
                                                                            }
                                                                        }
                                                                        r();
                                                                        int i5 = this.c;
                                                                        if (a(i5)) {
                                                                            strSubstring = null;
                                                                        } else {
                                                                            int iCodePointAt = this.b.codePointAt(i5);
                                                                            if (AbstractC3916j1.b(this.b.codePointAt(i5))) {
                                                                                int iCharCount2 = Character.charCount(iCodePointAt) + i5;
                                                                                while (!a(iCharCount2)) {
                                                                                    int iCodePointAt2 = this.b.codePointAt(iCharCount2);
                                                                                    if (iCodePointAt2 == 46) {
                                                                                        iCharCount2++;
                                                                                        if (!a(iCharCount2)) {
                                                                                            if (!j && a(iCharCount2)) {
                                                                                                throw new AssertionError();
                                                                                            }
                                                                                            if (AbstractC3916j1.a(this.b.charAt(iCharCount2))) {
                                                                                                iCharCount = Character.charCount(this.b.codePointAt(iCharCount2));
                                                                                                iCharCount2 += iCharCount;
                                                                                            }
                                                                                        }
                                                                                        this.c = iCharCount2;
                                                                                        strSubstring = this.b.substring(i5, iCharCount2);
                                                                                    } else if (AbstractC3916j1.a(iCodePointAt2)) {
                                                                                        iCharCount = Character.charCount(iCodePointAt2);
                                                                                        iCharCount2 += iCharCount;
                                                                                    } else {
                                                                                        this.c = iCharCount2;
                                                                                        strSubstring = this.b.substring(i5, iCharCount2);
                                                                                    }
                                                                                }
                                                                                this.c = iCharCount2;
                                                                                strSubstring = this.b.substring(i5, iCharCount2);
                                                                            } else {
                                                                                strSubstring = null;
                                                                            }
                                                                        }
                                                                        if (strSubstring != null) {
                                                                            int iLastIndexOf = strSubstring.lastIndexOf(".");
                                                                            z3 = false;
                                                                            z2 = true;
                                                                            aVarA.a(new C3786q3(this.i.b.e(C3970u0.H(strSubstring.substring(0, iLastIndexOf))), this.i.b.c(strSubstring.substring(iLastIndexOf + 1)), whI));
                                                                            if (aVarA.d()) {
                                                                                r();
                                                                                b(';');
                                                                            }
                                                                        } else {
                                                                            throw b("Expected qualified field");
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                throw b("Unexpected value specification", textPositionH4);
                                                            }
                                                        }
                                                        z3 = false;
                                                        z2 = true;
                                                        if (aVarA.d()) {
                                                            r();
                                                            b(';');
                                                        }
                                                    } else {
                                                        throw b("Expected field or method name");
                                                    }
                                                }
                                                str3 = str6;
                                                z3 = false;
                                                z2 = true;
                                                if (aVarA.d()) {
                                                    r();
                                                    b(';');
                                                }
                                            }
                                        } else {
                                            str = str4;
                                            str2 = str5;
                                            str3 = str6;
                                            z2 = true;
                                            if (aVarA.d()) {
                                                r();
                                                b(';');
                                            }
                                        }
                                    }
                                    if (aVarA.d()) {
                                        c3775o3A = aVarA.a();
                                    } else {
                                        c3775o3A = null;
                                    }
                                    if (c3775o3A != null) {
                                        aVar.n.add(c3775o3A);
                                        z4 = z2;
                                        str6 = str3;
                                        str4 = str;
                                        str5 = str2;
                                        c = '@';
                                        i2 = 2;
                                    } else {
                                        r();
                                        b('}');
                                        return;
                                    }
                                }
                                str = str4;
                                str2 = str5;
                                str3 = str6;
                                z2 = z4;
                                if (aVarA.d()) {
                                    r();
                                    b(';');
                                }
                                if (aVarA.d()) {
                                    c3775o3A = aVarA.a();
                                } else {
                                    c3775o3A = null;
                                }
                                if (c3775o3A != null) {
                                    aVar.n.add(c3775o3A);
                                    z4 = z2;
                                    str6 = str3;
                                    str4 = str;
                                    str5 = str2;
                                    c = '@';
                                    i2 = 2;
                                } else {
                                    r();
                                    b('}');
                                    return;
                                }
                            }
                        }
                        List listEmptyList2 = null;
                        while (true) {
                            r();
                            int i6 = this.c;
                            if (a('@')) {
                                O2 o2A6 = a(2, false);
                                if (o2A6 != null) {
                                    if (!j && o2A6.b) {
                                        throw new AssertionError();
                                    }
                                    N2.a aVar6 = o2A6.a;
                                    if (aVar6.a.equals(PsiKeyword.INTERFACE)) {
                                        this.c = i6;
                                        g3A3 = null;
                                    } else {
                                        g3A3 = G3.a(aVar6, G3.a.a, this.i.b);
                                    }
                                } else {
                                    throw b("Class name expected");
                                }
                            } else {
                                g3A3 = null;
                            }
                            if (g3A3 != null) {
                                if (listEmptyList2 == null) {
                                    listEmptyList2 = new ArrayList(2);
                                }
                                listEmptyList2.add(g3A3);
                            } else {
                                if (listEmptyList2 == null) {
                                    listEmptyList2 = Collections.emptyList();
                                }
                                aVar.b(listEmptyList2);
                                O2 o2A7 = a(2, false);
                                if (o2A7 != null) {
                                    if (!j && o2A7.b) {
                                        throw new AssertionError();
                                    }
                                    aVar.l = G3.a(o2A7.a, G3.a.a, this.i.b);
                                    aVar.e();
                                    break;
                                }
                                throw b("Class name expected");
                            }
                        }
                        r();
                        if (!g()) {
                            return;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean e(int i) {
        return (i == 44 || i == 59 || i == 41 || Character.isWhitespace(i)) ? false : true;
    }

    private String e() throws F3 {
        final char cD = d();
        String strA = a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda27
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return P2.a(cD, i);
            }
        });
        if (e(cD)) {
            c(cD);
        }
        return strA == null ? "" : strA;
    }

    private Path a(final boolean z) throws F3 {
        TextPosition textPositionH = h();
        r();
        if (this.g != null) {
            final char cD = d();
            final boolean zE = e(cD);
            String strA = a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda18
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    return P2.a(zE, cD, z, i);
                }
            });
            if (strA != null) {
                if (zE) {
                    if (!g()) {
                        a(cD);
                    } else {
                        throw b("No closing " + cD + " quote", textPositionH);
                    }
                }
                return this.g.resolve(c(strA));
            }
            throw b("File name expected", textPositionH);
        }
        throw b("Options with file names are not supported", textPositionH);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(boolean z, char c, boolean z2, int i) {
        return !(z && i == c) && !(!z && i == File.pathSeparatorChar && z2) && ((z || !Character.isWhitespace(i)) && (z || i != 40));
    }

    private boolean a(int i) {
        return i == this.b.length();
    }

    private boolean a(char c) {
        if (!d(c)) {
            return false;
        }
        this.c++;
        return true;
    }

    private boolean a(String str) {
        if (this.b.length() - this.c < str.length()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != this.b.charAt(this.c + i)) {
                return false;
            }
        }
        this.c = str.length() + this.c;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(char c, int i) {
        return (Character.isWhitespace(i) || i == c) ? false : true;
    }

    private String a() {
        return a(new IntPredicate() { // from class: com.android.tools.r8.shaking.P2$$ExternalSyntheticLambda11
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return this.f$0.b(i);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:53:0x0129 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:60:0x0138  */
    /* JADX WARN: Code duplicated, block: B:62:0x013b  */
    /* JADX WARN: Code duplicated, block: B:63:0x0147  */
    /* JADX WARN: Code duplicated, block: B:65:0x0159  */
    /* JADX WARN: Code duplicated, block: B:67:0x015d  */
    /* JADX WARN: Code duplicated, block: B:95:0x01c9  */
    private O2 a(int i, boolean z) {
        int iCharCount;
        C1507Xl c1507XlG = AbstractC1639am.g();
        r();
        char cD = d();
        boolean zE = e(cD);
        boolean zA = z ? a('!') : false;
        int i2 = this.c;
        int iCharCount2 = i2;
        StringBuilder sb = null;
        StringBuilder sb2 = null;
        int i3 = 0;
        while (!a(iCharCount2)) {
            int iCodePointAt = this.b.codePointAt(iCharCount2);
            boolean z2 = j;
            if (!z2 && sb != null && sb2 != null) {
                throw new AssertionError();
            }
            if (sb2 != null) {
                if (iCodePointAt == 62) {
                    try {
                        int unsignedInt = Integer.parseUnsignedInt(sb2.toString());
                        if (unsignedInt > 0) {
                            c1507XlG.a(new O3.a(unsignedInt));
                            try {
                                iCharCount2 += Character.charCount(iCodePointAt);
                                sb2 = null;
                            } catch (NumberFormatException unused) {
                                sb2 = null;
                            }
                        } else {
                            this.i.d.a(new StringDiagnostic("Wildcard <" + unsignedInt + "> is invalid.", this.h, h()));
                            throw null;
                        }
                    } catch (NumberFormatException unused2) {
                    }
                    this.i.d.a(new StringDiagnostic("Wildcard <" + sb2.toString() + "> is invalid.", this.h, h()));
                    throw null;
                }
                if ((48 <= iCodePointAt && iCodePointAt <= 57) || (iCodePointAt == 45 && sb2.length() == 0)) {
                    sb2.append((char) iCodePointAt);
                    iCharCount = Character.charCount(iCodePointAt);
                } else {
                    if (i == 2) {
                        this.i.d.a(new StringDiagnostic("Use of generics not allowed for java type.", this.h, h()));
                        throw null;
                    }
                    sb2 = null;
                    if (z2 && (sb != null || sb2 != null)) {
                        throw new AssertionError();
                    }
                    if (iCodePointAt == 42) {
                        if (i == 2) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append((char) iCodePointAt);
                            sb = sb3;
                            i3 = 1;
                        } else {
                            c1507XlG.a(new O3.b(String.valueOf((char) iCodePointAt)));
                        }
                        iCharCount = Character.charCount(iCodePointAt);
                    } else if (iCodePointAt == 63 && iCodePointAt != 37) {
                        if (i != 1 ? b(iCodePointAt) || iCodePointAt == 62 : AbstractC3916j1.a(iCodePointAt) || iCodePointAt == 46 || iCodePointAt == 42 || iCodePointAt == 63) {
                            iCharCount = Character.charCount(iCodePointAt);
                        } else {
                            if (i == 1 || iCodePointAt != 60) {
                                if (!zE || cD == iCodePointAt) {
                                    break;
                                    break;
                                }
                                this.i.d.a(new StringDiagnostic("Invalid character '" + ((char) iCodePointAt) + "', expected end-quote.", this.h, h()));
                                throw null;
                            }
                            sb2 = new StringBuilder();
                            iCharCount = Character.charCount(iCodePointAt);
                        }
                    } else {
                        c1507XlG.a(new O3.b(String.valueOf((char) iCodePointAt)));
                        iCharCount = Character.charCount(iCodePointAt);
                    }
                }
                iCharCount2 += iCharCount;
            } else if (sb == null) {
                if (z2) {
                }
                if (iCodePointAt == 42) {
                    if (i == 2) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append((char) iCodePointAt);
                        sb = sb4;
                        i3 = 1;
                    } else {
                        c1507XlG.a(new O3.b(String.valueOf((char) iCodePointAt)));
                    }
                    iCharCount = Character.charCount(iCodePointAt);
                } else if (iCodePointAt == 63) {
                    c1507XlG.a(new O3.b(String.valueOf((char) iCodePointAt)));
                    iCharCount = Character.charCount(iCodePointAt);
                } else {
                    c1507XlG.a(new O3.b(String.valueOf((char) iCodePointAt)));
                    iCharCount = Character.charCount(iCodePointAt);
                }
                iCharCount2 += iCharCount;
            } else {
                if (iCodePointAt == 42) {
                    if (i3 >= 3) {
                        c1507XlG.a(new O3.b(sb.toString()));
                        sb = new StringBuilder();
                        i3 = 0;
                    }
                    sb.append((char) iCodePointAt);
                    i3++;
                    iCharCount = Character.charCount(iCodePointAt);
                } else {
                    c1507XlG.a(new O3.b(sb.toString()));
                    sb = null;
                    i3 = 0;
                    if (z2) {
                    }
                    if (iCodePointAt == 42) {
                        if (i == 2) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append((char) iCodePointAt);
                            sb = sb5;
                            i3 = 1;
                        } else {
                            c1507XlG.a(new O3.b(String.valueOf((char) iCodePointAt)));
                        }
                        iCharCount = Character.charCount(iCodePointAt);
                    } else if (iCodePointAt == 63) {
                        c1507XlG.a(new O3.b(String.valueOf((char) iCodePointAt)));
                        iCharCount = Character.charCount(iCodePointAt);
                    } else {
                        c1507XlG.a(new O3.b(String.valueOf((char) iCodePointAt)));
                        iCharCount = Character.charCount(iCodePointAt);
                    }
                }
                iCharCount2 += iCharCount;
            }
        }
        this.c = zE ? iCharCount2 + 1 : iCharCount2;
        if (sb != null) {
            c1507XlG.a(new O3.b(sb.toString()));
        }
        if (i == 2 && sb2 != null) {
            this.i.d.a(new StringDiagnostic("Missing closing angular bracket", this.h, h()));
            throw null;
        }
        if (i2 == iCharCount2) {
            return null;
        }
        return new O2(this.b.substring(i2, iCharCount2), c1507XlG.a(), zA);
    }

    private String a(IntPredicate intPredicate) {
        int i = this.c;
        int iCharCount = i;
        while (!a(iCharCount)) {
            int iCodePointAt = this.b.codePointAt(iCharCount);
            if (!intPredicate.test(iCodePointAt)) {
                break;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        if (i == iCharCount) {
            return null;
        }
        this.c = iCharCount;
        return this.b.substring(i, iCharCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Integer num) {
        return (num.intValue() == 44 || Character.isWhitespace(num.intValue())) ? false : true;
    }

    private void a(String str, TextPosition textPosition) {
        this.i.d.info(new StringDiagnostic("Ignoring option: -" + str, this.h, a(textPosition)));
    }

    private void a(String str, String str2, TextPosition textPosition) {
        this.i.d.warning(new StringDiagnostic("The " + str + " \"" + str2 + "\" is used in a field rule. The characters in this " + str + " are legal for the JVM, but unlikely to originate from a source language. Maybe this is not the rule you are looking for.", this.h, a(textPosition)));
    }

    private Position a(TextPosition textPosition) {
        return textPosition.getOffset() == ((long) this.c) ? textPosition : new TextRange(textPosition, h());
    }

    private String a(String str, Position position, TextPosition textPosition) {
        if (position instanceof TextPosition) {
            TextPosition textPosition2 = (TextPosition) position;
            this.i.getClass();
            long offset = textPosition.getOffset() - textPosition2.getOffset();
            if (textPosition2.getOffset() >= 0 && textPosition.getOffset() >= 0 && textPosition2.getOffset() < str.length() && textPosition.getOffset() <= str.length() && offset > 0) {
                return str.substring((int) textPosition2.getOffset(), (int) textPosition.getOffset());
            }
        }
        return null;
    }
}
