package org.jetbrains.kotlin.ir.backend.js.dce;

import com.google.android.gms.cast.MediaTrack;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: UsefulDeclarationProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\bHÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0015\u001a\u00020\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/dce/ReachabilityInfo;", "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "target", MediaTrack.ROLE_DESCRIPTION, "", "isTargetContagious", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Ljava/lang/String;Z)V", "getDescription", "()Ljava/lang/String;", "()Z", "getSource", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getTarget", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class ReachabilityInfo {
    private final String description;
    private final boolean isTargetContagious;
    private final IrDeclaration source;
    private final IrDeclaration target;

    public static /* synthetic */ ReachabilityInfo copy$default(ReachabilityInfo reachabilityInfo, IrDeclaration irDeclaration, IrDeclaration irDeclaration2, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            irDeclaration = reachabilityInfo.source;
        }
        if ((i & 2) != 0) {
            irDeclaration2 = reachabilityInfo.target;
        }
        if ((i & 4) != 0) {
            str = reachabilityInfo.description;
        }
        if ((i & 8) != 0) {
            z = reachabilityInfo.isTargetContagious;
        }
        return reachabilityInfo.copy(irDeclaration, irDeclaration2, str, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IrDeclaration getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final IrDeclaration getTarget() {
        return this.target;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getIsTargetContagious() {
        return this.isTargetContagious;
    }

    public final ReachabilityInfo copy(IrDeclaration source, IrDeclaration target, String description, boolean isTargetContagious) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(description, "description");
        return new ReachabilityInfo(source, target, description, isTargetContagious);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReachabilityInfo)) {
            return false;
        }
        ReachabilityInfo reachabilityInfo = (ReachabilityInfo) other;
        return Intrinsics.areEqual(this.source, reachabilityInfo.source) && Intrinsics.areEqual(this.target, reachabilityInfo.target) && Intrinsics.areEqual(this.description, reachabilityInfo.description) && this.isTargetContagious == reachabilityInfo.isTargetContagious;
    }

    public int hashCode() {
        return (((((this.source.hashCode() * 31) + this.target.hashCode()) * 31) + this.description.hashCode()) * 31) + Boolean.hashCode(this.isTargetContagious);
    }

    public String toString() {
        return "ReachabilityInfo(source=" + this.source + ", target=" + this.target + ", description=" + this.description + ", isTargetContagious=" + this.isTargetContagious + ')';
    }

    public ReachabilityInfo(IrDeclaration source, IrDeclaration target, String description, boolean z) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(description, "description");
        this.source = source;
        this.target = target;
        this.description = description;
        this.isTargetContagious = z;
    }

    public final IrDeclaration getSource() {
        return this.source;
    }

    public final IrDeclaration getTarget() {
        return this.target;
    }

    public final String getDescription() {
        return this.description;
    }

    public final boolean isTargetContagious() {
        return this.isTargetContagious;
    }
}
