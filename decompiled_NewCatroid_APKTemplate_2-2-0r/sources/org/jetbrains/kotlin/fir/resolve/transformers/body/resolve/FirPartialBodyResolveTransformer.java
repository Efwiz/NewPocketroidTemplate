package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: FirPartialBodyResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J'\u0010\u001e\u001a\u0002H\u001f\"\b\b\u0000\u0010\u001f*\u00020 2\u0006\u0010!\u001a\u0002H\u001f2\u0006\u0010\"\u001a\u00020#H\u0016¢\u0006\u0002\u0010$R\u0018\u0010\u0005\u001a\u00020\u00068Æ\u0002¢\u0006\f\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\f8Æ\u0002¢\u0006\f\u0012\u0004\b\r\u0010\b\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00118V@QX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0017\u001a\u00020\u00188Æ\u0002¢\u0006\f\u0012\u0004\b\u0019\u0010\b\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirPartialBodyResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;)V", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "getComponents$annotations", "()V", "getComponents", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "context", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "getContext$annotations", "getContext", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "value", "", "implicitTypeOnly", "getImplicitTypeOnly", "()Z", "setImplicitTypeOnly$resolve", "(Z)V", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "getResolutionContext$annotations", "getResolutionContext", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "getTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;)Lorg/jetbrains/kotlin/fir/FirElement;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirPartialBodyResolveTransformer extends FirAbstractBodyResolveTransformer {
    private final FirAbstractBodyResolveTransformerDispatcher transformer;

    public static /* synthetic */ void getComponents$annotations() {
    }

    public static /* synthetic */ void getContext$annotations() {
    }

    public static /* synthetic */ void getResolutionContext$annotations() {
    }

    public final FirAbstractBodyResolveTransformerDispatcher getTransformer() {
        return this.transformer;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirPartialBodyResolveTransformer(FirAbstractBodyResolveTransformerDispatcher transformer) {
        super(transformer.getTransformerPhase());
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.transformer = transformer;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    public final BodyResolveContext getContext() {
        return getTransformer().getContext();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    public final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents getComponents() {
        return getTransformer().getComponents();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    public final ResolutionContext getResolutionContext() {
        return getTransformer().getResolutionContext();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    public boolean getImplicitTypeOnly() {
        return this.transformer.getImplicitTypeOnly();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    @PrivateForInline
    public void setImplicitTypeOnly$resolve(boolean z) {
        this.transformer.setImplicitTypeOnly$resolve(z);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        return (E) element.transform(this.transformer, data);
    }
}
