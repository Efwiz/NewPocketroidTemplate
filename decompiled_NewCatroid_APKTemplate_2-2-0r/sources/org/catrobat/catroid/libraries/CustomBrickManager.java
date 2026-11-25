package org.catrobat.catroid.libraries;

import android.util.Log;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraries/CustomBrickManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: CustomBrickManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005J\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006J\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/libraries/CustomBrickManager;", "", "()V", "definitions", "", "", "Lorg/catrobat/catroid/libraries/CustomBrickDefinition;", "findDefinitionById", "id", "getAllDefinitions", "", "registerBrick", "", "definition", "removeBricksByOwner", "libraryId", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CustomBrickManager {
    public static final CustomBrickManager INSTANCE = new CustomBrickManager();
    private static final Map<String, CustomBrickDefinition> definitions = new LinkedHashMap();

    private CustomBrickManager() {
    }

    public final void registerBrick(CustomBrickDefinition definition) {
        Intrinsics.checkNotNullParameter(definition, "definition");
        Map<String, CustomBrickDefinition> map = definitions;
        if (map.containsKey(definition.getId())) {
            Log.w("CustomBrickManager", "Переопределение блока: " + definition.getId());
        }
        map.put(definition.getId(), definition);
    }

    public final void removeBricksByOwner(String libraryId) {
        Intrinsics.checkNotNullParameter(libraryId, "libraryId");
        Map<String, CustomBrickDefinition> map = definitions;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, CustomBrickDefinition> entry : map.entrySet()) {
            if (Intrinsics.areEqual(entry.getValue().getOwnerLibraryId(), libraryId)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        Iterator it = linkedHashMap.keySet().iterator();
        while (it.hasNext()) {
            definitions.remove((String) it.next());
        }
        Log.i("CustomBrickManager", "Удалены блоки, принадлежащие " + libraryId);
    }

    public final CustomBrickDefinition findDefinitionById(String id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        return definitions.get(id2);
    }

    public final List<CustomBrickDefinition> getAllDefinitions() {
        return CollectionsKt.toList(definitions.values());
    }
}
