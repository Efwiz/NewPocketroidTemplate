package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.TreeBackedLighterAST;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.StubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILightStubFileElementType;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public class LightStubBuilder implements StubBuilder {
    private static final Logger LOG = Logger.getInstance((Class<?>) LightStubBuilder.class);
    public static final ThreadLocal<LighterAST> FORCED_AST = new ThreadLocal<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 2:
            case 3:
            case 8:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 4:
                objArr[0] = "root";
                break;
            case 5:
                objArr[0] = "rootStub";
                break;
            case 6:
            case 9:
                objArr[0] = "parent";
                break;
            case 7:
            case 10:
                objArr[0] = "node";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/LightStubBuilder";
        if (i != 1 && i != 2) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    objArr[2] = "skipChildProcessingWhenBuildingStubs";
                    break;
                default:
                    objArr[2] = "buildStubTree";
                    break;
            }
        } else {
            objArr[2] = "createStubForFile";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.StubBuilder
    public boolean skipChildProcessingWhenBuildingStubs(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        if (aSTNode2 != null) {
            return false;
        }
        $$$reportNull$$$0(7);
        return false;
    }

    protected boolean skipChildProcessingWhenBuildingStubs(LighterAST lighterAST, LighterASTNode lighterASTNode, LighterASTNode lighterASTNode2) {
        if (lighterAST == null) {
            $$$reportNull$$$0(8);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(9);
        }
        if (lighterASTNode2 != null) {
            return false;
        }
        $$$reportNull$$$0(10);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.StubBuilder
    public StubElement<?> buildStubTree(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        ThreadLocal<LighterAST> threadLocal = FORCED_AST;
        LighterAST lighterAST = threadLocal.get();
        if (lighterAST == null) {
            FileType fileType = psiFile.getFileType();
            if (!(fileType instanceof LanguageFileType)) {
                LOG.error("File is not of LanguageFileType: " + psiFile + ", " + fileType);
                return null;
            }
            if (!(psiFile instanceof PsiFileImpl)) {
                LOG.error("Unexpected PsiFile instance: " + psiFile + ", " + psiFile.getClass());
                return null;
            }
            if (((PsiFileImpl) psiFile).getElementTypeForStubBuilder() == null) {
                LOG.error("File is not of IStubFileElementType: " + psiFile);
                return null;
            }
            FileASTNode node = psiFile.getNode();
            lighterAST = node.getElementType() instanceof ILightStubFileElementType ? node.getLighterAST() : new TreeBackedLighterAST(node);
        } else {
            threadLocal.set(null);
        }
        StubElement<?> stubElementCreateStubForFile = createStubForFile(psiFile, lighterAST);
        buildStubTree(lighterAST, lighterAST.getRoot(), stubElementCreateStubForFile);
        return stubElementCreateStubForFile;
    }

    protected StubElement<?> createStubForFile(PsiFile psiFile, LighterAST lighterAST) {
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        if (lighterAST == null) {
            $$$reportNull$$$0(2);
        }
        return new PsiFileStubImpl(psiFile);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 2331. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    protected void buildStubTree(org.jetbrains.kotlin.com.intellij.lang.LighterAST r20, org.jetbrains.kotlin.com.intellij.lang.LighterASTNode r21, org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement<?> r22) {
        /*
            Method dump skipped, instruction units count: 233
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.com.intellij.psi.stubs.LightStubBuilder.buildStubTree(org.jetbrains.kotlin.com.intellij.lang.LighterAST, org.jetbrains.kotlin.com.intellij.lang.LighterASTNode, org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement):void");
    }

    private static StubElement<?> createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement<?> stubElement) {
        IElementType tokenType = lighterASTNode.getTokenType();
        if (tokenType instanceof IStubElementType) {
            if (tokenType instanceof ILightStubElementType) {
                ILightStubElementType iLightStubElementType = (ILightStubElementType) tokenType;
                if (iLightStubElementType.shouldCreateStub(lighterAST, lighterASTNode, stubElement)) {
                    return iLightStubElementType.createStub(lighterAST, lighterASTNode, stubElement);
                }
            } else {
                LOG.error("Element is not of ILightStubElementType: " + ObjectUtils.objectInfo(tokenType) + ", " + lighterASTNode);
            }
        }
        return stubElement;
    }

    private boolean skipNode(LighterAST lighterAST, LighterASTNode lighterASTNode, LighterASTNode lighterASTNode2) {
        if (lighterAST instanceof TreeBackedLighterAST) {
            TreeBackedLighterAST treeBackedLighterAST = (TreeBackedLighterAST) lighterAST;
            return skipChildProcessingWhenBuildingStubs(treeBackedLighterAST.unwrap(lighterASTNode), treeBackedLighterAST.unwrap(lighterASTNode2));
        }
        return skipChildProcessingWhenBuildingStubs(lighterAST, lighterASTNode, lighterASTNode2);
    }
}
