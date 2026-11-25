package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.Commenter;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageCommenters;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiParserFacade;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiParserFacadeImpl implements PsiParserFacade {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final PsiManagerEx myManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5 || i == 8 || i == 11 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5 || i == 8 || i == 11 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 13:
                objArr[0] = "text";
                break;
            case 2:
            case 5:
            case 8:
            case 11:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiParserFacadeImpl";
                break;
            case 3:
                objArr[0] = "fileType";
                break;
            case 6:
            case 9:
            case 12:
                objArr[0] = "language";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 2) {
            objArr[1] = "createWhiteSpaceFromText";
        } else if (i == 5 || i == 8) {
            objArr[1] = "createLineCommentFromText";
        } else if (i == 11) {
            objArr[1] = "createBlockCommentFromText";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiParserFacadeImpl";
        } else {
            objArr[1] = "createLineOrBlockCommentFromText";
        }
        switch (i) {
            case 1:
                objArr[2] = "createWhiteSpaceFromText";
                break;
            case 2:
            case 5:
            case 8:
            case 11:
            case 14:
                break;
            case 3:
            case 4:
            case 6:
            case 7:
                objArr[2] = "createLineCommentFromText";
                break;
            case 9:
            case 10:
                objArr[2] = "createBlockCommentFromText";
                break;
            case 12:
            case 13:
                objArr[2] = "createLineOrBlockCommentFromText";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 8 && i != 11 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiParserFacadeImpl(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myManager = PsiManagerEx.getInstanceEx(project);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParserFacade
    public PsiElement createWhiteSpaceFromText(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        FileElement treeElement = DummyHolderFactory.createHolder(this.myManager, null).getTreeElement();
        LeafElement leafElementLeaf = ASTFactory.leaf(TokenType.WHITE_SPACE, treeElement.getCharTable().intern(str));
        treeElement.rawAddChildren(leafElementLeaf);
        GeneratedMarkerVisitor.markGenerated(leafElementLeaf.getPsi());
        PsiElement psi = leafElementLeaf.getPsi();
        if (psi == null) {
            $$$reportNull$$$0(2);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParserFacade
    public PsiComment createLineCommentFromText(LanguageFileType languageFileType, String str) throws IncorrectOperationException {
        if (languageFileType == null) {
            $$$reportNull$$$0(3);
        }
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        PsiComment psiCommentCreateLineCommentFromText = createLineCommentFromText(languageFileType.getLanguage(), str);
        if (psiCommentCreateLineCommentFromText == null) {
            $$$reportNull$$$0(5);
        }
        return psiCommentCreateLineCommentFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParserFacade
    public PsiComment createLineCommentFromText(Language language, String str) throws IncorrectOperationException {
        if (language == null) {
            $$$reportNull$$$0(6);
        }
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        String lineCommentPrefix = LanguageCommenters.INSTANCE.forLanguage(language).getLineCommentPrefix();
        if (lineCommentPrefix == null) {
            throw new IncorrectOperationException("No line comment prefix defined for language " + language.getID());
        }
        PsiComment psiCommentFindPsiCommentChild = findPsiCommentChild(createDummyFile(language, lineCommentPrefix + str));
        if (psiCommentFindPsiCommentChild == null) {
            $$$reportNull$$$0(8);
        }
        return psiCommentFindPsiCommentChild;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParserFacade
    public PsiComment createBlockCommentFromText(Language language, String str) throws IncorrectOperationException {
        if (language == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        Commenter commenterForLanguage = LanguageCommenters.INSTANCE.forLanguage(language);
        PsiComment psiCommentFindPsiCommentChild = findPsiCommentChild(createDummyFile(language, commenterForLanguage.getBlockCommentPrefix() + str + commenterForLanguage.getBlockCommentSuffix()));
        if (psiCommentFindPsiCommentChild == null) {
            $$$reportNull$$$0(11);
        }
        return psiCommentFindPsiCommentChild;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParserFacade
    public PsiComment createLineOrBlockCommentFromText(Language language, String str) throws IncorrectOperationException {
        if (language == null) {
            $$$reportNull$$$0(12);
        }
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        Commenter commenterForLanguage = LanguageCommenters.INSTANCE.forLanguage(language);
        String lineCommentPrefix = commenterForLanguage.getLineCommentPrefix();
        PsiComment psiCommentFindPsiCommentChild = findPsiCommentChild(createDummyFile(language, (lineCommentPrefix != null ? new StringBuilder().append(lineCommentPrefix).append(str) : new StringBuilder().append(commenterForLanguage.getBlockCommentPrefix()).append(str).append(commenterForLanguage.getBlockCommentSuffix())).toString()));
        if (psiCommentFindPsiCommentChild == null) {
            $$$reportNull$$$0(14);
        }
        return psiCommentFindPsiCommentChild;
    }

    private PsiComment findPsiCommentChild(PsiFile psiFile) {
        PsiComment psiComment = (PsiComment) PsiTreeUtil.findChildOfType(psiFile, PsiComment.class);
        if (psiComment == null) {
            throw new IncorrectOperationException("Incorrect comment \"" + psiFile.getText() + "\".");
        }
        DummyHolderFactory.createHolder(this.myManager, (TreeElement) SourceTreeToPsiMap.psiElementToTree(psiComment), (PsiElement) null);
        return psiComment;
    }

    private PsiFile createDummyFile(Language language, String str) {
        return PsiFileFactory.getInstance(this.myManager.getProject()).createFileFromText("_Dummy_", language, str);
    }
}
