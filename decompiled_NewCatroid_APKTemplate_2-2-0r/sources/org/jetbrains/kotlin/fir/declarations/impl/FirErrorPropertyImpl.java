package org.jetbrains.kotlin.fir.declarations.impl;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
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
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirErrorProperty;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.symbols.impl.FirErrorPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirErrorTypeRefImpl;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirErrorPropertyImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\r\b\u0000\u0018\u00002\u00020\u0001B\u0089\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0013\u0012\u0006\u0010\u001b\u001a\u00020\u001c\u0012\u0006\u0010\u001d\u001a\u00020\u001e¢\u0006\u0002\u0010\u001fJ5\u0010e\u001a\u00020f\"\u0004\b\u0000\u0010g\"\u0004\b\u0001\u0010h2\u0012\u0010i\u001a\u000e\u0012\u0004\u0012\u0002Hg\u0012\u0004\u0012\u0002Hh0j2\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0002\u0010lJ\u0016\u0010m\u001a\u00020f2\f\u0010n\u001a\b\u0012\u0004\u0012\u00020\u001a0bH\u0016J\u0016\u0010o\u001a\u00020f2\f\u0010p\u001a\b\u0012\u0004\u0012\u00020\u00140bH\u0016J\u0012\u0010q\u001a\u00020f2\b\u0010r\u001a\u0004\u0018\u000100H\u0016J\u0010\u0010s\u001a\u00020f2\u0006\u0010t\u001a\u00020\rH\u0016J\u0012\u0010u\u001a\u00020f2\b\u0010v\u001a\u0004\u0018\u00010<H\u0016J\u0012\u0010w\u001a\u00020f2\b\u0010x\u001a\u0004\u0018\u000100H\u0016J\u0012\u0010y\u001a\u00020f2\b\u0010z\u001a\u0004\u0018\u00010LH\u0016J\u0010\u0010{\u001a\u00020f2\u0006\u0010|\u001a\u00020PH\u0016J\u0012\u0010}\u001a\u00020f2\b\u0010~\u001a\u0004\u0018\u00010<H\u0016J\u0011\u0010\u007f\u001a\u00020f2\u0007\u0010\u0080\u0001\u001a\u00020ZH\u0016J-\u0010\u0081\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u0085\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u0086\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u0087\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u0088\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u0089\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u008a\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u008b\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u008c\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u008d\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u008e\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001J-\u0010\u008f\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010h2\u000e\u0010\u0082\u0001\u001a\t\u0012\u0004\u0012\u0002Hh0\u0083\u00012\u0006\u0010k\u001a\u0002HhH\u0016¢\u0006\u0003\u0010\u0084\u0001R(\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0013X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010$\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R(\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010$\u001a\u0004\b-\u0010!\"\u0004\b.\u0010#R\u0016\u0010/\u001a\u0004\u0018\u0001008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u00102R\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0016\u0010;\u001a\u0004\u0018\u00010<8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0016\u0010?\u001a\u0004\u0018\u0001008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b@\u00102R\u0014\u0010A\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u0010CR\u0014\u0010D\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010CR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u0014\u0010\u0015\u001a\u00020\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0016\u0010K\u001a\u0004\u0018\u00010L8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010NR\u001a\u0010O\u001a\u00020PX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u0016\u0010U\u001a\u0004\u0018\u00010<8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bV\u0010>R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bW\u0010XR\u001a\u0010Y\u001a\u00020ZX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010\\\"\u0004\b]\u0010^R\u0014\u0010\u001d\u001a\u00020\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b_\u0010`R\u001a\u0010a\u001a\b\u0012\u0004\u0012\u00020c0b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bd\u0010!\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0090\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirErrorPropertyImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "name", "Lorg/jetbrains/kotlin/name/Name;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirErrorPropertySymbol;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Ljava/util/List;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;Ljava/util/List;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;Lorg/jetbrains/kotlin/fir/symbols/impl/FirErrorPropertySymbol;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "setBackingField", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;)V", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContextReceivers-5e3fPpI", "setContextReceivers-GqUYU-s", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "initializer", "getInitializer", "isVal", "", "()Z", "isVar", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "setter", "getSetter", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirErrorPropertySymbol;", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceContextReceivers", "newContextReceivers", "replaceDelegate", "newDelegate", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceGetter", "newGetter", "replaceInitializer", "newInitializer", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceSetter", "newSetter", "replaceStatus", "newStatus", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirErrorPropertyImpl;", "transformBackingField", "transformChildren", "transformDelegate", "transformGetter", "transformInitializer", "transformOtherChildren", "transformReceiverParameter", "transformReturnTypeRef", "transformSetter", "transformStatus", "transformTypeParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorPropertyImpl extends FirErrorProperty {
    private List<T> annotations;
    private final FirDeclarationAttributes attributes;
    private FirBackingField backingField;
    private final DeserializedContainerSource containerSource;
    private List<T> contextReceivers;
    private DeprecationsProvider deprecationsProvider;
    private final ConeDiagnostic diagnostic;
    private final ConeSimpleKotlinType dispatchReceiverType;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirDeclarationOrigin origin;
    private FirTypeRef returnTypeRef;
    private final KtSourceElement source;
    private FirDeclarationStatus status;
    private final FirErrorPropertySymbol symbol;

    public /* synthetic */ FirErrorPropertyImpl(KtSourceElement ktSourceElement, FirResolvePhase firResolvePhase, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirDeclarationAttributes firDeclarationAttributes, DeprecationsProvider deprecationsProvider, DeserializedContainerSource deserializedContainerSource, ConeSimpleKotlinType coneSimpleKotlinType, List list, Name name2, FirBackingField firBackingField, List list2, ConeDiagnostic coneDiagnostic, FirErrorPropertySymbol firErrorPropertySymbol, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firResolvePhase, firModuleData, firDeclarationOrigin, firDeclarationAttributes, deprecationsProvider, deserializedContainerSource, coneSimpleKotlinType, list, name2, firBackingField, list2, coneDiagnostic, firErrorPropertySymbol);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getDelegate() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirPropertyAccessor getGetter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirReceiverParameter getReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirPropertyAccessor getSetter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVal */
    public boolean getIsVal() {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVar */
    public boolean getIsVar() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceDelegate(FirExpression newDelegate) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceGetter(FirPropertyAccessor newGetter) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceInitializer(FirExpression newInitializer) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReceiverParameter(FirReceiverParameter newReceiverParameter) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceSetter(FirPropertyAccessor newSetter) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirErrorPropertyImpl transformDelegate(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirErrorPropertyImpl transformGetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirErrorPropertyImpl transformInitializer(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirErrorPropertyImpl transformReceiverParameter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirErrorPropertyImpl transformSetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirErrorPropertyImpl transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6845getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ List getContextReceivers() {
        return MutableOrEmptyList.m6756boximpl(m6846getContextReceivers5e3fPpI());
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirErrorProperty transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirErrorProperty transformBackingField(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirErrorProperty transformDelegate(FirTransformer firTransformer, Object obj) {
        return transformDelegate((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformDelegate(FirTransformer firTransformer, Object obj) {
        return transformDelegate((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirErrorProperty transformGetter(FirTransformer firTransformer, Object obj) {
        return transformGetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformGetter(FirTransformer firTransformer, Object obj) {
        return transformGetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirErrorProperty transformInitializer(FirTransformer firTransformer, Object obj) {
        return transformInitializer((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformInitializer(FirTransformer firTransformer, Object obj) {
        return transformInitializer((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirErrorProperty transformOtherChildren(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirErrorProperty transformReceiverParameter(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirErrorProperty transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirVariable transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirErrorProperty transformSetter(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirErrorProperty transformStatus(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirErrorProperty transformTypeParameters(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    /* JADX INFO: renamed from: getContextReceivers-5e3fPpI, reason: not valid java name */
    public List<T> m6846getContextReceivers5e3fPpI() {
        return this.contextReceivers;
    }

    /* JADX INFO: renamed from: setContextReceivers-GqUYU-s, reason: not valid java name */
    public void m6848setContextReceiversGqUYUs(List<T> list) {
        this.contextReceivers = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirBackingField getBackingField() {
        return this.backingField;
    }

    public void setBackingField(FirBackingField firBackingField) {
        this.backingField = firBackingField;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6845getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6847setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public ConeDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirErrorPropertySymbol getSymbol() {
        return this.symbol;
    }

    private FirErrorPropertyImpl(KtSourceElement ktSourceElement, FirResolvePhase resolvePhase, FirModuleData moduleData, FirDeclarationOrigin origin, FirDeclarationAttributes attributes, DeprecationsProvider deprecationsProvider, DeserializedContainerSource deserializedContainerSource, ConeSimpleKotlinType coneSimpleKotlinType, List<T> list, Name name2, FirBackingField firBackingField, List<T> list2, ConeDiagnostic diagnostic, FirErrorPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(deprecationsProvider, "deprecationsProvider");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.origin = origin;
        this.attributes = attributes;
        this.deprecationsProvider = deprecationsProvider;
        this.containerSource = deserializedContainerSource;
        this.dispatchReceiverType = coneSimpleKotlinType;
        this.contextReceivers = list;
        this.name = name2;
        this.backingField = firBackingField;
        this.annotations = list2;
        this.diagnostic = diagnostic;
        this.symbol = symbol;
        this.status = FirResolvedDeclarationStatusImpl.INSTANCE.getDEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS();
        this.returnTypeRef = new FirErrorTypeRefImpl(null, null, getDiagnostic(), false, null, 16, null);
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameterRef> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return this.status;
    }

    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirTypeRef getReturnTypeRef() {
        return this.returnTypeRef;
    }

    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getStatus().accept(visitor, data);
        getReturnTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6846getContextReceivers5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirContextReceiver) it.next()).accept(visitor, data);
        }
        FirBackingField backingField = getBackingField();
        if (backingField != null) {
            backingField.accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6845getAnnotations5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirAnnotation) it2.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirErrorPropertyImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformStatus((FirTransformer) transformer, (Object) data);
        transformReturnTypeRef((FirTransformer) transformer, (Object) data);
        transformBackingField((FirTransformer) transformer, (Object) data);
        transformOtherChildren((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirErrorPropertyImpl transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setStatus((FirDeclarationStatus) getStatus().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirErrorPropertyImpl transformReturnTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setReturnTypeRef((FirTypeRef) getReturnTypeRef().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirErrorPropertyImpl transformBackingField(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirBackingField backingField = getBackingField();
        setBackingField(backingField != null ? (FirBackingField) backingField.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirErrorPropertyImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6845getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirErrorPropertyImpl transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6846getContextReceivers5e3fPpI(), transformer, data);
        transformAnnotations((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        setStatus(newStatus);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReturnTypeRef(FirTypeRef newReturnTypeRef) {
        Intrinsics.checkNotNullParameter(newReturnTypeRef, "newReturnTypeRef");
        setReturnTypeRef(newReturnTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
        setDeprecationsProvider(newDeprecationsProvider);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers) {
        Intrinsics.checkNotNullParameter(newContextReceivers, "newContextReceivers");
        m6848setContextReceiversGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newContextReceivers));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirErrorProperty, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6847setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }
}
