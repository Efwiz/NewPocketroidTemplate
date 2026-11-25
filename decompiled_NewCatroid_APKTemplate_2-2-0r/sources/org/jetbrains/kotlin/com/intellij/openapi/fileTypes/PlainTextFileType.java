package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;

/* JADX INFO: loaded from: classes3.dex */
public final class PlainTextFileType extends LanguageFileType {
    public static final PlainTextFileType INSTANCE = new PlainTextFileType();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/PlainTextFileType";
        if (i == 1) {
            objArr[1] = "getIcon";
        } else if (i != 2) {
            objArr[1] = "getDescription";
        } else {
            objArr[1] = "getExtensionMode";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    private PlainTextFileType() {
        super(PlainTextLanguage.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getName() {
        return "PLAIN_TEXT";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDescription() {
        String strMessage = CoreBundle.message("filetype.plaintext.description", new Object[0]);
        if (strMessage == null) {
            $$$reportNull$$$0(0);
        }
        return strMessage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDefaultExtension() {
        return "txt";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public Icon getIcon() {
        Icon icon = AllIcons.FileTypes.Text;
        if (icon == null) {
            $$$reportNull$$$0(1);
        }
        return icon;
    }
}
