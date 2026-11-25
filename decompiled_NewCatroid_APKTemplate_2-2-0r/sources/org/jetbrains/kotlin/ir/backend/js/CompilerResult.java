package org.jetbrains.kotlin.ir.backend.js;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.TranslationMode;

/* JADX INFO: compiled from: compiler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/CompilerResult;", "", "outputs", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/TranslationMode;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "(Ljava/util/Map;)V", "getOutputs", "()Ljava/util/Map;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilerResult {
    private final Map<TranslationMode, CompilationOutputs> outputs;

    /* JADX WARN: Multi-variable type inference failed */
    public CompilerResult(Map<TranslationMode, ? extends CompilationOutputs> outputs) {
        Intrinsics.checkNotNullParameter(outputs, "outputs");
        this.outputs = outputs;
    }

    public final Map<TranslationMode, CompilationOutputs> getOutputs() {
        return this.outputs;
    }
}
