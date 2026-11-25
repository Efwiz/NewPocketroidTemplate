package org.jetbrains.kotlin.platform;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;

/* JADX INFO: compiled from: TargetPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0001\u0006R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/platform/TargetPlatformVersion;", "", MediaTrack.ROLE_DESCRIPTION, "", "getDescription", "()Ljava/lang/String;", "NoVersion", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TargetPlatformVersion {
    String getDescription();

    /* JADX INFO: compiled from: TargetPlatform.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/platform/TargetPlatformVersion$NoVersion;", "Lorg/jetbrains/kotlin/platform/TargetPlatformVersion;", "()V", MediaTrack.ROLE_DESCRIPTION, "", "getDescription", "()Ljava/lang/String;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoVersion implements TargetPlatformVersion {
        public static final NoVersion INSTANCE = new NoVersion();
        private static final String description = "";

        private NoVersion() {
        }

        @Override // org.jetbrains.kotlin.platform.TargetPlatformVersion
        public String getDescription() {
            return description;
        }
    }
}
