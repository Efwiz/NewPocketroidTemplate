package org.jetbrains.kotlin.psi.stubs.elements;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.KtValueArgumentList;

/* JADX INFO: loaded from: classes6.dex */
public class KtValueArgumentListElementType extends KtPlaceHolderStubElementType<KtValueArgumentList> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/psi/stubs/elements/KtValueArgumentListElementType", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtValueArgumentListElementType(String str) {
        super(str, KtValueArgumentList.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public boolean shouldCreateStub(ASTNode aSTNode) {
        ASTNode treeParent = aSTNode.getTreeParent();
        if (treeParent == null || treeParent.getElementType() != KtStubElementTypes.ANNOTATION_ENTRY || ((KtValueArgumentList) aSTNode.getPsi(KtValueArgumentList.class)).getArguments().isEmpty()) {
            return false;
        }
        return super.shouldCreateStub(aSTNode);
    }
}
