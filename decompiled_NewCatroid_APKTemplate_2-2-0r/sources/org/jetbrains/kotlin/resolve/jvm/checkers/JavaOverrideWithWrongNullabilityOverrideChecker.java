package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypePreparator;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: JavaOverrideWithWrongNullabilityOverrideChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JavaOverrideWithWrongNullabilityOverrideChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "typePreparatorUnwrappingEnhancement", "Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaOverrideWithWrongNullabilityOverrideChecker implements DeclarationChecker {
    public static final JavaOverrideWithWrongNullabilityOverrideChecker INSTANCE = new JavaOverrideWithWrongNullabilityOverrideChecker();
    private static final KotlinTypePreparator typePreparatorUnwrappingEnhancement = new KotlinTypePreparator() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaOverrideWithWrongNullabilityOverrideChecker$typePreparatorUnwrappingEnhancement$1
        @Override // org.jetbrains.kotlin.types.checker.KotlinTypePreparator, org.jetbrains.kotlin.types.AbstractTypePreparator
        public UnwrappedType prepareType(KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            UnwrappedType unwrappedTypePrepareType = super.prepareType(type);
            KotlinType enhancementDeeply = TypeWithEnhancementKt.getEnhancementDeeply(unwrappedTypePrepareType);
            if (enhancementDeeply != null) {
                unwrappedTypePrepareType = enhancementDeeply;
            }
            return unwrappedTypePrepareType.unwrap();
        }
    };

    private JavaOverrideWithWrongNullabilityOverrideChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtModifierList modifierList;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
            if (callableMemberDescriptor.getOverriddenDescriptors().isEmpty() || (modifierList = declaration.getModifierList()) == null || !modifierList.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
                return;
            }
            DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getDeclarationDescriptor();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor == null) {
                return;
            }
            for (CallableMemberDescriptor callableMemberDescriptor2 : callableMemberDescriptor.getOverriddenDescriptors()) {
                if (callableMemberDescriptor2 instanceof JavaMethodDescriptor) {
                    final LinkedHashSet linkedHashSet = new LinkedHashSet();
                    OverridingUtil overridingUtilCreateWithTypePreparatorAndCustomSubtype = OverridingUtil.createWithTypePreparatorAndCustomSubtype(typePreparatorUnwrappingEnhancement, new Function2<KotlinType, KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaOverrideWithWrongNullabilityOverrideChecker$check$overridingUtilWithEnhancementUnwrapped$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Boolean invoke(KotlinType kotlinType, KotlinType kotlinType2) {
                            JavaNullabilityChecker.Companion companion = JavaNullabilityChecker.INSTANCE;
                            Intrinsics.checkNotNull(kotlinType);
                            Intrinsics.checkNotNull(kotlinType2);
                            boolean zIsNullableTypeAgainstNotNullTypeParameter = companion.isNullableTypeAgainstNotNullTypeParameter(kotlinType, kotlinType2);
                            Set<TypeParameterDescriptor> set = linkedHashSet;
                            if (zIsNullableTypeAgainstNotNullTypeParameter) {
                                Set<TypeParameterDescriptor> set2 = set;
                                ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                                CollectionsKt.addIfNotNull(set2, this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null);
                            }
                            return Boolean.valueOf(!zIsNullableTypeAgainstNotNullTypeParameter);
                        }
                    });
                    Intrinsics.checkNotNullExpressionValue(overridingUtilCreateWithTypePreparatorAndCustomSubtype, "createWithTypePreparatorAndCustomSubtype(...)");
                    CallableMemberDescriptor callableMemberDescriptor3 = callableMemberDescriptor2;
                    CallableDescriptor callableDescriptor = (CallableDescriptor) descriptor;
                    if (overridingUtilCreateWithTypePreparatorAndCustomSubtype.isOverridableBy(callableMemberDescriptor3, callableDescriptor, classDescriptor, true).getResult() != OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE && OverridingUtil.DEFAULT.isOverridableBy(callableMemberDescriptor3, callableDescriptor, classDescriptor, true).getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) {
                        FunctionDescriptor functionDescriptorSubstitute = ((JavaMethodDescriptor) callableMemberDescriptor2).substitute(TypeSubstitutor.create(new TypeSubstitution() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaOverrideWithWrongNullabilityOverrideChecker$check$unwrappedOverridden$1
                            @Override // org.jetbrains.kotlin.types.TypeSubstitution
                            /* JADX INFO: renamed from: get */
                            public TypeProjection mo7475get(KotlinType key) {
                                Intrinsics.checkNotNullParameter(key, "key");
                                return null;
                            }

                            @Override // org.jetbrains.kotlin.types.TypeSubstitution
                            public KotlinType prepareTopLevelType(KotlinType topLevelType, Variance position) {
                                Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
                                Intrinsics.checkNotNullParameter(position, "position");
                                KotlinType enhancementDeeply = TypeWithEnhancementKt.getEnhancementDeeply(topLevelType);
                                return enhancementDeeply == null ? topLevelType : enhancementDeeply;
                            }
                        }));
                        if (functionDescriptorSubstitute == null) {
                            functionDescriptorSubstitute = (FunctionDescriptor) callableMemberDescriptor2;
                        }
                        if (!linkedHashSet.isEmpty()) {
                            context.getTrace().report(ErrorsJvm.WRONG_TYPE_PARAMETER_NULLABILITY_FOR_JAVA_OVERRIDE.on(declaration, (TypeParameterDescriptor) kotlin.collections.CollectionsKt.first(linkedHashSet)));
                            return;
                        } else {
                            context.getTrace().report(ErrorsJvm.WRONG_NULLABILITY_FOR_JAVA_OVERRIDE.on(declaration, descriptor, functionDescriptorSubstitute));
                            return;
                        }
                    }
                }
            }
        }
    }
}
