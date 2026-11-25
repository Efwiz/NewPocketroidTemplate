package org.jetbrains.kotlin.resolve.calls;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.functions.FunctionInvokeDescriptor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.LambdaArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.psi.psiUtil.ReservedCheckingKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.OverrideResolver;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;

/* JADX INFO: loaded from: classes6.dex */
public class ValueArgumentsToParametersMapper {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "tracing";
        } else if (i == 2) {
            objArr[0] = "candidateCall";
        } else if (i != 3) {
            objArr[0] = "call";
        } else {
            objArr[0] = "languageVersionSettings";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/ValueArgumentsToParametersMapper";
        objArr[2] = "mapValueArgumentsToParameters";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public enum Status {
        ERROR(false),
        WEAK_ERROR(false),
        OK(true);

        private final boolean success;

        Status(boolean z) {
            this.success = z;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public Status compose(Status status) {
            Status status2 = ERROR;
            return (this == status2 || status == status2 || this == (status2 = WEAK_ERROR) || status == status2) ? status2 : this;
        }
    }

    public static <D extends CallableDescriptor> Status mapValueArgumentsToParameters(Call call, TracingStrategy tracingStrategy, MutableResolvedCall<D> mutableResolvedCall, LanguageVersionSettings languageVersionSettings) {
        if (call == null) {
            $$$reportNull$$$0(0);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(1);
        }
        if (mutableResolvedCall == null) {
            $$$reportNull$$$0(2);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(3);
        }
        Processor processor = new Processor(call, mutableResolvedCall, tracingStrategy, languageVersionSettings);
        processor.process();
        return processor.status;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Processor<D extends CallableDescriptor> {
        private final Call call;
        private final MutableResolvedCall<D> candidateCall;
        private final LanguageVersionSettings languageVersionSettings;
        private final Map<Name, ValueParameterDescriptor> parameterByName;
        private Map<Name, ValueParameterDescriptor> parameterByNameInOverriddenMethods;
        private final List<ValueParameterDescriptor> parameters;
        private final ProcessorState positionedOnly;
        private final ProcessorState positionedThenNamed;
        private Status status;
        private final TracingStrategy tracing;
        private final Set<ValueParameterDescriptor> usedParameters;
        private final Map<ValueParameterDescriptor, VarargValueArgument> varargs;

        private interface ProcessorState {
            ProcessorState processArraySetRHS(ValueArgument valueArgument);

            ProcessorState processNamedArgument(ValueArgument valueArgument);

            ProcessorState processPositionedArgument(ValueArgument valueArgument);
        }

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "candidateCall";
            } else if (i == 2) {
                objArr[0] = "tracing";
            } else if (i == 3) {
                objArr[0] = "languageVersionSettings";
            } else if (i != 4) {
                objArr[0] = "call";
            } else {
                objArr[0] = "newStatus";
            }
            objArr[1] = "org/jetbrains/kotlin/resolve/calls/ValueArgumentsToParametersMapper$Processor";
            if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "setStatus";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private Processor(Call call, MutableResolvedCall<D> mutableResolvedCall, TracingStrategy tracingStrategy, LanguageVersionSettings languageVersionSettings) {
            if (call == null) {
                $$$reportNull$$$0(0);
            }
            if (mutableResolvedCall == null) {
                $$$reportNull$$$0(1);
            }
            if (tracingStrategy == null) {
                $$$reportNull$$$0(2);
            }
            if (languageVersionSettings == null) {
                $$$reportNull$$$0(3);
            }
            this.varargs = new HashMap();
            this.usedParameters = new HashSet();
            this.status = Status.OK;
            this.positionedOnly = new ProcessorState() { // from class: org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper.Processor.1
                private int currentParameter = 0;

                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    objArr[0] = "argument";
                    objArr[1] = "org/jetbrains/kotlin/resolve/calls/ValueArgumentsToParametersMapper$Processor$1";
                    if (i == 1) {
                        objArr[2] = "processPositionedArgument";
                    } else if (i == 2) {
                        objArr[2] = "processArraySetRHS";
                    } else if (i != 3) {
                        objArr[2] = "processNamedArgument";
                    } else {
                        objArr[2] = "processArgument";
                    }
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                private int numberOfParametersForPositionedArguments() {
                    return Processor.this.call.getCallType() == Call.CallType.ARRAY_SET_METHOD ? Processor.this.parameters.size() - 1 : Processor.this.parameters.size();
                }

                public ValueParameterDescriptor nextValueParameter() {
                    if (this.currentParameter >= numberOfParametersForPositionedArguments()) {
                        return null;
                    }
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) Processor.this.parameters.get(this.currentParameter);
                    if (valueParameterDescriptor.getVarargElementType() == null) {
                        this.currentParameter++;
                    }
                    return valueParameterDescriptor;
                }

                @Override // org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper.Processor.ProcessorState
                public ProcessorState processNamedArgument(ValueArgument valueArgument) {
                    if (valueArgument == null) {
                        $$$reportNull$$$0(0);
                    }
                    return Processor.this.positionedThenNamed.processNamedArgument(valueArgument);
                }

                @Override // org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper.Processor.ProcessorState
                public ProcessorState processPositionedArgument(ValueArgument valueArgument) {
                    if (valueArgument == null) {
                        $$$reportNull$$$0(1);
                    }
                    processArgument(valueArgument, nextValueParameter());
                    return Processor.this.positionedOnly;
                }

                @Override // org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper.Processor.ProcessorState
                public ProcessorState processArraySetRHS(ValueArgument valueArgument) {
                    if (valueArgument == null) {
                        $$$reportNull$$$0(2);
                    }
                    processArgument(valueArgument, (ValueParameterDescriptor) CollectionsKt.lastOrNull(Processor.this.parameters));
                    return Processor.this.positionedOnly;
                }

                private void processArgument(ValueArgument valueArgument, ValueParameterDescriptor valueParameterDescriptor) {
                    if (valueArgument == null) {
                        $$$reportNull$$$0(3);
                    }
                    if (valueParameterDescriptor != null) {
                        Processor.this.usedParameters.add(valueParameterDescriptor);
                        Processor.this.putVararg(valueParameterDescriptor, valueArgument);
                    } else {
                        Processor.this.report(Errors.TOO_MANY_ARGUMENTS.on(valueArgument.getCallElement(), Processor.this.candidateCall.get$suspendPropertyDescriptor()));
                        Processor.this.setStatus(Status.WEAK_ERROR);
                    }
                }
            };
            this.positionedThenNamed = new ProcessorState() { // from class: org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper.Processor.2
                static final /* synthetic */ boolean $assertionsDisabled = false;

                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    objArr[0] = "argument";
                    objArr[1] = "org/jetbrains/kotlin/resolve/calls/ValueArgumentsToParametersMapper$Processor$2";
                    if (i == 1) {
                        objArr[2] = "processPositionedArgument";
                    } else if (i != 2) {
                        objArr[2] = "processNamedArgument";
                    } else {
                        objArr[2] = "processArraySetRHS";
                    }
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                /* JADX WARN: Type inference failed for: r0v2, types: [org.jetbrains.kotlin.descriptors.CallableDescriptor] */
                @Override // org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper.Processor.ProcessorState
                public ProcessorState processNamedArgument(ValueArgument valueArgument) {
                    Errors.BadNamedArgumentsTarget badNamedArgumentsTarget;
                    if (valueArgument == null) {
                        $$$reportNull$$$0(0);
                    }
                    ?? candidateDescriptor = Processor.this.candidateCall.get$suspendPropertyDescriptor();
                    ValueArgumentName argumentName = valueArgument.getArgumentName();
                    ValueParameterDescriptor parameterByNameInOverriddenMethods = (ValueParameterDescriptor) Processor.this.parameterByName.get(argumentName.getAsName());
                    KtSimpleNameExpression referenceExpression = argumentName.getReferenceExpression();
                    if (!Processor.this.languageVersionSettings.supportsFeature(LanguageFeature.YieldIsNoMoreReserved)) {
                        ReservedCheckingKt.checkReservedYield(referenceExpression, Processor.this.candidateCall.getTrace());
                    }
                    if (referenceExpression != null) {
                        if ((candidateDescriptor instanceof MemberDescriptor) && ((MemberDescriptor) candidateDescriptor).get$isExpect() && (candidateDescriptor.getContainingModule() instanceof ClassDescriptor)) {
                            Processor.this.report(Errors.NAMED_ARGUMENTS_NOT_ALLOWED.on(referenceExpression, Errors.BadNamedArgumentsTarget.EXPECTED_CLASS_MEMBER));
                        } else if (!candidateDescriptor.hasStableParameterNames()) {
                            if (candidateDescriptor instanceof FunctionInvokeDescriptor) {
                                badNamedArgumentsTarget = Errors.BadNamedArgumentsTarget.INVOKE_ON_FUNCTION_TYPE;
                            } else if (candidateDescriptor instanceof DeserializedCallableMemberDescriptor) {
                                badNamedArgumentsTarget = Errors.BadNamedArgumentsTarget.INTEROP_FUNCTION;
                            } else {
                                badNamedArgumentsTarget = Errors.BadNamedArgumentsTarget.NON_KOTLIN_FUNCTION;
                            }
                            Processor.this.report(Errors.NAMED_ARGUMENTS_NOT_ALLOWED.on(referenceExpression, badNamedArgumentsTarget));
                        }
                    }
                    if (candidateDescriptor.hasStableParameterNames() && referenceExpression != null && (candidateDescriptor instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) candidateDescriptor).getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                        if (parameterByNameInOverriddenMethods == null) {
                            parameterByNameInOverriddenMethods = Processor.this.getParameterByNameInOverriddenMethods(argumentName.getAsName());
                        }
                        if (parameterByNameInOverriddenMethods != null) {
                            Iterator<ValueParameterDescriptor> it = parameterByNameInOverriddenMethods.getOverriddenDescriptors().iterator();
                            while (it.hasNext()) {
                                if (OverrideResolver.INSTANCE.shouldReportParameterNameOverrideWarning(parameterByNameInOverriddenMethods, it.next())) {
                                    Processor.this.report(Errors.NAME_FOR_AMBIGUOUS_PARAMETER.on(referenceExpression));
                                }
                            }
                        }
                    }
                    if (parameterByNameInOverriddenMethods == null) {
                        if (referenceExpression != null) {
                            Processor.this.report(Errors.NAMED_PARAMETER_NOT_FOUND.on(referenceExpression, referenceExpression));
                        }
                        Processor.this.setStatus(Status.WEAK_ERROR);
                    } else {
                        if (referenceExpression != null) {
                            Processor.this.candidateCall.getTrace().record(BindingContext.REFERENCE_TARGET, referenceExpression, parameterByNameInOverriddenMethods);
                        }
                        if (!Processor.this.usedParameters.add(parameterByNameInOverriddenMethods)) {
                            if (referenceExpression != null) {
                                Processor.this.report(Errors.ARGUMENT_PASSED_TWICE.on(referenceExpression));
                            }
                            Processor.this.setStatus(Status.WEAK_ERROR);
                        } else {
                            Processor.this.putVararg(parameterByNameInOverriddenMethods, valueArgument);
                        }
                    }
                    return Processor.this.positionedThenNamed;
                }

                @Override // org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper.Processor.ProcessorState
                public ProcessorState processPositionedArgument(ValueArgument valueArgument) {
                    if (valueArgument == null) {
                        $$$reportNull$$$0(1);
                    }
                    Processor.this.report(Errors.MIXING_NAMED_AND_POSITIONED_ARGUMENTS.on(valueArgument.getCallElement()));
                    Processor.this.setStatus(Status.WEAK_ERROR);
                    return Processor.this.positionedThenNamed;
                }

                @Override // org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper.Processor.ProcessorState
                public ProcessorState processArraySetRHS(ValueArgument valueArgument) {
                    if (valueArgument == null) {
                        $$$reportNull$$$0(2);
                    }
                    throw new IllegalStateException("Array set RHS cannot appear after a named argument syntactically: " + valueArgument);
                }
            };
            this.call = call;
            this.tracing = tracingStrategy;
            this.candidateCall = mutableResolvedCall;
            List<ValueParameterDescriptor> valueParameters = mutableResolvedCall.get$suspendPropertyDescriptor().getValueParameters();
            this.parameters = valueParameters;
            this.languageVersionSettings = languageVersionSettings;
            this.parameterByName = new HashMap();
            for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
                this.parameterByName.put(valueParameterDescriptor.getShortName(), valueParameterDescriptor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ValueParameterDescriptor getParameterByNameInOverriddenMethods(Name name2) {
            if (this.parameterByNameInOverriddenMethods == null) {
                this.parameterByNameInOverriddenMethods = new HashMap();
                for (ValueParameterDescriptor valueParameterDescriptor : this.parameters) {
                    Iterator<ValueParameterDescriptor> it = valueParameterDescriptor.getOverriddenDescriptors().iterator();
                    while (it.hasNext()) {
                        this.parameterByNameInOverriddenMethods.put(it.next().getShortName(), valueParameterDescriptor);
                    }
                }
            }
            return this.parameterByNameInOverriddenMethods.get(name2);
        }

        public void process() {
            ProcessorState processorStateProcessNamedArgument = this.positionedOnly;
            boolean z = this.call.getCallType() == Call.CallType.ARRAY_SET_METHOD;
            Iterator<ValueArgument> it = CallUtilKt.getValueArgumentsInParentheses(this.call).iterator();
            while (it.hasNext()) {
                ValueArgument next = it.next();
                if (next.isNamed()) {
                    processorStateProcessNamedArgument = processorStateProcessNamedArgument.processNamedArgument(next);
                } else if (z && !it.hasNext()) {
                    processorStateProcessNamedArgument = processorStateProcessNamedArgument.processArraySetRHS(next);
                } else {
                    processorStateProcessNamedArgument = processorStateProcessNamedArgument.processPositionedArgument(next);
                }
            }
            for (Map.Entry<ValueParameterDescriptor, VarargValueArgument> entry : this.varargs.entrySet()) {
                this.candidateCall.recordValueArgument(entry.getKey(), entry.getValue());
            }
            processFunctionLiteralArguments();
            reportUnmappedParameters();
        }

        private void processFunctionLiteralArguments() {
            List<? extends LambdaArgument> functionLiteralArguments = this.call.getFunctionLiteralArguments();
            if (functionLiteralArguments.isEmpty()) {
                return;
            }
            LambdaArgument lambdaArgument = functionLiteralArguments.get(0);
            KtExpression argumentExpression = lambdaArgument.getArgumentExpression();
            if (this.parameters.isEmpty()) {
                CallUtilKt.reportTrailingLambdaErrorOr(this.candidateCall.getTrace(), argumentExpression, new Function1() { // from class: org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper$Processor$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return this.f$0.m7431xcc4cc8f7((KtExpression) obj);
                    }
                });
                setStatus(Status.ERROR);
            } else {
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.last((List) this.parameters);
                if (valueParameterDescriptor.getVarargElementType() != null) {
                    CallUtilKt.reportTrailingLambdaErrorOr(this.candidateCall.getTrace(), argumentExpression, new Function1() { // from class: org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper$Processor$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return Errors.VARARG_OUTSIDE_PARENTHESES.on((KtExpression) obj);
                        }
                    });
                    setStatus(Status.ERROR);
                } else if (!this.usedParameters.add(valueParameterDescriptor)) {
                    CallUtilKt.reportTrailingLambdaErrorOr(this.candidateCall.getTrace(), argumentExpression, new Function1() { // from class: org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper$Processor$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return this.f$0.m7432xda9e8d79((KtExpression) obj);
                        }
                    });
                    setStatus(Status.WEAK_ERROR);
                } else {
                    putVararg(valueParameterDescriptor, lambdaArgument);
                }
            }
            for (int i = 1; i < functionLiteralArguments.size(); i++) {
                KtExpression argumentExpression2 = functionLiteralArguments.get(i).getArgumentExpression();
                if (argumentExpression2 instanceof KtLambdaExpression) {
                    report(Errors.MANY_LAMBDA_EXPRESSION_ARGUMENTS.on(argumentExpression2));
                    KtLambdaExpression ktLambdaExpression = (KtLambdaExpression) argumentExpression2;
                    if (CallUtilKt.isTrailingLambdaOnNewLIne(ktLambdaExpression)) {
                        report(Errors.UNEXPECTED_TRAILING_LAMBDA_ON_A_NEW_LINE.on(ktLambdaExpression));
                    }
                }
                setStatus(Status.WEAK_ERROR);
            }
        }

        /* JADX INFO: renamed from: lambda$processFunctionLiteralArguments$0$org-jetbrains-kotlin-resolve-calls-ValueArgumentsToParametersMapper$Processor, reason: not valid java name */
        /* synthetic */ Diagnostic m7431xcc4cc8f7(KtExpression ktExpression) {
            return Errors.TOO_MANY_ARGUMENTS.on(ktExpression, this.candidateCall.get$suspendPropertyDescriptor());
        }

        /* JADX INFO: renamed from: lambda$processFunctionLiteralArguments$2$org-jetbrains-kotlin-resolve-calls-ValueArgumentsToParametersMapper$Processor, reason: not valid java name */
        /* synthetic */ Diagnostic m7432xda9e8d79(KtExpression ktExpression) {
            return Errors.TOO_MANY_ARGUMENTS.on(ktExpression, this.candidateCall.get$suspendPropertyDescriptor());
        }

        private void reportUnmappedParameters() {
            for (ValueParameterDescriptor valueParameterDescriptor : this.parameters) {
                if (!this.usedParameters.contains(valueParameterDescriptor)) {
                    if (ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor)) {
                        this.candidateCall.recordValueArgument(valueParameterDescriptor, DefaultValueArgument.DEFAULT);
                    } else if (valueParameterDescriptor.getVarargElementType() != null) {
                        this.candidateCall.recordValueArgument(valueParameterDescriptor, new VarargValueArgument());
                    } else {
                        this.tracing.noValueForParameter(this.candidateCall.getTrace(), valueParameterDescriptor);
                        setStatus(Status.ERROR);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void putVararg(ValueParameterDescriptor valueParameterDescriptor, ValueArgument valueArgument) {
            if (valueParameterDescriptor.getVarargElementType() != null) {
                this.varargs.computeIfAbsent(valueParameterDescriptor, new Function() { // from class: org.jetbrains.kotlin.resolve.calls.ValueArgumentsToParametersMapper$Processor$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ValueArgumentsToParametersMapper.Processor.lambda$putVararg$3((ValueParameterDescriptor) obj);
                    }
                }).addArgument(valueArgument);
                return;
            }
            LeafPsiElement spreadElement = valueArgument.getSpreadElement();
            if (spreadElement != null) {
                this.candidateCall.getTrace().report(Errors.NON_VARARG_SPREAD.onError(spreadElement));
                setStatus(Status.WEAK_ERROR);
            }
            this.candidateCall.recordValueArgument(valueParameterDescriptor, new ExpressionValueArgument(valueArgument));
        }

        static /* synthetic */ VarargValueArgument lambda$putVararg$3(ValueParameterDescriptor valueParameterDescriptor) {
            return new VarargValueArgument();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStatus(Status status) {
            if (status == null) {
                $$$reportNull$$$0(4);
            }
            this.status = this.status.compose(status);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void report(Diagnostic diagnostic) {
            this.candidateCall.getTrace().report(diagnostic);
        }
    }

    private ValueArgumentsToParametersMapper() {
    }
}
