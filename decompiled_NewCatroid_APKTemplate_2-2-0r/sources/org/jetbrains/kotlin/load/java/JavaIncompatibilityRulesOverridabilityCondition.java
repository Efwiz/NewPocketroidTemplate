package org.jetbrains.kotlin.load.java;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.kotlin.JvmType;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.ExternalOverridabilityCondition;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JavaIncompatibilityRulesOverridabilityCondition.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002J\"\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaIncompatibilityRulesOverridabilityCondition;", "Lorg/jetbrains/kotlin/resolve/ExternalOverridabilityCondition;", "()V", "getContract", "Lorg/jetbrains/kotlin/resolve/ExternalOverridabilityCondition$Contract;", "isIncompatibleInAccordanceWithBuiltInOverridabilityRules", "", "superDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "subDescriptor", "subClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isOverridable", "Lorg/jetbrains/kotlin/resolve/ExternalOverridabilityCondition$Result;", "Companion", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaIncompatibilityRulesOverridabilityCondition implements ExternalOverridabilityCondition {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.resolve.ExternalOverridabilityCondition
    public ExternalOverridabilityCondition.Result isOverridable(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor, ClassDescriptor subClassDescriptor) {
        Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
        Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
        if (isIncompatibleInAccordanceWithBuiltInOverridabilityRules(superDescriptor, subDescriptor, subClassDescriptor)) {
            return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
        }
        if (INSTANCE.doesJavaOverrideHaveIncompatibleValueParameterKinds(superDescriptor, subDescriptor)) {
            return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
    }

    private final boolean isIncompatibleInAccordanceWithBuiltInOverridabilityRules(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor, ClassDescriptor subClassDescriptor) {
        if ((superDescriptor instanceof CallableMemberDescriptor) && (subDescriptor instanceof FunctionDescriptor) && !KotlinBuiltIns.isBuiltIn(subDescriptor)) {
            BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = BuiltinMethodsWithSpecialGenericSignature.INSTANCE;
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) subDescriptor;
            Name name2 = functionDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (!builtinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(name2)) {
                SpecialGenericSignatures.Companion companion = SpecialGenericSignatures.INSTANCE;
                Name name3 = functionDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                if (!companion.getSameAsRenamedInJvmBuiltin(name3)) {
                    return false;
                }
            }
            CallableMemberDescriptor overriddenSpecialBuiltin = SpecialBuiltinMembers.getOverriddenSpecialBuiltin((CallableMemberDescriptor) superDescriptor);
            boolean z = superDescriptor instanceof FunctionDescriptor;
            FunctionDescriptor functionDescriptor2 = z ? (FunctionDescriptor) superDescriptor : null;
            if (!(functionDescriptor2 != null && functionDescriptor.isHiddenToOvercomeSignatureClash() == functionDescriptor2.isHiddenToOvercomeSignatureClash()) && (overriddenSpecialBuiltin == null || !functionDescriptor.isHiddenToOvercomeSignatureClash())) {
                return true;
            }
            if ((subClassDescriptor instanceof JavaClassDescriptor) && functionDescriptor.getInitialSignatureDescriptor() == null && overriddenSpecialBuiltin != null && !SpecialBuiltinMembers.hasRealKotlinSuperClassWithOverrideOf(subClassDescriptor, overriddenSpecialBuiltin)) {
                if ((overriddenSpecialBuiltin instanceof FunctionDescriptor) && z && BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava((FunctionDescriptor) overriddenSpecialBuiltin) != null) {
                    String strComputeJvmDescriptor$default = MethodSignatureMappingKt.computeJvmDescriptor$default(functionDescriptor, false, false, 2, null);
                    FunctionDescriptor original = ((FunctionDescriptor) superDescriptor).getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                    if (Intrinsics.areEqual(strComputeJvmDescriptor$default, MethodSignatureMappingKt.computeJvmDescriptor$default(original, false, false, 2, null))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.ExternalOverridabilityCondition
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.CONFLICTS_ONLY;
    }

    /* JADX INFO: compiled from: JavaIncompatibilityRulesOverridabilityCondition.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaIncompatibilityRulesOverridabilityCondition$Companion;", "", "()V", "doesJavaOverrideHaveIncompatibleValueParameterKinds", "", "superDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "subDescriptor", "isPrimitiveCompareTo", "f", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "mapValueParameterType", "Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean doesJavaOverrideHaveIncompatibleValueParameterKinds(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor) {
            Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
            Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
            if ((subDescriptor instanceof JavaMethodDescriptor) && (superDescriptor instanceof FunctionDescriptor)) {
                JavaMethodDescriptor javaMethodDescriptor = (JavaMethodDescriptor) subDescriptor;
                javaMethodDescriptor.getValueParameters().size();
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) superDescriptor;
                functionDescriptor.getValueParameters().size();
                List<ValueParameterDescriptor> valueParameters = javaMethodDescriptor.getOriginal().getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                List<ValueParameterDescriptor> valueParameters2 = functionDescriptor.getOriginal().getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                for (Pair pair : CollectionsKt.zip(valueParameters, valueParameters2)) {
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component1();
                    ValueParameterDescriptor valueParameterDescriptor2 = (ValueParameterDescriptor) pair.component2();
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    boolean z = mapValueParameterType((FunctionDescriptor) subDescriptor, valueParameterDescriptor) instanceof JvmType.Primitive;
                    Intrinsics.checkNotNull(valueParameterDescriptor2);
                    if (z != (mapValueParameterType(functionDescriptor, valueParameterDescriptor2) instanceof JvmType.Primitive)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private final JvmType mapValueParameterType(FunctionDescriptor f, ValueParameterDescriptor valueParameterDescriptor) {
            if (MethodSignatureMappingKt.forceSingleValueParameterBoxing(f) || isPrimitiveCompareTo(f)) {
                KotlinType type = valueParameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                return MethodSignatureMappingKt.mapToJvmType(TypeUtilsKt.makeNullable(type));
            }
            KotlinType type2 = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            return MethodSignatureMappingKt.mapToJvmType(type2);
        }

        private final boolean isPrimitiveCompareTo(FunctionDescriptor f) {
            if (f.getValueParameters().size() != 1) {
                return false;
            }
            DeclarationDescriptor containingModule = f.getContainingModule();
            ClassDescriptor classDescriptor = containingModule instanceof ClassDescriptor ? (ClassDescriptor) containingModule : null;
            if (classDescriptor == null) {
                return false;
            }
            List<ValueParameterDescriptor> valueParameters = f.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ClassifierDescriptor this$0 = ((ValueParameterDescriptor) CollectionsKt.single((List) valueParameters)).getType().getConstructor().getThis$0();
            ClassDescriptor classDescriptor2 = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            return classDescriptor2 != null && KotlinBuiltIns.isPrimitiveClass(classDescriptor) && Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(classDescriptor), DescriptorUtilsKt.getFqNameSafe(classDescriptor2));
        }
    }
}
