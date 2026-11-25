package org.jetbrains.kotlin.js.parser.sourcemaps;

import com.reandroid.archive.PathTree;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0003\"\u00020\u0001¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005HÆ\u0003J\u0019\u0010\n\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonArray;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;", PathTree.NAME_elements, "", "([Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;)V", "", "(Ljava/util/List;)V", "getElements", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "write", "", "writer", "Ljava/io/Writer;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JsonArray extends JsonNode {
    private final List<JsonNode> elements;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JsonArray copy$default(JsonArray jsonArray, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = jsonArray.elements;
        }
        return jsonArray.copy(list);
    }

    public final List<JsonNode> component1() {
        return this.elements;
    }

    public final JsonArray copy(List<JsonNode> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return new JsonArray(elements);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof JsonArray) && Intrinsics.areEqual(this.elements, ((JsonArray) other).elements);
    }

    public int hashCode() {
        return this.elements.hashCode();
    }

    @Override // org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode
    public String toString() {
        return "JsonArray(elements=" + this.elements + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsonArray(List<JsonNode> elements) {
        super(null);
        Intrinsics.checkNotNullParameter(elements, "elements");
        this.elements = elements;
    }

    public final List<JsonNode> getElements() {
        return this.elements;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsonArray(JsonNode... elements) {
        this((List<JsonNode>) ArraysKt.toMutableList(elements));
        Intrinsics.checkNotNullParameter(elements, "elements");
    }

    @Override // org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode
    public void write(Writer writer) throws IOException {
        Intrinsics.checkNotNullParameter(writer, "writer");
        writer.append('[');
        boolean z = true;
        for (JsonNode jsonNode : this.elements) {
            if (!z) {
                writer.append(',');
            }
            jsonNode.write(writer);
            z = false;
        }
        writer.append(']');
    }
}
