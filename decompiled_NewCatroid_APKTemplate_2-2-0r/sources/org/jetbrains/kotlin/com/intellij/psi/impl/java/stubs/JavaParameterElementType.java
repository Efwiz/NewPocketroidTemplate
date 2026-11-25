package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiParameterStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiParameterImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ParameterElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;

/* JADX INFO: loaded from: classes3.dex */
public class JavaParameterElementType extends JavaStubElementType<PsiParameterStub, PsiParameter> {
    /* JADX WARN: Code duplicated, block: B:19:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2) {
            objArr[0] = Constants.TYPE_TREE;
        } else if (i == 3) {
            objArr[0] = "node";
        } else if (i == 4) {
            objArr[0] = "parentStub";
        } else if (i == 6 || i == 7) {
            objArr[0] = "dataStream";
        } else if (i != 9) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "sink";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaParameterElementType";
        switch (i) {
            case 2:
            case 3:
            case 4:
                objArr[2] = "createStub";
                break;
            case 5:
            case 6:
                objArr[2] = "serialize";
                break;
            case 7:
                objArr[2] = "deserialize";
                break;
            case 8:
            case 9:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "createPsi";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiParameterStub psiParameterStub, IndexSink indexSink) {
        if (psiParameterStub == null) {
            $$$reportNull$$$0(8);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(9);
        }
    }

    public JavaParameterElementType() {
        super("PARAMETER");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new ParameterElement(JavaElementType.PARAMETER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiParameter createPsi(PsiParameterStub psiParameterStub) {
        if (psiParameterStub == null) {
            $$$reportNull$$$0(0);
        }
        return getPsiFactory(psiParameterStub).createParameter(psiParameterStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiParameter createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiParameterImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiParameterStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(2);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(4);
        }
        TypeInfo typeInfoCreate = TypeInfo.create(lighterAST, lighterASTNode, stubElement);
        return new PsiParameterStubImpl(stubElement, RecordUtil.intern(lighterAST.getCharTable(), LightTreeUtil.requiredChildOfType(lighterAST, lighterASTNode, JavaTokenType.IDENTIFIER)), typeInfoCreate, typeInfoCreate.isEllipsis, false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiParameterStub psiParameterStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiParameterStub == null) {
            $$$reportNull$$$0(5);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(6);
        }
        stubOutputStream.writeName(psiParameterStub.getName());
        TypeInfo.writeTYPE(stubOutputStream, psiParameterStub.getType());
        stubOutputStream.writeByte(((PsiParameterStubImpl) psiParameterStub).getFlags());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiParameterStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(7);
        }
        String nameString = stubInputStream.readNameString();
        if (nameString == null) {
            throw new IOException("corrupted indices");
        }
        return new PsiParameterStubImpl(stubElement, nameString, TypeInfo.readTYPE(stubInputStream), stubInputStream.readByte());
    }
}
