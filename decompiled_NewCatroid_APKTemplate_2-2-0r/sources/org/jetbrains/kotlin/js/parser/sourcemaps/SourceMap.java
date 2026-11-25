package org.jetbrains.kotlin.js.parser.sourcemaps;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: SourceMap.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u001b\u0012\u0014\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0004J\u0016\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001f\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMap;", "", "sourceContentResolver", "Lkotlin/Function1;", "", "Ljava/io/Reader;", "(Lkotlin/jvm/functions/Function1;)V", "groups", "", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapGroup;", "getGroups", "()Ljava/util/List;", "getSourceContentResolver", "()Lkotlin/jvm/functions/Function1;", "debug", "", "writer", "Ljava/io/PrintStream;", "debugToString", "debugVerbose", "generatedJsFile", "Ljava/io/File;", "Companion", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceMap {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<SourceMapGroup> groups;
    private final Function1<String, Reader> sourceContentResolver;

    /* JADX WARN: Multi-variable type inference failed */
    public SourceMap(Function1<? super String, ? extends Reader> sourceContentResolver) {
        Intrinsics.checkNotNullParameter(sourceContentResolver, "sourceContentResolver");
        this.sourceContentResolver = sourceContentResolver;
        this.groups = new ArrayList();
    }

    public final Function1<String, Reader> getSourceContentResolver() {
        return this.sourceContentResolver;
    }

    public final List<SourceMapGroup> getGroups() {
        return this.groups;
    }

    public final String debugToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        debug(new PrintStream(byteArrayOutputStream));
        String string = byteArrayOutputStream.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static /* synthetic */ void debug$default(SourceMap sourceMap, PrintStream out, int i, Object obj) {
        if ((i & 1) != 0) {
            out = System.out;
            Intrinsics.checkNotNullExpressionValue(out, "out");
        }
        sourceMap.debug(out);
    }

    public final void debug(PrintStream writer) {
        Intrinsics.checkNotNullParameter(writer, "writer");
        int i = 0;
        for (SourceMapGroup sourceMapGroup : this.groups) {
            i++;
            writer.print(new StringBuilder().append(i).append(':').toString());
            for (SourceMapSegment sourceMapSegment : sourceMapGroup.getSegments()) {
                writer.print(" " + (sourceMapSegment.getGeneratedColumnNumber() + 1) + ':' + (sourceMapSegment.getSourceLineNumber() + 1) + ',' + (sourceMapSegment.getSourceColumnNumber() + 1) + (sourceMapSegment.getName() != null ? "(" + sourceMapSegment.getName() + ')' : ""));
            }
            writer.println();
        }
    }

    public final void debugVerbose(PrintStream writer, File generatedJsFile) {
        boolean z;
        Intrinsics.checkNotNullParameter(writer, "writer");
        Intrinsics.checkNotNullParameter(generatedJsFile, "generatedJsFile");
        generatedJsFile.exists();
        boolean z2 = true;
        int i = 0;
        String[] strArr = (String[]) FilesKt.readLines$default(generatedJsFile, null, 1, null).toArray(new String[0]);
        int i2 = 0;
        for (SourceMapGroup sourceMapGroup : this.groups) {
            int i3 = i2 + 1;
            writer.print(new StringBuilder().append(i3).append(':').toString());
            String str = strArr[i2];
            List<SourceMapSegment> segments = sourceMapGroup.getSegments();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(segments, 10));
            for (SourceMapSegment sourceMapSegment : segments) {
                arrayList.add(TuplesKt.to(Integer.valueOf(sourceMapSegment.getGeneratedColumnNumber()), sourceMapSegment));
            }
            Map map = MapsKt.toMap(arrayList);
            int length = str.length();
            int i4 = i;
            while (i4 < length) {
                SourceMapSegment sourceMapSegment2 = (SourceMapSegment) map.get(Integer.valueOf(i4));
                if (sourceMapSegment2 != null) {
                    String sourceFileName = sourceMapSegment2.getSourceFileName();
                    int sourceLineNumber = sourceMapSegment2.getSourceLineNumber();
                    int sourceColumnNumber = sourceMapSegment2.getSourceColumnNumber();
                    String name2 = sourceMapSegment2.getName();
                    String str2 = name2 != null ? "(" + name2 + ')' : "";
                    z = true;
                    writer.print("<" + sourceFileName + ':' + (sourceLineNumber + 1) + ':' + (sourceColumnNumber + 1) + str2 + '>');
                } else {
                    z = z2;
                }
                writer.print(str.charAt(i4));
                i4++;
                z2 = z;
            }
            writer.println();
            i2 = i3;
            i = 0;
        }
    }

    /* JADX INFO: compiled from: SourceMap.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\nJ\"\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\n¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMap$Companion;", "", "()V", "mapSources", "", FirebaseAnalytics.Param.CONTENT, "", "output", "Ljava/io/Writer;", "mapping", "Lkotlin/Function1;", "replaceSources", "sourceMapFile", "Ljava/io/File;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean replaceSources(File sourceMapFile, Function1<? super String, String> mapping) throws SourceMapSourceReplacementException, IOException {
            Intrinsics.checkNotNullParameter(sourceMapFile, "sourceMapFile");
            Intrinsics.checkNotNullParameter(mapping, "mapping");
            String text$default = FilesKt.readText$default(sourceMapFile, null, 1, null);
            Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(sourceMapFile), Charsets.UTF_8);
            BufferedWriter bufferedWriter = outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192);
            try {
                boolean zMapSources = SourceMap.INSTANCE.mapSources(text$default, bufferedWriter, mapping);
                CloseableKt.closeFinally(bufferedWriter, null);
                return zMapSources;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedWriter, th);
                    throw th2;
                }
            }
        }

        public final boolean mapSources(String content, Writer output, Function1<? super String, String> mapping) throws SourceMapSourceReplacementException, IOException {
            Intrinsics.checkNotNullParameter(content, "content");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(mapping, "mapping");
            try {
                JsonNode json = JSONKt.parseJson(content);
                JsonObject jsonObject = json instanceof JsonObject ? (JsonObject) json : null;
                if (jsonObject == null) {
                    throw new SourceMapSourceReplacementException("Top-level object expected", null, 2, null);
                }
                JsonNode jsonNode = jsonObject.getProperties().get("sources");
                if (jsonNode != null) {
                    JsonArray jsonArray = jsonNode instanceof JsonArray ? (JsonArray) jsonNode : null;
                    if (jsonArray == null) {
                        throw new SourceMapSourceReplacementException("'sources' property is not of array type", null, 2, null);
                    }
                    List<JsonNode> elements = jsonArray.getElements();
                    ArrayList arrayList = new ArrayList();
                    boolean z = false;
                    for (JsonNode jsonNode2 : elements) {
                        JsonString jsonString = jsonNode2 instanceof JsonString ? (JsonString) jsonNode2 : null;
                        if (jsonString == null) {
                            throw new SourceMapSourceReplacementException("'sources' array must contain strings", null, 2, null);
                        }
                        String strInvoke = mapping.invoke(jsonString.getValue());
                        if (!z && !Intrinsics.areEqual(strInvoke, jsonString.getValue())) {
                            z = true;
                        }
                        arrayList.add(new JsonString(strInvoke));
                    }
                    ArrayList arrayList2 = arrayList;
                    if (!z) {
                        return false;
                    }
                    jsonObject.getProperties().put("sources", new JsonArray(arrayList2));
                }
                jsonObject.write(output);
                return true;
            } catch (JsonSyntaxException e) {
                throw new SourceMapSourceReplacementException(null, e, 1, null);
            }
        }
    }
}
