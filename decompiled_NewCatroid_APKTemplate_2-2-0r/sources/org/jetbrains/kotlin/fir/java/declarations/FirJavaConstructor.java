package org.jetbrains.kotlin.fir.java.declarations;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirImplementationDetail;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirJavaConstructor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0083\u0001\b\u0007\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000f\u0012\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u0014\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u001a\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c¢\u0006\u0002\u0010\u001dJ5\u0010_\u001a\u00020`\"\u0004\b\u0000\u0010a\"\u0004\b\u0001\u0010b2\u0012\u0010c\u001a\u000e\u0012\u0004\u0012\u0002Ha\u0012\u0004\u0012\u0002Hb0d2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0002\u0010fJ\u0016\u0010g\u001a\u00020`2\f\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016J\u0012\u0010i\u001a\u00020`2\b\u0010j\u001a\u0004\u0018\u00010(H\u0016J\u0016\u0010k\u001a\u00020`2\f\u0010l\u001a\b\u0012\u0004\u0012\u0002020\u0015H\u0016J\u0010\u0010m\u001a\u00020`2\u0006\u0010n\u001a\u000205H\u0016J\u0012\u0010o\u001a\u00020`2\b\u0010p\u001a\u0004\u0018\u000109H\u0016J\u0012\u0010q\u001a\u00020`2\b\u0010r\u001a\u0004\u0018\u00010=H\u0016J\u0010\u0010s\u001a\u00020`2\u0006\u0010t\u001a\u00020AH\u0016J\u0012\u0010u\u001a\u00020`2\b\u0010v\u001a\u0004\u0018\u00010NH\u0016J\u0010\u0010w\u001a\u00020`2\u0006\u0010x\u001a\u00020\rH\u0016J\u0010\u0010y\u001a\u00020`2\u0006\u0010z\u001a\u00020\u0018H\u0016J\u0016\u0010{\u001a\u00020`2\f\u0010|\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015H\u0016J*\u0010}\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0080\u0001J+\u0010\u0081\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0082\u0001J+\u0010\u0083\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0080\u0001J+\u0010\u0084\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0082\u0001J+\u0010\u0085\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0080\u0001J+\u0010\u0086\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0080\u0001J+\u0010\u0087\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0082\u0001J+\u0010\u0088\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0080\u0001J+\u0010\u0089\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0080\u0001J+\u0010\u008a\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\f\u0010~\u001a\b\u0012\u0004\u0012\u0002Hb0\u007f2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0080\u0001R!\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b\u001f\u0010 R\u0014\u0010#\u001a\u00020$X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0016\u0010'\u001a\u0004\u0018\u00010(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\b\u0012\u0004\u0012\u0002020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u0010 R\u0014\u00104\u001a\u0002058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u0016\u00108\u001a\u0004\u0018\u0001098VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0016\u0010<\u001a\u0004\u0018\u00010=8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?R\u001a\u0010@\u001a\u00020AX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010HR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bK\u0010LR\u0016\u0010M\u001a\u0004\u0018\u00010N8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bO\u0010PR\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bU\u0010VR\u001a\u0010\u0017\u001a\u00020\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b[\u0010\\R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b]\u0010 R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b^\u0010 ¨\u0006\u008b\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaConstructor;", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "isPrimary", "", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "valueParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "annotationBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;ZLorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Ljava/util/List;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "annotations", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "Lkotlin/Lazy;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "getContractDescription", "()Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "delegatedConstructor", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "getDelegatedConstructor", "()Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "()Z", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "getTypeParameters", "getValueParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceBody", "newBody", "replaceContextReceivers", "newContextReceivers", "replaceContractDescription", "newContractDescription", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDelegatedConstructor", "newDelegatedConstructor", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceStatus", "newStatus", "replaceValueParameters", "newValueParameters", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaConstructor;", "transformBody", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "transformChildren", "transformContractDescription", "transformDelegatedConstructor", "transformReceiverParameter", "transformReturnTypeRef", "transformStatus", "transformTypeParameters", "transformValueParameters", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaConstructor extends FirConstructor {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final FirDeclarationAttributes attributes;
    private DeserializedContainerSource containerSource;
    private DeprecationsProvider deprecationsProvider;
    private final ConeSimpleKotlinType dispatchReceiverType;
    private final boolean isPrimary;
    private final FirModuleData moduleData;
    private final FirDeclarationOrigin.Java origin;
    private FirTypeRef returnTypeRef;
    private final KtSourceElement source;
    private FirDeclarationStatus status;
    private final FirConstructorSymbol symbol;
    private final List<FirTypeParameterRef> typeParameters;
    private final List<FirValueParameter> valueParameters;

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public FirBlock getBody() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor
    public FirDelegatedConstructorCall getDelegatedConstructor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirReceiverParameter getReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReceiverParameter(FirReceiverParameter newReceiverParameter) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirJavaConstructor transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor
    public <D> FirJavaConstructor transformDelegatedConstructor(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirJavaConstructor transformReceiverParameter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirConstructor transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor
    public /* bridge */ /* synthetic */ FirConstructor transformDelegatedConstructor(FirTransformer firTransformer, Object obj) {
        return transformDelegatedConstructor((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirConstructor transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirFunction transformReceiverParameter(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirConstructor transformStatus(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirConstructor transformTypeParameters(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirConstructor transformValueParameters(FirTransformer firTransformer, Object obj) {
        return transformValueParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirFunction transformValueParameters(FirTransformer firTransformer, Object obj) {
        return transformValueParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirConstructorSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin.Java getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor
    /* JADX INFO: renamed from: isPrimary, reason: from getter */
    public boolean getIsPrimary() {
        return this.isPrimary;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirTypeRef getReturnTypeRef() {
        return this.returnTypeRef;
    }

    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public List<FirValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameterRef> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return this.status;
    }

    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    @FirImplementationDetail
    public FirJavaConstructor(KtSourceElement ktSourceElement, FirModuleData moduleData, FirConstructorSymbol symbol, FirDeclarationOrigin.Java origin, boolean z, FirTypeRef returnTypeRef, List<FirValueParameter> valueParameters, List<FirTypeParameterRef> typeParameters, final Function0<? extends List<? extends FirAnnotation>> annotationBuilder, FirDeclarationStatus status, FirResolvePhase resolvePhase, ConeSimpleKotlinType coneSimpleKotlinType) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(returnTypeRef, "returnTypeRef");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(annotationBuilder, "annotationBuilder");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.symbol = symbol;
        this.origin = origin;
        this.isPrimary = z;
        this.returnTypeRef = returnTypeRef;
        this.valueParameters = valueParameters;
        this.typeParameters = typeParameters;
        this.status = status;
        this.dispatchReceiverType = coneSimpleKotlinType;
        this.deprecationsProvider = UnresolvedDeprecationProvider.INSTANCE;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
        this.attributes = new FirDeclarationAttributes();
        this.annotations = LazyKt.lazy(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.declarations.FirJavaConstructor$annotations$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return annotationBuilder.invoke();
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public FirContractDescription getContractDescription() {
        return FirEmptyContractDescription.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return (List) this.annotations.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public List<FirContextReceiver> getContextReceivers() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public <D> FirJavaConstructor transformValueParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getValueParameters(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirConstructor transformReturnTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setReturnTypeRef((FirTypeRef) FirTransformerUtilKt.transformSingle(getReturnTypeRef(), transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getReturnTypeRef().accept(visitor, data);
        FirControlFlowGraphReference controlFlowGraphReference = getControlFlowGraphReference();
        if (controlFlowGraphReference != null) {
            controlFlowGraphReference.accept(visitor, data);
        }
        Iterator<T> it = getTypeParameters().iterator();
        while (it.hasNext()) {
            ((FirTypeParameterRef) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = getValueParameters().iterator();
        while (it2.hasNext()) {
            ((FirValueParameter) it2.next()).accept(visitor, data);
        }
        getStatus().accept(visitor, data);
        Iterator<T> it3 = getAnnotations().iterator();
        while (it3.hasNext()) {
            ((FirAnnotation) it3.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirJavaConstructor transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformReturnTypeRef((FirTransformer) transformer, (Object) data);
        transformTypeParameters((FirTransformer) transformer, (Object) data);
        transformValueParameters((FirTransformer) transformer, (Object) data);
        setStatus((FirDeclarationStatus) FirTransformerUtilKt.transformSingle(getStatus(), transformer, data));
        transformAnnotations((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public <D> FirConstructor transformContractDescription(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirJavaConstructor transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setStatus((FirDeclarationStatus) FirTransformerUtilKt.transformSingle(getStatus(), transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        throw new AssertionError("Mutating annotations for FirJava* is not supported");
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public <D> FirConstructor transformBody(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirJavaConstructor transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getTypeParameters(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReturnTypeRef(FirTypeRef newReturnTypeRef) {
        Intrinsics.checkNotNullParameter(newReturnTypeRef, "newReturnTypeRef");
        setReturnTypeRef(newReturnTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public void replaceValueParameters(List<? extends FirValueParameter> newValueParameters) {
        Intrinsics.checkNotNullParameter(newValueParameters, "newValueParameters");
        getValueParameters().clear();
        CollectionsKt.addAll(getValueParameters(), newValueParameters);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor
    public void replaceDelegatedConstructor(FirDelegatedConstructorCall newDelegatedConstructor) {
        throw new IllegalStateException("Delegated constructor cannot be replaced for FirJavaConstructor".toString());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
        setDeprecationsProvider(newDeprecationsProvider);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers) {
        Intrinsics.checkNotNullParameter(newContextReceivers, "newContextReceivers");
        throw new IllegalStateException("Context receivers cannot be replaced for FirJavaConstructor".toString());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public void replaceContractDescription(FirContractDescription newContractDescription) {
        Intrinsics.checkNotNullParameter(newContractDescription, "newContractDescription");
        throw new IllegalStateException("Contract description cannot be replaced for FirJavaConstructor".toString());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public void replaceBody(FirBlock newBody) {
        throw new IllegalStateException("Body cannot be replaced for FirJavaConstructor".toString());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        setStatus(newStatus);
    }
}
