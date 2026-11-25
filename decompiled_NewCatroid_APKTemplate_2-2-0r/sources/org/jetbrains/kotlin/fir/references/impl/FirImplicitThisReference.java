package org.jetbrains.kotlin.fir.references.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirImplicitThisReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J5\u0010\u0018\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u001a\"\u0004\b\u0001\u0010\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u0002H\u001b0\u001d2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u00192\f\u0010!\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\u0016J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0005H\u0016J)\u0010$\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001b0&2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010'R\u001a\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000eX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000fR\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/fir/references/impl/FirImplicitThisReference;", "Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "boundSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "contextReceiverNumber", "", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;I)V", "getBoundSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getContextReceiverNumber", "()I", "setContextReceiverNumber", "(I)V", "isImplicit", "", "()Z", "labelName", "", "getLabelName", "()Ljava/lang/String;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceBoundSymbol", "newBoundSymbol", "replaceContextReceiverNumber", "newContextReceiverNumber", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/impl/FirImplicitThisReference;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirImplicitThisReference extends FirThisReference {
    private final FirBasedSymbol<?> boundSymbol;
    private int contextReceiverNumber;
    private final boolean isImplicit = true;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public String getLabelName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public void replaceBoundSymbol(FirBasedSymbol<?> newBoundSymbol) {
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirImplicitThisReference transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public FirBasedSymbol<?> getBoundSymbol() {
        return this.boundSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public int getContextReceiverNumber() {
        return this.contextReceiverNumber;
    }

    public void setContextReceiverNumber(int i) {
        this.contextReceiverNumber = i;
    }

    public FirImplicitThisReference(FirBasedSymbol<?> firBasedSymbol, int i) {
        this.boundSymbol = firBasedSymbol;
        this.contextReceiverNumber = i;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    /* JADX INFO: renamed from: isImplicit, reason: from getter */
    public boolean getIsImplicit() {
        return this.isImplicit;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public void replaceContextReceiverNumber(int newContextReceiverNumber) {
        setContextReceiverNumber(newContextReceiverNumber);
    }
}
