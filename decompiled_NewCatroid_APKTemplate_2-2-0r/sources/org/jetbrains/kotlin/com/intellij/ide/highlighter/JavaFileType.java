package org.jetbrains.kotlin.com.intellij.ide.highlighter;

import javax.swing.Icon;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaFileType extends LanguageFileType {
    public static final JavaFileType INSTANCE = new JavaFileType();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/ide/highlighter/JavaFileType", "getDescription"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType
    public boolean isJVMDebuggingSupported() {
        return true;
    }

    private JavaFileType() {
        super(JavaLanguage.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getName() {
        return SuffixConstants.EXTENSION_JAVA;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDescription() {
        String strMessage = JavaPsiBundle.message("filetype.java.description", new Object[0]);
        if (strMessage == null) {
            $$$reportNull$$$0(0);
        }
        return strMessage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDefaultExtension() {
        return SuffixConstants.EXTENSION_java;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public Icon getIcon() {
        return AllIcons.FileTypes.Java;
    }
}
