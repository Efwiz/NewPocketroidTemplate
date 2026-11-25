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
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
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
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.InlineStatus;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirAnonymousFunctionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\bF\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\n\b\u0000\u0018\u00002\u00020\u0001Bõ\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0007\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u0012\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 \u0012\b\u0010\"\u001a\u0004\u0018\u00010#\u0012\u0006\u0010$\u001a\u00020%\u0012\u0006\u0010&\u001a\u00020'\u0012\b\u0010(\u001a\u0004\u0018\u00010)\u0012\b\u0010*\u001a\u0004\u0018\u00010+\u0012\u0006\u0010,\u001a\u00020-\u0012\u0006\u0010.\u001a\u00020/\u0012\u0006\u00100\u001a\u00020/\u0012\f\u00101\u001a\b\u0012\u0004\u0012\u0002020 \u0012\u0006\u00103\u001a\u00020\u0012¢\u0006\u0002\u00104J5\u0010x\u001a\u00020y\"\u0004\b\u0000\u0010z\"\u0004\b\u0001\u0010{2\u0012\u0010|\u001a\u000e\u0012\u0004\u0012\u0002Hz\u0012\u0004\u0012\u0002H{0}2\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0002\u0010\u007fJ\u0019\u0010\u0080\u0001\u001a\u00020y2\u000e\u0010\u0081\u0001\u001a\t\u0012\u0004\u0012\u00020\b0\u0082\u0001H\u0016J\u0014\u0010\u0083\u0001\u001a\u00020y2\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010#H\u0016J\u0019\u0010\u0085\u0001\u001a\u00020y2\u000e\u0010\u0086\u0001\u001a\t\u0012\u0004\u0012\u00020\u001c0\u0082\u0001H\u0016J\u0012\u0010\u0087\u0001\u001a\u00020y2\u0007\u0010\u0088\u0001\u001a\u00020%H\u0016J\u0014\u0010\u0089\u0001\u001a\u00020y2\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u001eH\u0016J\u0012\u0010\u008b\u0001\u001a\u00020y2\u0007\u0010\u008c\u0001\u001a\u00020\u0016H\u0016J\u0012\u0010\u008d\u0001\u001a\u00020y2\u0007\u0010\u008e\u0001\u001a\u00020-H\u0016J\u0014\u0010\u008f\u0001\u001a\u00020y2\t\u0010\u0090\u0001\u001a\u0004\u0018\u00010+H\u0016J\u0014\u0010\u0091\u0001\u001a\u00020y2\t\u0010\u0092\u0001\u001a\u0004\u0018\u00010\u0014H\u0016J\u0012\u0010\u0093\u0001\u001a\u00020y2\u0007\u0010\u0094\u0001\u001a\u00020\u0012H\u0016J\u0012\u0010\u0095\u0001\u001a\u00020y2\u0007\u0010\u0096\u0001\u001a\u00020\u0010H\u0016J\u0012\u0010\u0097\u0001\u001a\u00020y2\u0007\u0010\u0098\u0001\u001a\u00020\u0012H\u0016J\u0019\u0010\u0099\u0001\u001a\u00020y2\u000e\u0010\u009a\u0001\u001a\t\u0012\u0004\u0012\u00020!0\u0082\u0001H\u0016J-\u0010\u009b\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010{2\u000e\u0010\u009c\u0001\u001a\t\u0012\u0004\u0012\u0002H{0\u009d\u00012\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0003\u0010\u009e\u0001J-\u0010\u009f\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010{2\u000e\u0010\u009c\u0001\u001a\t\u0012\u0004\u0012\u0002H{0\u009d\u00012\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0003\u0010\u009e\u0001J-\u0010 \u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010{2\u000e\u0010\u009c\u0001\u001a\t\u0012\u0004\u0012\u0002H{0\u009d\u00012\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0003\u0010\u009e\u0001J-\u0010¡\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010{2\u000e\u0010\u009c\u0001\u001a\t\u0012\u0004\u0012\u0002H{0\u009d\u00012\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0003\u0010\u009e\u0001J-\u0010¢\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010{2\u000e\u0010\u009c\u0001\u001a\t\u0012\u0004\u0012\u0002H{0\u009d\u00012\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0003\u0010\u009e\u0001J-\u0010£\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010{2\u000e\u0010\u009c\u0001\u001a\t\u0012\u0004\u0012\u0002H{0\u009d\u00012\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0003\u0010\u009e\u0001J-\u0010¤\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010{2\u000e\u0010\u009c\u0001\u001a\t\u0012\u0004\u0012\u0002H{0\u009d\u00012\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0003\u0010\u009e\u0001J-\u0010¥\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010{2\u000e\u0010\u009c\u0001\u001a\t\u0012\u0004\u0012\u0002H{0\u009d\u00012\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0003\u0010\u009e\u0001J-\u0010¦\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010{2\u000e\u0010\u009c\u0001\u001a\t\u0012\u0004\u0012\u0002H{0\u009d\u00012\u0006\u0010~\u001a\u0002H{H\u0016¢\u0006\u0003\u0010\u009e\u0001R(\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u00109\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u001c\u0010\"\u001a\u0004\u0018\u00010#X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010AR(\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0007X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u00109\u001a\u0004\bB\u00106\"\u0004\bC\u00108R\u001a\u0010$\u001a\u00020%X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u001a\u0010\u0015\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010M\"\u0004\bN\u0010OR\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010QR\u0014\u00100\u001a\u00020/X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010SR\u001a\u0010,\u001a\u00020-X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\u001c\u0010*\u001a\u0004\u0018\u00010+X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R\u0014\u0010.\u001a\u00020/X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010SR\u001c\u0010(\u001a\u0004\u0018\u00010)X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u0010aR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bb\u0010cR\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010e\"\u0004\bf\u0010gR\u001a\u0010\u0011\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bl\u0010mR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010o\"\u0004\bp\u0010qR\u0014\u0010&\u001a\u00020'X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\br\u0010sR\u001a\u00101\u001a\b\u0012\u0004\u0012\u0002020 X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bt\u00106R\u001a\u00103\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bu\u0010i\"\u0004\bv\u0010kR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bw\u00106\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006§\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirAnonymousFunctionImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "valueParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/FirLabel;", "invocationKind", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "inlineStatus", "Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;", "isLambda", "", "hasExplicitParameterList", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "typeRef", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Ljava/util/List;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Ljava/util/List;Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirBlock;Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;Lorg/jetbrains/kotlin/fir/FirLabel;Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;ZZLjava/util/List;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBody", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContextReceivers-5e3fPpI", "setContextReceivers-GqUYU-s", "getContractDescription", "()Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "setContractDescription", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;)V", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "setControlFlowGraphReference", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;)V", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getHasExplicitParameterList", "()Z", "getInlineStatus", "()Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;", "setInlineStatus", "(Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;)V", "getInvocationKind", "()Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "setInvocationKind", "(Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;)V", "getLabel", "()Lorg/jetbrains/kotlin/fir/FirLabel;", "setLabel", "(Lorg/jetbrains/kotlin/fir/FirLabel;)V", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;", "getTypeParameters", "getTypeRef", "setTypeRef", "getValueParameters", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceBody", "newBody", "replaceContextReceivers", "newContextReceivers", "replaceContractDescription", "newContractDescription", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceInlineStatus", "newInlineStatus", "replaceInvocationKind", "newInvocationKind", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceStatus", "newStatus", "replaceTypeRef", "newTypeRef", "replaceValueParameters", "newValueParameters", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirAnonymousFunctionImpl;", "transformBody", "transformChildren", "transformContractDescription", "transformReceiverParameter", "transformReturnTypeRef", "transformStatus", "transformTypeParameters", "transformValueParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnonymousFunctionImpl extends FirAnonymousFunction {
    private List<T> annotations;
    private final FirDeclarationAttributes attributes;
    private FirBlock body;
    private final DeserializedContainerSource containerSource;
    private List<T> contextReceivers;
    private FirContractDescription contractDescription;
    private FirControlFlowGraphReference controlFlowGraphReference;
    private DeprecationsProvider deprecationsProvider;
    private final ConeSimpleKotlinType dispatchReceiverType;
    private final boolean hasExplicitParameterList;
    private InlineStatus inlineStatus;
    private EventOccurrencesRange invocationKind;
    private final boolean isLambda;
    private FirLabel label;
    private final FirModuleData moduleData;
    private final FirDeclarationOrigin origin;
    private FirReceiverParameter receiverParameter;
    private FirTypeRef returnTypeRef;
    private final KtSourceElement source;
    private FirDeclarationStatus status;
    private final FirAnonymousFunctionSymbol symbol;
    private final List<FirTypeParameter> typeParameters;
    private FirTypeRef typeRef;
    private final List<FirValueParameter> valueParameters;

    public /* synthetic */ FirAnonymousFunctionImpl(KtSourceElement ktSourceElement, FirResolvePhase firResolvePhase, List list, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirDeclarationAttributes firDeclarationAttributes, FirDeclarationStatus firDeclarationStatus, FirTypeRef firTypeRef, FirReceiverParameter firReceiverParameter, DeprecationsProvider deprecationsProvider, DeserializedContainerSource deserializedContainerSource, ConeSimpleKotlinType coneSimpleKotlinType, List list2, FirControlFlowGraphReference firControlFlowGraphReference, List list3, FirBlock firBlock, FirContractDescription firContractDescription, FirAnonymousFunctionSymbol firAnonymousFunctionSymbol, FirLabel firLabel, EventOccurrencesRange eventOccurrencesRange, InlineStatus inlineStatus, boolean z, boolean z2, List list4, FirTypeRef firTypeRef2, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firResolvePhase, list, firModuleData, firDeclarationOrigin, firDeclarationAttributes, firDeclarationStatus, firTypeRef, firReceiverParameter, deprecationsProvider, deserializedContainerSource, coneSimpleKotlinType, list2, firControlFlowGraphReference, list3, firBlock, firContractDescription, firAnonymousFunctionSymbol, firLabel, eventOccurrencesRange, inlineStatus, z, z2, list4, firTypeRef2);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6811getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ List getContextReceivers() {
        return MutableOrEmptyList.m6756boximpl(m6812getContextReceivers5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnonymousFunction transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirAnonymousFunction transformBody(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public /* bridge */ /* synthetic */ FirAnonymousFunction transformContractDescription(FirTransformer firTransformer, Object obj) {
        return transformContractDescription((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public /* bridge */ /* synthetic */ FirContractDescriptionOwner transformContractDescription(FirTransformer firTransformer, Object obj) {
        return transformContractDescription((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirAnonymousFunction transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirFunction transformReceiverParameter(FirTransformer firTransformer, Object obj) {
        return transformReceiverParameter((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirAnonymousFunction transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirCallableDeclaration transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public /* bridge */ /* synthetic */ FirFunction transformReturnTypeRef(FirTransformer firTransformer, Object obj) {
        return transformReturnTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public /* bridge */ /* synthetic */ FirAnonymousFunction transformStatus(FirTransformer firTransformer, Object obj) {
        return transformStatus((FirTransformer<? super Object>) firTransformer, obj);
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public /* bridge */ /* synthetic */ FirAnonymousFunction transformTypeParameters(FirTransformer firTransformer, Object obj) {
        return transformTypeParameters((FirTransformer<? super Object>) firTransformer, obj);
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

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirAnonymousFunction transformValueParameters(FirTransformer firTransformer, Object obj) {
        return transformValueParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public /* bridge */ /* synthetic */ FirFunction transformValueParameters(FirTransformer firTransformer, Object obj) {
        return transformValueParameters((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6811getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6813setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public FirDeclarationStatus getStatus() {
        return this.status;
    }

    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirTypeRef getReturnTypeRef() {
        return this.returnTypeRef;
    }

    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public FirReceiverParameter getReceiverParameter() {
        return this.receiverParameter;
    }

    public void setReceiverParameter(FirReceiverParameter firReceiverParameter) {
        this.receiverParameter = firReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    /* JADX INFO: renamed from: getContextReceivers-5e3fPpI, reason: not valid java name */
    public List<T> m6812getContextReceivers5e3fPpI() {
        return this.contextReceivers;
    }

    /* JADX INFO: renamed from: setContextReceivers-GqUYU-s, reason: not valid java name */
    public void m6814setContextReceiversGqUYUs(List<T> list) {
        this.contextReceivers = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public FirControlFlowGraphReference getControlFlowGraphReference() {
        return this.controlFlowGraphReference;
    }

    public void setControlFlowGraphReference(FirControlFlowGraphReference firControlFlowGraphReference) {
        this.controlFlowGraphReference = firControlFlowGraphReference;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction
    public List<FirValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction
    public FirBlock getBody() {
        return this.body;
    }

    public void setBody(FirBlock firBlock) {
        this.body = firBlock;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public FirContractDescription getContractDescription() {
        return this.contractDescription;
    }

    public void setContractDescription(FirContractDescription firContractDescription) {
        Intrinsics.checkNotNullParameter(firContractDescription, "<set-?>");
        this.contractDescription = firContractDescription;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirAnonymousFunctionSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
    public FirLabel getLabel() {
        return this.label;
    }

    public void setLabel(FirLabel firLabel) {
        this.label = firLabel;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
    public EventOccurrencesRange getInvocationKind() {
        return this.invocationKind;
    }

    public void setInvocationKind(EventOccurrencesRange eventOccurrencesRange) {
        this.invocationKind = eventOccurrencesRange;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
    public InlineStatus getInlineStatus() {
        return this.inlineStatus;
    }

    public void setInlineStatus(InlineStatus inlineStatus) {
        Intrinsics.checkNotNullParameter(inlineStatus, "<set-?>");
        this.inlineStatus = inlineStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
    /* JADX INFO: renamed from: isLambda, reason: from getter */
    public boolean getIsLambda() {
        return this.isLambda;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
    public boolean getHasExplicitParameterList() {
        return this.hasExplicitParameterList;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public List<FirTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    private FirAnonymousFunctionImpl(KtSourceElement ktSourceElement, FirResolvePhase resolvePhase, List<T> list, FirModuleData moduleData, FirDeclarationOrigin origin, FirDeclarationAttributes attributes, FirDeclarationStatus status, FirTypeRef returnTypeRef, FirReceiverParameter firReceiverParameter, DeprecationsProvider deprecationsProvider, DeserializedContainerSource deserializedContainerSource, ConeSimpleKotlinType coneSimpleKotlinType, List<T> list2, FirControlFlowGraphReference firControlFlowGraphReference, List<FirValueParameter> valueParameters, FirBlock firBlock, FirContractDescription contractDescription, FirAnonymousFunctionSymbol symbol, FirLabel firLabel, EventOccurrencesRange eventOccurrencesRange, InlineStatus inlineStatus, boolean z, boolean z2, List<FirTypeParameter> typeParameters, FirTypeRef typeRef) {
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(returnTypeRef, "returnTypeRef");
        Intrinsics.checkNotNullParameter(deprecationsProvider, "deprecationsProvider");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(inlineStatus, "inlineStatus");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        this.source = ktSourceElement;
        this.annotations = list;
        this.moduleData = moduleData;
        this.origin = origin;
        this.attributes = attributes;
        this.status = status;
        this.returnTypeRef = returnTypeRef;
        this.receiverParameter = firReceiverParameter;
        this.deprecationsProvider = deprecationsProvider;
        this.containerSource = deserializedContainerSource;
        this.dispatchReceiverType = coneSimpleKotlinType;
        this.contextReceivers = list2;
        this.controlFlowGraphReference = firControlFlowGraphReference;
        this.valueParameters = valueParameters;
        this.body = firBlock;
        this.contractDescription = contractDescription;
        this.symbol = symbol;
        this.label = firLabel;
        this.invocationKind = eventOccurrencesRange;
        this.inlineStatus = inlineStatus;
        this.isLambda = z;
        this.hasExplicitParameterList = z2;
        this.typeParameters = typeParameters;
        this.typeRef = typeRef;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6811getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getStatus().accept(visitor, data);
        getReturnTypeRef().accept(visitor, data);
        FirReceiverParameter receiverParameter = getReceiverParameter();
        if (receiverParameter != null) {
            receiverParameter.accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6812getContextReceivers5e3fPpI()).iterator();
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
        FirBlock body = getBody();
        if (body != null) {
            body.accept(visitor, data);
        }
        getContractDescription().accept(visitor, data);
        FirLabel label = getLabel();
        if (label != null) {
            label.accept(visitor, data);
        }
        Iterator<T> it4 = getTypeParameters().iterator();
        while (it4.hasNext()) {
            ((FirTypeParameter) it4.next()).accept(visitor, data);
        }
        getTypeRef().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirAnonymousFunctionImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        transformStatus((FirTransformer) transformer, (Object) data);
        transformReturnTypeRef((FirTransformer) transformer, (Object) data);
        transformReceiverParameter((FirTransformer) transformer, (Object) data);
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6812getContextReceivers5e3fPpI(), transformer, data);
        FirControlFlowGraphReference controlFlowGraphReference = getControlFlowGraphReference();
        setControlFlowGraphReference(controlFlowGraphReference != null ? (FirControlFlowGraphReference) controlFlowGraphReference.transform(transformer, data) : null);
        transformValueParameters((FirTransformer) transformer, (Object) data);
        transformBody((FirTransformer) transformer, (Object) data);
        transformContractDescription((FirTransformer) transformer, (Object) data);
        FirLabel label = getLabel();
        setLabel(label != null ? (FirLabel) label.transform(transformer, data) : null);
        transformTypeParameters((FirTransformer) transformer, (Object) data);
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirAnonymousFunctionImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6811getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public <D> FirAnonymousFunctionImpl transformStatus(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setStatus((FirDeclarationStatus) getStatus().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirAnonymousFunctionImpl transformReturnTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setReturnTypeRef((FirTypeRef) getReturnTypeRef().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public <D> FirAnonymousFunctionImpl transformReceiverParameter(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirReceiverParameter receiverParameter = getReceiverParameter();
        setReceiverParameter(receiverParameter != null ? (FirReceiverParameter) receiverParameter.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction
    public <D> FirAnonymousFunctionImpl transformValueParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getValueParameters(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction
    public <D> FirAnonymousFunctionImpl transformBody(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirBlock body = getBody();
        setBody(body != null ? (FirBlock) body.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public <D> FirAnonymousFunctionImpl transformContractDescription(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setContractDescription((FirContractDescription) getContractDescription().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public <D> FirAnonymousFunctionImpl transformTypeParameters(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getTypeParameters(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6813setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public void replaceStatus(FirDeclarationStatus newStatus) {
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        setStatus(newStatus);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReturnTypeRef(FirTypeRef newReturnTypeRef) {
        Intrinsics.checkNotNullParameter(newReturnTypeRef, "newReturnTypeRef");
        setReturnTypeRef(newReturnTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceReceiverParameter(FirReceiverParameter newReceiverParameter) {
        setReceiverParameter(newReceiverParameter);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider) {
        Intrinsics.checkNotNullParameter(newDeprecationsProvider, "newDeprecationsProvider");
        setDeprecationsProvider(newDeprecationsProvider);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers) {
        Intrinsics.checkNotNullParameter(newContextReceivers, "newContextReceivers");
        m6814setContextReceiversGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newContextReceivers));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference) {
        setControlFlowGraphReference(newControlFlowGraphReference);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction
    public void replaceValueParameters(List<? extends FirValueParameter> newValueParameters) {
        Intrinsics.checkNotNullParameter(newValueParameters, "newValueParameters");
        getValueParameters().clear();
        getValueParameters().addAll(newValueParameters);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirFunction
    public void replaceBody(FirBlock newBody) {
        setBody(newBody);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction, org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public void replaceContractDescription(FirContractDescription newContractDescription) {
        Intrinsics.checkNotNullParameter(newContractDescription, "newContractDescription");
        setContractDescription(newContractDescription);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
    public void replaceInvocationKind(EventOccurrencesRange newInvocationKind) {
        setInvocationKind(newInvocationKind);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
    public void replaceInlineStatus(InlineStatus newInlineStatus) {
        Intrinsics.checkNotNullParameter(newInlineStatus, "newInlineStatus");
        setInlineStatus(newInlineStatus);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }
}
