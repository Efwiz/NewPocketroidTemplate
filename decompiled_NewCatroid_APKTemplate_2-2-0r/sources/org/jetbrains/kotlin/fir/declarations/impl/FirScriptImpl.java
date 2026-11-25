package org.jetbrains.kotlin.fir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirScriptImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0081\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0007\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u0012\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0007\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u001bJ5\u00103\u001a\u000204\"\u0004\b\u0000\u00105\"\u0004\b\u0001\u001062\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u0002H5\u0012\u0004\u0012\u0002H6082\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010:J\u0016\u0010;\u001a\u0002042\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\b0=H\u0016J\u0016\u0010>\u001a\u0002042\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00120=H\u0016J)\u0010@\u001a\u00020\u0000\"\u0004\b\u0000\u001062\f\u0010A\u001a\b\u0012\u0004\u0012\u0002H60B2\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010CJ)\u0010D\u001a\u00020\u0000\"\u0004\b\u0000\u001062\f\u0010A\u001a\b\u0012\u0004\u0012\u0002H60B2\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010CJ)\u0010E\u001a\u00020\u0000\"\u0004\b\u0000\u001062\f\u0010A\u001a\b\u0012\u0004\u0012\u0002H60B2\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010CR(\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010 \u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R(\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0007X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010 \u001a\u0004\b#\u0010\u001d\"\u0004\b$\u0010\u001fR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001dR\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010(R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R(\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0007X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010 \u001a\u0004\b/\u0010\u001d\"\u0004\b0\u0010\u001fR\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u00102\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirScriptImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "name", "Lorg/jetbrains/kotlin/name/Name;", "statements", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "parameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "resultPropertyName", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Ljava/util/List;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lorg/jetbrains/kotlin/name/Name;Ljava/util/List;Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/name/Name;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getContextReceivers-5e3fPpI", "setContextReceivers-GqUYU-s", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getParameters", "getResultPropertyName", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getStatements-5e3fPpI", "setStatements-GqUYU-s", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceStatements", "newStatements", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirScriptImpl;", "transformChildren", "transformStatements", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirScriptImpl extends FirScript {
    private List<T> annotations;
    private final FirDeclarationAttributes attributes;
    private List<T> contextReceivers;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirDeclarationOrigin origin;
    private final List<FirVariable> parameters;
    private final Name resultPropertyName;
    private final KtSourceElement source;
    private List<T> statements;
    private final FirScriptSymbol symbol;

    public /* synthetic */ FirScriptImpl(KtSourceElement ktSourceElement, FirResolvePhase firResolvePhase, List list, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirDeclarationAttributes firDeclarationAttributes, Name name2, List list2, FirScriptSymbol firScriptSymbol, List list3, List list4, Name name3, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firResolvePhase, list, firModuleData, firDeclarationOrigin, firDeclarationAttributes, name2, list2, firScriptSymbol, list3, list4, name3);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6871getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript
    public /* bridge */ /* synthetic */ List getContextReceivers() {
        return MutableOrEmptyList.m6756boximpl(m6872getContextReceivers5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript
    public /* bridge */ /* synthetic */ List getStatements() {
        return MutableOrEmptyList.m6756boximpl(m6873getStatements5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirScript transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript
    public /* bridge */ /* synthetic */ FirScript transformStatements(FirTransformer firTransformer, Object obj) {
        return transformStatements((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6871getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6874setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript
    public Name getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: getStatements-5e3fPpI, reason: not valid java name */
    public List<T> m6873getStatements5e3fPpI() {
        return this.statements;
    }

    /* JADX INFO: renamed from: setStatements-GqUYU-s, reason: not valid java name */
    public void m6876setStatementsGqUYUs(List<T> list) {
        this.statements = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirScriptSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript
    public List<FirVariable> getParameters() {
        return this.parameters;
    }

    /* JADX INFO: renamed from: getContextReceivers-5e3fPpI, reason: not valid java name */
    public List<T> m6872getContextReceivers5e3fPpI() {
        return this.contextReceivers;
    }

    /* JADX INFO: renamed from: setContextReceivers-GqUYU-s, reason: not valid java name */
    public void m6875setContextReceiversGqUYUs(List<T> list) {
        this.contextReceivers = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript
    public Name getResultPropertyName() {
        return this.resultPropertyName;
    }

    private FirScriptImpl(KtSourceElement ktSourceElement, FirResolvePhase resolvePhase, List<T> list, FirModuleData moduleData, FirDeclarationOrigin origin, FirDeclarationAttributes attributes, Name name2, List<T> list2, FirScriptSymbol symbol, List<FirVariable> parameters, List<T> list3, Name name3) {
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.source = ktSourceElement;
        this.annotations = list;
        this.moduleData = moduleData;
        this.origin = origin;
        this.attributes = attributes;
        this.name = name2;
        this.statements = list2;
        this.symbol = symbol;
        this.parameters = parameters;
        this.contextReceivers = list3;
        this.resultPropertyName = name3;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6871getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6873getStatements5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirStatement) it2.next()).accept(visitor, data);
        }
        Iterator<T> it3 = getParameters().iterator();
        while (it3.hasNext()) {
            ((FirVariable) it3.next()).accept(visitor, data);
        }
        Iterator<T> it4 = MutableOrEmptyList.m6756boximpl(m6872getContextReceivers5e3fPpI()).iterator();
        while (it4.hasNext()) {
            ((FirContextReceiver) it4.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirScriptImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        transformStatements((FirTransformer) transformer, (Object) data);
        FirTransformerUtilKt.transformInplace(getParameters(), transformer, data);
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6872getContextReceivers5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirScriptImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6871getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript
    public <D> FirScriptImpl transformStatements(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6873getStatements5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6874setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirScript
    public void replaceStatements(List<? extends FirStatement> newStatements) {
        Intrinsics.checkNotNullParameter(newStatements, "newStatements");
        m6876setStatementsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newStatements));
    }
}
