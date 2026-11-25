package org.jetbrains.kotlin.psi.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.stubs.KotlinNameReferenceExpressionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KotlinNameReferenceExpressionStubImpl extends KotlinStubBaseImpl<KtNameReferenceExpression> implements KotlinNameReferenceExpressionStub {
    private final boolean myClassRef;
    private final StringRef referencedName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i != 2) {
            objArr[0] = "referencedName";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/stubs/impl/KotlinNameReferenceExpressionStubImpl";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/psi/stubs/impl/KotlinNameReferenceExpressionStubImpl";
        } else {
            objArr[1] = "getReferencedName";
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
    public KotlinNameReferenceExpressionStubImpl(StubElement stubElement, StringRef stringRef) {
        super(stubElement, KtStubElementTypes.REFERENCE_EXPRESSION);
        if (stringRef == null) {
            $$$reportNull$$$0(0);
        }
        this.referencedName = stringRef;
        this.myClassRef = false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinNameReferenceExpressionStubImpl(StubElement<?> stubElement, StringRef stringRef, boolean z) {
        super(stubElement, KtStubElementTypes.REFERENCE_EXPRESSION);
        if (stringRef == null) {
            $$$reportNull$$$0(1);
        }
        this.referencedName = stringRef;
        this.myClassRef = z;
    }

    public boolean isClassRef() {
        return this.myClassRef;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinNameReferenceExpressionStub
    public String getReferencedName() {
        String string = this.referencedName.getString();
        if (string == null) {
            $$$reportNull$$$0(2);
        }
        return string;
    }
}
