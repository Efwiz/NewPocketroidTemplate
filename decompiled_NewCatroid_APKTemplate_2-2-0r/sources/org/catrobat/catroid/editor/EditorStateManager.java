package org.catrobat.catroid.editor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/EditorStateManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EditorStateManager {
    private static String cachedSceneJson;

    public static void cacheScene(String sceneJson) {
        cachedSceneJson = sceneJson;
    }

    public static String retrieveScene() {
        return cachedSceneJson;
    }

    public static boolean hasCachedScene() {
        String str = cachedSceneJson;
        return (str == null || str.isEmpty()) ? false : true;
    }

    public static void clearCache() {
        cachedSceneJson = null;
    }
}
