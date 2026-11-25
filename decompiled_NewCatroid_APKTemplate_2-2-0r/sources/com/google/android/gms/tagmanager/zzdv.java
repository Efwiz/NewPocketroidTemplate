package com.google.android.gms.tagmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.HashSet;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdv extends SQLiteOpenHelper {
    final /* synthetic */ zzdw zza;
    private boolean zzb;
    private long zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdv(zzdw zzdwVar, Context context, String str) {
        super(context, "gtm_urls.db", (SQLiteDatabase.CursorFactory) null, 1);
        this.zza = zzdwVar;
        this.zzc = 0L;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase writableDatabase;
        if (this.zzb && this.zzc + DateUtils.MILLIS_PER_HOUR > this.zza.zzg.currentTimeMillis()) {
            throw new SQLiteException("Database creation failed");
        }
        this.zzb = true;
        this.zzc = this.zza.zzg.currentTimeMillis();
        try {
            writableDatabase = super.getWritableDatabase();
        } catch (SQLiteException unused) {
            this.zza.zzd.getDatabasePath(this.zza.zze).delete();
            writableDatabase = null;
        }
        if (writableDatabase == null) {
            writableDatabase = super.getWritableDatabase();
        }
        this.zzb = false;
        return writableDatabase;
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
                Cursor cursorQuery = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{"gtm_hits"}, null, null, null);
                try {
                    boolean zMoveToFirst = cursorQuery.moveToFirst();
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (zMoveToFirst) {
                        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
                        HashSet hashSet = new HashSet();
                        try {
                            for (String str : cursorRawQuery.getColumnNames()) {
                                hashSet.add(str);
                            }
                            cursorRawQuery.close();
                            if (!hashSet.remove("hit_id") || !hashSet.remove("hit_url") || !hashSet.remove("hit_time") || !hashSet.remove("hit_first_send_time")) {
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
                    zzdh.zzc("Error querying for table gtm_hits");
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
            sQLiteDatabase.execSQL(zzdw.zza);
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
