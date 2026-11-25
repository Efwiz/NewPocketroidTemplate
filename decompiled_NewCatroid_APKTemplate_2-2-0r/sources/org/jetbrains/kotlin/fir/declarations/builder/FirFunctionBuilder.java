package org.jetbrains.kotlin.fir.declarations.builder;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: FirFunctionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u00012\u00020\u0002J\b\u0010P\u001a\u00020QH&R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u0015X¦\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0007R\u0018\u0010\u001d\u001a\u00020\u001eX¦\u000e¢\u0006\f\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u0004\u0018\u00010$X¦\u000e¢\u0006\f\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0018\u0010)\u001a\u00020*X¦\u000e¢\u0006\f\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0018\u0010/\u001a\u000200X¦\u000e¢\u0006\f\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u0018\u00105\u001a\u000206X¦\u000e¢\u0006\f\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u0018\u0010;\u001a\u00020<X¦\u000e¢\u0006\f\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001a\u0010A\u001a\u0004\u0018\u00010BX¦\u000e¢\u0006\f\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u0018\u0010G\u001a\u00020HX¦\u000e¢\u0006\f\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u0018\u0010M\u001a\b\u0012\u0004\u0012\u00020N0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bO\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006RÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirFunctionBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirDeclarationBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBody", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getValueParameters", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirFunctionBuilder extends FirAnnotationContainerBuilder, FirDeclarationBuilder {
    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    /* JADX INFO: renamed from: build */
    FirFunction mo6809build();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    List<FirAnnotation> getAnnotations();

    FirDeclarationAttributes getAttributes();

    FirBlock getBody();

    DeserializedContainerSource getContainerSource();

    List<FirContextReceiver> getContextReceivers();

    DeprecationsProvider getDeprecationsProvider();

    ConeSimpleKotlinType getDispatchReceiverType();

    FirModuleData getModuleData();

    FirDeclarationOrigin getOrigin();

    FirResolvePhase getResolvePhase();

    FirTypeRef getReturnTypeRef();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    KtSourceElement getSource();

    FirDeclarationStatus getStatus();

    List<FirValueParameter> getValueParameters();

    void setAttributes(FirDeclarationAttributes firDeclarationAttributes);

    void setBody(FirBlock firBlock);

    void setContainerSource(DeserializedContainerSource deserializedContainerSource);

    void setDeprecationsProvider(DeprecationsProvider deprecationsProvider);

    void setDispatchReceiverType(ConeSimpleKotlinType coneSimpleKotlinType);

    void setModuleData(FirModuleData firModuleData);

    void setOrigin(FirDeclarationOrigin firDeclarationOrigin);

    void setResolvePhase(FirResolvePhase firResolvePhase);

    void setReturnTypeRef(FirTypeRef firTypeRef);

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    void setSource(KtSourceElement ktSourceElement);

    void setStatus(FirDeclarationStatus firDeclarationStatus);
}
