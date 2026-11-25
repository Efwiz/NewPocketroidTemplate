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
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.EmptyDeprecationsProvider;
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
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
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

/* JADX INFO: compiled from: FirJavaValueParameter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ä\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B{\b\u0007\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u0013\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b¢\u0006\u0002\u0010\u001cJ5\u0010i\u001a\u00020j\"\u0004\b\u0000\u0010k\"\u0004\b\u0001\u0010l2\u0012\u0010m\u001a\u000e\u0012\u0004\u0012\u0002Hk\u0012\u0004\u0012\u0002Hl0n2\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0002\u0010pJ\u0016\u0010q\u001a\u00020j2\f\u0010r\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016J\u0016\u0010s\u001a\u00020j2\f\u0010t\u001a\b\u0012\u0004\u0012\u00020/0\u0014H\u0016J\u0012\u0010u\u001a\u00020j2\b\u0010v\u001a\u0004\u0018\u000102H\u0016J\u0012\u0010w\u001a\u00020j2\b\u0010x\u001a\u0004\u0018\u00010\u0017H\u0016J\u0012\u0010y\u001a\u00020j2\b\u0010z\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010{\u001a\u00020j2\u0006\u0010|\u001a\u00020<H\u0016J\u0012\u0010}\u001a\u00020j2\b\u0010~\u001a\u0004\u0018\u00010DH\u0016J\u0013\u0010\u007f\u001a\u00020j2\t\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u0017H\u0016J\u0014\u0010\u0081\u0001\u001a\u00020j2\t\u0010\u0082\u0001\u001a\u0004\u0018\u00010UH\u0016J\u0012\u0010\u0083\u0001\u001a\u00020j2\u0007\u0010\u0084\u0001\u001a\u00020\rH\u0016J\u0014\u0010\u0085\u0001\u001a\u00020j2\t\u0010\u0086\u0001\u001a\u0004\u0018\u00010DH\u0016J\u0012\u0010\u0087\u0001\u001a\u00020j2\u0007\u0010\u0088\u0001\u001a\u00020aH\u0016J-\u0010\u0089\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u008d\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u008e\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u008f\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0090\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0091\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0092\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0093\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0094\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0095\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0096\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001J-\u0010\u0097\u0001\u001a\u00020\u0001\"\u0004\b\u0000\u0010l2\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002Hl0\u008b\u00012\u0006\u0010o\u001a\u0002HlH\u0016¢\u0006\u0003\u0010\u008c\u0001R!\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0016\u0010$\u001a\u0004\u0018\u00010%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0016\u0010(\u001a\u0004\u0018\u00010)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0018\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u001a\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\u001fR\u0016\u00101\u001a\u0004\u0018\u0001028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u0016\u00109\u001a\u0004\u0018\u00010\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u00106R\u0014\u0010;\u001a\u00020<8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0016\u0010?\u001a\u0004\u0018\u00010@8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0016\u0010C\u001a\u0004\u0018\u00010D8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bE\u0010FR\u0016\u0010G\u001a\u0004\u0018\u00010\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u00106R\u0014\u0010I\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0014\u0010K\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010JR\u0014\u0010L\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bL\u0010JR\u0014\u0010M\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010JR\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010JR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bN\u0010OR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010QR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010SR\u0016\u0010T\u001a\u0004\u0018\u00010U8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bV\u0010WR\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R\u0016\u0010\\\u001a\u0004\u0018\u00010D8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b]\u0010FR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b^\u0010_R\u0014\u0010`\u001a\u00020a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bb\u0010cR\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bd\u0010eR\u001a\u0010f\u001a\b\u0012\u0004\u0012\u00020g0\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bh\u0010\u001f¨\u0006\u0098\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaValueParameter;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "name", "Lorg/jetbrains/kotlin/name/Name;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "annotationBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "defaultValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "containingFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "isVararg", "", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;Z)V", "annotations", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "Lkotlin/Lazy;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainingFunctionSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getDefaultValue", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDefaultValue", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "initializer", "getInitializer", "isCrossinline", "()Z", "isNoinline", "isVal", "isVar", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "setter", "getSetter", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceContextReceivers", "newContextReceivers", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDefaultValue", "newDefaultValue", "replaceDelegate", "newDelegate", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceGetter", "newGetter", "replaceInitializer", "newInitializer", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceSetter", "newSetter", "replaceStatus", "newStatus", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "transformBackingField", "transformChildren", "transformDelegate", "transformGetter", "transformInitializer", "transformOtherChildren", "transformReceiverParameter", "transformReturnTypeRef", "transformSetter", "transformStatus", "transformTypeParameters", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaValueParameter extends FirValueParameter {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final FirDeclarationAttributes attributes;
    private final FirFunctionSymbol<?> containingFunctionSymbol;
    private FirExpression defaultValue;
    private final boolean isVararg;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirDeclarationOrigin.Java origin;
    private FirTypeRef returnTypeRef;
    private final KtSourceElement source;
    private final FirValueParameterSymbol symbol;

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirBackingField getBackingField() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeserializedContainerSource getContainerSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getDelegate() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirPropertyAccessor getGetter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirReceiverParameter getReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public FirPropertyAccessor getSetter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter
    /* JADX INFO: renamed from: isCrossinline */
    public boolean getIsCrossinline() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter
    /* JADX INFO: renamed from: isNoinline */
    public boolean getIsNoinline() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVal */
    public boolean getIsVal() {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    /* JADX INFO: renamed from: isVar */
    public boolean getIsVar() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceDelegate(FirExpression newDelegate) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceGetter(FirPropertyAccessor newGetter) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceInitializer(FirExpression newInitializer) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReceiverParameter(FirReceiverParameter newReceiverParameter) {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public void replaceSetter(FirPropertyAccessor newSetter) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin.Java getOrigin() {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public Name getName() {
        return this.name;
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
    /* JADX INFO: renamed from: isVararg, reason: from getter */
    public boolean getIsVararg() {
        return this.isVararg;
    }

    @FirImplementationDetail
    public FirJavaValueParameter(KtSourceElement ktSourceElement, FirModuleData moduleData, FirDeclarationOrigin.Java origin, FirResolvePhase resolvePhase, FirDeclarationAttributes attributes, FirTypeRef returnTypeRef, Name name2, FirValueParameterSymbol symbol, final Function0<? extends List<? extends FirAnnotation>> annotationBuilder, FirExpression firExpression, FirFunctionSymbol<?> containingFunctionSymbol, boolean z) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(returnTypeRef, "returnTypeRef");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(annotationBuilder, "annotationBuilder");
        Intrinsics.checkNotNullParameter(containingFunctionSymbol, "containingFunctionSymbol");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.origin = origin;
        this.attributes = attributes;
        this.returnTypeRef = returnTypeRef;
        this.name = name2;
        this.symbol = symbol;
        this.defaultValue = firExpression;
        this.containingFunctionSymbol = containingFunctionSymbol;
        this.isVararg = z;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
        this.annotations = LazyKt.lazy(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.declarations.FirJavaValueParameter$annotations$2
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return (List) this.annotations.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return EmptyDeprecationsProvider.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameterRef> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return FirResolvedDeclarationStatusImpl.INSTANCE.getDEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public List<FirContextReceiver> getContextReceivers() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getReturnTypeRef().accept(visitor, data);
        Iterator<T> it = getAnnotations().iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        FirExpression defaultValue = getDefaultValue();
        if (defaultValue != null) {
            defaultValue.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirValueParameter transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformReturnTypeRef((FirTransformer) transformer, (Object) data);
        transformOtherChildren((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirValueParameter transformReturnTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setReturnTypeRef((FirTypeRef) FirTransformerUtilKt.transformSingle(getReturnTypeRef(), transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirValueParameter transformReceiverParameter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirValueParameter transformInitializer(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirValueParameter transformDelegate(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirValueParameter transformGetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirValueParameter transformSetter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirValueParameter transformBackingField(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        throw new AssertionError("Mutating annotations for FirJava* is not supported");
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirValueParameter transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable
    public <D> FirValueParameter transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirExpression defaultValue = getDefaultValue();
        setDefaultValue(defaultValue != null ? (FirExpression) FirTransformerUtilKt.transformSingle(defaultValue, transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirValueParameter transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirValueParameter transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReturnTypeRef(FirTypeRef newReturnTypeRef) {
        Intrinsics.checkNotNullParameter(newReturnTypeRef, "newReturnTypeRef");
        setReturnTypeRef(newReturnTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter
    public void replaceDefaultValue(FirExpression newDefaultValue) {
        throw new IllegalStateException("Java value parameter cannot has default value".toString());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers) {
        Intrinsics.checkNotNullParameter(newContextReceivers, "newContextReceivers");
        throw new IllegalStateException("Body cannot be replaced for FirJavaValueParameter".toString());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirValueParameter, org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        throw new IllegalStateException("Status cannot be replaced for FirJavaValueParameter".toString());
    }
}
