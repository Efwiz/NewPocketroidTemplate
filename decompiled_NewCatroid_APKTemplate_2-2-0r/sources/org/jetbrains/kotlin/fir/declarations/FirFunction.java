package org.jetbrains.kotlin.fir.declarations;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirTargetElement;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirFunction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ø\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0005J5\u0010H\u001a\u0002HI\"\u0004\b\u0000\u0010I\"\u0004\b\u0001\u0010J2\u0012\u0010K\u001a\u000e\u0012\u0004\u0012\u0002HI\u0012\u0004\u0012\u0002HJ0L2\u0006\u0010M\u001a\u0002HJH\u0016¢\u0006\u0002\u0010NJ\u0016\u0010O\u001a\u00020P2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H&J\u0012\u0010R\u001a\u00020P2\b\u0010S\u001a\u0004\u0018\u00010\u0010H&J\u0016\u0010T\u001a\u00020P2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00180\u0007H&J\u0012\u0010V\u001a\u00020P2\b\u0010W\u001a\u0004\u0018\u00010\u001bH&J\u0010\u0010X\u001a\u00020P2\u0006\u0010Y\u001a\u00020\u001fH&J\u0012\u0010Z\u001a\u00020P2\b\u0010[\u001a\u0004\u0018\u00010/H&J\u0010\u0010\\\u001a\u00020P2\u0006\u0010]\u001a\u000203H&J\u0010\u0010^\u001a\u00020P2\u0006\u0010_\u001a\u00020;H&J\u0016\u0010`\u001a\u00020P2\f\u0010a\u001a\b\u0012\u0004\u0012\u00020F0\u0007H&J3\u0010b\u001a\u0002Hc\"\b\b\u0000\u0010c*\u00020d\"\u0004\b\u0001\u0010J2\f\u0010e\u001a\b\u0012\u0004\u0012\u0002HJ0f2\u0006\u0010M\u001a\u0002HJH\u0016¢\u0006\u0002\u0010gJ)\u0010h\u001a\u00020\u0000\"\u0004\b\u0000\u0010J2\f\u0010e\u001a\b\u0012\u0004\u0012\u0002HJ0f2\u0006\u0010M\u001a\u0002HJH&¢\u0006\u0002\u0010iJ)\u0010j\u001a\u00020\u0000\"\u0004\b\u0000\u0010J2\f\u0010e\u001a\b\u0012\u0004\u0012\u0002HJ0f2\u0006\u0010M\u001a\u0002HJH&¢\u0006\u0002\u0010iJ)\u0010k\u001a\u00020\u0000\"\u0004\b\u0000\u0010J2\f\u0010e\u001a\b\u0012\u0004\u0012\u0002HJ0f2\u0006\u0010M\u001a\u0002HJH&¢\u0006\u0002\u0010iJ)\u0010l\u001a\u00020\u0000\"\u0004\b\u0000\u0010J2\f\u0010e\u001a\b\u0012\u0004\u0012\u0002HJ0f2\u0006\u0010M\u001a\u0002HJH&¢\u0006\u0002\u0010iJ)\u0010m\u001a\u00020\u0000\"\u0004\b\u0000\u0010J2\f\u0010e\u001a\b\u0012\u0004\u0012\u0002HJ0f2\u0006\u0010M\u001a\u0002HJH&¢\u0006\u0002\u0010iJ)\u0010n\u001a\u00020\u0000\"\u0004\b\u0000\u0010J2\f\u0010e\u001a\b\u0012\u0004\u0012\u0002HJ0f2\u0006\u0010M\u001a\u0002HJH&¢\u0006\u0002\u0010iJ)\u0010o\u001a\u00020\u0000\"\u0004\b\u0000\u0010J2\f\u0010e\u001a\b\u0012\u0004\u0012\u0002HJ0f2\u0006\u0010M\u001a\u0002HJH&¢\u0006\u0002\u0010iR\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\nR\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0012\u0010\u001e\u001a\u00020\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u0004\u0018\u00010#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0012\u0010*\u001a\u00020+X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\u0004\u0018\u00010/X¦\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000203X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0014\u00106\u001a\u0004\u0018\u000107X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0012\u0010:\u001a\u00020;X¦\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u001a\u0010>\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000?X¦\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0018\u0010B\u001a\b\u0012\u0004\u0012\u00020C0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\bD\u0010\nR\u0018\u0010E\u001a\b\u0012\u0004\u0012\u00020F0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\bG\u0010\n\u0082\u0001\u0005pqrst¨\u0006u"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/FirTargetElement;", "Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getValueParameters", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceBody", "newBody", "replaceContextReceivers", "newContextReceivers", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceStatus", "newStatus", "replaceValueParameters", "newValueParameters", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "transformBody", "transformReceiverParameter", "transformReturnTypeRef", "transformStatus", "transformTypeParameters", "transformValueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirFunction extends FirCallableDeclaration implements FirTargetElement, FirControlFlowGraphOwner, FirStatement {
    public /* synthetic */ FirFunction(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    public abstract FirBlock getBody();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeserializedContainerSource getContainerSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract List<FirContextReceiver> getContextReceivers();

    public abstract FirControlFlowGraphReference getControlFlowGraphReference();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeprecationsProvider getDeprecationsProvider();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract ConeSimpleKotlinType getDispatchReceiverType();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public abstract FirModuleData getModuleData();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract FirReceiverParameter getReceiverParameter();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract FirTypeRef getReturnTypeRef();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract FirDeclarationStatus getStatus();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirFunctionSymbol<? extends FirFunction> getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract List<FirTypeParameterRef> getTypeParameters();

    public abstract List<FirValueParameter> getValueParameters();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceBody(FirBlock newBody);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers);

    public abstract void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceReceiverParameter(FirReceiverParameter newReceiverParameter);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceReturnTypeRef(FirTypeRef newReturnTypeRef);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract void replaceStatus(FirDeclarationStatus newStatus);

    public abstract void replaceValueParameters(List<? extends FirValueParameter> newValueParameters);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirFunction transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirFunction transformBody(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirFunction transformReceiverParameter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirFunction transformReturnTypeRef(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract <D> FirFunction transformStatus(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract <D> FirFunction transformTypeParameters(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirFunction transformValueParameters(FirTransformer<? super D> transformer, D data);

    private FirFunction() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitFunction(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformFunction = transformer.transformFunction(this, data);
        Intrinsics.checkNotNull(firStatementTransformFunction, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirFunction.transform");
        return firStatementTransformFunction;
    }
}
