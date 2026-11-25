package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.VisitorWrappersKt;

/* JADX INFO: compiled from: AnnotationUseSiteTargetChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ$\u0010\r\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J$\u0010\u0012\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001c\u0010\u0013\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J$\u0010\u0016\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001c\u0010\u0017\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J0\u0010\u0018\u001a\u00020\u0011*\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u001aH\u0002J$\u0010\u001d\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\f\u0010\u001e\u001a\u00020\u001c*\u00020\u0015H\u0002¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AnnotationUseSiteTargetChecker;", "", "()V", "check", "", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkAsTopLevelTypeReference", "topLevelTypeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "isReceiver", "", "checkDeclaration", "checkIfDelegatedProperty", "annotation", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "checkIfHasBackingField", "checkIfMutableProperty", "checkIfProperty", "diagnosticFactory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "checkTypeReference", "useSiteDescription", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationUseSiteTargetChecker {
    public static final AnnotationUseSiteTargetChecker INSTANCE = new AnnotationUseSiteTargetChecker();

    /* JADX INFO: compiled from: AnnotationUseSiteTargetChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnnotationUseSiteTarget.values().length];
            try {
                iArr[AnnotationUseSiteTarget.FIELD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AnnotationUseSiteTarget.PROPERTY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[AnnotationUseSiteTarget.PROPERTY_GETTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[AnnotationUseSiteTarget.PROPERTY_SETTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[AnnotationUseSiteTarget.SETTER_PARAMETER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[AnnotationUseSiteTarget.FILE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[AnnotationUseSiteTarget.RECEIVER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private AnnotationUseSiteTargetChecker() {
    }

    public final void check(KtAnnotated annotated, DeclarationDescriptor descriptor, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
        VariableDescriptor variableDescriptor;
        Intrinsics.checkNotNullParameter(annotated, "annotated");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        checkDeclaration(trace, annotated, languageVersionSettings, descriptor);
        if (annotated instanceof KtCallableDeclaration) {
            KtCallableDeclaration ktCallableDeclaration = (KtCallableDeclaration) annotated;
            KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = ktCallableDeclaration.mo7393getReceiverTypeReference();
            if (ktTypeReferenceMo7393getReceiverTypeReference != null) {
                INSTANCE.checkTypeReference(trace, ktTypeReferenceMo7393getReceiverTypeReference, languageVersionSettings, true);
            }
            KtTypeReference ktTypeReferenceMo7396getTypeReference = ktCallableDeclaration.mo7396getTypeReference();
            if (ktTypeReferenceMo7396getTypeReference != null) {
                INSTANCE.checkTypeReference(trace, ktTypeReferenceMo7396getTypeReference, languageVersionSettings, false);
            }
        }
        if (annotated instanceof KtFunction) {
            for (KtParameter ktParameter : ((KtFunction) annotated).getValueParameters()) {
                if (!ktParameter.hasValOrVar() && (variableDescriptor = (VariableDescriptor) trace.getContext().get(BindingContext.VALUE_PARAMETER, ktParameter)) != null) {
                    Intrinsics.checkNotNull(ktParameter);
                    checkDeclaration(trace, ktParameter, languageVersionSettings, variableDescriptor);
                    KtTypeReference ktTypeReferenceMo7396getTypeReference2 = ktParameter.mo7396getTypeReference();
                    if (ktTypeReferenceMo7396getTypeReference2 != null) {
                        INSTANCE.checkTypeReference(trace, ktTypeReferenceMo7396getTypeReference2, languageVersionSettings, false);
                    }
                }
            }
        }
    }

    private final void checkTypeReference(final BindingTrace bindingTrace, KtTypeReference ktTypeReference, final LanguageVersionSettings languageVersionSettings, boolean z) {
        checkAsTopLevelTypeReference(bindingTrace, ktTypeReference, languageVersionSettings, z);
        ktTypeReference.acceptChildren(VisitorWrappersKt.typeReferenceRecursiveVisitor(new Function1<KtTypeReference, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationUseSiteTargetChecker.checkTypeReference.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(KtTypeReference ktTypeReference2) {
                invoke2(ktTypeReference2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KtTypeReference typeReference) {
                Intrinsics.checkNotNullParameter(typeReference, "typeReference");
                AnnotationUseSiteTargetChecker.INSTANCE.checkAsTopLevelTypeReference(bindingTrace, typeReference, languageVersionSettings, false);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkAsTopLevelTypeReference(BindingTrace bindingTrace, KtTypeReference ktTypeReference, LanguageVersionSettings languageVersionSettings, boolean z) {
        AnnotationUseSiteTarget annotationUseSiteTarget;
        Diagnostic diagnosticOn;
        for (KtAnnotationEntry ktAnnotationEntry : ktTypeReference.getAnnotationEntries()) {
            KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
            if (useSiteTarget != null && (annotationUseSiteTarget = useSiteTarget.getAnnotationUseSiteTarget()) != null && (annotationUseSiteTarget != AnnotationUseSiteTarget.RECEIVER || !z)) {
                if (languageVersionSettings.supportsFeature(LanguageFeature.RestrictionOfWrongAnnotationsWithUseSiteTargetsOnTypes)) {
                    diagnosticOn = Errors.WRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET.on(ktAnnotationEntry, "undefined target", annotationUseSiteTarget.getRenderName());
                } else {
                    diagnosticOn = Errors.WRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET_ON_TYPE.on(ktAnnotationEntry, annotationUseSiteTarget.getRenderName());
                }
                Intrinsics.checkNotNull(diagnosticOn);
                DiagnosticUtilsKt.reportDiagnosticOnce(bindingTrace, diagnosticOn);
            }
        }
    }

    private final void checkDeclaration(BindingTrace bindingTrace, KtAnnotated ktAnnotated, LanguageVersionSettings languageVersionSettings, DeclarationDescriptor declarationDescriptor) {
        AnnotationUseSiteTarget annotationUseSiteTarget;
        DiagnosticFactory1<PsiElement, String> diagnosticFactory1;
        for (KtAnnotationEntry ktAnnotationEntry : ktAnnotated.getAnnotationEntries()) {
            KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
            if (useSiteTarget != null && (annotationUseSiteTarget = useSiteTarget.getAnnotationUseSiteTarget()) != null) {
                switch (WhenMappings.$EnumSwitchMapping$0[annotationUseSiteTarget.ordinal()]) {
                    case 1:
                        Intrinsics.checkNotNull(ktAnnotationEntry);
                        checkIfHasBackingField(bindingTrace, ktAnnotated, declarationDescriptor, ktAnnotationEntry);
                        break;
                    case 2:
                    case 3:
                        Intrinsics.checkNotNull(ktAnnotationEntry);
                        boolean zSupportsFeature = languageVersionSettings.supportsFeature(LanguageFeature.ProhibitUseSiteGetTargetAnnotations);
                        if (zSupportsFeature) {
                            diagnosticFactory1 = Errors.INAPPLICABLE_TARGET_ON_PROPERTY;
                        } else if (!zSupportsFeature) {
                            diagnosticFactory1 = Errors.INAPPLICABLE_TARGET_ON_PROPERTY_WARNING;
                        } else {
                            throw new NoWhenBranchMatchedException();
                        }
                        Intrinsics.checkNotNull(diagnosticFactory1);
                        checkIfProperty(bindingTrace, ktAnnotated, ktAnnotationEntry, diagnosticFactory1);
                        break;
                    case 4:
                        Intrinsics.checkNotNull(ktAnnotationEntry);
                        checkIfDelegatedProperty(bindingTrace, ktAnnotated, ktAnnotationEntry);
                        break;
                    case 5:
                        Intrinsics.checkNotNull(ktAnnotationEntry);
                        checkIfMutableProperty(bindingTrace, ktAnnotated, ktAnnotationEntry);
                        break;
                    case 6:
                        if (!(ktAnnotated instanceof KtParameter)) {
                            bindingTrace.report(Errors.INAPPLICABLE_PARAM_TARGET.on(ktAnnotationEntry));
                        } else {
                            VariableDescriptor variableDescriptor = (VariableDescriptor) bindingTrace.getContext().get(BindingContext.VALUE_PARAMETER, ktAnnotated);
                            DeclarationDescriptor containingDeclaration = variableDescriptor != null ? variableDescriptor.getContainingModule() : null;
                            if (!(containingDeclaration instanceof ConstructorDescriptor) || !((ConstructorDescriptor) containingDeclaration).isPrimary()) {
                                bindingTrace.report(Errors.INAPPLICABLE_PARAM_TARGET.on(ktAnnotationEntry));
                            } else if (!((KtParameter) ktAnnotated).hasValOrVar()) {
                                bindingTrace.report(Errors.REDUNDANT_ANNOTATION_TARGET.on(ktAnnotationEntry, annotationUseSiteTarget.getRenderName()));
                            }
                        }
                        break;
                    case 7:
                        Intrinsics.checkNotNull(ktAnnotationEntry);
                        checkIfMutableProperty(bindingTrace, ktAnnotated, ktAnnotationEntry);
                        break;
                    case 8:
                        Diagnostic diagnosticOn = Errors.INAPPLICABLE_FILE_TARGET.on(useSiteTarget);
                        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                        DiagnosticUtilsKt.reportDiagnosticOnce(bindingTrace, diagnosticOn);
                        break;
                    case 9:
                        Diagnostic diagnosticOn2 = Errors.WRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET.on(ktAnnotationEntry, "declaration", annotationUseSiteTarget.getRenderName());
                        Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                        DiagnosticUtilsKt.reportDiagnosticOnce(bindingTrace, diagnosticOn2);
                        break;
                }
            }
        }
    }

    private final void checkIfDelegatedProperty(BindingTrace bindingTrace, KtAnnotated ktAnnotated, KtAnnotationEntry ktAnnotationEntry) {
        if ((!(ktAnnotated instanceof KtProperty) || ((KtProperty) ktAnnotated).hasDelegate()) && !((ktAnnotated instanceof KtParameter) && ((KtParameter) ktAnnotated).hasValOrVar())) {
            return;
        }
        bindingTrace.report(Errors.INAPPLICABLE_TARGET_PROPERTY_HAS_NO_DELEGATE.on(ktAnnotationEntry));
    }

    private final void checkIfHasBackingField(BindingTrace bindingTrace, KtAnnotated ktAnnotated, DeclarationDescriptor declarationDescriptor, KtAnnotationEntry ktAnnotationEntry) {
        if ((ktAnnotated instanceof KtProperty) && ((KtProperty) ktAnnotated).hasDelegate() && (declarationDescriptor instanceof PropertyDescriptor) && !Intrinsics.areEqual(bindingTrace.get(BindingContext.BACKING_FIELD_REQUIRED, declarationDescriptor), (Object) true)) {
            bindingTrace.report(Errors.INAPPLICABLE_TARGET_PROPERTY_HAS_NO_BACKING_FIELD.on(ktAnnotationEntry));
        }
    }

    private final String useSiteDescription(KtAnnotationEntry ktAnnotationEntry) {
        AnnotationUseSiteTarget annotationUseSiteTarget;
        String renderName;
        KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
        return (useSiteTarget == null || (annotationUseSiteTarget = useSiteTarget.getAnnotationUseSiteTarget()) == null || (renderName = annotationUseSiteTarget.getRenderName()) == null) ? "unknown target" : renderName;
    }

    private final void checkIfMutableProperty(BindingTrace bindingTrace, KtAnnotated ktAnnotated, KtAnnotationEntry ktAnnotationEntry) {
        boolean zIsMutable;
        DiagnosticFactory1<PsiElement, String> INAPPLICABLE_TARGET_ON_PROPERTY = Errors.INAPPLICABLE_TARGET_ON_PROPERTY;
        Intrinsics.checkNotNullExpressionValue(INAPPLICABLE_TARGET_ON_PROPERTY, "INAPPLICABLE_TARGET_ON_PROPERTY");
        if (checkIfProperty(bindingTrace, ktAnnotated, ktAnnotationEntry, INAPPLICABLE_TARGET_ON_PROPERTY)) {
            if (ktAnnotated instanceof KtProperty) {
                zIsMutable = ((KtProperty) ktAnnotated).isVar();
            } else {
                zIsMutable = ktAnnotated instanceof KtParameter ? ((KtParameter) ktAnnotated).isMutable() : false;
            }
            if (zIsMutable) {
                return;
            }
            bindingTrace.report(Errors.INAPPLICABLE_TARGET_PROPERTY_IMMUTABLE.on(ktAnnotationEntry, useSiteDescription(ktAnnotationEntry)));
        }
    }

    private final boolean checkIfProperty(BindingTrace bindingTrace, KtAnnotated ktAnnotated, KtAnnotationEntry ktAnnotationEntry, DiagnosticFactory1<PsiElement, String> diagnosticFactory1) {
        boolean zHasValOrVar = false;
        if (ktAnnotated instanceof KtProperty) {
            if (!((KtProperty) ktAnnotated).isLocal()) {
                zHasValOrVar = true;
            }
        } else if (ktAnnotated instanceof KtParameter) {
            zHasValOrVar = ((KtParameter) ktAnnotated).hasValOrVar();
        }
        if (!zHasValOrVar) {
            bindingTrace.report(diagnosticFactory1.on(ktAnnotationEntry, useSiteDescription(ktAnnotationEntry)));
        }
        return zHasValOrVar;
    }
}
