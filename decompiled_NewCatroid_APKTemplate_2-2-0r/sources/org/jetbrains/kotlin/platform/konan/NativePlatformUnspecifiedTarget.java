package org.jetbrains.kotlin.platform.konan;

import kotlin.Metadata;

/* JADX INFO: compiled from: NativePlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/platform/konan/NativePlatformUnspecifiedTarget;", "Lorg/jetbrains/kotlin/platform/konan/NativePlatform;", "()V", "targetName", "", "getTargetName", "()Ljava/lang/String;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativePlatformUnspecifiedTarget extends NativePlatform {
    public static final NativePlatformUnspecifiedTarget INSTANCE = new NativePlatformUnspecifiedTarget();

    private NativePlatformUnspecifiedTarget() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.platform.SimplePlatform
    public String getTargetName() {
        return "general";
    }
}
