package org.jetbrains.kotlin.resolve.jvm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.OverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: JvmOverridesBackwardCompatibilityHelper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0002J\u001e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0006H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmOverridesBackwardCompatibilityHelper;", "Lorg/jetbrains/kotlin/resolve/OverridesBackwardCompatibilityHelper;", "()V", "isOverridingOnlyDescriptorsThatCanBeImplicitlyOverridden", "", "overriddenDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "visitedDescriptors", "", "isPlatformSpecificDescriptorThatCanBeImplicitlyOverridden", "overrideCanBeOmitted", "overridingDescriptor", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmOverridesBackwardCompatibilityHelper implements OverridesBackwardCompatibilityHelper {
    public static final JvmOverridesBackwardCompatibilityHelper INSTANCE = new JvmOverridesBackwardCompatibilityHelper();

    /* JADX INFO: compiled from: JvmOverridesBackwardCompatibilityHelper.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CallableMemberDescriptor.Kind.values().length];
            try {
                iArr[CallableMemberDescriptor.Kind.DELEGATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CallableMemberDescriptor.Kind.FAKE_OVERRIDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CallableMemberDescriptor.Kind.DECLARATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private JvmOverridesBackwardCompatibilityHelper() {
    }

    @Override // org.jetbrains.kotlin.resolve.OverridesBackwardCompatibilityHelper
    public boolean overrideCanBeOmitted(CallableMemberDescriptor overridingDescriptor) {
        Intrinsics.checkNotNullParameter(overridingDescriptor, "overridingDescriptor");
        HashSet hashSet = new HashSet();
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = overridingDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
        if (collection.isEmpty()) {
            return true;
        }
        for (CallableMemberDescriptor callableMemberDescriptor : collection) {
            JvmOverridesBackwardCompatibilityHelper jvmOverridesBackwardCompatibilityHelper = INSTANCE;
            Intrinsics.checkNotNull(callableMemberDescriptor);
            if (!jvmOverridesBackwardCompatibilityHelper.isPlatformSpecificDescriptorThatCanBeImplicitlyOverridden(callableMemberDescriptor, hashSet)) {
                return false;
            }
        }
        return true;
    }

    private final boolean isPlatformSpecificDescriptorThatCanBeImplicitlyOverridden(CallableMemberDescriptor overriddenDescriptor, Set<CallableMemberDescriptor> visitedDescriptors) {
        ClassDescriptor containingClass;
        if (overriddenDescriptor.getModality() == Modality.FINAL) {
            return false;
        }
        if (visitedDescriptors.contains(overriddenDescriptor.getOriginal())) {
            return true;
        }
        CallableMemberDescriptor original = overriddenDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        visitedDescriptors.add(original);
        int i = WhenMappings.$EnumSwitchMapping$0[overriddenDescriptor.getKind().ordinal()];
        if (i == 1 || i == 2) {
            return isOverridingOnlyDescriptorsThatCanBeImplicitlyOverridden(overriddenDescriptor, visitedDescriptors);
        }
        if (i != 3) {
            return false;
        }
        if (overriddenDescriptor.getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME())) {
            return true;
        }
        if (!(overriddenDescriptor instanceof JavaMethodDescriptor) || (containingClass = DescriptorUtils.getContainingClass(overriddenDescriptor)) == null) {
            return false;
        }
        if (JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(DescriptorUtilsKt.getFqNameUnsafe(containingClass)) != null) {
            return true;
        }
        if (((JavaMethodDescriptor) overriddenDescriptor).getOverriddenDescriptors().isEmpty()) {
            return false;
        }
        return isOverridingOnlyDescriptorsThatCanBeImplicitlyOverridden(overriddenDescriptor, visitedDescriptors);
    }

    private final boolean isOverridingOnlyDescriptorsThatCanBeImplicitlyOverridden(CallableMemberDescriptor overriddenDescriptor, Set<CallableMemberDescriptor> visitedDescriptors) {
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = overriddenDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
        if (collection.isEmpty()) {
            return true;
        }
        for (CallableMemberDescriptor callableMemberDescriptor : collection) {
            JvmOverridesBackwardCompatibilityHelper jvmOverridesBackwardCompatibilityHelper = INSTANCE;
            Intrinsics.checkNotNull(callableMemberDescriptor);
            if (!jvmOverridesBackwardCompatibilityHelper.isPlatformSpecificDescriptorThatCanBeImplicitlyOverridden(callableMemberDescriptor, visitedDescriptors)) {
                return false;
            }
        }
        return true;
    }
}
