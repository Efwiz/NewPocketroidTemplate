package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: BoxingInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000^\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u0014\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u001a\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0001H\u0002\u001a\u001c\u0010\u0013\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0014\u001a\u00020\u0015\u001a\u0010\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0001H\u0002\u001a\u0010\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\nH\u0002\u001a\u0010\u0010\u001a\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0001H\u0002\u001a\u0010\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0001H\u0002\u001a\u0010\u0010\u001c\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0001H\u0002\u001a\n\u0010\u001d\u001a\u00020\f*\u00020\u001e\u001a\u0018\u0010\u001f\u001a\u00020\f*\u00020\u001e2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u001a\u0012\u0010 \u001a\u00020\f*\u00020\u001e2\u0006\u0010!\u001a\u00020\u0015\u001a\f\u0010\"\u001a\u00020\f*\u00020#H\u0002\u001a\n\u0010$\u001a\u00020\f*\u00020\u001e\u001a\u0014\u0010%\u001a\u00020\f*\u00020\u001e2\u0006\u0010!\u001a\u00020\u0015H\u0002\u001a\u0014\u0010&\u001a\u00020\f*\u00020#2\u0006\u0010!\u001a\u00020\u0015H\u0002\u001a\u0014\u0010'\u001a\u00020\f*\u00020\u001e2\u0006\u0010!\u001a\u00020\u0015H\u0002\u001a\u0014\u0010(\u001a\u00020\f*\u00020#2\u0006\u0010!\u001a\u00020\u0015H\u0002\u001a\n\u0010)\u001a\u00020\f*\u00020\u001e\u001a\u0018\u0010*\u001a\u00020\f*\u00020\u001e2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u001a\n\u0010+\u001a\u00020\f*\u00020\u001e\u001a\n\u0010,\u001a\u00020\f*\u00020\u001e\u001a\n\u0010-\u001a\u00020\f*\u00020\u001e\u001a\u0018\u0010.\u001a\u00020\f*\u00020\u001e2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u001a1\u0010/\u001a\u00020\f*\u00020\u001e2\u0006\u00100\u001a\u0002012\u0017\u00102\u001a\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\f03¢\u0006\u0002\b4H\u0086\bø\u0001\u0000\u001a\u0014\u00105\u001a\u00020\f*\u00020\u001e2\u0006\u0010!\u001a\u00020\u0015H\u0002\u001a\u0014\u00106\u001a\u00020\f*\u00020#2\u0006\u0010!\u001a\u00020\u0015H\u0002\u001a\u0014\u00107\u001a\u00020\f*\u00020\u001e2\u0006\u0010!\u001a\u00020\u0015H\u0002\u001a\u0014\u00108\u001a\u00020\f*\u00020#2\u0006\u0010!\u001a\u00020\u0015H\u0002\u001a\u0018\u00109\u001a\u00020\f*\u00020\u001e2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u001a\n\u0010:\u001a\u00020\f*\u00020\u001e\u001a\n\u0010;\u001a\u00020\f*\u00020\u001e\u001a\u0012\u0010<\u001a\u00020\f*\u00020\u001e2\u0006\u0010!\u001a\u00020\u0015\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0016\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0016\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\"2\u0010\u0007\u001a&\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00010\u0001 \u0003*\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00010\u0001\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000\"\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\n0\n0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006="}, d2 = {"BOXING_CLASS_INTERNAL_NAME", "", "JLCLASS_TO_KCLASS", JvmProtoBufUtil.PLATFORM_TYPE_ID, "KCLASS_TO_JLCLASS", "PROGRESSION_CLASS_FQNS", "", "UNBOXING_METHOD_NAMES", "Lorg/jetbrains/kotlin/com/google/common/collect/ImmutableSet;", "shouldUseEqualsForWrappers", "Lorg/jetbrains/org/objectweb/asm/Type;", "areSameTypedPrimitiveBoxedValues", "", CompoundEntry.NAME_values, "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "buildFqNameByInternal", "Lorg/jetbrains/kotlin/name/FqName;", "internalClassName", "canValuesBeUnboxedForAreEqual", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "isJvmPrimitiveName", "name", "isProgressionClass", ModuleXmlParser.TYPE, "isUnboxingMethodName", "isWrapperClassName", "isWrapperClassNameOrNumber", "isAreEqualIntrinsic", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isAreEqualIntrinsicForSameTypedBoxedValues", "isBoxing", "state", "isBoxingMethodDescriptor", "Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "isCoroutinePrimitiveBoxing", "isInlineClassBoxing", "isInlineClassBoxingMethodDescriptor", "isInlineClassUnboxing", "isInlineClassUnboxingMethodDescriptor", "isIteratorMethodCall", "isIteratorMethodCallOfProgression", "isJavaLangClassBoxing", "isJavaLangClassUnboxing", "isJavaLangComparableCompareTo", "isJavaLangComparableCompareToForSameTypedBoxedValues", "isMethodInsnWith", "opcode", "", "condition", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "isMultiFieldValueClassBoxing", "isMultiFieldValueClassBoxingMethodDescriptor", "isMultiFieldValueClassUnboxing", "isMultiFieldValueClassUnboxingMethodDescriptor", "isNextMethodCallOfProgressionIterator", "isPrimitiveBoxing", "isPrimitiveUnboxing", "isUnboxing", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BoxingInterpreterKt {
    private static final String BOXING_CLASS_INTERNAL_NAME;
    private static final Set<String> PROGRESSION_CLASS_FQNS;
    private static final Set<Type> shouldUseEqualsForWrappers;
    private static final ImmutableSet<String> UNBOXING_METHOD_NAMES = ImmutableSet.of("booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue");
    private static final String KCLASS_TO_JLCLASS = Type.getMethodDescriptor(AsmTypes.JAVA_CLASS_TYPE, AsmTypes.K_CLASS_TYPE);
    private static final String JLCLASS_TO_KCLASS = Type.getMethodDescriptor(AsmTypes.K_CLASS_TYPE, AsmTypes.JAVA_CLASS_TYPE);

    static {
        FqName fqNameChild = StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME.child(Name.identifier("Boxing"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        BOXING_CLASS_INTERNAL_NAME = CodegenUtilKt.topLevelClassInternalName(fqNameChild);
        PROGRESSION_CLASS_FQNS = SetsKt.setOf((Object[]) new String[]{RangeUtilKt.CHAR_RANGE_FQN, RangeUtilKt.CHAR_PROGRESSION_FQN, RangeUtilKt.INT_RANGE_FQN, RangeUtilKt.INT_PROGRESSION_FQN, RangeUtilKt.LONG_RANGE_FQN, RangeUtilKt.LONG_PROGRESSION_FQN});
        shouldUseEqualsForWrappers = SetsKt.setOf((Object[]) new Type[]{Type.DOUBLE_TYPE, Type.FLOAT_TYPE, AsmTypes.JAVA_CLASS_TYPE});
    }

    public static final boolean isUnboxing(AbstractInsnNode abstractInsnNode, GenerationState state) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(state, "state");
        return isPrimitiveUnboxing(abstractInsnNode) || isJavaLangClassUnboxing(abstractInsnNode) || isInlineClassUnboxing(abstractInsnNode, state) || isMultiFieldValueClassUnboxing(abstractInsnNode, state);
    }

    public static final boolean isBoxing(AbstractInsnNode abstractInsnNode, GenerationState state) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(state, "state");
        return isPrimitiveBoxing(abstractInsnNode) || isJavaLangClassBoxing(abstractInsnNode) || isInlineClassBoxing(abstractInsnNode, state) || isCoroutinePrimitiveBoxing(abstractInsnNode) || isMultiFieldValueClassBoxing(abstractInsnNode, state);
    }

    public static final boolean isMethodInsnWith(AbstractInsnNode abstractInsnNode, int i, Function1<? super MethodInsnNode, Boolean> condition) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(condition, "condition");
        return abstractInsnNode.getOpcode() == i && (abstractInsnNode instanceof MethodInsnNode) && condition.invoke(abstractInsnNode).booleanValue();
    }

    private static final boolean isWrapperClassNameOrNumber(String str) {
        return isWrapperClassName(str) || Intrinsics.areEqual(str, Type.getInternalName(Number.class));
    }

    private static final boolean isWrapperClassName(String str) {
        return JvmPrimitiveType.isWrapperClassName(buildFqNameByInternal(str));
    }

    private static final FqName buildFqNameByInternal(String str) {
        return new FqName(Type.getObjectType(str).getClassName());
    }

    private static final boolean isUnboxingMethodName(String str) {
        return UNBOXING_METHOD_NAMES.contains(str);
    }

    private static final boolean isJvmPrimitiveName(String str) {
        for (JvmPrimitiveType jvmPrimitiveType : JvmPrimitiveType.values()) {
            if (Intrinsics.areEqual(jvmPrimitiveType.getJavaKeywordName(), str)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean isBoxingMethodDescriptor(MethodInsnNode methodInsnNode) {
        Type objectType = Type.getObjectType(methodInsnNode.owner);
        return Intrinsics.areEqual(methodInsnNode.desc, Type.getMethodDescriptor(objectType, AsmUtil.unboxType(objectType)));
    }

    private static final boolean isMultiFieldValueClassUnboxing(AbstractInsnNode abstractInsnNode, GenerationState generationState) {
        return generationState.getConfig().getSupportMultiFieldValueClasses() && abstractInsnNode.getOpcode() == 182 && (abstractInsnNode instanceof MethodInsnNode) && isMultiFieldValueClassUnboxingMethodDescriptor((MethodInsnNode) abstractInsnNode, generationState);
    }

    private static final boolean isInlineClassBoxingMethodDescriptor(MethodInsnNode methodInsnNode, GenerationState generationState) {
        if (!Intrinsics.areEqual(methodInsnNode.f1015name, KotlinTypeMapper.BOX_JVM_METHOD_NAME)) {
            return false;
        }
        Type objectType = Type.getObjectType(methodInsnNode.owner);
        Intrinsics.checkNotNull(objectType);
        Type typeUnboxedTypeOfInlineClass = BoxedBasicValueKt.unboxedTypeOfInlineClass(objectType, generationState);
        if (typeUnboxedTypeOfInlineClass == null) {
            return false;
        }
        return Intrinsics.areEqual(methodInsnNode.desc, Type.getMethodDescriptor(objectType, typeUnboxedTypeOfInlineClass));
    }

    private static final boolean isMultiFieldValueClassBoxingMethodDescriptor(MethodInsnNode methodInsnNode, GenerationState generationState) {
        if (!Intrinsics.areEqual(methodInsnNode.f1015name, KotlinTypeMapper.BOX_JVM_METHOD_NAME)) {
            return false;
        }
        Type objectType = Type.getObjectType(methodInsnNode.owner);
        Intrinsics.checkNotNull(objectType);
        GenerationState.MultiFieldValueClassUnboxInfo multiFieldValueClassUnboxInfo = BoxedBasicValueKt.getMultiFieldValueClassUnboxInfo(objectType, generationState);
        if (multiFieldValueClassUnboxInfo == null) {
            return false;
        }
        String str = methodInsnNode.desc;
        Type[] typeArr = (Type[]) multiFieldValueClassUnboxInfo.getUnboxedTypes().toArray(new Type[0]);
        return Intrinsics.areEqual(str, Type.getMethodDescriptor(objectType, (Type[]) Arrays.copyOf(typeArr, typeArr.length)));
    }

    private static final boolean isInlineClassUnboxingMethodDescriptor(MethodInsnNode methodInsnNode, GenerationState generationState) {
        if (!Intrinsics.areEqual(methodInsnNode.f1015name, KotlinTypeMapper.UNBOX_JVM_METHOD_NAME)) {
            return false;
        }
        Type objectType = Type.getObjectType(methodInsnNode.owner);
        Intrinsics.checkNotNull(objectType);
        Type typeUnboxedTypeOfInlineClass = BoxedBasicValueKt.unboxedTypeOfInlineClass(objectType, generationState);
        if (typeUnboxedTypeOfInlineClass == null) {
            return false;
        }
        return Intrinsics.areEqual(methodInsnNode.desc, Type.getMethodDescriptor(typeUnboxedTypeOfInlineClass, new Type[0]));
    }

    private static final boolean isMultiFieldValueClassUnboxingMethodDescriptor(MethodInsnNode methodInsnNode, GenerationState generationState) {
        Type objectType = Type.getObjectType(methodInsnNode.owner);
        Intrinsics.checkNotNull(objectType);
        GenerationState.MultiFieldValueClassUnboxInfo multiFieldValueClassUnboxInfo = BoxedBasicValueKt.getMultiFieldValueClassUnboxInfo(objectType, generationState);
        if (multiFieldValueClassUnboxInfo == null) {
            return false;
        }
        List<Triple<Type, String, String>> unboxedTypesAndMethodNamesAndFieldNames = multiFieldValueClassUnboxInfo.getUnboxedTypesAndMethodNamesAndFieldNames();
        if ((unboxedTypesAndMethodNamesAndFieldNames instanceof Collection) && unboxedTypesAndMethodNamesAndFieldNames.isEmpty()) {
            return false;
        }
        Iterator<T> it = unboxedTypesAndMethodNamesAndFieldNames.iterator();
        while (it.hasNext()) {
            Triple triple = (Triple) it.next();
            Type type = (Type) triple.component1();
            if (Intrinsics.areEqual(methodInsnNode.f1015name, (String) triple.component2()) && Intrinsics.areEqual(methodInsnNode.desc, Type.getMethodDescriptor(type, new Type[0]))) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isNextMethodCallOfProgressionIterator(AbstractInsnNode abstractInsnNode, List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(values, "values");
        return (CollectionsKt.firstOrNull((List) values) instanceof ProgressionIteratorBasicValue) && abstractInsnNode.getOpcode() == 185 && (abstractInsnNode instanceof MethodInsnNode) && Intrinsics.areEqual(((MethodInsnNode) abstractInsnNode).f1015name, ES6Iterator.NEXT_METHOD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isProgressionClass(Type type) {
        return PROGRESSION_CLASS_FQNS.contains(type.getClassName());
    }

    public static final boolean isAreEqualIntrinsicForSameTypedBoxedValues(AbstractInsnNode abstractInsnNode, List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(values, "values");
        return isAreEqualIntrinsic(abstractInsnNode) && areSameTypedPrimitiveBoxedValues(values);
    }

    public static final boolean areSameTypedPrimitiveBoxedValues(List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(values, "values");
        if (values.size() != 2) {
            return false;
        }
        BasicValue basicValue = values.get(0);
        BasicValue basicValue2 = values.get(1);
        if (!(basicValue instanceof BoxedBasicValue) || !(basicValue2 instanceof BoxedBasicValue)) {
            return false;
        }
        BoxedBasicValue boxedBasicValue = (BoxedBasicValue) basicValue;
        if (boxedBasicValue.getDescriptor().getIsValueClassValue()) {
            return false;
        }
        BoxedBasicValue boxedBasicValue2 = (BoxedBasicValue) basicValue2;
        return !boxedBasicValue2.getDescriptor().getIsValueClassValue() && Intrinsics.areEqual(CollectionsKt.single((List) boxedBasicValue.getDescriptor().getUnboxedTypes()), CollectionsKt.single((List) boxedBasicValue2.getDescriptor().getUnboxedTypes()));
    }

    public static final boolean canValuesBeUnboxedForAreEqual(List<? extends BasicValue> values, GenerationState generationState) {
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        List<? extends BasicValue> list = values;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        for (BasicValue basicValue : list) {
            Type type = basicValue.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            Type type2 = basicValue.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            Type type3 = (Type) CollectionsKt.singleOrNull((List) BoxedBasicValueKt.getUnboxedTypes(type, generationState, BoxedBasicValueKt.getMultiFieldValueClassUnboxInfo(type2, generationState)));
            if (type3 == null || shouldUseEqualsForWrappers.contains(type3)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isJavaLangComparableCompareToForSameTypedBoxedValues(AbstractInsnNode abstractInsnNode, List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(values, "values");
        return isJavaLangComparableCompareTo(abstractInsnNode) && areSameTypedPrimitiveBoxedValues(values);
    }

    public static final boolean isPrimitiveUnboxing(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (abstractInsnNode.getOpcode() == 182 && (abstractInsnNode instanceof MethodInsnNode)) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            String owner = methodInsnNode.owner;
            Intrinsics.checkNotNullExpressionValue(owner, "owner");
            if (isWrapperClassNameOrNumber(owner)) {
                String name2 = methodInsnNode.f1015name;
                Intrinsics.checkNotNullExpressionValue(name2, "name");
                if (isUnboxingMethodName(name2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean isJavaLangClassUnboxing(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (abstractInsnNode.getOpcode() == 184 && (abstractInsnNode instanceof MethodInsnNode)) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            if (Intrinsics.areEqual(methodInsnNode.owner, "kotlin/jvm/JvmClassMappingKt") && Intrinsics.areEqual(methodInsnNode.f1015name, "getJavaClass") && Intrinsics.areEqual(methodInsnNode.desc, KCLASS_TO_JLCLASS)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isPrimitiveBoxing(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (abstractInsnNode.getOpcode() == 184 && (abstractInsnNode instanceof MethodInsnNode)) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            String owner = methodInsnNode.owner;
            Intrinsics.checkNotNullExpressionValue(owner, "owner");
            if (isWrapperClassName(owner) && Intrinsics.areEqual(methodInsnNode.f1015name, "valueOf") && isBoxingMethodDescriptor(methodInsnNode)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isCoroutinePrimitiveBoxing(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (abstractInsnNode.getOpcode() != 184 || !(abstractInsnNode instanceof MethodInsnNode)) {
            return false;
        }
        MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
        if (!Intrinsics.areEqual(methodInsnNode.owner, BOXING_CLASS_INTERNAL_NAME)) {
            return false;
        }
        String name2 = methodInsnNode.f1015name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        if (!StringsKt.startsWith$default(name2, "box", false, 2, (Object) null)) {
            return false;
        }
        String name3 = methodInsnNode.f1015name;
        Intrinsics.checkNotNullExpressionValue(name3, "name");
        String strSubstring = name3.substring(3);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        String lowerCase = strSubstring.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return isJvmPrimitiveName(lowerCase);
    }

    public static final boolean isJavaLangClassBoxing(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (abstractInsnNode.getOpcode() == 184 && (abstractInsnNode instanceof MethodInsnNode)) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            if (Intrinsics.areEqual(methodInsnNode.owner, AsmTypes.REFLECTION) && Intrinsics.areEqual(methodInsnNode.f1015name, "getOrCreateKotlinClass") && Intrinsics.areEqual(methodInsnNode.desc, JLCLASS_TO_KCLASS)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean isInlineClassBoxing(AbstractInsnNode abstractInsnNode, GenerationState generationState) {
        return abstractInsnNode.getOpcode() == 184 && (abstractInsnNode instanceof MethodInsnNode) && isInlineClassBoxingMethodDescriptor((MethodInsnNode) abstractInsnNode, generationState);
    }

    private static final boolean isMultiFieldValueClassBoxing(AbstractInsnNode abstractInsnNode, GenerationState generationState) {
        return abstractInsnNode.getOpcode() == 184 && (abstractInsnNode instanceof MethodInsnNode) && isMultiFieldValueClassBoxingMethodDescriptor((MethodInsnNode) abstractInsnNode, generationState);
    }

    private static final boolean isInlineClassUnboxing(AbstractInsnNode abstractInsnNode, GenerationState generationState) {
        return abstractInsnNode.getOpcode() == 182 && (abstractInsnNode instanceof MethodInsnNode) && isInlineClassUnboxingMethodDescriptor((MethodInsnNode) abstractInsnNode, generationState);
    }

    public static final boolean isIteratorMethodCall(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (abstractInsnNode.getOpcode() == 185 && (abstractInsnNode instanceof MethodInsnNode)) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            if (Intrinsics.areEqual(methodInsnNode.f1015name, "iterator") && Intrinsics.areEqual(methodInsnNode.desc, "()Ljava/util/Iterator;")) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isIteratorMethodCallOfProgression(AbstractInsnNode abstractInsnNode, List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(values, "values");
        if (abstractInsnNode.getOpcode() == 185 && (abstractInsnNode instanceof MethodInsnNode)) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            BasicValue basicValue = (BasicValue) CollectionsKt.firstOrNull((List) values);
            Type type = basicValue != null ? basicValue.getType() : null;
            if (Intrinsics.areEqual(methodInsnNode.f1015name, "iterator") && Intrinsics.areEqual(methodInsnNode.desc, "()Ljava/util/Iterator;") && type != null && isProgressionClass(type)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isAreEqualIntrinsic(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (abstractInsnNode.getOpcode() == 184 && (abstractInsnNode instanceof MethodInsnNode)) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            if (Intrinsics.areEqual(methodInsnNode.f1015name, "areEqual") && Intrinsics.areEqual(methodInsnNode.owner, "kotlin/jvm/internal/Intrinsics") && Intrinsics.areEqual(methodInsnNode.desc, "(Ljava/lang/Object;Ljava/lang/Object;)Z")) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isJavaLangComparableCompareTo(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (abstractInsnNode.getOpcode() == 185 && (abstractInsnNode instanceof MethodInsnNode)) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            if (Intrinsics.areEqual(methodInsnNode.f1015name, "compareTo") && Intrinsics.areEqual(methodInsnNode.owner, "java/lang/Comparable") && Intrinsics.areEqual(methodInsnNode.desc, "(Ljava/lang/Object;)I")) {
                return true;
            }
        }
        return false;
    }
}
