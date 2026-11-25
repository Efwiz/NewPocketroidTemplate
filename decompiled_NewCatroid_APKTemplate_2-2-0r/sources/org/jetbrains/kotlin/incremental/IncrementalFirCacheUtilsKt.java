package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.backend.FirMetadataSource;
import org.jetbrains.kotlin.fir.backend.jvm.FirMetadataSerializer;
import org.jetbrains.kotlin.fir.backend.jvm.FirMetadataSerializerKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.pipeline.FirResult;
import org.jetbrains.kotlin.fir.pipeline.ModuleCompilerAnalyzedOutput;
import org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: incrementalFirCacheUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aN\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000¨\u0006\u0010"}, d2 = {"collectNewDirtySources", "Ljava/util/LinkedHashSet;", "Ljava/io/File;", "Lkotlin/collections/LinkedHashSet;", "analysisResults", "Lorg/jetbrains/kotlin/fir/pipeline/FirResult;", "targetId", "Lorg/jetbrains/kotlin/modules/TargetId;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "caches", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCachesManager;", "alreadyCompiledSources", "", "reporter", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "incremental-compilation-impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IncrementalFirCacheUtilsKt {
    public static final LinkedHashSet<File> collectNewDirtySources(FirResult analysisResults, TargetId targetId, CompilerConfiguration configuration, IncrementalJvmCachesManager caches, Set<? extends File> alreadyCompiledSources, ICReporter reporter) {
        Intrinsics.checkNotNullParameter(analysisResults, "analysisResults");
        Intrinsics.checkNotNullParameter(targetId, "targetId");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(alreadyCompiledSources, "alreadyCompiledSources");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        ChangesCollector changesCollector = new ChangesCollector();
        JvmSerializationBindings jvmSerializationBindings = new JvmSerializationBindings();
        Iterator<ModuleCompilerAnalyzedOutput> it = analysisResults.getOutputs().iterator();
        while (it.hasNext()) {
            collectNewDirtySources$visitFirFiles(jvmSerializationBindings, targetId, configuration, caches, changesCollector, it.next());
        }
        DirtyData changedAndImpactedSymbols = BuildUtilKt.getChangedAndImpactedSymbols(changesCollector, CollectionsKt.listOf(caches.getPlatformCache()), reporter);
        Collection<LookupSymbol> collectionComponent1 = changedAndImpactedSymbols.component1();
        Collection<FqName> collectionComponent2 = changedAndImpactedSymbols.component2();
        Set setMapClassesFqNamesToFiles$default = BuildUtilKt.mapClassesFqNamesToFiles$default(CollectionsKt.listOf(caches.getPlatformCache()), changedAndImpactedSymbols.component3(), reporter, null, 8, null);
        LinkedHashSet<File> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(BuildUtilKt.mapLookupSymbolsToFiles(caches.getLookupCache(), collectionComponent1, reporter, alreadyCompiledSources));
        linkedHashSet.addAll(BuildUtilKt.mapClassesFqNamesToFiles(CollectionsKt.listOf(caches.getPlatformCache()), collectionComponent2, reporter, alreadyCompiledSources));
        Set set = setMapClassesFqNamesToFiles$default;
        if (!alreadyCompiledSources.containsAll(set)) {
            linkedHashSet.addAll(set);
        }
        CollectionsKt.removeAll(linkedHashSet, new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.incremental.IncrementalFirCacheUtilsKt$collectNewDirtySources$1$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(!it2.exists());
            }
        });
        return linkedHashSet;
    }

    private static final void collectNewDirtySources$visitFirFiles(final JvmSerializationBindings jvmSerializationBindings, final TargetId targetId, final CompilerConfiguration compilerConfiguration, final IncrementalJvmCachesManager incrementalJvmCachesManager, final ChangesCollector changesCollector, final ModuleCompilerAnalyzedOutput moduleCompilerAnalyzedOutput) {
        Iterator<T> it = moduleCompilerAnalyzedOutput.getFir().iterator();
        while (it.hasNext()) {
            ((FirFile) it.next()).accept(new FirVisitor<Unit, List<MetadataSerializer>>() { // from class: org.jetbrains.kotlin.incremental.IncrementalFirCacheUtilsKt$collectNewDirtySources$visitFirFiles$1$1
                @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
                public /* bridge */ /* synthetic */ Unit visitAnonymousObject(FirAnonymousObject firAnonymousObject, List<MetadataSerializer> list) throws IOException {
                    visitAnonymousObject2(firAnonymousObject, list);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
                public /* bridge */ /* synthetic */ Unit visitClass(FirClass firClass, List<MetadataSerializer> list) throws IOException {
                    visitClass2(firClass, list);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
                public /* bridge */ /* synthetic */ Unit visitConstructor(FirConstructor firConstructor, List<MetadataSerializer> list) {
                    visitConstructor2(firConstructor, list);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
                public /* bridge */ /* synthetic */ Unit visitElement(FirElement firElement, List<MetadataSerializer> list) {
                    visitElement2(firElement, list);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
                public /* bridge */ /* synthetic */ Unit visitFile(FirFile firFile, List<MetadataSerializer> list) {
                    visitFile2(firFile, list);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
                public /* bridge */ /* synthetic */ Unit visitProperty(FirProperty firProperty, List<MetadataSerializer> list) {
                    visitProperty2(firProperty, list);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
                public /* bridge */ /* synthetic */ Unit visitRegularClass(FirRegularClass firRegularClass, List<MetadataSerializer> list) throws IOException {
                    visitRegularClass2(firRegularClass, list);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
                public /* bridge */ /* synthetic */ Unit visitSimpleFunction(FirSimpleFunction firSimpleFunction, List<MetadataSerializer> list) {
                    visitSimpleFunction2(firSimpleFunction, list);
                    return Unit.INSTANCE;
                }

                public final void withMetadataSerializer(FirMetadataSource metadata, List<MetadataSerializer> data, Function1<? super MetadataSerializer, Unit> body) {
                    Intrinsics.checkNotNullParameter(metadata, "metadata");
                    Intrinsics.checkNotNullParameter(data, "data");
                    Intrinsics.checkNotNullParameter(body, "body");
                    FirMetadataSerializer firMetadataSerializerMakeLocalFirMetadataSerializerForMetadataSource = FirMetadataSerializerKt.makeLocalFirMetadataSerializerForMetadataSource(metadata, moduleCompilerAnalyzedOutput.getSession(), moduleCompilerAnalyzedOutput.getScopeSession(), jvmSerializationBindings, (MetadataSerializer) CollectionsKt.lastOrNull((List) data), targetId, compilerConfiguration, null);
                    org.jetbrains.kotlin.backend.common.UtilsKt.push(data, firMetadataSerializerMakeLocalFirMetadataSerializerForMetadataSource);
                    body.invoke(firMetadataSerializerMakeLocalFirMetadataSerializerForMetadataSource);
                    org.jetbrains.kotlin.backend.common.UtilsKt.pop(data);
                }

                /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
                public void visitElement2(FirElement element, List<MetadataSerializer> data) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    Intrinsics.checkNotNullParameter(data, "data");
                    element.acceptChildren(this, data);
                }

                /* JADX INFO: renamed from: visitRegularClass, reason: avoid collision after fix types in other method */
                public void visitRegularClass2(FirRegularClass regularClass, List<MetadataSerializer> data) throws IOException {
                    Intrinsics.checkNotNullParameter(regularClass, "regularClass");
                    Intrinsics.checkNotNullParameter(data, "data");
                    visitClass2((FirClass) regularClass, data);
                }

                /* JADX INFO: renamed from: visitAnonymousObject, reason: avoid collision after fix types in other method */
                public void visitAnonymousObject2(FirAnonymousObject anonymousObject, List<MetadataSerializer> data) throws IOException {
                    Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
                    Intrinsics.checkNotNullParameter(data, "data");
                    visitClass2((FirClass) anonymousObject, data);
                }

                /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
                public void visitFile2(FirFile file, List<MetadataSerializer> data) {
                    Intrinsics.checkNotNullParameter(file, "file");
                    Intrinsics.checkNotNullParameter(data, "data");
                    org.jetbrains.kotlin.backend.common.UtilsKt.push(data, FirMetadataSerializerKt.makeLocalFirMetadataSerializerForMetadataSource(new FirMetadataSource.File(CollectionsKt.listOf(file)), moduleCompilerAnalyzedOutput.getSession(), moduleCompilerAnalyzedOutput.getScopeSession(), jvmSerializationBindings, (MetadataSerializer) CollectionsKt.lastOrNull((List) data), targetId, compilerConfiguration, null));
                    file.acceptChildren(this, data);
                    org.jetbrains.kotlin.backend.common.UtilsKt.pop(data);
                }

                /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
                public void visitSimpleFunction2(FirSimpleFunction simpleFunction, List<MetadataSerializer> data) {
                    Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
                    Intrinsics.checkNotNullParameter(data, "data");
                    MetadataSerializer metadataSerializer = (MetadataSerializer) CollectionsKt.firstOrNull((List) data);
                    if (metadataSerializer != null) {
                        FirSimpleFunction firSimpleFunction = simpleFunction;
                        super.visitFunction(firSimpleFunction, data);
                        metadataSerializer.bindMethodMetadata(new FirMetadataSource.Function(firSimpleFunction), new Method(simpleFunction.getName().asString(), SignatureUtilsKt.computeJvmDescriptor$default(firSimpleFunction, null, false, null, 7, null)));
                    }
                }

                /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
                public void visitConstructor2(FirConstructor constructor, List<MetadataSerializer> data) {
                    Intrinsics.checkNotNullParameter(constructor, "constructor");
                    Intrinsics.checkNotNullParameter(data, "data");
                    super.visitConstructor(constructor, data);
                    FirConstructor firConstructor = constructor;
                    ((MetadataSerializer) CollectionsKt.first((List) data)).bindMethodMetadata(new FirMetadataSource.Function(firConstructor), new Method(SpecialNames.INIT.asString(), SignatureUtilsKt.computeJvmDescriptor$default(firConstructor, "", false, null, 6, null)));
                }

                /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
                public void visitProperty2(FirProperty property, List<MetadataSerializer> data) {
                    Intrinsics.checkNotNullParameter(property, "property");
                    Intrinsics.checkNotNullParameter(data, "data");
                    property.acceptChildren(this, data);
                }

                /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
                public void visitClass2(FirClass klass, List<MetadataSerializer> data) throws IOException {
                    Intrinsics.checkNotNullParameter(klass, "klass");
                    Intrinsics.checkNotNullParameter(data, "data");
                    FirMetadataSource.Class r3 = new FirMetadataSource.Class(klass);
                    IncrementalJvmCachesManager incrementalJvmCachesManager2 = incrementalJvmCachesManager;
                    ChangesCollector changesCollector2 = changesCollector;
                    FirMetadataSerializer firMetadataSerializerMakeLocalFirMetadataSerializerForMetadataSource = FirMetadataSerializerKt.makeLocalFirMetadataSerializerForMetadataSource(r3, moduleCompilerAnalyzedOutput.getSession(), moduleCompilerAnalyzedOutput.getScopeSession(), jvmSerializationBindings, (MetadataSerializer) CollectionsKt.lastOrNull((List) data), targetId, compilerConfiguration, null);
                    org.jetbrains.kotlin.backend.common.UtilsKt.push(data, firMetadataSerializerMakeLocalFirMetadataSerializerForMetadataSource);
                    klass.acceptChildren(this, data);
                    Pair<MessageLite, JvmStringTable> pairSerialize = firMetadataSerializerMakeLocalFirMetadataSerializerForMetadataSource.serialize(r3);
                    if (pairSerialize != null) {
                        MessageLite messageLiteComponent1 = pairSerialize.component1();
                        JvmStringTable jvmStringTableComponent2 = pairSerialize.component2();
                        IncrementalJvmCache platformCache = incrementalJvmCachesManager2.getPlatformCache();
                        ClassId classId = FirDeclarationUtilKt.getClassId(klass);
                        Intrinsics.checkNotNull(messageLiteComponent1, "null cannot be cast to non-null type org.jetbrains.kotlin.metadata.ProtoBuf.Class");
                        platformCache.saveFrontendClassToCache(classId, (ProtoBuf.Class) messageLiteComponent1, jvmStringTableComponent2, null, changesCollector2);
                    }
                    org.jetbrains.kotlin.backend.common.UtilsKt.pop(data);
                }
            }, new ArrayList());
        }
    }
}
