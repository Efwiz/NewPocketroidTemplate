package org.jetbrains.kotlin.config;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JvmStringConcat.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u0000 \r2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\rB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmStringConcat;", "", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "isDynamic", "", "()Z", "INLINE", "INDY_WITH_CONSTANTS", "INDY", "Companion", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum JvmStringConcat {
    INLINE("inline"),
    INDY_WITH_CONSTANTS("indy-with-constants"),
    INDY("indy");

    private final String description;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final JvmStringConcat fromString(String str) {
        return INSTANCE.fromString(str);
    }

    public static EnumEntries<JvmStringConcat> getEntries() {
        return $ENTRIES;
    }

    JvmStringConcat(String str) {
        this.description = str;
    }

    public final String getDescription() {
        return this.description;
    }

    public final boolean isDynamic() {
        return this != INLINE;
    }

    /* JADX INFO: compiled from: JvmStringConcat.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmStringConcat$Companion;", "", "()V", "fromString", "Lorg/jetbrains/kotlin/config/JvmStringConcat;", "string", "", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final JvmStringConcat fromString(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            for (JvmStringConcat jvmStringConcat : JvmStringConcat.values()) {
                if (Intrinsics.areEqual(jvmStringConcat.getDescription(), string)) {
                    return jvmStringConcat;
                }
            }
            return null;
        }
    }
}
