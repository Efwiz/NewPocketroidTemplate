package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.stubs.KotlinParameterStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinParameterStubImpl;

/* JADX INFO: loaded from: classes6.dex */
public class KtParameterElementType extends KtStubElementType<KotlinParameterStub, KtParameter> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "psi";
                break;
            case 2:
            case 5:
                objArr[0] = "stub";
                break;
            case 3:
            case 4:
                objArr[0] = "dataStream";
                break;
            case 6:
                objArr[0] = "sink";
                break;
            default:
                objArr[0] = "debugName";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtParameterElementType";
        switch (i) {
            case 1:
                objArr[2] = "createStub";
                break;
            case 2:
            case 3:
                objArr[2] = "serialize";
                break;
            case 4:
                objArr[2] = "deserialize";
                break;
            case 5:
            case 6:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtParameterElementType(String str) {
        super(str, KtParameter.class, KotlinParameterStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinParameterStub createStub(KtParameter ktParameter, StubElement stubElement) {
        if (ktParameter == null) {
            $$$reportNull$$$0(1);
        }
        FqName fqNameMo7391getFqName = ktParameter.mo7391getFqName();
        return new KotlinParameterStubImpl(stubElement, StringRef.fromString(fqNameMo7391getFqName != null ? fqNameMo7391getFqName.asString() : null), StringRef.fromString(ktParameter.get_name()), ktParameter.isMutable(), ktParameter.hasValOrVar(), ktParameter.hasDefaultValue(), null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinParameterStub kotlinParameterStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinParameterStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        stubOutputStream.writeName(kotlinParameterStub.getName());
        stubOutputStream.writeBoolean(kotlinParameterStub.getIsMutable());
        stubOutputStream.writeBoolean(kotlinParameterStub.getHasValOrVar());
        stubOutputStream.writeBoolean(kotlinParameterStub.getHasDefaultValue());
        FqName fqName = kotlinParameterStub.getFqName();
        stubOutputStream.writeName(fqName != null ? fqName.asString() : null);
        stubOutputStream.writeName(kotlinParameterStub instanceof KotlinParameterStubImpl ? ((KotlinParameterStubImpl) kotlinParameterStub).getFunctionTypeParameterName() : null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinParameterStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(4);
        }
        return new KotlinParameterStubImpl(stubElement, stubInputStream.readName(), stubInputStream.readName(), stubInputStream.readBoolean(), stubInputStream.readBoolean(), stubInputStream.readBoolean(), stubInputStream.readNameString());
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public void indexStub(KotlinParameterStub kotlinParameterStub, IndexSink indexSink) {
        if (kotlinParameterStub == null) {
            $$$reportNull$$$0(5);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(6);
        }
        StubIndexService.getInstance().indexParameter(kotlinParameterStub, indexSink);
    }
}
