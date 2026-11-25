package org.jetbrains.kotlin.incremental.storage;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: RelativeFileToPathConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0016\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0006H\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0003H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/RelativeFileToPathConverter;", "Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;", "baseDirFile", "Ljava/io/File;", "(Ljava/io/File;)V", "baseDirPath", "", "toFile", "path", "toPath", "file", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class RelativeFileToPathConverter implements FileToPathConverter {
    private static final Companion Companion = new Companion(null);
    private final String baseDirPath;

    public RelativeFileToPathConverter(File file) {
        File fileNormalize;
        this.baseDirPath = (file == null || (fileNormalize = FilesKt.normalize(file)) == null) ? null : FilesKt.getInvariantSeparatorsPath(fileNormalize);
    }

    @Override // org.jetbrains.kotlin.incremental.storage.FileToPathConverter
    public String toPath(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        String invariantSeparatorsPath = FilesKt.getInvariantSeparatorsPath(FilesKt.normalize(file));
        String str = this.baseDirPath;
        if (str == null || !StringsKt.startsWith$default(invariantSeparatorsPath, str, false, 2, (Object) null)) {
            return invariantSeparatorsPath;
        }
        StringBuilder sb = new StringBuilder("$PROJECT_DIR$");
        String strSubstring = invariantSeparatorsPath.substring(this.baseDirPath.length());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return sb.append(strSubstring).toString();
    }

    @Override // org.jetbrains.kotlin.incremental.storage.FileToPathConverter
    public File toFile(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        if (StringsKt.startsWith$default(path, "$PROJECT_DIR$", false, 2, (Object) null)) {
            String str = this.baseDirPath;
            if (str == null) {
                throw new IllegalStateException("Could not get project root dir".toString());
            }
            StringBuilder sbAppend = new StringBuilder().append(str);
            String strSubstring = path.substring(13);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return new File(sbAppend.append(strSubstring).toString());
        }
        return new File(path);
    }

    /* JADX INFO: compiled from: RelativeFileToPathConverter.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/RelativeFileToPathConverter$Companion;", "", "()V", "PROJECT_DIR_PLACEHOLDER", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
