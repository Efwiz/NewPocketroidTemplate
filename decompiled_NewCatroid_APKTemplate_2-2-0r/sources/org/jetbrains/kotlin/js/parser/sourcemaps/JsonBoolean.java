package org.jetbrains.kotlin.js.parser.sourcemaps;

import java.io.IOException;
import java.io.Writer;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonBoolean;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;", "value", "", "(Z)V", "stringValue", "", "getValue", "()Z", "write", "", "writer", "Ljava/io/Writer;", "Companion", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsonBoolean extends JsonNode {
    private final String stringValue;
    private final boolean value;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final JsonBoolean TRUE = new JsonBoolean(true);
    private static final JsonBoolean FALSE = new JsonBoolean(false);

    private JsonBoolean(boolean z) {
        super(null);
        this.value = z;
        this.stringValue = String.valueOf(z);
    }

    public final boolean getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode
    public void write(Writer writer) throws IOException {
        Intrinsics.checkNotNullParameter(writer, "writer");
        writer.append((CharSequence) this.stringValue);
    }

    /* JADX INFO: compiled from: JSON.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonBoolean$Companion;", "", "()V", "FALSE", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonBoolean;", "getFALSE", "()Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonBoolean;", "TRUE", "getTRUE", "of", "value", "", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JsonBoolean getTRUE() {
            return JsonBoolean.TRUE;
        }

        public final JsonBoolean getFALSE() {
            return JsonBoolean.FALSE;
        }

        public final JsonBoolean of(boolean value) {
            return value ? getTRUE() : getFALSE();
        }
    }
}
