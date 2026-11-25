package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.beans.PropertyChangeSupport;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuardImpl;
import org.jetbrains.kotlin.com.intellij.openapi.command.CommandProcessor;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.RangeMarker;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ReadOnlyFragmentModificationException;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ReadOnlyModificationException;
import org.jetbrains.kotlin.com.intellij.openapi.editor.actionSystem.DocCommandGroupId;
import org.jetbrains.kotlin.com.intellij.openapi.editor.actionSystem.ReadonlyFragmentModificationHandler;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.PrioritizedDocumentListener;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.RangeMarkerEx;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.event.DocumentEventImpl;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.ShutDownTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.LocalTimeCounter;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.text.ImmutableCharSequence;

/* JADX INFO: loaded from: classes3.dex */
public final class DocumentImpl extends UserDataHolderBase implements DocumentEx {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile boolean isStripTrailingSpacesEnabled;
    private volatile boolean myAcceptSlashR;
    private final boolean myAssertThreading;
    private volatile int myBufferSize;
    private volatile Throwable myBulkUpdateEnteringTrace;
    private boolean myChangeInProgress;
    private int myCheckGuardedBlocks;
    private final LockFreeCOWSortedArray<DocumentListener> myDocumentListeners;
    private volatile boolean myDoingBulkUpdate;
    private boolean myEventsHandling;
    private volatile FrozenDocument myFrozen;
    private final List<RangeMarker> myGuardedBlocks;
    private boolean myGuardsSuppressed;
    private boolean myIsReadOnly;
    private volatile LineSet myLineSet;
    private final Object myLineSetLock;
    private volatile long myModificationStamp;
    private final CharSequence myMutableCharSequence;
    private final RangeMarkerTree<RangeMarkerEx> myPersistentRangeMarkers;
    private final PropertyChangeSupport myPropertyChangeSupport;
    private final RangeMarkerTree<RangeMarkerEx> myRangeMarkers;
    private final List<Object> myReadOnlyListeners;
    private ReadonlyFragmentModificationHandler myReadonlyFragmentModificationHandler;
    private volatile ImmutableCharSequence myText;
    private volatile SoftReference<String> myTextString;
    private boolean myUpdatingBulkModeStatus;
    private final AtomicInteger sequence;
    private static final Logger LOG = Logger.getInstance((Class<?>) DocumentImpl.class);
    static final Key<Reference<RangeMarkerTree<RangeMarkerEx>>> RANGE_MARKERS_KEY = Key.create("RANGE_MARKERS_KEY");
    static final Key<Reference<RangeMarkerTree<RangeMarkerEx>>> PERSISTENT_RANGE_MARKERS_KEY = Key.create("PERSISTENT_RANGE_MARKERS_KEY");
    private static final ReferenceQueue<RangeMarkerTree<RangeMarkerEx>> rmTreeQueue = new ReferenceQueue<>();

