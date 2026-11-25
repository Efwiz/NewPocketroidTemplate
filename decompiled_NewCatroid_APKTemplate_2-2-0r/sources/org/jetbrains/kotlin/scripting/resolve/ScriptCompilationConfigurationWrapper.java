package org.jetbrains.kotlin.scripting.resolve;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.IdeScriptCompilationConfigurationBuilder;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptIdeConfigurationKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.dependencies.ScriptDependencies;
import kotlin.script.experimental.host.FileScriptSource;
import kotlin.script.experimental.jvm.JvmDependency;
import kotlin.script.experimental.jvm.JvmScriptCompilationConfigurationBuilder;
import kotlin.script.experimental.jvm.JvmScriptCompilationKt;
import kotlin.script.experimental.jvm.impl.BridgeDependenciesResolverKt;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: refineCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001:\u0002%&B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010#\u001a\u00020$H\u0016R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\rR\u0018\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\rR\u0018\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\rR\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u00198&X§\u0004¢\u0006\f\u0012\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationWrapper;", "", "script", "Lkotlin/script/experimental/api/SourceCode;", "(Lkotlin/script/experimental/api/SourceCode;)V", "configuration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "defaultImports", "", "", "getDefaultImports", "()Ljava/util/List;", "dependenciesClassPath", "Ljava/io/File;", "getDependenciesClassPath", "dependenciesSources", "getDependenciesSources", "importedScripts", "getImportedScripts", "javaHome", "getJavaHome", "()Ljava/io/File;", "legacyDependencies", "Lkotlin/script/experimental/dependencies/ScriptDependencies;", "getLegacyDependencies$annotations", "()V", "getLegacyDependencies", "()Lkotlin/script/experimental/dependencies/ScriptDependencies;", "getScript", "()Lkotlin/script/experimental/api/SourceCode;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "FromCompilationConfiguration", "FromLegacy", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ScriptCompilationConfigurationWrapper {
    private final SourceCode script;

    @Deprecated(message = "Use configuration collection instead")
    public static /* synthetic */ void getLegacyDependencies$annotations() {
    }

    public abstract ScriptCompilationConfiguration getConfiguration();

    public abstract List<String> getDefaultImports();

    public abstract List<File> getDependenciesClassPath();

    public abstract List<File> getDependenciesSources();

    public abstract List<SourceCode> getImportedScripts();

    public abstract File getJavaHome();

    public abstract ScriptDependencies getLegacyDependencies();

    public ScriptCompilationConfigurationWrapper(SourceCode script) {
        Intrinsics.checkNotNullParameter(script, "script");
        this.script = script;
    }

    public final SourceCode getScript() {
        return this.script;
    }

    public boolean equals(Object other) {
        SourceCode sourceCode = this.script;
        ScriptCompilationConfigurationWrapper scriptCompilationConfigurationWrapper = other instanceof ScriptCompilationConfigurationWrapper ? (ScriptCompilationConfigurationWrapper) other : null;
        return Intrinsics.areEqual(sourceCode, scriptCompilationConfigurationWrapper != null ? scriptCompilationConfigurationWrapper.script : null);
    }

    public int hashCode() {
        return this.script.hashCode();
    }

    /* JADX INFO: compiled from: refineCompilationConfiguration.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0096\u0002J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020\u000bH\u0016R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR!\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0010\u0010\rR!\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\n8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0012\u001a\u0004\b\u0014\u0010\rR\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\rR\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001c8VX\u0096\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 ¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationWrapper$FromCompilationConfiguration;", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationWrapper;", "script", "Lkotlin/script/experimental/api/SourceCode;", "configuration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "(Lkotlin/script/experimental/api/SourceCode;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;)V", "getConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "defaultImports", "", "", "getDefaultImports", "()Ljava/util/List;", "dependenciesClassPath", "Ljava/io/File;", "getDependenciesClassPath", "dependenciesClassPath$delegate", "Lkotlin/Lazy;", "dependenciesSources", "getDependenciesSources", "dependenciesSources$delegate", "importedScripts", "getImportedScripts", "javaHome", "getJavaHome", "()Ljava/io/File;", "legacyDependencies", "Lkotlin/script/experimental/dependencies/ScriptDependencies;", "getLegacyDependencies$annotations", "()V", "getLegacyDependencies", "()Lkotlin/script/experimental/dependencies/ScriptDependencies;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FromCompilationConfiguration extends ScriptCompilationConfigurationWrapper {
        private final ScriptCompilationConfiguration configuration;

        /* JADX INFO: renamed from: dependenciesClassPath$delegate, reason: from kotlin metadata */
        private final Lazy dependenciesClassPath;

        /* JADX INFO: renamed from: dependenciesSources$delegate, reason: from kotlin metadata */
        private final Lazy dependenciesSources;

        public static /* synthetic */ void getLegacyDependencies$annotations() {
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public ScriptCompilationConfiguration getConfiguration() {
            return this.configuration;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FromCompilationConfiguration(SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration) {
            super(script);
            Intrinsics.checkNotNullParameter(script, "script");
            this.configuration = scriptCompilationConfiguration;
            this.dependenciesClassPath = LazyKt.lazy(new Function0<List<? extends File>>() { // from class: org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper$FromCompilationConfiguration$dependenciesClassPath$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends File> invoke() {
                    ScriptCompilationConfiguration configuration = this.this$0.getConfiguration();
                    return BridgeDependenciesResolverKt.toClassPathOrEmpty(configuration != null ? (List) configuration.get(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE)) : null);
                }
            });
            this.dependenciesSources = LazyKt.lazy(new Function0<List<? extends File>>() { // from class: org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper$FromCompilationConfiguration$dependenciesSources$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends File> invoke() {
                    ScriptCompilationConfiguration configuration = this.this$0.getConfiguration();
                    return BridgeDependenciesResolverKt.toClassPathOrEmpty(configuration != null ? (List) configuration.get(ScriptIdeConfigurationKt.getDependenciesSources(ScriptIdeConfigurationKt.getIde(ScriptCompilationConfiguration.INSTANCE))) : null);
                }
            });
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public List<File> getDependenciesClassPath() {
            return (List) this.dependenciesClassPath.getValue();
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public List<File> getDependenciesSources() {
            return (List) this.dependenciesSources.getValue();
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public File getJavaHome() {
            ScriptCompilationConfiguration configuration = getConfiguration();
            if (configuration != null) {
                return (File) configuration.get(JvmScriptCompilationKt.getJdkHome(JvmScriptCompilationKt.getJvm(ScriptCompilationConfiguration.INSTANCE)));
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public List<String> getDefaultImports() {
            ScriptCompilationConfiguration configuration = getConfiguration();
            List<String> list = configuration != null ? (List) configuration.get(ScriptCompilationKt.getDefaultImports(ScriptCompilationConfiguration.INSTANCE)) : null;
            return list == null ? CollectionsKt.emptyList() : list;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public List<SourceCode> getImportedScripts() {
            List<SourceCode> list;
            ScriptCompilationConfiguration configuration = getConfiguration();
            if (configuration == null || (list = (List) configuration.get(RefineCompilationConfigurationKt.getResolvedImportScripts(ScriptCompilationConfiguration.INSTANCE))) == null) {
                ScriptCompilationConfiguration configuration2 = getConfiguration();
                list = configuration2 != null ? (List) configuration2.get(ScriptCompilationKt.getImportScripts(ScriptCompilationConfiguration.INSTANCE)) : null;
            }
            return list == null ? CollectionsKt.emptyList() : list;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public ScriptDependencies getLegacyDependencies() {
            ScriptCompilationConfiguration configuration = getConfiguration();
            if (configuration != null) {
                return BridgeDependenciesResolverKt.toDependencies(configuration, getDependenciesClassPath());
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public boolean equals(Object other) {
            return super.equals(other) && (other instanceof FromCompilationConfiguration) && Intrinsics.areEqual(getConfiguration(), ((FromCompilationConfiguration) other).getConfiguration());
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public int hashCode() {
            int iHashCode = super.hashCode();
            ScriptCompilationConfiguration configuration = getConfiguration();
            return iHashCode + ((configuration != null ? configuration.hashCode() : 1) * 23);
        }

        public String toString() {
            return "FromCompilationConfiguration(" + getConfiguration() + ')';
        }
    }

    /* JADX INFO: compiled from: refineCompilationConfiguration.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0096\u0002J\b\u0010$\u001a\u00020%H\u0016J\b\u0010&\u001a\u00020\u000fH\u0016R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0011R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00150\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0011R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0011R\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationWrapper$FromLegacy;", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationWrapper;", "script", "Lkotlin/script/experimental/api/SourceCode;", "legacyDependencies", "Lkotlin/script/experimental/dependencies/ScriptDependencies;", "definition", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "(Lkotlin/script/experimental/api/SourceCode;Lkotlin/script/experimental/dependencies/ScriptDependencies;Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;)V", "configuration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "defaultImports", "", "", "getDefaultImports", "()Ljava/util/List;", "getDefinition", "()Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "dependenciesClassPath", "Ljava/io/File;", "getDependenciesClassPath", "dependenciesSources", "getDependenciesSources", "importedScripts", "getImportedScripts", "javaHome", "getJavaHome", "()Ljava/io/File;", "getLegacyDependencies", "()Lkotlin/script/experimental/dependencies/ScriptDependencies;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FromLegacy extends ScriptCompilationConfigurationWrapper {
        private final ScriptDefinition definition;
        private final ScriptDependencies legacyDependencies;

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public ScriptDependencies getLegacyDependencies() {
            return this.legacyDependencies;
        }

        public final ScriptDefinition getDefinition() {
            return this.definition;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FromLegacy(SourceCode script, ScriptDependencies scriptDependencies, ScriptDefinition scriptDefinition) {
            super(script);
            Intrinsics.checkNotNullParameter(script, "script");
            this.legacyDependencies = scriptDependencies;
            this.definition = scriptDefinition;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public List<File> getDependenciesClassPath() {
            ScriptDependencies legacyDependencies = getLegacyDependencies();
            List<File> classpath = legacyDependencies != null ? legacyDependencies.getClasspath() : null;
            return classpath == null ? CollectionsKt.emptyList() : classpath;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public List<File> getDependenciesSources() {
            ScriptDependencies legacyDependencies = getLegacyDependencies();
            List<File> sources = legacyDependencies != null ? legacyDependencies.getSources() : null;
            return sources == null ? CollectionsKt.emptyList() : sources;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public File getJavaHome() {
            ScriptDependencies legacyDependencies = getLegacyDependencies();
            if (legacyDependencies != null) {
                return legacyDependencies.getJavaHome();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public List<String> getDefaultImports() {
            ScriptDependencies legacyDependencies = getLegacyDependencies();
            List<String> imports = legacyDependencies != null ? legacyDependencies.getImports() : null;
            return imports == null ? CollectionsKt.emptyList() : imports;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public List<SourceCode> getImportedScripts() {
            List<File> scripts;
            ScriptDependencies legacyDependencies = getLegacyDependencies();
            ArrayList arrayList = null;
            if (legacyDependencies != null && (scripts = legacyDependencies.getScripts()) != null) {
                List<File> list = scripts;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList2.add(new FileScriptSource((File) it.next(), null, 2, null));
                }
                arrayList = arrayList2;
            }
            return arrayList == null ? CollectionsKt.emptyList() : arrayList;
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public ScriptCompilationConfiguration getConfiguration() {
            ScriptDefinition scriptDefinition;
            ScriptCompilationConfiguration compilationConfiguration;
            final ScriptDependencies legacyDependencies = getLegacyDependencies();
            if (legacyDependencies == null || (scriptDefinition = this.definition) == null || (compilationConfiguration = scriptDefinition.getCompilationConfiguration()) == null) {
                return null;
            }
            return new ScriptCompilationConfiguration(new ScriptCompilationConfiguration[]{compilationConfiguration}, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper$FromLegacy$configuration$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptCompilationConfiguration.Builder $receiver) {
                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                    JvmScriptCompilationKt.updateClasspath($receiver, legacyDependencies.getClasspath());
                    ScriptCompilationConfiguration.Builder builder = $receiver;
                    $receiver.appendToList(ScriptCompilationKt.getDefaultImports(builder), legacyDependencies.getImports());
                    PropertiesCollection.Key<List<SourceCode>> importScripts = ScriptCompilationKt.getImportScripts(builder);
                    List<File> scripts = legacyDependencies.getScripts();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(scripts, 10));
                    Iterator<T> it = scripts.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new FileScriptSource((File) it.next(), null, 2, null));
                    }
                    $receiver.appendToList(importScripts, arrayList);
                    JvmScriptCompilationConfigurationBuilder jvm = JvmScriptCompilationKt.getJvm(builder);
                    final ScriptDependencies scriptDependencies = legacyDependencies;
                    $receiver.invoke(jvm, new Function1<JvmScriptCompilationConfigurationBuilder, Unit>() { // from class: org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper$FromLegacy$configuration$1$1.2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(JvmScriptCompilationConfigurationBuilder jvmScriptCompilationConfigurationBuilder) {
                            invoke2(jvmScriptCompilationConfigurationBuilder);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(JvmScriptCompilationConfigurationBuilder invoke) {
                            Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                            invoke.putIfNotNull(JvmScriptCompilationKt.getJdkHome(invoke), scriptDependencies.getJavaHome());
                        }
                    });
                    if (legacyDependencies.getSources().isEmpty()) {
                        return;
                    }
                    IdeScriptCompilationConfigurationBuilder ide = ScriptIdeConfigurationKt.getIde(builder);
                    final ScriptDependencies scriptDependencies2 = legacyDependencies;
                    $receiver.invoke(ide, new Function1<IdeScriptCompilationConfigurationBuilder, Unit>() { // from class: org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper$FromLegacy$configuration$1$1.3
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
                            invoke.append(ScriptIdeConfigurationKt.getDependenciesSources(invoke), new JvmDependency(scriptDependencies2.getSources()));
                        }
                    });
                }
            });
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public boolean equals(Object other) {
            return super.equals(other) && (other instanceof FromLegacy) && Intrinsics.areEqual(getLegacyDependencies(), ((FromLegacy) other).getLegacyDependencies());
        }

        @Override // org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper
        public int hashCode() {
            int iHashCode = super.hashCode();
            ScriptDependencies legacyDependencies = getLegacyDependencies();
            return iHashCode + ((legacyDependencies != null ? legacyDependencies.hashCode() : 1) * 31);
        }

        public String toString() {
            return "FromLegacy(" + getLegacyDependencies() + ')';
        }
    }
}
