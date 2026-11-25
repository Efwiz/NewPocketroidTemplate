package org.jetbrains.kotlin.incremental.js;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: IncrementalResultsConsumer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/incremental/js/FunctionWithSourceInfo;", "", "expression", Constant.LINE, "", "column", "(Ljava/lang/Object;II)V", "getColumn", "()I", "getExpression", "()Ljava/lang/Object;", "getLine", "md5", "", "getMd5", "()J", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionWithSourceInfo {
    private final int column;
    private final Object expression;
    private final int line;

    public FunctionWithSourceInfo(Object expression, int i, int i2) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.expression = expression;
        this.line = i;
        this.column = i2;
    }

    public final int getColumn() {
        return this.column;
    }

    public final Object getExpression() {
        return this.expression;
    }

    public final int getLine() {
        return this.line;
    }

    public final long getMd5() {
        byte[] bytes = ("(" + this.line + ':' + this.column + ')' + this.expression).getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return IncrementalResultsConsumerKt.md5(bytes);
    }
}
