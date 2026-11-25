package org.jetbrains.kotlin.js.parser.sourcemaps;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00060\u0001j\u0002`\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonSyntaxException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", TypedValues.CycleType.S_WAVE_OFFSET, "", Constant.LINE, "column", "text", "", "(IIILjava/lang/String;)V", "getColumn", "()I", "getLine", "getOffset", "getText", "()Ljava/lang/String;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsonSyntaxException extends RuntimeException {
    private final int column;
    private final int line;
    private final int offset;
    private final String text;

    public final int getColumn() {
        return this.column;
    }

    public final int getLine() {
        return this.line;
    }

    public final int getOffset() {
        return this.offset;
    }

    public final String getText() {
        return this.text;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsonSyntaxException(int i, int i2, int i3, String text) {
        super("JSON syntax error at " + (i2 + 1) + ", " + (i3 + 1) + ": " + text);
        Intrinsics.checkNotNullParameter(text, "text");
        this.offset = i;
        this.line = i2;
        this.column = i3;
        this.text = text;
    }
}
