package org.eclipse.jdt.internal.compiler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;

/* JADX INFO: loaded from: classes14.dex */
public class ReadManager implements Runnable {
    static final int CACHE_SIZE = 15;
    static final int START_CUSHION = 5;
    public static final int THRESHOLD = 10;
    private Throwable caughtException;
    char[][] contentsRead;
    ICompilationUnit[] filesRead;
    int nextAvailablePosition;
    int nextFileToRead;
    char[] readInProcessMarker = new char[0];
    Thread[] readingThreads;
    int readyToReadPosition;
    int sleepingThreadCount;
    ICompilationUnit[] units;

    public ReadManager(ICompilationUnit[] iCompilationUnitArr, int i) {
        int iIntValue;
        try {
            Method declaredMethod = Class.forName("java.lang.Runtime").getDeclaredMethod("availableProcessors", new Class[0]);
            if (declaredMethod == null || (iIntValue = ((Integer) declaredMethod.invoke(Runtime.getRuntime(), null)).intValue() + 1) < 2) {
                iIntValue = 0;
            } else if (iIntValue > 15) {
                iIntValue = 15;
            }
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
        }
        if (iIntValue > 0) {
            synchronized (this) {
                ICompilationUnit[] iCompilationUnitArr2 = new ICompilationUnit[i];
                this.units = iCompilationUnitArr2;
                System.arraycopy(iCompilationUnitArr, 0, iCompilationUnitArr2, 0, i);
                this.nextFileToRead = 5;
                this.filesRead = new ICompilationUnit[15];
                this.contentsRead = new char[15][];
                this.readyToReadPosition = 0;
                this.nextAvailablePosition = 0;
                this.sleepingThreadCount = 0;
                this.readingThreads = new Thread[iIntValue];
                while (true) {
                    iIntValue--;
                    if (iIntValue >= 0) {
                        this.readingThreads[iIntValue] = new Thread(this, "Compiler Source File Reader");
                        this.readingThreads[iIntValue].setDaemon(true);
                        this.readingThreads[iIntValue].start();
                    }
                }
            }
        }
    }

    public char[] getContents(ICompilationUnit iCompilationUnit) throws Error {
        char[] cArr;
        boolean z;
        if (this.readingThreads == null || this.units.length == 0) {
            Throwable th = this.caughtException;
            if (th != null) {
                if (th instanceof Error) {
                    throw ((Error) th);
                }
                throw ((RuntimeException) th);
            }
            return iCompilationUnit.getContents();
        }
        synchronized (this) {
            ICompilationUnit[] iCompilationUnitArr = this.filesRead;
            int i = this.readyToReadPosition;
            cArr = null;
            z = false;
            if (iCompilationUnit == iCompilationUnitArr[i]) {
                char[] cArr2 = this.contentsRead[i];
                while (true) {
                    if (cArr2 == this.readInProcessMarker || cArr2 == null) {
                        this.contentsRead[this.readyToReadPosition] = null;
                        try {
                            wait(250L);
                        } catch (InterruptedException unused) {
                        }
                        Throwable th2 = this.caughtException;
                        if (th2 != null) {
                            if (th2 instanceof Error) {
                                throw ((Error) th2);
                            }
                            throw ((RuntimeException) th2);
                        }
                        cArr2 = this.contentsRead[this.readyToReadPosition];
                    } else {
                        ICompilationUnit[] iCompilationUnitArr2 = this.filesRead;
                        int i2 = this.readyToReadPosition;
                        iCompilationUnitArr2[i2] = null;
                        char[][] cArr3 = this.contentsRead;
                        cArr3[i2] = null;
                        int i3 = i2 + 1;
                        this.readyToReadPosition = i3;
                        if (i3 >= cArr3.length) {
                            this.readyToReadPosition = 0;
                        }
                        if (this.sleepingThreadCount > 0) {
                            notify();
                            if (this.sleepingThreadCount == this.readingThreads.length) {
                                z = true;
                            }
                        }
                        cArr = cArr2;
                        break;
                    }
                }
            } else {
                int length = this.units.length;
                int i4 = 0;
                while (i4 < length && this.units[i4] != iCompilationUnit) {
                    i4++;
                }
                if (i4 == this.units.length) {
                    this.units = new ICompilationUnit[0];
                } else if (i4 >= this.nextFileToRead) {
                    this.nextFileToRead = i4 + 5;
                    this.readyToReadPosition = 0;
                    this.nextAvailablePosition = 0;
                    this.filesRead = new ICompilationUnit[15];
                    this.contentsRead = new char[15][];
                    notifyAll();
                }
            }
        }
        if (z) {
            Thread.yield();
        }
        return cArr != null ? cArr : iCompilationUnit.getContents();
    }

    @Override // java.lang.Runnable
    public void run() {
        ICompilationUnit[] iCompilationUnitArr;
        int i;
        ICompilationUnit iCompilationUnit;
        while (this.readingThreads != null && this.nextFileToRead < this.units.length) {
            try {
                synchronized (this) {
                    if (this.readingThreads == null) {
                        return;
                    }
                    while (true) {
                        iCompilationUnitArr = this.filesRead;
                        i = this.nextAvailablePosition;
                        if (iCompilationUnitArr[i] == null) {
                            break;
                        }
                        this.sleepingThreadCount++;
                        try {
                            wait(250L);
                        } catch (InterruptedException unused) {
                        }
                        this.sleepingThreadCount--;
                        if (this.readingThreads == null) {
                            return;
                        }
                    }
                    int i2 = this.nextFileToRead;
                    ICompilationUnit[] iCompilationUnitArr2 = this.units;
                    if (i2 >= iCompilationUnitArr2.length) {
                        return;
                    }
                    this.nextFileToRead = i2 + 1;
                    iCompilationUnit = iCompilationUnitArr2[i2];
                    int i3 = i + 1;
                    this.nextAvailablePosition = i3;
                    char[][] cArr = this.contentsRead;
                    if (i3 >= cArr.length) {
                        this.nextAvailablePosition = 0;
                    }
                    iCompilationUnitArr[i] = iCompilationUnit;
                    cArr[i] = this.readInProcessMarker;
                }
                char[] contents = iCompilationUnit.getContents();
                synchronized (this) {
                    if (this.filesRead[i] == iCompilationUnit) {
                        if (this.contentsRead[i] == null) {
                            notifyAll();
                        }
                        this.contentsRead[i] = contents;
                    }
                }
            } catch (Error e) {
                synchronized (this) {
                    this.caughtException = e;
                    shutdown();
                    return;
                }
            } catch (RuntimeException e2) {
                synchronized (this) {
                    this.caughtException = e2;
                    shutdown();
                    return;
                }
            }
        }
    }

    public synchronized void shutdown() {
        this.readingThreads = null;
        notifyAll();
    }
}
