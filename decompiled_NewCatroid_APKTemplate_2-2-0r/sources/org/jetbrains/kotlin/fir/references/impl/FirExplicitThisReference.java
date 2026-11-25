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

/* JADX INFO: compiled from: FirExplicitThisReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010\u001a\u001a\u00020\u001b\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010!J\u0016\u0010\"\u001a\u00020\u001b2\f\u0010#\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016J\u0010\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\u0007H\u0016J)\u0010&\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001d2\f\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u001d0(2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010)R \u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0015R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/references/impl/FirExplicitThisReference;", "Lorg/jetbrains/kotlin/fir/references/FirThisReference;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "labelName", "", "contextReceiverNumber", "", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/lang/String;I)V", "boundSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getBoundSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "setBoundSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)V", "getContextReceiverNumber", "()I", "setContextReceiverNumber", "(I)V", "isImplicit", "", "()Z", "getLabelName", "()Ljava/lang/String;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceBoundSymbol", "newBoundSymbol", "replaceContextReceiverNumber", "newContextReceiverNumber", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/impl/FirExplicitThisReference;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExplicitThisReference extends FirThisReference {
    private FirBasedSymbol<?> boundSymbol;
    private int contextReceiverNumber;
    private final boolean isImplicit;
    private final String labelName;
    private final KtSourceElement source;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirExplicitThisReference transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public String getLabelName() {
        return this.labelName;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public int getContextReceiverNumber() {
        return this.contextReceiverNumber;
    }

    public void setContextReceiverNumber(int i) {
        this.contextReceiverNumber = i;
    }

    public FirExplicitThisReference(KtSourceElement ktSourceElement, String str, int i) {
        this.source = ktSourceElement;
        this.labelName = str;
        this.contextReceiverNumber = i;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public FirBasedSymbol<?> getBoundSymbol() {
        return this.boundSymbol;
    }

    public void setBoundSymbol(FirBasedSymbol<?> firBasedSymbol) {
        this.boundSymbol = firBasedSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    /* JADX INFO: renamed from: isImplicit, reason: from getter */
    public boolean getIsImplicit() {
        return this.isImplicit;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public void replaceBoundSymbol(FirBasedSymbol<?> newBoundSymbol) {
        setBoundSymbol(newBoundSymbol);
    }

    @Override // org.jetbrains.kotlin.fir.references.FirThisReference
    public void replaceContextReceiverNumber(int newContextReceiverNumber) {
        setContextReceiverNumber(newContextReceiverNumber);
    }
}
