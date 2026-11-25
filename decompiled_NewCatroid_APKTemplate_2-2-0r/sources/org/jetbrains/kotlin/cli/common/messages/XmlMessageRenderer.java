package org.jetbrains.kotlin.cli.common.messages;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public class XmlMessageRenderer implements MessageRenderer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        } else if (i != 2) {
            objArr[0] = "severity";
        } else {
            objArr[0] = "usage";
        }
        objArr[1] = "org/jetbrains/kotlin/cli/common/messages/XmlMessageRenderer";
        if (i != 2) {
            objArr[2] = "render";
        } else {
            objArr[2] = "renderUsage";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String renderPreamble() {
        return "<MESSAGES>";
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String render(CompilerMessageSeverity compilerMessageSeverity, String str, CompilerMessageSourceLocation compilerMessageSourceLocation) {
        if (compilerMessageSeverity == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        StringBuilder sb = new StringBuilder("<");
        String presentableName = compilerMessageSeverity.getPresentableName();
        sb.append(presentableName);
        if (compilerMessageSourceLocation != null) {
            sb.append(" path=\"").append(e(compilerMessageSourceLocation.getPath())).append("\" line=\"");
            sb.append(compilerMessageSourceLocation.getLine()).append("\" column=\"");
            sb.append(compilerMessageSourceLocation.getColumn()).append("\"");
        }
        sb.append(">");
        sb.append(e(str));
        sb.append("</").append(presentableName).append(">\n");
        return sb.toString();
    }

    private static String e(String str) {
        return StringUtil.escapeXmlEntities(str);
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String renderUsage(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return render(CompilerMessageSeverity.STRONG_WARNING, str, null);
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String renderConclusion() {
        return "</MESSAGES>";
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String getName() {
        return "XML";
    }
}
