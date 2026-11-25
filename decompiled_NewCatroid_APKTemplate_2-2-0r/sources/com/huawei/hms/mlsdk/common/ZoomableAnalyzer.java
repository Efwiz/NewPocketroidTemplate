package com.huawei.hms.mlsdk.common;

import android.os.SystemClock;
import android.util.SparseArray;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ZoomableAnalyzer<T> extends MLAnalyzer<T> {
    public long a = 0;
    public ZoomableAnalyzer<T>.ZoomContext b = new ZoomContext(this);

    public final class ZoomContext {
        public int a = 0;
        public int b = 0;
        public int c = 0;
        public boolean d = false;

        public ZoomContext(ZoomableAnalyzer zoomableAnalyzer) {
        }

        public int getLastZoom() {
            return this.b;
        }

        public int getMaxZoom() {
            return this.c;
        }

        public int getOriginZoom() {
            return this.a;
        }

        public boolean isZooming() {
            return this.d;
        }

        public void setLastZoom(int i) {
            this.b = i;
        }

        public void setMaxZoom(int i) {
            this.c = i;
        }

        public void setOriginZoom(int i) {
            this.a = i;
        }

        public void setZooming(boolean z) {
            this.d = z;
        }
    }

    public boolean accept(MLFrame mLFrame) {
        boolean z;
        if (mLFrame == null) {
            return false;
        }
        MLFrame.Property.Ext framePropertyExt = getFramePropertyExt(mLFrame);
        int zoom = framePropertyExt == null ? 0 : framePropertyExt.getZoom();
        synchronized (this.b) {
            if (this.b.isZooming()) {
                if (SystemClock.elapsedRealtime() - this.a > getZoomAcceptTimeout()) {
                    endZoom();
                }
                z = zoom != this.b.getLastZoom();
            }
            if (z) {
                if (!this.b.isZooming()) {
                    this.b.setOriginZoom(zoom);
                    this.b.setLastZoom(zoom);
                    this.a = SystemClock.elapsedRealtime();
                }
                ZoomableAnalyzer<T>.ZoomContext zoomContext = this.b;
                int maxZoom = getFramePropertyExt(mLFrame) != null ? getFramePropertyExt(mLFrame).getMaxZoom() : 0;
                if (maxZoom == 0 && this.b.getMaxZoom() > 0) {
                    maxZoom = this.b.getMaxZoom();
                }
                if (maxZoom > 0) {
                    this.b.setMaxZoom(maxZoom);
                }
                zoomContext.setMaxZoom(maxZoom);
            }
        }
        return z;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public void destroy() {
        super.destroy();
    }

    public abstract SparseArray<T> detect(MLFrame mLFrame);

    public void endZoom() {
        synchronized (this.b) {
            if (this.b.isZooming()) {
                postMonitorEvent(new a(this.b.getOriginZoom()));
                this.b.setZooming(false);
            }
        }
    }

    public int getZoomAcceptTimeout() {
        return 5000;
    }

    public boolean isZooming() {
        boolean zIsZooming;
        synchronized (this.b) {
            zIsZooming = this.b.isZooming();
        }
        return zIsZooming;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0041  */
    /* JADX WARN: Code duplicated, block: B:20:0x0042  */
    /* JADX WARN: Code duplicated, block: B:23:0x004b A[Catch: all -> 0x005b, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000b, B:8:0x0017, B:12:0x0026, B:16:0x003a, B:21:0x0043, B:23:0x004b, B:24:0x0051, B:25:0x0059, B:15:0x0034, B:7:0x0012), top: B:30:0x0003 }] */
    public void startZoom(double d, int i) {
        int i2;
        int lastZoom;
        synchronized (this.b) {
            if (this.b.isZooming()) {
                this.b.setLastZoom(i);
            } else {
                this.b.setZooming(true);
            }
            int maxZoom = 0;
            if (this.b.getLastZoom() != 0 || d <= 1.0d) {
                if (d > 0.0d) {
                    lastZoom = this.b.getLastZoom();
                } else {
                    i2 = 0;
                }
                if (i2 < 0) {
                    maxZoom = i2;
                }
                if (maxZoom > this.b.getMaxZoom()) {
                    maxZoom = this.b.getMaxZoom();
                }
                postMonitorEvent(new a(maxZoom));
            } else {
                d -= 1.0d;
                lastZoom = this.b.getMaxZoom();
            }
            i2 = (int) (d * ((double) lastZoom));
            if (i2 < 0) {
                maxZoom = i2;
            }
            if (maxZoom > this.b.getMaxZoom()) {
                maxZoom = this.b.getMaxZoom();
            }
            postMonitorEvent(new a(maxZoom));
        }
    }
}
