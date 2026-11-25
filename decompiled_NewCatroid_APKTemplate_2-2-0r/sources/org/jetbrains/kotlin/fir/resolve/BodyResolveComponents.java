package org.jetbrains.kotlin.fir.resolve;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirCallResolver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataElement;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner;
import org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer;
import org.jetbrains.kotlin.fir.resolve.inference.FirCallCompleter;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.transformers.FirSyntheticCallGenerator;
import org.jetbrains.kotlin.fir.resolve.transformers.IntegerLiteralAndOperatorApproximationTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;

/* JADX INFO: compiled from: BodyResolveComponents.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0012\u0010\u001b\u001a\u00020\u001cX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0012R\u0012\u0010\"\u001a\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u001c\u0010*\u001a\f\u0012\u0004\u0012\u00020,0+j\u0002`-X¦\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0012\u00100\u001a\u000201X¦\u0004¢\u0006\u0006\u001a\u0004\b2\u00103R\u0012\u00104\u001a\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u0012\u00108\u001a\u000209X¦\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0012\u0010<\u001a\u00020=X¦\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?R\u0012\u0010@\u001a\u00020AX¦\u0004¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0012\u0010D\u001a\u00020EX¦\u0004¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0012\u0010H\u001a\u00020IX¦\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u0012\u0010L\u001a\u00020MX¦\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u0018\u0010P\u001a\b\u0012\u0004\u0012\u00020Q0\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\bR\u0010\u0012¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "()V", "callCompleter", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "getCallCompleter", "()Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "callResolver", "Lorg/jetbrains/kotlin/fir/FirCallResolver;", "getCallResolver", "()Lorg/jetbrains/kotlin/fir/FirCallResolver;", "container", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getContainer", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "containingDeclarations", "", "getContainingDeclarations", "()Ljava/util/List;", "dataFlowAnalyzer", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "getDataFlowAnalyzer", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "doubleColonExpressionResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirDoubleColonExpressionResolver;", "getDoubleColonExpressionResolver", "()Lorg/jetbrains/kotlin/fir/resolve/FirDoubleColonExpressionResolver;", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "fileImportsScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "getFileImportsScope", "implicitReceiverStack", "Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "getImplicitReceiverStack", "()Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "integerLiteralAndOperatorApproximationTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/IntegerLiteralAndOperatorApproximationTransformer;", "getIntegerLiteralAndOperatorApproximationTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/IntegerLiteralAndOperatorApproximationTransformer;", "localScopes", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "Lorg/jetbrains/kotlin/fir/declarations/FirLocalScopes;", "getLocalScopes", "()Lkotlinx/collections/immutable/PersistentList;", "noExpectedType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getNoExpectedType", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "outerClassManager", "Lorg/jetbrains/kotlin/fir/resolve/FirOuterClassManager;", "getOuterClassManager", "()Lorg/jetbrains/kotlin/fir/resolve/FirOuterClassManager;", "resolutionStageRunner", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "getResolutionStageRunner", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "getReturnTypeCalculator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "samResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "getSamResolver", "()Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getSymbolProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "syntheticCallGenerator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSyntheticCallGenerator;", "getSyntheticCallGenerator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSyntheticCallGenerator;", "towerDataContext", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "getTowerDataContext", "()Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "towerDataElements", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataElement;", "getTowerDataElements", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BodyResolveComponents implements SessionHolder {
    public abstract FirCallCompleter getCallCompleter();

    public abstract FirCallResolver getCallResolver();

    public abstract FirDeclaration getContainer();

    public abstract List<FirDeclaration> getContainingDeclarations();

    public abstract FirDataFlowAnalyzer getDataFlowAnalyzer();

    public abstract FirDoubleColonExpressionResolver getDoubleColonExpressionResolver();

    public abstract FirFile getFile();

    public abstract List<FirScope> getFileImportsScope();

    public abstract ImplicitReceiverStack getImplicitReceiverStack();

    public abstract IntegerLiteralAndOperatorApproximationTransformer getIntegerLiteralAndOperatorApproximationTransformer();

    public abstract PersistentList<FirLocalScope> getLocalScopes();

    public abstract FirTypeRef getNoExpectedType();

    public abstract FirOuterClassManager getOuterClassManager();

    public abstract ResolutionStageRunner getResolutionStageRunner();

    public abstract ReturnTypeCalculator getReturnTypeCalculator();

    public abstract FirSamResolver getSamResolver();

    public abstract FirSymbolProvider getSymbolProvider();

    public abstract FirSyntheticCallGenerator getSyntheticCallGenerator();

    public abstract FirTowerDataContext getTowerDataContext();

    public abstract List<FirTowerDataElement> getTowerDataElements();
}
