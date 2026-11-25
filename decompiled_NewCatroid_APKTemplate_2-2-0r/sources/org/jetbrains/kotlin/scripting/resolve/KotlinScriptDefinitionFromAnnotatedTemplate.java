package org.jetbrains.kotlin.scripting.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.full.KClasses;
import kotlin.script.dependencies.ScriptDependenciesResolver;
import kotlin.script.experimental.dependencies.AsyncDependenciesResolver;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import kotlin.script.experimental.location.ScriptExpectedLocation;
import kotlin.script.experimental.location.ScriptExpectedLocations;
import kotlin.script.extensions.SamWithReceiverAnnotations;
import kotlin.script.templates.AcceptedAnnotations;
import kotlin.script.templates.AnnotationsKt;
import kotlin.script.templates.ScriptTemplateAdditionalCompilerArguments;
import kotlin.script.templates.ScriptTemplateAdditionalCompilerArgumentsProvider;
import kotlin.script.templates.ScriptTemplateDefinition;
import kotlin.text.Regex;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinScriptDefinitionFromAnnotatedTemplate.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 F2\u00020\u0001:\u0001FB?\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0018\b\u0002\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u0012\u00102\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u0003030\tH\u0002J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u0016J'\u00108\u001a\u0004\u0018\u0001H9\"\b\b\u0000\u00109*\u00020\u00042\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H90\u0003H\u0002¢\u0006\u0002\u0010;J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u0007H\u0016J\u001a\u0010?\u001a\u0004\u0018\u00010\u001c2\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0002J0\u0010@\u001a\u0004\u0018\u0001H9\"\u0004\b\u0000\u001092\b\b\u0002\u0010A\u001a\u00020=2\u000e\u0010B\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H90CH\u0082\b¢\u0006\u0002\u0010DJ\b\u0010E\u001a\u00020\u0007H\u0016R)\u0010\f\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\r0\u00030\t8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR)\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00138VX\u0097\u0084\u0002¢\u0006\u0012\n\u0004\b\u0018\u0010\u0011\u0012\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u000fR\u001b\u0010\u001b\u001a\u00020\u001c8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0011\u001a\u0004\b\u001d\u0010\u001eR!\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R#\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u0011\u001a\u0004\b&\u0010\u000fR!\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\t8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\u0011\u001a\u0004\b*\u0010\u000fR\u001b\u0010,\u001a\u00020-8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\u0011\u001a\u0004\b.\u0010/R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u000f¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/KotlinScriptDefinitionFromAnnotatedTemplate;", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE, "Lkotlin/reflect/KClass;", "", "environment", "", "", "templateClasspath", "", "Ljava/io/File;", "(Lkotlin/reflect/KClass;Ljava/util/Map;Ljava/util/List;)V", "acceptedAnnotations", "", "getAcceptedAnnotations", "()Ljava/util/List;", "acceptedAnnotations$delegate", "Lkotlin/Lazy;", "additionalCompilerArguments", "", "getAdditionalCompilerArguments$annotations", "()V", "getAdditionalCompilerArguments", "()Ljava/lang/Iterable;", "additionalCompilerArguments$delegate", "annotationsForSamWithReceivers", "getAnnotationsForSamWithReceivers", "dependencyResolver", "Lkotlin/script/experimental/dependencies/DependenciesResolver;", "getDependencyResolver", "()Lkotlin/script/experimental/dependencies/DependenciesResolver;", "dependencyResolver$delegate", "getEnvironment", "()Ljava/util/Map;", "name", "getName", "()Ljava/lang/String;", "samWithReceiverAnnotations", "getSamWithReceiverAnnotations", "samWithReceiverAnnotations$delegate", "scriptExpectedLocations", "Lkotlin/script/experimental/location/ScriptExpectedLocation;", "getScriptExpectedLocations", "scriptExpectedLocations$delegate", "scriptFilePattern", "Lkotlin/text/Regex;", "getScriptFilePattern", "()Lkotlin/text/Regex;", "scriptFilePattern$delegate", "getTemplateClasspath", "getResolveFunctions", "Lkotlin/reflect/KFunction;", "getScriptName", "Lorg/jetbrains/kotlin/name/Name;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "instantiateResolver", ExifInterface.GPS_DIRECTION_TRUE, "resolverClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Object;", "isScript", "", PsiTreeChangeEvent.PROP_FILE_NAME, "resolverFromAnnotation", "takeUnlessError", "reportError", "body", "Lkotlin/Function0;", "(ZLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toString", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinScriptDefinitionFromAnnotatedTemplate extends KotlinScriptDefinition {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Logger log;

    /* JADX INFO: renamed from: acceptedAnnotations$delegate, reason: from kotlin metadata */
    private final Lazy acceptedAnnotations;

    /* JADX INFO: renamed from: additionalCompilerArguments$delegate, reason: from kotlin metadata */
    private final Lazy additionalCompilerArguments;

    /* JADX INFO: renamed from: dependencyResolver$delegate, reason: from kotlin metadata */
    private final Lazy dependencyResolver;
    private final Map<String, Object> environment;
    private final String name;

    /* JADX INFO: renamed from: samWithReceiverAnnotations$delegate, reason: from kotlin metadata */
    private final Lazy samWithReceiverAnnotations;

    /* JADX INFO: renamed from: scriptExpectedLocations$delegate, reason: from kotlin metadata */
    private final Lazy scriptExpectedLocations;

    /* JADX INFO: renamed from: scriptFilePattern$delegate, reason: from kotlin metadata */
    private final Lazy scriptFilePattern;
    private final List<File> templateClasspath;

    @Deprecated(message = "temporary workaround for missing functionality, will be replaced by the new API soon")
    public static /* synthetic */ void getAdditionalCompilerArguments$annotations() {
    }

    public final Map<String, Object> getEnvironment() {
        return this.environment;
    }

    public /* synthetic */ KotlinScriptDefinitionFromAnnotatedTemplate(KClass kClass, Map map, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kClass, (i & 2) != 0 ? null : map, (i & 4) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final List<File> getTemplateClasspath() {
        return this.templateClasspath;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KotlinScriptDefinitionFromAnnotatedTemplate(final KClass<? extends Object> template, Map<String, ? extends Object> map, List<? extends File> templateClasspath) {
        super(template);
        Intrinsics.checkNotNullParameter(template, "template");
        Intrinsics.checkNotNullParameter(templateClasspath, "templateClasspath");
        this.environment = map;
        this.templateClasspath = templateClasspath;
        this.scriptFilePattern = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Regex>() { // from class: org.jetbrains.kotlin.scripting.resolve.KotlinScriptDefinitionFromAnnotatedTemplate$scriptFilePattern$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Regex invoke() {
                String strScriptFilePattern;
                Object next;
                Object next2;
                KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate = this.this$0;
                String strScriptFilePattern2 = null;
                try {
                    Iterator<T> it = template.getAnnotations().iterator();
                    do {
                        if (!it.hasNext()) {
                            next2 = null;
                            break;
                        }
                        next2 = it.next();
                    } while (!(next2 instanceof ScriptTemplateDefinition));
                    ScriptTemplateDefinition scriptTemplateDefinition = (ScriptTemplateDefinition) next2;
                    strScriptFilePattern = scriptTemplateDefinition != null ? scriptTemplateDefinition.scriptFilePattern() : null;
                } catch (Throwable th) {
                    KotlinScriptDefinitionFromAnnotatedTemplate.INSTANCE.getLog$kotlin_scripting_compiler_impl().error("Invalid script template: " + kotlinScriptDefinitionFromAnnotatedTemplate.getTemplate().getQualifiedName(), th);
                }
                if (strScriptFilePattern == null) {
                    KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate2 = this.this$0;
                    try {
                        Iterator<T> it2 = template.getAnnotations().iterator();
                        do {
                            if (!it2.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it2.next();
                        } while (!(next instanceof ScriptTemplateDefinition));
                        ScriptTemplateDefinition scriptTemplateDefinition2 = (ScriptTemplateDefinition) next;
                        if (scriptTemplateDefinition2 != null) {
                            strScriptFilePattern2 = scriptTemplateDefinition2.scriptFilePattern();
                        }
                    } catch (Throwable th2) {
                        KotlinScriptDefinitionFromAnnotatedTemplate.INSTANCE.getLog$kotlin_scripting_compiler_impl().error("Invalid script template: " + kotlinScriptDefinitionFromAnnotatedTemplate2.getTemplate().getQualifiedName(), th2);
                    }
                    strScriptFilePattern = strScriptFilePattern2 == null ? AnnotationsKt.DEFAULT_SCRIPT_FILE_PATTERN : strScriptFilePattern2;
                }
                return new Regex(strScriptFilePattern);
            }
        });
        this.dependencyResolver = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<DependenciesResolver>() { // from class: org.jetbrains.kotlin.scripting.resolve.KotlinScriptDefinitionFromAnnotatedTemplate$dependencyResolver$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DependenciesResolver invoke() {
                DependenciesResolver dependenciesResolverResolverFromAnnotation = this.this$0.resolverFromAnnotation(template);
                return dependenciesResolverResolverFromAnnotation == null ? DependenciesResolver.NoDependencies.INSTANCE : dependenciesResolverResolverFromAnnotation;
            }
        });
        this.samWithReceiverAnnotations = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.scripting.resolve.KotlinScriptDefinitionFromAnnotatedTemplate$samWithReceiverAnnotations$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends String> invoke() {
                Object next;
                String[] strArrAnnotations;
                KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate = this.this$0;
                try {
                    Iterator<T> it = template.getAnnotations().iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (!(next instanceof SamWithReceiverAnnotations));
                    SamWithReceiverAnnotations samWithReceiverAnnotations = (SamWithReceiverAnnotations) next;
                    if (samWithReceiverAnnotations == null || (strArrAnnotations = samWithReceiverAnnotations.annotations()) == null) {
                        return null;
                    }
                    return ArraysKt.toList(strArrAnnotations);
                } catch (Throwable th) {
                    KotlinScriptDefinitionFromAnnotatedTemplate.INSTANCE.getLog$kotlin_scripting_compiler_impl().error("Invalid script template: " + kotlinScriptDefinitionFromAnnotatedTemplate.getTemplate().getQualifiedName(), th);
                    return null;
                }
            }
        });
        this.acceptedAnnotations = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends KClass<? extends Annotation>>>() { // from class: org.jetbrains.kotlin.scripting.resolve.KotlinScriptDefinitionFromAnnotatedTemplate$acceptedAnnotations$2
            {
                super(0);
            }

            private static final boolean invoke$sameSignature(KFunction<?> kFunction, KFunction<?> kFunction2) {
                if (Intrinsics.areEqual(kFunction.getName(), kFunction2.getName()) && kFunction.getParameters().size() == kFunction2.getParameters().size()) {
                    List<Pair> listZip = CollectionsKt.zip(kFunction.getParameters(), kFunction2.getParameters());
                    if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
                        for (Pair pair : listZip) {
                            if (((KParameter) pair.getFirst()).getKind() == KParameter.Kind.INSTANCE || Intrinsics.areEqual(((KParameter) pair.getFirst()).getName(), ((KParameter) pair.getSecond()).getName())) {
                            }
                        }
                    }
                    return true;
                }
                return false;
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KClass<? extends Annotation>> invoke() {
                List resolveFunctions = this.this$0.getResolveFunctions();
                Collection<KFunction<?>> memberFunctions = KClasses.getMemberFunctions(Reflection.getOrCreateKotlinClass(KotlinScriptDefinitionFromAnnotatedTemplateKt.unwrap(this.this$0.getDependencyResolver()).getClass()));
                ArrayList arrayList = new ArrayList();
                for (Object obj : memberFunctions) {
                    KFunction kFunction = (KFunction) obj;
                    List list = resolveFunctions;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            if (invoke$sameSignature(kFunction, (KFunction) it.next())) {
                                arrayList.add(obj);
                                break;
                            }
                        }
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    CollectionsKt.addAll(arrayList2, ((KFunction) it2.next()).getAnnotations());
                }
                ArrayList arrayList3 = new ArrayList();
                for (Object obj2 : arrayList2) {
                    if (obj2 instanceof AcceptedAnnotations) {
                        arrayList3.add(obj2);
                    }
                }
                ArrayList arrayList4 = new ArrayList();
                Iterator it3 = arrayList3.iterator();
                while (it3.hasNext()) {
                    CollectionsKt.addAll(arrayList4, ArraysKt.toList(Reflection.getOrCreateKotlinClasses(((AcceptedAnnotations) it3.next()).supportedAnnotationClasses())));
                }
                HashSet hashSet = new HashSet();
                ArrayList arrayList5 = new ArrayList();
                for (Object obj3 : arrayList4) {
                    if (hashSet.add(((KClass) obj3).getQualifiedName())) {
                        arrayList5.add(obj3);
                    }
                }
                return arrayList5;
            }
        });
        this.scriptExpectedLocations = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends ScriptExpectedLocation>>() { // from class: org.jetbrains.kotlin.scripting.resolve.KotlinScriptDefinitionFromAnnotatedTemplate$scriptExpectedLocations$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ScriptExpectedLocation> invoke() {
                ScriptExpectedLocation[] scriptExpectedLocationArrValue;
                List<? extends ScriptExpectedLocation> list;
                Object next;
                KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate = this.this$0;
                ScriptExpectedLocations scriptExpectedLocations = null;
                try {
                    Iterator<T> it = template.getAnnotations().iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (!(next instanceof ScriptExpectedLocations));
                    scriptExpectedLocations = (ScriptExpectedLocations) next;
                } catch (Throwable th) {
                    KotlinScriptDefinitionFromAnnotatedTemplate.INSTANCE.getLog$kotlin_scripting_compiler_impl().error("Invalid script template: " + kotlinScriptDefinitionFromAnnotatedTemplate.getTemplate().getQualifiedName(), th);
                }
                return (scriptExpectedLocations == null || (scriptExpectedLocationArrValue = scriptExpectedLocations.value()) == null || (list = ArraysKt.toList(scriptExpectedLocationArrValue)) == null) ? super/*org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition*/.getScriptExpectedLocations() : list;
            }
        });
        String simpleName = template.getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.name = simpleName;
        this.additionalCompilerArguments = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Iterable<? extends String>>() { // from class: org.jetbrains.kotlin.scripting.resolve.KotlinScriptDefinitionFromAnnotatedTemplate$additionalCompilerArguments$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function0
            public final Iterable<? extends String> invoke() {
                ScriptTemplateAdditionalCompilerArgumentsProvider scriptTemplateAdditionalCompilerArgumentsProvider;
                Object next;
                KFunction primaryConstructor;
                KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate = this.this$0;
                try {
                    Iterator<T> it = template.getAnnotations().iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (!(next instanceof ScriptTemplateAdditionalCompilerArguments));
                    ScriptTemplateAdditionalCompilerArguments scriptTemplateAdditionalCompilerArguments = (ScriptTemplateAdditionalCompilerArguments) next;
                    scriptTemplateAdditionalCompilerArgumentsProvider = (scriptTemplateAdditionalCompilerArguments == null || (primaryConstructor = KClasses.getPrimaryConstructor(Reflection.getOrCreateKotlinClass(scriptTemplateAdditionalCompilerArguments.provider()))) == null) ? null : (ScriptTemplateAdditionalCompilerArgumentsProvider) primaryConstructor.call(ArraysKt.asIterable(scriptTemplateAdditionalCompilerArguments.arguments()));
                } catch (Throwable th) {
                    KotlinScriptDefinitionFromAnnotatedTemplate.INSTANCE.getLog$kotlin_scripting_compiler_impl().error("Invalid script template: " + kotlinScriptDefinitionFromAnnotatedTemplate.getTemplate().getQualifiedName(), th);
                }
                if (scriptTemplateAdditionalCompilerArgumentsProvider != null) {
                    return scriptTemplateAdditionalCompilerArgumentsProvider.getAdditionalCompilerArguments(this.this$0.getEnvironment());
                }
                return null;
            }
        });
    }

    public final Regex getScriptFilePattern() {
        return (Regex) this.scriptFilePattern.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public DependenciesResolver getDependencyResolver() {
        return (DependenciesResolver) this.dependencyResolver.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DependenciesResolver resolverFromAnnotation(KClass<? extends Object> template) {
        ScriptTemplateDefinition scriptTemplateDefinition;
        Object next;
        try {
            Iterator<T> it = template.getAnnotations().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!(next instanceof ScriptTemplateDefinition));
            scriptTemplateDefinition = (ScriptTemplateDefinition) next;
        } catch (Throwable th) {
            INSTANCE.getLog$kotlin_scripting_compiler_impl().error("Invalid script template: " + getTemplate().getQualifiedName(), th);
            scriptTemplateDefinition = null;
        }
        if (scriptTemplateDefinition == null) {
            return null;
        }
        ScriptDependenciesResolver scriptDependenciesResolver = (ScriptDependenciesResolver) instantiateResolver(Reflection.getOrCreateKotlinClass(scriptTemplateDefinition.resolver()));
        if (scriptDependenciesResolver instanceof AsyncDependenciesResolver) {
            return new AsyncDependencyResolverWrapper((AsyncDependenciesResolver) scriptDependenciesResolver);
        }
        if (scriptDependenciesResolver instanceof DependenciesResolver) {
            return (DependenciesResolver) scriptDependenciesResolver;
        }
        return scriptDependenciesResolver != null ? new ApiChangeDependencyResolverWrapper(scriptDependenciesResolver) : null;
    }

    private final <T> T instantiateResolver(KClass<T> resolverClass) {
        T next;
        try {
            T objectInstance = resolverClass.getObjectInstance();
            if (objectInstance != null) {
                return objectInstance;
            }
            Iterator<T> it = resolverClass.getConstructors().iterator();
            loop0: while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                List<KParameter> parameters = ((KFunction) next).getParameters();
                if (!(parameters instanceof Collection) || !parameters.isEmpty()) {
                    Iterator<T> it2 = parameters.iterator();
                    do {
                        if (!it2.hasNext()) {
                            break loop0;
                        }
                    } while (((KParameter) it2.next()).isOptional());
                } else {
                    break;
                }
            }
            KFunction kFunction = (KFunction) next;
            if (kFunction == null) {
                log.warn("[kts] " + resolverClass.getQualifiedName() + " must have a constructor without required parameters");
                return null;
            }
            return (T) kFunction.callBy(MapsKt.emptyMap());
        } catch (ClassCastException e) {
            log.warn("[kts] Script def error " + e.getMessage());
            return null;
        }
    }

    private final List<String> getSamWithReceiverAnnotations() {
        return (List) this.samWithReceiverAnnotations.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public List<KClass<? extends Annotation>> getAcceptedAnnotations() {
        return (List) this.acceptedAnnotations.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<KFunction<?>> getResolveFunctions() {
        Collection<KFunction<?>> memberFunctions = KClasses.getMemberFunctions(Reflection.getOrCreateKotlinClass(AsyncDependenciesResolver.class));
        ArrayList arrayList = new ArrayList();
        for (Object obj : memberFunctions) {
            KFunction kFunction = (KFunction) obj;
            if (Intrinsics.areEqual(kFunction.getName(), "resolve") || Intrinsics.areEqual(kFunction.getName(), "resolveAsync")) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        arrayList2.size();
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public List<ScriptExpectedLocation> getScriptExpectedLocations() {
        return (List) this.scriptExpectedLocations.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public boolean isScript(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return getScriptFilePattern().matches(fileName);
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public Name getScriptName(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        String name2 = script.getContainingKtFile().getMyName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return NameUtils.getScriptNameForFile(name2);
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "KotlinScriptDefinitionFromAnnotatedTemplate - " + getTemplate().getSimpleName();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public List<String> getAnnotationsForSamWithReceivers() {
        List<String> samWithReceiverAnnotations = getSamWithReceiverAnnotations();
        return samWithReceiverAnnotations == null ? super.getAnnotationsForSamWithReceivers() : samWithReceiverAnnotations;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public Iterable<String> getAdditionalCompilerArguments() {
        return (Iterable) this.additionalCompilerArguments.getValue();
    }

    private final <T> T takeUnlessError(boolean reportError, Function0<? extends T> body) {
        try {
            return body.invoke();
        } catch (Throwable th) {
            if (reportError) {
                INSTANCE.getLog$kotlin_scripting_compiler_impl().error("Invalid script template: " + getTemplate().getQualifiedName(), th);
            } else {
                INSTANCE.getLog$kotlin_scripting_compiler_impl().warn("Invalid script template: " + getTemplate().getQualifiedName(), th);
            }
            return null;
        }
    }

    static /* synthetic */ Object takeUnlessError$default(KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate, boolean z, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: takeUnlessError");
        }
        if ((i & 1) != 0) {
            z = true;
        }
        try {
            return function0.invoke();
        } catch (Throwable th) {
            if (z) {
                INSTANCE.getLog$kotlin_scripting_compiler_impl().error("Invalid script template: " + kotlinScriptDefinitionFromAnnotatedTemplate.getTemplate().getQualifiedName(), th);
            } else {
                INSTANCE.getLog$kotlin_scripting_compiler_impl().warn("Invalid script template: " + kotlinScriptDefinitionFromAnnotatedTemplate.getTemplate().getQualifiedName(), th);
            }
            return null;
        }
    }

    /* JADX INFO: compiled from: KotlinScriptDefinitionFromAnnotatedTemplate.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/KotlinScriptDefinitionFromAnnotatedTemplate$Companion;", "", "()V", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "getLog$kotlin_scripting_compiler_impl", "()Lcom/intellij/openapi/diagnostic/Logger;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Logger getLog$kotlin_scripting_compiler_impl() {
            return KotlinScriptDefinitionFromAnnotatedTemplate.log;
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) KotlinScriptDefinitionFromAnnotatedTemplate.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        log = logger;
    }
}
