package org.jetbrains.kotlin.com.intellij.psi.scope;

import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaScopeProcessorEvent implements PsiScopeProcessor.Event {
    public static final JavaScopeProcessorEvent START_STATIC = new JavaScopeProcessorEvent();
    public static final JavaScopeProcessorEvent EXIT_LEVEL = new JavaScopeProcessorEvent();
    public static final JavaScopeProcessorEvent CHANGE_LEVEL = new JavaScopeProcessorEvent();
    public static final JavaScopeProcessorEvent SET_CURRENT_FILE_CONTEXT = new JavaScopeProcessorEvent();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "event", "org/jetbrains/kotlin/com/intellij/psi/scope/JavaScopeProcessorEvent", "isEnteringStaticScope"));
    }

    private JavaScopeProcessorEvent() {
    }

    public static boolean isEnteringStaticScope(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(0);
        }
        if (event == START_STATIC) {
            return true;
        }
        return event == EXIT_LEVEL && (obj instanceof PsiModifierListOwner) && ((PsiModifierListOwner) obj).hasModifierProperty("static");
    }
}
