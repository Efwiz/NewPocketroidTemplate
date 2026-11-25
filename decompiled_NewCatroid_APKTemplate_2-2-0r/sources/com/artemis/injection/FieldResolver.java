package com.artemis.injection;

import com.artemis.World;
import com.artemis.utils.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
public interface FieldResolver {
    void initialize(World world);

    Object resolve(Object obj, Class<?> cls, Field field);
}
