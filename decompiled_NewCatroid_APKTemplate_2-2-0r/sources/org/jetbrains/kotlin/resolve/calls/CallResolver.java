package org.jetbrains.kotlin.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.BindingTraceFilter;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ModifierCheckerCore;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallCandidateResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceUtilKt;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.OldResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategyForImplicitConstructorDelegationCall;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategyForInvoke;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategyImpl;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference;
import org.jetbrains.kotlin.resolve.calls.tower.PSICallResolver;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.ResolveArgumentsMode;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.util.PerformanceCounter;

/* JADX INFO: loaded from: classes6.dex */
public class CallResolver {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final PerformanceCounter callResolvePerfCounter = PerformanceCounter.INSTANCE.create("Call resolve", ExpressionTypingVisitorDispatcher.typeInfoPerfCounter);
    private PSICallResolver PSICallResolver;
    private ArgumentTypeResolver argumentTypeResolver;
    private final KotlinBuiltIns builtIns;
    private CallCompleter callCompleter;
    private final DataFlowValueFactory dataFlowValueFactory;
    private ExpressionTypingServices expressionTypingServices;
    private GenericCandidateResolver genericCandidateResolver;
    private final LanguageVersionSettings languageVersionSettings;
    private NewResolutionOldInference newResolutionOldInference;
    private SyntheticScopes syntheticScopes;
    private TypeResolver typeResolver;

