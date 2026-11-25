package org.jetbrains.kotlin.fir.references.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.impl.FirExplicitSuperReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirExplicitSuperReferenceBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0015\u001a\u00020\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/references/builder/FirExplicitSuperReferenceBuilder;", "", "()V", "labelName", "", "getLabelName", "()Ljava/lang/String;", "setLabelName", "(Ljava/lang/String;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "superTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSuperTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setSuperTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/references/FirSuperReference;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExplicitSuperReferenceBuilder {
    private String labelName;
    private KtSourceElement source;
    public FirTypeRef superTypeRef;

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final String getLabelName() {
        return this.labelName;
    }

    public final void setLabelName(String str) {
        this.labelName = str;
    }

    public final FirTypeRef getSuperTypeRef() {
        FirTypeRef firTypeRef = this.superTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("superTypeRef");
        return null;
    }

    public final void setSuperTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.superTypeRef = firTypeRef;
    }

    public final FirSuperReference build() {
        return new FirExplicitSuperReference(this.source, this.labelName, getSuperTypeRef());
    }
}