    /* JADX WARN: Code duplicated, block: B:15:0x0030  */
    /* JADX WARN: Code duplicated, block: B:74:0x0106  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 10 && i != 15 && i != 17 && i != 49 && i != 32 && i != 33 && i != 42 && i != 43 && i != 52 && i != 53) {
            switch (i) {
                case 35:
                case 36:
                case 37:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 10 && i != 15 && i != 17 && i != 49 && i != 32 && i != 33 && i != 42 && i != 43 && i != 52 && i != 53) {
            switch (i) {
                case 35:
                case 36:
                case 37:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 18:
                objArr[0] = "chars";
                break;
            case 4:
            case 5:
            case 6:
                objArr[0] = "f";
                break;
            case 7:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 8:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 9:
                objArr[0] = "file";
                break;
            case 10:
            case 15:
            case 17:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 42:
            case 43:
            case 49:
            case 52:
            case 53:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/DocumentImpl";
                break;
            case 11:
                objArr[0] = "filters";
                break;
            case 12:
            case 13:
            case 14:
                objArr[0] = "rangeMarker";
                break;
            case 16:
                objArr[0] = "block";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[0] = "s";
                break;
            case 23:
                objArr[0] = "guard";
                break;
            case 24:
            case 28:
                objArr[0] = "oldString";
                break;
            case 25:
            case 29:
                objArr[0] = "newString";
                break;
            case 26:
                objArr[0] = "caretLines";
                break;
            case 27:
                objArr[0] = "newText";
                break;
            case 30:
                objArr[0] = "event";
                break;
            case 31:
                objArr[0] = "prevText";
                break;
            case 34:
                objArr[0] = "range";
                break;
            case 38:
            case 39:
            case 41:
            case 44:
            case 45:
            case 46:
            case 47:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 40:
                objArr[0] = "parentDisposable";
                break;
            case 48:
            default:
                objArr[0] = "text";
                break;
            case 50:
            case 51:
                objArr[0] = "processor";
                break;
        }
        if (i == 10) {
            objArr[1] = "createRangeMarkerForVirtualFile";
        } else if (i == 15) {
            objArr[1] = "createGuardedBlock";
        } else if (i == 17) {
            objArr[1] = "getGuardedBlocks";
        } else if (i == 49) {
            objArr[1] = "getPublisher";
        } else if (i == 32) {
            objArr[1] = "getText";
        } else if (i == 33) {
            objArr[1] = "doGetText";
        } else if (i == 42) {
            objArr[1] = "createLineIterator";
        } else if (i == 43) {
            objArr[1] = "getListeners";
        } else if (i == 52) {
            objArr[1] = "dumpState";
        } else if (i != 53) {
            switch (i) {
                case 35:
                    objArr[1] = "getText";
                    break;
                case 36:
                    objArr[1] = "getCharsSequence";
                    break;
                case 37:
                    objArr[1] = "getImmutableCharSequence";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/DocumentImpl";
                    break;
            }
        } else {
            objArr[1] = "freeze";
        }
        switch (i) {
            case 4:
                objArr[2] = "documentCreatedFrom";
                break;
            case 5:
                objArr[2] = "areRangeMarkersRetainedFor";
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "getSaveRMTree";
                break;
            case 9:
                objArr[2] = "createRangeMarkerForVirtualFile";
                break;
            case 10:
            case 15:
            case 17:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 42:
            case 43:
            case 49:
            case 52:
            case 53:
                break;
            case 11:
                objArr[2] = "getMaxSpacesToLeave";
                break;
            case 12:
                objArr[2] = "treeFor";
                break;
            case 13:
                objArr[2] = "removeRangeMarker";
                break;
            case 14:
                objArr[2] = "registerRangeMarker";
                break;
            case 16:
                objArr[2] = "removeGuardedBlock";
                break;
            case 18:
                objArr[2] = "replaceText";
                break;
            case 19:
                objArr[2] = "insertString";
                break;
            case 20:
            case 21:
                objArr[2] = "replaceString";
                break;
            case 22:
                objArr[2] = "assertValidSeparators";
                break;
            case 23:
            case 24:
            case 25:
                objArr[2] = "throwGuardedFragment";
                break;
            case 26:
                objArr[2] = "clearLineModificationFlagsExcept";
                break;
            case 27:
            case 28:
            case 29:
                objArr[2] = "updateText";
                break;
            case 30:
            case 31:
                objArr[2] = "changedUpdate";
                break;
            case 34:
                objArr[2] = "getText";
                break;
            case 38:
            case 39:
            case 40:
                objArr[2] = "addDocumentListener";
                break;
            case 41:
                objArr[2] = "removeDocumentListener";
                break;
            case 44:
                objArr[2] = "addEditReadOnlyListener";
                break;
            case 45:
                objArr[2] = "removeEditReadOnlyListener";
                break;
            case 46:
                objArr[2] = "addPropertyChangeListener";
                break;
            case 47:
                objArr[2] = "removePropertyChangeListener";
                break;
            case 48:
                objArr[2] = "setText";
                break;
            case 50:
                objArr[2] = "processRangeMarkers";
                break;
            case 51:
                objArr[2] = "processRangeMarkersOverlappingWith";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 15 && i != 17 && i != 49 && i != 32 && i != 33 && i != 42 && i != 43 && i != 52 && i != 53) {
            switch (i) {
                case 35:
                case 36:
                case 37:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private static boolean rangesIntersect(int i, int i2, boolean z, boolean z2, int i3, int i4, boolean z3, boolean z4) {
        if (i > i3 || (i == i3 && !z)) {
            if (i4 == i) {
                return z && z4;
            }
            return i4 > i;
        }
        if (i2 == i3) {
            return z3 && z2;
        }
        return i2 > i3;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DocumentImpl(CharSequence charSequence) {
        this(charSequence, false);
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DocumentImpl(CharSequence charSequence, boolean z) {
        this(charSequence, false, z);
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
    }

    public DocumentImpl(CharSequence charSequence, boolean z, boolean z2) {
        if (charSequence == null) {
            $$$reportNull$$$0(3);
        }
        this.myDocumentListeners = new LockFreeCOWSortedArray<>(PrioritizedDocumentListener.COMPARATOR, DocumentListener.ARRAY_FACTORY);
        this.myRangeMarkers = new RangeMarkerTree<>(this);
        this.myPersistentRangeMarkers = new RangeMarkerTree<>(this);
        this.myGuardedBlocks = new ArrayList();
        this.myLineSetLock = ObjectUtils.sentinel("line set lock");
        this.isStripTrailingSpacesEnabled = true;
        this.myPropertyChangeSupport = new PropertyChangeSupport(this);
        this.myReadOnlyListeners = ContainerUtil.createLockFreeCopyOnWriteList();
        this.myMutableCharSequence = new CharSequence() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.impl.DocumentImpl.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/DocumentImpl$1";
                if (i != 1) {
                    objArr[1] = "subSequence";
                } else {
                    objArr[1] = "toString";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // java.lang.CharSequence
            public int length() {
                return DocumentImpl.this.myText.length();
            }

            @Override // java.lang.CharSequence
            public char charAt(int i) {
                return DocumentImpl.this.myText.charAt(i);
            }

            @Override // java.lang.CharSequence
            public CharSequence subSequence(int i, int i2) {
                CharSequence charSequenceSubSequence = DocumentImpl.this.myText.subSequence(i, i2);
                if (charSequenceSubSequence == null) {
                    $$$reportNull$$$0(0);
                }
                return charSequenceSubSequence;
            }

            @Override // java.lang.CharSequence
            public String toString() {
                String strDoGetText = DocumentImpl.this.doGetText();
                if (strDoGetText == null) {
                    $$$reportNull$$$0(1);
                }
                return strDoGetText;
            }
        };
        this.sequence = new AtomicInteger();
        setAcceptSlashR(z);
        assertValidSeparators(charSequence);
        this.myText = CharArrayUtil.createImmutableCharSequence(charSequence);
        setCyclicBufferSize(0);
        setModificationStamp(LocalTimeCounter.currentTime());
        this.myAssertThreading = !z2;
    }

    public boolean setAcceptSlashR(boolean z) {
        try {
            return this.myAcceptSlashR;
        } finally {
            this.myAcceptSlashR = z;
        }
    }

    private LineSet getLineSet() {
        LineSet lineSetCreateLineSet = this.myLineSet;
        if (lineSetCreateLineSet == null) {
            synchronized (this.myLineSetLock) {
                lineSetCreateLineSet = this.myLineSet;
                if (lineSetCreateLineSet == null) {
                    lineSetCreateLineSet = LineSet.createLineSet(this.myText);
                    this.myLineSet = lineSetCreateLineSet;
                }
            }
        }
        return lineSetCreateLineSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public void setReadOnly(boolean z) {
        if (this.myIsReadOnly != z) {
            this.myIsReadOnly = z;
            this.myPropertyChangeSupport.firePropertyChange("writable", !z, z);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public boolean isWritable() {
        if (this.myIsReadOnly) {
            return false;
        }
        for (DocumentWriteAccessGuard documentWriteAccessGuard : DocumentWriteAccessGuard.EP_NAME.getExtensions()) {
            if (!documentWriteAccessGuard.isWritable(this).isSuccess()) {
                return false;
            }
        }
        return true;
    }

    private RangeMarkerTree<RangeMarkerEx> treeFor(RangeMarkerEx rangeMarkerEx) {
        if (rangeMarkerEx == null) {
            $$$reportNull$$$0(12);
        }
        return rangeMarkerEx instanceof PersistentRangeMarker ? this.myPersistentRangeMarkers : this.myRangeMarkers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx
    public boolean removeRangeMarker(RangeMarkerEx rangeMarkerEx) {
        if (rangeMarkerEx == null) {
            $$$reportNull$$$0(13);
        }
        return treeFor(rangeMarkerEx).removeInterval(rangeMarkerEx);
    }

    public RangeMarker getRangeGuard(int i, int i2) {
        for (RangeMarker rangeMarker : this.myGuardedBlocks) {
            if (rangesIntersect(i, i2, true, true, rangeMarker.getStartOffset(), rangeMarker.getEndOffset(), rangeMarker.isGreedyToLeft(), rangeMarker.isGreedyToRight())) {
                return rangeMarker;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public long getModificationStamp() {
        return this.myModificationStamp;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx
    public void setModificationStamp(long j) {
        this.myModificationStamp = j;
        this.myFrozen = null;
    }

    private void trimToSize() {
        if (this.myBufferSize == 0 || getTextLength() <= this.myBufferSize) {
            return;
        }
        deleteString(0, getTextLength() - this.myBufferSize);
    }

    public void deleteString(int i, int i2) {
        assertBounds(i, i2);
        assertWriteAccess();
        if (i == i2) {
            return;
        }
        RangeMarker rangeGuard = getRangeGuard(i, i2);
        if (rangeGuard != null) {
            throwGuardedFragment(rangeGuard, i, this.myText.subSequence(i, i2), "");
        }
        updateText(this.myText.delete(i, i2), i, this.myText.subtext(i, i2), "", false, LocalTimeCounter.currentTime(), i, i2 - i, i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public void replaceString(int i, int i2, CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(20);
        }
        replaceString(i, i2, i, charSequence, LocalTimeCounter.currentTime(), false);
    }

    public void replaceString(int i, int i2, int i3, CharSequence charSequence, long j, boolean z) {
        ImmutableCharSequence immutableCharSequenceReplace;
        int i4 = i2;
        if (charSequence == null) {
            $$$reportNull$$$0(21);
        }
        assertBounds(i, i2);
        assertWriteAccess();
        assertValidSeparators(charSequence);
        if (i3 != i && i != i4 && charSequence.length() != 0) {
            throw new IllegalArgumentException("moveOffset != startOffset for a modification which is neither an insert nor deletion. startOffset: " + i + "; endOffset: " + i2 + ";; moveOffset: " + i3 + ";");
        }
        int i5 = i4 - i;
        int length = charSequence.length();
        ImmutableCharSequence immutableCharSequence = this.myText;
        int i6 = 0;
        int i7 = i;
        while (i6 < length && i7 < i4 && charSequence.charAt(i6) == immutableCharSequence.charAt(i7)) {
            i7++;
            i6++;
        }
        if (i6 == length && i7 == i4 && !z) {
            return;
        }
        while (i4 > i7 && length > i6 && charSequence.charAt(length - 1) == immutableCharSequence.charAt(i4 - 1)) {
            length--;
            i4--;
        }
        boolean z2 = (i7 == 0 && i4 == getTextLength()) ? true : z;
        CharSequence charSequenceSubSequence = charSequence.subSequence(i6, length);
        ImmutableCharSequence immutableCharSequenceSubtext = this.myText.subtext(i7, i4);
        RangeMarker rangeGuard = getRangeGuard(i7, i4);
        if (rangeGuard != null) {
            throwGuardedFragment(rangeGuard, i7, immutableCharSequenceSubtext, charSequenceSubSequence);
        }
        if (z2 && (charSequence instanceof ImmutableCharSequence)) {
            immutableCharSequenceReplace = (ImmutableCharSequence) charSequence;
        } else {
            immutableCharSequenceReplace = this.myText.replace(i7, i4, charSequenceSubSequence);
            charSequenceSubSequence = immutableCharSequenceReplace.subtext(i7, charSequenceSubSequence.length() + i7);
        }
        updateText(immutableCharSequenceReplace, i7, immutableCharSequenceSubtext, charSequenceSubSequence, z2, j, i, i5, (i == i7 && i4 - i7 == i5) ? i3 : i7);
        trimToSize();
    }

    private void assertBounds(int i, int i2) {
        if (i < 0 || i > getTextLength()) {
            throw new IndexOutOfBoundsException("Wrong startOffset: " + i + "; documentLength: " + getTextLength());
        }
        if (i2 < 0 || i2 > getTextLength()) {
            throw new IndexOutOfBoundsException("Wrong endOffset: " + i2 + "; documentLength: " + getTextLength());
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endOffset < startOffset: " + i2 + " < " + i + "; documentLength: " + getTextLength());
        }
    }

    private void assertWriteAccess() {
        Application application;
        if (this.myAssertThreading && (application = ApplicationManager.getApplication()) != null) {
            application.assertWriteAccessAllowed();
            VirtualFile file = FileDocumentManager.getInstance().getFile(this);
            if (file != null && file.isInLocalFileSystem()) {
                ((TransactionGuardImpl) TransactionGuard.getInstance()).assertWriteActionAllowed();
            }
        }
        if (this.myIsReadOnly) {
            throw new ReadOnlyModificationException(this, CoreBundle.message("attempt.to.modify.read.only.document.error.message", new Object[0]));
        }
        for (DocumentWriteAccessGuard documentWriteAccessGuard : DocumentWriteAccessGuard.EP_NAME.getExtensions()) {
            DocumentWriteAccessGuard.Result resultIsWritable = documentWriteAccessGuard.isWritable(this);
            if (!resultIsWritable.isSuccess()) {
                throw new ReadOnlyModificationException(this, String.format("%s: guardClass=%s, failureReason=%s", CoreBundle.message("attempt.to.modify.read.only.document.error.message", new Object[0]), documentWriteAccessGuard.getClass().getName(), resultIsWritable.getFailureReason()));
            }
        }
    }

    private void assertValidSeparators(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(22);
        }
        if (this.myAcceptSlashR) {
            return;
        }
        StringUtil.assertValidSeparators(charSequence);
    }

    private void assertNotNestedModification() throws IllegalStateException {
        if (this.myChangeInProgress) {
            throw new IllegalStateException("Detected document modification from DocumentListener");
        }
    }

    private void throwGuardedFragment(RangeMarker rangeMarker, int i, CharSequence charSequence, CharSequence charSequence2) {
        if (rangeMarker == null) {
            $$$reportNull$$$0(23);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(24);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(25);
        }
        if (this.myCheckGuardedBlocks > 0 && !this.myGuardsSuppressed) {
            throw new ReadOnlyFragmentModificationException(new DocumentEventImpl(this, i, charSequence, charSequence2, this.myModificationStamp, false, i, charSequence.length(), i), rangeMarker);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx
    public void suppressGuardedExceptions() {
        this.myGuardsSuppressed = true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx
    public void unSuppressGuardedExceptions() {
        this.myGuardsSuppressed = false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx
    public boolean isInEventsHandling() {
        return this.myEventsHandling;
    }

    public void clearLineModificationFlags() {
        this.myLineSet = getLineSet().clearModificationFlags();
        this.myFrozen = null;
    }

    private void updateText(ImmutableCharSequence immutableCharSequence, int i, CharSequence charSequence, CharSequence charSequence2, boolean z, long j, int i2, int i3, int i4) {
        if (immutableCharSequence == null) {
            $$$reportNull$$$0(27);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(28);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(29);
        }
        Logger logger = LOG;
        if (logger.isTraceEnabled()) {
            logger.trace("updating document " + this + ".\nNext string:'" + ((Object) charSequence2) + "'\nOld string:'" + ((Object) charSequence) + FormatHelper.QUOTE);
        }
        assertNotNestedModification();
        this.myChangeInProgress = true;
        DelayedExceptions delayedExceptions = new DelayedExceptions();
        try {
            DocumentEventImpl documentEventImpl = new DocumentEventImpl(this, i, charSequence, charSequence2, this.myModificationStamp, z, i2, i3, i4);
            beforeChangedUpdate(documentEventImpl, delayedExceptions);
            this.myTextString = null;
            ImmutableCharSequence immutableCharSequence2 = this.myText;
            this.myText = immutableCharSequence;
            this.sequence.incrementAndGet();
            changedUpdate(documentEventImpl, j, immutableCharSequence2, delayedExceptions);
        } finally {
            this.myChangeInProgress = false;
            delayedExceptions.rethrowPCE();
        }
    }

    private final class DelayedExceptions {
        Throwable myException;

        private DelayedExceptions() {
        }

        void register(Throwable th) {
            Throwable th2 = this.myException;
            if (th2 == null) {
                this.myException = th;
            } else {
                th2.addSuppressed(th);
            }
            if (!(th instanceof ProcessCanceledException)) {
                DocumentImpl.LOG.error(th);
            } else if (DocumentImpl.this.myAssertThreading) {
                DocumentImpl.LOG.error("ProcessCanceledException must not be thrown from document listeners for real document", new Throwable(th));
            }
        }

        void rethrowPCE() {
            Throwable th = this.myException;
            if (th instanceof ProcessCanceledException) {
                throw ((ProcessCanceledException) th);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx
    public int getModificationSequence() {
        return this.sequence.get();
    }

    private void beforeChangedUpdate(DocumentEvent documentEvent, DelayedExceptions delayedExceptions) {
        VirtualFile file;
        if (ApplicationManager.getApplication() != null && (file = FileDocumentManager.getInstance().getFile(this)) != null && !file.isValid()) {
            LOG.error("File of this document has been deleted: " + file);
        }
        assertInsideCommand();
        getLineSet();
        if (!ShutDownTracker.isShutdownHookRunning()) {
            DocumentListener[] listeners = getListeners();
            for (int length = listeners.length - 1; length >= 0; length--) {
                try {
                    listeners[length].beforeDocumentChange(documentEvent);
                } catch (Throwable th) {
                    delayedExceptions.register(th);
                }
            }
        }
        this.myEventsHandling = true;
    }

    private void assertInsideCommand() {
        if (this.myAssertThreading) {
            CommandProcessor commandProcessor = CommandProcessor.getInstance();
            if (!commandProcessor.isUndoTransparentActionInProgress() && commandProcessor.getCurrentCommand() == null) {
                throw new IncorrectOperationException("Must not change document outside command or undo-transparent action. See com.intellij.openapi.command.WriteCommandAction or com.intellij.openapi.command.CommandProcessor");
            }
        }
    }

    private void changedUpdate(DocumentEvent documentEvent, long j, CharSequence charSequence, DelayedExceptions delayedExceptions) {
        if (documentEvent == null) {
            $$$reportNull$$$0(30);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(31);
        }
        try {
            Logger logger = LOG;
            if (logger.isTraceEnabled()) {
                logger.trace(new Throwable(documentEvent.toString()));
            } else if (logger.isDebugEnabled()) {
                logger.debug(documentEvent.toString());
            }
            this.myLineSet = getLineSet().update(charSequence, documentEvent.getOffset(), documentEvent.getOldLength() + documentEvent.getOffset(), documentEvent.getNewFragment(), documentEvent.isWholeTextReplaced());
            this.myFrozen = null;
            setModificationStamp(j);
            if (!ShutDownTracker.isShutdownHookRunning()) {
                for (DocumentListener documentListener : getListeners()) {
                    try {
                        documentListener.documentChanged(documentEvent);
                    } catch (Throwable th) {
                        delayedExceptions.register(th);
                    }
                }
            }
            this.myEventsHandling = false;
        } catch (Throwable th2) {
            this.myEventsHandling = false;
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public String getText() {
        String str = (String) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.impl.DocumentImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.doGetText();
            }
        });
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String doGetText() {
        String str = (String) SoftReference.dereference(this.myTextString);
        if (str == null) {
            String string = this.myText.toString();
            this.myTextString = new SoftReference<>(string);
            str = string;
        }
        if (str == null) {
            $$$reportNull$$$0(33);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public int getTextLength() {
        return this.myText.length();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public CharSequence getCharsSequence() {
        CharSequence charSequence = this.myMutableCharSequence;
        if (charSequence == null) {
            $$$reportNull$$$0(36);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public CharSequence getImmutableCharSequence() {
        ImmutableCharSequence immutableCharSequence = this.myText;
        if (immutableCharSequence == null) {
            $$$reportNull$$$0(37);
        }
        return immutableCharSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public void addDocumentListener(DocumentListener documentListener) {
        if (documentListener == null) {
            $$$reportNull$$$0(38);
        }
        if (ArrayUtil.contains(documentListener, getListeners())) {
            LOG.error("Already registered: " + documentListener);
        }
        this.myDocumentListeners.add(documentListener);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public int getLineNumber(int i) {
        return getLineSet().findLineIndex(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public int getLineStartOffset(int i) {
        if (i == 0) {
            return 0;
        }
        return getLineSet().getLineStart(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public int getLineEndOffset(int i) {
        if (getTextLength() == 0 && i == 0) {
            return 0;
        }
        return getLineSet().getLineEnd(i) - getLineSeparatorLength(i);
    }

    public int getLineSeparatorLength(int i) {
        return getLineSet().getSeparatorLength(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public int getLineCount() {
        return getLineSet().getLineCount();
    }

    private DocumentListener[] getListeners() {
        DocumentListener[] array = this.myDocumentListeners.getArray();
        if (array == null) {
            $$$reportNull$$$0(43);
        }
        return array;
    }

    public void setCyclicBufferSize(int i) {
        this.myBufferSize = i;
    }

    /* JADX INFO: renamed from: lambda$setText$2$org-jetbrains-kotlin-com-intellij-openapi-editor-impl-DocumentImpl, reason: not valid java name */
    /* synthetic */ void m6474xf2d58f67(CharSequence charSequence) {
        replaceString(0, getTextLength(), 0, charSequence, LocalTimeCounter.currentTime(), true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public void setText(final CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(48);
        }
        Runnable runnable = new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.impl.DocumentImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6474xf2d58f67(charSequence);
            }
        };
        if (CommandProcessor.getInstance().isUndoTransparentActionInProgress() || !this.myAssertThreading) {
            runnable.run();
        } else {
            CommandProcessor.getInstance().executeCommand(null, runnable, "", DocCommandGroupId.noneGroupId(this));
        }
        clearLineModificationFlags();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public boolean isInBulkUpdate() {
        return this.myDoingBulkUpdate;
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "DocumentImpl[" + FileDocumentManager.getInstance().getFile(this) + (isInEventsHandling() ? ",inEventHandling" : "") + "]";
    }

    public FrozenDocument freeze() {
        FrozenDocument frozenDocument = this.myFrozen;
        if (frozenDocument == null) {
            synchronized (this.myLineSetLock) {
                frozenDocument = this.myFrozen;
                if (frozenDocument == null) {
                    frozenDocument = new FrozenDocument(this.myText, this.myLineSet, this.myModificationStamp, (String) SoftReference.dereference(this.myTextString));
                    this.myFrozen = frozenDocument;
                }
            }
        }
        if (frozenDocument == null) {
            $$$reportNull$$$0(53);
        }
        return frozenDocument;
    }
}
