package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.OrderedMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PixmapPacker implements Disposable {
    static Pattern indexPattern = Pattern.compile("(.+)_(\\d+)$");
    int alphaThreshold;
    private Color c;
    boolean disposed;
    boolean duplicateBorder;
    PackStrategy packStrategy;
    boolean packToTexture;
    int padding;
    Pixmap.Format pageFormat;
    int pageHeight;
    int pageWidth;
    final Array<Page> pages;
    boolean stripWhitespaceX;
    boolean stripWhitespaceY;
    Color transparentColor;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker$PackStrategy.class
      classes8.dex
     */
    public interface PackStrategy {
        Page pack(PixmapPacker pixmapPacker, String str, Rectangle rectangle);

        void sort(Array<Pixmap> array);
    }

    public PixmapPacker(int i, int i2, Pixmap.Format format, int i3, boolean z) {
        this(i, i2, format, i3, z, false, false, new GuillotineStrategy());
    }

    public PixmapPacker(int i, int i2, Pixmap.Format format, int i3, boolean z, PackStrategy packStrategy) {
        this(i, i2, format, i3, z, false, false, packStrategy);
    }

    public PixmapPacker(int i, int i2, Pixmap.Format format, int i3, boolean z, boolean z2, boolean z3, PackStrategy packStrategy) {
        this.transparentColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        this.pages = new Array<>();
        this.c = new Color();
        this.pageWidth = i;
        this.pageHeight = i2;
        this.pageFormat = format;
        this.padding = i3;
        this.duplicateBorder = z;
        this.stripWhitespaceX = z2;
        this.stripWhitespaceY = z3;
        this.packStrategy = packStrategy;
    }

    public void sort(Array<Pixmap> array) {
        this.packStrategy.sort(array);
    }

    public synchronized PixmapPackerRectangle pack(Pixmap pixmap) {
        return pack(null, pixmap);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x02ab A[Catch: all -> 0x02d5, TryCatch #0 {, blocks: (B:4:0x0009, B:10:0x0012, B:13:0x0019, B:14:0x002b, B:17:0x0030, B:19:0x0038, B:78:0x017b, B:80:0x0186, B:83:0x0193, B:85:0x019b, B:86:0x01a5, B:88:0x01b5, B:90:0x01b9, B:92:0x01bd, B:94:0x01c1, B:96:0x01e6, B:98:0x01ef, B:100:0x02ab, B:101:0x02ae, B:95:0x01e4, B:105:0x02b4, B:106:0x02bb, B:107:0x02bc, B:108:0x02d4, B:20:0x0093, B:22:0x0097, B:25:0x009c, B:26:0x00ac, B:29:0x00be, B:32:0x00c5, B:34:0x00cb, B:37:0x00d6, B:38:0x00d9, B:39:0x00de, B:40:0x00e2, B:43:0x00e7, B:45:0x00ed, B:48:0x00f8, B:49:0x00fb, B:52:0x0103, B:55:0x010c, B:59:0x0116, B:62:0x0121, B:63:0x0124, B:64:0x0129, B:65:0x012d, B:69:0x0135, B:72:0x0140, B:73:0x0143, B:76:0x014b), top: B:112:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:105:0x02b4 A[Catch: all -> 0x02d5, TRY_ENTER, TryCatch #0 {, blocks: (B:4:0x0009, B:10:0x0012, B:13:0x0019, B:14:0x002b, B:17:0x0030, B:19:0x0038, B:78:0x017b, B:80:0x0186, B:83:0x0193, B:85:0x019b, B:86:0x01a5, B:88:0x01b5, B:90:0x01b9, B:92:0x01bd, B:94:0x01c1, B:96:0x01e6, B:98:0x01ef, B:100:0x02ab, B:101:0x02ae, B:95:0x01e4, B:105:0x02b4, B:106:0x02bb, B:107:0x02bc, B:108:0x02d4, B:20:0x0093, B:22:0x0097, B:25:0x009c, B:26:0x00ac, B:29:0x00be, B:32:0x00c5, B:34:0x00cb, B:37:0x00d6, B:38:0x00d9, B:39:0x00de, B:40:0x00e2, B:43:0x00e7, B:45:0x00ed, B:48:0x00f8, B:49:0x00fb, B:52:0x0103, B:55:0x010c, B:59:0x0116, B:62:0x0121, B:63:0x0124, B:64:0x0129, B:65:0x012d, B:69:0x0135, B:72:0x0140, B:73:0x0143, B:76:0x014b), top: B:112:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:107:0x02bc A[Catch: all -> 0x02d5, TryCatch #0 {, blocks: (B:4:0x0009, B:10:0x0012, B:13:0x0019, B:14:0x002b, B:17:0x0030, B:19:0x0038, B:78:0x017b, B:80:0x0186, B:83:0x0193, B:85:0x019b, B:86:0x01a5, B:88:0x01b5, B:90:0x01b9, B:92:0x01bd, B:94:0x01c1, B:96:0x01e6, B:98:0x01ef, B:100:0x02ab, B:101:0x02ae, B:95:0x01e4, B:105:0x02b4, B:106:0x02bb, B:107:0x02bc, B:108:0x02d4, B:20:0x0093, B:22:0x0097, B:25:0x009c, B:26:0x00ac, B:29:0x00be, B:32:0x00c5, B:34:0x00cb, B:37:0x00d6, B:38:0x00d9, B:39:0x00de, B:40:0x00e2, B:43:0x00e7, B:45:0x00ed, B:48:0x00f8, B:49:0x00fb, B:52:0x0103, B:55:0x010c, B:59:0x0116, B:62:0x0121, B:63:0x0124, B:64:0x0129, B:65:0x012d, B:69:0x0135, B:72:0x0140, B:73:0x0143, B:76:0x014b), top: B:112:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:85:0x019b A[Catch: all -> 0x02d5, TryCatch #0 {, blocks: (B:4:0x0009, B:10:0x0012, B:13:0x0019, B:14:0x002b, B:17:0x0030, B:19:0x0038, B:78:0x017b, B:80:0x0186, B:83:0x0193, B:85:0x019b, B:86:0x01a5, B:88:0x01b5, B:90:0x01b9, B:92:0x01bd, B:94:0x01c1, B:96:0x01e6, B:98:0x01ef, B:100:0x02ab, B:101:0x02ae, B:95:0x01e4, B:105:0x02b4, B:106:0x02bb, B:107:0x02bc, B:108:0x02d4, B:20:0x0093, B:22:0x0097, B:25:0x009c, B:26:0x00ac, B:29:0x00be, B:32:0x00c5, B:34:0x00cb, B:37:0x00d6, B:38:0x00d9, B:39:0x00de, B:40:0x00e2, B:43:0x00e7, B:45:0x00ed, B:48:0x00f8, B:49:0x00fb, B:52:0x0103, B:55:0x010c, B:59:0x0116, B:62:0x0121, B:63:0x0124, B:64:0x0129, B:65:0x012d, B:69:0x0135, B:72:0x0140, B:73:0x0143, B:76:0x014b), top: B:112:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:95:0x01e4 A[Catch: all -> 0x02d5, TryCatch #0 {, blocks: (B:4:0x0009, B:10:0x0012, B:13:0x0019, B:14:0x002b, B:17:0x0030, B:19:0x0038, B:78:0x017b, B:80:0x0186, B:83:0x0193, B:85:0x019b, B:86:0x01a5, B:88:0x01b5, B:90:0x01b9, B:92:0x01bd, B:94:0x01c1, B:96:0x01e6, B:98:0x01ef, B:100:0x02ab, B:101:0x02ae, B:95:0x01e4, B:105:0x02b4, B:106:0x02bb, B:107:0x02bc, B:108:0x02d4, B:20:0x0093, B:22:0x0097, B:25:0x009c, B:26:0x00ac, B:29:0x00be, B:32:0x00c5, B:34:0x00cb, B:37:0x00d6, B:38:0x00d9, B:39:0x00de, B:40:0x00e2, B:43:0x00e7, B:45:0x00ed, B:48:0x00f8, B:49:0x00fb, B:52:0x0103, B:55:0x010c, B:59:0x0116, B:62:0x0121, B:63:0x0124, B:64:0x0129, B:65:0x012d, B:69:0x0135, B:72:0x0140, B:73:0x0143, B:76:0x014b), top: B:112:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:98:0x01ef A[Catch: all -> 0x02d5, TryCatch #0 {, blocks: (B:4:0x0009, B:10:0x0012, B:13:0x0019, B:14:0x002b, B:17:0x0030, B:19:0x0038, B:78:0x017b, B:80:0x0186, B:83:0x0193, B:85:0x019b, B:86:0x01a5, B:88:0x01b5, B:90:0x01b9, B:92:0x01bd, B:94:0x01c1, B:96:0x01e6, B:98:0x01ef, B:100:0x02ab, B:101:0x02ae, B:95:0x01e4, B:105:0x02b4, B:106:0x02bb, B:107:0x02bc, B:108:0x02d4, B:20:0x0093, B:22:0x0097, B:25:0x009c, B:26:0x00ac, B:29:0x00be, B:32:0x00c5, B:34:0x00cb, B:37:0x00d6, B:38:0x00d9, B:39:0x00de, B:40:0x00e2, B:43:0x00e7, B:45:0x00ed, B:48:0x00f8, B:49:0x00fb, B:52:0x0103, B:55:0x010c, B:59:0x0116, B:62:0x0121, B:63:0x0124, B:64:0x0129, B:65:0x012d, B:69:0x0135, B:72:0x0140, B:73:0x0143, B:76:0x014b), top: B:112:0x0009 }] */
    public synchronized PixmapPackerRectangle pack(String str, Pixmap pixmap) {
        int i;
        int i2;
        PixmapPackerRectangle pixmapPackerRectangle;
        Page pagePack;
        int i3;
        int i4;
        int i5;
        int i6;
        String str2 = str;
        Pixmap pixmap2 = pixmap;
        synchronized (this) {
            Pixmap pixmap3 = null;
            if (this.disposed) {
                return null;
            }
            if (str2 != null && getRect(str) != null) {
                throw new GdxRuntimeException("Pixmap has already been packed with name: " + str2);
            }
            if (str2 != null && str2.endsWith(".9")) {
                PixmapPackerRectangle pixmapPackerRectangle2 = new PixmapPackerRectangle(0, 0, pixmap.getWidth() - 2, pixmap.getHeight() - 2);
                Pixmap pixmap4 = new Pixmap(pixmap.getWidth() - 2, pixmap.getHeight() - 2, pixmap.getFormat());
                pixmap4.setBlending(Pixmap.Blending.None);
                pixmapPackerRectangle2.splits = getSplits(pixmap2);
                pixmapPackerRectangle2.pads = getPads(pixmap2, pixmapPackerRectangle2.splits);
                pixmap4.drawPixmap(pixmap, 0, 0, 1, 1, pixmap.getWidth() - 1, pixmap.getHeight() - 1);
                str2 = str2.split("\\.")[0];
                pixmapPackerRectangle = pixmapPackerRectangle2;
                pixmap2 = pixmap4;
            } else {
                if (this.stripWhitespaceX || this.stripWhitespaceY) {
                    int width = pixmap.getWidth();
                    int height = pixmap.getHeight();
                    int height2 = pixmap.getHeight();
                    if (this.stripWhitespaceY) {
                        int i7 = 0;
                        loop0: for (int i8 = 0; i8 < pixmap.getHeight(); i8++) {
                            for (int i9 = 0; i9 < pixmap.getWidth(); i9++) {
                                if ((pixmap2.getPixel(i9, i8) & 255) > this.alphaThreshold) {
                                    break loop0;
                                }
                            }
                            i7++;
                        }
                        int height3 = pixmap.getHeight();
                        loop2: while (true) {
                            height3--;
                            if (height3 < i7) {
                                break;
                            }
                            for (int i10 = 0; i10 < pixmap.getWidth(); i10++) {
                                if ((pixmap2.getPixel(i10, height3) & 255) > this.alphaThreshold) {
                                    break loop2;
                                }
                            }
                            height2--;
                        }
                        i = i7;
                    } else {
                        i = 0;
                    }
                    int width2 = pixmap.getWidth();
                    if (this.stripWhitespaceX) {
                        int i11 = 0;
                        loop4: for (int i12 = 0; i12 < pixmap.getWidth(); i12++) {
                            for (int i13 = i; i13 < height2; i13++) {
                                if ((pixmap2.getPixel(i12, i13) & 255) > this.alphaThreshold) {
                                    break loop4;
                                }
                            }
                            i11++;
                        }
                        int width3 = pixmap.getWidth();
                        loop6: while (true) {
                            width3--;
                            if (width3 < i11) {
                                break;
                            }
                            for (int i14 = i; i14 < height2; i14++) {
                                if ((pixmap2.getPixel(width3, i14) & 255) > this.alphaThreshold) {
                                    break loop6;
                                }
                            }
                            width2--;
                        }
                        i2 = i11;
                    } else {
                        i2 = 0;
                    }
                    int i15 = width2 - i2;
                    int i16 = height2 - i;
                    Pixmap pixmap5 = new Pixmap(i15, i16, pixmap.getFormat());
                    pixmap5.setBlending(Pixmap.Blending.None);
                    pixmap5.drawPixmap(pixmap, 0, 0, i2, i, i15, i16);
                    pixmapPackerRectangle = new PixmapPackerRectangle(0, 0, i15, i16, i2, i, width, height);
                    pixmap2 = pixmap5;
                } else {
                    pixmapPackerRectangle = new PixmapPackerRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
                }
                if (pixmapPackerRectangle.getWidth() <= this.pageWidth && pixmapPackerRectangle.getHeight() <= this.pageHeight) {
                    pagePack = this.packStrategy.pack(this, str2, pixmapPackerRectangle);
                    if (str2 != null) {
                        pagePack.rects.put(str2, pixmapPackerRectangle);
                        pagePack.addedRects.add(str2);
                    }
                    i3 = (int) pixmapPackerRectangle.x;
                    i4 = (int) pixmapPackerRectangle.y;
                    i5 = (int) pixmapPackerRectangle.width;
                    i6 = (int) pixmapPackerRectangle.height;
                    if (!this.packToTexture && !this.duplicateBorder && pagePack.texture != null && !pagePack.dirty) {
                        pagePack.texture.bind();
                        Gdx.gl.glTexSubImage2D(pagePack.texture.glTarget, 0, i3, i4, i5, i6, pixmap2.getGLFormat(), pixmap2.getGLType(), pixmap2.getPixels());
                    } else {
                        pagePack.dirty = true;
                    }
                    pagePack.image.drawPixmap(pixmap2, i3, i4);
                    if (this.duplicateBorder) {
                        int width4 = pixmap2.getWidth();
                        int height4 = pixmap2.getHeight();
                        int i17 = i3 - 1;
                        int i18 = i4 - 1;
                        pagePack.image.drawPixmap(pixmap2, 0, 0, 1, 1, i17, i18, 1, 1);
                        int i19 = width4 - 1;
                        int i20 = i3 + i5;
                        pagePack.image.drawPixmap(pixmap2, i19, 0, 1, 1, i20, i18, 1, 1);
                        int i21 = height4 - 1;
                        int i22 = i4 + i6;
                        pagePack.image.drawPixmap(pixmap2, 0, i21, 1, 1, i17, i22, 1, 1);
                        pagePack.image.drawPixmap(pixmap2, i19, i21, 1, 1, i20, i22, 1, 1);
                        pagePack.image.drawPixmap(pixmap2, 0, 0, width4, 1, i3, i18, i5, 1);
                        pagePack.image.drawPixmap(pixmap2, 0, i21, width4, 1, i3, i22, i5, 1);
                        pagePack.image.drawPixmap(pixmap2, 0, 0, 1, height4, i17, i4, 1, i6);
                        pagePack.image.drawPixmap(pixmap2, i19, 0, 1, height4, i20, i4, 1, i6);
                    }
                    if (pixmap3 != null) {
                        pixmap3.dispose();
                    }
                    pixmapPackerRectangle.page = pagePack;
                    return pixmapPackerRectangle;
                }
                if (str2 == null) {
                    throw new GdxRuntimeException("Page size too small for pixmap.");
                }
                throw new GdxRuntimeException("Page size too small for pixmap: " + str2);
            }
            pixmap3 = pixmap2;
            if (pixmapPackerRectangle.getWidth() <= this.pageWidth) {
                pagePack = this.packStrategy.pack(this, str2, pixmapPackerRectangle);
                if (str2 != null) {
                    pagePack.rects.put(str2, pixmapPackerRectangle);
                    pagePack.addedRects.add(str2);
                }
                i3 = (int) pixmapPackerRectangle.x;
                i4 = (int) pixmapPackerRectangle.y;
                i5 = (int) pixmapPackerRectangle.width;
                i6 = (int) pixmapPackerRectangle.height;
                if (!this.packToTexture) {
                    pagePack.dirty = true;
                } else {
                    pagePack.dirty = true;
                }
                pagePack.image.drawPixmap(pixmap2, i3, i4);
                if (this.duplicateBorder) {
                    int width5 = pixmap2.getWidth();
                    int height5 = pixmap2.getHeight();
                    int i110 = i3 - 1;
                    int i111 = i4 - 1;
                    pagePack.image.drawPixmap(pixmap2, 0, 0, 1, 1, i110, i111, 1, 1);
                    int i112 = width5 - 1;
                    int i23 = i3 + i5;
                    pagePack.image.drawPixmap(pixmap2, i112, 0, 1, 1, i23, i111, 1, 1);
                    int i24 = height5 - 1;
                    int i25 = i4 + i6;
                    pagePack.image.drawPixmap(pixmap2, 0, i24, 1, 1, i110, i25, 1, 1);
                    pagePack.image.drawPixmap(pixmap2, i112, i24, 1, 1, i23, i25, 1, 1);
                    pagePack.image.drawPixmap(pixmap2, 0, 0, width5, 1, i3, i111, i5, 1);
                    pagePack.image.drawPixmap(pixmap2, 0, i24, width5, 1, i3, i25, i5, 1);
                    pagePack.image.drawPixmap(pixmap2, 0, 0, 1, height5, i110, i4, 1, i6);
                    pagePack.image.drawPixmap(pixmap2, i112, 0, 1, height5, i23, i4, 1, i6);
                }
                if (pixmap3 != null) {
                    pixmap3.dispose();
                }
                pixmapPackerRectangle.page = pagePack;
                return pixmapPackerRectangle;
            }
            if (str2 == null) {
                throw new GdxRuntimeException("Page size too small for pixmap.");
            }
            throw new GdxRuntimeException("Page size too small for pixmap: " + str2);
        }
    }

    public Array<Page> getPages() {
        return this.pages;
    }

    public synchronized Rectangle getRect(String str) {
        Array.ArrayIterator<Page> it = this.pages.iterator();
        while (it.hasNext()) {
            PixmapPackerRectangle pixmapPackerRectangle = it.next().rects.get(str);
            if (pixmapPackerRectangle != null) {
                return pixmapPackerRectangle;
            }
        }
        return null;
    }

    public synchronized Page getPage(String str) {
        Array.ArrayIterator<Page> it = this.pages.iterator();
        while (it.hasNext()) {
            Page next = it.next();
            if (next.rects.get(str) != null) {
                return next;
            }
        }
        return null;
    }

    public synchronized int getPageIndex(String str) {
        for (int i = 0; i < this.pages.size; i++) {
            if (this.pages.get(i).rects.get(str) != null) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public synchronized void dispose() {
        Array.ArrayIterator<Page> it = this.pages.iterator();
        while (it.hasNext()) {
            Page next = it.next();
            if (next.texture == null) {
                next.image.dispose();
            }
        }
        this.disposed = true;
    }

    public synchronized TextureAtlas generateTextureAtlas(Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2, boolean z) {
        TextureAtlas textureAtlas;
        textureAtlas = new TextureAtlas();
        updateTextureAtlas(textureAtlas, textureFilter, textureFilter2, z);
        return textureAtlas;
    }

    public synchronized void updateTextureAtlas(TextureAtlas textureAtlas, Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2, boolean z) {
        updateTextureAtlas(textureAtlas, textureFilter, textureFilter2, z, true);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0082  */
    public synchronized void updateTextureAtlas(TextureAtlas textureAtlas, Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2, boolean z, boolean z2) {
        int i;
        updatePageTextures(textureFilter, textureFilter2, z);
        Array.ArrayIterator<Page> it = this.pages.iterator();
        while (it.hasNext()) {
            Page next = it.next();
            if (next.addedRects.size > 0) {
                Array.ArrayIterator<String> it2 = next.addedRects.iterator();
                while (it2.hasNext()) {
                    String next2 = it2.next();
                    PixmapPackerRectangle pixmapPackerRectangle = next.rects.get(next2);
                    TextureAtlas.AtlasRegion atlasRegion = new TextureAtlas.AtlasRegion(next.texture, (int) pixmapPackerRectangle.x, (int) pixmapPackerRectangle.y, (int) pixmapPackerRectangle.width, (int) pixmapPackerRectangle.height);
                    if (pixmapPackerRectangle.splits != null) {
                        atlasRegion.names = new String[]{"split", "pad"};
                        atlasRegion.values = new int[][]{pixmapPackerRectangle.splits, pixmapPackerRectangle.pads};
                    }
                    if (z2) {
                        Matcher matcher = indexPattern.matcher(next2);
                        if (matcher.matches()) {
                            next2 = matcher.group(1);
                            i = Integer.parseInt(matcher.group(2));
                        } else {
                            i = -1;
                        }
                    } else {
                        i = -1;
                    }
                    atlasRegion.f97name = next2;
                    atlasRegion.index = i;
                    atlasRegion.offsetX = pixmapPackerRectangle.offsetX;
                    atlasRegion.offsetY = (int) ((pixmapPackerRectangle.originalHeight - pixmapPackerRectangle.height) - pixmapPackerRectangle.offsetY);
                    atlasRegion.originalWidth = pixmapPackerRectangle.originalWidth;
                    atlasRegion.originalHeight = pixmapPackerRectangle.originalHeight;
                    textureAtlas.getRegions().add(atlasRegion);
                }
                next.addedRects.clear();
                textureAtlas.getTextures().add(next.texture);
            }
        }
    }

    public synchronized void updateTextureRegions(Array<TextureRegion> array, Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2, boolean z) {
        updatePageTextures(textureFilter, textureFilter2, z);
        while (array.size < this.pages.size) {
            array.add(new TextureRegion(this.pages.get(array.size).texture));
        }
    }

    public synchronized void updatePageTextures(Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2, boolean z) {
        Array.ArrayIterator<Page> it = this.pages.iterator();
        while (it.hasNext()) {
            it.next().updateTexture(textureFilter, textureFilter2, z);
        }
    }

    public int getPageWidth() {
        return this.pageWidth;
    }

    public void setPageWidth(int i) {
        this.pageWidth = i;
    }

    public int getPageHeight() {
        return this.pageHeight;
    }

    public void setPageHeight(int i) {
        this.pageHeight = i;
    }

    public Pixmap.Format getPageFormat() {
        return this.pageFormat;
    }

    public void setPageFormat(Pixmap.Format format) {
        this.pageFormat = format;
    }

    public int getPadding() {
        return this.padding;
    }

    public void setPadding(int i) {
        this.padding = i;
    }

    public boolean getDuplicateBorder() {
        return this.duplicateBorder;
    }

    public void setDuplicateBorder(boolean z) {
        this.duplicateBorder = z;
    }

    public boolean getPackToTexture() {
        return this.packToTexture;
    }

    public void setPackToTexture(boolean z) {
        this.packToTexture = z;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker$Page.class
      classes8.dex
     */
    public static class Page {
        boolean dirty;
        Pixmap image;
        Texture texture;
        OrderedMap<String, PixmapPackerRectangle> rects = new OrderedMap<>();
        final Array<String> addedRects = new Array<>();

        public Page(PixmapPacker pixmapPacker) {
            Pixmap pixmap = new Pixmap(pixmapPacker.pageWidth, pixmapPacker.pageHeight, pixmapPacker.pageFormat);
            this.image = pixmap;
            pixmap.setBlending(Pixmap.Blending.None);
            this.image.setColor(pixmapPacker.getTransparentColor());
            this.image.fill();
        }

        public Pixmap getPixmap() {
            return this.image;
        }

        public OrderedMap<String, PixmapPackerRectangle> getRects() {
            return this.rects;
        }

        public Texture getTexture() {
            return this.texture;
        }

        public boolean updateTexture(Texture.TextureFilter textureFilter, Texture.TextureFilter textureFilter2, boolean z) {
            Texture texture = this.texture;
            if (texture != null) {
                if (!this.dirty) {
                    return false;
                }
                texture.load(texture.getTextureData());
            } else {
                Pixmap pixmap = this.image;
                Texture texture2 = new Texture(new PixmapTextureData(pixmap, pixmap.getFormat(), z, false, true)) { // from class: com.badlogic.gdx.graphics.g2d.PixmapPacker.Page.1
                    @Override // com.badlogic.gdx.graphics.Texture, com.badlogic.gdx.graphics.GLTexture, com.badlogic.gdx.utils.Disposable
                    public void dispose() {
                        super.dispose();
                        Page.this.image.dispose();
                    }
                };
                this.texture = texture2;
                texture2.setFilter(textureFilter, textureFilter2);
            }
            this.dirty = false;
            return true;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker$GuillotineStrategy.class
      classes8.dex
     */
    public static class GuillotineStrategy implements PackStrategy {
        Comparator<Pixmap> comparator;

        @Override // com.badlogic.gdx.graphics.g2d.PixmapPacker.PackStrategy
        public void sort(Array<Pixmap> array) {
            if (this.comparator == null) {
                this.comparator = new Comparator<Pixmap>() { // from class: com.badlogic.gdx.graphics.g2d.PixmapPacker.GuillotineStrategy.1
                    @Override // java.util.Comparator
                    public int compare(Pixmap pixmap, Pixmap pixmap2) {
                        return Math.max(pixmap.getWidth(), pixmap.getHeight()) - Math.max(pixmap2.getWidth(), pixmap2.getHeight());
                    }
                };
            }
            array.sort(this.comparator);
        }

        @Override // com.badlogic.gdx.graphics.g2d.PixmapPacker.PackStrategy
        public Page pack(PixmapPacker pixmapPacker, String str, Rectangle rectangle) {
            GuillotinePage guillotinePage;
            if (pixmapPacker.pages.size == 0) {
                guillotinePage = new GuillotinePage(pixmapPacker);
                pixmapPacker.pages.add(guillotinePage);
            } else {
                guillotinePage = (GuillotinePage) pixmapPacker.pages.peek();
            }
            float f = pixmapPacker.padding;
            rectangle.width += f;
            rectangle.height += f;
            Node nodeInsert = insert(guillotinePage.root, rectangle);
            if (nodeInsert == null) {
                guillotinePage = new GuillotinePage(pixmapPacker);
                pixmapPacker.pages.add(guillotinePage);
                nodeInsert = insert(guillotinePage.root, rectangle);
            }
            nodeInsert.full = true;
            rectangle.set(nodeInsert.rect.x, nodeInsert.rect.y, nodeInsert.rect.width - f, nodeInsert.rect.height - f);
            return guillotinePage;
        }

        private Node insert(Node node, Rectangle rectangle) {
            if (!node.full && node.leftChild != null && node.rightChild != null) {
                Node nodeInsert = insert(node.leftChild, rectangle);
                return nodeInsert == null ? insert(node.rightChild, rectangle) : nodeInsert;
            }
            if (node.full) {
                return null;
            }
            if (node.rect.width == rectangle.width && node.rect.height == rectangle.height) {
                return node;
            }
            if (node.rect.width < rectangle.width || node.rect.height < rectangle.height) {
                return null;
            }
            node.leftChild = new Node();
            node.rightChild = new Node();
            if (((int) node.rect.width) - ((int) rectangle.width) > ((int) node.rect.height) - ((int) rectangle.height)) {
                node.leftChild.rect.x = node.rect.x;
                node.leftChild.rect.y = node.rect.y;
                node.leftChild.rect.width = rectangle.width;
                node.leftChild.rect.height = node.rect.height;
                node.rightChild.rect.x = node.rect.x + rectangle.width;
                node.rightChild.rect.y = node.rect.y;
                node.rightChild.rect.width = node.rect.width - rectangle.width;
                node.rightChild.rect.height = node.rect.height;
            } else {
                node.leftChild.rect.x = node.rect.x;
                node.leftChild.rect.y = node.rect.y;
                node.leftChild.rect.width = node.rect.width;
                node.leftChild.rect.height = rectangle.height;
                node.rightChild.rect.x = node.rect.x;
                node.rightChild.rect.y = node.rect.y + rectangle.height;
                node.rightChild.rect.width = node.rect.width;
                node.rightChild.rect.height = node.rect.height - rectangle.height;
            }
            return insert(node.leftChild, rectangle);
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker$GuillotineStrategy$Node.class
          classes8.dex
         */
        static final class Node {
            public boolean full;
            public Node leftChild;
            public final Rectangle rect = new Rectangle();
            public Node rightChild;

            Node() {
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker$GuillotineStrategy$GuillotinePage.class
          classes8.dex
         */
        static class GuillotinePage extends Page {
            Node root;

            public GuillotinePage(PixmapPacker pixmapPacker) {
                super(pixmapPacker);
                Node node = new Node();
                this.root = node;
                node.rect.x = pixmapPacker.padding;
                this.root.rect.y = pixmapPacker.padding;
                this.root.rect.width = pixmapPacker.pageWidth - (pixmapPacker.padding * 2);
                this.root.rect.height = pixmapPacker.pageHeight - (pixmapPacker.padding * 2);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker$SkylineStrategy.class
      classes8.dex
     */
    public static class SkylineStrategy implements PackStrategy {
        Comparator<Pixmap> comparator;

        @Override // com.badlogic.gdx.graphics.g2d.PixmapPacker.PackStrategy
        public void sort(Array<Pixmap> array) {
            if (this.comparator == null) {
                this.comparator = new Comparator<Pixmap>() { // from class: com.badlogic.gdx.graphics.g2d.PixmapPacker.SkylineStrategy.1
                    @Override // java.util.Comparator
                    public int compare(Pixmap pixmap, Pixmap pixmap2) {
                        return pixmap.getHeight() - pixmap2.getHeight();
                    }
                };
            }
            array.sort(this.comparator);
        }

        /* JADX WARN: Code duplicated, block: B:37:0x0090 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:38:0x00a0 A[SYNTHETIC] */
        @Override // com.badlogic.gdx.graphics.g2d.PixmapPacker.PackStrategy
        public Page pack(PixmapPacker pixmapPacker, String str, Rectangle rectangle) {
            int i = pixmapPacker.padding;
            int i2 = i * 2;
            int i3 = pixmapPacker.pageWidth - i2;
            int i4 = pixmapPacker.pageHeight - i2;
            int i5 = ((int) rectangle.width) + i;
            int i6 = ((int) rectangle.height) + i;
            int i7 = pixmapPacker.pages.size;
            for (int i8 = 0; i8 < i7; i8++) {
                SkylinePage skylinePage = (SkylinePage) pixmapPacker.pages.get(i8);
                int i9 = skylinePage.rows.size - 1;
                SkylinePage.Row row = null;
                for (int i10 = 0; i10 < i9; i10++) {
                    SkylinePage.Row row2 = skylinePage.rows.get(i10);
                    if (row2.x + i5 < i3 && row2.y + i6 < i4 && i6 <= row2.height && (row == null || row2.height < row.height)) {
                        row = row2;
                    }
                }
                if (row == null) {
                    SkylinePage.Row rowPeek = skylinePage.rows.peek();
                    if (rowPeek.y + i6 < i4) {
                        if (rowPeek.x + i5 < i3) {
                            rowPeek.height = Math.max(rowPeek.height, i6);
                            row = rowPeek;
                        } else if (rowPeek.y + rowPeek.height + i6 < i4) {
                            row = new SkylinePage.Row();
                            row.y = rowPeek.y + rowPeek.height;
                            row.height = i6;
                            skylinePage.rows.add(row);
                        }
                        if (row != null) {
                            rectangle.x = row.x;
                            rectangle.y = row.y;
                            row.x += i5;
                            return skylinePage;
                        }
                    } else {
                        continue;
                    }
                } else if (row != null) {
                    rectangle.x = row.x;
                    rectangle.y = row.y;
                    row.x += i5;
                    return skylinePage;
                }
            }
            SkylinePage skylinePage2 = new SkylinePage(pixmapPacker);
            pixmapPacker.pages.add(skylinePage2);
            SkylinePage.Row row3 = new SkylinePage.Row();
            row3.x = i5 + i;
            row3.y = i;
            row3.height = i6;
            skylinePage2.rows.add(row3);
            float f = i;
            rectangle.x = f;
            rectangle.y = f;
            return skylinePage2;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker$SkylineStrategy$SkylinePage.class
          classes8.dex
         */
        static class SkylinePage extends Page {
            Array<Row> rows;

            public SkylinePage(PixmapPacker pixmapPacker) {
                super(pixmapPacker);
                this.rows = new Array<>();
            }

            /* JADX WARN: Classes with same name are omitted, all sources:
              NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker$SkylineStrategy$SkylinePage$Row.class
              classes8.dex
             */
            static class Row {
                int height;
                int x;
                int y;

                Row() {
                }
            }
        }
    }

    public Color getTransparentColor() {
        return this.transparentColor;
    }

    public void setTransparentColor(Color color) {
        this.transparentColor.set(color);
    }

    private int[] getSplits(Pixmap pixmap) {
        int width;
        int height;
        int splitPoint = getSplitPoint(pixmap, 1, 0, true, true);
        int splitPoint2 = getSplitPoint(pixmap, splitPoint, 0, false, true);
        int splitPoint3 = getSplitPoint(pixmap, 0, 1, true, false);
        int splitPoint4 = getSplitPoint(pixmap, 0, splitPoint3, false, false);
        getSplitPoint(pixmap, splitPoint2 + 1, 0, true, true);
        getSplitPoint(pixmap, 0, splitPoint4 + 1, true, false);
        if (splitPoint == 0 && splitPoint2 == 0 && splitPoint3 == 0 && splitPoint4 == 0) {
            return null;
        }
        if (splitPoint != 0) {
            splitPoint--;
            width = (pixmap.getWidth() - 2) - (splitPoint2 - 1);
        } else {
            width = pixmap.getWidth() - 2;
        }
        if (splitPoint3 != 0) {
            splitPoint3--;
            height = (pixmap.getHeight() - 2) - (splitPoint4 - 1);
        } else {
            height = pixmap.getHeight() - 2;
        }
        return new int[]{splitPoint, width, splitPoint3, height};
    }

    private int[] getPads(Pixmap pixmap, int[] iArr) {
        int width;
        int height = pixmap.getHeight() - 1;
        int width2 = pixmap.getWidth() - 1;
        int splitPoint = getSplitPoint(pixmap, 1, height, true, true);
        int splitPoint2 = getSplitPoint(pixmap, width2, 1, true, false);
        int splitPoint3 = splitPoint != 0 ? getSplitPoint(pixmap, splitPoint + 1, height, false, true) : 0;
        int splitPoint4 = splitPoint2 != 0 ? getSplitPoint(pixmap, width2, splitPoint2 + 1, false, false) : 0;
        getSplitPoint(pixmap, splitPoint3 + 1, height, true, true);
        getSplitPoint(pixmap, width2, splitPoint4 + 1, true, false);
        if (splitPoint == 0 && splitPoint3 == 0 && splitPoint2 == 0 && splitPoint4 == 0) {
            return null;
        }
        int height2 = -1;
        if (splitPoint == 0 && splitPoint3 == 0) {
            width = -1;
            splitPoint = -1;
        } else if (splitPoint > 0) {
            splitPoint--;
            width = (pixmap.getWidth() - 2) - (splitPoint3 - 1);
        } else {
            width = pixmap.getWidth() - 2;
        }
        if (splitPoint2 == 0 && splitPoint4 == 0) {
            splitPoint2 = -1;
        } else if (splitPoint2 > 0) {
            splitPoint2--;
            height2 = (pixmap.getHeight() - 2) - (splitPoint4 - 1);
        } else {
            height2 = pixmap.getHeight() - 2;
        }
        int[] iArr2 = {splitPoint, width, splitPoint2, height2};
        if (iArr == null || !Arrays.equals(iArr2, iArr)) {
            return iArr2;
        }
        return null;
    }

    private int getSplitPoint(Pixmap pixmap, int i, int i2, boolean z, boolean z2) {
        int width = z2 ? pixmap.getWidth() : pixmap.getHeight();
        int i3 = z ? 255 : 0;
        for (int i4 = z2 ? i : i2; i4 != width; i4++) {
            if (z2) {
                i = i4;
            } else {
                i2 = i4;
            }
            this.c.set(pixmap.getPixel(i, i2));
            int[] iArr = {(int) (this.c.r * 255.0f), (int) (this.c.g * 255.0f), (int) (this.c.b * 255.0f), (int) (this.c.a * 255.0f)};
            int i5 = iArr[3];
            if (i5 == i3) {
                return i4;
            }
            if (!z && (iArr[0] != 0 || iArr[1] != 0 || iArr[2] != 0 || i5 != 255)) {
                System.out.println(i + "  " + i2 + " " + iArr + " ");
            }
        }
        return 0;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPacker$PixmapPackerRectangle.class
      classes8.dex
     */
    public static class PixmapPackerRectangle extends Rectangle {
        public int offsetX;
        public int offsetY;
        public int originalHeight;
        public int originalWidth;
        public int[] pads;
        public Page page;
        public int[] splits;

        PixmapPackerRectangle(int i, int i2, int i3, int i4) {
            super(i, i2, i3, i4);
            this.offsetX = 0;
            this.offsetY = 0;
            this.originalWidth = i3;
            this.originalHeight = i4;
        }

        PixmapPackerRectangle(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            super(i, i2, i3, i4);
            this.offsetX = i5;
            this.offsetY = i6;
            this.originalWidth = i7;
            this.originalHeight = i8;
        }
    }
}
