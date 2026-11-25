package org.catrobat.catroid.db;

import androidx.room.RoomDatabase;
import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/db/AppDatabase.class
  classes14.dex
 */
/* JADX INFO: compiled from: AppDatabase.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b'\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/db/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "featuredProjectDao", "Lorg/catrobat/catroid/db/FeaturedProjectDao;", "projectCategoryDao", "Lorg/catrobat/catroid/db/ProjectsCategoryDao;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FeaturedProjectDao featuredProjectDao();

    public abstract ProjectsCategoryDao projectCategoryDao();
}
