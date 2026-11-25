package org.jetbrains.kotlin.com.intellij.util.indexing;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.nio.charset.Charset;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.TreeBackedLighterAST;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.impl.LoadTextUtil;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.DefaultProjectFactory;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullComputable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.LanguageSubstitutors;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public final class FileContentImpl extends IndexedFileImpl implements PsiDependentFileContent {
    private static final Key<PsiFile> CACHED_PSI = Key.create("cached psi from content");
    private static final Key<LighterAST> LIGHTER_AST_NODE_KEY = Key.create("lighter.ast.node");
    private Charset myCharset;
    private byte[] myContent;
    private CharSequence myContentAsText;
    private final NotNullComputable<byte[]> myContentComputable;
    private byte[] myIndexedFileHash;
    private boolean myLighterASTShouldBeThreadSafe;
    private final boolean myPhysicalContent;

    /* JADX WARN: Code duplicated, block: B:11:0x001c A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 10 && i != 17 && i != 20 && i != 34) {
            switch (i) {
                default:
                    switch (i) {
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 10 && i != 17 && i != 20 && i != 34) {
            switch (i) {
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 7:
                objArr[0] = "fileType";
                break;
            case 2:
            case 14:
            case 16:
                objArr[0] = "contentComputable";
                break;
            case 3:
            case 10:
            case 17:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/indexing/FileContentImpl";
                break;
            case 4:
            case 6:
                objArr[0] = "text";
                break;
            case 5:
                objArr[0] = "project";
                break;
            case 8:
            case 11:
            case 13:
            case 15:
            case 18:
            case 19:
            case 21:
            default:
                objArr[0] = "file";
                break;
            case 9:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 12:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
            case 22:
                objArr[0] = "contentAsText";
                break;
            case 28:
                objArr[0] = "fileContentHash";
                break;
            case 33:
                objArr[0] = "indexedFile";
                break;
        }
        if (i == 3) {
            objArr[1] = "getLighterAST";
        } else if (i == 10) {
            objArr[1] = "createFileFromText";
        } else if (i == 17) {
            objArr[1] = "createByContent";
        } else if (i == 20) {
            objArr[1] = "createByFile";
        } else if (i != 34) {
            switch (i) {
                case 23:
                    objArr[1] = "createByText";
                    break;
                case 24:
                    objArr[1] = "getCharset";
                    break;
                case 25:
                    objArr[1] = "getContent";
                    break;
                case 26:
                case 27:
                    objArr[1] = "getContentAsText";
                    break;
                default:
                    switch (i) {
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                            objArr[1] = "getPsiFile";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/indexing/FileContentImpl";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getFileTypeWithoutSubstitution";
        }
        switch (i) {
            case 3:
            case 10:
            case 17:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "createFileFromText";
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "createByContent";
                break;
            case 18:
            case 19:
                objArr[2] = "createByFile";
                break;
            case 21:
            case 22:
                objArr[2] = "createByText";
                break;
            case 28:
                objArr[2] = "setIndexedFileHash";
                break;
            case 33:
                objArr[2] = "getFileTypeWithoutSubstitution";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 10 && i != 17 && i != 20 && i != 34) {
            switch (i) {
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                    break;
                default:
                    switch (i) {
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ byte[] lambda$createByContent$0(byte[] bArr) {
        return bArr;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private FileContentImpl(VirtualFile virtualFile, FileType fileType, CharSequence charSequence, NotNullComputable<byte[]> notNullComputable, boolean z) {
        super(virtualFile, fileType, null);
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (fileType == null) {
            $$$reportNull$$$0(1);
        }
        if (notNullComputable == null) {
            $$$reportNull$$$0(2);
        }
        this.myContentAsText = charSequence;
        this.myContentComputable = notNullComputable;
        this.myPhysicalContent = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.indexing.PsiDependentFileContent
    public LighterAST getLighterAST() {
        Key<LighterAST> key = LIGHTER_AST_NODE_KEY;
        LighterAST treeBackedLighterAST = (LighterAST) getUserData(key);
        if (treeBackedLighterAST == null) {
            FileASTNode node = getPsiFile().getNode();
            treeBackedLighterAST = this.myLighterASTShouldBeThreadSafe ? new TreeBackedLighterAST(node) : node.getLighterAST();
            putUserData(key, treeBackedLighterAST);
        }
        if (treeBackedLighterAST == null) {
            $$$reportNull$$$0(3);
        }
        return treeBackedLighterAST;
    }

    private PsiFile createFileFromText(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        Project project = getProject();
        if (project == null) {
            project = DefaultProjectFactory.getInstance().getDefaultProject();
        }
        FileType fileTypeWithoutSubstitution = getFileTypeWithoutSubstitution(this);
        if (!(fileTypeWithoutSubstitution instanceof LanguageFileType)) {
            throw new AssertionError("PSI can be created only for a file with LanguageFileType but actual is " + fileTypeWithoutSubstitution.getClass() + ".\nPlease use a proper FileBasedIndexExtension#getInputFilter() implementation for the caller index");
        }
        return createFileFromText(project, charSequence, (LanguageFileType) fileTypeWithoutSubstitution, this.myFile, getFileName());
    }

    public static PsiFile createFileFromText(Project project, CharSequence charSequence, LanguageFileType languageFileType, VirtualFile virtualFile, String str) {
        if (project == null) {
            $$$reportNull$$$0(5);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(6);
        }
        if (languageFileType == null) {
            $$$reportNull$$$0(7);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(8);
        }
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        Language language = languageFileType.getLanguage();
        Language languageSubstituteLanguage = LanguageSubstitutors.getInstance().substituteLanguage(language, virtualFile, project);
        PsiFile psiFileCreateFileFromText = PsiFileFactory.getInstance(project).createFileFromText(str, languageSubstituteLanguage, charSequence, false, false, false, virtualFile);
        if (psiFileCreateFileFromText == null) {
            throw new IllegalStateException("psiFile is null. language = " + language.getID() + ", substitutedLanguage = " + languageSubstituteLanguage.getID());
        }
        if (psiFileCreateFileFromText == null) {
            $$$reportNull$$$0(10);
        }
        return psiFileCreateFileFromText;
    }

    public static FileContent createByContent(VirtualFile virtualFile, final byte[] bArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(11);
        }
        if (bArr == null) {
            $$$reportNull$$$0(12);
        }
        return new FileContentImpl(virtualFile, FileTypeRegistry.getInstance().getFileTypeByFile(virtualFile, bArr), null, new NotNullComputable() { // from class: org.jetbrains.kotlin.com.intellij.util.indexing.FileContentImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullComputable, org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return FileContentImpl.lambda$createByContent$0(bArr);
            }
        }, true);
    }

    public static FileContent createByFile(VirtualFile virtualFile) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(18);
        }
        return createByFile(virtualFile, null);
    }

    public static FileContent createByFile(VirtualFile virtualFile, Project project) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(19);
        }
        FileContentImpl fileContentImpl = (FileContentImpl) createByContent(virtualFile, virtualFile.contentsToByteArray(false));
        if (project != null) {
            fileContentImpl.setProject(project);
        }
        if (fileContentImpl == null) {
            $$$reportNull$$$0(20);
        }
        return fileContentImpl;
    }

    public static FileContent createByText(VirtualFile virtualFile, CharSequence charSequence, Project project) {
        if (virtualFile == null) {
            $$$reportNull$$$0(21);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(22);
        }
        FileContentImpl fileContentImpl = new FileContentImpl(virtualFile, FileTypeRegistry.getInstance().getFileTypeByFile(virtualFile), charSequence, new NotNullComputable() { // from class: org.jetbrains.kotlin.com.intellij.util.indexing.FileContentImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullComputable, org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return FileContentImpl.lambda$createByText$1();
            }
        }, false);
        if (project != null) {
            fileContentImpl.setProject(project);
        }
        return fileContentImpl;
    }

    static /* synthetic */ byte[] lambda$createByText$1() {
        throw new IllegalStateException("Content must be converted from 'contentAsText'");
    }

    public Charset getCharset() {
        Charset charset = this.myCharset;
        if (charset == null) {
            charset = this.myFile.getCharset();
            this.myCharset = charset;
        }
        if (charset == null) {
            $$$reportNull$$$0(24);
        }
        return charset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.indexing.FileContent
    public byte[] getContent() {
        if (this.myContent == null) {
            CharSequence charSequence = this.myContentAsText;
            if (charSequence != null) {
                this.myContent = charSequence.toString().getBytes(getCharset());
            } else {
                this.myContent = this.myContentComputable.compute();
                if (!getFileTypeWithoutSubstitution(this).isBinary()) {
                    this.myContent = getContentAsText().toString().getBytes(getCharset());
                }
            }
        }
        byte[] bArr = this.myContent;
        if (bArr == null) {
            $$$reportNull$$$0(25);
        }
        return bArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.indexing.FileContent
    public CharSequence getContentAsText() {
        FileType fileTypeWithoutSubstitution = getFileTypeWithoutSubstitution(this);
        if (fileTypeWithoutSubstitution.isBinary()) {
            throw new UnsupportedOperationException("Cannot obtain text for binary file type : " + fileTypeWithoutSubstitution.getDescription());
        }
        CharSequence charSequence = (CharSequence) getUserData(IndexingDataKeys.FILE_TEXT_CONTENT_KEY);
        if (charSequence == null) {
            try {
                if (this.myContentAsText == null) {
                    this.myContentAsText = LoadTextUtil.getTextByBinaryPresentation(getContent(), this.myFile);
                }
                CharSequence charSequence2 = this.myContentAsText;
                this.myContent = null;
                if (charSequence2 == null) {
                    $$$reportNull$$$0(27);
                }
                return charSequence2;
            } catch (Throwable th) {
                this.myContent = null;
                throw th;
            }
        }
        this.myContent = null;
        if (charSequence == null) {
            $$$reportNull$$$0(26);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.indexing.IndexedFileImpl, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "FileContentImpl(" + getFileName() + Constant.AFTER_QUTO;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.indexing.PsiDependentFileContent
    public PsiFile getPsiFile() {
        Document cachedDocument;
        PsiFile psiFile;
        if (!this.myPhysicalContent && (cachedDocument = FileDocumentManager.getInstance().getCachedDocument(getFile())) != null) {
            PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(getProject());
            if (psiDocumentManager.isUncommited(cachedDocument) && (psiFile = psiDocumentManager.getPsiFile(cachedDocument)) != null) {
                if (psiFile == null) {
                    $$$reportNull$$$0(29);
                }
                return psiFile;
            }
        }
        PsiFile psiFile2 = (PsiFile) getUserData(IndexingDataKeys.PSI_FILE);
        if (psiFile2 != null) {
            if (psiFile2 == null) {
                $$$reportNull$$$0(30);
            }
            return psiFile2;
        }
        Key<PsiFile> key = CACHED_PSI;
        PsiFile psiFile3 = (PsiFile) getUserData(key);
        if (psiFile3 != null) {
            if (psiFile3 == null) {
                $$$reportNull$$$0(31);
            }
            return psiFile3;
        }
        PsiFile psiFileCreateFileFromText = createFileFromText(getContentAsText());
        psiFileCreateFileFromText.putUserData(IndexingDataKeys.VIRTUAL_FILE, getFile());
        putUserData(key, psiFileCreateFileFromText);
        if (psiFileCreateFileFromText == null) {
            $$$reportNull$$$0(32);
        }
        return psiFileCreateFileFromText;
    }

    public static FileType getFileTypeWithoutSubstitution(IndexedFile indexedFile) {
        if (indexedFile == null) {
            $$$reportNull$$$0(33);
        }
        FileType fileType = indexedFile.getFileType();
        if (fileType instanceof SubstitutedFileType) {
            fileType = ((SubstitutedFileType) fileType).getOriginalFileType();
        }
        if (fileType == null) {
            $$$reportNull$$$0(34);
        }
        return fileType;
    }
}
