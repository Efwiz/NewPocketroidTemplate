package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;

/* JADX INFO: loaded from: classes3.dex */
public final class UnknownFileType implements FileType {
    public static final FileType INSTANCE = new UnknownFileType();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/UnknownFileType";
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

    private UnknownFileType() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getName() {
        return GrsBaseInfo.CountryCodeSource.UNKNOWN;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDescription() {
        String strMessage = CoreBundle.message("filetype.unknown.description", new Object[0]);
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
        return AllIcons.FileTypes.Unknown;
    }
}
