package org.jetbrains.kotlin.com.intellij.navigation;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.ClassExtension;

/* JADX INFO: loaded from: classes3.dex */
public final class ItemPresentationProviders extends ClassExtension<ItemPresentationProvider> {
    public static final ItemPresentationProviders INSTANCE = new ItemPresentationProviders();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        objArr[1] = "org/jetbrains/kotlin/com/intellij/navigation/ItemPresentationProviders";
        if (i != 1) {
            objArr[2] = "getItemPresentationProvider";
        } else {
            objArr[2] = "getItemPresentation";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private ItemPresentationProviders() {
        super("org.jetbrains.kotlin.com.intellij.itemPresentationProvider");
    }

    public static <T extends NavigationItem> ItemPresentationProvider<T> getItemPresentationProvider(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        return INSTANCE.forClass(t.getClass());
    }

    public static ItemPresentation getItemPresentation(NavigationItem navigationItem) {
        if (navigationItem == null) {
            $$$reportNull$$$0(1);
        }
        ItemPresentationProvider itemPresentationProvider = getItemPresentationProvider(navigationItem);
        if (itemPresentationProvider == null) {
            return null;
        }
        return itemPresentationProvider.getPresentation(navigationItem);
    }
}
