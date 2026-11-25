package org.jetbrains.kotlin.fir.builder;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PsiRawFirBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u0000 \u00052\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/BodyBuildingMode;", "", "(Ljava/lang/String;I)V", "NORMAL", "LAZY_BODIES", "Companion", "psi2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum BodyBuildingMode {
    NORMAL,
    LAZY_BODIES;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<BodyBuildingMode> getEntries() {
        return $ENTRIES;
    }

    /* JADX INFO: compiled from: PsiRawFirBuilder.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/BodyBuildingMode$Companion;", "", "()V", "lazyBodies", "Lorg/jetbrains/kotlin/fir/builder/BodyBuildingMode;", "", "psi2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BodyBuildingMode lazyBodies(boolean lazyBodies) {
            return lazyBodies ? BodyBuildingMode.LAZY_BODIES : BodyBuildingMode.NORMAL;
        }
    }
}