    /* JADX WARN: Code duplicated, block: B:17:0x0037 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:91:0x0165  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 11 && i != 14 && i != 19 && i != 24 && i != 27 && i != 32 && i != 37 && i != 60 && i != 105 && i != 44 && i != 45) {
            switch (i) {
                default:
                    switch (i) {
                        case 80:
                        case 81:
                        case 82:
                        case 83:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 62:
                case 63:
                case 64:
                case 65:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 11 && i != 14 && i != 19 && i != 24 && i != 27 && i != 32 && i != 37 && i != 60 && i != 105 && i != 44 && i != 45) {
            switch (i) {
                case 62:
                case 63:
                case 64:
                case 65:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 80:
                        case 81:
                        case 82:
                        case 83:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "languageVersionSettings";
                break;
            case 2:
                objArr[0] = "dataFlowValueFactory";
                break;
            case 3:
                objArr[0] = "expressionTypingServices";
                break;
            case 4:
                objArr[0] = "typeResolver";
                break;
            case 5:
                objArr[0] = "argumentTypeResolver";
                break;
            case 6:
                objArr[0] = "callCompleter";
                break;
            case 7:
                objArr[0] = "newResolutionOldInference";
                break;
            case 8:
                objArr[0] = "PSICallResolver";
                break;
            case 9:
            case 87:
                objArr[0] = "syntheticScopes";
                break;
            case 10:
            case 13:
            case 15:
            case 20:
            case 25:
            case 28:
            case 33:
            case 38:
            case 41:
            case 46:
            case 50:
            case 61:
            case 66:
            case 68:
            case 76:
            case 86:
            case 90:
            case 92:
            case 95:
            case 98:
            case 102:
                objArr[0] = "context";
                break;
            case 11:
            case 14:
            case 19:
            case 24:
            case 27:
            case 32:
            case 37:
            case 44:
            case 45:
            case 60:
            case 62:
            case 63:
            case 64:
            case 65:
            case 80:
            case 81:
            case 82:
            case 83:
            case 105:
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/CallResolver";
                break;
            case 12:
                objArr[0] = "nameExpression";
                break;
            case 16:
            case 21:
            case 48:
            case 53:
            case 57:
            case 75:
            case 77:
            case 88:
                objArr[0] = "call";
                break;
            case 17:
            case 69:
                objArr[0] = "functionReference";
                break;
            case 18:
            case 22:
            case 29:
            case 34:
                objArr[0] = "name";
                break;
            case 23:
            case 26:
            case 35:
            case 43:
            case 89:
            case 94:
            case 97:
            case 101:
            case 104:
                objArr[0] = "tracing";
                break;
            case 30:
            case 39:
                objArr[0] = "referenceExpression";
                break;
            case 31:
            case 36:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 40:
            case 42:
                objArr[0] = "candidates";
                break;
            case 47:
            case 51:
            case 67:
                objArr[0] = "expression";
                break;
            case 49:
            case 54:
                objArr[0] = "functionDescriptors";
                break;
            case 52:
                objArr[0] = "receiver";
                break;
            case 55:
            case 71:
                objArr[0] = "trace";
                break;
            case 56:
            case 72:
                objArr[0] = "scope";
                break;
            case 58:
                objArr[0] = "expectedType";
                break;
            case 59:
            case 73:
                objArr[0] = "dataFlowInfo";
                break;
            case 70:
                objArr[0] = "constructedType";
                break;
            case 74:
                objArr[0] = "constructorDescriptor";
                break;
            case 78:
            case 84:
                objArr[0] = "calleeExpression";
                break;
            case 79:
                objArr[0] = "currentClassDescriptor";
                break;
            case 85:
                objArr[0] = "superType";
                break;
            case 91:
                objArr[0] = "candidate";
                break;
            case 93:
            case 103:
                objArr[0] = "resolutionTask";
                break;
            case 96:
            case 99:
                objArr[0] = "results";
                break;
            case 100:
                objArr[0] = "traceToResolveCall";
                break;
            default:
                objArr[0] = "builtIns";
                break;
        }
        if (i == 11) {
            objArr[1] = "resolveSimpleProperty";
        } else if (i == 14) {
            objArr[1] = "resolveCallForMember";
        } else if (i == 19 || i == 24) {
            objArr[1] = "resolveCallWithGivenName";
        } else if (i == 27) {
            objArr[1] = "resolveCallForInvoke";
        } else if (i == 32 || i == 37) {
            objArr[1] = "computeTasksAndResolveCall";
        } else if (i == 60) {
            objArr[1] = "resolveFunctionCall";
        } else if (i == 105) {
            objArr[1] = "doResolveCall";
        } else if (i == 44) {
            objArr[1] = "computeTasksFromCandidatesAndResolvedCall";
        } else if (i != 45) {
            switch (i) {
                case 62:
                case 63:
                case 64:
                case 65:
                    objArr[1] = "resolveFunctionCall";
                    break;
                default:
                    switch (i) {
                        case 80:
                        case 81:
                        case 82:
                        case 83:
                            objArr[1] = "resolveConstructorDelegationCall";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/resolve/calls/CallResolver";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "resolveBinaryCall";
        }
        switch (i) {
            case 3:
                objArr[2] = "setExpressionTypingServices";
                break;
            case 4:
                objArr[2] = "setTypeResolver";
                break;
            case 5:
                objArr[2] = "setArgumentTypeResolver";
                break;
            case 6:
                objArr[2] = "setCallCompleter";
                break;
            case 7:
                objArr[2] = "setResolutionOldInference";
                break;
            case 8:
                objArr[2] = "setPSICallResolver";
                break;
            case 9:
                objArr[2] = "setSyntheticScopes";
                break;
            case 10:
                objArr[2] = "resolveSimpleProperty";
                break;
            case 11:
            case 14:
            case 19:
            case 24:
            case 27:
            case 32:
            case 37:
            case 44:
            case 45:
            case 60:
            case 62:
            case 63:
            case 64:
            case 65:
            case 80:
            case 81:
            case 82:
            case 83:
            case 105:
                break;
            case 12:
            case 13:
                objArr[2] = "resolveCallForMember";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[2] = "resolveCallWithGivenName";
                break;
            case 25:
            case 26:
                objArr[2] = "resolveCallForInvoke";
                break;
            case 28:
            case 29:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
                objArr[2] = "computeTasksAndResolveCall";
                break;
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
                objArr[2] = "computeTasksFromCandidatesAndResolvedCall";
                break;
            case 46:
            case 47:
            case 48:
            case 49:
                objArr[2] = "resolveCollectionLiteralCallWithGivenDescriptor";
                break;
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
                objArr[2] = "resolveEqualsCallWithGivenDescriptors";
                break;
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 61:
                objArr[2] = "resolveFunctionCall";
                break;
            case 66:
            case 67:
                objArr[2] = "resolveCallForConstructor";
                break;
            case 68:
            case 69:
            case 70:
                objArr[2] = "resolveConstructorCall";
                break;
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
                objArr[2] = "resolveConstructorDelegationCall";
                break;
            case 84:
                objArr[2] = "calcReportOn";
                break;
            case 85:
            case 86:
            case 87:
                objArr[2] = "prepareCandidatesAndContextForConstructorCall";
                break;
            case 88:
            case 89:
            case 90:
            case 91:
                objArr[2] = "resolveCallWithKnownCandidate";
                break;
            case 92:
            case 93:
            case 94:
                objArr[2] = "doResolveCallOrGetCachedResults";
                break;
            case 95:
            case 96:
            case 97:
                objArr[2] = "completeTypeInferenceDependentOnFunctionLiterals";
                break;
            case 98:
            case 99:
            case 100:
            case 101:
                objArr[2] = "cacheResults";
                break;
            case 102:
            case 103:
            case 104:
                objArr[2] = "doResolveCall";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 11 && i != 14 && i != 19 && i != 24 && i != 27 && i != 32 && i != 37 && i != 60 && i != 105 && i != 44 && i != 45) {
            switch (i) {
                case 62:
                case 63:
                case 64:
                case 65:
                    break;
                default:
                    switch (i) {
                        case 80:
                        case 81:
                        case 82:
                        case 83:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public CallResolver(KotlinBuiltIns kotlinBuiltIns, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(0);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(1);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(2);
        }
        this.builtIns = kotlinBuiltIns;
        this.languageVersionSettings = languageVersionSettings;
        this.dataFlowValueFactory = dataFlowValueFactory;
    }

    @Inject
    public void setExpressionTypingServices(ExpressionTypingServices expressionTypingServices) {
        if (expressionTypingServices == null) {
            $$$reportNull$$$0(3);
        }
        this.expressionTypingServices = expressionTypingServices;
    }

    @Inject
    public void setTypeResolver(TypeResolver typeResolver) {
        if (typeResolver == null) {
            $$$reportNull$$$0(4);
        }
        this.typeResolver = typeResolver;
    }

    @Inject
    public void setArgumentTypeResolver(ArgumentTypeResolver argumentTypeResolver) {
        if (argumentTypeResolver == null) {
            $$$reportNull$$$0(5);
        }
        this.argumentTypeResolver = argumentTypeResolver;
    }

    @Inject
    public void setGenericCandidateResolver(GenericCandidateResolver genericCandidateResolver) {
        this.genericCandidateResolver = genericCandidateResolver;
    }

    @Inject
    public void setCallCompleter(CallCompleter callCompleter) {
        if (callCompleter == null) {
            $$$reportNull$$$0(6);
        }
        this.callCompleter = callCompleter;
    }

    @Inject
    public void setResolutionOldInference(NewResolutionOldInference newResolutionOldInference) {
        if (newResolutionOldInference == null) {
            $$$reportNull$$$0(7);
        }
        this.newResolutionOldInference = newResolutionOldInference;
    }

    @Inject
    public void setPSICallResolver(PSICallResolver pSICallResolver) {
        if (pSICallResolver == null) {
            $$$reportNull$$$0(8);
        }
        this.PSICallResolver = pSICallResolver;
    }

    @Inject
    public void setSyntheticScopes(SyntheticScopes syntheticScopes) {
        if (syntheticScopes == null) {
            $$$reportNull$$$0(9);
        }
        this.syntheticScopes = syntheticScopes;
    }

    public OverloadResolutionResults<VariableDescriptor> resolveSimpleProperty(BasicCallResolutionContext basicCallResolutionContext) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(10);
        }
        KtSimpleNameExpression ktSimpleNameExpression = (KtSimpleNameExpression) basicCallResolutionContext.call.getCalleeExpression();
        OverloadResolutionResults<VariableDescriptor> overloadResolutionResultsComputeTasksAndResolveCall = computeTasksAndResolveCall(basicCallResolutionContext, ktSimpleNameExpression.getReferencedNameAsName(), ktSimpleNameExpression, NewResolutionOldInference.ResolutionKind.Variable.INSTANCE);
        if (overloadResolutionResultsComputeTasksAndResolveCall == null) {
            $$$reportNull$$$0(11);
        }
        return overloadResolutionResultsComputeTasksAndResolveCall;
    }

    public OverloadResolutionResults<CallableDescriptor> resolveCallForMember(KtSimpleNameExpression ktSimpleNameExpression, BasicCallResolutionContext basicCallResolutionContext) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(12);
        }
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(13);
        }
        OverloadResolutionResults<CallableDescriptor> overloadResolutionResultsComputeTasksAndResolveCall = computeTasksAndResolveCall(basicCallResolutionContext, ktSimpleNameExpression.getReferencedNameAsName(), ktSimpleNameExpression, NewResolutionOldInference.ResolutionKind.CallableReference.INSTANCE);
        if (overloadResolutionResultsComputeTasksAndResolveCall == null) {
            $$$reportNull$$$0(14);
        }
        return overloadResolutionResultsComputeTasksAndResolveCall;
    }

    public OverloadResolutionResults<FunctionDescriptor> resolveCallWithGivenName(ResolutionContext<?> resolutionContext, Call call, KtReferenceExpression ktReferenceExpression, Name name2) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(15);
        }
        if (call == null) {
            $$$reportNull$$$0(16);
        }
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(17);
        }
        if (name2 == null) {
            $$$reportNull$$$0(18);
        }
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsComputeTasksAndResolveCall = computeTasksAndResolveCall(BasicCallResolutionContext.create(resolutionContext, call, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS), name2, ktReferenceExpression, NewResolutionOldInference.ResolutionKind.Function.INSTANCE);
        if (overloadResolutionResultsComputeTasksAndResolveCall == null) {
            $$$reportNull$$$0(19);
        }
        return overloadResolutionResultsComputeTasksAndResolveCall;
    }

    public OverloadResolutionResults<FunctionDescriptor> resolveCallWithGivenName(ResolutionContext<?> resolutionContext, Call call, Name name2, TracingStrategy tracingStrategy) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(20);
        }
        if (call == null) {
            $$$reportNull$$$0(21);
        }
        if (name2 == null) {
            $$$reportNull$$$0(22);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(23);
        }
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsComputeTasksAndResolveCall = computeTasksAndResolveCall(BasicCallResolutionContext.create(resolutionContext, call, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS), name2, tracingStrategy, NewResolutionOldInference.ResolutionKind.Function.INSTANCE);
        if (overloadResolutionResultsComputeTasksAndResolveCall == null) {
            $$$reportNull$$$0(24);
        }
        return overloadResolutionResultsComputeTasksAndResolveCall;
    }

    private OverloadResolutionResults<FunctionDescriptor> resolveCallForInvoke(BasicCallResolutionContext basicCallResolutionContext, TracingStrategy tracingStrategy) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(25);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(26);
        }
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsComputeTasksAndResolveCall = computeTasksAndResolveCall(basicCallResolutionContext, OperatorNameConventions.INVOKE, tracingStrategy, NewResolutionOldInference.ResolutionKind.Invoke.INSTANCE);
        if (overloadResolutionResultsComputeTasksAndResolveCall == null) {
            $$$reportNull$$$0(27);
        }
        return overloadResolutionResultsComputeTasksAndResolveCall;
    }

    public <D extends CallableDescriptor> OverloadResolutionResults<D> computeTasksAndResolveCall(BasicCallResolutionContext basicCallResolutionContext, Name name2, KtReferenceExpression ktReferenceExpression, NewResolutionOldInference.ResolutionKind resolutionKind) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(28);
        }
        if (name2 == null) {
            $$$reportNull$$$0(29);
        }
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(30);
        }
        if (resolutionKind == null) {
            $$$reportNull$$$0(31);
        }
        OverloadResolutionResults<D> overloadResolutionResultsComputeTasksAndResolveCall = computeTasksAndResolveCall(basicCallResolutionContext, name2, TracingStrategyImpl.create(ktReferenceExpression, basicCallResolutionContext.call), resolutionKind);
        if (overloadResolutionResultsComputeTasksAndResolveCall == null) {
            $$$reportNull$$$0(32);
        }
        return overloadResolutionResultsComputeTasksAndResolveCall;
    }

    public <D extends CallableDescriptor> OverloadResolutionResults<D> computeTasksAndResolveCall(final BasicCallResolutionContext basicCallResolutionContext, final Name name2, final TracingStrategy tracingStrategy, final NewResolutionOldInference.ResolutionKind resolutionKind) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(33);
        }
        if (name2 == null) {
            $$$reportNull$$$0(34);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(35);
        }
        if (resolutionKind == null) {
            $$$reportNull$$$0(36);
        }
        OverloadResolutionResults<D> overloadResolutionResults = (OverloadResolutionResults) callResolvePerfCounter.time(new Function0() { // from class: org.jetbrains.kotlin.resolve.calls.CallResolver$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7428xe75032b8(resolutionKind, name2, basicCallResolutionContext, tracingStrategy);
            }
        });
        if (overloadResolutionResults == null) {
            $$$reportNull$$$0(37);
        }
        return overloadResolutionResults;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$computeTasksAndResolveCall$0$org-jetbrains-kotlin-resolve-calls-CallResolver, reason: not valid java name */
    /* synthetic */ OverloadResolutionResults m7428xe75032b8(NewResolutionOldInference.ResolutionKind resolutionKind, Name name2, BasicCallResolutionContext basicCallResolutionContext, TracingStrategy tracingStrategy) {
        return doResolveCallOrGetCachedResults(basicCallResolutionContext, new ResolutionTask(resolutionKind, name2, null), tracingStrategy);
    }

