package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtFunctionTypeReceiver extends KtElementImplStub<KotlinPlaceHolderStub<KtFunctionTypeReceiver>> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtFunctionTypeReceiver";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtFunctionTypeReceiver";
        } else {
            objArr[1] = "getTypeReference";
        }
        if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFunctionTypeReceiver(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFunctionTypeReceiver(KotlinPlaceHolderStub<KtFunctionTypeReceiver> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.FUNCTION_TYPE_RECEIVER);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    public KtTypeReference getTypeReference() {
        KtTypeReference ktTypeReference = (KtTypeReference) getRequiredStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
        if (ktTypeReference == null) {
            $$$reportNull$$$0(2);
        }
        return ktTypeReference;
    }
}
