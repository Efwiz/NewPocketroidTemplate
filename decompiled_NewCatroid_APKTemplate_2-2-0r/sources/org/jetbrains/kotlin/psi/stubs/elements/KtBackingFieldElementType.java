package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.psi.KtBackingField;
import org.jetbrains.kotlin.psi.stubs.KotlinBackingFieldStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinBackingFieldStubImpl;

/* JADX INFO: loaded from: classes6.dex */
public class KtBackingFieldElementType extends KtStubElementType<KotlinBackingFieldStub, KtBackingField> {
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
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtBackingFieldElementType";
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
    public KtBackingFieldElementType(String str) {
        super(str, KtBackingField.class, KotlinBackingFieldStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinBackingFieldStub createStub(KtBackingField ktBackingField, StubElement stubElement) {
        if (ktBackingField == null) {
            $$$reportNull$$$0(1);
        }
        return new KotlinBackingFieldStubImpl(stubElement, ktBackingField.hasInitializer());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinBackingFieldStub kotlinBackingFieldStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinBackingFieldStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        stubOutputStream.writeBoolean(kotlinBackingFieldStub.hasInitializer());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinBackingFieldStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(4);
        }
        return new KotlinBackingFieldStubImpl(stubElement, stubInputStream.readBoolean());
    }
}
