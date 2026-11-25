package org.jetbrains.kotlin.com.intellij.util.io;

/* JADX INFO: loaded from: classes3.dex */
class PagedFileStorageCache {
    private DirectBufferWrapper myLastBuffer;
    private DirectBufferWrapper myLastBuffer2;
    private DirectBufferWrapper myLastBuffer3;
    private int myLastChangeCount;
    private int myLastChangeCount2;
    private int myLastChangeCount3;
    private int myLastPage = -1;
    private int myLastPage2 = -1;
    private int myLastPage3 = -1;

    PagedFileStorageCache() {
    }

    synchronized void clear() {
        this.myLastPage = -1;
        this.myLastPage2 = -1;
        this.myLastPage3 = -1;
        this.myLastBuffer = null;
        this.myLastBuffer2 = null;
        this.myLastBuffer3 = null;
    }

    synchronized DirectBufferWrapper getPageFromCache(long j, int i) {
        if (this.myLastPage == j) {
            if (!this.myLastBuffer.isReleased() && this.myLastChangeCount == i) {
                return this.myLastBuffer;
            }
        } else if (this.myLastPage2 == j) {
            if (!this.myLastBuffer2.isReleased() && this.myLastChangeCount2 == i) {
                return this.myLastBuffer2;
            }
        } else if (this.myLastPage3 == j && !this.myLastBuffer3.isReleased() && this.myLastChangeCount3 == i) {
            return this.myLastBuffer3;
        }
        return null;
    }

    synchronized void updateCache(long j, DirectBufferWrapper directBufferWrapper, int i) {
        int i2 = this.myLastPage;
        if (i2 != j) {
            this.myLastPage3 = this.myLastPage2;
            this.myLastBuffer3 = this.myLastBuffer2;
            this.myLastChangeCount3 = this.myLastChangeCount2;
            this.myLastPage2 = i2;
            this.myLastBuffer2 = this.myLastBuffer;
            this.myLastChangeCount2 = this.myLastChangeCount;
            this.myLastBuffer = directBufferWrapper;
            this.myLastPage = (int) j;
        } else {
            this.myLastBuffer = directBufferWrapper;
        }
        this.myLastChangeCount = i;
    }
}
