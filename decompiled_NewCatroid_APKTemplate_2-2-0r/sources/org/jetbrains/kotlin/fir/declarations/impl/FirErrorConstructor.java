package org.jetbrains.kotlin.fir.declarations.impl;

import androidx.core.app.NotificationCompat;
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
import org.jetbrains.kotlin.fir.FirImplementationDetail;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
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

/* JADX INFO: compiled from: FirErrorConstructor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001BÃ\u0001\b\u0007\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c\u0012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\r\u0012\u0006\u0010 \u001a\u00020!\u0012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001c\u0012\u0006\u0010$\u001a\u00020%\u0012\b\u0010&\u001a\u0004\u0018\u00010'\u0012\b\u0010(\u001a\u0004\u0018\u00010)¢\u0006\u0002\u0010*J5\u0010g\u001a\u00020h\"\u0004\b\u0000\u0010i\"\u0004\b\u0001\u0010j2\u0012\u0010k\u001a\u000e\u0012\u0004\u0012\u0002Hi\u0012\u0004\u0012\u0002Hj0l2\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0002\u0010nJ\u0016\u0010o\u001a\u00020h2\f\u0010p\u001a\b\u0012\u0004\u0012\u00020#0qH\u0016J\u0012\u0010r\u001a\u00020h2\b\u0010s\u001a\u0004\u0018\u00010)H\u0016J\u0016\u0010t\u001a\u00020h2\f\u0010u\u001a\b\u0012\u0004\u0012\u00020\u001d0qH\u0016J\u0010\u0010v\u001a\u00020h2\u0006\u0010w\u001a\u00020!H\u0016J\u0012\u0010x\u001a\u00020h2\b\u0010y\u001a\u0004\u0018\u00010?H\u0016J\u0012\u0010z\u001a\u00020h2\b\u0010{\u001a\u0004\u0018\u00010'H\u0016J\u0010\u0010|\u001a\u00020h2\u0006\u0010}\u001a\u00020\u0016H\u0016J\u0012\u0010~\u001a\u00020h2\b\u0010\u007f\u001a\u0004\u0018\u00010\u0014H\u0016J\u0012\u0010\u0080\u0001\u001a\u00020h2\u0007\u0010\u0081\u0001\u001a\u00020\u0012H\u0016J\u0012\u0010\u0082\u0001\u001a\u00020h2\u0007\u0010\u0083\u0001\u001a\u00020\u0010H\u0016J\u0018\u0010\u0084\u0001\u001a\u00020h2\r\u0010\u0085\u0001\u001a\b\u0012\u0004\u0012\u00020\u001f0qH\u0016J-\u0010\u0086\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001J-\u0010\u008a\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001J-\u0010\u008b\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001J-\u0010\u008c\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001J-\u0010\u008d\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001J-\u0010\u008e\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001J-\u0010\u008f\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001J-\u0010\u0090\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001J-\u0010\u0091\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001J-\u0010\u0092\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010j2\u000e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002Hj0\u0088\u00012\u0006\u0010m\u001a\u0002HjH\u0016¢\u0006\u0003\u0010\u0089\u0001R(\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001cX\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010/\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u001c\u0010(\u001a\u0004\u0018\u00010)X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R(\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010/\u001a\u0004\b8\u0010,\"\u0004\b9\u0010.R\u001a\u0010 \u001a\u00020!X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001c\u0010>\u001a\u0004\u0018\u00010?X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u001a\u0010\u0015\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010MR\u0014\u0010N\u001a\u00020O8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bN\u0010PR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010RR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bS\u0010TR\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR\u001a\u0010\u0011\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b]\u0010^R\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b_\u0010`\"\u0004\ba\u0010bR\u0014\u0010$\u001a\u00020%X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bc\u0010dR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\be\u0010,R\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bf\u0010,\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0093\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirErrorConstructor;", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "delegatedConstructor", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Ljava/util/List;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;Ljava/util/List;Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;Lorg/jetbrains/kotlin/fir/expressions/FirBlock;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBody", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContextReceivers-5e3fPpI", "setContextReceivers-GqUYU-s", "getContractDescription", "()Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "setContractDescription", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;)V", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "setControlFlowGraphReference", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;)V", "getDelegatedConstructor", "()Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "setDelegatedConstructor", "(Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;)V", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "isPrimary", "", "()Z", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "getTypeParameters", "getValueParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceBody", "newBody", "replaceContextReceivers", "newContextReceivers", "replaceContractDescription", "newContractDescription", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDelegatedConstructor", "newDelegatedConstructor", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceStatus", "newStatus", "replaceValueParameters", "newValueParameters", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirErrorConstructor;", "transformBody", "transformChildren", "transformContractDescription", "transformDelegatedConstructor", "transformReceiverParameter", "transformReturnTypeRef", "transformStatus", "transformTypeParameters", "transformValueParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorConstructor extends FirConstructor {
    private List<T> annotations;
    private final FirDeclarationAttributes attributes;
    private FirBlock body;
    private final DeserializedContainerSource containerSource;
    private List<T> contextReceivers;
    private FirContractDescription contractDescription;
    private FirControlFlowGraphReference controlFlowGraphReference;
    private FirDelegatedConstructorCall delegatedConstructor;
    private DeprecationsProvider deprecationsProvider;
    private final ConeSimpleKotlinType dispatchReceiverType;
    private final FirModuleData moduleData;
    private final FirDeclarationOrigin origin;
    private FirReceiverParameter receiverParameter;
    private FirTypeRef returnTypeRef;
    private final KtSourceElement source;
    private FirDeclarationStatus status;
    private final FirConstructorSymbol symbol;
    private final List<FirTypeParameterRef> typeParameters;
    private final List<FirValueParameter> valueParameters;

    @FirImplementationDetail
    public /* synthetic */ FirErrorConstructor(KtSourceElement ktSourceElement, FirResolvePhase firResolvePhase, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirDeclarationAttributes firDeclarationAttributes, List list, FirDeclarationStatus firDeclarationStatus, FirTypeRef firTypeRef, FirReceiverParameter firReceiverParameter, DeprecationsProvider deprecationsProvider, DeserializedContainerSource deserializedContainerSource, ConeSimpleKotlinType coneSimpleKotlinType, List list2, List list3, FirContractDescription firContractDescription, List list4, FirConstructorSymbol firConstructorSymbol, FirDelegatedConstructorCall firDelegatedConstructorCall, FirBlock firBlock, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firResolvePhase, firModuleData, firDeclarationOrigin, firDeclarationAttributes, list, firDeclarationStatus, firTypeRef, firReceiverParameter, deprecationsProvider, deserializedContainerSource, coneSimpleKotlinType, list2, list3, firContractDescription, list4, firConstructorSymbol, firDelegatedConstructorCall, firBlock);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor
    /* JADX INFO: renamed from: isPrimary */
    public boolean getIsPrimary() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6837getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ List getContextReceivers() {
        return MutableOrEmptyList.m6756boximpl(m6838getContextReceivers5e3fPpI());
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirConstructor transformBody(FirTransformer firTransformer, Object obj) {
        return transformBody((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirFunction transformBody(FirTransformer firTransformer, Object obj) {
        return transformBody((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public /* bridge */ /* synthetic */ FirConstructor transformContractDescription(FirTransformer firTransformer, Object obj) {
        return transformContractDescription((FirTransformer<? super Object>) firTransformer, obj);
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirConstructor transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
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
    public FirTypeRef getReturnTypeRef() {
        return this.returnTypeRef;
    }

    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirReceiverParameter getReceiverParameter() {
        return this.receiverParameter;
    }

    public void setReceiverParameter(FirReceiverParameter firReceiverParameter) {
        this.receiverParameter = firReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    /* JADX INFO: renamed from: getContextReceivers-5e3fPpI, reason: not valid java name */
    public List<T> m6838getContextReceivers5e3fPpI() {
        return this.contextReceivers;
    }

    /* JADX INFO: renamed from: setContextReceivers-GqUYU-s, reason: not valid java name */
    public void m6840setContextReceiversGqUYUs(List<T> list) {
        this.contextReceivers = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public List<FirValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public FirContractDescription getContractDescription() {
        return this.contractDescription;
    }

    public void setContractDescription(FirContractDescription firContractDescription) {
        Intrinsics.checkNotNullParameter(firContractDescription, "<set-?>");
        this.contractDescription = firContractDescription;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6837getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6839setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirConstructorSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor
    public FirDelegatedConstructorCall getDelegatedConstructor() {
        return this.delegatedConstructor;
    }

    public void setDelegatedConstructor(FirDelegatedConstructorCall firDelegatedConstructorCall) {
        this.delegatedConstructor = firDelegatedConstructorCall;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public FirBlock getBody() {
        return this.body;
    }

    public void setBody(FirBlock firBlock) {
        this.body = firBlock;
    }

    private FirErrorConstructor(KtSourceElement ktSourceElement, FirResolvePhase resolvePhase, FirModuleData moduleData, FirDeclarationOrigin origin, FirDeclarationAttributes attributes, List<FirTypeParameterRef> typeParameters, FirDeclarationStatus status, FirTypeRef returnTypeRef, FirReceiverParameter firReceiverParameter, DeprecationsProvider deprecationsProvider, DeserializedContainerSource deserializedContainerSource, ConeSimpleKotlinType coneSimpleKotlinType, List<T> list, List<FirValueParameter> valueParameters, FirContractDescription contractDescription, List<T> list2, FirConstructorSymbol symbol, FirDelegatedConstructorCall firDelegatedConstructorCall, FirBlock firBlock) {
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(returnTypeRef, "returnTypeRef");
        Intrinsics.checkNotNullParameter(deprecationsProvider, "deprecationsProvider");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.origin = origin;
        this.attributes = attributes;
        this.typeParameters = typeParameters;
        this.status = status;
        this.returnTypeRef = returnTypeRef;
        this.receiverParameter = firReceiverParameter;
        this.deprecationsProvider = deprecationsProvider;
        this.containerSource = deserializedContainerSource;
        this.dispatchReceiverType = coneSimpleKotlinType;
        this.contextReceivers = list;
        this.valueParameters = valueParameters;
        this.contractDescription = contractDescription;
        this.annotations = list2;
        this.symbol = symbol;
        this.delegatedConstructor = firDelegatedConstructorCall;
        this.body = firBlock;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return this.controlFlowGraphReference;
    }

    public void setControlFlowGraphReference(FirControlFlowGraphReference firControlFlowGraphReference) {
        this.controlFlowGraphReference = firControlFlowGraphReference;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getTypeParameters().iterator();
        while (it.hasNext()) {
            ((FirTypeParameterRef) it.next()).accept(visitor, data);
        }
        getStatus().accept(visitor, data);
        getReturnTypeRef().accept(visitor, data);
        FirReceiverParameter receiverParameter = getReceiverParameter();
        if (receiverParameter != null) {
            receiverParameter.accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6838getContextReceivers5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirContextReceiver) it2.next()).accept(visitor, data);
        }
        FirControlFlowGraphReference controlFlowGraphReference = getControlFlowGraphReference();
        if (controlFlowGraphReference != null) {
            controlFlowGraphReference.accept(visitor, data);
        }
        Iterator<T> it3 = getValueParameters().iterator();
        while (it3.hasNext()) {
            ((FirValueParameter) it3.next()).accept(visitor, data);
        }
        getContractDescription().accept(visitor, data);
        Iterator<T> it4 = MutableOrEmptyList.m6756boximpl(m6837getAnnotations5e3fPpI()).iterator();
        while (it4.hasNext()) {
            ((FirAnnotation) it4.next()).accept(visitor, data);
        }
        FirDelegatedConstructorCall delegatedConstructor = getDelegatedConstructor();
        if (delegatedConstructor != null) {
            delegatedConstructor.accept(visitor, data);
        }
        FirBlock body = getBody();
        if (body != null) {
            body.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirErrorConstructor transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformTypeParameters((FirTransformer) transformer, (Object) data);
        transformStatus((FirTransformer) transformer, (Object) data);
        transformReturnTypeRef((FirTransformer) transformer, (Object) data);
        transformReceiverParameter((FirTransformer) transformer, (Object) data);
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6838getContextReceivers5e3fPpI(), transformer, data);
        FirControlFlowGraphReference controlFlowGraphReference = getControlFlowGraphReference();
        setControlFlowGraphReference(controlFlowGraphReference != null ? (FirControlFlowGraphReference) controlFlowGraphReference.transform(transformer, data) : null);
        transformValueParameters((FirTransformer) transformer, (Object) data);
        transformContractDescription((FirTransformer) transformer, (Object) data);
        transformAnnotations((FirTransformer) transformer, (Object) data);
        transformDelegatedConstructor((FirTransformer) transformer, (Object) data);
        transformBody((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirErrorConstructor transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getTypeParameters(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirErrorConstructor transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setStatus((FirDeclarationStatus) getStatus().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirErrorConstructor transformReturnTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setReturnTypeRef((FirTypeRef) getReturnTypeRef().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirErrorConstructor transformReceiverParameter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirReceiverParameter receiverParameter = getReceiverParameter();
        setReceiverParameter(receiverParameter != null ? (FirReceiverParameter) receiverParameter.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public <D> FirErrorConstructor transformValueParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getValueParameters(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public <D> FirErrorConstructor transformContractDescription(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setContractDescription((FirContractDescription) getContractDescription().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirErrorConstructor transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6837getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor
    public <D> FirErrorConstructor transformDelegatedConstructor(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirDelegatedConstructorCall delegatedConstructor = getDelegatedConstructor();
        setDelegatedConstructor(delegatedConstructor != null ? (FirDelegatedConstructorCall) delegatedConstructor.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public <D> FirErrorConstructor transformBody(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirBlock body = getBody();
        setBody(body != null ? (FirBlock) body.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        setStatus(newStatus);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReturnTypeRef(FirTypeRef newReturnTypeRef) {
        Intrinsics.checkNotNullParameter(newReturnTypeRef, "newReturnTypeRef");
        setReturnTypeRef(newReturnTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReceiverParameter(FirReceiverParameter newReceiverParameter) {
        setReceiverParameter(newReceiverParameter);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
        setDeprecationsProvider(newDeprecationsProvider);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers) {
        Intrinsics.checkNotNullParameter(newContextReceivers, "newContextReceivers");
        m6840setContextReceiversGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newContextReceivers));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
        setControlFlowGraphReference(newControlFlowGraphReference);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public void replaceValueParameters(List<? extends FirValueParameter> newValueParameters) {
        Intrinsics.checkNotNullParameter(newValueParameters, "newValueParameters");
        getValueParameters().clear();
        getValueParameters().addAll(newValueParameters);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public void replaceContractDescription(FirContractDescription newContractDescription) {
        Intrinsics.checkNotNullParameter(newContractDescription, "newContractDescription");
        setContractDescription(newContractDescription);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6839setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor
    public void replaceDelegatedConstructor(FirDelegatedConstructorCall newDelegatedConstructor) {
        setDelegatedConstructor(newDelegatedConstructor);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirConstructor, org.jetbrains.kotlin.fir.declarations.FirFunction
    public void replaceBody(FirBlock newBody) {
        setBody(newBody);
    }
}
