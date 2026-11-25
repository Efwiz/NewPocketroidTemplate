package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProviderFactory;
import org.jetbrains.kotlin.com.intellij.psi.LanguageFileViewProviders;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiPlainTextFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.LocalTimeCounter;
import org.jetbrains.kotlin.com.intellij.util.text.CharSequenceSubSequence;

/* JADX INFO: loaded from: classes3.dex */
public class PsiFileFactoryImpl extends PsiFileFactory {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiFileFactoryImpl.class);
    private final PsiManager myManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 34 || i == 37 || i == 21 || i == 22 || i == 29 || i == 30) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 34 || i == 37 || i == 21 || i == 22 || i == 29 || i == 30) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "manager";
                break;
            case 2:
            case 6:
            case 9:
            case 12:
            case 15:
            case 18:
            case 25:
            case 31:
            case 35:
                objArr[0] = "name";
                break;
            case 3:
            case 19:
            case 26:
            case 32:
                objArr[0] = "fileType";
                break;
            case 4:
            case 8:
            case 11:
            case 14:
            case 17:
            case 20:
            case 28:
            case 33:
            case 36:
                objArr[0] = "text";
                break;
            case 5:
            case 21:
            case 22:
            case 29:
            case 30:
            case 34:
            case 37:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiFileFactoryImpl";
                break;
            case 7:
            case 10:
            case 13:
            case 16:
            case 24:
            case 40:
                objArr[0] = "language";
                break;
            case 23:
                objArr[0] = "virtualFile";
                break;
            case 27:
                objArr[0] = "targetLanguage";
                break;
            case 38:
                objArr[0] = "chars";
                break;
            case 39:
                objArr[0] = "original";
                break;
            case 41:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 5 || i == 34 || i == 37 || i == 21 || i == 22 || i == 29 || i == 30) {
            objArr[1] = "createFileFromText";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiFileFactoryImpl";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 25:
            case 26:
            case 27:
            case 28:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 38:
            case 39:
                objArr[2] = "createFileFromText";
                break;
            case 5:
            case 21:
            case 22:
            case 29:
            case 30:
            case 34:
            case 37:
                break;
            case 23:
            case 24:
                objArr[2] = "trySetupPsiForFile";
                break;
            case 40:
            case 41:
                objArr[2] = "createElementFromText";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 34 && i != 37 && i != 21 && i != 22 && i != 29 && i != 30) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiFileFactoryImpl(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myManager = PsiManager.getInstance(project);
    }

    public PsiFileFactoryImpl(PsiManager psiManager) {
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        this.myManager = psiManager;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(String str, FileType fileType, CharSequence charSequence, long j, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (fileType == null) {
            $$$reportNull$$$0(3);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        PsiFile psiFileCreateFileFromText = createFileFromText(str, fileType, charSequence, j, z, true);
        if (psiFileCreateFileFromText == null) {
            $$$reportNull$$$0(5);
        }
        return psiFileCreateFileFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(String str, Language language, CharSequence charSequence) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (language == null) {
            $$$reportNull$$$0(7);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(8);
        }
        return createFileFromText(str, language, charSequence, true, true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(String str, Language language, CharSequence charSequence, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (language == null) {
            $$$reportNull$$$0(10);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(11);
        }
        return createFileFromText(str, language, charSequence, z, z2, false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(String str, Language language, CharSequence charSequence, boolean z, boolean z2, boolean z3) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (language == null) {
            $$$reportNull$$$0(13);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(14);
        }
        return createFileFromText(str, language, charSequence, z, z2, z3, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(String str, Language language, CharSequence charSequence, boolean z, boolean z2, boolean z3, VirtualFile virtualFile) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (language == null) {
            $$$reportNull$$$0(16);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(17);
        }
        LightVirtualFile lightVirtualFile = new LightVirtualFile(str, language, charSequence);
        if (virtualFile != null) {
            lightVirtualFile.setOriginalFile(virtualFile);
            lightVirtualFile.setFileType(virtualFile.getFileType());
        }
        if (z3) {
            SingleRootFileViewProvider.doNotCheckFileSizeLimit(lightVirtualFile);
        }
        return trySetupPsiForFile(lightVirtualFile, language, z, z2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(String str, FileType fileType, CharSequence charSequence, long j, boolean z, boolean z2) {
        PsiFile psiFileTrySetupPsiForFile;
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        if (fileType == null) {
            $$$reportNull$$$0(19);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(20);
        }
        LightVirtualFile lightVirtualFile = new LightVirtualFile(str, fileType, charSequence, j);
        Language languageForPsi = LanguageUtil.getLanguageForPsi(this.myManager.getProject(), lightVirtualFile, fileType);
        if (languageForPsi != null && (psiFileTrySetupPsiForFile = trySetupPsiForFile(lightVirtualFile, languageForPsi, z, z2)) != null) {
            if (psiFileTrySetupPsiForFile == null) {
                $$$reportNull$$$0(21);
            }
            return psiFileTrySetupPsiForFile;
        }
        PsiPlainTextFileImpl psiPlainTextFileImpl = new PsiPlainTextFileImpl(new SingleRootFileViewProvider(this.myManager, lightVirtualFile, z));
        if (z2) {
            CodeEditUtil.setNodeGenerated(psiPlainTextFileImpl.getNode(), true);
        }
        return psiPlainTextFileImpl;
    }

    public PsiFile trySetupPsiForFile(LightVirtualFile lightVirtualFile, Language language, boolean z, boolean z2) {
        PsiFile psi;
        if (lightVirtualFile == null) {
            $$$reportNull$$$0(23);
        }
        if (language == null) {
            $$$reportNull$$$0(24);
        }
        FileViewProviderFactory fileViewProviderFactoryForLanguage = LanguageFileViewProviders.INSTANCE.forLanguage(language);
        FileViewProvider fileViewProviderCreateFileViewProvider = fileViewProviderFactoryForLanguage != null ? fileViewProviderFactoryForLanguage.createFileViewProvider(lightVirtualFile, language, this.myManager, z) : null;
        if (fileViewProviderCreateFileViewProvider == null) {
            fileViewProviderCreateFileViewProvider = new SingleRootFileViewProvider(this.myManager, lightVirtualFile, z);
        }
        Language baseLanguage = fileViewProviderCreateFileViewProvider.getBaseLanguage();
        if (LanguageParserDefinitions.INSTANCE.forLanguage(baseLanguage) == null || (psi = fileViewProviderCreateFileViewProvider.getPsi(baseLanguage)) == null) {
            return null;
        }
        if (z2) {
            if (psi.getNode() == null) {
                throw new AssertionError("No node for file " + psi + "; language=" + baseLanguage);
            }
            markGenerated(psi);
        }
        return psi;
    }

    public PsiFile createFileFromText(String str, FileType fileType, Language language, Language language2, CharSequence charSequence, long j, boolean z, boolean z2) {
        PsiFile psi;
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        if (fileType == null) {
            $$$reportNull$$$0(26);
        }
        if (language2 == null) {
            $$$reportNull$$$0(27);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(28);
        }
        LightVirtualFile lightVirtualFile = new LightVirtualFile(str, fileType, charSequence, j);
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(language);
        FileViewProviderFactory fileViewProviderFactoryForLanguage = LanguageFileViewProviders.INSTANCE.forLanguage(language);
        FileViewProvider fileViewProviderCreateFileViewProvider = fileViewProviderFactoryForLanguage != null ? fileViewProviderFactoryForLanguage.createFileViewProvider(lightVirtualFile, language, this.myManager, z) : null;
        if (fileViewProviderCreateFileViewProvider == null) {
            fileViewProviderCreateFileViewProvider = new SingleRootFileViewProvider(this.myManager, lightVirtualFile, z);
        }
        if (parserDefinitionForLanguage == null || (psi = fileViewProviderCreateFileViewProvider.getPsi(language2)) == null) {
            PsiPlainTextFileImpl psiPlainTextFileImpl = new PsiPlainTextFileImpl(new SingleRootFileViewProvider(this.myManager, lightVirtualFile, z));
            if (z2) {
                CodeEditUtil.setNodeGenerated(psiPlainTextFileImpl.getNode(), true);
            }
            return psiPlainTextFileImpl;
        }
        if (z2) {
            markGenerated(psi);
        }
        if (psi == null) {
            $$$reportNull$$$0(29);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(String str, FileType fileType, CharSequence charSequence) {
        if (str == null) {
            $$$reportNull$$$0(31);
        }
        if (fileType == null) {
            $$$reportNull$$$0(32);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(33);
        }
        PsiFile psiFileCreateFileFromText = createFileFromText(str, fileType, charSequence, LocalTimeCounter.currentTime(), false);
        if (psiFileCreateFileFromText == null) {
            $$$reportNull$$$0(34);
        }
        return psiFileCreateFileFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(35);
        }
        if (str2 == null) {
            $$$reportNull$$$0(36);
        }
        FileType fileTypeByFileName = FileTypeRegistry.getInstance().getFileTypeByFileName(str);
        if (fileTypeByFileName.isBinary()) {
            throw new RuntimeException("Cannot create binary files from text: name " + str + ", file type " + fileTypeByFileName);
        }
        PsiFile psiFileCreateFileFromText = createFileFromText(str, fileTypeByFileName, str2);
        if (psiFileCreateFileFromText == null) {
            $$$reportNull$$$0(37);
        }
        return psiFileCreateFileFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(FileType fileType, String str, CharSequence charSequence, int i, int i2) {
        LOG.assertTrue(!fileType.isBinary());
        if (i != 0 || i2 != charSequence.length()) {
            charSequence = new CharSequenceSubSequence(charSequence, i, i2);
        }
        return createFileFromText(str, fileType, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
    public PsiFile createFileFromText(CharSequence charSequence, PsiFile psiFile) {
        if (charSequence == null) {
            $$$reportNull$$$0(38);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(39);
        }
        PsiFile psiFileCreateFileFromText = createFileFromText(psiFile.getName(), psiFile.getLanguage(), charSequence, false, true);
        if (psiFileCreateFileFromText != null) {
            psiFileCreateFileFromText.putUserData(ORIGINAL_FILE, psiFile);
        }
        return psiFileCreateFileFromText;
    }

    public PsiElement createElementFromText(String str, Language language, IElementType iElementType, PsiElement psiElement) {
        if (language == null) {
            $$$reportNull$$$0(40);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(41);
        }
        if (str == null) {
            return null;
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(this.myManager, language, psiElement);
        FileElement treeElement = dummyHolderCreateHolder.getTreeElement();
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(language);
        if (parserDefinitionForLanguage == null) {
            throw new AssertionError("No parser definition for " + language);
        }
        Project project = this.myManager.getProject();
        ASTNode aSTNode = parserDefinitionForLanguage.createParser(project).parse(iElementType, PsiBuilderFactory.getInstance().createBuilder(project, treeElement, parserDefinitionForLanguage.createLexer(project), language, str));
        treeElement.rawAddChildren((TreeElement) aSTNode);
        markGenerated(dummyHolderCreateHolder);
        return aSTNode.getPsi();
    }

    public static void markGenerated(PsiElement psiElement) {
        ((TreeElement) psiElement.getNode()).acceptTree(new GeneratedMarkerVisitor());
    }
}
