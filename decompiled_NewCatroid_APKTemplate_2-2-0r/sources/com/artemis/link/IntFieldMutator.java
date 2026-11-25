package com.artemis.link;

import com.artemis.Component;
import com.artemis.World;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionException;

/* JADX INFO: loaded from: classes8.dex */
class IntFieldMutator implements UniFieldMutator {
    @Override // com.artemis.link.UniFieldMutator
    public void setWorld(World world) {
    }

    IntFieldMutator() {
    }

    @Override // com.artemis.link.UniFieldMutator
    public int read(Component component, Field field) {
        try {
            return ((Integer) field.get(component)).intValue();
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.artemis.link.UniFieldMutator
    public void write(int i, Component component, Field field) {
        try {
            field.set(component, Integer.valueOf(i));
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }
}
