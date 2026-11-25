package org.jetbrains.kotlin.fir.resolve.transformers.contracts;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.contracts.ContractUtilsKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.builder.FirLegacyRawContractDescriptionBuilder;
import org.jetbrains.kotlin.fir.contracts.builder.FirResolvedContractDescriptionBuilder;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceiverUtilsKt;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnonymousFunctionExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBlockBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirFunctionCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirLambdaArgumentExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirContractCallBlock;
import org.jetbrains.kotlin.fir.references.builder.FirSimpleNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer;
import org.jetbrains.kotlin.fir.scopes.impl.FirMemberTypeParameterScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirAbstractContractResolveTransformerDispatcher.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001!B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u0016\u0010\t\u001a\u00060\nR\u00020\u0000X¤\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirAbstractContractResolveTransformerDispatcher;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "outerBodyResolveContext", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;)V", "contractDeclarationsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer;", "getContractDeclarationsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer;", "contractMode", "", "declarationsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "getDeclarationsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "expressionsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "getExpressionsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "regularDeclarationsTransformer", "transformAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "FirDeclarationsContractResolveTransformer", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractContractResolveTransformerDispatcher extends FirAbstractBodyResolveTransformerDispatcher {
    private boolean contractMode;
    private final FirExpressionsResolveTransformer expressionsTransformer;
    private final FirDeclarationsResolveTransformer regularDeclarationsTransformer;

    protected abstract FirDeclarationsContractResolveTransformer getContractDeclarationsTransformer();

    public /* synthetic */ FirAbstractContractResolveTransformerDispatcher(FirSession firSession, ScopeSession scopeSession, BodyResolveContext bodyResolveContext, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, (i & 4) != 0 ? null : bodyResolveContext);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAbstractContractResolveTransformerDispatcher(FirSession session, ScopeSession scopeSession, BodyResolveContext bodyResolveContext) {
        super(session, FirResolvePhase.CONTRACTS, false, scopeSession, ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getContract(), bodyResolveContext, null, 64, null);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirAbstractContractResolveTransformerDispatcher firAbstractContractResolveTransformerDispatcher = this;
        this.expressionsTransformer = new FirExpressionsResolveTransformer(firAbstractContractResolveTransformerDispatcher);
        this.regularDeclarationsTransformer = new FirDeclarationsResolveTransformer(firAbstractContractResolveTransformerDispatcher);
        this.contractMode = true;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher
    public final FirExpressionsResolveTransformer getExpressionsTransformer() {
        return this.expressionsTransformer;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher
    public final FirDeclarationsResolveTransformer getDeclarationsTransformer() {
        return this.contractMode ? getContractDeclarationsTransformer() : this.regularDeclarationsTransformer;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotation(FirAnnotation annotation, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(data, "data");
        return annotation;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotationCall(FirAnnotationCall annotationCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return annotationCall;
    }

    /* JADX INFO: compiled from: FirAbstractContractResolveTransformerDispatcher.kt */
    @Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0094\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001f\u0010\u0017\u001a\u0002H\u0018\"\b\b\u0000\u0010\u0018*\u00020\u00052\u0006\u0010\u0019\u001a\u0002H\u0018H\u0002¢\u0006\u0002\u0010\u001aJ\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#2\u0006\u0010\u000b\u001a\u00020\fH\u0016J/\u0010%\u001a\u0002H\u0018\"\b\b\u0000\u0010\u0018*\u00020\u00052\u0006\u0010\u0019\u001a\u0002H\u00182\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010)J\u001f\u0010*\u001a\u0002H\u0018\"\b\b\u0000\u0010\u0018*\u00020\u00052\u0006\u0010\u0019\u001a\u0002H\u0018H\u0002¢\u0006\u0002\u0010\u001aJ\u0018\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020,2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010.\u001a\u00020\u00112\u0006\u0010/\u001a\u0002002\u0006\u0010\u0019\u001a\u00020,H\u0002J'\u00101\u001a\u0002H\u0018\"\b\b\u0000\u0010\u0018*\u00020\u00052\u0006\u0010\u0019\u001a\u0002H\u00182\u0006\u0010&\u001a\u000202H\u0002¢\u0006\u0002\u00103J\u0018\u00104\u001a\u00020\u00112\u0006\u00105\u001a\u0002062\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u00107\u001a\u0002082\u0006\u00109\u001a\u0002082\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020;2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\"\u0010=\u001a\u0002H\u0018\"\u0004\b\u0000\u0010\u00182\f\u0010>\u001a\b\u0012\u0004\u0012\u0002H\u00180?H\u0082\b¢\u0006\u0002\u0010@J\u001e\u0010A\u001a\u0002062\u0006\u00105\u001a\u0002062\f\u0010B\u001a\b\u0012\u0004\u0012\u0002060?H\u0016R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirAbstractContractResolveTransformerDispatcher;)V", "hasContractToResolve", "", "Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "getHasContractToResolve", "(Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;)Z", "transformAnonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "anonymousFunction", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformAnonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "anonymousInitializer", "transformAnonymousObject", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "transformConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "transformContractDescriptionOwner", ExifInterface.GPS_DIRECTION_TRUE, "owner", "(Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;)Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "transformDeclarationContent", "", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "transformEnumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "enumEntry", "transformField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "field", "transformLegacyRawContractDescriptionOwner", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", "hasBodyContract", "(Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;Z)Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "transformOwnerOfErrorContract", "transformProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "property", "transformPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "transformRawContractDescriptionOwner", "Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;", "(Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;)Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "transformRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformScript", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "script", "transformSimpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "simpleFunction", "withContractModeDisabled", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withRegularClass", "action", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected class FirDeclarationsContractResolveTransformer extends FirDeclarationsResolveTransformer {
        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirAnonymousInitializer transformAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, ResolutionMode data) {
            Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
            Intrinsics.checkNotNullParameter(data, "data");
            return anonymousInitializer;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirEnumEntry transformEnumEntry(FirEnumEntry enumEntry, ResolutionMode data) {
            Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
            Intrinsics.checkNotNullParameter(data, "data");
            return enumEntry;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirField transformField(FirField field, ResolutionMode data) {
            Intrinsics.checkNotNullParameter(field, "field");
            Intrinsics.checkNotNullParameter(data, "data");
            return field;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirScript transformScript(FirScript script, ResolutionMode data) {
            Intrinsics.checkNotNullParameter(script, "script");
            Intrinsics.checkNotNullParameter(data, "data");
            return script;
        }

        public FirDeclarationsContractResolveTransformer() {
            super(FirAbstractContractResolveTransformerDispatcher.this);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirSimpleFunction transformSimpleFunction(final FirSimpleFunction simpleFunction, ResolutionMode data) {
            FirSimpleFunction firSimpleFunction;
            Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
            Intrinsics.checkNotNullParameter(data, "data");
            if (!getHasContractToResolve(simpleFunction)) {
                return simpleFunction;
            }
            BodyResolveContext context = getTransformer().getContext();
            FirSession session = getSession();
            if (!(context.getContainerIfAny() instanceof FirClass)) {
                context.storeFunction(simpleFunction, session);
            }
            FirSimpleFunction firSimpleFunction2 = simpleFunction;
            if (firSimpleFunction2.getTypeParameters().isEmpty()) {
                context.getContainers().add(simpleFunction);
                try {
                    firSimpleFunction = (FirSimpleFunction) getTransformer().getContext().forFunctionBody(simpleFunction, getTransformer().getComponents(), new Function0<FirSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$transformSimpleFunction$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final FirSimpleFunction invoke() {
                            return (FirSimpleFunction) this.this$0.transformContractDescriptionOwner(simpleFunction);
                        }
                    });
                } finally {
                    context.getContainers().removeLast();
                }
            } else {
                FirMemberTypeParameterScope firMemberTypeParameterScope = new FirMemberTypeParameterScope(firSimpleFunction2);
                FirTowerDataContext towerDataContext = context.getTowerDataContext();
                try {
                    context.addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope, false));
                    context.getContainers().add(simpleFunction);
                    try {
                        firSimpleFunction = (FirSimpleFunction) getTransformer().getContext().forFunctionBody(simpleFunction, getTransformer().getComponents(), new Function0<FirSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$transformSimpleFunction$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final FirSimpleFunction invoke() {
                                return (FirSimpleFunction) this.this$0.transformContractDescriptionOwner(simpleFunction);
                            }
                        });
                        context.getContainers().removeLast();
                        context.replaceTowerDataContext(towerDataContext);
                    } catch (Throwable th) {
                        context.getContainers().removeLast();
                        throw th;
                    }
                } catch (Throwable th2) {
                    context.replaceTowerDataContext(towerDataContext);
                    throw th2;
                }
            }
            return firSimpleFunction;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer
        public FirAnonymousFunction transformAnonymousFunction(final FirAnonymousFunction anonymousFunction, ResolutionMode data) {
            Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
            Intrinsics.checkNotNullParameter(data, "data");
            if (!getHasContractToResolve(anonymousFunction)) {
                return anonymousFunction;
            }
            FirDeclarationsContractResolveTransformer firDeclarationsContractResolveTransformer = this;
            return (FirAnonymousFunction) firDeclarationsContractResolveTransformer.getTransformer().getContext().forFunctionBody(anonymousFunction, firDeclarationsContractResolveTransformer.getTransformer().getComponents(), new Function0<FirAnonymousFunction>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$transformAnonymousFunction$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirAnonymousFunction invoke() {
                    return (FirAnonymousFunction) this.this$0.transformContractDescriptionOwner(anonymousFunction);
                }
            });
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirProperty transformProperty(FirProperty property, ResolutionMode data) {
            FirPropertyAccessor setter;
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(data, "data");
            FirPropertyAccessor getter = property.getGetter();
            if (((getter == null || !getHasContractToResolve(getter)) && ((setter = property.getSetter()) == null || !getHasContractToResolve(setter))) || property.isLocal() || property.getDelegate() != null) {
                return property;
            }
            if (property instanceof FirSyntheticProperty) {
                transformSimpleFunction(((FirSyntheticProperty) property).getGetter().getDelegate(), data);
                return property;
            }
            BodyResolveContext context = getTransformer().getContext();
            FirProperty firProperty = property;
            if (firProperty.getTypeParameters().isEmpty()) {
                context.getContainers().add(property);
                try {
                    FirPropertyAccessor getter2 = property.getGetter();
                    if (getter2 != null) {
                        transformPropertyAccessor(getter2, property);
                    }
                    FirPropertyAccessor setter2 = property.getSetter();
                    if (setter2 != null) {
                        transformPropertyAccessor(setter2, property);
                    }
                } finally {
                    context.getContainers().removeLast();
                }
            } else {
                FirMemberTypeParameterScope firMemberTypeParameterScope = new FirMemberTypeParameterScope(firProperty);
                FirTowerDataContext towerDataContext = context.getTowerDataContext();
                try {
                    context.addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope, false));
                    context.getContainers().add(property);
                    try {
                        FirPropertyAccessor getter3 = property.getGetter();
                        if (getter3 != null) {
                            transformPropertyAccessor(getter3, property);
                        }
                        FirPropertyAccessor setter3 = property.getSetter();
                        if (setter3 != null) {
                            transformPropertyAccessor(setter3, property);
                        }
                        context.getContainers().removeLast();
                        context.replaceTowerDataContext(towerDataContext);
                    } catch (Throwable th) {
                        context.getContainers().removeLast();
                        throw th;
                    }
                } catch (Throwable th2) {
                    context.replaceTowerDataContext(towerDataContext);
                    throw th2;
                }
            }
            return property;
        }

        private final FirStatement transformPropertyAccessor(final FirPropertyAccessor propertyAccessor, FirProperty owner) {
            if (!getHasContractToResolve(propertyAccessor)) {
                return propertyAccessor;
            }
            FirDeclarationsContractResolveTransformer firDeclarationsContractResolveTransformer = this;
            return (FirStatement) firDeclarationsContractResolveTransformer.getTransformer().getContext().withPropertyAccessor(owner, propertyAccessor, firDeclarationsContractResolveTransformer.getTransformer().getComponents(), true, new Function0<FirPropertyAccessor>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$transformPropertyAccessor$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirPropertyAccessor invoke() {
                    return (FirPropertyAccessor) this.this$0.transformContractDescriptionOwner(propertyAccessor);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final <T extends FirContractDescriptionOwner> T transformContractDescriptionOwner(T owner) {
            getComponents().getDataFlowAnalyzer().enterContractDescription();
            FirContractDescription contractDescription = owner.getContractDescription();
            if (contractDescription instanceof FirLegacyRawContractDescription) {
                return (T) transformLegacyRawContractDescriptionOwner(owner, (FirLegacyRawContractDescription) contractDescription, true);
            }
            if (contractDescription instanceof FirRawContractDescription) {
                return (T) transformRawContractDescriptionOwner(owner, (FirRawContractDescription) contractDescription);
            }
            throw new IllegalArgumentException(owner + " has a contract description of an unknown type");
        }

        private final <T extends FirContractDescriptionOwner> T transformLegacyRawContractDescriptionOwner(T owner, FirLegacyRawContractDescription contractDescription, boolean hasBodyContract) {
            List valueParameters = FirAbstractContractResolveTransformerDispatcherKt.getValueParameters(owner);
            Iterator it = valueParameters.iterator();
            while (it.hasNext()) {
                getTransformer().getContext().storeVariable((FirValueParameter) it.next(), getSession());
            }
            try {
                FirAbstractContractResolveTransformerDispatcher.this.contractMode = false;
                FirElement firElementTransformSingle = FirTransformerUtilKt.transformSingle(contractDescription.getContractCall(), getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                ((FirFunctionCall) firElementTransformSingle).replaceTypeRef(getSession().getBuiltinTypes().getUnitType());
                final FirFunctionCall firFunctionCall = (FirFunctionCall) firElementTransformSingle;
                FirAbstractContractResolveTransformerDispatcher.this.contractMode = true;
                FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(firFunctionCall);
                if (!Intrinsics.areEqual(resolvedCallableSymbol != null ? resolvedCallableSymbol.getCallableId() : null, FirContractsDslNames.INSTANCE.getCONTRACT())) {
                    if (hasBodyContract) {
                        FirExpressionUtilKt.replaceFirstStatement(FirAbstractContractResolveTransformerDispatcherKt.getBody(owner), new Function1<FirContractCallBlock, FirStatement>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$transformLegacyRawContractDescriptionOwner$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final FirStatement invoke(FirContractCallBlock it2) {
                                Intrinsics.checkNotNullParameter(it2, "it");
                                return firFunctionCall;
                            }
                        });
                    }
                    owner.replaceContractDescription(FirEmptyContractDescription.INSTANCE);
                    getComponents().getDataFlowAnalyzer().exitContractDescription();
                    return owner;
                }
                if (hasBodyContract) {
                    FirExpressionUtilKt.replaceFirstStatement(FirAbstractContractResolveTransformerDispatcherKt.getBody(owner), new Function1<FirContractCallBlock, FirStatement>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$transformLegacyRawContractDescriptionOwner$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final FirStatement invoke(FirContractCallBlock it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            return new FirContractCallBlock(firFunctionCall);
                        }
                    });
                }
                Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) firFunctionCall.getArgumentList().getArguments());
                FirLambdaArgumentExpression firLambdaArgumentExpression = objSingleOrNull instanceof FirLambdaArgumentExpression ? (FirLambdaArgumentExpression) objSingleOrNull : null;
                if (firLambdaArgumentExpression == null) {
                    return (T) transformOwnerOfErrorContract(owner);
                }
                FirExpression expression = firLambdaArgumentExpression.getExpression();
                Intrinsics.checkNotNull(expression, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression");
                FirBlock body = ((FirAnonymousFunctionExpression) expression).getAnonymousFunction().getBody();
                if (body == null) {
                    return (T) transformOwnerOfErrorContract(owner);
                }
                FirResolvedContractDescriptionBuilder firResolvedContractDescriptionBuilder = new FirResolvedContractDescriptionBuilder();
                ConeEffectExtractor coneEffectExtractor = new ConeEffectExtractor(getSession(), owner, valueParameters);
                for (FirStatement firStatement : body.getStatements()) {
                    KtSourceElement source = firStatement.getSource();
                    if (!((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind.ImplicitReturn)) {
                        KtContractDescriptionElement ktContractDescriptionElement = (KtContractDescriptionElement) firStatement.accept(coneEffectExtractor, null);
                        if (ktContractDescriptionElement instanceof KtEffectDeclaration) {
                            boolean erroneous = ktContractDescriptionElement.getErroneous();
                            if (!erroneous) {
                                firResolvedContractDescriptionBuilder.getEffects().add(ContractUtilsKt.toFirElement((KtEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktContractDescriptionElement, firStatement.getSource()));
                            } else if (erroneous) {
                                firResolvedContractDescriptionBuilder.getUnresolvedEffects().add(ContractUtilsKt.toFirElement((KtEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktContractDescriptionElement, firStatement.getSource()));
                            }
                        } else {
                            firResolvedContractDescriptionBuilder.getUnresolvedEffects().add(ContractUtilsKt.toFirElement((KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic>) ktContractDescriptionElement, firStatement.getSource()));
                        }
                    }
                }
                firResolvedContractDescriptionBuilder.setSource(contractDescription.getSource());
                owner.replaceContractDescription(firResolvedContractDescriptionBuilder.build());
                getComponents().getDataFlowAnalyzer().exitContractDescription();
                return owner;
            } catch (Throwable th) {
                FirAbstractContractResolveTransformerDispatcher.this.contractMode = true;
                throw th;
            }
        }

        public void transformDeclarationContent(FirClass firClass, ResolutionMode data) {
            Intrinsics.checkNotNullParameter(firClass, "firClass");
            Intrinsics.checkNotNullParameter(data, "data");
            firClass.transformDeclarations(this, data);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformRegularClass(final FirRegularClass regularClass, final ResolutionMode data) {
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            Intrinsics.checkNotNullParameter(data, "data");
            return withRegularClass(regularClass, new Function0<FirRegularClass>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$transformRegularClass$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirRegularClass invoke() {
                    this.this$0.transformDeclarationContent((FirClass) regularClass, data);
                    return regularClass;
                }
            });
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer
        public FirRegularClass withRegularClass(FirRegularClass regularClass, final Function0<? extends FirRegularClass> action) {
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            Intrinsics.checkNotNullParameter(action, "action");
            FirDeclarationsContractResolveTransformer firDeclarationsContractResolveTransformer = this;
            return (FirRegularClass) firDeclarationsContractResolveTransformer.getTransformer().getContext().withRegularClass(regularClass, firDeclarationsContractResolveTransformer.getTransformer().getComponents(), new Function0<FirRegularClass>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$withRegularClass$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirRegularClass invoke() {
                    return action.invoke();
                }
            });
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformAnonymousObject(final FirAnonymousObject anonymousObject, final ResolutionMode data) {
            Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
            Intrinsics.checkNotNullParameter(data, "data");
            FirDeclarationsContractResolveTransformer firDeclarationsContractResolveTransformer = this;
            final BodyResolveContext context = firDeclarationsContractResolveTransformer.getTransformer().getContext();
            context.withScopesForClass(anonymousObject, firDeclarationsContractResolveTransformer.getTransformer().getComponents(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$transformAnonymousObject$$inlined$withAnonymousObject$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Unit invoke() {
                    BodyResolveContext bodyResolveContext = context;
                    bodyResolveContext.getContainers().add(anonymousObject);
                    try {
                        this.transformDeclarationContent((FirClass) anonymousObject, data);
                        return Unit.INSTANCE;
                    } finally {
                        bodyResolveContext.getContainers().removeLast();
                    }
                }
            });
            return anonymousObject;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirConstructor transformConstructor(final FirConstructor constructor, ResolutionMode data) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(data, "data");
            if (!getHasContractToResolve(constructor)) {
                return constructor;
            }
            BodyResolveContext context = getTransformer().getContext();
            context.getContainers().add(constructor);
            try {
                return (FirConstructor) getTransformer().getContext().forConstructorBody(constructor, getSession(), new Function0<FirConstructor>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer$transformConstructor$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final FirConstructor invoke() {
                        return (FirConstructor) this.this$0.transformContractDescriptionOwner(constructor);
                    }
                });
            } finally {
                context.getContainers().removeLast();
            }
        }

        private final <T extends FirContractDescriptionOwner> T transformOwnerOfErrorContract(T owner) {
            getComponents().getDataFlowAnalyzer().exitContractDescription();
            return owner;
        }

        private final boolean getHasContractToResolve(FirContractDescriptionOwner firContractDescriptionOwner) {
            return (firContractDescriptionOwner.getContractDescription() instanceof FirLegacyRawContractDescription) || (firContractDescriptionOwner.getContractDescription() instanceof FirRawContractDescription);
        }

        private final <T extends FirContractDescriptionOwner> T transformRawContractDescriptionOwner(T owner, FirRawContractDescription contractDescription) {
            FirAnonymousFunctionBuilder firAnonymousFunctionBuilder = new FirAnonymousFunctionBuilder();
            firAnonymousFunctionBuilder.setModuleData(FirModuleDataKt.getModuleData(getSession()));
            firAnonymousFunctionBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firAnonymousFunctionBuilder.setReturnTypeRef(FirImplicitTypeRefImplWithoutSource.INSTANCE);
            FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
            firReceiverParameterBuilder.setTypeRef(FirImplicitTypeRefImplWithoutSource.INSTANCE);
            firAnonymousFunctionBuilder.setReceiverParameter(firReceiverParameterBuilder.mo6809build());
            firAnonymousFunctionBuilder.setSymbol(new FirAnonymousFunctionSymbol());
            firAnonymousFunctionBuilder.setLambda(true);
            firAnonymousFunctionBuilder.setHasExplicitParameterList(true);
            FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
            Iterator<T> it = contractDescription.getRawEffects().iterator();
            while (it.hasNext()) {
                firBlockBuilder.getStatements().add((FirExpression) it.next());
            }
            firAnonymousFunctionBuilder.setBody(firBlockBuilder.mo6809build());
            FirAnonymousFunction firAnonymousFunctionMo6809build = firAnonymousFunctionBuilder.mo6809build();
            FirLambdaArgumentExpressionBuilder firLambdaArgumentExpressionBuilder = new FirLambdaArgumentExpressionBuilder();
            FirAnonymousFunctionExpressionBuilder firAnonymousFunctionExpressionBuilder = new FirAnonymousFunctionExpressionBuilder();
            firAnonymousFunctionExpressionBuilder.setAnonymousFunction(firAnonymousFunctionMo6809build);
            firLambdaArgumentExpressionBuilder.setExpression(firAnonymousFunctionExpressionBuilder.mo6809build());
            FirLambdaArgumentExpression firLambdaArgumentExpressionMo6809build = firLambdaArgumentExpressionBuilder.mo6809build();
            FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
            Name nameIdentifier = Name.identifier("contract");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            firSimpleNamedReferenceBuilder.setName(nameIdentifier);
            firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
            FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
            firArgumentListBuilder.getArguments().add(firLambdaArgumentExpressionMo6809build);
            firFunctionCallBuilder.setArgumentList(firArgumentListBuilder.build());
            FirFunctionCall firFunctionCallMo6809build = firFunctionCallBuilder.mo6809build();
            FirLegacyRawContractDescriptionBuilder firLegacyRawContractDescriptionBuilder = new FirLegacyRawContractDescriptionBuilder();
            firLegacyRawContractDescriptionBuilder.setContractCall(firFunctionCallMo6809build);
            firLegacyRawContractDescriptionBuilder.setSource(contractDescription.getSource());
            FirLegacyRawContractDescription firLegacyRawContractDescriptionBuild = firLegacyRawContractDescriptionBuilder.build();
            owner.replaceContractDescription(firLegacyRawContractDescriptionBuild);
            return (T) transformLegacyRawContractDescriptionOwner(owner, firLegacyRawContractDescriptionBuild, false);
        }
    }
}
