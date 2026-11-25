package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinClassStub;
import org.jetbrains.kotlin.psi.stubs.StubUtils;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinClassStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.Utils;

/* JADX INFO: loaded from: classes6.dex */
public class KtClassElementType extends KtStubElementType<KotlinClassStub, KtClass> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 4:
            case 7:
                objArr[0] = "stub";
                break;
            case 2:
                objArr[0] = "node";
                break;
            case 3:
                objArr[0] = "psi";
                break;
            case 5:
            case 6:
                objArr[0] = "dataStream";
                break;
            case 8:
                objArr[0] = "sink";
                break;
            default:
                objArr[0] = "debugName";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtClassElementType";
        switch (i) {
            case 1:
                objArr[2] = "createPsi";
                break;
            case 2:
                objArr[2] = "createPsiFromAst";
                break;
            case 3:
                objArr[2] = "createStub";
                break;
            case 4:
            case 5:
                objArr[2] = "serialize";
                break;
            case 6:
                objArr[2] = "deserialize";
                break;
            case 7:
            case 8:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClassElementType(String str) {
        super(str, KtClass.class, KotlinClassStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KtClass createPsi(KotlinClassStub kotlinClassStub) {
        if (kotlinClassStub == null) {
            $$$reportNull$$$0(1);
        }
        return !kotlinClassStub.getIsEnumEntry() ? new KtClass(kotlinClassStub) : new KtEnumEntry(kotlinClassStub);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public KtClass createPsiFromAst(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        return aSTNode.getElementType() != KtStubElementTypes.ENUM_ENTRY ? new KtClass(aSTNode) : new KtEnumEntry(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinClassStub createStub(KtClass ktClass, StubElement stubElement) {
        if (ktClass == null) {
            $$$reportNull$$$0(3);
        }
        FqName fqNameSafeFqNameForLazyResolve = KtPsiUtilKt.safeFqNameForLazyResolve(ktClass);
        boolean z = ktClass instanceof KtEnumEntry;
        return new KotlinClassStubImpl(getStubType(z), stubElement, StringRef.fromString(fqNameSafeFqNameForLazyResolve != null ? fqNameSafeFqNameForLazyResolve.asString() : null), ktClass.getClassId(), StringRef.fromString(ktClass.getName()), Utils.INSTANCE.wrapStrings(KtPsiUtilKt.getSuperNames(ktClass)), ktClass.isInterface(), z, ktClass.isLocal(), ktClass.isTopLevel());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinClassStub kotlinClassStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinClassStub == null) {
            $$$reportNull$$$0(4);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(5);
        }
        stubOutputStream.writeName(kotlinClassStub.getName());
        FqName fqName = kotlinClassStub.getFqName();
        stubOutputStream.writeName(fqName == null ? null : fqName.asString());
        StubUtils.serializeClassId(stubOutputStream, kotlinClassStub.getClassId());
        stubOutputStream.writeBoolean(kotlinClassStub.getIsInterface());
        stubOutputStream.writeBoolean(kotlinClassStub.getIsEnumEntry());
        stubOutputStream.writeBoolean(kotlinClassStub.getIsLocal());
        stubOutputStream.writeBoolean(kotlinClassStub.getIsTopLevel());
        List<String> superNames = kotlinClassStub.getSuperNames();
        stubOutputStream.writeVarInt(superNames.size());
        Iterator<String> it = superNames.iterator();
        while (it.hasNext()) {
            stubOutputStream.writeName(it.next());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinClassStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(6);
        }
        StringRef name2 = stubInputStream.readName();
        StringRef name3 = stubInputStream.readName();
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
        return new KotlinClassStubImpl(getStubType(z2), stubElement, name3, classIdDeserializeClassId, name2, stringRefArrCreateArray, z, z2, z3, z4);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public void indexStub(KotlinClassStub kotlinClassStub, IndexSink indexSink) {
        if (kotlinClassStub == null) {
            $$$reportNull$$$0(7);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(8);
        }
        StubIndexService.getInstance().indexClass(kotlinClassStub, indexSink);
    }

    public static KtClassElementType getStubType(boolean z) {
        return z ? KtStubElementTypes.ENUM_ENTRY : KtStubElementTypes.CLASS;
    }
}
