package org.catrobat.catroid.devices.raspberrypi;

import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.RaspiInterruptScript;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/raspberrypi/RaspberryPiService.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class RaspberryPiService {
    private static final String TAG = "RaspberryPiService";
    private static RaspberryPiService instance;
    public RPiSocketConnection connection = null;
    private Map<String, GpioVersionType> gpioVersionMap = new HashMap();
    private Set<Integer> pinInterrupts;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/raspberrypi/RaspberryPiService$GpioVersionType.class
      classes14.dex
     */
    private enum GpioVersionType {
        SMALL_GPIO,
        BIG_GPIO,
        COMPUTE_MODULE
    }

    public static RaspberryPiService getInstance() {
        if (instance == null) {
            instance = new RaspberryPiService();
        }
        return instance;
    }

    private RaspberryPiService() {
        this.pinInterrupts = null;
        initGpioVersionMap();
        this.pinInterrupts = new HashSet();
    }

    public void addPinInterrupt(int pin) {
        this.pinInterrupts.add(Integer.valueOf(pin));
    }

    public boolean connect(String host, int port) {
        try {
            AsyncRPiTaskRunner asyncRPiTaskRunner = new AsyncRPiTaskRunner();
            asyncRPiTaskRunner.connect(host, port);
            this.connection = asyncRPiTaskRunner.getConnection();
            return asyncRPiTaskRunner.getConnection().isConnected();
        } catch (Exception e) {
            Log.e(TAG, "connecting to " + host + ":" + port + " failed" + e);
            return false;
        }
    }

    public void disconnect() {
        RPiSocketConnection rPiSocketConnection = this.connection;
        if (rPiSocketConnection != null) {
            try {
                rPiSocketConnection.disconnect();
                this.connection = null;
            } catch (IOException e) {
                Log.e(TAG, "Exception during disconnect: " + e);
            }
        }
        this.pinInterrupts.clear();
    }

    public ArrayList<Integer> getGpioList(String revision) {
        GpioVersionType gpioVersionType = this.gpioVersionMap.get(revision);
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (gpioVersionType != GpioVersionType.SMALL_GPIO && gpioVersionType == GpioVersionType.BIG_GPIO) {
            arrayList.add(3);
            arrayList.add(5);
            arrayList.add(7);
            arrayList.add(8);
            arrayList.add(10);
            arrayList.add(11);
            arrayList.add(12);
            arrayList.add(13);
            arrayList.add(15);
            arrayList.add(16);
            arrayList.add(18);
            arrayList.add(19);
            arrayList.add(21);
            arrayList.add(22);
            arrayList.add(23);
            arrayList.add(24);
            arrayList.add(26);
            arrayList.add(29);
            arrayList.add(31);
            arrayList.add(32);
            arrayList.add(33);
            arrayList.add(35);
            arrayList.add(36);
            arrayList.add(37);
            arrayList.add(38);
            arrayList.add(40);
        } else {
            arrayList.add(3);
            arrayList.add(5);
            arrayList.add(7);
            arrayList.add(8);
            arrayList.add(10);
            arrayList.add(11);
            arrayList.add(12);
            arrayList.add(13);
            arrayList.add(15);
            arrayList.add(16);
            arrayList.add(18);
            arrayList.add(19);
            arrayList.add(21);
            arrayList.add(22);
            arrayList.add(23);
            arrayList.add(24);
            arrayList.add(26);
        }
        return arrayList;
    }

    private void initGpioVersionMap() {
        this.gpioVersionMap.put("900021", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("900032", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("900092", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("900093", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("9000c1", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("9020e0", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("920092", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("920093", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("900061", GpioVersionType.COMPUTE_MODULE);
        this.gpioVersionMap.put("a01040", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a01041", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a02082", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a020a0", GpioVersionType.COMPUTE_MODULE);
        this.gpioVersionMap.put("a020d3", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a02042", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a21041", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a22042", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a22082", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a220a0", GpioVersionType.COMPUTE_MODULE);
        this.gpioVersionMap.put("a32082", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a52082", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a22083", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a02100", GpioVersionType.COMPUTE_MODULE);
        this.gpioVersionMap.put("a03111", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("b03111", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("b03112", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("b03114", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("c03111", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("c03112", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("c03114", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("d03114", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("c03130", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("0015", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("0014", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("0013", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("0012", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("0011", GpioVersionType.COMPUTE_MODULE);
        this.gpioVersionMap.put("0010", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("000f", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("000e", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("000d", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0009", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0008", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0007", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0006", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0005", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0004", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0003", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0002", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("Beta", GpioVersionType.SMALL_GPIO);
    }

    public void enableRaspberryInterruptPinsForProject(Project project) {
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                for (Script script : it2.next().getScriptList()) {
                    if (script instanceof RaspiInterruptScript) {
                        getInstance().addPinInterrupt(Integer.parseInt(((RaspiInterruptScript) script).getPin()));
                    }
                }
            }
        }
    }

    public boolean isValidPin(String revision, int pinNumber) {
        return getGpioList(revision).contains(Integer.valueOf(pinNumber));
    }

    public Set<Integer> getPinInterrupts() {
        return this.pinInterrupts;
    }
}
