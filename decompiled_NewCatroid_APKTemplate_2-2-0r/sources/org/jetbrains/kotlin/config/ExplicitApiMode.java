package org.jetbrains.kotlin.config;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: compiled from: ExplicitApiMode.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/config/ExplicitApiMode;", "", "state", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getState", "()Ljava/lang/String;", "DISABLED", "STRICT", "WARNING", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ExplicitApiMode {
    DISABLED("disable"),
    STRICT(SchemaSymbols.ATTVAL_STRICT),
    WARNING(CompilerOptions.WARNING);

    private final String state;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<ExplicitApiMode> getEntries() {
        return $ENTRIES;
    }

    ExplicitApiMode(String str) {
        this.state = str;
    }

    public final String getState() {
        return this.state;
    }

    /* JADX INFO: compiled from: ExplicitApiMode.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0004¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/config/ExplicitApiMode$Companion;", "", "()V", "availableValues", "", "fromString", "Lorg/jetbrains/kotlin/config/ExplicitApiMode;", "string", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ExplicitApiMode fromString(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            for (ExplicitApiMode explicitApiMode : ExplicitApiMode.values()) {
                if (Intrinsics.areEqual(explicitApiMode.getState(), string)) {
                    return explicitApiMode;
                }
            }
            return null;
        }

        public final String availableValues() {
            return ArraysKt.joinToString$default(ExplicitApiMode.values(), (CharSequence) null, "{", "}", 0, (CharSequence) null, new Function1<ExplicitApiMode, CharSequence>() { // from class: org.jetbrains.kotlin.config.ExplicitApiMode$Companion$availableValues$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(ExplicitApiMode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getState();
                }
            }, 25, (Object) null);
        }
    }
}
