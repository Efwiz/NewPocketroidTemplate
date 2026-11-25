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
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.symbols.impl.FirErrorPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirErrorProperty.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010W\u001a\u0002HX\"\u0004\b\u0000\u0010X\"\u0004\b\u0001\u0010Y2\u0012\u0010Z\u001a\u000e\u0012\u0004\u0012\u0002HX\u0012\u0004\u0012\u0002HY0[2\u0006\u0010\\\u001a\u0002HYH\u0016¢\u0006\u0002\u0010]J\u0016\u0010^\u001a\u00020_2\f\u0010`\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0016\u0010a\u001a\u00020_2\f\u0010b\u001a\b\u0012\u0004\u0012\u00020\u00160\u0005H&J\u0012\u0010c\u001a\u00020_2\b\u0010d\u001a\u0004\u0018\u00010\u0019H&J\u0010\u0010e\u001a\u00020_2\u0006\u0010f\u001a\u00020\u001dH&J\u0012\u0010g\u001a\u00020_2\b\u0010h\u001a\u0004\u0018\u00010)H&J\u0012\u0010i\u001a\u00020_2\b\u0010j\u001a\u0004\u0018\u00010\u0019H&J\u0012\u0010k\u001a\u00020_2\b\u0010l\u001a\u0004\u0018\u00010?H&J\u0010\u0010m\u001a\u00020_2\u0006\u0010n\u001a\u00020CH&J\u0012\u0010o\u001a\u00020_2\b\u0010p\u001a\u0004\u0018\u00010)H&J\u0010\u0010q\u001a\u00020_2\u0006\u0010r\u001a\u00020MH&J3\u0010s\u001a\u0002Ht\"\b\b\u0000\u0010t*\u00020u\"\u0004\b\u0001\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH\u0016¢\u0006\u0002\u0010xJ)\u0010y\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ)\u0010{\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ)\u0010|\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ)\u0010}\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ)\u0010~\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ)\u0010\u007f\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ*\u0010\u0080\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ*\u0010\u0081\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ*\u0010\u0082\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ*\u0010\u0083\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zJ*\u0010\u0084\u0001\u001a\u00020\u0000\"\u0004\b\u0000\u0010Y2\f\u0010v\u001a\b\u0012\u0004\u0012\u0002HY0w2\u0006\u0010\\\u001a\u0002HYH&¢\u0006\u0002\u0010zR\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u0004\u0018\u00010\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\bR\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0012\u0010\u001c\u001a\u00020\u001dX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0012\u0010 \u001a\u00020!X¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u0004\u0018\u00010%X¦\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u0004\u0018\u00010)X¦\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0014\u0010,\u001a\u0004\u0018\u00010\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\u001bR\u0012\u0010.\u001a\u00020/X¦\u0004¢\u0006\u0006\u001a\u0004\b.\u00100R\u0012\u00101\u001a\u00020/X¦\u0004¢\u0006\u0006\u001a\u0004\b1\u00100R\u0012\u00102\u001a\u000203X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0012\u0010:\u001a\u00020;X¦\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0014\u0010>\u001a\u0004\u0018\u00010?X¦\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0012\u0010B\u001a\u00020CX¦\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0014\u0010F\u001a\u0004\u0018\u00010)X¦\u0004¢\u0006\u0006\u001a\u0004\bG\u0010+R\u0014\u0010H\u001a\u0004\u0018\u00010IX¦\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u0012\u0010L\u001a\u00020MX¦\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u0012\u0010P\u001a\u00020QX¦\u0004¢\u0006\u0006\u001a\u0004\bR\u0010SR\u0018\u0010T\u001a\b\u0012\u0004\u0012\u00020U0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\bV\u0010\b¨\u0006\u0085\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "initializer", "getInitializer", "isVal", "", "()Z", "isVar", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setter", "getSetter", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirErrorPropertySymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirErrorPropertySymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceContextReceivers", "newContextReceivers", "replaceDelegate", "newDelegate", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceGetter", "newGetter", "replaceInitializer", "newInitializer", "replaceReceiverParameter", "newReceiverParameter", "replaceReturnTypeRef", "newReturnTypeRef", "replaceSetter", "newSetter", "replaceStatus", "newStatus", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", "transformBackingField", "transformDelegate", "transformGetter", "transformInitializer", "transformOtherChildren", "transformReceiverParameter", "transformReturnTypeRef", "transformSetter", "transformStatus", "transformTypeParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirErrorProperty extends FirVariable implements FirDiagnosticHolder {
    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract FirBackingField getBackingField();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeserializedContainerSource getContainerSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract List<FirContextReceiver> getContextReceivers();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract FirExpression getDelegate();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract DeprecationsProvider getDeprecationsProvider();

    @Override // org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public abstract ConeDiagnostic getDiagnostic();

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
    public abstract FirErrorPropertySymbol getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract List<FirTypeParameterRef> getTypeParameters();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract boolean isVal();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract boolean isVar();

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract void replaceContextReceivers(List<? extends FirContextReceiver> newContextReceivers);

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
    public abstract <D> FirErrorProperty transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirErrorProperty transformBackingField(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirErrorProperty transformDelegate(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirErrorProperty transformGetter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirErrorProperty transformInitializer(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirErrorProperty transformOtherChildren(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirErrorProperty transformReceiverParameter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration
    public abstract <D> FirErrorProperty transformReturnTypeRef(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable
    public abstract <D> FirErrorProperty transformSetter(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract <D> FirErrorProperty transformStatus(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract <D> FirErrorProperty transformTypeParameters(FirTransformer<? super D> transformer, D data);

    public FirErrorProperty() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitErrorProperty(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirVariable, org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformErrorProperty = transformer.transformErrorProperty(this, data);
        Intrinsics.checkNotNull(firStatementTransformErrorProperty, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirErrorProperty.transform");
        return firStatementTransformErrorProperty;
    }
}
