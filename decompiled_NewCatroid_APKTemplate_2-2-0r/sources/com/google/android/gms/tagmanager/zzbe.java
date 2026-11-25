package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.gtm.zzfz;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import net.bytebuddy.description.type.TypeDescription;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbe implements zzax {
    private static final String zza = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", "datalayer", SchemaSymbols.ATTVAL_ID, ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "expires");
    private final Executor zzb;
    private final Context zzc;
    private final zzbc zzd;
    private final Clock zze;

    public zzbe(Context context) {
        Clock defaultClock = DefaultClock.getInstance();
        ExecutorService executorServiceZza = zzfz.zza().zza(2);
        this.zzc = context;
        this.zze = defaultClock;
        this.zzb = executorServiceZza;
        this.zzd = new zzbc(this, context, "google_tagmanager.db");
    }

    static /* bridge */ /* synthetic */ List zzf(zzbe zzbeVar) {
        ObjectInputStream objectInputStream;
        try {
            zzbeVar.zzk(zzbeVar.zze.currentTimeMillis());
            SQLiteDatabase sQLiteDatabaseZzi = zzbeVar.zzi("Error opening database for loadSerialized.");
            ArrayList<zzbd> arrayList = new ArrayList();
            if (sQLiteDatabaseZzi != null) {
                Cursor cursorQuery = sQLiteDatabaseZzi.query("datalayer", new String[]{ASTExpr.DEFAULT_MAP_KEY_NAME, "value"}, null, null, null, null, SchemaSymbols.ATTVAL_ID, null);
                while (cursorQuery.moveToNext()) {
                    try {
                        arrayList.add(new zzbd(cursorQuery.getString(0), cursorQuery.getBlob(1)));
                    } catch (Throwable th) {
                        cursorQuery.close();
                        throw th;
                    }
                }
                cursorQuery.close();
            }
            ArrayList arrayList2 = new ArrayList();
            for (zzbd zzbdVar : arrayList) {
                String str = zzbdVar.zza;
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(zzbdVar.zzb);
                ObjectInputStream objectInputStream2 = null;
                object = null;
                object = null;
                object = null;
                Object object = null;
                try {
                    objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    try {
                        object = objectInputStream.readObject();
                        try {
                            objectInputStream.close();
                        } catch (IOException unused) {
                        }
                    } catch (IOException unused2) {
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                    } catch (ClassNotFoundException unused3) {
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        objectInputStream2 = objectInputStream;
                        if (objectInputStream2 != null) {
                            try {
                                objectInputStream2.close();
                            } catch (IOException unused4) {
                                throw th;
                            }
                        }
                        byteArrayInputStream.close();
                        throw th;
                    }
                } catch (IOException unused5) {
                    objectInputStream = null;
                } catch (ClassNotFoundException unused6) {
                    objectInputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                }
                byteArrayInputStream.close();
                arrayList2.add(new zzau(str, object));
            }
            zzbeVar.zzj();
            return arrayList2;
        } catch (Throwable th4) {
            zzbeVar.zzj();
            throw th4;
        }
    }

    static /* bridge */ /* synthetic */ void zzg(zzbe zzbeVar, String str) {
        SQLiteDatabase sQLiteDatabaseZzi = zzbeVar.zzi("Error opening database for clearKeysWithPrefix.");
        if (sQLiteDatabaseZzi == null) {
            return;
        }
        try {
            int iDelete = sQLiteDatabaseZzi.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, String.valueOf(str).concat(".%")});
            StringBuilder sb = new StringBuilder(25);
            sb.append("Cleared ");
            sb.append(iDelete);
            sb.append(" items");
            zzdh.zzb.zzd(sb.toString());
        } catch (SQLiteException e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 44 + String.valueOf(strValueOf).length());
            sb2.append("Error deleting entries with key prefix: ");
            sb2.append(str);
            sb2.append(" (");
            sb2.append(strValueOf);
            sb2.append(").");
            zzdh.zzc(sb2.toString());
        } finally {
            zzbeVar.zzj();
        }
    }

    private final SQLiteDatabase zzi(String str) {
        try {
            return this.zzd.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzdh.zzc(str);
            return null;
        }
    }

    private final void zzj() {
        try {
            this.zzd.close();
        } catch (SQLiteException unused) {
        }
    }

    private final void zzk(long j) {
        SQLiteDatabase sQLiteDatabaseZzi = zzi("Error opening database for deleteOlderThan.");
        if (sQLiteDatabaseZzi == null) {
            return;
        }
        try {
            int iDelete = sQLiteDatabaseZzi.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)});
            StringBuilder sb = new StringBuilder(33);
            sb.append("Deleted ");
            sb.append(iDelete);
            sb.append(" expired items");
            zzdh.zzb.zzd(sb.toString());
        } catch (SQLiteException unused) {
            zzdh.zzc("Error deleting old entries.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:25:0x0049 A[Catch: all -> 0x0181, TRY_LEAVE, TryCatch #1 {all -> 0x0181, blocks: (B:4:0x0003, B:23:0x0044, B:25:0x0049, B:35:0x0091, B:68:0x0133, B:69:0x0136, B:50:0x00bc, B:52:0x00e7, B:55:0x00eb, B:57:0x00f3, B:58:0x010b, B:60:0x0111, B:62:0x0121, B:64:0x012b, B:63:0x0126, B:70:0x0137, B:73:0x0142, B:74:0x0146, B:76:0x014c, B:15:0x0031, B:22:0x0040, B:84:0x017d, B:85:0x0180), top: B:93:0x0003, outer: #5, inners: #9 }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0057  */
    /* JADX WARN: Code duplicated, block: B:32:0x007e A[Catch: SQLiteException -> 0x0095, all -> 0x012f, LOOP:0: B:32:0x007e->B:108:?, LOOP_START, TRY_LEAVE, TryCatch #6 {SQLiteException -> 0x0095, blocks: (B:30:0x0078, B:32:0x007e), top: B:97:0x0078 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0091 A[Catch: all -> 0x0181, PHI: r7
      0x0091: PHI (r7v18 android.database.Cursor) = (r7v17 android.database.Cursor), (r7v19 android.database.Cursor) binds: [B:48:0x00b9, B:34:0x008f] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0181, blocks: (B:4:0x0003, B:23:0x0044, B:25:0x0049, B:35:0x0091, B:68:0x0133, B:69:0x0136, B:50:0x00bc, B:52:0x00e7, B:55:0x00eb, B:57:0x00f3, B:58:0x010b, B:60:0x0111, B:62:0x0121, B:64:0x012b, B:63:0x0126, B:70:0x0137, B:73:0x0142, B:74:0x0146, B:76:0x014c, B:15:0x0031, B:22:0x0040, B:84:0x017d, B:85:0x0180), top: B:93:0x0003, outer: #5, inners: #9 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x00e7 A[Catch: all -> 0x0181, TryCatch #1 {all -> 0x0181, blocks: (B:4:0x0003, B:23:0x0044, B:25:0x0049, B:35:0x0091, B:68:0x0133, B:69:0x0136, B:50:0x00bc, B:52:0x00e7, B:55:0x00eb, B:57:0x00f3, B:58:0x010b, B:60:0x0111, B:62:0x0121, B:64:0x012b, B:63:0x0126, B:70:0x0137, B:73:0x0142, B:74:0x0146, B:76:0x014c, B:15:0x0031, B:22:0x0040, B:84:0x017d, B:85:0x0180), top: B:93:0x0003, outer: #5, inners: #9 }] */
    /* JADX WARN: Code duplicated, block: B:68:0x0133 A[Catch: all -> 0x0181, TryCatch #1 {all -> 0x0181, blocks: (B:4:0x0003, B:23:0x0044, B:25:0x0049, B:35:0x0091, B:68:0x0133, B:69:0x0136, B:50:0x00bc, B:52:0x00e7, B:55:0x00eb, B:57:0x00f3, B:58:0x010b, B:60:0x0111, B:62:0x0121, B:64:0x012b, B:63:0x0126, B:70:0x0137, B:73:0x0142, B:74:0x0146, B:76:0x014c, B:15:0x0031, B:22:0x0040, B:84:0x017d, B:85:0x0180), top: B:93:0x0003, outer: #5, inners: #9 }] */
    /* JADX WARN: Code duplicated, block: B:72:0x0141  */
    /* JADX WARN: Code duplicated, block: B:73:0x0142 A[Catch: all -> 0x0181, TryCatch #1 {all -> 0x0181, blocks: (B:4:0x0003, B:23:0x0044, B:25:0x0049, B:35:0x0091, B:68:0x0133, B:69:0x0136, B:50:0x00bc, B:52:0x00e7, B:55:0x00eb, B:57:0x00f3, B:58:0x010b, B:60:0x0111, B:62:0x0121, B:64:0x012b, B:63:0x0126, B:70:0x0137, B:73:0x0142, B:74:0x0146, B:76:0x014c, B:15:0x0031, B:22:0x0040, B:84:0x017d, B:85:0x0180), top: B:93:0x0003, outer: #5, inners: #9 }] */
    /* JADX WARN: Code duplicated, block: B:76:0x014c A[Catch: all -> 0x0181, LOOP:1: B:74:0x0146->B:76:0x014c, LOOP_END, TRY_LEAVE, TryCatch #1 {all -> 0x0181, blocks: (B:4:0x0003, B:23:0x0044, B:25:0x0049, B:35:0x0091, B:68:0x0133, B:69:0x0136, B:50:0x00bc, B:52:0x00e7, B:55:0x00eb, B:57:0x00f3, B:58:0x010b, B:60:0x0111, B:62:0x0121, B:64:0x012b, B:63:0x0126, B:70:0x0137, B:73:0x0142, B:74:0x0146, B:76:0x014c, B:15:0x0031, B:22:0x0040, B:84:0x017d, B:85:0x0180), top: B:93:0x0003, outer: #5, inners: #9 }] */
    /* JADX WARN: Code duplicated, block: B:84:0x017d A[Catch: all -> 0x0181, TRY_ENTER, TryCatch #1 {all -> 0x0181, blocks: (B:4:0x0003, B:23:0x0044, B:25:0x0049, B:35:0x0091, B:68:0x0133, B:69:0x0136, B:50:0x00bc, B:52:0x00e7, B:55:0x00eb, B:57:0x00f3, B:58:0x010b, B:60:0x0111, B:62:0x0121, B:64:0x012b, B:63:0x0126, B:70:0x0137, B:73:0x0142, B:74:0x0146, B:76:0x014c, B:15:0x0031, B:22:0x0040, B:84:0x017d, B:85:0x0180), top: B:93:0x0003, outer: #5, inners: #9 }] */
    public final synchronized void zzl(List<zzbd> list, long j) {
        Cursor cursorRawQuery;
        int i;
        int i2;
        long j2;
        SQLiteDatabase sQLiteDatabaseZzi;
        ArrayList arrayList;
        SQLiteDatabase sQLiteDatabaseZzi2;
        String[] strArr;
        int length;
        SQLiteDatabase sQLiteDatabaseZzi3;
        Cursor cursorQuery;
        try {
            long jCurrentTimeMillis = this.zze.currentTimeMillis();
            zzk(jCurrentTimeMillis);
            int size = list.size();
            SQLiteDatabase sQLiteDatabaseZzi4 = zzi("Error opening database for getNumStoredEntries.");
            Cursor cursor = null;
            if (sQLiteDatabaseZzi4 == null) {
                i = 0;
            } else {
                try {
                    cursorRawQuery = sQLiteDatabaseZzi4.rawQuery("SELECT COUNT(*) from datalayer", null);
                    try {
                        try {
                            i = cursorRawQuery.moveToFirst() ? (int) cursorRawQuery.getLong(0) : 0;
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                            }
                        } catch (SQLiteException unused) {
                            zzdh.zzc("Error getting numStoredEntries");
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                            }
                            i = 0;
                            i2 = (i - 2000) + size;
                            if (i2 > 0) {
                                arrayList = new ArrayList();
                                sQLiteDatabaseZzi2 = zzi("Error opening database for peekEntryIds.");
                                if (sQLiteDatabaseZzi2 != null) {
                                    try {
                                        cursorQuery = sQLiteDatabaseZzi2.query("datalayer", new String[]{SchemaSymbols.ATTVAL_ID}, null, null, null, null, String.format("%s ASC", SchemaSymbols.ATTVAL_ID), Integer.toString(i2));
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
                                                zzdh.zzc(strValueOf.length() != 0 ? "Error in peekEntries fetching entryIds: ".concat(strValueOf) : new String("Error in peekEntries fetching entryIds: "));
                                                if (cursorQuery != null) {
                                                }
                                                int size2 = arrayList.size();
                                                StringBuilder sb = new StringBuilder(64);
                                                sb.append("DataLayer store full, deleting ");
                                                sb.append(size2);
                                                sb.append(" entries to make room.");
                                                zzdh.zzb.zzb(sb.toString());
                                                strArr = (String[]) arrayList.toArray(new String[0]);
                                                if (strArr != null) {
                                                    try {
                                                        sQLiteDatabaseZzi3.delete("datalayer", String.format("%s in (%s)", SchemaSymbols.ATTVAL_ID, TextUtils.join(",", Collections.nCopies(length, TypeDescription.Generic.OfWildcardType.SYMBOL))), strArr);
                                                    } catch (SQLiteException unused2) {
                                                        String strValueOf2 = String.valueOf(Arrays.toString(strArr));
                                                        zzdh.zzc(strValueOf2.length() != 0 ? "Error deleting entries ".concat(strValueOf2) : new String("Error deleting entries "));
                                                    }
                                                }
                                                j2 = jCurrentTimeMillis + j;
                                                sQLiteDatabaseZzi = zzi("Error opening database for writeEntryToDatabase.");
                                                if (sQLiteDatabaseZzi != null) {
                                                    for (zzbd zzbdVar : list) {
                                                        ContentValues contentValues = new ContentValues();
                                                        contentValues.put("expires", Long.valueOf(j2));
                                                        contentValues.put(ASTExpr.DEFAULT_MAP_KEY_NAME, zzbdVar.zza);
                                                        contentValues.put("value", zzbdVar.zzb);
                                                        sQLiteDatabaseZzi.insert("datalayer", null, contentValues);
                                                    }
                                                }
                                                zzj();
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
                                int size3 = arrayList.size();
                                StringBuilder sb2 = new StringBuilder(64);
                                sb2.append("DataLayer store full, deleting ");
                                sb2.append(size3);
                                sb2.append(" entries to make room.");
                                zzdh.zzb.zzb(sb2.toString());
                                strArr = (String[]) arrayList.toArray(new String[0]);
                                if (strArr != null) {
                                    sQLiteDatabaseZzi3.delete("datalayer", String.format("%s in (%s)", SchemaSymbols.ATTVAL_ID, TextUtils.join(",", Collections.nCopies(length, TypeDescription.Generic.OfWildcardType.SYMBOL))), strArr);
                                }
                            }
                            j2 = jCurrentTimeMillis + j;
                            sQLiteDatabaseZzi = zzi("Error opening database for writeEntryToDatabase.");
                            if (sQLiteDatabaseZzi != null) {
                                while (r4.hasNext()) {
                                    ContentValues contentValues2 = new ContentValues();
                                    contentValues2.put("expires", Long.valueOf(j2));
                                    contentValues2.put(ASTExpr.DEFAULT_MAP_KEY_NAME, zzbdVar.zza);
                                    contentValues2.put("value", zzbdVar.zzb);
                                    sQLiteDatabaseZzi.insert("datalayer", null, contentValues2);
                                }
                            }
                            zzj();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        cursor = cursorRawQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                } catch (SQLiteException unused3) {
                    cursorRawQuery = null;
                } catch (Throwable th4) {
                    th = th4;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            i2 = (i - 2000) + size;
            if (i2 > 0) {
                arrayList = new ArrayList();
                sQLiteDatabaseZzi2 = zzi("Error opening database for peekEntryIds.");
                if (sQLiteDatabaseZzi2 != null) {
                    cursorQuery = sQLiteDatabaseZzi2.query("datalayer", new String[]{SchemaSymbols.ATTVAL_ID}, null, null, null, null, String.format("%s ASC", SchemaSymbols.ATTVAL_ID), Integer.toString(i2));
                    if (cursorQuery.moveToFirst()) {
                        do {
                            arrayList.add(String.valueOf(cursorQuery.getLong(0)));
                        } while (cursorQuery.moveToNext());
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
                int size4 = arrayList.size();
                StringBuilder sb3 = new StringBuilder(64);
                sb3.append("DataLayer store full, deleting ");
                sb3.append(size4);
                sb3.append(" entries to make room.");
                zzdh.zzb.zzb(sb3.toString());
                strArr = (String[]) arrayList.toArray(new String[0]);
                if (strArr != null && (length = strArr.length) != 0 && (sQLiteDatabaseZzi3 = zzi("Error opening database for deleteEntries.")) != null) {
                    sQLiteDatabaseZzi3.delete("datalayer", String.format("%s in (%s)", SchemaSymbols.ATTVAL_ID, TextUtils.join(",", Collections.nCopies(length, TypeDescription.Generic.OfWildcardType.SYMBOL))), strArr);
                }
            }
            j2 = jCurrentTimeMillis + j;
            sQLiteDatabaseZzi = zzi("Error opening database for writeEntryToDatabase.");
            if (sQLiteDatabaseZzi != null) {
                while (r4.hasNext()) {
                    ContentValues contentValues3 = new ContentValues();
                    contentValues3.put("expires", Long.valueOf(j2));
                    contentValues3.put(ASTExpr.DEFAULT_MAP_KEY_NAME, zzbdVar.zza);
                    contentValues3.put("value", zzbdVar.zzb);
                    sQLiteDatabaseZzi.insert("datalayer", null, contentValues3);
                }
            }
            zzj();
        } catch (Throwable th5) {
            zzj();
            throw th5;
        }
    }

    @Override // com.google.android.gms.tagmanager.zzax
    public final void zza(String str) {
        this.zzb.execute(new zzbb(this, str));
    }

    @Override // com.google.android.gms.tagmanager.zzax
    public final void zzb(zzaw zzawVar) {
        this.zzb.execute(new zzba(this, zzawVar));
    }

    @Override // com.google.android.gms.tagmanager.zzax
    public final void zzc(List<zzau> list, long j) throws Throwable {
        ObjectOutputStream objectOutputStream;
        ArrayList arrayList = new ArrayList();
        for (zzau zzauVar : list) {
            String str = zzauVar.zza;
            Object obj = zzauVar.zzb;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream2 = null;
            byteArray = null;
            byte[] byteArray = null;
            try {
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                try {
                    objectOutputStream.writeObject(obj);
                    byteArray = byteArrayOutputStream.toByteArray();
                } catch (IOException unused) {
                    if (objectOutputStream != null) {
                    }
                    byteArrayOutputStream.close();
                    arrayList.add(new zzbd(str, byteArray));
                } catch (Throwable th) {
                    th = th;
                    objectOutputStream2 = objectOutputStream;
                    if (objectOutputStream2 != null) {
                        try {
                            objectOutputStream2.close();
                        } catch (IOException unused2) {
                            throw th;
                        }
                    }
                    byteArrayOutputStream.close();
                    throw th;
                }
            } catch (IOException unused3) {
                objectOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                objectOutputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException unused4) {
            }
            arrayList.add(new zzbd(str, byteArray));
        }
        this.zzb.execute(new zzaz(this, arrayList, j));
    }
}
