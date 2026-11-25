package org.jetbrains.kotlin.cli.common.arguments;

import androidx.exifinterface.media.ExifInterface;
import com.beust.jcommander.Parameters;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.KClass;
import kotlin.reflect.KClasses;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: parseCommandLineArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000p\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u00062\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0002\u001a(\u0010\u0013\u001a\u0002H\u0014\"\n\b\u0000\u0010\u0014\u0018\u0001*\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0017H\u0086\b¢\u0006\u0002\u0010\u0018\u001a5\u0010\u0013\u001a\u00020\u0019\"\b\b\u0000\u0010\u001a*\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00172\u0006\u0010\u001b\u001a\u0002H\u001a2\b\b\u0002\u0010\u001c\u001a\u00020\t¢\u0006\u0002\u0010\u001d\u001a1\u0010\u0013\u001a\u0002H\u0014\"\b\b\u0000\u0010\u0014*\u00020\u00152\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00140\u001f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0017¢\u0006\u0002\u0010 \u001a\u001d\u0010!\u001a\u00020\u0019\"\b\b\u0000\u0010\u001a*\u00020\u00152\u0006\u0010\"\u001a\u0002H\u001a¢\u0006\u0002\u0010#\u001aC\u0010$\u001a\u00020\u0019\"\b\b\u0000\u0010\u001a*\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00172\u0006\u0010\u001b\u001a\u0002H\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&2\u0006\u0010\u001c\u001a\u00020\tH\u0002¢\u0006\u0002\u0010(\u001aI\u0010)\u001a\u00020\u0019\"\b\b\u0000\u0010\u001a*\u00020\u00152\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+2\u0006\u0010\u001b\u001a\u0002H\u001a2\u0006\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u001c\u001a\u00020\tH\u0002¢\u0006\u0002\u00100\u001a\u0012\u00101\u001a\u0004\u0018\u00010\u00012\b\u0010%\u001a\u0004\u0018\u00010'\u001a;\u00102\u001a\u00020\u0019\"\b\b\u0000\u0010\u001a*\u00020\u0015*\u0002H\u001a2\u0016\u00103\u001a\u0012\u0012\u0004\u0012\u00020504j\b\u0012\u0004\u0012\u000205`62\u0006\u0010\u001c\u001a\u00020\tH\u0002¢\u0006\u0002\u00107\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"*\u0010\u0003\u001a\u001e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\b\u001a\u00020\t*\u00020\n8F¢\u0006\u0006\u001a\u0004\b\b\u0010\u000b\"\u001d\u0010\f\u001a\u0004\u0018\u00010\u0001*\u00020\n8F¢\u0006\f\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010¨\u00068"}, d2 = {"ADVANCED_ARGUMENT_PREFIX", "", "FREE_ARGS_DELIMITER", "argumentsCache", "Ljava/util/concurrent/ConcurrentHashMap;", "Ljava/lang/Class;", "", "Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentField;", "isAdvanced", "", "Lorg/jetbrains/kotlin/cli/common/arguments/Argument;", "(Lorg/jetbrains/kotlin/cli/common/arguments/Argument;)Z", "resolvedDelimiter", "getResolvedDelimiter$annotations", "(Lorg/jetbrains/kotlin/cli/common/arguments/Argument;)V", "getResolvedDelimiter", "(Lorg/jetbrains/kotlin/cli/common/arguments/Argument;)Ljava/lang/String;", "getArguments", "klass", "parseCommandLineArguments", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "args", "", "(Ljava/util/List;)Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "", "A", "result", "overrideArguments", "(Ljava/util/List;Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;Z)V", "clazz", "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;Ljava/util/List;)Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "parseCommandLineArgumentsFromEnvironment", "arguments", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;)V", "parsePreprocessedCommandLineArguments", "errors", "Lkotlin/Lazy;", "Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;", "(Ljava/util/List;Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;Lkotlin/Lazy;Z)V", "updateField", "getter", "Ljava/lang/reflect/Method;", "setter", "value", "", "delimiter", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;Ljava/lang/Object;Ljava/lang/String;Z)V", "validateArguments", "updateInternalArguments", "newInternalArguments", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgument;", "Lkotlin/collections/ArrayList;", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;Ljava/util/ArrayList;Z)V", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ParseCommandLineArgumentsKt {
    private static final ConcurrentHashMap<Class<?>, Map<String, ArgumentField>> argumentsCache = new ConcurrentHashMap<>();

    public static /* synthetic */ void getResolvedDelimiter$annotations(Argument argument) {
    }

    public static final boolean isAdvanced(Argument argument) {
        Intrinsics.checkNotNullParameter(argument, "<this>");
        return StringsKt.startsWith$default(argument.value(), "-X", false, 2, (Object) null) && argument.value().length() > 2;
    }

    public static final String getResolvedDelimiter(Argument argument) {
        Intrinsics.checkNotNullParameter(argument, "<this>");
        String strDelimiter = argument.delimiter();
        if (Intrinsics.areEqual(strDelimiter, "")) {
            return null;
        }
        return Intrinsics.areEqual(strDelimiter, Argument.Delimiters.pathSeparator) ? File.INSTANCE.getPathSeparator() : argument.delimiter();
    }

    public static final /* synthetic */ <T extends CommonToolArguments> T parseCommandLineArguments(List<String> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) parseCommandLineArguments(Reflection.getOrCreateKotlinClass(CommonToolArguments.class), args);
    }

    public static final <T extends CommonToolArguments> T parseCommandLineArguments(KClass<T> clazz, List<String> args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Constructor<?> constructor;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(args, "args");
        Constructor<?>[] constructors = JvmClassMappingKt.getJavaClass((KClass) clazz).getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        Constructor<?>[] constructorArr = constructors;
        int length = constructorArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                constructor = null;
                break;
            }
            constructor = constructorArr[i];
            Parameter[] parameters = constructor.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            if (parameters.length == 0) {
                break;
            }
            i++;
        }
        Constructor<?> constructor2 = constructor;
        if (constructor2 == null) {
            throw new IllegalStateException(("Missing empty constructor on '" + JvmClassMappingKt.getJavaClass((KClass) clazz).getName()).toString());
        }
        T t = (T) KClasses.cast(clazz, constructor2.newInstance(new Object[0]));
        parseCommandLineArguments$default(args, t, false, 4, null);
        return t;
    }

    public static /* synthetic */ void parseCommandLineArguments$default(List list, CommonToolArguments commonToolArguments, boolean z, int i, Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if ((i & 4) != 0) {
            z = false;
        }
        parseCommandLineArguments(list, commonToolArguments, z);
    }

    public static final <A extends CommonToolArguments> void parseCommandLineArguments(List<String> args, final A result, boolean z) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(result, "result");
        Lazy lazy = LazyKt.lazy(new Function0<ArgumentParseErrors>() { // from class: org.jetbrains.kotlin.cli.common.arguments.ParseCommandLineArgumentsKt$parseCommandLineArguments$errors$1
            /* JADX WARN: Incorrect types in method signature: (TA;)V */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ArgumentParseErrors invoke() {
                ArgumentParseErrors errors = result.getErrors();
                if (errors != null) {
                    return errors;
                }
                ArgumentParseErrors argumentParseErrors = new ArgumentParseErrors(null, null, null, null, null, null, null, null, null, 511, null);
                result.setErrors(argumentParseErrors);
                return argumentParseErrors;
            }
        });
        parsePreprocessedCommandLineArguments(PreprocessCommandLineArgumentsKt.preprocessCommandLineArguments(args, lazy), result, lazy, z);
    }

    public static final <A extends CommonToolArguments> void parseCommandLineArgumentsFromEnvironment(A arguments) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        String value = CompilerSystemProperties.LANGUAGE_VERSION_SETTINGS.getValue();
        if (value != null) {
            if (value.length() <= 0) {
                value = null;
            }
            if (value != null) {
                List<String> listSplit = new Regex("\\s").split(value, 0);
                if (listSplit != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : listSplit) {
                        if (!StringsKt.isBlank((String) obj)) {
                            arrayList.add(obj);
                        }
                    }
                    parseCommandLineArguments(arrayList, arguments, true);
                }
            }
        }
    }

    private static final Map<String, ArgumentField> getArguments(Class<?> cls) throws NoSuchMethodException {
        ConcurrentHashMap<Class<?>, Map<String, ArgumentField>> concurrentHashMap = argumentsCache;
        Map<String, ArgumentField> mapBuild = concurrentHashMap.get(cls);
        if (mapBuild == null) {
            if (Intrinsics.areEqual(cls, Object.class)) {
                mapBuild = MapsKt.emptyMap();
            } else {
                Map mapCreateMapBuilder = MapsKt.createMapBuilder();
                Class<? super Object> superclass = cls.getSuperclass();
                Intrinsics.checkNotNullExpressionValue(superclass, "getSuperclass(...)");
                mapCreateMapBuilder.putAll(getArguments(superclass));
                Field[] declaredFields = cls.getDeclaredFields();
                Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
                for (Field field : declaredFields) {
                    Argument argument = (Argument) field.getAnnotation(Argument.class);
                    if (argument != null) {
                        Intrinsics.checkNotNull(argument);
                        String name2 = field.getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        Method method = cls.getMethod(JvmAbi.getterName(name2), new Class[0]);
                        String name3 = field.getName();
                        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                        Method method2 = cls.getMethod(JvmAbi.setterName(name3), field.getType());
                        Intrinsics.checkNotNull(method);
                        Intrinsics.checkNotNull(method2);
                        ArgumentField argumentField = new ArgumentField(method, method2, argument);
                        for (String str : CollectionsKt.listOf((Object[]) new String[]{argument.value(), argument.shortName(), argument.deprecatedName()})) {
                            if (str.length() > 0) {
                                mapCreateMapBuilder.put(str, argumentField);
                            }
                        }
                    }
                }
                mapBuild = MapsKt.build(mapCreateMapBuilder);
            }
            Map<String, ArgumentField> mapPutIfAbsent = concurrentHashMap.putIfAbsent(cls, mapBuild);
            if (mapPutIfAbsent != null) {
                mapBuild = mapPutIfAbsent;
            }
        }
        Intrinsics.checkNotNullExpressionValue(mapBuild, "getOrPut(...)");
        return mapBuild;
    }

    /* JADX WARN: Code duplicated, block: B:62:0x01d3  */
    /* JADX WARN: Code duplicated, block: B:84:0x028b  */
    /* JADX WARN: Multi-variable type inference failed */
    private static final <A extends CommonToolArguments> void parsePreprocessedCommandLineArguments(List<String> list, A a, Lazy<ArgumentParseErrors> lazy, boolean z) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int i;
        Object obj;
        Object objSubstring;
        boolean z2;
        boolean z3;
        Map<String, ArgumentField> arguments = getArguments(a.getClass());
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean z4 = false;
        int i2 = 0;
        boolean z5 = false;
        while (i2 < list.size()) {
            int i3 = i2 + 1;
            String str = list.get(i2);
            if (z5) {
                arrayList.add(str);
            } else if (Intrinsics.areEqual(str, "--")) {
                i2 = i3;
                z5 = true;
            } else if (StringsKt.startsWith$default(str, "-XX", z4, 2, (Object) null)) {
                List<InternalArgumentParser<?>> pARSERS$cli_common = InternalArgumentParser.INSTANCE.getPARSERS$cli_common();
                ArrayList arrayList3 = new ArrayList();
                for (Object obj2 : pARSERS$cli_common) {
                    if (((InternalArgumentParser) obj2).canParse(str)) {
                        arrayList3.add(obj2);
                    }
                }
                ArrayList arrayList4 = arrayList3;
                arrayList4.size();
                InternalArgumentParser internalArgumentParser = (InternalArgumentParser) CollectionsKt.firstOrNull((List) arrayList4);
                if (internalArgumentParser == null) {
                    lazy.getValue().getUnknownExtraFlags().add(str);
                } else {
                    final InternalArgument internalArgument = internalArgumentParser.parseInternalArgument(str, lazy.getValue());
                    if (internalArgument != null) {
                        final Function1<InternalArgument, Boolean> function1 = new Function1<InternalArgument, Boolean>() { // from class: org.jetbrains.kotlin.cli.common.arguments.ParseCommandLineArgumentsKt.parsePreprocessedCommandLineArguments.2
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(InternalArgument it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                ManualLanguageFeatureSetting manualLanguageFeatureSetting = it instanceof ManualLanguageFeatureSetting ? (ManualLanguageFeatureSetting) it : null;
                                LanguageFeature languageFeature = manualLanguageFeatureSetting != null ? manualLanguageFeatureSetting.getLanguageFeature() : null;
                                InternalArgument internalArgument2 = internalArgument;
                                ManualLanguageFeatureSetting manualLanguageFeatureSetting2 = internalArgument2 instanceof ManualLanguageFeatureSetting ? (ManualLanguageFeatureSetting) internalArgument2 : null;
                                return Boolean.valueOf(languageFeature == (manualLanguageFeatureSetting2 != null ? manualLanguageFeatureSetting2.getLanguageFeature() : null));
                            }
                        };
                        arrayList2.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.cli.common.arguments.ParseCommandLineArgumentsKt$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj3) {
                                return ParseCommandLineArgumentsKt.parsePreprocessedCommandLineArguments$lambda$8(function1, obj3);
                            }
                        });
                        arrayList2.add(internalArgument);
                    }
                }
            } else {
                String strSubstringBefore$default = StringsKt.substringBefore$default(str, '=', (String) null, 2, (Object) null);
                ArgumentField argumentField = arguments.get(strSubstringBefore$default);
                if (argumentField == null) {
                    if (StringsKt.startsWith$default(str, "-X", z4, 2, (Object) null)) {
                        lazy.getValue().getUnknownExtraFlags().add(str);
                    } else if (StringsKt.startsWith$default(str, Parameters.DEFAULT_OPTION_PREFIXES, z4, 2, (Object) null)) {
                        lazy.getValue().getUnknownArgs().add(str);
                    } else {
                        arrayList.add(str);
                    }
                } else {
                    Method getter = argumentField.getGetter();
                    Method setter = argumentField.getSetter();
                    Argument argument = argumentField.getArgument();
                    if (!Intrinsics.areEqual(strSubstringBefore$default, str) && Intrinsics.areEqual(strSubstringBefore$default, argument.shortName())) {
                        lazy.getValue().getUnknownArgs().add(str);
                    } else {
                        String strDeprecatedName = argument.deprecatedName();
                        if (Intrinsics.areEqual(strDeprecatedName, strSubstringBefore$default)) {
                            lazy.getValue().getDeprecatedArguments().put(strDeprecatedName, argument.value());
                        }
                        if (Intrinsics.areEqual(argument.value(), str) && isAdvanced(argument)) {
                            Class<?> returnType = getter.getReturnType();
                            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
                            if (!Intrinsics.areEqual(JvmClassMappingKt.getKotlinClass(returnType), Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                lazy.getValue().getExtraArgumentsPassedInObsoleteForm().add(str);
                            }
                        }
                        Class<?> returnType2 = getter.getReturnType();
                        Intrinsics.checkNotNullExpressionValue(returnType2, "getReturnType(...)");
                        if (Intrinsics.areEqual(JvmClassMappingKt.getKotlinClass(returnType2), Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                            if (StringsKt.startsWith$default(str, argument.value() + '=', false, 2, (Object) null)) {
                                String strSubstring = str.substring(argument.value().length() + 1);
                                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                                if (Intrinsics.areEqual(strSubstring, "true")) {
                                    z3 = true;
                                } else if (Intrinsics.areEqual(strSubstring, "false")) {
                                    z3 = false;
                                } else {
                                    lazy.getValue().setBooleanArgumentWithValue(str);
                                    z3 = true;
                                }
                            } else {
                                z3 = true;
                            }
                            objSubstring = Boolean.valueOf(z3);
                        } else if (StringsKt.startsWith$default(str, argument.value() + '=', false, 2, (Object) null)) {
                            objSubstring = str.substring(argument.value().length() + 1);
                            Intrinsics.checkNotNullExpressionValue(objSubstring, "substring(...)");
                        } else {
                            if (StringsKt.startsWith$default(str, argument.deprecatedName() + '=', false, 2, (Object) null)) {
                                objSubstring = str.substring(argument.deprecatedName().length() + 1);
                                Intrinsics.checkNotNullExpressionValue(objSubstring, "substring(...)");
                            } else if (i3 == list.size()) {
                                lazy.getValue().setArgumentWithoutValue(str);
                                break;
                            } else {
                                i = i2 + 2;
                                obj = list.get(i3);
                            }
                            if (getter.getReturnType().isArray() && !linkedHashSet.add(argument.value()) && (obj instanceof String)) {
                                z2 = false;
                                if (!Intrinsics.areEqual(getter.invoke(a, new Object[0]), obj)) {
                                    lazy.getValue().getDuplicateArguments().put(argument.value(), obj);
                                }
                            } else {
                                z2 = false;
                            }
                            updateField(getter, setter, a, obj, getResolvedDelimiter(argument), z);
                            i2 = i;
                            z4 = z2;
                        }
                        obj = objSubstring;
                        i = i3;
                        if (getter.getReturnType().isArray()) {
                            z2 = false;
                        } else {
                            z2 = false;
                        }
                        updateField(getter, setter, a, obj, getResolvedDelimiter(argument), z);
                        i2 = i;
                        z4 = z2;
                    }
                }
            }
            i2 = i3;
        }
        a.setFreeArgs(CollectionsKt.plus((Collection) a.getFreeArgs(), (Iterable) arrayList));
        updateInternalArguments(a, arrayList2, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean parsePreprocessedCommandLineArguments$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private static final <A extends CommonToolArguments> void updateInternalArguments(A a, ArrayList<InternalArgument> arrayList, boolean z) {
        ArrayList internalArguments;
        if (z) {
            List<InternalArgument> internalArguments2 = a.getInternalArguments();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : internalArguments2) {
                InternalArgument internalArgument = (InternalArgument) obj;
                if (internalArgument instanceof ManualLanguageFeatureSetting) {
                    ArrayList<InternalArgument> arrayList3 = arrayList;
                    if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                        Iterator<T> it = arrayList3.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                InternalArgument internalArgument2 = (InternalArgument) it.next();
                                if ((internalArgument2 instanceof ManualLanguageFeatureSetting) && ((ManualLanguageFeatureSetting) internalArgument2).getLanguageFeature() == ((ManualLanguageFeatureSetting) internalArgument).getLanguageFeature()) {
                                    break;
                                }
                            }
                        }
                    }
                }
                arrayList2.add(obj);
            }
            internalArguments = arrayList2;
        } else {
            internalArguments = a.getInternalArguments();
        }
        a.setInternalArguments(CollectionsKt.plus((Collection) internalArguments, (Iterable) arrayList));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <A extends CommonToolArguments> void updateField(Method method, Method method2, A a, Object obj, String str, boolean z) throws IllegalAccessException, InvocationTargetException {
        Object obj2;
        Class<?> returnType = method.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(returnType);
        if (Intrinsics.areEqual(kotlinClass, Reflection.getOrCreateKotlinClass(Boolean.TYPE)) ? true : Intrinsics.areEqual(kotlinClass, Reflection.getOrCreateKotlinClass(String.class))) {
            method2.invoke(a, obj);
            return;
        }
        if (Intrinsics.areEqual(kotlinClass, Reflection.getOrCreateKotlinClass(String[].class))) {
            String str2 = str;
            if (str2 == null || str2.length() == 0) {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                obj2 = new String[]{obj};
            } else {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                obj2 = (String[]) StringsKt.split$default((CharSequence) obj, new String[]{str}, false, 0, 6, (Object) null).toArray(new String[0]);
            }
            Object obj3 = (String[]) method.invoke(a, new Object[0]);
            Object obj4 = obj2;
            obj4 = obj2;
            if (obj3 != null && !z) {
                SpreadBuilder spreadBuilder = new SpreadBuilder(2);
                spreadBuilder.addSpread(obj3);
                spreadBuilder.addSpread(obj2);
                obj4 = (String[]) spreadBuilder.toArray(new String[spreadBuilder.size()]);
            }
            method2.invoke(a, obj4);
            return;
        }
        throw new IllegalStateException("Unsupported argument type: " + method.getReturnType());
    }

    public static final String validateArguments(ArgumentParseErrors argumentParseErrors) {
        if (argumentParseErrors == null) {
            return null;
        }
        if (argumentParseErrors.getArgumentWithoutValue() != null) {
            return "No value passed for argument " + argumentParseErrors.getArgumentWithoutValue();
        }
        String booleanArgumentWithValue = argumentParseErrors.getBooleanArgumentWithValue();
        if (booleanArgumentWithValue != null) {
            return "No value expected for boolean argument " + StringsKt.substringBefore$default(booleanArgumentWithValue, '=', (String) null, 2, (Object) null) + ". Please remove the value: " + booleanArgumentWithValue;
        }
        if (argumentParseErrors.getUnknownArgs().isEmpty()) {
            return null;
        }
        return "Invalid argument: " + ((String) CollectionsKt.first((List) argumentParseErrors.getUnknownArgs()));
    }
}
