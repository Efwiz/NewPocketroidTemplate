package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import io.netty.util.internal.StringUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.bytebuddy.asm.Advice;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/BitmapFont.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class BitmapFont implements Disposable {
    private static final int LOG2_PAGE_SIZE = 9;
    private static final int PAGES = 128;
    private static final int PAGE_SIZE = 512;
    private final BitmapFontCache cache;
    final BitmapFontData data;
    private boolean flipped;
    boolean integer;
    private boolean ownsTexture;
    Array<TextureRegion> regions;

    public BitmapFont() {
        this(Gdx.files.classpath("com/badlogic/gdx/utils/lsans-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/lsans-15.png"), false, true);
    }

    public BitmapFont(boolean z) {
        this(Gdx.files.classpath("com/badlogic/gdx/utils/lsans-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/lsans-15.png"), z, true);
    }

    public BitmapFont(FileHandle fileHandle, TextureRegion textureRegion) {
        this(fileHandle, textureRegion, false);
    }

    public BitmapFont(FileHandle fileHandle, TextureRegion textureRegion, boolean z) {
        this(new BitmapFontData(fileHandle, z), textureRegion, true);
    }

    public BitmapFont(FileHandle fileHandle) {
        this(fileHandle, false);
    }

    public BitmapFont(FileHandle fileHandle, boolean z) {
        BitmapFontData bitmapFontData = new BitmapFontData(fileHandle, z);
        this(bitmapFontData, (TextureRegion) null, true);
    }

    public BitmapFont(FileHandle fileHandle, FileHandle fileHandle2, boolean z) {
        this(fileHandle, fileHandle2, z, true);
    }

    public BitmapFont(FileHandle fileHandle, FileHandle fileHandle2, boolean z, boolean z2) {
        this(new BitmapFontData(fileHandle, z), new TextureRegion(new Texture(fileHandle2, false)), z2);
        this.ownsTexture = true;
    }

    public BitmapFont(BitmapFontData bitmapFontData, TextureRegion textureRegion, boolean z) {
        this(bitmapFontData, (Array<TextureRegion>) (textureRegion != null ? Array.with(textureRegion) : null), z);
    }

    public BitmapFont(BitmapFontData bitmapFontData, Array<TextureRegion> array, boolean z) {
        FileHandle fileHandle;
        this.flipped = bitmapFontData.flipped;
        this.data = bitmapFontData;
        this.integer = z;
        if (array == null || array.size == 0) {
            if (bitmapFontData.imagePaths == null) {
                throw new IllegalArgumentException("If no regions are specified, the font data must have an images path.");
            }
            int length = bitmapFontData.imagePaths.length;
            this.regions = new Array<>(length);
            for (int i = 0; i < length; i++) {
                if (bitmapFontData.fontFile == null) {
                    fileHandle = Gdx.files.internal(bitmapFontData.imagePaths[i]);
                } else {
                    fileHandle = Gdx.files.getFileHandle(bitmapFontData.imagePaths[i], bitmapFontData.fontFile.type());
                }
                this.regions.add(new TextureRegion(new Texture(fileHandle, false)));
            }
            this.ownsTexture = true;
        } else {
            this.regions = array;
            this.ownsTexture = false;
        }
        this.cache = newFontCache();
        load(bitmapFontData);
    }

    protected void load(BitmapFontData bitmapFontData) {
        for (Glyph[] glyphArr : bitmapFontData.glyphs) {
            if (glyphArr != null) {
                for (Glyph glyph : glyphArr) {
                    if (glyph != null) {
                        bitmapFontData.setGlyphRegion(glyph, this.regions.get(glyph.page));
                    }
                }
            }
        }
        if (bitmapFontData.missingGlyph != null) {
            bitmapFontData.setGlyphRegion(bitmapFontData.missingGlyph, this.regions.get(bitmapFontData.missingGlyph.page));
        }
    }

    public GlyphLayout draw(Batch batch, CharSequence charSequence, float f, float f2) {
        this.cache.clear();
        GlyphLayout glyphLayoutAddText = this.cache.addText(charSequence, f, f2);
        this.cache.draw(batch);
        return glyphLayoutAddText;
    }

    public GlyphLayout draw(Batch batch, CharSequence charSequence, float f, float f2, float f3, int i, boolean z) {
        this.cache.clear();
        GlyphLayout glyphLayoutAddText = this.cache.addText(charSequence, f, f2, f3, i, z);
        this.cache.draw(batch);
        return glyphLayoutAddText;
    }

    public GlyphLayout draw(Batch batch, CharSequence charSequence, float f, float f2, int i, int i2, float f3, int i3, boolean z) {
        this.cache.clear();
        GlyphLayout glyphLayoutAddText = this.cache.addText(charSequence, f, f2, i, i2, f3, i3, z);
        this.cache.draw(batch);
        return glyphLayoutAddText;
    }

    public GlyphLayout draw(Batch batch, CharSequence charSequence, float f, float f2, int i, int i2, float f3, int i3, boolean z, String str) {
        this.cache.clear();
        GlyphLayout glyphLayoutAddText = this.cache.addText(charSequence, f, f2, i, i2, f3, i3, z, str);
        this.cache.draw(batch);
        return glyphLayoutAddText;
    }

    public void draw(Batch batch, GlyphLayout glyphLayout, float f, float f2) {
        this.cache.clear();
        this.cache.addText(glyphLayout, f, f2);
        this.cache.draw(batch);
    }

    public Color getColor() {
        return this.cache.getColor();
    }

    public void setColor(Color color) {
        this.cache.getColor().set(color);
    }

    public void setColor(float f, float f2, float f3, float f4) {
        this.cache.getColor().set(f, f2, f3, f4);
    }

    public float getScaleX() {
        return this.data.scaleX;
    }

    public float getScaleY() {
        return this.data.scaleY;
    }

    public TextureRegion getRegion() {
        return this.regions.first();
    }

    public Array<TextureRegion> getRegions() {
        return this.regions;
    }

    public TextureRegion getRegion(int i) {
        return this.regions.get(i);
    }

    public float getLineHeight() {
        return this.data.lineHeight;
    }

    public float getSpaceXadvance() {
        return this.data.spaceXadvance;
    }

    public float getXHeight() {
        return this.data.xHeight;
    }

    public float getCapHeight() {
        return this.data.capHeight;
    }

    public float getAscent() {
        return this.data.ascent;
    }

    public float getDescent() {
        return this.data.descent;
    }

    public boolean isFlipped() {
        return this.flipped;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        if (this.ownsTexture) {
            for (int i = 0; i < this.regions.size; i++) {
                this.regions.get(i).getTexture().dispose();
            }
        }
    }

    public void setFixedWidthGlyphs(CharSequence charSequence) {
        BitmapFontData bitmapFontData = this.data;
        int length = charSequence.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            Glyph glyph = bitmapFontData.getGlyph(charSequence.charAt(i2));
            if (glyph != null && glyph.xadvance > i) {
                i = glyph.xadvance;
            }
        }
        int length2 = charSequence.length();
        for (int i3 = 0; i3 < length2; i3++) {
            Glyph glyph2 = bitmapFontData.getGlyph(charSequence.charAt(i3));
            if (glyph2 != null) {
                glyph2.xoffset += (i - glyph2.xadvance) / 2;
                glyph2.xadvance = i;
                glyph2.kerning = null;
                glyph2.fixedWidth = true;
            }
        }
    }

    public void setUseIntegerPositions(boolean z) {
        this.integer = z;
        this.cache.setUseIntegerPositions(z);
    }

    public boolean usesIntegerPositions() {
        return this.integer;
    }

    public BitmapFontCache getCache() {
        return this.cache;
    }

    public BitmapFontData getData() {
        return this.data;
    }

    public boolean ownsTexture() {
        return this.ownsTexture;
    }

    public void setOwnsTexture(boolean z) {
        this.ownsTexture = z;
    }

    public BitmapFontCache newFontCache() {
        return new BitmapFontCache(this, this.integer);
    }

    public String toString() {
        return this.data.f93name != null ? this.data.f93name : super.toString();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/BitmapFont$Glyph.class
      classes8.dex
     */
    public static class Glyph {
        public boolean fixedWidth;
        public int height;

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        public int f94id;
        public byte[][] kerning;
        public int page = 0;
        public int srcX;
        public int srcY;
        public float u;
        public float u2;
        public float v;
        public float v2;
        public int width;
        public int xadvance;
        public int xoffset;
        public int yoffset;

        public int getKerning(char c) {
            byte[] bArr;
            byte[][] bArr2 = this.kerning;
            if (bArr2 == null || (bArr = bArr2[c >>> '\t']) == null) {
                return 0;
            }
            return bArr[c & 511];
        }

        public void setKerning(int i, int i2) {
            if (this.kerning == null) {
                this.kerning = new byte[128][];
            }
            byte[][] bArr = this.kerning;
            int i3 = i >>> 9;
            byte[] bArr2 = bArr[i3];
            if (bArr2 == null) {
                bArr2 = new byte[512];
                bArr[i3] = bArr2;
            }
            bArr2[i & 511] = (byte) i2;
        }

        public String toString() {
            return Character.toString((char) this.f94id);
        }
    }

    static int indexOf(CharSequence charSequence, char c, int i) {
        int length = charSequence.length();
        while (i < length) {
            if (charSequence.charAt(i) == c) {
                return i;
            }
            i++;
        }
        return length;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/BitmapFont$BitmapFontData.class
      classes8.dex
     */
    public static class BitmapFontData {
        public float ascent;
        public float blankLineScale;
        public char[] breakChars;
        public char[] capChars;
        public float capHeight;
        public float cursorX;
        public float descent;
        public float down;
        public boolean flipped;
        public FileHandle fontFile;
        public final Glyph[][] glyphs;
        public String[] imagePaths;
        public float lineHeight;
        public boolean markupEnabled;
        public Glyph missingGlyph;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f93name;
        public float padBottom;
        public float padLeft;
        public float padRight;
        public float padTop;
        public float scaleX;
        public float scaleY;
        public float spaceXadvance;
        public char[] xChars;
        public float xHeight;

        public boolean isWhitespace(char c) {
            return c == '\t' || c == '\n' || c == '\r' || c == ' ';
        }

        public BitmapFontData() {
            this.capHeight = 1.0f;
            this.blankLineScale = 1.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.glyphs = new Glyph[128][];
            this.xHeight = 1.0f;
            this.xChars = new char[]{'x', 'e', 'a', 'o', 'n', Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL, Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL, 'c', 'u', Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL, 'v', 'w', 'z'};
            this.capChars = new char[]{'M', 'N', Util.C_BYTE, Util.C_DOUBLE, Util.C_CHAR, 'E', Util.C_FLOAT, 'K', 'A', 'G', 'H', 'I', Util.C_LONG, 'L', 'O', 'P', Util.C_UNRESOLVED, Matrix.MATRIX_TYPE_RANDOM_REGULAR, Util.C_SHORT, Util.C_TYPE_VARIABLE, Matrix.MATRIX_TYPE_RANDOM_UT, Util.C_VOID, 'W', 'X', 'Y', 'Z'};
        }

        public BitmapFontData(FileHandle fileHandle, boolean z) throws Throwable {
            this.capHeight = 1.0f;
            this.blankLineScale = 1.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.glyphs = new Glyph[128][];
            this.xHeight = 1.0f;
            this.xChars = new char[]{'x', 'e', 'a', 'o', 'n', Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL, Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL, 'c', 'u', Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL, 'v', 'w', 'z'};
            this.capChars = new char[]{'M', 'N', Util.C_BYTE, Util.C_DOUBLE, Util.C_CHAR, 'E', Util.C_FLOAT, 'K', 'A', 'G', 'H', 'I', Util.C_LONG, 'L', 'O', 'P', Util.C_UNRESOLVED, Matrix.MATRIX_TYPE_RANDOM_REGULAR, Util.C_SHORT, Util.C_TYPE_VARIABLE, Matrix.MATRIX_TYPE_RANDOM_UT, Util.C_VOID, 'W', 'X', 'Y', 'Z'};
            this.fontFile = fileHandle;
            this.flipped = z;
            load(fileHandle, z);
        }

        public void load(FileHandle fileHandle, boolean z) throws Throwable {
            int iMax;
            String line;
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            BufferedReader bufferedReader;
            Glyph[] glyphArr;
            String str;
            if (this.imagePaths != null) {
                throw new IllegalStateException("Already loaded.");
            }
            this.f93name = fileHandle.nameWithoutExtension();
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(fileHandle.read()), 512);
            try {
                try {
                    String line2 = bufferedReader2.readLine();
                    try {
                        if (line2 == null) {
                            throw new GdxRuntimeException("File is empty.");
                        }
                        String strSubstring = line2.substring(line2.indexOf("padding=") + 8);
                        boolean z2 = false;
                        String[] strArrSplit = strSubstring.substring(0, strSubstring.indexOf(32)).split(",", 4);
                        if (strArrSplit.length != 4) {
                            throw new GdxRuntimeException("Invalid padding.");
                        }
                        this.padTop = Integer.parseInt(strArrSplit[0]);
                        boolean z3 = true;
                        this.padRight = Integer.parseInt(strArrSplit[1]);
                        this.padBottom = Integer.parseInt(strArrSplit[2]);
                        this.padLeft = Integer.parseInt(strArrSplit[3]);
                        float f7 = this.padTop + this.padBottom;
                        String line3 = bufferedReader2.readLine();
                        if (line3 == null) {
                            throw new GdxRuntimeException("Missing common header.");
                        }
                        String[] strArrSplit2 = line3.split(" ", 9);
                        if (strArrSplit2.length < 3) {
                            throw new GdxRuntimeException("Invalid common header.");
                        }
                        if (!strArrSplit2[1].startsWith("lineHeight=")) {
                            throw new GdxRuntimeException("Missing: lineHeight");
                        }
                        this.lineHeight = Integer.parseInt(strArrSplit2[1].substring(11));
                        if (!strArrSplit2[2].startsWith("base=")) {
                            throw new GdxRuntimeException("Missing: base");
                        }
                        float f8 = Integer.parseInt(strArrSplit2[2].substring(5));
                        if (strArrSplit2.length < 6 || (str = strArrSplit2[5]) == null || !str.startsWith("pages=")) {
                            iMax = 1;
                        } else {
                            try {
                                iMax = Math.max(1, Integer.parseInt(strArrSplit2[5].substring(6)));
                            } catch (NumberFormatException unused) {
                                iMax = 1;
                            }
                        }
                        this.imagePaths = new String[iMax];
                        for (int i = 0; i < iMax; i++) {
                            String line4 = bufferedReader2.readLine();
                            if (line4 == null) {
                                throw new GdxRuntimeException("Missing additional page definitions.");
                            }
                            Matcher matcher = Pattern.compile(".*id=(\\d+)").matcher(line4);
                            if (matcher.find()) {
                                String strGroup = matcher.group(1);
                                try {
                                    if (Integer.parseInt(strGroup) != i) {
                                        throw new GdxRuntimeException("Page IDs must be indices starting at 0: " + strGroup);
                                    }
                                } catch (NumberFormatException e) {
                                    throw new GdxRuntimeException("Invalid page id: " + strGroup, e);
                                }
                            }
                            Matcher matcher2 = Pattern.compile(".*file=\"?([^\"]+)\"?").matcher(line4);
                            if (!matcher2.find()) {
                                throw new GdxRuntimeException("Missing: file");
                            }
                            this.imagePaths[i] = fileHandle.parent().child(matcher2.group(1)).path().replaceAll("\\\\", "/");
                        }
                        float f9 = 0.0f;
                        this.descent = 0.0f;
                        while (true) {
                            String line5 = bufferedReader2.readLine();
                            if (line5 == null || line5.startsWith("kernings ") || line5.startsWith("metrics ")) {
                                break;
                                break;
                                break;
                            }
                            BufferedReader bufferedReader3 = bufferedReader2;
                            if (line5.startsWith("char ")) {
                                Glyph glyph = new Glyph();
                                StringTokenizer stringTokenizer = new StringTokenizer(line5, " =");
                                stringTokenizer.nextToken();
                                stringTokenizer.nextToken();
                                int i2 = Integer.parseInt(stringTokenizer.nextToken());
                                if (i2 <= 0) {
                                    this.missingGlyph = glyph;
                                } else if (i2 <= 65535) {
                                    setGlyph(i2, glyph);
                                }
                                glyph.f94id = i2;
                                stringTokenizer.nextToken();
                                glyph.srcX = Integer.parseInt(stringTokenizer.nextToken());
                                stringTokenizer.nextToken();
                                glyph.srcY = Integer.parseInt(stringTokenizer.nextToken());
                                stringTokenizer.nextToken();
                                glyph.width = Integer.parseInt(stringTokenizer.nextToken());
                                stringTokenizer.nextToken();
                                glyph.height = Integer.parseInt(stringTokenizer.nextToken());
                                stringTokenizer.nextToken();
                                glyph.xoffset = Integer.parseInt(stringTokenizer.nextToken());
                                stringTokenizer.nextToken();
                                if (z) {
                                    glyph.yoffset = Integer.parseInt(stringTokenizer.nextToken());
                                } else {
                                    glyph.yoffset = -(glyph.height + Integer.parseInt(stringTokenizer.nextToken()));
                                }
                                stringTokenizer.nextToken();
                                glyph.xadvance = Integer.parseInt(stringTokenizer.nextToken());
                                if (stringTokenizer.hasMoreTokens()) {
                                    stringTokenizer.nextToken();
                                }
                                if (stringTokenizer.hasMoreTokens()) {
                                    try {
                                        glyph.page = Integer.parseInt(stringTokenizer.nextToken());
                                    } catch (NumberFormatException unused2) {
                                    }
                                }
                                if (glyph.width > 0 && glyph.height > 0) {
                                    this.descent = Math.min(glyph.yoffset + f8, this.descent);
                                }
                            }
                            bufferedReader2 = bufferedReader3;
                            z2 = false;
                        }
                        this.descent += this.padBottom;
                        while (true) {
                            line = bufferedReader2.readLine();
                            if (line == null || !line.startsWith("kerning ")) {
                                break;
                                break;
                            }
                            BufferedReader bufferedReader4 = bufferedReader2;
                            StringTokenizer stringTokenizer2 = new StringTokenizer(line, " =");
                            stringTokenizer2.nextToken();
                            stringTokenizer2.nextToken();
                            int i3 = Integer.parseInt(stringTokenizer2.nextToken());
                            stringTokenizer2.nextToken();
                            int i4 = Integer.parseInt(stringTokenizer2.nextToken());
                            if (i3 >= 0 && i3 <= 65535 && i4 >= 0 && i4 <= 65535) {
                                Glyph glyph2 = getGlyph((char) i3);
                                stringTokenizer2.nextToken();
                                int i5 = Integer.parseInt(stringTokenizer2.nextToken());
                                if (glyph2 != null) {
                                    glyph2.setKerning(i4, i5);
                                }
                            }
                            bufferedReader2 = bufferedReader4;
                            z2 = false;
                        }
                        if (line == null || !line.startsWith("metrics ")) {
                            z3 = z2;
                            f = 0.0f;
                            f2 = 0.0f;
                            f3 = 0.0f;
                            f4 = 0.0f;
                            f5 = 0.0f;
                            f6 = 0.0f;
                        } else {
                            StringTokenizer stringTokenizer3 = new StringTokenizer(line, " =");
                            stringTokenizer3.nextToken();
                            stringTokenizer3.nextToken();
                            float f10 = Float.parseFloat(stringTokenizer3.nextToken());
                            stringTokenizer3.nextToken();
                            f2 = Float.parseFloat(stringTokenizer3.nextToken());
                            stringTokenizer3.nextToken();
                            f3 = Float.parseFloat(stringTokenizer3.nextToken());
                            stringTokenizer3.nextToken();
                            f4 = Float.parseFloat(stringTokenizer3.nextToken());
                            stringTokenizer3.nextToken();
                            f5 = Float.parseFloat(stringTokenizer3.nextToken());
                            stringTokenizer3.nextToken();
                            f6 = Float.parseFloat(stringTokenizer3.nextToken());
                            stringTokenizer3.nextToken();
                            f = Float.parseFloat(stringTokenizer3.nextToken());
                            f9 = f10;
                        }
                        Glyph glyph3 = getGlyph(StringUtil.SPACE);
                        if (glyph3 == null) {
                            glyph3 = new Glyph();
                            glyph3.f94id = 32;
                            Glyph glyph4 = getGlyph('l');
                            if (glyph4 == null) {
                                glyph4 = getFirstGlyph();
                            }
                            glyph3.xadvance = glyph4.xadvance;
                            setGlyph(32, glyph3);
                        }
                        if (glyph3.width == 0) {
                            glyph3.width = (int) (this.padLeft + glyph3.xadvance + this.padRight);
                            glyph3.xoffset = (int) (-this.padLeft);
                        }
                        this.spaceXadvance = glyph3.xadvance;
                        char[] cArr = this.xChars;
                        int length = cArr.length;
                        int i6 = 0;
                        Glyph firstGlyph = null;
                        while (i6 < length) {
                            int i7 = length;
                            firstGlyph = getGlyph(cArr[i6]);
                            if (firstGlyph != null) {
                                break;
                            }
                            i6++;
                            length = i7;
                        }
                        if (firstGlyph == null) {
                            firstGlyph = getFirstGlyph();
                        }
                        this.xHeight = firstGlyph.height - f7;
                        char[] cArr2 = this.capChars;
                        int length2 = cArr2.length;
                        int i8 = 0;
                        Glyph glyph5 = null;
                        while (i8 < length2) {
                            int i9 = length2;
                            glyph5 = getGlyph(cArr2[i8]);
                            if (glyph5 != null) {
                                break;
                            }
                            i8++;
                            length2 = i9;
                        }
                        Glyph glyph6 = glyph5;
                        if (glyph6 == null) {
                            Glyph[][] glyphArr2 = this.glyphs;
                            int length3 = glyphArr2.length;
                            int i10 = 0;
                            while (i10 < length3) {
                                int i11 = length3;
                                Glyph[] glyphArr3 = glyphArr2[i10];
                                if (glyphArr3 != null) {
                                    int length4 = glyphArr3.length;
                                    int i12 = 0;
                                    while (i12 < length4) {
                                        int i13 = length4;
                                        Glyph glyph7 = glyphArr3[i12];
                                        if (glyph7 != null) {
                                            glyphArr = glyphArr3;
                                            if (glyph7.height != 0 && glyph7.width != 0) {
                                                this.capHeight = Math.max(this.capHeight, glyph7.height);
                                            }
                                        } else {
                                            glyphArr = glyphArr3;
                                        }
                                        i12++;
                                        length4 = i13;
                                        glyphArr3 = glyphArr;
                                    }
                                }
                                i10++;
                                length3 = i11;
                                glyphArr2 = glyphArr2;
                                bufferedReader2 = bufferedReader2;
                            }
                            bufferedReader = bufferedReader2;
                        } else {
                            bufferedReader = bufferedReader2;
                            this.capHeight = glyph6.height;
                        }
                        float f11 = this.capHeight - f7;
                        this.capHeight = f11;
                        float f12 = f8 - f11;
                        this.ascent = f12;
                        float f13 = -this.lineHeight;
                        this.down = f13;
                        if (z) {
                            this.ascent = -f12;
                            this.down = -f13;
                        }
                        if (z3) {
                            this.ascent = f9;
                            this.descent = f2;
                            this.down = f3;
                            this.capHeight = f4;
                            this.lineHeight = f5;
                            this.spaceXadvance = f6;
                            this.xHeight = f;
                        }
                        StreamUtils.closeQuietly(bufferedReader);
                    } catch (Exception e2) {
                        e = e2;
                        throw new GdxRuntimeException("Error loading font file: " + fileHandle, e);
                    }
                } catch (Throwable th) {
                    th = th;
                    Throwable th2 = th;
                    StreamUtils.closeQuietly(bufferedReader2);
                    throw th2;
                }
            } catch (Exception e3) {
                e = e3;
                throw new GdxRuntimeException("Error loading font file: " + fileHandle, e);
            } catch (Throwable th3) {
                th = th3;
                Throwable th4 = th;
                StreamUtils.closeQuietly(bufferedReader2);
                throw th4;
            }
        }

        /* JADX WARN: Code duplicated, block: B:14:0x006e A[PHI: r9 r15
          0x006e: PHI (r9v3 float) = (r9v2 float), (r9v4 float) binds: [B:7:0x004a, B:12:0x0063] A[DONT_GENERATE, DONT_INLINE]
          0x006e: PHI (r15v3 float) = (r15v2 float), (r15v9 float) binds: [B:7:0x004a, B:12:0x0063] A[DONT_GENERATE, DONT_INLINE]] */
        public void setGlyphRegion(Glyph glyph, TextureRegion textureRegion) {
            float f;
            float f2;
            Texture texture = textureRegion.getTexture();
            float width = 1.0f / texture.getWidth();
            float height = 1.0f / texture.getHeight();
            float f3 = textureRegion.u;
            float f4 = textureRegion.v;
            float regionWidth = textureRegion.getRegionWidth();
            float regionHeight = textureRegion.getRegionHeight();
            float f5 = 0.0f;
            if (textureRegion instanceof TextureAtlas.AtlasRegion) {
                TextureAtlas.AtlasRegion atlasRegion = (TextureAtlas.AtlasRegion) textureRegion;
                f = atlasRegion.offsetX;
                f2 = (atlasRegion.originalHeight - atlasRegion.packedHeight) - atlasRegion.offsetY;
            } else {
                f = 0.0f;
                f2 = 0.0f;
            }
            float f6 = glyph.srcX;
            float f7 = glyph.srcX + glyph.width;
            float f8 = glyph.srcY;
            float f9 = glyph.srcY + glyph.height;
            if (f > 0.0f) {
                f6 -= f;
                if (f6 < 0.0f) {
                    glyph.width = (int) (glyph.width + f6);
                    glyph.xoffset = (int) (glyph.xoffset - f6);
                    f6 = 0.0f;
                }
                f7 -= f;
                if (f7 > regionWidth) {
                    glyph.width = (int) (glyph.width - (f7 - regionWidth));
                } else {
                    regionWidth = f7;
                }
            } else {
                regionWidth = f7;
            }
            if (f2 > 0.0f) {
                float f10 = f8 - f2;
                if (f10 < 0.0f) {
                    glyph.height = (int) (glyph.height + f10);
                    if (glyph.height < 0) {
                        glyph.height = 0;
                    }
                } else {
                    f5 = f10;
                }
                f9 -= f2;
                if (f9 > regionHeight) {
                    float f11 = f9 - regionHeight;
                    glyph.height = (int) (glyph.height - f11);
                    glyph.yoffset = (int) (glyph.yoffset + f11);
                    f8 = f5;
                } else {
                    f8 = f5;
                    regionHeight = f9;
                }
            } else {
                regionHeight = f9;
            }
            glyph.u = (f6 * width) + f3;
            glyph.u2 = f3 + (regionWidth * width);
            if (this.flipped) {
                glyph.v = (f8 * height) + f4;
                glyph.v2 = f4 + (regionHeight * height);
            } else {
                glyph.v2 = (f8 * height) + f4;
                glyph.v = f4 + (regionHeight * height);
            }
        }

        public void setLineHeight(float f) {
            float f2 = f * this.scaleY;
            this.lineHeight = f2;
            if (!this.flipped) {
                f2 = -f2;
            }
            this.down = f2;
        }

        public void setGlyph(int i, Glyph glyph) {
            Glyph[][] glyphArr = this.glyphs;
            int i2 = i / 512;
            Glyph[] glyphArr2 = glyphArr[i2];
            if (glyphArr2 == null) {
                glyphArr2 = new Glyph[512];
                glyphArr[i2] = glyphArr2;
            }
            glyphArr2[i & 511] = glyph;
        }

        public Glyph getFirstGlyph() {
            for (Glyph[] glyphArr : this.glyphs) {
                if (glyphArr != null) {
                    for (Glyph glyph : glyphArr) {
                        if (glyph != null && glyph.height != 0 && glyph.width != 0) {
                            return glyph;
                        }
                    }
                }
            }
            throw new GdxRuntimeException("No glyphs found.");
        }

        public boolean hasGlyph(char c) {
            return (this.missingGlyph == null && getGlyph(c) == null) ? false : true;
        }

        public Glyph getGlyph(char c) {
            Glyph[] glyphArr = this.glyphs[c / 512];
            if (glyphArr != null) {
                return glyphArr[c & 511];
            }
            return null;
        }

        public void getGlyphs(GlyphLayout.GlyphRun glyphRun, CharSequence charSequence, int i, int i2, Glyph glyph) {
            Glyph glyph2;
            float kerning;
            int i3 = i2 - i;
            if (i3 == 0) {
                return;
            }
            boolean z = this.markupEnabled;
            float f = this.scaleX;
            Array<Glyph> array = glyphRun.glyphs;
            FloatArray floatArray = glyphRun.xAdvances;
            array.ensureCapacity(i3);
            glyphRun.xAdvances.ensureCapacity(i3 + 1);
            do {
                int i4 = i + 1;
                char cCharAt = charSequence.charAt(i);
                if (cCharAt == '\r' || ((glyph2 = getGlyph(cCharAt)) == null && (glyph2 = this.missingGlyph) == null)) {
                    i = i4;
                } else {
                    array.add(glyph2);
                    if (glyph == null) {
                        kerning = glyph2.fixedWidth ? 0.0f : ((-glyph2.xoffset) * f) - this.padLeft;
                    } else {
                        kerning = (glyph.xadvance + glyph.getKerning(cCharAt)) * f;
                    }
                    floatArray.add(kerning);
                    i = (z && cCharAt == '[' && i4 < i2 && charSequence.charAt(i4) == '[') ? i + 2 : i4;
                    glyph = glyph2;
                }
            } while (i < i2);
            if (glyph != null) {
                floatArray.add(glyph.fixedWidth ? glyph.xadvance * f : ((glyph.width + glyph.xoffset) * f) - this.padRight);
            }
        }

        public int getWrapIndex(Array<Glyph> array, int i) {
            int i2 = i - 1;
            Glyph[] glyphArr = array.items;
            char c = (char) glyphArr[i2].f94id;
            if (isWhitespace(c)) {
                return i2;
            }
            if (isBreakChar(c)) {
                i2 = i - 2;
            }
            while (i2 > 0) {
                char c2 = (char) glyphArr[i2].f94id;
                if (isWhitespace(c2) || isBreakChar(c2)) {
                    return i2 + 1;
                }
                i2--;
            }
            return 0;
        }

        public boolean isBreakChar(char c) {
            char[] cArr = this.breakChars;
            if (cArr == null) {
                return false;
            }
            for (char c2 : cArr) {
                if (c == c2) {
                    return true;
                }
            }
            return false;
        }

        public String getImagePath(int i) {
            return this.imagePaths[i];
        }

        public String[] getImagePaths() {
            return this.imagePaths;
        }

        public FileHandle getFontFile() {
            return this.fontFile;
        }

        public void setScale(float f, float f2) {
            if (f == 0.0f) {
                throw new IllegalArgumentException("scaleX cannot be 0.");
            }
            if (f2 == 0.0f) {
                throw new IllegalArgumentException("scaleY cannot be 0.");
            }
            float f3 = f / this.scaleX;
            float f4 = f2 / this.scaleY;
            this.lineHeight *= f4;
            this.spaceXadvance *= f3;
            this.xHeight *= f4;
            this.capHeight *= f4;
            this.ascent *= f4;
            this.descent *= f4;
            this.down *= f4;
            this.padLeft *= f3;
            this.padRight *= f3;
            this.padTop *= f4;
            this.padBottom *= f4;
            this.scaleX = f;
            this.scaleY = f2;
        }

        public void setScale(float f) {
            setScale(f, f);
        }

        public void scale(float f) {
            setScale(this.scaleX + f, this.scaleY + f);
        }

        public String toString() {
            String str = this.f93name;
            return str != null ? str : super.toString();
        }
    }
}
