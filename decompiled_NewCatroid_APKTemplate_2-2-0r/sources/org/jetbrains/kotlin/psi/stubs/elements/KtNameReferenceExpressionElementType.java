package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.stubs.KotlinNameReferenceExpressionStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinNameReferenceExpressionStubImpl;

/* JADX INFO: loaded from: classes6.dex */
public class KtNameReferenceExpressionElementType extends KtStubElementType<KotlinNameReferenceExpressionStub, KtNameReferenceExpression> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "psi";
        } else if (i == 2) {
            objArr[0] = "stub";
        } else if (i == 3 || i == 4) {
            objArr[0] = "dataStream";
        } else {
            objArr[0] = "debugName";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtNameReferenceExpressionElementType";
        if (i == 1) {
            objArr[2] = "createStub";
        } else if (i == 2 || i == 3) {
            objArr[2] = "serialize";
        } else if (i != 4) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "deserialize";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNameReferenceExpressionElementType(String str) {
        super(str, KtNameReferenceExpression.class, KotlinNameReferenceExpressionStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinNameReferenceExpressionStub createStub(KtNameReferenceExpression ktNameReferenceExpression, StubElement stubElement) {
        if (ktNameReferenceExpression == null) {
            $$$reportNull$$$0(1);
        }
        return new KotlinNameReferenceExpressionStubImpl(stubElement, StringRef.fromString(ktNameReferenceExpression.getReferencedName()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinNameReferenceExpressionStub kotlinNameReferenceExpressionStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinNameReferenceExpressionStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        stubOutputStream.writeName(kotlinNameReferenceExpressionStub.getReferencedName());
        stubOutputStream.writeBoolean((kotlinNameReferenceExpressionStub instanceof KotlinNameReferenceExpressionStubImpl) && ((KotlinNameReferenceExpressionStubImpl) kotlinNameReferenceExpressionStub).isClassRef());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinNameReferenceExpressionStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(4);
        }
        return new KotlinNameReferenceExpressionStubImpl(stubElement, stubInputStream.readName(), stubInputStream.readBoolean());
    }
}
