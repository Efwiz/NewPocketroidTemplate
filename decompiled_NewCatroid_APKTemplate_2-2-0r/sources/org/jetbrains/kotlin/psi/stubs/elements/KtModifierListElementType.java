package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.DataInputOutputUtil;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.stubs.KotlinModifierListStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinModifierListStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.ModifierMaskUtils;

/* JADX INFO: loaded from: classes6.dex */
public class KtModifierListElementType<T extends KtModifierList> extends KtStubElementType<KotlinModifierListStub, T> {
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
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtModifierListElementType";
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtModifierList) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtModifierListElementType(String str, Class<T> cls) {
        super(str, cls, KotlinModifierListStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
    }

    public KotlinModifierListStub createStub(T t, StubElement<?> stubElement) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        return new KotlinModifierListStubImpl(stubElement, ModifierMaskUtils.computeMaskFromModifierList(t), this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinModifierListStub kotlinModifierListStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinModifierListStub == null) {
            $$$reportNull$$$0(3);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(4);
        }
        DataInputOutputUtil.writeLONG(stubOutputStream, ((KotlinModifierListStubImpl) kotlinModifierListStub).getMask());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinModifierListStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(5);
        }
        return new KotlinModifierListStubImpl(stubElement, DataInputOutputUtil.readLONG(stubInputStream), this);
    }
}
