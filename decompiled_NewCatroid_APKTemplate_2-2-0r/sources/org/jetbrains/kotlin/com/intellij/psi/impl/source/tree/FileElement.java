package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FCTSBackedLighterAST;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.TreeBackedLighterAST;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.StackOverflowPreventedException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.StubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.CharTableImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILightStubFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class FileElement extends LazyParseableElement implements FileASTNode {
    public static final FileElement[] EMPTY_ARRAY = new FileElement[0];
    private volatile CharTable myCharTable;
    private volatile boolean myDetached;
    private volatile AstSpine myStubbedSpine;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/FileElement";
        } else {
            objArr[0] = "table";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/FileElement";
        } else if (i == 3 || i == 4) {
            objArr[1] = "getStubbedSpine";
        } else {
            objArr[1] = "getCharTable";
        }
        if (i == 1) {
            objArr[2] = "<init>";
        } else if (i == 2) {
            objArr[2] = "setCharTable";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    protected PsiElement createPsiNoLock() {
        if (this.myDetached) {
            return null;
        }
        return super.createPsiNoLock();
    }

    public void detachFromFile() {
        this.myDetached = true;
        clearPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.FileASTNode
    public CharTable getCharTable() {
        CharTable charTable = this.myCharTable;
        if (charTable == null) {
            $$$reportNull$$$0(0);
        }
        return charTable;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.FileASTNode
    public LighterAST getLighterAST() {
        IElementType elementType = getElementType();
        if (!isParsed() && (elementType instanceof ILightStubFileElementType)) {
            return new FCTSBackedLighterAST(getCharTable(), ((ILightStubFileElementType) elementType).parseContentsLight(this));
        }
        return new TreeBackedLighterAST(this);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileElement(IElementType iElementType, CharSequence charSequence) {
        super(iElementType, charSequence);
        if (iElementType == null) {
            $$$reportNull$$$0(1);
        }
        this.myCharTable = new CharTableImpl();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public PsiManagerEx getManager() {
        CompositeElement treeParent = getTreeParent();
        if (treeParent != null) {
            return treeParent.getManager();
        }
        PsiElement psi = getPsi();
        if (psi == null) {
            throw PsiInvalidElementAccessException.createByNode(this, null);
        }
        return (PsiManagerEx) psi.getManager();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public ASTNode copyElement() {
        return ((PsiFileImpl) ((PsiFileImpl) getPsi()).copy()).getTreeElement();
    }

    public void setCharTable(CharTable charTable) {
        if (charTable == null) {
            $$$reportNull$$$0(2);
        }
        this.myCharTable = charTable;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        this.myStubbedSpine = null;
    }

    public final AstSpine getStubbedSpine() {
        AstSpine astSpine = this.myStubbedSpine;
        if (astSpine == null) {
            PsiFileImpl psiFileImpl = (PsiFileImpl) getPsi();
            final IStubFileElementType<?> elementTypeForStubBuilder = psiFileImpl.getElementTypeForStubBuilder();
            if (elementTypeForStubBuilder == null) {
                AstSpine astSpine2 = AstSpine.EMPTY_SPINE;
                if (astSpine2 == null) {
                    $$$reportNull$$$0(3);
                }
                return astSpine2;
            }
            astSpine = (AstSpine) RecursionManager.doPreventingRecursion(psiFileImpl, false, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final Object compute() {
                    return this.f$0.m6674x81b585ea(elementTypeForStubBuilder);
                }
            });
            if (astSpine == null) {
                throw new StackOverflowPreventedException("Endless recursion prevented");
            }
            this.myStubbedSpine = astSpine;
        }
        if (astSpine == null) {
            $$$reportNull$$$0(4);
        }
        return astSpine;
    }

    /* JADX INFO: renamed from: lambda$getStubbedSpine$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-FileElement, reason: not valid java name */
    /* synthetic */ AstSpine m6674x81b585ea(IStubFileElementType iStubFileElementType) {
        return new AstSpine(calcStubbedDescendants(iStubFileElementType.getBuilder()));
    }

    private List<CompositeElement> calcStubbedDescendants(final StubBuilder stubBuilder) {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(this);
        acceptTree(new RecursiveTreeElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
            public void visitComposite(CompositeElement compositeElement) {
                CompositeElement treeParent = compositeElement.getTreeParent();
                if (treeParent == null || !stubBuilder.skipChildProcessingWhenBuildingStubs(treeParent, compositeElement)) {
                    IElementType elementType = compositeElement.getElementType();
                    if ((elementType instanceof IStubElementType) && ((IStubElementType) elementType).shouldCreateStub(compositeElement)) {
                        arrayList.add(compositeElement);
                    }
                    super.visitNode(compositeElement);
                }
            }
        });
        return arrayList;
    }
}
