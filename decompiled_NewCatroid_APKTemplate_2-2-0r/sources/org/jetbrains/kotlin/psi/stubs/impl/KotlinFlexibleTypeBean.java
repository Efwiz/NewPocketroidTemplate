package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;

/* JADX INFO: compiled from: KotlinUserTypeStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\t\u0010\r\u001a\u00020\u0001HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0001HÆ\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u0001HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFlexibleTypeBean;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "lowerBound", "upperBound", "(Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;)V", "getLowerBound", "()Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "nullable", "", "getNullable", "()Z", "getUpperBound", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KotlinFlexibleTypeBean implements KotlinTypeBean, FlexibleTypeMarker {
    private final KotlinTypeBean lowerBound;
    private final KotlinTypeBean upperBound;

    public static /* synthetic */ KotlinFlexibleTypeBean copy$default(KotlinFlexibleTypeBean kotlinFlexibleTypeBean, KotlinTypeBean kotlinTypeBean, KotlinTypeBean kotlinTypeBean2, int i, Object obj) {
        if ((i & 1) != 0) {
            kotlinTypeBean = kotlinFlexibleTypeBean.lowerBound;
        }
        if ((i & 2) != 0) {
            kotlinTypeBean2 = kotlinFlexibleTypeBean.upperBound;
        }
        return kotlinFlexibleTypeBean.copy(kotlinTypeBean, kotlinTypeBean2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KotlinTypeBean getLowerBound() {
        return this.lowerBound;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final KotlinTypeBean getUpperBound() {
        return this.upperBound;
    }

    public final KotlinFlexibleTypeBean copy(KotlinTypeBean lowerBound, KotlinTypeBean upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        return new KotlinFlexibleTypeBean(lowerBound, upperBound);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KotlinFlexibleTypeBean)) {
            return false;
        }
        KotlinFlexibleTypeBean kotlinFlexibleTypeBean = (KotlinFlexibleTypeBean) other;
        return Intrinsics.areEqual(this.lowerBound, kotlinFlexibleTypeBean.lowerBound) && Intrinsics.areEqual(this.upperBound, kotlinFlexibleTypeBean.upperBound);
    }

    public int hashCode() {
        return (this.lowerBound.hashCode() * 31) + this.upperBound.hashCode();
    }

    public String toString() {
        return "KotlinFlexibleTypeBean(lowerBound=" + this.lowerBound + ", upperBound=" + this.upperBound + ')';
    }

    public KotlinFlexibleTypeBean(KotlinTypeBean lowerBound, KotlinTypeBean upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public final KotlinTypeBean getLowerBound() {
        return this.lowerBound;
    }

    public final KotlinTypeBean getUpperBound() {
        return this.upperBound;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean
    public boolean getNullable() {
        return this.lowerBound.getNullable();
    }
}
