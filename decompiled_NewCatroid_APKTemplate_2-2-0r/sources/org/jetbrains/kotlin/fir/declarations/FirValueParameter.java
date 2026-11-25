package org.jetbrains.kotlin.fir.declarations;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirValueParameter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010`\u001a\u0002Ha\"\u0004\b\u0000\u0010a\"\u0004\b\u0001\u0010b2\u0012\u0010c\u001a\u000e\u0012\u0004\u0012\u0002Ha\u0012\u0004\u0012\u0002Hb0d2\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0002\u0010fJ\u0016\u0010g\u001a\u00020h2\f\u0010i\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0016\u0010j\u001a\u00020h2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0005H&J\u0012\u0010l\u001a\u00020h2\b\u0010m\u001a\u0004\u0018\u00010\u001dH&J\u0012\u0010n\u001a\u00020h2\b\u0010o\u001a\u0004\u0018\u00010!H&J\u0012\u0010p\u001a\u00020h2\b\u0010q\u001a\u0004\u0018\u00010!H&J\u0010\u0010r\u001a\u00020h2\u0006\u0010s\u001a\u00020'H&J\u0012\u0010t\u001a\u00020h2\b\u0010u\u001a\u0004\u0018\u00010/H&J\u0012\u0010v\u001a\u00020h2\b\u0010w\u001a\u0004\u0018\u00010!H&J\u0012\u0010x\u001a\u00020h2\b\u0010y\u001a\u0004\u0018\u00010HH&J\u0010\u0010z\u001a\u00020h2\u0006\u0010{\u001a\u00020LH&J\u0012\u0010|\u001a\u00020h2\b\u0010}\u001a\u0004\u0018\u00010/H&J\u0010\u0010~\u001a\u00020h2\u0006\u0010\u007f\u001a\u00020VH&J:\u0010\u0080\u0001\u001a\u0003H\u0081\u0001\"\n\b\u0000\u0010\u0081\u0001*\u00030\u0082\u0001\"\u0004\b\u0001\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH\u0016¢\u0006\u0003\u0010\u0085\u0001J-\u0010\u0086\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0088\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0089\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008a\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008b\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008c\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008d\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008e\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u008f\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0090\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001J-\u0010\u0091\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010b2\u000e\u0010\u0083\u0001\u001a\t\u0012\u0004\u0012\u0002Hb0\u0084\u00012\u0006\u0010e\u001a\u0002HbH&¢\u0006\u0003\u0010\u0087\u0001R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u0004\u0018\u00010\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\bR\u0014\u0010\u001c\u001a\u0004\u0018\u00010\u001dX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u0004\u0018\u00010!X¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u0004\u0018\u00010!X¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010#R\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u0004\u0018\u00010+X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\u0004\u0018\u00010/X¦\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0014\u00102\u001a\u0004\u0018\u00010!X¦\u0004¢\u0006\u0006\u001a\u0004\b3\u0010#R\u0012\u00104\u001a\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u00106R\u0012\u00107\u001a\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\b7\u00106R\u0012\u00108\u001a\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00106R\u0012\u00109\u001a\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\b9\u00106R\u0012\u0010:\u001a\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\b:\u00106R\u0012\u0010;\u001a\u00020<X¦\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0012\u0010?\u001a\u00020@X¦\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0012\u0010C\u001a\u00020DX¦\u0004¢\u0006\u0006\u001a\u0004\bE\u0010FR\u0014\u0010G\u001a\u0004\u0018\u00010HX¦\u0004¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0012\u0010K\u001a\u00020LX¦\u0004¢\u0006\u0006\u001a\u0004\bM\u0010NR\u0014\u0010O\u001a\u0004\u0018\u00010/X¦\u0004¢\u0006\u0006\u001a\u0004\bP\u00101R\u0014\u0010Q\u001a\u0004\u0018\u00010RX¦\u0004¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0012\u0010U\u001a\u00020VX¦\u0004¢\u0006\u0006\u001a\u0004\bW\u0010XR\u0012\u0010Y\u001a\u00020ZX¦\u0004¢\u0006\u0006\u001a\u0004\b[\u0010\\R\u0018\u0010]\u001a\b\u0012\u0004\u0012\u00020^0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b_\u0010\b¨\u0006\u0092\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "containingFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "getContainingFunctionSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "defaultValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDefaultValue", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "initializer", "getInitializer", "isCrossinline", "", "()Z", "isNoinline", "isVal", "isVar", "isVararg", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setter", "getSetter", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceContextReceivers", "newContextReceivers", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDefaultValue", "newDefaultValue", "replaceDelegate", "newDelegate", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceGetter", "newGetter", "replaceInitializer", "newInitializer", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceSetter", "newSetter", "replaceStatus", "newStatus", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "transformBackingField", "transformDelegate", "transformGetter", "transformInitializer", "transformOtherChildren", "transformReceiverParameter", "transformReturnTypeRef", "transformSetter", "transformStatus", "transformTypeParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirValueParameter extends FirVariable implements FirControlFlowGraphOwner {
    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract FirBackingField getBackingField();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeserializedContainerSource getContainerSource();

    public abstract FirFunctionSymbol<?> getContainingFunctionSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract List<FirContextReceiver> getContextReceivers();

    @Override // org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public abstract FirControlFlowGraphReference getControlFlowGraphReference();

    public abstract FirExpression getDefaultValue();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract FirExpression getDelegate();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeprecationsProvider getDeprecationsProvider();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract ConeSimpleKotlinType getDispatchReceiverType();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract FirPropertyAccessor getGetter();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract FirExpression getInitializer();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public abstract FirModuleData getModuleData();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract Name getName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract FirReceiverParameter getReceiverParameter();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract FirTypeRef getReturnTypeRef();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract FirPropertyAccessor getSetter();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract FirDeclarationStatus getStatus();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirValueParameterSymbol getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract List<FirTypeParameterRef> getTypeParameters();

    public abstract boolean isCrossinline();

    public abstract boolean isNoinline();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract boolean isVal();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract boolean isVar();

    public abstract boolean isVararg();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers);

    @Override // org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public abstract void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference);

    public abstract void replaceDefaultValue(FirExpression newDefaultValue);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract void replaceDelegate(FirExpression newDelegate);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract void replaceGetter(FirPropertyAccessor newGetter);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract void replaceInitializer(FirExpression newInitializer);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceReceiverParameter(FirReceiverParameter newReceiverParameter);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceReturnTypeRef(FirTypeRef newReturnTypeRef);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract void replaceSetter(FirPropertyAccessor newSetter);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract void replaceStatus(FirDeclarationStatus newStatus);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirValueParameter transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirValueParameter transformBackingField(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirValueParameter transformDelegate(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirValueParameter transformGetter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirValueParameter transformInitializer(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirValueParameter transformOtherChildren(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirValueParameter transformReceiverParameter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirValueParameter transformReturnTypeRef(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirValueParameter transformSetter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract <D> FirValueParameter transformStatus(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract <D> FirValueParameter transformTypeParameters(FirTransformer<? super D> transformer, D data);

    public FirValueParameter() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitValueParameter(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformValueParameter = transformer.transformValueParameter(this, data);
        Intrinsics.checkNotNull(firStatementTransformValueParameter, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirValueParameter.transform");
        return firStatementTransformValueParameter;
    }
}
