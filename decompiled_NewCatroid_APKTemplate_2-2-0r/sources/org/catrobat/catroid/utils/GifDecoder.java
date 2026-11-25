package org.catrobat.catroid.utils;

import androidx.core.view.ViewCompat;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.io.InputStream;
import java.util.Vector;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/GifDecoder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GifDecoder {
    private int[] act;
    private int bgIndex;
    private int gceDelay;
    private int[] gct;
    private boolean gctFlag;
    private int gctSize;
    private int height;
    private int ih;
    private InputStream in;
    private int iw;
    private int ix;
    private int iy;
    private int[] lct;
    private boolean lctFlag;
    private int lctSize;
    private int lsdBgColor;
    private int lsdHeight;
    private int lsdWidth;
    private int pixelAspect;
    private byte[] pixels;
    private int status;
    private boolean transColor;
    private int transIndex;
    private int width;
    private int blockLength = 0;
    private final byte[] block = new byte[256];
    private final short[] prefix = new short[4096];
    private final byte[] suffix = new byte[4096];
    private final byte[] pixelStack = new byte[4097];
    private final Vector<GifFrame> frames = new Vector<>();
    private int dispose = 0;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/GifDecoder$GifFrame.class
      classes14.dex
     */
    private static class GifFrame {
        public int delay;
        public Pixmap image;

        public GifFrame(Pixmap im, int del) {
            this.image = im;
            this.delay = del;
        }
    }

    public static Animation<TextureRegion> loadGIFAnimation(Animation.PlayMode playMode, InputStream is) {
        GifDecoder gifDecoder = new GifDecoder();
        if (gifDecoder.read(is) != 0) {
            return null;
        }
        Array array = new Array();
        int frameCount = gifDecoder.getFrameCount();
        float delay = 0.0f;
        for (int i = 0; i < frameCount; i++) {
            Pixmap frame = gifDecoder.getFrame(i);
            if (frame != null) {
                array.add(new TextureRegion(new Texture(frame)));
                delay += gifDecoder.getDelay(i) / 1000.0f;
                frame.dispose();
            }
        }
        float f = delay / frameCount;
        if (f <= 0.0f) {
            f = 0.1f;
        }
        Animation<TextureRegion> animation = new Animation<>(f, (Array<? extends TextureRegion>) array);
        animation.setPlayMode(playMode);
        return animation;
    }

    public int getDelay(int n) {
        this.gceDelay = -1;
        if (n >= 0 && n < this.frames.size()) {
            this.gceDelay = this.frames.elementAt(n).delay;
        }
        return this.gceDelay;
    }

    public int getFrameCount() {
        return this.frames.size();
    }

    public Pixmap getFrame(int n) {
        if (this.frames.size() <= 0) {
            return null;
        }
        return this.frames.elementAt(n).image;
    }

    public int read(InputStream is) {
        init();
        if (is != null) {
            this.in = is;
            readHeader();
            if (!err()) {
                readContents();
                if (getFrameCount() < 0) {
                    this.status = 1;
                }
            }
        } else {
            this.status = 1;
        }
        try {
            is.close();
        } catch (Exception unused) {
        }
        return this.status;
    }

    private void init() {
        this.status = 0;
        this.frames.clear();
        this.gct = null;
        this.lct = null;
    }

    private boolean err() {
        return this.status != 0;
    }

    private void readHeader() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((char) readByte());
        }
        if (!sb.toString().startsWith("GIF")) {
            this.status = 1;
            return;
        }
        readLSD();
        if (!this.gctFlag || err()) {
            return;
        }
        this.gct = readColorTable(this.gctSize);
    }

    private void readLSD() {
        this.lsdWidth = readShort();
        this.lsdHeight = readShort();
        int i = readByte();
        this.gctFlag = (i & 128) != 0;
        this.gctSize = 2 << (i & 7);
        this.bgIndex = readByte();
        this.pixelAspect = readByte();
        this.width = this.lsdWidth;
        this.height = this.lsdHeight;
    }

    private int readByte() {
        try {
            return this.in.read();
        } catch (Exception unused) {
            this.status = 1;
            return 0;
        }
    }

    private int readShort() {
        return readByte() | (readByte() << 8);
    }

    private int[] readColorTable(int ncolors) {
        int i;
        int i2 = ncolors * 3;
        byte[] bArr = new byte[i2];
        try {
            i = this.in.read(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        if (i < i2) {
            this.status = 1;
            return null;
        }
        int[] iArr = new int[256];
        int i3 = 0;
        for (int i4 = 0; i4 < ncolors; i4++) {
            int i5 = bArr[i3] & 255;
            int i6 = i3 + 2;
            int i7 = bArr[i3 + 1] & 255;
            i3 += 3;
            iArr[i4] = (i7 << 8) | (i5 << 16) | ViewCompat.MEASURED_STATE_MASK | (bArr[i6] & 255);
        }
        return iArr;
    }

    private void readContents() {
        boolean z = false;
        while (!z && !err()) {
            int i = readByte();
            if (i != 33) {
                if (i == 44) {
                    readImage();
                } else if (i != 59) {
                    this.status = 1;
                } else {
                    z = true;
                }
            } else if (readByte() == 249) {
                readGCE();
            } else {
                skip();
            }
        }
    }

    private void readGCE() {
        readByte();
        int i = readByte();
        this.dispose = (i & 28) >> 2;
        this.transColor = (i & 1) != 0;
        this.gceDelay = readShort() * 10;
        this.transIndex = readByte();
        readByte();
    }

    private void readImage() {
        this.ix = readShort();
        this.iy = readShort();
        this.iw = readShort();
        this.ih = readShort();
        int i = readByte();
        int i2 = 0;
        boolean z = (i & 128) != 0;
        this.lctFlag = z;
        int i3 = 2 << (i & 7);
        this.lctSize = i3;
        if (z) {
            int[] colorTable = readColorTable(i3);
            this.lct = colorTable;
            this.act = colorTable;
        } else {
            this.act = this.gct;
        }
        if (this.transColor) {
            int[] iArr = this.act;
            int i4 = this.transIndex;
            int i5 = iArr[i4];
            iArr[i4] = 0;
            i2 = i5;
        }
        if (this.act == null) {
            this.status = 1;
        }
        if (err()) {
            return;
        }
        decodeImageData();
        skip();
        if (err()) {
            return;
        }
        setPixmapFrame();
        if (this.transColor) {
            this.act[this.transIndex] = i2;
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 2951. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    private void decodeImageData() {
        /*
            Method dump skipped, instruction units count: 295
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.utils.GifDecoder.decodeImageData():void");
    }

    private int readBlock() {
        int i;
        int i2 = readByte();
        this.blockLength = i2;
        int i3 = 0;
        if (i2 > 0) {
            while (true) {
                try {
                    int i4 = this.blockLength;
                    if (i3 >= i4 || (i = this.in.read(this.block, i3, i4 - i3)) == -1) {
                        break;
                    }
                    i3 += i;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (i3 < this.blockLength) {
                this.status = 1;
            }
        }
        return i3;
    }

    private void skip() {
        do {
            readBlock();
            if (this.blockLength <= 0) {
                return;
            }
        } while (!err());
    }

    private void setPixmapFrame() {
        Pixmap pixmap = new Pixmap(this.width, this.height, Pixmap.Format.RGBA8888);
        if (this.frames.size() > 0) {
            Pixmap.Blending blending = pixmap.getBlending();
            pixmap.setBlending(Pixmap.Blending.None);
            Vector<GifFrame> vector = this.frames;
            Pixmap pixmap2 = vector.elementAt(vector.size() - 1).image;
            if (pixmap2 != null) {
                pixmap.drawPixmap(pixmap2, 0, 0);
            }
            pixmap.setBlending(blending);
        }
        int i = this.ix;
        int i2 = this.iy;
        int i3 = this.iw;
        int i4 = this.ih;
        int i5 = 0;
        for (int i6 = 0; i6 < i4; i6++) {
            int i7 = 0;
            while (i7 < i3) {
                int i8 = i5 + 1;
                int i9 = this.pixels[i5] & 255;
                if (!this.transColor || i9 != this.transIndex) {
                    int i10 = this.act[i9];
                    pixmap.setColor(((i10 >> 16) & 255) / 255.0f, ((i10 >> 8) & 255) / 255.0f, (i10 & 255) / 255.0f, ((i10 >> 24) & 255) / 255.0f);
                    pixmap.drawPixel(i + i7, i2 + i6);
                }
                i7++;
                i5 = i8;
            }
        }
        this.frames.addElement(new GifFrame(pixmap, this.gceDelay));
    }
}
