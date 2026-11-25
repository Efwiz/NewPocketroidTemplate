package org.jetbrains.kotlin.js.parser.sourcemaps;

import java.io.IOException;
import java.io.Writer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNull;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;", "()V", "write", "", "writer", "Ljava/io/Writer;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsonNull extends JsonNode {
    public static final JsonNull INSTANCE = new JsonNull();

    private JsonNull() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode
    public void write(Writer writer) throws IOException {
        Intrinsics.checkNotNullParameter(writer, "writer");
        writer.append("null");
    }
}
