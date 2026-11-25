package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertyAccessorDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation1;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.resolve.typeBinding.TypeArgumentBinding;
import org.jetbrains.kotlin.resolve.typeBinding.TypeBinding;
import org.jetbrains.kotlin.resolve.typeBinding.TypeBindingKt;
import org.jetbrains.kotlin.types.EnrichedProjectionKind;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.TypeCheckingProcedure;

/* JADX INFO: compiled from: VarianceChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 02\u00020\u0001:\u00010B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u000e\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0016\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001eJ\u0010\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010 \u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u000e\u0010!\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u001eJ\u001c\u0010\"\u001a\u00020\u0012*\u00020#2\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010$\u001a\u00020%H\u0002J#\u0010&\u001a\u0004\u0018\u00010\u0012*\u00020'2\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010(\u001a\u00020%H\u0002¢\u0006\u0002\u0010)J\"\u0010&\u001a\u00020\u0012*\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010,\u001a\u00020-2\u0006\u0010(\u001a\u00020%H\u0002J\u001a\u0010&\u001a\u00020\u0012*\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010(\u001a\u00020%H\u0002J\f\u0010.\u001a\u00020%*\u00020/H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/resolve/VarianceCheckerCore;", "", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "diagnosticSink", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "manualVariance", "Lorg/jetbrains/kotlin/resolve/ManualVariance;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;Lorg/jetbrains/kotlin/resolve/ManualVariance;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "getContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "check", "", "c", "Lorg/jetbrains/kotlin/resolve/TopDownAnalysisContext;", "checkCallableDeclaration", "", "trace", "declaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "checkClassHeader", "klass", "Lorg/jetbrains/kotlin/psi/KtClass;", "checkClasses", "checkMember", "member", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "checkMembers", "isIrrelevant", "recordPrivateToThisIfNeeded", "checkTypeParameters", "Lorg/jetbrains/kotlin/psi/KtTypeParameterListOwner;", "typePosition", "Lorg/jetbrains/kotlin/types/Variance;", "checkTypePosition", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "position", "(Lorg/jetbrains/kotlin/psi/KtTypeReference;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/types/Variance;)Ljava/lang/Boolean;", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "containingType", "Lorg/jetbrains/kotlin/types/KotlinType;", "varianceWithManual", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VarianceCheckerCore {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final BindingContext context;
    private final DiagnosticSink diagnosticSink;
    private final LanguageVersionSettings languageVersionSettings;
    private final ManualVariance manualVariance;

    /* JADX INFO: compiled from: VarianceChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EnrichedProjectionKind.values().length];
            try {
                iArr[EnrichedProjectionKind.OUT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EnrichedProjectionKind.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[EnrichedProjectionKind.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[EnrichedProjectionKind.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public VarianceCheckerCore(BindingContext context, DiagnosticSink diagnosticSink, ManualVariance manualVariance, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(diagnosticSink, "diagnosticSink");
        this.context = context;
        this.diagnosticSink = diagnosticSink;
        this.manualVariance = manualVariance;
        this.languageVersionSettings = languageVersionSettings;
    }

    public /* synthetic */ VarianceCheckerCore(BindingContext bindingContext, DiagnosticSink diagnosticSink, ManualVariance manualVariance, LanguageVersionSettings languageVersionSettings, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bindingContext, diagnosticSink, (i & 4) != 0 ? null : manualVariance, (i & 8) != 0 ? null : languageVersionSettings);
    }

    public final BindingContext getContext() {
        return this.context;
    }

    public final void check(TopDownAnalysisContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        checkClasses(c);
        checkMembers(c);
    }

    private final void checkClasses(TopDownAnalysisContext c) {
        Map<KtClassOrObject, ClassDescriptorWithResolutionScopes> declaredClasses = c.getDeclaredClasses();
        Intrinsics.checkNotNull(declaredClasses);
        for (KtClassOrObject ktClassOrObject : declaredClasses.keySet()) {
            if (ktClassOrObject instanceof KtClass) {
                checkClassHeader((KtClass) ktClassOrObject);
            }
        }
    }

    public final boolean checkClassHeader(KtClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        boolean zAnd = true;
        for (KtSuperTypeListEntry ktSuperTypeListEntry : klass.getSuperTypeListEntries()) {
            Companion companion = INSTANCE;
            KtTypeReference typeReference = ktSuperTypeListEntry.getTypeReference();
            zAnd = companion.and(zAnd, typeReference != null ? checkTypePosition(typeReference, this.context, Variance.OUT_VARIANCE) : null);
        }
        return checkTypeParameters(klass, this.context, Variance.OUT_VARIANCE) & zAnd;
    }

    private final void checkMembers(TopDownAnalysisContext c) {
        Map<KtCallableDeclaration, CallableMemberDescriptor> members = c.getMembers();
        Intrinsics.checkNotNullExpressionValue(members, "getMembers(...)");
        for (Map.Entry<KtCallableDeclaration, CallableMemberDescriptor> entry : members.entrySet()) {
            KtCallableDeclaration key = entry.getKey();
            CallableMemberDescriptor value = entry.getValue();
            Intrinsics.checkNotNull(key);
            Intrinsics.checkNotNull(value);
            checkMember(key, value);
        }
    }

    public final boolean checkMember(KtCallableDeclaration member, CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return DescriptorVisibilities.isPrivate(descriptor.getVisibility()) || checkCallableDeclaration(this.context, member, descriptor);
    }

    private final Variance varianceWithManual(TypeParameterDescriptor typeParameterDescriptor) {
        if (this.manualVariance != null && Intrinsics.areEqual(typeParameterDescriptor.getOriginal(), this.manualVariance.getDescriptor())) {
            return this.manualVariance.getVariance();
        }
        Variance variance = typeParameterDescriptor.getVariance();
        Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
        return variance;
    }

    public final void recordPrivateToThisIfNeeded(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        CallableMemberDescriptor callableMemberDescriptor = descriptor;
        if (isIrrelevant(callableMemberDescriptor) || !Intrinsics.areEqual(descriptor.getVisibility(), DescriptorVisibilities.PRIVATE)) {
            return;
        }
        SourceElement source = descriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        KtCallableDeclaration ktCallableDeclaration = psi instanceof KtCallableDeclaration ? (KtCallableDeclaration) psi : null;
        if (ktCallableDeclaration == null || checkCallableDeclaration(this.context, ktCallableDeclaration, callableMemberDescriptor)) {
            return;
        }
        INSTANCE.recordPrivateToThis(descriptor);
    }

    private final boolean checkCallableDeclaration(BindingContext trace, KtCallableDeclaration declaration, CallableDescriptor descriptor) {
        if (isIrrelevant(descriptor)) {
            return true;
        }
        boolean zCheckTypeParameters = checkTypeParameters(declaration, trace, Variance.IN_VARIANCE);
        Companion companion = INSTANCE;
        KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = declaration.mo7393getReceiverTypeReference();
        boolean zAnd = companion.and(zCheckTypeParameters, ktTypeReferenceMo7393getReceiverTypeReference != null ? checkTypePosition(ktTypeReferenceMo7393getReceiverTypeReference, trace, Variance.IN_VARIANCE) : null);
        for (KtParameter ktParameter : declaration.getValueParameters()) {
            Companion companion2 = INSTANCE;
            KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
            zAnd = companion2.and(zAnd, ktTypeReferenceMo7396getTypeReference != null ? checkTypePosition(ktTypeReferenceMo7396getTypeReference, trace, Variance.IN_VARIANCE) : null);
        }
        Variance variance = ((descriptor instanceof VariableDescriptor) && ((VariableDescriptor) descriptor).isVar()) ? Variance.INVARIANT : Variance.OUT_VARIANCE;
        Companion companion3 = INSTANCE;
        TypeBinding<PsiElement> typeBindingCreateTypeBindingForReturnType = TypeBindingKt.createTypeBindingForReturnType(declaration, trace);
        return companion3.and(zAnd, typeBindingCreateTypeBindingForReturnType != null ? Boolean.valueOf(checkTypePosition(typeBindingCreateTypeBindingForReturnType, variance)) : null);
    }

    private final boolean checkTypeParameters(KtTypeParameterListOwner ktTypeParameterListOwner, BindingContext bindingContext, Variance variance) {
        Iterator<KtTypeParameter> it = ktTypeParameterListOwner.getTypeParameters().iterator();
        boolean zAnd = true;
        while (true) {
            Boolean boolCheckTypePosition = null;
            if (!it.hasNext()) {
                break;
            }
            KtTypeParameter next = it.next();
            Companion companion = INSTANCE;
            KtTypeReference extendsBound = next.getExtendsBound();
            if (extendsBound != null) {
                boolCheckTypePosition = checkTypePosition(extendsBound, bindingContext, variance);
            }
            zAnd = companion.and(zAnd, boolCheckTypePosition);
        }
        for (KtTypeConstraint ktTypeConstraint : ktTypeParameterListOwner.getTypeConstraints()) {
            Companion companion2 = INSTANCE;
            KtTypeReference boundTypeReference = ktTypeConstraint.getBoundTypeReference();
            zAnd = companion2.and(zAnd, boundTypeReference != null ? checkTypePosition(boundTypeReference, bindingContext, variance) : null);
        }
        return zAnd;
    }

    private final Boolean checkTypePosition(KtTypeReference ktTypeReference, BindingContext bindingContext, Variance variance) {
        TypeBinding<KtTypeElement> typeBindingCreateTypeBinding = TypeBindingKt.createTypeBinding(ktTypeReference, bindingContext);
        if (typeBindingCreateTypeBinding != null) {
            return Boolean.valueOf(checkTypePosition(typeBindingCreateTypeBinding, variance));
        }
        return null;
    }

    private final boolean checkTypePosition(TypeBinding<? extends PsiElement> typeBinding, Variance variance) {
        return checkTypePosition(typeBinding, typeBinding.getType(), variance);
    }

    private final boolean checkTypePosition(TypeBinding<? extends PsiElement> typeBinding, KotlinType kotlinType, Variance variance) {
        ClassifierDescriptor this$0 = typeBinding.getType().getConstructor().getThis$0();
        if (this$0 instanceof TypeParameterDescriptor) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) this$0;
            Variance varianceVarianceWithManual = varianceWithManual(typeParameterDescriptor);
            if (!varianceVarianceWithManual.allowsPosition(variance) && !typeBinding.getType().getAnnotations().hasAnnotation(StandardNames.FqNames.unsafeVariance)) {
                VarianceConflictDiagnosticData varianceConflictDiagnosticData = new VarianceConflictDiagnosticData(kotlinType, typeParameterDescriptor, variance);
                if (typeBinding.getIsArgumentFromQualifier()) {
                    DiagnosticSink diagnosticSink = this.diagnosticSink;
                    DiagnosticFactoryForDeprecation1<PsiElement, VarianceConflictDiagnosticData> diagnosticFactoryForDeprecation1 = Errors.TYPE_VARIANCE_CONFLICT;
                    LanguageVersionSettingsImpl languageVersionSettingsImpl = this.languageVersionSettings;
                    if (languageVersionSettingsImpl == null) {
                        languageVersionSettingsImpl = LanguageVersionSettingsImpl.DEFAULT;
                    }
                    diagnosticSink.report(diagnosticFactoryForDeprecation1.on(languageVersionSettingsImpl, typeBinding.getPsiElement(), varianceConflictDiagnosticData));
                } else if (typeBinding.isInAbbreviation()) {
                    this.diagnosticSink.report(Errors.TYPE_VARIANCE_CONFLICT_IN_EXPANDED_TYPE.on(typeBinding.getPsiElement(), varianceConflictDiagnosticData));
                } else {
                    this.diagnosticSink.report(Errors.TYPE_VARIANCE_CONFLICT.getErrorFactory().on(typeBinding.getPsiElement(), varianceConflictDiagnosticData));
                }
            }
            return varianceVarianceWithManual.allowsPosition(variance);
        }
        Iterator it = typeBinding.getArguments().iterator();
        boolean zCheckTypePosition = true;
        while (it.hasNext()) {
            TypeArgumentBinding typeArgumentBinding = (TypeArgumentBinding) it.next();
            Variance varianceOpposite = null;
            if ((typeArgumentBinding != null ? typeArgumentBinding.getTypeParameter() : null) != null && !typeArgumentBinding.getProjection().isStarProjection()) {
                TypeParameterDescriptor typeParameter = typeArgumentBinding.getTypeParameter();
                Intrinsics.checkNotNull(typeParameter);
                EnrichedProjectionKind effectiveProjectionKind = TypeCheckingProcedure.getEffectiveProjectionKind(typeParameter, typeArgumentBinding.getProjection());
                Intrinsics.checkNotNull(effectiveProjectionKind);
                int i = WhenMappings.$EnumSwitchMapping$0[effectiveProjectionKind.ordinal()];
                if (i == 1) {
                    varianceOpposite = variance;
                } else if (i == 2) {
                    varianceOpposite = variance.opposite();
                } else if (i == 3) {
                    varianceOpposite = Variance.INVARIANT;
                } else if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                if (varianceOpposite != null) {
                    zCheckTypePosition &= checkTypePosition(typeArgumentBinding.getBinding(), kotlinType, varianceOpposite);
                }
            }
        }
        return zCheckTypePosition;
    }

    private final boolean isIrrelevant(CallableDescriptor descriptor) {
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null) {
            return true;
        }
        List<TypeParameterDescriptor> parameters = classDescriptor.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeParameterDescriptor> list = parameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            if (varianceWithManual(typeParameterDescriptor) != Variance.INVARIANT) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: compiled from: VarianceChecker.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u001c\u0010\u0007\u001a\u00020\b*\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0082\u0004¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/VarianceCheckerCore$Companion;", "", "()V", "recordPrivateToThis", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "and", "", "other", "(ZLjava/lang/Boolean;)Z", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void recordPrivateToThis(CallableMemberDescriptor descriptor) {
            if (descriptor instanceof FunctionDescriptorImpl) {
                ((FunctionDescriptorImpl) descriptor).setVisibility(DescriptorVisibilities.PRIVATE_TO_THIS);
                return;
            }
            if (descriptor instanceof PropertyDescriptorImpl) {
                PropertyDescriptorImpl propertyDescriptorImpl = (PropertyDescriptorImpl) descriptor;
                propertyDescriptorImpl.setVisibility(DescriptorVisibilities.PRIVATE_TO_THIS);
                for (PropertyAccessorDescriptor propertyAccessorDescriptor : propertyDescriptorImpl.getAccessors()) {
                    Intrinsics.checkNotNull(propertyAccessorDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.PropertyAccessorDescriptorImpl");
                    ((PropertyAccessorDescriptorImpl) propertyAccessorDescriptor).setVisibility(DescriptorVisibilities.PRIVATE_TO_THIS);
                }
                return;
            }
            throw new IllegalStateException("Unexpected descriptor type: " + descriptor.getClass().getName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean and(boolean z, Boolean bool) {
            return bool == null ? z : z & bool.booleanValue();
        }
    }
}
