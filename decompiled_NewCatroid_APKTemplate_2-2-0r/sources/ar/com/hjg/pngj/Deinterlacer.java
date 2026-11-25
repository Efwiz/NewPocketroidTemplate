package ar.com.hjg.pngj;

/* JADX INFO: loaded from: classes.dex */
public class Deinterlacer {
    private int cols;
    int dX;
    int dXsamples;
    int dY;
    final ImageInfo imi;
    int oX;
    int oXsamples;
    int oY;
    private int rows;
    private int currRowSubimg = -1;
    private int currRowReal = -1;
    int totalRows = 0;
    private boolean ended = false;
    private int pass = 0;
    private int currRowSeq = 0;

    public Deinterlacer(ImageInfo imageInfo) {
        this.imi = imageInfo;
        setPass(1);
        setRow(0);
    }

    private void setRow(int i) {
        this.currRowSubimg = i;
        int i2 = (i * this.dY) + this.oY;
        this.currRowReal = i2;
        if (i2 < 0 || i2 >= this.imi.rows) {
            throw new PngjExceptionInternal("bad row - this should not happen");
        }
    }

    boolean nextRow() {
        int i;
        this.currRowSeq++;
        int i2 = this.rows;
        if (i2 == 0 || (i = this.currRowSubimg) >= i2 - 1) {
            int i3 = this.pass;
            if (i3 == 7) {
                this.ended = true;
                return false;
            }
            setPass(i3 + 1);
            if (this.rows == 0) {
                this.currRowSeq--;
                return nextRow();
            }
            setRow(0);
        } else {
            setRow(i + 1);
        }
        return true;
    }

    boolean isEnded() {
        return this.ended;
    }

    void setPass(int i) {
        int i2;
        int i3;
        if (this.pass == i) {
            return;
        }
        this.pass = i;
        byte[] bArrParamsForPass = paramsForPass(i);
        this.dX = bArrParamsForPass[0];
        this.dY = bArrParamsForPass[1];
        this.oX = bArrParamsForPass[2];
        this.oY = bArrParamsForPass[3];
        if (this.imi.rows > this.oY) {
            int i4 = this.imi.rows;
            int i5 = this.dY;
            i2 = (((i4 + i5) - 1) - this.oY) / i5;
        } else {
            i2 = 0;
        }
        this.rows = i2;
        if (this.imi.cols > this.oX) {
            int i6 = this.imi.cols;
            int i7 = this.dX;
            i3 = (((i6 + i7) - 1) - this.oX) / i7;
        } else {
            i3 = 0;
        }
        this.cols = i3;
        if (i3 == 0) {
            this.rows = 0;
        }
        this.dXsamples = this.dX * this.imi.channels;
        this.oXsamples = this.oX * this.imi.channels;
    }

    static byte[] paramsForPass(int i) {
        switch (i) {
            case 1:
                return new byte[]{8, 8, 0, 0};
            case 2:
                return new byte[]{8, 8, 4, 0};
            case 3:
                return new byte[]{4, 8, 0, 4};
            case 4:
                return new byte[]{4, 4, 2, 0};
            case 5:
                return new byte[]{2, 4, 0, 2};
            case 6:
                return new byte[]{2, 2, 1, 0};
            case 7:
                return new byte[]{1, 2, 0, 1};
            default:
                throw new PngjExceptionInternal("bad interlace pass" + i);
        }
    }

    int getCurrRowSubimg() {
        return this.currRowSubimg;
    }

    int getCurrRowReal() {
        return this.currRowReal;
    }

    int getPass() {
        return this.pass;
    }

    int getRows() {
        return this.rows;
    }

    int getCols() {
        return this.cols;
    }

    public int getPixelsToRead() {
        return getCols();
    }

    public int getBytesToRead() {
        return ((this.imi.bitspPixel * getPixelsToRead()) + 7) / 8;
    }

    public int getdY() {
        return this.dY;
    }

    public int getdX() {
        return this.dX;
    }

    public int getoY() {
        return this.oY;
    }

    public int getoX() {
        return this.oX;
    }

    public int getTotalRows() {
        int i;
        if (this.totalRows == 0) {
            for (int i2 = 1; i2 <= 7; i2++) {
                byte[] bArrParamsForPass = paramsForPass(i2);
                int i3 = 0;
                if (this.imi.rows > bArrParamsForPass[3]) {
                    int i4 = this.imi.rows;
                    byte b = bArrParamsForPass[1];
                    i = (((i4 + b) - 1) - bArrParamsForPass[3]) / b;
                } else {
                    i = 0;
                }
                if (this.imi.cols > bArrParamsForPass[2]) {
                    int i5 = this.imi.cols;
                    byte b2 = bArrParamsForPass[0];
                    i3 = (((i5 + b2) - 1) - bArrParamsForPass[2]) / b2;
                }
                if (i > 0 && i3 > 0) {
                    this.totalRows += i;
                }
            }
        }
        return this.totalRows;
    }

    public long getTotalRawBytes() {
        int i;
        long j = 0;
        for (int i2 = 1; i2 <= 7; i2++) {
            byte[] bArrParamsForPass = paramsForPass(i2);
            int i3 = 0;
            if (this.imi.rows > bArrParamsForPass[3]) {
                int i4 = this.imi.rows;
                byte b = bArrParamsForPass[1];
                i = (((i4 + b) - 1) - bArrParamsForPass[3]) / b;
            } else {
                i = 0;
            }
            if (this.imi.cols > bArrParamsForPass[2]) {
                int i5 = this.imi.cols;
                byte b2 = bArrParamsForPass[0];
                i3 = (((i5 + b2) - 1) - bArrParamsForPass[2]) / b2;
            }
            int i6 = ((this.imi.bitspPixel * i3) + 7) / 8;
            if (i > 0 && i3 > 0) {
                j += ((long) i) * (((long) i6) + 1);
            }
        }
        return j;
    }

    public int getCurrRowSeq() {
        return this.currRowSeq;
    }
}
