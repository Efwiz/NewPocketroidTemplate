package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.diagnostic.ThreadDumper;
import org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.TreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeEventImpl;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.FreeThreadedFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes3.dex */
public class CompositeElement extends TreeElement {
    private TreeElement firstChild;
    private TreeElement lastChild;
    private volatile int myCachedLength;
    private volatile int myHC;
    private volatile PsiElement myWrapper;
    private static final Logger LOG = Logger.getInstance((Class<?>) CompositeElement.class);
    public static final CompositeElement[] EMPTY_ARRAY = new CompositeElement[0];
    private static final AtomicReferenceFieldUpdater<CompositeElement, PsiElement> myWrapperUpdater = AtomicReferenceFieldUpdater.newUpdater(CompositeElement.class, PsiElement.class, "myWrapper");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 31 || i == 52 || i == 12 || i == 13 || i == 17 || i == 18 || i == 20 || i == 21 || i == 24 || i == 25) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 31 || i == 52 || i == 12 || i == 13 || i == 17 || i == 18 || i == 20 || i == 21 || i == 24 || i == 25) ? 2 : 3];
        switch (i) {
            case 1:
            case 12:
            case 13:
            case 17:
            case 18:
            case 20:
            case 21:
            case 24:
            case 25:
            case 31:
            case 52:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/CompositeElement";
                break;
            case 2:
                objArr[0] = "file";
                break;
            case 3:
            case 4:
            case 46:
                objArr[0] = "psiFile";
                break;
            case 5:
                objArr[0] = "visitor";
                break;
            case 6:
            case 8:
            case 9:
            case 22:
            case 26:
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 7:
            case 10:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 11:
                objArr[0] = "typesSet";
                break;
            case 14:
                objArr[0] = "buffer";
                break;
            case 15:
            case 16:
            case 27:
            case 28:
            case 32:
            case 35:
            case 36:
            case 50:
            case 64:
                objArr[0] = "child";
                break;
            case 19:
            case 23:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 29:
                objArr[0] = "newElement";
                break;
            case 30:
                objArr[0] = "start";
                break;
            case 33:
                objArr[0] = "leafType";
                break;
            case 34:
                objArr[0] = "leafText";
                break;
            case 37:
            case 48:
            case 49:
            case 56:
            case 60:
            case 65:
                objArr[0] = "first";
                break;
            case 38:
            case 45:
            case 62:
                objArr[0] = "oldChild";
                break;
            case 39:
            case 44:
            case 63:
                objArr[0] = "newChild";
                break;
            case 40:
                objArr[0] = "anotherParent";
                break;
            case 41:
                objArr[0] = "firstChild";
                break;
            case 42:
                objArr[0] = "clazz";
                break;
            case 43:
                objArr[0] = "psi";
                break;
            case 47:
                objArr[0] = "newNode";
                break;
            case 51:
            case 55:
                objArr[0] = "parent";
                break;
            case 53:
                objArr[0] = "oldParent";
                break;
            case 54:
            case 57:
            case 58:
                objArr[0] = "destinationTreeChange";
                break;
            case 59:
                objArr[0] = "anchorBefore";
                break;
            case 61:
                objArr[0] = "sourceTreeChange";
                break;
        }
        if (i == 1) {
            objArr[1] = "clone";
        } else if (i == 31) {
            objArr[1] = "drillDown";
        } else if (i == 52) {
            objArr[1] = "rawSetParents";
        } else if (i == 12) {
            objArr[1] = "getChars";
        } else if (i == 13) {
            objArr[1] = "textToCharArray";
        } else if (i == 17 || i == 18) {
            objArr[1] = "getChildren";
        } else if (i == 20 || i == 21 || i == 24 || i == 25) {
            objArr[1] = "getChildrenAsPsiElements";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/CompositeElement";
        }
        switch (i) {
            case 1:
            case 12:
            case 13:
            case 17:
            case 18:
            case 20:
            case 21:
            case 24:
            case 25:
            case 31:
            case 52:
                break;
            case 2:
                objArr[2] = "assertThreading";
                break;
            case 3:
                objArr[2] = "getThreadingDiagnostics";
                break;
            case 4:
                objArr[2] = "isNonPhysicalOrInjected";
                break;
            case 5:
                objArr[2] = "acceptTree";
                break;
            case 6:
            case 7:
                objArr[2] = "findPsiChildByType";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "findChildByType";
                break;
            case 14:
                objArr[2] = "textMatches";
                break;
            case 15:
            case 16:
                objArr[2] = "getChildRole";
                break;
            case 19:
            case 22:
            case 23:
                objArr[2] = "getChildrenAsPsiElements";
                break;
            case 26:
                objArr[2] = "countChildren";
                break;
            case 27:
                objArr[2] = "deleteChildInternal";
                break;
            case 28:
            case 29:
                objArr[2] = "replaceChildInternal";
                break;
            case 30:
                objArr[2] = "drillDown";
                break;
            case 32:
            case 35:
                objArr[2] = "addChild";
                break;
            case 33:
            case 34:
                objArr[2] = "addLeaf";
                break;
            case 36:
                objArr[2] = "removeChild";
                break;
            case 37:
                objArr[2] = "removeRange";
                break;
            case 38:
            case 39:
                objArr[2] = "replaceChild";
                break;
            case 40:
                objArr[2] = "replaceAllChildrenToChildrenOf";
                break;
            case 41:
                objArr[2] = "addChildren";
                break;
            case 42:
                objArr[2] = "getPsi";
                break;
            case 43:
                objArr[2] = "setPsi";
                break;
            case 44:
                objArr[2] = "applyInsertOnReparse";
                break;
            case 45:
                objArr[2] = "applyDeleteOnReparse";
                break;
            case 46:
            case 47:
                objArr[2] = "applyReplaceFileOnReparse";
                break;
            case 48:
                objArr[2] = "rawAddChildren";
                break;
            case 49:
                objArr[2] = "rawAddChildrenWithoutNotifications";
                break;
            case 50:
            case 51:
                objArr[2] = "rawSetParents";
                break;
            case 53:
                objArr[2] = "repairRemovedElement";
                break;
            case 54:
            case 55:
            case 56:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 57:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            case 58:
            case 59:
            case 60:
                objArr[2] = "insertBefore";
                break;
            case 61:
            case 62:
            case 63:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 64:
                objArr[2] = "removeChildInner";
                break;
            case 65:
                objArr[2] = "removeChildrenInner";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 31 && i != 52 && i != 12 && i != 13 && i != 17 && i != 18 && i != 20 && i != 21 && i != 24 && i != 25) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompositeElement(IElementType iElementType) {
        super(iElementType);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        this.myCachedLength = -1;
        this.myHC = -1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public CompositeElement clone() {
        CompositeElement compositeElement = (CompositeElement) super.clone();
        compositeElement.firstChild = null;
        compositeElement.lastChild = null;
        compositeElement.myWrapper = null;
        for (ASTNode aSTNodeRawFirstChild = rawFirstChild(); aSTNodeRawFirstChild != null; aSTNodeRawFirstChild = aSTNodeRawFirstChild.getTreeNext()) {
            compositeElement.rawAddChildrenWithoutNotifications((TreeElement) aSTNodeRawFirstChild.clone());
        }
        compositeElement.clearCaches();
        if (compositeElement == null) {
            $$$reportNull$$$0(1);
        }
        return compositeElement;
    }

    public void subtreeChanged() {
        for (CompositeElement treeParent = this; treeParent != null; treeParent = treeParent.getTreeParent()) {
            treeParent.clearCaches();
            if (!(treeParent instanceof PsiElement)) {
                PsiElement psiElement = treeParent.myWrapper;
                if (psiElement instanceof ASTDelegatePsiElement) {
                    ((ASTDelegatePsiElement) psiElement).subtreeChanged();
                } else if (psiElement instanceof PsiFile) {
                    PsiFile psiFile = (PsiFile) psiElement;
                    psiFile.subtreeChanged();
                    assertThreading(psiFile);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        this.myCachedLength = -1;
        this.myHC = -1;
        clearRelativeOffsets(rawFirstChild());
    }

    private static void assertThreading(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        if (ApplicationManager.getApplication().isWriteAccessAllowed() || isNonPhysicalOrInjected(psiFile)) {
            return;
        }
        LOG.error("Threading assertion. " + getThreadingDiagnostics(psiFile));
    }

    private static String getThreadingDiagnostics(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(3);
        }
        return "psiFile: " + psiFile + "; psiFile.getViewProvider(): " + psiFile.getViewProvider() + "; psiFile.isPhysical(): " + psiFile.isPhysical() + "; nonPhysicalOrInjected: " + isNonPhysicalOrInjected(psiFile);
    }

    private static boolean isNonPhysicalOrInjected(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(4);
        }
        return (psiFile instanceof DummyHolder) || (psiFile.getViewProvider() instanceof FreeThreadedFileViewProvider) || !psiFile.isPhysical();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void acceptTree(TreeElementVisitor treeElementVisitor) {
        if (treeElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        treeElementVisitor.visitComposite(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public LeafElement findLeafElementAt(int i) {
        if (getTreeParent() == null && i >= getTextLength()) {
            return null;
        }
        TreeElement treeNext = this;
        while (true) {
            TreeElement firstChildNode = treeNext.getFirstChildNode();
            TreeElement lastChildNode = treeNext.getLastChildNode();
            int textLength = treeNext.getTextLength();
            boolean z = lastChildNode == null || textLength / 2 > i;
            if (z) {
                treeNext = firstChildNode;
            } else {
                i = textLength - i;
                treeNext = lastChildNode;
            }
            while (treeNext != null) {
                int textLength2 = treeNext.getTextLength();
                if (textLength2 > i || (!z && textLength2 >= i)) {
                    if (treeNext instanceof LeafElement) {
                        if (treeNext instanceof ForeignLeafPsiElement) {
                            treeNext = z ? treeNext.getTreeNext() : treeNext.getTreePrev();
                        } else {
                            return (LeafElement) treeNext;
                        }
                    } else if (!z) {
                        i = textLength2 - i;
                    }
                } else {
                    i -= textLength2;
                    treeNext = z ? treeNext.getTreeNext() : treeNext.getTreePrev();
                }
            }
            return null;
        }
    }

    public PsiElement findPsiChildByType(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(6);
        }
        ASTNode aSTNodeFindChildByType = findChildByType(iElementType);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return aSTNodeFindChildByType.getPsi();
    }

    public PsiElement findPsiChildByType(TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(7);
        }
        ASTNode aSTNodeFindChildByType = findChildByType(tokenSet);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return aSTNodeFindChildByType.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public ASTNode findChildByType(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(8);
        }
        if (DebugUtil.CHECK_INSIDE_ATOMIC_ACTION_ENABLED) {
            assertReadAccessAllowed();
        }
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getElementType() == iElementType) {
                return firstChildNode;
            }
        }
        return null;
    }

    public ASTNode findChildByType(IElementType iElementType, ASTNode aSTNode) {
        if (iElementType == null) {
            $$$reportNull$$$0(9);
        }
        if (DebugUtil.CHECK_INSIDE_ATOMIC_ACTION_ENABLED) {
            assertReadAccessAllowed();
        }
        return TreeUtil.findSibling(aSTNode, iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public ASTNode findChildByType(TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(10);
        }
        if (DebugUtil.CHECK_INSIDE_ATOMIC_ACTION_ENABLED) {
            assertReadAccessAllowed();
        }
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (tokenSet.contains(firstChildNode.getElementType())) {
                return firstChildNode;
            }
        }
        return null;
    }

    public ASTNode findChildByType(TokenSet tokenSet, ASTNode aSTNode) {
        if (tokenSet == null) {
            $$$reportNull$$$0(11);
        }
        if (DebugUtil.CHECK_INSIDE_ATOMIC_ACTION_ENABLED) {
            assertReadAccessAllowed();
        }
        return TreeUtil.findSibling(aSTNode, tokenSet);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public String getText() {
        return new String(textToCharArray());
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public CharSequence getChars() {
        String text = getText();
        if (text == null) {
            $$$reportNull$$$0(12);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public char[] textToCharArray() {
        String str;
        assertReadAccessAllowed();
        int textLength = getTextLength();
        char[] cArr = new char[textLength];
        try {
            int buffer = AstBufferUtil.toBuffer(this, cArr, 0);
            if (buffer == textLength) {
                return cArr;
            }
            throw new AssertionError(("len=" + textLength + ";\n endOffset=" + buffer) + diagnoseTextInconsistency(new String(cArr, 0, Math.min(textLength, buffer))));
        } catch (ArrayIndexOutOfBoundsException e) {
            String str2 = "Underestimated text length: " + textLength;
            try {
                str = str2 + ";\n repetition gives success (" + AstBufferUtil.toBuffer(this, new char[textLength], 0) + Constant.AFTER_QUTO;
            } catch (ArrayIndexOutOfBoundsException unused) {
                str = str2 + ";\n repetition fails as well";
            }
            throw new RuntimeException(str, e);
        }
    }

    private String diagnoseTextInconsistency(String str) {
        String str2;
        PsiFile containingFile;
        String str3 = (";\n nonPhysicalOrInjected=" + isNonPhysicalOrInjected(SharedImplUtil.getContainingFile(this))) + ";\n buffer=" + str;
        try {
            str2 = str3 + ";\n this=" + this;
        } catch (StackOverflowError unused) {
            str2 = str3 + ";\n this.toString produces SOE";
        }
        int iTextMatches = textMatches(str, 0);
        String str4 = str2 + ";\n matches until " + iTextMatches;
        LeafElement leafElementFindLeafElementAt = findLeafElementAt(Math.abs(iTextMatches));
        String str5 = str4 + ";\n element there=" + leafElementFindLeafElementAt;
        if (leafElementFindLeafElementAt != null) {
            PsiElement psi = leafElementFindLeafElementAt.getPsi();
            str5 = (((str5 + ";\n leaf.text=" + leafElementFindLeafElementAt.getText()) + ";\n leaf.psi=" + psi) + ";\n leaf.lang=" + (psi == null ? null : psi.getLanguage())) + ";\n leaf.type=" + leafElementFindLeafElementAt.getElementType();
        }
        PsiElement psi2 = getPsi();
        if (psi2 == null) {
            return str5;
        }
        boolean zIsValid = psi2.isValid();
        String str6 = str5 + ";\n psi.valid=" + zIsValid;
        if (!zIsValid || (containingFile = psi2.getContainingFile()) == null) {
            return str6;
        }
        return ((((((str6 + ";\n psi.file=" + containingFile) + ";\n psi.file.tl=" + containingFile.getTextLength()) + ";\n psi.file.lang=" + containingFile.getLanguage()) + ";\n psi.file.vp=" + containingFile.getViewProvider()) + ";\n psi.file.vp.lang=" + containingFile.getViewProvider().getLanguages()) + ";\n psi.file.vp.lang=" + containingFile.getViewProvider().getLanguages()) + ";\n leaves at start=" + containingFile.findElementAt(getTextRange().getStartOffset()) + " and " + findLeafElementAt(0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public boolean textContains(char c) {
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.textContains(c)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    protected int textMatches(final CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(14);
        }
        final int[] iArr = {i};
        acceptTree(new RecursiveTreeElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
            public void visitLeaf(LeafElement leafElement) {
                matchText(leafElement);
            }

            private void matchText(TreeElement treeElement) {
                int[] iArr2 = iArr;
                iArr2[0] = treeElement.textMatches(charSequence, iArr2[0]);
                if (iArr[0] < 0) {
                    stopWalking();
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
            public void visitComposite(CompositeElement compositeElement) {
                if ((compositeElement instanceof LazyParseableElement) && !((LazyParseableElement) compositeElement).isParsed()) {
                    matchText(compositeElement);
                } else {
                    super.visitComposite(compositeElement);
                }
            }
        });
        return iArr[0];
    }

    public final PsiElement findChildByRoleAsPsiElement(int i) {
        ASTNode aSTNodeFindChildByRole = findChildByRole(i);
        if (aSTNodeFindChildByRole == null) {
            return null;
        }
        return SourceTreeToPsiMap.treeElementToPsi(aSTNodeFindChildByRole);
    }

    public ASTNode findChildByRole(int i) {
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (getChildRole(firstChildNode) == i) {
                return firstChildNode;
            }
        }
        return null;
    }

    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(15);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this, aSTNode);
        return 0;
    }

    protected final int getChildRole(ASTNode aSTNode, int i) {
        if (aSTNode == null) {
            $$$reportNull$$$0(16);
        }
        if (findChildByRole(i) == aSTNode) {
            return i;
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public ASTNode[] getChildren(TokenSet tokenSet) {
        int iCountChildren = countChildren(tokenSet);
        if (iCountChildren == 0) {
            CompositeElement[] compositeElementArr = EMPTY_ARRAY;
            if (compositeElementArr == null) {
                $$$reportNull$$$0(17);
            }
            return compositeElementArr;
        }
        ASTNode[] aSTNodeArr = new ASTNode[iCountChildren];
        int i = 0;
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (tokenSet == null || tokenSet.contains(firstChildNode.getElementType())) {
                aSTNodeArr[i] = firstChildNode;
                i++;
            }
        }
        return aSTNodeArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends PsiElement> T[] getChildrenAsPsiElements(TokenSet tokenSet, ArrayFactory<? extends T> arrayFactory) {
        if (arrayFactory == null) {
            $$$reportNull$$$0(19);
        }
        assertReadAccessAllowed();
        int iCountChildren = countChildren(tokenSet);
        T[] tArr = (T[]) ((PsiElement[]) arrayFactory.create(iCountChildren));
        if (iCountChildren == 0) {
            if (tArr == 0) {
                $$$reportNull$$$0(20);
            }
            return tArr;
        }
        int i = 0;
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null && i < iCountChildren; firstChildNode = firstChildNode.getTreeNext()) {
            if (tokenSet == null || tokenSet.contains(firstChildNode.getElementType())) {
                PsiElement psi = firstChildNode.getPsi();
                LOG.assertTrue(psi != null, firstChildNode);
                tArr[i] = psi;
                i++;
            }
        }
        if (tArr == 0) {
            $$$reportNull$$$0(21);
        }
        return tArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends PsiElement> T[] getChildrenAsPsiElements(IElementType iElementType, ArrayFactory<? extends T> arrayFactory) {
        if (iElementType == null) {
            $$$reportNull$$$0(22);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(23);
        }
        assertReadAccessAllowed();
        int iCountChildren = countChildren(iElementType);
        T[] tArr = (T[]) ((PsiElement[]) arrayFactory.create(iCountChildren));
        if (iCountChildren == 0) {
            if (tArr == 0) {
                $$$reportNull$$$0(24);
            }
            return tArr;
        }
        int i = 0;
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null && i < iCountChildren; firstChildNode = firstChildNode.getTreeNext()) {
            if (iElementType == firstChildNode.getElementType()) {
                PsiElement psi = firstChildNode.getPsi();
                LOG.assertTrue(psi != null, firstChildNode);
                tArr[i] = psi;
                i++;
            }
        }
        if (tArr == 0) {
            $$$reportNull$$$0(25);
        }
        return tArr;
    }

    public int countChildren(TokenSet tokenSet) {
        int i = 0;
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (tokenSet == null || tokenSet.contains(firstChildNode.getElementType())) {
                i++;
            }
        }
        return i;
    }

    private int countChildren(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(26);
        }
        int i = 0;
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (iElementType == firstChildNode.getElementType()) {
                i++;
            }
        }
        return i;
    }

    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        if (aSTNode2 == null) {
            aSTNode2 = (bool == null || bool.booleanValue()) ? null : getFirstChildNode();
        } else if (!bool.booleanValue()) {
            aSTNode2 = aSTNode2.getTreeNext();
        }
        return (TreeElement) CodeEditUtil.addChildren(this, treeElement, aSTNode, aSTNode2);
    }

    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(27);
        }
        CodeEditUtil.removeChild(this, aSTNode);
    }

    public void replaceChildInternal(ASTNode aSTNode, TreeElement treeElement) {
        if (aSTNode == null) {
            $$$reportNull$$$0(28);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(29);
        }
        CodeEditUtil.replaceChild(this, aSTNode, treeElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public int getTextLength() {
        int i = this.myCachedLength;
        if (i >= 0) {
            return i;
        }
        assertReadAccessAllowed();
        try {
            return walkCachingLength();
        } catch (AssertionError e) {
            this.myCachedLength = -1;
            throw new AssertionError("Walking failure: ===\n" + StringUtil.getThrowableText(e) + "\n=== Thread dump:\n" + ThreadDumper.dumpThreadsToString() + "\n===\n");
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public int hc() {
        int i = this.myHC;
        if (i != -1) {
            return i;
        }
        int iHc = 0;
        for (TreeElement treeNext = this.firstChild; treeNext != null; treeNext = treeNext.getTreeNext()) {
            iHc += treeNext.hc();
        }
        this.myHC = iHc;
        return iHc;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public int getCachedLength() {
        return this.myCachedLength;
    }

    private static TreeElement drillDown(TreeElement treeElement) {
        TreeElement firstChildNode;
        if (treeElement == null) {
            $$$reportNull$$$0(30);
        }
        while (treeElement.getCachedLength() < 0 && (firstChildNode = treeElement.getFirstChildNode()) != null) {
            treeElement = firstChildNode;
        }
        if (treeElement == null) {
            $$$reportNull$$$0(31);
        }
        return treeElement;
    }

    private int walkCachingLength() {
        TreeElement treeElementDrillDown = drillDown(this);
        while (true) {
            int cachedLength = treeElementDrillDown.getCachedLength();
            if (cachedLength < 0) {
                int textLength = 0;
                for (TreeElement firstChildNode = treeElementDrillDown.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
                    textLength += firstChildNode.getTextLength();
                }
                ((CompositeElement) treeElementDrillDown).setCachedLength(textLength);
                cachedLength = textLength;
            }
            if (treeElementDrillDown == this) {
                return cachedLength;
            }
            TreeElement treeNext = treeElementDrillDown.getTreeNext();
            treeElementDrillDown = treeNext != null ? drillDown(treeNext) : getNotNullParent(treeElementDrillDown);
        }
    }

    private static TreeElement getNotNullParent(TreeElement treeElement) {
        CompositeElement treeParent = treeElement.getTreeParent();
        if (treeParent == null) {
            diagnoseNullParent(treeElement);
        }
        return treeParent;
    }

    private static void diagnoseNullParent(TreeElement treeElement) {
        PsiElement psi = treeElement.getPsi();
        if (psi != null) {
            PsiUtilCore.ensureValid(psi);
        }
        throw new IllegalStateException("Null parent of " + treeElement + " " + treeElement.getClass());
    }

    void setCachedLength(int i) {
        this.myCachedLength = i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public TreeElement getFirstChildNode() {
        return this.firstChild;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public TreeElement getLastChildNode() {
        return this.lastChild;
    }

    void setFirstChildNode(TreeElement treeElement) {
        this.firstChild = treeElement;
        clearRelativeOffsets(treeElement);
    }

    void setLastChildNode(TreeElement treeElement) {
        this.lastChild = treeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void addChild(ASTNode aSTNode, final ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(32);
        }
        LOG.assertTrue(aSTNode2 == null || ((TreeElement) aSTNode2).getTreeParent() == this, "anchorBefore == null || anchorBefore.getTreeParent() == parent");
        TreeUtil.ensureParsed(getFirstChildNode());
        TreeUtil.ensureParsed(aSTNode);
        final TreeElement treeElement = (TreeElement) aSTNode;
        removeChildrenInner(treeElement, treeElement.getTreeNext());
        ChangeUtil.prepareAndRunChangeAction(new ChangeUtil.ChangeAction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil.ChangeAction
            public final void makeChange(TreeChangeEvent treeChangeEvent) {
                this.f$0.m6671x8020cc7a(aSTNode2, treeElement, treeChangeEvent);
            }
        }, this);
    }

    /* JADX INFO: renamed from: lambda$addChild$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-CompositeElement, reason: not valid java name */
    /* synthetic */ void m6671x8020cc7a(ASTNode aSTNode, TreeElement treeElement, TreeChangeEvent treeChangeEvent) {
        if (aSTNode != null) {
            insertBefore((TreeChangeEventImpl) treeChangeEvent, (TreeElement) aSTNode, treeElement);
        } else {
            add((TreeChangeEventImpl) treeChangeEvent, this, treeElement);
        }
    }

    public void addLeaf(IElementType iElementType, CharSequence charSequence, ASTNode aSTNode) {
        if (iElementType == null) {
            $$$reportNull$$$0(33);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(34);
        }
        FileElement treeElement = new DummyHolder(getManager(), null).getTreeElement();
        LeafElement leafElementLeaf = ASTFactory.leaf(iElementType, treeElement.getCharTable().intern(charSequence));
        CodeEditUtil.setNodeGenerated(leafElementLeaf, true);
        treeElement.rawAddChildren(leafElementLeaf);
        addChild(leafElementLeaf, aSTNode);
    }

    public void addChild(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(35);
        }
        addChild(aSTNode, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void removeChild(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(36);
        }
        removeChildInner((TreeElement) aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void removeRange(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(37);
        }
        removeChildrenInner((TreeElement) aSTNode, (TreeElement) aSTNode2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void replaceChild(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(38);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(39);
        }
        final TreeElement treeElement = (TreeElement) aSTNode;
        LOG.assertTrue(treeElement.getTreeParent() == this);
        final TreeElement treeElement2 = (TreeElement) aSTNode2;
        TreeElement treeNext = treeElement2.getTreeNext();
        if (treeElement == treeElement2) {
            return;
        }
        removeChildrenInner(treeElement2, treeNext);
        ChangeUtil.prepareAndRunChangeAction(new ChangeUtil.ChangeAction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil.ChangeAction
            public final void makeChange(TreeChangeEvent treeChangeEvent) {
                this.f$0.m6673x38e88288(treeElement, treeElement2, treeChangeEvent);
            }
        }, this);
    }

    /* JADX INFO: renamed from: lambda$replaceChild$1$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-CompositeElement, reason: not valid java name */
    /* synthetic */ void m6673x38e88288(TreeElement treeElement, TreeElement treeElement2, TreeChangeEvent treeChangeEvent) {
        replace((TreeChangeEventImpl) treeChangeEvent, treeElement, treeElement2);
        repairRemovedElement(this, treeElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void replaceAllChildrenToChildrenOf(final ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(40);
        }
        TreeUtil.ensureParsed(getFirstChildNode());
        TreeUtil.ensureParsed(aSTNode.getFirstChildNode());
        final ASTNode firstChildNode = aSTNode.getFirstChildNode();
        ChangeUtil.prepareAndRunChangeAction(new ChangeUtil.ChangeAction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil.ChangeAction
            public final void makeChange(TreeChangeEvent treeChangeEvent) {
                CompositeElement.remove((TreeChangeEventImpl) treeChangeEvent, (TreeElement) aSTNode.getFirstChildNode(), null);
            }
        }, (TreeElement) aSTNode);
        if (firstChildNode != null) {
            ChangeUtil.prepareAndRunChangeAction(new ChangeUtil.ChangeAction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil.ChangeAction
                public final void makeChange(TreeChangeEvent treeChangeEvent) {
                    this.f$0.m6672x242284df(firstChildNode, treeChangeEvent);
                }
            }, this);
        } else {
            removeAllChildren();
        }
    }

    /* JADX INFO: renamed from: lambda$replaceAllChildrenToChildrenOf$3$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-CompositeElement, reason: not valid java name */
    /* synthetic */ void m6672x242284df(ASTNode aSTNode, TreeChangeEvent treeChangeEvent) {
        TreeElement firstChildNode = getFirstChildNode();
        TreeChangeEventImpl treeChangeEventImpl = (TreeChangeEventImpl) treeChangeEvent;
        CompositeElement treeParent = getTreeParent();
        if (treeParent != null) {
            treeChangeEventImpl.addElementaryChange(treeParent);
        }
        remove(treeChangeEventImpl, firstChildNode, null);
        add(treeChangeEventImpl, this, (TreeElement) aSTNode);
        if (treeParent != null) {
            repairRemovedElement(this, firstChildNode);
        }
    }

    public void removeAllChildren() {
        TreeElement firstChildNode = getFirstChildNode();
        if (firstChildNode != null) {
            removeRange(firstChildNode, null);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public void addChildren(ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3) {
        if (aSTNode == null) {
            $$$reportNull$$$0(41);
        }
        while (aSTNode != aSTNode2) {
            ASTNode treeNext = aSTNode.getTreeNext();
            addChild(aSTNode, aSTNode3);
            aSTNode = treeNext;
        }
    }

    final PsiElement getCachedPsi() {
        return this.myWrapper;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public final PsiElement getPsi() {
        ProgressIndicatorProvider.checkCanceled();
        PsiElement psiElement = this.myWrapper;
        if (psiElement != null) {
            return psiElement;
        }
        PsiElement psiElementCreatePsiNoLock = createPsiNoLock();
        return AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(myWrapperUpdater, this, null, psiElementCreatePsiNoLock) ? psiElementCreatePsiNoLock : (PsiElement) Objects.requireNonNull(this.myWrapper);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public <T extends PsiElement> T getPsi(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(42);
        }
        return (T) LeafElement.getPsi(cls, getPsi(), LOG);
    }

    protected PsiElement createPsiNoLock() {
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(getElementType().getLanguage());
        if (parserDefinitionForLanguage != null) {
            return parserDefinitionForLanguage.createElement(this);
        }
        return null;
    }

    public void setPsi(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(43);
        }
        PsiElement psiElement2 = this.myWrapper;
        if (psiElement2 != null && psiElement2 != psiElement) {
            DebugUtil.onInvalidated(psiElement2);
        }
        this.myWrapper = psiElement;
    }

    void clearPsi() {
        this.myWrapper = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ReparseableASTNode
    public final void applyInsertOnReparse(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(44);
        }
        TreeElement treeElement = (TreeElement) aSTNode;
        treeElement.rawRemove();
        if (aSTNode2 != null) {
            ((TreeElement) aSTNode2).rawInsertAfterMe(treeElement);
        } else {
            TreeElement firstChildNode = getFirstChildNode();
            if (firstChildNode != null) {
                firstChildNode.rawInsertBeforeMe(treeElement);
            } else {
                rawAddChildren(treeElement);
            }
        }
        treeElement.clearCaches();
        subtreeChanged();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ReparseableASTNode
    public final void applyDeleteOnReparse(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(45);
        }
        ((TreeElement) aSTNode).rawRemove();
        subtreeChanged();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ReparseableASTNode
    public final void applyReplaceFileOnReparse(PsiFile psiFile, FileASTNode fileASTNode) {
        if (psiFile == null) {
            $$$reportNull$$$0(46);
        }
        if (fileASTNode == null) {
            $$$reportNull$$$0(47);
        }
        if (getFirstChildNode() != null) {
            rawRemoveAllChildren();
        }
        ASTNode firstChildNode = fileASTNode.getFirstChildNode();
        if (firstChildNode != null) {
            rawAddChildren((TreeElement) firstChildNode);
        }
        ((PsiFileImpl) psiFile).calcTreeElement().setCharTable(fileASTNode.getCharTable());
        subtreeChanged();
    }

    public final void rawAddChildren(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(48);
        }
        rawAddChildrenWithoutNotifications(treeElement);
        subtreeChanged();
    }

    public void rawAddChildrenWithoutNotifications(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(49);
        }
        TreeElement lastChildNode = getLastChildNode();
        if (lastChildNode == null) {
            TreeElement treeElementRawSetParents = rawSetParents(treeElement, this);
            setFirstChildNode(treeElement);
            setLastChildNode(treeElementRawSetParents);
        } else {
            lastChildNode.rawInsertAfterMeWithoutNotifications(treeElement);
        }
        DebugUtil.checkTreeStructure(this);
    }

    static TreeElement rawSetParents(TreeElement treeElement, CompositeElement compositeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(50);
        }
        if (compositeElement == null) {
            $$$reportNull$$$0(51);
        }
        treeElement.rawRemoveUpToWithoutNotifications(null, false);
        while (true) {
            treeElement.setTreeParent(compositeElement);
            TreeElement treeNext = treeElement.getTreeNext();
            if (treeNext == null) {
                break;
            }
            treeElement = treeNext;
        }
        if (treeElement == null) {
            $$$reportNull$$$0(52);
        }
        return treeElement;
    }

    public void rawRemoveAllChildren() {
        TreeElement firstChildNode = getFirstChildNode();
        if (firstChildNode != null) {
            firstChildNode.rawRemoveUpToLast();
        }
    }

    private static void repairRemovedElement(CompositeElement compositeElement, TreeElement treeElement) {
        if (compositeElement == null) {
            $$$reportNull$$$0(53);
        }
        if (treeElement == null) {
            return;
        }
        DummyHolderFactory.createHolder((PsiManager) compositeElement.getManager(), (CharTable) null, false).getTreeElement().rawAddChildren(treeElement);
    }

    private static void add(TreeChangeEventImpl treeChangeEventImpl, CompositeElement compositeElement, TreeElement treeElement) {
        if (treeChangeEventImpl == null) {
            $$$reportNull$$$0(54);
        }
        if (compositeElement == null) {
            $$$reportNull$$$0(55);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(56);
        }
        treeChangeEventImpl.addElementaryChange(compositeElement);
        compositeElement.rawAddChildren(treeElement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void remove(TreeChangeEventImpl treeChangeEventImpl, TreeElement treeElement, TreeElement treeElement2) {
        if (treeChangeEventImpl == null) {
            $$$reportNull$$$0(57);
        }
        if (treeElement != null) {
            treeChangeEventImpl.addElementaryChange(treeElement.getTreeParent());
            treeElement.rawRemoveUpTo(treeElement2);
        }
    }

    private static void insertBefore(TreeChangeEventImpl treeChangeEventImpl, TreeElement treeElement, TreeElement treeElement2) {
        if (treeChangeEventImpl == null) {
            $$$reportNull$$$0(58);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(59);
        }
        if (treeElement2 == null) {
            $$$reportNull$$$0(60);
        }
        treeChangeEventImpl.addElementaryChange(treeElement.getTreeParent());
        treeElement.rawInsertBeforeMe(treeElement2);
    }

    private static void replace(TreeChangeEventImpl treeChangeEventImpl, TreeElement treeElement, TreeElement treeElement2) {
        if (treeChangeEventImpl == null) {
            $$$reportNull$$$0(61);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(62);
        }
        if (treeElement2 == null) {
            $$$reportNull$$$0(63);
        }
        treeChangeEventImpl.addElementaryChange(treeElement.getTreeParent());
        treeElement.rawReplaceWithList(treeElement2);
    }

    private static void removeChildInner(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(64);
        }
        removeChildrenInner(treeElement, treeElement.getTreeNext());
    }

    private static void removeChildrenInner(final TreeElement treeElement, final TreeElement treeElement2) {
        if (treeElement == null) {
            $$$reportNull$$$0(65);
        }
        final FileElement fileElement = TreeUtil.getFileElement(treeElement);
        if (fileElement != null) {
            ChangeUtil.prepareAndRunChangeAction(new ChangeUtil.ChangeAction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil.ChangeAction
                public final void makeChange(TreeChangeEvent treeChangeEvent) {
                    CompositeElement.lambda$removeChildrenInner$4(treeElement, treeElement2, fileElement, treeChangeEvent);
                }
            }, treeElement.getTreeParent());
        } else {
            treeElement.rawRemoveUpTo(treeElement2);
        }
    }

    static /* synthetic */ void lambda$removeChildrenInner$4(TreeElement treeElement, TreeElement treeElement2, FileElement fileElement, TreeChangeEvent treeChangeEvent) {
        remove((TreeChangeEventImpl) treeChangeEvent, treeElement, treeElement2);
        repairRemovedElement(fileElement, treeElement);
    }

    public TreeElement rawFirstChild() {
        return this.firstChild;
    }

    public TreeElement rawLastChild() {
        return this.lastChild;
    }
}
