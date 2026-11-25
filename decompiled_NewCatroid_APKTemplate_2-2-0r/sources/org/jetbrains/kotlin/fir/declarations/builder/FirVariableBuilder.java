package org.jetbrains.kotlin.fir.declarations.builder;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: FirVariableBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010o\u001a\u00020pH&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\bX¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u0004\u0018\u00010\u000eX¦\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u0014X¦\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0006R\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u001dX¦\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0018\u0010\"\u001a\u00020#X¦\u000e¢\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010(\u001a\u0004\u0018\u00010)X¦\u000e¢\u0006\f\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010.\u001a\u0004\u0018\u00010/X¦\u000e¢\u0006\f\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u0004\u0018\u00010\u001dX¦\u000e¢\u0006\f\u001a\u0004\b5\u0010\u001f\"\u0004\b6\u0010!R\u0018\u00107\u001a\u000208X¦\u000e¢\u0006\f\u001a\u0004\b7\u00109\"\u0004\b:\u0010;R\u0018\u0010<\u001a\u00020=X¦\u000e¢\u0006\f\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u0018\u0010B\u001a\u00020CX¦\u000e¢\u0006\f\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u0018\u0010H\u001a\u00020IX¦\u000e¢\u0006\f\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR\u001a\u0010N\u001a\u0004\u0018\u00010OX¦\u000e¢\u0006\f\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u0018\u0010T\u001a\u00020UX¦\u000e¢\u0006\f\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u0018\u0010Z\u001a\u00020[X¦\u000e¢\u0006\f\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R\u001a\u0010`\u001a\u0004\u0018\u00010/X¦\u000e¢\u0006\f\u001a\u0004\ba\u00101\"\u0004\bb\u00103R\u001a\u0010c\u001a\u0004\u0018\u00010dX¦\u000e¢\u0006\f\u001a\u0004\be\u0010f\"\u0004\bg\u0010hR\u0018\u0010i\u001a\u00020jX¦\u000e¢\u0006\f\u001a\u0004\bk\u0010l\"\u0004\bm\u0010nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006qÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirVariableBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirDeclarationBuilder;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "setBackingField", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDelegate", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDelegate", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "setGetter", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;)V", "initializer", "getInitializer", "setInitializer", "isVar", "", "()Z", "setVar", "(Z)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "setter", "getSetter", "setSetter", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirVariableBuilder extends FirDeclarationBuilder {
    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirVariable build();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirDeclarationAttributes getAttributes();

    FirBackingField getBackingField();

    DeserializedContainerSource getContainerSource();

    List<FirContextReceiver> getContextReceivers();

    FirExpression getDelegate();

    DeprecationsProvider getDeprecationsProvider();

    ConeSimpleKotlinType getDispatchReceiverType();

    FirPropertyAccessor getGetter();

    FirExpression getInitializer();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirModuleData getModuleData();

    Name getName();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirDeclarationOrigin getOrigin();

    FirReceiverParameter getReceiverParameter();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirResolvePhase getResolvePhase();

    FirTypeRef getReturnTypeRef();

    FirPropertyAccessor getSetter();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    KtSourceElement getSource();

    FirDeclarationStatus getStatus();

    boolean isVar();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setAttributes(FirDeclarationAttributes firDeclarationAttributes);

    void setBackingField(FirBackingField firBackingField);

    void setContainerSource(DeserializedContainerSource deserializedContainerSource);

    void setDelegate(FirExpression firExpression);

    void setDeprecationsProvider(DeprecationsProvider deprecationsProvider);

    void setDispatchReceiverType(ConeSimpleKotlinType coneSimpleKotlinType);

    void setGetter(FirPropertyAccessor firPropertyAccessor);

    void setInitializer(FirExpression firExpression);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setModuleData(FirModuleData firModuleData);

    void setName(Name name2);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setOrigin(FirDeclarationOrigin firDeclarationOrigin);

    void setReceiverParameter(FirReceiverParameter firReceiverParameter);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setResolvePhase(FirResolvePhase firResolvePhase);

    void setReturnTypeRef(FirTypeRef firTypeRef);

    void setSetter(FirPropertyAccessor firPropertyAccessor);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setSource(KtSourceElement ktSourceElement);

    void setStatus(FirDeclarationStatus firDeclarationStatus);

    void setVar(boolean z);
}
