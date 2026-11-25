package org.jetbrains.kotlin.platform.konan;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.target.KonanTarget;

/* JADX INFO: compiled from: NativePlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/platform/konan/NativePlatformWithTarget;", "Lorg/jetbrains/kotlin/platform/konan/NativePlatform;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "(Lorg/jetbrains/kotlin/konan/target/KonanTarget;)V", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetName", "", "getTargetName", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class NativePlatformWithTarget extends NativePlatform {
    private final KonanTarget target;

    public static /* synthetic */ NativePlatformWithTarget copy$default(NativePlatformWithTarget nativePlatformWithTarget, KonanTarget konanTarget, int i, Object obj) {
        if ((i & 1) != 0) {
            konanTarget = nativePlatformWithTarget.target;
        }
        return nativePlatformWithTarget.copy(konanTarget);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KonanTarget getTarget() {
        return this.target;
    }

    public final NativePlatformWithTarget copy(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return new NativePlatformWithTarget(target);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof NativePlatformWithTarget) && Intrinsics.areEqual(this.target, ((NativePlatformWithTarget) other).target);
    }

    public int hashCode() {
        return this.target.hashCode();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NativePlatformWithTarget(KonanTarget target) {
        super(null);
        Intrinsics.checkNotNullParameter(target, "target");
        this.target = target;
    }

    public final KonanTarget getTarget() {
        return this.target;
    }

    @Override // org.jetbrains.kotlin.platform.SimplePlatform
    public String toString() {
        return super.toString();
    }

    @Override // org.jetbrains.kotlin.platform.SimplePlatform
    public String getTargetName() {
        return this.target.getVisibleName();
    }
}
