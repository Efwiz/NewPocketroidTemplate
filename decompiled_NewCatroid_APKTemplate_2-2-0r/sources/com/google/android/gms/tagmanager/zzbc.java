package com.google.android.gms.tagmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.HashSet;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbc extends SQLiteOpenHelper {
    final /* synthetic */ zzbe zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbc(zzbe zzbeVar, Context context, String str) {
        super(context, "google_tagmanager.db", (SQLiteDatabase.CursorFactory) null, 1);
        this.zza = zzbeVar;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase writableDatabase;
        try {
            writableDatabase = super.getWritableDatabase();
        } catch (SQLiteException unused) {
            this.zza.zzc.getDatabasePath("google_tagmanager.db").delete();
            writableDatabase = null;
        }
        return writableDatabase == null ? super.getWritableDatabase() : writableDatabase;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzbv.zza(sQLiteDatabase.getPath());
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onOpen(SQLiteDatabase sQLiteDatabase) throws Throwable {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{"datalayer"}, null, null, null);
                try {
                    boolean zMoveToFirst = cursorQuery.moveToFirst();
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (zMoveToFirst) {
                        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
                        HashSet hashSet = new HashSet();
                        try {
                            for (String str : cursorRawQuery.getColumnNames()) {
                                hashSet.add(str);
                            }
                            cursorRawQuery.close();
                            if (!hashSet.remove(ASTExpr.DEFAULT_MAP_KEY_NAME) || !hashSet.remove("value") || !hashSet.remove(SchemaSymbols.ATTVAL_ID) || !hashSet.remove("expires")) {
                                throw new SQLiteException("Database column missing");
                            }
                            if (!hashSet.isEmpty()) {
                                throw new SQLiteException("Database has extra columns");
                            }
                            return;
                        } catch (Throwable th) {
                            cursorRawQuery.close();
                            throw th;
                        }
                    }
                } catch (SQLiteException unused) {
                    cursor = cursorQuery;
                    zzdh.zzc("Error querying for table datalayer");
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (SQLiteException unused2) {
            }
            sQLiteDatabase.execSQL(zzbe.zza);
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
