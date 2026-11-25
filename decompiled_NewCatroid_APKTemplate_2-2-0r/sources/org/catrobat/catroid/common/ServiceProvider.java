package org.catrobat.catroid.common;

import android.util.Log;
import java.util.HashMap;
import org.catrobat.catroid.bluetooth.BluetoothDeviceServiceImpl;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/ServiceProvider.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public final class ServiceProvider {
    public static final String TAG = "ServiceProvider";
    private static HashMap<Class<? extends CatroidService>, CatroidService> services = new HashMap<>();

    private ServiceProvider() {
    }

    public static synchronized <T extends CatroidService, S extends CatroidService> void registerService(Class<T> serviceType, S serviceInstance) {
        if (services.put(serviceType, serviceInstance) != null) {
            Log.w(TAG, "Service '" + serviceType.getSimpleName() + "' got overwritten!");
        }
    }

    public static synchronized <T extends CatroidService> T getService(Class<T> cls) {
        T t = (T) services.get(cls);
        if (t != null) {
            return t;
        }
        T t2 = (T) createCommonService(cls);
        if (t2 != null) {
            return t2;
        }
        Log.w(TAG, "No Service '" + cls.getSimpleName() + "' is registered!");
        return null;
    }

    public static synchronized <T extends CatroidService> void unregisterService(Class<T> serviceType) {
        if (services.remove(serviceType) == null) {
            Log.w(TAG, "Unregister Service: Service '" + serviceType.getSimpleName() + "' is not registered!");
        }
    }

    private static CatroidService createCommonService(Class<? extends CatroidService> serviceType) {
        BluetoothDeviceServiceImpl bluetoothDeviceServiceImpl = serviceType == CatroidService.BLUETOOTH_DEVICE_SERVICE ? new BluetoothDeviceServiceImpl() : null;
        if (bluetoothDeviceServiceImpl != null) {
            registerService(serviceType, bluetoothDeviceServiceImpl);
        }
        return bluetoothDeviceServiceImpl;
    }
}
