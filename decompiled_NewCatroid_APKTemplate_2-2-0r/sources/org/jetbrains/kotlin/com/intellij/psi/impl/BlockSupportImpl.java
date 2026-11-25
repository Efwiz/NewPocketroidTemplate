package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.util.Couple;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ASTShallowComparator;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ASTStructure;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.ITemplateDataElementType;
import org.jetbrains.kotlin.com.intellij.psi.text.BlockSupport;
import org.jetbrains.kotlin.com.intellij.psi.tree.ICustomParsingType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableElementTypeBase;
import org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableLeafElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.OuterLanguageElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.diff.DiffTree;
import org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.com.intellij.util.diff.ShallowNodeComparator;

/* JADX INFO: loaded from: classes3.dex */
public class BlockSupportImpl extends BlockSupport {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) BlockSupportImpl.class);

    /* JADX WARN: Code duplicated, block: B:8:0x0012  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 8 && i != 35 && i != 38) {
            switch (i) {
                case 44:
                case 45:
                case 46:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 8 && i != 35 && i != 38) {
            switch (i) {
                case 44:
                case 45:
                case 46:
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
                objArr[0] = "newText";
                break;
            case 2:
            case 9:
            case 15:
            default:
                objArr[0] = "file";
                break;
            case 3:
            case 10:
            case 16:
            case 29:
                objArr[0] = "oldFileNode";
                break;
            case 4:
            case 11:
            case 17:
                objArr[0] = "changedPsiRange";
                break;
            case 5:
            case 12:
            case 18:
            case 30:
                objArr[0] = "newFileText";
                break;
            case 6:
            case 13:
            case 31:
            case 42:
            case 51:
            case 54:
                objArr[0] = "indicator";
                break;
            case 7:
            case 14:
            case 32:
            case 43:
            case 52:
                objArr[0] = "lastCommittedText";
                break;
            case 8:
            case 35:
            case 38:
            case 44:
            case 45:
            case 46:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/BlockSupportImpl";
                break;
            case 19:
            case 25:
                objArr[0] = "reparseable";
                break;
            case 20:
            case 26:
                objArr[0] = "node";
                break;
            case 21:
            case 27:
                objArr[0] = "newTextStr";
                break;
            case 22:
                objArr[0] = "manager";
                break;
            case 23:
                objArr[0] = "baseLanguage";
                break;
            case 24:
                objArr[0] = "charTable";
                break;
            case 28:
            case 39:
            case 55:
                objArr[0] = "fileImpl";
                break;
            case 33:
                objArr[0] = "originalFile";
                break;
            case 34:
                objArr[0] = "providerCopy";
                break;
            case 36:
            case 40:
            case 47:
            case 53:
                objArr[0] = "oldRoot";
                break;
            case 37:
            case 41:
            case 56:
                objArr[0] = "newRoot";
                break;
            case 48:
                objArr[0] = "builder";
                break;
            case 49:
                objArr[0] = "comparator";
                break;
            case 50:
                objArr[0] = "newTreeStructure";
                break;
        }
        if (i == 8) {
            objArr[1] = "reparseRange";
        } else if (i == 35) {
            objArr[1] = "getFileCopy";
        } else if (i != 38) {
            switch (i) {
                case 44:
                case 45:
                case 46:
                    objArr[1] = "mergeTrees";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/BlockSupportImpl";
                    break;
            }
        } else {
            objArr[1] = "replaceElementWithEvents";
        }
        switch (i) {
            case 8:
            case 35:
            case 38:
            case 44:
            case 45:
            case 46:
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[2] = "reparse";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "findReparseableRoots";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "tryReparseNode";
                break;
            case 25:
            case 26:
            case 27:
                objArr[2] = "tryReparseLeaf";
                break;
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
                objArr[2] = "makeFullParse";
                break;
            case 33:
            case 34:
                objArr[2] = "getFileCopy";
                break;
            case 36:
            case 37:
                objArr[2] = "replaceElementWithEvents";
                break;
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
                objArr[2] = "mergeTrees";
                break;
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                objArr[2] = "diffTrees";
                break;
            case 53:
            case 54:
                objArr[2] = "createInterruptibleASTStructure";
                break;
            case 55:
            case 56:
                objArr[2] = "isReplaceWholeNode";
                break;
            default:
                objArr[2] = "reparseRange";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 35 && i != 38) {
            switch (i) {
                case 44:
                case 45:
                case 46:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.text.BlockSupport
    public void reparseRange(PsiFile psiFile, int i, int i2, CharSequence charSequence) throws IncorrectOperationException {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(psiFile.isValid());
        PsiFileImpl psiFileImpl = (PsiFileImpl) psiFile;
        Document document = psiFileImpl.getViewProvider().getDocument();
        document.replaceString(i, i2, charSequence);
        PsiDocumentManager.getInstance(psiFileImpl.getProject()).commitDocument(document);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.text.BlockSupport
    public DiffLog reparseRange(PsiFile psiFile, FileASTNode fileASTNode, TextRange textRange, CharSequence charSequence, ProgressIndicator progressIndicator, CharSequence charSequence2) {
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        if (fileASTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (textRange == null) {
            $$$reportNull$$$0(4);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(6);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(7);
        }
        DiffLog diffLog = reparse(psiFile, fileASTNode, textRange, charSequence, progressIndicator, charSequence2).log;
        if (diffLog == null) {
            $$$reportNull$$$0(8);
        }
        return diffLog;
    }

    static class ReparseResult {
        final DiffLog log;
        final ASTNode newRoot;
        final ASTNode oldRoot;

        ReparseResult(DiffLog diffLog, ASTNode aSTNode, ASTNode aSTNode2) {
            this.log = diffLog;
            this.oldRoot = aSTNode;
            this.newRoot = aSTNode2;
        }
    }

    static ReparseResult reparse(PsiFile psiFile, FileASTNode fileASTNode, TextRange textRange, CharSequence charSequence, ProgressIndicator progressIndicator, CharSequence charSequence2) {
        if (psiFile == null) {
            $$$reportNull$$$0(9);
        }
        if (fileASTNode == null) {
            $$$reportNull$$$0(10);
        }
        if (textRange == null) {
            $$$reportNull$$$0(11);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(12);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(13);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(14);
        }
        PsiFileImpl psiFileImpl = (PsiFileImpl) psiFile;
        Couple<ASTNode> coupleFindReparseableRoots = findReparseableRoots(psiFileImpl, fileASTNode, textRange, charSequence);
        if (coupleFindReparseableRoots == null) {
            return makeFullParse(psiFileImpl, fileASTNode, charSequence, progressIndicator, charSequence2);
        }
        ASTNode aSTNode = (ASTNode) coupleFindReparseableRoots.first;
        ASTNode aSTNode2 = (ASTNode) coupleFindReparseableRoots.second;
        return new ReparseResult(mergeTrees(psiFileImpl, aSTNode, aSTNode2, progressIndicator, charSequence2), aSTNode, aSTNode2);
    }

    public static Couple<ASTNode> findReparseableRoots(final PsiFileImpl psiFileImpl, FileASTNode fileASTNode, TextRange textRange, final CharSequence charSequence) {
        ASTNode treeParent;
        Couple<ASTNode> couple;
        Couple<ASTNode> couple2;
        if (psiFileImpl == null) {
            $$$reportNull$$$0(15);
        }
        if (fileASTNode == null) {
            $$$reportNull$$$0(16);
        }
        if (textRange == null) {
            $$$reportNull$$$0(17);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(18);
        }
        final CharTable charTable = fileASTNode.getCharTable();
        final int length = charSequence.length() - fileASTNode.getTextLength();
        if (isTooDeep(psiFileImpl)) {
            return null;
        }
        boolean z = fileASTNode.getElementType() instanceof ITemplateDataElementType;
        ASTNode aSTNodeFindLeafElementAt = fileASTNode.findLeafElementAt(Math.max(0, textRange.getStartOffset() - 1));
        ASTNode aSTNodeFindLeafElementAt2 = fileASTNode.findLeafElementAt(Math.min(textRange.getEndOffset(), fileASTNode.getTextLength() - 1));
        if (aSTNodeFindLeafElementAt != null && aSTNodeFindLeafElementAt2 != null) {
            treeParent = TreeUtil.findCommonParent(aSTNodeFindLeafElementAt, aSTNodeFindLeafElementAt2);
        }
        treeParent = fileASTNode;
        treeParent = fileASTNode;
        final Language baseLanguage = psiFileImpl.getViewProvider().getBaseLanguage();
        Function function = new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.BlockSupportImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BlockSupportImpl.lambda$findReparseableRoots$0(length, baseLanguage, charSequence, psiFileImpl, charTable, (ASTNode) obj);
            }
        };
        TextRange textRange2 = aSTNodeFindLeafElementAt == null ? null : aSTNodeFindLeafElementAt.getTextRange();
        TextRange textRange3 = aSTNodeFindLeafElementAt2 == null ? null : aSTNodeFindLeafElementAt2.getTextRange();
        IElementType elementType = PsiUtilCore.getElementType(aSTNodeFindLeafElementAt);
        if ((elementType instanceof IReparseableLeafElementType) && textRange2.getEndOffset() == textRange.getEndOffset() && ((!z || (elementType instanceof OuterLanguageElementType)) && (couple2 = (Couple) function.apply(aSTNodeFindLeafElementAt)) != null && couple2.first != 0)) {
            return couple2;
        }
        IElementType elementType2 = PsiUtilCore.getElementType(aSTNodeFindLeafElementAt2);
        if ((elementType2 instanceof IReparseableLeafElementType) && textRange3.getStartOffset() == textRange.getStartOffset() && ((!z || (elementType2 instanceof OuterLanguageElementType)) && (couple = (Couple) function.apply(aSTNodeFindLeafElementAt2)) != null && couple.first != 0)) {
            return couple;
        }
        while (treeParent != null && !(treeParent instanceof FileElement) && (!z || (PsiUtilCore.getElementType(treeParent) instanceof OuterLanguageElementType))) {
            Couple<ASTNode> couple3 = (Couple) function.apply(treeParent);
            if (couple3 != null) {
                if (couple3.first == 0) {
                    break;
                }
                return couple3;
            }
            treeParent = treeParent.getTreeParent();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Couple lambda$findReparseableRoots$0(int i, Language language, CharSequence charSequence, PsiFileImpl psiFileImpl, CharTable charTable, ASTNode aSTNode) {
        ASTNode aSTNodeTryReparseLeaf;
        String str;
        IElementType elementType = aSTNode.getElementType();
        boolean z = elementType instanceof IReparseableElementTypeBase;
        if (z || (elementType instanceof IReparseableLeafElementType)) {
            TextRange textRange = aSTNode.getTextRange();
            if (textRange.getLength() + i > 0 && (language.isKindOf(elementType.getLanguage()) || (elementType instanceof IReparseableLeafElementType) || !TreeUtil.containsOuterLanguageElements(aSTNode))) {
                int startOffset = textRange.getStartOffset();
                int length = textRange.getLength() + startOffset + i;
                if (length > charSequence.length()) {
                    reportInconsistentLength(psiFileImpl, charSequence, aSTNode, startOffset, length);
                    return Couple.of(null, null);
                }
                CharSequence charSequenceSubSequence = charSequence.subSequence(startOffset, length);
                if (z) {
                    aSTNodeTryReparseLeaf = tryReparseNode((IReparseableElementTypeBase) elementType, aSTNode, charSequenceSubSequence, psiFileImpl.getManager(), language, charTable);
                } else {
                    aSTNodeTryReparseLeaf = tryReparseLeaf((IReparseableLeafElementType) elementType, aSTNode, charSequenceSubSequence);
                }
                if (aSTNodeTryReparseLeaf != null) {
                    if (aSTNodeTryReparseLeaf.getTextLength() != charSequenceSubSequence.length()) {
                        if (ApplicationManager.getApplication().isInternal()) {
                            str = "text=" + ((Object) charSequenceSubSequence) + "; treeText=" + aSTNodeTryReparseLeaf.getText() + ";";
                        } else {
                            str = "";
                        }
                        LOG.error("Inconsistent reparse: " + str + " type=" + elementType);
                    }
                    return Couple.of(aSTNode, aSTNodeTryReparseLeaf);
                }
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected static ASTNode tryReparseNode(IReparseableElementTypeBase iReparseableElementTypeBase, ASTNode aSTNode, CharSequence charSequence, PsiManager psiManager, Language language, CharTable charTable) {
        ASTNode aSTNodeCreateNode;
        if (iReparseableElementTypeBase == 0) {
            $$$reportNull$$$0(19);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(20);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(21);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(22);
        }
        if (language == null) {
            $$$reportNull$$$0(23);
        }
        if (charTable == null) {
            $$$reportNull$$$0(24);
        }
        if (!iReparseableElementTypeBase.isReparseable(aSTNode, charSequence, language, psiManager.getProject())) {
            return null;
        }
        if (iReparseableElementTypeBase instanceof ICustomParsingType) {
            aSTNodeCreateNode = ((ICustomParsingType) iReparseableElementTypeBase).parse(charSequence, SharedImplUtil.findCharTableByTree(aSTNode));
        } else if (iReparseableElementTypeBase instanceof ILazyParseableElementType) {
            aSTNodeCreateNode = ((ILazyParseableElementType) iReparseableElementTypeBase).createNode(charSequence);
        } else {
            throw new AssertionError(iReparseableElementTypeBase.getClass() + " must either implement ICustomParsingType or extend ILazyParseableElementType");
        }
        if (aSTNodeCreateNode == null) {
            return null;
        }
        DummyHolderFactory.createHolder(psiManager, null, aSTNode.getPsi(), charTable).getTreeElement().rawAddChildren((TreeElement) aSTNodeCreateNode);
        if (iReparseableElementTypeBase.isValidReparse(aSTNode, aSTNodeCreateNode)) {
            return aSTNodeCreateNode;
        }
        return null;
    }

    protected static ASTNode tryReparseLeaf(IReparseableLeafElementType iReparseableLeafElementType, ASTNode aSTNode, CharSequence charSequence) {
        if (iReparseableLeafElementType == null) {
            $$$reportNull$$$0(25);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(26);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(27);
        }
        return iReparseableLeafElementType.reparseLeaf(aSTNode, charSequence);
    }

    private static void reportInconsistentLength(PsiFile psiFile, CharSequence charSequence, ASTNode aSTNode, int i, int i2) {
        String str = "Index out of bounds: type=" + aSTNode.getElementType() + "; file=" + psiFile + "; file.class=" + psiFile.getClass() + "; start=" + i + "; end=" + i2 + "; length=" + aSTNode.getTextLength();
        if (psiFile.getText().subSequence(0, i).toString().equals(charSequence.subSequence(0, i).toString())) {
            str = str + "; oldTextBefore==newTextBefore";
        }
        LOG.error(str, new Attachment(psiFile.getName() + "_oldNodeText.txt", aSTNode.getText()), new Attachment(psiFile.getName() + "_oldFileText.txt", psiFile.getText()), new Attachment(psiFile.getName() + "_newFileText.txt", charSequence.toString()));
    }

    static ReparseResult makeFullParse(PsiFileImpl psiFileImpl, FileASTNode fileASTNode, CharSequence charSequence, ProgressIndicator progressIndicator, CharSequence charSequence2) {
        if (psiFileImpl == null) {
            $$$reportNull$$$0(28);
        }
        if (fileASTNode == null) {
            $$$reportNull$$$0(29);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(30);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(31);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(32);
        }
        if (psiFileImpl instanceof PsiCodeFragment) {
            FileElement treeElement = psiFileImpl.getTreeElement();
            PsiElement context = psiFileImpl.getContext();
            PsiManager manager = psiFileImpl.getManager();
            if (context == null || !context.isValid()) {
                context = null;
            }
            FileElement treeElement2 = new DummyHolder(manager, context).getTreeElement();
            treeElement2.rawAddChildren(psiFileImpl.createContentLeafElement(treeElement2.getCharTable().intern(charSequence, 0, charSequence.length())));
            DiffLog diffLog = new DiffLog();
            diffLog.appendReplaceFileElement(treeElement, (FileElement) treeElement2.getFirstChildNode());
            return new ReparseResult(diffLog, fileASTNode, treeElement2);
        }
        FileViewProvider viewProvider = psiFileImpl.getViewProvider();
        viewProvider.getLanguages();
        VirtualFile virtualFile = viewProvider.getVirtualFile();
        LightVirtualFile lightVirtualFile = new LightVirtualFile(psiFileImpl.getName(), virtualFile.getFileType(), charSequence, virtualFile.getCharset(), viewProvider.getModificationStamp());
        lightVirtualFile.setOriginalFile(virtualFile);
        FileViewProvider fileViewProviderCreateCopy = viewProvider.createCopy(lightVirtualFile);
        if (fileViewProviderCreateCopy.isEventSystemEnabled()) {
            throw new AssertionError("Copied view provider must be non-physical for reparse to deliver correct events: " + viewProvider);
        }
        fileViewProviderCreateCopy.getLanguages();
        SingleRootFileViewProvider.doNotCheckFileSizeLimit(lightVirtualFile);
        PsiFileImpl fileCopy = getFileCopy(psiFileImpl, fileViewProviderCreateCopy);
        fileCopy.setOriginalFile(psiFileImpl);
        FileASTNode node = fileCopy.getNode();
        if (charSequence2.length() != fileASTNode.getTextLength()) {
            throw new IncorrectOperationException(viewProvider.toString());
        }
        return new ReparseResult(mergeTrees(psiFileImpl, fileASTNode, node, progressIndicator, charSequence2), fileASTNode, node);
    }

    public static PsiFileImpl getFileCopy(PsiFileImpl psiFileImpl, FileViewProvider fileViewProvider) {
        if (psiFileImpl == null) {
            $$$reportNull$$$0(33);
        }
        if (fileViewProvider == null) {
            $$$reportNull$$$0(34);
        }
        FileViewProvider viewProvider = psiFileImpl.getViewProvider();
        Language language = psiFileImpl.getLanguage();
        PsiFile psi = fileViewProvider.getPsi(language);
        if (psi != null && !(psi instanceof PsiFileImpl)) {
            throw new RuntimeException("View provider " + viewProvider + " refused to provide PsiFileImpl for " + language + details(fileViewProvider, viewProvider) + " and returned this strange thing instead of PsiFileImpl: " + psi + " (" + psi.getClass() + Constant.AFTER_QUTO);
        }
        PsiFileImpl psiFileImpl2 = (PsiFileImpl) psi;
        if (psiFileImpl2 == null && language == PlainTextLanguage.INSTANCE && psiFileImpl == viewProvider.getPsi(viewProvider.getBaseLanguage())) {
            psiFileImpl2 = (PsiFileImpl) fileViewProvider.getPsi(fileViewProvider.getBaseLanguage());
        }
        if (psiFileImpl2 == null) {
            throw new RuntimeException("View provider " + viewProvider + " refused to parse text with " + language + details(fileViewProvider, viewProvider));
        }
        if (psiFileImpl2 == null) {
            $$$reportNull$$$0(35);
        }
        return psiFileImpl2;
    }

    private static String details(FileViewProvider fileViewProvider, FileViewProvider fileViewProvider2) {
        return "; languages: " + fileViewProvider2.getLanguages() + "; base: " + fileViewProvider2.getBaseLanguage() + "; copy: " + fileViewProvider + "; copy.base: " + fileViewProvider.getBaseLanguage() + "; vFile: " + fileViewProvider2.getVirtualFile() + "; copy.vFile: " + fileViewProvider.getVirtualFile() + "; fileType: " + fileViewProvider2.getVirtualFile().getFileType() + "; copy.original(): " + (fileViewProvider.getVirtualFile() instanceof LightVirtualFile ? ((LightVirtualFile) fileViewProvider.getVirtualFile()).getOriginalFile() : null);
    }

    private static DiffLog replaceElementWithEvents(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(36);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(37);
        }
        DiffLog diffLog = new DiffLog();
        if (aSTNode instanceof CompositeElement) {
            diffLog.appendReplaceElementWithEvents((CompositeElement) aSTNode, (CompositeElement) aSTNode2);
        } else {
            diffLog.nodeReplaced(aSTNode, aSTNode2);
        }
        return diffLog;
    }

    public static DiffLog mergeTrees(PsiFileImpl psiFileImpl, ASTNode aSTNode, ASTNode aSTNode2, ProgressIndicator progressIndicator, CharSequence charSequence) {
        FileElement treeElement;
        if (psiFileImpl == null) {
            $$$reportNull$$$0(39);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(40);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(41);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(42);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(43);
        }
        PsiUtilCore.ensureValid(psiFileImpl);
        if ((aSTNode2 instanceof FileElement) && (treeElement = psiFileImpl.getTreeElement()) != null) {
            ((FileElement) aSTNode2).setCharTable(treeElement.getCharTable());
        }
        try {
            try {
                aSTNode2.putUserData(TREE_TO_BE_REPARSED, Pair.create(aSTNode, charSequence));
                if (isReplaceWholeNode(psiFileImpl, aSTNode2)) {
                    DiffLog diffLogReplaceElementWithEvents = replaceElementWithEvents(aSTNode, aSTNode2);
                    psiFileImpl.putUserData(TREE_DEPTH_LIMIT_EXCEEDED, Boolean.TRUE);
                    aSTNode2.putUserData(TREE_TO_BE_REPARSED, null);
                    if (diffLogReplaceElementWithEvents == null) {
                        $$$reportNull$$$0(44);
                    }
                    return diffLogReplaceElementWithEvents;
                }
                aSTNode2.getFirstChildNode();
                aSTNode2.putUserData(TREE_TO_BE_REPARSED, null);
                ASTShallowComparator aSTShallowComparator = new ASTShallowComparator(progressIndicator);
                ASTStructure aSTStructureCreateInterruptibleASTStructure = createInterruptibleASTStructure(aSTNode2, progressIndicator);
                DiffLog diffLog = new DiffLog();
                diffTrees(aSTNode, diffLog, aSTShallowComparator, aSTStructureCreateInterruptibleASTStructure, progressIndicator, charSequence);
                return diffLog;
            } catch (BlockSupport.ReparsedSuccessfullyException e) {
                DiffLog diffLog2 = e.getDiffLog();
                aSTNode2.putUserData(TREE_TO_BE_REPARSED, null);
                if (diffLog2 == null) {
                    $$$reportNull$$$0(45);
                }
                return diffLog2;
            }
        } catch (Throwable th) {
            aSTNode2.putUserData(TREE_TO_BE_REPARSED, null);
            throw th;
        }
    }

    public static <T> void diffTrees(ASTNode aSTNode, DiffTreeChangeBuilder<ASTNode, T> diffTreeChangeBuilder, ShallowNodeComparator<ASTNode, T> shallowNodeComparator, FlyweightCapableTreeStructure<T> flyweightCapableTreeStructure, ProgressIndicator progressIndicator, CharSequence charSequence) {
        if (aSTNode == null) {
            $$$reportNull$$$0(47);
        }
        if (diffTreeChangeBuilder == null) {
            $$$reportNull$$$0(48);
        }
        if (shallowNodeComparator == null) {
            $$$reportNull$$$0(49);
        }
        if (flyweightCapableTreeStructure == null) {
            $$$reportNull$$$0(50);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(51);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(52);
        }
        DiffTree.diff(createInterruptibleASTStructure(aSTNode, progressIndicator), flyweightCapableTreeStructure, shallowNodeComparator, diffTreeChangeBuilder, charSequence);
    }

    private static ASTStructure createInterruptibleASTStructure(ASTNode aSTNode, final ProgressIndicator progressIndicator) {
        if (aSTNode == null) {
            $$$reportNull$$$0(53);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(54);
        }
        return new ASTStructure(aSTNode) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.BlockSupportImpl.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "astNode";
                } else {
                    objArr[0] = "into";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/BlockSupportImpl$1";
                objArr[2] = "getChildren";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ASTStructure, org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
            public int getChildren(ASTNode aSTNode2, Ref<ASTNode[]> ref) {
                if (aSTNode2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (ref == null) {
                    $$$reportNull$$$0(1);
                }
                progressIndicator.checkCanceled();
                return super.getChildren(aSTNode2, ref);
            }
        };
    }

    private static boolean isReplaceWholeNode(PsiFileImpl psiFileImpl, ASTNode aSTNode) throws BlockSupport.ReparsedSuccessfullyException {
        if (psiFileImpl == null) {
            $$$reportNull$$$0(55);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(56);
        }
        Boolean bool = (Boolean) psiFileImpl.getUserData(DO_NOT_REPARSE_INCREMENTALLY);
        if (bool != null) {
            psiFileImpl.putUserData(DO_NOT_REPARSE_INCREMENTALLY, null);
        }
        if (Boolean.TRUE.equals(bool) || isTooDeep(psiFileImpl)) {
            return true;
        }
        ASTNode firstChildNode = aSTNode.getFirstChildNode();
        boolean zIsTooDeep = isTooDeep(firstChildNode);
        if (zIsTooDeep) {
            firstChildNode.putUserData(TREE_DEPTH_LIMIT_EXCEEDED, null);
            psiFileImpl.putUserData(TREE_DEPTH_LIMIT_EXCEEDED, Boolean.TRUE);
        }
        return zIsTooDeep;
    }
}
