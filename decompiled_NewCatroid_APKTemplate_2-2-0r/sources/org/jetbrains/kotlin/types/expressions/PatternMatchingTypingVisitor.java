package org.jetbrains.kotlin.types.expressions;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PlatformSpecificCastChecker;
import org.jetbrains.kotlin.builtins.PlatformToKotlinClassMapper;
import org.jetbrains.kotlin.cfg.WhenChecker;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.EffectSystem;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.LocalVariableResolver;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.TypeResolutionContext;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiExpressionChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiExpressionInformation;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiOperation;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.smartcasts.ConditionalDataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastResult;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.checkers.ConfusingWhenBranchSyntaxChecker;
import org.jetbrains.kotlin.resolve.checkers.PrimitiveNumericComparisonCallChecker;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.types.CastDiagnosticsUtil;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.EnumCompatibilityCheckerKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: PatternMatchingTypingVisitor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001CB\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J(\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J.\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00190\u001eH\u0002J(\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\"H\u0002J \u0010#\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00172\u0006\u0010\f\u001a\u00020\rH\u0002J8\u0010$\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020\"2\u0006\u0010&\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0002J \u0010+\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010,\u001a\u00020-2\u0006\u0010\u0012\u001a\u00020\u000bH\u0002J@\u0010.\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010/\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\"2\u0006\u0010&\u001a\u00020\u00142\u0006\u0010)\u001a\u00020*2\u0006\u00100\u001a\u00020\u0014H\u0002J8\u00101\u001a\u0004\u0018\u00010\u00192\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u00102\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b2\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u000704H\u0002J4\u00105\u001a\u0002062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u00107\u001a\u0004\u0018\u00010\u00192\u0006\u00108\u001a\u00020\u00142\b\u00109\u001a\u0004\u0018\u00010*H\u0002J\u0010\u0010:\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000bH\u0002J\u0018\u0010;\u001a\u00020\r2\u0006\u0010<\u001a\u00020=2\u0006\u0010\u0015\u001a\u00020\u000bH\u0002J\u0018\u0010>\u001a\u0002062\u0006\u0010\b\u001a\u00020?2\u0006\u00102\u001a\u00020\u000bH\u0016J\u0018\u0010@\u001a\u0002062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000bH\u0016J\u001e\u0010@\u001a\u0002062\u0006\u0010\b\u001a\u00020\t2\u0006\u00102\u001a\u00020\u000b2\u0006\u0010A\u001a\u00020\u0014J\u0016\u0010B\u001a\b\u0012\u0004\u0012\u00020\u0017042\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/PatternMatchingTypingVisitor;", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingVisitor;", "facade", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingInternals;", "(Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingInternals;)V", "analyzeConditionsInWhenEntries", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "expression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "contextAfterSubject", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "subject", "Lorg/jetbrains/kotlin/types/expressions/PatternMatchingTypingVisitor$Subject;", "analyzeWhenEntryConditions", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ConditionalDataFlowInfo;", "whenEntry", "Lorg/jetbrains/kotlin/psi/KtWhenEntry;", "context", "checkSmartCastToExpectedTypeInSubject", "", "contextBeforeSubject", "subjectExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "subjectType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expectedType", "checkSmartCastsInSubjectIfRequired", "", "possibleTypesForSubject", "", "checkTypeCompatibility", ModuleXmlParser.TYPE, "reportErrorOn", "Lorg/jetbrains/kotlin/psi/KtElement;", "checkTypeForExpressionCondition", "checkTypeForIs", "isCheck", "negated", "typeReferenceAfterIs", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "subjectDataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "checkWhenCondition", "condition", "Lorg/jetbrains/kotlin/psi/KtWhenCondition;", "detectRedundantIs", "targetType", "typesAreCompatible", "inferTypeForWhenExpression", "contextWithExpectedType", "dataFlowInfoForEntries", "", "joinWhenExpressionBranches", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "resultType", "jumpOutPossibleInSubject", "whenResultValue", "noChange", "processVariableSubject", "subjectVariable", "Lorg/jetbrains/kotlin/psi/KtProperty;", "visitIsExpression", "Lorg/jetbrains/kotlin/psi/KtIsExpression;", "visitWhenExpression", "isStatement", "wrapWhenEntryExpressionsAsSpecialCallArguments", "Subject", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PatternMatchingTypingVisitor extends ExpressionTypingVisitor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PatternMatchingTypingVisitor(ExpressionTypingInternals facade) {
        super(facade);
        Intrinsics.checkNotNullParameter(facade, "facade");
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitIsExpression(KtIsExpression expression, ExpressionTypingContext contextWithExpectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(contextWithExpectedType, "contextWithExpectedType");
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = contextWithExpectedType.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceContextDependency, "replaceContextDependency(...)");
        ExpressionTypingContext expressionTypingContext = expressionTypingContextReplaceContextDependency;
        KtExpression leftHandSide = expression.getLeftHandSide();
        Intrinsics.checkNotNullExpressionValue(leftHandSide, "getLeftHandSide(...)");
        KotlinTypeInfo kotlinTypeInfoSafeGetTypeInfo = this.facade.safeGetTypeInfo(leftHandSide, expressionTypingContext);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeInfoSafeGetTypeInfo, "safeGetTypeInfo(...)");
        KotlinType type = kotlinTypeInfoSafeGetTypeInfo.getType();
        KtTypeReference typeReference = expression.getTypeReference();
        if (typeReference != null && type != null) {
            expressionTypingContext.trace.record(BindingContext.DATAFLOW_INFO_AFTER_CONDITION, expression, checkTypeForIs(expressionTypingContext, expression, expression.isNegated(), type, typeReference, this.components.dataFlowValueFactory.createDataFlowValue(leftHandSide, type, expressionTypingContext)).getThenInfo().and(kotlinTypeInfoSafeGetTypeInfo.getDataFlowInfo()));
        }
        DefinitelyNotNullDeprecationKt.reportDeprecatedDefinitelyNotNullSyntax(expression, expression.getTypeReference(), contextWithExpectedType);
        KotlinTypeInfo kotlinTypeInfoCheckType = this.components.dataFlowAnalyzer.checkType(kotlinTypeInfoSafeGetTypeInfo.replaceType(this.components.builtIns.getBooleanType()), expression, contextWithExpectedType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeInfoCheckType, "checkType(...)");
        if (typeReference != null) {
            RttiExpressionInformation rttiExpressionInformation = new RttiExpressionInformation(leftHandSide, type, (KotlinType) expressionTypingContext.trace.get(BindingContext.TYPE, typeReference), expression.isNegated() ? RttiOperation.NOT_IS : RttiOperation.IS);
            Iterable<RttiExpressionChecker> rttiExpressionCheckers = this.components.rttiExpressionCheckers;
            Intrinsics.checkNotNullExpressionValue(rttiExpressionCheckers, "rttiExpressionCheckers");
            Iterator<RttiExpressionChecker> it = rttiExpressionCheckers.iterator();
            while (it.hasNext()) {
                BindingTrace trace = expressionTypingContext.trace;
                Intrinsics.checkNotNullExpressionValue(trace, "trace");
                it.next().check(rttiExpressionInformation, expression, trace);
            }
        }
        return kotlinTypeInfoCheckType;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitWhenExpression(KtWhenExpression expression, ExpressionTypingContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        return visitWhenExpression(expression, context, false);
    }

    /* JADX INFO: compiled from: PatternMatchingTypingVisitor.kt */
    @Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\"\u0018\u00002\u00020\u0001:\u0003./0B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0018\u0010$\u001a\u00020\u00102\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H$J\n\u0010)\u001a\u0004\u0018\u00010!H\u0016J\u0016\u0010*\u001a\u00020+2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(J\n\u0010,\u001a\u0004\u0018\u00010-H&R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010@BX\u0086.¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u0004\u0018\u00010!X¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/PatternMatchingTypingVisitor$Subject;", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "typeInfo", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "scopeWithSubject", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;Lorg/jetbrains/kotlin/types/KotlinType;)V", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDataFlowInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "<set-?>", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "dataFlowValue", "getDataFlowValue", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "getElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "jumpOutPossible", "", "getJumpOutPossible", "()Z", "getScopeWithSubject", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getTypeInfo", "()Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "valueExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getValueExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "createDataFlowValue", "contextAfterSubject", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getCalleeExpressionForSpecialCall", "initDataFlowValue", "", "makeValueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "Expression", "None", "Variable", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class Subject {
        private DataFlowValue dataFlowValue;
        private final KtElement element;
        private final LexicalScope scopeWithSubject;
        private final KotlinType type;
        private final KotlinTypeInfo typeInfo;

        protected abstract DataFlowValue createDataFlowValue(ExpressionTypingContext contextAfterSubject, KotlinBuiltIns builtIns);

        public KtExpression getCalleeExpressionForSpecialCall() {
            return null;
        }

        public abstract KtExpression getValueExpression();

        public abstract ValueArgument makeValueArgument();

        public Subject(KtElement ktElement, KotlinTypeInfo kotlinTypeInfo, LexicalScope lexicalScope, KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.element = ktElement;
            this.typeInfo = kotlinTypeInfo;
            this.scopeWithSubject = lexicalScope;
            this.type = type;
        }

        public final KtElement getElement() {
            return this.element;
        }

        public final KotlinTypeInfo getTypeInfo() {
            return this.typeInfo;
        }

        public final LexicalScope getScopeWithSubject() {
            return this.scopeWithSubject;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public /* synthetic */ Subject(KtElement ktElement, KotlinTypeInfo kotlinTypeInfo, LexicalScope lexicalScope, KotlinType kotlinType, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 8) != 0 && (kotlinTypeInfo == null || (kotlinType = kotlinTypeInfo.getType()) == null)) {
                kotlinType = ErrorUtils.createErrorType(ErrorTypeKind.UNKNOWN_TYPE, new String[0]);
            }
            this(ktElement, kotlinTypeInfo, lexicalScope, kotlinType);
        }

        public final KotlinType getType() {
            return this.type;
        }

        public final DataFlowValue getDataFlowValue() {
            DataFlowValue dataFlowValue = this.dataFlowValue;
            if (dataFlowValue != null) {
                return dataFlowValue;
            }
            Intrinsics.throwUninitializedPropertyAccessException("dataFlowValue");
            return null;
        }

        public final void initDataFlowValue(ExpressionTypingContext contextAfterSubject, KotlinBuiltIns builtIns) {
            Intrinsics.checkNotNullParameter(contextAfterSubject, "contextAfterSubject");
            Intrinsics.checkNotNullParameter(builtIns, "builtIns");
            this.dataFlowValue = createDataFlowValue(contextAfterSubject, builtIns);
        }

        public final DataFlowInfo getDataFlowInfo() {
            KotlinTypeInfo kotlinTypeInfo = this.typeInfo;
            if (kotlinTypeInfo != null) {
                return kotlinTypeInfo.getDataFlowInfo();
            }
            return null;
        }

        public final boolean getJumpOutPossible() {
            KotlinTypeInfo kotlinTypeInfo = this.typeInfo;
            if (kotlinTypeInfo != null) {
                return kotlinTypeInfo.getJumpOutPossible();
            }
            return false;
        }

        /* JADX INFO: compiled from: PatternMatchingTypingVisitor.kt */
        @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/PatternMatchingTypingVisitor$Subject$Expression;", "Lorg/jetbrains/kotlin/types/expressions/PatternMatchingTypingVisitor$Subject;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "typeInfo", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "(Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;)V", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "valueExpression", "getValueExpression", "createDataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "contextAfterSubject", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "makeValueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Expression extends Subject {
            private final DataFlowValueFactory dataFlowValueFactory;
            private final KtExpression expression;

            public final KtExpression getExpression() {
                return this.expression;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Expression(KtExpression expression, KotlinTypeInfo typeInfo, DataFlowValueFactory dataFlowValueFactory) {
                super(expression, typeInfo, null, null, 8, null);
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(typeInfo, "typeInfo");
                Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
                this.expression = expression;
                this.dataFlowValueFactory = dataFlowValueFactory;
            }

            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            protected DataFlowValue createDataFlowValue(ExpressionTypingContext contextAfterSubject, KotlinBuiltIns builtIns) {
                Intrinsics.checkNotNullParameter(contextAfterSubject, "contextAfterSubject");
                Intrinsics.checkNotNullParameter(builtIns, "builtIns");
                return this.dataFlowValueFactory.createDataFlowValue(this.expression, getType(), contextAfterSubject);
            }

            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            public ValueArgument makeValueArgument() {
                ValueArgument valueArgumentMakeExternalValueArgument = CallMaker.makeExternalValueArgument(this.expression);
                Intrinsics.checkNotNullExpressionValue(valueArgumentMakeExternalValueArgument, "makeExternalValueArgument(...)");
                return valueArgumentMakeExternalValueArgument;
            }

            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            public KtExpression getValueExpression() {
                return this.expression;
            }
        }

        /* JADX INFO: compiled from: PatternMatchingTypingVisitor.kt */
        @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u000eH\u0016J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/PatternMatchingTypingVisitor$Subject$Variable;", "Lorg/jetbrains/kotlin/types/expressions/PatternMatchingTypingVisitor$Subject;", "variable", "Lorg/jetbrains/kotlin/psi/KtProperty;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "typeInfo", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "scopeWithSubject", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "(Lorg/jetbrains/kotlin/psi/KtProperty;Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "valueExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getValueExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getVariable", "()Lorg/jetbrains/kotlin/psi/KtProperty;", "createDataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "contextAfterSubject", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getCalleeExpressionForSpecialCall", "makeValueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Variable extends Subject {
            private final VariableDescriptor descriptor;
            private final KtProperty variable;

            public final KtProperty getVariable() {
                return this.variable;
            }

            public final VariableDescriptor getDescriptor() {
                return this.descriptor;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Variable(KtProperty variable, VariableDescriptor descriptor, KotlinTypeInfo typeInfo, LexicalScope scopeWithSubject) {
                super(variable, typeInfo, scopeWithSubject, null, 8, null);
                Intrinsics.checkNotNullParameter(variable, "variable");
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                Intrinsics.checkNotNullParameter(typeInfo, "typeInfo");
                Intrinsics.checkNotNullParameter(scopeWithSubject, "scopeWithSubject");
                this.variable = variable;
                this.descriptor = descriptor;
            }

            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            protected DataFlowValue createDataFlowValue(ExpressionTypingContext contextAfterSubject, KotlinBuiltIns builtIns) {
                Intrinsics.checkNotNullParameter(contextAfterSubject, "contextAfterSubject");
                Intrinsics.checkNotNullParameter(builtIns, "builtIns");
                IdentifierInfo.Variable variable = new IdentifierInfo.Variable(this.descriptor, DataFlowValue.Kind.STABLE_VALUE, (DataFlowValue) contextAfterSubject.trace.getContext().get(BindingContext.BOUND_INITIALIZER_VALUE, this.descriptor));
                KotlinType type = this.descriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                return new DataFlowValue(variable, type, null, 4, null);
            }

            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            public ValueArgument makeValueArgument() {
                KtExpression initializer = this.variable.getInitializer();
                if (initializer == null) {
                    return null;
                }
                Project project = this.variable.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                KtPsiFactory ktPsiFactory = new KtPsiFactory(project, true);
                String name2 = this.variable.get$name();
                Intrinsics.checkNotNull(name2);
                return CallMaker.makeExternalValueArgument(ktPsiFactory.createExpression(name2), initializer);
            }

            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            public KtExpression getCalleeExpressionForSpecialCall() {
                return this.variable;
            }

            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            public KtExpression getValueExpression() {
                return this.variable.getInitializer();
            }
        }

        /* JADX INFO: compiled from: PatternMatchingTypingVisitor.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\n\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/PatternMatchingTypingVisitor$Subject$None;", "Lorg/jetbrains/kotlin/types/expressions/PatternMatchingTypingVisitor$Subject;", "()V", "valueExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getValueExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "createDataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "contextAfterSubject", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "makeValueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class None extends Subject {
            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            public KtExpression getValueExpression() {
                return null;
            }

            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            public ValueArgument makeValueArgument() {
                return null;
            }

            public None() {
                super(null, null, null, null, 8, null);
            }

            @Override // org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.Subject
            protected DataFlowValue createDataFlowValue(ExpressionTypingContext contextAfterSubject, KotlinBuiltIns builtIns) {
                Intrinsics.checkNotNullParameter(contextAfterSubject, "contextAfterSubject");
                Intrinsics.checkNotNullParameter(builtIns, "builtIns");
                return DataFlowValue.INSTANCE.nullValue(builtIns);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x00e4  */
    public final KotlinTypeInfo visitWhenExpression(KtWhenExpression expression, ExpressionTypingContext contextWithExpectedType, boolean isStatement) {
        Subject.None none;
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo;
        Set<KotlinType> setEmptySet;
        DataFlowInfo dataFlowInfo;
        ExpressionTypingContext expressionTypingContextReplaceScope;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(contextWithExpectedType, "contextWithExpectedType");
        BindingTrace trace = contextWithExpectedType.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        WhenChecker.INSTANCE.checkDeprecatedWhenSyntax(trace, expression);
        PsiElement whenKeyword = expression.getWhenKeyword();
        Intrinsics.checkNotNullExpressionValue(whenKeyword, "getWhenKeyword(...)");
        WhenChecker.INSTANCE.checkSealedWhenIsReserved(trace, whenKeyword);
        KtWhenExpression ktWhenExpression = expression;
        this.components.dataFlowAnalyzer.recordExpectedType(trace, ktWhenExpression, contextWithExpectedType.expectedType);
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = contextWithExpectedType.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceContextDependency, "replaceContextDependency(...)");
        ExpressionTypingContext expressionTypingContext = expressionTypingContextReplaceContextDependency;
        KtExpression subjectExpression = expression.getSubjectExpression();
        KtProperty subjectVariable = expression.getSubjectVariable();
        if (subjectVariable != null) {
            none = processVariableSubject(subjectVariable, expressionTypingContext);
        } else if (subjectExpression != null) {
            KotlinTypeInfo typeInfo = this.facade.getTypeInfo(subjectExpression, expressionTypingContext);
            Intrinsics.checkNotNullExpressionValue(typeInfo, "getTypeInfo(...)");
            DataFlowValueFactory dataFlowValueFactory = this.components.dataFlowValueFactory;
            Intrinsics.checkNotNullExpressionValue(dataFlowValueFactory, "dataFlowValueFactory");
            none = new Subject.Expression(subjectExpression, typeInfo, dataFlowValueFactory);
        } else {
            none = new Subject.None();
        }
        LexicalScope scopeWithSubject = none.getScopeWithSubject();
        if (scopeWithSubject != null) {
            expressionTypingContextReplaceDataFlowInfo = expressionTypingContext.replaceScope(scopeWithSubject);
            Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceDataFlowInfo, "replaceScope(...)");
        } else {
            expressionTypingContextReplaceDataFlowInfo = expressionTypingContext;
        }
        DataFlowInfo dataFlowInfo2 = none.getDataFlowInfo();
        if (dataFlowInfo2 != null) {
            expressionTypingContextReplaceDataFlowInfo = expressionTypingContextReplaceDataFlowInfo.replaceDataFlowInfo(dataFlowInfo2);
            Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
        }
        ExpressionTypingContext expressionTypingContext2 = expressionTypingContextReplaceDataFlowInfo;
        LexicalScope scopeWithSubject2 = none.getScopeWithSubject();
        ExpressionTypingContext expressionTypingContext3 = (scopeWithSubject2 == null || (expressionTypingContextReplaceScope = contextWithExpectedType.replaceScope(scopeWithSubject2)) == null) ? contextWithExpectedType : expressionTypingContextReplaceScope;
        KotlinBuiltIns builtIns = this.components.builtIns;
        Intrinsics.checkNotNullExpressionValue(builtIns, "builtIns");
        none.initDataFlowValue(expressionTypingContext2, builtIns);
        KotlinTypeInfo typeInfo2 = none.getTypeInfo();
        if (typeInfo2 == null || (dataFlowInfo = typeInfo2.getDataFlowInfo()) == null) {
            setEmptySet = SetsKt.emptySet();
        } else {
            DataFlowValue dataFlowValue = none.getDataFlowValue();
            LanguageVersionSettings languageVersionSettings = this.components.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            setEmptySet = dataFlowInfo.getStableTypes(dataFlowValue, languageVersionSettings);
            if (setEmptySet == null) {
                setEmptySet = SetsKt.emptySet();
            }
        }
        checkSmartCastsInSubjectIfRequired(expression, expressionTypingContext, none.getType(), setEmptySet);
        KotlinType kotlinTypeInferTypeForWhenExpression = inferTypeForWhenExpression(expression, none, expressionTypingContext3, expressionTypingContext2, analyzeConditionsInWhenEntries(expression, expressionTypingContext2, none));
        KotlinTypeInfo kotlinTypeInfoJoinWhenExpressionBranches = joinWhenExpressionBranches(expression, expressionTypingContext2, kotlinTypeInferTypeForWhenExpression, none.getJumpOutPossible(), kotlinTypeInferTypeForWhenExpression != null ? this.facade.getComponents().dataFlowValueFactory.createDataFlowValue(ktWhenExpression, kotlinTypeInferTypeForWhenExpression, expressionTypingContext2) : null);
        boolean zIsWhenExhaustive = WhenChecker.isWhenExhaustive(expression, trace);
        DataFlowInfo dataFlowInfo3 = kotlinTypeInfoJoinWhenExpressionBranches.getDataFlowInfo();
        if (expression.getElseExpression() == null && !zIsWhenExhaustive) {
            DataFlowInfo dataFlowInfo4 = expressionTypingContext2.dataFlowInfo;
            Intrinsics.checkNotNullExpressionValue(dataFlowInfo4, "dataFlowInfo");
            dataFlowInfo3 = dataFlowInfo3.or(dataFlowInfo4);
        }
        if (kotlinTypeInferTypeForWhenExpression != null && zIsWhenExhaustive && expression.getElseExpression() == null && KotlinBuiltIns.isNothing(kotlinTypeInferTypeForWhenExpression)) {
            trace.record(BindingContext.IMPLICIT_EXHAUSTIVE_WHEN, expression);
        }
        KotlinType type = kotlinTypeInfoJoinWhenExpressionBranches.getType();
        if (type == null) {
            return TypeInfoFactoryKt.noTypeInfo(dataFlowInfo3);
        }
        KotlinType kotlinTypeCheckType = this.components.dataFlowAnalyzer.checkType(type, ktWhenExpression, contextWithExpectedType);
        ConfusingWhenBranchSyntaxChecker confusingWhenBranchSyntaxChecker = ConfusingWhenBranchSyntaxChecker.INSTANCE;
        LanguageVersionSettings languageVersionSettings2 = contextWithExpectedType.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings2, "languageVersionSettings");
        confusingWhenBranchSyntaxChecker.check(expression, languageVersionSettings2, trace);
        boolean jumpOutPossible = kotlinTypeInfoJoinWhenExpressionBranches.getJumpOutPossible();
        DataFlowInfo dataFlowInfo5 = contextWithExpectedType.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo5, "dataFlowInfo");
        return TypeInfoFactoryKt.createTypeInfo(kotlinTypeCheckType, dataFlowInfo3, jumpOutPossible, dataFlowInfo5);
    }

    private final Subject processVariableSubject(KtProperty subjectVariable, ExpressionTypingContext contextBeforeSubject) {
        String str;
        BindingTrace trace = contextBeforeSubject.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        if (!this.components.languageVersionSettings.supportsFeature(LanguageFeature.VariableDeclarationInWhenSubject)) {
            trace.report(Errors.UNSUPPORTED_FEATURE.on(subjectVariable, new Pair<>(LanguageFeature.VariableDeclarationInWhenSubject, this.components.languageVersionSettings)));
        } else {
            if (subjectVariable.isVar()) {
                str = PsiKeyword.VAR;
            } else if (subjectVariable.getInitializer() == null) {
                str = "variable without initializer";
            } else if (subjectVariable.hasDelegateExpression()) {
                str = "delegated property";
            } else {
                str = (subjectVariable.getGetter() == null && subjectVariable.getSetter() == null) ? null : "property with accessors";
            }
            if (str != null) {
                trace.report(Errors.ILLEGAL_DECLARATION_IN_WHEN_SUBJECT.on(subjectVariable, str));
            }
        }
        LexicalWritableScope lexicalWritableScopeNewWritableScopeImpl = ExpressionTypingUtils.newWritableScopeImpl(contextBeforeSubject, LexicalScopeKind.WHEN, this.components.overloadChecker);
        Intrinsics.checkNotNullExpressionValue(lexicalWritableScopeNewWritableScopeImpl, "newWritableScopeImpl(...)");
        LocalVariableResolver localVariableResolver = this.components.localVariableResolver;
        LexicalScope scope = contextBeforeSubject.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        ExpressionTypingInternals facade = this.facade;
        Intrinsics.checkNotNullExpressionValue(facade, "facade");
        Pair<KotlinTypeInfo, VariableDescriptor> pairProcess = localVariableResolver.process(subjectVariable, contextBeforeSubject, scope, facade);
        KotlinTypeInfo kotlinTypeInfoComponent1 = pairProcess.component1();
        VariableDescriptor variableDescriptorComponent2 = pairProcess.component2();
        lexicalWritableScopeNewWritableScopeImpl.addVariableDescriptor(variableDescriptorComponent2);
        return new Subject.Variable(subjectVariable, variableDescriptorComponent2, kotlinTypeInfoComponent1.replaceType(variableDescriptorComponent2.getType()), lexicalWritableScopeNewWritableScopeImpl);
    }

    private final KotlinType inferTypeForWhenExpression(KtWhenExpression expression, Subject subject, ExpressionTypingContext contextWithExpectedType, ExpressionTypingContext contextAfterSubject, List<? extends DataFlowInfo> dataFlowInfoForEntries) {
        List<KtWhenEntry> entries = expression.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        List<KtWhenEntry> list = entries;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (((KtWhenEntry) it.next()).getExpression() != null) {
                    final List<KtExpression> listWrapWhenEntryExpressionsAsSpecialCallArguments = wrapWhenEntryExpressionsAsSpecialCallArguments(expression);
                    KtWhenExpression ktWhenExpression = expression;
                    KtExpression calleeExpressionForSpecialCall = subject.getCalleeExpressionForSpecialCall();
                    if (calleeExpressionForSpecialCall == null) {
                        calleeExpressionForSpecialCall = ktWhenExpression;
                    }
                    Call callCreateCallForSpecialConstruction = ControlStructureTypingUtils.createCallForSpecialConstruction(ktWhenExpression, calleeExpressionForSpecialCall, listWrapWhenEntryExpressionsAsSpecialCallArguments);
                    return ((FunctionDescriptor) this.components.controlStructureTypingUtils.resolveSpecialConstructionAsCall(callCreateCallForSpecialConstruction, ControlStructureTypingUtils.ResolveConstruct.WHEN, new AbstractList<String>() { // from class: org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor$inferTypeForWhenExpression$resolvedCall$1
                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                        public final /* bridge */ boolean contains(Object obj) {
                            if (obj == null ? true : obj instanceof String) {
                                return contains((String) obj);
                            }
                            return false;
                        }

                        public /* bridge */ boolean contains(String str) {
                            return super.contains((Object) str);
                        }

                        @Override // java.util.AbstractList, java.util.List
                        public final /* bridge */ int indexOf(Object obj) {
                            if (obj == null ? true : obj instanceof String) {
                                return indexOf((String) obj);
                            }
                            return -1;
                        }

                        public /* bridge */ int indexOf(String str) {
                            return super.indexOf((Object) str);
                        }

                        @Override // java.util.AbstractList, java.util.List
                        public final /* bridge */ int lastIndexOf(Object obj) {
                            if (obj == null ? true : obj instanceof String) {
                                return lastIndexOf((String) obj);
                            }
                            return -1;
                        }

                        public /* bridge */ int lastIndexOf(String str) {
                            return super.lastIndexOf((Object) str);
                        }

                        @Override // java.util.AbstractList, java.util.List
                        public final /* bridge */ String remove(int i) {
                            return removeAt(i);
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                        public final /* bridge */ boolean remove(Object obj) {
                            if (obj == null ? true : obj instanceof String) {
                                return remove((String) obj);
                            }
                            return false;
                        }

                        public /* bridge */ boolean remove(String str) {
                            return super.remove((Object) str);
                        }

                        public /* bridge */ String removeAt(int i) {
                            return (String) super.remove(i);
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                        public final /* bridge */ int size() {
                            return getSize();
                        }

                        @Override // java.util.AbstractList, java.util.List
                        public String get(int index) {
                            return "entry" + index;
                        }

                        public int getSize() {
                            return listWrapWhenEntryExpressionsAsSpecialCallArguments.size();
                        }
                    }, Collections.nCopies(listWrapWhenEntryExpressionsAsSpecialCallArguments.size(), false), contextWithExpectedType, ControlStructureTypingUtils.createDataFlowInfoForArgumentsOfWhenCall(callCreateCallForSpecialConstruction, contextAfterSubject.dataFlowInfo, dataFlowInfoForEntries)).getResultingDescriptor()).getReturnType();
                }
            }
        }
        return this.components.builtIns.getUnitType();
    }

    private final List<KtExpression> wrapWhenEntryExpressionsAsSpecialCallArguments(KtWhenExpression expression) {
        KtBlockExpression ktBlockExpressionWrapInABlockWrapper;
        Project project = expression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtPsiFactory ktPsiFactory = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null);
        List<KtWhenEntry> entries = expression.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = entries.iterator();
        while (it.hasNext()) {
            KtExpression expression2 = ((KtWhenEntry) it.next()).getExpression();
            if (expression2 != null) {
                Intrinsics.checkNotNull(expression2);
                ktBlockExpressionWrapInABlockWrapper = ktPsiFactory.wrapInABlockWrapper(expression2);
            } else {
                ktBlockExpressionWrapInABlockWrapper = null;
            }
            if (ktBlockExpressionWrapInABlockWrapper != null) {
                arrayList.add(ktBlockExpressionWrapInABlockWrapper);
            }
        }
        return arrayList;
    }

    private final ArrayList<DataFlowInfo> analyzeConditionsInWhenEntries(KtWhenExpression expression, ExpressionTypingContext contextAfterSubject, Subject subject) {
        ArrayList<DataFlowInfo> arrayList = new ArrayList<>();
        DataFlowInfo dataFlowInfo = contextAfterSubject.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        for (KtWhenEntry ktWhenEntry : expression.getEntries()) {
            Intrinsics.checkNotNull(ktWhenEntry);
            ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = contextAfterSubject.replaceDataFlowInfo(dataFlowInfo);
            Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
            ConditionalDataFlowInfo conditionalDataFlowInfoAnalyzeWhenEntryConditions = analyzeWhenEntryConditions(ktWhenEntry, expressionTypingContextReplaceDataFlowInfo, subject);
            dataFlowInfo = dataFlowInfo.and(conditionalDataFlowInfoAnalyzeWhenEntryConditions.getElseInfo());
            if (ktWhenEntry.getExpression() != null) {
                arrayList.add(conditionalDataFlowInfoAnalyzeWhenEntryConditions.getThenInfo());
            }
        }
        return arrayList;
    }

    private final KotlinTypeInfo joinWhenExpressionBranches(KtWhenExpression expression, ExpressionTypingContext contextAfterSubject, KotlinType resultType, boolean jumpOutPossibleInSubject, DataFlowValue whenResultValue) {
        KotlinTypeInfo recordedTypeInfo;
        DataFlowInfo dataFlowInfo;
        BindingContext context = contextAfterSubject.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        Iterator<KtWhenEntry> it = expression.getEntries().iterator();
        DataFlowInfo dataFlowInfo2 = null;
        boolean z = false;
        while (it.hasNext()) {
            KtExpression expression2 = it.next().getExpression();
            if (expression2 != null && (recordedTypeInfo = BindingContextUtils.getRecordedTypeInfo(expression2, context)) != null) {
                KotlinType type = recordedTypeInfo.getType();
                if (type == null) {
                    z = true;
                }
                if (whenResultValue != null && type != null) {
                    DataFlowValue dataFlowValueCreateDataFlowValue = this.facade.getComponents().dataFlowValueFactory.createDataFlowValue(expression2, type, contextAfterSubject);
                    DataFlowInfo dataFlowInfo3 = recordedTypeInfo.getDataFlowInfo();
                    LanguageVersionSettings languageVersionSettings = this.components.languageVersionSettings;
                    Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
                    dataFlowInfo = dataFlowInfo3.assign(whenResultValue, dataFlowValueCreateDataFlowValue, languageVersionSettings);
                } else {
                    dataFlowInfo = recordedTypeInfo.getDataFlowInfo();
                }
                if (type == null || !KotlinBuiltIns.isNothing(type)) {
                    dataFlowInfo2 = dataFlowInfo2 != null ? dataFlowInfo2.or(dataFlowInfo) : dataFlowInfo;
                }
                jumpOutPossibleInSubject |= recordedTypeInfo.getJumpOutPossible();
            }
        }
        if (dataFlowInfo2 == null) {
            dataFlowInfo2 = contextAfterSubject.dataFlowInfo;
            Intrinsics.checkNotNullExpressionValue(dataFlowInfo2, "dataFlowInfo");
        }
        if (resultType == null || (z && KotlinBuiltIns.isNothing(resultType))) {
            return TypeInfoFactoryKt.noTypeInfo(dataFlowInfo2);
        }
        return TypeInfoFactoryKt.createTypeInfo(resultType, dataFlowInfo2, jumpOutPossibleInSubject, dataFlowInfo2);
    }

    private final void checkSmartCastsInSubjectIfRequired(KtWhenExpression expression, ExpressionTypingContext contextBeforeSubject, KotlinType subjectType, Set<? extends KotlinType> possibleTypesForSubject) {
        KtExpression subjectExpression = expression.getSubjectExpression();
        if (subjectExpression == null) {
            return;
        }
        for (KotlinType kotlinType : CollectionsKt.reversed(possibleTypesForSubject)) {
            ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor != null && (classDescriptor.getClassKind() == ClassKind.ENUM_CLASS || classDescriptor.getModality() == Modality.SEALED)) {
                if (checkSmartCastToExpectedTypeInSubject(contextBeforeSubject, subjectExpression, subjectType, kotlinType)) {
                    return;
                }
            }
        }
        boolean zIsNullableType = TypeUtils.isNullableType(subjectType);
        BindingContext context = contextBeforeSubject.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        if (!zIsNullableType || WhenChecker.INSTANCE.containsNullCase(expression, context)) {
            return;
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(subjectType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNotNullable, "makeNotNullable(...)");
        checkSmartCastToExpectedTypeInSubject(contextBeforeSubject, subjectExpression, subjectType, kotlinTypeMakeNotNullable);
    }

    private final boolean checkSmartCastToExpectedTypeInSubject(ExpressionTypingContext contextBeforeSubject, KtExpression subjectExpression, KotlinType subjectType, KotlinType expectedType) {
        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(contextBeforeSubject.trace, "Temporary trace for when subject nullability");
        Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
        ExpressionTypingContext expressionTypingContextReplaceBindingTrace = contextBeforeSubject.replaceExpectedType(expectedType).replaceBindingTrace(temporaryBindingTraceCreate);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceBindingTrace, "replaceBindingTrace(...)");
        SmartCastResult smartCastResultCheckPossibleCast = this.facade.getComponents().dataFlowAnalyzer.checkPossibleCast(subjectType, KtPsiUtil.safeDeparenthesize(subjectExpression), expressionTypingContextReplaceBindingTrace);
        if (smartCastResultCheckPossibleCast == null || !smartCastResultCheckPossibleCast.getIsCorrect()) {
            return false;
        }
        temporaryBindingTraceCreate.commit();
        return true;
    }

    private final ConditionalDataFlowInfo analyzeWhenEntryConditions(KtWhenEntry whenEntry, ExpressionTypingContext context, Subject subject) {
        if (whenEntry.isElse()) {
            DataFlowInfo dataFlowInfo = context.dataFlowInfo;
            Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
            return new ConditionalDataFlowInfo(dataFlowInfo, null, 2, null);
        }
        KtWhenCondition[] conditions = whenEntry.getConditions();
        Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
        ExpressionTypingContext expressionTypingContext = context;
        ConditionalDataFlowInfo conditionalDataFlowInfo = null;
        for (KtWhenCondition ktWhenCondition : conditions) {
            Intrinsics.checkNotNull(ktWhenCondition);
            ConditionalDataFlowInfo conditionalDataFlowInfoCheckWhenCondition = checkWhenCondition(subject, ktWhenCondition, expressionTypingContext);
            conditionalDataFlowInfo = conditionalDataFlowInfo != null ? new ConditionalDataFlowInfo(conditionalDataFlowInfo.getThenInfo().or(conditionalDataFlowInfoCheckWhenCondition.getThenInfo()), conditionalDataFlowInfo.getElseInfo().and(conditionalDataFlowInfoCheckWhenCondition.getElseInfo())) : conditionalDataFlowInfoCheckWhenCondition;
            ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContext.replaceDataFlowInfo(conditionalDataFlowInfoCheckWhenCondition.getElseInfo());
            Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
            expressionTypingContext = expressionTypingContextReplaceDataFlowInfo;
        }
        if (conditionalDataFlowInfo != null) {
            return conditionalDataFlowInfo;
        }
        DataFlowInfo dataFlowInfo2 = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo2, "dataFlowInfo");
        return new ConditionalDataFlowInfo(dataFlowInfo2, null, 2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [T, org.jetbrains.kotlin.resolve.calls.smartcasts.ConditionalDataFlowInfo] */
    private final ConditionalDataFlowInfo checkWhenCondition(final Subject subject, KtWhenCondition condition, final ExpressionTypingContext context) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = noChange(context);
        condition.accept(new KtVisitorVoid() { // from class: org.jetbrains.kotlin.types.expressions.PatternMatchingTypingVisitor.checkWhenCondition.1
            /* JADX WARN: Type inference failed for: r1v6, types: [T, org.jetbrains.kotlin.resolve.calls.smartcasts.ConditionalDataFlowInfo] */
            /* JADX WARN: Type inference failed for: r3v2, types: [T, org.jetbrains.kotlin.resolve.calls.smartcasts.ConditionalDataFlowInfo] */
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitWhenConditionInRange(KtWhenConditionInRange condition2) {
                Intrinsics.checkNotNullParameter(condition2, "condition");
                KtExpression rangeExpression = condition2.getRangeExpression();
                if (rangeExpression == null) {
                    return;
                }
                Subject subject2 = subject;
                if (subject2 instanceof Subject.None) {
                    context.trace.report(Errors.EXPECTED_CONDITION.on(condition2));
                    DataFlowInfo dataFlowInfo = this.facade.getTypeInfo(rangeExpression, context).getDataFlowInfo();
                    objectRef.element = new ConditionalDataFlowInfo(dataFlowInfo, null, 2, null);
                    return;
                }
                ValueArgument valueArgumentMakeValueArgument = subject2.makeValueArgument();
                if (valueArgumentMakeValueArgument == null) {
                    return;
                }
                KotlinTypeInfo kotlinTypeInfoCheckInExpression = this.facade.checkInExpression(condition2, condition2.getOperationReference(), valueArgumentMakeValueArgument, rangeExpression, context);
                Intrinsics.checkNotNullExpressionValue(kotlinTypeInfoCheckInExpression, "checkInExpression(...)");
                DataFlowInfo dataFlowInfo2 = kotlinTypeInfoCheckInExpression.getDataFlowInfo();
                objectRef.element = new ConditionalDataFlowInfo(dataFlowInfo2, null, 2, null);
                KotlinType type = kotlinTypeInfoCheckInExpression.getType();
                if (type == null || !KotlinBuiltIns.isBoolean(type)) {
                    context.trace.report(Errors.TYPE_MISMATCH_IN_RANGE.on(condition2));
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitWhenConditionIsPattern(KtWhenConditionIsPattern condition2) {
                T conditionalDataFlowInfo;
                Intrinsics.checkNotNullParameter(condition2, "condition");
                if (subject instanceof Subject.None) {
                    context.trace.report(Errors.EXPECTED_CONDITION.on(condition2));
                }
                KtTypeReference typeReference = condition2.getTypeReference();
                if (typeReference == null) {
                    return;
                }
                ConditionalDataFlowInfo conditionalDataFlowInfoCheckTypeForIs = this.checkTypeForIs(context, condition2, condition2.isNegated(), subject.getType(), typeReference, subject.getDataFlowValue());
                Ref.ObjectRef<ConditionalDataFlowInfo> objectRef2 = objectRef;
                if (condition2.isNegated()) {
                    conditionalDataFlowInfo = conditionalDataFlowInfoCheckTypeForIs;
                    conditionalDataFlowInfo = new ConditionalDataFlowInfo(conditionalDataFlowInfoCheckTypeForIs.getElseInfo(), conditionalDataFlowInfoCheckTypeForIs.getThenInfo());
                }
                conditionalDataFlowInfo = conditionalDataFlowInfoCheckTypeForIs;
                objectRef2.element = conditionalDataFlowInfo;
                KotlinType kotlinType = (KotlinType) context.trace.get(BindingContext.TYPE, typeReference);
                if (subject instanceof Subject.None) {
                    return;
                }
                KtElement element = subject.getElement();
                Intrinsics.checkNotNull(element);
                RttiExpressionInformation rttiExpressionInformation = new RttiExpressionInformation(element, subject.getType(), kotlinType, condition2.isNegated() ? RttiOperation.NOT_IS : RttiOperation.IS);
                Iterable<RttiExpressionChecker> rttiExpressionCheckers = this.components.rttiExpressionCheckers;
                Intrinsics.checkNotNullExpressionValue(rttiExpressionCheckers, "rttiExpressionCheckers");
                ExpressionTypingContext expressionTypingContext = context;
                Iterator<RttiExpressionChecker> it = rttiExpressionCheckers.iterator();
                while (it.hasNext()) {
                    BindingTrace trace = expressionTypingContext.trace;
                    Intrinsics.checkNotNullExpressionValue(trace, "trace");
                    it.next().check(rttiExpressionInformation, condition2, trace);
                }
            }

            /* JADX WARN: Type inference failed for: r7v3, types: [T, org.jetbrains.kotlin.resolve.calls.smartcasts.ConditionalDataFlowInfo] */
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitWhenConditionWithExpression(KtWhenConditionWithExpression condition2) {
                Intrinsics.checkNotNullParameter(condition2, "condition");
                KtExpression expression = condition2.getExpression();
                if (expression == null) {
                    return;
                }
                ConditionalDataFlowInfo conditionalDataFlowInfoCheckTypeForExpressionCondition = this.checkTypeForExpressionCondition(context, expression, subject);
                ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(context.scope.getOwnerDescriptor());
                Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
                EffectSystem effectSystem = this.components.effectSystem;
                KtExpression valueExpression = subject.getValueExpression();
                BindingTrace trace = context.trace;
                Intrinsics.checkNotNullExpressionValue(trace, "trace");
                ConditionalDataFlowInfo dataFlowInfoWhenEquals = effectSystem.getDataFlowInfoWhenEquals(valueExpression, expression, trace, containingModule);
                objectRef.element = conditionalDataFlowInfoCheckTypeForExpressionCondition.and(dataFlowInfoWhenEquals);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitKtElement(KtElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                context.trace.report(Errors.UNSUPPORTED.on(element, getClass().getCanonicalName()));
            }
        });
        return (ConditionalDataFlowInfo) objectRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConditionalDataFlowInfo checkTypeForExpressionCondition(ExpressionTypingContext context, KtExpression expression, Subject subject) {
        KotlinTypeInfo typeInfo = this.facade.getTypeInfo(expression, context);
        Intrinsics.checkNotNullExpressionValue(typeInfo, "getTypeInfo(...)");
        KotlinType type = typeInfo.getType();
        if (type == null) {
            return noChange(context);
        }
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = context.replaceDataFlowInfo(typeInfo.getDataFlowInfo());
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
        ExpressionTypingContext expressionTypingContext = expressionTypingContextReplaceDataFlowInfo;
        if (subject instanceof Subject.None) {
            SimpleType booleanType = this.components.builtIns.getBooleanType();
            Intrinsics.checkNotNullExpressionValue(booleanType, "getBooleanType(...)");
            SimpleType simpleType = booleanType;
            KotlinTypeInfo kotlinTypeInfoCheckType = this.components.dataFlowAnalyzer.checkType(typeInfo, expression, expressionTypingContext.replaceExpectedType(simpleType));
            Intrinsics.checkNotNullExpressionValue(kotlinTypeInfoCheckType, "checkType(...)");
            KotlinTypeChecker kotlinTypeChecker = KotlinTypeChecker.DEFAULT;
            KotlinType type2 = kotlinTypeInfoCheckType.getType();
            if (type2 != null) {
                type = type2;
            }
            if (kotlinTypeChecker.equalTypes(simpleType, type)) {
                DataFlowInfo dataFlowInfoExtractDataFlowInfoFromCondition = this.components.dataFlowAnalyzer.extractDataFlowInfoFromCondition(expression, true, expressionTypingContext);
                Intrinsics.checkNotNullExpressionValue(dataFlowInfoExtractDataFlowInfoFromCondition, "extractDataFlowInfoFromCondition(...)");
                DataFlowInfo dataFlowInfoExtractDataFlowInfoFromCondition2 = this.components.dataFlowAnalyzer.extractDataFlowInfoFromCondition(expression, false, expressionTypingContext);
                Intrinsics.checkNotNullExpressionValue(dataFlowInfoExtractDataFlowInfoFromCondition2, "extractDataFlowInfoFromCondition(...)");
                return new ConditionalDataFlowInfo(dataFlowInfoExtractDataFlowInfoFromCondition, dataFlowInfoExtractDataFlowInfoFromCondition2);
            }
            return noChange(expressionTypingContext);
        }
        KtExpression ktExpression = expression;
        checkTypeCompatibility(expressionTypingContext, type, subject.getType(), ktExpression);
        DataFlowValue dataFlowValueCreateDataFlowValue = this.facade.getComponents().dataFlowValueFactory.createDataFlowValue(expression, type, expressionTypingContext);
        List listListOf = CollectionsKt.listOf(subject.getType());
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        DataFlowValue dataFlowValue = subject.getDataFlowValue();
        LanguageVersionSettings languageVersionSettings = this.components.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        List<? extends KotlinType> listPlus = CollectionsKt.plus((Collection) listListOf, (Iterable) dataFlowInfo.getStableTypes(dataFlowValue, languageVersionSettings));
        List listListOf2 = CollectionsKt.listOf(type);
        DataFlowInfo dataFlowInfo2 = expressionTypingContext.dataFlowInfo;
        LanguageVersionSettings languageVersionSettings2 = this.components.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings2, "languageVersionSettings");
        List<? extends KotlinType> listPlus2 = CollectionsKt.plus((Collection) listListOf2, (Iterable) dataFlowInfo2.getStableTypes(dataFlowValueCreateDataFlowValue, languageVersionSettings2));
        PrimitiveNumericComparisonCallChecker primitiveNumericComparisonCallChecker = PrimitiveNumericComparisonCallChecker.INSTANCE;
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        primitiveNumericComparisonCallChecker.inferPrimitiveNumericComparisonType(trace, listPlus, listPlus2, expression);
        ConditionalDataFlowInfo conditionalDataFlowInfoNoChange = noChange(expressionTypingContext);
        DataFlowInfo thenInfo = conditionalDataFlowInfoNoChange.getThenInfo();
        DataFlowValue dataFlowValue2 = subject.getDataFlowValue();
        boolean zTypeHasEqualsFromAny = this.facade.getComponents().dataFlowAnalyzer.typeHasEqualsFromAny(subject.getType(), ktExpression);
        LanguageVersionSettings languageVersionSettings3 = this.components.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings3, "languageVersionSettings");
        DataFlowInfo dataFlowInfoEquate = thenInfo.equate(dataFlowValue2, dataFlowValueCreateDataFlowValue, zTypeHasEqualsFromAny, languageVersionSettings3);
        DataFlowInfo elseInfo = conditionalDataFlowInfoNoChange.getElseInfo();
        DataFlowValue dataFlowValue3 = subject.getDataFlowValue();
        LanguageVersionSettings languageVersionSettings4 = this.components.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings4, "languageVersionSettings");
        return new ConditionalDataFlowInfo(dataFlowInfoEquate, elseInfo.disequate(dataFlowValue3, dataFlowValueCreateDataFlowValue, languageVersionSettings4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConditionalDataFlowInfo checkTypeForIs(ExpressionTypingContext context, KtElement isCheck, boolean negated, KotlinType subjectType, KtTypeReference typeReferenceAfterIs, DataFlowValue subjectDataFlowValue) {
        KotlinType kotlinTypeReconstructBareType = TypeReconstructionUtil.reconstructBareType(typeReferenceAfterIs, this.components.typeResolver.resolvePossiblyBareType(new TypeResolutionContext(context.scope, context.trace, true, true, context.isDebuggerContext), typeReferenceAfterIs), subjectType, context.trace, this.components.builtIns);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeReconstructBareType, "reconstructBareType(...)");
        if (DynamicTypesKt.isDynamic(kotlinTypeReconstructBareType)) {
            context.trace.report(Errors.DYNAMIC_NOT_ALLOWED.on(typeReferenceAfterIs));
        }
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(kotlinTypeReconstructBareType);
        if (classDescriptor != null && DescriptorUtils.isEnumEntry(classDescriptor)) {
            context.trace.report(Errors.IS_ENUM_ENTRY.on(typeReferenceAfterIs));
        }
        if (!TypeUtilsKt.containsError(subjectType) && !TypeUtils.isNullableType(subjectType) && kotlinTypeReconstructBareType.getIsMarkedNullable()) {
            KtTypeElement typeElement = typeReferenceAfterIs.getTypeElement();
            boolean z = typeElement instanceof KtNullableType;
            BindingTrace bindingTrace = context.trace;
            DiagnosticFactory0<KtNullableType> diagnosticFactory0 = Errors.USELESS_NULLABLE_CHECK;
            Intrinsics.checkNotNull(typeElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtNullableType");
            bindingTrace.report(diagnosticFactory0.on((KtNullableType) typeElement));
        }
        detectRedundantIs(context, subjectType, kotlinTypeReconstructBareType, isCheck, negated, subjectDataFlowValue, checkTypeCompatibility(context, kotlinTypeReconstructBareType, subjectType, typeReferenceAfterIs));
        if (context.languageVersionSettings.supportsFeature(LanguageFeature.ProperCheckAnnotationsTargetInTypeUsePositions)) {
            AnnotationChecker annotationChecker = this.components.annotationChecker;
            Intrinsics.checkNotNullExpressionValue(annotationChecker, "annotationChecker");
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            AnnotationChecker.check$default(annotationChecker, typeReferenceAfterIs, trace, null, 4, null);
        }
        KotlinTypeChecker DEFAULT = KotlinTypeChecker.DEFAULT;
        Intrinsics.checkNotNullExpressionValue(DEFAULT, "DEFAULT");
        if (CastDiagnosticsUtil.isCastErased(subjectType, kotlinTypeReconstructBareType, DEFAULT)) {
            context.trace.report(Errors.CANNOT_CHECK_FOR_ERASED.on(typeReferenceAfterIs, kotlinTypeReconstructBareType));
        }
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        LanguageVersionSettings languageVersionSettings = this.components.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        DataFlowInfo dataFlowInfoEstablishSubtyping = dataFlowInfo.establishSubtyping(subjectDataFlowValue, kotlinTypeReconstructBareType, languageVersionSettings);
        Intrinsics.checkNotNull(dataFlowInfo);
        return new ConditionalDataFlowInfo(dataFlowInfoEstablishSubtyping, dataFlowInfo);
    }

    private final void detectRedundantIs(ExpressionTypingContext context, KotlinType subjectType, KotlinType targetType, KtElement isCheck, boolean negated, DataFlowValue subjectDataFlowValue, boolean typesAreCompatible) {
        KotlinType kotlinType;
        PlatformToKotlinClassMapper platformToKotlinClassMapper;
        PlatformSpecificCastChecker platformSpecificCastChecker;
        if (TypeUtilsKt.containsError(subjectType) || TypeUtilsKt.containsError(targetType)) {
            return;
        }
        Collection<KotlinType> allPossibleTypes = DataFlowAnalyzer.getAllPossibleTypes(subjectType, context, subjectDataFlowValue, context.languageVersionSettings);
        Intrinsics.checkNotNullExpressionValue(allPossibleTypes, "getAllPossibleTypes(...)");
        if (typesAreCompatible && !KotlinTypeKt.isError(targetType)) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : allPossibleTypes) {
                KotlinType kotlinType2 = (KotlinType) obj;
                Intrinsics.checkNotNull(kotlinType2);
                if (!TypeUtilsKt.isAnyOrNullableAny(kotlinType2)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            Collection<KotlinType> collection = arrayList2 != null ? arrayList2 : allPossibleTypes;
            if ((collection instanceof Collection) && collection.isEmpty()) {
                context.trace.report(Errors.USELESS_IS_CHECK.on(isCheck, Boolean.valueOf(negated)));
                break;
            }
            Iterator<T> it = collection.iterator();
            do {
                if (it.hasNext()) {
                    kotlinType = (KotlinType) it.next();
                    Intrinsics.checkNotNull(kotlinType);
                    platformToKotlinClassMapper = this.components.platformToKotlinClassMapper;
                    Intrinsics.checkNotNullExpressionValue(platformToKotlinClassMapper, "platformToKotlinClassMapper");
                    platformSpecificCastChecker = this.components.platformSpecificCastChecker;
                    Intrinsics.checkNotNullExpressionValue(platformSpecificCastChecker, "platformSpecificCastChecker");
                } else {
                    context.trace.report(Errors.USELESS_IS_CHECK.on(isCheck, Boolean.valueOf(negated)));
                    break;
                }
            } while (!CastDiagnosticsUtil.isCastPossible(kotlinType, targetType, platformToKotlinClassMapper, platformSpecificCastChecker));
        }
        if (CastDiagnosticsUtil.INSTANCE.isRefinementUseless(allPossibleTypes, targetType, false)) {
            context.trace.report(Errors.USELESS_IS_CHECK.on(isCheck, Boolean.valueOf(!negated)));
        }
    }

    private final ConditionalDataFlowInfo noChange(ExpressionTypingContext context) {
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        return new ConditionalDataFlowInfo(dataFlowInfo, null, 2, null);
    }

    private final boolean checkTypeCompatibility(ExpressionTypingContext context, KotlinType type, KotlinType subjectType, KtElement reportErrorOn) {
        if (TypeIntersector.isIntersectionEmpty(type, subjectType)) {
            context.trace.report(Errors.INCOMPATIBLE_TYPES.on(reportErrorOn, type, subjectType));
            return false;
        }
        EnumCompatibilityCheckerKt.checkEnumsForCompatibility(context, reportErrorOn, subjectType, type);
        if (!KotlinBuiltIns.isNullableNothing(type) || TypeUtils.isNullableType(subjectType)) {
            return true;
        }
        context.trace.report(Errors.SENSELESS_NULL_IN_WHEN.on(reportErrorOn));
        return true;
    }
}
