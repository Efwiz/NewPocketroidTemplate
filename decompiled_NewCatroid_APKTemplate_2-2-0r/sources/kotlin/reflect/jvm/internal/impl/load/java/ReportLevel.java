package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;

/* JADX INFO: compiled from: ReportLevel.kt */
/* JADX INFO: loaded from: classes12.dex */
public enum ReportLevel {
    IGNORE("ignore"),
    WARN(CommonCompilerArguments.WARN),
    STRICT(SchemaSymbols.ATTVAL_STRICT);

    public static final Companion Companion = new Companion(null);
    private final String description;

    ReportLevel(String str) {
        this.description = str;
    }

    public final String getDescription() {
        return this.description;
    }

    /* JADX INFO: compiled from: ReportLevel.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean isWarning() {
        return this == WARN;
    }

    public final boolean isIgnore() {
        return this == IGNORE;
    }
}
