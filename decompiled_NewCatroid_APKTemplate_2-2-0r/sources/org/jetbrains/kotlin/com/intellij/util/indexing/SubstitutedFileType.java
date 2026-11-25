package org.jetbrains.kotlin.com.intellij.util.indexing;

import com.google.firebase.analytics.FirebaseAnalytics;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public final class SubstitutedFileType extends LanguageFileType {
    private final FileType myFileType;
    private final FileType myOriginalFileType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 10:
            case 11:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
                i2 = 2;
                break;
            case 10:
            case 11:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "substitutionFileType";
                break;
            case 2:
                objArr[0] = "substitutedLanguage";
                break;
            case 3:
            case 10:
                objArr[0] = "file";
                break;
            case 4:
                objArr[0] = "fileType";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/indexing/SubstitutedFileType";
                break;
            case 11:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
            default:
                objArr[0] = "originalFileType";
                break;
        }
        switch (i) {
            case 5:
            case 6:
                objArr[1] = "substituteFileType";
                break;
            case 7:
                objArr[1] = "getName";
                break;
            case 8:
                objArr[1] = "getDescription";
                break;
            case 9:
                objArr[1] = "getDefaultExtension";
                break;
            case 10:
            case 11:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/indexing/SubstitutedFileType";
                break;
            case 12:
                objArr[1] = "getOriginalFileType";
                break;
            case 13:
                objArr[1] = "getFileType";
                break;
        }
        switch (i) {
            case 3:
            case 4:
                objArr[2] = "substituteFileType";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
                break;
            case 10:
            case 11:
                objArr[2] = "getCharset";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
                throw new IllegalStateException(str2);
            case 10:
            case 11:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private SubstitutedFileType(FileType fileType, LanguageFileType languageFileType, Language language) {
        super(language);
        if (fileType == null) {
            $$$reportNull$$$0(0);
        }
        if (languageFileType == null) {
            $$$reportNull$$$0(1);
        }
        if (language == null) {
            $$$reportNull$$$0(2);
        }
        this.myOriginalFileType = fileType;
        this.myFileType = languageFileType;
    }

    public static FileType substituteFileType(VirtualFile virtualFile, FileType fileType, Project project) {
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        if (fileType == null) {
            $$$reportNull$$$0(4);
        }
        if (project == null) {
            if (fileType == null) {
                $$$reportNull$$$0(5);
            }
            return fileType;
        }
        if (fileType instanceof LanguageFileType) {
            Language languageForPsi = LanguageUtil.getLanguageForPsi(project, virtualFile, fileType);
            LanguageFileType associatedFileType = (languageForPsi == null || languageForPsi == ((LanguageFileType) fileType).getLanguage()) ? null : languageForPsi.getAssociatedFileType();
            if (associatedFileType != null) {
                return new SubstitutedFileType(fileType, associatedFileType, languageForPsi);
            }
        }
        if (fileType == null) {
            $$$reportNull$$$0(6);
        }
        return fileType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getName() {
        String name2 = this.myFileType.getName();
        if (name2 == null) {
            $$$reportNull$$$0(7);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDescription() {
        String description = this.myFileType.getDescription();
        if (description == null) {
            $$$reportNull$$$0(8);
        }
        return description;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDefaultExtension() {
        String defaultExtension = this.myFileType.getDefaultExtension();
        if (defaultExtension == null) {
            $$$reportNull$$$0(9);
        }
        return defaultExtension;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public Icon getIcon() {
        return this.myFileType.getIcon();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getCharset(VirtualFile virtualFile, byte[] bArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(10);
        }
        if (bArr == null) {
            $$$reportNull$$$0(11);
        }
        return this.myFileType.getCharset(virtualFile, bArr);
    }

    public FileType getOriginalFileType() {
        FileType fileType = this.myOriginalFileType;
        if (fileType == null) {
            $$$reportNull$$$0(12);
        }
        return fileType;
    }

    public String toString() {
        return "SubstitutedFileType: original=" + this.myOriginalFileType + "; substituted=" + this.myFileType;
    }
}
