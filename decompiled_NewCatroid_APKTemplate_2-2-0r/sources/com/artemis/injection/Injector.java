package com.artemis.injection;

import com.artemis.World;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public interface Injector {
    <T> T getRegistered(Class<T> cls);

    <T> T getRegistered(String str);

    void initialize(World world, Map<String, Object> map);

    void inject(Object obj) throws RuntimeException;

    boolean isInjectable(Object obj);

    Injector setFieldHandler(FieldHandler fieldHandler);
}
