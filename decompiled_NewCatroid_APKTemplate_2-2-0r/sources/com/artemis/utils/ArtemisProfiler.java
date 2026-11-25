package com.artemis.utils;

import com.artemis.BaseSystem;
import com.artemis.World;

/* JADX INFO: loaded from: classes8.dex */
public interface ArtemisProfiler {
    void initialize(BaseSystem baseSystem, World world);

    void start();

    void stop();
}
