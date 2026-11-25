package org.catrobat.catroid.plugins;

import java.io.File;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/plugins/PluginInfo.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PluginInfo {
    public final String description;
    public boolean isEnabled = true;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final String f589name;
    public final String packageName;
    public final File pluginDirectory;
    public final File settingsFile;
    public final String version;

    public PluginInfo(String packageName, String name2, String version, String description, File pluginDirectory) {
        this.packageName = packageName;
        this.f589name = name2;
        this.version = version;
        this.description = description;
        this.pluginDirectory = pluginDirectory;
        this.settingsFile = new File(pluginDirectory, "settings.json");
    }

    public boolean hasSettings() {
        return this.settingsFile.exists() && this.settingsFile.canRead();
    }
}
