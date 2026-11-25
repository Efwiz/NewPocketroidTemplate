package org.catrobat.catroid.scratchconverter.protocol.message;

import android.util.SparseArray;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/Message.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class Message {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/Message$CategoryType.class
      classes14.dex
     */
    public enum CategoryType {
        BASE(0),
        JOB(1);

        private static SparseArray<CategoryType> categoryTypes = new SparseArray<>();
        private int categoryID;

        static {
            for (CategoryType categoryType : values()) {
                categoryTypes.put(categoryType.categoryID, categoryType);
            }
        }

        CategoryType(final int categoryID) {
            this.categoryID = categoryID;
        }

        public static CategoryType valueOf(int categoryID) {
            return categoryTypes.get(categoryID);
        }

        public int getCategoryID() {
            return this.categoryID;
        }
    }
}
