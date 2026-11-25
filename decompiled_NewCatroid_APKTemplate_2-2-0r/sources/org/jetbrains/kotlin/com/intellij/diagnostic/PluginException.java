package org.jetbrains.kotlin.com.intellij.diagnostic;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import java.util.Collections;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.bouncycastle.i18n.ErrorBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;

/* JADX INFO: loaded from: classes3.dex */
public class PluginException extends RuntimeException implements ExceptionWithAttachments {
    private final List<Attachment> myAttachments;
    private final PluginId myPluginId;

    /* JADX WARN: Code duplicated, block: B:33:0x0064  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 7 || i == 8 || i == 9 || i == 12 || i == 15) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 7 || i == 8 || i == 9 || i == 12 || i == 15) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "e";
        } else if (i != 4) {
            switch (i) {
                case 6:
                    objArr[0] = "attachments";
                    break;
                case 7:
                case 8:
                case 9:
                case 12:
                case 15:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/diagnostic/PluginException";
                    break;
                case 10:
                case 17:
                    objArr[0] = MLAsrCaptureConstants.ASR_ERROR_MESSAGE;
                    break;
                case 11:
                case 14:
                case 18:
                    objArr[0] = "pluginClass";
                    break;
                case 13:
                    objArr[0] = "cause";
                    break;
                case 16:
                    objArr[0] = "logger";
                    break;
                case 19:
                    objArr[0] = "signature";
                    break;
                case 20:
                case 23:
                    objArr[0] = ErrorBundle.DETAIL_ENTRY;
                    break;
                case 21:
                    objArr[0] = "violator";
                    break;
                case 22:
                    objArr[0] = "methodName";
                    break;
                default:
                    objArr[0] = CrashHianalyticsData.MESSAGE;
                    break;
            }
        } else {
            objArr[0] = "attachments";
        }
        if (i == 7 || i == 8) {
            objArr[1] = "getMessage";
        } else if (i == 9) {
            objArr[1] = "getAttachments";
        } else if (i == 12 || i == 15) {
            objArr[1] = "createByClass";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/diagnostic/PluginException";
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 12:
            case 15:
                break;
            case 10:
            case 11:
            case 13:
            case 14:
                objArr[2] = "createByClass";
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "logPluginError";
                break;
            case 19:
            case 20:
                objArr[2] = "reportDeprecatedUsage";
                break;
            case 21:
            case 22:
            case 23:
                objArr[2] = "reportDeprecatedDefault";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 8 && i != 9 && i != 12 && i != 15) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PluginException(String str, Throwable th, PluginId pluginId) {
        super(str, th);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myPluginId = pluginId;
        this.myAttachments = Collections.emptyList();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PluginException(Throwable th, PluginId pluginId) {
        super(th.getMessage(), th);
        if (th == null) {
            $$$reportNull$$$0(1);
        }
        this.myPluginId = pluginId;
        this.myAttachments = Collections.emptyList();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PluginException(String str, PluginId pluginId) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        this.myPluginId = pluginId;
        this.myAttachments = Collections.emptyList();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String message = super.getMessage();
        if (this.myPluginId == null || (message != null && message.contains("PluginDescriptor("))) {
            if (message == null) {
                $$$reportNull$$$0(7);
            }
            return message;
        }
        StringBuilder sb = new StringBuilder();
        if (message == null) {
            message = "null";
        }
        String string = sb.append(message).append(" [Plugin: ").append(this.myPluginId).append("]").toString();
        if (string == null) {
            $$$reportNull$$$0(8);
        }
        return string;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments
    public final Attachment[] getAttachments() {
        Attachment[] attachmentArr = (Attachment[]) this.myAttachments.toArray(Attachment.EMPTY_ARRAY);
        if (attachmentArr == null) {
            $$$reportNull$$$0(9);
        }
        return attachmentArr;
    }

    public static PluginException createByClass(String str, Throwable th, Class<?> cls) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (cls == null) {
            $$$reportNull$$$0(11);
        }
        PluginException pluginExceptionCreatePluginExceptionByClass = PluginProblemReporter.getInstance().createPluginExceptionByClass(str, th, cls);
        if (pluginExceptionCreatePluginExceptionByClass == null) {
            $$$reportNull$$$0(12);
        }
        return pluginExceptionCreatePluginExceptionByClass;
    }

    public static PluginException createByClass(Throwable th, Class<?> cls) {
        if (th == null) {
            $$$reportNull$$$0(13);
        }
        if (cls == null) {
            $$$reportNull$$$0(14);
        }
        String message = th.getMessage();
        PluginProblemReporter pluginProblemReporter = PluginProblemReporter.getInstance();
        if (message == null) {
            message = "";
        }
        PluginException pluginExceptionCreatePluginExceptionByClass = pluginProblemReporter.createPluginExceptionByClass(message, th, cls);
        if (pluginExceptionCreatePluginExceptionByClass == null) {
            $$$reportNull$$$0(15);
        }
        return pluginExceptionCreatePluginExceptionByClass;
    }

    public static void logPluginError(Logger logger, String str, Throwable th, Class<?> cls) {
        if (logger == null) {
            $$$reportNull$$$0(16);
        }
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        if (cls == null) {
            $$$reportNull$$$0(18);
        }
        logger.error((Throwable) createByClass(str, th, cls));
    }

    public static void reportDeprecatedUsage(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        if (str2 == null) {
            $$$reportNull$$$0(20);
        }
        Logger.getInstance((Class<?>) PluginException.class).error(FormatHelper.QUOTE + str + "' is deprecated and going to be removed soon. " + str2);
    }
}
