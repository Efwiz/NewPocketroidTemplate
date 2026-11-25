package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclarationKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ConflictingJvmDeclarationsData;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: duplicateJvmSignatureUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001\u001a\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0082\u0004¨\u0006\t"}, d2 = {"getJvmSignatureDiagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "otherDiagnostics", "higherThan", "", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "other", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DuplicateJvmSignatureUtilKt {

    /* JADX INFO: compiled from: duplicateJvmSignatureUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JvmDeclarationOriginKind.values().length];
            try {
                iArr[JvmDeclarationOriginKind.INTERFACE_DEFAULT_IMPL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JvmDeclarationOriginKind.MULTIFILE_CLASS_PART.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private static final Diagnostics getJvmSignatureDiagnostics$getDiagnosticsForClass(KtClassOrObject ktClassOrObject) {
        return CliExtraDiagnosticsProvider.INSTANCE.forClassOrObject(KtLightClassForSourceDeclarationKt.getOutermostClassOrObject(ktClassOrObject));
    }

    private static final Diagnostics getJvmSignatureDiagnostics$doGetDiagnostics(PsiElement psiElement) {
        PsiElement myParent;
        PsiFile containingFile = psiElement.getContainingFile();
        final KtFile ktFile = containingFile instanceof KtFile ? (KtFile) containingFile : null;
        if (ktFile != null && ((Boolean) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.DuplicateJvmSignatureUtilKt$getJvmSignatureDiagnostics$doGetDiagnostics$$inlined$safeIsScript$1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return (T) Boolean.valueOf(ktFile.isScript());
            }
        })).booleanValue()) {
            return null;
        }
        PsiElement myParent2 = psiElement.getMyParent();
        if (psiElement instanceof KtPropertyAccessor) {
            myParent2 = myParent2 != null ? myParent2.getMyParent() : null;
        }
        if ((psiElement instanceof KtParameter) && ((KtParameter) psiElement).hasValOrVar()) {
            PsiElement myParent3 = (myParent2 == null || (myParent = myParent2.getMyParent()) == null) ? null : myParent.getMyParent();
            KtClass ktClass = myParent3 instanceof KtClass ? (KtClass) myParent3 : null;
            if (ktClass != null) {
                return getJvmSignatureDiagnostics$getDiagnosticsForClass(ktClass);
            }
        }
        if (psiElement instanceof KtClassOrObject) {
            return getJvmSignatureDiagnostics$getDiagnosticsForClass((KtClassOrObject) psiElement);
        }
        if (myParent2 instanceof KtFile) {
            CliExtraDiagnosticsProvider cliExtraDiagnosticsProvider = CliExtraDiagnosticsProvider.INSTANCE;
            Intrinsics.checkNotNull(myParent2);
            return cliExtraDiagnosticsProvider.forFacade((KtFile) myParent2);
        }
        if (myParent2 instanceof KtClassBody) {
            PsiElement myParent4 = myParent2.getMyParent();
            if (myParent4 instanceof KtClassOrObject) {
                return getJvmSignatureDiagnostics$getDiagnosticsForClass((KtClassOrObject) myParent4);
            }
        }
        return null;
    }

    public static final Diagnostics getJvmSignatureDiagnostics(PsiElement element, Diagnostics otherDiagnostics) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(otherDiagnostics, "otherDiagnostics");
        Diagnostics jvmSignatureDiagnostics$doGetDiagnostics = getJvmSignatureDiagnostics$doGetDiagnostics(element);
        if (jvmSignatureDiagnostics$doGetDiagnostics == null) {
            return null;
        }
        return new FilteredJvmDiagnostics(jvmSignatureDiagnostics$doGetDiagnostics, otherDiagnostics);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean higherThan(ConflictingJvmDeclarationsData conflictingJvmDeclarationsData, ConflictingJvmDeclarationsData conflictingJvmDeclarationsData2) {
        JvmDeclarationOrigin classOrigin = conflictingJvmDeclarationsData2.getClassOrigin();
        JvmDeclarationOriginKind originKind = classOrigin != null ? classOrigin.getOriginKind() : null;
        int i = originKind == null ? -1 : WhenMappings.$EnumSwitchMapping$0[originKind.ordinal()];
        if (i == 1) {
            JvmDeclarationOrigin classOrigin2 = conflictingJvmDeclarationsData.getClassOrigin();
            if ((classOrigin2 != null ? classOrigin2.getOriginKind() : null) == JvmDeclarationOriginKind.INTERFACE_DEFAULT_IMPL) {
                return false;
            }
        } else {
            if (i != 2) {
                return false;
            }
            JvmDeclarationOrigin classOrigin3 = conflictingJvmDeclarationsData.getClassOrigin();
            if ((classOrigin3 != null ? classOrigin3.getOriginKind() : null) != JvmDeclarationOriginKind.MULTIFILE_CLASS) {
                return false;
            }
        }
        return true;
    }
}
