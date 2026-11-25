package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: FilePreprocessor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/FilePreprocessor;", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "extensions", "", "Lorg/jetbrains/kotlin/resolve/FilePreprocessorExtension;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Ljava/lang/Iterable;)V", "preprocessFile", "", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "registerFileByPackage", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FilePreprocessor {
    private final Iterable<FilePreprocessorExtension> extensions;
    private final BindingTrace trace;

    /* JADX WARN: Multi-variable type inference failed */
    public FilePreprocessor(BindingTrace trace, Iterable<? extends FilePreprocessorExtension> extensions) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        this.trace = trace;
        this.extensions = extensions;
    }

    public final void preprocessFile(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        registerFileByPackage(file);
        Iterator<FilePreprocessorExtension> it = this.extensions.iterator();
        while (it.hasNext()) {
            it.next().preprocessFile(file);
        }
    }

    private final void registerFileByPackage(KtFile file) {
        BindingTrace bindingTrace = this.trace;
        WritableSlice<FqName, Collection<KtFile>> PACKAGE_TO_FILES = BindingContext.PACKAGE_TO_FILES;
        Intrinsics.checkNotNullExpressionValue(PACKAGE_TO_FILES, "PACKAGE_TO_FILES");
        FilePreprocessorKt.addElementToSlice(bindingTrace, PACKAGE_TO_FILES, file.getPackageFqName(), file);
    }
}
