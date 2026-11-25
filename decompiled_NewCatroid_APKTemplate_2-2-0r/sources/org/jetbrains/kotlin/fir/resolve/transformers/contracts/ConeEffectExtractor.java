package org.jetbrains.kotlin.fir.resolve.transformers.contracts;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.KtBinaryLogicExpression;
import org.jetbrains.kotlin.contracts.description.KtBooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.KtBooleanExpression;
import org.jetbrains.kotlin.contracts.description.KtBooleanValueParameterReference;
import org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtErroneousCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtErroneousConstantReference;
import org.jetbrains.kotlin.contracts.description.KtErroneousContractElement;
import org.jetbrains.kotlin.contracts.description.KtErroneousIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtErroneousValueParameterReference;
import org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtIsNullPredicate;
import org.jetbrains.kotlin.contracts.description.KtLogicalNot;
import org.jetbrains.kotlin.contracts.description.KtReturnsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtValueParameterReference;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.contracts.description.ConeContractConstantValues;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeContractDescriptionError;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: ConeEffectExtractor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Þ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 M2 \u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0001:\u0001MB#\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001c\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J0\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0015j\u0002`\u00162\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J*\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J6\u0010 \u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u0005\"\u0004\b\u0000\u0010!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H!0#2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J*\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u0010%\u001a\u00020&2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J*\u0010'\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u0010(\u001a\u00020)2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J*\u0010*\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u0010+\u001a\u00020,2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J*\u0010-\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u0010.\u001a\u00020/2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J*\u00100\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u00101\u001a\u0002022\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J*\u00103\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u00104\u001a\u0002052\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J*\u00106\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u00107\u001a\u0002082\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J*\u00109\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u0010:\u001a\u00020;2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J*\u0010<\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u00052\u0006\u0010=\u001a\u00020>2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J\u001c\u0010?\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040@j\u0002`A*\u00020BH\u0002J\u001c\u0010C\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u0005*\u00020&H\u0002J\u001c\u0010D\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0015j\u0002`\u0016*\u00020BH\u0002J\u0018\u0010E\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0010*\u00020FH\u0002J\u0014\u0010G\u001a\u00020H*\u00020\n2\u0006\u0010I\u001a\u00020JH\u0002J\u000e\u0010K\u001a\u0004\u0018\u00010L*\u00020BH\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006N"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/ConeEffectExtractor;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractDescriptionElement;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "owner", "Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "valueParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;Ljava/util/List;)V", "noArgument", "Lorg/jetbrains/kotlin/contracts/description/KtErroneousContractElement;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "noReceiver", "toValueParameterReference", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeValueParameterReference;", ModuleXmlParser.TYPE, "index", "", "name", "", "visitBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "data", "visitConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "visitFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "visitQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "visitReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "visitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "visitThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "visitTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "asContractBooleanExpression", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeBooleanExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "asContractElement", "asContractValueExpression", "asElement", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", "isAccessorOf", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "parseInvocationKind", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeEffectExtractor extends FirDefaultVisitor {
    private final FirContractDescriptionOwner owner;
    private final FirSession session;
    private final List<FirValueParameter> valueParameters;
    private static final CallableId BOOLEAN_AND = FirContractsDslNames.INSTANCE.id$resolve(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "Boolean", "and");
    private static final CallableId BOOLEAN_OR = FirContractsDslNames.INSTANCE.id$resolve(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "Boolean", "or");
    private static final CallableId BOOLEAN_NOT = FirContractsDslNames.INSTANCE.id$resolve(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "Boolean", "not");

    /* JADX INFO: compiled from: ConeEffectExtractor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[EventOccurrencesRange.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[FirOperation.values().length];
            try {
                iArr[FirOperation.EQ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirOperation.NOT_EQ.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$1 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConeEffectExtractor(FirSession session, FirContractDescriptionOwner owner, List<? extends FirValueParameter> valueParameters) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        this.session = session;
        this.owner = owner;
        this.valueParameters = valueParameters;
    }

    private final KtErroneousContractElement<ConeKotlinType, ConeDiagnostic> asElement(ConeContractDescriptionError coneContractDescriptionError) {
        return new KtErroneousContractElement<>(coneContractDescriptionError);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitElement(FirElement element, Void data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return asElement(new ConeContractDescriptionError.IllegalElement(element));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitReturnExpression(FirReturnExpression returnExpression, Void data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        return (KtContractDescriptionElement) returnExpression.getResult().accept(this, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitFunctionCall(FirFunctionCall functionCall, Void data) {
        CallableId callableId;
        KtErroneousContractElement<ConeKotlinType, ConeDiagnostic> ktErroneousContractElementNoReceiver;
        KtErroneousContractElement<ConeKotlinType, ConeDiagnostic> ktErroneousContractElementNoReceiver2;
        KtErroneousContractElement<ConeKotlinType, ConeDiagnostic> ktErroneousContractElementNoArgument;
        LogicOperationKind logicOperationKind;
        KtErroneousCallsEffectDeclaration ktCallsEffectDeclaration;
        KtErroneousConstantReference ktErroneousConstantReference;
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(functionCall);
        if (resolvedCallableSymbol == null || (callableId = resolvedCallableSymbol.getCallableId()) == null) {
            return asElement(new ConeContractDescriptionError.UnresolvedCall(functionCall.getCalleeReference().getName()));
        }
        if (Intrinsics.areEqual(callableId, FirContractsDslNames.INSTANCE.getIMPLIES())) {
            FirExpression explicitReceiver = functionCall.getExplicitReceiver();
            KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> ktContractDescriptionElementAsContractElement = explicitReceiver != null ? asContractElement(explicitReceiver) : null;
            KtErroneousContractElement<ConeKotlinType, ConeDiagnostic> ktErroneousContractElementNoReceiver3 = ktContractDescriptionElementAsContractElement instanceof KtEffectDeclaration ? (KtEffectDeclaration) ktContractDescriptionElementAsContractElement : null;
            if (ktErroneousContractElementNoReceiver3 == null) {
                ktErroneousContractElementNoReceiver3 = noReceiver(callableId);
            }
            KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> ktContractDescriptionElementAsContractElement2 = asContractElement((FirExpression) CollectionsKt.first((List) functionCall.getArgumentList().getArguments()));
            KtErroneousContractElement<ConeKotlinType, ConeDiagnostic> ktErroneousContractElementNoArgument2 = ktContractDescriptionElementAsContractElement2 instanceof KtBooleanExpression ? (KtBooleanExpression) ktContractDescriptionElementAsContractElement2 : null;
            if (ktErroneousContractElementNoArgument2 == null) {
                ktErroneousContractElementNoArgument2 = noArgument(callableId);
            }
            return new KtConditionalEffectDeclaration(ktErroneousContractElementNoReceiver3, ktErroneousContractElementNoArgument2);
        }
        if (Intrinsics.areEqual(callableId, FirContractsDslNames.INSTANCE.getRETURNS())) {
            FirExpression firExpression = (FirExpression) CollectionsKt.firstOrNull((List) functionCall.getArgumentList().getArguments());
            if (firExpression == null) {
                ktErroneousConstantReference = ConeContractConstantValues.INSTANCE.getWILDCARD();
            } else {
                KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> ktContractDescriptionElementAsContractElement3 = asContractElement(firExpression);
                ktErroneousConstantReference = ktContractDescriptionElementAsContractElement3 instanceof KtConstantReference ? (KtConstantReference) ktContractDescriptionElementAsContractElement3 : new KtErroneousConstantReference(new ConeContractDescriptionError.NotAConstant(ktContractDescriptionElementAsContractElement3));
            }
            Intrinsics.checkNotNull(ktErroneousConstantReference, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.description.KtConstantReference<org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic>{ org.jetbrains.kotlin.fir.contracts.description.ConeValuesKt.ConeConstantReference }");
            return new KtReturnsEffectDeclaration(ktErroneousConstantReference);
        }
        if (Intrinsics.areEqual(callableId, FirContractsDslNames.INSTANCE.getRETURNS_NOT_NULL())) {
            return new KtReturnsEffectDeclaration(ConeContractConstantValues.INSTANCE.getNOT_NULL());
        }
        if (Intrinsics.areEqual(callableId, FirContractsDslNames.INSTANCE.getCALLS_IN_PLACE())) {
            FirFunctionCall firFunctionCall = functionCall;
            KtValueParameterReference<ConeKotlinType, ConeDiagnostic> ktValueParameterReferenceAsContractValueExpression = asContractValueExpression(firFunctionCall.getArgumentList().getArguments().get(0));
            FirExpression firExpression2 = (FirExpression) CollectionsKt.getOrNull(firFunctionCall.getArgumentList().getArguments(), 1);
            if (firExpression2 == null) {
                ktCallsEffectDeclaration = new KtCallsEffectDeclaration(ktValueParameterReferenceAsContractValueExpression, EventOccurrencesRange.UNKNOWN);
            } else {
                EventOccurrencesRange invocationKind = parseInvocationKind(firExpression2);
                if ((invocationKind == null ? -1 : WhenMappings.$EnumSwitchMapping$0[invocationKind.ordinal()]) == -1) {
                    ktCallsEffectDeclaration = new KtErroneousCallsEffectDeclaration(ktValueParameterReferenceAsContractValueExpression, new ConeContractDescriptionError.UnresolvedInvocationKind(firExpression2));
                } else {
                    ktCallsEffectDeclaration = new KtCallsEffectDeclaration(ktValueParameterReferenceAsContractValueExpression, invocationKind);
                }
            }
            return ktCallsEffectDeclaration;
        }
        CallableId callableId2 = BOOLEAN_AND;
        if (Intrinsics.areEqual(callableId, callableId2) ? true : Intrinsics.areEqual(callableId, BOOLEAN_OR)) {
            FirExpression explicitReceiver2 = functionCall.getExplicitReceiver();
            if (explicitReceiver2 == null || (ktErroneousContractElementNoReceiver2 = asContractBooleanExpression(explicitReceiver2)) == null) {
                ktErroneousContractElementNoReceiver2 = noReceiver(callableId);
            }
            FirExpression firExpression3 = (FirExpression) CollectionsKt.firstOrNull((List) functionCall.getArgumentList().getArguments());
            if (firExpression3 == null || (ktErroneousContractElementNoArgument = asContractBooleanExpression(firExpression3)) == null) {
                ktErroneousContractElementNoArgument = noArgument(callableId);
            }
            if (Intrinsics.areEqual(callableId, callableId2)) {
                logicOperationKind = LogicOperationKind.AND;
            } else {
                if (!Intrinsics.areEqual(callableId, BOOLEAN_OR)) {
                    AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
                    throw null;
                }
                logicOperationKind = LogicOperationKind.OR;
            }
            return new KtBinaryLogicExpression(ktErroneousContractElementNoReceiver2, ktErroneousContractElementNoArgument, logicOperationKind);
        }
        if (Intrinsics.areEqual(callableId, BOOLEAN_NOT)) {
            FirExpression explicitReceiver3 = functionCall.getExplicitReceiver();
            if (explicitReceiver3 == null || (ktErroneousContractElementNoReceiver = asContractBooleanExpression(explicitReceiver3)) == null) {
                ktErroneousContractElementNoReceiver = noReceiver(callableId);
            }
            return new KtLogicalNot(ktErroneousContractElementNoReceiver);
        }
        return asElement(new ConeContractDescriptionError.NotContractDsl(callableId));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, Void data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        return new KtBinaryLogicExpression(asContractBooleanExpression(binaryLogicExpression.getLeftOperand()), asContractBooleanExpression(binaryLogicExpression.getRightOperand()), binaryLogicExpression.getKind());
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, Void data) {
        boolean z;
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        FirOperation operation = equalityOperatorCall.getOperation();
        int i = WhenMappings.$EnumSwitchMapping$1[operation.ordinal()];
        if (i == 1) {
            z = false;
        } else {
            if (i != 2) {
                return asElement(new ConeContractDescriptionError.IllegalEqualityOperator(operation));
            }
            z = true;
        }
        FirEqualityOperatorCall firEqualityOperatorCall = equalityOperatorCall;
        FirExpression firExpression = firEqualityOperatorCall.getArgumentList().getArguments().get(1);
        FirConstExpression firConstExpression = firExpression instanceof FirConstExpression ? (FirConstExpression) firExpression : null;
        if (firConstExpression == null) {
            return asElement(new ConeContractDescriptionError.NotAConstant(firExpression));
        }
        return !Intrinsics.areEqual(firConstExpression.getKind(), ConstantValueKind.Null.INSTANCE) ? asElement(new ConeContractDescriptionError.IllegalConst(firConstExpression, true)) : new KtIsNullPredicate(asContractValueExpression(firEqualityOperatorCall.getArgumentList().getArguments().get(0)), z);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitSmartCastExpression(FirSmartCastExpression smartCastExpression, Void data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        return (KtContractDescriptionElement) smartCastExpression.getOriginalExpression().accept(this, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, Void data) {
        Name nameSpecial;
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(qualifiedAccessExpression);
        if (resolvedCallableSymbol == null) {
            FirReference calleeReference = qualifiedAccessExpression.getCalleeReference();
            FirNamedReference firNamedReference = calleeReference instanceof FirNamedReference ? (FirNamedReference) calleeReference : null;
            if (firNamedReference == null || (nameSpecial = firNamedReference.getName()) == null) {
                nameSpecial = Name.special("unresolved");
                Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            }
            return asElement(new ConeContractDescriptionError.UnresolvedCall(nameSpecial));
        }
        Object fir = resolvedCallableSymbol.getFir();
        FirValueParameter firValueParameter = fir instanceof FirValueParameter ? (FirValueParameter) fir : null;
        if (firValueParameter == null) {
            return new KtErroneousValueParameterReference(new ConeContractDescriptionError.IllegalParameter(resolvedCallableSymbol, resolvedCallableSymbol + " is not a value parameter"));
        }
        Integer numValueOf = Integer.valueOf(this.valueParameters.indexOf(firValueParameter));
        Integer num = numValueOf.intValue() >= 0 ? numValueOf : null;
        if (num == null) {
            return new KtErroneousValueParameterReference(new ConeContractDescriptionError.IllegalParameter(resolvedCallableSymbol, "Value paramter " + resolvedCallableSymbol + " is not found in parameters of outer function"));
        }
        int iIntValue = num.intValue();
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
        String strAsString = firValueParameter.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return toValueParameterReference(coneType, iIntValue, strAsString);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, Void data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        return visitQualifiedAccessExpression((FirQualifiedAccessExpression) propertyAccessExpression, data);
    }

    private final KtValueParameterReference<ConeKotlinType, ConeDiagnostic> toValueParameterReference(ConeKotlinType type, int index, String name2) {
        if (Intrinsics.areEqual(type, this.session.getBuiltinTypes().getBooleanType().getType())) {
            return new KtBooleanValueParameterReference(index, name2);
        }
        return new KtValueParameterReference<>(index, name2);
    }

    private final boolean isAccessorOf(FirContractDescriptionOwner firContractDescriptionOwner, FirDeclaration firDeclaration) {
        if (firDeclaration instanceof FirProperty) {
            FirProperty firProperty = (FirProperty) firDeclaration;
            if (Intrinsics.areEqual(firProperty.getGetter(), firContractDescriptionOwner) || Intrinsics.areEqual(firProperty.getSetter(), firContractDescriptionOwner)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, Void data) {
        FirDeclaration fir;
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        FirBasedSymbol<?> boundSymbol = thisReceiverExpression.getCalleeReference().getBoundSymbol();
        if (boundSymbol == null || (fir = boundSymbol.getFir()) == null) {
            return asElement(new ConeContractDescriptionError.UnresolvedThis(thisReceiverExpression));
        }
        Object obj = this.owner;
        FirCallableDeclaration firCallableDeclaration = obj instanceof FirCallableDeclaration ? (FirCallableDeclaration) obj : null;
        boolean z = (firCallableDeclaration != null ? firCallableDeclaration.getReceiverParameter() : null) != null;
        boolean zAreEqual = Intrinsics.areEqual(firCallableDeclaration != null ? ResolveUtilsKt.getContainingClass(firCallableDeclaration, this.session) : null, fir);
        if (Intrinsics.areEqual(fir, this.owner) || isAccessorOf(this.owner, fir) || (zAreEqual && !z)) {
            return toValueParameterReference(FirTypeUtilsKt.getConeType(thisReceiverExpression.getTypeRef()), -1, "this");
        }
        return asElement(new ConeContractDescriptionError.IllegalThis(thisReceiverExpression));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public <T> KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitConstExpression(FirConstExpression<T> constExpression, Void data) {
        KtBooleanConstantReference<ConeKotlinType, ConeDiagnostic> ktBooleanConstantReference;
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        ConstantValueKind<T> kind = constExpression.getKind();
        if (Intrinsics.areEqual(kind, ConstantValueKind.Null.INSTANCE)) {
            return ConeContractConstantValues.INSTANCE.getNULL();
        }
        if (!Intrinsics.areEqual(kind, ConstantValueKind.Boolean.INSTANCE)) {
            return asElement(new ConeContractDescriptionError.IllegalConst(constExpression, false));
        }
        T value = constExpression.getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
        boolean zBooleanValue = ((Boolean) value).booleanValue();
        if (zBooleanValue) {
            ktBooleanConstantReference = ConeContractConstantValues.INSTANCE.getTRUE();
        } else {
            if (zBooleanValue) {
                throw new NoWhenBranchMatchedException();
            }
            ktBooleanConstantReference = ConeContractConstantValues.INSTANCE.getFALSE();
        }
        return ktBooleanConstantReference;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> visitTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, Void data) {
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        KtValueParameterReference<ConeKotlinType, ConeDiagnostic> ktValueParameterReferenceAsContractValueExpression = asContractValueExpression((FirExpression) CollectionsKt.first((List) typeOperatorCall.getArgumentList().getArguments()));
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(typeOperatorCall.getConversionTypeRef()), this.session);
        boolean z = typeOperatorCall.getOperation() == FirOperation.NOT_IS;
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(coneKotlinTypeFullyExpandedType, this.session);
        Object notSelfTypeParameter = null;
        FirTypeParameterSymbol firTypeParameterSymbol = symbol instanceof FirTypeParameterSymbol ? (FirTypeParameterSymbol) symbol : null;
        if (firTypeParameterSymbol != null) {
            FirContractDescriptionOwner firContractDescriptionOwner = this.owner;
            FirTypeParameterRefsOwner firTypeParameterRefsOwner = firContractDescriptionOwner instanceof FirTypeParameterRefsOwner ? (FirTypeParameterRefsOwner) firContractDescriptionOwner : null;
            List<FirTypeParameterRef> typeParameters = firTypeParameterRefsOwner != null ? firTypeParameterRefsOwner.getTypeParameters() : null;
            if (typeParameters == null) {
                typeParameters = CollectionsKt.emptyList();
            }
            List<FirTypeParameterRef> list = typeParameters;
            if ((list instanceof Collection) && list.isEmpty()) {
                notSelfTypeParameter = new ConeContractDescriptionError.NotSelfTypeParameter(firTypeParameterSymbol);
            } else {
                for (FirTypeParameterRef firTypeParameterRef : list) {
                    if ((firTypeParameterRef instanceof FirTypeParameter) && Intrinsics.areEqual(firTypeParameterRef.getSymbol(), firTypeParameterSymbol)) {
                        notSelfTypeParameter = (ConeContractDescriptionError) (firTypeParameterSymbol.isReified() ? null : new ConeContractDescriptionError.NotReifiedTypeParameter(firTypeParameterSymbol));
                    }
                }
                notSelfTypeParameter = new ConeContractDescriptionError.NotSelfTypeParameter(firTypeParameterSymbol);
            }
        }
        if (notSelfTypeParameter == null) {
            return new KtIsInstancePredicate(ktValueParameterReferenceAsContractValueExpression, coneKotlinTypeFullyExpandedType, z);
        }
        return new KtErroneousIsInstancePredicate(ktValueParameterReferenceAsContractValueExpression, coneKotlinTypeFullyExpandedType, z, notSelfTypeParameter);
    }

    private final EventOccurrencesRange parseInvocationKind(FirExpression firExpression) {
        FirCallableSymbol<?> resolvedCallableSymbol;
        CallableId callableId;
        if (!(firExpression instanceof FirQualifiedAccessExpression) || (resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(firExpression)) == null || (callableId = resolvedCallableSymbol.getCallableId()) == null) {
            return null;
        }
        if (Intrinsics.areEqual(callableId, FirContractsDslNames.INSTANCE.getEXACTLY_ONCE_KIND())) {
            return EventOccurrencesRange.EXACTLY_ONCE;
        }
        if (Intrinsics.areEqual(callableId, FirContractsDslNames.INSTANCE.getAT_LEAST_ONCE_KIND())) {
            return EventOccurrencesRange.AT_LEAST_ONCE;
        }
        if (Intrinsics.areEqual(callableId, FirContractsDslNames.INSTANCE.getAT_MOST_ONCE_KIND())) {
            return EventOccurrencesRange.AT_MOST_ONCE;
        }
        if (Intrinsics.areEqual(callableId, FirContractsDslNames.INSTANCE.getUNKNOWN_KIND())) {
            return EventOccurrencesRange.UNKNOWN;
        }
        return null;
    }

    private final KtErroneousContractElement<ConeKotlinType, ConeDiagnostic> noReceiver(CallableId callableId) {
        return asElement(new ConeContractDescriptionError.NoReceiver(callableId.getCallableName()));
    }

    private final KtErroneousContractElement<ConeKotlinType, ConeDiagnostic> noArgument(CallableId callableId) {
        return asElement(new ConeContractDescriptionError.NoArgument(callableId.getCallableName()));
    }

    private final KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> asContractElement(FirElement firElement) {
        return (KtContractDescriptionElement) firElement.accept(this, null);
    }

    private final KtBooleanExpression<ConeKotlinType, ConeDiagnostic> asContractBooleanExpression(FirExpression firExpression) {
        KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> ktContractDescriptionElementAsContractElement = asContractElement(firExpression);
        return ktContractDescriptionElementAsContractElement instanceof KtBooleanExpression ? (KtBooleanExpression) ktContractDescriptionElementAsContractElement : asElement(new ConeContractDescriptionError.NotABooleanExpression(ktContractDescriptionElementAsContractElement));
    }

    private final KtValueParameterReference<ConeKotlinType, ConeDiagnostic> asContractValueExpression(FirExpression firExpression) {
        KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> ktContractDescriptionElementAsContractElement = asContractElement(firExpression);
        return ktContractDescriptionElementAsContractElement instanceof KtValueParameterReference ? (KtValueParameterReference) ktContractDescriptionElementAsContractElement : new KtErroneousValueParameterReference(new ConeContractDescriptionError.NotAParameterReference(ktContractDescriptionElementAsContractElement));
    }
}
