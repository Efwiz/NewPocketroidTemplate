package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirCallResolver;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataElement;
import org.jetbrains.kotlin.fir.expressions.FirLazyBlock;
import org.jetbrains.kotlin.fir.expressions.FirLazyExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.FirDoubleColonExpressionResolver;
import org.jetbrains.kotlin.fir.resolve.FirOuterClassManager;
import org.jetbrains.kotlin.fir.resolve.FirSamResolver;
import org.jetbrains.kotlin.fir.resolve.ImplicitReceiverStack;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ResolutionModeKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner;
import org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer;
import org.jetbrains.kotlin.fir.resolve.inference.FirCallCompleter;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponentsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.FirSpecificTypeResolverTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.FirSyntheticCallGenerator;
import org.jetbrains.kotlin.fir.resolve.transformers.IntegerLiteralAndOperatorApproximationTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.util.PrivateForInline;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FirAbstractBodyResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001iB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0002J\u0018\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020\u0002H\u0016J\u0018\u0010`\u001a\u00020\\2\u0006\u0010a\u001a\u00020b2\u0006\u0010_\u001a\u00020\u0002H\u0016J)\u0010c\u001a\u0002Hd\"\u0004\b\u0000\u0010d2\u000e\b\u0004\u0010e\u001a\b\u0012\u0004\u0012\u0002Hd0fH\u0080\bø\u0001\u0000¢\u0006\u0004\bg\u0010hR\u0015\u0010\u0006\u001a\u00020\u00078Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0015\u0010\n\u001a\u00020\u000b8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u00178Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\u001a\u001a\u00020\u001b8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0015\u0010\u001e\u001a\u00020\u001f8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\"\u0010$\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@aX¦\u000e¢\u0006\f\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0015\u0010)\u001a\u00020*8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u001b\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0015\u00102\u001a\u0002038Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0015\u0010:\u001a\u00020;8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0015\u0010>\u001a\u00020?8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0015\u0010B\u001a\u00020C8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0011\u0010F\u001a\u00020G8F¢\u0006\u0006\u001a\u0004\bH\u0010IR\u0015\u0010J\u001a\u00020K8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\bL\u0010MR\u0015\u0010N\u001a\u00020O8Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\bP\u0010QR\u0017\u0010R\u001a\u0004\u0018\u000103*\u00020\u00028F¢\u0006\u0006\u001a\u0004\bS\u0010T\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006j"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractPhaseTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "callCompleter", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "getCallCompleter", "()Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "callResolver", "Lorg/jetbrains/kotlin/fir/FirCallResolver;", "getCallResolver", "()Lorg/jetbrains/kotlin/fir/FirCallResolver;", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "getComponents", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "context", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "getContext", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "dataFlowAnalyzer", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "getDataFlowAnalyzer", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "implicitReceiverStack", "Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "getImplicitReceiverStack", "()Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "<set-?>", "", "implicitTypeOnly", "getImplicitTypeOnly", "()Z", "setImplicitTypeOnly$resolve", "(Z)V", "inferenceComponents", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "getInferenceComponents", "()Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "localScopes", "", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "getLocalScopes", "()Ljava/util/List;", "noExpectedType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getNoExpectedType", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "getResolutionContext", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "resolutionStageRunner", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "getResolutionStageRunner", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "samResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "getSamResolver", "()Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getSymbolProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "typeResolverTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSpecificTypeResolverTransformer;", "getTypeResolverTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSpecificTypeResolverTransformer;", "expectedType", "getExpectedType", "(Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "suppressOrThrowError", "", CrashHianalyticsData.MESSAGE, "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformLazyBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "lazyBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyBlock;", "data", "transformLazyExpression", "lazyExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;", "withFullBodyResolve", ExifInterface.GPS_DIRECTION_TRUE, "l", "Lkotlin/Function0;", "withFullBodyResolve$resolve", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "BodyResolveTransformerComponents", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractBodyResolveTransformer extends FirAbstractPhaseTransformer<ResolutionMode> {
    public abstract BodyResolveTransformerComponents getComponents();

    public abstract BodyResolveContext getContext();

    public abstract boolean getImplicitTypeOnly();

    public abstract ResolutionContext getResolutionContext();

    @PrivateForInline
    public abstract void setImplicitTypeOnly$resolve(boolean z);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAbstractBodyResolveTransformer(FirResolvePhase phase) {
        super(phase);
        Intrinsics.checkNotNullParameter(phase, "phase");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer
    public final FirSession getSession() {
        return getComponents().getSession();
    }

    public final <T> T withFullBodyResolve$resolve(Function0<? extends T> l) {
        Intrinsics.checkNotNullParameter(l, "l");
        boolean implicitTypeOnly = getImplicitTypeOnly();
        if (implicitTypeOnly) {
            setImplicitTypeOnly$resolve(false);
        }
        try {
            return l.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            if (implicitTypeOnly) {
                setImplicitTypeOnly$resolve(true);
            }
            InlineMarker.finallyEnd(1);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformLazyExpression(FirLazyExpression lazyExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(lazyExpression, "lazyExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        suppressOrThrowError("FirLazyExpression should be calculated before accessing", lazyExpression);
        return lazyExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformLazyBlock(FirLazyBlock lazyBlock, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(lazyBlock, "lazyBlock");
        Intrinsics.checkNotNullParameter(data, "data");
        suppressOrThrowError("FirLazyBlock should be calculated before accessing", lazyBlock);
        return lazyBlock;
    }

    private final void suppressOrThrowError(String message, FirElement element) {
        if (Boolean.parseBoolean(System.getProperty("kotlin.suppress.lazy.expression.access"))) {
            return;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message, null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "firElement", element);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    protected final List<FirLocalScope> getLocalScopes() {
        return getComponents().getLocalScopes();
    }

    protected final FirTypeRef getNoExpectedType() {
        return getComponents().getNoExpectedType();
    }

    protected final FirSymbolProvider getSymbolProvider() {
        return getComponents().getSymbolProvider();
    }

    protected final ImplicitReceiverStack getImplicitReceiverStack() {
        return getComponents().getImplicitReceiverStack();
    }

    protected final InferenceComponents getInferenceComponents() {
        return InferenceComponentsKt.getInferenceComponents(getSession());
    }

    protected final ResolutionStageRunner getResolutionStageRunner() {
        return getComponents().getResolutionStageRunner();
    }

    protected final FirSamResolver getSamResolver() {
        return getComponents().getSamResolver();
    }

    protected final FirSpecificTypeResolverTransformer getTypeResolverTransformer() {
        return getComponents().getTypeResolverTransformer();
    }

    protected final FirCallResolver getCallResolver() {
        return getComponents().getCallResolver();
    }

    protected final FirCallCompleter getCallCompleter() {
        return getComponents().getCallCompleter();
    }

    public final FirDataFlowAnalyzer getDataFlowAnalyzer() {
        return getComponents().getDataFlowAnalyzer();
    }

    protected final ScopeSession getScopeSession() {
        return getComponents().getScopeSession();
    }

    protected final FirFile getFile() {
        return getComponents().getFile();
    }

    public final FirTypeRef getExpectedType(ResolutionMode resolutionMode) {
        Intrinsics.checkNotNullParameter(resolutionMode, "<this>");
        return ResolutionModeKt.expectedType(resolutionMode, getComponents());
    }

    /* JADX INFO: compiled from: FirAbstractBodyResolveTransformer.kt */
    @Metadata(d1 = {"\u0000Ô\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00140\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u001a\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u001aR\u0014\u0010,\u001a\u00020-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0014\u00100\u001a\u000201X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u001e\u00104\u001a\f\u0012\u0004\u0012\u00020605j\u0002`78VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0014\u0010:\u001a\u00020;X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u0014\u0010>\u001a\u00020?X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u0014\u0010B\u001a\u00020CX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010ER\u0014\u0010F\u001a\u00020G8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR\u0014\u0010J\u001a\u00020KX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010MR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bN\u0010OR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010QR\u0014\u0010R\u001a\u00020SX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010UR\u0014\u0010V\u001a\u00020WX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bX\u0010YR\u0014\u0010Z\u001a\u00020[8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\\\u0010]R\u001a\u0010^\u001a\b\u0012\u0004\u0012\u00020_0\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b`\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\ba\u0010bR\u0011\u0010c\u001a\u00020d¢\u0006\b\n\u0000\u001a\u0004\be\u0010f¨\u0006g"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "context", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;)V", "callCompleter", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "getCallCompleter", "()Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "callResolver", "Lorg/jetbrains/kotlin/fir/FirCallResolver;", "getCallResolver", "()Lorg/jetbrains/kotlin/fir/FirCallResolver;", "container", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getContainer", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "containingDeclarations", "", "getContainingDeclarations", "()Ljava/util/List;", "getContext", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "dataFlowAnalyzer", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "getDataFlowAnalyzer", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "doubleColonExpressionResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirDoubleColonExpressionResolver;", "getDoubleColonExpressionResolver", "()Lorg/jetbrains/kotlin/fir/resolve/FirDoubleColonExpressionResolver;", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "fileImportsScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "getFileImportsScope", "implicitReceiverStack", "Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "getImplicitReceiverStack", "()Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "integerLiteralAndOperatorApproximationTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/IntegerLiteralAndOperatorApproximationTransformer;", "getIntegerLiteralAndOperatorApproximationTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/IntegerLiteralAndOperatorApproximationTransformer;", "localScopes", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "Lorg/jetbrains/kotlin/fir/declarations/FirLocalScopes;", "getLocalScopes", "()Lkotlinx/collections/immutable/PersistentList;", "noExpectedType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getNoExpectedType", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "outerClassManager", "Lorg/jetbrains/kotlin/fir/resolve/FirOuterClassManager;", "getOuterClassManager", "()Lorg/jetbrains/kotlin/fir/resolve/FirOuterClassManager;", "resolutionStageRunner", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "getResolutionStageRunner", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "getReturnTypeCalculator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "samResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "getSamResolver", "()Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getSymbolProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "syntheticCallGenerator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSyntheticCallGenerator;", "getSyntheticCallGenerator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSyntheticCallGenerator;", "towerDataContext", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "getTowerDataContext", "()Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "towerDataElements", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataElement;", "getTowerDataElements", "getTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "typeResolverTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSpecificTypeResolverTransformer;", "getTypeResolverTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSpecificTypeResolverTransformer;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class BodyResolveTransformerComponents extends BodyResolveComponents {
        private final FirCallCompleter callCompleter;
        private final FirCallResolver callResolver;
        private final BodyResolveContext context;
        private final FirDataFlowAnalyzer dataFlowAnalyzer;
        private final FirDoubleColonExpressionResolver doubleColonExpressionResolver;
        private final IntegerLiteralAndOperatorApproximationTransformer integerLiteralAndOperatorApproximationTransformer;
        private final FirTypeRef noExpectedType;
        private final FirOuterClassManager outerClassManager;
        private final ResolutionStageRunner resolutionStageRunner;
        private final FirSamResolver samResolver;
        private final ScopeSession scopeSession;
        private final FirSession session;
        private final FirSymbolProvider symbolProvider;
        private final FirSyntheticCallGenerator syntheticCallGenerator;
        private final FirAbstractBodyResolveTransformerDispatcher transformer;
        private final FirSpecificTypeResolverTransformer typeResolverTransformer;

        @Override // org.jetbrains.kotlin.fir.resolve.SessionHolder
        public FirSession getSession() {
            return this.session;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.SessionHolder
        public ScopeSession getScopeSession() {
            return this.scopeSession;
        }

        public final FirAbstractBodyResolveTransformerDispatcher getTransformer() {
            return this.transformer;
        }

        public final BodyResolveContext getContext() {
            return this.context;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public BodyResolveTransformerComponents(FirSession session, ScopeSession scopeSession, FirAbstractBodyResolveTransformerDispatcher transformer, BodyResolveContext context) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            Intrinsics.checkNotNullParameter(transformer, "transformer");
            Intrinsics.checkNotNullParameter(context, "context");
            this.session = session;
            this.scopeSession = scopeSession;
            this.transformer = transformer;
            this.context = context;
            this.noExpectedType = FirImplicitTypeRefImplWithoutSource.INSTANCE;
            this.symbolProvider = FirSymbolProviderKt.getSymbolProvider(getSession());
            this.resolutionStageRunner = new ResolutionStageRunner();
            this.callResolver = new FirCallResolver(this, null, 2, 0 == true ? 1 : 0);
            this.typeResolverTransformer = new FirSpecificTypeResolverTransformer(getSession(), false, false, null, 14, null);
            this.callCompleter = new FirCallCompleter(transformer, this);
            this.dataFlowAnalyzer = FirDataFlowAnalyzer.INSTANCE.createFirDataFlowAnalyzer(this, context.getDataFlowAnalyzerContext());
            this.syntheticCallGenerator = new FirSyntheticCallGenerator(this);
            this.doubleColonExpressionResolver = new FirDoubleColonExpressionResolver(getSession());
            this.outerClassManager = new FirOuterClassManager(getSession(), context.getOuterLocalClassForNested());
            this.samResolver = new FirSamResolver(getSession(), getScopeSession(), getOuterClassManager());
            this.integerLiteralAndOperatorApproximationTransformer = new IntegerLiteralAndOperatorApproximationTransformer(getSession(), getScopeSession());
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public List<FirScope> getFileImportsScope() {
            return this.context.getFileImportsScope();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public List<FirTowerDataElement> getTowerDataElements() {
            return this.context.getTowerDataContext().getTowerDataElements();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public PersistentList<FirLocalScope> getLocalScopes() {
            return this.context.getTowerDataContext().getLocalScopes();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirTowerDataContext getTowerDataContext() {
            return this.context.getTowerDataContext();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirFile getFile() {
            return this.context.getFile();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public ImplicitReceiverStack getImplicitReceiverStack() {
            return this.context.getImplicitReceiverStack();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public List<FirDeclaration> getContainingDeclarations() {
            return this.context.getContainers();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public ReturnTypeCalculator getReturnTypeCalculator() {
            return this.context.getReturnTypeCalculator();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirDeclaration getContainer() {
            FirDeclaration containerIfAny = this.context.getContainerIfAny();
            Intrinsics.checkNotNull(containerIfAny);
            return containerIfAny;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirTypeRef getNoExpectedType() {
            return this.noExpectedType;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirSymbolProvider getSymbolProvider() {
            return this.symbolProvider;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public ResolutionStageRunner getResolutionStageRunner() {
            return this.resolutionStageRunner;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirCallResolver getCallResolver() {
            return this.callResolver;
        }

        public final FirSpecificTypeResolverTransformer getTypeResolverTransformer() {
            return this.typeResolverTransformer;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirCallCompleter getCallCompleter() {
            return this.callCompleter;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirDataFlowAnalyzer getDataFlowAnalyzer() {
            return this.dataFlowAnalyzer;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirSyntheticCallGenerator getSyntheticCallGenerator() {
            return this.syntheticCallGenerator;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirDoubleColonExpressionResolver getDoubleColonExpressionResolver() {
            return this.doubleColonExpressionResolver;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirOuterClassManager getOuterClassManager() {
            return this.outerClassManager;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public FirSamResolver getSamResolver() {
            return this.samResolver;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.BodyResolveComponents
        public IntegerLiteralAndOperatorApproximationTransformer getIntegerLiteralAndOperatorApproximationTransformer() {
            return this.integerLiteralAndOperatorApproximationTransformer;
        }
    }
}
