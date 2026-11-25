package org.catrobat.catroid.ui.runtimepermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/runtimepermissions/BrickResourcesToRuntimePermissions.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickResourcesToRuntimePermissions {
    private BrickResourcesToRuntimePermissions() {
        throw new AssertionError("No.");
    }

    public static List<String> translate(Brick.ResourcesSet brickResources) {
        HashMap map = new HashMap();
        map.put(18, Arrays.asList("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"));
        List listAsList = Arrays.asList("android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH");
        map.put(2, listAsList);
        map.put(20, listAsList);
        map.put(10, listAsList);
        map.put(6, listAsList);
        map.put(22, Arrays.asList("android.permission.CHANGE_WIFI_MULTICAST_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_WIFI_STATE"));
        map.put(11, Arrays.asList("android.permission.CAMERA"));
        map.put(12, Arrays.asList("android.permission.CAMERA"));
        map.put(17, Arrays.asList("android.permission.CAMERA"));
        map.put(8, Arrays.asList("android.permission.CAMERA"));
        map.put(9, Arrays.asList("android.permission.VIBRATE"));
        map.put(16, Arrays.asList("android.permission.NFC"));
        map.put(4, Collections.singletonList("android.permission.CAMERA"));
        map.put(28, Collections.singletonList("android.permission.CAMERA"));
        map.put(27, Collections.singletonList("android.permission.CAMERA"));
        map.put(5, Collections.singletonList("android.permission.CAMERA"));
        map.put(24, Arrays.asList("android.permission.RECORD_AUDIO"));
        map.put(25, Arrays.asList("android.permission.READ_EXTERNAL_STORAGE"));
        map.put(26, Arrays.asList("android.permission.WRITE_EXTERNAL_STORAGE"));
        HashSet hashSet = new HashSet();
        Iterator<Integer> it = brickResources.iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            if (map.containsKey(Integer.valueOf(iIntValue))) {
                hashSet.addAll((Collection) map.get(Integer.valueOf(iIntValue)));
            }
        }
        return new ArrayList(hashSet);
    }
}
