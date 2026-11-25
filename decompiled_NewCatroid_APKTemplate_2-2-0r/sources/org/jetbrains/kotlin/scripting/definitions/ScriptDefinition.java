package org.jetbrains.kotlin.scripting.definitions;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.ConfigurationFromTemplateKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.scripting.resolve.KotlinScriptDefinitionFromAnnotatedTemplate;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptDefinition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u0000 E2\u00020\u0001:\u0007EFGHIJKB\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010?\u001a\u0004\u0018\u0001H@\"\n\b\u0000\u0010@\u0018\u0001*\u000201H\u0086\b¢\u0006\u0002\u00104J\u0010\u0010A\u001a\u00020\r2\u0006\u0010B\u001a\u00020CH&J\b\u0010D\u001a\u00020\u0005H\u0016R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000fR\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0012\u0010#\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010 R\u0014\u0010%\u001a\u0004\u0018\u00010&X¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0012\u0010)\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b*\u0010 R\u0012\u0010+\u001a\u00020,X¦\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0014\u0010/\u001a\u00020\rX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u000fR\u001a\u00100\u001a\u0002018&X§\u0004¢\u0006\f\u0012\u0004\b2\u0010\u0002\u001a\u0004\b3\u00104R\u0012\u00105\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b6\u0010 R\u001a\u00107\u001a\u000208X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u0014\u0010=\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b>\u0010 ¨\u0006L"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/UserDataHolderBase;", "()V", "annotationsForSamWithReceivers", "", "", "getAnnotationsForSamWithReceivers", "()Ljava/util/List;", "baseClassType", "Lkotlin/script/experimental/api/KotlinType;", "getBaseClassType", "()Lkotlin/script/experimental/api/KotlinType;", "canAutoReloadScriptConfigurationsBeSwitchedOff", "", "getCanAutoReloadScriptConfigurationsBeSwitchedOff", "()Z", "canDefinitionBeSwitchedOff", "getCanDefinitionBeSwitchedOff", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "compilerOptions", "", "getCompilerOptions", "()Ljava/lang/Iterable;", "contextClassLoader", "Ljava/lang/ClassLoader;", "getContextClassLoader", "()Ljava/lang/ClassLoader;", "defaultClassName", "getDefaultClassName", "()Ljava/lang/String;", "defaultCompilerOptions", "getDefaultCompilerOptions", "definitionId", "getDefinitionId", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "getEvaluationConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "fileExtension", "getFileExtension", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "isDefault", "legacyDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "getLegacyDefinition$annotations", "getLegacyDefinition", "()Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "name", "getName", "order", "", "getOrder", "()I", "setOrder", "(I)V", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "getPlatform", "asLegacyOrNull", ExifInterface.GPS_DIRECTION_TRUE, "isScript", "script", "Lkotlin/script/experimental/api/SourceCode;", "toString", "Companion", "FromConfigurations", "FromConfigurationsBase", "FromLegacy", "FromLegacyTemplate", "FromNewDefinition", "FromTemplate", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ScriptDefinition extends UserDataHolderBase {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean isDefault;
    private final String defaultClassName = "Script";
    private int order = Integer.MAX_VALUE;
    private final Iterable<String> defaultCompilerOptions = CollectionsKt.emptyList();

    @Deprecated(message = "Use configurations instead")
    public static /* synthetic */ void getLegacyDefinition$annotations() {
    }

    public abstract List<String> getAnnotationsForSamWithReceivers();

    public abstract KotlinType getBaseClassType();

    public boolean getCanAutoReloadScriptConfigurationsBeSwitchedOff() {
        return true;
    }

    public boolean getCanDefinitionBeSwitchedOff() {
        return true;
    }

    public abstract ScriptCompilationConfiguration getCompilationConfiguration();

    public abstract Iterable<String> getCompilerOptions();

    public abstract ClassLoader getContextClassLoader();

    public abstract String getDefinitionId();

    public abstract ScriptEvaluationConfiguration getEvaluationConfiguration();

    public abstract String getFileExtension();

    public abstract ScriptingHostConfiguration getHostConfiguration();

    public abstract KotlinScriptDefinition getLegacyDefinition();

    public abstract String getName();

    public abstract boolean isScript(SourceCode script);

    public String getDefaultClassName() {
        return this.defaultClassName;
    }

    public String getPlatform() {
        return "JVM";
    }

    /* JADX INFO: renamed from: isDefault, reason: from getter */
    public boolean getIsDefault() {
        return this.isDefault;
    }

    public final int getOrder() {
        return this.order;
    }

    public final void setOrder(int i) {
        this.order = i;
    }

    public Iterable<String> getDefaultCompilerOptions() {
        return this.defaultCompilerOptions;
    }

    public final /* synthetic */ <T extends KotlinScriptDefinition> T asLegacyOrNull() {
        if (!(this instanceof FromLegacy)) {
            return null;
        }
        T t = (T) getLegacyDefinition();
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return t;
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "ScriptDefinition(" + getName() + ')';
    }

    /* JADX INFO: compiled from: ScriptDefinition.kt */
    @Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0013\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u000105H\u0096\u0002J\b\u00106\u001a\u000207H\u0016J\u0010\u00108\u001a\u0002032\u0006\u00109\u001a\u00020:H\u0016R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0012\u001a\u00020\u00138VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001aR\u0014\u0010 \u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u001b\u0010#\u001a\u00020$8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u0017\u001a\u0004\b%\u0010&R\u0014\u0010(\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\"R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u0010\"R\u0014\u00100\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010\"¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromLegacy;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "legacyDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "defaultCompilerOptions", "", "", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;Ljava/lang/Iterable;)V", "annotationsForSamWithReceivers", "", "getAnnotationsForSamWithReceivers", "()Ljava/util/List;", "baseClassType", "Lkotlin/script/experimental/api/KotlinType;", "getBaseClassType", "()Lkotlin/script/experimental/api/KotlinType;", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "compilationConfiguration$delegate", "Lkotlin/Lazy;", "compilerOptions", "getCompilerOptions", "()Ljava/lang/Iterable;", "contextClassLoader", "Ljava/lang/ClassLoader;", "getContextClassLoader", "()Ljava/lang/ClassLoader;", "getDefaultCompilerOptions", "definitionId", "getDefinitionId", "()Ljava/lang/String;", "evaluationConfiguration", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptEvaluationConfigurationFromDefinition;", "getEvaluationConfiguration", "()Lorg/jetbrains/kotlin/scripting/definitions/ScriptEvaluationConfigurationFromDefinition;", "evaluationConfiguration$delegate", "fileExtension", "getFileExtension", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getLegacyDefinition", "()Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "name", "getName", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "getPlatform", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "isScript", "script", "Lkotlin/script/experimental/api/SourceCode;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class FromLegacy extends ScriptDefinition {

        /* JADX INFO: renamed from: compilationConfiguration$delegate, reason: from kotlin metadata */
        private final Lazy compilationConfiguration;
        private final Iterable<String> defaultCompilerOptions;

        /* JADX INFO: renamed from: evaluationConfiguration$delegate, reason: from kotlin metadata */
        private final Lazy evaluationConfiguration;
        private final ScriptingHostConfiguration hostConfiguration;
        private final KotlinScriptDefinition legacyDefinition;

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ScriptingHostConfiguration getHostConfiguration() {
            return this.hostConfiguration;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public KotlinScriptDefinition getLegacyDefinition() {
            return this.legacyDefinition;
        }

        public /* synthetic */ FromLegacy(ScriptingHostConfiguration scriptingHostConfiguration, KotlinScriptDefinition kotlinScriptDefinition, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(scriptingHostConfiguration, kotlinScriptDefinition, (i & 4) != 0 ? CollectionsKt.emptyList() : list);
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public Iterable<String> getDefaultCompilerOptions() {
            return this.defaultCompilerOptions;
        }

        public FromLegacy(ScriptingHostConfiguration hostConfiguration, KotlinScriptDefinition legacyDefinition, Iterable<String> defaultCompilerOptions) {
            Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
            Intrinsics.checkNotNullParameter(legacyDefinition, "legacyDefinition");
            Intrinsics.checkNotNullParameter(defaultCompilerOptions, "defaultCompilerOptions");
            this.hostConfiguration = hostConfiguration;
            this.legacyDefinition = legacyDefinition;
            this.defaultCompilerOptions = defaultCompilerOptions;
            this.compilationConfiguration = LazyKt.lazy(new Function0<ScriptCompilationConfigurationFromDefinition>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptDefinition$FromLegacy$compilationConfiguration$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ScriptCompilationConfigurationFromDefinition invoke() {
                    return new ScriptCompilationConfigurationFromDefinition(this.this$0.getHostConfiguration(), this.this$0.getLegacyDefinition());
                }
            });
            this.evaluationConfiguration = LazyKt.lazy(new Function0<ScriptEvaluationConfigurationFromDefinition>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptDefinition$FromLegacy$evaluationConfiguration$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ScriptEvaluationConfigurationFromDefinition invoke() {
                    return new ScriptEvaluationConfigurationFromDefinition(this.this$0.getHostConfiguration(), this.this$0.getLegacyDefinition());
                }
            });
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ScriptCompilationConfiguration getCompilationConfiguration() {
            return (ScriptCompilationConfiguration) this.compilationConfiguration.getValue();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ScriptEvaluationConfigurationFromDefinition getEvaluationConfiguration() {
            return (ScriptEvaluationConfigurationFromDefinition) this.evaluationConfiguration.getValue();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public boolean isScript(SourceCode script) {
            Intrinsics.checkNotNullParameter(script, "script");
            String name2 = script.getName();
            return name2 != null ? getLegacyDefinition().isScript(name2) : getIsDefault();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public String getFileExtension() {
            return getLegacyDefinition().getFileExtension();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public String getName() {
            return getLegacyDefinition().getName();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public String getDefinitionId() {
            String qualifiedName = Reflection.getOrCreateKotlinClass(getLegacyDefinition().getClass()).getQualifiedName();
            return qualifiedName == null ? "unknown" : qualifiedName;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public String getPlatform() {
            return getLegacyDefinition().getPlatform();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ClassLoader getContextClassLoader() {
            return JvmClassMappingKt.getJavaClass((KClass) getLegacyDefinition().getTemplate()).getClassLoader();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public KotlinType getBaseClassType() {
            return new KotlinType((KClass) getLegacyDefinition().getTemplate(), false, 2, (DefaultConstructorMarker) null);
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public Iterable<String> getCompilerOptions() {
            Iterable<String> additionalCompilerArguments = getLegacyDefinition().getAdditionalCompilerArguments();
            return additionalCompilerArguments == null ? CollectionsKt.emptyList() : additionalCompilerArguments;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public List<String> getAnnotationsForSamWithReceivers() {
            return getLegacyDefinition().getAnnotationsForSamWithReceivers();
        }

        public boolean equals(Object other) {
            if (this != other) {
                KotlinScriptDefinition legacyDefinition = getLegacyDefinition();
                FromLegacy fromLegacy = other instanceof FromLegacy ? (FromLegacy) other : null;
                if (!Intrinsics.areEqual(legacyDefinition, fromLegacy != null ? fromLegacy.getLegacyDefinition() : null)) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            return getLegacyDefinition().hashCode();
        }
    }

    /* JADX INFO: compiled from: ScriptDefinition.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromLegacyTemplate;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromLegacy;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE, "Lkotlin/reflect/KClass;", "templateClasspath", "", "Ljava/io/File;", "defaultCompilerOptions", "", "", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lkotlin/reflect/KClass;Ljava/util/List;Ljava/lang/Iterable;)V", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class FromLegacyTemplate extends FromLegacy {
        public /* synthetic */ FromLegacyTemplate(ScriptingHostConfiguration scriptingHostConfiguration, KClass kClass, List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(scriptingHostConfiguration, kClass, (i & 4) != 0 ? CollectionsKt.emptyList() : list, (i & 8) != 0 ? CollectionsKt.emptyList() : list2);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public FromLegacyTemplate(ScriptingHostConfiguration hostConfiguration, KClass<?> template, List<? extends File> templateClasspath, Iterable<String> defaultCompilerOptions) {
            Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
            Intrinsics.checkNotNullParameter(template, "template");
            Intrinsics.checkNotNullParameter(templateClasspath, "templateClasspath");
            Intrinsics.checkNotNullParameter(defaultCompilerOptions, "defaultCompilerOptions");
            Function0 function0 = (Function0) hostConfiguration.get(ScriptCompilationConfigurationFromDefinitionKt.getGetEnvironment(ScriptingHostConfiguration.INSTANCE));
            super(hostConfiguration, new KotlinScriptDefinitionFromAnnotatedTemplate(template, function0 != null ? (Map) function0.invoke() : null, templateClasspath), defaultCompilerOptions);
        }
    }

    /* JADX INFO: compiled from: ScriptDefinition.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0096\u0002J\b\u0010.\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020+2\u0006\u00101\u001a\u000202H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u0004\u0018\u00010\u00118VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0016\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0018R\u0014\u0010\u001b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0018R\u001d\u0010\u001d\u001a\u0004\u0018\u00010\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0015\u001a\u0004\b\u001e\u0010\u0018R!\u0010 \u001a\u00020!8VX\u0096\u0084\u0002¢\u0006\u0012\n\u0004\b%\u0010\u0015\u0012\u0004\b\"\u0010\u0002\u001a\u0004\b#\u0010$R\u0014\u0010&\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\u0018R\u0014\u0010(\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u0018¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromConfigurationsBase;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "()V", "annotationsForSamWithReceivers", "", "", "getAnnotationsForSamWithReceivers", "()Ljava/util/List;", "baseClassType", "Lkotlin/script/experimental/api/KotlinType;", "getBaseClassType", "()Lkotlin/script/experimental/api/KotlinType;", "compilerOptions", "", "getCompilerOptions", "()Ljava/lang/Iterable;", "contextClassLoader", "Ljava/lang/ClassLoader;", "getContextClassLoader", "()Ljava/lang/ClassLoader;", "contextClassLoader$delegate", "Lkotlin/Lazy;", "defaultClassName", "getDefaultClassName", "()Ljava/lang/String;", "definitionId", "getDefinitionId", "fileExtension", "getFileExtension", "filePathPattern", "getFilePathPattern", "filePathPattern$delegate", "legacyDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinitionAdapterFromNewAPI;", "getLegacyDefinition$annotations", "getLegacyDefinition", "()Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinitionAdapterFromNewAPI;", "legacyDefinition$delegate", "name", "getName", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "getPlatform", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "isScript", "script", "Lkotlin/script/experimental/api/SourceCode;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class FromConfigurationsBase extends ScriptDefinition {

        /* JADX INFO: renamed from: legacyDefinition$delegate, reason: from kotlin metadata */
        private final Lazy legacyDefinition = LazyKt.lazy(new Function0<KotlinScriptDefinitionAdapterFromNewAPI>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptDefinition$FromConfigurationsBase$legacyDefinition$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinScriptDefinitionAdapterFromNewAPI invoke() {
                return new KotlinScriptDefinitionAdapterFromNewAPI(this.this$0.getCompilationConfiguration(), this.this$0.getHostConfiguration());
            }
        });

        /* JADX INFO: renamed from: filePathPattern$delegate, reason: from kotlin metadata */
        private final Lazy filePathPattern = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptDefinition$FromConfigurationsBase$filePathPattern$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                String str = (String) this.this$0.getCompilationConfiguration().get(ScriptCompilationKt.getFilePathPattern(ScriptCompilationConfiguration.INSTANCE));
                if (str == null || StringsKt.isBlank(str)) {
                    return null;
                }
                return str;
            }
        });

        /* JADX INFO: renamed from: contextClassLoader$delegate, reason: from kotlin metadata */
        private final Lazy contextClassLoader = LazyKt.lazy(new Function0<ClassLoader>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptDefinition$FromConfigurationsBase$contextClassLoader$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ClassLoader invoke() {
                KClass<?> fromClass;
                Class javaClass;
                ClassLoader classLoader;
                KotlinType kotlinType = (KotlinType) this.this$0.getCompilationConfiguration().get(ScriptCompilationKt.getBaseClass(ScriptCompilationConfiguration.INSTANCE));
                return (kotlinType == null || (fromClass = kotlinType.getFromClass()) == null || (javaClass = JvmClassMappingKt.getJavaClass((KClass) fromClass)) == null || (classLoader = javaClass.getClassLoader()) == null) ? (ClassLoader) this.this$0.getHostConfiguration().get(JvmScriptingHostConfigurationKt.getBaseClassLoader(JvmScriptingHostConfigurationKt.getJvm(ScriptingHostConfiguration.INSTANCE))) : classLoader;
            }
        });

        public static /* synthetic */ void getLegacyDefinition$annotations() {
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public KotlinScriptDefinitionAdapterFromNewAPI getLegacyDefinition() {
            return (KotlinScriptDefinitionAdapterFromNewAPI) this.legacyDefinition.getValue();
        }

        public final String getFilePathPattern() {
            return (String) this.filePathPattern.getValue();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public boolean isScript(SourceCode script) {
            Intrinsics.checkNotNullParameter(script, "script");
            String str = "." + getFileExtension();
            String locationId = script.getLocationId();
            if (locationId == null) {
                return false;
            }
            String name2 = script.getName();
            if ((name2 == null || !StringsKt.endsWith$default(name2, str, false, 2, (Object) null)) && !StringsKt.endsWith$default(locationId, str, false, 2, (Object) null)) {
                return false;
            }
            String filePathPattern = getFilePathPattern();
            if (filePathPattern != null) {
                Regex regex = new Regex(filePathPattern);
                String systemIndependentName = FileUtilRt.toSystemIndependentName(locationId);
                Intrinsics.checkNotNullExpressionValue(systemIndependentName, "toSystemIndependentName(...)");
                if (!regex.matches(systemIndependentName)) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public String getFileExtension() {
            Object obj = getCompilationConfiguration().get(ScriptCompilationKt.getFileExtension(ScriptCompilationConfiguration.INSTANCE));
            Intrinsics.checkNotNull(obj);
            return (String) obj;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public String getName() {
            String str = (String) getCompilationConfiguration().get(ScriptCompilationKt.getDisplayName(ScriptCompilationConfiguration.INSTANCE));
            if (str != null) {
                if (StringsKt.isBlank(str)) {
                    str = null;
                }
                if (str != null) {
                    return str;
                }
            }
            Object obj = getCompilationConfiguration().get(ScriptCompilationKt.getBaseClass(ScriptCompilationConfiguration.INSTANCE));
            Intrinsics.checkNotNull(obj);
            return StringsKt.substringAfterLast$default(((KotlinType) obj).getTypeName(), '.', (String) null, 2, (Object) null);
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public String getDefaultClassName() {
            String str = (String) getCompilationConfiguration().get(ScriptCompilationKt.getDefaultIdentifier(ScriptCompilationConfiguration.INSTANCE));
            return str == null ? super.getDefaultClassName() : str;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public String getDefinitionId() {
            Object obj = getCompilationConfiguration().get(ScriptCompilationKt.getBaseClass(ScriptCompilationConfiguration.INSTANCE));
            Intrinsics.checkNotNull(obj);
            return ((KotlinType) obj).getTypeName();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ClassLoader getContextClassLoader() {
            return (ClassLoader) this.contextClassLoader.getValue();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public String getPlatform() {
            String str = (String) getCompilationConfiguration().get(ScriptCompilationConfigurationFromDefinitionKt.getPlatform(ScriptCompilationConfiguration.INSTANCE));
            return str == null ? super.getPlatform() : str;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public KotlinType getBaseClassType() {
            Object obj = getCompilationConfiguration().get(ScriptCompilationKt.getBaseClass(ScriptCompilationConfiguration.INSTANCE));
            Intrinsics.checkNotNull(obj);
            return (KotlinType) obj;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public Iterable<String> getCompilerOptions() {
            List listEmptyList = (List) getCompilationConfiguration().get(ScriptCompilationKt.getCompilerOptions(ScriptCompilationConfiguration.INSTANCE));
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            return listEmptyList;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public List<String> getAnnotationsForSamWithReceivers() {
            List listEmptyList = (List) getCompilationConfiguration().get(ScriptCompilationConfigurationFromDefinitionKt.getAnnotationsForSamWithReceivers(ScriptCompilationConfiguration.INSTANCE));
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            List list = listEmptyList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((KotlinType) it.next()).getTypeName());
            }
            return arrayList;
        }

        public boolean equals(Object other) {
            if (this != other) {
                FromConfigurationsBase fromConfigurationsBase = other instanceof FromConfigurationsBase ? (FromConfigurationsBase) other : null;
                if (fromConfigurationsBase == null || !Intrinsics.areEqual(getCompilationConfiguration(), fromConfigurationsBase.getCompilationConfiguration()) || !Intrinsics.areEqual(getEvaluationConfiguration(), fromConfigurationsBase.getEvaluationConfiguration())) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int iHashCode = getCompilationConfiguration().hashCode();
            ScriptEvaluationConfiguration evaluationConfiguration = getEvaluationConfiguration();
            return iHashCode + ((evaluationConfiguration != null ? evaluationConfiguration.hashCode() : 0) * 37);
        }
    }

    /* JADX INFO: compiled from: ScriptDefinition.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromConfigurations;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromConfigurationsBase;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "defaultCompilerOptions", "", "", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Ljava/lang/Iterable;)V", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getDefaultCompilerOptions", "()Ljava/lang/Iterable;", "getEvaluationConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class FromConfigurations extends FromConfigurationsBase {
        private final ScriptCompilationConfiguration compilationConfiguration;
        private final Iterable<String> defaultCompilerOptions;
        private final ScriptEvaluationConfiguration evaluationConfiguration;
        private final ScriptingHostConfiguration hostConfiguration;

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ScriptingHostConfiguration getHostConfiguration() {
            return this.hostConfiguration;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ScriptCompilationConfiguration getCompilationConfiguration() {
            return this.compilationConfiguration;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ScriptEvaluationConfiguration getEvaluationConfiguration() {
            return this.evaluationConfiguration;
        }

        public /* synthetic */ FromConfigurations(ScriptingHostConfiguration scriptingHostConfiguration, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptEvaluationConfiguration scriptEvaluationConfiguration, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(scriptingHostConfiguration, scriptCompilationConfiguration, scriptEvaluationConfiguration, (i & 8) != 0 ? CollectionsKt.emptyList() : list);
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public Iterable<String> getDefaultCompilerOptions() {
            return this.defaultCompilerOptions;
        }

        public FromConfigurations(ScriptingHostConfiguration hostConfiguration, ScriptCompilationConfiguration compilationConfiguration, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Iterable<String> defaultCompilerOptions) {
            Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
            Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
            Intrinsics.checkNotNullParameter(defaultCompilerOptions, "defaultCompilerOptions");
            this.hostConfiguration = hostConfiguration;
            this.compilationConfiguration = compilationConfiguration;
            this.evaluationConfiguration = scriptEvaluationConfiguration;
            this.defaultCompilerOptions = defaultCompilerOptions;
        }
    }

    /* JADX INFO: compiled from: ScriptDefinition.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromNewDefinition;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromConfigurationsBase;", "baseHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "definition", "Lkotlin/script/experimental/host/ScriptDefinition;", "defaultCompilerOptions", "", "", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lkotlin/script/experimental/host/ScriptDefinition;Ljava/lang/Iterable;)V", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getDefaultCompilerOptions", "()Ljava/lang/Iterable;", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "getEvaluationConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "hostConfiguration", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class FromNewDefinition extends FromConfigurationsBase {
        private final ScriptingHostConfiguration baseHostConfiguration;
        private final Iterable<String> defaultCompilerOptions;
        private final kotlin.script.experimental.host.ScriptDefinition definition;

        public /* synthetic */ FromNewDefinition(ScriptingHostConfiguration scriptingHostConfiguration, kotlin.script.experimental.host.ScriptDefinition scriptDefinition, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(scriptingHostConfiguration, scriptDefinition, (i & 4) != 0 ? CollectionsKt.emptyList() : list);
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public Iterable<String> getDefaultCompilerOptions() {
            return this.defaultCompilerOptions;
        }

        public FromNewDefinition(ScriptingHostConfiguration baseHostConfiguration, kotlin.script.experimental.host.ScriptDefinition definition, Iterable<String> defaultCompilerOptions) {
            Intrinsics.checkNotNullParameter(baseHostConfiguration, "baseHostConfiguration");
            Intrinsics.checkNotNullParameter(definition, "definition");
            Intrinsics.checkNotNullParameter(defaultCompilerOptions, "defaultCompilerOptions");
            this.baseHostConfiguration = baseHostConfiguration;
            this.definition = definition;
            this.defaultCompilerOptions = defaultCompilerOptions;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ScriptingHostConfiguration getHostConfiguration() {
            ScriptingHostConfiguration scriptingHostConfiguration = (ScriptingHostConfiguration) this.definition.getCompilationConfiguration().get(ScriptCompilationKt.getHostConfiguration(ScriptCompilationConfiguration.INSTANCE));
            return scriptingHostConfiguration == null ? this.baseHostConfiguration : scriptingHostConfiguration;
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ScriptCompilationConfiguration getCompilationConfiguration() {
            return this.definition.getCompilationConfiguration();
        }

        @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
        public ScriptEvaluationConfiguration getEvaluationConfiguration() {
            return this.definition.getEvaluationConfiguration();
        }
    }

    /* JADX INFO: compiled from: ScriptDefinition.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\f\b\u0002\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromTemplate;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromNewDefinition;", "baseHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE, "Lkotlin/reflect/KClass;", "contextClass", "defaultCompilerOptions", "", "", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lkotlin/reflect/KClass;Lkotlin/reflect/KClass;Ljava/lang/Iterable;)V", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class FromTemplate extends FromNewDefinition {
        public /* synthetic */ FromTemplate(ScriptingHostConfiguration scriptingHostConfiguration, KClass kClass, KClass kClass2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(scriptingHostConfiguration, kClass, (i & 4) != 0 ? Reflection.getOrCreateKotlinClass(ScriptCompilationConfiguration.class) : kClass2, (i & 8) != 0 ? CollectionsKt.emptyList() : list);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FromTemplate(ScriptingHostConfiguration baseHostConfiguration, KClass<?> template, KClass<?> contextClass, Iterable<String> defaultCompilerOptions) {
            super(baseHostConfiguration, ConfigurationFromTemplateKt.createScriptDefinitionFromTemplate$default(new KotlinType((KClass) template, false, 2, (DefaultConstructorMarker) null), baseHostConfiguration, contextClass, null, null, 24, null), defaultCompilerOptions);
            Intrinsics.checkNotNullParameter(baseHostConfiguration, "baseHostConfiguration");
            Intrinsics.checkNotNullParameter(template, "template");
            Intrinsics.checkNotNullParameter(contextClass, "contextClass");
            Intrinsics.checkNotNullParameter(defaultCompilerOptions, "defaultCompilerOptions");
        }
    }

    /* JADX INFO: compiled from: ScriptDefinition.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$Companion;", "", "()V", "getDefault", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromConfigurations;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FromConfigurations getDefault(final ScriptingHostConfiguration hostConfiguration) {
            Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
            final ScriptCompilationConfigurationFromDefinition scriptCompilationConfigurationFromDefinition = new ScriptCompilationConfigurationFromDefinition(hostConfiguration, StandardScriptDefinition.INSTANCE);
            final ScriptEvaluationConfigurationFromDefinition scriptEvaluationConfigurationFromDefinition = new ScriptEvaluationConfigurationFromDefinition(hostConfiguration, StandardScriptDefinition.INSTANCE);
            return new FromConfigurations(hostConfiguration, scriptCompilationConfigurationFromDefinition, scriptEvaluationConfigurationFromDefinition) { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptDefinition$Companion$getDefault$1
                private final boolean isDefault = true;

                {
                    ScriptCompilationConfigurationFromDefinition scriptCompilationConfigurationFromDefinition2 = scriptCompilationConfigurationFromDefinition;
                    ScriptEvaluationConfigurationFromDefinition scriptEvaluationConfigurationFromDefinition2 = scriptEvaluationConfigurationFromDefinition;
                    int i = 8;
                    DefaultConstructorMarker defaultConstructorMarker = null;
                    Iterable iterable = null;
                }

                @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
                /* JADX INFO: renamed from: isDefault, reason: from getter */
                public boolean getIsDefault() {
                    return this.isDefault;
                }
            };
        }
    }
}
