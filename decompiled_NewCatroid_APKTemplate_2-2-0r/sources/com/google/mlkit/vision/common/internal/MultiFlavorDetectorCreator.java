package com.google.mlkit.vision.common.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.inject.Provider;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes10.dex */
public class MultiFlavorDetectorCreator {
    private final Map<Class<? extends DetectorOptions<?>>, Provider<? extends DetectorCreator<?, ?>>> zza = new HashMap();

    /* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
    public interface DetectorCreator<ResultT, OptionsT extends DetectorOptions<ResultT>> {
        MobileVisionBase<ResultT> create(OptionsT optionst);
    }

    /* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
    public interface DetectorOptions<ResultT> {
    }

    /* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
    public static class Registration {
        private final Class<? extends DetectorOptions<?>> zza;
        private final Provider<? extends DetectorCreator<?, ?>> zzb;
        private final int zzc;

        public <ResultT, OptionsT extends DetectorOptions<ResultT>> Registration(Class<? extends OptionsT> cls, Provider<? extends DetectorCreator<ResultT, OptionsT>> provider) {
            this(cls, provider, 100);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public <ResultT, OptionsT extends DetectorOptions<ResultT>> Registration(Class<? extends OptionsT> cls, Provider<? extends DetectorCreator<ResultT, OptionsT>> provider, int i) {
            this.zza = cls;
            this.zzb = provider;
            this.zzc = i;
        }

        final int zza() {
            return this.zzc;
        }

        final Provider<? extends DetectorCreator<?, ?>> zzb() {
            return this.zzb;
        }

        final Class<? extends DetectorOptions<?>> zzc() {
            return this.zza;
        }
    }

    MultiFlavorDetectorCreator(Set<Registration> set) {
        HashMap map = new HashMap();
        for (Registration registration : set) {
            Class<? extends DetectorOptions<?>> clsZzc = registration.zzc();
            if (!this.zza.containsKey(clsZzc) || registration.zza() >= ((Integer) Preconditions.checkNotNull((Integer) map.get(clsZzc))).intValue()) {
                this.zza.put(clsZzc, registration.zzb());
                map.put(clsZzc, Integer.valueOf(registration.zza()));
            }
        }
    }

    public static synchronized MultiFlavorDetectorCreator getInstance() {
        return (MultiFlavorDetectorCreator) MlKitContext.getInstance().get(MultiFlavorDetectorCreator.class);
    }

    public <ResultT, OptionsT extends DetectorOptions<ResultT>> MobileVisionBase<ResultT> create(OptionsT optionst) {
        return ((DetectorCreator) ((Provider) Preconditions.checkNotNull(this.zza.get(optionst.getClass()))).get()).create(optionst);
    }
}
