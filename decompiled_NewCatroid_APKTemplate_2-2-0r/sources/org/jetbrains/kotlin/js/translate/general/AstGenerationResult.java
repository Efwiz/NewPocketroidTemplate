package org.jetbrains.kotlin.js.translate.general;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.facade.TranslationUnit;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.serialization.js.ast.JsAstDeserializer;
import org.jetbrains.kotlin.serialization.js.ast.JsAstProtoBuf;

/* JADX INFO: compiled from: AstGenerationResult.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001BK\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00040\u0006\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u0018\u0010%\u001a\u0014\u0012\u0004\u0012\u00020'\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0 0&J\u000e\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/general/AstGenerationResult;", "", "units", "", "Lorg/jetbrains/kotlin/js/facade/TranslationUnit;", "translatedSourceFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "Lorg/jetbrains/kotlin/js/translate/general/SourceFileTranslationResult;", "inlineFunctionTagMap", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "(Ljava/util/Collection;Ljava/util/Map;Ljava/util/Map;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/js/config/JsConfig;)V", ResponseCacheMiddleware.CACHE, "", "Lorg/jetbrains/kotlin/js/facade/TranslationUnit$BinaryAst;", "Lorg/jetbrains/kotlin/js/translate/general/DeserializedFileTranslationResult;", "deserializer", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstDeserializer;", "getInlineFunctionTagMap", "()Ljava/util/Map;", "merger", "Lorg/jetbrains/kotlin/js/translate/general/Merger;", "newFragments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "getNewFragments", "()Ljava/util/Set;", "sourceRoots", "", "Ljava/io/File;", "getTranslatedSourceFiles", "getUnits", "()Ljava/util/Collection;", "buildProgram", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "getTranslationResult", "Lorg/jetbrains/kotlin/js/translate/general/FileTranslationResult;", "unit", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AstGenerationResult {
    private final Map<TranslationUnit.BinaryAst, DeserializedFileTranslationResult> cache;
    private final JsAstDeserializer deserializer;
    private final Map<String, TranslationUnit> inlineFunctionTagMap;
    private final Merger merger;
    private final Set<JsProgramFragment> newFragments;
    private final List<File> sourceRoots;
    private final Map<KtFile, SourceFileTranslationResult> translatedSourceFiles;
    private final Collection<TranslationUnit> units;

    /* JADX WARN: Multi-variable type inference failed */
    public AstGenerationResult(Collection<? extends TranslationUnit> units, Map<KtFile, SourceFileTranslationResult> translatedSourceFiles, Map<String, ? extends TranslationUnit> inlineFunctionTagMap, ModuleDescriptor moduleDescriptor, JsConfig config) {
        Intrinsics.checkNotNullParameter(units, "units");
        Intrinsics.checkNotNullParameter(translatedSourceFiles, "translatedSourceFiles");
        Intrinsics.checkNotNullParameter(inlineFunctionTagMap, "inlineFunctionTagMap");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(config, "config");
        this.units = units;
        this.translatedSourceFiles = translatedSourceFiles;
        this.inlineFunctionTagMap = inlineFunctionTagMap;
        Collection<SourceFileTranslationResult> collectionValues = translatedSourceFiles.values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.add(((SourceFileTranslationResult) it.next()).getFragment());
        }
        this.newFragments = CollectionsKt.toSet(arrayList);
        this.cache = new LinkedHashMap();
        String moduleId = config.getModuleId();
        Intrinsics.checkNotNullExpressionValue(moduleId, "getModuleId(...)");
        ModuleKind moduleKind = config.getModuleKind();
        Intrinsics.checkNotNullExpressionValue(moduleKind, "getModuleKind(...)");
        this.merger = new Merger(moduleDescriptor, moduleId, moduleKind);
        List<String> sourceMapRoots = config.getSourceMapRoots();
        Intrinsics.checkNotNullExpressionValue(sourceMapRoots, "getSourceMapRoots(...)");
        List<String> list = sourceMapRoots;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList2.add(new File((String) it2.next()));
        }
        ArrayList arrayList3 = arrayList2;
        this.sourceRoots = arrayList3;
        this.deserializer = new JsAstDeserializer(this.merger.getProgram(), arrayList3);
    }

    public final Collection<TranslationUnit> getUnits() {
        return this.units;
    }

    public final Map<KtFile, SourceFileTranslationResult> getTranslatedSourceFiles() {
        return this.translatedSourceFiles;
    }

    public final Map<String, TranslationUnit> getInlineFunctionTagMap() {
        return this.inlineFunctionTagMap;
    }

    public final Set<JsProgramFragment> getNewFragments() {
        return this.newFragments;
    }

    public final FileTranslationResult getTranslationResult(TranslationUnit unit) throws IOException {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (unit instanceof TranslationUnit.SourceFile) {
            SourceFileTranslationResult sourceFileTranslationResult = this.translatedSourceFiles.get(((TranslationUnit.SourceFile) unit).getFile());
            Intrinsics.checkNotNull(sourceFileTranslationResult);
            return sourceFileTranslationResult;
        }
        if (!(unit instanceof TranslationUnit.BinaryAst)) {
            throw new NoWhenBranchMatchedException();
        }
        Map<TranslationUnit.BinaryAst, DeserializedFileTranslationResult> map = this.cache;
        DeserializedFileTranslationResult deserializedFileTranslationResult = map.get(unit);
        if (deserializedFileTranslationResult == null) {
            TranslationUnit.BinaryAst binaryAst = (TranslationUnit.BinaryAst) unit;
            DeserializedFileTranslationResult deserializedFileTranslationResult2 = new DeserializedFileTranslationResult(this.deserializer.deserialize(new ByteArrayInputStream(binaryAst.getData())), new HashSet(JsAstProtoBuf.InlineData.parseFrom(CodedInputStream.newInstance(binaryAst.getInlineData())).getInlineFunctionTagsList()));
            map.put(unit, deserializedFileTranslationResult2);
            deserializedFileTranslationResult = deserializedFileTranslationResult2;
        }
        return deserializedFileTranslationResult;
    }

    public final Pair<JsProgram, List<String>> buildProgram() {
        Collection<TranslationUnit> collection = this.units;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(getTranslationResult((TranslationUnit) it.next()).getFragment());
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            this.merger.addFragment((JsProgramFragment) it2.next());
        }
        JsProgram jsProgramBuildProgram = this.merger.buildProgram();
        List<JsImportedModule> importedModules = this.merger.getImportedModules();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(importedModules, 10));
        Iterator<T> it3 = importedModules.iterator();
        while (it3.hasNext()) {
            arrayList2.add(((JsImportedModule) it3.next()).getExternalName());
        }
        return TuplesKt.to(jsProgramBuildProgram, arrayList2);
    }
}
