package org.jetbrains.kotlin.js.sourceMap;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.apkeditor.Options;
import gnu.trove.TObjectIntHashMap;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonArray;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonNull;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonNumber;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonObject;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonString;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SourceMap3Builder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001:\u0003<=>B%\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010#\u001a\u00020$H\u0016JD\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020\b2\b\u0010'\u001a\u0004\u0018\u00010(2\u000e\u0010)\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00112\u0006\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u00062\b\u0010,\u001a\u0004\u0018\u00010\bH\u0016JJ\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020\b2\b\u0010'\u001a\u0004\u0018\u00010(2\u000e\u0010)\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00112\u0006\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u00062\b\u0010,\u001a\u0004\u0018\u00010\b2\u0006\u0010-\u001a\u00020\u0006J\u0010\u0010.\u001a\u00020$2\u0006\u0010/\u001a\u000200H\u0002J\u0010\u00101\u001a\u00020$2\u0006\u0010/\u001a\u000200H\u0002J\b\u00102\u001a\u00020\bH\u0016J\u0010\u00103\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\bH\u0002J\b\u00104\u001a\u00020\u0003H\u0016J*\u00105\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\b2\b\u0010'\u001a\u0004\u0018\u00010(2\u000e\u00106\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0011H\u0002J\b\u00107\u001a\u00020$H\u0016J\u0010\u00108\u001a\u00020$2\u0006\u00109\u001a\u00020\u0006H\u0016J\u0010\u0010:\u001a\u00020$2\u0006\u0010;\u001a\u00020\u0006H\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0010\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0014\u001a\u00060\u0015j\u0002`\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/js/sourceMap/SourceMap3Builder;", "Lorg/jetbrains/kotlin/js/sourceMap/SourceMapBuilder;", "generatedFile", "Ljava/io/File;", "getCurrentOutputColumn", "Lkotlin/Function0;", "", "pathPrefix", "", "(Ljava/io/File;Lkotlin/jvm/functions/Function0;Ljava/lang/String;)V", "currentMappingIsEmpty", "", "names", "Lorg/jetbrains/kotlin/js/sourceMap/SourceMap3Builder$ObjectIntHashMap;", "orderedNames", "", "orderedSourceContentSuppliers", "Ljava/util/function/Supplier;", "Ljava/io/Reader;", "orderedSources", "out", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "previousGeneratedColumn", "previousMappingOffset", "previousNameIndex", "previousPreviousNameIndex", "previousPreviousSourceColumn", "previousPreviousSourceIndex", "previousPreviousSourceLine", "previousSourceColumn", "previousSourceIndex", "previousSourceLine", "sources", "Lorg/jetbrains/kotlin/js/sourceMap/SourceMap3Builder$SourceKey;", "addEmptyMapping", "", "addMapping", FirebaseAnalytics.Param.SOURCE, "fileIdentity", "", "sourceContent", "sourceLine", "sourceColumn", "name", "outputColumn", "appendSources", Options.TYPE_JSON, "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonObject;", "appendSourcesContent", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "getNameIndex", "getOutFile", "getSourceIndex", "contentSupplier", "newLine", "skipLinesAtBeginning", "count", "startMapping", "column", "Base64VLQ", "ObjectIntHashMap", "SourceKey", "js.sourcemap"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceMap3Builder implements SourceMapBuilder {
    private boolean currentMappingIsEmpty;
    private final File generatedFile;
    private final Function0<Integer> getCurrentOutputColumn;
    private final ObjectIntHashMap<String> names;
    private final List<String> orderedNames;
    private final List<Supplier<Reader>> orderedSourceContentSuppliers;
    private final List<String> orderedSources;
    private final StringBuilder out;
    private final String pathPrefix;
    private int previousGeneratedColumn;
    private int previousMappingOffset;
    private int previousNameIndex;
    private int previousPreviousNameIndex;
    private int previousPreviousSourceColumn;
    private int previousPreviousSourceIndex;
    private int previousPreviousSourceLine;
    private int previousSourceColumn;
    private int previousSourceIndex;
    private int previousSourceLine;
    private final ObjectIntHashMap<SourceKey> sources;

    public SourceMap3Builder(File file, Function0<Integer> getCurrentOutputColumn, String pathPrefix) {
        Intrinsics.checkNotNullParameter(getCurrentOutputColumn, "getCurrentOutputColumn");
        Intrinsics.checkNotNullParameter(pathPrefix, "pathPrefix");
        this.generatedFile = file;
        this.getCurrentOutputColumn = getCurrentOutputColumn;
        this.pathPrefix = pathPrefix;
        this.out = new StringBuilder(8192);
        this.sources = new ObjectIntHashMap<>();
        this.orderedSources = new ArrayList();
        this.orderedSourceContentSuppliers = new ArrayList();
        this.names = new ObjectIntHashMap<>();
        this.orderedNames = new ArrayList();
        this.previousGeneratedColumn = -1;
        this.currentMappingIsEmpty = true;
    }

    /* JADX INFO: compiled from: SourceMap3Builder.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/sourceMap/SourceMap3Builder$ObjectIntHashMap;", ExifInterface.GPS_DIRECTION_TRUE, "Lgnu/trove/TObjectIntHashMap;", "()V", "get", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)I", "js.sourcemap"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ObjectIntHashMap<T> extends TObjectIntHashMap<T> {
        @Override // gnu.trove.TObjectIntHashMap
        public int get(T key) {
            int iIndex = index(key);
            if (iIndex < 0) {
                return -1;
            }
            return this._values[iIndex];
        }
    }

    @Override // org.jetbrains.kotlin.js.sourceMap.SourceMapBuilder
    public File getOutFile() {
        File file = this.generatedFile;
        Intrinsics.checkNotNull(file);
        return new File(file.getParentFile(), this.generatedFile.getName() + ".map");
    }

    @Override // org.jetbrains.kotlin.js.sourceMap.SourceMapBuilder
    public String build() {
        JsonObject jsonObject = new JsonObject((Pair<String, ? extends JsonNode>[]) new Pair[0]);
        jsonObject.getProperties().put("version", new JsonNumber(3.0d));
        if (this.generatedFile != null) {
            Map<String, JsonNode> properties = jsonObject.getProperties();
            String name2 = this.generatedFile.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            properties.put("file", new JsonString(name2));
        }
        appendSources(jsonObject);
        appendSourcesContent(jsonObject);
        Map<String, JsonNode> properties2 = jsonObject.getProperties();
        List<String> list = this.orderedNames;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new JsonString((String) it.next()));
        }
        properties2.put("names", new JsonArray(arrayList));
        Map<String, JsonNode> properties3 = jsonObject.getProperties();
        String string = this.out.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        properties3.put("mappings", new JsonString(string));
        return jsonObject.toString();
    }

    private final void appendSources(JsonObject json) {
        Map<String, JsonNode> properties = json.getProperties();
        List<String> list = this.orderedSources;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new JsonString(this.pathPrefix + ((String) it.next())));
        }
        properties.put("sources", new JsonArray(arrayList));
    }

    private final void appendSourcesContent(JsonObject json) {
        JsonNode jsonString;
        Map<String, JsonNode> properties = json.getProperties();
        List<Supplier<Reader>> list = this.orderedSourceContentSuppliers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            try {
                Closeable closeable = (Closeable) ((Supplier) it.next()).get();
                try {
                    Reader reader = (Reader) closeable;
                    if (reader != null) {
                        jsonString = new JsonString(TextStreamsKt.readText(reader));
                    } else {
                        jsonString = JsonNull.INSTANCE;
                    }
                    CloseableKt.closeFinally(closeable, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(closeable, th);
                        throw th2;
                    }
                }
            } catch (IOException e) {
                System.err.println("An exception occurred during embedding sources into source map");
                e.printStackTrace();
                jsonString = JsonNull.INSTANCE;
            }
            arrayList.add(jsonString);
        }
        properties.put("sourcesContent", new JsonArray(arrayList));
    }

    @Override // org.jetbrains.kotlin.js.sourceMap.SourceMapMappingConsumer
    public void newLine() {
        this.out.append(';');
        this.previousGeneratedColumn = -1;
    }

    @Override // org.jetbrains.kotlin.js.sourceMap.SourceMapBuilder
    public void skipLinesAtBeginning(int count) {
        this.out.insert(0, StringsKt.repeat(";", count));
    }

    private final int getSourceIndex(String source, Object fileIdentity, Supplier<Reader> contentSupplier) {
        SourceKey sourceKey = new SourceKey(source, fileIdentity);
        int i = this.sources.get(sourceKey);
        if (i != -1) {
            return i;
        }
        int size = this.orderedSources.size();
        this.sources.put(sourceKey, size);
        this.orderedSources.add(source);
        this.orderedSourceContentSuppliers.add(contentSupplier);
        return size;
    }

    private final int getNameIndex(String name2) {
        int i = this.names.get(name2);
        if (i != -1) {
            return i;
        }
        int size = this.orderedNames.size();
        this.names.put(name2, size);
        this.orderedNames.add(name2);
        return size;
    }

    @Override // org.jetbrains.kotlin.js.sourceMap.SourceMapMappingConsumer
    public void addMapping(String source, Object fileIdentity, Supplier<Reader> sourceContent, int sourceLine, int sourceColumn, String name2) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(sourceContent, "sourceContent");
        addMapping(source, fileIdentity, sourceContent, sourceLine, sourceColumn, name2, this.getCurrentOutputColumn.invoke().intValue());
    }

    public final void addMapping(String source, Object fileIdentity, Supplier<Reader> sourceContent, int sourceLine, int sourceColumn, String name2, int outputColumn) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(sourceContent, "sourceContent");
        int sourceIndex = getSourceIndex(StringsKt.replace$default(source, File.separatorChar, '/', false, 4, (Object) null), fileIdentity, sourceContent);
        int nameIndex = name2 != null ? getNameIndex(name2) : -1;
        if (!this.currentMappingIsEmpty && this.previousSourceIndex == sourceIndex && this.previousSourceLine == sourceLine && this.previousSourceColumn == sourceColumn) {
            return;
        }
        startMapping(outputColumn);
        Base64VLQ.INSTANCE.encode(this.out, sourceIndex - this.previousSourceIndex);
        this.previousSourceIndex = sourceIndex;
        Base64VLQ.INSTANCE.encode(this.out, sourceLine - this.previousSourceLine);
        this.previousSourceLine = sourceLine;
        Base64VLQ.INSTANCE.encode(this.out, sourceColumn - this.previousSourceColumn);
        this.previousSourceColumn = sourceColumn;
        if (nameIndex >= 0) {
            Base64VLQ.INSTANCE.encode(this.out, nameIndex - this.previousNameIndex);
            this.previousNameIndex = nameIndex;
        }
        this.currentMappingIsEmpty = false;
    }

    @Override // org.jetbrains.kotlin.js.sourceMap.SourceMapMappingConsumer
    public void addEmptyMapping() {
        if (this.currentMappingIsEmpty) {
            return;
        }
        startMapping(this.getCurrentOutputColumn.invoke().intValue());
        this.currentMappingIsEmpty = true;
    }

    private final void startMapping(int column) {
        boolean z = this.previousGeneratedColumn == -1;
        if (z) {
            this.previousGeneratedColumn = 0;
        }
        int i = column - this.previousGeneratedColumn;
        if (!z) {
            this.out.append(',');
        }
        if (i > 0 || z) {
            Base64VLQ.INSTANCE.encode(this.out, i);
            this.previousGeneratedColumn = column;
            this.previousMappingOffset = this.out.length();
            this.previousPreviousSourceIndex = this.previousSourceIndex;
            this.previousPreviousSourceLine = this.previousSourceLine;
            this.previousPreviousSourceColumn = this.previousSourceColumn;
            this.previousPreviousNameIndex = this.previousNameIndex;
            return;
        }
        this.out.setLength(this.previousMappingOffset);
        this.previousSourceIndex = this.previousPreviousSourceIndex;
        this.previousSourceLine = this.previousPreviousSourceLine;
        this.previousSourceColumn = this.previousPreviousSourceColumn;
        this.previousNameIndex = this.previousPreviousNameIndex;
    }

    /* JADX INFO: compiled from: SourceMap3Builder.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0010\u001a\u00020\u0007J\u0010\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007H\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/sourceMap/SourceMap3Builder$Base64VLQ;", "", "()V", "BASE64_MAP", "", "getBASE64_MAP$annotations", "VLQ_BASE", "", "VLQ_BASE_MASK", "VLQ_BASE_SHIFT", "VLQ_CONTINUATION_BIT", "encode", "", "out", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "value", "toVLQSigned", "js.sourcemap"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Base64VLQ {
        private static final char[] BASE64_MAP;
        public static final Base64VLQ INSTANCE = new Base64VLQ();

        private final int toVLQSigned(int value) {
            return value < 0 ? ((-value) << 1) + 1 : value << 1;
        }

        private Base64VLQ() {
        }

        static {
            char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
            Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
            BASE64_MAP = charArray;
        }

        public final void encode(StringBuilder out, int value) {
            Intrinsics.checkNotNullParameter(out, "out");
            int vLQSigned = toVLQSigned(value);
            do {
                int i = vLQSigned & 31;
                vLQSigned >>>= 5;
                if (vLQSigned > 0) {
                    i |= 32;
                }
                out.append(BASE64_MAP[i]);
            } while (vLQSigned > 0);
        }
    }

    /* JADX INFO: compiled from: SourceMap3Builder.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005J\t\u0010\u0006\u001a\u00020\u0003HÂ\u0003J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0001HÂ\u0003J\u001f\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0001HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/sourceMap/SourceMap3Builder$SourceKey;", "", "sourcePath", "", "fileIdentity", "(Ljava/lang/String;Ljava/lang/Object;)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "js.sourcemap"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class SourceKey {
        private final Object fileIdentity;
        private final String sourcePath;

        public static /* synthetic */ SourceKey copy$default(SourceKey sourceKey, String str, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                str = sourceKey.sourcePath;
            }
            if ((i & 2) != 0) {
                obj = sourceKey.fileIdentity;
            }
            return sourceKey.copy(str, obj);
        }

        public final SourceKey copy(String sourcePath, Object fileIdentity) {
            Intrinsics.checkNotNullParameter(sourcePath, "sourcePath");
            return new SourceKey(sourcePath, fileIdentity);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SourceKey)) {
                return false;
            }
            SourceKey sourceKey = (SourceKey) other;
            return Intrinsics.areEqual(this.sourcePath, sourceKey.sourcePath) && Intrinsics.areEqual(this.fileIdentity, sourceKey.fileIdentity);
        }

        public int hashCode() {
            int iHashCode = this.sourcePath.hashCode() * 31;
            Object obj = this.fileIdentity;
            return iHashCode + (obj == null ? 0 : obj.hashCode());
        }

        public String toString() {
            return "SourceKey(sourcePath=" + this.sourcePath + ", fileIdentity=" + this.fileIdentity + ')';
        }

        public SourceKey(String sourcePath, Object obj) {
            Intrinsics.checkNotNullParameter(sourcePath, "sourcePath");
            this.sourcePath = sourcePath;
            this.fileIdentity = obj;
        }
    }
}
