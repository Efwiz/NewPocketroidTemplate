package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtSuperTypeListEntry extends KtElementImplStub<KotlinPlaceHolderStub<? extends KtSuperTypeListEntry>> {
    private static final KtSuperTypeListEntry[] EMPTY_ARRAY = new KtSuperTypeListEntry[0];
    public static ArrayFactory<KtSuperTypeListEntry> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.psi.KtSuperTypeListEntry$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return KtSuperTypeListEntry.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "nodeType";
        } else if (i != 3) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtSuperTypeListEntry";
        if (i != 3) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static /* synthetic */ KtSuperTypeListEntry[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new KtSuperTypeListEntry[i];
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSuperTypeListEntry(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSuperTypeListEntry(KotlinPlaceHolderStub<? extends KtSuperTypeListEntry> kotlinPlaceHolderStub, IStubElementType iStubElementType) {
        super(kotlinPlaceHolderStub, iStubElementType);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(3);
        }
        return ktVisitor.visitSuperTypeListEntry(this, d);
    }

    public KtTypeReference getTypeReference() {
        return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
    }

    public KtUserType getTypeAsUserType() {
        KtTypeReference typeReference = getTypeReference();
        if (typeReference == null) {
            return null;
        }
        KtTypeElement typeElement = typeReference.getTypeElement();
        if (typeElement instanceof KtUserType) {
            return (KtUserType) typeElement;
        }
        return null;
    }
}
