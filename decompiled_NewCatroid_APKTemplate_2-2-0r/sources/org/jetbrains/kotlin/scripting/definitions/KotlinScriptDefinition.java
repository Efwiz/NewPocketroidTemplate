package org.jetbrains.kotlin.scripting.definitions;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import kotlin.script.experimental.location.ScriptExpectedLocation;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.parsing.KotlinParserDefinition;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinScriptDefinition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u0000 :2\u00020\u0001:\u0001:B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0016J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\rH\u0016R\"\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u00030\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR$\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f8\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\nR\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\nR\u0014\u0010\"\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001aR\u0014\u0010$\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u001aR&\u0010&\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020 0'0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\nR \u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u0007X\u0096\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b+\u0010\u000f\u001a\u0004\b,\u0010\nR\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\nR\u001a\u0010/\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\nR\u001c\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u00102¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/UserDataHolderBase;", ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE, "Lkotlin/reflect/KClass;", "", "(Lkotlin/reflect/KClass;)V", "acceptedAnnotations", "", "", "getAcceptedAnnotations", "()Ljava/util/List;", "additionalCompilerArguments", "", "", "getAdditionalCompilerArguments$annotations", "()V", "getAdditionalCompilerArguments", "()Ljava/lang/Iterable;", "annotationsForSamWithReceivers", "getAnnotationsForSamWithReceivers", "dependencyResolver", "Lkotlin/script/experimental/dependencies/DependenciesResolver;", "getDependencyResolver", "()Lkotlin/script/experimental/dependencies/DependenciesResolver;", "fileExtension", "getFileExtension", "()Ljava/lang/String;", "fileType", "Lorg/jetbrains/kotlin/com/intellij/openapi/fileTypes/LanguageFileType;", "getFileType", "()Lcom/intellij/openapi/fileTypes/LanguageFileType;", "implicitReceivers", "Lkotlin/reflect/KType;", "getImplicitReceivers", "name", "getName", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "getPlatform", "providedProperties", "Lkotlin/Pair;", "getProvidedProperties", "scriptExpectedLocations", "Lkotlin/script/experimental/location/ScriptExpectedLocation;", "getScriptExpectedLocations$annotations", "getScriptExpectedLocations", "targetClassAnnotations", "getTargetClassAnnotations", "targetMethodAnnotations", "getTargetMethodAnnotations", "getTemplate", "()Lkotlin/reflect/KClass;", "getScriptName", "Lorg/jetbrains/kotlin/name/Name;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "isScript", "", PsiTreeChangeEvent.PROP_FILE_NAME, "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinScriptDefinition extends UserDataHolderBase {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Iterable<String> additionalCompilerArguments;
    private final LanguageFileType fileType;
    private final String name;
    private final List<ScriptExpectedLocation> scriptExpectedLocations;
    private final KClass<? extends Object> template;

    @Deprecated(message = "temporary workaround for missing functionality, will be replaced by the new API soon")
    public static /* synthetic */ void getAdditionalCompilerArguments$annotations() {
    }

    public static /* synthetic */ void getScriptExpectedLocations$annotations() {
    }

    public KotlinScriptDefinition(KClass<? extends Object> template) {
        Intrinsics.checkNotNullParameter(template, "template");
        this.template = template;
        this.name = INSTANCE.getKOTLIN_SCRIPT();
        KotlinFileType INSTANCE2 = KotlinFileType.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE2, "INSTANCE");
        this.fileType = INSTANCE2;
        this.scriptExpectedLocations = CollectionsKt.listOf((Object[]) new ScriptExpectedLocation[]{ScriptExpectedLocation.SourcesOnly, ScriptExpectedLocation.TestsOnly});
    }

    public KClass<? extends Object> getTemplate() {
        return this.template;
    }

    public String getName() {
        return this.name;
    }

    public LanguageFileType getFileType() {
        return this.fileType;
    }

    public List<String> getAnnotationsForSamWithReceivers() {
        return CollectionsKt.emptyList();
    }

    public boolean isScript(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return StringsKt.endsWith$default(fileName, KotlinParserDefinition.STD_SCRIPT_EXT, false, 2, (Object) null);
    }

    public Name getScriptName(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        String name2 = script.getContainingKtFile().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return NameUtils.getScriptNameForFile(name2);
    }

    public String getFileExtension() {
        return "kts";
    }

    public String getPlatform() {
        return "JVM";
    }

    public DependenciesResolver getDependencyResolver() {
        return DependenciesResolver.NoDependencies.INSTANCE;
    }

    public List<KClass<? extends Annotation>> getAcceptedAnnotations() {
        return CollectionsKt.emptyList();
    }

    public Iterable<String> getAdditionalCompilerArguments() {
        return this.additionalCompilerArguments;
    }

    public List<ScriptExpectedLocation> getScriptExpectedLocations() {
        return this.scriptExpectedLocations;
    }

    public List<KType> getImplicitReceivers() {
        return CollectionsKt.emptyList();
    }

    public List<Pair<String, KType>> getProvidedProperties() {
        return CollectionsKt.emptyList();
    }

    public List<Annotation> getTargetClassAnnotations() {
        return CollectionsKt.emptyList();
    }

    public List<Annotation> getTargetMethodAnnotations() {
        return CollectionsKt.emptyList();
    }

    /* JADX INFO: compiled from: KotlinScriptDefinition.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition$Companion;", "", "()V", "KOTLIN_SCRIPT", "", "getKOTLIN_SCRIPT", "()Ljava/lang/String;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getKOTLIN_SCRIPT() {
            return "Kotlin Script";
        }
    }
}
