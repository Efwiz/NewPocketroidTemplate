package org.jetbrains.kotlin.load.java.components;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.FilePreprocessorExtension;
import org.jetbrains.kotlin.resolve.FilePreprocessorKt;
import org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice;
import org.jetbrains.kotlin.util.slicedMap.Slices;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: FilesByFacadeFqNameIndexer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/FilesByFacadeFqNameIndexer;", "Lorg/jetbrains/kotlin/resolve/FilePreprocessorExtension;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "preprocessFile", "", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FilesByFacadeFqNameIndexer implements FilePreprocessorExtension {
    public static final WritableSlice<FqName, Collection<KtFile>> FACADE_FILES_BY_FQ_NAME;
    public static final WritableSlice<FqName, Collection<KtFile>> FACADE_FILES_BY_PACKAGE_NAME;
    private final BindingTrace trace;

    public FilesByFacadeFqNameIndexer(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.trace = trace;
    }

    @Override // org.jetbrains.kotlin.resolve.FilePreprocessorExtension
    public void preprocessFile(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (file.hasTopLevelCallables()) {
            FilePreprocessorKt.addElementToSlice(this.trace, FACADE_FILES_BY_FQ_NAME, JvmFileClassUtilKt.getJavaFileFacadeFqName(file), file);
            BindingTrace bindingTrace = this.trace;
            WritableSlice<FqName, Collection<KtFile>> writableSlice = FACADE_FILES_BY_PACKAGE_NAME;
            FqName fqNameParent = JvmFileClassUtilKt.getJavaFileFacadeFqName(file).parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            FilePreprocessorKt.addElementToSlice(bindingTrace, writableSlice, fqNameParent, file);
        }
    }

    static {
        WritableSlice<FqName, Collection<KtFile>> writableSliceCreateSimpleSlice = Slices.createSimpleSlice();
        Intrinsics.checkNotNullExpressionValue(writableSliceCreateSimpleSlice, "createSimpleSlice(...)");
        FACADE_FILES_BY_FQ_NAME = writableSliceCreateSimpleSlice;
        WritableSlice<FqName, Collection<KtFile>> writableSliceCreateSimpleSlice2 = Slices.createSimpleSlice();
        Intrinsics.checkNotNullExpressionValue(writableSliceCreateSimpleSlice2, "createSimpleSlice(...)");
        FACADE_FILES_BY_PACKAGE_NAME = writableSliceCreateSimpleSlice2;
        BasicWritableSlice.initSliceDebugNames(FilesByFacadeFqNameIndexer.class);
    }
}
