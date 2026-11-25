package org.jetbrains.kotlin.types;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: RangeUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\"\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u001b\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u001c\u001a\u00020\u00182\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010\"\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010#\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010$\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010%\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u001e\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020\u00182\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fH\u0002\u001a\u000e\u0010)\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010*\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010+\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010,\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010-\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u0010\u0010.\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010/\u001a\u000e\u00100\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u00101\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u00102\u001a\u00020\u001f2\u0006\u00103\u001a\u00020!\u001a\u0010\u00104\u001a\u00020\u001f2\u0006\u00105\u001a\u00020\u0018H\u0002\u001a\u000e\u00106\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u00107\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u0018\u001a\u000e\u00108\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u00109\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u0018\u001a\u000e\u0010:\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u0010\u0010;\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002\u001a\u000e\u0010<\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u0010\u0010=\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010/\u001a\u000e\u0010>\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010?\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010@\u001a\u00020\u001f2\u0006\u00103\u001a\u00020!\u001a\u000e\u0010A\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u000e\u0010B\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u0018\u001a\u000e\u0010C\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u0018\u001a\u0016\u0010D\u001a\u0004\u0018\u00010E*\u00020/2\u0006\u0010F\u001a\u00020GH\u0002\u001a\u0012\u0010H\u001a\u00020\u0018*\u00020I2\u0006\u0010J\u001a\u00020K\u001a1\u0010L\u001a\u00020\u001f*\u00020!2\u0006\u0010M\u001a\u00020\u00022\u0006\u0010N\u001a\u00020\u00022\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u001f0PH\u0082\b\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0002X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0002X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0002X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0002X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0002X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0002X\u0086T¢\u0006\u0002\n\u0000\"\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u0002*\u00020\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006Q"}, d2 = {"ALL_PROGRESSION_AND_RANGES", "", "", "CHAR_PROGRESSION_FQN", "CHAR_RANGE_FQN", "CLOSED_DOUBLE_RANGE_FQN", "CLOSED_FLOATING_POINT_RANGE_FQN", "CLOSED_FLOAT_RANGE_FQN", "CLOSED_RANGE_FQN", "COMPARABLE_RANGE_FQN", "INT_PROGRESSION_FQN", "INT_RANGE_FQN", "LONG_PROGRESSION_FQN", "LONG_RANGE_FQN", "PRIMITIVE_PROGRESSION_FQNS", "", "PRIMITIVE_RANGE_FQNS", "UINT_PROGRESSION_FQN", "UINT_RANGE_FQN", "ULONG_PROGRESSION_FQN", "ULONG_RANGE_FQN", "UNSIGNED_PROGRESSION_FQNS", "UNSIGNED_RANGE_FQNS", "classFqnString", "Lorg/jetbrains/kotlin/types/KotlinType;", "getClassFqnString", "(Lorg/jetbrains/kotlin/types/KotlinType;)Ljava/lang/String;", "getRangeOrProgressionElementType", "rangeType", "progressionsAndRanges", "isArrayOrPrimitiveArrayIndices", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isArrayOrPrimitiveArrayWithIndex", "isCharSequenceIndices", "isCharSequenceIterator", "isCharSequenceWithIndex", "isClassTypeWithFqn", "kotlinType", "fqns", "isClosedFloatingPointRangeContains", "isClosedRangeContains", "isCollectionIndices", "isComparableRangeTo", "isIterableWithIndex", "isPrimitiveNumberClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isPrimitiveNumberDownTo", "isPrimitiveNumberRangeExtensionContainsPrimitiveNumber", "isPrimitiveNumberRangeTo", "rangeTo", "isPrimitiveNumberType", ModuleXmlParser.TYPE, "isPrimitiveNumberUntil", "isPrimitiveProgression", "isPrimitiveProgressionReverse", "isPrimitiveRange", "isPrimitiveRangeContains", "isPrimitiveRangeToExtension", "isSequenceWithIndex", "isUnsignedIntegerClassDescriptor", "isUnsignedIntegerDownTo", "isUnsignedIntegerRangeContains", "isUnsignedIntegerRangeTo", "isUnsignedIntegerUntil", "isUnsignedProgression", "isUnsignedRange", "findTypeInModuleByTopLevelClassFqName", "Lorg/jetbrains/kotlin/types/SimpleType;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getElementType", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "isTopLevelExtensionOnType", "name", "packageFQN", "receiverTypePredicate", "Lkotlin/Function1;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RangeUtilKt {
    public static final String CHAR_RANGE_FQN = "kotlin.ranges.CharRange";
    public static final String INT_RANGE_FQN = "kotlin.ranges.IntRange";
    public static final String LONG_RANGE_FQN = "kotlin.ranges.LongRange";
    private static final Set<String> PRIMITIVE_RANGE_FQNS = SetsKt.setOf((Object[]) new String[]{CHAR_RANGE_FQN, INT_RANGE_FQN, LONG_RANGE_FQN});
    public static final String CHAR_PROGRESSION_FQN = "kotlin.ranges.CharProgression";
    public static final String INT_PROGRESSION_FQN = "kotlin.ranges.IntProgression";
    public static final String LONG_PROGRESSION_FQN = "kotlin.ranges.LongProgression";
    private static final Set<String> PRIMITIVE_PROGRESSION_FQNS = SetsKt.setOf((Object[]) new String[]{CHAR_PROGRESSION_FQN, INT_PROGRESSION_FQN, LONG_PROGRESSION_FQN});
    public static final String UINT_RANGE_FQN = "kotlin.ranges.UIntRange";
    public static final String ULONG_RANGE_FQN = "kotlin.ranges.ULongRange";
    private static final Set<String> UNSIGNED_RANGE_FQNS = SetsKt.setOf((Object[]) new String[]{UINT_RANGE_FQN, ULONG_RANGE_FQN});
    public static final String UINT_PROGRESSION_FQN = "kotlin.ranges.UIntProgression";
    public static final String ULONG_PROGRESSION_FQN = "kotlin.ranges.ULongProgression";
    private static final Set<String> UNSIGNED_PROGRESSION_FQNS = SetsKt.setOf((Object[]) new String[]{UINT_PROGRESSION_FQN, ULONG_PROGRESSION_FQN});
    private static final List<String> ALL_PROGRESSION_AND_RANGES = CollectionsKt.listOf((Object[]) new String[]{CHAR_RANGE_FQN, CHAR_PROGRESSION_FQN, INT_RANGE_FQN, INT_PROGRESSION_FQN, LONG_RANGE_FQN, LONG_PROGRESSION_FQN, "kotlin.ranges.ClosedFloatRange", "kotlin.ranges.ClosedDoubleRange", "kotlin.ranges.ClosedRange", "kotlin.ranges.ClosedFloatingPointRange", "kotlin.ranges.ComparableRange", UINT_RANGE_FQN, UINT_PROGRESSION_FQN, ULONG_RANGE_FQN, ULONG_PROGRESSION_FQN});

    public static final boolean isPrimitiveRange(KotlinType rangeType) {
        Intrinsics.checkNotNullParameter(rangeType, "rangeType");
        return isClassTypeWithFqn(rangeType, PRIMITIVE_RANGE_FQNS);
    }

    public static final boolean isUnsignedRange(KotlinType rangeType) {
        Intrinsics.checkNotNullParameter(rangeType, "rangeType");
        return isClassTypeWithFqn(rangeType, UNSIGNED_RANGE_FQNS);
    }

    public static final boolean isPrimitiveProgression(KotlinType rangeType) {
        Intrinsics.checkNotNullParameter(rangeType, "rangeType");
        return isClassTypeWithFqn(rangeType, PRIMITIVE_PROGRESSION_FQNS);
    }

    public static final boolean isUnsignedProgression(KotlinType rangeType) {
        Intrinsics.checkNotNullParameter(rangeType, "rangeType");
        return isClassTypeWithFqn(rangeType, UNSIGNED_PROGRESSION_FQNS);
    }

    private static final String getClassFqnString(KotlinType kotlinType) {
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            return null;
        }
        FqNameUnsafe fqName = DescriptorUtils.getFqName(classDescriptor);
        Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
        if (fqName.isSafe()) {
            return fqName.asString();
        }
        return null;
    }

    private static final boolean isClassTypeWithFqn(KotlinType kotlinType, Set<String> set) {
        return CollectionsKt.contains(set, getClassFqnString(kotlinType));
    }

    public static /* synthetic */ KotlinType getRangeOrProgressionElementType$default(KotlinType kotlinType, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = ALL_PROGRESSION_AND_RANGES;
        }
        return getRangeOrProgressionElementType(kotlinType, list);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final KotlinType getRangeOrProgressionElementType(KotlinType rangeType, List<String> progressionsAndRanges) {
        TypeProjection typeProjection;
        TypeProjection typeProjection2;
        TypeProjection typeProjection3;
        Intrinsics.checkNotNullParameter(rangeType, "rangeType");
        Intrinsics.checkNotNullParameter(progressionsAndRanges, "progressionsAndRanges");
        ClassifierDescriptor this$0 = rangeType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            return null;
        }
        ClassDescriptor classDescriptor2 = classDescriptor;
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(classDescriptor2);
        String strAsString = DescriptorUtilsKt.getFqNameSafe(classDescriptor2).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (!progressionsAndRanges.contains(strAsString)) {
            return null;
        }
        switch (strAsString.hashCode()) {
            case -2079675915:
                if (strAsString.equals("kotlin.ranges.ClosedDoubleRange")) {
                    return builtIns.getDoubleType();
                }
                return null;
            case -1574668363:
                if (!strAsString.equals(ULONG_PROGRESSION_FQN)) {
                    return null;
                }
                return findTypeInModuleByTopLevelClassFqName(classDescriptor2, StandardNames.FqNames.uLongFqName);
            case -1404642343:
                if (!strAsString.equals(INT_RANGE_FQN)) {
                    return null;
                }
                return builtIns.getIntType();
            case -1316009897:
                if (!strAsString.equals(INT_PROGRESSION_FQN)) {
                    return null;
                }
                return builtIns.getIntType();
            case -939226568:
                if (!strAsString.equals(UINT_RANGE_FQN)) {
                    return null;
                }
                return findTypeInModuleByTopLevelClassFqName(classDescriptor2, StandardNames.FqNames.uIntFqName);
            case -468986362:
                if (strAsString.equals("kotlin.ranges.ClosedRange") && (typeProjection = (TypeProjection) CollectionsKt.singleOrNull((List) rangeType.getArguments())) != null) {
                    return typeProjection.getNullableAnyType();
                }
                return null;
            case -421901258:
                if (!strAsString.equals(LONG_RANGE_FQN)) {
                    return null;
                }
                return builtIns.getLongType();
            case 102183946:
                if (strAsString.equals("kotlin.ranges.ClosedFloatingPointRange") && (typeProjection2 = (TypeProjection) CollectionsKt.singleOrNull((List) rangeType.getArguments())) != null) {
                    return typeProjection2.getNullableAnyType();
                }
                return null;
            case 306491608:
                if (strAsString.equals("kotlin.ranges.ComparableRange") && (typeProjection3 = (TypeProjection) CollectionsKt.singleOrNull((List) rangeType.getArguments())) != null) {
                    return typeProjection3.getNullableAnyType();
                }
                return null;
            case 1012823964:
                if (!strAsString.equals(CHAR_RANGE_FQN)) {
                    return null;
                }
                return builtIns.getCharType();
            case 1121085879:
                if (!strAsString.equals(ULONG_RANGE_FQN)) {
                    return null;
                }
                return findTypeInModuleByTopLevelClassFqName(classDescriptor2, StandardNames.FqNames.uLongFqName);
            case 1168491994:
                if (!strAsString.equals(CHAR_PROGRESSION_FQN)) {
                    return null;
                }
                return builtIns.getCharType();
            case 1449933080:
                if (strAsString.equals("kotlin.ranges.ClosedFloatRange")) {
                    return builtIns.getFloatType();
                }
                return null;
            case 1668431604:
                if (!strAsString.equals(LONG_PROGRESSION_FQN)) {
                    return null;
                }
                return builtIns.getLongType();
            case 1765962614:
                if (!strAsString.equals(UINT_PROGRESSION_FQN)) {
                    return null;
                }
                return findTypeInModuleByTopLevelClassFqName(classDescriptor2, StandardNames.FqNames.uIntFqName);
            default:
                return null;
        }
    }

    private static final SimpleType findTypeInModuleByTopLevelClassFqName(DeclarationDescriptor declarationDescriptor, FqName fqName) {
        ModuleDescriptor module = DescriptorUtilsKt.getModule(declarationDescriptor);
        ClassId classId = ClassId.topLevel(fqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(module, classId);
        if (classDescriptorFindClassAcrossModuleDependencies != null) {
            return classDescriptorFindClassAcrossModuleDependencies.getDefaultType();
        }
        return null;
    }

    public static final KotlinType getElementType(BindingContext bindingContext, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(bindingContext, "<this>");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        KtExpression loopRange = forExpression.getLoopRange();
        Intrinsics.checkNotNull(loopRange);
        ResolvedCall resolvedCall = (ResolvedCall) bindingContext.get(BindingContext.LOOP_RANGE_NEXT_RESOLVED_CALL, loopRange);
        if (resolvedCall == null) {
            throw new AssertionError("No next() function " + PsiDiagnosticUtils.atLocation(loopRange));
        }
        KotlinType returnType = ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getReturnType();
        Intrinsics.checkNotNull(returnType);
        return returnType;
    }

    public static final boolean isPrimitiveNumberRangeTo(CallableDescriptor rangeTo) {
        Intrinsics.checkNotNullParameter(rangeTo, "rangeTo");
        return (Intrinsics.areEqual("rangeTo", rangeTo.getShortName().asString()) && isPrimitiveNumberClassDescriptor(rangeTo.getContainingModule())) || isPrimitiveRangeToExtension(rangeTo);
    }

    public static final boolean isUnsignedIntegerRangeTo(CallableDescriptor rangeTo) {
        Intrinsics.checkNotNullParameter(rangeTo, "rangeTo");
        return Intrinsics.areEqual("rangeTo", rangeTo.getShortName().asString()) && isUnsignedIntegerClassDescriptor(rangeTo.getContainingModule());
    }

    public static final boolean isUnsignedIntegerClassDescriptor(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor != null && UnsignedTypes.INSTANCE.isUnsignedClass(declarationDescriptor);
    }

    private static final boolean isPrimitiveRangeToExtension(CallableDescriptor callableDescriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        if (!DescriptorUtilKt.isTopLevelInPackage(callableDescriptor, "rangeTo", "kotlin.ranges") || (extensionReceiverParameter = callableDescriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return KotlinBuiltIns.isPrimitiveType(type);
    }

    public static final boolean isPrimitiveNumberDownTo(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "downTo", "kotlin.ranges") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return isPrimitiveNumberClassDescriptor(type.getConstructor().getThis$0());
    }

    public static final boolean isUnsignedIntegerDownTo(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "downTo", "kotlin.ranges") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return isUnsignedIntegerClassDescriptor(type.getConstructor().getThis$0());
    }

    public static final boolean isPrimitiveNumberUntil(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "until", "kotlin.ranges") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return isPrimitiveNumberClassDescriptor(type.getConstructor().getThis$0());
    }

    public static final boolean isUnsignedIntegerUntil(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "until", "kotlin.ranges") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return isUnsignedIntegerClassDescriptor(type.getConstructor().getThis$0());
    }

    public static final boolean isArrayOrPrimitiveArrayIndices(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "indices", "kotlin.collections") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return KotlinBuiltIns.isArray(type) || KotlinBuiltIns.isPrimitiveArray(type);
    }

    public static final boolean isArrayOrPrimitiveArrayWithIndex(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "withIndex", "kotlin.collections") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return KotlinBuiltIns.isArray(type) || KotlinBuiltIns.isPrimitiveArray(type);
    }

    public static final boolean isCollectionIndices(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "indices", "kotlin.collections") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return KotlinBuiltIns.isCollectionOrNullableCollection(type);
    }

    public static final boolean isIterableWithIndex(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "withIndex", "kotlin.collections") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return KotlinBuiltIns.isIterableOrNullableIterable(type);
    }

    public static final boolean isCharSequenceIndices(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "indices", "kotlin.text") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return KotlinBuiltIns.isCharSequenceOrNullableCharSequence(type);
    }

    public static final boolean isCharSequenceWithIndex(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "withIndex", "kotlin.text") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return KotlinBuiltIns.isCharSequenceOrNullableCharSequence(type);
    }

    public static final boolean isComparableRangeTo(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "rangeTo", "kotlin.ranges") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        TypeParameterDescriptor typeParameterDescriptor = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
        if (typeParameterDescriptor == null) {
            return false;
        }
        List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        KotlinType kotlinType = (KotlinType) CollectionsKt.singleOrNull((List) upperBounds);
        if (kotlinType == null) {
            return false;
        }
        ClassifierDescriptor this$1 = kotlinType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$1 instanceof ClassDescriptor ? (ClassDescriptor) this$1 : null;
        if (classDescriptor == null) {
            return false;
        }
        return DescriptorUtilKt.isTopLevelInPackage(classDescriptor, "Comparable", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
    }

    public static final boolean isClosedRangeContains(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!Intrinsics.areEqual(descriptor.getShortName().asString(), "contains")) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = descriptor.getContainingModule();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        return classDescriptor != null && DescriptorUtilKt.isTopLevelInPackage(classDescriptor, "ClosedRange", "kotlin.ranges");
    }

    public static final boolean isPrimitiveRangeContains(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return Intrinsics.areEqual(descriptor.getShortName().asString(), "contains") && (receiverParameterDescriptorMo6753getDispatchReceiverParameter = descriptor.mo6753getDispatchReceiverParameter()) != null && (type = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType()) != null && isPrimitiveRange(type);
    }

    public static final boolean isUnsignedIntegerRangeContains(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = descriptor.mo6753getDispatchReceiverParameter();
        KotlinType type = receiverParameterDescriptorMo6753getDispatchReceiverParameter != null ? receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType() : null;
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        KotlinType type2 = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        if (type == null || type2 != null) {
            if (type2 != null && type == null && DescriptorUtilKt.isTopLevelInPackage(descriptor, "contains", "kotlin.ranges")) {
                return isUnsignedRange(type2);
            }
            return false;
        }
        if (Intrinsics.areEqual(descriptor.getShortName().asString(), "contains")) {
            return isUnsignedRange(type);
        }
        return false;
    }

    public static final boolean isPrimitiveNumberRangeExtensionContainsPrimitiveNumber(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        KotlinType rangeOrProgressionElementType$default;
        KotlinType type2;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "contains", "kotlin.ranges") || (extensionReceiverParameter = descriptor.getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null || (rangeOrProgressionElementType$default = getRangeOrProgressionElementType$default(type, null, 2, null)) == null || !isPrimitiveNumberType(rangeOrProgressionElementType$default)) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.singleOrNull((List) valueParameters);
        return (valueParameterDescriptor == null || (type2 = valueParameterDescriptor.getType()) == null || !isPrimitiveNumberType(type2)) ? false : true;
    }

    public static final boolean isPrimitiveProgressionReverse(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "reversed", "kotlin.ranges") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        return isPrimitiveProgression(type);
    }

    private static final boolean isPrimitiveNumberType(KotlinType kotlinType) {
        return KotlinBuiltIns.isByte(kotlinType) || KotlinBuiltIns.isShort(kotlinType) || KotlinBuiltIns.isInt(kotlinType) || KotlinBuiltIns.isChar(kotlinType) || KotlinBuiltIns.isLong(kotlinType) || KotlinBuiltIns.isFloat(kotlinType) || KotlinBuiltIns.isDouble(kotlinType);
    }

    public static final boolean isClosedFloatingPointRangeContains(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!Intrinsics.areEqual(descriptor.getShortName().asString(), "contains")) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = descriptor.getContainingModule();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        return classDescriptor != null && DescriptorUtilKt.isTopLevelInPackage(classDescriptor, "ClosedFloatingPointRange", "kotlin.ranges");
    }

    public static final boolean isCharSequenceIterator(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "iterator", "kotlin.text") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (this$0 == null) {
            return false;
        }
        Intrinsics.checkNotNull(this$0);
        return DescriptorUtilKt.isTopLevelInPackage(this$0, "CharSequence", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
    }

    public static final boolean isPrimitiveNumberClassDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
            if (KotlinBuiltIns.isPrimitiveClass(classDescriptor) && !KotlinBuiltIns.isBoolean(classDescriptor)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isSequenceWithIndex(CallableDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!DescriptorUtilKt.isTopLevelInPackage(descriptor, "withIndex", "kotlin.sequences") || (extensionReceiverParameter = descriptor.getOriginal().getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(type);
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (this$0 == null) {
            return false;
        }
        Intrinsics.checkNotNull(this$0);
        return DescriptorUtilKt.isTopLevelInPackage(this$0, "Sequence", "kotlin.sequences");
    }
}
