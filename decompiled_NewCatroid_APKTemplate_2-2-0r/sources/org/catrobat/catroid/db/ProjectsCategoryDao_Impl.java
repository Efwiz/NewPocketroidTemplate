package org.catrobat.catroid.db;

import android.database.Cursor;
import androidx.collection.ArrayMap;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.google.android.gms.cast.MediaTrack;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import kotlinx.coroutines.flow.Flow;
import org.catrobat.catroid.retrofit.converters.StringListConverter;
import org.catrobat.catroid.retrofit.models.ProjectCategoryWithResponses;
import org.catrobat.catroid.retrofit.models.ProjectResponse;
import org.catrobat.catroid.retrofit.models.ProjectsCategory;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/db/ProjectsCategoryDao_Impl.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ProjectsCategoryDao_Impl extends ProjectsCategoryDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<ProjectResponse> __insertionAdapterOfProjectResponse;
    private final EntityInsertionAdapter<ProjectsCategory> __insertionAdapterOfProjectsCategory;
    private final SharedSQLiteStatement __preparedStmtOfDeleteAllProjectResponse;
    private final SharedSQLiteStatement __preparedStmtOfDeleteAllProjectsCategory;
    private final StringListConverter __stringListConverter = new StringListConverter();

    public ProjectsCategoryDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfProjectsCategory = new EntityInsertionAdapter<ProjectsCategory>(__db) { // from class: org.catrobat.catroid.db.ProjectsCategoryDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `project_category` (`type`,`name`) VALUES (?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, ProjectsCategory value) {
                if (value.getType() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getType());
                }
                if (value.getName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getName());
                }
            }
        };
        this.__insertionAdapterOfProjectResponse = new EntityInsertionAdapter<ProjectResponse>(__db) { // from class: org.catrobat.catroid.db.ProjectsCategoryDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `project_response` (`id`,`name`,`author`,`description`,`version`,`views`,`download`,`flavor`,`tags`,`uploaded`,`uploadedString`,`screenshotLarge`,`screenshotSmall`,`projectUrl`,`downloadUrl`,`fileSize`,`categoryType`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, ProjectResponse value) {
                if (value.getId() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getId());
                }
                if (value.getName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getName());
                }
                if (value.getAuthor() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getAuthor());
                }
                if (value.getDescription() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getDescription());
                }
                if (value.getVersion() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getVersion());
                }
                stmt.bindLong(6, value.getViews());
                stmt.bindLong(7, value.getDownload());
                if (value.getFlavor() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getFlavor());
                }
                String strStringListToString = ProjectsCategoryDao_Impl.this.__stringListConverter.stringListToString(value.getTags());
                if (strStringListToString == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, strStringListToString);
                }
                stmt.bindLong(10, value.getUploaded());
                if (value.getUploadedString() == null) {
                    stmt.bindNull(11);
                } else {
                    stmt.bindString(11, value.getUploadedString());
                }
                if (value.getScreenshotLarge() == null) {
                    stmt.bindNull(12);
                } else {
                    stmt.bindString(12, value.getScreenshotLarge());
                }
                if (value.getScreenshotSmall() == null) {
                    stmt.bindNull(13);
                } else {
                    stmt.bindString(13, value.getScreenshotSmall());
                }
                if (value.getProjectUrl() == null) {
                    stmt.bindNull(14);
                } else {
                    stmt.bindString(14, value.getProjectUrl());
                }
                if (value.getDownloadUrl() == null) {
                    stmt.bindNull(15);
                } else {
                    stmt.bindString(15, value.getDownloadUrl());
                }
                stmt.bindDouble(16, value.getFileSize());
                if (value.getCategoryType() == null) {
                    stmt.bindNull(17);
                } else {
                    stmt.bindString(17, value.getCategoryType());
                }
            }
        };
        this.__preparedStmtOfDeleteAllProjectsCategory = new SharedSQLiteStatement(__db) { // from class: org.catrobat.catroid.db.ProjectsCategoryDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM project_category";
            }
        };
        this.__preparedStmtOfDeleteAllProjectResponse = new SharedSQLiteStatement(__db) { // from class: org.catrobat.catroid.db.ProjectsCategoryDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM project_response";
            }
        };
    }

    @Override // org.catrobat.catroid.db.ProjectsCategoryDao
    protected void insertProjectCategory(final ProjectsCategory projectsCategory) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfProjectsCategory.insert(projectsCategory);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // org.catrobat.catroid.db.ProjectsCategoryDao
    protected void insertProjectResponses(final List<ProjectResponse> projectResponseList) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfProjectResponse.insert(projectResponseList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // org.catrobat.catroid.db.ProjectsCategoryDao
    public void insertProjectCategoriesWithResponses(final List<ProjectCategoryWithResponses> projectCategoryWithResponses) {
        this.__db.beginTransaction();
        try {
            super.insertProjectCategoriesWithResponses(projectCategoryWithResponses);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // org.catrobat.catroid.db.ProjectsCategoryDao
    public void nukeAll() {
        this.__db.beginTransaction();
        try {
            super.nukeAll();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // org.catrobat.catroid.db.ProjectsCategoryDao
    protected void deleteAllProjectsCategory() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteAllProjectsCategory.acquire();
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteAllProjectsCategory.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // org.catrobat.catroid.db.ProjectsCategoryDao
    protected void deleteAllProjectResponse() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteAllProjectResponse.acquire();
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteAllProjectResponse.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // org.catrobat.catroid.db.ProjectsCategoryDao
    public Flow<List<ProjectCategoryWithResponses>> getProjectsCategories() {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM project_category", 0);
        return CoroutinesRoom.createFlow(this.__db, true, new String[]{"project_response", "project_category"}, new Callable<List<ProjectCategoryWithResponses>>() { // from class: org.catrobat.catroid.db.ProjectsCategoryDao_Impl.5
            @Override // java.util.concurrent.Callable
            public List<ProjectCategoryWithResponses> call() throws Exception {
                ProjectsCategory projectsCategory;
                ProjectsCategoryDao_Impl.this.__db.beginTransaction();
                try {
                    Cursor cursorQuery = DBUtil.query(ProjectsCategoryDao_Impl.this.__db, roomSQLiteQueryAcquire, true, null);
                    try {
                        int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, ModuleXmlParser.TYPE);
                        int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "name");
                        ArrayMap arrayMap = new ArrayMap();
                        while (cursorQuery.moveToNext()) {
                            String string = cursorQuery.getString(columnIndexOrThrow);
                            if (((ArrayList) arrayMap.get(string)) == null) {
                                arrayMap.put(string, new ArrayList());
                            }
                        }
                        cursorQuery.moveToPosition(-1);
                        ProjectsCategoryDao_Impl.this.__fetchRelationshipprojectResponseAsorgCatrobatCatroidRetrofitModelsProjectResponse(arrayMap);
                        ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                        while (cursorQuery.moveToNext()) {
                            if (cursorQuery.isNull(columnIndexOrThrow) && cursorQuery.isNull(columnIndexOrThrow2)) {
                                projectsCategory = null;
                            } else {
                                projectsCategory = new ProjectsCategory(cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2));
                            }
                            ArrayList arrayList2 = (ArrayList) arrayMap.get(cursorQuery.getString(columnIndexOrThrow));
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            arrayList.add(new ProjectCategoryWithResponses(projectsCategory, arrayList2));
                        }
                        ProjectsCategoryDao_Impl.this.__db.setTransactionSuccessful();
                        cursorQuery.close();
                        ProjectsCategoryDao_Impl.this.__db.endTransaction();
                        return arrayList;
                    } catch (Throwable th) {
                        cursorQuery.close();
                        throw th;
                    }
                } catch (Throwable th2) {
                    ProjectsCategoryDao_Impl.this.__db.endTransaction();
                    throw th2;
                }
            }

            protected void finalize() {
                roomSQLiteQueryAcquire.release();
            }
        });
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void __fetchRelationshipprojectResponseAsorgCatrobatCatroidRetrofitModelsProjectResponse(final ArrayMap<String, ArrayList<ProjectResponse>> _map) {
        int i;
        ProjectsCategoryDao_Impl projectsCategoryDao_Impl = this;
        ArrayMap<String, ArrayList<ProjectResponse>> arrayMap = _map;
        Set<String> setKeySet = _map.keySet();
        if (setKeySet.isEmpty()) {
            return;
        }
        if (_map.getSize() > 999) {
            ArrayMap<String, ArrayList<ProjectResponse>> arrayMap2 = new ArrayMap<>(999);
            int size = _map.getSize();
            int i2 = 0;
            int i3 = 0;
            while (i2 < size) {
                arrayMap2.put(arrayMap.keyAt(i2), arrayMap.valueAt(i2));
                i2++;
                i3++;
                if (i3 == 999) {
                    projectsCategoryDao_Impl.__fetchRelationshipprojectResponseAsorgCatrobatCatroidRetrofitModelsProjectResponse(arrayMap2);
                    arrayMap2 = new ArrayMap<>(999);
                    i3 = 0;
                }
            }
            if (i3 > 0) {
                projectsCategoryDao_Impl.__fetchRelationshipprojectResponseAsorgCatrobatCatroidRetrofitModelsProjectResponse(arrayMap2);
                return;
            }
            return;
        }
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("SELECT `id`,`name`,`author`,`description`,`version`,`views`,`download`,`flavor`,`tags`,`uploaded`,`uploadedString`,`screenshotLarge`,`screenshotSmall`,`projectUrl`,`downloadUrl`,`fileSize`,`categoryType` FROM `project_response` WHERE `categoryType` IN (");
        int size2 = setKeySet.size();
        StringUtil.appendPlaceholders(sbNewStringBuilder, size2);
        sbNewStringBuilder.append(Constant.AFTER_QUTO);
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire(sbNewStringBuilder.toString(), size2);
        int i4 = 1;
        for (String str : setKeySet) {
            if (str == null) {
                roomSQLiteQueryAcquire.bindNull(i4);
            } else {
                roomSQLiteQueryAcquire.bindString(i4, str);
            }
            i4++;
        }
        Cursor cursorQuery = DBUtil.query(projectsCategoryDao_Impl.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndex = CursorUtil.getColumnIndex(cursorQuery, "categoryType");
            if (columnIndex == -1) {
                return;
            }
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "name");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "author");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, MediaTrack.ROLE_DESCRIPTION);
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "version");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "views");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "download");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "flavor");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "tags");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "uploaded");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "uploadedString");
            int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "screenshotLarge");
            int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "screenshotSmall");
            int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "projectUrl");
            int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "downloadUrl");
            int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "fileSize");
            int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "categoryType");
            while (cursorQuery.moveToNext()) {
                int i5 = columnIndexOrThrow17;
                ArrayList<ProjectResponse> arrayList = arrayMap.get(cursorQuery.getString(columnIndex));
                if (arrayList != null) {
                    ProjectResponse projectResponse = new ProjectResponse();
                    projectResponse.setId(cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow));
                    projectResponse.setName(cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2));
                    projectResponse.setAuthor(cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3));
                    projectResponse.setDescription(cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4));
                    projectResponse.setVersion(cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5));
                    projectResponse.setViews(cursorQuery.getInt(columnIndexOrThrow6));
                    projectResponse.setDownload(cursorQuery.getInt(columnIndexOrThrow7));
                    projectResponse.setFlavor(cursorQuery.isNull(columnIndexOrThrow8) ? null : cursorQuery.getString(columnIndexOrThrow8));
                    projectResponse.setTags(projectsCategoryDao_Impl.__stringListConverter.stringToStringList(cursorQuery.isNull(columnIndexOrThrow9) ? null : cursorQuery.getString(columnIndexOrThrow9)));
                    projectResponse.setUploaded(cursorQuery.getLong(columnIndexOrThrow10));
                    int i6 = columnIndexOrThrow11;
                    projectResponse.setUploadedString(cursorQuery.isNull(i6) ? null : cursorQuery.getString(i6));
                    int i7 = columnIndexOrThrow12;
                    projectResponse.setScreenshotLarge(cursorQuery.isNull(i7) ? null : cursorQuery.getString(i7));
                    columnIndexOrThrow13 = columnIndexOrThrow13;
                    projectResponse.setScreenshotSmall(cursorQuery.isNull(columnIndexOrThrow13) ? null : cursorQuery.getString(columnIndexOrThrow13));
                    columnIndexOrThrow14 = columnIndexOrThrow14;
                    projectResponse.setProjectUrl(cursorQuery.isNull(columnIndexOrThrow14) ? null : cursorQuery.getString(columnIndexOrThrow14));
                    columnIndexOrThrow15 = columnIndexOrThrow15;
                    projectResponse.setDownloadUrl(cursorQuery.isNull(columnIndexOrThrow15) ? null : cursorQuery.getString(columnIndexOrThrow15));
                    columnIndexOrThrow11 = i6;
                    columnIndexOrThrow12 = i7;
                    projectResponse.setFileSize(cursorQuery.getDouble(columnIndexOrThrow16));
                    i = i5;
                    projectResponse.setCategoryType(cursorQuery.isNull(i) ? null : cursorQuery.getString(i));
                    arrayList.add(projectResponse);
                } else {
                    columnIndex = columnIndex;
                    columnIndexOrThrow = columnIndexOrThrow;
                    i = i5;
                }
                arrayMap = _map;
                columnIndexOrThrow17 = i;
                columnIndexOrThrow = columnIndexOrThrow;
                columnIndex = columnIndex;
                projectsCategoryDao_Impl = this;
            }
        } finally {
            cursorQuery.close();
        }
    }
}
