package org.jetbrains.kotlin.cli.common.messages;

import com.android.dx.rop.code.RegisterSpec;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import io.netty.util.internal.StringUtil;
import java.io.File;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: GradleStyleMessagerRenderer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\"\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016J\b\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004H\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/GradleStyleMessageRenderer;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageRenderer;", "()V", "getName", "", "render", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "renderConclusion", "renderPreamble", "renderUsage", "usage", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GradleStyleMessageRenderer implements MessageRenderer {

    /* JADX INFO: compiled from: GradleStyleMessagerRenderer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CompilerMessageSeverity.values().length];
            try {
                iArr[CompilerMessageSeverity.WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CompilerMessageSeverity.STRONG_WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CompilerMessageSeverity.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CompilerMessageSeverity.EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[CompilerMessageSeverity.LOGGING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[CompilerMessageSeverity.OUTPUT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[CompilerMessageSeverity.INFO.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String renderUsage(String usage) {
        Intrinsics.checkNotNullParameter(usage, "usage");
        return usage;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String render(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location) {
        String str;
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        switch (WhenMappings.$EnumSwitchMapping$0[severity.ordinal()]) {
            case 1:
            case 2:
                str = "w";
                break;
            case 3:
            case 4:
                str = "e";
                break;
            case 5:
            case 6:
                str = RegisterSpec.PREFIX;
                break;
            case 7:
                str = ASTExpr.DEFAULT_INDEX_VARIABLE_NAME;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.concat(": "));
        if (location != null) {
            sb.append(String.valueOf(new File(location.getPath()).toPath().toUri()));
            if (location.getLine() > 0 && location.getColumn() > 0) {
                sb.append(":" + location.getLine() + ':' + location.getColumn() + StringUtil.SPACE);
            }
        }
        sb.append(message);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String renderPreamble() {
        return "";
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String renderConclusion() {
        return "";
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageRenderer
    public String getName() {
        return "GradleStyle";
    }
}
