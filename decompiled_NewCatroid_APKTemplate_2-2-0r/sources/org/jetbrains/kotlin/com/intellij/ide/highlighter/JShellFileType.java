package org.jetbrains.kotlin.com.intellij.ide.highlighter;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;
import org.jetbrains.kotlin.com.intellij.lang.java.JShellLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;

/* JADX INFO: loaded from: classes3.dex */
public final class JShellFileType extends LanguageFileType {
    public static final JShellFileType INSTANCE = new JShellFileType();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/ide/highlighter/JShellFileType", "getDescription"));
    }

    private JShellFileType() {
        super(JShellLanguage.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getName() {
        return "JSHELL";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDescription() {
        String strMessage = JavaPsiBundle.message("filetype.jshell.description", new Object[0]);
        if (strMessage == null) {
            $$$reportNull$$$0(0);
        }
        return strMessage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDefaultExtension() {
        return "snippet";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public Icon getIcon() {
        return AllIcons.FileTypes.Java;
    }
}
