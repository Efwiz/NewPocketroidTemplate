package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinObjectStub;
import org.jetbrains.kotlin.psi.stubs.StubUtils;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinObjectStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.Utils;

/* JADX INFO: loaded from: classes6.dex */
public class KtObjectElementType extends KtStubElementType<KotlinObjectStub, KtObjectDeclaration> {
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
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtObjectElementType";
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
    public KtObjectElementType(String str) {
        super(str, KtObjectDeclaration.class, KotlinObjectStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinObjectStub createStub(KtObjectDeclaration ktObjectDeclaration, StubElement stubElement) {
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(1);
        }
        String name2 = ktObjectDeclaration.getName();
        return new KotlinObjectStubImpl(stubElement, StringRef.fromString(name2), KtPsiUtilKt.safeFqNameForLazyResolve(ktObjectDeclaration), ktObjectDeclaration.getClassId(), Utils.INSTANCE.wrapStrings(KtPsiUtilKt.getSuperNames(ktObjectDeclaration)), ktObjectDeclaration.isTopLevel(), ktObjectDeclaration.isCompanion(), ktObjectDeclaration.isLocal(), ktObjectDeclaration.isObjectLiteral());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinObjectStub kotlinObjectStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinObjectStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        stubOutputStream.writeName(kotlinObjectStub.getName());
        FqName fqName = kotlinObjectStub.getFqName();
        stubOutputStream.writeName(fqName != null ? fqName.toString() : null);
        StubUtils.serializeClassId(stubOutputStream, kotlinObjectStub.getClassId());
        stubOutputStream.writeBoolean(kotlinObjectStub.getIsTopLevel());
        stubOutputStream.writeBoolean(kotlinObjectStub.getIsDefault());
        stubOutputStream.writeBoolean(kotlinObjectStub.getIsLocal());
        stubOutputStream.writeBoolean(kotlinObjectStub.getIsObjectLiteral());
        List<String> superNames = kotlinObjectStub.getSuperNames();
        stubOutputStream.writeVarInt(superNames.size());
        Iterator<String> it = superNames.iterator();
        while (it.hasNext()) {
            stubOutputStream.writeName(it.next());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinObjectStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(4);
        }
        StringRef name2 = stubInputStream.readName();
        StringRef name3 = stubInputStream.readName();
        FqName fqName = name3 != null ? new FqName(name3.toString()) : null;
        ClassId classIdDeserializeClassId = StubUtils.deserializeClassId(stubInputStream);
        boolean z = stubInputStream.readBoolean();
        boolean z2 = stubInputStream.readBoolean();
        boolean z3 = stubInputStream.readBoolean();
        boolean z4 = stubInputStream.readBoolean();
        int varInt = stubInputStream.readVarInt();
        StringRef[] stringRefArrCreateArray = StringRef.createArray(varInt);
        for (int i = 0; i < varInt; i++) {
            stringRefArrCreateArray[i] = stubInputStream.readName();
        }
        return new KotlinObjectStubImpl(stubElement, name2, fqName, classIdDeserializeClassId, stringRefArrCreateArray, z, z2, z3, z4);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public void indexStub(KotlinObjectStub kotlinObjectStub, IndexSink indexSink) {
        if (kotlinObjectStub == null) {
            $$$reportNull$$$0(5);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(6);
        }
        StubIndexService.getInstance().indexObject(kotlinObjectStub, indexSink);
    }
}
