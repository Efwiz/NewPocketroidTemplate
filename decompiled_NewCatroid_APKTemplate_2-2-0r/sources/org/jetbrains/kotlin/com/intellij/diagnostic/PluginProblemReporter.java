package org.jetbrains.kotlin.com.intellij.diagnostic;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
interface PluginProblemReporter {
    PluginException createPluginExceptionByClass(String str, Throwable th, Class cls);

    static PluginProblemReporter getInstance() {
        if (ApplicationManager.getApplication() == null) {
            return new PluginProblemReporter() { // from class: org.jetbrains.kotlin.com.intellij.diagnostic.PluginProblemReporter.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i != 1) {
                        objArr[0] = MLAsrCaptureConstants.ASR_ERROR_MESSAGE;
                    } else {
                        objArr[0] = "pluginClass";
                    }
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/diagnostic/PluginProblemReporter$1";
                    objArr[2] = "createPluginExceptionByClass";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.com.intellij.diagnostic.PluginProblemReporter
                public PluginException createPluginExceptionByClass(String str, Throwable th, Class cls) {
                    if (str == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (cls == null) {
                        $$$reportNull$$$0(1);
                    }
                    return new PluginException(str, th, null);
                }
            };
        }
        return (PluginProblemReporter) ApplicationManager.getApplication().getService(PluginProblemReporter.class);
    }
}
