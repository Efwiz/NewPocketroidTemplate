package com.huawei.hms.mlsdk.tts.client;

import com.huawei.hms.ml.common.utils.SmartLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: ModuleLauncher.java */
/* JADX INFO: loaded from: classes10.dex */
public class a {
    private static HashMap<String, b> a = new HashMap<>();

    public static synchronized b a(ModuleInfo moduleInfo) {
        if (moduleInfo != null) {
            if (moduleInfo.getModuleName() != null && moduleInfo.getCreatorName() != null) {
                if (a.containsKey(moduleInfo.getModuleName())) {
                    return a.get(moduleInfo.getModuleName());
                }
                b bVar = new b(moduleInfo);
                a.put(moduleInfo.getModuleName(), bVar);
                return bVar;
            }
        }
        throw new IllegalArgumentException("module info is invalid");
    }

    public static synchronized void a() {
        Iterator<Map.Entry<String, b>> it = a.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            b value = it.next().getValue();
            SmartLog.d("ModuleLauncher", com.huawei.hms.mlsdk.t.a.a("destroy: ").append(value.c()).toString());
            if (value.c()) {
                z = true;
            }
            value.a();
        }
        SmartLog.d("ModuleLauncher", "destroy: modules.clear()before");
        if (!z) {
            a.clear();
        }
        SmartLog.d("ModuleLauncher", "destroy: modules.clear()after");
    }
}
