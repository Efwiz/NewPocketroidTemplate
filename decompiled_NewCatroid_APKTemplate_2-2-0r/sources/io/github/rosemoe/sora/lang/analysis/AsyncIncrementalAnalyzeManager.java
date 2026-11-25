package io.github.rosemoe.sora.lang.analysis;

import android.os.Message;
import android.util.Log;
import io.github.rosemoe.sora.lang.styling.CodeBlock;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.SpanFactory;
import io.github.rosemoe.sora.lang.styling.Spans;
import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.lang.util.BaseAnalyzeManager;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.util.IntPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AsyncIncrementalAnalyzeManager<S, T> extends BaseAnalyzeManager implements IncrementalAnalyzeManager<S, T> {
    private static final int MSG_BASE = 11451400;
    private static final int MSG_INIT = 11451401;
    private static final int MSG_MOD = 11451402;
    private static int sThreadId;
    private volatile long runCount;
    private AsyncIncrementalAnalyzeManager<S, T>.LooperThread thread;

    public interface ReceiverConsumer {
        void accept(StyleReceiver styleReceiver);
    }

    public abstract List<CodeBlock> computeBlocks(Content content, AsyncIncrementalAnalyzeManager<S, T>.CodeBlockAnalyzeDelegate codeBlockAnalyzeDelegate);

    @Override // io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
    public void onAbandonState(S s) {
    }

    @Override // io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
    public void onAddState(S s) {
    }

    /* JADX WARN: In static synchronized method top region not synchronized by class const: (wrap java.lang.Class:0x0000: CONST_CLASS  A[WRAPPED] io.github.rosemoe.sora.lang.analysis.AsyncIncrementalAnalyzeManager.class) */
    private static synchronized int nextThreadId() {
        int i;
        synchronized (AsyncIncrementalAnalyzeManager.class) {
            i = sThreadId + 1;
            sThreadId = i;
        }
        return i;
    }

    protected void withReceiver(ReceiverConsumer receiverConsumer) {
        StyleReceiver receiver = getReceiver();
        if (receiver != null) {
            receiverConsumer.accept(receiver);
        }
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void insert(CharPosition charPosition, CharPosition charPosition2, CharSequence charSequence) {
        if (this.thread != null) {
            increaseRunCount();
            this.thread.offerMessage(MSG_MOD, new TextModification(IntPair.pack(charPosition.line, charPosition.column), IntPair.pack(charPosition2.line, charPosition2.column), charSequence));
        }
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void delete(CharPosition charPosition, CharPosition charPosition2, CharSequence charSequence) {
        if (this.thread != null) {
            increaseRunCount();
            this.thread.offerMessage(MSG_MOD, new TextModification(IntPair.pack(charPosition.line, charPosition.column), IntPair.pack(charPosition2.line, charPosition2.column), null));
        }
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void rerun() {
        AsyncIncrementalAnalyzeManager<S, T>.LooperThread looperThread = this.thread;
        if (looperThread != null && looperThread.isAlive()) {
            this.thread.interrupt();
            this.thread.abort = true;
        }
        Content contentCopyText = getContentRef().getReference().copyText(false);
        contentCopyText.setUndoEnabled(false);
        AsyncIncrementalAnalyzeManager<S, T>.LooperThread looperThread2 = new LooperThread();
        this.thread = looperThread2;
        looperThread2.setName("AsyncAnalyzer-" + nextThreadId());
        this.thread.offerMessage(MSG_INIT, contentCopyText);
        increaseRunCount();
        sendNewStyles(null);
        this.thread.start();
    }

    @Override // io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
    public IncrementalAnalyzeManager.LineTokenizeResult<S, T> getState(int i) {
        AsyncIncrementalAnalyzeManager<S, T>.LooperThread looperThread = this.thread;
        if (looperThread == Thread.currentThread()) {
            if (i < 0 || i >= looperThread.states.size()) {
                return null;
            }
            return looperThread.states.get(i);
        }
        throw new SecurityException("Can not get state from non-analytical or abandoned thread");
    }

    private synchronized void increaseRunCount() {
        this.runCount++;
    }

    @Override // io.github.rosemoe.sora.lang.util.BaseAnalyzeManager, io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void destroy() {
        AsyncIncrementalAnalyzeManager<S, T>.LooperThread looperThread = this.thread;
        if (looperThread != null) {
            if (looperThread.isAlive()) {
                this.thread.interrupt();
            }
            this.thread.abort = true;
        }
        this.thread = null;
        super.destroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendNewStyles(Styles styles) {
        StyleReceiver receiver = getReceiver();
        if (receiver != null) {
            receiver.setStyles(this, styles);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdate(Styles styles, int i, int i2) {
        StyleReceiver receiver = getReceiver();
        if (receiver != null) {
            receiver.updateStyles(this, styles, new SequenceUpdateRange(i, i2));
        }
    }

    public Styles getManagedStyles() {
        Thread threadCurrentThread = Thread.currentThread();
        if (threadCurrentThread.getClass() != LooperThread.class) {
            throw new IllegalThreadStateException();
        }
        return ((LooperThread) threadCurrentThread).styles;
    }

    private static class LockedSpans implements Spans {
        private final List<Line> lines = new ArrayList(128);
        private final Lock lock = new ReentrantLock();

        @Override // io.github.rosemoe.sora.lang.styling.Spans
        public void adjustOnDelete(CharPosition charPosition, CharPosition charPosition2) {
        }

        @Override // io.github.rosemoe.sora.lang.styling.Spans
        public void adjustOnInsert(CharPosition charPosition, CharPosition charPosition2) {
        }

        @Override // io.github.rosemoe.sora.lang.styling.Spans
        public boolean supportsModify() {
            return true;
        }

        @Override // io.github.rosemoe.sora.lang.styling.Spans
        public int getLineCount() {
            return this.lines.size();
        }

        @Override // io.github.rosemoe.sora.lang.styling.Spans
        public Spans.Reader read() {
            return new ReaderImpl();
        }

        @Override // io.github.rosemoe.sora.lang.styling.Spans
        public Spans.Modifier modify() {
            return new ModifierImpl();
        }

        private static class Line {
            public Lock lock;
            public List<Span> spans;

            public Line() {
                this(null);
            }

            public Line(List<Span> list) {
                this.lock = new ReentrantLock();
                this.spans = list;
            }
        }

        private class ReaderImpl implements Spans.Reader {
            private Line line;

            private ReaderImpl() {
            }

            @Override // io.github.rosemoe.sora.lang.styling.Spans.Reader
            public void moveToLine(int i) {
                boolean zTryLock;
                if (i < 0 || i >= LockedSpans.this.lines.size()) {
                    Line line = this.line;
                    if (line != null) {
                        line.lock.unlock();
                    }
                    this.line = null;
                    return;
                }
                Line line2 = this.line;
                if (line2 != null) {
                    line2.lock.unlock();
                }
                try {
                    zTryLock = LockedSpans.this.lock.tryLock(100L, TimeUnit.MICROSECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    zTryLock = false;
                }
                if (zTryLock) {
                    try {
                        Line line3 = (Line) LockedSpans.this.lines.get(i);
                        if (line3.lock.tryLock()) {
                            this.line = line3;
                        } else {
                            this.line = null;
                        }
                        return;
                    } finally {
                        LockedSpans.this.lock.unlock();
                    }
                }
                this.line = null;
            }

            @Override // io.github.rosemoe.sora.lang.styling.Spans.Reader
            public int getSpanCount() {
                Line line = this.line;
                if (line == null) {
                    return 1;
                }
                return line.spans.size();
            }

            @Override // io.github.rosemoe.sora.lang.styling.Spans.Reader
            public Span getSpanAt(int i) {
                Line line = this.line;
                return line == null ? SpanFactory.obtain(0, 5L) : line.spans.get(i);
            }

            @Override // io.github.rosemoe.sora.lang.styling.Spans.Reader
            public List<Span> getSpansOnLine(int i) {
                boolean zTryLock;
                ArrayList arrayList = new ArrayList();
                try {
                    zTryLock = LockedSpans.this.lock.tryLock(1L, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    zTryLock = false;
                }
                if (zTryLock) {
                    try {
                        Line line = i < LockedSpans.this.lines.size() ? (Line) LockedSpans.this.lines.get(i) : null;
                        LockedSpans.this.lock.unlock();
                        if (line != null && line.lock.tryLock()) {
                            try {
                                return Collections.unmodifiableList(line.spans);
                            } finally {
                                line.lock.unlock();
                            }
                        }
                        arrayList.add(getSpanAt(0));
                    } catch (Throwable th) {
                        LockedSpans.this.lock.unlock();
                        throw th;
                    }
                } else {
                    arrayList.add(getSpanAt(0));
                }
                return arrayList;
            }
        }

        private class ModifierImpl implements Spans.Modifier {
            private ModifierImpl() {
            }

            @Override // io.github.rosemoe.sora.lang.styling.Spans.Modifier
            public void setSpansOnLine(int i, List<Span> list) {
                LockedSpans.this.lock.lock();
                while (LockedSpans.this.lines.size() <= i) {
                    try {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(SpanFactory.obtain(0, 5L));
                        LockedSpans.this.lines.add(new Line(arrayList));
                    } catch (Throwable th) {
                        LockedSpans.this.lock.unlock();
                        throw th;
                    }
                }
                Line line = (Line) LockedSpans.this.lines.get(i);
                line.lock.lock();
                try {
                    line.spans = list;
                    line.lock.unlock();
                    LockedSpans.this.lock.unlock();
                } catch (Throwable th2) {
                    line.lock.unlock();
                    throw th2;
                }
            }

            @Override // io.github.rosemoe.sora.lang.styling.Spans.Modifier
            public void addLineAt(int i, List<Span> list) {
                LockedSpans.this.lock.lock();
                try {
                    LockedSpans.this.lines.add(i, new Line(list));
                } finally {
                    LockedSpans.this.lock.unlock();
                }
            }

            @Override // io.github.rosemoe.sora.lang.styling.Spans.Modifier
            public void deleteLineAt(int i) {
                LockedSpans.this.lock.lock();
                try {
                    Line line = (Line) LockedSpans.this.lines.get(i);
                    line.lock.lock();
                    try {
                        LockedSpans.this.lines.remove(i);
                        line.lock.unlock();
                        LockedSpans.this.lock.unlock();
                    } catch (Throwable th) {
                        line.lock.unlock();
                        throw th;
                    }
                } catch (Throwable th2) {
                    LockedSpans.this.lock.unlock();
                    throw th2;
                }
            }
        }
    }

    private static class TextModification {
        private final CharSequence changedText;
        private final long end;
        private final long start;

        TextModification(long j, long j2, CharSequence charSequence) {
            this.start = j;
            this.end = j2;
            this.changedText = charSequence;
        }
    }

    public class CodeBlockAnalyzeDelegate {
        int suppressSwitch;
        private final AsyncIncrementalAnalyzeManager<S, T>.LooperThread thread;

        CodeBlockAnalyzeDelegate(AsyncIncrementalAnalyzeManager<S, T>.LooperThread looperThread) {
            this.thread = looperThread;
        }

        public void setSuppressSwitch(int i) {
            this.suppressSwitch = i;
        }

        void reset() {
            this.suppressSwitch = Integer.MAX_VALUE;
        }

        public boolean isCancelled() {
            return this.thread.myRunCount != AsyncIncrementalAnalyzeManager.this.runCount || this.thread.abort || this.thread.isInterrupted();
        }

        public boolean isNotCancelled() {
            return !isCancelled();
        }
    }

    private final class LooperThread extends Thread {
        volatile boolean abort;
        AsyncIncrementalAnalyzeManager<S, T>.CodeBlockAnalyzeDelegate delegate;
        private final BlockingQueue<Message> messageQueue;
        long myRunCount;
        Content shadowed;
        LockedSpans spans;
        List<IncrementalAnalyzeManager.LineTokenizeResult<S, T>> states;
        Styles styles;

        private LooperThread() {
            this.messageQueue = new LinkedBlockingQueue();
            this.states = new ArrayList();
            this.delegate = new CodeBlockAnalyzeDelegate(this);
        }

        public void offerMessage(int i, Object obj) {
            Message messageObtain = Message.obtain();
            messageObtain.what = i;
            messageObtain.obj = obj;
            offerMessage(messageObtain);
        }

        public void offerMessage(Message message) {
            this.messageQueue.offer(message);
        }

        private void initialize() {
            LockedSpans lockedSpans = new LockedSpans();
            this.spans = lockedSpans;
            this.styles = new Styles(lockedSpans);
            S initialState = AsyncIncrementalAnalyzeManager.this.getInitialState();
            Spans.Modifier modifierModify = this.spans.modify();
            int i = 0;
            while (i < this.shadowed.getLineCount() && !this.abort && !isInterrupted()) {
                IncrementalAnalyzeManager.LineTokenizeResult<S, T> lineTokenizeResult = AsyncIncrementalAnalyzeManager.this.tokenizeLine(this.shadowed.getLine(i), initialState, i);
                S s = lineTokenizeResult.state;
                List<Span> listGenerateSpansForLine = lineTokenizeResult.spans != null ? lineTokenizeResult.spans : AsyncIncrementalAnalyzeManager.this.generateSpansForLine(lineTokenizeResult);
                this.states.add(lineTokenizeResult.clearSpans());
                AsyncIncrementalAnalyzeManager.this.onAddState(lineTokenizeResult.state);
                modifierModify.addLineAt(i, listGenerateSpansForLine);
                i++;
                initialState = s;
            }
            this.styles.blocks = AsyncIncrementalAnalyzeManager.this.computeBlocks(this.shadowed, this.delegate);
            this.styles.setSuppressSwitch(this.delegate.suppressSwitch);
            this.styles.finishBuilding();
            if (this.abort) {
                return;
            }
            AsyncIncrementalAnalyzeManager.this.sendNewStyles(this.styles);
        }

        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        public boolean handleMessage(Message message) {
            int first;
            int i;
            try {
                this.myRunCount = AsyncIncrementalAnalyzeManager.this.runCount;
                this.delegate.reset();
                switch (message.what) {
                    case AsyncIncrementalAnalyzeManager.MSG_INIT /* 11451401 */:
                        this.shadowed = (Content) message.obj;
                        if (!this.abort && !isInterrupted()) {
                            initialize();
                        }
                        return true;
                    case AsyncIncrementalAnalyzeManager.MSG_MOD /* 11451402 */:
                        if (this.abort || isInterrupted()) {
                            first = 0;
                            i = 0;
                        } else {
                            TextModification textModification = (TextModification) message.obj;
                            first = IntPair.getFirst(textModification.start);
                            int first2 = IntPair.getFirst(textModification.end);
                            if (textModification.changedText == null) {
                                this.shadowed.delete(IntPair.getFirst(textModification.start), IntPair.getSecond(textModification.start), IntPair.getFirst(textModification.end), IntPair.getSecond(textModification.end));
                                S initialState = first == 0 ? AsyncIncrementalAnalyzeManager.this.getInitialState() : this.states.get(first - 1).state;
                                int i2 = first + 1;
                                if (first2 >= i2) {
                                    List<IncrementalAnalyzeManager.LineTokenizeResult<S, T>> listSubList = this.states.subList(i2, first2 + 1);
                                    Iterator<IncrementalAnalyzeManager.LineTokenizeResult<S, T>> it = listSubList.iterator();
                                    while (it.hasNext()) {
                                        AsyncIncrementalAnalyzeManager.this.onAbandonState(it.next().state);
                                    }
                                    listSubList.clear();
                                }
                                Spans.Modifier modifierModify = this.spans.modify();
                                for (int i3 = i2; i3 <= first2; i3++) {
                                    modifierModify.deleteLineAt(i2);
                                }
                                i = first;
                                while (i < this.shadowed.getLineCount()) {
                                    IncrementalAnalyzeManager.LineTokenizeResult<S, T> lineTokenizeResult = AsyncIncrementalAnalyzeManager.this.tokenizeLine(this.shadowed.getLine(i), initialState, i);
                                    modifierModify.setSpansOnLine(i, lineTokenizeResult.spans != null ? lineTokenizeResult.spans : AsyncIncrementalAnalyzeManager.this.generateSpansForLine(lineTokenizeResult));
                                    IncrementalAnalyzeManager.LineTokenizeResult<S, T> lineTokenizeResult2 = this.states.set(i, lineTokenizeResult.clearSpans());
                                    if (lineTokenizeResult2 != null) {
                                        AsyncIncrementalAnalyzeManager.this.onAbandonState(lineTokenizeResult2.state);
                                    }
                                    AsyncIncrementalAnalyzeManager.this.onAddState(lineTokenizeResult.state);
                                    if (!AsyncIncrementalAnalyzeManager.this.stateEquals(lineTokenizeResult2 == null ? null : lineTokenizeResult2.state, lineTokenizeResult.state)) {
                                        initialState = lineTokenizeResult.state;
                                        i++;
                                    }
                                }
                            } else {
                                this.shadowed.insert(IntPair.getFirst(textModification.start), IntPair.getSecond(textModification.start), textModification.changedText);
                                S initialState2 = first == 0 ? AsyncIncrementalAnalyzeManager.this.getInitialState() : this.states.get(first - 1).state;
                                Spans.Modifier modifierModify2 = this.styles.spans.modify();
                                int i4 = first;
                                while (i4 <= first2) {
                                    IncrementalAnalyzeManager.LineTokenizeResult<S, T> lineTokenizeResult3 = AsyncIncrementalAnalyzeManager.this.tokenizeLine(this.shadowed.getLine(i4), initialState2, i4);
                                    if (i4 == first) {
                                        modifierModify2.setSpansOnLine(i4, lineTokenizeResult3.spans != null ? lineTokenizeResult3.spans : AsyncIncrementalAnalyzeManager.this.generateSpansForLine(lineTokenizeResult3));
                                        IncrementalAnalyzeManager.LineTokenizeResult<S, T> lineTokenizeResult4 = this.states.set(i4, lineTokenizeResult3.clearSpans());
                                        if (lineTokenizeResult4 != null) {
                                            AsyncIncrementalAnalyzeManager.this.onAbandonState(lineTokenizeResult4.state);
                                        }
                                    } else {
                                        modifierModify2.addLineAt(i4, lineTokenizeResult3.spans != null ? lineTokenizeResult3.spans : AsyncIncrementalAnalyzeManager.this.generateSpansForLine(lineTokenizeResult3));
                                        this.states.add(i4, lineTokenizeResult3.clearSpans());
                                    }
                                    AsyncIncrementalAnalyzeManager.this.onAddState(lineTokenizeResult3.state);
                                    initialState2 = lineTokenizeResult3.state;
                                    i4++;
                                }
                                i = i4;
                                boolean z = true;
                                while (i < this.shadowed.getLineCount() && z) {
                                    IncrementalAnalyzeManager.LineTokenizeResult<S, T> lineTokenizeResult5 = AsyncIncrementalAnalyzeManager.this.tokenizeLine(this.shadowed.getLine(i), initialState2, i);
                                    if (AsyncIncrementalAnalyzeManager.this.stateEquals(lineTokenizeResult5.state, this.states.get(i).state)) {
                                        z = false;
                                    }
                                    modifierModify2.setSpansOnLine(i, lineTokenizeResult5.spans != null ? lineTokenizeResult5.spans : AsyncIncrementalAnalyzeManager.this.generateSpansForLine(lineTokenizeResult5));
                                    IncrementalAnalyzeManager.LineTokenizeResult<S, T> lineTokenizeResult6 = this.states.set(i, lineTokenizeResult5.clearSpans());
                                    if (lineTokenizeResult6 != null) {
                                        AsyncIncrementalAnalyzeManager.this.onAbandonState(lineTokenizeResult6.state);
                                    }
                                    AsyncIncrementalAnalyzeManager.this.onAddState(lineTokenizeResult5.state);
                                    initialState2 = lineTokenizeResult5.state;
                                    i++;
                                }
                            }
                        }
                        List<CodeBlock> listComputeBlocks = AsyncIncrementalAnalyzeManager.this.computeBlocks(this.shadowed, this.delegate);
                        if (this.delegate.isNotCancelled()) {
                            this.styles.blocks = listComputeBlocks;
                            this.styles.finishBuilding();
                            this.styles.setSuppressSwitch(this.delegate.suppressSwitch);
                        }
                        if (!this.abort) {
                            AsyncIncrementalAnalyzeManager.this.sendUpdate(this.styles, first, i);
                        }
                        return true;
                    default:
                        return true;
                }
            } catch (Exception e) {
                Log.w("AsyncAnalysis", "Thread " + Thread.currentThread().getName() + " failed", e);
                return false;
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (!this.abort && !isInterrupted()) {
                try {
                    Message messageTake = this.messageQueue.take();
                    if (!handleMessage(messageTake)) {
                        return;
                    } else {
                        messageTake.recycle();
                    }
                } catch (InterruptedException unused) {
                    return;
                }
            }
        }
    }
}
