package org.jetbrains.kotlin.backend.jvm.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.psi.PsiSourceManager;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: JvmIrDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u0002H\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"descriptorOrigin", "Lorg/jetbrains/kotlin/backend/jvm/extensions/JvmIrDeclarationOrigin;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getDescriptorOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/backend/jvm/extensions/JvmIrDeclarationOrigin;", "findPsiElementForDeclarationOrigin", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmIrDeclarationOriginKt {
    public static final JvmIrDeclarationOrigin getDescriptorOrigin(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        PsiElement psiElementFindPsiElementForDeclarationOrigin = findPsiElementForDeclarationOrigin(irDeclaration);
        if (Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.FILE_CLASS.INSTANCE)) {
            return new JvmIrDeclarationOrigin(JvmDeclarationOriginKind.PACKAGE_PART, psiElementFindPsiElementForDeclarationOrigin, irDeclaration);
        }
        if (((irDeclaration instanceof IrSimpleFunction) && ((IrSimpleFunction) irDeclaration).isSuspend() && JvmIrInlineUtilsKt.isEffectivelyInlineOnly((IrDeclarationWithVisibility) irDeclaration)) || Intrinsics.areEqual(irDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE.INSTANCE) || Intrinsics.areEqual(irDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE.INSTANCE)) {
            return new JvmIrDeclarationOrigin(JvmDeclarationOriginKind.INLINE_VERSION_OF_SUSPEND_FUN, psiElementFindPsiElementForDeclarationOrigin, irDeclaration);
        }
        return new JvmIrDeclarationOrigin(JvmDeclarationOriginKind.OTHER, psiElementFindPsiElementForDeclarationOrigin, irDeclaration);
    }

    private static final PsiElement findPsiElementForDeclarationOrigin(IrDeclaration irDeclaration) {
        if (irDeclaration instanceof IrFunction) {
            IrFunction irFunction = (IrFunction) irDeclaration;
            String strAsString = irFunction.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (StringsKt.endsWith$default(strAsString, JvmAbi.ANNOTATED_PROPERTY_METHOD_NAME_SUFFIX, false, 2, (Object) null)) {
                MetadataSource metadata = irFunction.getMetadata();
                DescriptorMetadataSource.Property property = metadata instanceof DescriptorMetadataSource.Property ? (DescriptorMetadataSource.Property) metadata : null;
                if (property != null) {
                    return JvmIrUtilsKt.getPsiElement(property.getDescriptor());
                }
            }
        }
        PsiElement psiElementFindPsiElement = PsiSourceManager.INSTANCE.findPsiElement(irDeclaration);
        return psiElementFindPsiElement instanceof KtPropertyDelegate ? ((KtPropertyDelegate) psiElementFindPsiElement).getParent() : psiElementFindPsiElement;
    }
}
