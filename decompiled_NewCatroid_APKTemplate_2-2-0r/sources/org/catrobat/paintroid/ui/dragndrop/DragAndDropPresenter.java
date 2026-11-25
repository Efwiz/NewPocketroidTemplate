package org.catrobat.paintroid.ui.dragndrop;

import kotlin.Metadata;

/* JADX INFO: compiled from: DragAndDropPresenter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005H&J\u0018\u0010\n\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005H&¨\u0006\u000b"}, d2 = {"Lorg/catrobat/paintroid/ui/dragndrop/DragAndDropPresenter;", "", "markMergeable", "", "position", "", "mergeWith", "mergeItems", "reorderItems", "swapWith", "swapItemsVisually", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface DragAndDropPresenter {
    void markMergeable(int position, int mergeWith);

    void mergeItems(int position, int mergeWith);

    void reorderItems(int position, int swapWith);

    int swapItemsVisually(int position, int swapWith);
}
