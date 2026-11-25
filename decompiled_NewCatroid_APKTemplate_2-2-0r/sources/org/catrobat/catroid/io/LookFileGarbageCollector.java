package org.catrobat.catroid.io;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.asynctask.ProjectLoader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/LookFileGarbageCollector.class
  classes14.dex
 */
/* JADX INFO: compiled from: LookFileGarbageCollector.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0002¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/io/LookFileGarbageCollector;", "", "()V", "cleanUpUnusedLookFiles", "", "project", "Lorg/catrobat/catroid/content/Project;", "deleteUnusedLookFiles", "scene", "Lorg/catrobat/catroid/content/Scene;", "fileNamesToKeep", "", "", "getAllFileNamesToKeep", "getLookDataFileNames", "getOtherFileNamesToKeep", "tryDeleteLookFile", "file", "Ljava/io/File;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LookFileGarbageCollector {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Object LOCK = new Object();

    public final void cleanUpUnusedLookFiles(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        synchronized (LOCK) {
            List<Scene> sceneList = project.getSceneList();
            Intrinsics.checkNotNullExpressionValue(sceneList, "getSceneList(...)");
            for (Scene scene : sceneList) {
                Intrinsics.checkNotNull(scene);
                deleteUnusedLookFiles(scene, getAllFileNamesToKeep(scene));
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final List<String> getAllFileNamesToKeep(Scene scene) {
        return CollectionsKt.plus((Collection) getLookDataFileNames(scene), (Iterable) getOtherFileNamesToKeep());
    }

    private final List<String> getLookDataFileNames(Scene scene) {
        List<Sprite> spriteList = scene.getSpriteList();
        Intrinsics.checkNotNullExpressionValue(spriteList, "getSpriteList(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = spriteList.iterator();
        while (it.hasNext()) {
            List<LookData> lookList = ((Sprite) it.next()).getLookList();
            Intrinsics.checkNotNullExpressionValue(lookList, "getLookList(...)");
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it2 = lookList.iterator();
            while (it2.hasNext()) {
                CollectionsKt.addAll(arrayList2, CollectionsKt.listOf(((LookData) it2.next()).getFile().getName()));
            }
            CollectionsKt.addAll(arrayList, arrayList2);
        }
        return arrayList;
    }

    private final List<String> getOtherFileNamesToKeep() {
        return CollectionsKt.listOf(Constants.NO_MEDIA_FILE);
    }

    private final void deleteUnusedLookFiles(Scene scene, List<String> fileNamesToKeep) {
        File[] fileArrListFiles = new File(scene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME).listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        ArrayList<File> arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            if (!fileNamesToKeep.contains(file.getName())) {
                arrayList.add(file);
            }
        }
        for (File file2 : arrayList) {
            Intrinsics.checkNotNull(file2);
            tryDeleteLookFile(file2);
        }
    }

    private final void tryDeleteLookFile(File file) {
        try {
            StorageOperations.deleteFile(file);
        } catch (IOException e) {
            Log.e(ProjectLoader.INSTANCE.getTAG(), "Error while deleting file " + file.getName() + " during cleanup of unused LookFile", e);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/LookFileGarbageCollector$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LookFileGarbageCollector.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/io/LookFileGarbageCollector$Companion;", "", "()V", "LOCK", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getLOCK", "()Ljava/lang/Object;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Object getLOCK() {
            return LookFileGarbageCollector.LOCK;
        }
    }
}
