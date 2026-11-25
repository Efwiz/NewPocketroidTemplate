package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.SourceInfo;

/* JADX INFO: compiled from: SMAP.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\bH\u0002J\u0018\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001aJ\u000e\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u000eR&\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "", "sourceInfo", "Lorg/jetbrains/kotlin/codegen/SourceInfo;", "(Lorg/jetbrains/kotlin/codegen/SourceInfo;)V", "fileMappings", "Ljava/util/LinkedHashMap;", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/codegen/inline/FileMapping;", "isTrivial", "", "()Z", "maxUsedValue", "", "resultMappings", "", "getResultMappings", "()Ljava/util/List;", "getSourceInfo", "()Lorg/jetbrains/kotlin/codegen/SourceInfo;", "getOrRegisterNewSource", "name", "path", "mapLineNumber", "inlineSource", "Lorg/jetbrains/kotlin/codegen/inline/SourcePosition;", "inlineCallSite", "mapSyntheticLineNumber", "id", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceMapper {
    public static final String FAKE_FILE_NAME = "fake.kt";
    public static final String FAKE_PATH = "kotlin/jvm/internal/FakeKt";
    public static final int LOCAL_VARIABLE_INLINE_ARGUMENT_SYNTHETIC_LINE_NUMBER = 1;
    private LinkedHashMap<Pair<String, String>, FileMapping> fileMappings;
    private int maxUsedValue;
    private final SourceInfo sourceInfo;

    public SourceMapper(SourceInfo sourceInfo) {
        this.sourceInfo = sourceInfo;
        this.maxUsedValue = sourceInfo != null ? sourceInfo.getLinesInFile() : 0;
        this.fileMappings = new LinkedHashMap<>();
        if (sourceInfo != null) {
            String sourceFileName = sourceInfo.getSourceFileName();
            FileMapping.mapNewInterval$default(getOrRegisterNewSource(sourceFileName == null ? FAKE_FILE_NAME : sourceFileName, sourceInfo.getPathOrCleanFQN()), 1, 1, sourceInfo.getLinesInFile(), null, 8, null);
        }
    }

    public final SourceInfo getSourceInfo() {
        return this.sourceInfo;
    }

    public final List<FileMapping> getResultMappings() {
        Collection<FileMapping> collectionValues = this.fileMappings.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        return CollectionsKt.toList(collectionValues);
    }

    public final boolean isTrivial() {
        SourceInfo sourceInfo;
        int i = this.maxUsedValue;
        return i == 0 || ((sourceInfo = this.sourceInfo) != null && i == sourceInfo.getLinesInFile());
    }

    private final FileMapping getOrRegisterNewSource(String name2, String path) {
        LinkedHashMap<Pair<String, String>, FileMapping> linkedHashMap = this.fileMappings;
        Pair<String, String> pair = TuplesKt.to(name2, path);
        FileMapping fileMapping = linkedHashMap.get(pair);
        if (fileMapping == null) {
            fileMapping = new FileMapping(name2, path);
            linkedHashMap.put(pair, fileMapping);
        }
        return fileMapping;
    }

    public final int mapLineNumber(SourcePosition inlineSource, SourcePosition inlineCallSite) {
        Intrinsics.checkNotNullParameter(inlineSource, "inlineSource");
        int iMapNewLineNumber = getOrRegisterNewSource(inlineSource.getFile(), inlineSource.getPath()).mapNewLineNumber(inlineSource.getLine(), this.maxUsedValue, inlineCallSite);
        this.maxUsedValue = Math.max(this.maxUsedValue, iMapNewLineNumber);
        return iMapNewLineNumber;
    }

    public final int mapSyntheticLineNumber(int id2) {
        return mapLineNumber(new SourcePosition(id2, FAKE_FILE_NAME, FAKE_PATH), null);
    }
}
