package org.jetbrains.kotlin.backend.jvm.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: JvmIrDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/extensions/JvmIrDeclarationOrigin;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "originKind", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "originalSourceElement", "", "getOriginalSourceElement", "()Ljava/lang/Object;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmIrDeclarationOrigin extends JvmDeclarationOrigin {
    private final IrDeclaration declaration;

    public final IrDeclaration getDeclaration() {
        return this.declaration;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmIrDeclarationOrigin(JvmDeclarationOriginKind originKind, PsiElement psiElement, IrDeclaration irDeclaration) {
        super(originKind, psiElement, irDeclaration != null ? IrBasedDescriptorsKt.toIrBasedDescriptor(irDeclaration) : null, null);
        Intrinsics.checkNotNullParameter(originKind, "originKind");
        this.declaration = irDeclaration;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
    public Object getOriginalSourceElement() {
        IrDeclaration irDeclaration = this.declaration;
        IrAttributeContainer irAttributeContainer = irDeclaration instanceof IrAttributeContainer ? (IrAttributeContainer) irDeclaration : null;
        if (irAttributeContainer != null) {
            return irAttributeContainer.getAttributeOwnerId();
        }
        return null;
    }
}
