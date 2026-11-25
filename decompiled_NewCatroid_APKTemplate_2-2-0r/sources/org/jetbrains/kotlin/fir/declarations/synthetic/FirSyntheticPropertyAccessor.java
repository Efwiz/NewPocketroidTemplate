package org.jetbrains.kotlin.fir.declarations.synthetic;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.impl.FirPropertyAccessorImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSyntheticPropertyAccessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010U\u001a\u00020V\"\u0004\b\u0000\u0010W\"\u0004\b\u0001\u0010X2\u0012\u0010Y\u001a\u000e\u0012\u0004\u0012\u0002HW\u0012\u0004\u0012\u0002HX0Z2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010\\J\b\u0010]\u001a\u00020^H\u0002J\u0016\u0010_\u001a\u00020V2\f\u0010`\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0012\u0010a\u001a\u00020V2\b\u0010b\u001a\u0004\u0018\u00010\u0013H\u0016J\u0016\u0010c\u001a\u00020V2\f\u0010d\u001a\b\u0012\u0004\u0012\u00020\u001b0\nH\u0016J\u0010\u0010e\u001a\u00020V2\u0006\u0010f\u001a\u00020\u001eH\u0016J\u0012\u0010g\u001a\u00020V2\b\u0010h\u001a\u0004\u0018\u00010\"H\u0016J\u0010\u0010i\u001a\u00020V2\u0006\u0010j\u001a\u00020(H\u0016J\u0012\u0010k\u001a\u00020V2\b\u0010l\u001a\u0004\u0018\u00010<H\u0016J\u0010\u0010m\u001a\u00020V2\u0006\u0010n\u001a\u00020@H\u0016J\u0010\u0010o\u001a\u00020V2\u0006\u0010p\u001a\u00020HH\u0016J\u0016\u0010q\u001a\u00020V2\f\u0010r\u001a\b\u0012\u0004\u0012\u00020S0\nH\u0016J)\u0010s\u001a\u00020\u0001\"\u0004\b\u0000\u0010X2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002HX0u2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010vJ)\u0010w\u001a\u00020\u0001\"\u0004\b\u0000\u0010X2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002HX0u2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010vJ)\u0010x\u001a\u00020y\"\u0004\b\u0000\u0010X2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002HX0u2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010zJ)\u0010{\u001a\u00020y\"\u0004\b\u0000\u0010X2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002HX0u2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010zJ)\u0010|\u001a\u00020y\"\u0004\b\u0000\u0010X2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002HX0u2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010zJ)\u0010}\u001a\u00020y\"\u0004\b\u0000\u0010X2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002HX0u2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010zJ)\u0010~\u001a\u00020y\"\u0004\b\u0000\u0010X2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002HX0u2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010zJ)\u0010\u007f\u001a\u00020\u0001\"\u0004\b\u0000\u0010X2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002HX0u2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010vJ*\u0010\u0080\u0001\u001a\u00020y\"\u0004\b\u0000\u0010X2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002HX0u2\u0006\u0010[\u001a\u0002HXH\u0016¢\u0006\u0002\u0010zR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\rR\u0014\u0010\u001d\u001a\u00020\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0016\u0010!\u001a\u0004\u0018\u00010\"X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0014\u0010'\u001a\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0016\u0010+\u001a\u0004\u0018\u00010,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010/R\u0014\u00100\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010/R\u0014\u00101\u001a\u0002028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u0014\u00105\u001a\u0002068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0016\u0010;\u001a\u0004\u0018\u00010<8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0014\u0010?\u001a\u00020@8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0016\u0010C\u001a\u0004\u0018\u00010D8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bE\u0010FR\u0014\u0010G\u001a\u00020H8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0014\u0010K\u001a\u00020LX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bM\u0010NR\u001a\u0010O\u001a\b\u0012\u0004\u0012\u00020P0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010\rR\u001a\u0010R\u001a\b\u0012\u0004\u0012\u00020S0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bT\u0010\r¨\u0006\u0081\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticPropertyAccessor;", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "isGetter", "", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;ZLorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "getContractDescription", "()Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "()Z", "isSetter", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getPropertySymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "getTypeParameters", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getValueParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "notSupported", "", "replaceAnnotations", "newAnnotations", "replaceBody", "newBody", "replaceContextReceivers", "newContextReceivers", "replaceContractDescription", "newContractDescription", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceStatus", "newStatus", "replaceValueParameters", "newValueParameters", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "transformBody", "transformChildren", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirPropertyAccessorImpl;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirPropertyAccessorImpl;", "transformContractDescription", "transformReceiverParameter", "transformReturnTypeRef", "transformStatus", "transformTypeParameters", "transformValueParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSyntheticPropertyAccessor extends FirPropertyAccessor {
    private final FirContractDescription contractDescription;
    private final FirControlFlowGraphReference controlFlowGraphReference;
    private final FirSimpleFunction delegate;
    private final boolean isGetter;
    private final FirPropertySymbol propertySymbol;
    private final FirPropertyAccessorSymbol symbol;

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeserializedContainerSource getContainerSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirReceiverParameter getReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirFunction transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirFunction transformBody(FirTransformer firTransformer, Object obj) {
        return transformBody((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public /* bridge */ /* synthetic */ FirContractDescriptionOwner transformContractDescription(FirTransformer firTransformer, Object obj) {
        return transformContractDescription((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public /* bridge */ /* synthetic */ FirPropertyAccessor transformContractDescription(FirTransformer firTransformer, Object obj) {
        return transformContractDescription((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirFunction transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirPropertyAccessor transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirFunction transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirPropertyAccessor transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirFunction transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirPropertyAccessor transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirFunction transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirTypeParameterRefsOwner transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirTypeParametersOwner transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirFunction transformValueParameters(FirTransformer firTransformer, Object obj) {
        return transformValueParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirPropertyAccessor transformValueParameters(FirTransformer firTransformer, Object obj) {
        return transformValueParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    public final FirSimpleFunction getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor
    /* JADX INFO: renamed from: isGetter, reason: from getter */
    public boolean getIsGetter() {
        return this.isGetter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor
    public FirPropertySymbol getPropertySymbol() {
        return this.propertySymbol;
    }

    public FirSyntheticPropertyAccessor(FirSimpleFunction delegate, boolean z, FirPropertySymbol propertySymbol) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        this.delegate = delegate;
        this.isGetter = z;
        this.propertySymbol = propertySymbol;
        FirPropertyAccessorSymbol firPropertyAccessorSymbol = new FirPropertyAccessorSymbol();
        firPropertyAccessorSymbol.bind(this);
        this.symbol = firPropertyAccessorSymbol;
        this.contractDescription = FirEmptyContractDescription.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.delegate.getSource();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.delegate.getModuleData();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return FirDeclarationOrigin.Synthetic.JavaProperty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirTypeRef getReturnTypeRef() {
        return this.delegate.getReturnTypeRef();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return this.delegate.getStatus();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.delegate.getDispatchReceiverType();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return this.delegate.getDeprecationsProvider();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public List<FirValueParameter> getValueParameters() {
        return this.delegate.getValueParameters();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return this.delegate.getAnnotations();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameter> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor
    public boolean isSetter() {
        return !getIsGetter();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public FirBlock getBody() {
        return this.delegate.getBody();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.delegate.getAttributes();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirPropertyAccessorSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public List<FirContextReceiver> getContextReceivers() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return this.controlFlowGraphReference;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public FirContractDescription getContractDescription() {
        return this.contractDescription;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        this.delegate.accept(visitor, data);
        FirControlFlowGraphReference controlFlowGraphReference = getControlFlowGraphReference();
        if (controlFlowGraphReference != null) {
            controlFlowGraphReference.accept(visitor, data);
        }
        getContractDescription().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public void replaceBody(FirBlock newBody) {
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirPropertyAccessorImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirPropertyAccessorImpl transformReturnTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirPropertyAccessorImpl transformReceiverParameter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public <D> FirPropertyAccessorImpl transformValueParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public <D> FirPropertyAccessorImpl transformContractDescription(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirPropertyAccessorImpl transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirPropertyAccessor transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public <D> FirPropertyAccessor transformBody(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirPropertyAccessor transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReturnTypeRef(FirTypeRef newReturnTypeRef) {
        Intrinsics.checkNotNullParameter(newReturnTypeRef, "newReturnTypeRef");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReceiverParameter(FirReceiverParameter newReceiverParameter) {
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public void replaceValueParameters(List<? extends FirValueParameter> newValueParameters) {
        Intrinsics.checkNotNullParameter(newValueParameters, "newValueParameters");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public void replaceContractDescription(FirContractDescription newContractDescription) {
        Intrinsics.checkNotNullParameter(newContractDescription, "newContractDescription");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers) {
        Intrinsics.checkNotNullParameter(newContextReceivers, "newContextReceivers");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        notSupported();
        throw null;
    }

    private final Void notSupported() {
        throw new IllegalStateException("Mutation of synthetic property accessor isn't supported".toString());
    }
}
