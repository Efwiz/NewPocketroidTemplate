package org.jetbrains.kotlin.diagnostics;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRenderer;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: diagnosticUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a0\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bH\u0086\bø\u0001\u0000\u001a<\u0010\u000e\u001a\u00020\u0005\"\n\b\u0000\u0010\u000f\u0018\u0001*\u00020\u00102\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u00020\r0\u000bH\u0086\bø\u0001\u0000\u001a0\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bH\u0086\bø\u0001\u0000\u001a\u0012\u0010\u0012\u001a\u00020\u0005*\u00020\u00072\u0006\u0010\u0013\u001a\u00020\r\u001a3\u0010\u0014\u001a\u00020\u0005*\u00020\u00072\u0006\u0010\u0015\u001a\u00020\r2\u001a\u0010\u0016\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00180\u0017\"\u0006\u0012\u0002\b\u00030\u0018¢\u0006\u0002\u0010\u0019\u001a+\u0010\u001a\u001a\u00020\u0005\"\b\b\u0000\u0010\u001b*\u00020\r*\u00020\u001c2\u0006\u0010\u0013\u001a\u0002H\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007¢\u0006\u0002\u0010\u001f\u001a(\u0010 \u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030!2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010#\u001a(\u0010%\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030!2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010#\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006&"}, d2 = {"isScalaLikeEqualsBlock", "", "expression", "Lorg/jetbrains/kotlin/psi/KtElement;", "reportOnDeclaration", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "what", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "reportOnDeclarationAs", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "reportOnDeclarationOrFail", "reportDiagnosticOnce", "diagnostic", "reportDiagnosticOnceWrtDiagnosticFactoryList", "diagnosticToReport", "diagnosticFactories", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/diagnostics/Diagnostic;[Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;)V", "reportFromPlugin", "D", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "ext", "Lorg/jetbrains/kotlin/diagnostics/rendering/DefaultErrorMessages$Extension;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;Lorg/jetbrains/kotlin/diagnostics/Diagnostic;Lorg/jetbrains/kotlin/diagnostics/rendering/DefaultErrorMessages$Extension;)V", "reportTypeMismatchDueToScalaLikeNamedFunctionSyntax", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expressionType", "reportTypeMismatchDueToTypeProjection", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticUtilsKt {

    /* JADX INFO: compiled from: diagnosticUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Severity.values().length];
            try {
                iArr[Severity.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Severity.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Severity.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean reportTypeMismatchDueToTypeProjection(final ResolutionContext<?> resolutionContext, KtElement expression, KotlinType expectedType, KotlinType kotlinType) {
        Pair pair;
        KotlinType kotlinType2;
        KtExpression calleeExpression;
        Intrinsics.checkNotNullParameter(resolutionContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (!TypeUtils.contains(expectedType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection.1
            /* JADX WARN: Code duplicated, block: B:11:0x0020  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType unwrappedType) {
                boolean z;
                UnwrappedType unwrappedType2 = unwrappedType;
                if (TypeUtils.noExpectedType(unwrappedType2)) {
                    z = false;
                } else {
                    Intrinsics.checkNotNull(unwrappedType);
                    if (TypeUtilsKt.isAnyOrNullableAny(unwrappedType2) || TypeUtilsKt.isNothing(unwrappedType2) || TypeUtilsKt.isNullableNothing(unwrappedType2)) {
                        z = true;
                    } else {
                        z = false;
                    }
                }
                return Boolean.valueOf(z);
            }
        })) {
            return false;
        }
        CallPosition callPosition = resolutionContext.callPosition;
        if (callPosition instanceof CallPosition.ValueArgumentPosition) {
            pair = TuplesKt.to(((CallPosition.ValueArgumentPosition) resolutionContext.callPosition).getResolvedCall(), new Function1<CallableDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(CallableDescriptor f) {
                    Intrinsics.checkNotNullParameter(f, "f");
                    ValueParameterDescriptor valueParameterDescriptor = f.getValueParameters().get(((CallPosition.ValueArgumentPosition) resolutionContext.callPosition).getValueParameter().getIndex());
                    Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "get(...)");
                    return CallResolverUtilKt.getEffectiveExpectedType(valueParameterDescriptor, ((CallPosition.ValueArgumentPosition) resolutionContext.callPosition).getValueArgument(), resolutionContext);
                }
            });
        } else if (callPosition instanceof CallPosition.ExtensionReceiverPosition) {
            pair = TuplesKt.to(((CallPosition.ExtensionReceiverPosition) resolutionContext.callPosition).getResolvedCall(), new Function1<CallableDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection.3
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(CallableDescriptor f) {
                    Intrinsics.checkNotNullParameter(f, "f");
                    ReceiverParameterDescriptor extensionReceiverParameter = f.getExtensionReceiverParameter();
                    if (extensionReceiverParameter != null) {
                        return extensionReceiverParameter.getType();
                    }
                    return null;
                }
            });
        } else if (callPosition instanceof CallPosition.PropertyAssignment) {
            if (((CallPosition.PropertyAssignment) resolutionContext.callPosition).getIsLeft()) {
                return false;
            }
            KtExpression leftPart = ((CallPosition.PropertyAssignment) resolutionContext.callPosition).getLeftPart();
            BindingContext context = resolutionContext.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(leftPart, context);
            if (resolvedCall == null) {
                return false;
            }
            pair = TuplesKt.to(resolvedCall, new Function1<CallableDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection.4
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(CallableDescriptor f) {
                    PropertySetterDescriptor setter;
                    List<ValueParameterDescriptor> valueParameters;
                    ValueParameterDescriptor valueParameterDescriptor;
                    Intrinsics.checkNotNullParameter(f, "f");
                    PropertyDescriptor propertyDescriptor = f instanceof PropertyDescriptor ? (PropertyDescriptor) f : null;
                    if (propertyDescriptor == null || (setter = propertyDescriptor.getSetter()) == null || (valueParameters = setter.getValueParameters()) == null || (valueParameterDescriptor = valueParameters.get(0)) == null) {
                        return null;
                    }
                    return valueParameterDescriptor.getType();
                }
            });
        } else {
            if (callPosition instanceof CallPosition.Unknown ? true : callPosition instanceof CallPosition.CallableReferenceRhs) {
                return false;
            }
            throw new NoWhenBranchMatchedException();
        }
        ResolvedCall resolvedCall2 = (ResolvedCall) pair.component1();
        Function1 function1 = (Function1) pair.component2();
        KotlinType smartCastDispatchReceiverType = resolvedCall2.getSmartCastDispatchReceiverType();
        if (smartCastDispatchReceiverType == null) {
            ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall2.mo7304getDispatchReceiver();
            if (receiverValueMo7304getDispatchReceiver == null) {
                return false;
            }
            smartCastDispatchReceiverType = receiverValueMo7304getDispatchReceiver.getType();
        }
        Intrinsics.checkNotNull(smartCastDispatchReceiverType);
        CallableDescriptor original = resolvedCall2.getResultingDescriptor().getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        CallableDescriptor callableDescriptorSubstitute = original.substitute(CapturedTypeConstructorKt.wrapWithCapturingSubstitution(TypeConstructorSubstitution.INSTANCE.create(smartCastDispatchReceiverType), false).buildSubstitutor());
        if (callableDescriptorSubstitute == null || (kotlinType2 = (KotlinType) function1.invoke(callableDescriptorSubstitute)) == null || !TypeUtils.contains(kotlinType2, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection.5
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType unwrappedType) {
                Intrinsics.checkNotNull(unwrappedType);
                return Boolean.valueOf(CapturedTypeConstructorKt.isCaptured(unwrappedType));
            }
        })) {
            return false;
        }
        if (TypeUtilsKt.isNothing(expectedType)) {
            if (resolutionContext.callPosition instanceof CallPosition.PropertyAssignment) {
                BindingTrace bindingTrace = resolutionContext.trace;
                DiagnosticFactory1<KtExpression, DeclarationDescriptor> diagnosticFactory1 = Errors.SETTER_PROJECTED_OUT;
                KtExpression leftPart2 = ((CallPosition.PropertyAssignment) resolutionContext.callPosition).getLeftPart();
                if (leftPart2 == null) {
                    return false;
                }
                bindingTrace.report(diagnosticFactory1.on(leftPart2, resolvedCall2.getResultingDescriptor()));
            } else {
                Call call = resolvedCall2.getCall();
                Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
                if (resolvedCall2 instanceof VariableAsFunctionResolvedCall) {
                    calleeExpression = ((VariableAsFunctionResolvedCall) resolvedCall2).getVariableCall().getCall().getCalleeExpression();
                } else {
                    calleeExpression = call.getCalleeExpression();
                }
                BindingTrace trace = resolutionContext.trace;
                Intrinsics.checkNotNullExpressionValue(trace, "trace");
                Diagnostic diagnosticOn = Errors.MEMBER_PROJECTED_OUT.on(calleeExpression != null ? calleeExpression : call.getCallElement(), original, smartCastDispatchReceiverType);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                reportDiagnosticOnce(trace, diagnosticOn);
            }
        } else {
            if (kotlinType == null) {
                return false;
            }
            resolutionContext.trace.report(Errors.TYPE_MISMATCH_DUE_TO_TYPE_PROJECTIONS.on(expression, new TypeMismatchDueToTypeProjectionsData(expectedType, kotlinType, smartCastDispatchReceiverType, original)));
        }
        return true;
    }

    public static final void reportDiagnosticOnce(BindingTrace bindingTrace, Diagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(bindingTrace, "<this>");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Collection<Diagnostic> collectionForElement = bindingTrace.getContext().getDiagnostics().noSuppression().forElement(diagnostic.getPsiElement());
        if (!(collectionForElement instanceof Collection) || !collectionForElement.isEmpty()) {
            Iterator<T> it = collectionForElement.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((Diagnostic) it.next()).getFactory(), diagnostic.getFactory())) {
                    return;
                }
            }
        }
        bindingTrace.report(diagnostic);
    }

    public static final void reportDiagnosticOnceWrtDiagnosticFactoryList(BindingTrace bindingTrace, Diagnostic diagnosticToReport, DiagnosticFactory<?>... diagnosticFactories) {
        Intrinsics.checkNotNullParameter(bindingTrace, "<this>");
        Intrinsics.checkNotNullParameter(diagnosticToReport, "diagnosticToReport");
        Intrinsics.checkNotNullParameter(diagnosticFactories, "diagnosticFactories");
        Collection<Diagnostic> collectionForElement = bindingTrace.getContext().getDiagnostics().noSuppression().forElement(diagnosticToReport.getPsiElement());
        if (!(collectionForElement instanceof Collection) || !collectionForElement.isEmpty()) {
            for (Diagnostic diagnostic : collectionForElement) {
                if (Intrinsics.areEqual(diagnostic.getFactory(), diagnosticToReport.getFactory())) {
                    return;
                }
                for (DiagnosticFactory<?> diagnosticFactory : diagnosticFactories) {
                    if (Intrinsics.areEqual(diagnosticFactory, diagnostic.getFactory())) {
                        return;
                    }
                }
            }
        }
        bindingTrace.report(diagnosticToReport);
    }

    public static final boolean reportTypeMismatchDueToScalaLikeNamedFunctionSyntax(ResolutionContext<?> resolutionContext, KtElement expression, KotlinType expectedType, KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(resolutionContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (kotlinType == null || !FunctionTypesKt.isFunctionType(kotlinType) || FunctionTypesKt.isFunctionType(expectedType) || !isScalaLikeEqualsBlock(expression)) {
            return false;
        }
        resolutionContext.trace.report(Errors.TYPE_MISMATCH_DUE_TO_EQUALS_LAMBDA_IN_FUN.on(expression, expectedType));
        return true;
    }

    private static final boolean isScalaLikeEqualsBlock(KtElement ktElement) {
        if (ktElement instanceof KtLambdaExpression) {
            PsiElement parent = ((KtLambdaExpression) ktElement).getLightParent();
            if ((parent instanceof KtNamedFunction) && ((KtNamedFunction) parent).mo7390getEqualsToken() != null) {
                return true;
            }
        }
        return false;
    }

    public static final void reportOnDeclaration(BindingTrace trace, DeclarationDescriptor descriptor, Function1<? super PsiElement, ? extends Diagnostic> what) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(what, "what");
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(descriptor);
        if (psiElementDescriptorToDeclaration != null) {
            trace.report(what.invoke(psiElementDescriptorToDeclaration));
        }
    }

    public static final void reportOnDeclarationOrFail(BindingTrace trace, DeclarationDescriptor descriptor, Function1<? super PsiElement, ? extends Diagnostic> what) {
        Unit unit;
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(what, "what");
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(descriptor);
        if (psiElementDescriptorToDeclaration != null) {
            trace.report(what.invoke(psiElementDescriptorToDeclaration));
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            throw new AssertionError("No declaration for " + descriptor);
        }
    }

    public static final /* synthetic */ <T extends KtDeclaration> void reportOnDeclarationAs(BindingTrace trace, DeclarationDescriptor descriptor, Function1<? super T, ? extends Diagnostic> what) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(what, "what");
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(descriptor);
        Unit unit = null;
        if (psiElementDescriptorToDeclaration != null) {
            Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
            KtDeclaration ktDeclaration = (KtDeclaration) psiElementDescriptorToDeclaration;
            if (ktDeclaration != null) {
                trace.report(what.invoke(ktDeclaration));
                unit = Unit.INSTANCE;
            }
            if (unit != null) {
                unit = Unit.INSTANCE;
            } else {
                StringBuilder sbAppend = new StringBuilder("Declaration for ").append(descriptor).append(" is expected to be ");
                Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
                throw new AssertionError(sbAppend.append(Reflection.getOrCreateKotlinClass(KtDeclaration.class).getSimpleName()).append(", actual declaration: ").append(psiElementDescriptorToDeclaration).toString());
            }
        }
        if (unit == null) {
            throw new AssertionError("No declaration for " + descriptor);
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Please register DefaultErrorMessages.Extension in moment of DiagnosticFactory initialization by calling initializeFactoryNamesAndDefaultErrorMessages method instead of initializeFactoryNames", replaceWith = @ReplaceWith(expression = "report(diagnostic)", imports = {}))
    public static final <D extends Diagnostic> void reportFromPlugin(DiagnosticSink diagnosticSink, D diagnostic, DefaultErrorMessages.Extension ext) {
        Intrinsics.checkNotNullParameter(diagnosticSink, "<this>");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(ext, "ext");
        DiagnosticRenderer<?> diagnosticRenderer = ext.getMap().get(diagnostic.getFactory());
        if (!(diagnosticRenderer instanceof DiagnosticRenderer)) {
            diagnosticRenderer = null;
        }
        if (diagnosticRenderer == null) {
            throw new IllegalStateException(("Renderer not found for diagnostic " + diagnostic.getFactory().getName()).toString());
        }
        RenderedDiagnostic<?> renderedDiagnostic = new RenderedDiagnostic<>(diagnostic, diagnosticRenderer);
        int i = WhenMappings.$EnumSwitchMapping$0[diagnostic.getSeverity().ordinal()];
        if (i == 1) {
            diagnosticSink.report(Errors.PLUGIN_ERROR.on(diagnostic.getPsiElement(), renderedDiagnostic));
        } else if (i == 2) {
            diagnosticSink.report(Errors.PLUGIN_WARNING.on(diagnostic.getPsiElement(), renderedDiagnostic));
        } else {
            if (i != 3) {
                return;
            }
            diagnosticSink.report(Errors.PLUGIN_INFO.on(diagnostic.getPsiElement(), renderedDiagnostic));
        }
    }
}
