package org.catrobat.catroid.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/BlockClipboard.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BlockClipboard {
    private static final int MAX_HISTORY_SIZE = 10;
    private static BlockClipboard instance;
    private final List<List<Brick>> history = new ArrayList();

    private BlockClipboard() {
    }

    public static synchronized BlockClipboard getInstance() {
        if (instance == null) {
            instance = new BlockClipboard();
        }
        return instance;
    }

    public void copy(List<Brick> bricks) {
        if (bricks == null || bricks.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(it.next().mo4893clone());
            } catch (CloneNotSupportedException unused) {
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        addToHistory(arrayList);
    }

    private void addToHistory(List<Brick> bricks) {
        this.history.remove(bricks);
        this.history.add(0, bricks);
        if (this.history.size() > 10) {
            List<List<Brick>> list = this.history;
            list.remove(list.size() - 1);
        }
    }

    public List<List<Brick>> getHistory() {
        return this.history;
    }

    public List<Brick> getLatest() {
        if (this.history.isEmpty()) {
            return null;
        }
        return this.history.get(0);
    }
}
