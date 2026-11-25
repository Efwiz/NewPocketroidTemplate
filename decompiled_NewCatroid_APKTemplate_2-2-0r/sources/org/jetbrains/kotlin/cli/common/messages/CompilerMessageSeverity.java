package org.jetbrains.kotlin.cli.common.messages;

import java.util.EnumSet;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;

/* JADX INFO: loaded from: classes3.dex */
public enum CompilerMessageSeverity {
    EXCEPTION,
    ERROR,
    STRONG_WARNING,
    WARNING,
    INFO,
    LOGGING,
    OUTPUT;

    public static final EnumSet<CompilerMessageSeverity> VERBOSE = EnumSet.of(LOGGING);

    public boolean isError() {
        return this == EXCEPTION || this == ERROR;
    }

    public boolean isWarning() {
        return this == STRONG_WARNING || this == WARNING;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity;

        static {
            int[] iArr = new int[CompilerMessageSeverity.values().length];
            $SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity = iArr;
            try {
                iArr[CompilerMessageSeverity.EXCEPTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity[CompilerMessageSeverity.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity[CompilerMessageSeverity.STRONG_WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity[CompilerMessageSeverity.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity[CompilerMessageSeverity.INFO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity[CompilerMessageSeverity.LOGGING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity[CompilerMessageSeverity.OUTPUT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public String getPresentableName() {
        switch (AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity[ordinal()]) {
            case 1:
                return K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION;
            case 2:
                return "error";
            case 3:
            case 4:
                return CompilerOptions.WARNING;
            case 5:
                return CompilerOptions.INFO;
            case 6:
                return "logging";
            case 7:
                return "output";
            default:
                throw new UnsupportedOperationException("Unknown severity: " + this);
        }
    }
}
