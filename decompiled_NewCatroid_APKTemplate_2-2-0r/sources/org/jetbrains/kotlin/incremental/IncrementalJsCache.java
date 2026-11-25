package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.GeneratedFile;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumerImpl;
import org.jetbrains.kotlin.incremental.js.IrTranslationResultValue;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;
import org.jetbrains.kotlin.incremental.storage.AbstractDirtyClassesMap;
import org.jetbrains.kotlin.incremental.storage.AbstractSourceToOutputMap;
import org.jetbrains.kotlin.incremental.storage.DirtyClassesFqNameMap;
import org.jetbrains.kotlin.incremental.storage.SourceToFqNameMap;
import org.jetbrains.kotlin.incremental.storage.SourceToJsOutputMap;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;

/* JADX INFO: compiled from: IncrementalJsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 F2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001FB\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J\"\u0010/\u001a\u00020,2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u000202012\u0006\u0010-\u001a\u00020.J\u0016\u00103\u001a\u00020,2\u0006\u00104\u001a\u0002052\u0006\u0010-\u001a\u00020.J\u0014\u00106\u001a\b\u0012\u0004\u0012\u00020\u0004072\u0006\u00108\u001a\u00020\u0004J\u0016\u00109\u001a\u00020,2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u000407H\u0016J\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020<01J\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020201J\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020>\u0012\u0004\u0012\u00020\u001201J\u0018\u0010?\u001a\u00020,2\u0006\u0010@\u001a\u00020\u00042\u0006\u0010A\u001a\u00020\u0002H\u0002J\u0014\u0010B\u001a\u00020,2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020E0DR\u0014\u0010\n\u001a\u00020\u000bX\u0090\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u000fj\b\u0012\u0004\u0012\u00020\u0004`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u00128F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\u00020$X\u0090\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u000e\u0010'\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJsCache;", "Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache;", "Lorg/jetbrains/kotlin/name/FqName;", "cachesDir", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "serializerProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "dirtyOutputClassesMap", "Lorg/jetbrains/kotlin/incremental/storage/DirtyClassesFqNameMap;", "getDirtyOutputClassesMap$kotlin_build_common", "()Lorg/jetbrains/kotlin/incremental/storage/DirtyClassesFqNameMap;", "dirtySources", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "value", "", "header", "getHeader", "()[B", "setHeader", "([B)V", "headerFile", "getHeaderFile", "()Ljava/io/File;", "inlineFunctions", "Lorg/jetbrains/kotlin/incremental/InlineFunctionsMap;", "irTranslationResults", "Lorg/jetbrains/kotlin/incremental/IrTranslationResultMap;", "packageMetadata", "Lorg/jetbrains/kotlin/incremental/PackageMetadataMap;", "protoData", "Lorg/jetbrains/kotlin/incremental/ProtoDataProvider;", "sourceToClassesMap", "Lorg/jetbrains/kotlin/incremental/storage/SourceToFqNameMap;", "getSourceToClassesMap$kotlin_build_common", "()Lorg/jetbrains/kotlin/incremental/storage/SourceToFqNameMap;", "sourceToJsOutputsMap", "Lorg/jetbrains/kotlin/incremental/storage/SourceToJsOutputMap;", "translationResults", "Lorg/jetbrains/kotlin/incremental/TranslationResultMap;", "clearCacheForRemovedClasses", "", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "compare", "translatedFiles", "", "Lorg/jetbrains/kotlin/incremental/js/TranslationResultValue;", "compareAndUpdate", "incrementalResults", "Lorg/jetbrains/kotlin/incremental/js/IncrementalResultsConsumerImpl;", "getOutputsBySource", "", "sourceFile", "markDirty", "removedAndCompiledSources", "nonDirtyIrParts", "Lorg/jetbrains/kotlin/incremental/js/IrTranslationResultValue;", "nonDirtyPackageParts", "", "registerOutputForFile", "srcFile", "name", "updateSourceToOutputMap", "generatedFiles", "", "Lorg/jetbrains/kotlin/build/GeneratedFile;", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IncrementalJsCache extends AbstractIncrementalCache<FqName> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final DirtyClassesFqNameMap dirtyOutputClassesMap;
    private final HashSet<File> dirtySources;
    private final IncrementalCompilationContext icContext;
    private final InlineFunctionsMap inlineFunctions;
    private final IrTranslationResultMap irTranslationResults;
    private final PackageMetadataMap packageMetadata;
    private final ProtoDataProvider protoData;
    private final SourceToFqNameMap sourceToClassesMap;
    private final SourceToJsOutputMap sourceToJsOutputsMap;
    private final TranslationResultMap translationResults;

    /* JADX INFO: compiled from: IncrementalJsCache.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJsCache$Companion;", "", "()V", "HEADER_FILE_NAME", "", "INLINE_FUNCTIONS", "IR_TRANSLATION_RESULT_MAP", "PACKAGE_META_FILE", "SOURCE_TO_JS_OUTPUT", "TRANSLATION_RESULT_MAP", "hasHeaderFile", "", "cachesDir", "Ljava/io/File;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean hasHeaderFile(File cachesDir) {
            Intrinsics.checkNotNullParameter(cachesDir, "cachesDir");
            return new File(cachesDir, "header.meta").exists();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IncrementalJsCache(File cachesDir, IncrementalCompilationContext icContext, SerializerExtensionProtocol serializerProtocol) {
        super(cachesDir, icContext);
        Intrinsics.checkNotNullParameter(cachesDir, "cachesDir");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        Intrinsics.checkNotNullParameter(serializerProtocol, "serializerProtocol");
        this.icContext = icContext;
        ProtoDataProvider protoDataProvider = new ProtoDataProvider(serializerProtocol);
        this.protoData = protoDataProvider;
        AbstractIncrementalCache.Companion companion = AbstractIncrementalCache.INSTANCE;
        this.sourceToClassesMap = (SourceToFqNameMap) registerMap(new SourceToFqNameMap(getStorageFile(AbstractIncrementalCache.getSOURCE_TO_CLASSES()), icContext));
        AbstractIncrementalCache.Companion companion2 = AbstractIncrementalCache.INSTANCE;
        this.dirtyOutputClassesMap = (DirtyClassesFqNameMap) registerMap(new DirtyClassesFqNameMap(getStorageFile(AbstractIncrementalCache.getDIRTY_OUTPUT_CLASSES()), icContext));
        this.translationResults = (TranslationResultMap) registerMap(new TranslationResultMap(getStorageFile("translation-result"), protoDataProvider, icContext));
        this.irTranslationResults = (IrTranslationResultMap) registerMap(new IrTranslationResultMap(getStorageFile("ir-translation-result"), icContext));
        this.inlineFunctions = (InlineFunctionsMap) registerMap(new InlineFunctionsMap(getStorageFile("inline-functions"), icContext));
        this.packageMetadata = (PackageMetadataMap) registerMap(new PackageMetadataMap(getStorageFile("packages-meta"), icContext));
        this.sourceToJsOutputsMap = (SourceToJsOutputMap) registerMap(new SourceToJsOutputMap(getStorageFile("source-to-js-output"), icContext));
        this.dirtySources = new HashSet<>();
    }

    @Override // org.jetbrains.kotlin.incremental.AbstractIncrementalCache
    public AbstractSourceToOutputMap<FqName> getSourceToClassesMap$kotlin_build_common() {
        return this.sourceToClassesMap;
    }

    @Override // org.jetbrains.kotlin.incremental.AbstractIncrementalCache
    public AbstractDirtyClassesMap<FqName> getDirtyOutputClassesMap$kotlin_build_common() {
        return this.dirtyOutputClassesMap;
    }

    private final File getHeaderFile() {
        return new File(getCachesDir(), "header.meta");
    }

    public final byte[] getHeader() {
        return FilesKt.readBytes(getHeaderFile());
    }

    public final void setHeader(byte[] value) {
        Intrinsics.checkNotNullParameter(value, "value");
        CompilationTransaction transaction = this.icContext.getTransaction();
        Path path = getHeaderFile().toPath();
        Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
        CompilationTransactionKt.writeBytes(transaction, path, value);
    }

    @Override // org.jetbrains.kotlin.incremental.AbstractIncrementalCache, org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public void markDirty(Collection<? extends File> removedAndCompiledSources) {
        Object obj;
        String strAsString;
        Intrinsics.checkNotNullParameter(removedAndCompiledSources, "removedAndCompiledSources");
        for (File file : removedAndCompiledSources) {
            this.sourceToJsOutputsMap.remove(file);
            Collection<FqName> collection = getSourceToClassesMap$kotlin_build_common().get(file);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                FqName fqNameParentOrNull = FqNamesUtilKt.parentOrNull((FqName) it.next());
                if (fqNameParentOrNull == null || (strAsString = fqNameParentOrNull.asString()) == null) {
                    strAsString = "";
                }
                arrayList.add(strAsString);
            }
            Iterator it2 = arrayList.iterator();
            if (it2.hasNext()) {
                Object next = it2.next();
                if (it2.hasNext()) {
                    int length = ((String) next).length();
                    do {
                        Object next2 = it2.next();
                        int length2 = ((String) next2).length();
                        if (length > length2) {
                            next = next2;
                            length = length2;
                        }
                    } while (it2.hasNext());
                }
                obj = next;
            } else {
                obj = null;
            }
            String str = (String) obj;
            if (str != null) {
                PackageMetadataMap packageMetadataMap = this.packageMetadata;
                Intrinsics.checkNotNull(str);
                packageMetadataMap.remove(str);
            }
        }
        super.markDirty(removedAndCompiledSources);
        this.dirtySources.addAll(removedAndCompiledSources);
    }

    public final void compare(Map<File, TranslationResultValue> translatedFiles, ChangesCollector changesCollector) throws InvalidProtocolBufferException {
        Map<ClassId, ProtoData> mapEmptyMap;
        byte[] metadata;
        Intrinsics.checkNotNullParameter(translatedFiles, "translatedFiles");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        for (Map.Entry<File, TranslationResultValue> entry : translatedFiles.entrySet()) {
            File key = entry.getKey();
            TranslationResultValue value = entry.getValue();
            TranslationResultValue translationResultValue = this.translationResults.get(key);
            if (translationResultValue == null || (metadata = translationResultValue.getMetadata()) == null || (mapEmptyMap = this.protoData.invoke(key, metadata)) == null) {
                mapEmptyMap = MapsKt.emptyMap();
            }
            Map<ClassId, ProtoData> mapInvoke = this.protoData.invoke(key, value.getMetadata());
            for (ClassId classId : SetsKt.plus((Set) mapEmptyMap.keySet(), (Iterable) mapInvoke.keySet())) {
                ChangesCollector.collectProtoChanges$default(changesCollector, mapEmptyMap.get(classId), mapInvoke.get(classId), false, null, 12, null);
            }
        }
    }

    public final Collection<File> getOutputsBySource(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        return this.sourceToJsOutputsMap.get(sourceFile);
    }

    public final void compareAndUpdate(IncrementalResultsConsumerImpl incrementalResults, ChangesCollector changesCollector) throws InvalidProtocolBufferException {
        Map<ClassId, ProtoData> mapEmptyMap;
        byte[] metadata;
        Intrinsics.checkNotNullParameter(incrementalResults, "incrementalResults");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        for (Map.Entry<File, TranslationResultValue> entry : incrementalResults.getPackageParts().entrySet()) {
            File key = entry.getKey();
            TranslationResultValue value = entry.getValue();
            this.dirtySources.remove(key);
            byte[] metadata2 = value.getMetadata();
            byte[] binaryAst = value.getBinaryAst();
            byte[] inlineData = value.getInlineData();
            TranslationResultValue translationResultValue = this.translationResults.get(key);
            if (translationResultValue == null || (metadata = translationResultValue.getMetadata()) == null || (mapEmptyMap = this.protoData.invoke(key, metadata)) == null) {
                mapEmptyMap = MapsKt.emptyMap();
            }
            Map<ClassId, ProtoData> map = mapEmptyMap;
            Map<ClassId, ProtoData> mapInvoke = this.protoData.invoke(key, metadata2);
            for (Map.Entry<ClassId, ProtoData> entry2 : mapInvoke.entrySet()) {
                ClassId key2 = entry2.getKey();
                ProtoData value2 = entry2.getValue();
                FqName fqNameAsSingleFqName = key2.asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                registerOutputForFile(key, fqNameAsSingleFqName);
                if (value2 instanceof ClassProtoData) {
                    addToClassStorage((ClassProtoData) value2, key);
                }
            }
            for (ClassId classId : SetsKt.plus((Set) map.keySet(), (Iterable) mapInvoke.keySet())) {
                ChangesCollector.collectProtoChanges$default(changesCollector, map.get(classId), mapInvoke.get(classId), false, null, 12, null);
                mapInvoke = mapInvoke;
            }
            this.translationResults.put(key, metadata2, binaryAst, inlineData);
        }
        for (Map.Entry<File, Map<String, Long>> entry3 : incrementalResults.getInlineFunctions().entrySet()) {
            this.inlineFunctions.process(entry3.getKey(), entry3.getValue(), changesCollector);
        }
        for (Map.Entry<String, byte[]> entry4 : incrementalResults.getPackageMetadata().entrySet()) {
            this.packageMetadata.put(entry4.getKey(), entry4.getValue());
        }
        for (Map.Entry<File, IrTranslationResultValue> entry5 : incrementalResults.getIrFileData().entrySet()) {
            File key3 = entry5.getKey();
            IrTranslationResultValue value3 = entry5.getValue();
            this.irTranslationResults.put(key3, value3.getFileData(), value3.getTypes(), value3.getSignatures(), value3.getStrings(), value3.getDeclarations(), value3.getBodies(), value3.getFqn(), value3.getFileMetadata(), value3.getDebugInfo());
        }
    }

    private final void registerOutputForFile(File srcFile, FqName name2) {
        getSourceToClassesMap$kotlin_build_common().add(srcFile, name2);
        getDirtyOutputClassesMap$kotlin_build_common().notDirty(name2);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public void clearCacheForRemovedClasses(ChangesCollector changesCollector) {
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        for (File file : this.dirtySources) {
            this.translationResults.remove(file, changesCollector);
            this.irTranslationResults.remove(file);
            this.inlineFunctions.remove(file);
        }
        removeAllFromClassStorage(getDirtyOutputClassesMap$kotlin_build_common().getDirtyOutputClasses(), changesCollector);
        this.dirtySources.clear();
        getDirtyOutputClassesMap$kotlin_build_common().clean();
    }

    public final Map<File, TranslationResultValue> nonDirtyPackageParts() {
        HashMap map = new HashMap();
        for (File file : this.translationResults.keys()) {
            if (!this.dirtySources.contains(file)) {
                TranslationResultValue translationResultValue = this.translationResults.get(file);
                Intrinsics.checkNotNull(translationResultValue);
                map.put(file, translationResultValue);
            }
        }
        return map;
    }

    public final Map<String, byte[]> packageMetadata() {
        HashMap map = new HashMap();
        for (String str : this.packageMetadata.keys()) {
            byte[] bArr = this.packageMetadata.get(str);
            Intrinsics.checkNotNull(bArr);
            map.put(str, bArr);
        }
        return map;
    }

    public final Map<File, IrTranslationResultValue> nonDirtyIrParts() {
        HashMap map = new HashMap();
        for (File file : this.irTranslationResults.keys()) {
            if (!this.dirtySources.contains(file)) {
                IrTranslationResultValue irTranslationResultValue = this.irTranslationResults.get(file);
                Intrinsics.checkNotNull(irTranslationResultValue);
                map.put(file, irTranslationResultValue);
            }
        }
        return map;
    }

    public final void updateSourceToOutputMap(Iterable<? extends GeneratedFile> generatedFiles) {
        Intrinsics.checkNotNullParameter(generatedFiles, "generatedFiles");
        for (GeneratedFile generatedFile : generatedFiles) {
            for (File file : generatedFile.getSourceFiles()) {
                if (this.dirtySources.contains(file)) {
                    this.sourceToJsOutputsMap.add(file, generatedFile.getOutputFile());
                }
            }
        }
    }
}
