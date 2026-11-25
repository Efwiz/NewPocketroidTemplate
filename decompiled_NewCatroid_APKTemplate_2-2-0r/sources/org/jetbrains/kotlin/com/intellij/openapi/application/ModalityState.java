package org.jetbrains.kotlin.com.intellij.openapi.application;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ModalityState {
    public static final ModalityState NON_MODAL;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 2 ? 2 : 3];
        if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/ModalityState";
        } else {
            objArr[0] = "component";
        }
        if (i == 1) {
            objArr[1] = "any";
        } else if (i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/ModalityState";
        } else if (i == 3) {
            objArr[1] = "stateForComponent";
        } else if (i != 4) {
            objArr[1] = "current";
        } else {
            objArr[1] = "defaultModalityState";
        }
        if (i == 2) {
            objArr[2] = "stateForComponent";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalArgumentException(str2);
        }
    }

    public abstract boolean dominates(ModalityState modalityState);

    public abstract String toString();

    static {
        try {
            NON_MODAL = (ModalityState) Class.forName("org.jetbrains.kotlin.com.intellij.openapi.application.impl.ModalityStateEx").newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static ModalityState current() {
        ModalityState currentModalityState = ApplicationManager.getApplication().getCurrentModalityState();
        if (currentModalityState == null) {
            $$$reportNull$$$0(0);
        }
        return currentModalityState;
    }

    public static ModalityState any() {
        ModalityState anyModalityState = ApplicationManager.getApplication().getAnyModalityState();
        if (anyModalityState == null) {
            $$$reportNull$$$0(1);
        }
        return anyModalityState;
    }

    public static ModalityState defaultModalityState() {
        ModalityState defaultModalityState = ApplicationManager.getApplication().getDefaultModalityState();
        if (defaultModalityState == null) {
            $$$reportNull$$$0(4);
        }
        return defaultModalityState;
    }
}
