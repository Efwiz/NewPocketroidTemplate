package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: WhenMissingCase.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0006\b\t\n\u000b\f\rB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\u0004H\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0006\u000e\u000f\u0010\u0011\u0012\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "", "()V", "branchConditionText", "", "getBranchConditionText", "()Ljava/lang/String;", "toString", "BooleanIsMissing", "ConditionTypeIsExpect", "EnumCheckIsMissing", "IsTypeCheckIsMissing", "NullIsMissing", "Unknown", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$BooleanIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$EnumCheckIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$IsTypeCheckIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$NullIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$Unknown;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WhenMissingCase {
    public /* synthetic */ WhenMissingCase(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String getBranchConditionText();

    private WhenMissingCase() {
    }

    /* JADX INFO: compiled from: WhenMissingCase.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\u0004H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$Unknown;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "()V", "branchConditionText", "", "getBranchConditionText", "()Ljava/lang/String;", "toString", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Unknown extends WhenMissingCase {
        public static final Unknown INSTANCE = new Unknown();
        private static final String branchConditionText = PsiKeyword.ELSE;

        private Unknown() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String toString() {
            return "unknown";
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String getBranchConditionText() {
            return branchConditionText;
        }
    }

    /* JADX INFO: compiled from: WhenMissingCase.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\n\u000b\fB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\u0003H\u0016R\u0014\u0010\u0005\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u0082\u0001\u0003\r\u000e\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "typeOfDeclaration", "", "(Ljava/lang/String;)V", "branchConditionText", "getBranchConditionText", "()Ljava/lang/String;", "getTypeOfDeclaration", "toString", "Enum", "SealedClass", "SealedInterface", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect$Enum;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect$SealedClass;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect$SealedInterface;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ConditionTypeIsExpect extends WhenMissingCase {
        private final String branchConditionText;
        private final String typeOfDeclaration;

        public /* synthetic */ ConditionTypeIsExpect(String str, DefaultConstructorMarker defaultConstructorMarker) {
            this(str);
        }

        /* JADX INFO: compiled from: WhenMissingCase.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect$SealedClass;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SealedClass extends ConditionTypeIsExpect {
            public static final SealedClass INSTANCE = new SealedClass();

            private SealedClass() {
                super("sealed class", null);
            }
        }

        private ConditionTypeIsExpect(String str) {
            super(null);
            this.typeOfDeclaration = str;
            this.branchConditionText = PsiKeyword.ELSE;
        }

        public final String getTypeOfDeclaration() {
            return this.typeOfDeclaration;
        }

        /* JADX INFO: compiled from: WhenMissingCase.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect$SealedInterface;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SealedInterface extends ConditionTypeIsExpect {
            public static final SealedInterface INSTANCE = new SealedInterface();

            private SealedInterface() {
                super("sealed interface", null);
            }
        }

        /* JADX INFO: compiled from: WhenMissingCase.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect$Enum;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$ConditionTypeIsExpect;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Enum extends ConditionTypeIsExpect {
            public static final Enum INSTANCE = new Enum();

            private Enum() {
                super(PsiKeyword.ENUM, null);
            }
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String getBranchConditionText() {
            return this.branchConditionText;
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String toString() {
            return "unknown";
        }
    }

    /* JADX INFO: compiled from: WhenMissingCase.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$NullIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "()V", "branchConditionText", "", "getBranchConditionText", "()Ljava/lang/String;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NullIsMissing extends WhenMissingCase {
        public static final NullIsMissing INSTANCE = new NullIsMissing();
        private static final String branchConditionText = "null";

        private NullIsMissing() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String getBranchConditionText() {
            return branchConditionText;
        }
    }

    /* JADX INFO: compiled from: WhenMissingCase.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u000b\fB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0001\u0002\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$BooleanIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "value", "", "(Z)V", "branchConditionText", "", "getBranchConditionText", "()Ljava/lang/String;", "getValue", "()Z", "FalseIsMissing", "TrueIsMissing", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$BooleanIsMissing$FalseIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$BooleanIsMissing$TrueIsMissing;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class BooleanIsMissing extends WhenMissingCase {
        private final String branchConditionText;
        private final boolean value;

        public /* synthetic */ BooleanIsMissing(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(z);
        }

        /* JADX INFO: compiled from: WhenMissingCase.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$BooleanIsMissing$TrueIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$BooleanIsMissing;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class TrueIsMissing extends BooleanIsMissing {
            public static final TrueIsMissing INSTANCE = new TrueIsMissing();

            private TrueIsMissing() {
                super(true, null);
            }
        }

        private BooleanIsMissing(boolean z) {
            super(null);
            this.value = z;
            this.branchConditionText = String.valueOf(z);
        }

        public final boolean getValue() {
            return this.value;
        }

        /* JADX INFO: compiled from: WhenMissingCase.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$BooleanIsMissing$FalseIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$BooleanIsMissing;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FalseIsMissing extends BooleanIsMissing {
            public static final FalseIsMissing INSTANCE = new FalseIsMissing();

            private FalseIsMissing() {
                super(false, null);
            }
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String getBranchConditionText() {
            return this.branchConditionText;
        }
    }

    /* JADX INFO: compiled from: WhenMissingCase.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\bH\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$IsTypeCheckIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "isSingleton", "", "(Lorg/jetbrains/kotlin/name/ClassId;Z)V", "branchConditionText", "", "getBranchConditionText", "()Ljava/lang/String;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "()Z", "toString", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IsTypeCheckIsMissing extends WhenMissingCase {
        private final String branchConditionText;
        private final ClassId classId;
        private final boolean isSingleton;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IsTypeCheckIsMissing(ClassId classId, boolean z) {
            super(null);
            Intrinsics.checkNotNullParameter(classId, "classId");
            this.classId = classId;
            this.isSingleton = z;
            String string = this.classId.asSingleFqName().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            this.branchConditionText = this.isSingleton ? string : "is " + string;
        }

        public final ClassId getClassId() {
            return this.classId;
        }

        /* JADX INFO: renamed from: isSingleton, reason: from getter */
        public final boolean getIsSingleton() {
            return this.isSingleton;
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String getBranchConditionText() {
            return this.branchConditionText;
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String toString() {
            Name shortClassName = this.classId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            String strAsString = shortClassName.isSpecial() ? shortClassName.asString() : shortClassName.getIdentifier();
            Intrinsics.checkNotNull(strAsString);
            return this.isSingleton ? strAsString : "is " + strAsString;
        }
    }

    /* JADX INFO: compiled from: WhenMissingCase.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase$EnumCheckIsMissing;", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "branchConditionText", "", "getBranchConditionText", "()Ljava/lang/String;", "getCallableId", "()Lorg/jetbrains/kotlin/name/CallableId;", "toString", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EnumCheckIsMissing extends WhenMissingCase {
        private final String branchConditionText;
        private final CallableId callableId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EnumCheckIsMissing(CallableId callableId) {
            super(null);
            Intrinsics.checkNotNullParameter(callableId, "callableId");
            this.callableId = callableId;
            String string = callableId.asSingleFqName().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            this.branchConditionText = string;
        }

        public final CallableId getCallableId() {
            return this.callableId;
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String getBranchConditionText() {
            return this.branchConditionText;
        }

        @Override // org.jetbrains.kotlin.diagnostics.WhenMissingCase
        public String toString() {
            String identifier = this.callableId.getCallableName().getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            return identifier;
        }
    }

    public String toString() {
        return getBranchConditionText();
    }
}
