package org.catrobat.catroid.db;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/db/DatabaseMigrations.class
  classes14.dex
 */
/* JADX INFO: compiled from: DatabaseMigrations.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/db/DatabaseMigrations;", "", "()V", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "getMIGRATION_1_2", "()Landroidx/room/migration/Migration;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DatabaseMigrations {
    public static final DatabaseMigrations INSTANCE = new DatabaseMigrations();
    private static final Migration MIGRATION_1_2 = new Migration() { // from class: org.catrobat.catroid.db.DatabaseMigrations$MIGRATION_1_2$1
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase database) {
            Intrinsics.checkNotNullParameter(database, "database");
            database.execSQL("CREATE TABLE IF NOT EXISTS `project_category` (`type` TEXT NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`type`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `project_response` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `author` TEXT NOT NULL, `description` TEXT NOT NULL, `version` TEXT NOT NULL, `views` INTEGER NOT NULL, `download` INTEGER NOT NULL, `flavor` TEXT NOT NULL, `tags` TEXT NOT NULL, `uploaded` INTEGER NOT NULL, `uploadedString` TEXT NOT NULL, `screenshotLarge` TEXT NOT NULL, `screenshotSmall` TEXT NOT NULL, `projectUrl` TEXT NOT NULL, `downloadUrl` TEXT NOT NULL, `fileSize` REAL NOT NULL, `categoryType` TEXT NOT NULL, PRIMARY KEY(`id`, `categoryType`))");
        }
    };

    private DatabaseMigrations() {
    }

    public final Migration getMIGRATION_1_2() {
        return MIGRATION_1_2;
    }
}
