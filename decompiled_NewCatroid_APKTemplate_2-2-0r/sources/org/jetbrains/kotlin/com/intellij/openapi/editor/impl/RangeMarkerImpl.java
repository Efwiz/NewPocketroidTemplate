package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.RangeMarkerEx;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.event.DocumentEventImpl;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileTypeDecompilers;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.UnfairTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
public class RangeMarkerImpl extends UserDataHolderBase implements RangeMarkerEx {
    private static final Logger LOG = Logger.getInstance((Class<?>) RangeMarkerImpl.class);
    private static final StripedIDGenerator counter = new StripedIDGenerator();
    private final Object myDocumentOrFile;
    private final long myId;
    RangeMarkerTree.RMNode<RangeMarkerEx> myNode;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "virtualFile";
                break;
            case 2:
                objArr[0] = "documentOrFile";
                break;
            case 4:
                objArr[0] = "reason";
                break;
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/RangeMarkerImpl";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[0] = "e";
                break;
            case 14:
            case 15:
                objArr[0] = "file";
                break;
            default:
                objArr[0] = "document";
                break;
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/RangeMarkerImpl";
        } else {
            objArr[1] = "getDocument";
        }
        switch (i) {
            case 3:
                objArr[2] = "estimateDocumentLength";
                break;
            case 4:
                objArr[2] = "invalidate";
                break;
            case 5:
                break;
            case 6:
                objArr[2] = "documentChanged";
                break;
            case 7:
                objArr[2] = "changedUpdateImpl";
                break;
            case 8:
                objArr[2] = "doChangeUpdate";
                break;
            case 9:
                objArr[2] = "persistentHighlighterUpdate";
                break;
            case 10:
                objArr[2] = "translatedViaDiff";
                break;
            case 11:
                objArr[2] = "onReTarget";
                break;
            case 12:
                objArr[2] = "applyChange";
                break;
            case 13:
                objArr[2] = "processIfOnePoint";
                break;
            case 14:
                objArr[2] = "canHaveDocument";
                break;
            case 15:
                objArr[2] = "isBinaryWithoutDecompiler";
                break;
            case 16:
                objArr[2] = "reRegister";
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

    protected void onReTarget(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(11);
        }
    }

    protected void unregisterInTree() {
        if (isValid()) {
            IntervalTreeImpl<RangeMarkerEx> tree = this.myNode.getTree();
            tree.checkMax(true);
            DocumentEx cachedDocument = getCachedDocument();
            if (cachedDocument == null) {
                this.myNode = null;
            } else {
                cachedDocument.removeRangeMarker(this);
            }
            tree.checkMax(true);
        }
    }

    public long getId() {
        return this.myId;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.RangeMarker
    public void dispose() {
        unregisterInTree();
    }

    public int getStartOffset() {
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        if (rMNode == null) {
            return -1;
        }
        return rMNode.computeDeltaUpToRoot() + rMNode.intervalStart();
    }

    public int getEndOffset() {
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        if (rMNode == null) {
            return -1;
        }
        return rMNode.computeDeltaUpToRoot() + rMNode.intervalEnd();
    }

    void invalidate(final Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(4);
        }
        setValid(false);
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        if (rMNode != null) {
            rMNode.processAliveKeys(new Processor() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RangeMarkerImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Processor
                public final boolean process(Object obj2) {
                    return this.f$0.m6477x8256b26f(obj, (RangeMarkerEx) obj2);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$invalidate$0$org-jetbrains-kotlin-com-intellij-openapi-editor-impl-RangeMarkerImpl, reason: not valid java name */
    /* synthetic */ boolean m6477x8256b26f(Object obj, RangeMarkerEx rangeMarkerEx) {
        this.myNode.getTree().beforeRemove(rangeMarkerEx, obj);
        return true;
    }

    public final DocumentEx getDocument() {
        Object obj = this.myDocumentOrFile;
        DocumentEx documentEx = obj instanceof VirtualFile ? (DocumentEx) FileDocumentManager.getInstance().getDocument((VirtualFile) obj) : (DocumentEx) obj;
        if (documentEx == null) {
            LOG.error("document is null; isValid=" + isValid() + "; file=" + obj);
        }
        if (documentEx == null) {
            $$$reportNull$$$0(5);
        }
        return documentEx;
    }

    DocumentEx getCachedDocument() {
        Object cachedDocument = this.myDocumentOrFile;
        if (cachedDocument instanceof VirtualFile) {
            cachedDocument = FileDocumentManager.getInstance().getCachedDocument((VirtualFile) cachedDocument);
        }
        return (DocumentEx) cachedDocument;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.RangeMarker
    public boolean isGreedyToLeft() {
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        return rMNode != null && rMNode.isGreedyToLeft();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.RangeMarker
    public boolean isGreedyToRight() {
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        return rMNode != null && rMNode.isGreedyToRight();
    }

    public boolean isStickingToRight() {
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        return rMNode != null && rMNode.isStickingToRight();
    }

    public final void documentChanged(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(6);
        }
        int iIntervalStart = intervalStart();
        int iIntervalEnd = intervalEnd();
        int textLength = documentEvent.getDocument().getTextLength();
        if (!isValid()) {
            LOG.error("Invalid range marker " + (isGreedyToLeft() ? "[" : "(") + iIntervalStart + ", " + iIntervalEnd + (isGreedyToRight() ? "]" : Constant.AFTER_QUTO) + ". Event = " + documentEvent + ". Doc length=" + textLength + "; " + getClass());
            return;
        }
        if (iIntervalStart > iIntervalEnd || iIntervalStart < 0 || iIntervalEnd > (textLength - documentEvent.getNewLength()) + documentEvent.getOldLength()) {
            LOG.error("RangeMarker" + (isGreedyToLeft() ? "[" : "(") + iIntervalStart + ", " + iIntervalEnd + (isGreedyToRight() ? "]" : Constant.AFTER_QUTO) + " is invalid before update. Event = " + documentEvent + ". Doc length=" + textLength + "; " + getClass());
            invalidate(documentEvent);
            return;
        }
        changedUpdateImpl(documentEvent);
        if (isValid()) {
            int iIntervalStart2 = intervalStart();
            int iIntervalEnd2 = intervalEnd();
            if (iIntervalStart2 > iIntervalEnd2 || iIntervalStart2 < 0 || iIntervalEnd2 > textLength) {
                LOG.error("Update failed. Event = " + documentEvent + ". Doc length=" + textLength + "; " + getClass() + ". Before update: " + (isGreedyToLeft() ? "[" : "(") + iIntervalStart + ", " + iIntervalEnd + (isGreedyToRight() ? "]" : Constant.AFTER_QUTO) + " After update: '" + this + FormatHelper.QUOTE);
                invalidate(documentEvent);
            }
        }
    }

    protected void changedUpdateImpl(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(7);
        }
        doChangeUpdate(documentEvent);
    }

    private void doChangeUpdate(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(8);
        }
        if (isValid()) {
            TextRange textRangeApplyChange = applyChange(documentEvent, intervalStart(), intervalEnd(), isGreedyToLeft(), isGreedyToRight(), isStickingToRight());
            if (textRangeApplyChange == null) {
                invalidate(documentEvent);
            } else {
                setIntervalStart(textRangeApplyChange.getStartOffset());
                setIntervalEnd(textRangeApplyChange.getEndOffset());
            }
        }
    }

    static TextRange applyChange(DocumentEvent documentEvent, int i, int i2, boolean z, boolean z2, boolean z3) {
        if (documentEvent == null) {
            $$$reportNull$$$0(12);
        }
        if (i == i2) {
            return processIfOnePoint(documentEvent, i, z2, z3);
        }
        int offset = documentEvent.getOffset();
        int oldLength = documentEvent.getOldLength();
        int newLength = documentEvent.getNewLength();
        if (i2 < offset) {
            return new UnfairTextRange(i, i2);
        }
        if (!z2 && i2 == offset) {
            if ((documentEvent instanceof DocumentEventImpl) && oldLength == 0 && ((DocumentEventImpl) documentEvent).getInitialStartOffset() < offset) {
                return new UnfairTextRange(i, i2 + newLength);
            }
            return new UnfairTextRange(i, i2);
        }
        int i3 = offset + oldLength;
        if (i > i3) {
            return new UnfairTextRange((i + newLength) - oldLength, (i2 + newLength) - oldLength);
        }
        if (!z && i == i3) {
            if ((documentEvent instanceof DocumentEventImpl) && oldLength == 0) {
                DocumentEventImpl documentEventImpl = (DocumentEventImpl) documentEvent;
                if (documentEventImpl.getInitialStartOffset() + documentEventImpl.getInitialOldLength() > offset) {
                    return new UnfairTextRange(i, i2 + newLength);
                }
            }
            return new UnfairTextRange((i + newLength) - oldLength, (i2 + newLength) - oldLength);
        }
        if (i <= offset && i2 >= i3) {
            return new ProperTextRange(i, (i2 + newLength) - oldLength);
        }
        if (i >= offset && i <= i3 && i2 > i3) {
            return new ProperTextRange(offset + newLength, (i2 + newLength) - oldLength);
        }
        if (i2 < offset || i2 > i3 || i >= offset) {
            return null;
        }
        return new UnfairTextRange(i, offset);
    }

    private static TextRange processIfOnePoint(DocumentEvent documentEvent, int i, boolean z, boolean z2) {
        if (documentEvent == null) {
            $$$reportNull$$$0(13);
        }
        int offset = documentEvent.getOffset();
        int oldLength = documentEvent.getOldLength();
        int i2 = offset + oldLength;
        if (offset < i && i < i2) {
            return null;
        }
        if (offset == i && oldLength == 0) {
            if (z) {
                return new UnfairTextRange(i, documentEvent.getNewLength() + i);
            }
            if (z2) {
                return new UnfairTextRange(documentEvent.getNewLength() + i, i + documentEvent.getNewLength());
            }
        }
        if (i > i2 || (i == i2 && oldLength > 0)) {
            return new UnfairTextRange((documentEvent.getNewLength() + i) - oldLength, (i + documentEvent.getNewLength()) - oldLength);
        }
        return new UnfairTextRange(i, i);
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "RangeMarker" + (isGreedyToLeft() ? "[" : "(") + (isValid() ? "" : "invalid:") + getStartOffset() + "," + getEndOffset() + (isGreedyToRight() ? "]" : Constant.AFTER_QUTO) + " " + getId();
    }

    int setIntervalStart(int i) {
        if (i < 0) {
            LOG.error("Negative start: " + i);
        }
        return this.myNode.setIntervalStart(i);
    }

    int setIntervalEnd(int i) {
        if (i < 0) {
            LOG.error("Negative end: " + i);
        }
        return this.myNode.setIntervalEnd(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.RangeMarker
    public boolean isValid() {
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        if (rMNode == null || !rMNode.isValid()) {
            return false;
        }
        Object obj = this.myDocumentOrFile;
        return (obj instanceof Document) || canHaveDocument((VirtualFile) obj);
    }

    private static boolean canHaveDocument(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(14);
        }
        if (FileDocumentManager.getInstance().getCachedDocument(virtualFile) != null) {
            return true;
        }
        if (!virtualFile.isValid() || virtualFile.isDirectory() || isBinaryWithoutDecompiler(virtualFile)) {
            return false;
        }
        return (virtualFile.getFileType().isBinary() && FileUtilRt.isTooLarge(virtualFile.getLength())) ? false : true;
    }

    private static boolean isBinaryWithoutDecompiler(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(15);
        }
        FileType fileType = virtualFile.getFileType();
        return fileType.isBinary() && BinaryFileTypeDecompilers.getInstance().forFileType(fileType) == null;
    }

    protected boolean setValid(boolean z) {
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        return rMNode == null || rMNode.setValid(z);
    }

    public int intervalStart() {
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        if (rMNode == null) {
            return -1;
        }
        return rMNode.intervalStart();
    }

    public int intervalEnd() {
        RangeMarkerTree.RMNode<RangeMarkerEx> rMNode = this.myNode;
        if (rMNode == null) {
            return -1;
        }
        return rMNode.intervalEnd();
    }
}
