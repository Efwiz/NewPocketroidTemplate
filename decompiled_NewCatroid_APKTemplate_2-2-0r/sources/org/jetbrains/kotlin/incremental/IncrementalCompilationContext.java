package org.jetbrains.kotlin.incremental;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.DoNothingICReporter;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.incremental.storage.FileToAbsolutePathConverter;
import org.jetbrains.kotlin.incremental.storage.FileToPathConverter;

/* JADX INFO: compiled from: IncrementalCompilationContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001BA\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005¢\u0006\u0002\u0010\fBK\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005¢\u0006\u0002\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "", "pathConverter", "Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;", "storeFullFqNamesInLookupCache", "", "transaction", "Lorg/jetbrains/kotlin/incremental/CompilationTransaction;", "reporter", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "trackChangesInLookupCache", "keepIncrementalCompilationCachesInMemory", "(Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;ZLorg/jetbrains/kotlin/incremental/CompilationTransaction;Lorg/jetbrains/kotlin/build/report/ICReporter;ZZ)V", "pathConverterForSourceFiles", "pathConverterForOutputFiles", "(Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;ZLorg/jetbrains/kotlin/incremental/CompilationTransaction;Lorg/jetbrains/kotlin/build/report/ICReporter;ZZ)V", "getKeepIncrementalCompilationCachesInMemory", "()Z", "getPathConverter", "()Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;", "getPathConverterForOutputFiles", "getPathConverterForSourceFiles", "getReporter", "()Lorg/jetbrains/kotlin/build/report/ICReporter;", "getStoreFullFqNamesInLookupCache", "getTrackChangesInLookupCache", "getTransaction", "()Lorg/jetbrains/kotlin/incremental/CompilationTransaction;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalCompilationContext {
    private final boolean keepIncrementalCompilationCachesInMemory;
    private final FileToPathConverter pathConverter;
    private final FileToPathConverter pathConverterForOutputFiles;
    private final FileToPathConverter pathConverterForSourceFiles;
    private final ICReporter reporter;
    private final boolean storeFullFqNamesInLookupCache;
    private final boolean trackChangesInLookupCache;
    private final CompilationTransaction transaction;

    public IncrementalCompilationContext() {
        this(null, null, false, null, null, false, false, 127, null);
    }

    public IncrementalCompilationContext(FileToPathConverter pathConverterForSourceFiles, FileToPathConverter pathConverterForOutputFiles, boolean z, CompilationTransaction transaction, ICReporter reporter, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(pathConverterForSourceFiles, "pathConverterForSourceFiles");
        Intrinsics.checkNotNullParameter(pathConverterForOutputFiles, "pathConverterForOutputFiles");
        Intrinsics.checkNotNullParameter(transaction, "transaction");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        this.pathConverterForSourceFiles = pathConverterForSourceFiles;
        this.pathConverterForOutputFiles = pathConverterForOutputFiles;
        this.storeFullFqNamesInLookupCache = z;
        this.transaction = transaction;
        this.reporter = reporter;
        this.trackChangesInLookupCache = z2;
        this.keepIncrementalCompilationCachesInMemory = z3;
        this.pathConverter = pathConverterForSourceFiles;
    }

    public /* synthetic */ IncrementalCompilationContext(FileToAbsolutePathConverter fileToAbsolutePathConverter, FileToAbsolutePathConverter fileToAbsolutePathConverter2, boolean z, NonRecoverableCompilationTransaction nonRecoverableCompilationTransaction, DoNothingICReporter doNothingICReporter, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? FileToAbsolutePathConverter.INSTANCE : fileToAbsolutePathConverter, (i & 2) != 0 ? FileToAbsolutePathConverter.INSTANCE : fileToAbsolutePathConverter2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? new NonRecoverableCompilationTransaction() : nonRecoverableCompilationTransaction, (i & 16) != 0 ? DoNothingICReporter.INSTANCE : doNothingICReporter, (i & 32) != 0 ? false : z2, (i & 64) == 0 ? z3 : false);
    }

    public final FileToPathConverter getPathConverterForSourceFiles() {
        return this.pathConverterForSourceFiles;
    }

    public final FileToPathConverter getPathConverterForOutputFiles() {
        return this.pathConverterForOutputFiles;
    }

    public final boolean getStoreFullFqNamesInLookupCache() {
        return this.storeFullFqNamesInLookupCache;
    }

    public final CompilationTransaction getTransaction() {
        return this.transaction;
    }

    public final ICReporter getReporter() {
        return this.reporter;
    }

    public final boolean getTrackChangesInLookupCache() {
        return this.trackChangesInLookupCache;
    }

    public final boolean getKeepIncrementalCompilationCachesInMemory() {
        return this.keepIncrementalCompilationCachesInMemory;
    }

    public /* synthetic */ IncrementalCompilationContext(FileToPathConverter fileToPathConverter, boolean z, CompilationTransaction compilationTransaction, ICReporter iCReporter, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(fileToPathConverter, (i & 2) != 0 ? false : z, (i & 4) != 0 ? new NonRecoverableCompilationTransaction() : compilationTransaction, (i & 8) != 0 ? DoNothingICReporter.INSTANCE : iCReporter, (i & 16) != 0 ? false : z2, (i & 32) == 0 ? z3 : false);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @Deprecated(message = "This constructor is scheduled to be removed. KSP is using it")
    public IncrementalCompilationContext(FileToPathConverter pathConverter, boolean z, CompilationTransaction transaction, ICReporter reporter, boolean z2, boolean z3) {
        this(pathConverter, pathConverter, z, transaction, reporter, z2, z3);
        Intrinsics.checkNotNullParameter(pathConverter, "pathConverter");
        Intrinsics.checkNotNullParameter(transaction, "transaction");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
    }

    public final FileToPathConverter getPathConverter() {
        return this.pathConverter;
    }
}
