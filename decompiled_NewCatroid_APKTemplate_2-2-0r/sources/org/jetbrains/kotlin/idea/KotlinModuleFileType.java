package org.jetbrains.kotlin.idea;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.function.Supplier;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes4.dex */
public class KotlinModuleFileType implements FileType {
    public static final String EXTENSION = "kotlin_module";
    public static final KotlinModuleFileType INSTANCE = new KotlinModuleFileType();
    private final NotNullLazyValue<Icon> myIcon = NotNullLazyValue.lazy(new Supplier() { // from class: org.jetbrains.kotlin.idea.KotlinModuleFileType$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return KotlinIconProviderService.getInstance().getFileIcon();
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "file";
        } else {
            objArr[0] = FirebaseAnalytics.Param.CONTENT;
        }
        objArr[1] = "org/jetbrains/kotlin/idea/KotlinModuleFileType";
        objArr[2] = "getCharset";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getCharset(VirtualFile virtualFile, byte[] bArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (bArr != null) {
            return null;
        }
        $$$reportNull$$$0(1);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public boolean isBinary() {
        return true;
    }

    public boolean isReadOnly() {
        return true;
    }

    private KotlinModuleFileType() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getName() {
        return "kotlin_module";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDescription() {
        return "Kotlin module info: contains package part mappings";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDefaultExtension() {
        return "kotlin_module";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public Icon getIcon() {
        return this.myIcon.getValue();
    }
}
