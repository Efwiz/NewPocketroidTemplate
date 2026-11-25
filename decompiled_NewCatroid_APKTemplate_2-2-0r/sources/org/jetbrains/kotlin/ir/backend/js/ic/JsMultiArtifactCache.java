package org.jetbrains.kotlin.ir.backend.js.ic;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache.CacheInfo;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsBuilt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsCached;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CrossModuleReferences;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModule;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModuleHeader;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;

/* JADX INFO: compiled from: JsMultiArtifactCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0003&'(B\u0005¢\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\tH&¢\u0006\u0002\u0010\nJ\u0017\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u0006H&J*\u0010\u000f\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00110\u00102\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011H&J\u0015\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011H&J\u001c\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u0019H&J\u0014\u0010\u001c\u001a\u00020\u0017*\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001aH\u0004J\f\u0010\u001f\u001a\u00020 *\u00020!H\u0004J\u0016\u0010\"\u001a\u00020\u0017*\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0004¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$CacheInfo;", "", "()V", "commitCompiledJsCode", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "cacheInfo", "compilationOutputs", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$CacheInfo;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;)Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "fetchCompiledJsCode", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsCached;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$CacheInfo;)Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsCached;", "fetchCompiledJsCodeForNullCacheInfo", "getMainModuleAndDependencies", "Lkotlin/Pair;", "", "loadJsIrModule", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$CacheInfo;)Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "loadProgramHeadersFromCache", "loadRequiredJsIrModules", "", "crossModuleReferences", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "commitJsIrModuleHeaderNames", "Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;", "jsIrHeader", "fetchJsIrModuleHeaderNames", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$JsIrModuleHeaderNames;", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "writeIfNotNull", "Ljava/io/File;", "data", "", "CacheInfo", "JsIrModuleHeaderNames", "NameType", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JsMultiArtifactCache<T extends CacheInfo> {

    /* JADX INFO: compiled from: JsMultiArtifactCache.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$CacheInfo;", "", "jsIrHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "getJsIrHeader", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface CacheInfo {
        JsIrModuleHeader getJsIrHeader();
    }

    public abstract CompilationOutputs commitCompiledJsCode(T cacheInfo, CompilationOutputsBuilt compilationOutputs);

    public abstract CompilationOutputsCached fetchCompiledJsCode(T cacheInfo);

    /* JADX INFO: renamed from: fetchCompiledJsCodeForNullCacheInfo */
    public abstract CompilationOutputs mo7146fetchCompiledJsCodeForNullCacheInfo();

    public abstract Pair<T, List<T>> getMainModuleAndDependencies(List<? extends T> cacheInfo);

    public abstract JsIrModule loadJsIrModule(T cacheInfo);

    public abstract List<T> loadProgramHeadersFromCache();

    public abstract void loadRequiredJsIrModules(Map<JsIrModuleHeader, CrossModuleReferences> crossModuleReferences);

    protected final void writeIfNotNull(File file, String str) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        if (str != null) {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FilesKt.writeText$default(file, str, null, 2, null);
            return;
        }
        file.delete();
    }

    protected final JsIrModuleHeaderNames fetchJsIrModuleHeaderNames(CodedInputStream codedInputStream) throws IOException {
        Intrinsics.checkNotNullParameter(codedInputStream, "<this>");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        HashSet hashSet = new HashSet();
        int int32 = codedInputStream.readInt32();
        for (int i = 0; i < int32; i++) {
            String string = codedInputStream.readString();
            int int33 = codedInputStream.readInt32();
            if ((NameType.DEFINITIONS.getTypeMask() & int33) != 0) {
                linkedHashSet.add(string);
            }
            if ((NameType.OPTIONAL_IMPORTS.getTypeMask() & int33) != 0) {
                hashSet.add(string);
            }
            if ((int33 & NameType.NAME_BINDINGS.getTypeMask()) != 0) {
                Intrinsics.checkNotNull(string);
                String string2 = codedInputStream.readString();
                Intrinsics.checkNotNullExpressionValue(string2, "readString(...)");
                linkedHashMap.put(string, string2);
            }
        }
        return new JsIrModuleHeaderNames(linkedHashSet, linkedHashMap, hashSet);
    }

    protected final void commitJsIrModuleHeaderNames(CodedOutputStream codedOutputStream, JsIrModuleHeader jsIrHeader) throws IOException {
        Intrinsics.checkNotNullParameter(codedOutputStream, "<this>");
        Intrinsics.checkNotNullParameter(jsIrHeader, "jsIrHeader");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, String> entry : jsIrHeader.getNameBindings().entrySet()) {
            linkedHashMap.put(entry.getKey(), TuplesKt.to(Integer.valueOf(NameType.NAME_BINDINGS.getTypeMask()), entry.getValue()));
        }
        Iterator<String> it = jsIrHeader.getOptionalCrossModuleImports().iterator();
        while (true) {
            String str = null;
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            Pair pair = (Pair) linkedHashMap.get(next);
            Integer numValueOf = Integer.valueOf((pair != null ? ((Number) pair.getFirst()).intValue() : 0) | NameType.OPTIONAL_IMPORTS.getTypeMask());
            if (pair != null) {
                str = (String) pair.getSecond();
            }
            linkedHashMap.put(next, TuplesKt.to(numValueOf, str));
        }
        for (String str2 : jsIrHeader.getDefinitions()) {
            Pair pair2 = (Pair) linkedHashMap.get(str2);
            linkedHashMap.put(str2, TuplesKt.to(Integer.valueOf((pair2 != null ? ((Number) pair2.getFirst()).intValue() : 0) | NameType.DEFINITIONS.getTypeMask()), pair2 != null ? (String) pair2.getSecond() : null));
        }
        codedOutputStream.writeInt32NoTag(linkedHashMap.size());
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            String str3 = (String) entry2.getKey();
            Pair pair3 = (Pair) entry2.getValue();
            codedOutputStream.writeStringNoTag(str3);
            codedOutputStream.writeInt32NoTag(((Number) pair3.getFirst()).intValue());
            if (pair3.getSecond() != null) {
                codedOutputStream.writeStringNoTag((String) pair3.getSecond());
            }
        }
    }

    /* JADX INFO: compiled from: JsMultiArtifactCache.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0084\b\u0018\u00002\u00020\u0001B5\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\bJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0015\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0006HÆ\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J?\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0004HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$JsIrModuleHeaderNames;", "", "definitions", "", "", "nameBindings", "", "optionalCrossModuleImports", "(Ljava/util/Set;Ljava/util/Map;Ljava/util/Set;)V", "getDefinitions", "()Ljava/util/Set;", "getNameBindings", "()Ljava/util/Map;", "getOptionalCrossModuleImports", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final /* data */ class JsIrModuleHeaderNames {
        private final Set<String> definitions;
        private final Map<String, String> nameBindings;
        private final Set<String> optionalCrossModuleImports;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ JsIrModuleHeaderNames copy$default(JsIrModuleHeaderNames jsIrModuleHeaderNames, Set set, Map map, Set set2, int i, Object obj) {
            if ((i & 1) != 0) {
                set = jsIrModuleHeaderNames.definitions;
            }
            if ((i & 2) != 0) {
                map = jsIrModuleHeaderNames.nameBindings;
            }
            if ((i & 4) != 0) {
                set2 = jsIrModuleHeaderNames.optionalCrossModuleImports;
            }
            return jsIrModuleHeaderNames.copy(set, map, set2);
        }

        public final Set<String> component1() {
            return this.definitions;
        }

        public final Map<String, String> component2() {
            return this.nameBindings;
        }

        public final Set<String> component3() {
            return this.optionalCrossModuleImports;
        }

        public final JsIrModuleHeaderNames copy(Set<String> definitions, Map<String, String> nameBindings, Set<String> optionalCrossModuleImports) {
            Intrinsics.checkNotNullParameter(definitions, "definitions");
            Intrinsics.checkNotNullParameter(nameBindings, "nameBindings");
            Intrinsics.checkNotNullParameter(optionalCrossModuleImports, "optionalCrossModuleImports");
            return new JsIrModuleHeaderNames(definitions, nameBindings, optionalCrossModuleImports);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof JsIrModuleHeaderNames)) {
                return false;
            }
            JsIrModuleHeaderNames jsIrModuleHeaderNames = (JsIrModuleHeaderNames) other;
            return Intrinsics.areEqual(this.definitions, jsIrModuleHeaderNames.definitions) && Intrinsics.areEqual(this.nameBindings, jsIrModuleHeaderNames.nameBindings) && Intrinsics.areEqual(this.optionalCrossModuleImports, jsIrModuleHeaderNames.optionalCrossModuleImports);
        }

        public int hashCode() {
            return (((this.definitions.hashCode() * 31) + this.nameBindings.hashCode()) * 31) + this.optionalCrossModuleImports.hashCode();
        }

        public String toString() {
            return "JsIrModuleHeaderNames(definitions=" + this.definitions + ", nameBindings=" + this.nameBindings + ", optionalCrossModuleImports=" + this.optionalCrossModuleImports + ')';
        }

        public JsIrModuleHeaderNames(Set<String> definitions, Map<String, String> nameBindings, Set<String> optionalCrossModuleImports) {
            Intrinsics.checkNotNullParameter(definitions, "definitions");
            Intrinsics.checkNotNullParameter(nameBindings, "nameBindings");
            Intrinsics.checkNotNullParameter(optionalCrossModuleImports, "optionalCrossModuleImports");
            this.definitions = definitions;
            this.nameBindings = nameBindings;
            this.optionalCrossModuleImports = optionalCrossModuleImports;
        }

        public final Set<String> getDefinitions() {
            return this.definitions;
        }

        public final Map<String, String> getNameBindings() {
            return this.nameBindings;
        }

        public final Set<String> getOptionalCrossModuleImports() {
            return this.optionalCrossModuleImports;
        }
    }

    /* JADX INFO: compiled from: JsMultiArtifactCache.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0084\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$NameType;", "", "typeMask", "", "(Ljava/lang/String;II)V", "getTypeMask", "()I", "DEFINITIONS", "NAME_BINDINGS", "OPTIONAL_IMPORTS", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected enum NameType {
        DEFINITIONS(1),
        NAME_BINDINGS(2),
        OPTIONAL_IMPORTS(4);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final int typeMask;

        public static EnumEntries<NameType> getEntries() {
            return $ENTRIES;
        }

        NameType(int i) {
            this.typeMask = i;
        }

        public final int getTypeMask() {
            return this.typeMask;
        }
    }
}
