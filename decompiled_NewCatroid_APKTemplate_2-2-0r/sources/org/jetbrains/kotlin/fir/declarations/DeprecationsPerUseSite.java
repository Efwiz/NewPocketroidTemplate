package org.jetbrains.kotlin.fir.declarations;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;

/* JADX INFO: compiled from: deprecations.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B%\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0014\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J!\u0010\f\u001a\u0004\u0018\u00010\u00032\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000e\"\u00020\u0006¢\u0006\u0002\u0010\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0011J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/DeprecationsPerUseSite;", "", DevModeOverwritingStrategies.ALL, "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "bySpecificSite", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "(Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;Ljava/util/Map;)V", "getAll", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "getBySpecificSite", "()Ljava/util/Map;", "forUseSite", "sites", "", "([Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;)Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "isEmpty", "", "isNotEmpty", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecationsPerUseSite {
    private final DeprecationInfo all;
    private final Map<AnnotationUseSiteTarget, DeprecationInfo> bySpecificSite;

    /* JADX WARN: Multi-variable type inference failed */
    public DeprecationsPerUseSite(DeprecationInfo deprecationInfo, Map<AnnotationUseSiteTarget, ? extends DeprecationInfo> map) {
        this.all = deprecationInfo;
        this.bySpecificSite = map;
    }

    public final DeprecationInfo getAll() {
        return this.all;
    }

    public final Map<AnnotationUseSiteTarget, DeprecationInfo> getBySpecificSite() {
        return this.bySpecificSite;
    }

    public final DeprecationInfo forUseSite(AnnotationUseSiteTarget... sites) {
        Intrinsics.checkNotNullParameter(sites, "sites");
        if (this.bySpecificSite != null) {
            for (AnnotationUseSiteTarget annotationUseSiteTarget : sites) {
                DeprecationInfo deprecationInfo = this.bySpecificSite.get(annotationUseSiteTarget);
                if (deprecationInfo != null) {
                    return deprecationInfo;
                }
            }
        }
        return this.all;
    }

    public final boolean isEmpty() {
        return this.all == null && this.bySpecificSite == null;
    }

    public final boolean isNotEmpty() {
        return !isEmpty();
    }

    public String toString() {
        return isEmpty() ? "NoDeprecation" : "org.jetbrains.kotlin.fir.declarations.DeprecationInfoForUseSites(all=" + this.all + ", bySpecificSite=" + this.bySpecificSite + ')';
    }
}
