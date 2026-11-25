package com.google.android.gms.internal.gtm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.HttpUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.bytebuddy.description.type.TypeDescription;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzce extends zzbs implements Closeable {
    private static final String zza = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    private static final String zzb = String.format("SELECT MAX(%s) FROM %s WHERE 1;", "hit_time", "hits2");
    private final zzcd zzc;
    private final zzfo zzd;
    private final zzfo zze;

    zzce(zzbv zzbvVar) {
        super(zzbvVar);
        this.zzd = new zzfo(zzC());
        this.zze = new zzfo(zzC());
        this.zzc = new zzcd(this, zzbvVar.zza(), zzae());
    }

    private final long zzad(String str, String[] strArr, long j) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = zzf().rawQuery(str, strArr);
                if (cursorRawQuery.moveToFirst()) {
                    long j2 = cursorRawQuery.getLong(0);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return j2;
                }
                if (cursorRawQuery == null) {
                    return 0L;
                }
                cursorRawQuery.close();
                return 0L;
            } catch (SQLiteException e) {
                zzL("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zzae() {
        zzw();
        zzw();
        return "google_analytics_v4.db";
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        try {
            this.zzc.close();
        } catch (SQLiteException e) {
            zzK("Sql error closing database", e);
        } catch (IllegalStateException e2) {
            zzK("Error closing database", e2);
        }
    }

    public final void zzZ(List<Long> list) {
        Preconditions.checkNotNull(list);
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("hit_id in (");
        for (int i = 0; i < list.size(); i++) {
            Long l = list.get(i);
            if (l == null || l.longValue() == 0) {
                throw new SQLiteException("Invalid hit id");
            }
            if (i > 0) {
                sb.append(",");
            }
            sb.append(l);
        }
        sb.append(Constant.AFTER_QUTO);
        String string = sb.toString();
        try {
            SQLiteDatabase sQLiteDatabaseZzf = zzf();
            zzP("Deleting dispatched hits. count", Integer.valueOf(list.size()));
            int iDelete = sQLiteDatabaseZzf.delete("hits2", string, null);
            if (iDelete != list.size()) {
                zzU("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(iDelete), string);
            }
        } catch (SQLiteException e) {
            zzK("Error deleting hits", e);
            throw e;
        }
    }

    public final int zza() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (!this.zzd.zzc(DateUtils.MILLIS_PER_DAY)) {
            return 0;
        }
        this.zzd.zzb();
        zzO("Deleting stale hits (if any)");
        int iDelete = zzf().delete("hits2", "hit_time < ?", new String[]{Long.toString(zzC().currentTimeMillis() - 2592000000L)});
        zzP("Deleted stale hits, count", Integer.valueOf(iDelete));
        return iDelete;
    }

    public final void zzaa() {
        zzW();
        zzf().endTransaction();
    }

    public final void zzab() {
        zzW();
        zzf().setTransactionSuccessful();
    }

    final boolean zzac() {
        return zzb() == 0;
    }

    public final long zzb() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = zzf().rawQuery("SELECT COUNT(*) FROM hits2", null);
                if (!cursorRawQuery.moveToFirst()) {
                    throw new SQLiteException("Database returned empty set");
                }
                long j = cursorRawQuery.getLong(0);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzL("Database error", "SELECT COUNT(*) FROM hits2", e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public final long zzc() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        return zzad(zzb, null, 0L);
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
    }

    public final long zze(long j, String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzW();
        com.google.android.gms.analytics.zzr.zzh();
        return zzad("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{"0", str, str2}, 0L);
    }

    final SQLiteDatabase zzf() {
        try {
            return this.zzc.getWritableDatabase();
        } catch (SQLiteException e) {
            zzS("Error opening database", e);
            throw e;
        }
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00ce  */
    /* JADX WARN: Multi-variable type inference failed */
    public final List<zzex> zzj(long j) throws Throwable {
        Cursor cursor;
        Cursor cursor2;
        ArrayList arrayList;
        int i = 0;
        Preconditions.checkArgument(j >= 0);
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        try {
            int i2 = 3;
            Cursor cursorQuery = zzf().query("hits2", new String[]{"hit_id", "hit_time", "hit_string", "hit_url", "hit_app_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Long.toString(j));
            try {
                ArrayList arrayList2 = new ArrayList();
                if (cursorQuery.moveToFirst()) {
                    while (true) {
                        long j2 = cursorQuery.getLong(i);
                        long j3 = cursorQuery.getLong(1);
                        String string = cursorQuery.getString(2);
                        String string2 = cursorQuery.getString(i2);
                        int i3 = cursorQuery.getInt(4);
                        Map<String, String> mapZzk = zzk(string);
                        boolean z = (!TextUtils.isEmpty(string2) && string2.startsWith("http:")) ? i : 1;
                        arrayList = arrayList2;
                        cursor2 = cursorQuery;
                        try {
                            arrayList.add(new zzex(this, mapZzk, j3, z, j2, i3, null));
                            if (!cursor2.moveToNext()) {
                                break;
                            }
                            arrayList2 = arrayList;
                            cursorQuery = cursor2;
                            i2 = 3;
                            i = 0;
                        } catch (SQLiteException e) {
                            e = e;
                            cursor = cursor2;
                            try {
                                zzK("Error loading hits from the database", e);
                                throw e;
                            } catch (Throwable th) {
                                th = th;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            cursor = cursor2;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    }
                } else {
                    arrayList = arrayList2;
                    cursor2 = cursorQuery;
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
                return arrayList;
            } catch (SQLiteException e2) {
                e = e2;
                cursor2 = cursorQuery;
            } catch (Throwable th3) {
                th = th3;
                cursor2 = cursorQuery;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
        }
    }

    final Map<String, String> zzk(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith(TypeDescription.Generic.OfWildcardType.SYMBOL)) {
                String strValueOf = String.valueOf(str);
                str = strValueOf.length() != 0 ? TypeDescription.Generic.OfWildcardType.SYMBOL.concat(strValueOf) : new String(TypeDescription.Generic.OfWildcardType.SYMBOL);
            }
            return HttpUtils.parse(new URI(str), "UTF-8");
        } catch (URISyntaxException e) {
            zzK("Error parsing hit parameters", e);
            return new HashMap(0);
        }
    }

    final Map<String, String> zzl(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            String strValueOf = String.valueOf(str);
            return HttpUtils.parse(new URI(strValueOf.length() != 0 ? TypeDescription.Generic.OfWildcardType.SYMBOL.concat(strValueOf) : new String(TypeDescription.Generic.OfWildcardType.SYMBOL)), "UTF-8");
        } catch (URISyntaxException e) {
            zzK("Error parsing property parameters", e);
            return new HashMap(0);
        }
    }

    public final void zzm() {
        zzW();
        zzf().beginTransaction();
    }

    public final void zzn(long j) {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        ArrayList arrayList = new ArrayList(1);
        Long lValueOf = Long.valueOf(j);
        arrayList.add(lValueOf);
        zzP("Deleting hit, id", lValueOf);
        zzZ(arrayList);
    }
}
