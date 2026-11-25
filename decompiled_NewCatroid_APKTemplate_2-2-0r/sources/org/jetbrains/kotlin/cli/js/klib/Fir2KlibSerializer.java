package org.jetbrains.kotlin.cli.js.klib;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.ConstValueProviderImpl;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.pipeline.Fir2IrActualizedResult;
import org.jetbrains.kotlin.fir.pipeline.ModuleCompilerAnalyzedOutput;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.serialization.FirKLibSerializerExtension;
import org.jetbrains.kotlin.fir.serialization.FirKlibSerializationKt;
import org.jetbrains.kotlin.ir.backend.js.ModulesStructure;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;

/* JADX INFO: compiled from: compilerPipeline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0013R#\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R,\u0010\u0011\u001a \u0012\u0004\u0012\u00020\u0013\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u00140\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00130\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/cli/js/klib/Fir2KlibSerializer;", "", "moduleStructure", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "firOutputs", "", "Lorg/jetbrains/kotlin/fir/pipeline/ModuleCompilerAnalyzedOutput;", "fir2IrActualizedResult", "Lorg/jetbrains/kotlin/fir/pipeline/Fir2IrActualizedResult;", "(Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;Ljava/util/List;Lorg/jetbrains/kotlin/fir/pipeline/Fir2IrActualizedResult;)V", "actualizedExpectDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getActualizedExpectDeclarations", "()Ljava/util/Set;", "actualizedExpectDeclarations$delegate", "Lkotlin/Lazy;", "firFilesAndSessionsBySourceFile", "", "Lorg/jetbrains/kotlin/KtSourceFile;", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/FirSession;", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "sourceFiles", "getSourceFiles", "()Ljava/util/List;", "serializeSingleFirFile", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "file", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class Fir2KlibSerializer {

    /* JADX INFO: renamed from: actualizedExpectDeclarations$delegate, reason: from kotlin metadata */
    private final Lazy actualizedExpectDeclarations;
    private final Fir2IrActualizedResult fir2IrActualizedResult;
    private final Map<KtSourceFile, Triple<FirFile, FirSession, ScopeSession>> firFilesAndSessionsBySourceFile;
    private final List<ModuleCompilerAnalyzedOutput> firOutputs;
    private final LanguageVersionSettings languageVersionSettings;
    private final BinaryVersion metadataVersion;
    private final List<KtSourceFile> sourceFiles;

    public Fir2KlibSerializer(ModulesStructure moduleStructure, List<ModuleCompilerAnalyzedOutput> firOutputs, Fir2IrActualizedResult fir2IrActualizedResult) {
        Intrinsics.checkNotNullParameter(moduleStructure, "moduleStructure");
        Intrinsics.checkNotNullParameter(firOutputs, "firOutputs");
        Intrinsics.checkNotNullParameter(fir2IrActualizedResult, "fir2IrActualizedResult");
        this.firOutputs = firOutputs;
        this.fir2IrActualizedResult = fir2IrActualizedResult;
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (ModuleCompilerAnalyzedOutput moduleCompilerAnalyzedOutput : firOutputs) {
            for (FirFile firFile : moduleCompilerAnalyzedOutput.getFir()) {
                KtSourceFile sourceFile = firFile.getSourceFile();
                Intrinsics.checkNotNull(sourceFile);
                mapCreateMapBuilder.put(sourceFile, new Triple(firFile, moduleCompilerAnalyzedOutput.getSession(), moduleCompilerAnalyzedOutput.getScopeSession()));
            }
        }
        Map<KtSourceFile, Triple<FirFile, FirSession, ScopeSession>> mapBuild = MapsKt.build(mapCreateMapBuilder);
        this.firFilesAndSessionsBySourceFile = mapBuild;
        this.actualizedExpectDeclarations = LazyKt.lazy(new Function0<Set<? extends FirDeclaration>>() { // from class: org.jetbrains.kotlin.cli.js.klib.Fir2KlibSerializer$actualizedExpectDeclarations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends FirDeclaration> invoke() {
                return ConversionUtilsKt.extractFirDeclarations(this.this$0.fir2IrActualizedResult.getIrActualizedResult());
            }
        });
        this.metadataVersion = MetadataHelpersKt.metadataVersion$default(moduleStructure.getCompilerConfiguration(), null, 1, null);
        this.languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(moduleStructure.getCompilerConfiguration());
        this.sourceFiles = CollectionsKt.toList(mapBuild.keySet());
    }

    private final Set<FirDeclaration> getActualizedExpectDeclarations() {
        return (Set) this.actualizedExpectDeclarations.getValue();
    }

    public final List<KtSourceFile> getSourceFiles() {
        return this.sourceFiles;
    }

    public final ProtoBuf.PackageFragment serializeSingleFirFile(KtSourceFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        Triple<FirFile, FirSession, ScopeSession> triple = this.firFilesAndSessionsBySourceFile.get(file);
        if (triple == null) {
            throw new IllegalStateException(("cannot find FIR file by source file " + file.getName() + " (" + file.getPath() + ')').toString());
        }
        FirFile firFileComponent1 = triple.component1();
        FirSession firSessionComponent2 = triple.component2();
        return FirKlibSerializationKt.serializeSingleFirFile(firFileComponent1, firSessionComponent2, triple.component3(), getActualizedExpectDeclarations(), new FirKLibSerializerExtension(firSessionComponent2, this.metadataVersion, new ConstValueProviderImpl(this.fir2IrActualizedResult.getComponents()), false, false, this.fir2IrActualizedResult.getComponents().getAnnotationsFromPluginRegistrar().createMetadataAnnotationsProvider()), this.languageVersionSettings);
    }
}