    private <D extends FunctionDescriptor> OverloadResolutionResults<D> computeTasksFromCandidatesAndResolvedCall(BasicCallResolutionContext basicCallResolutionContext, KtReferenceExpression ktReferenceExpression, Collection<OldResolutionCandidate<D>> collection) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(38);
        }
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(39);
        }
        if (collection == null) {
            $$$reportNull$$$0(40);
        }
        return computeTasksFromCandidatesAndResolvedCall(basicCallResolutionContext, collection, TracingStrategyImpl.create(ktReferenceExpression, basicCallResolutionContext.call));
    }

    private <D extends FunctionDescriptor> OverloadResolutionResults<D> computeTasksFromCandidatesAndResolvedCall(final BasicCallResolutionContext basicCallResolutionContext, final Collection<OldResolutionCandidate<D>> collection, final TracingStrategy tracingStrategy) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(41);
        }
        if (collection == null) {
            $$$reportNull$$$0(42);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(43);
        }
        OverloadResolutionResults<D> overloadResolutionResults = (OverloadResolutionResults) callResolvePerfCounter.time(new Function0() { // from class: org.jetbrains.kotlin.resolve.calls.CallResolver$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7429x9bc78b31(collection, basicCallResolutionContext, tracingStrategy);
            }
        });
        if (overloadResolutionResults == null) {
            $$$reportNull$$$0(44);
        }
        return overloadResolutionResults;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$computeTasksFromCandidatesAndResolvedCall$1$org-jetbrains-kotlin-resolve-calls-CallResolver, reason: not valid java name */
    /* synthetic */ OverloadResolutionResults m7429x9bc78b31(Collection collection, BasicCallResolutionContext basicCallResolutionContext, TracingStrategy tracingStrategy) {
        return doResolveCallOrGetCachedResults(basicCallResolutionContext, new ResolutionTask(new NewResolutionOldInference.ResolutionKind.GivenCandidates(), null, collection), tracingStrategy);
    }

    public OverloadResolutionResults<FunctionDescriptor> resolveBinaryCall(ExpressionTypingContext expressionTypingContext, ExpressionReceiver expressionReceiver, KtBinaryExpression ktBinaryExpression, Name name2) {
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveCallWithGivenName = resolveCallWithGivenName(expressionTypingContext, CallMaker.makeCall(expressionReceiver, ktBinaryExpression), ktBinaryExpression.getOperationReference(), name2);
        if (overloadResolutionResultsResolveCallWithGivenName == null) {
            $$$reportNull$$$0(45);
        }
        return overloadResolutionResultsResolveCallWithGivenName;
    }

    public OverloadResolutionResults<FunctionDescriptor> resolveCollectionLiteralCallWithGivenDescriptor(ExpressionTypingContext expressionTypingContext, KtCollectionLiteralExpression ktCollectionLiteralExpression, final Call call, Collection<FunctionDescriptor> collection) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(46);
        }
        if (ktCollectionLiteralExpression == null) {
            $$$reportNull$$$0(47);
        }
        if (call == null) {
            $$$reportNull$$$0(48);
        }
        if (collection == null) {
            $$$reportNull$$$0(49);
        }
        return computeTasksFromCandidatesAndResolvedCall(BasicCallResolutionContext.create(expressionTypingContext, call, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS), CollectionsKt.map(collection, new Function1() { // from class: org.jetbrains.kotlin.resolve.calls.CallResolver$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return OldResolutionCandidate.create(call, (FunctionDescriptor) obj, null, ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, null);
            }
        }), TracingStrategyImpl.create(ktCollectionLiteralExpression, call));
    }

    public OverloadResolutionResults<FunctionDescriptor> resolveEqualsCallWithGivenDescriptors(ExpressionTypingContext expressionTypingContext, KtReferenceExpression ktReferenceExpression, final ExpressionReceiver expressionReceiver, final Call call, Collection<FunctionDescriptor> collection) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(50);
        }
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(51);
        }
        if (expressionReceiver == null) {
            $$$reportNull$$$0(52);
        }
        if (call == null) {
            $$$reportNull$$$0(53);
        }
        if (collection == null) {
            $$$reportNull$$$0(54);
        }
        return computeTasksFromCandidatesAndResolvedCall(BasicCallResolutionContext.create(expressionTypingContext, call, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS), CollectionsKt.map(collection, new Function1() { // from class: org.jetbrains.kotlin.resolve.calls.CallResolver$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return OldResolutionCandidate.create(call, (FunctionDescriptor) obj, expressionReceiver, ExplicitReceiverKind.DISPATCH_RECEIVER, null);
            }
        }), TracingStrategyImpl.create(ktReferenceExpression, call));
    }

    public OverloadResolutionResults<FunctionDescriptor> resolveFunctionCall(BindingTrace bindingTrace, LexicalScope lexicalScope, Call call, KotlinType kotlinType, DataFlowInfo dataFlowInfo, boolean z, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(55);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(56);
        }
        if (call == null) {
            $$$reportNull$$$0(57);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(58);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(59);
        }
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveFunctionCall = resolveFunctionCall(BasicCallResolutionContext.create(bindingTrace, lexicalScope, call, kotlinType, dataFlowInfo, ContextDependency.INDEPENDENT, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, z, this.languageVersionSettings, this.dataFlowValueFactory, inferenceSession != null ? inferenceSession : InferenceSession.INSTANCE.getDefault()));
        if (overloadResolutionResultsResolveFunctionCall == null) {
            $$$reportNull$$$0(60);
        }
        return overloadResolutionResultsResolveFunctionCall;
    }

    public OverloadResolutionResults<FunctionDescriptor> resolveFunctionCall(BasicCallResolutionContext basicCallResolutionContext) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(61);
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        Call.CallType callType = basicCallResolutionContext.call.getCallType();
        if (callType == Call.CallType.ARRAY_GET_METHOD || callType == Call.CallType.ARRAY_SET_METHOD) {
            OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsComputeTasksAndResolveCall = computeTasksAndResolveCall(basicCallResolutionContext, callType == Call.CallType.ARRAY_GET_METHOD ? OperatorNameConventions.GET : OperatorNameConventions.SET, (KtArrayAccessExpression) basicCallResolutionContext.call.getCallElement(), NewResolutionOldInference.ResolutionKind.Function.INSTANCE);
            if (overloadResolutionResultsComputeTasksAndResolveCall == null) {
                $$$reportNull$$$0(62);
            }
            return overloadResolutionResultsComputeTasksAndResolveCall;
        }
        KtExpression calleeExpression = basicCallResolutionContext.call.getCalleeExpression();
        if (calleeExpression instanceof KtSimpleNameExpression) {
            KtSimpleNameExpression ktSimpleNameExpression = (KtSimpleNameExpression) calleeExpression;
            OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsComputeTasksAndResolveCall2 = computeTasksAndResolveCall(basicCallResolutionContext, ktSimpleNameExpression.getReferencedNameAsName(), ktSimpleNameExpression, NewResolutionOldInference.ResolutionKind.Function.INSTANCE);
            if (overloadResolutionResultsComputeTasksAndResolveCall2 == null) {
                $$$reportNull$$$0(63);
            }
            return overloadResolutionResultsComputeTasksAndResolveCall2;
        }
        if (calleeExpression instanceof KtConstructorCalleeExpression) {
            OverloadResolutionResults<ConstructorDescriptor> overloadResolutionResultsResolveCallForConstructor = resolveCallForConstructor(basicCallResolutionContext, (KtConstructorCalleeExpression) calleeExpression);
            if (overloadResolutionResultsResolveCallForConstructor == null) {
                $$$reportNull$$$0(64);
            }
            return overloadResolutionResultsResolveCallForConstructor;
        }
        if (calleeExpression instanceof KtConstructorDelegationReferenceExpression) {
            return resolveConstructorDelegationCall(basicCallResolutionContext, (KtConstructorDelegationCall) basicCallResolutionContext.call.getCallElement(), (KtConstructorDelegationReferenceExpression) calleeExpression, (ClassDescriptor) basicCallResolutionContext.scope.getOwnerDescriptor().getContainingModule());
        }
        if (calleeExpression == null) {
            OverloadResolutionResultsImpl overloadResolutionResultsImplCheckArgumentTypesAndFail = checkArgumentTypesAndFail(basicCallResolutionContext);
            if (overloadResolutionResultsImplCheckArgumentTypesAndFail == null) {
                $$$reportNull$$$0(65);
            }
            return overloadResolutionResultsImplCheckArgumentTypesAndFail;
        }
        SimpleType simpleTypeCreateFunctionType = TypeUtils.NO_EXPECTED_TYPE;
        if (calleeExpression instanceof KtLambdaExpression) {
            int size = ((KtLambdaExpression) calleeExpression).getValueParameters().size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(TypeUtils.NO_EXPECTED_TYPE);
            }
            simpleTypeCreateFunctionType = FunctionTypesKt.createFunctionType(this.builtIns, Annotations.INSTANCE.getEMPTY(), null, Collections.emptyList(), arrayList, null, basicCallResolutionContext.expectedType);
        }
        KotlinType kotlinTypeSafeGetType = this.expressionTypingServices.safeGetType(basicCallResolutionContext.scope, calleeExpression, simpleTypeCreateFunctionType, basicCallResolutionContext.dataFlowInfo, basicCallResolutionContext.inferenceSession, basicCallResolutionContext.trace);
        CallTransformer.CallForImplicitInvoke callForImplicitInvoke = new CallTransformer.CallForImplicitInvoke(basicCallResolutionContext.call.getExplicitReceiver(), ExpressionReceiver.INSTANCE.create(calleeExpression, kotlinTypeSafeGetType, basicCallResolutionContext.trace.getContext()), basicCallResolutionContext.call, false);
        return resolveCallForInvoke(basicCallResolutionContext.replaceCall(callForImplicitInvoke), new TracingStrategyForInvoke(calleeExpression, callForImplicitInvoke, kotlinTypeSafeGetType));
    }

    private OverloadResolutionResults<ConstructorDescriptor> resolveCallForConstructor(BasicCallResolutionContext basicCallResolutionContext, KtConstructorCalleeExpression ktConstructorCalleeExpression) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(66);
        }
        if (ktConstructorCalleeExpression == null) {
            $$$reportNull$$$0(67);
        }
        basicCallResolutionContext.trace.record(BindingContext.LEXICAL_SCOPE, basicCallResolutionContext.call.getCallElement(), basicCallResolutionContext.scope);
        KtSimpleNameExpression constructorReferenceExpression = ktConstructorCalleeExpression.getConstructorReferenceExpression();
        KtTypeReference typeReference = ktConstructorCalleeExpression.getTypeReference();
        if (constructorReferenceExpression == null || typeReference == null) {
            CallResolverUtilKt.checkForConstructorCallOnFunctionalType(typeReference, basicCallResolutionContext);
            return checkArgumentTypesAndFail(basicCallResolutionContext);
        }
        KotlinType kotlinTypeResolveType = this.typeResolver.resolveType(basicCallResolutionContext.scope, typeReference, basicCallResolutionContext.trace, true);
        if (KotlinTypeKt.isError(kotlinTypeResolveType)) {
            return checkArgumentTypesAndFail(basicCallResolutionContext);
        }
        ClassifierDescriptor this$0 = kotlinTypeResolveType.getConstructor().getThis$0();
        if (!(this$0 instanceof ClassDescriptor)) {
            basicCallResolutionContext.trace.report(Errors.NOT_A_CLASS.on(ktConstructorCalleeExpression));
            return checkArgumentTypesAndFail(basicCallResolutionContext);
        }
        if (((ClassDescriptor) this$0).getConstructors().isEmpty()) {
            basicCallResolutionContext.trace.report(Errors.NO_CONSTRUCTOR.on(CallUtilKt.getValueArgumentListOrElement(basicCallResolutionContext.call)));
            return checkArgumentTypesAndFail(basicCallResolutionContext);
        }
        return resolveConstructorCall(basicCallResolutionContext, constructorReferenceExpression, kotlinTypeResolveType);
    }

    public OverloadResolutionResults<ConstructorDescriptor> resolveConstructorCall(BasicCallResolutionContext basicCallResolutionContext, KtReferenceExpression ktReferenceExpression, KotlinType kotlinType) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(68);
        }
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(69);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(70);
        }
        Pair<Collection<OldResolutionCandidate<ConstructorDescriptor>>, BasicCallResolutionContext> pairPrepareCandidatesAndContextForConstructorCall = prepareCandidatesAndContextForConstructorCall(kotlinType, basicCallResolutionContext, this.syntheticScopes);
        return computeTasksFromCandidatesAndResolvedCall(pairPrepareCandidatesAndContextForConstructorCall.getSecond(), ktReferenceExpression, pairPrepareCandidatesAndContextForConstructorCall.getFirst());
    }

    public OverloadResolutionResults<ConstructorDescriptor> resolveConstructorDelegationCall(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, ClassConstructorDescriptor classConstructorDescriptor, KtConstructorDelegationCall ktConstructorDelegationCall, InferenceSession inferenceSession) {
        DiagnosticFactory0<PsiElement> diagnosticFactory0;
        if (bindingTrace == null) {
            $$$reportNull$$$0(71);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(72);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(73);
        }
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(74);
        }
        if (ktConstructorDelegationCall == null) {
            $$$reportNull$$$0(75);
        }
        BasicCallResolutionContext basicCallResolutionContextCreate = BasicCallResolutionContext.create(bindingTrace, lexicalScope, CallMaker.makeCall(null, null, ktConstructorDelegationCall), TypeUtils.NO_EXPECTED_TYPE, dataFlowInfo, ContextDependency.INDEPENDENT, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, false, this.languageVersionSettings, this.dataFlowValueFactory, inferenceSession != null ? inferenceSession : InferenceSession.INSTANCE.getDefault());
        KtConstructorDelegationReferenceExpression calleeExpression = ktConstructorDelegationCall.getCalleeExpression();
        if (calleeExpression == null) {
            return checkArgumentTypesAndFail(basicCallResolutionContextCreate);
        }
        ClassDescriptor containingModule = classConstructorDescriptor.getContainingModule();
        if (classConstructorDescriptor.getConstructedClass().getClassKind() == ClassKind.ENUM_CLASS && ktConstructorDelegationCall.isImplicit()) {
            if (containingModule.mo6347getUnsubstitutedPrimaryConstructor() != null) {
                if (this.languageVersionSettings.supportsFeature(LanguageFeature.RequiredPrimaryConstructorDelegationCallInEnums)) {
                    diagnosticFactory0 = Errors.PRIMARY_CONSTRUCTOR_DELEGATION_CALL_EXPECTED;
                } else {
                    diagnosticFactory0 = Errors.PRIMARY_CONSTRUCTOR_DELEGATION_CALL_EXPECTED_IN_ENUM;
                }
                basicCallResolutionContextCreate.trace.report(diagnosticFactory0.on(calcReportOn(calleeExpression)));
            }
            return null;
        }
        return resolveConstructorDelegationCall(basicCallResolutionContextCreate, ktConstructorDelegationCall, ktConstructorDelegationCall.getCalleeExpression(), containingModule);
    }

    private OverloadResolutionResults<ConstructorDescriptor> resolveConstructorDelegationCall(BasicCallResolutionContext basicCallResolutionContext, KtConstructorDelegationCall ktConstructorDelegationCall, KtConstructorDelegationReferenceExpression ktConstructorDelegationReferenceExpression, ClassDescriptor classDescriptor) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(76);
        }
        if (ktConstructorDelegationCall == null) {
            $$$reportNull$$$0(77);
        }
        if (ktConstructorDelegationReferenceExpression == null) {
            $$$reportNull$$$0(78);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(79);
        }
        basicCallResolutionContext.trace.record(BindingContext.LEXICAL_SCOPE, ktConstructorDelegationCall, basicCallResolutionContext.scope);
        boolean zIsThis = ktConstructorDelegationReferenceExpression.isThis();
        if (classDescriptor.getClassKind() == ClassKind.ENUM_CLASS && !zIsThis) {
            basicCallResolutionContext.trace.report(Errors.DELEGATION_SUPER_CALL_IN_ENUM_CONSTRUCTOR.on(ktConstructorDelegationReferenceExpression));
            OverloadResolutionResultsImpl overloadResolutionResultsImplCheckArgumentTypesAndFail = checkArgumentTypesAndFail(basicCallResolutionContext);
            if (overloadResolutionResultsImplCheckArgumentTypesAndFail == null) {
                $$$reportNull$$$0(80);
            }
            return overloadResolutionResultsImplCheckArgumentTypesAndFail;
        }
        ClassDescriptor superClassOrAny = zIsThis ? classDescriptor : DescriptorUtilsKt.getSuperClassOrAny(classDescriptor);
        Collection<ClassConstructorDescriptor> constructors = superClassOrAny.getConstructors();
        if (!zIsThis && classDescriptor.mo6347getUnsubstitutedPrimaryConstructor() != null) {
            if (DescriptorUtils.canHaveDeclaredConstructors(classDescriptor)) {
                basicCallResolutionContext.trace.report(Errors.PRIMARY_CONSTRUCTOR_DELEGATION_CALL_EXPECTED.on(calcReportOn(ktConstructorDelegationReferenceExpression)));
            }
            if (ktConstructorDelegationCall.isImplicit()) {
                OverloadResolutionResultsImpl overloadResolutionResultsImplNameNotFound = OverloadResolutionResultsImpl.nameNotFound();
                if (overloadResolutionResultsImplNameNotFound == null) {
                    $$$reportNull$$$0(81);
                }
                return overloadResolutionResultsImplNameNotFound;
            }
        }
        if (constructors.isEmpty()) {
            basicCallResolutionContext.trace.report(Errors.NO_CONSTRUCTOR.on(CallUtilKt.getValueArgumentListOrElement(basicCallResolutionContext.call)));
            OverloadResolutionResultsImpl overloadResolutionResultsImplCheckArgumentTypesAndFail2 = checkArgumentTypesAndFail(basicCallResolutionContext);
            if (overloadResolutionResultsImplCheckArgumentTypesAndFail2 == null) {
                $$$reportNull$$$0(82);
            }
            return overloadResolutionResultsImplCheckArgumentTypesAndFail2;
        }
        Pair<Collection<OldResolutionCandidate<ConstructorDescriptor>>, BasicCallResolutionContext> pairPrepareCandidatesAndContextForConstructorCall = prepareCandidatesAndContextForConstructorCall(zIsThis ? classDescriptor.getDefaultType() : DescriptorUtils.getSuperClassType(classDescriptor), basicCallResolutionContext, this.syntheticScopes);
        Collection<OldResolutionCandidate<ConstructorDescriptor>> first = pairPrepareCandidatesAndContextForConstructorCall.getFirst();
        BasicCallResolutionContext second = pairPrepareCandidatesAndContextForConstructorCall.getSecond();
        TracingStrategy tracingStrategyForImplicitConstructorDelegationCall = ktConstructorDelegationCall.isImplicit() ? new TracingStrategyForImplicitConstructorDelegationCall(ktConstructorDelegationCall, second.call) : TracingStrategyImpl.create(ktConstructorDelegationReferenceExpression, second.call);
        boolean zIsImplicit = ktConstructorDelegationCall.isImplicit();
        PsiElement psiElement = ktConstructorDelegationCall;
        if (!zIsImplicit) {
            psiElement = ktConstructorDelegationReferenceExpression;
        }
        if (superClassOrAny.getIsInner() && !DescriptorResolver.checkHasOuterClassInstance(second.scope, second.trace, psiElement, (ClassDescriptor) superClassOrAny.getContainingModule())) {
            OverloadResolutionResultsImpl overloadResolutionResultsImplCheckArgumentTypesAndFail3 = checkArgumentTypesAndFail(second);
            if (overloadResolutionResultsImplCheckArgumentTypesAndFail3 == null) {
                $$$reportNull$$$0(83);
            }
            return overloadResolutionResultsImplCheckArgumentTypesAndFail3;
        }
        return computeTasksFromCandidatesAndResolvedCall(second, first, tracingStrategyForImplicitConstructorDelegationCall);
    }

    private PsiElement calcReportOn(KtConstructorDelegationReferenceExpression ktConstructorDelegationReferenceExpression) {
        if (ktConstructorDelegationReferenceExpression == null) {
            $$$reportNull$$$0(84);
        }
        return CallResolverUtilKt.reportOnElement(ktConstructorDelegationReferenceExpression.getLightParent());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Pair<Collection<OldResolutionCandidate<ConstructorDescriptor>>, BasicCallResolutionContext> prepareCandidatesAndContextForConstructorCall(KotlinType kotlinType, BasicCallResolutionContext basicCallResolutionContext, SyntheticScopes syntheticScopes) {
        if (kotlinType == null) {
            $$$reportNull$$$0(85);
        }
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(86);
        }
        if (syntheticScopes == null) {
            $$$reportNull$$$0(87);
        }
        if (!(kotlinType.getConstructor().getThis$0() instanceof ClassDescriptor)) {
            return new Pair<>(Collections.emptyList(), basicCallResolutionContext);
        }
        boolean zAnyConstructorHasDeclaredTypeParameters = anyConstructorHasDeclaredTypeParameters(kotlinType.getConstructor().getThis$0());
        if (zAnyConstructorHasDeclaredTypeParameters) {
            basicCallResolutionContext = (BasicCallResolutionContext) basicCallResolutionContext.replaceExpectedType(kotlinType);
        }
        return new Pair<>(CallResolverUtilKt.createResolutionCandidatesForConstructors(basicCallResolutionContext.scope, basicCallResolutionContext.call, kotlinType, !zAnyConstructorHasDeclaredTypeParameters, syntheticScopes), basicCallResolutionContext);
    }

    private static boolean anyConstructorHasDeclaredTypeParameters(ClassifierDescriptor classifierDescriptor) {
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            return false;
        }
        for (ClassConstructorDescriptor classConstructorDescriptor : ((ClassDescriptor) classifierDescriptor).getConstructors()) {
            if (classConstructorDescriptor.getTypeParameters().size() > classConstructorDescriptor.getContainingModule().getDeclaredTypeParameters().size()) {
                return true;
            }
        }
        return false;
    }

    public OverloadResolutionResults<FunctionDescriptor> resolveCallWithKnownCandidate(final Call call, final TracingStrategy tracingStrategy, final ResolutionContext<?> resolutionContext, final OldResolutionCandidate<FunctionDescriptor> oldResolutionCandidate, final MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments) {
        if (call == null) {
            $$$reportNull$$$0(88);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(89);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(90);
        }
        if (oldResolutionCandidate == null) {
            $$$reportNull$$$0(91);
        }
        return (OverloadResolutionResults) callResolvePerfCounter.time(new Function0() { // from class: org.jetbrains.kotlin.resolve.calls.CallResolver$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7430x3f278308(resolutionContext, call, mutableDataFlowInfoForArguments, oldResolutionCandidate, tracingStrategy);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$resolveCallWithKnownCandidate$4$org-jetbrains-kotlin-resolve-calls-CallResolver, reason: not valid java name */
    /* synthetic */ OverloadResolutionResults m7430x3f278308(ResolutionContext resolutionContext, Call call, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments, OldResolutionCandidate oldResolutionCandidate, TracingStrategy tracingStrategy) {
        return doResolveCallOrGetCachedResults(BasicCallResolutionContext.create(resolutionContext, call, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, mutableDataFlowInfoForArguments), new ResolutionTask(new NewResolutionOldInference.ResolutionKind.GivenCandidates(), null, Collections.singleton(oldResolutionCandidate)), tracingStrategy);
    }

    private <D extends CallableDescriptor> OverloadResolutionResults<D> doResolveCallOrGetCachedResults(BasicCallResolutionContext basicCallResolutionContext, ResolutionTask<D> resolutionTask, TracingStrategy tracingStrategy) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(92);
        }
        if (resolutionTask == null) {
            $$$reportNull$$$0(93);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(94);
        }
        Call call = basicCallResolutionContext.call;
        tracingStrategy.bindCall(basicCallResolutionContext.trace, call);
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.NewInference);
        NewResolutionOldInference.ResolutionKind resolutionKind = resolutionTask.resolutionKind;
        if (zSupportsFeature && this.PSICallResolver.getDefaultResolutionKinds().contains(resolutionKind)) {
            BindingContextUtilsKt.recordScope(basicCallResolutionContext.trace, basicCallResolutionContext.scope, basicCallResolutionContext.call.getCalleeExpression());
            return this.PSICallResolver.runResolutionAndInference(basicCallResolutionContext, resolutionTask.f955name, resolutionKind, tracingStrategy);
        }
        if (zSupportsFeature && (resolutionKind instanceof NewResolutionOldInference.ResolutionKind.GivenCandidates)) {
            BindingContextUtilsKt.recordScope(basicCallResolutionContext.trace, basicCallResolutionContext.scope, basicCallResolutionContext.call.getCalleeExpression());
            return this.PSICallResolver.runResolutionAndInferenceForGivenCandidates(basicCallResolutionContext, resolutionTask.givenCandidates, tracingStrategy);
        }
        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(basicCallResolutionContext.trace, "trace to resolve call", call);
        BasicCallResolutionContext basicCallResolutionContext2 = (BasicCallResolutionContext) basicCallResolutionContext.replaceBindingTrace(temporaryBindingTraceCreate);
        BindingContextUtilsKt.recordScope(basicCallResolutionContext2.trace, basicCallResolutionContext2.scope, basicCallResolutionContext2.call.getCalleeExpression());
        BindingContextUtilsKt.recordDataFlowInfo(basicCallResolutionContext2, basicCallResolutionContext2.call.getCalleeExpression());
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplDoResolveCall = doResolveCall(basicCallResolutionContext2, resolutionTask, tracingStrategy);
        if (BuilderInferenceUtilKt.isResultWithBuilderInference(overloadResolutionResultsImplDoResolveCall)) {
            temporaryBindingTraceCreate.commit();
            return overloadResolutionResultsImplDoResolveCall;
        }
        DelegatingBindingTrace trace = overloadResolutionResultsImplDoResolveCall.getTrace();
        if (trace != null) {
            trace.addOwnDataTo(temporaryBindingTraceCreate);
        }
        completeTypeInferenceDependentOnFunctionLiterals(basicCallResolutionContext2, overloadResolutionResultsImplDoResolveCall, tracingStrategy);
        if (basicCallResolutionContext.contextDependency == ContextDependency.DEPENDENT) {
            cacheResults(basicCallResolutionContext, overloadResolutionResultsImplDoResolveCall, temporaryBindingTraceCreate, tracingStrategy);
        }
        temporaryBindingTraceCreate.commit();
        return basicCallResolutionContext.contextDependency == ContextDependency.INDEPENDENT ? this.callCompleter.completeCall(basicCallResolutionContext, overloadResolutionResultsImplDoResolveCall, tracingStrategy) : overloadResolutionResultsImplDoResolveCall;
    }

    private <D extends CallableDescriptor> void completeTypeInferenceDependentOnFunctionLiterals(BasicCallResolutionContext basicCallResolutionContext, OverloadResolutionResultsImpl<D> overloadResolutionResultsImpl, TracingStrategy tracingStrategy) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(95);
        }
        if (overloadResolutionResultsImpl == null) {
            $$$reportNull$$$0(96);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(97);
        }
        if (CallResolverUtilKt.isInvokeCallOnVariable(basicCallResolutionContext.call)) {
            return;
        }
        if (!overloadResolutionResultsImpl.isSingleResult()) {
            if (overloadResolutionResultsImpl.getResultCode() == OverloadResolutionResults.Code.INCOMPLETE_TYPE_INFERENCE) {
                this.argumentTypeResolver.checkTypesWithNoCallee(basicCallResolutionContext);
            }
        } else {
            this.genericCandidateResolver.completeTypeInferenceDependentOnFunctionArgumentsForCall(CallCandidateResolutionContext.createForCallBeingAnalyzed(overloadResolutionResultsImpl.mo7436getResultingCall(), basicCallResolutionContext, tracingStrategy));
        }
    }

    private static <F extends CallableDescriptor> void cacheResults(BasicCallResolutionContext basicCallResolutionContext, OverloadResolutionResultsImpl<F> overloadResolutionResultsImpl, DelegatingBindingTrace delegatingBindingTrace, TracingStrategy tracingStrategy) {
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(98);
        }
        if (overloadResolutionResultsImpl == null) {
            $$$reportNull$$$0(99);
        }
        if (delegatingBindingTrace == null) {
            $$$reportNull$$$0(100);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(101);
        }
        Call call = basicCallResolutionContext.call;
        if (CallResolverUtilKt.isInvokeCallOnVariable(call)) {
            return;
        }
        DelegatingBindingTrace delegatingBindingTrace2 = new DelegatingBindingTrace(BindingContext.EMPTY, "delta trace for caching resolve of", basicCallResolutionContext.call, BindingTraceFilter.INSTANCE.getACCEPT_ALL(), false);
        delegatingBindingTrace.addOwnDataTo(delegatingBindingTrace2);
        basicCallResolutionContext.resolutionResultsCache.record(call, overloadResolutionResultsImpl, basicCallResolutionContext, tracingStrategy, delegatingBindingTrace2);
    }

    private <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> checkArgumentTypesAndFail(BasicCallResolutionContext basicCallResolutionContext) {
        this.argumentTypeResolver.checkTypesWithNoCallee(basicCallResolutionContext);
        return OverloadResolutionResultsImpl.nameNotFound();
    }

    private <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> doResolveCall(BasicCallResolutionContext basicCallResolutionContext, ResolutionTask<D> resolutionTask, TracingStrategy tracingStrategy) {
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplRunResolutionForGivenCandidates;
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(102);
        }
        if (resolutionTask == null) {
            $$$reportNull$$$0(103);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(104);
        }
        DataFlowInfo resultInfo = basicCallResolutionContext.dataFlowInfoForArguments.getResultInfo();
        if (basicCallResolutionContext.checkArguments == CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS) {
            this.argumentTypeResolver.analyzeArgumentsAndRecordTypes(basicCallResolutionContext, ResolveArgumentsMode.SHAPE_FUNCTION_ARGUMENTS);
        }
        for (KtTypeProjection ktTypeProjection : basicCallResolutionContext.call.getTypeArguments()) {
            if (ktTypeProjection.getProjectionKind() != KtProjectionKind.NONE) {
                basicCallResolutionContext.trace.report(Errors.PROJECTION_ON_NON_CLASS_TYPE_ARGUMENT.on(ktTypeProjection));
                ModifierCheckerCore.INSTANCE.check(ktTypeProjection, basicCallResolutionContext.trace, null, this.languageVersionSettings);
            }
            KotlinType kotlinTypeResolveTypeRefWithDefault = this.argumentTypeResolver.resolveTypeRefWithDefault(ktTypeProjection.getTypeReference(), basicCallResolutionContext.scope, basicCallResolutionContext.trace, null);
            if (kotlinTypeResolveTypeRefWithDefault != null) {
                ForceResolveUtil.forceResolveAllContents(kotlinTypeResolveTypeRefWithDefault);
            }
        }
        if (!(resolutionTask.resolutionKind instanceof NewResolutionOldInference.ResolutionKind.GivenCandidates)) {
            overloadResolutionResultsImplRunResolutionForGivenCandidates = this.newResolutionOldInference.runResolution(basicCallResolutionContext, resolutionTask.f955name, resolutionTask.resolutionKind, tracingStrategy);
        } else {
            overloadResolutionResultsImplRunResolutionForGivenCandidates = this.newResolutionOldInference.runResolutionForGivenCandidates(basicCallResolutionContext, tracingStrategy, resolutionTask.givenCandidates);
        }
        if (((Boolean) this.languageVersionSettings.getFlag(AnalysisFlags.getIgnoreDataFlowInAssert())).booleanValue() && overloadResolutionResultsImplRunResolutionForGivenCandidates.isSingleResult()) {
            CallableDescriptor callableDescriptorMo7437getResultingDescriptor = overloadResolutionResultsImplRunResolutionForGivenCandidates.mo7437getResultingDescriptor();
            if (callableDescriptorMo7437getResultingDescriptor.getShortName().equals(Name.identifier(PsiKeyword.ASSERT))) {
                DeclarationDescriptor containingDeclaration = callableDescriptorMo7437getResultingDescriptor.getContainingModule();
                if ((containingDeclaration instanceof PackageFragmentDescriptor) && ((PackageFragmentDescriptor) containingDeclaration).getFqName().asString().equals(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
                    basicCallResolutionContext.dataFlowInfoForArguments.updateInfo(basicCallResolutionContext.call.getValueArguments().get(0), resultInfo);
                }
            }
        }
        if (overloadResolutionResultsImplRunResolutionForGivenCandidates == null) {
            $$$reportNull$$$0(105);
        }
        return overloadResolutionResultsImplRunResolutionForGivenCandidates;
    }

    private static class ResolutionTask<D extends CallableDescriptor> {
        final Collection<OldResolutionCandidate<D>> givenCandidates;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        final Name f955name;
        final NewResolutionOldInference.ResolutionKind resolutionKind;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", Namer.METADATA_CLASS_KIND, "org/jetbrains/kotlin/resolve/calls/CallResolver$ResolutionTask", "<init>"));
        }

        private ResolutionTask(NewResolutionOldInference.ResolutionKind resolutionKind, Name name2, Collection<OldResolutionCandidate<D>> collection) {
            if (resolutionKind == null) {
                $$$reportNull$$$0(0);
            }
            this.f955name = name2;
            this.givenCandidates = collection;
            this.resolutionKind = resolutionKind;
        }
    }
}
