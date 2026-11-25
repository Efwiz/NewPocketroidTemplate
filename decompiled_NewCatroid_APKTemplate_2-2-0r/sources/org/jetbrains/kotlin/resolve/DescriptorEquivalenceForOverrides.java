package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: DescriptorEquivalenceForOverrides.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u0018\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u000eH\u0002J,\u0010\u000f\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00102\b\u0010\u0007\u001a\u0004\u0018\u00010\u00102\u0006\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\u0004J@\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\u00042\u001e\b\u0002\u0010\u0013\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\u00040\u0014H\u0007J>\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u00102\u001c\u0010\u0013\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\u00040\u00142\u0006\u0010\b\u001a\u00020\u0004H\u0002J\u000f\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u00020\u0006H\u0082\u0010¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DescriptorEquivalenceForOverrides;", "", "()V", "areCallableDescriptorsEquivalent", "", "a", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "allowCopiesFromTheSameDeclaration", "distinguishExpectsAndNonExpects", "ignoreReturnType", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "areClassesEquivalent", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "areEquivalent", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "areTypeParametersEquivalent", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "equivalentCallables", "Lkotlin/Function2;", "ownersEquivalent", "singleSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorEquivalenceForOverrides {
    public static final DescriptorEquivalenceForOverrides INSTANCE = new DescriptorEquivalenceForOverrides();

    public final boolean areTypeParametersEquivalent(TypeParameterDescriptor a, TypeParameterDescriptor b, boolean z) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return areTypeParametersEquivalent$default(this, a, b, z, null, 8, null);
    }

    private DescriptorEquivalenceForOverrides() {
    }

    public static /* synthetic */ boolean areEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2, boolean z, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = true;
        }
        return descriptorEquivalenceForOverrides.areEquivalent(declarationDescriptor, declarationDescriptor2, z, z2);
    }

    public final boolean areEquivalent(DeclarationDescriptor a, DeclarationDescriptor b, boolean allowCopiesFromTheSameDeclaration, boolean distinguishExpectsAndNonExpects) {
        if ((a instanceof ClassDescriptor) && (b instanceof ClassDescriptor)) {
            return areClassesEquivalent((ClassDescriptor) a, (ClassDescriptor) b);
        }
        if ((a instanceof TypeParameterDescriptor) && (b instanceof TypeParameterDescriptor)) {
            return areTypeParametersEquivalent$default(this, (TypeParameterDescriptor) a, (TypeParameterDescriptor) b, allowCopiesFromTheSameDeclaration, null, 8, null);
        }
        if ((a instanceof CallableDescriptor) && (b instanceof CallableDescriptor)) {
            return areCallableDescriptorsEquivalent$default(this, (CallableDescriptor) a, (CallableDescriptor) b, allowCopiesFromTheSameDeclaration, distinguishExpectsAndNonExpects, false, KotlinTypeRefiner.Default.INSTANCE, 16, null);
        }
        return ((a instanceof PackageFragmentDescriptor) && (b instanceof PackageFragmentDescriptor)) ? Intrinsics.areEqual(((PackageFragmentDescriptor) a).getFqName(), ((PackageFragmentDescriptor) b).getFqName()) : Intrinsics.areEqual(a, b);
    }

    private final boolean areClassesEquivalent(ClassDescriptor a, ClassDescriptor b) {
        return Intrinsics.areEqual(a.getTypeConstructor(), b.getTypeConstructor());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ boolean areTypeParametersEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, TypeParameterDescriptor typeParameterDescriptor, TypeParameterDescriptor typeParameterDescriptor2, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 8) != 0) {
            function2 = new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DescriptorEquivalenceForOverrides.areTypeParametersEquivalent.1
                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                    return false;
                }
            };
        }
        return descriptorEquivalenceForOverrides.areTypeParametersEquivalent(typeParameterDescriptor, typeParameterDescriptor2, z, function2);
    }

    public final boolean areTypeParametersEquivalent(TypeParameterDescriptor a, TypeParameterDescriptor b, boolean allowCopiesFromTheSameDeclaration, Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> equivalentCallables) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(equivalentCallables, "equivalentCallables");
        if (Intrinsics.areEqual(a, b)) {
            return true;
        }
        return !Intrinsics.areEqual(a.getPackageFragment(), b.getPackageFragment()) && ownersEquivalent(a, b, equivalentCallables, allowCopiesFromTheSameDeclaration) && a.getIndex() == b.getIndex();
    }

    private final SourceElement singleSource(CallableDescriptor callableDescriptor) {
        while (callableDescriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) callableDescriptor;
            if (callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                break;
            }
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) CollectionsKt.singleOrNull(overriddenDescriptors);
            if (callableMemberDescriptor2 == null) {
                return null;
            }
            callableDescriptor = callableMemberDescriptor2;
        }
        return callableDescriptor.getSourceElement();
    }

    public static /* synthetic */ boolean areCallableDescriptorsEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, CallableDescriptor callableDescriptor, CallableDescriptor callableDescriptor2, boolean z, boolean z2, boolean z3, KotlinTypeRefiner kotlinTypeRefiner, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = true;
        }
        boolean z4 = z2;
        if ((i & 16) != 0) {
            z3 = false;
        }
        return descriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent(callableDescriptor, callableDescriptor2, z, z4, z3, kotlinTypeRefiner);
    }

    public final boolean areCallableDescriptorsEquivalent(final CallableDescriptor a, final CallableDescriptor b, final boolean allowCopiesFromTheSameDeclaration, boolean distinguishExpectsAndNonExpects, boolean ignoreReturnType, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        if (Intrinsics.areEqual(a, b)) {
            return true;
        }
        if (!Intrinsics.areEqual(a.getShortName(), b.getShortName())) {
            return false;
        }
        if (distinguishExpectsAndNonExpects && (a instanceof MemberDescriptor) && (b instanceof MemberDescriptor) && ((MemberDescriptor) a).get$isExpect() != ((MemberDescriptor) b).get$isExpect()) {
            return false;
        }
        if (Intrinsics.areEqual(a.getPackageFragment(), b.getPackageFragment()) && (!allowCopiesFromTheSameDeclaration || !Intrinsics.areEqual(singleSource(a), singleSource(b)))) {
            return false;
        }
        CallableDescriptor callableDescriptor = a;
        if (!DescriptorUtils.isLocal(callableDescriptor)) {
            CallableDescriptor callableDescriptor2 = b;
            if (DescriptorUtils.isLocal(callableDescriptor2) || !ownersEquivalent(callableDescriptor, callableDescriptor2, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DescriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent.1
                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                    return false;
                }
            }, allowCopiesFromTheSameDeclaration)) {
                return false;
            }
            OverridingUtil overridingUtilCreate = OverridingUtil.create(kotlinTypeRefiner, new KotlinTypeChecker.TypeConstructorEquality() { // from class: org.jetbrains.kotlin.resolve.DescriptorEquivalenceForOverrides$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.types.checker.KotlinTypeChecker.TypeConstructorEquality
                public final boolean equals(TypeConstructor typeConstructor, TypeConstructor typeConstructor2) {
                    return DescriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent$lambda$0(allowCopiesFromTheSameDeclaration, a, b, typeConstructor, typeConstructor2);
                }
            });
            Intrinsics.checkNotNullExpressionValue(overridingUtilCreate, "create(...)");
            return overridingUtilCreate.isOverridableBy(a, b, null, ignoreReturnType ^ true).getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE && overridingUtilCreate.isOverridableBy(b, a, null, ignoreReturnType ^ true).getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean areCallableDescriptorsEquivalent$lambda$0(boolean z, final CallableDescriptor a, final CallableDescriptor b, TypeConstructor c1, TypeConstructor c2) {
        Intrinsics.checkNotNullParameter(a, "$a");
        Intrinsics.checkNotNullParameter(b, "$b");
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        if (Intrinsics.areEqual(c1, c2)) {
            return true;
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = c1.getThis$0();
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor2 = c2.getThis$0();
        if ((classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor) && (classifierDescriptorMo7434getDeclarationDescriptor2 instanceof TypeParameterDescriptor)) {
            return INSTANCE.areTypeParametersEquivalent((TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor, (TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor2, z, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DescriptorEquivalenceForOverrides$areCallableDescriptorsEquivalent$overridingUtil$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                    return Boolean.valueOf(Intrinsics.areEqual(declarationDescriptor, a) && Intrinsics.areEqual(declarationDescriptor2, b));
                }
            });
        }
        return false;
    }

    private final boolean ownersEquivalent(DeclarationDescriptor a, DeclarationDescriptor b, Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> equivalentCallables, boolean allowCopiesFromTheSameDeclaration) {
        DeclarationDescriptor containingDeclaration = a.getPackageFragment();
        DeclarationDescriptor containingDeclaration2 = b.getPackageFragment();
        if ((containingDeclaration instanceof CallableMemberDescriptor) || (containingDeclaration2 instanceof CallableMemberDescriptor)) {
            return equivalentCallables.invoke(containingDeclaration, containingDeclaration2).booleanValue();
        }
        return areEquivalent$default(this, containingDeclaration, containingDeclaration2, allowCopiesFromTheSameDeclaration, false, 8, null);
    }
}
