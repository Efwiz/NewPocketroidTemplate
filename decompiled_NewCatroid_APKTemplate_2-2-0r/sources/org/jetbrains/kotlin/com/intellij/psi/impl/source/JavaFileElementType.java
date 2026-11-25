package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParser;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil;
import org.jetbrains.kotlin.com.intellij.model.ModelBranch;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.JavaFileElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.LightStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILightStubFileElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: loaded from: classes3.dex */
public class JavaFileElementType extends ILightStubFileElementType<PsiJavaFileStub> {
    public static final int STUB_VERSION = 53;
    private static volatile int TEST_STUB_VERSION_MODIFIER;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "chameleon";
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
                objArr[0] = "file";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaFileElementType";
        switch (i) {
            case 1:
                objArr[2] = "parseContents";
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
                objArr[2] = "isInSourceContent";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiJavaFileStub psiJavaFileStub, IndexSink indexSink) {
        if (psiJavaFileStub == null) {
            $$$reportNull$$$0(5);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(6);
        }
    }

    public JavaFileElementType() {
        super("java.FILE", JavaLanguage.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILightStubFileElementType, org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType
    public LightStubBuilder getBuilder() {
        return new JavaLightStubBuilder();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType
    public int getStubVersion() {
        return (ApplicationManager.getApplication().isUnitTestMode() ? TEST_STUB_VERSION_MODIFIER : 0) + 53;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType
    public boolean shouldBuildStubFor(VirtualFile virtualFile) {
        return isInSourceContent(virtualFile);
    }

    public static boolean isInSourceContent(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        VirtualFile parent = virtualFile.getParent();
        if (parent == null || parent.getUserData(LanguageLevel.KEY) != null) {
            return true;
        }
        ModelBranch fileBranch = ModelBranch.getFileBranch(virtualFile);
        return fileBranch != null && FileIndexFacade.getInstance(fileBranch.getProject()).isInSourceContent(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType
    public ASTNode createNode(CharSequence charSequence) {
        return new JavaFileElement(charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILightStubFileElementType
    public FlyweightCapableTreeStructure<LighterASTNode> parseContentsLight(ASTNode aSTNode) {
        PsiBuilder psiBuilderCreateBuilder = JavaParserUtil.createBuilder(aSTNode);
        doParse(psiBuilderCreateBuilder);
        return psiBuilderCreateBuilder.getLightTree();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
    public ASTNode parseContents(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        PsiBuilder psiBuilderCreateBuilder = JavaParserUtil.createBuilder(aSTNode);
        doParse(psiBuilderCreateBuilder);
        return psiBuilderCreateBuilder.getTreeBuilt().getFirstChildNode();
    }

    private void doParse(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        JavaParser.INSTANCE.getFileParser().parse(psiBuilder);
        markerM6463mark.done(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public String getExternalId() {
        return "java.FILE";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiJavaFileStub psiJavaFileStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiJavaFileStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        stubOutputStream.writeBoolean(psiJavaFileStub.isCompiled());
        LanguageLevel languageLevel = psiJavaFileStub.getLanguageLevel();
        stubOutputStream.writeByte(languageLevel != null ? languageLevel.ordinal() : -1);
        stubOutputStream.writeName(psiJavaFileStub.getPackageName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiJavaFileStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(4);
        }
        boolean z = stubInputStream.readBoolean();
        byte b = stubInputStream.readByte();
        return new PsiJavaFileStubImpl(null, stubInputStream.readNameString(), b >= 0 ? LanguageLevel.values()[b] : null, z);
    }

    public static void setTestStubVersionModifier(int i) {
        TEST_STUB_VERSION_MODIFIER = i;
    }
}
