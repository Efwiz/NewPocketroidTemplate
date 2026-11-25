package org.jetbrains.kotlin.com.intellij.openapi.application.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.WeakList;

/* JADX INFO: loaded from: classes3.dex */
public final class ModalityStateEx extends ModalityState {
    private static final Set<Object> ourTransparentEntities = Collections.newSetFromMap(CollectionFactory.createConcurrentWeakMap());
    private final WeakList<Object> myModalEntities;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/impl/ModalityStateEx";
                break;
            case 2:
                objArr[0] = "progress";
                break;
            case 4:
                objArr[0] = "anEntity";
                break;
            case 5:
                objArr[0] = "anotherState";
                break;
            case 6:
                objArr[0] = "modalEntity";
                break;
            default:
                objArr[0] = "modalEntities";
                break;
        }
        if (i == 1) {
            objArr[1] = "getModalEntities";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/impl/ModalityStateEx";
        } else {
            objArr[1] = "appendProgress";
        }
        switch (i) {
            case 1:
            case 3:
                break;
            case 2:
                objArr[2] = "appendProgress";
                break;
            case 4:
                objArr[2] = "appendEntity";
                break;
            case 5:
                objArr[2] = "dominates";
                break;
            case 6:
                objArr[2] = "unmarkTransparent";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ModalityStateEx() {
        this.myModalEntities = new WeakList<>();
    }

    ModalityStateEx(Collection<Object> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        WeakList<Object> weakList = new WeakList<>();
        this.myModalEntities = weakList;
        weakList.addAll(collection);
    }

    List<Object> getModalEntities() {
        List<Object> strongList = this.myModalEntities.toStrongList();
        if (strongList == null) {
            $$$reportNull$$$0(1);
        }
        return strongList;
    }

    public ModalityState appendProgress(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(2);
        }
        ModalityStateEx modalityStateExAppendEntity = appendEntity(progressIndicator);
        if (modalityStateExAppendEntity == null) {
            $$$reportNull$$$0(3);
        }
        return modalityStateExAppendEntity;
    }

    ModalityStateEx appendEntity(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(4);
        }
        List<Object> modalEntities = getModalEntities();
        ArrayList arrayList = new ArrayList(modalEntities.size() + 1);
        arrayList.addAll(modalEntities);
        arrayList.add(obj);
        return new ModalityStateEx(arrayList);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState
    public boolean dominates(ModalityState modalityState) {
        if (modalityState == null) {
            $$$reportNull$$$0(5);
        }
        if (modalityState == ModalityState.any() || this.myModalEntities.isEmpty()) {
            return false;
        }
        List<Object> modalEntities = ((ModalityStateEx) modalityState).getModalEntities();
        for (Object obj : getModalEntities()) {
            if (!modalEntities.contains(obj) && !ourTransparentEntities.contains(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState
    public String toString() {
        if (this == NON_MODAL) {
            return "ModalityState.NON_MODAL";
        }
        return "ModalityState:{" + StringUtil.join((Collection) getModalEntities(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.impl.ModalityStateEx$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ModalityStateEx.lambda$toString$0(obj);
            }
        }, ", ") + "}";
    }

    static /* synthetic */ String lambda$toString$0(Object obj) {
        return "[" + obj + "]";
    }

    void removeModality(Object obj) {
        this.myModalEntities.remove(obj);
    }

    static void unmarkTransparent(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(6);
        }
        ourTransparentEntities.remove(obj);
    }
}
