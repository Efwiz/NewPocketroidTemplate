package com.hcaptcha.sdk.journeylitics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public class JLConfig {
    static final JLConfig DEFAULT = new JLConfig();
    private final boolean enableClicks;
    private final boolean enableScreens;
    private final boolean enableScrolls;
    private final boolean enableSearch;
    private final boolean enableSliders;
    private final boolean enableTextInputs;
    private final boolean enableToggles;
    private final List<JLSink> sinks;

    public JLConfig(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, List<JLSink> list) {
        this.enableScreens = z;
        this.enableClicks = z2;
        this.enableToggles = z3;
        this.enableSliders = z4;
        this.enableScrolls = z5;
        this.enableSearch = z6;
        this.enableTextInputs = z7;
        this.sinks = list;
    }

    public List<JLSink> getSinks() {
        return this.sinks;
    }

    public boolean isEnableClicks() {
        return this.enableClicks;
    }

    public boolean isEnableScreens() {
        return this.enableScreens;
    }

    public boolean isEnableScrolls() {
        return this.enableScrolls;
    }

    public boolean isEnableSearch() {
        return this.enableSearch;
    }

    public boolean isEnableSliders() {
        return this.enableSliders;
    }

    public boolean isEnableTextInputs() {
        return this.enableTextInputs;
    }

    public boolean isEnableToggles() {
        return this.enableToggles;
    }

    public JLConfig() {
        this(true, true, true, true, true, true, true, Collections.EMPTY_LIST);
    }

    public JLConfig(JLSink jLSink) {
        this(true, true, true, true, true, true, true, Arrays.asList(jLSink));
    }
}
