package io.github.rosemoe.sora.widget.layout;

import android.util.SparseArray;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.ContentListener;
import io.github.rosemoe.sora.text.LineRemoveListener;

/* JADX INFO: loaded from: classes11.dex */
public interface Layout extends LineRemoveListener, ContentListener {
    void destroyLayout();

    float[] getCharLayoutOffset(int i, int i2, float[] fArr);

    long getCharPositionForLayoutOffset(float f, float f2);

    long getDownPosition(int i, int i2);

    int getLayoutHeight();

    int getLayoutWidth();

    int getLineNumberForRow(int i);

    Row getRowAt(int i);

    int getRowCount();

    int getRowCountForLine(int i);

    int getRowIndexForPosition(int i);

    long getUpPosition(int i, int i2);

    RowIterator obtainRowIterator(int i, SparseArray<ContentLine> sparseArray);

    default RowIterator obtainRowIterator(int i) {
        return obtainRowIterator(i, null);
    }

    default float[] getCharLayoutOffset(int i, int i2) {
        return getCharLayoutOffset(i, i2, new float[2]);
    }
}
