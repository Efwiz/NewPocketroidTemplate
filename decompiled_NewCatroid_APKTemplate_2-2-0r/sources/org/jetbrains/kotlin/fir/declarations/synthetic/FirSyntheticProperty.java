package org.jetbrains.kotlin.fir.declarations.synthetic;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirPropertyBodyResolveState;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirDelegateFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSyntheticProperty.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000à\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J5\u0010a\u001a\u00020b\"\u0004\b\u0000\u0010c\"\u0004\b\u0001\u0010d2\u0012\u0010e\u001a\u000e\u0012\u0004\u0012\u0002Hc\u0012\u0004\u0012\u0002Hd0f2\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0002\u0010hJ\b\u0010i\u001a\u00020jH\u0002J\u0016\u0010k\u001a\u00020b2\f\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0016J\u0010\u0010m\u001a\u00020b2\u0006\u0010n\u001a\u00020\"H\u0016J\u0016\u0010o\u001a\u00020b2\f\u0010p\u001a\b\u0012\u0004\u0012\u00020*0\u0017H\u0016J\u0012\u0010q\u001a\u00020b2\b\u0010r\u001a\u0004\u0018\u00010-H\u0016J\u0012\u0010s\u001a\u00020b2\b\u0010t\u001a\u0004\u0018\u000101H\u0016J\u0010\u0010u\u001a\u00020b2\u0006\u0010v\u001a\u00020\u0014H\u0016J\u0012\u0010w\u001a\u00020b2\b\u0010x\u001a\u0004\u0018\u00010yH\u0016J\u0012\u0010z\u001a\u00020b2\b\u0010{\u001a\u0004\u0018\u000101H\u0016J\u0012\u0010|\u001a\u00020b2\b\u0010}\u001a\u0004\u0018\u00010NH\u0016J\u0010\u0010~\u001a\u00020b2\u0006\u0010\u007f\u001a\u00020RH\u0016J\u0014\u0010\u0080\u0001\u001a\u00020b2\t\u0010\u0081\u0001\u001a\u0004\u0018\u00010yH\u0016J\u0012\u0010\u0082\u0001\u001a\u00020b2\u0007\u0010\u0083\u0001\u001a\u00020\u000bH\u0016J-\u0010\u0084\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0088\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0089\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008a\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u008b\u0001J-\u0010\u008c\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u008b\u0001J-\u0010\u008d\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008e\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008f\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0090\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0091\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0092\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0093\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0094\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010d2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002Hd0\u0086\u00012\u0006\u0010g\u001a\u0002HdH\u0016¢\u0006\u0003\u0010\u008b\u0001R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0016\u0010%\u001a\u0004\u0018\u00010&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u001a\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u001aR\u0016\u0010,\u001a\u0004\u0018\u00010-X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0016\u00100\u001a\u0004\u0018\u0001018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u00103R\u0016\u00104\u001a\u0004\u0018\u0001058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u0016\u0010:\u001a\u0004\u0018\u00010;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?R\u0016\u0010@\u001a\u0004\u0018\u0001018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u00103R\u0014\u0010B\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0014\u0010D\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010CR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010CR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0014\u0010I\u001a\u00020J8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0016\u0010M\u001a\u0004\u0018\u00010N8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bO\u0010PR\u0014\u0010Q\u001a\u00020R8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bU\u0010?R\u0016\u0010V\u001a\u0004\u0018\u00010W8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bX\u0010YR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010[R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010]R\u001a\u0010^\u001a\b\u0012\u0004\u0012\u00020_0\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b`\u0010\u001a¨\u0006\u0095\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "isVar", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticPropertyAccessor;", "setter", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "(Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/name/Name;ZLorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticPropertyAccessor;Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticPropertyAccessor;Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "bodyResolveState", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyBodyResolveState;", "getBodyResolveState", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyBodyResolveState;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "delegateFieldSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;", "getDelegateFieldSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticPropertyAccessor;", "initializer", "getInitializer", "isLocal", "()Z", "isVal", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSetter", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "getTypeParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "notSupported", "", "replaceAnnotations", "newAnnotations", "replaceBodyResolveState", "newBodyResolveState", "replaceContextReceivers", "newContextReceivers", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDelegate", "newDelegate", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceGetter", "newGetter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "replaceInitializer", "newInitializer", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceSetter", "newSetter", "replaceStatus", "newStatus", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticProperty;", "transformBackingField", "transformChildren", "transformContextReceivers", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "transformDelegate", "transformGetter", "transformInitializer", "transformOtherChildren", "transformReceiverParameter", "transformReturnTypeRef", "transformSetter", "transformStatus", "transformTypeParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSyntheticProperty extends FirProperty {
    private final FirDeclarationAttributes attributes;
    private final FirBackingField backingField;
    private final FirControlFlowGraphReference controlFlowGraphReference;
    private final DeprecationsProvider deprecationsProvider;
    private final FirSyntheticPropertyAccessor getter;
    private final boolean isVar;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirSyntheticPropertyAccessor setter;
    private final FirDeclarationStatus status;
    private final FirSyntheticPropertySymbol symbol;

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeserializedContainerSource getContainerSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getDelegate() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty
    public FirDelegateFieldSymbol getDelegateFieldSymbol() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirReceiverParameter getReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty
    public boolean isLocal() {
        return false;
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirProperty transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirVariable transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirProperty transformBackingField(FirTransformer firTransformer, Object obj) {
        return transformBackingField((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformBackingField(FirTransformer firTransformer, Object obj) {
        return transformBackingField((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformDelegate(FirTransformer firTransformer, Object obj) {
        return transformDelegate((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirProperty transformGetter(FirTransformer firTransformer, Object obj) {
        return transformGetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformGetter(FirTransformer firTransformer, Object obj) {
        return transformGetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirProperty transformInitializer(FirTransformer firTransformer, Object obj) {
        return transformInitializer((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformInitializer(FirTransformer firTransformer, Object obj) {
        return transformInitializer((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirProperty transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirProperty transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirVariable transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirProperty transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirVariable transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirProperty transformSetter(FirTransformer firTransformer, Object obj) {
        return transformSetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformSetter(FirTransformer firTransformer, Object obj) {
        return transformSetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirProperty transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirVariable transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformTypeParameters(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirVariable transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVar, reason: from getter */
    public boolean getIsVar() {
        return this.isVar;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirSyntheticPropertySymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return this.status;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirSyntheticPropertyAccessor getGetter() {
        return this.getter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirSyntheticPropertyAccessor getSetter() {
        return this.setter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirBackingField getBackingField() {
        return this.backingField;
    }

    public /* synthetic */ FirSyntheticProperty(FirModuleData firModuleData, Name name2, boolean z, FirSyntheticPropertySymbol firSyntheticPropertySymbol, FirDeclarationStatus firDeclarationStatus, FirResolvePhase firResolvePhase, FirSyntheticPropertyAccessor firSyntheticPropertyAccessor, FirSyntheticPropertyAccessor firSyntheticPropertyAccessor2, FirBackingField firBackingField, DeprecationsProvider deprecationsProvider, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firModuleData, name2, z, firSyntheticPropertySymbol, firDeclarationStatus, firResolvePhase, firSyntheticPropertyAccessor, (i & 128) != 0 ? null : firSyntheticPropertyAccessor2, (i & 256) != 0 ? null : firBackingField, (i & 512) != 0 ? UnresolvedDeprecationProvider.INSTANCE : deprecationsProvider);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    public FirSyntheticProperty(FirModuleData moduleData, Name name2, boolean z, FirSyntheticPropertySymbol symbol, FirDeclarationStatus status, FirResolvePhase resolvePhase, FirSyntheticPropertyAccessor getter, FirSyntheticPropertyAccessor firSyntheticPropertyAccessor, FirBackingField firBackingField, DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(getter, "getter");
        Intrinsics.checkNotNullParameter(deprecationsProvider, "deprecationsProvider");
        this.moduleData = moduleData;
        this.name = name2;
        this.isVar = z;
        this.symbol = symbol;
        this.status = status;
        this.getter = getter;
        this.setter = firSyntheticPropertyAccessor;
        this.backingField = firBackingField;
        this.deprecationsProvider = deprecationsProvider;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
        this.attributes = new FirDeclarationAttributes();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirTypeRef getReturnTypeRef() {
        return getGetter().getReturnTypeRef();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return getGetter().getDispatchReceiverType();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return FirDeclarationOrigin.Synthetic.JavaProperty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVal */
    public boolean getIsVal() {
        return !getIsVar();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameter> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return this.controlFlowGraphReference;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty
    public FirPropertyBodyResolveState getBodyResolveState() {
        return FirPropertyBodyResolveState.EVERYTHING_RESOLVED;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public List<FirContextReceiver> getContextReceivers() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getReturnTypeRef().accept(visitor, data);
        getStatus().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirSyntheticProperty transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirSyntheticProperty transformReturnTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirSyntheticProperty transformReceiverParameter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirSyntheticProperty transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirSyntheticProperty transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirSyntheticProperty transformInitializer(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirSyntheticProperty transformGetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirSyntheticProperty transformSetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirSyntheticProperty transformBackingField(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirProperty transformDelegate(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirSyntheticProperty transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirProperty transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty
    public <D> FirProperty transformContextReceivers(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReturnTypeRef(FirTypeRef newReturnTypeRef) {
        Intrinsics.checkNotNullParameter(newReturnTypeRef, "newReturnTypeRef");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReceiverParameter(FirReceiverParameter newReceiverParameter) {
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceInitializer(FirExpression newInitializer) {
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceDelegate(FirExpression newDelegate) {
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty
    public void replaceBodyResolveState(FirPropertyBodyResolveState newBodyResolveState) {
        Intrinsics.checkNotNullParameter(newBodyResolveState, "newBodyResolveState");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceGetter(FirPropertyAccessor newGetter) {
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceSetter(FirPropertyAccessor newSetter) {
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers) {
        Intrinsics.checkNotNullParameter(newContextReceivers, "newContextReceivers");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        notSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        notSupported();
        throw null;
    }

    private final Void notSupported() {
        throw new IllegalStateException("Transformation of synthetic property isn't supported".toString());
    }
}
