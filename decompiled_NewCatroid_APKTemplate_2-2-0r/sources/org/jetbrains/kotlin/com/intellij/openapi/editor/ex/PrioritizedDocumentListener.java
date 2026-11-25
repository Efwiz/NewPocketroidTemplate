package org.jetbrains.kotlin.com.intellij.openapi.editor.ex;

import java.util.Comparator;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface PrioritizedDocumentListener extends DocumentListener {
    public static final Comparator<? super DocumentListener> COMPARATOR = new Comparator<Object>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.ex.PrioritizedDocumentListener.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "o", "org/jetbrains/kotlin/com/intellij/openapi/editor/ex/PrioritizedDocumentListener$1", "getPriority"));
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return Integer.compare(getPriority(obj), getPriority(obj2));
        }

        private int getPriority(Object obj) {
            if (obj == null) {
                $$$reportNull$$$0(0);
            }
            if (obj instanceof PrioritizedDocumentListener) {
                return ((PrioritizedDocumentListener) obj).getPriority();
            }
            return Integer.MAX_VALUE;
        }
    };

    int getPriority();
}
