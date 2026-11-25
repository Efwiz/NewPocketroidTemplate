package org.jetbrains.kotlin.js.parser.sourcemaps;

import java.io.IOException;
import java.io.Writer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNumber;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;", "value", "", "(D)V", "getValue", "()D", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "write", "", "writer", "Ljava/io/Writer;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JsonNumber extends JsonNode {
    private final double value;

    public static /* synthetic */ JsonNumber copy$default(JsonNumber jsonNumber, double d, int i, Object obj) {
        if ((i & 1) != 0) {
            d = jsonNumber.value;
        }
        return jsonNumber.copy(d);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final double getValue() {
        return this.value;
    }

    public final JsonNumber copy(double value) {
        return new JsonNumber(value);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof JsonNumber) && Double.compare(this.value, ((JsonNumber) other).value) == 0;
    }

    public int hashCode() {
        return Double.hashCode(this.value);
    }

    public JsonNumber(double d) {
        super(null);
        this.value = d;
    }

    public final double getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode
    public void write(Writer writer) throws IOException {
        Intrinsics.checkNotNullParameter(writer, "writer");
        double d = this.value;
        if (((long) d) == d) {
            writer.append((CharSequence) String.valueOf((long) d));
        } else {
            writer.append((CharSequence) String.valueOf(d));
        }
    }

    @Override // org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode
    public String toString() {
        return super.toString();
    }
}
