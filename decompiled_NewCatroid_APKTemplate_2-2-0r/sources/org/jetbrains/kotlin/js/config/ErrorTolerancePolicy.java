package org.jetbrains.kotlin.js.config;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 org.jetbrains.kotlin.js.config.ErrorTolerancePolicy, still in use, count: 1, list:
  (r0v0 org.jetbrains.kotlin.js.config.ErrorTolerancePolicy) from 0x0034: SPUT (r0v0 org.jetbrains.kotlin.js.config.ErrorTolerancePolicy) (LINE:18) org.jetbrains.kotlin.js.config.ErrorTolerancePolicy.DEFAULT org.jetbrains.kotlin.js.config.ErrorTolerancePolicy
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:257)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:187)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: ErrorTolerancePolicy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u0000 \u000e2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000eB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy;", "", "allowSyntaxErrors", "", "allowSemanticErrors", "(Ljava/lang/String;IZZ)V", "allowErrors", "getAllowErrors", "()Z", "getAllowSemanticErrors", "getAllowSyntaxErrors", "NONE", "SEMANTIC", "ALL", "Companion", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorTolerancePolicy {
    NONE(false, false),
    SEMANTIC(false, true),
    ALL(true, true);

    private final boolean allowSemanticErrors;
    private final boolean allowSyntaxErrors;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ErrorTolerancePolicy DEFAULT = new ErrorTolerancePolicy(false, false);

    public static EnumEntries<ErrorTolerancePolicy> getEntries() {
        return $ENTRIES;
    }

    public static ErrorTolerancePolicy valueOf(String str) {
        return (ErrorTolerancePolicy) Enum.valueOf(ErrorTolerancePolicy.class, str);
    }

    public static ErrorTolerancePolicy[] values() {
        return (ErrorTolerancePolicy[]) $VALUES.clone();
    }

    private ErrorTolerancePolicy(boolean z, boolean z2) {
        super(str, i);
        this.allowSyntaxErrors = z;
        this.allowSemanticErrors = z2;
    }

    public final boolean getAllowSemanticErrors() {
        return this.allowSemanticErrors;
    }

    public final boolean getAllowSyntaxErrors() {
        return this.allowSyntaxErrors;
    }

    static {
    }

    public final boolean getAllowErrors() {
        return this.allowSyntaxErrors || this.allowSemanticErrors;
    }

    /* JADX INFO: compiled from: ErrorTolerancePolicy.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy;", "getDEFAULT", "()Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy;", "resolvePolicy", ASTExpr.DEFAULT_MAP_KEY_NAME, "", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ErrorTolerancePolicy getDEFAULT() {
            return ErrorTolerancePolicy.DEFAULT;
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x0034, code lost:
        
            if (r0.equals("ALL") != false) goto L16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x003d, code lost:
        
            if (r0.equals("SYNTAX") != false) goto L16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0041, code lost:
        
            return org.jetbrains.kotlin.js.config.ErrorTolerancePolicy.ALL;
         */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final org.jetbrains.kotlin.js.config.ErrorTolerancePolicy resolvePolicy(java.lang.String r4) {
            /*
                r3 = this;
                java.lang.String r0 = "key"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
                java.util.Locale r0 = java.util.Locale.ROOT
                java.lang.String r0 = r4.toUpperCase(r0)
                java.lang.String r1 = "toUpperCase(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
                int r1 = r0.hashCode()
                switch(r1) {
                    case -1834147869: goto L37;
                    case 64897: goto L2e;
                    case 2402104: goto L23;
                    case 1999461830: goto L18;
                    default: goto L17;
                }
            L17:
                goto L42
            L18:
                java.lang.String r1 = "SEMANTIC"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L42
                org.jetbrains.kotlin.js.config.ErrorTolerancePolicy r4 = org.jetbrains.kotlin.js.config.ErrorTolerancePolicy.SEMANTIC
                goto L41
            L23:
                java.lang.String r1 = "NONE"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L42
                org.jetbrains.kotlin.js.config.ErrorTolerancePolicy r4 = org.jetbrains.kotlin.js.config.ErrorTolerancePolicy.NONE
                goto L41
            L2e:
                java.lang.String r1 = "ALL"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L42
                goto L3f
            L37:
                java.lang.String r1 = "SYNTAX"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L42
            L3f:
                org.jetbrains.kotlin.js.config.ErrorTolerancePolicy r4 = org.jetbrains.kotlin.js.config.ErrorTolerancePolicy.ALL
            L41:
                return r4
            L42:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = "Unknown error tolerance policy '"
                r1.<init>(r2)
                java.lang.StringBuilder r4 = r1.append(r4)
                r1 = 39
                java.lang.StringBuilder r4 = r4.append(r1)
                java.lang.String r4 = r4.toString()
                java.lang.String r4 = r4.toString()
                r0.<init>(r4)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.js.config.ErrorTolerancePolicy.Companion.resolvePolicy(java.lang.String):org.jetbrains.kotlin.js.config.ErrorTolerancePolicy");
        }
    }
}
