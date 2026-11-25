package org.eclipse.jdt.internal.compiler;

import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;

/* JADX INFO: loaded from: classes14.dex */
public class ProcessTaskManager implements Runnable {
    public static final int PROCESSED_QUEUE_SIZE = 12;
    private Throwable caughtException;
    Compiler compiler;
    private Thread processingThread;
    private int unitIndex;
    CompilationUnitDeclaration unitToProcess;
    volatile int currentIndex = 0;
    volatile int availableIndex = 0;
    volatile int size = 12;
    volatile int sleepCount = 0;
    CompilationUnitDeclaration[] units = new CompilationUnitDeclaration[this.size];

    public ProcessTaskManager(Compiler compiler, int i) {
        this.compiler = compiler;
        this.unitIndex = i;
        synchronized (this) {
            Thread thread = new Thread(this, "Compiler Processing Task");
            this.processingThread = thread;
            thread.setDaemon(true);
            this.processingThread.start();
        }
    }

    private synchronized void addNextUnit(CompilationUnitDeclaration compilationUnitDeclaration) {
        while (this.units[this.availableIndex] != null) {
            this.sleepCount = 1;
            try {
                wait(250L);
            } catch (InterruptedException unused) {
            }
            this.sleepCount = 0;
        }
        CompilationUnitDeclaration[] compilationUnitDeclarationArr = this.units;
        int i = this.availableIndex;
        this.availableIndex = i + 1;
        compilationUnitDeclarationArr[i] = compilationUnitDeclaration;
        if (this.availableIndex >= this.size) {
            this.availableIndex = 0;
        }
        if (this.sleepCount <= -1) {
            notify();
        }
    }

    public CompilationUnitDeclaration removeNextUnit() throws Error {
        synchronized (this) {
            CompilationUnitDeclaration compilationUnitDeclaration = this.units[this.currentIndex];
            boolean z = false;
            if (compilationUnitDeclaration == null || this.caughtException != null) {
                do {
                    if (this.processingThread == null) {
                        Throwable th = this.caughtException;
                        if (th == null) {
                            return null;
                        }
                        if (th instanceof Error) {
                            throw ((Error) th);
                        }
                        throw ((RuntimeException) th);
                    }
                    this.sleepCount = -1;
                    try {
                        wait(100L);
                    } catch (InterruptedException unused) {
                    }
                    this.sleepCount = 0;
                    compilationUnitDeclaration = this.units[this.currentIndex];
                } while (compilationUnitDeclaration == null);
            }
            CompilationUnitDeclaration[] compilationUnitDeclarationArr = this.units;
            int i = this.currentIndex;
            this.currentIndex = i + 1;
            compilationUnitDeclarationArr[i] = null;
            if (this.currentIndex >= this.size) {
                this.currentIndex = 0;
            }
            if (this.sleepCount >= 1) {
                int i2 = this.sleepCount + 1;
                this.sleepCount = i2;
                if (i2 > 4) {
                    notify();
                    if (this.sleepCount > 8) {
                        z = true;
                    }
                }
            }
            if (z) {
                Thread.yield();
            }
            return compilationUnitDeclaration;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0048, code lost:
    
        r11.compiler.reportProgress(org.eclipse.jdt.internal.compiler.util.Messages.bind(org.eclipse.jdt.internal.compiler.util.Messages.compilation_processing, new java.lang.String(r11.unitToProcess.getFileName())));
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0064, code lost:
    
        if (r11.compiler.options.verbose == false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0066, code lost:
    
        r11.compiler.out.println(org.eclipse.jdt.internal.compiler.util.Messages.bind(org.eclipse.jdt.internal.compiler.util.Messages.compilation_process, (java.lang.Object[]) new java.lang.String[]{java.lang.String.valueOf(r7), java.lang.String.valueOf(r11.compiler.totalUnits), new java.lang.String(r11.unitToProcess.getFileName())}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0094, code lost:
    
        r11.compiler.process(r11.unitToProcess, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x009b, code lost:
    
        r5 = r11.unitToProcess;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009d, code lost:
    
        if (r5 == null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x009f, code lost:
    
        if (r4 == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a1, code lost:
    
        r5.cleanUp();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a4, code lost:
    
        addNextUnit(r11.unitToProcess);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ab, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ac, code lost:
    
        r1 = r11.unitToProcess;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ae, code lost:
    
        if (r1 != null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b2, code lost:
    
        r1.cleanUp();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b5, code lost:
    
        throw r0;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() {
        /*
            Method dump skipped, instruction units count: 207
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.ProcessTaskManager.run():void");
    }

    public void shutdown() {
        Thread thread;
        try {
            synchronized (this) {
                thread = this.processingThread;
                if (thread != null) {
                    this.processingThread = null;
                    notifyAll();
                } else {
                    thread = null;
                }
            }
            if (thread != null) {
                thread.join(250L);
            }
        } catch (InterruptedException unused) {
        }
    }
}
