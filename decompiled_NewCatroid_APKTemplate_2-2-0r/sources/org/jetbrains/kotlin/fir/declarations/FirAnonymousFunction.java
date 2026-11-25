package org.jetbrains.kotlin.fir.declarations;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirAnonymousFunction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J5\u0010^\u001a\u0002H_\"\u0004\b\u0000\u0010_\"\u0004\b\u0001\u0010`2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u0002H_\u0012\u0004\u0012\u0002H`0b2\u0006\u0010c\u001a\u0002H`H\u0016¢\u0006\u0002\u0010dJ\u0016\u0010e\u001a\u00020f2\f\u0010g\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H&J\u0012\u0010h\u001a\u00020f2\b\u0010i\u001a\u0004\u0018\u00010\u000fH&J\u0016\u0010j\u001a\u00020f2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006H&J\u0010\u0010l\u001a\u00020f2\u0006\u0010m\u001a\u00020\u001aH&J\u0012\u0010n\u001a\u00020f2\b\u0010o\u001a\u0004\u0018\u00010\u001eH&J\u0010\u0010p\u001a\u00020f2\u0006\u0010q\u001a\u00020\"H&J\u0010\u0010r\u001a\u00020f2\u0006\u0010s\u001a\u00020.H&J\u0012\u0010t\u001a\u00020f2\b\u0010u\u001a\u0004\u0018\u000102H&J\u0012\u0010v\u001a\u00020f2\b\u0010w\u001a\u0004\u0018\u00010CH&J\u0010\u0010x\u001a\u00020f2\u0006\u0010y\u001a\u00020GH&J\u0010\u0010z\u001a\u00020f2\u0006\u0010{\u001a\u00020OH&J\u0010\u0010|\u001a\u00020f2\u0006\u0010}\u001a\u00020GH&J\u0016\u0010~\u001a\u00020f2\f\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020\\0\u0006H&J:\u0010\u0080\u0001\u001a\u0003H\u0081\u0001\"\n\b\u0000\u0010\u0081\u0001*\u00030\u0082\u0001\"\u0004\b\u0001\u0010`2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002H`0\u0084\u00012\u0006\u0010c\u001a\u0002H`H\u0016¢\u0006\u0003\u0010\u0085\u0001J-\u0010\u0086\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010`2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002H`0\u0084\u00012\u0006\u0010c\u001a\u0002H`H&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0088\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010`2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002H`0\u0084\u00012\u0006\u0010c\u001a\u0002H`H&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0089\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010`2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002H`0\u0084\u00012\u0006\u0010c\u001a\u0002H`H&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008a\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010`2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002H`0\u0084\u00012\u0006\u0010c\u001a\u0002H`H&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008b\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010`2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002H`0\u0084\u00012\u0006\u0010c\u001a\u0002H`H&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008c\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010`2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002H`0\u0084\u00012\u0006\u0010c\u001a\u0002H`H&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008d\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010`2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002H`0\u0084\u00012\u0006\u0010c\u001a\u0002H`H&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008e\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010`2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002H`0\u0084\u00012\u0006\u0010c\u001a\u0002H`H&¢\u0006\u0003\u0010\u0087\u0001R\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\tR\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0012\u0010!\u001a\u00020\"X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\u0004\u0018\u00010&X¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0012\u0010)\u001a\u00020*X¦\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0012\u0010-\u001a\u00020.X¦\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u0014\u00101\u001a\u0004\u0018\u000102X¦\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u0012\u00105\u001a\u00020*X¦\u0004¢\u0006\u0006\u001a\u0004\b5\u0010,R\u0014\u00106\u001a\u0004\u0018\u000107X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0012\u0010:\u001a\u00020;X¦\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0012\u0010>\u001a\u00020?X¦\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0014\u0010B\u001a\u0004\u0018\u00010CX¦\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0012\u0010F\u001a\u00020GX¦\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR\u0014\u0010J\u001a\u0004\u0018\u00010KX¦\u0004¢\u0006\u0006\u001a\u0004\bL\u0010MR\u0012\u0010N\u001a\u00020OX¦\u0004¢\u0006\u0006\u001a\u0004\bP\u0010QR\u0012\u0010R\u001a\u00020SX¦\u0004¢\u0006\u0006\u001a\u0004\bT\u0010UR\u0018\u0010V\u001a\b\u0012\u0004\u0012\u00020W0\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\bX\u0010\tR\u0012\u0010Y\u001a\u00020GX¦\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010IR\u0018\u0010[\u001a\b\u0012\u0004\u0012\u00020\\0\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b]\u0010\t¨\u0006\u008f\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;", "Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "getContractDescription", "()Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "hasExplicitParameterList", "", "getHasExplicitParameterList", "()Z", "inlineStatus", "Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;", "getInlineStatus", "()Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;", "invocationKind", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "getInvocationKind", "()Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "isLambda", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/FirLabel;", "getLabel", "()Lorg/jetbrains/kotlin/fir/FirLabel;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "getTypeParameters", "typeRef", "getTypeRef", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getValueParameters", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceBody", "newBody", "replaceContextReceivers", "newContextReceivers", "replaceContractDescription", "newContractDescription", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceInlineStatus", "newInlineStatus", "replaceInvocationKind", "newInvocationKind", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceStatus", "newStatus", "replaceTypeRef", "newTypeRef", "replaceValueParameters", "newValueParameters", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "transformBody", "transformContractDescription", "transformReceiverParameter", "transformReturnTypeRef", "transformStatus", "transformTypeParameters", "transformValueParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAnonymousFunction extends FirFunction implements FirContractDescriptionOwner, FirTypeParametersOwner {
    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public abstract FirBlock getBody();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeserializedContainerSource getContainerSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract List<FirContextReceiver> getContextReceivers();

    @Override // org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public abstract FirContractDescription getContractDescription();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public abstract FirControlFlowGraphReference getControlFlowGraphReference();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeprecationsProvider getDeprecationsProvider();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract ConeSimpleKotlinType getDispatchReceiverType();

    public abstract boolean getHasExplicitParameterList();

    public abstract InlineStatus getInlineStatus();

    public abstract EventOccurrencesRange getInvocationKind();

    public abstract FirLabel getLabel();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public abstract FirModuleData getModuleData();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract FirReceiverParameter getReceiverParameter();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract FirTypeRef getReturnTypeRef();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract FirDeclarationStatus getStatus();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirAnonymousFunctionSymbol getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract List<FirTypeParameter> getTypeParameters();

    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public abstract List<FirValueParameter> getValueParameters();

    public abstract boolean isLambda();

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public abstract void replaceBody(FirBlock newBody);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers);

    @Override // org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public abstract void replaceContractDescription(FirContractDescription newContractDescription);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public abstract void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider);

    public abstract void replaceInlineStatus(InlineStatus newInlineStatus);

    public abstract void replaceInvocationKind(EventOccurrencesRange newInvocationKind);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceReceiverParameter(FirReceiverParameter newReceiverParameter);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceReturnTypeRef(FirTypeRef newReturnTypeRef);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract void replaceStatus(FirDeclarationStatus newStatus);

    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public abstract void replaceValueParameters(List<? extends FirValueParameter> newValueParameters);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirAnonymousFunction transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public abstract <D> FirAnonymousFunction transformBody(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner
    public abstract <D> FirAnonymousFunction transformContractDescription(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirAnonymousFunction transformReceiverParameter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirAnonymousFunction transformReturnTypeRef(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract <D> FirAnonymousFunction transformStatus(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract <D> FirAnonymousFunction transformTypeParameters(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction
    public abstract <D> FirAnonymousFunction transformValueParameters(FirTransformer<? super D> transformer, D data);

    public FirAnonymousFunction() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitAnonymousFunction(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirFunction, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformAnonymousFunction = transformer.transformAnonymousFunction(this, data);
        Intrinsics.checkNotNull(firStatementTransformAnonymousFunction, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction.transform");
        return firStatementTransformAnonymousFunction;
    }
}
