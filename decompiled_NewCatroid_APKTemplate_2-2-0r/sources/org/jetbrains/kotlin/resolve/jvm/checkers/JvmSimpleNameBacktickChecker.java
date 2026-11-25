package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.resolve.IdentifierChecker;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: JvmSimpleNameBacktickChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0012\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u000eH\u0002J(\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00162\u000e\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u00182\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmSimpleNameBacktickChecker;", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "()V", "DANGEROUS_CHARS", "", "", "INVALID_CHARS", "getINVALID_CHARS", "()Ljava/util/Set;", "checkDeclaration", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "checkIdentifier", "simpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "checkNamed", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "reportIfNeeded", "name", "", "reportOn", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSimpleNameBacktickChecker implements IdentifierChecker {
    public static final JvmSimpleNameBacktickChecker INSTANCE = new JvmSimpleNameBacktickChecker();
    private static final Set<Character> INVALID_CHARS = SetsKt.setOf((Object[]) new Character[]{'.', ';', '[', ']', '/', '<', '>', ':', '\\'});
    private static final Set<Character> DANGEROUS_CHARS = SetsKt.setOf((Object[]) new Character[]{Character.valueOf(MangleConstant.Q_MARK), '*', '\"', '|', Character.valueOf(MangleConstant.PLATFORM_FUNCTION_MARKER)});

    private JvmSimpleNameBacktickChecker() {
    }

    public final Set<Character> getINVALID_CHARS() {
        return INVALID_CHARS;
    }

    @Override // org.jetbrains.kotlin.resolve.IdentifierChecker
    public void checkIdentifier(final KtSimpleNameExpression simpleNameExpression, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(simpleNameExpression, "simpleNameExpression");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        reportIfNeeded(simpleNameExpression.getReferencedName(), new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JvmSimpleNameBacktickChecker.checkIdentifier.1
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
                JvmSimpleNameBacktickChecker jvmSimpleNameBacktickChecker = INSTANCE;
                Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
                jvmSimpleNameBacktickChecker.checkNamed(ktDestructuringDeclarationEntry, diagnosticHolder);
            }
        }
        if (declaration instanceof KtCallableDeclaration) {
            List<KtParameter> valueParameters = ((KtCallableDeclaration) declaration).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            for (KtParameter ktParameter : valueParameters) {
                JvmSimpleNameBacktickChecker jvmSimpleNameBacktickChecker2 = INSTANCE;
                Intrinsics.checkNotNull(ktParameter);
                jvmSimpleNameBacktickChecker2.checkNamed(ktParameter, diagnosticHolder);
            }
        }
        if (declaration instanceof KtTypeParameterListOwner) {
            List<KtTypeParameter> typeParameters = ((KtTypeParameterListOwner) declaration).getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            for (KtTypeParameter ktTypeParameter : typeParameters) {
                JvmSimpleNameBacktickChecker jvmSimpleNameBacktickChecker3 = INSTANCE;
                Intrinsics.checkNotNull(ktTypeParameter);
                jvmSimpleNameBacktickChecker3.checkNamed(ktTypeParameter, diagnosticHolder);
            }
        }
        if (declaration instanceof KtNamedDeclaration) {
            checkNamed((KtNamedDeclaration) declaration, diagnosticHolder);
        }
    }

    private final void checkNamed(final KtNamedDeclaration declaration, DiagnosticSink diagnosticHolder) {
        String name2 = declaration.getName();
        if (name2 == null) {
            return;
        }
        reportIfNeeded(name2, new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JvmSimpleNameBacktickChecker.checkNamed.1
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
            DiagnosticFactory1<PsiElement, String> diagnosticFactory1 = Errors.INVALID_CHARACTERS;
            PsiElement psiElementInvoke = reportOn.invoke();
            if (psiElementInvoke == null) {
                return;
            }
            diagnosticHolder.report(diagnosticFactory1.on(psiElementInvoke, "should not be empty"));
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            Set<Character> set = INVALID_CHARS;
            if (set.contains(Character.valueOf(cCharAt))) {
                DiagnosticFactory1<PsiElement, String> diagnosticFactory2 = Errors.INVALID_CHARACTERS;
                PsiElement psiElementInvoke2 = reportOn.invoke();
                if (psiElementInvoke2 == null) {
                    return;
                }
                diagnosticHolder.report(diagnosticFactory2.on(psiElementInvoke2, "contains illegal characters: " + CollectionsKt.joinToString$default(CollectionsKt.intersect(set, StringsKt.toSet(str)), "", null, null, 0, null, null, 62, null)));
                return;
            }
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt2 = str.charAt(i2);
            Set<Character> set2 = DANGEROUS_CHARS;
            if (set2.contains(Character.valueOf(cCharAt2))) {
                DiagnosticFactory1<PsiElement, String> diagnosticFactory3 = ErrorsJvm.DANGEROUS_CHARACTERS;
                PsiElement psiElementInvoke3 = reportOn.invoke();
                if (psiElementInvoke3 == null) {
                    return;
                }
                diagnosticHolder.report(diagnosticFactory3.on(psiElementInvoke3, CollectionsKt.joinToString$default(CollectionsKt.intersect(set2, StringsKt.toSet(str)), "", null, null, 0, null, null, 62, null)));
                return;
            }
        }
    }
}
