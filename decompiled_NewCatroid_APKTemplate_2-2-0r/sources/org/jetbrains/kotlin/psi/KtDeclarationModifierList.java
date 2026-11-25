package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.stubs.KotlinModifierListStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtDeclarationModifierList extends KtModifierList {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "node";
        } else {
            objArr[0] = "stub";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtDeclarationModifierList";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDeclarationModifierList(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDeclarationModifierList(KotlinModifierListStub kotlinModifierListStub) {
        super(kotlinModifierListStub, KtStubElementTypes.MODIFIER_LIST);
        if (kotlinModifierListStub == null) {
            $$$reportNull$$$0(1);
        }
    }
}
