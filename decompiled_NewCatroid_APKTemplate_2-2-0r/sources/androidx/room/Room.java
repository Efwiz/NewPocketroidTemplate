package androidx.room;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class Room {
    private static final String CURSOR_CONV_SUFFIX = "_CursorConverter";
    static final String LOG_TAG = "ROOM";
    public static final String MASTER_TABLE_NAME = "room_master_table";

    public static <T extends RoomDatabase> RoomDatabase.Builder<T> databaseBuilder(Context context, Class<T> klass, String name2) {
        if (name2 == null || name2.trim().length() == 0) {
            throw new IllegalArgumentException("Cannot build a database with null or empty name. If you are trying to create an in memory database, use Room.inMemoryDatabaseBuilder");
        }
        return new RoomDatabase.Builder<>(context, klass, name2);
    }

    public static <T extends RoomDatabase> RoomDatabase.Builder<T> inMemoryDatabaseBuilder(Context context, Class<T> klass) {
        return new RoomDatabase.Builder<>(context, klass, null);
    }

    static <T, C> T getGeneratedImplementation(Class<C> cls, String str) {
        String name2 = cls.getPackage().getName();
        String canonicalName = cls.getCanonicalName();
        if (!name2.isEmpty()) {
            canonicalName = canonicalName.substring(name2.length() + 1);
        }
        String str2 = canonicalName.replace('.', '_') + str;
        try {
            return (T) Class.forName(name2.isEmpty() ? str2 : name2 + "." + str2, true, cls.getClassLoader()).newInstance();
        } catch (ClassNotFoundException unused) {
            throw new RuntimeException("cannot find implementation for " + cls.getCanonicalName() + ". " + str2 + " does not exist");
        } catch (IllegalAccessException unused2) {
            throw new RuntimeException("Cannot access the constructor" + cls.getCanonicalName());
        } catch (InstantiationException unused3) {
            throw new RuntimeException("Failed to create an instance of " + cls.getCanonicalName());
        }
    }

    @Deprecated
    public Room() {
    }
}
