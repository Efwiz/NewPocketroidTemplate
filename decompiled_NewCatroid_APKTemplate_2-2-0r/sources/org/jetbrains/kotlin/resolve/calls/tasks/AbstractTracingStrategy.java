package org.jetbrains.kotlin.resolve.calls.tasks;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.NavigatablePsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceErrorData;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: loaded from: classes6.dex */
public abstract class AbstractTracingStrategy implements TracingStrategy {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final Call call;
    protected final KtExpression reference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 40:
                objArr[0] = "call";
                break;
            case 2:
            case 4:
            case 6:
            case 8:
            case 12:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 26:
            case 29:
            case 31:
            case 33:
                objArr[0] = "trace";
                break;
            case 3:
                objArr[0] = "candidates";
                break;
            case 5:
                objArr[0] = "valueParameter";
                break;
            case 7:
                objArr[0] = "expectedReceiver";
                break;
            case 9:
                objArr[0] = "receiverParameter";
                break;
            case 10:
                objArr[0] = "receiverArgument";
                break;
            case 11:
                objArr[0] = "c";
                break;
            case 14:
            case 34:
                objArr[0] = "descriptor";
                break;
            case 16:
                objArr[0] = "resolvedCalls";
                break;
            case 18:
            case 20:
                objArr[0] = "descriptors";
                break;
            case 23:
                objArr[0] = "languageVersionSettings";
                break;
            case 27:
                objArr[0] = "classDescriptor";
                break;
            case 28:
                objArr[0] = "explicitReceiverKind";
                break;
            case 30:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 32:
                objArr[0] = "binaryExpression";
                break;
            case 35:
            case 37:
                objArr[0] = "context";
                break;
            case 36:
            case 38:
                objArr[0] = "data";
                break;
            case 39:
            default:
                objArr[0] = "reference";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/tasks/AbstractTracingStrategy";
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "recordAmbiguity";
                break;
            case 4:
            case 5:
                objArr[2] = "noValueForParameter";
                break;
            case 6:
            case 7:
                objArr[2] = "missingReceiver";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "wrongReceiverType";
                break;
            case 12:
                objArr[2] = "noReceiverAllowed";
                break;
            case 13:
            case 14:
                objArr[2] = "wrongNumberOfTypeArguments";
                break;
            case 15:
            case 16:
                objArr[2] = "ambiguity";
                break;
            case 17:
            case 18:
                objArr[2] = "noneApplicable";
                break;
            case 19:
            case 20:
                objArr[2] = "cannotCompleteResolve";
                break;
            case 21:
                objArr[2] = "instantiationOfAbstractClass";
                break;
            case 22:
            case 23:
                objArr[2] = "recursiveType";
                break;
            case 24:
                objArr[2] = "abstractSuperCall";
                break;
            case 25:
                objArr[2] = "abstractSuperCallWarning";
                break;
            case 26:
            case 27:
            case 28:
                objArr[2] = "nestedClassAccessViaInstanceReference";
                break;
            case 29:
            case 30:
                objArr[2] = "unsafeCall";
                break;
            case 31:
            case 32:
                objArr[2] = "reportUnsafeCallOnBinaryExpression";
                break;
            case 33:
            case 34:
                objArr[2] = "invisibleMember";
                break;
            case 35:
            case 36:
                objArr[2] = "typeInferenceFailed";
                break;
            case 37:
            case 38:
            case 39:
            case 40:
                objArr[2] = "typeInferenceFailedDiagnostic";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected AbstractTracingStrategy(KtExpression ktExpression, Call call) {
        if (ktExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (call == null) {
            $$$reportNull$$$0(1);
        }
        this.reference = ktExpression;
        this.call = call;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void recordAmbiguity(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(2);
        }
        if (collection == null) {
            $$$reportNull$$$0(3);
        }
        HashSet hashSet = new HashSet();
        Iterator<? extends ResolvedCall<D>> it = collection.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().get$suspendPropertyDescriptor());
        }
        bindingTrace.record(BindingContext.AMBIGUOUS_REFERENCE_TARGET, this.reference, hashSet);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void noValueForParameter(BindingTrace bindingTrace, ValueParameterDescriptor valueParameterDescriptor) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(4);
        }
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        bindingTrace.report(Errors.NO_VALUE_FOR_PARAMETER.on(CallUtilKt.getValueArgumentListOrElement(this.call), valueParameterDescriptor));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void missingReceiver(BindingTrace bindingTrace, ReceiverParameterDescriptor receiverParameterDescriptor) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(6);
        }
        if (receiverParameterDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        bindingTrace.report(Errors.MISSING_RECEIVER.on(this.reference, receiverParameterDescriptor.getType()));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void wrongReceiverType(BindingTrace bindingTrace, ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverValue receiverValue, ResolutionContext<?> resolutionContext) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(8);
        }
        if (receiverParameterDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        if (receiverValue == null) {
            $$$reportNull$$$0(10);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(11);
        }
        KtExpression expression = receiverValue instanceof ExpressionReceiver ? ((ExpressionReceiver) receiverValue).getExpression() : this.reference;
        if (DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection(resolutionContext, expression, receiverParameterDescriptor.getType(), receiverValue.getType())) {
            return;
        }
        bindingTrace.report(Errors.TYPE_MISMATCH.on(expression, receiverParameterDescriptor.getType(), receiverValue.getType()));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void noReceiverAllowed(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(12);
        }
        bindingTrace.report(Errors.NO_RECEIVER_ALLOWED.on(this.reference));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void wrongNumberOfTypeArguments(BindingTrace bindingTrace, int i, CallableDescriptor callableDescriptor) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(13);
        }
        if (callableDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        NavigatablePsiElement typeArgumentList = this.call.getTypeArgumentList();
        DiagnosticFactory2<KtElement, Integer, DeclarationDescriptor> diagnosticFactory2 = Errors.WRONG_NUMBER_OF_TYPE_ARGUMENTS;
        if (typeArgumentList == null) {
            typeArgumentList = this.reference;
        }
        bindingTrace.report(diagnosticFactory2.on(typeArgumentList, Integer.valueOf(i), callableDescriptor));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void ambiguity(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(15);
        }
        if (collection == null) {
            $$$reportNull$$$0(16);
        }
        bindingTrace.report(Errors.OVERLOAD_RESOLUTION_AMBIGUITY.on(this.reference, collection));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void noneApplicable(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(17);
        }
        if (collection == null) {
            $$$reportNull$$$0(18);
        }
        bindingTrace.report(Errors.NONE_APPLICABLE.on(this.reference, collection));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void cannotCompleteResolve(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(19);
        }
        if (collection == null) {
            $$$reportNull$$$0(20);
        }
        bindingTrace.report(Errors.CANNOT_COMPLETE_RESOLVE.on(this.reference, collection));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void instantiationOfAbstractClass(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(21);
        }
        bindingTrace.report(Errors.CREATING_AN_INSTANCE_OF_ABSTRACT_CLASS.on(this.call.getCallElement()));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void recursiveType(BindingTrace bindingTrace, LanguageVersionSettings languageVersionSettings, boolean z) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(22);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(23);
        }
        KtExpression calleeExpression = this.call.getCalleeExpression();
        if (calleeExpression == null) {
            return;
        }
        if (z) {
            bindingTrace.report(Errors.TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM_IN_AUGMENTED_ASSIGNMENT.on(languageVersionSettings, calleeExpression));
        } else {
            bindingTrace.report(Errors.TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM.on(languageVersionSettings, calleeExpression));
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void abstractSuperCall(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(24);
        }
        bindingTrace.report(Errors.ABSTRACT_SUPER_CALL.on(this.reference));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void abstractSuperCallWarning(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(25);
        }
        bindingTrace.report(Errors.ABSTRACT_SUPER_CALL_WARNING.on(this.reference));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void nestedClassAccessViaInstanceReference(BindingTrace bindingTrace, ClassDescriptor classDescriptor, ExplicitReceiverKind explicitReceiverKind) {
        String strAsString;
        if (bindingTrace == null) {
            $$$reportNull$$$0(26);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        if (explicitReceiverKind == null) {
            $$$reportNull$$$0(28);
        }
        if (explicitReceiverKind == ExplicitReceiverKind.NO_EXPLICIT_RECEIVER) {
            DeclarationDescriptor importableDescriptor = DescriptorUtilsKt.getImportableDescriptor(classDescriptor);
            if (DescriptorUtils.getFqName(importableDescriptor).isSafe()) {
                FqName fqNameFromTopLevelClass = DescriptorUtils.getFqNameFromTopLevelClass(importableDescriptor);
                if (this.reference.getLightParent() instanceof KtCallableReferenceExpression) {
                    strAsString = fqNameFromTopLevelClass.parent() + "::" + classDescriptor.getShortName();
                } else {
                    strAsString = fqNameFromTopLevelClass.asString();
                }
                bindingTrace.report(Errors.NESTED_CLASS_SHOULD_BE_QUALIFIED.on(this.reference, classDescriptor, strAsString));
                return;
            }
        }
        bindingTrace.report(Errors.NESTED_CLASS_ACCESSED_VIA_INSTANCE_REFERENCE.on(this.reference, classDescriptor));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void unsafeCall(BindingTrace bindingTrace, KotlinType kotlinType, boolean z) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(29);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(30);
        }
        ASTNode callOperationNode = this.call.getCallOperationNode();
        if (callOperationNode != null && !z) {
            bindingTrace.report(Errors.UNSAFE_CALL.on(callOperationNode.getPsi(), kotlinType));
            return;
        }
        KtElement callElement = this.call.getCallElement();
        if (callElement instanceof KtBinaryExpression) {
            reportUnsafeCallOnBinaryExpression(bindingTrace, (KtBinaryExpression) callElement);
        } else if (z) {
            bindingTrace.report(Errors.UNSAFE_IMPLICIT_INVOKE_CALL.on(this.reference, kotlinType));
        } else {
            bindingTrace.report(Errors.UNSAFE_CALL.on(this.reference, kotlinType));
        }
    }

    private void reportUnsafeCallOnBinaryExpression(BindingTrace bindingTrace, KtBinaryExpression ktBinaryExpression) {
        Name nameForOperationSymbol;
        if (bindingTrace == null) {
            $$$reportNull$$$0(31);
        }
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(32);
        }
        KtOperationReferenceExpression operationReference = ktBinaryExpression.getOperationReference();
        boolean z = operationReference.getReferencedNameElementType() == KtTokens.IDENTIFIER;
        if (z) {
            nameForOperationSymbol = Name.identifier(operationReference.getText());
        } else {
            nameForOperationSymbol = OperatorConventions.getNameForOperationSymbol((KtToken) operationReference.getReferencedNameElementType());
        }
        if (nameForOperationSymbol == null) {
            return;
        }
        KtExpression left = ktBinaryExpression.getLeft();
        KtExpression right = ktBinaryExpression.getRight();
        if (left == null || right == null) {
            return;
        }
        if (z) {
            bindingTrace.report(Errors.UNSAFE_INFIX_CALL.on(this.reference, left, nameForOperationSymbol.asString(), right));
            return;
        }
        boolean zIsInOrNotInOperation = KtPsiUtil.isInOrNotInOperation(ktBinaryExpression);
        KtExpression ktExpression = zIsInOrNotInOperation ? right : left;
        if (!zIsInOrNotInOperation) {
            left = right;
        }
        bindingTrace.report(Errors.UNSAFE_OPERATOR_CALL.on(this.reference, ktExpression, nameForOperationSymbol.asString(), left));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void invisibleMember(BindingTrace bindingTrace, DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(33);
        }
        if (declarationDescriptorWithVisibility == null) {
            $$$reportNull$$$0(34);
        }
        bindingTrace.report(Errors.INVISIBLE_MEMBER.on(this.call.getCallElement(), declarationDescriptorWithVisibility, declarationDescriptorWithVisibility.getVisibilityImpl(), declarationDescriptorWithVisibility));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void typeInferenceFailed(ResolutionContext<?> resolutionContext, InferenceErrorData inferenceErrorData) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(35);
        }
        if (inferenceErrorData == null) {
            $$$reportNull$$$0(36);
        }
        Diagnostic diagnosticTypeInferenceFailedDiagnostic = typeInferenceFailedDiagnostic(resolutionContext, inferenceErrorData, this.reference, this.call);
        if (diagnosticTypeInferenceFailedDiagnostic != null) {
            resolutionContext.trace.report(diagnosticTypeInferenceFailedDiagnostic);
        }
    }

    public static Diagnostic typeInferenceFailedDiagnostic(ResolutionContext<?> resolutionContext, InferenceErrorData inferenceErrorData, KtExpression ktExpression, Call call) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(37);
        }
        if (inferenceErrorData == null) {
            $$$reportNull$$$0(38);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(39);
        }
        if (call == null) {
            $$$reportNull$$$0(40);
        }
        ConstraintSystem constraintSystem = inferenceErrorData.constraintSystem;
        ConstraintSystemStatus status = constraintSystem.getStatus();
        if (status.hasErrorInConstrainingTypes()) {
            return null;
        }
        if (status.hasOnlyErrorsDerivedFrom(ConstraintPositionKind.EXPECTED_TYPE_POSITION)) {
            KotlinType returnType = inferenceErrorData.descriptor.getReturnType();
            if (returnType == null) {
                return null;
            }
            KotlinType kotlinTypeSubstitute = ConstraintSystemUtilsKt.filterConstraintsOut(constraintSystem, ConstraintPositionKind.EXPECTED_TYPE_POSITION).getResultingSubstitutor().substitute(returnType, Variance.OUT_VARIANCE);
            if (DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection(resolutionContext, call.getCallElement(), inferenceErrorData.expectedType, kotlinTypeSubstitute)) {
                return null;
            }
            return Errors.TYPE_INFERENCE_EXPECTED_TYPE_MISMATCH.on(call.getCallElement(), inferenceErrorData.expectedType, kotlinTypeSubstitute);
        }
        if (status.hasCannotCaptureTypesError()) {
            return Errors.TYPE_INFERENCE_CANNOT_CAPTURE_TYPES.on(ktExpression, inferenceErrorData);
        }
        if (status.hasViolatedUpperBound()) {
            return Errors.TYPE_INFERENCE_UPPER_BOUND_VIOLATED.on(ktExpression, inferenceErrorData);
        }
        if (status.hasParameterConstraintError()) {
            return Errors.TYPE_INFERENCE_PARAMETER_CONSTRAINT_ERROR.on(ktExpression, inferenceErrorData);
        }
        if (status.hasConflictingConstraints()) {
            return Errors.TYPE_INFERENCE_CONFLICTING_SUBSTITUTIONS.on(ktExpression, inferenceErrorData);
        }
        if (status.hasTypeInferenceIncorporationError()) {
            return Errors.TYPE_INFERENCE_INCORPORATION_ERROR.on(ktExpression);
        }
        if (status.hasTypeParameterWithUnsatisfiedOnlyInputTypesError()) {
            return Errors.TYPE_INFERENCE_ONLY_INPUT_TYPES.getErrorFactory().on(ktExpression, inferenceErrorData.descriptor.getTypeParameters().get(0));
        }
        return Errors.TYPE_INFERENCE_NO_INFORMATION_FOR_PARAMETER.on(ktExpression, inferenceErrorData);
    }
}
