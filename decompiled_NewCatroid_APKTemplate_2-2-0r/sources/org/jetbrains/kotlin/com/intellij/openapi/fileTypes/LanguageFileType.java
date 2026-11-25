package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.nio.charset.Charset;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LanguageFileType implements FileType {
    private final Language myLanguage;
    private final boolean mySecondary;

    /* JADX WARN: Code duplicated, block: B:19:0x002b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5) ? 2 : 3];
        if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/LanguageFileType";
        } else if (i == 3 || i == 4) {
            objArr[0] = FirebaseAnalytics.Param.CONTENT;
        } else if (i != 5) {
            objArr[0] = "language";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/LanguageFileType";
        }
        if (i == 2) {
            objArr[1] = "getLanguage";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/LanguageFileType";
        } else {
            objArr[1] = "getDisplayName";
        }
        if (i != 2) {
            if (i == 3 || i == 4) {
                objArr[2] = "extractCharsetFromFileContent";
            } else if (i != 5) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Deprecated
    public Charset extractCharsetFromFileContent(Project project, VirtualFile virtualFile, String str) {
        if (str != null) {
            return null;
        }
        $$$reportNull$$$0(3);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public final boolean isBinary() {
        return false;
    }

    @Deprecated
    public boolean isJVMDebuggingSupported() {
        return false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected LanguageFileType(Language language) {
        this(language, false);
        if (language == null) {
            $$$reportNull$$$0(0);
        }
    }

    protected LanguageFileType(Language language, boolean z) {
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        this.myLanguage = language;
        this.mySecondary = z;
    }

    public final Language getLanguage() {
        Language language = this.myLanguage;
        if (language == null) {
            $$$reportNull$$$0(2);
        }
        return language;
    }

    public boolean isSecondary() {
        return this.mySecondary;
    }

    public Charset extractCharsetFromFileContent(Project project, VirtualFile virtualFile, CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        return extractCharsetFromFileContent(project, virtualFile, charSequence.toString());
    }

    public String getDisplayName() {
        String displayName = this.myLanguage.getDisplayName();
        if (displayName == null) {
            $$$reportNull$$$0(5);
        }
        return displayName;
    }
}
