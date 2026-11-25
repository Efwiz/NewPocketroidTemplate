package org.jetbrains.kotlin.incremental;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.compilerRunner.OutputItemsCollector;

/* JADX INFO: compiled from: CompilationTransaction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\u001e\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\nH\u0016R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/TransactionOutputsRegistrar;", "Lorg/jetbrains/kotlin/compilerRunner/OutputItemsCollector;", "transaction", "Lorg/jetbrains/kotlin/incremental/CompilationTransaction;", "origin", "(Lorg/jetbrains/kotlin/incremental/CompilationTransaction;Lorg/jetbrains/kotlin/compilerRunner/OutputItemsCollector;)V", ProductAction.ACTION_ADD, "", "sourceFiles", "", "Ljava/io/File;", "outputFile", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TransactionOutputsRegistrar implements OutputItemsCollector {
    private final OutputItemsCollector origin;
    private final CompilationTransaction transaction;

    public TransactionOutputsRegistrar(CompilationTransaction transaction, OutputItemsCollector origin) {
        Intrinsics.checkNotNullParameter(transaction, "transaction");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.transaction = transaction;
        this.origin = origin;
    }

    @Override // org.jetbrains.kotlin.compilerRunner.OutputItemsCollector
    public void add(Collection<? extends File> sourceFiles, File outputFile) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        CompilationTransaction compilationTransaction = this.transaction;
        Path path = outputFile.toPath();
        Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
        compilationTransaction.registerAddedOrChangedFile(path);
        this.origin.add(sourceFiles, outputFile);
    }
}
