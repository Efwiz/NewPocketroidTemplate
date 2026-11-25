package com.hcaptcha.sdk.journeylitics;

import com.android.dx.rop.code.RegisterSpec;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.cast.HlsSegmentFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public class JLEvent {
    private final EventKind kind;
    private final Object metadata;
    private final long timestamp;
    private final String view;

    public JLEvent(long j, EventKind eventKind, String str, Object obj) {
        this.timestamp = j;
        this.kind = eventKind;
        this.view = str;
        this.metadata = obj;
    }

    @JsonProperty(JvmAnnotationNames.KIND_FIELD_NAME)
    public EventKind getKind() {
        return this.kind;
    }

    @JsonProperty("m")
    public Object getMetadata() {
        return this.metadata;
    }

    @JsonProperty(HlsSegmentFormat.TS)
    public long getTimestamp() {
        return this.timestamp;
    }

    @JsonProperty(RegisterSpec.PREFIX)
    public String getView() {
        return this.view;
    }

    public JLEvent(EventKind eventKind, String str, Map<String, Object> map) {
        this(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()), eventKind, str, map);
    }

    public JLEvent(EventKind eventKind, String str) {
        this(eventKind, str, new HashMap());
    }
}
