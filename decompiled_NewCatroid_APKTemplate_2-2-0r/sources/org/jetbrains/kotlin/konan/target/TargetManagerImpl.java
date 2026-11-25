package org.jetbrains.kotlin.konan.target;

import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: TargetManager.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0011\u001a\u00020\nH\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/TargetManagerImpl;", "Lorg/jetbrains/kotlin/konan/target/TargetManager;", "userRequest", "", "hostManager", "Lorg/jetbrains/kotlin/konan/target/HostManager;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/konan/target/HostManager;)V", "getHostManager", "()Lorg/jetbrains/kotlin/konan/target/HostManager;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetName", "getTargetName", "()Ljava/lang/String;", "getUserRequest", "determineCurrent", SchemaSymbols.ATTVAL_LIST, "", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class TargetManagerImpl implements TargetManager {
    private final HostManager hostManager;
    private final KonanTarget target;
    private final String userRequest;

    public TargetManagerImpl(String str, HostManager hostManager) {
        Intrinsics.checkNotNullParameter(hostManager, "hostManager");
        this.userRequest = str;
        this.hostManager = hostManager;
        this.target = determineCurrent();
    }

    public final HostManager getHostManager() {
        return this.hostManager;
    }

    public final String getUserRequest() {
        return this.userRequest;
    }

    @Override // org.jetbrains.kotlin.konan.target.TargetManager
    public KonanTarget getTarget() {
        return this.target;
    }

    @Override // org.jetbrains.kotlin.konan.target.TargetManager
    public String getTargetName() {
        return getTarget().getVisibleName();
    }

    @Override // org.jetbrains.kotlin.konan.target.TargetManager
    public void list() {
        for (KonanTarget konanTarget : this.hostManager.getEnabled()) {
            String str = "";
            String str2 = Intrinsics.areEqual(konanTarget, getTarget()) ? " (default)" : "";
            if (KonanTarget.INSTANCE.getDeprecatedTargets().contains(konanTarget)) {
                str = " (deprecated)";
            }
            System.out.println((Object) (konanTarget.getVisibleName() + str2 + str));
        }
    }

    private final KonanTarget determineCurrent() {
        String str = this.userRequest;
        if (str == null || Intrinsics.areEqual(str, "host")) {
            return HostManager.INSTANCE.getHost();
        }
        return (KonanTarget) MapsKt.getValue(this.hostManager.getTargets(), this.hostManager.known(HostManager.INSTANCE.resolveAlias(this.userRequest)));
    }
}
