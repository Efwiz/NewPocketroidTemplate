package io.github.rosemoe.sora.widget.layout;

import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractLayout implements Layout {
    protected static final int MIN_LINE_COUNT_FOR_SUBTASK = 3000;
    protected static final int SUBTASK_COUNT = 8;
    protected CodeEditor editor;
    protected Content text;
    protected static final BidiLayoutHelper BidiLayout = BidiLayoutHelper.INSTANCE;
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, Math.max(2, Runtime.getRuntime().availableProcessors()), 1, TimeUnit.MINUTES, new LinkedBlockingQueue(128));

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void afterDelete(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void afterInsert(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
    }

    @Override // io.github.rosemoe.sora.text.LineRemoveListener
    public void onRemove(Content content, ContentLine contentLine) {
    }

    public AbstractLayout(CodeEditor codeEditor, Content content) {
        this.editor = codeEditor;
        this.text = content;
    }

    protected List<Span> getSpans(int i) {
        return this.editor.getSpansForLine(i);
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public void destroyLayout() {
        this.editor = null;
        this.text = null;
    }

    protected void submitTask(LayoutTask<?> layoutTask) {
        executor.submit(layoutTask);
    }

    protected static class TaskMonitor {
        private final Callback callback;
        private final Object[] results;
        private final int taskCount;
        private int completedCount = 0;
        private int cancelledCount = 0;

        public interface Callback {
            void onCompleted(Object[] objArr, int i);
        }

        public TaskMonitor(int i, Callback callback) {
            this.taskCount = i;
            this.results = new Object[i];
            this.callback = callback;
        }

        public synchronized void reportCompleted(Object obj) {
            Object[] objArr = this.results;
            int i = this.completedCount;
            int i2 = i + 1;
            this.completedCount = i2;
            objArr[i] = obj;
            if (i2 == this.taskCount) {
                this.callback.onCompleted(objArr, this.cancelledCount);
            }
        }

        public synchronized void reportCancelled() {
            this.cancelledCount++;
            reportCompleted(null);
        }
    }

    protected abstract class LayoutTask<T> implements Runnable {
        private final TaskMonitor monitor;

        protected abstract T compute();

        protected LayoutTask(TaskMonitor taskMonitor) {
            this.monitor = taskMonitor;
        }

        protected boolean shouldRun() {
            return AbstractLayout.this.editor != null;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (shouldRun()) {
                this.monitor.reportCompleted(compute());
            } else {
                this.monitor.reportCancelled();
            }
        }
    }
}
