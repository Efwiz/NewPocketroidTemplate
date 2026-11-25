package org.catrobat.catroid.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.google.android.gms.cast.MediaTrack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.catrobat.catroid.ui.SpriteActivity;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/db/AppDatabase_Impl.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class AppDatabase_Impl extends AppDatabase {
    private volatile FeaturedProjectDao _featuredProjectDao;
    private volatile ProjectsCategoryDao _projectsCategoryDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.f43name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) { // from class: org.catrobat.catroid.db.AppDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase _db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `featured_project` (`id` TEXT NOT NULL, `project_id` TEXT NOT NULL, `project_url` TEXT NOT NULL, `name` TEXT NOT NULL, `author` TEXT NOT NULL, `featured_image` TEXT NOT NULL, PRIMARY KEY(`id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `project_category` (`type` TEXT NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`type`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `project_response` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `author` TEXT NOT NULL, `description` TEXT NOT NULL, `version` TEXT NOT NULL, `views` INTEGER NOT NULL, `download` INTEGER NOT NULL, `flavor` TEXT NOT NULL, `tags` TEXT NOT NULL, `uploaded` INTEGER NOT NULL, `uploadedString` TEXT NOT NULL, `screenshotLarge` TEXT NOT NULL, `screenshotSmall` TEXT NOT NULL, `projectUrl` TEXT NOT NULL, `downloadUrl` TEXT NOT NULL, `fileSize` REAL NOT NULL, `categoryType` TEXT NOT NULL, PRIMARY KEY(`id`, `categoryType`))");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c60ebf67428479ff4619d56d1eb30d08')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `featured_project`");
                _db.execSQL("DROP TABLE IF EXISTS `project_category`");
                _db.execSQL("DROP TABLE IF EXISTS `project_response`");
                if (AppDatabase_Impl.this.mCallbacks != null) {
                    int size = AppDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) AppDatabase_Impl.this.mCallbacks.get(i)).onDestructiveMigration(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase _db) {
                if (AppDatabase_Impl.this.mCallbacks != null) {
                    int size = AppDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) AppDatabase_Impl.this.mCallbacks.get(i)).onCreate(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase _db) {
                AppDatabase_Impl.this.mDatabase = _db;
                AppDatabase_Impl.this.internalInitInvalidationTracker(_db);
                if (AppDatabase_Impl.this.mCallbacks != null) {
                    int size = AppDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) AppDatabase_Impl.this.mCallbacks.get(i)).onOpen(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(SupportSQLiteDatabase _db) {
                DBUtil.dropFtsSyncTriggers(_db);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
                HashMap map = new HashMap(6);
                map.put("id", new TableInfo.Column("id", SpriteActivity.EXTRA_TEXT, true, 1, null, 1));
                map.put("project_id", new TableInfo.Column("project_id", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map.put("project_url", new TableInfo.Column("project_url", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map.put("name", new TableInfo.Column("name", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map.put("author", new TableInfo.Column("author", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map.put("featured_image", new TableInfo.Column("featured_image", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                TableInfo tableInfo = new TableInfo("featured_project", map, new HashSet(0), new HashSet(0));
                TableInfo tableInfo2 = TableInfo.read(_db, "featured_project");
                if (!tableInfo.equals(tableInfo2)) {
                    return new RoomOpenHelper.ValidationResult(false, "featured_project(org.catrobat.catroid.retrofit.models.FeaturedProject).\n Expected:\n" + tableInfo + "\n Found:\n" + tableInfo2);
                }
                HashMap map2 = new HashMap(2);
                map2.put(ModuleXmlParser.TYPE, new TableInfo.Column(ModuleXmlParser.TYPE, SpriteActivity.EXTRA_TEXT, true, 1, null, 1));
                map2.put("name", new TableInfo.Column("name", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                TableInfo tableInfo3 = new TableInfo("project_category", map2, new HashSet(0), new HashSet(0));
                TableInfo tableInfo4 = TableInfo.read(_db, "project_category");
                if (!tableInfo3.equals(tableInfo4)) {
                    return new RoomOpenHelper.ValidationResult(false, "project_category(org.catrobat.catroid.retrofit.models.ProjectsCategory).\n Expected:\n" + tableInfo3 + "\n Found:\n" + tableInfo4);
                }
                HashMap map3 = new HashMap(17);
                map3.put("id", new TableInfo.Column("id", SpriteActivity.EXTRA_TEXT, true, 1, null, 1));
                map3.put("name", new TableInfo.Column("name", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("author", new TableInfo.Column("author", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put(MediaTrack.ROLE_DESCRIPTION, new TableInfo.Column(MediaTrack.ROLE_DESCRIPTION, SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("version", new TableInfo.Column("version", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("views", new TableInfo.Column("views", "INTEGER", true, 0, null, 1));
                map3.put("download", new TableInfo.Column("download", "INTEGER", true, 0, null, 1));
                map3.put("flavor", new TableInfo.Column("flavor", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("tags", new TableInfo.Column("tags", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("uploaded", new TableInfo.Column("uploaded", "INTEGER", true, 0, null, 1));
                map3.put("uploadedString", new TableInfo.Column("uploadedString", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("screenshotLarge", new TableInfo.Column("screenshotLarge", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("screenshotSmall", new TableInfo.Column("screenshotSmall", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("projectUrl", new TableInfo.Column("projectUrl", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("downloadUrl", new TableInfo.Column("downloadUrl", SpriteActivity.EXTRA_TEXT, true, 0, null, 1));
                map3.put("fileSize", new TableInfo.Column("fileSize", "REAL", true, 0, null, 1));
                map3.put("categoryType", new TableInfo.Column("categoryType", SpriteActivity.EXTRA_TEXT, true, 2, null, 1));
                TableInfo tableInfo5 = new TableInfo("project_response", map3, new HashSet(0), new HashSet(0));
                TableInfo tableInfo6 = TableInfo.read(_db, "project_response");
                if (!tableInfo5.equals(tableInfo6)) {
                    return new RoomOpenHelper.ValidationResult(false, "project_response(org.catrobat.catroid.retrofit.models.ProjectResponse).\n Expected:\n" + tableInfo5 + "\n Found:\n" + tableInfo6);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "c60ebf67428479ff4619d56d1eb30d08", "e1fa942ed1621eaa158e06aa895679d7")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "featured_project", "project_category", "project_response");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `featured_project`");
            writableDatabase.execSQL("DELETE FROM `project_category`");
            writableDatabase.execSQL("DELETE FROM `project_response`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // androidx.room.RoomDatabase
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap map = new HashMap();
        map.put(FeaturedProjectDao.class, FeaturedProjectDao_Impl.getRequiredConverters());
        map.put(ProjectsCategoryDao.class, ProjectsCategoryDao_Impl.getRequiredConverters());
        return map;
    }

    @Override // org.catrobat.catroid.db.AppDatabase
    public FeaturedProjectDao featuredProjectDao() {
        FeaturedProjectDao featuredProjectDao;
        if (this._featuredProjectDao != null) {
            return this._featuredProjectDao;
        }
        synchronized (this) {
            if (this._featuredProjectDao == null) {
                this._featuredProjectDao = new FeaturedProjectDao_Impl(this);
            }
            featuredProjectDao = this._featuredProjectDao;
        }
        return featuredProjectDao;
    }

    @Override // org.catrobat.catroid.db.AppDatabase
    public ProjectsCategoryDao projectCategoryDao() {
        ProjectsCategoryDao projectsCategoryDao;
        if (this._projectsCategoryDao != null) {
            return this._projectsCategoryDao;
        }
        synchronized (this) {
            if (this._projectsCategoryDao == null) {
                this._projectsCategoryDao = new ProjectsCategoryDao_Impl(this);
            }
            projectsCategoryDao = this._projectsCategoryDao;
        }
        return projectsCategoryDao;
    }
}
