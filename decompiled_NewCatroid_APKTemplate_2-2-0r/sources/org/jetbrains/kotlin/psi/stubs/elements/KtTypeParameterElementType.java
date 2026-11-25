package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.stubs.KotlinTypeParameterStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeParameterStubImpl;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: loaded from: classes6.dex */
public class KtTypeParameterElementType extends KtStubElementType<KotlinTypeParameterStub, KtTypeParameter> {
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
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtTypeParameterElementType";
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
    public KtTypeParameterElementType(String str) {
        super(str, KtTypeParameter.class, KotlinTypeParameterStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinTypeParameterStub createStub(KtTypeParameter ktTypeParameter, StubElement stubElement) {
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(1);
        }
        return new KotlinTypeParameterStubImpl(stubElement, StringRef.fromString(ktTypeParameter.getName()), ktTypeParameter.getVariance() == Variance.IN_VARIANCE, ktTypeParameter.getVariance() == Variance.OUT_VARIANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinTypeParameterStub kotlinTypeParameterStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinTypeParameterStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        stubOutputStream.writeName(kotlinTypeParameterStub.getName());
        stubOutputStream.writeBoolean(kotlinTypeParameterStub.getIsInVariance());
        stubOutputStream.writeBoolean(kotlinTypeParameterStub.getIsOutVariance());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinTypeParameterStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(4);
        }
        return new KotlinTypeParameterStubImpl(stubElement, stubInputStream.readName(), stubInputStream.readBoolean(), stubInputStream.readBoolean());
    }
}
