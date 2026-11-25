package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import java.util.ArrayList;
import java.util.Collections;
import net.bytebuddy.description.type.TypeDescription;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdw implements zzcd {
    private static final String zza = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", "gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time");
    private final zzdv zzb;
    private volatile zzbk zzc;
    private final Context zzd;
    private final String zze;
    private long zzf;
    private final Clock zzg;
    private final int zzh;
    private final zzez zzi;

    zzdw(zzez zzezVar, Context context, byte[] bArr) {
        Context applicationContext = context.getApplicationContext();
        this.zzd = applicationContext;
        this.zze = "gtm_urls.db";
        this.zzi = zzezVar;
        this.zzg = DefaultClock.getInstance();
        this.zzb = new zzdv(this, applicationContext, "gtm_urls.db");
        this.zzc = new zzfj(applicationContext, new zzdu(this));
        this.zzf = 0L;
        this.zzh = 2000;
    }

    static /* bridge */ /* synthetic */ void zzi(zzdw zzdwVar, long j, long j2) {
        SQLiteDatabase sQLiteDatabaseZzk = zzdwVar.zzk("Error opening database for getNumStoredHits.");
        if (sQLiteDatabaseZzk == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_first_send_time", Long.valueOf(j2));
        try {
            sQLiteDatabaseZzk.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
        } catch (SQLiteException unused) {
            StringBuilder sb = new StringBuilder(69);
            sb.append("Error setting HIT_FIRST_DISPATCH_TIME for hitId: ");
            sb.append(j);
            zzdh.zzc(sb.toString());
            zzdwVar.zzl(j);
        }
    }

    private final SQLiteDatabase zzk(String str) {
        try {
            return this.zzb.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzdh.zzc(str);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzl(long j) {
        zzj(new String[]{String.valueOf(j)});
    }

    /* JADX WARN: Code duplicated, block: B:109:0x01e5  */
    /* JADX WARN: Code duplicated, block: B:114:0x01f3  */
    /* JADX WARN: Code duplicated, block: B:119:0x01fb  */
    /* JADX WARN: Code duplicated, block: B:150:0x0146 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:152:0x0148 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:153:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:154:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:50:0x0117 A[Catch: all -> 0x00f3, TryCatch #7 {all -> 0x00f3, blocks: (B:22:0x00a6, B:25:0x00ad, B:27:0x00ba, B:29:0x00e3, B:28:0x00c8, B:48:0x0107, B:50:0x0117, B:52:0x0121, B:53:0x012e, B:55:0x0134, B:60:0x0148, B:51:0x011c), top: B:125:0x00a6 }] */
    /* JADX WARN: Code duplicated, block: B:51:0x011c A[Catch: all -> 0x00f3, TryCatch #7 {all -> 0x00f3, blocks: (B:22:0x00a6, B:25:0x00ad, B:27:0x00ba, B:29:0x00e3, B:28:0x00c8, B:48:0x0107, B:50:0x0117, B:52:0x0121, B:53:0x012e, B:55:0x0134, B:60:0x0148, B:51:0x011c), top: B:125:0x00a6 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x0134 A[Catch: all -> 0x00f3, TryCatch #7 {all -> 0x00f3, blocks: (B:22:0x00a6, B:25:0x00ad, B:27:0x00ba, B:29:0x00e3, B:28:0x00c8, B:48:0x0107, B:50:0x0117, B:52:0x0121, B:53:0x012e, B:55:0x0134, B:60:0x0148, B:51:0x011c), top: B:125:0x00a6 }] */
    /* JADX WARN: Code duplicated, block: B:57:0x0144 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:59:0x0147  */
    /* JADX WARN: Code duplicated, block: B:63:0x014e  */
    /* JADX WARN: Code duplicated, block: B:65:0x0154  */
    /* JADX WARN: Code duplicated, block: B:83:0x0185 A[Catch: all -> 0x01f7, TryCatch #9 {all -> 0x01f7, blocks: (B:81:0x0175, B:83:0x0185, B:85:0x018f, B:84:0x018a), top: B:133:0x0175 }] */
    /* JADX WARN: Code duplicated, block: B:84:0x018a A[Catch: all -> 0x01f7, TryCatch #9 {all -> 0x01f7, blocks: (B:81:0x0175, B:83:0x0185, B:85:0x018f, B:84:0x018a), top: B:133:0x0175 }] */
    /* JADX WARN: Code duplicated, block: B:87:0x0194  */
    /* JADX WARN: Code duplicated, block: B:90:0x019d  */
    /* JADX WARN: Code duplicated, block: B:92:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:95:0x01b8  */
    /* JADX WARN: Code duplicated, block: B:99:0x01d1  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v47, types: [com.google.android.gms.tagmanager.zzez] */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v15 */
    /* JADX WARN: Type inference failed for: r15v16 */
    /* JADX WARN: Type inference failed for: r15v17 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v8, types: [boolean] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.google.android.gms.tagmanager.zzcd
    public final void zza() throws Throwable {
        ?? r15;
        int i;
        Cursor cursor;
        Cursor cursor2;
        String strValueOf;
        String str;
        Cursor cursor3;
        ArrayList<zzca> arrayList;
        Cursor cursorQuery;
        String strValueOf2;
        String str2;
        boolean z;
        SQLiteDatabase sQLiteDatabaseZzk;
        Cursor cursorQuery2;
        int count;
        zzdh.zzb.zzd("GTM Dispatch running...");
        if (this.zzc.zzb()) {
            ArrayList arrayList2 = new ArrayList();
            SQLiteDatabase sQLiteDatabaseZzk2 = zzk("Error opening database for peekHits");
            if (sQLiteDatabaseZzk2 == null) {
                r15 = 1;
                i = 0;
            } else {
                try {
                    try {
                        r15 = 1;
                        i = 0;
                        try {
                            Cursor cursorQuery3 = sQLiteDatabaseZzk2.query("gtm_hits", new String[]{"hit_id", "hit_time", "hit_first_send_time"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(40));
                            try {
                                try {
                                    ArrayList arrayList3 = new ArrayList();
                                    try {
                                        if (cursorQuery3.moveToFirst()) {
                                            do {
                                                arrayList3.add(new zzca(cursorQuery3.getLong(0), cursorQuery3.getLong(1), cursorQuery3.getLong(2)));
                                            } while (cursorQuery3.moveToNext());
                                        }
                                        if (cursorQuery3 != null) {
                                            cursorQuery3.close();
                                        }
                                        try {
                                            arrayList = arrayList3;
                                            cursor3 = cursorQuery3;
                                            try {
                                                cursorQuery = sQLiteDatabaseZzk2.query("gtm_hits", new String[]{"hit_id", "hit_url"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(40));
                                                try {
                                                    try {
                                                        if (cursorQuery.moveToFirst()) {
                                                            int i2 = 0;
                                                            do {
                                                                if (((SQLiteCursor) cursorQuery).getWindow().getNumRows() > 0) {
                                                                    ((zzca) arrayList.get(i2)).zzd(cursorQuery.getString(1));
                                                                } else {
                                                                    zzdh.zzc(String.format("HitString for hitId %d too large.  Hit will be deleted.", Long.valueOf(((zzca) arrayList.get(i2)).zzb())));
                                                                }
                                                                i2++;
                                                            } while (cursorQuery.moveToNext());
                                                        }
                                                        if (cursorQuery != null) {
                                                            cursorQuery.close();
                                                        }
                                                        arrayList2 = arrayList;
                                                    } catch (SQLiteException e) {
                                                        e = e;
                                                        strValueOf2 = String.valueOf(e.getMessage());
                                                        if (strValueOf2.length() != 0) {
                                                            str2 = "Error in peekHits fetching hit url: ".concat(strValueOf2);
                                                        } else {
                                                            str2 = new String("Error in peekHits fetching hit url: ");
                                                        }
                                                        zzdh.zzc(str2);
                                                        arrayList2 = new ArrayList();
                                                        z = false;
                                                        for (zzca zzcaVar : arrayList) {
                                                            if (!TextUtils.isEmpty(zzcaVar.zzc())) {
                                                                if (z) {
                                                                    break;
                                                                } else {
                                                                    z = true;
                                                                }
                                                            }
                                                            arrayList2.add(zzcaVar);
                                                        }
                                                        if (cursorQuery != null) {
                                                            cursorQuery.close();
                                                        }
                                                    }
                                                } catch (Throwable th) {
                                                    th = th;
                                                    if (cursorQuery != null) {
                                                        cursorQuery.close();
                                                    }
                                                    throw th;
                                                }
                                            } catch (SQLiteException e2) {
                                                e = e2;
                                                cursorQuery = cursor3;
                                                strValueOf2 = String.valueOf(e.getMessage());
                                                if (strValueOf2.length() != 0) {
                                                    str2 = "Error in peekHits fetching hit url: ".concat(strValueOf2);
                                                } else {
                                                    str2 = new String("Error in peekHits fetching hit url: ");
                                                }
                                                zzdh.zzc(str2);
                                                arrayList2 = new ArrayList();
                                                z = false;
                                                while (r0.hasNext()) {
                                                    if (!TextUtils.isEmpty(zzcaVar.zzc())) {
                                                        if (z) {
                                                            break;
                                                            break;
                                                        }
                                                        z = true;
                                                    }
                                                    arrayList2.add(zzcaVar);
                                                }
                                                if (cursorQuery != null) {
                                                    cursorQuery.close();
                                                }
                                                if (arrayList2.isEmpty()) {
                                                    zzdh.zzb.zzd("...nothing to dispatch");
                                                    this.zzi.zza(r15);
                                                    return;
                                                }
                                                this.zzc.zza(arrayList2);
                                                sQLiteDatabaseZzk = zzk("Error opening database for getNumStoredHits.");
                                                if (sQLiteDatabaseZzk == null) {
                                                    return;
                                                }
                                                try {
                                                    String[] strArr = new String[2];
                                                    strArr[i] = "hit_id";
                                                    strArr[r15] = "hit_first_send_time";
                                                    cursorQuery2 = sQLiteDatabaseZzk.query("gtm_hits", strArr, "hit_first_send_time=0", null, null, null, null);
                                                    try {
                                                        try {
                                                            count = cursorQuery2.getCount();
                                                            if (cursorQuery2 != null) {
                                                                cursorQuery2.close();
                                                            }
                                                        } catch (SQLiteException unused) {
                                                            zzdh.zzc("Error getting num untried hits");
                                                            if (cursorQuery2 != null) {
                                                                cursorQuery2.close();
                                                                return;
                                                            }
                                                            count = i;
                                                        }
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                                        if (cursorQuery2 != null) {
                                                            cursorQuery2.close();
                                                        }
                                                        throw th;
                                                    }
                                                } catch (SQLiteException unused2) {
                                                    cursorQuery2 = null;
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    cursorQuery2 = null;
                                                    if (cursorQuery2 != null) {
                                                        cursorQuery2.close();
                                                    }
                                                    throw th;
                                                }
                                                if (count > 0) {
                                                    zzff.zzg().zza();
                                                }
                                            } catch (Throwable th4) {
                                                th = th4;
                                                cursorQuery = cursor3;
                                                if (cursorQuery != null) {
                                                    cursorQuery.close();
                                                }
                                                throw th;
                                            }
                                        } catch (SQLiteException e3) {
                                            e = e3;
                                            arrayList = arrayList3;
                                            cursor3 = cursorQuery3;
                                        } catch (Throwable th5) {
                                            th = th5;
                                            cursor3 = cursorQuery3;
                                        }
                                    } catch (SQLiteException e4) {
                                        e = e4;
                                        arrayList2 = arrayList3;
                                        cursor2 = cursorQuery3;
                                        r15 = r15;
                                        try {
                                            strValueOf = String.valueOf(e.getMessage());
                                            if (strValueOf.length() != 0) {
                                                str = "Error in peekHits fetching hitIds: ".concat(strValueOf);
                                            } else {
                                                str = new String("Error in peekHits fetching hitIds: ");
                                            }
                                            zzdh.zzc(str);
                                            if (cursor2 != null) {
                                                cursor2.close();
                                            }
                                        } catch (Throwable th6) {
                                            th = th6;
                                            cursor = cursor2;
                                            if (cursor != null) {
                                                cursor.close();
                                            }
                                            throw th;
                                        }
                                    }
                                } catch (SQLiteException e5) {
                                    e = e5;
                                }
                            } catch (Throwable th7) {
                                th = th7;
                                cursor = cursorQuery3;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteException e6) {
                            e = e6;
                            cursor2 = null;
                            r15 = r15;
                            strValueOf = String.valueOf(e.getMessage());
                            if (strValueOf.length() != 0) {
                                str = "Error in peekHits fetching hitIds: ".concat(strValueOf);
                            } else {
                                str = new String("Error in peekHits fetching hitIds: ");
                            }
                            zzdh.zzc(str);
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            if (arrayList2.isEmpty()) {
                                zzdh.zzb.zzd("...nothing to dispatch");
                                this.zzi.zza(r15);
                                return;
                            }
                            this.zzc.zza(arrayList2);
                            sQLiteDatabaseZzk = zzk("Error opening database for getNumStoredHits.");
                            if (sQLiteDatabaseZzk == null) {
                                return;
                            }
                            String[] strArr2 = new String[2];
                            strArr2[i] = "hit_id";
                            strArr2[r15] = "hit_first_send_time";
                            cursorQuery2 = sQLiteDatabaseZzk.query("gtm_hits", strArr2, "hit_first_send_time=0", null, null, null, null);
                            count = cursorQuery2.getCount();
                            if (cursorQuery2 != null) {
                                cursorQuery2.close();
                            }
                            if (count > 0) {
                                zzff.zzg().zza();
                            }
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        cursor = null;
                    }
                } catch (SQLiteException e7) {
                    e = e7;
                    r15 = 1;
                    i = 0;
                }
            }
            if (arrayList2.isEmpty()) {
                zzdh.zzb.zzd("...nothing to dispatch");
                this.zzi.zza(r15);
                return;
            }
            this.zzc.zza(arrayList2);
            sQLiteDatabaseZzk = zzk("Error opening database for getNumStoredHits.");
            if (sQLiteDatabaseZzk == null) {
                return;
            }
            String[] strArr3 = new String[2];
            strArr3[i] = "hit_id";
            strArr3[r15] = "hit_first_send_time";
            cursorQuery2 = sQLiteDatabaseZzk.query("gtm_hits", strArr3, "hit_first_send_time=0", null, null, null, null);
            count = cursorQuery2.getCount();
            if (cursorQuery2 != null) {
                cursorQuery2.close();
            }
            if (count > 0) {
                zzff.zzg().zza();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00be A[PHI: r2
      0x00be: PHI (r2v9 android.database.Cursor) = (r2v8 android.database.Cursor), (r2v10 android.database.Cursor) binds: [B:35:0x00bc, B:22:0x0096] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:41:0x00f2  */
    @Override // com.google.android.gms.tagmanager.zzcd
    public final void zzb(long j, String str) throws Throwable {
        Cursor cursorQuery;
        long jCurrentTimeMillis = this.zzg.currentTimeMillis();
        if (jCurrentTimeMillis > this.zzf + DateUtils.MILLIS_PER_DAY) {
            this.zzf = jCurrentTimeMillis;
            SQLiteDatabase sQLiteDatabaseZzk = zzk("Error opening database for deleteStaleHits.");
            if (sQLiteDatabaseZzk != null) {
                sQLiteDatabaseZzk.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.zzg.currentTimeMillis() - 2592000000L)});
                this.zzi.zza(zzc() == 0);
            }
        }
        int iZzc = (zzc() - this.zzh) + 1;
        Cursor cursor = null;
        if (iZzc > 0) {
            ArrayList arrayList = new ArrayList();
            SQLiteDatabase sQLiteDatabaseZzk2 = zzk("Error opening database for peekHitIds.");
            if (sQLiteDatabaseZzk2 != null) {
                try {
                    cursorQuery = sQLiteDatabaseZzk2.query("gtm_hits", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(iZzc));
                    try {
                        try {
                            if (cursorQuery.moveToFirst()) {
                                do {
                                    arrayList.add(String.valueOf(cursorQuery.getLong(0)));
                                } while (cursorQuery.moveToNext());
                            }
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                        } catch (SQLiteException e) {
                            e = e;
                            String strValueOf = String.valueOf(e.getMessage());
                            zzdh.zzc(strValueOf.length() != 0 ? "Error in peekHits fetching hitIds: ".concat(strValueOf) : new String("Error in peekHits fetching hitIds: "));
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                    cursorQuery = null;
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            int size = arrayList.size();
            StringBuilder sb = new StringBuilder(51);
            sb.append("Store full, deleting ");
            sb.append(size);
            sb.append(" hits to make room.");
            zzdh.zzb.zzd(sb.toString());
            zzj((String[]) arrayList.toArray(new String[0]));
        }
        SQLiteDatabase sQLiteDatabaseZzk3 = zzk("Error opening database for putHit");
        if (sQLiteDatabaseZzk3 == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_time", Long.valueOf(j));
        contentValues.put("hit_url", str);
        contentValues.put("hit_first_send_time", (Integer) 0);
        try {
            sQLiteDatabaseZzk3.insert("gtm_hits", null, contentValues);
            this.zzi.zza(false);
        } catch (SQLiteException unused) {
            zzdh.zzc("Error storing hit");
        }
    }

    final int zzc() {
        SQLiteDatabase sQLiteDatabaseZzk = zzk("Error opening database for getNumStoredHits.");
        int i = 0;
        if (sQLiteDatabaseZzk == null) {
            return 0;
        }
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = sQLiteDatabaseZzk.rawQuery("SELECT COUNT(*) from gtm_hits", null);
                i = cursorRawQuery.moveToFirst() ? (int) cursorRawQuery.getLong(0) : 0;
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                    return i;
                }
            } catch (SQLiteException unused) {
                zzdh.zzc("Error getting numStoredHits");
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                    return 0;
                }
            }
            return i;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    final void zzj(String[] strArr) {
        int length;
        SQLiteDatabase sQLiteDatabaseZzk;
        if (strArr == null || (length = strArr.length) == 0 || (sQLiteDatabaseZzk = zzk("Error opening database for deleteHits.")) == null) {
            return;
        }
        try {
            sQLiteDatabaseZzk.delete("gtm_hits", String.format("HIT_ID in (%s)", TextUtils.join(",", Collections.nCopies(length, TypeDescription.Generic.OfWildcardType.SYMBOL))), strArr);
            this.zzi.zza(zzc() == 0);
        } catch (SQLiteException unused) {
            zzdh.zzc("Error deleting hits");
        }
    }
}
