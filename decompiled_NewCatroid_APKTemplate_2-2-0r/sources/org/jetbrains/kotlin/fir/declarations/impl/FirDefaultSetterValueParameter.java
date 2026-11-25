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
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirDefaultSetterValueParameter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\r\b\u0000\u0018\u00002\u00020\u0001Bñ\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u0012\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u001a\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u0012\b\u0010\u001f\u001a\u0004\u0018\u00010 \u0012\b\u0010!\u001a\u0004\u0018\u00010 \u0012\b\u0010\"\u001a\u0004\u0018\u00010#\u0012\f\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0017\u0012\u0006\u0010&\u001a\u00020'\u0012\b\u0010(\u001a\u0004\u0018\u00010\u001a\u0012\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*\u0012\u0006\u0010+\u001a\u00020\u001d\u0012\u0006\u0010,\u001a\u00020\u001d\u0012\u0006\u0010-\u001a\u00020\u001d¢\u0006\u0002\u0010.J6\u0010y\u001a\u00020z\"\u0004\b\u0000\u0010{\"\u0004\b\u0001\u0010|2\u0012\u0010}\u001a\u000e\u0012\u0004\u0012\u0002H{\u0012\u0004\u0012\u0002H|0~2\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u0080\u0001J\u0018\u0010\u0081\u0001\u001a\u00020z2\r\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020%0vH\u0016J\u0018\u0010\u0083\u0001\u001a\u00020z2\r\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u00020\u00180vH\u0016J\u0014\u0010\u0085\u0001\u001a\u00020z2\t\u0010\u0086\u0001\u001a\u0004\u0018\u00010AH\u0016J\u0014\u0010\u0087\u0001\u001a\u00020z2\t\u0010\u0088\u0001\u001a\u0004\u0018\u00010\u001aH\u0016J\u0014\u0010\u0089\u0001\u001a\u00020z2\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u001aH\u0016J\u0012\u0010\u008b\u0001\u001a\u00020z2\u0007\u0010\u008c\u0001\u001a\u00020\u0011H\u0016J\u0014\u0010\u008d\u0001\u001a\u00020z2\t\u0010\u008e\u0001\u001a\u0004\u0018\u00010 H\u0016J\u0014\u0010\u008f\u0001\u001a\u00020z2\t\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u001aH\u0016J\u0014\u0010\u0091\u0001\u001a\u00020z2\t\u0010\u0092\u0001\u001a\u0004\u0018\u00010\u000fH\u0016J\u0012\u0010\u0093\u0001\u001a\u00020z2\u0007\u0010\u0094\u0001\u001a\u00020\rH\u0016J\u0014\u0010\u0095\u0001\u001a\u00020z2\t\u0010\u0096\u0001\u001a\u0004\u0018\u00010 H\u0016J\u0012\u0010\u0097\u0001\u001a\u00020z2\u0007\u0010\u0098\u0001\u001a\u00020nH\u0016J-\u0010\u0099\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010\u009d\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010\u009e\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010\u009f\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010 \u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010¡\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010¢\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010£\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010¤\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010¥\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010¦\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001J-\u0010§\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010|2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u0002H|0\u009b\u00012\u0006\u0010\u007f\u001a\u0002H|H\u0016¢\u0006\u0003\u0010\u009c\u0001R(\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0017X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u00103\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u001c\u0010\"\u001a\u0004\u0018\u00010#X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u0018\u0010)\u001a\u0006\u0012\u0002\b\u00030*X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=R(\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u00103\u001a\u0004\b>\u00100\"\u0004\b?\u00102R\u001c\u0010@\u001a\u0004\u0018\u00010AX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001c\u0010(\u001a\u0004\u0018\u00010\u001aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010G\"\u0004\bK\u0010IR\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010M\"\u0004\bN\u0010OR\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010QR\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010G\"\u0004\bW\u0010IR\u0014\u0010+\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010XR\u0014\u0010,\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010XR\u0014\u0010\u001e\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010XR\u0014\u0010\u001c\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010XR\u0014\u0010-\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010XR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bY\u0010ZR\u0014\u0010[\u001a\u00020\\X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b]\u0010^R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b_\u0010`R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010f\"\u0004\bg\u0010hR\u001c\u0010!\u001a\u0004\u0018\u00010 X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010S\"\u0004\bj\u0010UR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bk\u0010lR\u001a\u0010m\u001a\u00020nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR\u0014\u0010&\u001a\u00020'X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bs\u0010tR\u001a\u0010u\u001a\b\u0012\u0004\u0012\u00020w0v8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bx\u00100\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006¨\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultSetterValueParameter;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "initializer", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "isVar", "", "isVal", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "setter", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "defaultValue", "containingFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "isCrossinline", "isNoinline", "isVararg", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;ZZLorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;Ljava/util/List;Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;ZZZLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "setBackingField", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;)V", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainingFunctionSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "getContextReceivers-5e3fPpI", "setContextReceivers-GqUYU-s", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "setControlFlowGraphReference", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;)V", "getDefaultValue", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDefaultValue", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getDelegate", "setDelegate", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "setGetter", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;)V", "getInitializer", "setInitializer", "()Z", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getSetter", "setSetter", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceContextReceivers", "newContextReceivers", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDefaultValue", "newDefaultValue", "replaceDelegate", "newDelegate", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceGetter", "newGetter", "replaceInitializer", "newInitializer", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceSetter", "newSetter", "replaceStatus", "newStatus", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultSetterValueParameter;", "transformBackingField", "transformChildren", "transformDelegate", "transformGetter", "transformInitializer", "transformOtherChildren", "transformReceiverParameter", "transformReturnTypeRef", "transformSetter", "transformStatus", "transformTypeParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDefaultSetterValueParameter extends FirValueParameter {
    private List<T> annotations;
    private final FirDeclarationAttributes attributes;
    private FirBackingField backingField;
    private final DeserializedContainerSource containerSource;
    private final FirFunctionSymbol<?> containingFunctionSymbol;
    private List<T> contextReceivers;
    private FirControlFlowGraphReference controlFlowGraphReference;
    private FirExpression defaultValue;
    private FirExpression delegate;
    private DeprecationsProvider deprecationsProvider;
    private final ConeSimpleKotlinType dispatchReceiverType;
    private FirPropertyAccessor getter;
    private FirExpression initializer;
    private final boolean isCrossinline;
    private final boolean isNoinline;
    private final boolean isVal;
    private final boolean isVar;
    private final boolean isVararg;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirDeclarationOrigin origin;
    private FirReceiverParameter receiverParameter;
    private FirTypeRef returnTypeRef;
    private FirPropertyAccessor setter;
    private final KtSourceElement source;
    private FirDeclarationStatus status;
    private final FirValueParameterSymbol symbol;

    public /* synthetic */ FirDefaultSetterValueParameter(KtSourceElement ktSourceElement, FirResolvePhase firResolvePhase, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirDeclarationAttributes firDeclarationAttributes, FirTypeRef firTypeRef, FirReceiverParameter firReceiverParameter, DeprecationsProvider deprecationsProvider, DeserializedContainerSource deserializedContainerSource, ConeSimpleKotlinType coneSimpleKotlinType, List list, FirExpression firExpression, FirExpression firExpression2, boolean z, boolean z2, FirPropertyAccessor firPropertyAccessor, FirPropertyAccessor firPropertyAccessor2, FirBackingField firBackingField, List list2, FirValueParameterSymbol firValueParameterSymbol, FirExpression firExpression3, FirFunctionSymbol firFunctionSymbol, boolean z3, boolean z4, boolean z5, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firResolvePhase, firModuleData, firDeclarationOrigin, firDeclarationAttributes, firTypeRef, firReceiverParameter, deprecationsProvider, deserializedContainerSource, coneSimpleKotlinType, list, firExpression, firExpression2, z, z2, firPropertyAccessor, firPropertyAccessor2, firBackingField, list2, firValueParameterSymbol, firExpression3, firFunctionSymbol, z3, z4, z5);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirDefaultSetterValueParameter transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6829getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ List getContextReceivers() {
        return MutableOrEmptyList.m6756boximpl(m6830getContextReceivers5e3fPpI());
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirValueParameter transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirValueParameter transformBackingField(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirValueParameter transformDelegate(FirTransformer firTransformer, Object obj) {
        return transformDelegate((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformDelegate(FirTransformer firTransformer, Object obj) {
        return transformDelegate((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirValueParameter transformGetter(FirTransformer firTransformer, Object obj) {
        return transformGetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformGetter(FirTransformer firTransformer, Object obj) {
        return transformGetter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirValueParameter transformInitializer(FirTransformer firTransformer, Object obj) {
        return transformInitializer((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirVariable transformInitializer(FirTransformer firTransformer, Object obj) {
        return transformInitializer((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirValueParameter transformOtherChildren(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirValueParameter transformReceiverParameter(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirValueParameter transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirVariable transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public /* bridge */ /* synthetic */ FirValueParameter transformSetter(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirValueParameter transformStatus(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirValueParameter transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirVariable transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirTypeRef getReturnTypeRef() {
        return this.returnTypeRef;
    }

    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirReceiverParameter getReceiverParameter() {
        return this.receiverParameter;
    }

    public void setReceiverParameter(FirReceiverParameter firReceiverParameter) {
        this.receiverParameter = firReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    /* JADX INFO: renamed from: getContextReceivers-5e3fPpI, reason: not valid java name */
    public List<T> m6830getContextReceivers5e3fPpI() {
        return this.contextReceivers;
    }

    /* JADX INFO: renamed from: setContextReceivers-GqUYU-s, reason: not valid java name */
    public void m6832setContextReceiversGqUYUs(List<T> list) {
        this.contextReceivers = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getInitializer() {
        return this.initializer;
    }

    public void setInitializer(FirExpression firExpression) {
        this.initializer = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getDelegate() {
        return this.delegate;
    }

    public void setDelegate(FirExpression firExpression) {
        this.delegate = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVar, reason: from getter */
    public boolean getIsVar() {
        return this.isVar;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVal, reason: from getter */
    public boolean getIsVal() {
        return this.isVal;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirPropertyAccessor getGetter() {
        return this.getter;
    }

    public void setGetter(FirPropertyAccessor firPropertyAccessor) {
        this.getter = firPropertyAccessor;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirPropertyAccessor getSetter() {
        return this.setter;
    }

    public void setSetter(FirPropertyAccessor firPropertyAccessor) {
        this.setter = firPropertyAccessor;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirBackingField getBackingField() {
        return this.backingField;
    }

    public void setBackingField(FirBackingField firBackingField) {
        this.backingField = firBackingField;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6829getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6831setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirValueParameterSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter
    public FirExpression getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(FirExpression firExpression) {
        this.defaultValue = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter
    public FirFunctionSymbol<?> getContainingFunctionSymbol() {
        return this.containingFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter
    /* JADX INFO: renamed from: isCrossinline, reason: from getter */
    public boolean getIsCrossinline() {
        return this.isCrossinline;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter
    /* JADX INFO: renamed from: isNoinline, reason: from getter */
    public boolean getIsNoinline() {
        return this.isNoinline;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter
    /* JADX INFO: renamed from: isVararg, reason: from getter */
    public boolean getIsVararg() {
        return this.isVararg;
    }

    private FirDefaultSetterValueParameter(KtSourceElement ktSourceElement, FirResolvePhase resolvePhase, FirModuleData moduleData, FirDeclarationOrigin origin, FirDeclarationAttributes attributes, FirTypeRef returnTypeRef, FirReceiverParameter firReceiverParameter, DeprecationsProvider deprecationsProvider, DeserializedContainerSource deserializedContainerSource, ConeSimpleKotlinType coneSimpleKotlinType, List<T> list, FirExpression firExpression, FirExpression firExpression2, boolean z, boolean z2, FirPropertyAccessor firPropertyAccessor, FirPropertyAccessor firPropertyAccessor2, FirBackingField firBackingField, List<T> list2, FirValueParameterSymbol symbol, FirExpression firExpression3, FirFunctionSymbol<?> containingFunctionSymbol, boolean z3, boolean z4, boolean z5) {
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(returnTypeRef, "returnTypeRef");
        Intrinsics.checkNotNullParameter(deprecationsProvider, "deprecationsProvider");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(containingFunctionSymbol, "containingFunctionSymbol");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.origin = origin;
        this.attributes = attributes;
        this.returnTypeRef = returnTypeRef;
        this.receiverParameter = firReceiverParameter;
        this.deprecationsProvider = deprecationsProvider;
        this.containerSource = deserializedContainerSource;
        this.dispatchReceiverType = coneSimpleKotlinType;
        this.contextReceivers = list;
        this.initializer = firExpression;
        this.delegate = firExpression2;
        this.isVar = z;
        this.isVal = z2;
        this.getter = firPropertyAccessor;
        this.setter = firPropertyAccessor2;
        this.backingField = firBackingField;
        this.annotations = list2;
        this.symbol = symbol;
        this.defaultValue = firExpression3;
        this.containingFunctionSymbol = containingFunctionSymbol;
        this.isCrossinline = z3;
        this.isNoinline = z4;
        this.isVararg = z5;
        this.status = FirResolvedDeclarationStatusImpl.INSTANCE.getDEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS();
        Name nameIdentifier = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.name = nameIdentifier;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameterRef> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return this.status;
    }

    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return this.controlFlowGraphReference;
    }

    public void setControlFlowGraphReference(FirControlFlowGraphReference firControlFlowGraphReference) {
        this.controlFlowGraphReference = firControlFlowGraphReference;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getStatus().accept(visitor, data);
        getReturnTypeRef().accept(visitor, data);
        FirReceiverParameter receiverParameter = getReceiverParameter();
        if (receiverParameter != null) {
            receiverParameter.accept(visitor, data);
        }
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6830getContextReceivers5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirContextReceiver) it.next()).accept(visitor, data);
        }
        FirExpression initializer = getInitializer();
        if (initializer != null) {
            initializer.accept(visitor, data);
        }
        FirExpression delegate = getDelegate();
        if (delegate != null) {
            delegate.accept(visitor, data);
        }
        FirPropertyAccessor getter = getGetter();
        if (getter != null) {
            getter.accept(visitor, data);
        }
        FirPropertyAccessor setter = getSetter();
        if (setter != null) {
            setter.accept(visitor, data);
        }
        FirBackingField backingField = getBackingField();
        if (backingField != null) {
            backingField.accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6829getAnnotations5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirAnnotation) it2.next()).accept(visitor, data);
        }
        FirControlFlowGraphReference controlFlowGraphReference = getControlFlowGraphReference();
        if (controlFlowGraphReference != null) {
            controlFlowGraphReference.accept(visitor, data);
        }
        FirExpression defaultValue = getDefaultValue();
        if (defaultValue != null) {
            defaultValue.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirDefaultSetterValueParameter transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformStatus((FirTransformer) transformer, (Object) data);
        transformReturnTypeRef((FirTransformer) transformer, (Object) data);
        transformReceiverParameter((FirTransformer) transformer, (Object) data);
        transformInitializer((FirTransformer) transformer, (Object) data);
        transformDelegate((FirTransformer) transformer, (Object) data);
        transformGetter((FirTransformer) transformer, (Object) data);
        transformSetter((FirTransformer) transformer, (Object) data);
        transformBackingField((FirTransformer) transformer, (Object) data);
        transformOtherChildren((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirDefaultSetterValueParameter transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setStatus((FirDeclarationStatus) getStatus().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirDefaultSetterValueParameter transformReturnTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setReturnTypeRef((FirTypeRef) getReturnTypeRef().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirDefaultSetterValueParameter transformReceiverParameter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirReceiverParameter receiverParameter = getReceiverParameter();
        setReceiverParameter(receiverParameter != null ? (FirReceiverParameter) receiverParameter.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirDefaultSetterValueParameter transformInitializer(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirExpression initializer = getInitializer();
        setInitializer(initializer != null ? (FirExpression) initializer.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirDefaultSetterValueParameter transformDelegate(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirExpression delegate = getDelegate();
        setDelegate(delegate != null ? (FirExpression) delegate.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirDefaultSetterValueParameter transformGetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirPropertyAccessor getter = getGetter();
        setGetter(getter != null ? (FirPropertyAccessor) getter.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirDefaultSetterValueParameter transformSetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirPropertyAccessor setter = getSetter();
        setSetter(setter != null ? (FirPropertyAccessor) setter.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirDefaultSetterValueParameter transformBackingField(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirBackingField backingField = getBackingField();
        setBackingField(backingField != null ? (FirBackingField) backingField.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirDefaultSetterValueParameter transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6829getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirDefaultSetterValueParameter transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6830getContextReceivers5e3fPpI(), transformer, data);
        transformAnnotations((FirTransformer) transformer, (Object) data);
        FirControlFlowGraphReference controlFlowGraphReference = getControlFlowGraphReference();
        setControlFlowGraphReference(controlFlowGraphReference != null ? (FirControlFlowGraphReference) controlFlowGraphReference.transform(transformer, data) : null);
        FirExpression defaultValue = getDefaultValue();
        setDefaultValue(defaultValue != null ? (FirExpression) defaultValue.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        setStatus(newStatus);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReturnTypeRef(FirTypeRef newReturnTypeRef) {
        Intrinsics.checkNotNullParameter(newReturnTypeRef, "newReturnTypeRef");
        setReturnTypeRef(newReturnTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReceiverParameter(FirReceiverParameter newReceiverParameter) {
        setReceiverParameter(newReceiverParameter);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
        setDeprecationsProvider(newDeprecationsProvider);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers) {
        Intrinsics.checkNotNullParameter(newContextReceivers, "newContextReceivers");
        m6832setContextReceiversGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newContextReceivers));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceInitializer(FirExpression newInitializer) {
        setInitializer(newInitializer);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceDelegate(FirExpression newDelegate) {
        setDelegate(newDelegate);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceGetter(FirPropertyAccessor newGetter) {
        setGetter(newGetter);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceSetter(FirPropertyAccessor newSetter) {
        setSetter(newSetter);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6831setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
        setControlFlowGraphReference(newControlFlowGraphReference);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter
    public void replaceDefaultValue(FirExpression newDefaultValue) {
        setDefaultValue(newDefaultValue);
    }
}
