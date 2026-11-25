package org.jetbrains.kotlin.scripting.definitions;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.IdeScriptCompilationConfigurationBuilder;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.RefineConfigurationBuilder;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptConfigurationRefinementContext;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptIdeConfigurationKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import kotlin.script.experimental.dependencies.ScriptDependencies;
import kotlin.script.experimental.dependencies.ScriptReport;
import kotlin.script.experimental.host.FileScriptSource;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmDependency;
import kotlin.script.experimental.jvm.JvmScriptCompilationConfigurationBuilder;
import kotlin.script.experimental.jvm.JvmScriptCompilationKt;
import kotlin.script.experimental.jvm.compat.DiagnosticsUtilKt;
import kotlin.script.experimental.jvm.compat.ExpectedLocationUtilKt;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptCompilationConfigurationFromDefinition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptCompilationConfigurationFromDefinition;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "scriptDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;)V", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getScriptDefinition", "()Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptCompilationConfigurationFromDefinition extends ScriptCompilationConfiguration {
    private final ScriptingHostConfiguration hostConfiguration;
    private final KotlinScriptDefinition scriptDefinition;

    public final ScriptingHostConfiguration getHostConfiguration() {
        return this.hostConfiguration;
    }

    public final KotlinScriptDefinition getScriptDefinition() {
        return this.scriptDefinition;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptCompilationConfigurationFromDefinition(final ScriptingHostConfiguration hostConfiguration, final KotlinScriptDefinition scriptDefinition) {
        super(new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinition.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptCompilationConfiguration.Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "$this$null");
                ScriptCompilationConfiguration.Builder builder2 = builder;
                builder.invoke(ScriptCompilationKt.getHostConfiguration(builder2), hostConfiguration);
                builder.invoke(ScriptCompilationKt.getDisplayName(builder2), scriptDefinition.getName());
                builder.invoke(ScriptCompilationKt.getFileExtension(builder2), scriptDefinition.getFileExtension());
                builder.invoke(ScriptCompilationKt.getBaseClass(builder2), new KotlinType((KClass) scriptDefinition.getTemplate(), false, 2, (DefaultConstructorMarker) null));
                PropertiesCollection.Key<List<KotlinType>> implicitReceivers = ScriptCompilationKt.getImplicitReceivers(builder2);
                List<KType> implicitReceivers2 = scriptDefinition.getImplicitReceivers();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(implicitReceivers2, 10));
                Iterator<T> it = implicitReceivers2.iterator();
                while (it.hasNext()) {
                    arrayList.add(new KotlinType((KType) it.next()));
                }
                builder.putIfAny(implicitReceivers, arrayList);
                PropertiesCollection.Key<Map<String, KotlinType>> providedProperties = ScriptCompilationKt.getProvidedProperties(builder2);
                List<Pair<String, KType>> providedProperties2 = scriptDefinition.getProvidedProperties();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(providedProperties2, 10));
                Iterator<T> it2 = providedProperties2.iterator();
                while (it2.hasNext()) {
                    Pair pair = (Pair) it2.next();
                    arrayList2.add(TuplesKt.to(pair.getFirst(), new KotlinType((KType) pair.getSecond())));
                }
                builder.putIfAny_map(providedProperties, arrayList2);
                PropertiesCollection.Key<List<KotlinType>> annotationsForSamWithReceivers = ScriptCompilationConfigurationFromDefinitionKt.getAnnotationsForSamWithReceivers(builder2);
                List<String> annotationsForSamWithReceivers2 = scriptDefinition.getAnnotationsForSamWithReceivers();
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotationsForSamWithReceivers2, 10));
                Iterator<T> it3 = annotationsForSamWithReceivers2.iterator();
                while (it3.hasNext()) {
                    arrayList3.add(new KotlinType((String) it3.next(), false, 2, (DefaultConstructorMarker) null));
                }
                builder.put(annotationsForSamWithReceivers, arrayList3);
                builder.invoke(ScriptCompilationConfigurationFromDefinitionKt.getPlatform(builder2), scriptDefinition.getPlatform());
                builder.putIfAny(ScriptCompilationKt.getCompilerOptions(builder2), scriptDefinition.getAdditionalCompilerArguments());
                if (ArraysKt.contains(new String[]{"gradle.kts", "init.gradle.kts", "settings.gradle.kts"}, builder.get(ScriptCompilationKt.getFileExtension(builder2)))) {
                    builder.invoke((PropertiesCollection.Key<boolean>) ScriptCompilationKt.isStandalone(builder2), false);
                }
                IdeScriptCompilationConfigurationBuilder ide = ScriptIdeConfigurationKt.getIde(builder2);
                final KotlinScriptDefinition kotlinScriptDefinition = scriptDefinition;
                builder.invoke(ide, new Function1<IdeScriptCompilationConfigurationBuilder, Unit>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinition.1.4
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(IdeScriptCompilationConfigurationBuilder ideScriptCompilationConfigurationBuilder) {
                        invoke2(ideScriptCompilationConfigurationBuilder);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(IdeScriptCompilationConfigurationBuilder invoke) {
                        Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                        invoke.put(ScriptIdeConfigurationKt.getAcceptedLocations(invoke), ExpectedLocationUtilKt.mapLegacyExpectedLocations(kotlinScriptDefinition.getScriptExpectedLocations()));
                    }
                });
                if (Intrinsics.areEqual(scriptDefinition.getDependencyResolver(), DependenciesResolver.NoDependencies.INSTANCE)) {
                    return;
                }
                RefineConfigurationBuilder refineConfiguration = ScriptCompilationKt.getRefineConfiguration(builder);
                final KotlinScriptDefinition kotlinScriptDefinition2 = scriptDefinition;
                builder.invoke(refineConfiguration, new Function1<RefineConfigurationBuilder, Unit>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinition.1.5
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(RefineConfigurationBuilder refineConfigurationBuilder) {
                        invoke2(refineConfigurationBuilder);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(RefineConfigurationBuilder invoke) {
                        Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                        List<KClass<? extends Annotation>> acceptedAnnotations = kotlinScriptDefinition2.getAcceptedAnnotations();
                        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(acceptedAnnotations, 10));
                        Iterator<T> it4 = acceptedAnnotations.iterator();
                        while (it4.hasNext()) {
                            arrayList4.add(new KotlinType((KClass) it4.next(), false, 2, (DefaultConstructorMarker) null));
                        }
                        final KotlinScriptDefinition kotlinScriptDefinition3 = kotlinScriptDefinition2;
                        invoke.onAnnotations((List<KotlinType>) arrayList4, (Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>>) new Function1<ScriptConfigurationRefinementContext, ResultWithDiagnostics<? extends ScriptCompilationConfiguration>>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinition.1.5.2
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final ResultWithDiagnostics<ScriptCompilationConfiguration> invoke(ScriptConfigurationRefinementContext context) {
                                Function0 function0;
                                Intrinsics.checkNotNullParameter(context, "context");
                                DependenciesResolver dependencyResolver = kotlinScriptDefinition3.getDependencyResolver();
                                ScriptContentsFromRefinementContext scriptContentsFromRefinementContext = new ScriptContentsFromRefinementContext(context);
                                ScriptingHostConfiguration scriptingHostConfiguration = (ScriptingHostConfiguration) context.getCompilationConfiguration().get(ScriptCompilationKt.getHostConfiguration(ScriptCompilationConfiguration.INSTANCE));
                                Map<String, ? extends Object> mapEmptyMap = (scriptingHostConfiguration == null || (function0 = (Function0) scriptingHostConfiguration.get(ScriptCompilationConfigurationFromDefinitionKt.getGetEnvironment(ScriptingHostConfiguration.INSTANCE))) == null) ? null : (Map) function0.invoke();
                                if (mapEmptyMap == null) {
                                    mapEmptyMap = MapsKt.emptyMap();
                                }
                                DependenciesResolver.ResolveResult resolveResultResolve = dependencyResolver.resolve(scriptContentsFromRefinementContext, mapEmptyMap);
                                List<ScriptReport> reports = resolveResultResolve.getReports();
                                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(reports, 10));
                                for (ScriptReport scriptReport : reports) {
                                    arrayList5.add(new ScriptDiagnostic(-1, scriptReport.getMessage(), DiagnosticsUtilKt.mapLegacyDiagnosticSeverity(scriptReport.getSeverity()), null, null, null, 56, null));
                                }
                                ArrayList arrayList6 = arrayList5;
                                DependenciesResolver.ResolveResult.Success success = resolveResultResolve instanceof DependenciesResolver.ResolveResult.Success ? (DependenciesResolver.ResolveResult.Success) resolveResultResolve : null;
                                final ScriptDependencies dependencies = success != null ? success.getDependencies() : null;
                                if (dependencies == null) {
                                    return new ResultWithDiagnostics.Failure(arrayList6);
                                }
                                return ErrorHandlingKt.asSuccess(new ScriptCompilationConfiguration(new ScriptCompilationConfiguration[]{context.getCompilationConfiguration()}, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinition.1.5.2.1
                                    {
                                        super(1);
                                    }

                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2(ScriptCompilationConfiguration.Builder $receiver) {
                                        Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                                        if (!dependencies.getClasspath().isEmpty()) {
                                            $receiver.append(ScriptCompilationKt.getDependencies($receiver), new JvmDependency(dependencies.getClasspath()));
                                        }
                                        ScriptCompilationConfiguration.Builder builder3 = $receiver;
                                        $receiver.appendToList(ScriptCompilationKt.getDefaultImports(builder3), dependencies.getImports());
                                        PropertiesCollection.Key<List<SourceCode>> importScripts = ScriptCompilationKt.getImportScripts(builder3);
                                        List<File> scripts = dependencies.getScripts();
                                        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(scripts, 10));
                                        Iterator<T> it5 = scripts.iterator();
                                        while (it5.hasNext()) {
                                            arrayList7.add(new FileScriptSource((File) it5.next(), null, 2, null));
                                        }
                                        $receiver.appendToList(importScripts, arrayList7);
                                        JvmScriptCompilationConfigurationBuilder jvm = JvmScriptCompilationKt.getJvm(builder3);
                                        final ScriptDependencies scriptDependencies = dependencies;
                                        $receiver.invoke(jvm, new Function1<JvmScriptCompilationConfigurationBuilder, Unit>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinition.1.5.2.1.2
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public /* bridge */ /* synthetic */ Unit invoke(JvmScriptCompilationConfigurationBuilder jvmScriptCompilationConfigurationBuilder) {
                                                invoke2(jvmScriptCompilationConfigurationBuilder);
                                                return Unit.INSTANCE;
                                            }

                                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2(JvmScriptCompilationConfigurationBuilder invoke2) {
                                                Intrinsics.checkNotNullParameter(invoke2, "$this$invoke");
                                                invoke2.putIfNotNull(JvmScriptCompilationKt.getJdkHome(invoke2), scriptDependencies.getJavaHome());
                                            }
                                        });
                                        if (dependencies.getSources().isEmpty()) {
                                            return;
                                        }
                                        IdeScriptCompilationConfigurationBuilder ide2 = ScriptIdeConfigurationKt.getIde(builder3);
                                        final ScriptDependencies scriptDependencies2 = dependencies;
                                        $receiver.invoke(ide2, new Function1<IdeScriptCompilationConfigurationBuilder, Unit>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinition.1.5.2.1.3
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public /* bridge */ /* synthetic */ Unit invoke(IdeScriptCompilationConfigurationBuilder ideScriptCompilationConfigurationBuilder) {
                                                invoke2(ideScriptCompilationConfigurationBuilder);
                                                return Unit.INSTANCE;
                                            }

                                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2(IdeScriptCompilationConfigurationBuilder invoke2) {
                                                Intrinsics.checkNotNullParameter(invoke2, "$this$invoke");
                                                invoke2.append(ScriptIdeConfigurationKt.getDependenciesSources(invoke2), new JvmDependency(scriptDependencies2.getSources()));
                                            }
                                        });
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder3) {
                                        invoke2(builder3);
                                        return Unit.INSTANCE;
                                    }
                                }), arrayList6);
                            }
                        });
                    }
                });
            }
        });
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(scriptDefinition, "scriptDefinition");
        this.hostConfiguration = hostConfiguration;
        this.scriptDefinition = scriptDefinition;
    }
}
