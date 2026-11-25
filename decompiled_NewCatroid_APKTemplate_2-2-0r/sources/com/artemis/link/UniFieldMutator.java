package com.artemis.link;

import com.artemis.Component;
import com.artemis.World;
import com.artemis.utils.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
public interface UniFieldMutator {
    int read(Component component, Field field);

    void setWorld(World world);

    void write(int i, Component component, Field field);
}
