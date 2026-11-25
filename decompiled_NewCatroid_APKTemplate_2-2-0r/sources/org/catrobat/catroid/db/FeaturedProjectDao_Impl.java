package org.catrobat.catroid.db;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlinx.coroutines.flow.Flow;
import org.catrobat.catroid.retrofit.models.FeaturedProject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/db/FeaturedProjectDao_Impl.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FeaturedProjectDao_Impl extends FeaturedProjectDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<FeaturedProject> __insertionAdapterOfFeaturedProject;
    private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

    public FeaturedProjectDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfFeaturedProject = new EntityInsertionAdapter<FeaturedProject>(__db) { // from class: org.catrobat.catroid.db.FeaturedProjectDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `featured_project` (`id`,`project_id`,`project_url`,`name`,`author`,`featured_image`) VALUES (?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, FeaturedProject value) {
                if (value.getId() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getId());
                }
                if (value.getProject_id() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getProject_id());
                }
                if (value.getProject_url() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getProject_url());
                }
                if (value.getName() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getName());
                }
                if (value.getAuthor() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getAuthor());
                }
                if (value.getFeatured_image() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getFeatured_image());
                }
            }
        };
        this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) { // from class: org.catrobat.catroid.db.FeaturedProjectDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM featured_project";
            }
        };
    }

    @Override // org.catrobat.catroid.db.FeaturedProjectDao
    public void insertFeaturedProjects(final List<FeaturedProject> projects) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfFeaturedProject.insert(projects);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // org.catrobat.catroid.db.FeaturedProjectDao
    public void deleteAll() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteAll.acquire();
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteAll.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // org.catrobat.catroid.db.FeaturedProjectDao
    public Flow<List<FeaturedProject>> getFeaturedProjects() {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM featured_project", 0);
        return CoroutinesRoom.createFlow(this.__db, false, new String[]{"featured_project"}, new Callable<List<FeaturedProject>>() { // from class: org.catrobat.catroid.db.FeaturedProjectDao_Impl.3
            @Override // java.util.concurrent.Callable
            public List<FeaturedProject> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(FeaturedProjectDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "project_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "project_url");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "name");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "author");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "featured_image");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new FeaturedProject(cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5), cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getString(columnIndexOrThrow6)));
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
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
}
