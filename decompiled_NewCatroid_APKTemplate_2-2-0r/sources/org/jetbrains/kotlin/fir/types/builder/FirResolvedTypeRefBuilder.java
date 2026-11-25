package org.jetbrains.kotlin.fir.types.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirResolvedTypeRefImpl;

/* JADX INFO: compiled from: FirResolvedTypeRefBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001f\u001a\u00020 H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/builder/FirResolvedTypeRefBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "delegatedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getDelegatedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setDelegatedTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "isFromStubType", "", "()Z", "setFromStubType", "(Z)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "setType", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedTypeRefBuilder implements FirAnnotationContainerBuilder {
    private final List<FirAnnotation> annotations = new ArrayList();
    private FirTypeRef delegatedTypeRef;
    private boolean isFromStubType;
    private KtSourceElement source;
    public ConeKotlinType type;

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final ConeKotlinType getType() {
        ConeKotlinType coneKotlinType = this.type;
        if (coneKotlinType != null) {
            return coneKotlinType;
        }
        Intrinsics.throwUninitializedPropertyAccessException(ModuleXmlParser.TYPE);
        return null;
    }

    public final void setType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<set-?>");
        this.type = coneKotlinType;
    }

    public final FirTypeRef getDelegatedTypeRef() {
        return this.delegatedTypeRef;
    }

    public final void setDelegatedTypeRef(FirTypeRef firTypeRef) {
        this.delegatedTypeRef = firTypeRef;
    }

    /* JADX INFO: renamed from: isFromStubType, reason: from getter */
    public final boolean getIsFromStubType() {
        return this.isFromStubType;
    }

    public final void setFromStubType(boolean z) {
        this.isFromStubType = z;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirResolvedTypeRef build() {
        return new FirResolvedTypeRefImpl(getSource(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getType(), this.delegatedTypeRef, this.isFromStubType, null);
    }
}
