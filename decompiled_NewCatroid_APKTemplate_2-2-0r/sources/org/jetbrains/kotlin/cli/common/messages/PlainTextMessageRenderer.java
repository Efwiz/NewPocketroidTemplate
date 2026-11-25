package org.jetbrains.kotlin.cli.common.messages;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.EnumSet;
import java.util.Set;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;
import org.jetbrains.kotlin.cli.common.PropertiesKt;
import org.jetbrains.kotlin.org.fusesource.jansi.Ansi;
import org.jetbrains.kotlin.org.fusesource.jansi.AnsiConsole;
import org.jetbrains.kotlin.org.fusesource.jansi.internal.CLibrary;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PlainTextMessageRenderer implements MessageRenderer {
    private static final Set<CompilerMessageSeverity> IMPORTANT_MESSAGE_SEVERITIES;
    private static final boolean IS_STDERR_A_TTY;
    private static final String LINE_SEPARATOR;
    private final boolean colorEnabled;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                i2 = 2;
                break;
            case 6:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/cli/common/messages/PlainTextMessageRenderer";
                break;
            case 6:
            default:
                objArr[0] = "severity";
                break;
            case 14:
                objArr[0] = "usage";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
                objArr[1] = "decapitalizeIfNeeded";
                break;
            case 6:
            default:
                objArr[1] = "org/jetbrains/kotlin/cli/common/messages/PlainTextMessageRenderer";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[1] = "severityColor";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "decapitalizeIfNeeded";
                break;
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            case 6:
                objArr[2] = "severityColor";
                break;
            case 14:
                objArr[2] = "renderUsage";
                break;
            default:
                objArr[2] = "render";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                throw new IllegalStateException(str2);
            case 6:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    protected abstract String getPath(CompilerMessageSourceLocation compilerMessageSourceLocation);

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String renderUsage(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        return str;
    }

    static {
        boolean z = false;
        if (!PropertiesKt.isWindows() && "true".equals(CompilerSystemProperties.KOTLIN_COLORS_ENABLED_PROPERTY.getValue())) {
            try {
                if (CLibrary.isatty(CLibrary.STDERR_FILENO) != 0) {
                    z = true;
                }
            } catch (UnsatisfiedLinkError unused) {
            }
        }
        IS_STDERR_A_TTY = z;
        LINE_SEPARATOR = System.lineSeparator();
        IMPORTANT_MESSAGE_SEVERITIES = EnumSet.of(CompilerMessageSeverity.EXCEPTION, CompilerMessageSeverity.ERROR, CompilerMessageSeverity.STRONG_WARNING, CompilerMessageSeverity.WARNING);
    }

    public PlainTextMessageRenderer() {
        this(IS_STDERR_A_TTY);
    }

    public PlainTextMessageRenderer(boolean z) {
        this.colorEnabled = z;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String renderPreamble() {
        return "";
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String render(CompilerMessageSeverity compilerMessageSeverity, String str, CompilerMessageSourceLocation compilerMessageSourceLocation) {
        if (compilerMessageSeverity == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        StringBuilder sb = new StringBuilder();
        int line = compilerMessageSourceLocation != null ? compilerMessageSourceLocation.getLine() : -1;
        int column = compilerMessageSourceLocation != null ? compilerMessageSourceLocation.getColumn() : -1;
        String lineContent = compilerMessageSourceLocation != null ? compilerMessageSourceLocation.getLineContent() : null;
        String path = compilerMessageSourceLocation != null ? getPath(compilerMessageSourceLocation) : null;
        if (path != null) {
            sb.append(path);
            sb.append(":");
            if (line > 0) {
                sb.append(line).append(":");
                if (column > 0) {
                    sb.append(column).append(":");
                }
            }
            sb.append(" ");
        }
        if (this.colorEnabled) {
            Ansi ansiReset = Ansi.ansi().bold().fg(severityColor(compilerMessageSeverity)).a(compilerMessageSeverity.getPresentableName()).a(": ").reset();
            if (IMPORTANT_MESSAGE_SEVERITIES.contains(compilerMessageSeverity)) {
                ansiReset.bold();
            }
            String strDecapitalizeIfNeeded = decapitalizeIfNeeded(str);
            int iIndexOf = strDecapitalizeIfNeeded.indexOf(LINE_SEPARATOR);
            if (iIndexOf < 0) {
                sb.append(ansiReset.a(strDecapitalizeIfNeeded).reset());
            } else {
                sb.append(ansiReset.a(strDecapitalizeIfNeeded.substring(0, iIndexOf)).reset().a(strDecapitalizeIfNeeded.substring(iIndexOf)));
            }
        } else {
            sb.append(compilerMessageSeverity.getPresentableName());
            sb.append(": ");
            sb.append(decapitalizeIfNeeded(str));
        }
        if (lineContent != null && 1 <= column && column <= lineContent.length() + 1) {
            String str2 = LINE_SEPARATOR;
            sb.append(str2);
            sb.append(lineContent);
            sb.append(str2);
            sb.append(StringsKt.repeat(" ", column - 1));
            sb.append("^");
        }
        return sb.toString();
    }

    private static String decapitalizeIfNeeded(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (str.startsWith("Java") || str.startsWith("Kotlin")) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            return str;
        }
        if (str.length() >= 2 && Character.isUpperCase(str.charAt(0)) && Character.isUpperCase(str.charAt(1))) {
            if (str == null) {
                $$$reportNull$$$0(4);
            }
            return str;
        }
        String strDecapitalizeAsciiOnly = CapitalizeDecapitalizeKt.decapitalizeAsciiOnly(str);
        if (strDecapitalizeAsciiOnly == null) {
            $$$reportNull$$$0(5);
        }
        return strDecapitalizeAsciiOnly;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.common.messages.PlainTextMessageRenderer$1, reason: invalid class name */
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

    private static Ansi.Color severityColor(CompilerMessageSeverity compilerMessageSeverity) {
        if (compilerMessageSeverity == null) {
            $$$reportNull$$$0(6);
        }
        switch (AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$cli$common$messages$CompilerMessageSeverity[compilerMessageSeverity.ordinal()]) {
            case 1:
                Ansi.Color color = Ansi.Color.RED;
                if (color == null) {
                    $$$reportNull$$$0(7);
                }
                return color;
            case 2:
                Ansi.Color color2 = Ansi.Color.RED;
                if (color2 == null) {
                    $$$reportNull$$$0(8);
                }
                return color2;
            case 3:
                Ansi.Color color3 = Ansi.Color.YELLOW;
                if (color3 == null) {
                    $$$reportNull$$$0(9);
                }
                return color3;
            case 4:
                Ansi.Color color4 = Ansi.Color.YELLOW;
                if (color4 == null) {
                    $$$reportNull$$$0(10);
                }
                return color4;
            case 5:
                Ansi.Color color5 = Ansi.Color.BLUE;
                if (color5 == null) {
                    $$$reportNull$$$0(11);
                }
                return color5;
            case 6:
                Ansi.Color color6 = Ansi.Color.BLUE;
                if (color6 == null) {
                    $$$reportNull$$$0(12);
                }
                return color6;
            case 7:
                Ansi.Color color7 = Ansi.Color.BLUE;
                if (color7 == null) {
                    $$$reportNull$$$0(13);
                }
                return color7;
            default:
                throw new UnsupportedOperationException("Unknown severity: " + compilerMessageSeverity);
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String renderConclusion() {
        return "";
    }

    public void enableColorsIfNeeded() {
        if (this.colorEnabled) {
            AnsiConsole.systemInstall();
        }
    }

    public void disableColorsIfNeeded() {
        if (this.colorEnabled) {
            AnsiConsole.systemUninstall();
        }
    }
}
