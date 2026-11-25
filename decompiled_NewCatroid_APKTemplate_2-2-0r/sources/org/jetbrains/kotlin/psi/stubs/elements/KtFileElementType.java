package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.StubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.parsing.KotlinParser;
import org.jetbrains.kotlin.psi.stubs.KotlinFileStub;

/* JADX INFO: loaded from: classes6.dex */
public class KtFileElementType extends IStubFileElementType<KotlinFileStub> {
    public static final KtFileElementType INSTANCE = new KtFileElementType();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "dataStream";
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/psi/stubs/elements/KtFileElementType";
        } else if (i == 4) {
            objArr[0] = "chameleon";
        } else if (i == 5) {
            objArr[0] = "psi";
        } else if (i != 7) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "sink";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtFileElementType";
        } else {
            objArr[1] = "deserialize";
        }
        switch (i) {
            case 2:
                objArr[2] = "deserialize";
                break;
            case 3:
                break;
            case 4:
            case 5:
                objArr[2] = "doParseContents";
                break;
            case 6:
            case 7:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "serialize";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType
    public int getStubVersion() {
        return 154;
    }

    private KtFileElementType() {
        super("kotlin.FILE", KotlinLanguage.INSTANCE);
    }

    protected KtFileElementType(String str) {
        super(str, KotlinLanguage.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType
    public StubBuilder getBuilder() {
        return new KtFileStubBuilder();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public String getExternalId() {
        return "kotlin.FILE";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinFileStub kotlinFileStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinFileStub == null) {
            $$$reportNull$$$0(0);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(1);
        }
        StubIndexService.getInstance().serializeFileStub(kotlinFileStub, stubOutputStream);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinFileStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(2);
        }
        KotlinFileStub kotlinFileStubDeserializeFileStub = StubIndexService.getInstance().deserializeFileStub(stubInputStream);
        if (kotlinFileStubDeserializeFileStub == null) {
            $$$reportNull$$$0(3);
        }
        return kotlinFileStubDeserializeFileStub;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType
    protected ASTNode doParseContents(ASTNode aSTNode, PsiElement psiElement) {
        if (aSTNode == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        return KotlinParser.parse(PsiBuilderFactory.getInstance().createBuilder(psiElement.getProject(), aSTNode, (Lexer) null, getLanguageForParser(psiElement), aSTNode.getChars()), psiElement.getContainingFile()).getFirstChildNode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(KotlinFileStub kotlinFileStub, IndexSink indexSink) {
        if (kotlinFileStub == null) {
            $$$reportNull$$$0(6);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(7);
        }
        StubIndexService.getInstance().indexFile(kotlinFileStub, indexSink);
    }
}
