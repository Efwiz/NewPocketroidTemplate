package org.jetbrains.kotlin.descriptors;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Visibilities.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001:\t\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001dB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\t¢\u0006\u0002\u0010\u000fJ!\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\tH\u0000¢\u0006\u0004\b\u0011\u0010\u000fJ\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R \u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\u0002¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities;", "", "()V", "DEFAULT_VISIBILITY", "Lorg/jetbrains/kotlin/descriptors/Visibilities$Public;", "getDEFAULT_VISIBILITY", "()Lorg/jetbrains/kotlin/descriptors/Visibilities$Public;", "ORDERED_VISIBILITIES", "", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "", "getORDERED_VISIBILITIES$annotations", "compare", "first", "second", "(Lorg/jetbrains/kotlin/descriptors/Visibility;Lorg/jetbrains/kotlin/descriptors/Visibility;)Ljava/lang/Integer;", "compareLocal", "compareLocal$compiler_common", "isPrivate", "", "visibility", "Inherited", "Internal", "InvisibleFake", "Local", "Private", "PrivateToThis", "Protected", "Public", "Unknown", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Visibilities {
    private static final Public DEFAULT_VISIBILITY;
    public static final Visibilities INSTANCE = new Visibilities();
    private static final Map<Visibility, Integer> ORDERED_VISIBILITIES;

    /* JADX INFO: compiled from: Visibilities.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities$Private;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "mustCheckInImports", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Private extends Visibility {
        public static final Private INSTANCE = new Private();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return true;
        }

        private Private() {
            super("private", false);
        }
    }

    private Visibilities() {
    }

    /* JADX INFO: compiled from: Visibilities.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities$PrivateToThis;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "internalDisplayName", "", "getInternalDisplayName", "()Ljava/lang/String;", "mustCheckInImports", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PrivateToThis extends Visibility {
        public static final PrivateToThis INSTANCE = new PrivateToThis();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return true;
        }

        private PrivateToThis() {
            super("private_to_this", false);
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        /* JADX INFO: renamed from: getInternalDisplayName */
        public String getName() {
            return "private/*private to this*/";
        }
    }

    /* JADX INFO: compiled from: Visibilities.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities$Protected;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "mustCheckInImports", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Protected extends Visibility {
        public static final Protected INSTANCE = new Protected();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return false;
        }

        private Protected() {
            super("protected", true);
        }
    }

    /* JADX INFO: compiled from: Visibilities.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities$Internal;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "mustCheckInImports", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Internal extends Visibility {
        public static final Internal INSTANCE = new Internal();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return true;
        }

        private Internal() {
            super("internal", false);
        }
    }

    /* JADX INFO: compiled from: Visibilities.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities$Public;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "mustCheckInImports", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Public extends Visibility {
        public static final Public INSTANCE = new Public();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return false;
        }

        private Public() {
            super("public", true);
        }
    }

    /* JADX INFO: compiled from: Visibilities.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities$Local;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "mustCheckInImports", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Local extends Visibility {
        public static final Local INSTANCE = new Local();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return true;
        }

        private Local() {
            super("local", false);
        }
    }

    /* JADX INFO: compiled from: Visibilities.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities$Inherited;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "mustCheckInImports", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Inherited extends Visibility {
        public static final Inherited INSTANCE = new Inherited();

        private Inherited() {
            super("inherited", false);
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            throw new IllegalStateException("This method shouldn't be invoked for INHERITED visibility");
        }
    }

    /* JADX INFO: compiled from: Visibilities.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities$InvisibleFake;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "externalDisplayName", "", "getExternalDisplayName", "()Ljava/lang/String;", "mustCheckInImports", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InvisibleFake extends Visibility {
        public static final InvisibleFake INSTANCE = new InvisibleFake();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return true;
        }

        private InvisibleFake() {
            super("invisible_fake", false);
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public String getExternalDisplayName() {
            return "invisible (private in a supertype)";
        }
    }

    /* JADX INFO: compiled from: Visibilities.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibilities$Unknown;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "mustCheckInImports", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Unknown extends Visibility {
        public static final Unknown INSTANCE = new Unknown();

        private Unknown() {
            super("unknown", false);
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            throw new IllegalStateException("This method shouldn't be invoked for UNKNOWN visibility");
        }
    }

    static {
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        mapCreateMapBuilder.put(PrivateToThis.INSTANCE, 0);
        mapCreateMapBuilder.put(Private.INSTANCE, 0);
        mapCreateMapBuilder.put(Internal.INSTANCE, 1);
        mapCreateMapBuilder.put(Protected.INSTANCE, 1);
        mapCreateMapBuilder.put(Public.INSTANCE, 2);
        ORDERED_VISIBILITIES = MapsKt.build(mapCreateMapBuilder);
        DEFAULT_VISIBILITY = Public.INSTANCE;
    }

    public final Integer compare(Visibility first, Visibility second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        Integer numCompareTo = first.compareTo(second);
        if (numCompareTo != null) {
            return numCompareTo;
        }
        Integer numCompareTo2 = second.compareTo(first);
        if (numCompareTo2 != null) {
            return Integer.valueOf(-numCompareTo2.intValue());
        }
        return null;
    }

    public final Integer compareLocal$compiler_common(Visibility first, Visibility second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        if (first == second) {
            return 0;
        }
        Map<Visibility, Integer> map = ORDERED_VISIBILITIES;
        Integer num = map.get(first);
        Integer num2 = map.get(second);
        if (num == null || num2 == null || Intrinsics.areEqual(num, num2)) {
            return null;
        }
        return Integer.valueOf(num.intValue() - num2.intValue());
    }

    public final boolean isPrivate(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        return visibility == Private.INSTANCE || visibility == PrivateToThis.INSTANCE;
    }

    public final Public getDEFAULT_VISIBILITY() {
        return DEFAULT_VISIBILITY;
    }
}
