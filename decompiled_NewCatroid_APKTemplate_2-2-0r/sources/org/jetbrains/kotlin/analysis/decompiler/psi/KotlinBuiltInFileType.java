package org.jetbrains.kotlin.analysis.decompiler.psi;

import com.google.firebase.analytics.FirebaseAnalytics;
import javax.swing.Icon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.idea.KotlinIconProviderService;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: KotlinBuiltInFileType.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u0004H\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinBuiltInFileType;", "Lorg/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileType;", "()V", "DEFAULT_DESCRIPTION", "", "getCharset", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", FirebaseAnalytics.Param.CONTENT, "", "getDefaultExtension", "getDescription", "getIcon", "Ljavax/swing/Icon;", "getName", "isBinary", "", "isReadOnly", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinBuiltInFileType implements FileType {
    public static final KotlinBuiltInFileType INSTANCE = new KotlinBuiltInFileType();

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getCharset(VirtualFile file, byte[] content) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(content, "content");
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public boolean isBinary() {
        return true;
    }

    public boolean isReadOnly() {
        return true;
    }

    private KotlinBuiltInFileType() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getName() {
        return BuiltInSerializerProtocol.BUILTINS_FILE_EXTENSION;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDescription() {
        String labelForBuiltInFileType;
        KotlinLabelProviderService service = KotlinLabelProviderService.INSTANCE.getService();
        return (service == null || (labelForBuiltInFileType = service.getLabelForBuiltInFileType()) == null) ? "Kotlin built-in declarations" : labelForBuiltInFileType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDefaultExtension() {
        return BuiltInSerializerProtocol.BUILTINS_FILE_EXTENSION;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public Icon getIcon() {
        Icon builtInFileIcon = KotlinIconProviderService.getInstance().getBuiltInFileIcon();
        Intrinsics.checkNotNullExpressionValue(builtInFileIcon, "getBuiltInFileIcon(...)");
        return builtInFileIcon;
    }
}
