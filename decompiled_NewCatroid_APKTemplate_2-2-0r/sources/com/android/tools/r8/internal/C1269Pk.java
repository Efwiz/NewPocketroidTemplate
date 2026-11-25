package com.android.tools.r8.internal;

import com.android.tools.r8.TextInputStream;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.profile.art.ArtProfileBuilder;
import com.android.tools.r8.profile.art.ArtProfileClassRuleBuilder;
import com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder;
import com.android.tools.r8.profile.art.ArtProfileMethodRuleInfoBuilder;
import com.android.tools.r8.profile.art.ArtProfileRulePredicate;
import com.android.tools.r8.profile.art.HumanReadableArtProfileParserBuilder;
import com.android.tools.r8.profile.art.diagnostic.HumanReadableArtProfileParserErrorDiagnostic;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.utils.C3889e2;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1269Pk {
    static final /* synthetic */ boolean e = true;
    private final Consumer a;
    private final ArtProfileBuilder b;
    private final ArtProfileRulePredicate c;
    private final com.android.tools.r8.utils.O2 d;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.Pk$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a implements HumanReadableArtProfileParserBuilder {
        private Consumer a;
        private ArtProfileBuilder b;
        private ArtProfileRulePredicate c = new R1();
        private com.android.tools.r8.utils.O2 d;

        public final a a(Consumer consumer) {
            this.a = consumer;
            return this;
        }

        @Override // com.android.tools.r8.profile.art.HumanReadableArtProfileParserBuilder
        public final HumanReadableArtProfileParserBuilder setRulePredicate(ArtProfileRulePredicate artProfileRulePredicate) {
            this.c = artProfileRulePredicate;
            return this;
        }

        public a a(com.android.tools.r8.utils.O2 o2) {
            this.d = o2;
            return this;
        }

        public a a(ArtProfileBuilder artProfileBuilder) {
            this.b = artProfileBuilder;
            return this;
        }

        public C1269Pk a() {
            final com.android.tools.r8.utils.O2 o2;
            if (this.a == null && (o2 = this.d) != null) {
                Objects.requireNonNull(o2);
                this.a = new Consumer() { // from class: com.android.tools.r8.internal.Pk$a$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        o2.error((HumanReadableArtProfileParserErrorDiagnostic) obj);
                    }
                };
            }
            return new C1269Pk(this.a, this.b, this.c, this.d);
        }
    }

    C1269Pk(Consumer consumer, ArtProfileBuilder artProfileBuilder, ArtProfileRulePredicate artProfileRulePredicate, com.android.tools.r8.utils.O2 o2) {
        this.a = consumer;
        this.b = artProfileBuilder;
        this.c = artProfileRulePredicate;
        this.d = o2;
    }

    public static a a() {
        return new a();
    }

    public boolean b(String str) {
        boolean zA;
        try {
            final C3.a aVarA = C3.a();
            Objects.requireNonNull(aVarA);
            InterfaceC3880d interfaceC3880d = new InterfaceC3880d() { // from class: com.android.tools.r8.internal.Pk$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.utils.InterfaceC3880d
                public final void a() {
                    aVarA.d();
                }
            };
            if (!str.isEmpty() && str.charAt(0) == 'H') {
                interfaceC3880d.a();
                str = str.substring(1);
            }
            InterfaceC3880d interfaceC3880d2 = new InterfaceC3880d() { // from class: com.android.tools.r8.internal.Pk$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.utils.InterfaceC3880d
                public final void a() {
                    aVarA.f();
                }
            };
            if (!str.isEmpty() && str.charAt(0) == 'S') {
                interfaceC3880d2.a();
                str = str.substring(1);
            }
            InterfaceC3880d interfaceC3880d3 = new InterfaceC3880d() { // from class: com.android.tools.r8.internal.Pk$$ExternalSyntheticLambda5
                @Override // com.android.tools.r8.utils.InterfaceC3880d
                public final void a() {
                    aVarA.e();
                }
            };
            if (!str.isEmpty() && str.charAt(0) == 'P') {
                interfaceC3880d3.a();
                str = str.substring(1);
            }
            C3 c3A = aVarA.a();
            int iIndexOf = str.indexOf("->");
            if (iIndexOf >= 0) {
                zA = a(str, c3A, iIndexOf);
            } else {
                if (!c3A.d()) {
                    return false;
                }
                zA = a(str);
            }
            return zA;
        } catch (Throwable unused) {
            return false;
        }
    }

    public void a(TextInputStream textInputStream, Origin origin) {
        Consumer consumer;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(textInputStream.getInputStream(), textInputStream.getCharset());
            try {
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                int i = 1;
                while (bufferedReader.ready()) {
                    try {
                        String line = bufferedReader.readLine();
                        int iIndexOf = line.indexOf(35);
                        String strM = iIndexOf >= 0 ? AT$$ExternalSyntheticBackport0.m(line.substring(0, iIndexOf)) : line;
                        for (int i2 = 0; i2 < strM.length(); i2++) {
                            if (!Character.isWhitespace(strM.charAt(i2))) {
                                if (!b(strM) && (consumer = this.a) != null) {
                                    consumer.accept(new HumanReadableArtProfileParserErrorDiagnostic(line, i, origin));
                                    break;
                                }
                                break;
                            }
                        }
                        i++;
                    } catch (Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                bufferedReader.close();
                inputStreamReader.close();
                com.android.tools.r8.utils.O2 o2 = this.d;
                if (o2 != null) {
                    o2.a();
                }
            } catch (Throwable th3) {
                try {
                    inputStreamReader.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
                throw th3;
            }
        } catch (IOException e2) {
            throw new UncheckedIOException(e2);
        }
    }

    private boolean a(String str) {
        int i;
        int i2 = C3970u0.c;
        int i3 = -1;
        while (true) {
            i = i3 + 1;
            if (i >= str.length() || str.charAt(i) != '[') {
                break;
            }
            i3 = i;
        }
        if (i3 >= 0) {
            str = str.substring(i);
        }
        if (!C3970u0.B(str)) {
            return false;
        }
        TypeReference typeReferenceTypeFromDescriptor = Reference.typeFromDescriptor(str);
        boolean z = e;
        if (!z && typeReferenceTypeFromDescriptor == null) {
            throw new AssertionError();
        }
        if (!z && !typeReferenceTypeFromDescriptor.isClass()) {
            throw new AssertionError();
        }
        final ClassReference classReferenceAsClass = typeReferenceTypeFromDescriptor.asClass();
        if (!this.c.testClassRule(classReferenceAsClass, C3035t3.a())) {
            return true;
        }
        this.b.addClassRule(new Consumer() { // from class: com.android.tools.r8.internal.Pk$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ArtProfileClassRuleBuilder) obj).setClassReference(classReferenceAsClass);
            }
        });
        return true;
    }

    private boolean a(String str, final C3 c3, int i) {
        int iIndexOf = str.indexOf(43, i + 2);
        if (iIndexOf > 0) {
            str = str.substring(0, iIndexOf);
        }
        final MethodReference methodReferenceA = C3889e2.a(i, str);
        if (methodReferenceA == null || !C3970u0.B(methodReferenceA.getHolderClass().getDescriptor())) {
            return false;
        }
        Iterator<TypeReference> it = methodReferenceA.getFormalTypes().iterator();
        while (it.hasNext()) {
            if (!C3970u0.C(it.next().getDescriptor())) {
                return false;
            }
        }
        if (methodReferenceA.getReturnType() != null && !C3970u0.C(methodReferenceA.getReturnType().getDescriptor())) {
            return false;
        }
        if (!this.c.testMethodRule(methodReferenceA, c3)) {
            return true;
        }
        this.b.addMethodRule(new Consumer() { // from class: com.android.tools.r8.internal.Pk$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1269Pk.a(methodReferenceA, c3, (ArtProfileMethodRuleBuilder) obj);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(MethodReference methodReference, final C3 c3, ArtProfileMethodRuleBuilder artProfileMethodRuleBuilder) {
        artProfileMethodRuleBuilder.setMethodReference(methodReference).setMethodRuleInfo(new Consumer() { // from class: com.android.tools.r8.internal.Pk$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1269Pk.a(c3, (ArtProfileMethodRuleInfoBuilder) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3 c3, ArtProfileMethodRuleInfoBuilder artProfileMethodRuleInfoBuilder) {
        artProfileMethodRuleInfoBuilder.setIsHot(c3.isHot()).setIsStartup(c3.isStartup()).setIsPostStartup(c3.isPostStartup());
    }
}
