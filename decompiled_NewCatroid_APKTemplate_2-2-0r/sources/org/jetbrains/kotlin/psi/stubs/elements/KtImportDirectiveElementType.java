package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.stubs.KotlinImportDirectiveStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinImportDirectiveStubImpl;

/* JADX INFO: loaded from: classes6.dex */
public class KtImportDirectiveElementType extends KtStubElementType<KotlinImportDirectiveStub, KtImportDirective> {
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
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtImportDirectiveElementType";
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
    public KtImportDirectiveElementType(String str) {
        super(str, KtImportDirective.class, KotlinImportDirectiveStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinImportDirectiveStub createStub(KtImportDirective ktImportDirective, StubElement stubElement) {
        if (ktImportDirective == null) {
            $$$reportNull$$$0(1);
        }
        FqName importedFqName = ktImportDirective.getImportedFqName();
        return new KotlinImportDirectiveStubImpl(stubElement, ktImportDirective.isAllUnder(), StringRef.fromString(importedFqName == null ? null : importedFqName.asString()), ktImportDirective.isValidImport());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinImportDirectiveStub kotlinImportDirectiveStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinImportDirectiveStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        stubOutputStream.writeBoolean(kotlinImportDirectiveStub.getIsAllUnder());
        FqName importedFqName = kotlinImportDirectiveStub.getImportedFqName();
        stubOutputStream.writeName(importedFqName != null ? importedFqName.asString() : null);
        stubOutputStream.writeBoolean(kotlinImportDirectiveStub.getIsValid());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinImportDirectiveStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(4);
        }
        return new KotlinImportDirectiveStubImpl(stubElement, stubInputStream.readBoolean(), stubInputStream.readName(), stubInputStream.readBoolean());
    }
}
