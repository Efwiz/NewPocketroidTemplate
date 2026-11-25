package com.google.mlkit.vision.text;

import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
public class TextRecognizerOptions {
    private final Executor zza;

    /* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
    public static class Builder {
        private Executor zza;

        public TextRecognizerOptions build() {
            return new TextRecognizerOptions(this.zza, null);
        }

        public Builder setExecutor(Executor executor) {
            this.zza = executor;
            return this;
        }
    }

    /* synthetic */ TextRecognizerOptions(Executor executor, zzd zzdVar) {
        this.zza = executor;
    }

    public final Executor zza() {
        return this.zza;
    }
}
