package org.jetbrains.kotlin.fir.declarations.builder;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirClassBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u00012\u00020\u0002J\b\u0010G\u001a\u00020HH&R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0007R\u0018\u0010\u0017\u001a\u00020\u0018X¦\u000e¢\u0006\f\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0018\u0010\u001d\u001a\u00020\u001eX¦\u000e¢\u0006\f\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0018\u0010#\u001a\u00020$X¦\u000e¢\u0006\f\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0018\u0010)\u001a\u00020*X¦\u000e¢\u0006\f\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0018\u0010/\u001a\u000200X¦\u000e¢\u0006\f\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001a\u00105\u001a\u0004\u0018\u000106X¦\u000e¢\u0006\f\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u0018\u0010;\u001a\u00020<X¦\u000e¢\u0006\f\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u0018\u0010A\u001a\b\u0012\u0004\u0012\u00020B0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bC\u0010\u0007R\u0018\u0010D\u001a\b\u0012\u0004\u0012\u00020E0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bF\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006IÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirClassBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirDeclarationBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getClassKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "setClassKind", "(Lorg/jetbrains/kotlin/descriptors/ClassKind;)V", "declarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getDeclarations", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "scopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "getScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "setScopeProvider", "(Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "superTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSuperTypeRefs", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirClassBuilder extends FirAnnotationContainerBuilder, FirDeclarationBuilder {
    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    FirClass build();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirDeclarationAttributes getAttributes();

    ClassKind getClassKind();

    List<FirDeclaration> getDeclarations();

    DeprecationsProvider getDeprecationsProvider();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirModuleData getModuleData();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirDeclarationOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirResolvePhase getResolvePhase();

    FirScopeProvider getScopeProvider();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    KtSourceElement getSource();

    FirDeclarationStatus getStatus();

    List<FirTypeRef> getSuperTypeRefs();

    List<FirTypeParameterRef> getTypeParameters();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setAttributes(FirDeclarationAttributes firDeclarationAttributes);

    void setClassKind(ClassKind classKind);

    void setDeprecationsProvider(DeprecationsProvider deprecationsProvider);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setModuleData(FirModuleData firModuleData);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setOrigin(FirDeclarationOrigin firDeclarationOrigin);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setResolvePhase(FirResolvePhase firResolvePhase);

    void setScopeProvider(FirScopeProvider firScopeProvider);

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    void setSource(KtSourceElement ktSourceElement);

    void setStatus(FirDeclarationStatus firDeclarationStatus);
}
