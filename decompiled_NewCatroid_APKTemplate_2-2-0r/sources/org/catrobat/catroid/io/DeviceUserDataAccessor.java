package org.catrobat.catroid.io;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/DeviceUserDataAccessor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class DeviceUserDataAccessor {
    public static final String TAG = "DeviceUserDataAccessor";
    private File deviceFile;

    protected abstract String getDeviceFileName();

    protected abstract Object getLock();

    protected abstract List<? extends UserData> getUserData(Project project);

    protected abstract List<? extends UserData> getUserData(Sprite sprite);

    public DeviceUserDataAccessor(File projectDirectory) {
        this.deviceFile = new File(projectDirectory, getDeviceFileName());
    }

    public void setDeviceFile(File deviceFile) {
        this.deviceFile = deviceFile;
    }

    public boolean readUserData(UserData userData) {
        Map<UUID, Object> mapFromJson = readMapFromJson();
        if (mapFromJson == null) {
            userData.reset();
            return false;
        }
        if (!mapFromJson.containsKey(userData.getDeviceKey())) {
            userData.reset();
            return false;
        }
        userData.setValue(mapFromJson.get(userData.getDeviceKey()));
        return true;
    }

    public void writeUserData(UserData userData) {
        synchronized (getLock()) {
            try {
                if (!this.deviceFile.exists() && !this.deviceFile.createNewFile()) {
                    Log.e(TAG, "Cannot create " + this.deviceFile.getAbsolutePath());
                    return;
                }
                Map<UUID, Object> mapFromJson = readMapFromJson();
                if (mapFromJson == null) {
                    mapFromJson = new HashMap<>();
                }
                mapFromJson.put(userData.getDeviceKey(), userData.getValue());
                writeMapToJson(mapFromJson);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void removeDeviceValue(UserData userData) {
        synchronized (getLock()) {
            if (this.deviceFile.exists()) {
                try {
                    Map<UUID, Object> mapFromJson = readMapFromJson();
                    if (mapFromJson == null) {
                        return;
                    }
                    mapFromJson.remove(userData.getDeviceKey());
                    writeMapToJson(mapFromJson);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public Map<UUID, Object> readMapFromJson() {
        try {
            return (Map) new Gson().fromJson(new FileReader(this.deviceFile), new TypeToken<HashMap<UUID, Object>>() { // from class: org.catrobat.catroid.io.DeviceUserDataAccessor.1
            }.getType());
        } catch (FileNotFoundException unused) {
            if (!this.deviceFile.exists()) {
                return null;
            }
            Log.e(TAG, "Device Variable File corrupted!");
            this.deviceFile.delete();
            return null;
        }
    }

    public void writeMapToJson(Map map) {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.deviceFile));
            try {
                bufferedOutputStream.write(new Gson().toJson(map, new TypeToken<HashMap<UUID, Object>>() { // from class: org.catrobat.catroid.io.DeviceUserDataAccessor.2
                }.getType()).getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            } catch (Throwable th) {
                try {
                    bufferedOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void cleanUpDeletedUserData(Project project) {
        synchronized (getLock()) {
            if (this.deviceFile.exists()) {
                Map<UUID, Object> mapFromJson = readMapFromJson();
                if (mapFromJson == null) {
                    return;
                }
                List<UUID> keyList = getKeyList(getUserData(project));
                ArrayList arrayList = new ArrayList();
                Iterator<Scene> it = project.getSceneList().iterator();
                while (it.hasNext()) {
                    Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
                    while (it2.hasNext()) {
                        arrayList.addAll(getKeyList(getUserData(it2.next())));
                    }
                }
                HashSet hashSet = new HashSet(mapFromJson.keySet());
                hashSet.removeAll(keyList);
                hashSet.removeAll(arrayList);
                Iterator it3 = hashSet.iterator();
                while (it3.hasNext()) {
                    mapFromJson.remove((UUID) it3.next());
                }
                try {
                    writeMapToJson(mapFromJson);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    private List<UUID> getKeyList(List<? extends UserData> userDataList) {
        ArrayList arrayList = new ArrayList();
        Iterator<? extends UserData> it = userDataList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getDeviceKey());
        }
        return arrayList;
    }
}
