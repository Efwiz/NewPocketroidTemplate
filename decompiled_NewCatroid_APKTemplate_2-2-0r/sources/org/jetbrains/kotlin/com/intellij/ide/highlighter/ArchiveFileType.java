package org.jetbrains.kotlin.com.intellij.ide.highlighter;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;

/* JADX INFO: loaded from: classes3.dex */
public class ArchiveFileType implements FileType {
    public static final ArchiveFileType INSTANCE = new ArchiveFileType();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/ide/highlighter/ArchiveFileType";
        if (i != 1) {
            objArr[1] = "getDescription";
        } else {
            objArr[1] = "getDisplayName";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public boolean isBinary() {
        return true;
    }

    protected ArchiveFileType() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getName() {
        return "ARCHIVE";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDescription() {
        String strMessage = CoreBundle.message("filetype.archive.description", new Object[0]);
        if (strMessage == null) {
            $$$reportNull$$$0(0);
        }
        return strMessage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDefaultExtension() {
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public Icon getIcon() {
        return AllIcons.FileTypes.Archive;
    }
}
