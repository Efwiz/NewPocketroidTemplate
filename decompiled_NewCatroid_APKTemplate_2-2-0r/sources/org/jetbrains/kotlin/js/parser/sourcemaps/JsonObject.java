package org.jetbrains.kotlin.js.parser.sourcemaps;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B3\b\u0016\u0012*\u0010\u0002\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00040\u0003\"\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\u0002\u0010\u0006B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0007HÆ\u0003J\u001f\u0010\f\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0007HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0005H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonObject;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;", "properties", "", "Lkotlin/Pair;", "", "([Lkotlin/Pair;)V", "", "(Ljava/util/Map;)V", "getProperties", "()Ljava/util/Map;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "write", "", "writer", "Ljava/io/Writer;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JsonObject extends JsonNode {
    private final Map<String, JsonNode> properties;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JsonObject copy$default(JsonObject jsonObject, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            map = jsonObject.properties;
        }
        return jsonObject.copy(map);
    }

    public final Map<String, JsonNode> component1() {
        return this.properties;
    }

    public final JsonObject copy(Map<String, JsonNode> properties) {
        Intrinsics.checkNotNullParameter(properties, "properties");
        return new JsonObject(properties);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof JsonObject) && Intrinsics.areEqual(this.properties, ((JsonObject) other).properties);
    }

    public int hashCode() {
        return this.properties.hashCode();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsonObject(Map<String, JsonNode> properties) {
        super(null);
        Intrinsics.checkNotNullParameter(properties, "properties");
        this.properties = properties;
    }

    public final Map<String, JsonNode> getProperties() {
        return this.properties;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsonObject(Pair<String, ? extends JsonNode>... properties) {
        this((Map<String, JsonNode>) MapsKt.toMutableMap(MapsKt.toMap(properties)));
        Intrinsics.checkNotNullParameter(properties, "properties");
    }

    @Override // org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode
    public void write(Writer writer) throws IOException {
        Intrinsics.checkNotNullParameter(writer, "writer");
        writer.append('{');
        boolean z = true;
        for (Map.Entry<String, JsonNode> entry : this.properties.entrySet()) {
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            if (!z) {
                writer.append(',');
            }
            new JsonString(key).write(writer);
            writer.append(':');
            value.write(writer);
            z = false;
        }
        writer.append('}');
    }

    @Override // org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode
    public String toString() {
        return super.toString();
    }
}
