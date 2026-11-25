package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class EmptyStubElementType<T extends PsiElement> extends IStubElementType<EmptyStub<?>, T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "psi";
                break;
            case 2:
            case 3:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/EmptyStubElementType";
                break;
            case 4:
            case 8:
                objArr[0] = "stub";
                break;
            case 5:
            case 6:
                objArr[0] = "dataStream";
                break;
            case 9:
                objArr[0] = "sink";
                break;
            default:
                objArr[0] = "debugName";
                break;
        }
        if (i == 2) {
            objArr[1] = "createStub";
        } else if (i == 3) {
            objArr[1] = "getExternalId";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/EmptyStubElementType";
        } else {
            objArr[1] = "deserialize";
        }
        switch (i) {
            case 1:
                objArr[2] = "createStub";
                break;
            case 2:
            case 3:
            case 7:
                break;
            case 4:
            case 5:
                objArr[2] = "serialize";
                break;
            case 6:
                objArr[2] = "deserialize";
                break;
            case 8:
            case 9:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public final void indexStub(EmptyStub emptyStub, IndexSink indexSink) {
        if (emptyStub == null) {
            $$$reportNull$$$0(8);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(9);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public final void serialize(EmptyStub emptyStub, StubOutputStream stubOutputStream) {
        if (emptyStub == null) {
            $$$reportNull$$$0(4);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(5);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub(psiElement, (StubElement<? extends PsiElement>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected EmptyStubElementType(String str, Language language) {
        super(str, language);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public final EmptyStub<?> createStub(T t, StubElement<? extends PsiElement> stubElement) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        EmptyStub<?> emptyStubCreateStub = createStub(stubElement);
        if (emptyStubCreateStub == null) {
            $$$reportNull$$$0(2);
        }
        return emptyStubCreateStub;
    }

    protected EmptyStub<?> createStub(StubElement<?> stubElement) {
        return new EmptyStub<>(stubElement, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public String getExternalId() {
        String str = getLanguage().getID() + this;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public final EmptyStub<?> deserialize(StubInputStream stubInputStream, StubElement stubElement) {
        if (stubInputStream == null) {
            $$$reportNull$$$0(6);
        }
        EmptyStub<?> emptyStubCreateStub = createStub(stubElement);
        if (emptyStubCreateStub == null) {
            $$$reportNull$$$0(7);
        }
        return emptyStubCreateStub;
    }
}
