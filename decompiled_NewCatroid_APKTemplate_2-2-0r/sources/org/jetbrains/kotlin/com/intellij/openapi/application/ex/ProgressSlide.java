package org.jetbrains.kotlin.com.intellij.openapi.application.ex;

/* JADX INFO: loaded from: classes3.dex */
public final class ProgressSlide {
    public final float progressRation;
    public final String url;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "url", "org/jetbrains/kotlin/com/intellij/openapi/application/ex/ProgressSlide", "<init>"));
    }

    public ProgressSlide(String str, float f) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.url = str;
        this.progressRation = f;
    }
}
