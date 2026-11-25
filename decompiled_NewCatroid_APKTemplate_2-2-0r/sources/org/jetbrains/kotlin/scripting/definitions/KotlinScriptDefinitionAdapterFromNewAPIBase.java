package org.jetbrains.kotlin.scripting.definitions;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlin.reflect.full.KClassifiers;
import kotlin.reflect.full.KTypes;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.RefineConfigurationOnAnnotationsData;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptIdeConfigurationKt;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import kotlin.script.experimental.host.GetScriptingClass;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.compat.ExpectedLocationUtilKt;
import kotlin.script.experimental.jvm.impl.BridgeDependenciesResolver;
import kotlin.script.experimental.location.ScriptExpectedLocation;
import kotlin.script.experimental.util.PropertiesCollectionKt;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinScriptDefinitionAdapterFromNewAPI.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J\u0014\u0010@\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010A\u001a\u00020BH\u0002J\u0010\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\fH\u0016R)\u0010\u0003\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00048VX\u0097\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u0002\u001a\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\bR\u001f\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u00058VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\n\u001a\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0015\u001a\u00020\u00168VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\n\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0012\u0010\u001e\u001a\u00020\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R!\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\n\u001a\u0004\b$\u0010\bR\u0014\u0010&\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R-\u0010)\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020#0*0\u00048VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\n\u001a\u0004\b+\u0010\bR\u0012\u0010-\u001a\u00020.X¦\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R \u00101\u001a\b\u0012\u0004\u0012\u0002020\u00048VX\u0096\u0004¢\u0006\f\u0012\u0004\b3\u0010\u0002\u001a\u0004\b4\u0010\bR\u001b\u00105\u001a\u0002068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b9\u0010\n\u001a\u0004\b7\u00108R\u0018\u0010:\u001a\u0006\u0012\u0002\b\u00030\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b;\u0010\u0013¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinitionAdapterFromNewAPIBase;", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "()V", "acceptedAnnotations", "", "Lkotlin/reflect/KClass;", "", "getAcceptedAnnotations", "()Ljava/util/List;", "acceptedAnnotations$delegate", "Lkotlin/Lazy;", "additionalCompilerArguments", "", "getAdditionalCompilerArguments$annotations", "getAdditionalCompilerArguments", "annotationsForSamWithReceivers", "getAnnotationsForSamWithReceivers", "baseClass", "getBaseClass", "()Lkotlin/reflect/KClass;", "baseClass$delegate", "dependencyResolver", "Lkotlin/script/experimental/dependencies/DependenciesResolver;", "getDependencyResolver", "()Lkotlin/script/experimental/dependencies/DependenciesResolver;", "dependencyResolver$delegate", "fileType", "Lorg/jetbrains/kotlin/com/intellij/openapi/fileTypes/LanguageFileType;", "getFileType", "()Lcom/intellij/openapi/fileTypes/LanguageFileType;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "implicitReceivers", "Lkotlin/reflect/KType;", "getImplicitReceivers", "implicitReceivers$delegate", "name", "getName", "()Ljava/lang/String;", "providedProperties", "Lkotlin/Pair;", "getProvidedProperties", "providedProperties$delegate", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getScriptCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "scriptExpectedLocations", "Lkotlin/script/experimental/location/ScriptExpectedLocation;", "getScriptExpectedLocations$annotations", "getScriptExpectedLocations", "scriptingClassGetter", "Lkotlin/script/experimental/host/GetScriptingClass;", "getScriptingClassGetter", "()Lkotlin/script/experimental/host/GetScriptingClass;", "scriptingClassGetter$delegate", ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE, "getTemplate", "getScriptName", "Lorg/jetbrains/kotlin/name/Name;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "getScriptingClass", ModuleXmlParser.TYPE, "Lkotlin/script/experimental/api/KotlinType;", "isScript", "", PsiTreeChangeEvent.PROP_FILE_NAME, PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinScriptDefinitionAdapterFromNewAPIBase extends KotlinScriptDefinition {

    /* JADX INFO: renamed from: acceptedAnnotations$delegate, reason: from kotlin metadata */
    private final Lazy acceptedAnnotations;

    /* JADX INFO: renamed from: baseClass$delegate, reason: from kotlin metadata */
    private final Lazy baseClass;

    /* JADX INFO: renamed from: dependencyResolver$delegate, reason: from kotlin metadata */
    private final Lazy dependencyResolver;
    private final LanguageFileType fileType;

    /* JADX INFO: renamed from: implicitReceivers$delegate, reason: from kotlin metadata */
    private final Lazy implicitReceivers;

    /* JADX INFO: renamed from: providedProperties$delegate, reason: from kotlin metadata */
    private final Lazy providedProperties;

    /* JADX INFO: renamed from: scriptingClassGetter$delegate, reason: from kotlin metadata */
    private final Lazy scriptingClassGetter;

    @Deprecated(message = "temporary workaround for missing functionality, will be replaced by the new API soon")
    public static /* synthetic */ void getAdditionalCompilerArguments$annotations() {
    }

    public static /* synthetic */ void getScriptExpectedLocations$annotations() {
    }

    public abstract ScriptingHostConfiguration getHostConfiguration();

    public abstract ScriptCompilationConfiguration getScriptCompilationConfiguration();

    public KotlinScriptDefinitionAdapterFromNewAPIBase() {
        super(Reflection.getOrCreateKotlinClass(Object.class));
        this.baseClass = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<KClass<?>>() { // from class: org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinitionAdapterFromNewAPIBase$baseClass$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KClass<?> invoke() {
                KotlinScriptDefinitionAdapterFromNewAPIBase kotlinScriptDefinitionAdapterFromNewAPIBase = this.this$0;
                return kotlinScriptDefinitionAdapterFromNewAPIBase.getScriptingClass((KotlinType) PropertiesCollectionKt.getOrError(kotlinScriptDefinitionAdapterFromNewAPIBase.getScriptCompilationConfiguration(), ScriptCompilationKt.getBaseClass(ScriptCompilationConfiguration.INSTANCE)));
            }
        });
        KotlinFileType INSTANCE = KotlinFileType.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
        this.fileType = INSTANCE;
        this.dependencyResolver = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<BridgeDependenciesResolver>() { // from class: org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinitionAdapterFromNewAPIBase$dependencyResolver$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final BridgeDependenciesResolver invoke() {
                return new BridgeDependenciesResolver(this.this$0.getScriptCompilationConfiguration(), null, null, 6, null);
            }
        });
        this.acceptedAnnotations = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends KClass<? extends Annotation>>>() { // from class: org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinitionAdapterFromNewAPIBase$acceptedAnnotations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KClass<? extends Annotation>> invoke() {
                ArrayList arrayList;
                List list = (List) this.this$0.getScriptCompilationConfiguration().get(ScriptCompilationKt.getRefineConfigurationOnAnnotations(ScriptCompilationConfiguration.INSTANCE));
                if (list != null) {
                    KotlinScriptDefinitionAdapterFromNewAPIBase kotlinScriptDefinitionAdapterFromNewAPIBase = this.this$0;
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        List<KotlinType> annotations = ((RefineConfigurationOnAnnotationsData) it.next()).getAnnotations();
                        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
                        Iterator<T> it2 = annotations.iterator();
                        while (it2.hasNext()) {
                            KClass scriptingClass = kotlinScriptDefinitionAdapterFromNewAPIBase.getScriptingClass((KotlinType) it2.next());
                            Intrinsics.checkNotNull(scriptingClass, "null cannot be cast to non-null type kotlin.reflect.KClass<out kotlin.Annotation>");
                            arrayList3.add(scriptingClass);
                        }
                        CollectionsKt.addAll(arrayList2, arrayList3);
                    }
                    arrayList = arrayList2;
                } else {
                    arrayList = null;
                }
                return arrayList == null ? CollectionsKt.emptyList() : arrayList;
            }
        });
        this.implicitReceivers = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends KType>>() { // from class: org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinitionAdapterFromNewAPIBase$implicitReceivers$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KType> invoke() {
                List listEmptyList = (List) this.this$0.getScriptCompilationConfiguration().get(ScriptCompilationKt.getImplicitReceivers(ScriptCompilationConfiguration.INSTANCE));
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                List<KotlinType> list = listEmptyList;
                KotlinScriptDefinitionAdapterFromNewAPIBase kotlinScriptDefinitionAdapterFromNewAPIBase = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (KotlinType kotlinType : list) {
                    arrayList.add(KTypes.withNullability(KClassifiers.getStarProjectedType(kotlinScriptDefinitionAdapterFromNewAPIBase.getScriptingClass(kotlinType)), kotlinType.getIsNullable()));
                }
                return arrayList;
            }
        });
        this.providedProperties = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends Pair<? extends String, ? extends KType>>>() { // from class: org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinitionAdapterFromNewAPIBase$providedProperties$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends Pair<? extends String, ? extends KType>> invoke() {
                ArrayList arrayList;
                Map map = (Map) this.this$0.getScriptCompilationConfiguration().get(ScriptCompilationKt.getProvidedProperties(ScriptCompilationConfiguration.INSTANCE));
                if (map != null) {
                    KotlinScriptDefinitionAdapterFromNewAPIBase kotlinScriptDefinitionAdapterFromNewAPIBase = this.this$0;
                    ArrayList arrayList2 = new ArrayList(map.size());
                    for (Map.Entry entry : map.entrySet()) {
                        String str = (String) entry.getKey();
                        KotlinType kotlinType = (KotlinType) entry.getValue();
                        arrayList2.add(TuplesKt.to(str, KTypes.withNullability(KClassifiers.getStarProjectedType(kotlinScriptDefinitionAdapterFromNewAPIBase.getScriptingClass(kotlinType)), kotlinType.getIsNullable())));
                    }
                    arrayList = arrayList2;
                } else {
                    arrayList = null;
                }
                return arrayList == null ? CollectionsKt.emptyList() : arrayList;
            }
        });
        this.scriptingClassGetter = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<GetScriptingClass>() { // from class: org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinitionAdapterFromNewAPIBase$scriptingClassGetter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final GetScriptingClass invoke() {
                GetScriptingClass getScriptingClass = (GetScriptingClass) this.this$0.getHostConfiguration().get(HostConfigurationKt.getGetScriptingClass(ScriptingHostConfiguration.INSTANCE));
                if (getScriptingClass != null) {
                    return getScriptingClass;
                }
                throw new IllegalArgumentException("Expecting 'getScriptingClass' property in the scripting environment");
            }
        });
    }

    public KClass<?> getBaseClass() {
        return (KClass) this.baseClass.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public KClass<?> getTemplate() {
        return getBaseClass();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public String getName() {
        String str = (String) getScriptCompilationConfiguration().get(ScriptCompilationKt.getDisplayName(ScriptCompilationConfiguration.INSTANCE));
        return str == null ? KotlinScriptDefinition.INSTANCE.getKOTLIN_SCRIPT() : str;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public LanguageFileType getFileType() {
        return this.fileType;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public boolean isScript(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return StringsKt.endsWith$default(fileName, "." + getFileExtension(), false, 2, (Object) null);
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public Name getScriptName(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        String name2 = script.getContainingKtFile().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        String identifier = NameUtils.getScriptNameForFile(name2).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        Name nameIdentifier = Name.identifier(StringsKt.removeSuffix(identifier, (CharSequence) ("." + getFileExtension())));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public List<String> getAnnotationsForSamWithReceivers() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public DependenciesResolver getDependencyResolver() {
        return (DependenciesResolver) this.dependencyResolver.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public List<KClass<? extends Annotation>> getAcceptedAnnotations() {
        return (List) this.acceptedAnnotations.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public List<KType> getImplicitReceivers() {
        return (List) this.implicitReceivers.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public List<Pair<String, KType>> getProvidedProperties() {
        return (List) this.providedProperties.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public List<String> getAdditionalCompilerArguments() {
        List<String> list = (List) getScriptCompilationConfiguration().get(ScriptCompilationKt.getCompilerOptions(ScriptCompilationConfiguration.INSTANCE));
        return list == null ? CollectionsKt.emptyList() : list;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public List<ScriptExpectedLocation> getScriptExpectedLocations() {
        List<ScriptExpectedLocation> listMapToLegacyExpectedLocations;
        List list = (List) getScriptCompilationConfiguration().get(ScriptIdeConfigurationKt.getAcceptedLocations(ScriptIdeConfigurationKt.getIde(ScriptCompilationConfiguration.INSTANCE)));
        return (list == null || (listMapToLegacyExpectedLocations = ExpectedLocationUtilKt.mapToLegacyExpectedLocations(list)) == null) ? CollectionsKt.listOf((Object[]) new ScriptExpectedLocation[]{ScriptExpectedLocation.SourcesOnly, ScriptExpectedLocation.TestsOnly}) : listMapToLegacyExpectedLocations;
    }

    private final GetScriptingClass getScriptingClassGetter() {
        return (GetScriptingClass) this.scriptingClassGetter.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KClass<?> getScriptingClass(KotlinType type) {
        return getScriptingClassGetter().invoke(type, Reflection.getOrCreateKotlinClass(KotlinScriptDefinition.class), getHostConfiguration());
    }
}
