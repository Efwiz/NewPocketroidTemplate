package org.jetbrains.kotlin.fir.expressions;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.EnumSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v8 org.jetbrains.kotlin.fir.expressions.FirOperation, still in use, count: 1, list:
  (r0v8 org.jetbrains.kotlin.fir.expressions.FirOperation) from 0x0112: INVOKE (r0v10 java.util.EnumSet) = 
  (r0v8 org.jetbrains.kotlin.fir.expressions.FirOperation)
  (wrap org.jetbrains.kotlin.fir.expressions.FirOperation[]:0x010c: FILLED_NEW_ARRAY 
  (r1v9 org.jetbrains.kotlin.fir.expressions.FirOperation)
  (r2v10 org.jetbrains.kotlin.fir.expressions.FirOperation)
  (r3v11 org.jetbrains.kotlin.fir.expressions.FirOperation)
  (r4v3 org.jetbrains.kotlin.fir.expressions.FirOperation)
  (r5v3 org.jetbrains.kotlin.fir.expressions.FirOperation)
 A[WRAPPED] elemType: org.jetbrains.kotlin.fir.expressions.FirOperation)
 STATIC call: java.util.EnumSet.of(java.lang.Enum, java.lang.Enum[]):java.util.EnumSet A[MD:<E extends java.lang.Enum<E>>:(E extends java.lang.Enum<E>, E extends java.lang.Enum<E>[]):java.util.EnumSet<E extends java.lang.Enum<E>> VARARG (c), VARARG_CALL] (LINE:39)
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
/* JADX INFO: compiled from: FirOperation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0018\b\u0086\u0081\u0002\u0018\u0000 \u001a2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001aB\u0011\b\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "", "operator", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getOperator", "()Ljava/lang/String;", "EQ", "NOT_EQ", "IDENTITY", "NOT_IDENTITY", "LT", "GT", "LT_EQ", "GT_EQ", "ASSIGN", "PLUS_ASSIGN", "MINUS_ASSIGN", "TIMES_ASSIGN", "DIV_ASSIGN", "REM_ASSIGN", "IS", "NOT_IS", "AS", "SAFE_AS", "OTHER", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOperation {
    EQ("=="),
    NOT_EQ("!="),
    IDENTITY("==="),
    NOT_IDENTITY("!=="),
    LT("<"),
    GT(">"),
    LT_EQ("<="),
    GT_EQ(">="),
    ASSIGN(ContainerUtils.KEY_VALUE_DELIMITER),
    PLUS_ASSIGN("+="),
    MINUS_ASSIGN("-="),
    TIMES_ASSIGN("*="),
    DIV_ASSIGN("/="),
    REM_ASSIGN("%="),
    IS(LanguageCodeUtil.IS),
    NOT_IS("!is"),
    AS("as"),
    SAFE_AS("as?"),
    OTHER(null, 1, null);

    private static final Set<FirOperation> ASSIGNMENTS;
    private static final Set<FirOperation> TYPES;
    private final String operator;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<FirOperation> getEntries() {
        return $ENTRIES;
    }

    public static FirOperation valueOf(String str) {
        return (FirOperation) Enum.valueOf(FirOperation.class, str);
    }

    public static FirOperation[] values() {
        return (FirOperation[]) $VALUES.clone();
    }

    private FirOperation(String str) {
        super(str, i);
        this.operator = str;
    }

    /* synthetic */ FirOperation(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "???" : str);
    }

    public final String getOperator() {
        return this.operator;
    }

    static {
        EnumSet enumSetOf = EnumSet.of(new FirOperation(ContainerUtils.KEY_VALUE_DELIMITER), new FirOperation("+="), new FirOperation("-="), new FirOperation("*="), new FirOperation("/="), new FirOperation("%="));
        Intrinsics.checkNotNullExpressionValue(enumSetOf, "of(...)");
        ASSIGNMENTS = enumSetOf;
        EnumSet enumSetOf2 = EnumSet.of(new FirOperation(LanguageCodeUtil.IS), new FirOperation("!is"), new FirOperation("as"), new FirOperation("as?"));
        Intrinsics.checkNotNullExpressionValue(enumSetOf2, "of(...)");
        TYPES = enumSetOf2;
    }

    /* JADX INFO: compiled from: FirOperation.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirOperation$Companion;", "", "()V", "ASSIGNMENTS", "", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "getASSIGNMENTS", "()Ljava/util/Set;", "TYPES", "getTYPES", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<FirOperation> getASSIGNMENTS() {
            return FirOperation.ASSIGNMENTS;
        }

        public final Set<FirOperation> getTYPES() {
            return FirOperation.TYPES;
        }
    }
}
