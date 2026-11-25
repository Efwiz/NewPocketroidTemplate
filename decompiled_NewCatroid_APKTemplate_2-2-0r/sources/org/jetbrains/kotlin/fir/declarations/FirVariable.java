package org.jetbrains.kotlin.fir.declarations;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirVariable.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000à\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0004¢\u0006\u0002\u0010\u0003J5\u0010S\u001a\u0002HT\"\u0004\b\u0000\u0010T\"\u0004\b\u0001\u0010U2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u0002HT\u0012\u0004\u0012\u0002HU0W2\u0006\u0010X\u001a\u0002HUH\u0016¢\u0006\u0002\u0010YJ\u0016\u0010Z\u001a\u00020[2\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0016\u0010]\u001a\u00020[2\f\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00160\u0005H&J\u0012\u0010_\u001a\u00020[2\b\u0010`\u001a\u0004\u0018\u00010\u0019H&J\u0010\u0010a\u001a\u00020[2\u0006\u0010b\u001a\u00020\u001dH&J\u0012\u0010c\u001a\u00020[2\b\u0010d\u001a\u0004\u0018\u00010%H&J\u0012\u0010e\u001a\u00020[2\b\u0010f\u001a\u0004\u0018\u00010\u0019H&J\u0012\u0010g\u001a\u00020[2\b\u0010h\u001a\u0004\u0018\u00010;H&J\u0010\u0010i\u001a\u00020[2\u0006\u0010j\u001a\u00020?H&J\u0012\u0010k\u001a\u00020[2\b\u0010l\u001a\u0004\u0018\u00010%H&J\u0010\u0010m\u001a\u00020[2\u0006\u0010n\u001a\u00020IH&J3\u0010o\u001a\u0002Hp\"\b\b\u0000\u0010p*\u00020q\"\u0004\b\u0001\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH\u0016¢\u0006\u0002\u0010tJ)\u0010u\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ)\u0010w\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ)\u0010x\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ)\u0010y\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ)\u0010z\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ)\u0010{\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ)\u0010|\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ)\u0010}\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ)\u0010~\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ)\u0010\u007f\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vJ*\u0010\u0080\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010U2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002HU0s2\u0006\u0010X\u001a\u0002HUH&¢\u0006\u0002\u0010vR\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u0004\u0018\u00010\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\bR\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0012\u0010\u001c\u001a\u00020\u001dX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u0004\u0018\u00010!X¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u0004\u0018\u00010%X¦\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u0004\u0018\u00010\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u001bR\u0012\u0010*\u001a\u00020+X¦\u0004¢\u0006\u0006\u001a\u0004\b*\u0010,R\u0012\u0010-\u001a\u00020+X¦\u0004¢\u0006\u0006\u001a\u0004\b-\u0010,R\u0012\u0010.\u001a\u00020/X¦\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000203X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0014\u0010:\u001a\u0004\u0018\u00010;X¦\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0012\u0010>\u001a\u00020?X¦\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0014\u0010B\u001a\u0004\u0018\u00010%X¦\u0004¢\u0006\u0006\u001a\u0004\bC\u0010'R\u0014\u0010D\u001a\u0004\u0018\u00010EX¦\u0004¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0012\u0010H\u001a\u00020IX¦\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u001a\u0010L\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000MX¦\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u0018\u0010P\u001a\b\u0012\u0004\u0012\u00020Q0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\bR\u0010\b\u0082\u0001\u000b\u000e\u0081\u0001\u0082\u0001\u0083\u0001\u0084\u0001\u0085\u0001¨\u0006\u0086\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "initializer", "getInitializer", "isVal", "", "()Z", "isVar", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setter", "getSetter", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceContextReceivers", "newContextReceivers", "replaceDelegate", "newDelegate", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceGetter", "newGetter", "replaceInitializer", "newInitializer", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceSetter", "newSetter", "replaceStatus", "newStatus", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "transformBackingField", "transformDelegate", "transformGetter", "transformInitializer", "transformOtherChildren", "transformReceiverParameter", "transformReturnTypeRef", "transformSetter", "transformStatus", "transformTypeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirVariable extends FirCallableDeclaration implements FirStatement {
    public /* synthetic */ FirVariable(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    public abstract FirBackingField getBackingField();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeserializedContainerSource getContainerSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract List<FirContextReceiver> getContextReceivers();

    public abstract FirExpression getDelegate();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeprecationsProvider getDeprecationsProvider();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract ConeSimpleKotlinType getDispatchReceiverType();

    public abstract FirPropertyAccessor getGetter();

    public abstract FirExpression getInitializer();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public abstract FirModuleData getModuleData();

    public abstract Name getName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract FirReceiverParameter getReceiverParameter();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract FirTypeRef getReturnTypeRef();

    public abstract FirPropertyAccessor getSetter();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract FirDeclarationStatus getStatus();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirVariableSymbol<? extends FirVariable> getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract List<FirTypeParameterRef> getTypeParameters();

    public abstract boolean isVal();

    public abstract boolean isVar();

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers);

    public abstract void replaceDelegate(FirExpression newDelegate);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider);

    public abstract void replaceGetter(FirPropertyAccessor newGetter);

    public abstract void replaceInitializer(FirExpression newInitializer);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceReceiverParameter(FirReceiverParameter newReceiverParameter);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceReturnTypeRef(FirTypeRef newReturnTypeRef);

    public abstract void replaceSetter(FirPropertyAccessor newSetter);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract void replaceStatus(FirDeclarationStatus newStatus);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirVariable transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirVariable transformBackingField(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirVariable transformDelegate(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirVariable transformGetter(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirVariable transformInitializer(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirVariable transformOtherChildren(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirVariable transformReceiverParameter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirVariable transformReturnTypeRef(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirVariable transformSetter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract <D> FirVariable transformStatus(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract <D> FirVariable transformTypeParameters(FirTransformer<? super D> transformer, D data);

    private FirVariable() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitVariable(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformVariable = transformer.transformVariable(this, data);
        Intrinsics.checkNotNull(firStatementTransformVariable, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirVariable.transform");
        return firStatementTransformVariable;
    }
}
