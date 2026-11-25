package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiNameValuePairStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.NameValuePairElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiNameValuePairImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaNameValuePairType extends JavaStubElementType<PsiNameValuePairStub, PsiNameValuePair> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 2:
            default:
                objArr[0] = "node";
                break;
            case 3:
                objArr[0] = "parentStub";
                break;
            case 4:
            case 5:
            case 8:
                objArr[0] = "stub";
                break;
            case 6:
            case 7:
                objArr[0] = "dataStream";
                break;
            case 9:
                objArr[0] = "sink";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaNameValuePairType";
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "createStub";
                break;
            case 4:
            default:
                objArr[2] = "createPsi";
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
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiNameValuePairStub psiNameValuePairStub, IndexSink indexSink) {
        if (psiNameValuePairStub == null) {
            $$$reportNull$$$0(8);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(9);
        }
    }

    JavaNameValuePairType() {
        super("NAME_VALUE_PAIR", true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiNameValuePair createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        return new PsiNameValuePairImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new NameValuePairElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiNameValuePairStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(1);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(3);
        }
        String strIntern = null;
        String filteredString = null;
        for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
            if (lighterASTNode2.getTokenType() == JavaTokenType.IDENTIFIER) {
                strIntern = RecordUtil.intern(lighterAST.getCharTable(), lighterASTNode2);
            } else if (ElementType.ANNOTATION_MEMBER_VALUE_BIT_SET.contains(lighterASTNode2.getTokenType())) {
                filteredString = LightTreeUtil.toFilteredString(lighterAST, lighterASTNode2, null);
            }
        }
        return new PsiNameValuePairStubImpl(stubElement, strIntern, filteredString);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiNameValuePair createPsi(PsiNameValuePairStub psiNameValuePairStub) {
        if (psiNameValuePairStub == null) {
            $$$reportNull$$$0(4);
        }
        return getPsiFactory(psiNameValuePairStub).createNameValuePair(psiNameValuePairStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiNameValuePairStub psiNameValuePairStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiNameValuePairStub == null) {
            $$$reportNull$$$0(5);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(6);
        }
        stubOutputStream.writeName(psiNameValuePairStub.getName());
        String value = psiNameValuePairStub.getValue();
        boolean z = value != null;
        stubOutputStream.writeBoolean(z);
        if (z) {
            stubOutputStream.writeUTFFast(value);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiNameValuePairStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(7);
        }
        return new PsiNameValuePairStubImpl(stubElement, stubInputStream.readNameString(), stubInputStream.readBoolean() ? stubInputStream.readUTFFast() : null);
    }
}
