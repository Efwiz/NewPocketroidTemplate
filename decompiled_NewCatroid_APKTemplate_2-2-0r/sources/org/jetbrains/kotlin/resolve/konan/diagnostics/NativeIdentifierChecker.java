package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation1;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.resolve.IdentifierChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;

/* JADX INFO: compiled from: NativeIdentifierChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0017\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J(\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001d2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeIdentifierChecker;", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "invalidChars", "", "", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkDeclaration", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "checkIdentifier", "simpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "checkNamed", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "reportIfNeeded", "name", "", "reportOn", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeIdentifierChecker implements IdentifierChecker, DeclarationChecker {
    private final Set<Character> invalidChars;
    private final LanguageVersionSettings languageVersionSettings;

    public NativeIdentifierChecker(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
        this.invalidChars = SetsKt.setOf((Object[]) new Character[]{'.', ';', ',', '(', ')', '[', ']', '{', '}', '/', '<', '>', ':', '\\', '$', Character.valueOf(Typography.amp), '~', '*', Character.valueOf(MangleConstant.Q_MARK), '#', '|', (char) 167, Character.valueOf(MangleConstant.PLATFORM_FUNCTION_MARKER), '@'});
    }

    @Override // org.jetbrains.kotlin.resolve.IdentifierChecker
    public void checkIdentifier(final KtSimpleNameExpression simpleNameExpression, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(simpleNameExpression, "simpleNameExpression");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        reportIfNeeded(simpleNameExpression.getReferencedName(), new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.NativeIdentifierChecker.checkIdentifier.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiElement invoke() {
                return simpleNameExpression.getIdentifier();
            }
        }, diagnosticHolder);
    }

    @Override // org.jetbrains.kotlin.resolve.IdentifierChecker
    public void checkDeclaration(KtDeclaration declaration, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        if (declaration instanceof KtDestructuringDeclaration) {
            List<KtDestructuringDeclarationEntry> entries = ((KtDestructuringDeclaration) declaration).getEntries();
            Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
            for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : entries) {
                Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
                checkNamed(ktDestructuringDeclarationEntry, diagnosticHolder);
            }
        }
        if (declaration instanceof KtCallableDeclaration) {
            List<KtParameter> valueParameters = ((KtCallableDeclaration) declaration).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            for (KtParameter ktParameter : valueParameters) {
                Intrinsics.checkNotNull(ktParameter);
                checkNamed(ktParameter, diagnosticHolder);
            }
        }
        if (declaration instanceof KtTypeParameterListOwner) {
            List<KtTypeParameter> typeParameters = ((KtTypeParameterListOwner) declaration).getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            for (KtTypeParameter ktTypeParameter : typeParameters) {
                Intrinsics.checkNotNull(ktTypeParameter);
                checkNamed(ktTypeParameter, diagnosticHolder);
            }
        }
        if (declaration instanceof KtNamedDeclaration) {
            checkNamed((KtNamedDeclaration) declaration, diagnosticHolder);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (declaration instanceof KtTypeAlias) {
            checkNamed((KtNamedDeclaration) declaration, context.getTrace());
        }
    }

    private final void checkNamed(final KtNamedDeclaration declaration, DiagnosticSink diagnosticHolder) {
        String name2 = declaration.getName();
        if (name2 == null) {
            return;
        }
        reportIfNeeded(name2, new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.NativeIdentifierChecker.checkNamed.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiElement invoke() {
                PsiElement nameIdentifier = declaration.mo7392getNameIdentifier();
                return nameIdentifier == null ? declaration : nameIdentifier;
            }
        }, diagnosticHolder);
    }

    private final void reportIfNeeded(String name2, Function0<? extends PsiElement> reportOn, DiagnosticSink diagnosticHolder) {
        String strUnquoteIdentifier = KtPsiUtil.unquoteIdentifier(name2);
        Intrinsics.checkNotNullExpressionValue(strUnquoteIdentifier, "unquoteIdentifier(...)");
        String str = strUnquoteIdentifier;
        if (str.length() == 0) {
            DiagnosticFactoryForDeprecation1<PsiElement, String> diagnosticFactoryForDeprecation1 = ErrorsNative.INVALID_CHARACTERS_NATIVE;
            LanguageVersionSettings languageVersionSettings = this.languageVersionSettings;
            PsiElement psiElementInvoke = reportOn.invoke();
            if (psiElementInvoke == null) {
                return;
            }
            diagnosticHolder.report(diagnosticFactoryForDeprecation1.on(languageVersionSettings, psiElementInvoke, "should not be empty"));
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            if (this.invalidChars.contains(Character.valueOf(str.charAt(i)))) {
                DiagnosticFactoryForDeprecation1<PsiElement, String> diagnosticFactoryForDeprecation2 = ErrorsNative.INVALID_CHARACTERS_NATIVE;
                LanguageVersionSettings languageVersionSettings2 = this.languageVersionSettings;
                PsiElement psiElementInvoke2 = reportOn.invoke();
                if (psiElementInvoke2 == null) {
                    return;
                }
                diagnosticHolder.report(diagnosticFactoryForDeprecation2.on(languageVersionSettings2, psiElementInvoke2, "contains illegal characters: " + CollectionsKt.joinToString$default(CollectionsKt.intersect(this.invalidChars, StringsKt.toSet(str)), "", "\"", "\"", 0, null, null, 56, null)));
                return;
            }
        }
    }
}
