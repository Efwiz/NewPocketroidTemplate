package org.jetbrains.kotlin.js.sourceMap;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.util.TextOutput;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"addSourceMappingURL", "", "Lorg/jetbrains/kotlin/js/util/TextOutput;", "outputJsFile", "Ljava/io/File;", "js.sourcemap"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final void addSourceMappingURL(TextOutput textOutput, File outputJsFile) {
        Intrinsics.checkNotNullParameter(textOutput, "<this>");
        Intrinsics.checkNotNullParameter(outputJsFile, "outputJsFile");
        textOutput.print("\n//# sourceMappingURL=");
        textOutput.print(outputJsFile.getName());
        textOutput.print(".map\n");
    }
}
