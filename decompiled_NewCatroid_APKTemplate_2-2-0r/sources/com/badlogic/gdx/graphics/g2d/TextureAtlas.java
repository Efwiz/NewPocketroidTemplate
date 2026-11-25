package com.badlogic.gdx.graphics.g2d;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.util.Comparator;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/TextureAtlas.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TextureAtlas implements Disposable {
    private final Array<AtlasRegion> regions;
    private final ObjectSet<Texture> textures;

    public TextureAtlas() {
        this.textures = new ObjectSet<>(4);
        this.regions = new Array<>();
    }

    public TextureAtlas(String str) {
        this(Gdx.files.internal(str));
    }

    public TextureAtlas(FileHandle fileHandle) {
        this(fileHandle, fileHandle.parent());
    }

    public TextureAtlas(FileHandle fileHandle, boolean z) {
        this(fileHandle, fileHandle.parent(), z);
    }

    public TextureAtlas(FileHandle fileHandle, FileHandle fileHandle2) {
        this(fileHandle, fileHandle2, false);
    }

    public TextureAtlas(FileHandle fileHandle, FileHandle fileHandle2, boolean z) {
        this(new TextureAtlasData(fileHandle, fileHandle2, z));
    }

    public TextureAtlas(TextureAtlasData textureAtlasData) {
        this.textures = new ObjectSet<>(4);
        this.regions = new Array<>();
        load(textureAtlasData);
    }

    public void load(TextureAtlasData textureAtlasData) {
        this.textures.ensureCapacity(textureAtlasData.pages.size);
        Array.ArrayIterator<TextureAtlasData.Page> it = textureAtlasData.pages.iterator();
        while (it.hasNext()) {
            TextureAtlasData.Page next = it.next();
            if (next.texture == null) {
                next.texture = new Texture(next.textureFile, next.format, next.useMipMaps);
            }
            next.texture.setFilter(next.minFilter, next.magFilter);
            next.texture.setWrap(next.uWrap, next.vWrap);
            this.textures.add(next.texture);
        }
        this.regions.ensureCapacity(textureAtlasData.regions.size);
        Array.ArrayIterator<TextureAtlasData.Region> it2 = textureAtlasData.regions.iterator();
        while (it2.hasNext()) {
            TextureAtlasData.Region next2 = it2.next();
            AtlasRegion atlasRegion = new AtlasRegion(next2.page.texture, next2.left, next2.top, next2.rotate ? next2.height : next2.width, next2.rotate ? next2.width : next2.height);
            atlasRegion.index = next2.index;
            atlasRegion.f97name = next2.f99name;
            atlasRegion.offsetX = next2.offsetX;
            atlasRegion.offsetY = next2.offsetY;
            atlasRegion.originalHeight = next2.originalHeight;
            atlasRegion.originalWidth = next2.originalWidth;
            atlasRegion.rotate = next2.rotate;
            atlasRegion.degrees = next2.degrees;
            atlasRegion.names = next2.names;
            atlasRegion.values = next2.values;
            if (next2.flip) {
                atlasRegion.flip(false, true);
            }
            this.regions.add(atlasRegion);
        }
    }

    public AtlasRegion addRegion(String str, Texture texture, int i, int i2, int i3, int i4) {
        this.textures.add(texture);
        AtlasRegion atlasRegion = new AtlasRegion(texture, i, i2, i3, i4);
        atlasRegion.f97name = str;
        this.regions.add(atlasRegion);
        return atlasRegion;
    }

    public AtlasRegion addRegion(String str, TextureRegion textureRegion) {
        this.textures.add(textureRegion.texture);
        AtlasRegion atlasRegion = new AtlasRegion(textureRegion);
        atlasRegion.f97name = str;
        this.regions.add(atlasRegion);
        return atlasRegion;
    }

    public Array<AtlasRegion> getRegions() {
        return this.regions;
    }

    public AtlasRegion findRegion(String str) {
        int i = this.regions.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.regions.get(i2).f97name.equals(str)) {
                return this.regions.get(i2);
            }
        }
        return null;
    }

    public AtlasRegion findRegion(String str, int i) {
        int i2 = this.regions.size;
        for (int i3 = 0; i3 < i2; i3++) {
            AtlasRegion atlasRegion = this.regions.get(i3);
            if (atlasRegion.f97name.equals(str) && atlasRegion.index == i) {
                return atlasRegion;
            }
        }
        return null;
    }

    public Array<AtlasRegion> findRegions(String str) {
        Array<AtlasRegion> array = new Array<>(AtlasRegion.class);
        int i = this.regions.size;
        for (int i2 = 0; i2 < i; i2++) {
            AtlasRegion atlasRegion = this.regions.get(i2);
            if (atlasRegion.f97name.equals(str)) {
                array.add(new AtlasRegion(atlasRegion));
            }
        }
        return array;
    }

    public Array<Sprite> createSprites() {
        Array<Sprite> array = new Array<>(true, this.regions.size, Sprite.class);
        int i = this.regions.size;
        for (int i2 = 0; i2 < i; i2++) {
            array.add(newSprite(this.regions.get(i2)));
        }
        return array;
    }

    public Sprite createSprite(String str) {
        int i = this.regions.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.regions.get(i2).f97name.equals(str)) {
                return newSprite(this.regions.get(i2));
            }
        }
        return null;
    }

    public Sprite createSprite(String str, int i) {
        int i2 = this.regions.size;
        for (int i3 = 0; i3 < i2; i3++) {
            AtlasRegion atlasRegion = this.regions.get(i3);
            if (atlasRegion.index == i && atlasRegion.f97name.equals(str)) {
                return newSprite(this.regions.get(i3));
            }
        }
        return null;
    }

    public Array<Sprite> createSprites(String str) {
        Array<Sprite> array = new Array<>(Sprite.class);
        int i = this.regions.size;
        for (int i2 = 0; i2 < i; i2++) {
            AtlasRegion atlasRegion = this.regions.get(i2);
            if (atlasRegion.f97name.equals(str)) {
                array.add(newSprite(atlasRegion));
            }
        }
        return array;
    }

    private Sprite newSprite(AtlasRegion atlasRegion) {
        if (atlasRegion.packedWidth == atlasRegion.originalWidth && atlasRegion.packedHeight == atlasRegion.originalHeight) {
            if (atlasRegion.rotate) {
                Sprite sprite = new Sprite(atlasRegion);
                sprite.setBounds(0.0f, 0.0f, atlasRegion.getRegionHeight(), atlasRegion.getRegionWidth());
                sprite.rotate90(true);
                return sprite;
            }
            return new Sprite(atlasRegion);
        }
        return new AtlasSprite(atlasRegion);
    }

    public NinePatch createPatch(String str) {
        int i = this.regions.size;
        for (int i2 = 0; i2 < i; i2++) {
            AtlasRegion atlasRegion = this.regions.get(i2);
            if (atlasRegion.f97name.equals(str)) {
                int[] iArrFindValue = atlasRegion.findValue("split");
                if (iArrFindValue == null) {
                    throw new IllegalArgumentException("Region does not have ninepatch splits: " + str);
                }
                NinePatch ninePatch = new NinePatch(atlasRegion, iArrFindValue[0], iArrFindValue[1], iArrFindValue[2], iArrFindValue[3]);
                int[] iArrFindValue2 = atlasRegion.findValue("pad");
                if (iArrFindValue2 != null) {
                    ninePatch.setPadding(iArrFindValue2[0], iArrFindValue2[1], iArrFindValue2[2], iArrFindValue2[3]);
                }
                return ninePatch;
            }
        }
        return null;
    }

    public ObjectSet<Texture> getTextures() {
        return this.textures;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        ObjectSet.ObjectSetIterator<Texture> it = this.textures.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.textures.clear(0);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/TextureAtlas$TextureAtlasData.class
      classes8.dex
     */
    public static class TextureAtlasData {
        final Array<Page> pages = new Array<>();
        final Array<Region> regions = new Array<>();

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/TextureAtlas$TextureAtlasData$Field.class
          classes8.dex
         */
        private interface Field<T> {
            void parse(T t);
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/TextureAtlas$TextureAtlasData$Page.class
          classes8.dex
         */
        public static class Page {
            public float height;

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            public String f98name;
            public boolean pma;
            public Texture texture;
            public FileHandle textureFile;
            public boolean useMipMaps;
            public float width;
            public Pixmap.Format format = Pixmap.Format.RGBA8888;
            public Texture.TextureFilter minFilter = Texture.TextureFilter.Nearest;
            public Texture.TextureFilter magFilter = Texture.TextureFilter.Nearest;
            public Texture.TextureWrap uWrap = Texture.TextureWrap.ClampToEdge;
            public Texture.TextureWrap vWrap = Texture.TextureWrap.ClampToEdge;
        }

        public TextureAtlasData() {
        }

        public TextureAtlasData(FileHandle fileHandle, FileHandle fileHandle2, boolean z) {
            load(fileHandle, fileHandle2, z);
        }

        public void load(FileHandle fileHandle, FileHandle fileHandle2, boolean z) {
            String str;
            final String[] strArr = new String[5];
            ObjectMap objectMap = new ObjectMap(15, 0.99f);
            objectMap.put("size", new Field<Page>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.1
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Page page) {
                    page.width = Integer.parseInt(strArr[1]);
                    page.height = Integer.parseInt(strArr[2]);
                }
            });
            objectMap.put(ConfigConstants.CONFIG_KEY_FORMAT, new Field<Page>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.2
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Page page) {
                    page.format = Pixmap.Format.valueOf(strArr[1]);
                }
            });
            objectMap.put("filter", new Field<Page>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.3
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Page page) {
                    page.minFilter = Texture.TextureFilter.valueOf(strArr[1]);
                    page.magFilter = Texture.TextureFilter.valueOf(strArr[2]);
                    page.useMipMaps = page.minFilter.isMipMap();
                }
            });
            objectMap.put("repeat", new Field<Page>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.4
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Page page) {
                    if (strArr[1].indexOf(120) != -1) {
                        page.uWrap = Texture.TextureWrap.Repeat;
                    }
                    if (strArr[1].indexOf(121) != -1) {
                        page.vWrap = Texture.TextureWrap.Repeat;
                    }
                }
            });
            objectMap.put("pma", new Field<Page>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.5
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Page page) {
                    page.pma = strArr[1].equals("true");
                }
            });
            boolean z2 = true;
            int i = 0;
            final boolean[] zArr = {false};
            ObjectMap objectMap2 = new ObjectMap(127, 0.99f);
            objectMap2.put("xy", new Field<Region>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.6
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Region region) {
                    region.left = Integer.parseInt(strArr[1]);
                    region.top = Integer.parseInt(strArr[2]);
                }
            });
            objectMap2.put("size", new Field<Region>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.7
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Region region) {
                    region.width = Integer.parseInt(strArr[1]);
                    region.height = Integer.parseInt(strArr[2]);
                }
            });
            objectMap2.put("bounds", new Field<Region>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.8
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Region region) {
                    region.left = Integer.parseInt(strArr[1]);
                    region.top = Integer.parseInt(strArr[2]);
                    region.width = Integer.parseInt(strArr[3]);
                    region.height = Integer.parseInt(strArr[4]);
                }
            });
            objectMap2.put(TypedValues.CycleType.S_WAVE_OFFSET, new Field<Region>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.9
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Region region) {
                    region.offsetX = Integer.parseInt(strArr[1]);
                    region.offsetY = Integer.parseInt(strArr[2]);
                }
            });
            objectMap2.put("orig", new Field<Region>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.10
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Region region) {
                    region.originalWidth = Integer.parseInt(strArr[1]);
                    region.originalHeight = Integer.parseInt(strArr[2]);
                }
            });
            objectMap2.put("offsets", new Field<Region>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.11
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Region region) {
                    region.offsetX = Integer.parseInt(strArr[1]);
                    region.offsetY = Integer.parseInt(strArr[2]);
                    region.originalWidth = Integer.parseInt(strArr[3]);
                    region.originalHeight = Integer.parseInt(strArr[4]);
                }
            });
            objectMap2.put("rotate", new Field<Region>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.12
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Region region) {
                    String str2 = strArr[1];
                    if (str2.equals("true")) {
                        region.degrees = 90;
                    } else if (!str2.equals("false")) {
                        region.degrees = Integer.parseInt(str2);
                    }
                    region.rotate = region.degrees == 90;
                }
            });
            objectMap2.put("index", new Field<Region>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.13
                @Override // com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Field
                public void parse(Region region) {
                    region.index = Integer.parseInt(strArr[1]);
                    if (region.index != -1) {
                        zArr[0] = true;
                    }
                }
            });
            BufferedReader erVar = fileHandle.reader(1024);
            try {
                try {
                    String line = erVar.readLine();
                    while (line != null) {
                        try {
                            if (line.trim().length() != 0) {
                                break;
                            } else {
                                line = erVar.readLine();
                            }
                        } catch (Exception e) {
                            e = e;
                            str = line;
                            throw new GdxRuntimeException("Error reading texture atlas file: " + fileHandle + (str == null ? "" : "\nLine: " + str), e);
                        }
                    }
                    while (line != null && line.trim().length() != 0 && readEntry(strArr, line) != 0) {
                        line = erVar.readLine();
                    }
                    Page page = null;
                    Array array = null;
                    Array array2 = null;
                    while (line != null) {
                        if (line.trim().length() == 0) {
                            line = erVar.readLine();
                            page = null;
                        } else if (page == null) {
                            page = new Page();
                            page.f98name = line;
                            page.textureFile = fileHandle2.child(line);
                            while (true) {
                                line = erVar.readLine();
                                if (readEntry(strArr, line) == 0) {
                                    break;
                                }
                                Field field = (Field) objectMap.get(strArr[i]);
                                if (field != null) {
                                    field.parse(page);
                                }
                            }
                            this.pages.add(page);
                        } else {
                            Region region = new Region();
                            region.page = page;
                            region.f99name = line.trim();
                            if (z) {
                                region.flip = z2;
                            }
                            while (true) {
                                line = erVar.readLine();
                                int entry = readEntry(strArr, line);
                                if (entry == 0) {
                                    break;
                                }
                                Field field2 = (Field) objectMap2.get(strArr[i]);
                                if (field2 != null) {
                                    field2.parse(region);
                                } else {
                                    if (array == null) {
                                        array = new Array(8);
                                        array2 = new Array(8);
                                    }
                                    array.add(strArr[i]);
                                    int[] iArr = new int[entry];
                                    while (i < entry) {
                                        int i2 = i + 1;
                                        try {
                                            iArr[i] = Integer.parseInt(strArr[i2]);
                                        } catch (NumberFormatException unused) {
                                        }
                                        i = i2;
                                    }
                                    array2.add(iArr);
                                }
                                z2 = true;
                                i = 0;
                            }
                            if (region.originalWidth == 0 && region.originalHeight == 0) {
                                region.originalWidth = region.width;
                                region.originalHeight = region.height;
                            }
                            if (array != null && array.size > 0) {
                                region.names = (String[]) array.toArray(String.class);
                                region.values = (int[][]) array2.toArray(int[].class);
                                array.clear();
                                array2.clear();
                            }
                            this.regions.add(region);
                        }
                    }
                    StreamUtils.closeQuietly(erVar);
                    if (zArr[i]) {
                        this.regions.sort(new Comparator<Region>() { // from class: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.14
                            @Override // java.util.Comparator
                            public int compare(Region region2, Region region3) {
                                int i3 = region2.index;
                                if (i3 == -1) {
                                    i3 = Integer.MAX_VALUE;
                                }
                                int i4 = region3.index;
                                return i3 - (i4 != -1 ? i4 : Integer.MAX_VALUE);
                            }
                        });
                    }
                } catch (Exception e2) {
                    e = e2;
                    str = null;
                }
            } catch (Throwable th) {
                StreamUtils.closeQuietly(erVar);
                throw th;
            }
        }

        public Array<Page> getPages() {
            return this.pages;
        }

        public Array<Region> getRegions() {
            return this.regions;
        }

        private static int readEntry(String[] strArr, String str) {
            int iIndexOf;
            if (str == null) {
                return 0;
            }
            String strTrim = str.trim();
            if (strTrim.length() == 0 || (iIndexOf = strTrim.indexOf(58)) == -1) {
                return 0;
            }
            strArr[0] = strTrim.substring(0, iIndexOf).trim();
            int i = 1;
            int i2 = iIndexOf + 1;
            while (true) {
                int iIndexOf2 = strTrim.indexOf(44, i2);
                if (iIndexOf2 == -1) {
                    strArr[i] = strTrim.substring(i2).trim();
                    return i;
                }
                strArr[i] = strTrim.substring(i2, iIndexOf2).trim();
                i2 = iIndexOf2 + 1;
                if (i == 4) {
                    return 4;
                }
                i++;
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/TextureAtlas$TextureAtlasData$Region.class
          classes8.dex
         */
        public static class Region {
            public int degrees;
            public boolean flip;
            public int height;
            public int index = -1;
            public int left;

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            public String f99name;
            public String[] names;
            public float offsetX;
            public float offsetY;
            public int originalHeight;
            public int originalWidth;
            public Page page;
            public boolean rotate;
            public int top;
            public int[][] values;
            public int width;

            public int[] findValue(String str) {
                String[] strArr = this.names;
                if (strArr == null) {
                    return null;
                }
                int length = strArr.length;
                for (int i = 0; i < length; i++) {
                    if (str.equals(this.names[i])) {
                        return this.values[i];
                    }
                }
                return null;
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/TextureAtlas$AtlasRegion.class
      classes8.dex
     */
    public static class AtlasRegion extends TextureRegion {
        public int degrees;
        public int index;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f97name;
        public String[] names;
        public float offsetX;
        public float offsetY;
        public int originalHeight;
        public int originalWidth;
        public int packedHeight;
        public int packedWidth;
        public boolean rotate;
        public int[][] values;

        public AtlasRegion(Texture texture, int i, int i2, int i3, int i4) {
            super(texture, i, i2, i3, i4);
            this.index = -1;
            this.originalWidth = i3;
            this.originalHeight = i4;
            this.packedWidth = i3;
            this.packedHeight = i4;
        }

        public AtlasRegion(AtlasRegion atlasRegion) {
            this.index = -1;
            setRegion(atlasRegion);
            this.index = atlasRegion.index;
            this.f97name = atlasRegion.f97name;
            this.offsetX = atlasRegion.offsetX;
            this.offsetY = atlasRegion.offsetY;
            this.packedWidth = atlasRegion.packedWidth;
            this.packedHeight = atlasRegion.packedHeight;
            this.originalWidth = atlasRegion.originalWidth;
            this.originalHeight = atlasRegion.originalHeight;
            this.rotate = atlasRegion.rotate;
            this.degrees = atlasRegion.degrees;
            this.names = atlasRegion.names;
            this.values = atlasRegion.values;
        }

        public AtlasRegion(TextureRegion textureRegion) {
            this.index = -1;
            setRegion(textureRegion);
            this.packedWidth = textureRegion.getRegionWidth();
            int regionHeight = textureRegion.getRegionHeight();
            this.packedHeight = regionHeight;
            this.originalWidth = this.packedWidth;
            this.originalHeight = regionHeight;
        }

        @Override // com.badlogic.gdx.graphics.g2d.TextureRegion
        public void flip(boolean z, boolean z2) {
            super.flip(z, z2);
            if (z) {
                this.offsetX = (this.originalWidth - this.offsetX) - getRotatedPackedWidth();
            }
            if (z2) {
                this.offsetY = (this.originalHeight - this.offsetY) - getRotatedPackedHeight();
            }
        }

        public float getRotatedPackedWidth() {
            return this.rotate ? this.packedHeight : this.packedWidth;
        }

        public float getRotatedPackedHeight() {
            return this.rotate ? this.packedWidth : this.packedHeight;
        }

        public int[] findValue(String str) {
            String[] strArr = this.names;
            if (strArr == null) {
                return null;
            }
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                if (str.equals(this.names[i])) {
                    return this.values[i];
                }
            }
            return null;
        }

        public String toString() {
            return this.f97name;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/TextureAtlas$AtlasSprite.class
      classes8.dex
     */
    public static class AtlasSprite extends Sprite {
        float originalOffsetX;
        float originalOffsetY;
        final AtlasRegion region;

        public AtlasSprite(AtlasRegion atlasRegion) {
            this.region = new AtlasRegion(atlasRegion);
            this.originalOffsetX = atlasRegion.offsetX;
            this.originalOffsetY = atlasRegion.offsetY;
            setRegion(atlasRegion);
            setOrigin(atlasRegion.originalWidth / 2.0f, atlasRegion.originalHeight / 2.0f);
            int regionWidth = atlasRegion.getRegionWidth();
            int regionHeight = atlasRegion.getRegionHeight();
            if (atlasRegion.rotate) {
                super.rotate90(true);
                super.setBounds(atlasRegion.offsetX, atlasRegion.offsetY, regionHeight, regionWidth);
            } else {
                super.setBounds(atlasRegion.offsetX, atlasRegion.offsetY, regionWidth, regionHeight);
            }
            setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        public AtlasSprite(AtlasSprite atlasSprite) {
            this.region = atlasSprite.region;
            this.originalOffsetX = atlasSprite.originalOffsetX;
            this.originalOffsetY = atlasSprite.originalOffsetY;
            set(atlasSprite);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public void setPosition(float f, float f2) {
            super.setPosition(f + this.region.offsetX, f2 + this.region.offsetY);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public void setX(float f) {
            super.setX(f + this.region.offsetX);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public void setY(float f) {
            super.setY(f + this.region.offsetY);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public void setBounds(float f, float f2, float f3, float f4) {
            float f5 = f3 / this.region.originalWidth;
            float f6 = f4 / this.region.originalHeight;
            this.region.offsetX = this.originalOffsetX * f5;
            this.region.offsetY = this.originalOffsetY * f6;
            super.setBounds(f + this.region.offsetX, f2 + this.region.offsetY, (this.region.rotate ? this.region.packedHeight : this.region.packedWidth) * f5, (this.region.rotate ? this.region.packedWidth : this.region.packedHeight) * f6);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public void setSize(float f, float f2) {
            setBounds(getX(), getY(), f, f2);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public void setOrigin(float f, float f2) {
            super.setOrigin(f - this.region.offsetX, f2 - this.region.offsetY);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public void setOriginCenter() {
            super.setOrigin((this.width / 2.0f) - this.region.offsetX, (this.height / 2.0f) - this.region.offsetY);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite, com.badlogic.gdx.graphics.g2d.TextureRegion
        public void flip(boolean z, boolean z2) {
            if (this.region.rotate) {
                super.flip(z2, z);
            } else {
                super.flip(z, z2);
            }
            float originX = getOriginX();
            float originY = getOriginY();
            float f = this.region.offsetX;
            float f2 = this.region.offsetY;
            float widthRatio = getWidthRatio();
            float heightRatio = getHeightRatio();
            this.region.offsetX = this.originalOffsetX;
            this.region.offsetY = this.originalOffsetY;
            this.region.flip(z, z2);
            this.originalOffsetX = this.region.offsetX;
            this.originalOffsetY = this.region.offsetY;
            this.region.offsetX *= widthRatio;
            this.region.offsetY *= heightRatio;
            translate(this.region.offsetX - f, this.region.offsetY - f2);
            setOrigin(originX, originY);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public void rotate90(boolean z) {
            super.rotate90(z);
            float originX = getOriginX();
            float originY = getOriginY();
            float f = this.region.offsetX;
            float f2 = this.region.offsetY;
            float widthRatio = getWidthRatio();
            float heightRatio = getHeightRatio();
            if (z) {
                this.region.offsetX = f2;
                AtlasRegion atlasRegion = this.region;
                atlasRegion.offsetY = ((atlasRegion.originalHeight * heightRatio) - f) - (this.region.packedWidth * widthRatio);
            } else {
                AtlasRegion atlasRegion2 = this.region;
                atlasRegion2.offsetX = ((atlasRegion2.originalWidth * widthRatio) - f2) - (this.region.packedHeight * heightRatio);
                this.region.offsetY = f;
            }
            translate(this.region.offsetX - f, this.region.offsetY - f2);
            setOrigin(originX, originY);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public float getX() {
            return super.getX() - this.region.offsetX;
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public float getY() {
            return super.getY() - this.region.offsetY;
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public float getOriginX() {
            return super.getOriginX() + this.region.offsetX;
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public float getOriginY() {
            return super.getOriginY() + this.region.offsetY;
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public float getWidth() {
            return (super.getWidth() / this.region.getRotatedPackedWidth()) * this.region.originalWidth;
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public float getHeight() {
            return (super.getHeight() / this.region.getRotatedPackedHeight()) * this.region.originalHeight;
        }

        public float getWidthRatio() {
            return super.getWidth() / this.region.getRotatedPackedWidth();
        }

        public float getHeightRatio() {
            return super.getHeight() / this.region.getRotatedPackedHeight();
        }

        public AtlasRegion getAtlasRegion() {
            return this.region;
        }

        public String toString() {
            return this.region.toString();
        }
    }
}
