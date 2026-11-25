package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPropertyStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinStubOrigin;

/* JADX INFO: loaded from: classes6.dex */
public class KtPropertyElementType extends KtStubElementType<KotlinPropertyStub, KtProperty> {
    static final /* synthetic */ boolean $assertionsDisabled = false;

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
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtPropertyElementType";
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
    public KtPropertyElementType(String str) {
        super(str, KtProperty.class, KotlinPropertyStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinPropertyStub createStub(KtProperty ktProperty, StubElement stubElement) {
        if (ktProperty == null) {
            $$$reportNull$$$0(1);
        }
        return new KotlinPropertyStubImpl(stubElement, StringRef.fromString(ktProperty.getName()), ktProperty.isVar(), ktProperty.isTopLevel(), ktProperty.hasDelegate(), ktProperty.hasDelegateExpression(), ktProperty.hasInitializer(), ktProperty.mo7393getReceiverTypeReference() != null, ktProperty.mo7396getTypeReference() != null, KtPsiUtilKt.safeFqNameForLazyResolve(ktProperty), null, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinPropertyStub kotlinPropertyStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinPropertyStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        stubOutputStream.writeName(kotlinPropertyStub.getName());
        stubOutputStream.writeBoolean(kotlinPropertyStub.getIsVar());
        stubOutputStream.writeBoolean(kotlinPropertyStub.getIsTopLevel());
        stubOutputStream.writeBoolean(kotlinPropertyStub.getHasDelegate());
        stubOutputStream.writeBoolean(kotlinPropertyStub.getHasDelegateExpression());
        stubOutputStream.writeBoolean(kotlinPropertyStub.getHasInitializer());
        stubOutputStream.writeBoolean(kotlinPropertyStub.getIsExtension());
        stubOutputStream.writeBoolean(kotlinPropertyStub.getHasReturnTypeRef());
        FqName fqName = kotlinPropertyStub.getFqName();
        stubOutputStream.writeName(fqName != null ? fqName.asString() : null);
        if (kotlinPropertyStub instanceof KotlinPropertyStubImpl) {
            KotlinPropertyStubImpl kotlinPropertyStubImpl = (KotlinPropertyStubImpl) kotlinPropertyStub;
            ConstantValue<?> constantInitializer = kotlinPropertyStubImpl.getConstantInitializer();
            if (constantInitializer != null) {
                KotlinConstantValueKt.serialize(constantInitializer, stubOutputStream);
            } else {
                stubOutputStream.writeInt(-1);
            }
            KotlinStubOrigin.serialize(kotlinPropertyStubImpl.getOrigin(), stubOutputStream);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinPropertyStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(4);
        }
        StringRef name2 = stubInputStream.readName();
        boolean z = stubInputStream.readBoolean();
        boolean z2 = stubInputStream.readBoolean();
        boolean z3 = stubInputStream.readBoolean();
        boolean z4 = stubInputStream.readBoolean();
        boolean z5 = stubInputStream.readBoolean();
        boolean z6 = stubInputStream.readBoolean();
        boolean z7 = stubInputStream.readBoolean();
        StringRef name3 = stubInputStream.readName();
        return new KotlinPropertyStubImpl(stubElement, name2, z, z2, z3, z4, z5, z6, z7, name3 != null ? new FqName(name3.toString()) : null, KotlinConstantValueKt.createConstantValue(stubInputStream), KotlinStubOrigin.deserialize(stubInputStream));
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public void indexStub(KotlinPropertyStub kotlinPropertyStub, IndexSink indexSink) {
        if (kotlinPropertyStub == null) {
            $$$reportNull$$$0(5);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(6);
        }
        StubIndexService.getInstance().indexProperty(kotlinPropertyStub, indexSink);
    }
}
