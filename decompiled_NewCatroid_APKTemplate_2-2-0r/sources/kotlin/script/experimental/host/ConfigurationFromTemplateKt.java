package kotlin.script.experimental.host;

import androidx.exifinterface.media.ExifInterface;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.script.experimental.annotations.KotlinScript;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: configurationFromTemplate.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001b\n\u0002\b\u0004\u001aE\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011¢\u0006\u0002\b\u0014H\u0002\u001a1\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00130\u0011¢\u0006\u0002\b\u0014H\u0002\u001a9\u0010\u0018\u001a\u00020\f2\u000e\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u00052\u0006\u0010\u001a\u001a\u00020\f2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00130\u0011¢\u0006\u0002\b\u0014H\u0002\u001a?\u0010\u001c\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\f2\f\b\u0002\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u00052\u0019\b\u0002\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011¢\u0006\u0002\b\u0014\u001a?\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\f2\f\b\u0002\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u00052\u0019\b\u0002\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00130\u0011¢\u0006\u0002\b\u0014\u001aZ\u0010\u001f\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\f2\f\b\u0002\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u00052\u0019\b\u0002\u0010!\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011¢\u0006\u0002\b\u00142\u0019\b\u0002\u0010\"\u001a\u0013\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00130\u0011¢\u0006\u0002\b\u0014\u001a,\u0010#\u001a\u0004\u0018\u0001H$\"\n\b\u0000\u0010$\u0018\u0001*\u00020%2\u000e\u0010&\u001a\n\u0012\u0006\b\u0001\u0012\u0002H$0\u0005H\u0082\b¢\u0006\u0002\u0010'\u001a$\u0010(\u001a\u0004\u0018\u0001H$\"\n\b\u0000\u0010$\u0018\u0001*\u00020)*\u0006\u0012\u0002\b\u00030\u0005H\u0082\b¢\u0006\u0002\u0010*\u001a$\u0010+\u001a\u0006\u0012\u0002\b\u00030\u0005*\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f2\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0002\u001a(\u0010,\u001a\u00020\u0013*\u00020\u00122\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u0004H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u001c\u0010\u0003\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006-"}, d2 = {"ERROR_MSG_PREFIX", "", "SCRIPT_RUNTIME_TEMPLATES_PACKAGE", "kotlinScriptAnnotation", "Lkotlin/script/experimental/annotations/KotlinScript;", "Lkotlin/reflect/KClass;", "getKotlinScriptAnnotation", "(Lkotlin/reflect/KClass;)Lkotlin/script/experimental/annotations/KotlinScript;", "constructCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "mainAnnotation", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "templateClass", "baseClassType", "Lkotlin/script/experimental/api/KotlinType;", "body", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration$Builder;", "", "Lkotlin/ExtensionFunctionType;", "constructEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration$Builder;", "constructHostConfiguration", "hostConfigurationKClass", "baseHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration$Builder;", "createCompilationConfigurationFromTemplate", "contextClass", "createEvaluationConfigurationFromTemplate", "createScriptDefinitionFromTemplate", "Lkotlin/script/experimental/host/ScriptDefinition;", "compilation", "evaluation", "scriptConfigInstance", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/script/experimental/util/PropertiesCollection;", MangleConstant.CLASS_PREFIX, "(Lkotlin/reflect/KClass;)Lkotlin/script/experimental/util/PropertiesCollection;", "findAnnotation", "", "(Lkotlin/reflect/KClass;)Ljava/lang/annotation/Annotation;", "getTemplateClass", "propertiesFromTemplate", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConfigurationFromTemplateKt {
    private static final String ERROR_MSG_PREFIX = "Unable to construct script definition: ";
    private static final String SCRIPT_RUNTIME_TEMPLATES_PACKAGE = "kotlin.script.templates.standard";

    public static /* synthetic */ ScriptDefinition createScriptDefinitionFromTemplate$default(KotlinType kotlinType, ScriptingHostConfiguration scriptingHostConfiguration, KClass kClass, Function1 function1, Function1 function2, int i, Object obj) {
        if ((i & 4) != 0) {
            kClass = Reflection.getOrCreateKotlinClass(ScriptDefinition.class);
        }
        if ((i & 8) != 0) {
            function1 = new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt.createScriptDefinitionFromTemplate.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptCompilationConfiguration.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 16) != 0) {
            function2 = new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt.createScriptDefinitionFromTemplate.2
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptEvaluationConfiguration.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            };
        }
        return createScriptDefinitionFromTemplate(kotlinType, scriptingHostConfiguration, kClass, function1, function2);
    }

    public static final ScriptDefinition createScriptDefinitionFromTemplate(KotlinType baseClassType, ScriptingHostConfiguration baseHostConfiguration, KClass<?> contextClass, Function1<? super ScriptCompilationConfiguration.Builder, Unit> compilation, Function1<? super ScriptEvaluationConfiguration.Builder, Unit> evaluation) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(baseClassType, "baseClassType");
        Intrinsics.checkNotNullParameter(baseHostConfiguration, "baseHostConfiguration");
        Intrinsics.checkNotNullParameter(contextClass, "contextClass");
        Intrinsics.checkNotNullParameter(compilation, "compilation");
        Intrinsics.checkNotNullParameter(evaluation, "evaluation");
        KClass<?> templateClass = getTemplateClass(baseClassType, baseHostConfiguration, contextClass);
        KotlinScript kotlinScriptAnnotation = getKotlinScriptAnnotation(templateClass);
        ScriptingHostConfiguration scriptingHostConfigurationConstructHostConfiguration = constructHostConfiguration(Reflection.getOrCreateKotlinClass(kotlinScriptAnnotation.hostConfiguration()), baseHostConfiguration, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt$createScriptDefinitionFromTemplate$hostConfiguration$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptingHostConfiguration.Builder constructHostConfiguration) {
                Intrinsics.checkNotNullParameter(constructHostConfiguration, "$this$constructHostConfiguration");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }
        });
        return new ScriptDefinition(constructCompilationConfiguration(kotlinScriptAnnotation, scriptingHostConfigurationConstructHostConfiguration, templateClass, baseClassType, compilation), constructEvaluationConfiguration(kotlinScriptAnnotation, scriptingHostConfigurationConstructHostConfiguration, evaluation));
    }

    public static /* synthetic */ ScriptCompilationConfiguration createCompilationConfigurationFromTemplate$default(KotlinType kotlinType, ScriptingHostConfiguration scriptingHostConfiguration, KClass kClass, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            kClass = Reflection.getOrCreateKotlinClass(ScriptCompilationConfiguration.class);
        }
        if ((i & 8) != 0) {
            function1 = new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt.createCompilationConfigurationFromTemplate.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptCompilationConfiguration.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            };
        }
        return createCompilationConfigurationFromTemplate(kotlinType, scriptingHostConfiguration, kClass, function1);
    }

    public static final ScriptCompilationConfiguration createCompilationConfigurationFromTemplate(KotlinType baseClassType, ScriptingHostConfiguration baseHostConfiguration, KClass<?> contextClass, Function1<? super ScriptCompilationConfiguration.Builder, Unit> body) {
        Intrinsics.checkNotNullParameter(baseClassType, "baseClassType");
        Intrinsics.checkNotNullParameter(baseHostConfiguration, "baseHostConfiguration");
        Intrinsics.checkNotNullParameter(contextClass, "contextClass");
        Intrinsics.checkNotNullParameter(body, "body");
        KClass<?> templateClass = getTemplateClass(baseClassType, baseHostConfiguration, contextClass);
        KotlinScript kotlinScriptAnnotation = getKotlinScriptAnnotation(templateClass);
        return constructCompilationConfiguration(kotlinScriptAnnotation, constructHostConfiguration(Reflection.getOrCreateKotlinClass(kotlinScriptAnnotation.hostConfiguration()), baseHostConfiguration, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt$createCompilationConfigurationFromTemplate$hostConfiguration$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptingHostConfiguration.Builder constructHostConfiguration) {
                Intrinsics.checkNotNullParameter(constructHostConfiguration, "$this$constructHostConfiguration");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }
        }), templateClass, baseClassType, body);
    }

    public static /* synthetic */ ScriptEvaluationConfiguration createEvaluationConfigurationFromTemplate$default(KotlinType kotlinType, ScriptingHostConfiguration scriptingHostConfiguration, KClass kClass, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            kClass = Reflection.getOrCreateKotlinClass(ScriptEvaluationConfiguration.class);
        }
        if ((i & 8) != 0) {
            function1 = new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt.createEvaluationConfigurationFromTemplate.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptEvaluationConfiguration.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            };
        }
        return createEvaluationConfigurationFromTemplate(kotlinType, scriptingHostConfiguration, kClass, function1);
    }

    public static final ScriptEvaluationConfiguration createEvaluationConfigurationFromTemplate(KotlinType baseClassType, ScriptingHostConfiguration baseHostConfiguration, KClass<?> contextClass, Function1<? super ScriptEvaluationConfiguration.Builder, Unit> body) {
        Intrinsics.checkNotNullParameter(baseClassType, "baseClassType");
        Intrinsics.checkNotNullParameter(baseHostConfiguration, "baseHostConfiguration");
        Intrinsics.checkNotNullParameter(contextClass, "contextClass");
        Intrinsics.checkNotNullParameter(body, "body");
        KotlinScript kotlinScriptAnnotation = getKotlinScriptAnnotation(getTemplateClass(baseClassType, baseHostConfiguration, contextClass));
        return constructEvaluationConfiguration(kotlinScriptAnnotation, constructHostConfiguration(Reflection.getOrCreateKotlinClass(kotlinScriptAnnotation.hostConfiguration()), baseHostConfiguration, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt$createEvaluationConfigurationFromTemplate$hostConfiguration$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptingHostConfiguration.Builder constructHostConfiguration) {
                Intrinsics.checkNotNullParameter(constructHostConfiguration, "$this$constructHostConfiguration");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }
        }), body);
    }

    private static final ScriptCompilationConfiguration constructCompilationConfiguration(final KotlinScript kotlinScript, final ScriptingHostConfiguration scriptingHostConfiguration, final KClass<?> kClass, final KotlinType kotlinType, final Function1<? super ScriptCompilationConfiguration.Builder, Unit> function1) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(kotlinScript.compilationConfiguration());
        PropertiesCollection propertiesCollection = (PropertiesCollection) orCreateKotlinClass.getObjectInstance();
        if (propertiesCollection == null) {
            Constructor<?>[] constructors = JvmClassMappingKt.getJavaClass(orCreateKotlinClass).getConstructors();
            Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
            Constructor<?>[] constructorArr = constructors;
            int length = constructorArr.length;
            int i = 0;
            boolean z = false;
            Constructor<?> constructor = null;
            while (true) {
                if (i >= length) {
                    if (!z) {
                        break;
                    }
                    break;
                }
                Constructor<?> constructor2 = constructorArr[i];
                Parameter[] parameters = constructor2.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                if (parameters.length == 0) {
                    if (!z) {
                        z = true;
                        constructor = constructor2;
                    }
                }
                i++;
                constructor = null;
                break;
            }
            Constructor<?> constructor3 = constructor;
            if (constructor3 != null) {
                Object objNewInstance = constructor3.newInstance(new Object[0]);
                if (objNewInstance == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.script.experimental.api.ScriptCompilationConfiguration");
                }
                propertiesCollection = (ScriptCompilationConfiguration) objNewInstance;
            } else {
                propertiesCollection = null;
            }
        }
        ScriptCompilationConfiguration scriptCompilationConfiguration = (ScriptCompilationConfiguration) propertiesCollection;
        if (scriptCompilationConfiguration == null) {
            throw new IllegalArgumentException("Unable to construct script definition: Illegal argument compilationConfiguration of the KotlinScript annotation: expecting an object or default-constructable class derived from ScriptCompilationConfiguration");
        }
        return new ScriptCompilationConfiguration(new ScriptCompilationConfiguration[]{scriptCompilationConfiguration}, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt.constructCompilationConfiguration.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
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
                PropertiesCollection.Key<ScriptingHostConfiguration> hostConfiguration = ScriptCompilationKt.getHostConfiguration($receiver);
                final ScriptingHostConfiguration scriptingHostConfiguration2 = scriptingHostConfiguration;
                $receiver.update(hostConfiguration, new Function1<ScriptingHostConfiguration, ScriptingHostConfiguration>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt.constructCompilationConfiguration.1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ScriptingHostConfiguration invoke(ScriptingHostConfiguration scriptingHostConfiguration3) {
                        return HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration3, scriptingHostConfiguration2);
                    }
                });
                ConfigurationFromTemplateKt.propertiesFromTemplate($receiver, kClass, kotlinType, kotlinScript);
                function1.invoke($receiver);
            }
        });
    }

    private static final ScriptEvaluationConfiguration constructEvaluationConfiguration(KotlinScript kotlinScript, final ScriptingHostConfiguration scriptingHostConfiguration, final Function1<? super ScriptEvaluationConfiguration.Builder, Unit> function1) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(kotlinScript.evaluationConfiguration());
        PropertiesCollection propertiesCollection = (PropertiesCollection) orCreateKotlinClass.getObjectInstance();
        if (propertiesCollection == null) {
            Constructor<?>[] constructors = JvmClassMappingKt.getJavaClass(orCreateKotlinClass).getConstructors();
            Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
            Constructor<?>[] constructorArr = constructors;
            int length = constructorArr.length;
            int i = 0;
            boolean z = false;
            Constructor<?> constructor = null;
            while (true) {
                if (i >= length) {
                    if (!z) {
                        break;
                    }
                    break;
                }
                Constructor<?> constructor2 = constructorArr[i];
                Parameter[] parameters = constructor2.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                if (parameters.length == 0) {
                    if (!z) {
                        z = true;
                        constructor = constructor2;
                    }
                }
                i++;
                constructor = null;
                break;
            }
            Constructor<?> constructor3 = constructor;
            if (constructor3 != null) {
                Object objNewInstance = constructor3.newInstance(new Object[0]);
                if (objNewInstance == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.script.experimental.api.ScriptEvaluationConfiguration");
                }
                propertiesCollection = (ScriptEvaluationConfiguration) objNewInstance;
            } else {
                propertiesCollection = null;
            }
        }
        ScriptEvaluationConfiguration scriptEvaluationConfiguration = (ScriptEvaluationConfiguration) propertiesCollection;
        if (scriptEvaluationConfiguration == null) {
            throw new IllegalArgumentException("Unable to construct script definition: Illegal argument evaluationConfiguration of the KotlinScript annotation: expecting an object or default-constructable class derived from ScriptEvaluationConfiguration");
        }
        return new ScriptEvaluationConfiguration(new ScriptEvaluationConfiguration[]{scriptEvaluationConfiguration}, new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt.constructEvaluationConfiguration.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptEvaluationConfiguration.Builder $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                PropertiesCollection.Key<ScriptingHostConfiguration> hostConfiguration = ScriptEvaluationKt.getHostConfiguration($receiver);
                final ScriptingHostConfiguration scriptingHostConfiguration2 = scriptingHostConfiguration;
                $receiver.update(hostConfiguration, new Function1<ScriptingHostConfiguration, ScriptingHostConfiguration>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt.constructEvaluationConfiguration.1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ScriptingHostConfiguration invoke(ScriptingHostConfiguration scriptingHostConfiguration3) {
                        return HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration3, scriptingHostConfiguration2);
                    }
                });
                function1.invoke($receiver);
            }
        });
    }

    private static final ScriptingHostConfiguration constructHostConfiguration(KClass<? extends ScriptingHostConfiguration> kClass, ScriptingHostConfiguration scriptingHostConfiguration, final Function1<? super ScriptingHostConfiguration.Builder, Unit> function1) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        ScriptingHostConfiguration scriptingHostConfiguration2;
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(ScriptingHostConfiguration.class))) {
            return HostConfigurationKt.withDefaultsFrom(new ScriptingHostConfiguration(function1), scriptingHostConfiguration);
        }
        Constructor<?>[] constructors = JvmClassMappingKt.getJavaClass((KClass) kClass).getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        Constructor<?>[] constructorArr = constructors;
        int length = constructorArr.length;
        ScriptingHostConfiguration scriptingHostConfiguration3 = null;
        int i = 0;
        boolean z = false;
        Constructor<?> constructor = null;
        while (true) {
            if (i >= length) {
                if (!z) {
                    break;
                }
                break;
            }
            Constructor<?> constructor2 = constructorArr[i];
            Constructor<?> constructor3 = constructor2;
            Parameter[] parameters = constructor3.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            if (!(parameters.length == 0)) {
                Parameter[] parameters2 = constructor3.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
                if (!((Parameter) ArraysKt.first(parameters2)).getType().isAssignableFrom(ScriptingHostConfiguration.class)) {
                    continue;
                } else if (!z) {
                    z = true;
                    constructor = constructor2;
                }
            }
            i++;
            constructor = null;
            break;
        }
        Constructor<?> constructor4 = constructor;
        if (constructor4 != null) {
            Object objNewInstance = constructor4.newInstance(scriptingHostConfiguration);
            Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type kotlin.script.experimental.host.ScriptingHostConfiguration");
            scriptingHostConfiguration2 = (ScriptingHostConfiguration) objNewInstance;
        } else {
            ScriptingHostConfiguration objectInstance = kClass.getObjectInstance();
            if (objectInstance == null) {
                Constructor<?>[] constructors2 = JvmClassMappingKt.getJavaClass((KClass) kClass).getConstructors();
                Intrinsics.checkNotNullExpressionValue(constructors2, "getConstructors(...)");
                Constructor<?>[] constructorArr2 = constructors2;
                int length2 = constructorArr2.length;
                int i2 = 0;
                boolean z2 = false;
                Constructor<?> constructor5 = null;
                while (true) {
                    if (i2 >= length2) {
                        if (!z2) {
                            break;
                        }
                        break;
                    }
                    Constructor<?> constructor6 = constructorArr2[i2];
                    Parameter[] parameters3 = constructor6.getParameters();
                    Intrinsics.checkNotNullExpressionValue(parameters3, "getParameters(...)");
                    if (parameters3.length == 0) {
                        if (!z2) {
                            constructor5 = constructor6;
                            z2 = true;
                        }
                    }
                    i2++;
                    constructor5 = null;
                    break;
                }
                Constructor<?> constructor7 = constructor5;
                if (constructor7 != null) {
                    Object objNewInstance2 = constructor7.newInstance(new Object[0]);
                    if (objNewInstance2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.script.experimental.host.ScriptingHostConfiguration");
                    }
                    scriptingHostConfiguration3 = (ScriptingHostConfiguration) objNewInstance2;
                }
                objectInstance = scriptingHostConfiguration3;
            }
            scriptingHostConfiguration2 = objectInstance;
            if (scriptingHostConfiguration2 == null) {
                throw new IllegalArgumentException("Unable to construct script definition: Illegal argument hostConfiguration of the KotlinScript annotation: expecting an object or a class derived from ScriptingHostConfiguration constructable without arguments or from a base configuration");
            }
        }
        return HostConfigurationKt.withDefaultsFrom(HostConfigurationKt.with(scriptingHostConfiguration2, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.host.ConfigurationFromTemplateKt.constructHostConfiguration.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptingHostConfiguration.Builder with) {
                Intrinsics.checkNotNullParameter(with, "$this$with");
                function1.invoke(with);
            }
        }), scriptingHostConfiguration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void propertiesFromTemplate(ScriptCompilationConfiguration.Builder builder, KClass<?> kClass, KotlinType kotlinType, KotlinScript kotlinScript) {
        ScriptCompilationConfiguration.Builder builder2 = builder;
        PropertiesCollection.Key<KotlinType> baseClass = ScriptCompilationKt.getBaseClass(builder2);
        if (!Intrinsics.areEqual(kClass, kotlinType.getFromClass())) {
            kotlinType = new KotlinType((KClass) kClass, false, 2, (DefaultConstructorMarker) null);
        }
        builder.replaceOnlyDefault(baseClass, kotlinType);
        builder.replaceOnlyDefault(ScriptCompilationKt.getFileExtension(builder2), kotlinScript.fileExtension());
        if (ArraysKt.contains(new String[]{"gradle.kts", "init.gradle.kts", "settings.gradle.kts"}, builder.get(ScriptCompilationKt.getFileExtension(builder2)))) {
            builder.invoke((PropertiesCollection.Key<boolean>) ScriptCompilationKt.isStandalone(builder2), false);
        }
        builder.replaceOnlyDefault(ScriptCompilationKt.getFilePathPattern(builder2), kotlinScript.filePathPattern());
        builder.replaceOnlyDefault(ScriptCompilationKt.getDisplayName(builder2), kotlinScript.displayName());
    }

    private static final KClass<?> getTemplateClass(KotlinType kotlinType, ScriptingHostConfiguration scriptingHostConfiguration, KClass<?> kClass) {
        GetScriptingClass getScriptingClass = (GetScriptingClass) scriptingHostConfiguration.get(HostConfigurationKt.getGetScriptingClass(ScriptingHostConfiguration.INSTANCE));
        if (getScriptingClass == null) {
            throw new IllegalArgumentException("Unable to construct script definition: Expecting 'getScriptingClass' parameter in the scripting host configuration");
        }
        try {
            return getScriptingClass.invoke(kotlinType, kClass, scriptingHostConfiguration);
        } catch (Throwable th) {
            throw new IllegalArgumentException("Unable to construct script definition: Unable to load base class " + kotlinType, th);
        }
    }

    private static final /* synthetic */ <T extends Annotation> T findAnnotation(KClass<?> kClass) {
        Annotation[] annotations = JvmClassMappingKt.getJavaClass((KClass) kClass).getAnnotations();
        Intrinsics.checkNotNullExpressionValue(annotations, "getAnnotations(...)");
        for (Annotation annotation : annotations) {
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (annotation instanceof Annotation) {
                Intrinsics.reifiedOperationMarker(1, "T?");
                return (T) annotation;
            }
        }
        annotation = null;
        Intrinsics.reifiedOperationMarker(1, "T?");
        return (T) annotation;
    }

    private static final /* synthetic */ <T extends PropertiesCollection> T scriptConfigInstance(KClass<? extends T> kClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T objectInstance = kClass.getObjectInstance();
        if (objectInstance == null) {
            Constructor<?>[] constructors = JvmClassMappingKt.getJavaClass((KClass) kClass).getConstructors();
            Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
            Constructor<?>[] constructorArr = constructors;
            int length = constructorArr.length;
            int i = 0;
            boolean z = false;
            Constructor<?> constructor = null;
            while (true) {
                if (i >= length) {
                    if (!z) {
                        break;
                    }
                    break;
                }
                Constructor<?> constructor2 = constructorArr[i];
                Parameter[] parameters = constructor2.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                if (parameters.length == 0) {
                    if (!z) {
                        z = true;
                        constructor = constructor2;
                    }
                }
                i++;
                constructor = null;
                break;
            }
            Constructor<?> constructor3 = constructor;
            if (constructor3 != null) {
                Object objNewInstance = constructor3.newInstance(new Object[0]);
                Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
                objectInstance = (T) ((PropertiesCollection) objNewInstance);
            } else {
                objectInstance = null;
            }
        }
        T t = objectInstance;
        return objectInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final KotlinScript getKotlinScriptAnnotation(KClass<?> kClass) {
        KotlinScript kotlinScript;
        Annotation annotation;
        Annotation[] annotations = JvmClassMappingKt.getJavaClass((KClass) kClass).getAnnotations();
        Intrinsics.checkNotNullExpressionValue(annotations, "getAnnotations(...)");
        Annotation[] annotationArr = annotations;
        int length = annotationArr.length;
        int i = 0;
        while (true) {
            kotlinScript = null;
            if (i >= length) {
                annotation = null;
                break;
            }
            annotation = annotationArr[i];
            if (annotation instanceof KotlinScript) {
                break;
            }
            i++;
        }
        KotlinScript kotlinScript2 = (KotlinScript) annotation;
        if (kotlinScript2 != null) {
            return kotlinScript2;
        }
        String qualifiedName = kClass.getQualifiedName();
        if (Intrinsics.areEqual(qualifiedName, Reflection.getOrCreateKotlinClass(Object.class).getQualifiedName()) ? true : Intrinsics.areEqual(qualifiedName, "kotlin.script.templates.standard.SimpleScriptTemplate") ? true : Intrinsics.areEqual(qualifiedName, "kotlin.script.templates.standard.ScriptTemplateWithArgs") ? true : Intrinsics.areEqual(qualifiedName, "kotlin.script.templates.standard.ScriptTemplateWithBindings")) {
            Annotation[] annotations2 = DummyScriptTemplate.class.getAnnotations();
            Intrinsics.checkNotNullExpressionValue(annotations2, "getAnnotations(...)");
            for (Annotation annotation2 : annotations2) {
                if (annotation2 instanceof KotlinScript) {
                    kotlinScript = annotation2;
                    break;
                }
            }
            kotlinScript = (KotlinScript) kotlinScript;
        }
        if (kotlinScript != null) {
            return kotlinScript;
        }
        throw new IllegalArgumentException("Unable to construct script definition: Expecting KotlinScript annotation on the " + kClass);
    }
}
