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
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirImplementationDetail;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirJavaField.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0095\u0001\b\u0007\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0012\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u0015\u0012\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019\u0012\u000e\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001d0\u001c\u0012\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f\u0012\u0006\u0010 \u001a\u00020!¢\u0006\u0002\u0010\"J5\u0010i\u001a\u00020j\"\u0004\b\u0000\u0010k\"\u0004\b\u0001\u0010l2\u0012\u0010m\u001a\u000e\u0012\u0004\u0012\u0002Hk\u0012\u0004\u0012\u0002Hl0n2\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0002\u0010pJ\u0016\u0010q\u001a\u00020j2\f\u0010r\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0016J\u0016\u0010s\u001a\u00020j2\f\u0010t\u001a\b\u0012\u0004\u0012\u0002050\u0016H\u0016J\u0012\u0010u\u001a\u00020j2\b\u0010v\u001a\u0004\u0018\u000108H\u0016J\u0012\u0010w\u001a\u00020j2\b\u0010x\u001a\u0004\u0018\u00010\u001dH\u0016J\u0010\u0010y\u001a\u00020j2\u0006\u0010z\u001a\u00020?H\u0016J\u0012\u0010{\u001a\u00020j2\b\u0010|\u001a\u0004\u0018\u00010FH\u0016J\u0012\u0010}\u001a\u00020j2\b\u0010~\u001a\u0004\u0018\u00010\u001dH\u0016J\u0013\u0010\u007f\u001a\u00020j2\t\u0010\u0080\u0001\u001a\u0004\u0018\u00010WH\u0016J\u0012\u0010\u0081\u0001\u001a\u00020j2\u0007\u0010\u0082\u0001\u001a\u00020\u000fH\u0016J\u0014\u0010\u0083\u0001\u001a\u00020j2\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010FH\u0016J\u0012\u0010\u0085\u0001\u001a\u00020j2\u0007\u0010\u0086\u0001\u001a\u00020\u0011H\u0016J-\u0010\u0087\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008a\u0001J-\u0010\u008b\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u008d\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008a\u0001J-\u0010\u008e\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u008f\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0090\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0091\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0092\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0093\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0094\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0095\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008a\u0001J-\u0010\u0096\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u0089\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001R!\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00170\u00168VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b$\u0010%R\u0014\u0010 \u001a\u00020!X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0016\u0010*\u001a\u0004\u0018\u00010+X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u001c\u0010.\u001a\u0004\u0018\u00010/X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u0010%R\u0016\u00107\u001a\u0004\u0018\u0001088VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:R\u0016\u0010;\u001a\u0004\u0018\u00010\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u001b\u0010>\u001a\u00020?8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bB\u0010'\u001a\u0004\b@\u0010AR\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0016\u0010E\u001a\u0004\u0018\u00010F8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0016\u0010I\u001a\u0004\u0018\u00010\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010=R\u0014\u0010K\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010LR.\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001d0\u001c2\u000e\u0010M\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001d0\u001c@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\bN\u0010OR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010QR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010SR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010UR\u0016\u0010V\u001a\u0004\u0018\u00010W8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bX\u0010YR\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]R\u0016\u0010^\u001a\u0004\u0018\u00010F8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b_\u0010HR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u0010aR\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bf\u0010gR\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bh\u0010%¨\u0006\u0097\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaField;", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "isVar", "", "annotationBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "lazyInitializer", "Lkotlin/Lazy;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;ZLkotlin/jvm/functions/Function0;Ljava/util/List;Lkotlin/Lazy;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "annotations", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "Lkotlin/Lazy;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "deprecationsProvider$delegate", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "initializer", "getInitializer", "isVal", "()Z", "<set-?>", "getLazyInitializer$java", "()Lkotlin/Lazy;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "setter", "getSetter", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "getTypeParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceContextReceivers", "newContextReceivers", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDelegate", "newDelegate", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceGetter", "newGetter", "replaceInitializer", "newInitializer", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceSetter", "newSetter", "replaceStatus", "newStatus", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaField;", "transformBackingField", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirField;", "transformChildren", "transformDelegate", "transformGetter", "transformInitializer", "transformOtherChildren", "transformReceiverParameter", "transformReturnTypeRef", "transformSetter", "transformStatus", "transformTypeParameters", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaField extends FirField {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final FirDeclarationAttributes attributes;
    private final FirBackingField backingField;
    private DeserializedContainerSource containerSource;

    /* JADX INFO: renamed from: deprecationsProvider$delegate, reason: from kotlin metadata */
    private final Lazy deprecationsProvider;
    private final ConeSimpleKotlinType dispatchReceiverType;
    private final boolean isVar;
    private Lazy<? extends FirExpression> lazyInitializer;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirDeclarationOrigin.Java origin;
    private FirTypeRef returnTypeRef;
    private final KtSourceElement source;
    private FirDeclarationStatus status;
    private final FirFieldSymbol symbol;
    private final List<FirTypeParameterRef> typeParameters;

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getDelegate() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirPropertyAccessor getGetter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirReceiverParameter getReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirPropertyAccessor getSetter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceDelegate(FirExpression newDelegate) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceGetter(FirPropertyAccessor newGetter) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReceiverParameter(FirReceiverParameter newReceiverParameter) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceSetter(FirPropertyAccessor newSetter) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirJavaField transformAnnotations(FirTransformer<? super D> transformer, D data) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirField transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformGetter(FirTransformer firTransformer, Object obj) {
        return transformGetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformInitializer(FirTransformer firTransformer, Object obj) {
        return transformInitializer((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformReceiverParameter(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirVariable transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformSetter(FirTransformer firTransformer, Object obj) {
        return transformSetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirField transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirMemberDeclaration transformStatus(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirVariable transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin.Java getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirFieldSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirTypeRef getReturnTypeRef() {
        return this.returnTypeRef;
    }

    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return this.status;
    }

    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVar, reason: from getter */
    public boolean getIsVar() {
        return this.isVar;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameterRef> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @FirImplementationDetail
    public FirJavaField(KtSourceElement ktSourceElement, FirModuleData moduleData, FirDeclarationOrigin.Java origin, FirFieldSymbol symbol, Name name2, FirResolvePhase resolvePhase, FirTypeRef returnTypeRef, FirDeclarationStatus status, boolean z, final Function0<? extends List<? extends FirAnnotation>> annotationBuilder, List<FirTypeParameterRef> typeParameters, Lazy<? extends FirExpression> lazyInitializer, ConeSimpleKotlinType coneSimpleKotlinType, FirDeclarationAttributes attributes) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(returnTypeRef, "returnTypeRef");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(annotationBuilder, "annotationBuilder");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(lazyInitializer, "lazyInitializer");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.origin = origin;
        this.symbol = symbol;
        this.name = name2;
        this.returnTypeRef = returnTypeRef;
        this.status = status;
        this.isVar = z;
        this.typeParameters = typeParameters;
        this.dispatchReceiverType = coneSimpleKotlinType;
        this.attributes = attributes;
        this.lazyInitializer = lazyInitializer;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
        this.annotations = LazyKt.lazy(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.declarations.FirJavaField$annotations$2
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
        this.deprecationsProvider = LazyKt.lazy(new Function0<DeprecationsProvider>() { // from class: org.jetbrains.kotlin.fir.java.declarations.FirJavaField$deprecationsProvider$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DeprecationsProvider invoke() {
                return DeprecationUtilsKt.getDeprecationsProviderFromAnnotations$default(this.this$0.getAnnotations(), this.this$0.getModuleData().getSession(), true, null, 4, null);
            }
        });
    }

    public final Lazy<FirExpression> getLazyInitializer$java() {
        return this.lazyInitializer;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVal */
    public boolean getIsVal() {
        return !getIsVar();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirBackingField getBackingField() {
        return this.backingField;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return (List) this.annotations.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getInitializer() {
        return this.lazyInitializer.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return (DeprecationsProvider) this.deprecationsProvider.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public List<FirContextReceiver> getContextReceivers() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirField transformReturnTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setReturnTypeRef((FirTypeRef) FirTransformerUtilKt.transformSingle(getReturnTypeRef(), transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirField transformGetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirField transformSetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirField transformBackingField(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirField transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        FirExpression initializer = getInitializer();
        replaceInitializer(initializer != null ? (FirExpression) FirTransformerUtilKt.transformSingle(initializer, transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirField transformReceiverParameter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getReturnTypeRef().accept(visitor, data);
        Iterator<T> it = getAnnotations().iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = getTypeParameters().iterator();
        while (it2.hasNext()) {
            ((FirTypeParameterRef) it2.next()).accept(visitor, data);
        }
        FirExpression initializer = getInitializer();
        if (initializer != null) {
            initializer.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirJavaField transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformReturnTypeRef((FirTransformer) transformer, (Object) data);
        transformTypeParameters((FirTransformer) transformer, (Object) data);
        transformOtherChildren((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirJavaField transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setStatus((FirDeclarationStatus) FirTransformerUtilKt.transformSingle(getStatus(), transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReturnTypeRef(FirTypeRef newReturnTypeRef) {
        Intrinsics.checkNotNullParameter(newReturnTypeRef, "newReturnTypeRef");
        setReturnTypeRef(newReturnTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        throw new AssertionError("Mutating annotations for FirJava* is not supported");
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceInitializer(FirExpression newInitializer) {
        this.lazyInitializer = LazyKt.lazyOf(newInitializer);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirField transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getTypeParameters(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirField transformInitializer(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirField transformDelegate(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers) {
        Intrinsics.checkNotNullParameter(newContextReceivers, "newContextReceivers");
        throw new IllegalStateException("Body cannot be replaced for FirJavaField".toString());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirField, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        setStatus(newStatus);
    }
}
