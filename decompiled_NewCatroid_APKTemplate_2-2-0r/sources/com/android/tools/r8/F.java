package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.Y2;
import java.io.PrintStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class F implements MarkerInfoConsumer {
    private final PrintStream a;

    public F(PrintStream printStream) {
        this.a = printStream;
    }

    @Override // com.android.tools.r8.MarkerInfoConsumer
    public final void acceptMarkerInfo(MarkerInfoConsumerData markerInfoConsumerData) {
        if (!markerInfoConsumerData.hasMarkers()) {
            Origin inputOrigin = markerInfoConsumerData.getInputOrigin();
            String strE = Y2.e("no marker");
            this.a.print(inputOrigin.toString());
            this.a.print(": ");
            this.a.print(strE);
            this.a.println();
            return;
        }
        for (MarkerInfo markerInfo : markerInfoConsumerData.getMarkers()) {
            Origin inputOrigin2 = markerInfoConsumerData.getInputOrigin();
            String rawEncoding = markerInfo.getRawEncoding();
            this.a.print(inputOrigin2.toString());
            this.a.print(": ");
            this.a.print(rawEncoding);
            this.a.println();
        }
    }

    @Override // com.android.tools.r8.MarkerInfoConsumer
    public final void finished() {
    }
}
