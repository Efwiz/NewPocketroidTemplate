package org.jetbrains.kotlin.js.parser.sourcemaps;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: SourceMapParser.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapParser;", "", "()V", "parse", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapParseResult;", "file", "Ljava/io/File;", FirebaseAnalytics.Param.CONTENT, "", "jsonObject", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;", "MappingStream", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceMapParser {
    public static final SourceMapParser INSTANCE = new SourceMapParser();

    private SourceMapParser() {
    }

    public final SourceMapParseResult parse(File file) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        return parse(FilesKt.readText(file, Charsets.UTF_8));
    }

    public final SourceMapParseResult parse(String content) throws IOException {
        Intrinsics.checkNotNullParameter(content, "content");
        try {
            return parse(JSONKt.parseJson(content));
        } catch (JsonSyntaxException e) {
            String message = e.getMessage();
            if (message == null) {
                message = "parse error";
            }
            return new SourceMapError(message);
        }
    }

    private final SourceMapParseResult parse(JsonNode jsonObject) throws IOException {
        String value;
        ArrayList arrayListEmptyList;
        ArrayList arrayListEmptyList2;
        ArrayList arrayListEmptyList3;
        String str;
        String value2;
        if (!(jsonObject instanceof JsonObject)) {
            return new SourceMapError("Top-level object expected");
        }
        JsonObject jsonObject2 = (JsonObject) jsonObject;
        JsonNode jsonNode = jsonObject2.getProperties().get("version");
        if (jsonNode == null) {
            return new SourceMapError("Version not defined");
        }
        if (!(jsonNode instanceof JsonNumber) || ((JsonNumber) jsonNode).getValue() != 3.0d) {
            return new SourceMapError("Unsupported version: " + jsonNode);
        }
        JsonNode jsonNode2 = jsonObject2.getProperties().get("sourceRoot");
        if (jsonNode2 != null) {
            JsonString jsonString = jsonNode2 instanceof JsonString ? (JsonString) jsonNode2 : null;
            if (jsonString == null) {
                return new SourceMapError("'sourceRoot' property is not of string type");
            }
            value = jsonString.getValue();
        } else {
            value = "";
        }
        JsonNode jsonNode3 = jsonObject2.getProperties().get("sources");
        if (jsonNode3 != null) {
            JsonArray jsonArray = jsonNode3 instanceof JsonArray ? (JsonArray) jsonNode3 : null;
            if (jsonArray == null) {
                return new SourceMapError("'sources' property is not of array type");
            }
            List<JsonNode> elements = jsonArray.getElements();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements, 10));
            for (JsonNode jsonNode4 : elements) {
                JsonString jsonString2 = jsonNode4 instanceof JsonString ? (JsonString) jsonNode4 : null;
                if (jsonString2 == null) {
                    return new SourceMapError("'sources' array must contain strings");
                }
                arrayList.add(jsonString2.getValue());
            }
            arrayListEmptyList = arrayList;
        } else {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        JsonNode jsonNode5 = jsonObject2.getProperties().get("sourcesContent");
        if (jsonNode5 != null) {
            JsonArray jsonArray2 = jsonNode5 instanceof JsonArray ? (JsonArray) jsonNode5 : null;
            if (jsonArray2 == null) {
                return new SourceMapError("'sourcesContent' property is not of array type");
            }
            List<JsonNode> elements2 = jsonArray2.getElements();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements2, 10));
            for (JsonNode jsonNode6 : elements2) {
                if (jsonNode6 instanceof JsonNull) {
                    value2 = null;
                } else {
                    if (!(jsonNode6 instanceof JsonString)) {
                        return new SourceMapError("'sources' array must contain strings");
                    }
                    value2 = ((JsonString) jsonNode6).getValue();
                }
                arrayList2.add(value2);
            }
            arrayListEmptyList2 = arrayList2;
        } else {
            arrayListEmptyList2 = CollectionsKt.emptyList();
        }
        JsonNode jsonNode7 = jsonObject2.getProperties().get("names");
        if (jsonNode7 != null) {
            JsonArray jsonArray3 = jsonNode7 instanceof JsonArray ? (JsonArray) jsonNode7 : null;
            if (jsonArray3 == null) {
                return new SourceMapError("'names' property is not of array type");
            }
            List<JsonNode> elements3 = jsonArray3.getElements();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements3, 10));
            for (JsonNode jsonNode8 : elements3) {
                JsonString jsonString3 = jsonNode8 instanceof JsonString ? (JsonString) jsonNode8 : null;
                if (jsonString3 == null) {
                    return new SourceMapError("'names' array must contain strings");
                }
                arrayList3.add(jsonString3.getValue());
            }
            arrayListEmptyList3 = arrayList3;
        } else {
            arrayListEmptyList3 = CollectionsKt.emptyList();
        }
        List<Pair> listZip = CollectionsKt.zip(arrayListEmptyList, arrayListEmptyList2);
        final LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZip, 10)), 16));
        for (Pair pair : listZip) {
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        JsonNode jsonNode9 = jsonObject2.getProperties().get("mappings");
        if (jsonNode9 == null) {
            return new SourceMapError("'mappings' property not found");
        }
        if (!(jsonNode9 instanceof JsonString)) {
            return new SourceMapError("'mappings' property is not of string type");
        }
        MappingStream mappingStream = new MappingStream(((JsonString) jsonNode9).getValue());
        SourceMap sourceMap = new SourceMap(new Function1<String, Reader>() { // from class: org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapParser$parse$sourceMap$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Reader invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String str2 = linkedHashMap.get(it);
                return str2 != null ? new StringReader(str2) : null;
            }
        });
        SourceMapGroup sourceMapGroup = new SourceMapGroup();
        sourceMap.getGroups().add(sourceMapGroup);
        int iIntValue = 0;
        int iIntValue2 = 0;
        int iIntValue3 = 0;
        int iIntValue4 = 0;
        int iIntValue5 = 0;
        while (!mappingStream.isEof()) {
            if (mappingStream.isGroupTerminator()) {
                sourceMapGroup = new SourceMapGroup();
                sourceMap.getGroups().add(sourceMapGroup);
                mappingStream.skipChar();
                iIntValue = 0;
            } else {
                Integer num = mappingStream.readInt();
                if (num == null) {
                    return mappingStream.createError("VLQ-encoded JS column number expected");
                }
                iIntValue += num.intValue();
                if (mappingStream.isEncodedInt()) {
                    Integer num2 = mappingStream.readInt();
                    if (num2 == null) {
                        return mappingStream.createError("VLQ-encoded source index expected");
                    }
                    iIntValue2 += num2.intValue();
                    Integer num3 = mappingStream.readInt();
                    if (num3 == null) {
                        return mappingStream.createError("VLQ-encoded source line expected");
                    }
                    iIntValue3 += num3.intValue();
                    Integer num4 = mappingStream.readInt();
                    if (num4 == null) {
                        return mappingStream.createError("VLQ-encoded source column expected");
                    }
                    iIntValue4 += num4.intValue();
                    if (mappingStream.isEncodedInt()) {
                        Integer num5 = mappingStream.readInt();
                        if (num5 == null) {
                            return mappingStream.createError("VLQ-encoded name index expected");
                        }
                        iIntValue5 += num5.intValue();
                        if (iIntValue5 < 0 || iIntValue5 >= arrayListEmptyList3.size()) {
                            return mappingStream.createError("Name index " + iIntValue5 + " is out of bounds " + CollectionsKt.getIndices(arrayListEmptyList3));
                        }
                        str = (String) arrayListEmptyList3.get(iIntValue5);
                    } else {
                        str = null;
                    }
                    if (iIntValue2 < 0 || iIntValue2 >= arrayListEmptyList.size()) {
                        return mappingStream.createError("Source index " + iIntValue2 + " is out of bounds " + CollectionsKt.getIndices(arrayListEmptyList));
                    }
                    sourceMapGroup.getSegments().add(new SourceMapSegment(iIntValue, value + ((String) arrayListEmptyList.get(iIntValue2)), iIntValue3, iIntValue4, str));
                } else {
                    sourceMapGroup.getSegments().add(new SourceMapSegment(iIntValue, null, -1, -1, null));
                }
                if (mappingStream.isEof()) {
                    return mappingStream.createError("Unexpected EOF, ',' or ';' expected");
                }
                if (mappingStream.isGroupTerminator()) {
                    SourceMapGroup sourceMapGroup2 = new SourceMapGroup();
                    sourceMap.getGroups().add(sourceMapGroup2);
                    sourceMapGroup = sourceMapGroup2;
                    iIntValue = 0;
                } else if (!mappingStream.isSegmentTerminator()) {
                    return mappingStream.createError("Unexpected char, ',' or ';' expected");
                }
                mappingStream.skipChar();
            }
        }
        return new SourceMapSuccess(sourceMap);
    }

    /* JADX INFO: compiled from: SourceMapParser.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0017\u0010\u0013\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002¢\u0006\u0002\u0010\u0016J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0003J\r\u0010\u001a\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\u001bJ\u0006\u0010\u001c\u001a\u00020\u001dR\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0011\u0010\b\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0007R\u0011\u0010\t\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0007R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapParser$MappingStream;", "", "string", "", "(Ljava/lang/String;)V", "isEncodedInt", "", "()Z", "isEof", "isGroupTerminator", "isSegmentTerminator", "position", "", "getPosition", "()I", "setPosition", "(I)V", "getString", "()Ljava/lang/String;", "base64value", "c", "", "(C)Ljava/lang/Integer;", "createError", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapError;", "error", "readInt", "()Ljava/lang/Integer;", "skipChar", "", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MappingStream {
        private int position;
        private final String string;

        public MappingStream(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            this.string = string;
        }

        public final String getString() {
            return this.string;
        }

        public final int getPosition() {
            return this.position;
        }

        public final void setPosition(int i) {
            this.position = i;
        }

        public final boolean isEof() {
            return this.position == this.string.length();
        }

        public final boolean isSegmentTerminator() {
            return this.string.charAt(this.position) == ',';
        }

        public final boolean isGroupTerminator() {
            return this.string.charAt(this.position) == ';';
        }

        public final boolean isEncodedInt() {
            return (isEof() || isSegmentTerminator() || isGroupTerminator()) ? false : true;
        }

        public final void skipChar() {
            this.position++;
        }

        public final Integer readInt() {
            int i = 0;
            int i2 = 0;
            while (!isEof()) {
                String str = this.string;
                int i3 = this.position;
                this.position = i3 + 1;
                Integer numBase64value = base64value(str.charAt(i3));
                if (numBase64value == null) {
                    return null;
                }
                int iIntValue = numBase64value.intValue();
                i |= (iIntValue & 31) << i2;
                if ((iIntValue & 32) == 0) {
                    int i4 = i >>> 1;
                    return (i & 1) == 0 ? Integer.valueOf(i4) : Integer.valueOf(-i4);
                }
                i2 += 5;
            }
            return null;
        }

        private final Integer base64value(char c) {
            if ('A' <= c && c < '[') {
                return Integer.valueOf(c - 'A');
            }
            if ('a' <= c && c < '{') {
                return Integer.valueOf(c - 'G');
            }
            if ('0' <= c && c < ':') {
                return Integer.valueOf(c + 4);
            }
            if (c == '+') {
                return 62;
            }
            return c == '/' ? 63 : null;
        }

        public final SourceMapError createError(String error) {
            Intrinsics.checkNotNullParameter(error, "error");
            return new SourceMapError("Error parsing stream at offset " + this.position + ": " + error);
        }
    }
}
