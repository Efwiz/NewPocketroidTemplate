package org.catrobat.catroid.io;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import org.catrobat.catroid.common.RecentBricksHolder;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.userbrick.UserDefinedBrickData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/RecentBrickListSerializer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class RecentBrickListSerializer {
    private static final String TAG = "RecentBrickListSerializer";
    private final Gson recentBrickListGson;
    private final File recentBricksFile;

    public RecentBrickListSerializer(File recentBricksFile) {
        this.recentBricksFile = recentBricksFile;
        GsonBuilder prettyPrinting = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting();
        prettyPrinting.registerTypeAdapter(Brick.class, new BackpackInterfaceSerializerAndDeserializer(recentBricksFile));
        prettyPrinting.registerTypeAdapter(UserDefinedBrickData.class, new BackpackInterfaceSerializerAndDeserializer(recentBricksFile));
        prettyPrinting.registerTypeAdapter(Brick.FormulaField.class, new BackpackFormulaFieldSerializerAndDeserializer(recentBricksFile));
        this.recentBrickListGson = prettyPrinting.create();
    }

    public boolean saveRecentBricks(RecentBricksHolder recentBricksHolder) throws Throwable {
        String json = this.recentBrickListGson.toJson(recentBricksHolder);
        FileWriter fileWriter = null;
        try {
            try {
                this.recentBricksFile.createNewFile();
                FileWriter fileWriter2 = new FileWriter(this.recentBricksFile);
                try {
                    fileWriter2.write(json);
                    try {
                        fileWriter2.close();
                        return true;
                    } catch (IOException e) {
                        Log.e(TAG, "Cannot close Buffered Writer", e);
                        return true;
                    }
                } catch (IOException e2) {
                    e = e2;
                    fileWriter = fileWriter2;
                    Log.e(TAG, Log.getStackTraceString(e));
                    if (fileWriter == null) {
                        return false;
                    }
                    try {
                        fileWriter.close();
                        return false;
                    } catch (IOException e3) {
                        Log.e(TAG, "Cannot close Buffered Writer", e3);
                        return false;
                    }
                } catch (Throwable th) {
                    th = th;
                    fileWriter = fileWriter2;
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (IOException e4) {
                            Log.e(TAG, "Cannot close Buffered Writer", e4);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e5) {
            e = e5;
        }
    }

    public RecentBricksHolder loadRecentBricks() {
        if (!this.recentBricksFile.exists()) {
            return new RecentBricksHolder();
        }
        try {
            return (RecentBricksHolder) this.recentBrickListGson.fromJson((Reader) new BufferedReader(new FileReader(this.recentBricksFile)), RecentBricksHolder.class);
        } catch (Exception e) {
            if (!(e instanceof FileNotFoundException)) {
                this.recentBricksFile.delete();
            }
            Log.e(TAG, "Cannot load Recent Bricks File. Creating new Recent Bricks File.", e);
            return new RecentBricksHolder();
        }
    }
}
