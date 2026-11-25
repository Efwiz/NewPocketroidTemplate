package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.providers;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public interface CustomizableReferenceProvider {
    Map<CustomizationKey, Object> getOptions();

    PsiReference[] getReferencesByElement(PsiElement psiElement, ProcessingContext processingContext);

    public static final class CustomizationKey<T> {
        private final String myOptionDescription;

        public CustomizationKey(String str) {
            this.myOptionDescription = str;
        }

        public String toString() {
            return this.myOptionDescription;
        }

        public T getValue(Map<CustomizationKey, Object> map) {
            if (map == null) {
                return null;
            }
            return (T) map.get(this);
        }

        public boolean getBooleanValue(Map<CustomizationKey, Object> map) {
            Boolean bool = map == null ? null : (Boolean) map.get(this);
            return bool != null && bool.booleanValue();
        }

        public void putValue(Map<CustomizationKey, Object> map, T t) {
            map.put(this, t);
        }
    }
}
