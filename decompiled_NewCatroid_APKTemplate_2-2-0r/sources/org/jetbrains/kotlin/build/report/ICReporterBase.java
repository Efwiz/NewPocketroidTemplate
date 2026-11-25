package org.jetbrains.kotlin.build.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ICReporterBase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\b&\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J!\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\b\"\u00020\u0003H\u0004¢\u0006\u0002\u0010\tJ\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0004J\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0004J\u001e\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u0010\u001a\u00020\u0006H\u0016J\u001e\u0010\u0011\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u0012\u001a\u00020\u0006H\u0016J&\u0010\u0013\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0006H\u0016J\f\u0010\u0016\u001a\u00020\u0003*\u00020\u0003H\u0004R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/build/report/ICReporterBase;", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "pathsBase", "Ljava/io/File;", "(Ljava/io/File;)V", "pathsAsString", "", "files", "", "([Ljava/io/File;)Ljava/lang/String;", "", "relativizeIfPossible", "", "reportMarkDirty", "", "affectedFiles", "reason", "reportMarkDirtyClass", "classFqName", "reportMarkDirtyMember", "scope", "name", "relativeOrAbsolute", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ICReporterBase implements ICReporter {
    private final File pathsBase;

    /* JADX WARN: Multi-variable type inference failed */
    public ICReporterBase() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public ICReporterBase(File file) {
        this.pathsBase = file;
    }

    public /* synthetic */ ICReporterBase(File file, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : file);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyClass(Iterable<? extends File> affectedFiles, String classFqName) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        reportMarkDirty(affectedFiles, "dirty class " + classFqName);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyMember(Iterable<? extends File> affectedFiles, String scope, String name2) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(name2, "name");
        reportMarkDirty(affectedFiles, "dirty member " + scope + '#' + name2);
    }

    protected final String pathsAsString(Iterable<? extends File> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        List<File> listRelativizeIfPossible = relativizeIfPossible(files);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listRelativizeIfPossible, 10));
        Iterator<T> it = listRelativizeIfPossible.iterator();
        while (it.hasNext()) {
            arrayList.add(((File) it.next()).getPath());
        }
        return CollectionsKt.joinToString$default(CollectionsKt.sorted(arrayList), null, null, null, 0, null, null, 63, null);
    }

    protected final String pathsAsString(File... files) {
        Intrinsics.checkNotNullParameter(files, "files");
        return pathsAsString(ArraysKt.toList(files));
    }

    protected final File relativeOrAbsolute(File file) {
        File fileRelativeToOrNull;
        Intrinsics.checkNotNullParameter(file, "<this>");
        File file2 = this.pathsBase;
        if (file2 != null && (fileRelativeToOrNull = FilesKt.relativeToOrNull(file, file2)) != null) {
            return fileRelativeToOrNull;
        }
        File absoluteFile = FilesKt.normalize(file).getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
        return absoluteFile;
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirty(Iterable<? extends File> affectedFiles, final String reason) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(reason, "reason");
        for (final File file : affectedFiles) {
            ICReporterKt.debug(this, new Function0<String>() { // from class: org.jetbrains.kotlin.build.report.ICReporterBase$reportMarkDirty$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return this.this$0.pathsAsString(file) + " is marked dirty: " + reason;
                }
            });
        }
    }

    protected final List<File> relativizeIfPossible(Iterable<? extends File> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(files, 10));
        Iterator<? extends File> it = files.iterator();
        while (it.hasNext()) {
            arrayList.add(relativeOrAbsolute(it.next()));
        }
        return arrayList;
    }
}
