package org.jetbrains.kotlin.incremental;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.BuildReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;

/* JADX INFO: compiled from: CompilationTransaction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B!\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\bH\u0016J\b\u0010\u0014\u001a\u00020\bH\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\bH\u0002J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\bH\u0016J\b\u0010\u0018\u001a\u00020\u0010H\u0002J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\bH\u0002R.\u0010\n\u001a\"\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000bj\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/incremental/RecoverableCompilationTransaction;", "Lorg/jetbrains/kotlin/incremental/CompilationTransaction;", "Lorg/jetbrains/kotlin/incremental/BaseCompilationTransaction;", "reporter", "Lorg/jetbrains/kotlin/build/report/BuildReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "stashDir", "Ljava/nio/file/Path;", "(Lorg/jetbrains/kotlin/build/report/BuildReporter;Ljava/nio/file/Path;)V", "fileRelocationRegistry", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "filesCounter", "", "cleanupStash", "", "close", "deleteFile", "outputFile", "getNextRelocatedFilePath", "isFileRelocationIsAlreadyRegisteredFor", "", "registerAddedOrChangedFile", "revertChanges", "stashFile", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RecoverableCompilationTransaction extends BaseCompilationTransaction implements CompilationTransaction {
    private final HashMap<Path, Path> fileRelocationRegistry;
    private int filesCounter;
    private final BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter;
    private final Path stashDir;

    public RecoverableCompilationTransaction(BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter, Path stashDir) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(stashDir, "stashDir");
        this.reporter = reporter;
        this.stashDir = stashDir;
        this.fileRelocationRegistry = new HashMap<>();
    }

    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public void registerAddedOrChangedFile(final Path outputFile) {
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        if (isFileRelocationIsAlreadyRegisteredFor(outputFile)) {
            return;
        }
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.PRECISE_BACKUP_OUTPUT;
        buildReporter.startMeasure(gradleBuildTime);
        try {
            if (Files.exists(outputFile, new LinkOption[0])) {
                stashFile(outputFile);
            } else {
                ICReporterKt.debug(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.RecoverableCompilationTransaction$registerAddedOrChangedFile$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Marking the " + outputFile + " file as newly added";
                    }
                });
                this.fileRelocationRegistry.put(outputFile, null);
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            buildReporter.endMeasure(gradleBuildTime);
        }
    }

    @Override // org.jetbrains.kotlin.incremental.CompilationTransaction
    public void deleteFile(final Path outputFile) throws IOException {
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        if (Files.exists(outputFile, new LinkOption[0])) {
            if (isFileRelocationIsAlreadyRegisteredFor(outputFile)) {
                ICReporterKt.debug(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.RecoverableCompilationTransaction.deleteFile.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Deleting " + outputFile;
                    }
                });
                Files.delete(outputFile);
                return;
            }
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
            GradleBuildTime gradleBuildTime = GradleBuildTime.PRECISE_BACKUP_OUTPUT;
            buildReporter.startMeasure(gradleBuildTime);
            try {
                stashFile(outputFile);
                Unit unit = Unit.INSTANCE;
            } finally {
                buildReporter.endMeasure(gradleBuildTime);
            }
        }
    }

    private final void stashFile(final Path outputFile) throws IOException {
        final Path nextRelocatedFilePath = getNextRelocatedFilePath();
        ICReporterKt.debug(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.RecoverableCompilationTransaction.stashFile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "Moving " + outputFile + " to the stash as " + nextRelocatedFilePath;
            }
        });
        this.fileRelocationRegistry.put(outputFile, nextRelocatedFilePath);
        Files.move(outputFile, nextRelocatedFilePath, new CopyOption[0]);
    }

    private final Path getNextRelocatedFilePath() {
        Path pathResolve = this.stashDir.resolve(this.filesCounter + ".backup");
        this.filesCounter++;
        Intrinsics.checkNotNullExpressionValue(pathResolve, "also(...)");
        return pathResolve;
    }

    private final boolean isFileRelocationIsAlreadyRegisteredFor(Path outputFile) {
        return this.fileRelocationRegistry.containsKey(outputFile);
    }

    private final void revertChanges() {
        ICReporterKt.debug(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.RecoverableCompilationTransaction.revertChanges.1
            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "Reverting changes";
            }
        });
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.RESTORE_OUTPUT_FROM_BACKUP;
        buildReporter.startMeasure(gradleBuildTime);
        try {
            for (Map.Entry<Path, Path> entry : this.fileRelocationRegistry.entrySet()) {
                Path key = entry.getKey();
                Path value = entry.getValue();
                if (value == null) {
                    if (Files.exists(key, new LinkOption[0])) {
                        Files.delete(key);
                    }
                } else {
                    Files.move(value, key, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            buildReporter.endMeasure(gradleBuildTime);
        }
    }

    private final void cleanupStash() {
        ICReporterKt.debug(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.RecoverableCompilationTransaction.cleanupStash.1
            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "Cleaning up stash";
            }
        });
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.CLEAN_BACKUP_STASH;
        buildReporter.startMeasure(gradleBuildTime);
        try {
            boolean z = false;
            Stream<Path> streamWalk = Files.walk(this.stashDir, new FileVisitOption[0]);
            try {
                Stream<Path> streamSorted = streamWalk.sorted(Comparator.reverseOrder());
                final RecoverableCompilationTransaction$cleanupStash$2$1$1 recoverableCompilationTransaction$cleanupStash$2$1$1 = RecoverableCompilationTransaction$cleanupStash$2$1$1.INSTANCE;
                streamSorted.forEach(new Consumer() { // from class: org.jetbrains.kotlin.incremental.RecoverableCompilationTransaction$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RecoverableCompilationTransaction.cleanupStash$lambda$6$lambda$5$lambda$4(recoverableCompilationTransaction$cleanupStash$2$1$1, obj);
                    }
                });
                Unit unit = Unit.INSTANCE;
                if (streamWalk != null) {
                    streamWalk.close();
                }
                Unit unit2 = Unit.INSTANCE;
                buildReporter.endMeasure(gradleBuildTime);
            } catch (Exception e) {
                if (streamWalk != null) {
                    try {
                        try {
                            streamWalk.close();
                        } catch (Exception unused) {
                        }
                    } catch (Throwable th) {
                        th = th;
                        z = true;
                        if (!z && streamWalk != null) {
                            streamWalk.close();
                        }
                        throw th;
                    }
                }
                throw e;
            } catch (Throwable th2) {
                th = th2;
                if (!z) {
                    streamWalk.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            buildReporter.endMeasure(gradleBuildTime);
            throw th3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cleanupStash$lambda$6$lambda$5$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        Object objM2898constructorimpl;
        checkForExecutionException();
        Throwable thCloseCachesManager = closeCachesManager();
        try {
            Result.Companion companion = Result.INSTANCE;
            RecoverableCompilationTransaction recoverableCompilationTransaction = this;
            if (getIsSuccessful()) {
                cleanupStash();
            } else {
                revertChanges();
                cleanupStash();
            }
            objM2898constructorimpl = Result.m2898constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(objM2898constructorimpl);
        if (thM2901exceptionOrNullimpl != null && thCloseCachesManager != null) {
            ExceptionsKt.addSuppressed(thCloseCachesManager, thM2901exceptionOrNullimpl);
        }
        if (thCloseCachesManager == null) {
            thCloseCachesManager = thM2901exceptionOrNullimpl;
        }
        if (thCloseCachesManager != null) {
            throw thCloseCachesManager;
        }
    }
}
