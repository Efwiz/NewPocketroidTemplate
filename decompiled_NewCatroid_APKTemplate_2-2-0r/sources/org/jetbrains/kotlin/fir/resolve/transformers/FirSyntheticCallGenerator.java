package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationBuildingUtilsKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirFunctionCallBuilder;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedCallableReference;
import org.jetbrains.kotlin.fir.references.builder.FirErrorNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedErrorReferenceBuilder;
import org.jetbrains.kotlin.fir.references.impl.FirSimpleNamedReference;
import org.jetbrains.kotlin.fir.references.impl.FirStubReference;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.ErrorCandidateUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CallKind;
import org.jetbrains.kotlin.fir.resolve.calls.CallableReferenceResolutionKt;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.fir.resolve.calls.FirErrorReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticFunctionSymbol;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.SyntheticCallableId;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirTypeProjectionWithVarianceBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.ArrayFqNames;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.types.Variance;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSyntheticCallGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ê\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J(\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\u0016\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020&J\u0016\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(2\u0006\u0010%\u001a\u00020&J\u0016\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+2\u0006\u0010%\u001a\u00020&J\u0016\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.2\u0006\u0010%\u001a\u00020&J(\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u00104\u001a\u0002052\u0006\u0010%\u001a\u00020&H\u0002J:\u00100\u001a\u0002012\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u00106\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u00072\b\b\u0002\u0010 \u001a\u00020!2\u0006\u0010%\u001a\u00020&H\u0002J \u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u001a2\u0006\u00106\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020&H\u0002J \u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010=\u001a\u00020\u0018H\u0002J\u001e\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020A2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010%\u001a\u00020&J\b\u0010B\u001a\u00020\u000bH\u0002J\b\u0010C\u001a\u00020\u000bH\u0002J\u0016\u0010D\u001a\u00020?2\u0006\u0010@\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&J\u0010\u0010E\u001a\u00020\u000b2\u0006\u0010F\u001a\u00020GH\u0002J&\u0010H\u001a\u000e\u0012\u0004\u0012\u00020J\u0012\u0004\u0012\u0002050I2\u0006\u0010K\u001a\u00020L2\b\b\u0002\u0010M\u001a\u00020NH\u0002J\u0012\u0010O\u001a\u0004\u0018\u00010\b2\u0006\u0010P\u001a\u00020\u0007H\u0002J \u0010Q\u001a\u0002032\u0006\u00102\u001a\u0002032\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010%\u001a\u00020&J*\u0010R\u001a\u00020S*\u0002052\u0006\u0010T\u001a\u00020U2\n\u0010K\u001a\u0006\u0012\u0002\b\u00030V2\b\b\u0002\u0010W\u001a\u00020NH\u0002J\u001c\u0010X\u001a\u00020\u0013*\u0002032\u0006\u0010Y\u001a\u00020?2\u0006\u0010Z\u001a\u00020NH\u0002R$\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006["}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSyntheticCallGenerator;", "", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;)V", "arrayOfSymbolCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "", "checkNotNullFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "elvisFunction", "idFunction", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "trySelectFunction", "whenSelectFunction", "assertSyntheticResolvableReferenceIsNotResolved", "", "resolvable", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "calculateArrayOfSymbol", "expectedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "generateCallInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "callSite", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "name", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "callKind", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "generateCalleeForCheckNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "checkNotNullCall", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "generateCalleeForElvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "elvisExpression", "generateCalleeForTryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "tryExpression", "generateCalleeForWhenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "whenExpression", "generateCalleeReferenceWithCandidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "parameterTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "function", "generateCandidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "generateMemberFunction", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirSimpleFunctionBuilder;", NativeSymbol.TYPE_NAME, "returnType", "generateSyntheticArrayOfCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "generateSyntheticCheckNotNullFunction", "generateSyntheticElvisFunction", "generateSyntheticIdCall", "generateSyntheticSelectFunction", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "generateSyntheticSelectTypeParameter", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticFunctionSymbol;", "isNullableBound", "", "getArrayOfSymbol", "arrayOfName", "resolveCallableReferenceWithSyntheticOuterCall", "toValueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "nameAsString", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "isVararg", "updateErrorsIfNecessary", "fakeCall", "initialCallWasUnresolved", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSyntheticCallGenerator {
    private final FirCache arrayOfSymbolCache;
    private final FirSimpleFunction checkNotNullFunction;
    private final BodyResolveComponents components;
    private final FirSimpleFunction elvisFunction;
    private final FirSimpleFunction idFunction;
    private final FirSession session;
    private final FirSimpleFunction trySelectFunction;
    private final FirSimpleFunction whenSelectFunction;

    public FirSyntheticCallGenerator(BodyResolveComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
        FirSession session = components.getSession();
        this.session = session;
        this.whenSelectFunction = generateSyntheticSelectFunction(SyntheticCallableId.INSTANCE.getWHEN());
        this.trySelectFunction = generateSyntheticSelectFunction(SyntheticCallableId.INSTANCE.getTRY());
        this.idFunction = generateSyntheticSelectFunction(SyntheticCallableId.INSTANCE.getID());
        this.checkNotNullFunction = generateSyntheticCheckNotNullFunction();
        this.elvisFunction = generateSyntheticElvisFunction();
        this.arrayOfSymbolCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSyntheticCallGenerator$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((Name) obj, (Void) obj2);
            }

            public final FirNamedFunctionSymbol invoke(Name key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.getArrayOfSymbol(key);
            }
        });
    }

    private final void assertSyntheticResolvableReferenceIsNotResolved(FirResolvable resolvable) {
        boolean z = resolvable.getCalleeReference() instanceof FirStubReference;
    }

    public final FirWhenExpression generateCalleeForWhenExpression(FirWhenExpression whenExpression, ResolutionContext context) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(context, "context");
        assertSyntheticResolvableReferenceIsNotResolved(whenExpression);
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        List<FirExpression> arguments = firArgumentListBuilder.getArguments();
        List<FirWhenBranch> branches = whenExpression.getBranches();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(branches, 10));
        Iterator<T> it = branches.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirWhenBranch) it.next()).getResult());
        }
        CollectionsKt.addAll(arguments, arrayList);
        return whenExpression.transformCalleeReference((FirTransformer<? super FirNamedReferenceWithCandidate>) UpdateReference.INSTANCE, generateCalleeReferenceWithCandidate$default(this, whenExpression, this.whenSelectFunction, firArgumentListBuilder.build(), SyntheticCallableId.INSTANCE.getWHEN().getCallableName(), null, context, 16, null));
    }

    public final FirTryExpression generateCalleeForTryExpression(FirTryExpression tryExpression, ResolutionContext context) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        Intrinsics.checkNotNullParameter(context, "context");
        assertSyntheticResolvableReferenceIsNotResolved(tryExpression);
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        firArgumentListBuilder.getArguments().add(tryExpression.getTryBlock());
        Iterator<T> it = tryExpression.getCatches().iterator();
        while (it.hasNext()) {
            firArgumentListBuilder.getArguments().add(((FirCatch) it.next()).getBlock());
        }
        return tryExpression.transformCalleeReference((FirTransformer<? super FirNamedReferenceWithCandidate>) UpdateReference.INSTANCE, generateCalleeReferenceWithCandidate$default(this, tryExpression, this.trySelectFunction, firArgumentListBuilder.build(), SyntheticCallableId.INSTANCE.getTRY().getCallableName(), null, context, 16, null));
    }

    public final FirCheckNotNullCall generateCalleeForCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, ResolutionContext context) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        Intrinsics.checkNotNullParameter(context, "context");
        assertSyntheticResolvableReferenceIsNotResolved(checkNotNullCall);
        return checkNotNullCall.transformCalleeReference((FirTransformer<? super FirNamedReferenceWithCandidate>) UpdateReference.INSTANCE, generateCalleeReferenceWithCandidate$default(this, checkNotNullCall, this.checkNotNullFunction, checkNotNullCall.getArgumentList(), SyntheticCallableId.INSTANCE.getCHECK_NOT_NULL().getCallableName(), null, context, 16, null));
    }

    public final FirElvisExpression generateCalleeForElvisExpression(FirElvisExpression elvisExpression, ResolutionContext context) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        Intrinsics.checkNotNullParameter(context, "context");
        assertSyntheticResolvableReferenceIsNotResolved(elvisExpression);
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        firArgumentListBuilder.getArguments().add(elvisExpression.getLhs());
        firArgumentListBuilder.getArguments().add(elvisExpression.getRhs());
        return elvisExpression.transformCalleeReference((FirTransformer<? super FirNamedReferenceWithCandidate>) UpdateReference.INSTANCE, generateCalleeReferenceWithCandidate$default(this, elvisExpression, this.elvisFunction, firArgumentListBuilder.build(), SyntheticCallableId.INSTANCE.getELVIS_NOT_NULL().getCallableName(), null, context, 16, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirFunctionCall generateSyntheticArrayOfCall(FirArrayLiteral arrayLiteral, FirTypeRef expectedTypeRef, ResolutionContext context) {
        FirErrorNamedReference firErrorNamedReferenceBuild;
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidateGenerateCalleeReferenceWithCandidate;
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        Intrinsics.checkNotNullParameter(expectedTypeRef, "expectedTypeRef");
        Intrinsics.checkNotNullParameter(context, "context");
        FirArgumentList argumentList = arrayLiteral.getArgumentList();
        FirNamedFunctionSymbol firNamedFunctionSymbolCalculateArrayOfSymbol = calculateArrayOfSymbol(expectedTypeRef);
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        firFunctionCallBuilder.setArgumentList(argumentList);
        if (firNamedFunctionSymbolCalculateArrayOfSymbol != null && (firNamedReferenceWithCandidateGenerateCalleeReferenceWithCandidate = generateCalleeReferenceWithCandidate(arrayLiteral, (FirSimpleFunction) firNamedFunctionSymbolCalculateArrayOfSymbol.getFir(), argumentList, ArrayFqNames.INSTANCE.getARRAY_OF_FUNCTION(), CallKind.Function.INSTANCE, context)) != null) {
            firErrorNamedReferenceBuild = firNamedReferenceWithCandidateGenerateCalleeReferenceWithCandidate;
        } else {
            FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
            firErrorNamedReferenceBuilder.setDiagnostic(new ConeUnresolvedNameError(ArrayFqNames.INSTANCE.getARRAY_OF_FUNCTION()));
            firErrorNamedReferenceBuild = firErrorNamedReferenceBuilder.build();
        }
        firFunctionCallBuilder.setCalleeReference(firErrorNamedReferenceBuild);
        firFunctionCallBuilder.setSource(arrayLiteral.getSource());
        FirFunctionCall firFunctionCallBuild = firFunctionCallBuilder.mo6809build();
        if (firNamedFunctionSymbolCalculateArrayOfSymbol == null) {
            firFunctionCallBuild.replaceTypeRef(ResolveUtilsKt.typeFromCallee(this.components, firFunctionCallBuild));
        }
        return firFunctionCallBuild;
    }

    private final FirNamedFunctionSymbol calculateArrayOfSymbol(FirTypeRef expectedTypeRef) {
        Name array_of_function;
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(expectedTypeRef);
        if (ConeBuiltinTypeUtilsKt.isPrimitiveArray(coneType)) {
            ConeKotlinType coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(coneType);
            Intrinsics.checkNotNull(coneKotlinTypeArrayElementType);
            ClassId classId = ConeTypeUtilsKt.getClassId(coneKotlinTypeArrayElementType);
            PrimitiveType.Companion companion = PrimitiveType.INSTANCE;
            Intrinsics.checkNotNull(classId);
            String strAsString = classId.getShortClassName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            Name name2 = ArrayFqNames.INSTANCE.getPRIMITIVE_TYPE_TO_ARRAY().get(companion.getByShortName(strAsString));
            Intrinsics.checkNotNull(name2);
            array_of_function = name2;
        } else if (ConeBuiltinTypeUtilsKt.isUnsignedArray(coneType)) {
            ConeKotlinType coneKotlinTypeArrayElementType2 = ArrayUtilsKt.arrayElementType(coneType);
            Intrinsics.checkNotNull(coneKotlinTypeArrayElementType2);
            ClassId classId2 = ConeTypeUtilsKt.getClassId(coneKotlinTypeArrayElementType2);
            Map<FqName, Name> unsigned_type_to_array = ArrayFqNames.INSTANCE.getUNSIGNED_TYPE_TO_ARRAY();
            Intrinsics.checkNotNull(classId2);
            Name name3 = unsigned_type_to_array.get(classId2.asSingleFqName());
            Intrinsics.checkNotNull(name3);
            array_of_function = name3;
        } else {
            array_of_function = ArrayFqNames.INSTANCE.getARRAY_OF_FUNCTION();
        }
        return (FirNamedFunctionSymbol) this.arrayOfSymbolCache.getValue(array_of_function, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirNamedFunctionSymbol getArrayOfSymbol(Name arrayOfName) {
        return (FirNamedFunctionSymbol) CollectionsKt.firstOrNull((List) FirSymbolProviderKt.getSymbolProvider(this.session).getTopLevelFunctionSymbols(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, arrayOfName));
    }

    public final FirCallableReferenceAccess resolveCallableReferenceWithSyntheticOuterCall(FirCallableReferenceAccess callableReferenceAccess, FirTypeRef expectedTypeRef, ResolutionContext context) {
        boolean z;
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        Intrinsics.checkNotNullParameter(context, "context");
        FirArgumentList firArgumentListBuildUnaryArgumentList = FirArgumentUtilKt.buildUnaryArgumentList(callableReferenceAccess);
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidateGenerateCalleeReferenceWithCandidate = generateCalleeReferenceWithCandidate(callableReferenceAccess, firArgumentListBuildUnaryArgumentList, (!(expectedTypeRef instanceof FirResolvedTypeRef) || CallableReferenceResolutionKt.isUnitOrFlexibleUnit(FirTypeUtilsKt.getConeType(expectedTypeRef))) ? context.getSession().getBuiltinTypes().getAnyType() : (FirResolvedTypeRef) expectedTypeRef, context);
        if (!(firNamedReferenceWithCandidateGenerateCalleeReferenceWithCandidate instanceof FirErrorReferenceWithCandidate) || !(((FirErrorReferenceWithCandidate) firNamedReferenceWithCandidateGenerateCalleeReferenceWithCandidate).getDiagnostic() instanceof ConeInapplicableCandidateError)) {
            z = false;
        } else {
            if (!(callableReferenceAccess.getCalleeReference() instanceof FirSimpleNamedReference) || !(callableReferenceAccess.getTypeRef() instanceof FirImplicitTypeRef)) {
                throw new IllegalStateException("Expected FirCallableReferenceAccess to be unresolved.".toString());
            }
            firNamedReferenceWithCandidateGenerateCalleeReferenceWithCandidate = generateCalleeReferenceWithCandidate(callableReferenceAccess, firArgumentListBuildUnaryArgumentList, context.getSession().getBuiltinTypes().getAnyType(), context);
            z = true;
        }
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        firFunctionCallBuilder.setCalleeReference(firNamedReferenceWithCandidateGenerateCalleeReferenceWithCandidate);
        firFunctionCallBuilder.setArgumentList(firArgumentListBuildUnaryArgumentList);
        FirFunctionCall firFunctionCallBuild = firFunctionCallBuilder.mo6809build();
        this.components.getCallCompleter().completeCall(firFunctionCallBuild, ResolutionMode.ContextIndependent.INSTANCE);
        updateErrorsIfNecessary(callableReferenceAccess, firFunctionCallBuild, z);
        return callableReferenceAccess;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void updateErrorsIfNecessary(FirCallableReferenceAccess firCallableReferenceAccess, FirFunctionCall firFunctionCall, boolean z) {
        FirNamedReference errorReference;
        FirNamedReference calleeReference = firFunctionCall.getCalleeReference();
        FirNamedReference calleeReference2 = firCallableReferenceAccess.getCalleeReference();
        if (FirReferenceUtilsKt.isError(calleeReference)) {
            FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference2 instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference2 : null;
            if (firNamedReferenceWithCandidate != null && (errorReference = ResolveUtilsKt.toErrorReference(firNamedReferenceWithCandidate, ((FirDiagnosticHolder) calleeReference).getDiagnostic())) != null) {
                firCallableReferenceAccess.replaceCalleeReference(errorReference);
            }
            if (FirReferenceUtilsKt.isError(calleeReference2)) {
                return;
            }
            FirResolvedCallableReference firResolvedCallableReference = calleeReference2 instanceof FirResolvedCallableReference ? (FirResolvedCallableReference) calleeReference2 : null;
            if (firResolvedCallableReference == null) {
                throw new IllegalStateException("By this time the actual callable reference must have already been resolved".toString());
            }
            FirResolvedErrorReferenceBuilder firResolvedErrorReferenceBuilder = new FirResolvedErrorReferenceBuilder();
            firResolvedErrorReferenceBuilder.setName(firResolvedCallableReference.getName());
            firResolvedErrorReferenceBuilder.setSource(firResolvedCallableReference.getSource());
            firResolvedErrorReferenceBuilder.setResolvedSymbol(firResolvedCallableReference.getResolvedSymbol());
            firResolvedErrorReferenceBuilder.setDiagnostic(((FirDiagnosticHolder) calleeReference).getDiagnostic());
            firCallableReferenceAccess.replaceCalleeReference((FirNamedReference) firResolvedErrorReferenceBuilder.build());
            return;
        }
        if (z && (calleeReference2 instanceof FirErrorNamedReference)) {
            ConeDiagnostic diagnostic = ((FirErrorNamedReference) calleeReference2).getDiagnostic();
            ConeAmbiguityError coneAmbiguityError = diagnostic instanceof ConeAmbiguityError ? (ConeAmbiguityError) diagnostic : null;
            if (coneAmbiguityError != null) {
                FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
                firErrorNamedReferenceBuilder.setSource(calleeReference2.getSource());
                firErrorNamedReferenceBuilder.setDiagnostic(new ConeAmbiguityError(coneAmbiguityError.getName(), CandidateApplicability.INAPPLICABLE, coneAmbiguityError.getCandidates()));
                firCallableReferenceAccess.replaceCalleeReference((FirNamedReference) firErrorNamedReferenceBuilder.build());
            }
        }
    }

    private final FirNamedReferenceWithCandidate generateCalleeReferenceWithCandidate(FirCallableReferenceAccess callableReferenceAccess, FirArgumentList argumentList, FirResolvedTypeRef parameterTypeRef, ResolutionContext context) {
        CallableId accept_specific_type = SyntheticCallableId.INSTANCE.getACCEPT_SPECIFIC_TYPE();
        FirSyntheticFunctionSymbol firSyntheticFunctionSymbol = new FirSyntheticFunctionSymbol(accept_specific_type);
        FirSimpleFunctionBuilder firSimpleFunctionBuilderGenerateMemberFunction = generateMemberFunction(firSyntheticFunctionSymbol, accept_specific_type.getCallableName(), context.getSession().getBuiltinTypes().getUnitType());
        firSimpleFunctionBuilderGenerateMemberFunction.getValueParameters().add(toValueParameter(parameterTypeRef, "reference", firSyntheticFunctionSymbol, false));
        return generateCalleeReferenceWithCandidate(callableReferenceAccess, firSimpleFunctionBuilderGenerateMemberFunction.mo6809build(), argumentList, accept_specific_type.getCallableName(), CallKind.SyntheticIdForCallableReferencesResolution.INSTANCE, context);
    }

    static /* synthetic */ FirNamedReferenceWithCandidate generateCalleeReferenceWithCandidate$default(FirSyntheticCallGenerator firSyntheticCallGenerator, FirExpression firExpression, FirSimpleFunction firSimpleFunction, FirArgumentList firArgumentList, Name name2, CallKind callKind, ResolutionContext resolutionContext, int i, Object obj) {
        if ((i & 16) != 0) {
            callKind = CallKind.SyntheticSelect.INSTANCE;
        }
        return firSyntheticCallGenerator.generateCalleeReferenceWithCandidate(firExpression, firSimpleFunction, firArgumentList, name2, callKind, resolutionContext);
    }

    private final FirNamedReferenceWithCandidate generateCalleeReferenceWithCandidate(FirExpression callSite, FirSimpleFunction function, FirArgumentList argumentList, Name name2, CallKind callKind, ResolutionContext context) {
        Candidate candidateGenerateCandidate = generateCandidate(generateCallInfo(callSite, name2, argumentList, callKind), function, context);
        CandidateApplicability candidateApplicabilityProcessCandidate$default = ResolutionStageRunner.processCandidate$default(this.components.getResolutionStageRunner(), candidateGenerateCandidate, context, false, 4, null);
        KtSourceElement source = callSite.getSource();
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.SyntheticCall.INSTANCE, 0, 0, 6, null) : null;
        if (!CandidateApplicabilityKt.isSuccess(candidateApplicabilityProcessCandidate$default)) {
            return ErrorCandidateUtilsKt.createErrorReferenceWithExistingCandidate(candidateGenerateCandidate, new ConeInapplicableCandidateError(candidateApplicabilityProcessCandidate$default, candidateGenerateCandidate), ktSourceElementFakeElement$default, context, this.components.getResolutionStageRunner());
        }
        return new FirNamedReferenceWithCandidate(ktSourceElementFakeElement$default, name2, candidateGenerateCandidate);
    }

    private final Candidate generateCandidate(CallInfo callInfo, FirSimpleFunction function, ResolutionContext context) {
        return new CandidateFactory(context, callInfo).createCandidate(callInfo, function.getSymbol(), ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, null, (240 & 16) != 0 ? null : null, (240 & 32) != 0 ? CollectionsKt.emptyList() : null, (240 & 64) != 0 ? false : false, (240 & 128) != 0 ? false : false);
    }

    private final CallInfo generateCallInfo(FirExpression callSite, Name name2, FirArgumentList argumentList, CallKind callKind) {
        return new CallInfo(callSite, callKind, name2, null, argumentList, false, CollectionsKt.emptyList(), this.session, this.components.getFile(), this.components.getContainingDeclarations(), null, null, null, null, null, 31744, null);
    }

    static /* synthetic */ Pair generateSyntheticSelectTypeParameter$default(FirSyntheticCallGenerator firSyntheticCallGenerator, FirSyntheticFunctionSymbol firSyntheticFunctionSymbol, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return firSyntheticCallGenerator.generateSyntheticSelectTypeParameter(firSyntheticFunctionSymbol, z);
    }

    private final Pair<FirTypeParameter, FirResolvedTypeRef> generateSyntheticSelectTypeParameter(FirSyntheticFunctionSymbol functionSymbol, boolean isNullableBound) {
        FirTypeParameterSymbol firTypeParameterSymbol = new FirTypeParameterSymbol();
        FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
        firTypeParameterBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
        firTypeParameterBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
        firTypeParameterBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        Name nameIdentifier = Name.identifier("K");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        firTypeParameterBuilder.setName(nameIdentifier);
        firTypeParameterBuilder.setSymbol(firTypeParameterSymbol);
        firTypeParameterBuilder.setContainingDeclarationSymbol(functionSymbol);
        firTypeParameterBuilder.setVariance(Variance.INVARIANT);
        firTypeParameterBuilder.setReified(false);
        if (!isNullableBound) {
            firTypeParameterBuilder.getBounds().add(firTypeParameterBuilder.getModuleData().getSession().getBuiltinTypes().getAnyType());
        } else {
            FirDeclarationBuildingUtilsKt.addDefaultBoundIfNecessary(firTypeParameterBuilder);
        }
        FirTypeParameter firTypeParameterBuild = firTypeParameterBuilder.mo6809build();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(new ConeTypeParameterTypeImpl(firTypeParameterSymbol.getLookupTag(), false, null, 4, null));
        return TuplesKt.to(firTypeParameterBuild, firResolvedTypeRefBuilder.mo6809build());
    }

    private final FirSimpleFunction generateSyntheticSelectFunction(CallableId callableId) {
        FirSyntheticFunctionSymbol firSyntheticFunctionSymbol = new FirSyntheticFunctionSymbol(callableId);
        Pair pairGenerateSyntheticSelectTypeParameter$default = generateSyntheticSelectTypeParameter$default(this, firSyntheticFunctionSymbol, false, 2, null);
        FirTypeParameter firTypeParameter = (FirTypeParameter) pairGenerateSyntheticSelectTypeParameter$default.component1();
        FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) pairGenerateSyntheticSelectTypeParameter$default.component2();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(ArrayUtilsKt.createArrayType$default(firResolvedTypeRef.getType(), false, false, 3, null));
        FirResolvedTypeRef firResolvedTypeRefBuild = firResolvedTypeRefBuilder.mo6809build();
        FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
        firTypeProjectionWithVarianceBuilder.setTypeRef(firResolvedTypeRef);
        firTypeProjectionWithVarianceBuilder.setVariance(Variance.INVARIANT);
        FirSimpleFunctionBuilder firSimpleFunctionBuilderGenerateMemberFunction = generateMemberFunction(firSyntheticFunctionSymbol, callableId.getCallableName(), firTypeProjectionWithVarianceBuilder.build().getTypeRef());
        firSimpleFunctionBuilderGenerateMemberFunction.getTypeParameters().add(firTypeParameter);
        firSimpleFunctionBuilderGenerateMemberFunction.getValueParameters().add(toValueParameter(firResolvedTypeRefBuild, "branches", firSyntheticFunctionSymbol, true));
        return firSimpleFunctionBuilderGenerateMemberFunction.mo6809build();
    }

    private final FirSimpleFunction generateSyntheticCheckNotNullFunction() {
        FirSyntheticFunctionSymbol firSyntheticFunctionSymbol = new FirSyntheticFunctionSymbol(SyntheticCallableId.INSTANCE.getCHECK_NOT_NULL());
        Pair<FirTypeParameter, FirResolvedTypeRef> pairGenerateSyntheticSelectTypeParameter = generateSyntheticSelectTypeParameter(firSyntheticFunctionSymbol, false);
        FirTypeParameter firTypeParameterComponent1 = pairGenerateSyntheticSelectTypeParameter.component1();
        FirResolvedTypeRef firResolvedTypeRefComponent2 = pairGenerateSyntheticSelectTypeParameter.component2();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(TypeUtilsKt.withNullability$default(firResolvedTypeRefComponent2.getType(), ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(this.session), null, false, 12, null));
        FirResolvedTypeRef firResolvedTypeRefBuild = firResolvedTypeRefBuilder.mo6809build();
        FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
        firTypeProjectionWithVarianceBuilder.setTypeRef(firResolvedTypeRefComponent2);
        firTypeProjectionWithVarianceBuilder.setVariance(Variance.INVARIANT);
        FirSimpleFunctionBuilder firSimpleFunctionBuilderGenerateMemberFunction = generateMemberFunction(firSyntheticFunctionSymbol, SyntheticCallableId.INSTANCE.getCHECK_NOT_NULL().getCallableName(), firTypeProjectionWithVarianceBuilder.build().getTypeRef());
        firSimpleFunctionBuilderGenerateMemberFunction.getTypeParameters().add(firTypeParameterComponent1);
        firSimpleFunctionBuilderGenerateMemberFunction.getValueParameters().add(toValueParameter$default(this, firResolvedTypeRefBuild, ParameterDescription.NAME_PREFIX, firSyntheticFunctionSymbol, false, 4, null));
        return firSimpleFunctionBuilderGenerateMemberFunction.mo6809build();
    }

    private final FirSimpleFunction generateSyntheticElvisFunction() {
        FirSyntheticFunctionSymbol firSyntheticFunctionSymbol = new FirSyntheticFunctionSymbol(SyntheticCallableId.INSTANCE.getELVIS_NOT_NULL());
        Pair pairGenerateSyntheticSelectTypeParameter$default = generateSyntheticSelectTypeParameter$default(this, firSyntheticFunctionSymbol, false, 2, null);
        FirTypeParameter firTypeParameter = (FirTypeParameter) pairGenerateSyntheticSelectTypeParameter$default.component1();
        FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) pairGenerateSyntheticSelectTypeParameter$default.component2();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRef;
        ConeKotlinType type = firResolvedTypeRef2.getType();
        if (type != null) {
            firResolvedTypeRefBuilder.setType(TypeUtilsKt.withNullability$default(type, ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(this.session), null, false, 12, null));
            FirResolvedTypeRef firResolvedTypeRefBuild = firResolvedTypeRefBuilder.mo6809build();
            FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(firResolvedTypeRef2, TypeUtilsKt.withAttributes(firResolvedTypeRef.getType(), ConeAttributes.INSTANCE.create(CollectionsKt.listOf(CompilerConeAttributes.Exact.INSTANCE))), null, 2, null);
            FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
            firTypeProjectionWithVarianceBuilder.setTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
            firTypeProjectionWithVarianceBuilder.setVariance(Variance.INVARIANT);
            FirSimpleFunctionBuilder firSimpleFunctionBuilderGenerateMemberFunction = generateMemberFunction(firSyntheticFunctionSymbol, SyntheticCallableId.INSTANCE.getELVIS_NOT_NULL().getCallableName(), firTypeProjectionWithVarianceBuilder.build().getTypeRef());
            firSimpleFunctionBuilderGenerateMemberFunction.getTypeParameters().add(firTypeParameter);
            FirSyntheticFunctionSymbol firSyntheticFunctionSymbol2 = firSyntheticFunctionSymbol;
            firSimpleFunctionBuilderGenerateMemberFunction.getValueParameters().add(toValueParameter$default(this, firResolvedTypeRefBuild, "x", firSyntheticFunctionSymbol2, false, 4, null));
            firSimpleFunctionBuilderGenerateMemberFunction.getValueParameters().add(toValueParameter$default(this, firResolvedTypeRef, "y", firSyntheticFunctionSymbol2, false, 4, null));
            return firSimpleFunctionBuilderGenerateMemberFunction.mo6809build();
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
    }

    private final FirSimpleFunctionBuilder generateMemberFunction(FirNamedFunctionSymbol symbol, Name name2, FirTypeRef returnType) {
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
        firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.Synthetic.FakeFunction.INSTANCE);
        firSimpleFunctionBuilder.setSymbol(symbol);
        firSimpleFunctionBuilder.setName(name2);
        firSimpleFunctionBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL));
        firSimpleFunctionBuilder.setReturnTypeRef(returnType);
        firSimpleFunctionBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        return firSimpleFunctionBuilder;
    }

    static /* synthetic */ FirValueParameter toValueParameter$default(FirSyntheticCallGenerator firSyntheticCallGenerator, FirResolvedTypeRef firResolvedTypeRef, String str, FirFunctionSymbol firFunctionSymbol, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return firSyntheticCallGenerator.toValueParameter(firResolvedTypeRef, str, firFunctionSymbol, z);
    }

    private final FirValueParameter toValueParameter(FirResolvedTypeRef firResolvedTypeRef, String str, FirFunctionSymbol<?> firFunctionSymbol, boolean z) {
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
        firValueParameterBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
        firValueParameterBuilder.setContainingFunctionSymbol(firFunctionSymbol);
        firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
        firValueParameterBuilder.setName(nameIdentifier);
        firValueParameterBuilder.setReturnTypeRef(firResolvedTypeRef);
        firValueParameterBuilder.setCrossinline(false);
        firValueParameterBuilder.setNoinline(false);
        firValueParameterBuilder.setVararg(z);
        firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(nameIdentifier));
        firValueParameterBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        return firValueParameterBuilder.mo6809build();
    }

    public final FirFunctionCall generateSyntheticIdCall(FirExpression arrayLiteral, ResolutionContext context) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        Intrinsics.checkNotNullParameter(context, "context");
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        firArgumentListBuilder.getArguments().add(arrayLiteral);
        FirArgumentList firArgumentListBuild = firArgumentListBuilder.build();
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        firFunctionCallBuilder.setArgumentList(firArgumentListBuild);
        firFunctionCallBuilder.setCalleeReference(generateCalleeReferenceWithCandidate$default(this, arrayLiteral, this.idFunction, firArgumentListBuild, SyntheticCallableId.INSTANCE.getID().getCallableName(), null, context, 16, null));
        return firFunctionCallBuilder.mo6809build();
    }
}
