package com.android.tools.r8.profile.art;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.TextOutputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ArtProfileConsumer {
    void finished(DiagnosticsHandler diagnosticsHandler);

    default TextOutputStream getHumanReadableArtProfileConsumer() {
        return null;
    }

    default ArtProfileRuleConsumer getRuleConsumer() {
        return null;
    }
}
