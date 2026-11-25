package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFilePropertyChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public class VirtualFilePropertyEvent extends VirtualFileEvent {
    private final Object myNewValue;
    private final Object myOldValue;
    private final String myPropertyName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "propertyName";
        } else if (i != 2) {
            objArr[0] = "file";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFilePropertyEvent";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFilePropertyEvent";
        } else {
            objArr[1] = "getPropertyName";
        }
        if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VirtualFilePropertyEvent(Object obj, VirtualFile virtualFile, String str, Object obj2, Object obj3) {
        super(obj, virtualFile, virtualFile.getParent(), 0L, 0L);
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myPropertyName = str;
        this.myOldValue = obj2;
        this.myNewValue = obj3;
        VFilePropertyChangeEvent.checkPropertyValuesCorrect(obj, str, obj2, obj3);
    }

    public String getPropertyName() {
        String str = this.myPropertyName;
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    public Object getOldValue() {
        return this.myOldValue;
    }

    public Object getNewValue() {
        return this.myNewValue;
    }
}
