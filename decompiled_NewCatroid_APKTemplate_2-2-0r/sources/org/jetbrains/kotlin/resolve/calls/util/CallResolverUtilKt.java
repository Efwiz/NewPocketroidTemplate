package org.jetbrains.kotlin.resolve.calls.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionType;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.CallTransformer;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.ComposedSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.TypeVariable;
import org.jetbrains.kotlin.resolve.calls.inference.components.EmptySubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SubKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.OldResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tower.ExpressionKotlinCallArgumentImpl;
import org.jetbrains.kotlin.resolve.calls.tower.NewCallArgumentsKt;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallImpl;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopesKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.InferenceUtilsKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: CallResolverUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a(\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u0018\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010\u001a:\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001d\u001a$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00122\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00122\u0006\u0010 \u001a\u00020!H\u0002\u001a\"\u0010\"\u001a\u00020\u001a2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030#\u001a&\u0010\"\u001a\u00020\u001a2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010$\u001a\u00020%2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a&\u0010&\u001a\u00020\u001a2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u0016\u0010'\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020!\u001a\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0016\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u00122\u0006\u0010.\u001a\u00020\u001aH\u0002\u001a\u000e\u0010/\u001a\u00020\u001a2\u0006\u0010.\u001a\u00020\u001a\u001a\u0010\u00100\u001a\u0004\u0018\u0001012\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u000e\u00102\u001a\u00020\u00012\u0006\u0010.\u001a\u00020\u001a\u001a\u000e\u00103\u001a\u00020\u00012\u0006\u0010.\u001a\u00020\u001a\u001a\u0016\u00104\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\t\u001a\u000e\u00105\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u000e\u00106\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u000e\u00107\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u000e\u00108\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u000e\u00109\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u000e\u0010:\u001a\u00020\u00012\u0006\u0010*\u001a\u00020;\u001a\u000e\u0010<\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u000e\u0010=\u001a\u00020\u001a2\u0006\u0010.\u001a\u00020\u001a\u001a\u0016\u0010>\u001a\u00020\u001a2\u0006\u0010.\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\u001a\u001a\u001e\u0010@\u001a\u00020A2\u0006\u0010.\u001a\u00020\u001a2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020-0\u0012H\u0002\u001a\u0012\u0010C\u001a\u00020\u0001*\u00020!2\u0006\u0010D\u001a\u00020E\u001a\u0014\u0010F\u001a\u00020\u0001*\u00020!2\u0006\u0010D\u001a\u00020EH\u0002\u001a&\u0010G\u001a\u00020H*\u00020H2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020I0\u00122\n\b\u0002\u0010J\u001a\u0004\u0018\u00010KH\u0000\u001aD\u0010L\u001a\b\u0012\u0004\u0012\u00020I0\u0012*\b\u0012\u0004\u0012\u00020I0\u00122\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010M\u001a\u00020N2\u001a\u0010O\u001a\u0016\u0012\u0004\u0012\u00020Q\u0012\u0004\u0012\u00020R\u0012\u0006\u0012\u0004\u0018\u00010R0PH\u0000\u001a\f\u0010S\u001a\u00020T*\u00020TH\u0000¨\u0006U"}, d2 = {"arrayAssignmentToVarargInNamedFormInAnnotation", "", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "argument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "arrayAssignmentToVarargInNamedFormInFunction", "checkForConstructorCallOnFunctionalType", "", "typeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "createResolutionCandidatesForConstructors", "", "Lorg/jetbrains/kotlin/resolve/calls/tasks/OldResolutionCandidate;", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "lexicalScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "typeWithConstructors", "Lorg/jetbrains/kotlin/types/KotlinType;", "useKnownTypeSubstitutor", "syntheticScopes", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "filterOutTypeParameters", "upperBounds", "candidateDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getEffectiveExpectedType", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "resolvedArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "getEffectiveExpectedTypeForSingleArgument", "getErasedReceiverType", "receiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "descriptor", "getExpectedType", "getParameterArgumentsOfCallableType", "Lorg/jetbrains/kotlin/types/TypeProjection;", ModuleXmlParser.TYPE, "getReturnTypeForCallable", "getSuperCallExpression", "Lorg/jetbrains/kotlin/psi/KtSuperExpression;", "hasUnknownFunctionParameter", "hasUnknownReturnType", "isArrayOrArrayLiteral", "isBinaryRemOperator", "isConventionCall", "isInfixCall", "isInvokeCallOnExpressionWithBothReceivers", "isInvokeCallOnVariable", "isOrOverridesSynthesized", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "isSuperOrDelegatingConstructorCall", "replaceReturnTypeByUnknown", "replaceReturnTypeForCallable", "given", "replaceTypeArguments", "Lorg/jetbrains/kotlin/types/SimpleType;", "newArguments", "hasInferredReturnType", "constraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem;", "hasReturnTypeDependentOnUninferredParams", "replaceArguments", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "newReceiverArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "replaceTypes", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", SchemaSymbols.ATTVAL_REPLACE, "Lkotlin/Function2;", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "reportOnElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallResolverUtilKt {
    public static final boolean hasUnknownFunctionParameter(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!ReflectionTypes.INSTANCE.isCallableType(type)) {
            FunctionTypesKt.isSuspendFunctionType(type);
        }
        List<TypeProjection> parameterArgumentsOfCallableType = getParameterArgumentsOfCallableType(type);
        if ((parameterArgumentsOfCallableType instanceof Collection) && parameterArgumentsOfCallableType.isEmpty()) {
            return false;
        }
        for (TypeProjection typeProjection : parameterArgumentsOfCallableType) {
            KotlinType nullableAnyType = typeProjection.getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
            if (!TypeUtilsKt.contains(nullableAnyType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt$hasUnknownFunctionParameter$2$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(TypeUtils.isDontCarePlaceholder(it));
                }
            })) {
                ErrorUtils errorUtils = ErrorUtils.INSTANCE;
                KotlinType nullableAnyType2 = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType2, "getType(...)");
                if (errorUtils.containsUninferredTypeVariable(nullableAnyType2)) {
                }
            }
            return true;
        }
        return false;
    }

    public static final boolean hasUnknownReturnType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!ReflectionTypes.INSTANCE.isCallableType(type)) {
            FunctionTypesKt.isSuspendFunctionType(type);
        }
        return ErrorUtils.INSTANCE.containsErrorType(getReturnTypeForCallable(type));
    }

    public static final KotlinType replaceReturnTypeForCallable(KotlinType type, KotlinType given) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(given, "given");
        if (!ReflectionTypes.INSTANCE.isCallableType(type)) {
            FunctionTypesKt.isSuspendFunctionType(type);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        arrayListNewArrayList.addAll(getParameterArgumentsOfCallableType(type));
        arrayListNewArrayList.add(new TypeProjectionImpl(Variance.INVARIANT, given));
        Intrinsics.checkNotNull(arrayListNewArrayList);
        return replaceTypeArguments(type, arrayListNewArrayList);
    }

    public static final KotlinType replaceReturnTypeByUnknown(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        SimpleType DONT_CARE = TypeUtils.DONT_CARE;
        Intrinsics.checkNotNullExpressionValue(DONT_CARE, "DONT_CARE");
        return replaceReturnTypeForCallable(type, DONT_CARE);
    }

    private static final SimpleType replaceTypeArguments(KotlinType kotlinType, List<? extends TypeProjection> list) {
        return KotlinTypeFactory.simpleType$default(kotlinType.getAttributes(), kotlinType.getConstructor(), list, kotlinType.getIsMarkedNullable(), (KotlinTypeRefiner) null, 16, (Object) null);
    }

    private static final List<TypeProjection> getParameterArgumentsOfCallableType(KotlinType kotlinType) {
        return CollectionsKt.dropLast(kotlinType.getArguments(), 1);
    }

    public static final KotlinType getReturnTypeForCallable(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        KotlinType nullableAnyType = ((TypeProjection) CollectionsKt.last((List) type.getArguments())).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
        return nullableAnyType;
    }

    private static final boolean hasReturnTypeDependentOnUninferredParams(CallableDescriptor callableDescriptor, ConstraintSystem constraintSystem) {
        KotlinType returnType = callableDescriptor.getReturnType();
        if (returnType == null) {
            return false;
        }
        List<TypeVariable> nestedTypeVariables = ConstraintSystemUtilsKt.getNestedTypeVariables(constraintSystem, returnType);
        if ((nestedTypeVariables instanceof Collection) && nestedTypeVariables.isEmpty()) {
            return false;
        }
        Iterator<T> it = nestedTypeVariables.iterator();
        while (it.hasNext()) {
            if (constraintSystem.getTypeBounds((TypeVariable) it.next()).getValue() == null) {
                return true;
            }
        }
        return false;
    }

    public static final boolean hasInferredReturnType(CallableDescriptor callableDescriptor, ConstraintSystem constraintSystem) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(constraintSystem, "constraintSystem");
        return (hasReturnTypeDependentOnUninferredParams(callableDescriptor, constraintSystem) || constraintSystem.getStatus().hasOnlyErrorsDerivedFrom(ConstraintPositionKind.EXPECTED_TYPE_POSITION)) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final List<KotlinType> filterOutTypeParameters(List<? extends KotlinType> list, CallableDescriptor callableDescriptor) {
        if (list.size() < 2) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            ClassifierDescriptor this$0 = ((KotlinType) obj).getConstructor().getThis$0();
            if (!(this$0 instanceof TypeParameterDescriptor) || !Intrinsics.areEqual(((TypeParameterDescriptor) this$0).getContainingDeclaration(), callableDescriptor)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        return arrayList2.isEmpty() ? list : arrayList2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final KotlinType getErasedReceiverType(ReceiverParameterDescriptor receiverParameterDescriptor, CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(receiverParameterDescriptor, "receiverParameterDescriptor");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        KotlinType type = receiverParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        for (TypeParameterDescriptor typeParameterDescriptor : descriptor.getTypeParameters()) {
            if (Intrinsics.areEqual(typeParameterDescriptor.getTypeConstructor(), type.getConstructor())) {
                List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
                Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
                type = TypeIntersector.intersectUpperBounds(typeParameterDescriptor, filterOutTypeParameters(upperBounds, descriptor));
                Intrinsics.checkNotNullExpressionValue(type, "intersectUpperBounds(...)");
            }
        }
        SmartList smartList = new SmartList();
        Iterator<TypeProjection> it = type.getArguments().iterator();
        while (it.hasNext()) {
            smartList.add(new TypeProjectionImpl(it.next().getProjectionKind(), TypeUtils.DONT_CARE));
        }
        IntersectionTypeConstructor constructor = type.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            IntersectionTypeConstructor intersectionTypeConstructor = (IntersectionTypeConstructor) constructor;
            Collection<KotlinType> collectionMo7438getSupertypes = intersectionTypeConstructor.mo7438getSupertypes();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes, 10));
            Object[] objArr = false;
            for (KotlinType kotlinType : collectionMo7438getSupertypes) {
                List<TypeProjection> arguments = kotlinType.getArguments();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
                Iterator<T> it2 = arguments.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(new TypeProjectionImpl(((TypeProjection) it2.next()).getProjectionKind(), TypeUtils.DONT_CARE));
                }
                arrayList.add(TypeSubstitutionKt.replace$default(kotlinType, arrayList2, null, null, 6, null));
                objArr = true;
            }
            ArrayList arrayList3 = arrayList;
            IntersectionTypeConstructor alternative = null;
            KotlinType kotlinTypeReplace$default = null;
            if (objArr != false) {
                KotlinType alternative2 = intersectionTypeConstructor.getAlternative();
                if (alternative2 != null) {
                    List<TypeProjection> arguments2 = alternative2.getArguments();
                    ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments2, 10));
                    Iterator<T> it3 = arguments2.iterator();
                    while (it3.hasNext()) {
                        arrayList4.add(new TypeProjectionImpl(((TypeProjection) it3.next()).getProjectionKind(), TypeUtils.DONT_CARE));
                    }
                    kotlinTypeReplace$default = TypeSubstitutionKt.replace$default(alternative2, arrayList4, null, null, 6, null);
                }
                alternative = new IntersectionTypeConstructor(arrayList3).setAlternative(kotlinTypeReplace$default);
            }
            if (alternative != null) {
                intersectionTypeConstructor = alternative;
            }
            constructor = intersectionTypeConstructor;
        }
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(type.getAttributes(), constructor, smartList, type.getIsMarkedNullable(), ErrorUtils.createErrorScope(ErrorScopeKind.ERASED_RECEIVER_TYPE_SCOPE, true, new String[0]));
    }

    public static final boolean isOrOverridesSynthesized(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor.getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED) {
            return true;
        }
        if (descriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            return false;
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = descriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!isOrOverridesSynthesized((CallableMemberDescriptor) it.next())) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isBinaryRemOperator(Call call) {
        Name nameForOperationSymbol;
        Intrinsics.checkNotNullParameter(call, "call");
        KtElement callElement = call.getCallElement();
        KtBinaryExpression ktBinaryExpression = callElement instanceof KtBinaryExpression ? (KtBinaryExpression) callElement : null;
        if (ktBinaryExpression == null) {
            return false;
        }
        IElementType operationToken = ktBinaryExpression.getOperationToken();
        Intrinsics.checkNotNullExpressionValue(operationToken, "getOperationToken(...)");
        if ((operationToken instanceof KtToken) && (nameForOperationSymbol = OperatorConventions.getNameForOperationSymbol((KtToken) operationToken, true, true)) != null) {
            return ((ImmutableSet) OperatorConventions.REM_TO_MOD_OPERATION_NAMES.keySet()).contains(nameForOperationSymbol);
        }
        return false;
    }

    public static final boolean isConventionCall(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        if (call instanceof CallTransformer.CallForImplicitInvoke) {
            return true;
        }
        KtElement callElement = call.getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        if ((callElement instanceof KtArrayAccessExpression) || (callElement instanceof KtDestructuringDeclarationEntry)) {
            return true;
        }
        KtExpression calleeExpression = call.getCalleeExpression();
        KtOperationReferenceExpression ktOperationReferenceExpression = calleeExpression instanceof KtOperationReferenceExpression ? (KtOperationReferenceExpression) calleeExpression : null;
        if (ktOperationReferenceExpression == null) {
            return false;
        }
        return ktOperationReferenceExpression.isConventionOperator();
    }

    public static final boolean isInfixCall(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        KtExpression calleeExpression = call.getCalleeExpression();
        KtOperationReferenceExpression ktOperationReferenceExpression = calleeExpression instanceof KtOperationReferenceExpression ? (KtOperationReferenceExpression) calleeExpression : null;
        if (ktOperationReferenceExpression == null) {
            return false;
        }
        PsiElement parent = ktOperationReferenceExpression.getMyParent();
        KtBinaryExpression ktBinaryExpression = parent instanceof KtBinaryExpression ? (KtBinaryExpression) parent : null;
        return ktBinaryExpression != null && ktBinaryExpression.getOperationReference() == ktOperationReferenceExpression && ktOperationReferenceExpression.getOperationSignTokenType() == null;
    }

    public static final boolean isSuperOrDelegatingConstructorCall(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        KtExpression calleeExpression = call.getCalleeExpression();
        return (calleeExpression instanceof KtConstructorCalleeExpression) || (calleeExpression instanceof KtConstructorDelegationReferenceExpression);
    }

    public static final boolean isInvokeCallOnVariable(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        if (call.getCallType() != Call.CallType.INVOKE) {
            return false;
        }
        ReceiverValue dispatchReceiver = call.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver");
        return ((ExpressionReceiver) dispatchReceiver).getExpression() instanceof KtSimpleNameExpression;
    }

    public static final boolean isInvokeCallOnExpressionWithBothReceivers(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return (call.getCallType() != Call.CallType.INVOKE || isInvokeCallOnVariable(call) || call.getExplicitReceiver() == null || call.getDispatchReceiver() == null) ? false : true;
    }

    public static final KtSuperExpression getSuperCallExpression(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        Receiver explicitReceiver = call.getExplicitReceiver();
        ExpressionReceiver expressionReceiver = explicitReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) explicitReceiver : null;
        KtExpression expression = expressionReceiver != null ? expressionReceiver.getExpression() : null;
        if (expression instanceof KtSuperExpression) {
            return (KtSuperExpression) expression;
        }
        return null;
    }

    public static final KotlinType getEffectiveExpectedType(ValueParameterDescriptor parameterDescriptor, ResolvedValueArgument resolvedArgument, LanguageVersionSettings languageVersionSettings, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
        Intrinsics.checkNotNullParameter(resolvedArgument, "resolvedArgument");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(trace, "trace");
        List<ValueArgument> arguments = resolvedArgument.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        ValueArgument valueArgument = (ValueArgument) CollectionsKt.singleOrNull((List) arguments);
        if (valueArgument != null) {
            return getEffectiveExpectedTypeForSingleArgument(parameterDescriptor, valueArgument, languageVersionSettings, trace);
        }
        return getExpectedType(parameterDescriptor);
    }

    public static final KotlinType getEffectiveExpectedType(ValueParameterDescriptor parameterDescriptor, ValueArgument argument, ResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(context, "context");
        LanguageVersionSettings languageVersionSettings = context.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        return getEffectiveExpectedTypeForSingleArgument(parameterDescriptor, argument, languageVersionSettings, trace);
    }

    public static final KotlinType getEffectiveExpectedTypeForSingleArgument(ValueParameterDescriptor parameterDescriptor, ValueArgument argument, LanguageVersionSettings languageVersionSettings, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(trace, "trace");
        if (argument.getSpreadElement() != null) {
            if (parameterDescriptor.getVarargElementType() == null) {
                SimpleType DONT_CARE = TypeUtils.DONT_CARE;
                Intrinsics.checkNotNullExpressionValue(DONT_CARE, "DONT_CARE");
                return DONT_CARE;
            }
            KotlinType type = parameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return type;
        }
        if (arrayAssignmentToVarargInNamedFormInAnnotation(parameterDescriptor, argument, languageVersionSettings, trace) || arrayAssignmentToVarargInNamedFormInFunction(parameterDescriptor, argument, languageVersionSettings, trace)) {
            KotlinType type2 = parameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            return type2;
        }
        return getExpectedType(parameterDescriptor);
    }

    private static final KotlinType getExpectedType(ValueParameterDescriptor valueParameterDescriptor) {
        KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
        if (varargElementType != null) {
            return varargElementType;
        }
        KotlinType type = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return type;
    }

    private static final boolean arrayAssignmentToVarargInNamedFormInAnnotation(ValueParameterDescriptor valueParameterDescriptor, ValueArgument valueArgument, LanguageVersionSettings languageVersionSettings, BindingTrace bindingTrace) {
        if (languageVersionSettings.supportsFeature(LanguageFeature.AssigningArraysToVarargsInNamedFormInAnnotations)) {
            return (languageVersionSettings.supportsFeature(LanguageFeature.ProhibitAssigningSingleElementsToVarargsInNamedForm) || isArrayOrArrayLiteral(valueArgument, bindingTrace)) && DescriptorUtilsKt.isParameterOfAnnotation(valueParameterDescriptor) && valueArgument.isNamed() && ArgumentsUtilsKt.isVararg(valueParameterDescriptor);
        }
        return false;
    }

    private static final boolean arrayAssignmentToVarargInNamedFormInFunction(ValueParameterDescriptor valueParameterDescriptor, ValueArgument valueArgument, LanguageVersionSettings languageVersionSettings, BindingTrace bindingTrace) {
        if (languageVersionSettings.supportsFeature(LanguageFeature.AllowAssigningArrayElementsToVarargsInNamedFormForFunctions)) {
            return (languageVersionSettings.supportsFeature(LanguageFeature.ProhibitAssigningSingleElementsToVarargsInNamedForm) || isArrayOrArrayLiteral(valueArgument, bindingTrace)) && valueArgument.isNamed() && ArgumentsUtilsKt.isVararg(valueParameterDescriptor);
        }
        return false;
    }

    public static final boolean isArrayOrArrayLiteral(ValueArgument argument, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(trace, "trace");
        KtExpression argumentExpression = argument.getArgumentExpression();
        if (argumentExpression == null) {
            return false;
        }
        if (argumentExpression instanceof KtCollectionLiteralExpression) {
            return true;
        }
        KotlinType type = trace.getType(argumentExpression);
        if (type == null) {
            return false;
        }
        return KotlinBuiltIns.isArrayOrPrimitiveArray(type);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0060  */
    public static final List<OldResolutionCandidate<ConstructorDescriptor>> createResolutionCandidatesForConstructors(LexicalScope lexicalScope, Call call, KotlinType typeWithConstructors, boolean z, SyntheticScopes syntheticScopes) {
        TypeSubstitutor typeSubstitutorCreate;
        TypeAliasDescriptor typeAliasDescriptor;
        ArrayList constructors;
        ExplicitReceiverKind explicitReceiverKind;
        SimpleType defaultType;
        SimpleType simpleTypeSubstitute;
        Collection<TypeAliasConstructorDescriptor> constructors2;
        SimpleType abbreviation;
        Intrinsics.checkNotNullParameter(lexicalScope, "lexicalScope");
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(typeWithConstructors, "typeWithConstructors");
        Intrinsics.checkNotNullParameter(syntheticScopes, "syntheticScopes");
        ClassifierDescriptor this$0 = typeWithConstructors.getConstructor().getThis$0();
        Intrinsics.checkNotNull(this$0, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        ClassDescriptor classDescriptor = (ClassDescriptor) this$0;
        UnwrappedType unwrappedTypeUnwrap = typeWithConstructors.unwrap();
        ReceiverValue value = null;
        Object obj = null;
        if (z) {
            AbbreviatedType abbreviatedType = unwrappedTypeUnwrap instanceof AbbreviatedType ? (AbbreviatedType) unwrappedTypeUnwrap : null;
            typeSubstitutorCreate = TypeSubstitutor.create((abbreviatedType == null || (abbreviation = abbreviatedType.getAbbreviation()) == null) ? unwrappedTypeUnwrap : abbreviation);
        } else {
            typeSubstitutorCreate = null;
        }
        if (unwrappedTypeUnwrap instanceof AbbreviatedType) {
            ClassifierDescriptor this$1 = ((AbbreviatedType) unwrappedTypeUnwrap).getAbbreviation().getConstructor().getThis$0();
            if (this$1 instanceof TypeAliasDescriptor) {
                typeAliasDescriptor = (TypeAliasDescriptor) this$1;
            } else {
                typeAliasDescriptor = null;
            }
        } else {
            typeAliasDescriptor = null;
        }
        if (typeAliasDescriptor == null || (constructors2 = typeAliasDescriptor.getConstructors()) == null) {
            constructors = classDescriptor.getConstructors();
            Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        } else {
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = constructors2.iterator();
            while (it.hasNext()) {
                TypeAliasConstructorDescriptor withDispatchReceiver = ((TypeAliasConstructorDescriptor) it.next()).getWithDispatchReceiver();
                if (withDispatchReceiver != null) {
                    arrayList.add(withDispatchReceiver);
                }
            }
            constructors = arrayList;
        }
        if (constructors.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        if (classDescriptor.getIsInner()) {
            DeclarationDescriptor containingDeclaration = classDescriptor.getContainingDeclaration();
            ClassDescriptor classDescriptor2 = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor2 == null || (defaultType = classDescriptor2.getDefaultType()) == null) {
                return CollectionsKt.emptyList();
            }
            if (typeSubstitutorCreate == null || (simpleTypeSubstitute = typeSubstitutorCreate.substitute(defaultType, Variance.INVARIANT)) == null) {
                simpleTypeSubstitute = defaultType;
            }
            for (Object obj2 : ScopeUtilsKt.getImplicitReceiversHierarchy(lexicalScope)) {
                if (KotlinTypeChecker.DEFAULT.isSubtypeOf(((ReceiverParameterDescriptor) obj2).getType(), simpleTypeSubstitute)) {
                    obj = obj2;
                    break;
                }
            }
            ReceiverParameterDescriptor receiverParameterDescriptor = (ReceiverParameterDescriptor) obj;
            if (receiverParameterDescriptor == null) {
                return CollectionsKt.emptyList();
            }
            explicitReceiverKind = ExplicitReceiverKind.DISPATCH_RECEIVER;
            value = receiverParameterDescriptor.getValue();
        } else {
            explicitReceiverKind = ExplicitReceiverKind.NO_EXPLICIT_RECEIVER;
        }
        ArrayList arrayList2 = new ArrayList();
        for (ConstructorDescriptor constructorDescriptor : constructors) {
            Intrinsics.checkNotNull(constructorDescriptor);
            CollectionsKt.addAll(arrayList2, SyntheticScopesKt.collectSyntheticConstructors(syntheticScopes, constructorDescriptor));
        }
        List listPlus = CollectionsKt.plus((Collection) constructors, (Iterable) arrayList2);
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
        Iterator it2 = listPlus.iterator();
        while (it2.hasNext()) {
            arrayList3.add(OldResolutionCandidate.create(call, (ConstructorDescriptor) it2.next(), value, explicitReceiverKind, typeSubstitutorCreate));
        }
        return arrayList3;
    }

    public static final PsiElement reportOnElement(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        KtConstructorDelegationCall ktConstructorDelegationCall = psiElement instanceof KtConstructorDelegationCall ? (KtConstructorDelegationCall) psiElement : null;
        if (ktConstructorDelegationCall == null) {
            return psiElement;
        }
        if ((((KtConstructorDelegationCall) psiElement).isImplicit() ? ktConstructorDelegationCall : null) == null) {
            return psiElement;
        }
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElement, KtSecondaryConstructor.class, true);
        Intrinsics.checkNotNull(parentOfType);
        KtSecondaryConstructor ktSecondaryConstructor = (KtSecondaryConstructor) parentOfType;
        return ktSecondaryConstructor != null ? ktSecondaryConstructor : psiElement;
    }

    public static final List<KotlinCallArgument> replaceTypes(List<? extends KotlinCallArgument> list, BasicCallResolutionContext context, KotlinResolutionCallbacks resolutionCallbacks, Function2<? super Integer, ? super UnwrappedType, ? extends UnwrappedType> replace) {
        KtExpression psiExpression;
        EmptySubstitutor composedSubstitutor;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(replace, "replace");
        List<? extends KotlinCallArgument> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        int i = 0;
        for (Object obj : list2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ExpressionKotlinCallArgumentImpl expressionKotlinCallArgumentImpl = (KotlinCallArgument) obj;
            if ((expressionKotlinCallArgumentImpl instanceof SimpleKotlinCallArgument) && (psiExpression = NewCallArgumentsKt.getPsiExpression(expressionKotlinCallArgumentImpl)) != null) {
                if (expressionKotlinCallArgumentImpl instanceof SubKotlinCallArgument) {
                    SubKotlinCallArgument subKotlinCallArgument = (SubKotlinCallArgument) expressionKotlinCallArgumentImpl;
                    TypeSubstitutorMarker typeSubstitutorMarkerBuildNotFixedVariablesToPossibleResultType = InferenceUtilsKt.buildNotFixedVariablesToPossibleResultType(subKotlinCallArgument.getCallResult().getConstraintSystem(), resolutionCallbacks);
                    Intrinsics.checkNotNull(typeSubstitutorMarkerBuildNotFixedVariablesToPossibleResultType, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor");
                    TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = subKotlinCallArgument.getCallResult().getConstraintSystem().getBuilder().buildCurrentSubstitutor();
                    Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor");
                    composedSubstitutor = new ComposedSubstitutor((NewTypeSubstitutor) typeSubstitutorMarkerBuildNotFixedVariablesToPossibleResultType, (NewTypeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor);
                } else {
                    composedSubstitutor = EmptySubstitutor.INSTANCE;
                }
                UnwrappedType unwrappedTypeInvoke = replace.invoke(Integer.valueOf(i), composedSubstitutor.safeSubstitute(((SimpleKotlinCallArgument) expressionKotlinCallArgumentImpl).getReceiver().getReceiverValue().getType().unwrap()));
                if (unwrappedTypeInvoke != null) {
                    ValueArgument valueArgument = NewCallArgumentsKt.getPsiCallArgument(expressionKotlinCallArgumentImpl).getValueArgument();
                    DataFlowInfo dataFlowInfoBeforeThisArgument = NewCallArgumentsKt.getPsiCallArgument(expressionKotlinCallArgumentImpl).getDataFlowInfoBeforeThisArgument();
                    DataFlowInfo dataFlowInfoAfterThisArgument = NewCallArgumentsKt.getPsiCallArgument(expressionKotlinCallArgumentImpl).getDataFlowInfoAfterThisArgument();
                    BindingContext context2 = context.trace.getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    expressionKotlinCallArgumentImpl = new ExpressionKotlinCallArgumentImpl(valueArgument, dataFlowInfoBeforeThisArgument, dataFlowInfoAfterThisArgument, new ReceiverValueWithSmartCastInfo(ExpressionReceiver.INSTANCE.create(psiExpression, unwrappedTypeInvoke, context2), SetsKt.emptySet(), true, null, 8, null));
                }
            }
            arrayList.add(expressionKotlinCallArgumentImpl);
            i = i2;
        }
        return arrayList;
    }

    public static /* synthetic */ PSIKotlinCall replaceArguments$default(PSIKotlinCall pSIKotlinCall, List list, ReceiverKotlinCallArgument receiverKotlinCallArgument, int i, Object obj) {
        if ((i & 2) != 0) {
            receiverKotlinCallArgument = null;
        }
        return replaceArguments(pSIKotlinCall, list, receiverKotlinCallArgument);
    }

    public static final PSIKotlinCall replaceArguments(PSIKotlinCall pSIKotlinCall, List<? extends KotlinCallArgument> newArguments, ReceiverKotlinCallArgument receiverKotlinCallArgument) {
        Intrinsics.checkNotNullParameter(pSIKotlinCall, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        return new PSIKotlinCallImpl(pSIKotlinCall.getCallKind(), pSIKotlinCall.getPsiCall(), pSIKotlinCall.getTracingStrategy(), receiverKotlinCallArgument, pSIKotlinCall.getDispatchReceiverForInvokeExtension(), pSIKotlinCall.getName(), pSIKotlinCall.getTypeArguments(), newArguments, pSIKotlinCall.getExternalArgument(), pSIKotlinCall.getStartingDataFlowInfo(), pSIKotlinCall.getResultDataFlowInfo(), pSIKotlinCall.getDataFlowInfoForArguments(), pSIKotlinCall.getIsForImplicitInvoke());
    }

    public static final void checkForConstructorCallOnFunctionalType(KtTypeReference ktTypeReference, BasicCallResolutionContext context) {
        DiagnosticFactory0<PsiElement> diagnosticFactory0;
        Intrinsics.checkNotNullParameter(context, "context");
        if ((ktTypeReference != null ? ktTypeReference.getTypeElement() : null) instanceof KtFunctionType) {
            boolean zSupportsFeature = context.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitConstructorCallOnFunctionalSupertype);
            if (zSupportsFeature) {
                diagnosticFactory0 = Errors.NO_CONSTRUCTOR;
            } else if (!zSupportsFeature) {
                diagnosticFactory0 = Errors.NO_CONSTRUCTOR_WARNING;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            BindingTrace bindingTrace = context.trace;
            Call call = context.call;
            Intrinsics.checkNotNullExpressionValue(call, "call");
            bindingTrace.report(diagnosticFactory0.on(CallUtilKt.getValueArgumentListOrElement(call)));
        }
    }
}
