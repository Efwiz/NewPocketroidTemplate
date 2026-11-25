package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.patterns.DescriptorPredicate;
import org.jetbrains.kotlin.js.patterns.NamePredicate;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.BuiltInPropertyIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ArrayFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000y\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001 \bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0012\u0010\u0018\u001a\u00020\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\nH\u0002J\u0018\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0011H\u0002J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ`\u0010\u001f\u001a\u00020 2Q\u0010!\u001aM\u0012\u0013\u0012\u00110#¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00110'¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u00110\"H\u0002¢\u0006\u0002\u0010*J\u0010\u0010+\u001a\u00020,2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010-\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0014\u001a\u00020\u0015R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\f0\f0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\r\u001a\u00020\f*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/ArrayFIF;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/CompositeFIF;", "()V", "GET_INTRINSIC", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "LENGTH_PROPERTY_INTRINSIC", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/BuiltInPropertyIntrinsic;", "SET_INTRINSIC", "TYPED_ARRAY_MAP", "Ljava/util/EnumMap;", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "lowerCaseName", "getLowerCaseName", "(Lorg/jetbrains/kotlin/builtins/PrimitiveType;)Ljava/lang/String;", "castOrCreatePrimitiveArray", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "ctx", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayLiteral;", "createConstructorIntrinsic", "createTypedArray", "getTag", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "intrinsify", "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/ArrayFIF$intrinsify$1", "f", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "Lkotlin/ParameterName;", "name", "callInfo", "", "arguments", "context", "(Lkotlin/jvm/functions/Function3;)Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/ArrayFIF$intrinsify$1;", "typedArraysEnabled", "", "unsignedPrimitiveToSigned", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayFIF extends CompositeFIF {
    public static final FunctionIntrinsic GET_INTRINSIC;
    public static final ArrayFIF INSTANCE;
    public static final BuiltInPropertyIntrinsic LENGTH_PROPERTY_INTRINSIC;
    public static final FunctionIntrinsic SET_INTRINSIC;
    private static final EnumMap<PrimitiveType, String> TYPED_ARRAY_MAP;

    private ArrayFIF() {
    }

    static {
        ArrayFIF arrayFIF = new ArrayFIF();
        INSTANCE = arrayFIF;
        GET_INTRINSIC = arrayFIF.intrinsify(new Function3<CallInfo, List<? extends JsExpression>, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF$GET_INTRINSIC$1
            @Override // kotlin.jvm.functions.Function3
            public final JsExpression invoke(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext translationContext) {
                Intrinsics.checkNotNullParameter(callInfo, "callInfo");
                Intrinsics.checkNotNullParameter(arguments, "arguments");
                Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 2>");
                arguments.size();
                return new JsArrayAccess(callInfo.getDispatchReceiver(), arguments.get(0));
            }
        });
        SET_INTRINSIC = arrayFIF.intrinsify(new Function3<CallInfo, List<? extends JsExpression>, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF$SET_INTRINSIC$1
            @Override // kotlin.jvm.functions.Function3
            public final JsExpression invoke(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext translationContext) {
                Intrinsics.checkNotNullParameter(callInfo, "callInfo");
                Intrinsics.checkNotNullParameter(arguments, "arguments");
                Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 2>");
                arguments.size();
                JsExpression jsExpression = arguments.get(0);
                JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(new JsArrayAccess(callInfo.getDispatchReceiver(), jsExpression), arguments.get(1));
                Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAssignment, "assignment(...)");
                return jsBinaryOperationAssignment;
            }
        });
        LENGTH_PROPERTY_INTRINSIC = new BuiltInPropertyIntrinsic("length");
        TYPED_ARRAY_MAP = new EnumMap<>(MapsKt.mapOf(TuplesKt.to(PrimitiveType.BYTE, "Int8"), TuplesKt.to(PrimitiveType.SHORT, "Int16"), TuplesKt.to(PrimitiveType.INT, "Int32"), TuplesKt.to(PrimitiveType.FLOAT, "Float32"), TuplesKt.to(PrimitiveType.DOUBLE, "Float64")));
        Name nameShortName = StandardNames.FqNames.array.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        List listMutableListOf = CollectionsKt.mutableListOf(nameShortName);
        for (PrimitiveType primitiveType : PrimitiveType.values()) {
            listMutableListOf.add(primitiveType.getArrayTypeName());
        }
        NamePredicate namePredicate = new NamePredicate((Collection<Name>) listMutableListOf);
        ArrayFIF arrayFIF2 = INSTANCE;
        arrayFIF2.add(PatternBuilder.pattern(namePredicate, "get"), GET_INTRINSIC);
        arrayFIF2.add(PatternBuilder.pattern(namePredicate, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX), SET_INTRINSIC);
        arrayFIF2.add(PatternBuilder.pattern(namePredicate, "<get-size>"), LENGTH_PROPERTY_INTRINSIC);
        for (final PrimitiveType primitiveType2 : PrimitiveType.values()) {
            ArrayFIF arrayFIF3 = INSTANCE;
            arrayFIF3.add(PatternBuilder.pattern(new NamePredicate(primitiveType2.getArrayTypeName()), "<init>(Int)"), arrayFIF3.intrinsify(new Function3<CallInfo, List<? extends JsExpression>, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF.2

                /* JADX INFO: renamed from: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF$2$WhenMappings */
                /* JADX INFO: compiled from: ArrayFIF.kt */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                public /* synthetic */ class WhenMappings {
                    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                    static {
                        int[] iArr = new int[PrimitiveType.values().length];
                        try {
                            iArr[PrimitiveType.BOOLEAN.ordinal()] = 1;
                        } catch (NoSuchFieldError unused) {
                        }
                        try {
                            iArr[PrimitiveType.LONG.ordinal()] = 2;
                        } catch (NoSuchFieldError unused2) {
                        }
                        $EnumSwitchMapping$0 = iArr;
                    }
                }

                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final JsExpression invoke(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
                    JsBooleanLiteral jsBooleanLiteral;
                    Intrinsics.checkNotNullParameter(callInfo, "<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter(arguments, "arguments");
                    Intrinsics.checkNotNullParameter(context, "context");
                    arguments.size();
                    JsExpression jsExpression = arguments.get(0);
                    JsConfig config = context.getConfig();
                    Intrinsics.checkNotNullExpressionValue(config, "getConfig(...)");
                    if (ArrayFIF.typedArraysEnabled(config)) {
                        if (ArrayFIF.TYPED_ARRAY_MAP.containsKey(primitiveType2)) {
                            return ArrayFIF.INSTANCE.createTypedArray(primitiveType2, jsExpression);
                        }
                        JsInvocation jsInvocationInvokeKotlinFunction = JsAstUtils.invokeKotlinFunction(ArrayFIF.INSTANCE.getLowerCaseName(primitiveType2) + "Array", jsExpression);
                        Intrinsics.checkNotNull(jsInvocationInvokeKotlinFunction);
                        return jsInvocationInvokeKotlinFunction;
                    }
                    int i = WhenMappings.$EnumSwitchMapping$0[primitiveType2.ordinal()];
                    if (i == 1) {
                        jsBooleanLiteral = new JsBooleanLiteral(false);
                    } else if (i == 2) {
                        jsBooleanLiteral = new JsNameRef(Namer.LONG_ZERO, Namer.kotlinLong());
                    } else {
                        jsBooleanLiteral = new JsIntLiteral(0);
                    }
                    JsInvocation jsInvocationInvokeKotlinFunction2 = JsAstUtils.invokeKotlinFunction("newArray", jsExpression, jsBooleanLiteral);
                    Intrinsics.checkNotNull(jsInvocationInvokeKotlinFunction2);
                    return jsInvocationInvokeKotlinFunction2;
                }
            }));
            arrayFIF3.add(PatternBuilder.pattern(new NamePredicate(primitiveType2.getArrayTypeName()), "<init>(Int,Function1)"), arrayFIF3.createConstructorIntrinsic(primitiveType2));
            arrayFIF3.add(PatternBuilder.pattern(new NamePredicate(primitiveType2.getArrayTypeName()), "iterator"), arrayFIF3.intrinsify(new Function3<CallInfo, List<? extends JsExpression>, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF.3
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final JsExpression invoke(CallInfo callInfo, List<? extends JsExpression> list, TranslationContext context) {
                    Intrinsics.checkNotNullParameter(callInfo, "callInfo");
                    Intrinsics.checkNotNullParameter(list, "<anonymous parameter 1>");
                    Intrinsics.checkNotNullParameter(context, "context");
                    JsExpression dispatchReceiver = callInfo.getDispatchReceiver();
                    JsConfig config = context.getConfig();
                    Intrinsics.checkNotNullExpressionValue(config, "getConfig(...)");
                    if (ArrayFIF.typedArraysEnabled(config)) {
                        String str = ArrayFIF.INSTANCE.getLowerCaseName(primitiveType2) + "ArrayIterator";
                        Intrinsics.checkNotNull(dispatchReceiver);
                        JsInvocation jsInvocationInvokeKotlinFunction = JsAstUtils.invokeKotlinFunction(str, dispatchReceiver);
                        Intrinsics.checkNotNull(jsInvocationInvokeKotlinFunction);
                        return jsInvocationInvokeKotlinFunction;
                    }
                    Intrinsics.checkNotNull(dispatchReceiver);
                    JsInvocation jsInvocationInvokeKotlinFunction2 = JsAstUtils.invokeKotlinFunction("arrayIterator", dispatchReceiver, new JsStringLiteral(primitiveType2.getArrayTypeName().asString()));
                    Intrinsics.checkNotNull(jsInvocationInvokeKotlinFunction2);
                    return jsInvocationInvokeKotlinFunction2;
                }
            }));
        }
        ArrayFIF arrayFIF4 = INSTANCE;
        arrayFIF4.add(PatternBuilder.pattern(new NamePredicate(nameShortName), "<init>(Int,Function1)"), arrayFIF4.createConstructorIntrinsic(null));
        arrayFIF4.add(PatternBuilder.pattern(new NamePredicate(nameShortName), "iterator"), new KotlinFunctionIntrinsic("arrayIterator", new JsExpression[0]));
        arrayFIF4.add(PatternBuilder.pattern(Namer.KOTLIN_LOWER_NAME, "arrayOfNulls"), new KotlinFunctionIntrinsic("newArray", new JsNullLiteral()));
        List list = listMutableListOf;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Name.identifier(StringUtil.decapitalize(((Name) it.next()).asString() + "Of")));
        }
        DescriptorPredicate descriptorPredicatePattern = PatternBuilder.pattern(Namer.KOTLIN_LOWER_NAME, new NamePredicate((Collection<Name>) arrayList));
        Intrinsics.checkNotNullExpressionValue(descriptorPredicatePattern, "pattern(...)");
        ArrayFIF arrayFIF5 = INSTANCE;
        arrayFIF5.add(descriptorPredicatePattern, arrayFIF5.intrinsify(new Function3<CallInfo, List<? extends JsExpression>, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF.4
            @Override // kotlin.jvm.functions.Function3
            public final JsExpression invoke(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext translationContext) {
                Intrinsics.checkNotNullParameter(callInfo, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(arguments, "arguments");
                Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 2>");
                return arguments.get(0);
            }
        }));
    }

    @JvmStatic
    public static final boolean typedArraysEnabled(JsConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        Object obj = config.getConfiguration().get(JSConfigurationKeys.TYPED_ARRAYS_ENABLED, true);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        return ((Boolean) obj).booleanValue();
    }

    public final PrimitiveType unsignedPrimitiveToSigned(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!InlineClassesUtilsKt.isInlineClassType(type) || type.getIsMarkedNullable()) {
            return null;
        }
        if (KotlinBuiltIns.isUByte(type)) {
            return PrimitiveType.BYTE;
        }
        if (KotlinBuiltIns.isUShort(type)) {
            return PrimitiveType.SHORT;
        }
        if (KotlinBuiltIns.isUInt(type)) {
            return PrimitiveType.INT;
        }
        if (KotlinBuiltIns.isULong(type)) {
            return PrimitiveType.LONG;
        }
        return null;
    }

    public final JsExpression castOrCreatePrimitiveArray(TranslationContext ctx, KotlinType type, JsArrayLiteral arg) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(arg, "arg");
        if (type.getIsMarkedNullable()) {
            return arg;
        }
        PrimitiveType primitiveTypeUnsignedPrimitiveToSigned = unsignedPrimitiveToSigned(type);
        if (primitiveTypeUnsignedPrimitiveToSigned != null) {
            final String str = "to" + primitiveTypeUnsignedPrimitiveToSigned.getTypeName();
            arg.getExpressions().replaceAll(new UnaryOperator() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ArrayFIF.castOrCreatePrimitiveArray$lambda$0(str, (JsExpression) obj);
                }
            });
        }
        if (primitiveTypeUnsignedPrimitiveToSigned == null && ((primitiveTypeUnsignedPrimitiveToSigned = KotlinBuiltIns.getPrimitiveType(type)) == null || type.getIsMarkedNullable())) {
            primitiveTypeUnsignedPrimitiveToSigned = null;
        }
        if (primitiveTypeUnsignedPrimitiveToSigned != null) {
            JsConfig config = ctx.getConfig();
            Intrinsics.checkNotNullExpressionValue(config, "getConfig(...)");
            if (typedArraysEnabled(config)) {
                if (TYPED_ARRAY_MAP.containsKey(primitiveTypeUnsignedPrimitiveToSigned)) {
                    return createTypedArray(primitiveTypeUnsignedPrimitiveToSigned, arg);
                }
                String str2 = getLowerCaseName(primitiveTypeUnsignedPrimitiveToSigned) + "ArrayOf";
                List<JsExpression> expressions = arg.getExpressions();
                Intrinsics.checkNotNullExpressionValue(expressions, "getExpressions(...)");
                JsExpression[] jsExpressionArr = (JsExpression[]) expressions.toArray(new JsExpression[0]);
                JsInvocation jsInvocationInvokeKotlinFunction = JsAstUtils.invokeKotlinFunction(str2, (JsExpression[]) Arrays.copyOf(jsExpressionArr, jsExpressionArr.length));
                Intrinsics.checkNotNull(jsInvocationInvokeKotlinFunction);
                return jsInvocationInvokeKotlinFunction;
            }
        }
        return arg;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsExpression castOrCreatePrimitiveArray$lambda$0(String conversionFunction, JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(conversionFunction, "$conversionFunction");
        return new JsInvocation(new JsNameRef(conversionFunction, jsExpression), new JsExpression[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsExpression createTypedArray(PrimitiveType type, JsExpression arg) {
        EnumMap<PrimitiveType, String> enumMap = TYPED_ARRAY_MAP;
        enumMap.containsKey(type);
        return new JsNew(new JsNameRef(enumMap.get(type) + "Array"), CollectionsKt.listOf(arg));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getLowerCaseName(PrimitiveType primitiveType) {
        String strAsString = primitiveType.getTypeName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String lowerCase = strAsString.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return lowerCase;
    }

    public final String getTag(CallableDescriptor descriptor, JsConfig config) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(config, "config");
        if (!(descriptor instanceof ConstructorDescriptor)) {
            return null;
        }
        ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) descriptor;
        ClassDescriptor constructedClass = constructorDescriptor.getConstructedClass();
        Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
        if (!KotlinBuiltIns.isArrayOrPrimitiveArray(constructedClass) || constructorDescriptor.getValueParameters().size() != 2) {
            return null;
        }
        List<ValueParameterDescriptor> valueParameters = constructorDescriptor.getValueParameters();
        Intrinsics.checkNotNull(valueParameters);
        ValueParameterDescriptor valueParameterDescriptor = valueParameters.get(0);
        ValueParameterDescriptor valueParameterDescriptor2 = valueParameters.get(1);
        if (KotlinBuiltIns.isInt(valueParameterDescriptor.getType())) {
            KotlinType type = valueParameterDescriptor2.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (FunctionTypesKt.isBuiltinFunctionalType(type)) {
                KotlinType type2 = valueParameterDescriptor2.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (FunctionTypesKt.getValueParameterTypesFromFunctionType(type2).size() != 1) {
                    return null;
                }
                PrimitiveType primitiveArrayElementType = KotlinBuiltIns.getPrimitiveArrayElementType(constructedClass.getDefaultType());
                if (typedArraysEnabled(config) && primitiveArrayElementType != null) {
                    if (TYPED_ARRAY_MAP.containsKey(primitiveArrayElementType)) {
                        return "kotlin.fillArray";
                    }
                    return "kotlin." + getLowerCaseName(primitiveArrayElementType) + "ArrayF";
                }
                if (primitiveArrayElementType == PrimitiveType.CHAR) {
                    return "kotlin.untypedCharArrayF";
                }
                return "kotlin.newArrayF";
            }
        }
        return null;
    }

    private final FunctionIntrinsic createConstructorIntrinsic(final PrimitiveType type) {
        return intrinsify(new Function3<CallInfo, List<? extends JsExpression>, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF.createConstructorIntrinsic.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final JsExpression invoke(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
                JsInvocation jsInvocationInvokeKotlinFunction;
                Intrinsics.checkNotNullParameter(callInfo, "callInfo");
                Intrinsics.checkNotNullParameter(arguments, "arguments");
                Intrinsics.checkNotNullParameter(context, "context");
                arguments.size();
                JsExpression jsExpression = arguments.get(0);
                JsExpression jsExpression2 = arguments.get(1);
                JsConfig config = context.getConfig();
                Intrinsics.checkNotNullExpressionValue(config, "getConfig(...)");
                if (ArrayFIF.typedArraysEnabled(config) && type != null) {
                    if (!ArrayFIF.TYPED_ARRAY_MAP.containsKey(type)) {
                        jsInvocationInvokeKotlinFunction = JsAstUtils.invokeKotlinFunction(ArrayFIF.INSTANCE.getLowerCaseName(type) + "ArrayF", jsExpression, jsExpression2);
                    } else {
                        jsInvocationInvokeKotlinFunction = JsAstUtils.invokeKotlinFunction("fillArray", ArrayFIF.INSTANCE.createTypedArray(type, jsExpression), jsExpression2);
                    }
                } else {
                    jsInvocationInvokeKotlinFunction = JsAstUtils.invokeKotlinFunction(type == PrimitiveType.CHAR ? "untypedCharArrayF" : "newArrayF", jsExpression, jsExpression2);
                }
                Intrinsics.checkNotNull(jsInvocationInvokeKotlinFunction);
                MetadataProperties.setInline(jsInvocationInvokeKotlinFunction, (Boolean) true);
                ClassConstructorDescriptor original = callInfo.getResolvedCall().getResultingDescriptor().getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                if (original instanceof TypeAliasConstructorDescriptor) {
                    original = ((TypeAliasConstructorDescriptor) original).getUnderlyingConstructorDescriptor();
                }
                MetadataProperties.setDescriptor(jsInvocationInvokeKotlinFunction, original);
                context.addInlineCall(original);
                return jsInvocationInvokeKotlinFunction;
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF$intrinsify$1] */
    private final C47841 intrinsify(final Function3<? super CallInfo, ? super List<? extends JsExpression>, ? super TranslationContext, ? extends JsExpression> f) {
        return new FunctionIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF.intrinsify.1
            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
            public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
                Intrinsics.checkNotNullParameter(callInfo, "callInfo");
                Intrinsics.checkNotNullParameter(arguments, "arguments");
                Intrinsics.checkNotNullParameter(context, "context");
                return f.invoke(callInfo, arguments, context);
            }
        };
    }
}
