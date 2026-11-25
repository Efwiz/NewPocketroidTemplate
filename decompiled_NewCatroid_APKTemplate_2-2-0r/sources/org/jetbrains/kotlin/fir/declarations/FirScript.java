package org.jetbrains.kotlin.fir.declarations;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirScript.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010+\u001a\u0002H,\"\u0004\b\u0000\u0010,\"\u0004\b\u0001\u0010-2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u0002H,\u0012\u0004\u0012\u0002H-0/2\u0006\u00100\u001a\u0002H-H\u0016¢\u0006\u0002\u00101J\u0016\u00102\u001a\u0002032\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\u0016\u00105\u001a\u0002032\f\u00106\u001a\b\u0012\u0004\u0012\u00020%0\u0004H&J3\u00107\u001a\u0002H8\"\b\b\u0000\u00108*\u000209\"\u0004\b\u0001\u0010-2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H-0;2\u0006\u00100\u001a\u0002H-H\u0016¢\u0006\u0002\u0010<J)\u0010=\u001a\u00020\u0000\"\u0004\b\u0000\u0010-2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H-0;2\u0006\u00100\u001a\u0002H-H&¢\u0006\u0002\u0010>J)\u0010?\u001a\u00020\u0000\"\u0004\b\u0000\u0010-2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H-0;2\u0006\u00100\u001a\u0002H-H&¢\u0006\u0002\u0010>R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0007R\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0007R\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0016R\u0014\u0010 \u001a\u0004\u0018\u00010!X¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0018\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\u0007R\u0012\u0010'\u001a\u00020(X¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "parameters", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "getParameters", "resultPropertyName", "getResultPropertyName", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "statements", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "getStatements", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceStatements", "newStatements", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "transformStatements", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirScript extends FirDeclaration {
    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    public abstract List<FirContextReceiver> getContextReceivers();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public abstract FirModuleData getModuleData();

    public abstract Name getName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationOrigin getOrigin();

    public abstract List<FirVariable> getParameters();

    public abstract Name getResultPropertyName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract List<FirStatement> getStatements();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirScriptSymbol getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceStatements(List<? extends FirStatement> newStatements);

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirScript transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirScript transformStatements(FirTransformer<? super D> transformer, D data);

    public FirScript() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitScript(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirScript firScriptTransformScript = transformer.transformScript(this, data);
        Intrinsics.checkNotNull(firScriptTransformScript, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirScript.transform");
        return firScriptTransformScript;
    }
}
