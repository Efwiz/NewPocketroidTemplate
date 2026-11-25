package org.catrobat.catroid.io.asynctask;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.LegoEV3Setting;
import org.catrobat.catroid.content.LegoNXTSetting;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Setting;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectSaverKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectSaver.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0007\u001a\u00020\b2\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\t"}, d2 = {"saveLegoEV3SettingsToProject", "", "project", "Lorg/catrobat/catroid/content/Project;", "context", "Landroid/content/Context;", "saveLegoNXTSettingsToProject", "saveProjectSerial", "", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProjectSaverKt {
    public static final boolean saveProjectSerial(Project project, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (project == null) {
            return false;
        }
        saveLegoNXTSettingsToProject(project, context);
        saveLegoEV3SettingsToProject(project, context);
        return XstreamSerializer.getInstance().saveProject(project);
    }

    private static final void saveLegoNXTSettingsToProject(Project project, Context context) {
        if (!project.getRequiredResources().contains(2)) {
            List<Setting> settings = project.getSettings();
            Intrinsics.checkNotNullExpressionValue(settings, "getSettings(...)");
            Object[] array = settings.toArray(new Setting[0]);
            ArrayList arrayList = new ArrayList();
            for (Object obj : array) {
                if (obj instanceof LegoNXTSetting) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                project.getSettings().remove((LegoNXTSetting) it.next());
            }
            return;
        }
        NXTSensor.Sensor[] legoNXTSensorMapping = SettingsFragment.getLegoNXTSensorMapping(context);
        List<Setting> settings2 = project.getSettings();
        Intrinsics.checkNotNullExpressionValue(settings2, "getSettings(...)");
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : settings2) {
            if (obj2 instanceof LegoNXTSetting) {
                arrayList2.add(obj2);
            }
        }
        Iterator it2 = arrayList2.iterator();
        if (it2.hasNext()) {
            ((LegoNXTSetting) it2.next()).updateMapping(legoNXTSensorMapping);
        } else {
            project.getSettings().add(new LegoNXTSetting(legoNXTSensorMapping));
        }
    }

    private static final void saveLegoEV3SettingsToProject(Project project, Context context) {
        if (!project.getRequiredResources().contains(20)) {
            List<Setting> settings = project.getSettings();
            Intrinsics.checkNotNullExpressionValue(settings, "getSettings(...)");
            Object[] array = settings.toArray(new Setting[0]);
            ArrayList arrayList = new ArrayList();
            for (Object obj : array) {
                if (obj instanceof LegoEV3Setting) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                project.getSettings().remove((LegoEV3Setting) it.next());
            }
            return;
        }
        EV3Sensor.Sensor[] legoEV3SensorMapping = SettingsFragment.getLegoEV3SensorMapping(context);
        List<Setting> settings2 = project.getSettings();
        Intrinsics.checkNotNullExpressionValue(settings2, "getSettings(...)");
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : settings2) {
            if (obj2 instanceof LegoEV3Setting) {
                arrayList2.add(obj2);
            }
        }
        Iterator it2 = arrayList2.iterator();
        if (it2.hasNext()) {
            ((LegoEV3Setting) it2.next()).updateMapping(legoEV3SensorMapping);
        } else {
            project.getSettings().add(new LegoEV3Setting(legoEV3SensorMapping));
        }
    }
}
