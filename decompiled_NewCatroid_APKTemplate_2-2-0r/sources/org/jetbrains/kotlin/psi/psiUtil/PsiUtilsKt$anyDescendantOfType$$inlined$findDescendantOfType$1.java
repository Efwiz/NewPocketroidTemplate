package org.jetbrains.kotlin.psi.psiUtil;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor;

/* JADX INFO: compiled from: psiUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006¸\u0006\u0007"}, d2 = {"org/jetbrains/kotlin/psi/psiUtil/PsiUtilsKt$findDescendantOfType$4", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiRecursiveElementWalkingVisitor;", "visitElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psi", "org/jetbrains/kotlin/psi/psiUtil/PsiUtilsKt$findDescendantOfType$$inlined$findDescendantOfType$1"}, k = 1, mv = {1, 9, 0}, xi = 176)
public final class PsiUtilsKt$anyDescendantOfType$$inlined$findDescendantOfType$1 extends PsiRecursiveElementWalkingVisitor {
    final /* synthetic */ Function1 $predicate;
    final /* synthetic */ Ref.ObjectRef $result;

    public PsiUtilsKt$anyDescendantOfType$$inlined$findDescendantOfType$1(Function1 function1, Ref.ObjectRef objectRef) {
        this.$predicate = function1;
        this.$result = objectRef;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
    public void visitElement(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
        if ((element instanceof PsiElement) && ((Boolean) this.$predicate.invoke(element)).booleanValue()) {
            this.$result.element = element;
            stopWalking();
        } else {
            super.visitElement(element);
        }
    }
}
