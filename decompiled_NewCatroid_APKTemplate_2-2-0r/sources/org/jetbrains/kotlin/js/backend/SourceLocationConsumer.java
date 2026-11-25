package org.jetbrains.kotlin.js.backend;

import kotlin.Metadata;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: compiled from: SourceLocationConsumer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\u0012\u0010\u0005\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/SourceLocationConsumer;", "", "newLine", "", "popSourceInfo", "pushSourceInfo", CompilerOptions.INFO, "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SourceLocationConsumer {
    void newLine();

    void popSourceInfo();

    void pushSourceInfo(Object info);
}
