package kotlin.script.experimental.jvm.util;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmClasspathUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lkotlin/script/experimental/jvm/util/ClassLoaderResourceRootFIlePathCalculator;", "", "keyResourcePath", "", "(Ljava/lang/String;)V", "keyResourcePathDepth", "", "invoke", "Ljava/io/File;", "resourceFile", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassLoaderResourceRootFIlePathCalculator {
    private final String keyResourcePath;
    private int keyResourcePathDepth;

    public ClassLoaderResourceRootFIlePathCalculator(String keyResourcePath) {
        Intrinsics.checkNotNullParameter(keyResourcePath, "keyResourcePath");
        this.keyResourcePath = keyResourcePath;
        this.keyResourcePathDepth = -1;
    }

    public final File invoke(File resourceFile) {
        int i;
        Intrinsics.checkNotNullParameter(resourceFile, "resourceFile");
        if (this.keyResourcePathDepth < 0) {
            if (StringsKt.isBlank(this.keyResourcePath)) {
                i = 0;
            } else {
                String strTrim = StringsKt.trim(this.keyResourcePath, '/');
                int i2 = 0;
                for (int i3 = 0; i3 < strTrim.length(); i3++) {
                    if (strTrim.charAt(i3) == '/') {
                        i2++;
                    }
                }
                i = i2 + 1;
            }
            this.keyResourcePathDepth = i;
        }
        int i4 = this.keyResourcePathDepth;
        for (int i5 = 0; i5 < i4; i5++) {
            resourceFile = resourceFile.getParentFile();
            Intrinsics.checkNotNullExpressionValue(resourceFile, "getParentFile(...)");
        }
        return resourceFile;
    }
}
