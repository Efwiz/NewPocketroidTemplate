package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinFunctionStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFunctionStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinStubOrigin;

/* JADX INFO: loaded from: classes6.dex */
public class KtFunctionElementType extends KtStubElementType<KotlinFunctionStub, KtNamedFunction> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "psi";
                break;
            case 2:
                objArr[0] = "parentStub";
                break;
            case 3:
            case 6:
                objArr[0] = "stub";
                break;
            case 4:
            case 5:
                objArr[0] = "dataStream";
                break;
            case 7:
                objArr[0] = "sink";
                break;
            default:
                objArr[0] = "debugName";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtFunctionElementType";
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "createStub";
                break;
            case 3:
            case 4:
                objArr[2] = "serialize";
                break;
            case 5:
                objArr[2] = "deserialize";
                break;
            case 6:
            case 7:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFunctionElementType(String str) {
        super(str, KtNamedFunction.class, KotlinFunctionStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinFunctionStub createStub(KtNamedFunction ktNamedFunction, StubElement stubElement) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(1);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(2);
        }
        boolean z = ktNamedFunction.getMyParent() instanceof KtFile;
        boolean z2 = ktNamedFunction.mo7393getReceiverTypeReference() != null;
        return new KotlinFunctionStubImpl(stubElement, StringRef.fromString(ktNamedFunction.getMyName()), z, KtPsiUtilKt.safeFqNameForLazyResolve(ktNamedFunction), z2, ktNamedFunction.hasBlockBody(), ktNamedFunction.hasBody(), ktNamedFunction.hasTypeParameterListBeforeFunctionName(), ktNamedFunction.mayHaveContract(), null, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinFunctionStub kotlinFunctionStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinFunctionStub == null) {
            $$$reportNull$$$0(3);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(4);
        }
        stubOutputStream.writeName(kotlinFunctionStub.getName());
        stubOutputStream.writeBoolean(kotlinFunctionStub.getIsTopLevel());
        FqName fqName = kotlinFunctionStub.getFqName();
        stubOutputStream.writeName(fqName != null ? fqName.asString() : null);
        stubOutputStream.writeBoolean(kotlinFunctionStub.getIsExtension());
        stubOutputStream.writeBoolean(kotlinFunctionStub.getHasBlockBody());
        stubOutputStream.writeBoolean(kotlinFunctionStub.getHasBody());
        stubOutputStream.writeBoolean(kotlinFunctionStub.getHasTypeParameterListBeforeFunctionName());
        boolean zMayHaveContract = kotlinFunctionStub.getMayHaveContract();
        stubOutputStream.writeBoolean(zMayHaveContract);
        if (kotlinFunctionStub instanceof KotlinFunctionStubImpl) {
            KotlinFunctionStubImpl kotlinFunctionStubImpl = (KotlinFunctionStubImpl) kotlinFunctionStub;
            if (zMayHaveContract) {
                kotlinFunctionStubImpl.serializeContract(stubOutputStream);
            }
            KotlinStubOrigin.serialize(kotlinFunctionStubImpl.getOrigin(), stubOutputStream);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinFunctionStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(5);
        }
        StringRef name2 = stubInputStream.readName();
        boolean z = stubInputStream.readBoolean();
        StringRef name3 = stubInputStream.readName();
        FqName fqName = name3 != null ? new FqName(name3.toString()) : null;
        boolean z2 = stubInputStream.readBoolean();
        boolean z3 = stubInputStream.readBoolean();
        boolean z4 = stubInputStream.readBoolean();
        boolean z5 = stubInputStream.readBoolean();
        boolean z6 = stubInputStream.readBoolean();
        return new KotlinFunctionStubImpl(stubElement, name2, z, fqName, z2, z3, z4, z5, z6, z6 ? KotlinFunctionStubImpl.INSTANCE.deserializeContract(stubInputStream) : null, KotlinStubOrigin.deserialize(stubInputStream));
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public void indexStub(KotlinFunctionStub kotlinFunctionStub, IndexSink indexSink) {
        if (kotlinFunctionStub == null) {
            $$$reportNull$$$0(6);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(7);
        }
        StubIndexService.getInstance().indexFunction(kotlinFunctionStub, indexSink);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public String getExternalId() {
        return "kotlin.FUNCTION";
    }
}
