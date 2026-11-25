package org.jetbrains.kotlin.cli.common;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty1;
import kotlin.reflect.jvm.ReflectJvmMapping;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.arguments.Argument;
import org.jetbrains.kotlin.cli.common.arguments.CommonToolArguments;
import org.jetbrains.kotlin.cli.common.arguments.ParseCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfo;

/* JADX INFO: loaded from: classes3.dex */
public class Usage {
    public static final String BAT_DELIMITER_CHARACTERS_NOTE = "Note: on Windows, arguments that contain delimiter characters (whitespace, =, ;, ,) need to be surrounded with double quotes (\").";

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "arguments";
                break;
            case 2:
                objArr[0] = "org/jetbrains/kotlin/cli/common/Usage";
                break;
            case 3:
            case 5:
            case 6:
            case 7:
                objArr[0] = "sb";
                break;
            case 4:
                objArr[0] = "property";
                break;
            case 8:
                objArr[0] = "string";
                break;
            default:
                objArr[0] = "tool";
                break;
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/cli/common/Usage";
        } else {
            objArr[1] = "render";
        }
        switch (i) {
            case 2:
                break;
            case 3:
            case 4:
                objArr[2] = "propertyUsage";
                break;
            case 5:
                objArr[2] = "renderOptionJUsage";
                break;
            case 6:
                objArr[2] = "renderArgfileUsage";
                break;
            case 7:
            case 8:
                objArr[2] = "appendln";
                break;
            default:
                objArr[2] = "render";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public static <A extends CommonToolArguments> String render(CLITool<A> cLITool, A a) {
        if (cLITool == null) {
            $$$reportNull$$$0(0);
        }
        if (a == null) {
            $$$reportNull$$$0(1);
        }
        boolean extraHelp = a.getExtraHelp();
        StringBuilder sb = new StringBuilder();
        appendln(sb, "Usage: " + cLITool.executableScriptFileName() + " <options> <source files>");
        appendln(sb, "where " + (extraHelp ? "advanced" : "possible") + " options include:");
        for (KCallable<?> kCallable : JvmClassMappingKt.getKotlinClass(a.getClass()).getMembers()) {
            if (kCallable instanceof KProperty1) {
                propertyUsage(sb, (KProperty1) kCallable, extraHelp);
            }
        }
        if (extraHelp) {
            appendln(sb, "");
            appendln(sb, "Advanced options are non-standard and may be changed or removed without any notice.");
        } else {
            renderOptionJUsage(sb);
            renderArgfileUsage(sb);
        }
        if (SystemInfo.isWindows) {
            appendln(sb, "");
            appendln(sb, BAT_DELIMITER_CHARACTERS_NOTE);
        }
        if (!extraHelp && (cLITool instanceof CLICompiler)) {
            appendln(sb, "");
            appendln(sb, "For details, see https://kotl.in/cli");
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(2);
        }
        return string;
    }

    private static void propertyUsage(StringBuilder sb, KProperty1<?, ?> kProperty1, boolean z) {
        if (sb == null) {
            $$$reportNull$$$0(3);
        }
        if (kProperty1 == null) {
            $$$reportNull$$$0(4);
        }
        Argument argument = (Argument) ReflectJvmMapping.getJavaField(kProperty1).getAnnotation(Argument.class);
        if (argument != null && z == ParseCommandLineArgumentsKt.isAdvanced(argument)) {
            int length = sb.length();
            sb.append("  ");
            sb.append(argument.value());
            if (!argument.shortName().isEmpty()) {
                sb.append(" (");
                sb.append(argument.shortName());
                sb.append(Constant.AFTER_QUTO);
            }
            if (!argument.valueDescription().isEmpty()) {
                sb.append(ParseCommandLineArgumentsKt.isAdvanced(argument) ? ContainerUtils.KEY_VALUE_DELIMITER : " ");
                sb.append(argument.valueDescription());
            }
            int length2 = length + 28;
            if (sb.length() >= length + 33) {
                sb.append("\n");
                length2 += sb.length() - length;
            }
            while (sb.length() < length2) {
                sb.append(" ");
            }
            sb.append(" ");
            appendln(sb, argument.description().replace("\n", "\n" + StringsKt.repeat(" ", 29)));
        }
    }

    private static void renderOptionJUsage(StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(5);
        }
        int length = sb.length() + 29;
        sb.append("  -J<option>");
        while (sb.length() < length) {
            sb.append(" ");
        }
        appendln(sb, "Pass an option directly to JVM");
    }

    private static void renderArgfileUsage(StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(6);
        }
        int length = sb.length() + 29;
        sb.append("  ");
        sb.append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
        sb.append("<argfile>");
        while (sb.length() < length) {
            sb.append(" ");
        }
        appendln(sb, "Read compiler arguments and file paths from the given file");
    }

    private static void appendln(StringBuilder sb, String str) {
        if (sb == null) {
            $$$reportNull$$$0(7);
        }
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        sb.append(str);
        sb.append('\n');
    }
}
