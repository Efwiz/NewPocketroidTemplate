package org.jetbrains.kotlin.resolve.jvm.checkers;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.cfg.WhenChecker;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.UpperBoundChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.Nullability;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.AbstractNullabilityChecker;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.NotNullTypeParameter;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.ClassicTypeCheckerState;
import org.jetbrains.kotlin.types.checker.ClassicTypeCheckerStateKt;
import org.jetbrains.kotlin.types.checker.KotlinTypePreparator;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeCheckerImpl;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.SenselessComparisonChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: JavaNullabilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 02\u00020\u0001:\u00010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J,\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0016J,\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0017H\u0016J$\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0017H\u0002Jf\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u001f26\u0010 \u001a2\u0012\u0013\u0012\u00110\u0015¢\u0006\f\b\"\u0012\b\b#\u0012\u0004\b\b(\u001a\u0012\u0013\u0012\u00110\u0015¢\u0006\f\b\"\u0012\b\b#\u0012\u0004\b\b($\u0012\u0004\u0012\u00020\b0!H\u0002JI\u0010%\u001a\u0004\u0018\u0001H&\"\b\b\u0000\u0010&*\u00020'2\u0006\u0010(\u001a\u00020\u00152\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00172\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H&0\u001cH\u0002¢\u0006\u0002\u0010+J&\u0010,\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J,\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010\u0014\u001a\u00020\u00152\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00172\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u00061²\u0006\n\u0010)\u001a\u00020\u001dX\u008a\u0084\u0002²\u0006\n\u0010)\u001a\u00020\u001dX\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JavaNullabilityChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "upperBoundChecker", "Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "(Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;)V", "getUpperBoundChecker", "()Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "checkReceiver", "", "receiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "receiverArgument", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "safeAccess", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/context/CallResolutionContext;", "checkType", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expressionTypeWithSmartCast", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "checkTypeParameterBounds", "doCheckType", "expectedType", "expressionTypeDataFlowValue", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "reportWarning", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "actualType", "doIfNotNull", ExifInterface.GPS_DIRECTION_TRUE, "", ModuleXmlParser.TYPE, "dataFlowValue", "body", "(Lorg/jetbrains/kotlin/types/KotlinType;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "exactedExpressionTypeByDataFlowNullability", "findTypeParameterWithWrongBoundsNullability", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "dataFlowValueForWholeExpression", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaNullabilityChecker implements AdditionalTypeChecker {
    private final UpperBoundChecker upperBoundChecker;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final NewKotlinTypeCheckerImpl typeCheckerForEnhancedTypes = new NewKotlinTypeCheckerImpl(KotlinTypeRefiner.Default.INSTANCE, new KotlinTypePreparator() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker$Companion$typeCheckerForEnhancedTypes$1
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
    });
    private static final NewKotlinTypeCheckerImpl typeCheckerForBaseTypes = new NewKotlinTypeCheckerImpl(KotlinTypeRefiner.Default.INSTANCE, null, 2, null);

    public JavaNullabilityChecker(UpperBoundChecker upperBoundChecker) {
        Intrinsics.checkNotNullParameter(upperBoundChecker, "upperBoundChecker");
        this.upperBoundChecker = upperBoundChecker;
    }

    public final UpperBoundChecker getUpperBoundChecker() {
        return this.upperBoundChecker;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker
    public void checkType(final KtExpression expression, final KotlinType expressionType, KotlinType expressionTypeWithSmartCast, final ResolutionContext<?> c) {
        final KtExpression baseExpression;
        final KotlinType type;
        KtExpression subjectExpression;
        KotlinType type2;
        ClassDescriptor classDescriptorOfTypeIfEnum;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(expressionType, "expressionType");
        Intrinsics.checkNotNullParameter(expressionTypeWithSmartCast, "expressionTypeWithSmartCast");
        Intrinsics.checkNotNullParameter(c, "c");
        checkTypeParameterBounds(expression, expressionType, c);
        final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<DataFlowValue>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker$checkType$dataFlowValue$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DataFlowValue invoke() {
                return c.dataFlowValueFactory.createDataFlowValue(expression, expressionType, c);
            }
        });
        TypeParameterDescriptor typeParameterDescriptorFindTypeParameterWithWrongBoundsNullability = findTypeParameterWithWrongBoundsNullability(expressionType, c, new Function0<DataFlowValue>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkType.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DataFlowValue invoke() {
                return JavaNullabilityChecker.checkType$lambda$0(lazy);
            }
        });
        if (typeParameterDescriptorFindTypeParameterWithWrongBoundsNullability != null) {
            c.trace.report(ErrorsJvm.NULLABLE_TYPE_PARAMETER_AGAINST_NOT_NULL_TYPE_PARAMETER.on(expression, typeParameterDescriptorFindTypeParameterWithWrongBoundsNullability));
        }
        KotlinType expectedType = c.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        Function0<DataFlowValue> function0 = new Function0<DataFlowValue>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkType.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DataFlowValue invoke() {
                return JavaNullabilityChecker.checkType$lambda$0(lazy);
            }
        };
        DataFlowInfo dataFlowInfo = c.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        doCheckType(expressionType, expectedType, function0, dataFlowInfo, new Function2<KotlinType, KotlinType, Unit>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkType.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType, KotlinType kotlinType2) {
                invoke2(kotlinType, kotlinType2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KotlinType expectedType2, KotlinType actualType) {
                Intrinsics.checkNotNullParameter(expectedType2, "expectedType");
                Intrinsics.checkNotNullParameter(actualType, "actualType");
                c.trace.report(ErrorsJvm.NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS.on(expression, expectedType2, actualType));
            }
        });
        if (expression instanceof KtWhenExpression) {
            KtWhenExpression ktWhenExpression = (KtWhenExpression) expression;
            if (ktWhenExpression.getElseExpression() != null || (subjectExpression = ktWhenExpression.getSubjectExpression()) == null || (type2 = c.trace.getType(subjectExpression)) == null || !FlexibleTypesKt.isFlexible(type2) || !TypeUtils.isNullableType(FlexibleTypesKt.asFlexibleType(type2).getUpperBound()) || (classDescriptorOfTypeIfEnum = WhenChecker.getClassDescriptorOfTypeIfEnum(type2)) == null) {
                return;
            }
            BindingContext context = c.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            if (!WhenChecker.getEnumMissingCases(ktWhenExpression, context, classDescriptorOfTypeIfEnum).isEmpty() || WhenChecker.INSTANCE.containsNullCase(ktWhenExpression, context)) {
                return;
            }
            DataFlowValue dataFlowValueCreateDataFlowValue = c.dataFlowValueFactory.createDataFlowValue(subjectExpression, type2, c);
            KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) c.trace.get(BindingContext.EXPRESSION_TYPE_INFO, subjectExpression);
            DataFlowInfo dataFlowInfo2 = kotlinTypeInfo != null ? kotlinTypeInfo.getDataFlowInfo() : null;
            if (dataFlowInfo2 == null || dataFlowInfo2.getStableNullability(dataFlowValueCreateDataFlowValue).canBeNull()) {
                BindingTrace bindingTrace = c.trace;
                DiagnosticFactory0<KtExpression> diagnosticFactory0 = ErrorsJvm.WHEN_ENUM_CAN_BE_NULL_IN_JAVA;
                KtExpression subjectExpression2 = ktWhenExpression.getSubjectExpression();
                Intrinsics.checkNotNull(subjectExpression2);
                bindingTrace.report(diagnosticFactory0.on(subjectExpression2));
                return;
            }
            return;
        }
        if (expression instanceof KtPostfixExpression) {
            KtPostfixExpression ktPostfixExpression = (KtPostfixExpression) expression;
            if (!Intrinsics.areEqual(ktPostfixExpression.getOperationToken(), KtTokens.EXCLEXCL) || (baseExpression = ktPostfixExpression.getBaseExpression()) == null || (type = c.trace.getType(baseExpression)) == null) {
                return;
            }
            doIfNotNull(type, new Function0<DataFlowValue>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkType.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final DataFlowValue invoke() {
                    return c.dataFlowValueFactory.createDataFlowValue(baseExpression, type, c);
                }
            }, c, new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkType.6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    c.trace.report(Errors.UNNECESSARY_NOT_NULL_ASSERTION.on(((KtPostfixExpression) expression).getOperationReference(), type));
                }
            });
            return;
        }
        if (expression instanceof KtBinaryExpression) {
            KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) expression;
            IElementType operationToken = ktBinaryExpression.getOperationToken();
            if (!(Intrinsics.areEqual(operationToken, KtTokens.EQEQ) ? true : Intrinsics.areEqual(operationToken, KtTokens.EXCLEQ) ? true : Intrinsics.areEqual(operationToken, KtTokens.EQEQEQ) ? true : Intrinsics.areEqual(operationToken, KtTokens.EXCLEQEQEQ)) || ktBinaryExpression.getLeft() == null || ktBinaryExpression.getRight() == null) {
                return;
            }
            KtExpression left = ktBinaryExpression.getLeft();
            Intrinsics.checkNotNull(left);
            KtExpression right = ktBinaryExpression.getRight();
            Intrinsics.checkNotNull(right);
            SenselessComparisonChecker.checkSenselessComparisonWithNull(ktBinaryExpression, left, right, c, new Function1<KtExpression, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkType.7
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(KtExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return c.trace.getType(it);
                }
            }, new Function1<DataFlowValue, Nullability>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkType.8
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Nullability invoke(final DataFlowValue value) {
                    Intrinsics.checkNotNullParameter(value, "value");
                    Nullability nullability = (Nullability) JavaNullabilityChecker.this.doIfNotNull(value.getType(), new Function0<DataFlowValue>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkType.8.1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final DataFlowValue invoke() {
                            return value;
                        }
                    }, c, new Function0<Nullability>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkType.8.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Nullability invoke() {
                            return Nullability.NOT_NULL;
                        }
                    });
                    return nullability == null ? Nullability.UNKNOWN : nullability;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DataFlowValue checkType$lambda$0(Lazy<DataFlowValue> lazy) {
        return lazy.getValue();
    }

    private final void checkTypeParameterBounds(KtExpression expression, KotlinType expressionType, ResolutionContext<?> c) {
        Call call;
        Iterable<Map.Entry> iterableEntrySet;
        KtTypeReference typeReference;
        if (expressionType instanceof AbbreviatedType) {
            UpperBoundChecker upperBoundChecker = this.upperBoundChecker;
            SimpleType expandedType = ((AbbreviatedType) expressionType).getExpandedType();
            BindingTrace trace = c.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            upperBoundChecker.checkBoundsOfExpandedTypeAlias(expandedType, expression, trace);
        }
        if (this.upperBoundChecker instanceof WarningAwareUpperBoundChecker) {
            BasicCallResolutionContext basicCallResolutionContext = c instanceof BasicCallResolutionContext ? (BasicCallResolutionContext) c : null;
            if (basicCallResolutionContext == null || (call = basicCallResolutionContext.call) == null) {
                BindingContext context = c.trace.getContext();
                WritableSlice<KtElement, Call> writableSlice = BindingContext.CALL;
                KtCallExpression ktCallExpression = expression instanceof KtCallExpression ? (KtCallExpression) expression : null;
                call = (Call) context.get(writableSlice, ktCallExpression != null ? ktCallExpression.getCalleeExpression() : null);
                if (call == null) {
                    return;
                }
            }
            ResolvedCall resolvedCall = (ResolvedCall) c.trace.getContext().get(BindingContext.RESOLVED_CALL, call);
            if (resolvedCall == null) {
                return;
            }
            if (resolvedCall instanceof NewResolvedCallImpl) {
                iterableEntrySet = ((NewResolvedCallImpl) resolvedCall).getResolvedCallAtom().getTypeArgumentMappingByOriginal();
            } else {
                iterableEntrySet = resolvedCall.getTypeArguments().entrySet();
            }
            for (Map.Entry entry : iterableEntrySet) {
                TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) entry.getKey();
                KotlinType kotlinType = (KotlinType) entry.getValue();
                List<KtTypeProjection> typeArguments = call.getTypeArguments();
                Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
                KtTypeProjection ktTypeProjection = (KtTypeProjection) CollectionsKt.getOrNull(typeArguments, typeParameterDescriptor.getIndex());
                if (ktTypeProjection != null && (typeReference = ktTypeProjection.getTypeReference()) != null && kotlinType != null) {
                    WarningAwareUpperBoundChecker warningAwareUpperBoundChecker = (WarningAwareUpperBoundChecker) this.upperBoundChecker;
                    TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(kotlinType);
                    Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
                    BindingTrace trace2 = c.trace;
                    Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                    WarningAwareUpperBoundChecker.checkBounds$default(warningAwareUpperBoundChecker, typeReference, kotlinType, typeParameterDescriptor, typeSubstitutorCreate, trace2, null, true, null, 160, null);
                }
            }
        }
    }

    private final TypeParameterDescriptor findTypeParameterWithWrongBoundsNullability(KotlinType expressionType, final ResolutionContext<?> c, final Function0<DataFlowValue> dataFlowValueForWholeExpression) {
        if (c.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitUsingNullableTypeParameterAgainstNotNullAnnotated) || TypeUtils.noExpectedType(c.expectedType)) {
            return null;
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        ClassicTypeCheckerState classicTypeCheckerState = new ClassicTypeCheckerState() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker$findTypeParameterWithWrongBoundsNullability$typeState$1
            private boolean expectsTypeArgument;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(true, false, null, null, null, 30, null);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.types.TypeCheckerState
            public boolean customIsSubtypeOf(KotlinTypeMarker subType, KotlinTypeMarker superType) {
                Intrinsics.checkNotNullParameter(subType, "subType");
                Intrinsics.checkNotNullParameter(superType, "superType");
                KotlinType kotlinType = (KotlinType) subType;
                if (JavaNullabilityChecker.INSTANCE.isNullableTypeAgainstNotNullTypeParameter(kotlinType, (KotlinType) superType) && (this.expectsTypeArgument || c.dataFlowInfo.getStableNullability(dataFlowValueForWholeExpression.invoke()) != Nullability.NOT_NULL)) {
                    Ref.ObjectRef<TypeParameterDescriptor> objectRef2 = objectRef;
                    ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                    objectRef2.element = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : 0;
                    return false;
                }
                if (!this.expectsTypeArgument) {
                    this.expectsTypeArgument = true;
                }
                return true;
            }
        };
        AbstractTypeChecker abstractTypeChecker = AbstractTypeChecker.INSTANCE;
        KotlinType kotlinType = expressionType;
        KotlinType expectedType = c.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        AbstractTypeChecker.isSubtypeOf$default(abstractTypeChecker, (TypeCheckerState) classicTypeCheckerState, (KotlinTypeMarker) kotlinType, (KotlinTypeMarker) expectedType, false, 8, (Object) null);
        return (TypeParameterDescriptor) objectRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DataFlowValue checkReceiver$lambda$2(Lazy<DataFlowValue> lazy) {
        return lazy.getValue();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker
    public void checkReceiver(ReceiverParameterDescriptor receiverParameter, final ReceiverValue receiverArgument, boolean safeAccess, final CallResolutionContext<?> c) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        Intrinsics.checkNotNullParameter(receiverArgument, "receiverArgument");
        Intrinsics.checkNotNullParameter(c, "c");
        final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<DataFlowValue>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker$checkReceiver$dataFlowValue$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DataFlowValue invoke() {
                return c.dataFlowValueFactory.createDataFlowValue(receiverArgument, c);
            }
        });
        if (safeAccess) {
            ASTNode callOperationNode = c.call.getCallOperationNode();
            final PsiElement psi = callOperationNode != null ? callOperationNode.getPsi() : null;
            if (psi == null) {
                return;
            }
            KotlinType type = receiverArgument.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            doIfNotNull(type, new Function0<DataFlowValue>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkReceiver.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final DataFlowValue invoke() {
                    return JavaNullabilityChecker.checkReceiver$lambda$2(lazy);
                }
            }, c, new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkReceiver.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    c.trace.report(Errors.UNNECESSARY_SAFE_CALL.on(psi, receiverArgument.getType()));
                }
            });
            return;
        }
        KotlinType type2 = receiverArgument.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        KotlinType type3 = receiverParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
        Function0<DataFlowValue> function0 = new Function0<DataFlowValue>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkReceiver.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DataFlowValue invoke() {
                return JavaNullabilityChecker.checkReceiver$lambda$2(lazy);
            }
        };
        DataFlowInfo dataFlowInfo = c.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        doCheckType(type2, type3, function0, dataFlowInfo, new Function2<KotlinType, KotlinType, Unit>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker.checkReceiver.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType, KotlinType kotlinType2) {
                invoke2(kotlinType, kotlinType2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KotlinType expectedType, KotlinType actualType) {
                Intrinsics.checkNotNullParameter(expectedType, "expectedType");
                Intrinsics.checkNotNullParameter(actualType, "actualType");
                ReceiverValue receiverValue = receiverArgument;
                ExpressionReceiver expressionReceiver = receiverValue instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValue : null;
                KtExpression expression = expressionReceiver != null ? expressionReceiver.getExpression() : null;
                if (expression != null) {
                    c.trace.report(ErrorsJvm.RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS.on(expression, actualType));
                    return;
                }
                KtExpression calleeExpression = c.call.getCalleeExpression();
                KtExpression callElement = calleeExpression != null ? calleeExpression : c.call.getCallElement();
                Intrinsics.checkNotNull(callElement);
                c.trace.report(ErrorsJvm.NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS.on(callElement, expectedType, actualType));
            }
        });
    }

    private final void doCheckType(KotlinType expressionType, KotlinType expectedType, Function0<DataFlowValue> expressionTypeDataFlowValue, DataFlowInfo dataFlowInfo, Function2<? super KotlinType, ? super KotlinType, Unit> reportWarning) {
        KotlinType kotlinTypeExactedExpressionTypeByDataFlowNullability;
        boolean zIsSubtypeOf;
        if (TypeUtils.noExpectedType(expectedType) || (zIsSubtypeOf = typeCheckerForEnhancedTypes.isSubtypeOf((kotlinTypeExactedExpressionTypeByDataFlowNullability = exactedExpressionTypeByDataFlowNullability(expressionType, expressionTypeDataFlowValue, dataFlowInfo)), expectedType))) {
            return;
        }
        boolean zIsSubtypeOf2 = typeCheckerForBaseTypes.isSubtypeOf(kotlinTypeExactedExpressionTypeByDataFlowNullability, expectedType);
        if (zIsSubtypeOf || !zIsSubtypeOf2) {
            return;
        }
        reportWarning.invoke(TypeWithEnhancementKt.unwrapEnhancementDeeply(expectedType), TypeWithEnhancementKt.unwrapEnhancementDeeply(kotlinTypeExactedExpressionTypeByDataFlowNullability));
    }

    private final KotlinType exactedExpressionTypeByDataFlowNullability(KotlinType expressionType, Function0<DataFlowValue> expressionTypeDataFlowValue, DataFlowInfo dataFlowInfo) {
        return (KotlinTypeKt.isNullable(expressionType) && (dataFlowInfo.getStableNullability(expressionTypeDataFlowValue.invoke()) == Nullability.NOT_NULL)) ? TypeUtilsKt.makeNotNullable(expressionType) : expressionType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T> T doIfNotNull(KotlinType type, Function0<DataFlowValue> dataFlowValue, ResolutionContext<?> c, Function0<? extends T> body) {
        EnhancedNullabilityInfo enhancedNullabilityInfoMustNotBeNull = JavaNullabilityCheckerKt.mustNotBeNull(type);
        if (enhancedNullabilityInfoMustNotBeNull != null && enhancedNullabilityInfoMustNotBeNull.getIsFromJava() && c.dataFlowInfo.getStableNullability(dataFlowValue.invoke()).canBeNull()) {
            return body.invoke();
        }
        return null;
    }

    /* JADX INFO: compiled from: JavaNullabilityChecker.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JavaNullabilityChecker$Companion;", "", "()V", "typeCheckerForBaseTypes", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeCheckerImpl;", "getTypeCheckerForBaseTypes", "()Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeCheckerImpl;", "typeCheckerForEnhancedTypes", "getTypeCheckerForEnhancedTypes", "isNullableTypeAgainstNotNullTypeParameter", "", "subType", "Lorg/jetbrains/kotlin/types/KotlinType;", "superType", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final NewKotlinTypeCheckerImpl getTypeCheckerForEnhancedTypes() {
            return JavaNullabilityChecker.typeCheckerForEnhancedTypes;
        }

        public final NewKotlinTypeCheckerImpl getTypeCheckerForBaseTypes() {
            return JavaNullabilityChecker.typeCheckerForBaseTypes;
        }

        public final boolean isNullableTypeAgainstNotNullTypeParameter(KotlinType subType, KotlinType superType) {
            Intrinsics.checkNotNullParameter(subType, "subType");
            Intrinsics.checkNotNullParameter(superType, "superType");
            if (!(superType instanceof NotNullTypeParameter) || (subType instanceof NotNullTypeParameter)) {
                return false;
            }
            return !AbstractNullabilityChecker.INSTANCE.isSubtypeOfAny(ClassicTypeCheckerStateKt.createClassicTypeCheckerState$default(true, false, null, null, null, 30, null), subType);
        }
    }
}
