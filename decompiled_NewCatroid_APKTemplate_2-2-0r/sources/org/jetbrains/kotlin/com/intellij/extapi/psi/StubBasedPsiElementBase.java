package org.jetbrains.kotlin.com.intellij.extapi.psi;

import java.util.ArrayList;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.RuntimeExceptionWithAttachments;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.project.ProjectCoreUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.Stub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTree;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public class StubBasedPsiElementBase<T extends StubElement> extends ASTDelegatePsiElement {
    public static final Key<String> CREATION_TRACE = Key.create("CREATION_TRACE");
    public static final boolean ourTraceStubAstBinding = "true".equals(System.getProperty("trace.stub.ast.binding", "false"));
    private final IElementType myElementType;
    private volatile SubstrateRef mySubstrateRef;

    /* JADX WARN: Code duplicated, block: B:18:0x0039  */
    /* JADX WARN: Code duplicated, block: B:34:0x005a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 4 && i != 5 && i != 9 && i != 21 && i != 24 && i != 25 && i != 28 && i != 29 && i != 32 && i != 33 && i != 36 && i != 37) {
            switch (i) {
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 4 && i != 5 && i != 9 && i != 21 && i != 24 && i != 25 && i != 28 && i != 29 && i != 32 && i != 33 && i != 36 && i != 37) {
            switch (i) {
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "nodeType";
                break;
            case 2:
            case 12:
                objArr[0] = "node";
                break;
            case 3:
            case 4:
            case 5:
            case 9:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 21:
            case 24:
            case 25:
            case 28:
            case 29:
            case 32:
            case 33:
            case 36:
            case 37:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/extapi/psi/StubBasedPsiElementBase";
                break;
            case 6:
            case 10:
                objArr[0] = "file";
                break;
            case 7:
            case 8:
                objArr[0] = "fileElement";
                break;
            case 11:
                objArr[0] = "treeElement";
                break;
            case 13:
                objArr[0] = "substrateRef";
                break;
            case 19:
            case 20:
            case 22:
            case 26:
                objArr[0] = "elementType";
                break;
            case 23:
            case 31:
                objArr[0] = "array";
                break;
            case 27:
            case 35:
                objArr[0] = "f";
                break;
            case 30:
            case 34:
                objArr[0] = "filter";
                break;
            case 38:
                objArr[0] = "parentClass";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 3 || i == 4 || i == 5) {
            objArr[1] = "getNode";
        } else if (i == 9) {
            objArr[1] = "dumpCreationTraces";
        } else if (i == 21) {
            objArr[1] = "getRequiredStubOrPsiChild";
        } else if (i != 24 && i != 25 && i != 28 && i != 29 && i != 32 && i != 33 && i != 36 && i != 37) {
            switch (i) {
                case 14:
                    objArr[1] = "getLanguage";
                    break;
                case 15:
                    objArr[1] = "getContainingFile";
                    break;
                case 16:
                case 17:
                    objArr[1] = "getProject";
                    break;
                case 18:
                    objArr[1] = "getElementType";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/extapi/psi/StubBasedPsiElementBase";
                    break;
            }
        } else {
            objArr[1] = "getStubOrPsiChildren";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 9:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 21:
            case 24:
            case 25:
            case 28:
            case 29:
            case 32:
            case 33:
            case 36:
            case 37:
                break;
            case 6:
            case 7:
                objArr[2] = "failedToBindStubToAst";
                break;
            case 8:
                objArr[2] = "dumpCreationTraces";
                break;
            case 10:
            case 11:
                objArr[2] = "notBoundInExistingAst";
                break;
            case 12:
                objArr[2] = "setNode";
                break;
            case 13:
                objArr[2] = "setSubstrateRef";
                break;
            case 19:
                objArr[2] = "getStubOrPsiChild";
                break;
            case 20:
                objArr[2] = "getRequiredStubOrPsiChild";
                break;
            case 22:
            case 23:
            case 26:
            case 27:
            case 30:
            case 31:
            case 34:
            case 35:
                objArr[2] = "getStubOrPsiChildren";
                break;
            case 38:
                objArr[2] = "getStubOrPsiParentOfType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 9 && i != 21 && i != 24 && i != 25 && i != 28 && i != 29 && i != 32 && i != 33 && i != 36 && i != 37) {
            switch (i) {
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public StubBasedPsiElementBase(T t, IStubElementType<?, ?> iStubElementType) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
        this.mySubstrateRef = new SubstrateRef.StubRef(t);
        this.myElementType = iStubElementType;
    }

    public StubBasedPsiElementBase(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        this.mySubstrateRef = SubstrateRef.createAstStrongRef(aSTNode);
        this.myElementType = aSTNode.getElementType();
    }

    public StubBasedPsiElementBase(T t, IElementType iElementType, ASTNode aSTNode) {
        if (t != null) {
            if (iElementType == null) {
                throw new IllegalArgumentException("null cannot be passed to 'nodeType' when 'stub' is non-null");
            }
            if (aSTNode != null) {
                throw new IllegalArgumentException("null must be passed to 'node' parameter when 'stub' is non-null");
            }
            this.mySubstrateRef = new SubstrateRef.StubRef(t);
            this.myElementType = iElementType;
            return;
        }
        if (aSTNode == null) {
            throw new IllegalArgumentException("'stub' and 'node' parameters cannot be null both");
        }
        if (iElementType != null) {
            throw new IllegalArgumentException("null must be passed to 'nodeType' parameter when 'node' is non-null");
        }
        this.mySubstrateRef = SubstrateRef.createAstStrongRef(aSTNode);
        this.myElementType = aSTNode.getElementType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public ASTNode getNode() {
        if (this.mySubstrateRef instanceof SubstrateRef.StubRef) {
            ApplicationManager.getApplication().assertReadAccessAllowed();
            PsiFileImpl psiFileImpl = (PsiFileImpl) getContainingFile();
            if (!psiFileImpl.isValid()) {
                throw new PsiInvalidElementAccessException(psiFileImpl);
            }
            FileElement treeElement = psiFileImpl.getTreeElement();
            if (treeElement != null && (this.mySubstrateRef instanceof SubstrateRef.StubRef)) {
                ASTNode aSTNodeNotBoundInExistingAst = notBoundInExistingAst(psiFileImpl, treeElement);
                if (aSTNodeNotBoundInExistingAst == null) {
                    $$$reportNull$$$0(3);
                }
                return aSTNodeNotBoundInExistingAst;
            }
            FileElement fileElementCalcTreeElement = psiFileImpl.calcTreeElement();
            if (this.mySubstrateRef instanceof SubstrateRef.StubRef) {
                ASTNode aSTNodeFailedToBindStubToAst = failedToBindStubToAst(psiFileImpl, fileElementCalcTreeElement);
                if (aSTNodeFailedToBindStubToAst == null) {
                    $$$reportNull$$$0(4);
                }
                return aSTNodeFailedToBindStubToAst;
            }
        }
        ASTNode node = this.mySubstrateRef.getNode();
        if (node == null) {
            $$$reportNull$$$0(5);
        }
        return node;
    }

    private ASTNode failedToBindStubToAst(PsiFileImpl psiFileImpl, final FileElement fileElement) {
        if (psiFileImpl == null) {
            $$$reportNull$$$0(6);
        }
        if (fileElement == null) {
            $$$reportNull$$$0(7);
        }
        VirtualFile virtualFile = psiFileImpl.getVirtualFile();
        StubTree stubTree = psiFileImpl.getStubTree();
        String strPrintTree = stubTree != null ? ((PsiFileStubImpl) stubTree.getRoot()).printTree() : null;
        String str = (String) RecursionManager.doPreventingRecursion("failedToBindStubToAst", true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return DebugUtil.treeToString(fileElement, false);
            }
        });
        String str2 = "Failed to bind stub to AST for element " + getClass() + " in " + (virtualFile == null ? "<unknown file>" : virtualFile.getPath()) + "\nFile:\n" + psiFileImpl + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + System.identityHashCode(psiFileImpl);
        String strDumpCreationTraces = ourTraceStubAstBinding ? dumpCreationTraces(fileElement) : null;
        ArrayList arrayList = new ArrayList();
        if (strPrintTree != null) {
            arrayList.add(new Attachment("stubTree.txt", strPrintTree));
        }
        if (str != null) {
            arrayList.add(new Attachment("ast.txt", str));
        }
        if (strDumpCreationTraces != null) {
            arrayList.add(new Attachment("creationTraces.txt", strDumpCreationTraces));
        }
        throw new RuntimeExceptionWithAttachments(str2, (Attachment[]) arrayList.toArray(Attachment.EMPTY_ARRAY));
    }

    private String dumpCreationTraces(FileElement fileElement) {
        if (fileElement == null) {
            $$$reportNull$$$0(8);
        }
        final StringBuilder sb = new StringBuilder("\nNow " + Thread.currentThread() + "\n");
        sb.append("My creation trace:\n").append((String) getUserData(CREATION_TRACE));
        sb.append("AST creation traces:\n");
        fileElement.acceptTree(new RecursiveTreeElementWalkingVisitor(false) { // from class: org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
            public void visitComposite(CompositeElement compositeElement) {
                PsiElement psi = compositeElement.getPsi();
                if (psi != null) {
                    sb.append(psi).append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT).append(System.identityHashCode(psi)).append("\n");
                    String str = (String) psi.getUserData(StubBasedPsiElementBase.CREATION_TRACE);
                    if (str != null) {
                        sb.append(str).append("\n");
                    }
                }
                super.visitComposite(compositeElement);
            }
        });
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(9);
        }
        return string;
    }

    private ASTNode notBoundInExistingAst(PsiFileImpl psiFileImpl, FileElement fileElement) {
        if (psiFileImpl == null) {
            $$$reportNull$$$0(10);
        }
        if (fileElement == null) {
            $$$reportNull$$$0(11);
        }
        String str = "file=" + psiFileImpl + "; tree=" + fileElement;
        PsiElement parentByStub = this;
        while (parentByStub != null) {
            str = str + "\n each of class " + parentByStub.getClass() + "; valid=" + parentByStub.isValid();
            if (parentByStub instanceof StubBasedPsiElementBase) {
                StubBasedPsiElementBase stubBasedPsiElementBase = (StubBasedPsiElementBase) parentByStub;
                str = str + "; ref=" + stubBasedPsiElementBase.mySubstrateRef;
                parentByStub = stubBasedPsiElementBase.getParentByStub();
            } else {
                if (!(parentByStub instanceof PsiFile)) {
                    break;
                }
                str = str + "; same file=" + (parentByStub == psiFileImpl) + "; current tree= " + psiFileImpl.getTreeElement() + "; stubTree=" + psiFileImpl.getStubTree() + "; physical=" + psiFileImpl.getIsPhysical();
                break;
            }
        }
        for (StubElement stub = getStub(); stub != null; stub = stub.getParentStub()) {
            str = str + "\n each stub " + (stub instanceof PsiFileStubImpl ? ((PsiFileStubImpl) stub).getDiagnostics() : stub);
        }
        if (ourTraceStubAstBinding) {
            str = str + dumpCreationTraces(fileElement);
        }
        throw new AssertionError(str);
    }

    public final void setNode(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(12);
        }
        setSubstrateRef(SubstrateRef.createAstStrongRef(aSTNode));
    }

    public final void setSubstrateRef(SubstrateRef substrateRef) {
        if (substrateRef == null) {
            $$$reportNull$$$0(13);
        }
        this.mySubstrateRef = substrateRef;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        Language language = this.myElementType.getLanguage();
        if (language == null) {
            $$$reportNull$$$0(14);
        }
        return language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        try {
            PsiFile containingFile = this.mySubstrateRef.getContainingFile();
            if (containingFile == null) {
                $$$reportNull$$$0(15);
            }
            return containingFile;
        } catch (PsiInvalidElementAccessException e) {
            if (PsiInvalidElementAccessException.getInvalidationTrace(this) != null) {
                throw new PsiInvalidElementAccessException(this, e);
            }
            throw e;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return getContainingFile().isWritable();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        ProgressManager.checkCanceled();
        return this.mySubstrateRef.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManagerEx getManager() {
        Project projectTheOnlyOpenProject = ProjectCoreUtil.theOnlyOpenProject();
        if (projectTheOnlyOpenProject != null) {
            return PsiManagerEx.getInstanceEx(projectTheOnlyOpenProject);
        }
        return (PsiManagerEx) getContainingFile().getManager();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Project getProject() {
        Project projectTheOnlyOpenProject = ProjectCoreUtil.theOnlyOpenProject();
        if (projectTheOnlyOpenProject != null) {
            if (projectTheOnlyOpenProject == null) {
                $$$reportNull$$$0(16);
            }
            return projectTheOnlyOpenProject;
        }
        Project project = getContainingFile().getProject();
        if (project == null) {
            $$$reportNull$$$0(17);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return getContainingFile().getIsPhysical();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        StubElement stub = getStub();
        if (stub != null && !(stub instanceof PsiFileStub)) {
            return stub.getParentStub().getPsi();
        }
        return super.getContext();
    }

    protected final PsiElement getParentByStub() {
        StubElement stub = getStub();
        if (stub != null) {
            return stub.getParentStub().getPsi();
        }
        return SharedImplUtil.getParent(getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getThis$0() {
        Stub greenStub = getGreenStub();
        if (greenStub != null && !((ObjectStubBase) greenStub).isDangling()) {
            return greenStub.getParentStub().getPsi();
        }
        return SourceTreeToPsiMap.treeElementToPsi(getNode().getTreeParent());
    }

    public IStubElementType getElementType() {
        IElementType iElementType = this.myElementType;
        if (!(iElementType instanceof IStubElementType)) {
            throw new ClassCastException("Not a stub type: " + this.myElementType + " in " + getClass());
        }
        IStubElementType iStubElementType = (IStubElementType) iElementType;
        if (iStubElementType == null) {
            $$$reportNull$$$0(18);
        }
        return iStubElementType;
    }

    public T getStub() {
        ProgressIndicatorProvider.checkCanceled();
        return (T) this.mySubstrateRef.getStub();
    }

    public final T getGreenStub() {
        ProgressIndicatorProvider.checkCanceled();
        return (T) this.mySubstrateRef.getGreenStub();
    }

    public <Psi extends PsiElement> Psi getStubOrPsiChild(IStubElementType<? extends StubElement, Psi> iStubElementType) {
        if (iStubElementType == null) {
            $$$reportNull$$$0(19);
        }
        StubElement greenStub = getGreenStub();
        if (greenStub != null) {
            StubElement stubElementFindChildStubByType = greenStub.findChildStubByType(iStubElementType);
            if (stubElementFindChildStubByType != null) {
                return (Psi) stubElementFindChildStubByType.getPsi();
            }
            return null;
        }
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(iStubElementType);
        if (aSTNodeFindChildByType != null) {
            return (Psi) aSTNodeFindChildByType.getPsi();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <S extends StubElement<?>, Psi extends PsiElement> Psi getRequiredStubOrPsiChild(IStubElementType<S, Psi> iStubElementType) {
        if (iStubElementType == 0) {
            $$$reportNull$$$0(20);
        }
        Psi psi = (Psi) getStubOrPsiChild(iStubElementType);
        if (psi == null) {
            throw new AssertionError("Missing required child of type " + iStubElementType + "; tree: " + DebugUtil.psiToString(this, true));
        }
        if (psi == null) {
            $$$reportNull$$$0(21);
        }
        return psi;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <S extends StubElement<?>, Psi extends PsiElement> Psi[] getStubOrPsiChildren(IStubElementType<S, ? extends Psi> iStubElementType, Psi[] psiArr) {
        if (iStubElementType == null) {
            $$$reportNull$$$0(22);
        }
        if (psiArr == null) {
            $$$reportNull$$$0(23);
        }
        StubElement greenStub = getGreenStub();
        if (greenStub != null) {
            Psi[] psiArr2 = (Psi[]) greenStub.getChildrenByType(iStubElementType, psiArr);
            if (psiArr2 == null) {
                $$$reportNull$$$0(24);
            }
            return psiArr2;
        }
        ASTNode[] childrenOfType = SharedImplUtil.getChildrenOfType(getNode(), iStubElementType);
        Psi[] psiArr3 = (Psi[]) ((PsiElement[]) ArrayUtil.newArray(ArrayUtil.getComponentType(psiArr), childrenOfType.length));
        for (int i = 0; i < childrenOfType.length; i++) {
            psiArr3[i] = childrenOfType[i].getPsi();
        }
        if (psiArr3 == 0) {
            $$$reportNull$$$0(25);
        }
        return psiArr3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <S extends StubElement<?>, Psi extends PsiElement> Psi[] getStubOrPsiChildren(IStubElementType<S, ? extends Psi> iStubElementType, ArrayFactory<? extends Psi> arrayFactory) {
        if (iStubElementType == null) {
            $$$reportNull$$$0(26);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(27);
        }
        StubElement greenStub = getGreenStub();
        if (greenStub != null) {
            Psi[] psiArr = (Psi[]) greenStub.getChildrenByType(iStubElementType, arrayFactory);
            if (psiArr == null) {
                $$$reportNull$$$0(28);
            }
            return psiArr;
        }
        ASTNode[] childrenOfType = SharedImplUtil.getChildrenOfType(getNode(), iStubElementType);
        Psi[] psiArr2 = (Psi[]) ((PsiElement[]) arrayFactory.create(childrenOfType.length));
        for (int i = 0; i < childrenOfType.length; i++) {
            psiArr2[i] = childrenOfType[i].getPsi();
        }
        if (psiArr2 == 0) {
            $$$reportNull$$$0(29);
        }
        return psiArr2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <Psi extends PsiElement> Psi[] getStubOrPsiChildren(TokenSet tokenSet, Psi[] psiArr) {
        if (tokenSet == null) {
            $$$reportNull$$$0(30);
        }
        if (psiArr == null) {
            $$$reportNull$$$0(31);
        }
        StubElement greenStub = getGreenStub();
        if (greenStub != null) {
            Psi[] psiArr2 = (Psi[]) greenStub.getChildrenByType(tokenSet, psiArr);
            if (psiArr2 == null) {
                $$$reportNull$$$0(32);
            }
            return psiArr2;
        }
        ASTNode[] children = getNode().getChildren(tokenSet);
        Psi[] psiArr3 = (Psi[]) ((PsiElement[]) ArrayUtil.newArray(ArrayUtil.getComponentType(psiArr), children.length));
        for (int i = 0; i < children.length; i++) {
            psiArr3[i] = children[i].getPsi();
        }
        if (psiArr3 == 0) {
            $$$reportNull$$$0(33);
        }
        return psiArr3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <Psi extends PsiElement> Psi[] getStubOrPsiChildren(TokenSet tokenSet, ArrayFactory<? extends Psi> arrayFactory) {
        if (tokenSet == null) {
            $$$reportNull$$$0(34);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(35);
        }
        StubElement greenStub = getGreenStub();
        if (greenStub != null) {
            Psi[] psiArr = (Psi[]) greenStub.getChildrenByType(tokenSet, arrayFactory);
            if (psiArr == null) {
                $$$reportNull$$$0(36);
            }
            return psiArr;
        }
        ASTNode[] children = getNode().getChildren(tokenSet);
        Psi[] psiArr2 = (Psi[]) ((PsiElement[]) arrayFactory.create(children.length));
        for (int i = 0; i < children.length; i++) {
            psiArr2[i] = children[i].getPsi();
        }
        if (psiArr2 == 0) {
            $$$reportNull$$$0(37);
        }
        return psiArr2;
    }

    protected <E extends PsiElement> E getStubOrPsiParentOfType(Class<E> cls) {
        if (cls == null) {
            $$$reportNull$$$0(38);
        }
        StubElement stub = getStub();
        if (stub != null) {
            return (E) stub.getParentStubOfType(cls);
        }
        return (E) PsiTreeUtil.getParentOfType(this, cls);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    protected Object clone() {
        StubBasedPsiElementBase stubBasedPsiElementBase = (StubBasedPsiElementBase) super.clone();
        stubBasedPsiElementBase.setSubstrateRef(SubstrateRef.createAstStrongRef(getNode()));
        return stubBasedPsiElementBase;
    }
}
