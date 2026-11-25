package org.jetbrains.kotlin.resolve.constants.evaluate;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.CompileTimeConstantUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ModuleAnnotationsResolver;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.checkers.OptInUsageChecker;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactory;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ConstantExpressionEvaluator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 C2\u00020\u0001:\u0001CB'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ(\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J.\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u00162\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 2\b\u0010!\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J,\u0010\"\u001a\u00020\u00142\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010#\u001a\b\u0012\u0002\b\u0003\u0018\u00010 2\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001a2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u0018J$\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010%2\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u0018J$\u0010&\u001a\b\u0012\u0002\b\u0003\u0018\u00010%2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*J \u0010+\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010,2\u0006\u0010\u001e\u001a\u00020-2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001c\u0010.\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010,2\n\u0010/\u001a\u0006\u0012\u0002\b\u000300H\u0002J \u00101\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010,2\u0006\u0010\u001e\u001a\u0002022\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u00103\u001a\u00020\u001c2\u0006\u00104\u001a\u00020*H\u0002J&\u00105\u001a\u00020\u001c2\u0014\u00106\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010%0,2\u0006\u0010)\u001a\u00020*H\u0002J\u0018\u00107\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J1\u00108\u001a\u0012\u0012\u0004\u0012\u00020:\u0012\b\u0012\u0006\u0012\u0002\b\u00030%092\n\u0010/\u001a\u0006\u0012\u0002\b\u0003002\u0006\u0010\u0019\u001a\u00020\u001aH\u0000¢\u0006\u0002\b;J2\u0010<\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030 0,2\u0006\u0010=\u001a\u00020*2\u0006\u0010>\u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J(\u0010?\u001a\u00020\u00142\u0006\u0010@\u001a\u00020\u00182\b\u0010\u001e\u001a\u0004\u0018\u00010\u00162\u0006\u0010A\u001a\u00020B2\u0006\u0010\u0019\u001a\u00020\u001aR\u0014\u0010\b\u001a\u00020\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;)V", "getInlineConstTracker$frontend", "()Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "getLanguageVersionSettings$frontend", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModule$frontend", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleAnnotationsResolver", "Lorg/jetbrains/kotlin/resolve/ModuleAnnotationsResolver;", "checkCompileTimeConstant", "", "argumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "useDeprecationWarning", "", "checkExperimentalityOfConstantLiteral", "expression", "constant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "expectedType", "checkInnerPartsOfCompileTimeConstant", "evaluateExpression", "evaluateToConstantValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getAnnotationArgumentValue", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "resolvedArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "getArgumentExpressionsForArrayCall", "", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "getArgumentExpressionsForArrayLikeCall", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getArgumentExpressionsForCollectionLiteralCall", "Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;", "hasSpread", "argument", "isArrayPassedInNamedForm", "constants", "reportDeprecationWarningOnNonConst", "resolveAnnotationArguments", "", "Lorg/jetbrains/kotlin/name/Name;", "resolveAnnotationArguments$frontend", "resolveAnnotationValueArguments", "resolvedValueArgument", "deprecatedExpectedType", "updateNumberType", "numberType", "statementFilter", "Lorg/jetbrains/kotlin/resolve/StatementFilter;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantExpressionEvaluator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final OptInUsageChecker.OptInReporterMultiplexer EXPERIMENTAL_UNSIGNED_LITERALS_DIAGNOSTICS;
    private final InlineConstTracker inlineConstTracker;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor module;
    private final ModuleAnnotationsResolver moduleAnnotationsResolver;

    @JvmStatic
    public static final CompileTimeConstant<?> getConstant(KtExpression ktExpression, BindingContext bindingContext) {
        return INSTANCE.getConstant(ktExpression, bindingContext);
    }

    @JvmStatic
    public static final CompileTimeConstant<?> getPossiblyErrorConstant(KtExpression ktExpression, BindingContext bindingContext) {
        return INSTANCE.getPossiblyErrorConstant(ktExpression, bindingContext);
    }

    public ConstantExpressionEvaluator(ModuleDescriptor module, LanguageVersionSettings languageVersionSettings, Project project, InlineConstTracker inlineConstTracker) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(inlineConstTracker, "inlineConstTracker");
        this.module = module;
        this.languageVersionSettings = languageVersionSettings;
        this.inlineConstTracker = inlineConstTracker;
        this.moduleAnnotationsResolver = ModuleAnnotationsResolver.INSTANCE.getInstance(project);
    }

    /* JADX INFO: renamed from: getModule$frontend, reason: from getter */
    public final ModuleDescriptor getModule() {
        return this.module;
    }

    /* JADX INFO: renamed from: getLanguageVersionSettings$frontend, reason: from getter */
    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public /* synthetic */ ConstantExpressionEvaluator(ModuleDescriptor moduleDescriptor, LanguageVersionSettings languageVersionSettings, Project project, InlineConstTracker.DoNothing doNothing, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, languageVersionSettings, project, (i & 8) != 0 ? InlineConstTracker.DoNothing.INSTANCE : doNothing);
    }

    /* JADX INFO: renamed from: getInlineConstTracker$frontend, reason: from getter */
    public final InlineConstTracker getInlineConstTracker() {
        return this.inlineConstTracker;
    }

    public final void updateNumberType(KotlinType numberType, KtExpression expression, StatementFilter statementFilter, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(numberType, "numberType");
        Intrinsics.checkNotNullParameter(statementFilter, "statementFilter");
        Intrinsics.checkNotNullParameter(trace, "trace");
        if (expression == null) {
            return;
        }
        BindingContextUtils.updateRecordedType(numberType, expression, trace, false);
        if (!(expression instanceof KtConstantExpression)) {
            KtExpression lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(expression, statementFilter);
            if (lastElementDeparenthesized != expression) {
                updateNumberType(numberType, lastElementDeparenthesized, statementFilter, trace);
                return;
            }
            return;
        }
        evaluateExpression(expression, trace, numberType);
    }

    public final Map<Name, ConstantValue<?>> resolveAnnotationArguments$frontend(ResolvedCall<?> resolvedCall, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(trace, "trace");
        HashMap map = new HashMap();
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : resolvedCall.getValueArguments().entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            ResolvedValueArgument value = entry.getValue();
            Intrinsics.checkNotNull(key);
            Intrinsics.checkNotNull(value);
            ConstantValue<?> annotationArgumentValue = getAnnotationArgumentValue(trace, key, value);
            if (annotationArgumentValue != null) {
                Name name2 = key.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                map.put(name2, annotationArgumentValue);
            }
        }
        return map;
    }

    public final ConstantValue<?> getAnnotationArgumentValue(BindingTrace trace, ValueParameterDescriptor parameterDescriptor, ResolvedValueArgument resolvedArgument) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
        Intrinsics.checkNotNullParameter(resolvedArgument, "resolvedArgument");
        KotlinType varargElementType = parameterDescriptor.getVarargElementType();
        boolean z = (varargElementType == null || hasSpread(resolvedArgument)) ? false : true;
        if (!z) {
            varargElementType = parameterDescriptor.getType();
        }
        KotlinType effectiveExpectedType = CallResolverUtilKt.getEffectiveExpectedType(parameterDescriptor, resolvedArgument, this.languageVersionSettings, trace);
        Intrinsics.checkNotNull(varargElementType);
        List<CompileTimeConstant<?>> listResolveAnnotationValueArguments = resolveAnnotationValueArguments(resolvedArgument, varargElementType, effectiveExpectedType, trace);
        List<CompileTimeConstant<?>> list = listResolveAnnotationValueArguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((CompileTimeConstant) it.next()).toConstantValue(effectiveExpectedType));
        }
        ArrayList arrayList2 = arrayList;
        if (z) {
            if (isArrayPassedInNamedForm(arrayList2, resolvedArgument)) {
                return (ConstantValue) CollectionsKt.single((List) arrayList2);
            }
            if (parameterDescriptor.declaresDefaultValue() && listResolveAnnotationValueArguments.isEmpty()) {
                return null;
            }
            ConstantValueFactory constantValueFactory = ConstantValueFactory.INSTANCE;
            KotlinType type = parameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return constantValueFactory.createArrayValue(arrayList2, type);
        }
        return (ConstantValue) CollectionsKt.lastOrNull((List) arrayList2);
    }

    private final boolean isArrayPassedInNamedForm(List<? extends ConstantValue<? extends Object>> constants, ResolvedValueArgument resolvedArgument) {
        ConstantValue constantValue = (ConstantValue) CollectionsKt.singleOrNull((List) constants);
        if (constantValue == null) {
            return false;
        }
        List<ValueArgument> arguments = resolvedArgument.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        ValueArgument valueArgument = (ValueArgument) CollectionsKt.singleOrNull((List) arguments);
        return valueArgument != null && (constantValue instanceof ArrayValue) && valueArgument.isNamed();
    }

    private final void checkCompileTimeConstant(KtExpression argumentExpression, KotlinType expressionType, BindingTrace trace, boolean useDeprecationWarning) {
        DiagnosticFactory0<KtExpression> diagnosticFactory0;
        Companion companion = INSTANCE;
        BindingContext bindingContext = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        CompileTimeConstant<?> constant = companion.getConstant(argumentExpression, bindingContext);
        if (constant != null && constant.getCanBeUsedInAnnotations()) {
            checkInnerPartsOfCompileTimeConstant(constant, trace, argumentExpression, useDeprecationWarning);
            return;
        }
        ClassifierDescriptor this$0 = expressionType.getConstructor().getThis$0();
        if (DescriptorUtils.isEnumClass(this$0)) {
            diagnosticFactory0 = Errors.ANNOTATION_ARGUMENT_MUST_BE_ENUM_CONST;
        } else if ((this$0 instanceof ClassDescriptor) && KotlinBuiltIns.isKClass((ClassDescriptor) this$0)) {
            TypeProjection typeProjection = (TypeProjection) CollectionsKt.singleOrNull((List) expressionType.getArguments());
            if (companion.isTypeParameterOrArrayOfTypeParameter$frontend(typeProjection != null ? typeProjection.getNullableAnyType() : null)) {
                diagnosticFactory0 = Errors.ANNOTATION_ARGUMENT_KCLASS_LITERAL_OF_TYPE_PARAMETER_ERROR;
            } else {
                diagnosticFactory0 = Errors.ANNOTATION_ARGUMENT_MUST_BE_KCLASS_LITERAL;
            }
        } else {
            diagnosticFactory0 = Errors.ANNOTATION_ARGUMENT_MUST_BE_CONST;
        }
        if (useDeprecationWarning) {
            reportDeprecationWarningOnNonConst(argumentExpression, trace);
        } else {
            trace.report(diagnosticFactory0.on(argumentExpression));
        }
    }

    private final void checkInnerPartsOfCompileTimeConstant(CompileTimeConstant<?> constant, BindingTrace trace, KtExpression argumentExpression, boolean useDeprecationWarning) {
        List<KtExpression> argumentExpressionsForCollectionLiteralCall;
        KtExpression receiverExpression;
        if (argumentExpression instanceof KtCallExpression) {
            argumentExpressionsForCollectionLiteralCall = getArgumentExpressionsForArrayCall((KtCallExpression) argumentExpression, trace);
        } else {
            argumentExpressionsForCollectionLiteralCall = argumentExpression instanceof KtCollectionLiteralExpression ? getArgumentExpressionsForCollectionLiteralCall((KtCollectionLiteralExpression) argumentExpression, trace) : null;
        }
        if (argumentExpressionsForCollectionLiteralCall != null) {
            for (KtExpression ktExpression : argumentExpressionsForCollectionLiteralCall) {
                KotlinType type = trace.getType(ktExpression);
                if (type != null) {
                    checkCompileTimeConstant(ktExpression, type, trace, useDeprecationWarning);
                }
            }
        }
        if (constant.getUsesNonConstValAsConstant()) {
            if (useDeprecationWarning) {
                reportDeprecationWarningOnNonConst(argumentExpression, trace);
            } else {
                trace.report(Errors.NON_CONST_VAL_USED_IN_CONSTANT_EXPRESSION.on(argumentExpression));
            }
        }
        if (!(argumentExpression instanceof KtClassLiteralExpression) || (receiverExpression = ((KtClassLiteralExpression) argumentExpression).getReceiverExpression()) == null) {
            return;
        }
        DoubleColonLHS doubleColonLHS = (DoubleColonLHS) trace.getContext().get(BindingContext.DOUBLE_COLON_LHS, receiverExpression);
        if (!(doubleColonLHS instanceof DoubleColonLHS.Expression) || ((DoubleColonLHS.Expression) doubleColonLHS).getIsObjectQualifier()) {
            if ((doubleColonLHS instanceof DoubleColonLHS.Type) && INSTANCE.isTypeParameterOrArrayOfTypeParameter$frontend(((DoubleColonLHS.Type) doubleColonLHS).getType())) {
                trace.report(Errors.ANNOTATION_ARGUMENT_KCLASS_LITERAL_OF_TYPE_PARAMETER.on(argumentExpression));
                return;
            }
            return;
        }
        if (useDeprecationWarning) {
            reportDeprecationWarningOnNonConst(argumentExpression, trace);
        } else {
            trace.report(Errors.ANNOTATION_ARGUMENT_MUST_BE_KCLASS_LITERAL.on(argumentExpression));
        }
    }

    private final void reportDeprecationWarningOnNonConst(KtExpression expression, BindingTrace trace) {
        trace.report(Errors.ANNOTATION_ARGUMENT_IS_NON_CONST.on(expression));
    }

    private final List<KtExpression> getArgumentExpressionsForArrayCall(KtCallExpression expression, BindingTrace trace) {
        BindingContext bindingContext = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, bindingContext);
        if (resolvedCall == null) {
            return null;
        }
        return getArgumentExpressionsForArrayLikeCall(resolvedCall);
    }

    private final List<KtExpression> getArgumentExpressionsForCollectionLiteralCall(KtCollectionLiteralExpression expression, BindingTrace trace) {
        ResolvedCall<?> resolvedCall = (ResolvedCall) trace.get(BindingContext.COLLECTION_LITERAL_CALL, expression);
        if (resolvedCall == null) {
            return null;
        }
        return getArgumentExpressionsForArrayLikeCall(resolvedCall);
    }

    private final List<KtExpression> getArgumentExpressionsForArrayLikeCall(ResolvedCall<?> resolvedCall) {
        if (!CompileTimeConstantUtils.isArrayFunctionCall(resolvedCall)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        Iterator<Map.Entry<ValueParameterDescriptor, ResolvedValueArgument>> it = valueArguments.entrySet().iterator();
        while (it.hasNext()) {
            Iterator<ValueArgument> it2 = it.next().getValue().getArguments().iterator();
            while (it2.hasNext()) {
                KtExpression argumentExpression = it2.next().getArgumentExpression();
                if (argumentExpression != null) {
                    arrayList.add(argumentExpression);
                }
            }
        }
        return arrayList;
    }

    private final boolean hasSpread(ResolvedValueArgument argument) {
        List<ValueArgument> arguments = argument.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        return arguments.size() == 1 && arguments.get(0).getSpreadElement() != null;
    }

    private final List<CompileTimeConstant<?>> resolveAnnotationValueArguments(ResolvedValueArgument resolvedValueArgument, KotlinType deprecatedExpectedType, KotlinType expectedType, BindingTrace trace) {
        ArrayList arrayList = new ArrayList();
        Iterator<ValueArgument> it = resolvedValueArgument.getArguments().iterator();
        while (it.hasNext()) {
            KtExpression argumentExpression = it.next().getArgumentExpression();
            if (argumentExpression != null) {
                CompileTimeConstant<?> compileTimeConstantEvaluateExpression = evaluateExpression(argumentExpression, trace, expectedType);
                if (compileTimeConstantEvaluateExpression instanceof IntegerValueTypeConstant) {
                    updateNumberType(((IntegerValueTypeConstant) compileTimeConstantEvaluateExpression).getType(expectedType), argumentExpression, StatementFilter.NONE, trace);
                }
                if (compileTimeConstantEvaluateExpression != null) {
                    arrayList.add(compileTimeConstantEvaluateExpression);
                }
                KotlinType type = trace.getType(argumentExpression);
                if (type != null) {
                    if (!this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitNonConstValuesAsVarargsInAnnotations) && !KotlinTypeChecker.DEFAULT.isSubtypeOf(type, deprecatedExpectedType)) {
                        if (KotlinTypeChecker.DEFAULT.isSubtypeOf(type, expectedType)) {
                            checkCompileTimeConstant(argumentExpression, type, trace, true);
                        }
                    } else {
                        checkCompileTimeConstant(argumentExpression, type, trace, false);
                    }
                }
            }
        }
        return arrayList;
    }

    public static /* synthetic */ CompileTimeConstant evaluateExpression$default(ConstantExpressionEvaluator constantExpressionEvaluator, KtExpression ktExpression, BindingTrace bindingTrace, KotlinType kotlinType, int i, Object obj) {
        if ((i & 4) != 0) {
            kotlinType = TypeUtils.NO_EXPECTED_TYPE;
        }
        return constantExpressionEvaluator.evaluateExpression(ktExpression, bindingTrace, kotlinType);
    }

    public final CompileTimeConstant<?> evaluateExpression(KtExpression expression, BindingTrace trace, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(trace, "trace");
        CompileTimeConstant<?> compileTimeConstantEvaluate = new ConstantExpressionEvaluatorVisitor(this, trace).evaluate(expression, expectedType);
        if (compileTimeConstantEvaluate == null) {
            return null;
        }
        checkExperimentalityOfConstantLiteral(expression, compileTimeConstantEvaluate, expectedType, trace);
        if (compileTimeConstantEvaluate.isError()) {
            return null;
        }
        return compileTimeConstantEvaluate;
    }

    public final ConstantValue<?> evaluateToConstantValue(KtExpression expression, BindingTrace trace, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        CompileTimeConstant<?> compileTimeConstantEvaluateExpression = evaluateExpression(expression, trace, expectedType);
        if (compileTimeConstantEvaluateExpression != null) {
            return compileTimeConstantEvaluateExpression.toConstantValue(expectedType);
        }
        return null;
    }

    private final void checkExperimentalityOfConstantLiteral(KtExpression expression, CompileTimeConstant<?> constant, KotlinType expectedType, BindingTrace trace) {
        KotlinType type;
        if (constant.isError()) {
            return;
        }
        if (constant.getParameters().isUnsignedNumberLiteral() || constant.getParameters().isUnsignedLongNumberLiteral()) {
            if (constant instanceof TypedCompileTimeConstant) {
                type = ((TypedCompileTimeConstant) constant).getType();
            } else if (expectedType == null) {
                return;
            } else {
                type = constant.toConstantValue(expectedType).getType(this.module);
            }
            if (UnsignedTypes.isUnsignedType(type)) {
                OptInUsageChecker.Companion companion = OptInUsageChecker.INSTANCE;
                ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
                if (this$0 == null) {
                    return;
                }
                Intrinsics.checkNotNull(this$0);
                ModuleAnnotationsResolver moduleAnnotationsResolver = this.moduleAnnotationsResolver;
                BindingContext bindingContext = trace.getContext();
                Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
                companion.reportNotAllowedOptIns(OptInUsageChecker.Companion.loadOptIns$default(companion, this$0, moduleAnnotationsResolver, bindingContext, this.languageVersionSettings, null, false, false, false, 120, null), expression, this.languageVersionSettings, trace, EXPERIMENTAL_UNSIGNED_LITERALS_DIAGNOSTICS);
            }
        }
    }

    /* JADX INFO: compiled from: ConstantExpressionEvaluator.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0013B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u001e\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u0006J\u0017\u0010\u000f\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0000¢\u0006\u0002\b\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator$Companion;", "", "()V", "EXPERIMENTAL_UNSIGNED_LITERALS_DIAGNOSTICS", "Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInReporterMultiplexer;", "getConstant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getPossiblyErrorConstant", "isComplexBooleanConstant", "", "constant", "isTypeParameterOrArrayOfTypeParameter", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isTypeParameterOrArrayOfTypeParameter$frontend", "ExperimentalityDiagnostic1", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: ConstantExpressionEvaluator.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J*\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u0016R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator$Companion$ExperimentalityDiagnostic1;", "Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInDiagnosticReporter;", "factory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "verb", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;Ljava/lang/String;)V", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "getVerb", "()Ljava/lang/String;", "report", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "fqName", "Lorg/jetbrains/kotlin/name/FqName;", CrashHianalyticsData.MESSAGE, "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private static final class ExperimentalityDiagnostic1 implements OptInUsageChecker.OptInDiagnosticReporter {
            private final DiagnosticFactory1<PsiElement, String> factory;
            private final String verb;

            public ExperimentalityDiagnostic1(DiagnosticFactory1<PsiElement, String> factory, String verb) {
                Intrinsics.checkNotNullParameter(factory, "factory");
                Intrinsics.checkNotNullParameter(verb, "verb");
                this.factory = factory;
                this.verb = verb;
            }

            public final DiagnosticFactory1<PsiElement, String> getFactory() {
                return this.factory;
            }

            public final String getVerb() {
                return this.verb;
            }

            @Override // org.jetbrains.kotlin.resolve.checkers.OptInUsageChecker.OptInDiagnosticReporter
            public void report(BindingTrace trace, PsiElement element, FqName fqName, String message) {
                Intrinsics.checkNotNullParameter(trace, "trace");
                Intrinsics.checkNotNullParameter(element, "element");
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                Diagnostic diagnosticOn = this.factory.on(element, OptInUsageChecker.INSTANCE.getDefaultDiagnosticMessage$frontend("Unsigned literals are experimental and their usages " + this.verb + " be marked").invoke(fqName));
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
            }
        }

        private Companion() {
        }

        @JvmStatic
        public final CompileTimeConstant<?> getConstant(KtExpression expression, BindingContext bindingContext) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            CompileTimeConstant<?> possiblyErrorConstant = getPossiblyErrorConstant(expression, bindingContext);
            if (possiblyErrorConstant == null || possiblyErrorConstant.isError()) {
                return null;
            }
            return possiblyErrorConstant;
        }

        @JvmStatic
        public final CompileTimeConstant<?> getPossiblyErrorConstant(KtExpression expression, BindingContext bindingContext) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            return (CompileTimeConstant) bindingContext.get(BindingContext.COMPILE_TIME_VALUE, expression);
        }

        public final boolean isTypeParameterOrArrayOfTypeParameter$frontend(KotlinType type) {
            if (type == null) {
                return false;
            }
            if (!KotlinBuiltIns.isArray(type)) {
                return type.getConstructor().getThis$0() instanceof TypeParameterDescriptor;
            }
            TypeProjection typeProjection = (TypeProjection) CollectionsKt.singleOrNull((List) type.getArguments());
            return isTypeParameterOrArrayOfTypeParameter$frontend(typeProjection != null ? typeProjection.getNullableAnyType() : null);
        }

        public final boolean isComplexBooleanConstant(KtExpression expression, CompileTimeConstant<?> constant) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(constant, "constant");
            if (constant.isError()) {
                return false;
            }
            SimpleType booleanType = constant.getModuleDescriptor().getBuiltIns().getBooleanType();
            Intrinsics.checkNotNullExpressionValue(booleanType, "getBooleanType(...)");
            return (!TypeUtilsKt.isBoolean(constant.toConstantValue(booleanType).getType(constant.getModuleDescriptor())) || (expression instanceof KtConstantExpression) || constant.getParameters().getUsesVariableAsConstant()) ? false : true;
        }
    }

    static {
        DiagnosticFactory1<PsiElement, String> EXPERIMENTAL_UNSIGNED_LITERALS = Errors.EXPERIMENTAL_UNSIGNED_LITERALS;
        Intrinsics.checkNotNullExpressionValue(EXPERIMENTAL_UNSIGNED_LITERALS, "EXPERIMENTAL_UNSIGNED_LITERALS");
        Companion.ExperimentalityDiagnostic1 experimentalityDiagnostic1 = new Companion.ExperimentalityDiagnostic1(EXPERIMENTAL_UNSIGNED_LITERALS, "should");
        DiagnosticFactory1<PsiElement, String> EXPERIMENTAL_UNSIGNED_LITERALS_ERROR = Errors.EXPERIMENTAL_UNSIGNED_LITERALS_ERROR;
        Intrinsics.checkNotNullExpressionValue(EXPERIMENTAL_UNSIGNED_LITERALS_ERROR, "EXPERIMENTAL_UNSIGNED_LITERALS_ERROR");
        Companion.ExperimentalityDiagnostic1 experimentalityDiagnostic2 = new Companion.ExperimentalityDiagnostic1(EXPERIMENTAL_UNSIGNED_LITERALS_ERROR, "must");
        DiagnosticFactory1<PsiElement, String> EXPERIMENTAL_UNSIGNED_LITERALS_ERROR2 = Errors.EXPERIMENTAL_UNSIGNED_LITERALS_ERROR;
        Intrinsics.checkNotNullExpressionValue(EXPERIMENTAL_UNSIGNED_LITERALS_ERROR2, "EXPERIMENTAL_UNSIGNED_LITERALS_ERROR");
        EXPERIMENTAL_UNSIGNED_LITERALS_DIAGNOSTICS = new OptInUsageChecker.OptInReporterMultiplexer(experimentalityDiagnostic1, experimentalityDiagnostic2, new Companion.ExperimentalityDiagnostic1(EXPERIMENTAL_UNSIGNED_LITERALS_ERROR2, "must"));
    }
}
