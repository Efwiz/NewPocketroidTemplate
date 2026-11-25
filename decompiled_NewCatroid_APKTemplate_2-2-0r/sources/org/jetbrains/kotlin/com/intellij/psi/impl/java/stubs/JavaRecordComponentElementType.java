package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiRecordComponentStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiRecordComponentImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;

/* JADX INFO: loaded from: classes3.dex */
public class JavaRecordComponentElementType extends JavaStubElementType<PsiRecordComponentStub, PsiRecordComponent> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "dataStream";
                break;
            case 3:
            case 5:
            default:
                objArr[0] = "stub";
                break;
            case 4:
                objArr[0] = "sink";
                break;
            case 6:
            case 8:
                objArr[0] = "node";
                break;
            case 7:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 9:
                objArr[0] = "parentStub";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaRecordComponentElementType";
        switch (i) {
            case 2:
                objArr[2] = "deserialize";
                break;
            case 3:
            case 4:
                objArr[2] = "indexStub";
                break;
            case 5:
            case 6:
                objArr[2] = "createPsi";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "createStub";
                break;
            default:
                objArr[2] = "serialize";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiRecordComponentStub psiRecordComponentStub, IndexSink indexSink) {
        if (psiRecordComponentStub == null) {
            $$$reportNull$$$0(3);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(4);
        }
    }

    public JavaRecordComponentElementType() {
        super("RECORD_COMPONENT");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new CompositeElement(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiRecordComponentStub psiRecordComponentStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiRecordComponentStub == null) {
            $$$reportNull$$$0(0);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(1);
        }
        stubOutputStream.writeName(psiRecordComponentStub.getName());
        TypeInfo.writeTYPE(stubOutputStream, psiRecordComponentStub.getType());
        stubOutputStream.writeByte(((PsiRecordComponentStubImpl) psiRecordComponentStub).getFlags());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiRecordComponentStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(2);
        }
        return new PsiRecordComponentStubImpl(stubElement, stubInputStream.readNameString(), TypeInfo.readTYPE(stubInputStream), stubInputStream.readByte());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiRecordComponent createPsi(PsiRecordComponentStub psiRecordComponentStub) {
        if (psiRecordComponentStub == null) {
            $$$reportNull$$$0(5);
        }
        return getPsiFactory(psiRecordComponentStub).createRecordComponent(psiRecordComponentStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiRecordComponent createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        return new PsiRecordComponentImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiRecordComponentStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(7);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(8);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(9);
        }
        TypeInfo typeInfoCreate = TypeInfo.create(lighterAST, lighterASTNode, stubElement);
        String strIntern = RecordUtil.intern(lighterAST.getCharTable(), LightTreeUtil.requiredChildOfType(lighterAST, lighterASTNode, JavaTokenType.IDENTIFIER));
        LighterASTNode lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode, JavaElementType.MODIFIER_LIST);
        return new PsiRecordComponentStubImpl(stubElement, strIntern, typeInfoCreate, typeInfoCreate.isEllipsis, lighterASTNodeFirstChildOfType != null && RecordUtil.isDeprecatedByAnnotation(lighterAST, lighterASTNodeFirstChildOfType));
    }
}
