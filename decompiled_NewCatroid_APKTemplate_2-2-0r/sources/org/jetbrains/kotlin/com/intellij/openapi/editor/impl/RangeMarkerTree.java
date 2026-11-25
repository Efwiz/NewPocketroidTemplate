package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.PrioritizedDocumentListener;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.RangeMarkerEx;
import org.jetbrains.kotlin.com.intellij.util.DocumentEventUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
class RangeMarkerTree<T extends RangeMarkerEx> extends IntervalTreeImpl<T> implements PrioritizedDocumentListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "i1";
                break;
            case 2:
                objArr[0] = "i2";
                break;
            case 3:
            default:
                objArr[0] = "document";
                break;
            case 4:
                objArr[0] = "interval";
                break;
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/RangeMarkerTree";
                break;
            case 6:
            case 14:
                objArr[0] = "node";
                break;
            case 7:
            case 8:
            case 9:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 10:
            case 11:
            case 12:
            case 16:
                objArr[0] = "e";
                break;
            case 13:
            case 15:
            case 17:
                objArr[0] = "affected";
                break;
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/RangeMarkerTree";
        } else {
            objArr[1] = "addInterval";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "compareEqualStartIntervals";
                break;
            case 3:
                objArr[2] = "dispose";
                break;
            case 4:
                objArr[2] = "addInterval";
                break;
            case 5:
                break;
            case 6:
                objArr[2] = "errMsg";
                break;
            case 7:
                objArr[2] = "createNewNode";
                break;
            case 8:
                objArr[2] = "lookupNode";
                break;
            case 9:
                objArr[2] = "setNode";
                break;
            case 10:
                objArr[2] = "documentChanged";
                break;
            case 11:
                objArr[2] = "updateMarkersOnChange";
                break;
            case 12:
            case 13:
                objArr[2] = "updateAffectedNodes";
                break;
            case 14:
                objArr[2] = "getNodeMarker";
                break;
            case 15:
                objArr[2] = "collectAffectedMarkersAndShiftSubtrees";
                break;
            case 16:
                objArr[2] = "reTargetMarkersOnChange";
                break;
            case 17:
                objArr[2] = "collectNodesToRetarget";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.PrioritizedDocumentListener
    public int getPriority() {
        return 40;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.IntervalTreeImpl
    protected /* bridge */ /* synthetic */ void setNode(Object obj, IntervalTreeImpl.IntervalNode intervalNode) {
        setNode((RangeMarkerEx) obj, (IntervalTreeImpl.IntervalNode<RangeMarkerEx>) intervalNode);
    }

    RangeMarkerTree(Document document) {
        if (document == null) {
            $$$reportNull$$$0(0);
        }
        document.addDocumentListener(this);
    }

    RangeMarkerTree() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.IntervalTreeImpl
    protected int compareEqualStartIntervals(IntervalTreeImpl.IntervalNode<T> intervalNode, IntervalTreeImpl.IntervalNode<T> intervalNode2) {
        if (intervalNode == null) {
            $$$reportNull$$$0(1);
        }
        if (intervalNode2 == null) {
            $$$reportNull$$$0(2);
        }
        RMNode rMNode = (RMNode) intervalNode;
        RMNode rMNode2 = (RMNode) intervalNode2;
        boolean zIsGreedyToLeft = rMNode.isGreedyToLeft();
        if (zIsGreedyToLeft != rMNode2.isGreedyToLeft()) {
            return zIsGreedyToLeft ? -1 : 1;
        }
        int iIntervalEnd = (rMNode.intervalEnd() - rMNode.intervalStart()) - (rMNode2.intervalEnd() - rMNode2.intervalStart());
        if (iIntervalEnd != 0) {
            return iIntervalEnd;
        }
        boolean zIsGreedyToRight = rMNode.isGreedyToRight();
        if (zIsGreedyToRight != rMNode2.isGreedyToRight()) {
            return zIsGreedyToRight ? -1 : 1;
        }
        boolean zIsStickingToRight = rMNode.isStickingToRight();
        if (zIsStickingToRight != rMNode2.isStickingToRight()) {
            return zIsStickingToRight ? -1 : 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.IntervalTreeImpl
    public RMNode<T> lookupNode(T t) {
        if (t == null) {
            $$$reportNull$$$0(8);
        }
        return (RMNode<T>) ((RangeMarkerImpl) t).myNode;
    }

    protected void setNode(T t, IntervalTreeImpl.IntervalNode<T> intervalNode) {
        if (t == null) {
            $$$reportNull$$$0(9);
        }
        ((RangeMarkerImpl) t).myNode = (RMNode) intervalNode;
    }

    static class RMNode<T extends RangeMarkerEx> extends IntervalTreeImpl.IntervalNode<T> {
        void onRemoved() {
        }

        boolean isGreedyToLeft() {
            return isFlagSet((byte) 8);
        }

        boolean isGreedyToRight() {
            return isFlagSet((byte) 16);
        }

        boolean isStickingToRight() {
            return isFlagSet((byte) 32);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.IntervalTreeImpl.IntervalNode
        public String toString() {
            return (isGreedyToLeft() ? "[" : "(") + intervalStart() + "," + intervalEnd() + (isGreedyToRight() ? "]" : Constant.AFTER_QUTO);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener
    public void documentChanged(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(10);
        }
        this.l.writeLock().lock();
        try {
            if (size() != 0) {
                updateMarkersOnChange(documentEvent);
                if (DocumentEventUtil.isMoveInsertion(documentEvent)) {
                    reTargetMarkersOnChange(documentEvent);
                }
                getRoot();
            }
        } finally {
            this.l.writeLock().unlock();
        }
    }

    private void updateMarkersOnChange(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(11);
        }
        checkMax(true);
        incModCount();
        SmartList smartList = new SmartList();
        int offset = documentEvent.getOffset();
        int oldLength = documentEvent.getOldLength();
        collectAffectedMarkersAndShiftSubtrees(getRoot(), offset, offset + oldLength, documentEvent.getNewLength() - oldLength, smartList);
        checkMax(false);
        if (smartList.isEmpty()) {
            return;
        }
        updateAffectedNodes(documentEvent, 0, smartList);
    }

    private void updateAffectedNodes(DocumentEvent documentEvent, int i, List<? extends IntervalTreeImpl.IntervalNode<T>> list) {
        if (documentEvent == null) {
            $$$reportNull$$$0(12);
        }
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            IntervalTreeImpl.IntervalNode<T> intervalNode = list.get(size);
            intervalNode.intervalStart();
            intervalNode.intervalEnd();
            removeNode(intervalNode);
            checkMax(false);
            intervalNode.setParent(null);
            intervalNode.setLeft(null);
            intervalNode.setRight(null);
            intervalNode.setValid(true);
            if (i == 0) {
                intervalNode.clearDelta();
            } else {
                intervalNode.changeDelta(i);
                pushDelta(intervalNode);
            }
        }
        checkMax(true);
        for (IntervalTreeImpl.IntervalNode<T> intervalNode2 : list) {
            RangeMarkerImpl nodeMarker = getNodeMarker(intervalNode2);
            if (nodeMarker != null) {
                if (i == 0) {
                    nodeMarker.documentChanged(documentEvent);
                } else {
                    nodeMarker.onReTarget(documentEvent);
                }
                if (nodeMarker.isValid()) {
                    findOrInsertWithIntervals(intervalNode2);
                } else {
                    intervalNode2.setValid(false);
                    ((RMNode) intervalNode2).onRemoved();
                }
            }
        }
        checkMax(true);
    }

    private static <T extends RangeMarkerEx> RangeMarkerImpl getNodeMarker(IntervalTreeImpl.IntervalNode<T> intervalNode) {
        if (intervalNode == null) {
            $$$reportNull$$$0(14);
        }
        List<Supplier<T>> list = intervalNode.intervals;
        for (int size = list.size() - 1; size >= 0; size--) {
            RangeMarkerImpl rangeMarkerImpl = (RangeMarkerImpl) list.get(size).get();
            if (rangeMarkerImpl != null) {
                if (rangeMarkerImpl.isValid()) {
                    return rangeMarkerImpl;
                }
                intervalNode.removeIntervalInternal(size);
            }
        }
        return null;
    }

    private void findOrInsertWithIntervals(IntervalTreeImpl.IntervalNode<T> intervalNode) {
        IntervalTreeImpl.IntervalNode<T> intervalNodeFindOrInsert = findOrInsert(intervalNode);
        if (intervalNodeFindOrInsert != intervalNode) {
            intervalNodeFindOrInsert.addIntervalsFrom(intervalNode);
        }
    }

    void collectAffectedMarkersAndShiftSubtrees(IntervalTreeImpl.IntervalNode<T> intervalNode, int i, int i2, int i3, List<? super IntervalTreeImpl.IntervalNode<T>> list) {
        if (list == null) {
            $$$reportNull$$$0(15);
        }
        if (intervalNode == null) {
            return;
        }
        pushDelta(intervalNode);
        int i4 = intervalNode.maxEnd;
        boolean zHasAliveKey = intervalNode.hasAliveKey(false);
        if (!zHasAliveKey) {
            list.add(intervalNode);
        }
        if (i > i4) {
            return;
        }
        if (i2 < intervalNode.intervalStart()) {
            intervalNode.changeDelta(i3);
            IntervalTreeImpl.IntervalNode<T> left = intervalNode.getLeft();
            if (left != null) {
                left.changeDelta(-i3);
            }
            pushDelta(intervalNode);
            collectAffectedMarkersAndShiftSubtrees(left, i, i2, i3, list);
            correctMax(intervalNode, 0);
            return;
        }
        if (i <= intervalNode.intervalEnd()) {
            if (zHasAliveKey) {
                list.add(intervalNode);
            }
            intervalNode.setValid(false);
        }
        collectAffectedMarkersAndShiftSubtrees(intervalNode.getLeft(), i, i2, i3, list);
        collectAffectedMarkersAndShiftSubtrees(intervalNode.getRight(), i, i2, i3, list);
        correctMax(intervalNode, 0);
    }

    private void reTargetMarkersOnChange(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(16);
        }
        checkMax(true);
        SmartList smartList = new SmartList();
        int moveOffset = documentEvent.getMoveOffset();
        collectNodesToRetarget(getRoot(), moveOffset, documentEvent.getNewLength() + moveOffset, smartList);
        if (smartList.isEmpty()) {
            return;
        }
        updateAffectedNodes(documentEvent, documentEvent.getOffset() - documentEvent.getMoveOffset(), smartList);
    }

    private void collectNodesToRetarget(IntervalTreeImpl.IntervalNode<T> intervalNode, int i, int i2, List<? super IntervalTreeImpl.IntervalNode<T>> list) {
        if (list == null) {
            $$$reportNull$$$0(17);
        }
        if (intervalNode == null) {
            return;
        }
        pushDelta(intervalNode);
        if (i > intervalNode.maxEnd) {
            return;
        }
        collectNodesToRetarget(intervalNode.getLeft(), i, i2, list);
        if (i <= intervalNode.intervalStart() && intervalNode.intervalEnd() <= i2) {
            list.add(intervalNode);
        }
        if (i2 < intervalNode.intervalStart()) {
            return;
        }
        collectNodesToRetarget(intervalNode.getRight(), i, i2, list);
    }
}
