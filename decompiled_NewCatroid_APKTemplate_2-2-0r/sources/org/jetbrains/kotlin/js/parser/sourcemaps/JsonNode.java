package org.jetbrains.kotlin.js.parser.sourcemaps;

import java.io.StringWriter;
import java.io.Writer;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&\u0082\u0001\u0006\t\n\u000b\f\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;", "", "()V", "toString", "", "write", "", "writer", "Ljava/io/Writer;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonArray;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonBoolean;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNull;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNumber;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonObject;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonString;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JsonNode {
    public /* synthetic */ JsonNode(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract void write(Writer writer);

    private JsonNode() {
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        write(stringWriter);
        String string = stringWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
