package org.catrobat.catroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/FavoriteBricksManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: FavoriteBricksManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\r2\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/utils/FavoriteBricksManager;", "", "()V", "KEY_FAVORITES", "", "PREFS_NAME", "addFavorite", "", "context", "Landroid/content/Context;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "getFavoriteClassNames", "", "isFavorite", "", "removeFavorite", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FavoriteBricksManager {
    public static final FavoriteBricksManager INSTANCE = new FavoriteBricksManager();
    private static final String KEY_FAVORITES = "favorites_set";
    private static final String PREFS_NAME = "favorite_bricks_prefs";

    private FavoriteBricksManager() {
    }

    public final Set<String> getFavoriteClassNames(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Set<String> stringSet = context.getSharedPreferences(PREFS_NAME, 0).getStringSet(KEY_FAVORITES, SetsKt.emptySet());
        return stringSet == null ? SetsKt.emptySet() : stringSet;
    }

    public final boolean isFavorite(Context context, Brick brick) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(brick, "brick");
        return getFavoriteClassNames(context).contains(brick.getClass().getName());
    }

    public final void addFavorite(Context context, Brick brick) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(brick, "brick");
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        Set<String> mutableSet = CollectionsKt.toMutableSet(getFavoriteClassNames(context));
        String name2 = brick.getClass().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        mutableSet.add(name2);
        sharedPreferences.edit().putStringSet(KEY_FAVORITES, mutableSet).apply();
    }

    public final void removeFavorite(Context context, Brick brick) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(brick, "brick");
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        Set<String> mutableSet = CollectionsKt.toMutableSet(getFavoriteClassNames(context));
        mutableSet.remove(brick.getClass().getName());
        sharedPreferences.edit().putStringSet(KEY_FAVORITES, mutableSet).apply();
    }
}
