package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPlaceHolderStubImpl;

/* JADX INFO: loaded from: classes6.dex */
public class KtPlaceHolderStubElementType<T extends KtElementImplStub<? extends StubElement<?>>> extends KtStubElementType<KotlinPlaceHolderStub<T>, T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "psiClass";
        } else if (i == 2) {
            objArr[0] = "psi";
        } else if (i == 3) {
            objArr[0] = "stub";
        } else if (i == 4 || i == 5) {
            objArr[0] = "dataStream";
        } else {
            objArr[0] = "debugName";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtPlaceHolderStubElementType";
        if (i == 2) {
            objArr[2] = "createStub";
        } else if (i == 3 || i == 4) {
            objArr[2] = "serialize";
        } else if (i != 5) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "deserialize";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinPlaceHolderStub<T> kotlinPlaceHolderStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(3);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(4);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtElementImplStub) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtPlaceHolderStubElementType(String str, Class<T> cls) {
        super(str, cls, KotlinPlaceHolderStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
    }

    public KotlinPlaceHolderStub<T> createStub(T t, StubElement<?> stubElement) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        return new KotlinPlaceHolderStubImpl(stubElement, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinPlaceHolderStub<T> deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(5);
        }
        return new KotlinPlaceHolderStubImpl(stubElement, this);
    }
}
