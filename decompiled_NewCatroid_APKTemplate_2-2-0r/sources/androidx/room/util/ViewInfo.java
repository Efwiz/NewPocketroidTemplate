package androidx.room.util;

import android.database.Cursor;
import androidx.sqlite.db.SupportSQLiteDatabase;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes.dex */
public final class ViewInfo {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final String f48name;
    public final String sql;

    public ViewInfo(String name2, String sql) {
        this.f48name = name2;
        this.sql = sql;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewInfo)) {
            return false;
        }
        ViewInfo viewInfo = (ViewInfo) o;
        String str = this.f48name;
        if (str == null ? viewInfo.f48name == null : str.equals(viewInfo.f48name)) {
            String str2 = this.sql;
            if (str2 != null) {
                if (str2.equals(viewInfo.sql)) {
                    return true;
                }
            } else if (viewInfo.sql == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String str = this.f48name;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.sql;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "ViewInfo{name='" + this.f48name + "', sql='" + this.sql + "'}";
    }

    public static ViewInfo read(SupportSQLiteDatabase database, String viewName) {
        Cursor cursorQuery = database.query("SELECT name, sql FROM sqlite_master WHERE type = 'view' AND name = '" + viewName + FormatHelper.QUOTE);
        try {
            if (cursorQuery.moveToFirst()) {
                return new ViewInfo(cursorQuery.getString(0), cursorQuery.getString(1));
            }
            return new ViewInfo(viewName, null);
        } finally {
            cursorQuery.close();
        }
    }
}
