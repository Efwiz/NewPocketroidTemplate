package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: LightClassDataProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003H\u0016J\u0010\u0010\b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003H\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003H\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/ClassFilterForClassOrObject;", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$GenerateClassFilter;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "shouldAnnotateClass", "", "processingClassOrObject", "shouldGenerateClass", "shouldGenerateClassMembers", "shouldGenerateCodeFragment", "script", "Lorg/jetbrains/kotlin/psi/KtCodeFragment;", "shouldGeneratePackagePart", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "shouldGenerateScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ClassFilterForClassOrObject extends GenerationState.GenerateClassFilter {
    private final KtClassOrObject classOrObject;

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldGenerateCodeFragment(KtCodeFragment script) {
        Intrinsics.checkNotNullParameter(script, "script");
        return false;
    }

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldGeneratePackagePart(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        return true;
    }

    public ClassFilterForClassOrObject(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        this.classOrObject = classOrObject;
    }

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldAnnotateClass(KtClassOrObject processingClassOrObject) {
        Intrinsics.checkNotNullParameter(processingClassOrObject, "processingClassOrObject");
        return shouldGenerateClass(processingClassOrObject);
    }

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldGenerateClassMembers(final KtClassOrObject processingClassOrObject) {
        Intrinsics.checkNotNullParameter(processingClassOrObject, "processingClassOrObject");
        KtClassOrObject ktClassOrObject = this.classOrObject;
        if (ktClassOrObject == processingClassOrObject) {
            return true;
        }
        KtClassOrObject ktClassOrObject2 = processingClassOrObject;
        if (PsiUtilsKt.isAncestor(ktClassOrObject, ktClassOrObject2, true)) {
            return true;
        }
        final KtClassOrObject ktClassOrObject3 = this.classOrObject;
        if (!((Boolean) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.ClassFilterForClassOrObject$shouldGenerateClassMembers$$inlined$safeIsLocal$1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return (T) Boolean.valueOf(ktClassOrObject3.isLocal());
            }
        })).booleanValue() || !((Boolean) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.ClassFilterForClassOrObject$shouldGenerateClassMembers$$inlined$safeIsLocal$2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return (T) Boolean.valueOf(processingClassOrObject.isLocal());
            }
        })).booleanValue()) {
            return false;
        }
        PsiElement psiElementFindCommonParent = PsiTreeUtil.findCommonParent(this.classOrObject, ktClassOrObject2);
        return (psiElementFindCommonParent == null || (psiElementFindCommonParent instanceof PsiFile)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldGenerateClass(KtClassOrObject processingClassOrObject) {
        Intrinsics.checkNotNullParameter(processingClassOrObject, "processingClassOrObject");
        return shouldGenerateClassMembers(processingClassOrObject) || PsiUtilsKt.isAncestor(processingClassOrObject, this.classOrObject, true);
    }

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldGenerateScript(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        return PsiTreeUtil.isAncestor(script, this.classOrObject, false);
    }
}
