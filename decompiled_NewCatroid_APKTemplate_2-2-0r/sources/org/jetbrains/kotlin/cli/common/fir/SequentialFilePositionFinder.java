package org.jetbrains.kotlin.cli.common.fir;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: FirDiagnosticsCompilerResultsReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u000f\b\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/fir/SequentialFilePositionFinder;", "Ljava/io/Closeable;", "Lorg/jetbrains/kotlin/cli/common/fir/SequentialPositionFinder;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "reader", "Ljava/io/InputStreamReader;", "(Ljava/io/InputStreamReader;)V", "close", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SequentialFilePositionFinder extends SequentialPositionFinder implements Closeable {
    private final InputStreamReader reader;

    private SequentialFilePositionFinder(InputStreamReader inputStreamReader) {
        super(inputStreamReader);
        this.reader = inputStreamReader;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SequentialFilePositionFinder(File file) {
        this(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
        Intrinsics.checkNotNullParameter(file, "file");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.reader.close();
    }
}
