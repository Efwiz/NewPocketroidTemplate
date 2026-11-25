package org.jetbrains.kotlin.js.parser.sourcemaps;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"parseJson", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;", "file", "Ljava/io/File;", "text", "", "js.parser"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JSONKt {
    public static final JsonNode parseJson(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return parseJson(FilesKt.readText(file, Charsets.UTF_8));
    }

    public static final JsonNode parseJson(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return new JsonParser(text).parse();
    }
}
