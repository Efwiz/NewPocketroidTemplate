package org.jetbrains.kotlin.config;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JvmAbiStability.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u0000 \t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\tB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmAbiStability;", "", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "STABLE", "UNSTABLE", "Companion", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum JvmAbiStability {
    STABLE("stable"),
    UNSTABLE("unstable");

    private final String description;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<JvmAbiStability> getEntries() {
        return $ENTRIES;
    }

    JvmAbiStability(String str) {
        this.description = str;
    }

    public final String getDescription() {
        return this.description;
    }

    /* JADX INFO: compiled from: JvmAbiStability.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmAbiStability$Companion;", "", "()V", "fromStringOrNull", "Lorg/jetbrains/kotlin/config/JvmAbiStability;", "string", "", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JvmAbiStability fromStringOrNull(String string) {
            for (JvmAbiStability jvmAbiStability : JvmAbiStability.values()) {
                if (Intrinsics.areEqual(jvmAbiStability.getDescription(), string)) {
                    return jvmAbiStability;
                }
            }
            return null;
        }
    }
}
