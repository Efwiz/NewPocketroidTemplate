package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.lang.ref.Reference;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: loaded from: classes3.dex */
public final class LastComputedIconCache {
    private static final Key<Reference<Int2ObjectMap<Icon>>> LAST_COMPUTED_ICON = Key.create("lastComputedIcon");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "holder";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/LastComputedIconCache";
        if (i == 1) {
            objArr[2] = "put";
        } else if (i != 2) {
            objArr[2] = "get";
        } else {
            objArr[2] = LineReader.CLEAR;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static Icon get(UserDataHolder userDataHolder, int i) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(0);
        }
        Int2ObjectMap int2ObjectMap = (Int2ObjectMap) SoftReference.dereference((Reference) userDataHolder.getUserData(LAST_COMPUTED_ICON));
        if (int2ObjectMap == null) {
            return null;
        }
        return (Icon) int2ObjectMap.get(i);
    }

    public static void put(UserDataHolder userDataHolder, Icon icon, int i) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(1);
        }
        Reference reference = (Reference) userDataHolder.getUserData(LAST_COMPUTED_ICON);
        Int2ObjectMap int2ObjectMapSynchronize = (Int2ObjectMap) SoftReference.dereference(reference);
        if (icon != null) {
            while (int2ObjectMapSynchronize == null) {
                int2ObjectMapSynchronize = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap());
                Key<Reference<Int2ObjectMap<Icon>>> key = LAST_COMPUTED_ICON;
                if (!((UserDataHolderEx) userDataHolder).replace(key, reference, new SoftReference(int2ObjectMapSynchronize))) {
                    int2ObjectMapSynchronize = (Int2ObjectMap) SoftReference.dereference((Reference) userDataHolder.getUserData(key));
                }
            }
        } else if (int2ObjectMapSynchronize == null) {
            return;
        }
        if (icon == null) {
            int2ObjectMapSynchronize.remove(i);
        } else {
            int2ObjectMapSynchronize.put(i, icon);
        }
    }
}
