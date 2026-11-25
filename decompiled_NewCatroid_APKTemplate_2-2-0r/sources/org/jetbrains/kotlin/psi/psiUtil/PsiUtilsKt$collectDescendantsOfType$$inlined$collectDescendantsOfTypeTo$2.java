package org.jetbrains.kotlin.psi.psiUtil;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementVisitor;

/* JADX INFO: compiled from: psiUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006¸\u0006\u0007"}, d2 = {"org/jetbrains/kotlin/psi/psiUtil/PsiUtilsKt$forEachDescendantOfType$2", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiRecursiveElementVisitor;", "visitElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psi", "org/jetbrains/kotlin/psi/psiUtil/PsiUtilsKt$collectDescendantsOfTypeTo$$inlined$forEachDescendantOfType$1"}, k = 1, mv = {1, 9, 0}, xi = 176)
public final class PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$2 extends PsiRecursiveElementVisitor {
    final /* synthetic */ Function1 $action;
    final /* synthetic */ Function1 $canGoInside;

    public PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$2(Function1 function1, Function1 function2) {
        this.$canGoInside = function1;
        this.$action = function2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
    public void visitElement(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (((Boolean) this.$canGoInside.invoke(element)).booleanValue()) {
            super.visitElement(element);
        }
        Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
        if (element instanceof PsiElement) {
            this.$action.invoke(element);
        }
    }
}
