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
import org.catrobat.catroid.common.Backpack;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.userbrick.UserDefinedBrickData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/BackpackSerializer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BackpackSerializer {
    private static final String TAG = "BackpackSerializer";
    private final File backpackFile;
    private Gson backpackGson;

    public BackpackSerializer(File backpackFile) {
        this.backpackFile = backpackFile;
        GsonBuilder prettyPrinting = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting();
        prettyPrinting.registerTypeAdapter(Script.class, new BackpackScriptSerializerAndDeserializer());
        prettyPrinting.registerTypeAdapter(Brick.class, new BackpackInterfaceSerializerAndDeserializer(backpackFile));
        prettyPrinting.registerTypeAdapter(UserDefinedBrickData.class, new BackpackInterfaceSerializerAndDeserializer(backpackFile));
        prettyPrinting.registerTypeAdapter(Brick.FormulaField.class, new BackpackFormulaFieldSerializerAndDeserializer(backpackFile));
        this.backpackGson = prettyPrinting.create();
    }

    public boolean saveBackpack(Backpack backpack) {
        String json = this.backpackGson.toJson(backpack);
        FileWriter fileWriter = null;
        try {
            try {
                this.backpackFile.createNewFile();
                FileWriter fileWriter2 = new FileWriter(this.backpackFile);
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

    public Backpack loadBackpack() {
        if (!this.backpackFile.exists()) {
            return new Backpack();
        }
        try {
            return (Backpack) this.backpackGson.fromJson((Reader) new BufferedReader(new FileReader(this.backpackFile)), Backpack.class);
        } catch (Exception e) {
            if (!(e instanceof FileNotFoundException)) {
                this.backpackFile.delete();
            }
            Log.e(TAG, "Cannot load Backpack. Creating new Backpack File.", e);
            return new Backpack();
        }
    }
}
