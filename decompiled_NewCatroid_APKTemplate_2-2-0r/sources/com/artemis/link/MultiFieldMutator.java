package com.artemis.link;

import com.artemis.Component;
import com.artemis.World;
import com.artemis.utils.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
public interface MultiFieldMutator<T, C extends Component> {
    T read(C c, Field field);

    void setWorld(World world);

    void validate(int i, T t, LinkListener linkListener);
}
