package org.jetbrains.kotlin.js.parser.sourcemaps;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SourceMap.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapSuccess;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapParseResult;", "value", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMap;", "(Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMap;)V", "getValue", "()Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMap;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceMapSuccess extends SourceMapParseResult {
    private final SourceMap value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SourceMapSuccess(SourceMap value) {
        super(null);
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
    }

    public final SourceMap getValue() {
        return this.value;
    }
}
