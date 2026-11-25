package com.artemis;

import com.artemis.utils.reflect.ClassReflection;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes8.dex */
public class ComponentType {
    private final int index;
    public final boolean isPooled;
    private final Class<? extends Component> type;

    ComponentType(Class<? extends Component> cls, int i) {
        this.index = i;
        this.type = cls;
        this.isPooled = ClassReflection.isAssignableFrom(PooledComponent.class, cls);
    }

    public int getIndex() {
        return this.index;
    }

    public Class<? extends Component> getType() {
        return this.type;
    }

    public String toString() {
        return "ComponentType[" + ClassReflection.getSimpleName(this.type) + "] (" + this.index + Constant.AFTER_QUTO;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.index == ((ComponentType) obj).index;
    }

    public int hashCode() {
        return this.index;
    }
}
