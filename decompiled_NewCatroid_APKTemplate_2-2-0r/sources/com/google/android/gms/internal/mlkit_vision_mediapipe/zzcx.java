package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcx {
    private static final zzf zza = zzf.zza("com/google/mediapipe/framework/Graph");
    private final List<zzde> zzc = new ArrayList();
    private Map<String, zzdf> zzd = new HashMap();
    private Map<String, zzdf> zze = new HashMap();
    private boolean zzf = false;
    private boolean zzg = false;
    private boolean zzh = false;
    private Map<String, ArrayList<zzcz>> zzi = new HashMap();
    private final Object zzj = new Object();
    private long zzb = zzf();

    private final native void zza(long j);

    private final native void zza(long j, String str, long j2, long j3);

    private final native void zza(long j, String str, zzde zzdeVar);

    private final native void zza(long j, byte[] bArr);

    private final native void zza(long j, String[] strArr, long[] jArr, String[] strArr2, long[] jArr2);

    private final native void zzb(long j);

    private final native void zzc(long j);

    private final native long zzf();

    public final synchronized long zza() {
        return this.zzb;
    }

    public final synchronized void zza(byte[] bArr) {
        zza.zza(this.zzb != 0, "Invalid context, tearDown() might have been called already.");
        zza(this.zzb, bArr);
    }

    public final synchronized void zza(String str, zzde zzdeVar) {
        zza.zza(this.zzb != 0, "Invalid context, tearDown() might have been called already.");
        zza.zza(str);
        zza.zza(zzdeVar);
        if (this.zzh || this.zzg) {
            throw new IllegalStateException();
        }
        this.zzc.add(zzdeVar);
        zza(this.zzb, str, zzdeVar);
    }

    public final synchronized <T> void zza(zzdb<T> zzdbVar, T t) {
        zzdbVar.zza(this.zzb, t);
    }

    public final synchronized void zzb() {
        zza.zza(this.zzb != 0, "Invalid context, tearDown() might have been called.");
        this.zzg = true;
        Iterator<Map.Entry<String, zzdf>> it = this.zze.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue() == null) {
                zza.zza(Level.INFO).zza("com/google/mediapipe/framework/Graph", "startRunningGraph", 279, "Graph.java").zza("MediaPipe graph won't start until all stream headers are available.");
                return;
            }
        }
        String[] strArr = new String[this.zzd.size()];
        long[] jArr = new long[this.zzd.size()];
        zza(this.zzd, strArr, jArr);
        String[] strArr2 = new String[this.zze.size()];
        long[] jArr2 = new long[this.zze.size()];
        zza(this.zze, strArr2, jArr2);
        zza(this.zzb, strArr, jArr, strArr2, jArr2);
        this.zzh = true;
        if (!this.zzi.isEmpty()) {
            for (Map.Entry<String, ArrayList<zzcz>> entry : this.zzi.entrySet()) {
                ArrayList<zzcz> value = entry.getValue();
                int size = value.size();
                int i = 0;
                while (i < size) {
                    zzcz zzczVar = value.get(i);
                    i++;
                    zzcz zzczVar2 = zzczVar;
                    try {
                        zza(this.zzb, entry.getKey(), zzczVar2.zza.zza(), zzczVar2.zzb.longValue());
                        zzczVar2.zza.zzd();
                    } catch (zzdd e) {
                        zza.zza(Level.SEVERE).zza("com/google/mediapipe/framework/Graph", "moveBufferedPacketsToInputStream", 543, "Graph.java").zza("AddPacket for stream: %s failed: %s.", entry.getKey(), e.getMessage());
                        throw e;
                    }
                }
            }
            this.zzi.clear();
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.google.android.gms.internal.mlkit_vision_mediapipe.zzcz.<init>(com.google.android.gms.internal.mlkit_vision_mediapipe.zzdf, java.lang.Long, com.google.android.gms.internal.mlkit_vision_mediapipe.zzda):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:309)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:224)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:93)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    public final synchronized void zza(java.lang.String r9, com.google.android.gms.internal.mlkit_vision_mediapipe.zzdf r10, long r11) {
        /*
            r8 = this;
            monitor-enter(r8)
            long r0 = r8.zzb     // Catch: java.lang.Throwable -> La4
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto Lb
            r0 = 1
            goto Lc
        Lb:
            r0 = 0
        Lc:
            java.lang.String r1 = "Invalid context, tearDown() might have been called."
            com.google.android.gms.internal.mlkit_vision_mediapipe.zza.zza(r0, r1)     // Catch: java.lang.Throwable -> La4
            boolean r0 = r8.zzh     // Catch: java.lang.Throwable -> La4
            if (r0 != 0) goto L93
            com.google.android.gms.internal.mlkit_vision_mediapipe.zzdf r0 = r10.zzc()     // Catch: java.lang.Throwable -> La4
            java.util.Map<java.lang.String, java.util.ArrayList<com.google.android.gms.internal.mlkit_vision_mediapipe.zzcz>> r1 = r8.zzi     // Catch: java.lang.Throwable -> La4
            boolean r1 = r1.containsKey(r9)     // Catch: java.lang.Throwable -> La4
            if (r1 != 0) goto L2b
            java.util.Map<java.lang.String, java.util.ArrayList<com.google.android.gms.internal.mlkit_vision_mediapipe.zzcz>> r1 = r8.zzi     // Catch: java.lang.Throwable -> La4
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> La4
            r2.<init>()     // Catch: java.lang.Throwable -> La4
            r1.put(r9, r2)     // Catch: java.lang.Throwable -> La4
        L2b:
            java.util.Map<java.lang.String, java.util.ArrayList<com.google.android.gms.internal.mlkit_vision_mediapipe.zzcz>> r1 = r8.zzi     // Catch: java.lang.Throwable -> La4
            java.lang.Object r9 = r1.get(r9)     // Catch: java.lang.Throwable -> La4
            java.util.List r9 = (java.util.List) r9     // Catch: java.lang.Throwable -> La4
            int r1 = r9.size()     // Catch: java.lang.Throwable -> La4
            r2 = 20
            if (r1 <= r2) goto L81
            java.util.Map<java.lang.String, com.google.android.gms.internal.mlkit_vision_mediapipe.zzdf> r9 = r8.zze     // Catch: java.lang.Throwable -> La4
            java.util.Set r9 = r9.entrySet()     // Catch: java.lang.Throwable -> La4
            java.util.Iterator r9 = r9.iterator()     // Catch: java.lang.Throwable -> La4
        L45:
            boolean r10 = r9.hasNext()     // Catch: java.lang.Throwable -> La4
            if (r10 == 0) goto L79
            java.lang.Object r10 = r9.next()     // Catch: java.lang.Throwable -> La4
            java.util.Map$Entry r10 = (java.util.Map.Entry) r10     // Catch: java.lang.Throwable -> La4
            java.lang.Object r11 = r10.getValue()     // Catch: java.lang.Throwable -> La4
            if (r11 != 0) goto L45
            com.google.android.gms.internal.mlkit_vision_mediapipe.zzf r11 = com.google.android.gms.internal.mlkit_vision_mediapipe.zzcx.zza     // Catch: java.lang.Throwable -> La4
            java.util.logging.Level r12 = java.util.logging.Level.SEVERE     // Catch: java.lang.Throwable -> La4
            com.google.android.gms.internal.mlkit_vision_mediapipe.zzv r11 = r11.zza(r12)     // Catch: java.lang.Throwable -> La4
            com.google.android.gms.internal.mlkit_vision_mediapipe.zzh r11 = (com.google.android.gms.internal.mlkit_vision_mediapipe.zzh) r11     // Catch: java.lang.Throwable -> La4
            java.lang.String r12 = "com/google/mediapipe/framework/Graph"
            java.lang.String r0 = "addPacketToBuffer"
            java.lang.String r1 = "Graph.java"
            r2 = 524(0x20c, float:7.34E-43)
            com.google.android.gms.internal.mlkit_vision_mediapipe.zzv r11 = r11.zza(r12, r0, r2, r1)     // Catch: java.lang.Throwable -> La4
            com.google.android.gms.internal.mlkit_vision_mediapipe.zzh r11 = (com.google.android.gms.internal.mlkit_vision_mediapipe.zzh) r11     // Catch: java.lang.Throwable -> La4
            java.lang.String r12 = "Stream: %s might be missing."
            java.lang.Object r10 = r10.getKey()     // Catch: java.lang.Throwable -> La4
            r11.zza(r12, r10)     // Catch: java.lang.Throwable -> La4
            goto L45
        L79:
            java.lang.RuntimeException r9 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> La4
            java.lang.String r10 = "Graph is not started because of missing streams"
            r9.<init>(r10)     // Catch: java.lang.Throwable -> La4
            throw r9     // Catch: java.lang.Throwable -> La4
        L81:
            com.google.android.gms.internal.mlkit_vision_mediapipe.zzcz r1 = new com.google.android.gms.internal.mlkit_vision_mediapipe.zzcz     // Catch: java.lang.Throwable -> La4
            java.lang.Long r11 = java.lang.Long.valueOf(r11)     // Catch: java.lang.Throwable -> La4
            r12 = 0
            r1.<init>(r0, r11)     // Catch: java.lang.Throwable -> La4
            r9.add(r1)     // Catch: java.lang.Throwable -> La4
            r10.zzd()     // Catch: java.lang.Throwable -> La4
            monitor-exit(r8)
            return
        L93:
            long r1 = r8.zzb     // Catch: java.lang.Throwable -> La4
            long r4 = r10.zza()     // Catch: java.lang.Throwable -> La4
            r0 = r8
            r3 = r9
            r6 = r11
            r0.zza(r1, r3, r4, r6)     // Catch: java.lang.Throwable -> La4
            r10.zzd()     // Catch: java.lang.Throwable -> La4
            monitor-exit(r8)
            return
        La4:
            r9 = move-exception
            monitor-exit(r8)     // Catch: java.lang.Throwable -> La4
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_vision_mediapipe.zzcx.zza(java.lang.String, com.google.android.gms.internal.mlkit_vision_mediapipe.zzdf, long):void");
    }

    public final synchronized void zzc() {
        zza.zza(this.zzb != 0, "Invalid context, tearDown() might have been called.");
        zzb(this.zzb);
    }

    public final synchronized void zzd() {
        zza.zza(this.zzb != 0, "Invalid context, tearDown() might have been called.");
        zzc(this.zzb);
    }

    public final synchronized void zze() {
        zza.zza(this.zzb != 0, "Invalid context, tearDown() might have been called already.");
        Iterator<Map.Entry<String, zzdf>> it = this.zzd.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().zzd();
        }
        this.zzd.clear();
        for (Map.Entry<String, zzdf> entry : this.zze.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().zzd();
            }
        }
        this.zze.clear();
        Iterator<Map.Entry<String, ArrayList<zzcz>>> it2 = this.zzi.entrySet().iterator();
        while (it2.hasNext()) {
            ArrayList<zzcz> value = it2.next().getValue();
            int size = value.size();
            int i = 0;
            while (i < size) {
                zzcz zzczVar = value.get(i);
                i++;
                zzczVar.zza.zzd();
            }
        }
        this.zzi.clear();
        synchronized (this.zzj) {
            long j = this.zzb;
            if (j != 0) {
                zza(j);
                this.zzb = 0L;
            }
        }
        this.zzc.clear();
    }

    private static void zza(Map<String, zzdf> map, String[] strArr, long[] jArr) {
        if (map.size() != strArr.length || map.size() != jArr.length) {
            throw new RuntimeException("Input array length doesn't match the map size!");
        }
        int i = 0;
        for (Map.Entry<String, zzdf> entry : map.entrySet()) {
            strArr[i] = entry.getKey();
            jArr[i] = entry.getValue().zza();
            i++;
        }
    }
}
