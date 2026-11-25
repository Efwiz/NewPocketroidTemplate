package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import java.io.Writer;
import java.util.regex.Matcher;
import org.catrobat.catroid.common.Constants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPackerIO.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PixmapPackerIO {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPackerIO$SaveParameters.class
      classes8.dex
     */
    public static class SaveParameters {
        public boolean useIndexes;
        public ImageFormat format = ImageFormat.PNG;
        public Texture.TextureFilter minFilter = Texture.TextureFilter.Nearest;
        public Texture.TextureFilter magFilter = Texture.TextureFilter.Nearest;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPackerIO$ImageFormat.class
      classes8.dex
     */
    public enum ImageFormat {
        CIM(".cim"),
        PNG(Constants.DEFAULT_IMAGE_EXTENSION);

        private final String extension;

        public String getExtension() {
            return this.extension;
        }

        ImageFormat(String str) {
            this.extension = str;
        }
    }

    public void save(FileHandle fileHandle, PixmapPacker pixmapPacker) throws Throwable {
        save(fileHandle, pixmapPacker, new SaveParameters());
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0132  */
    public void save(FileHandle fileHandle, PixmapPacker pixmapPacker, SaveParameters saveParameters) throws Throwable {
        int i;
        String strGroup;
        int i2;
        int i3;
        Writer writer = fileHandle.writer(false);
        Array.ArrayIterator<PixmapPacker.Page> it = pixmapPacker.pages.iterator();
        int i4 = 0;
        while (it.hasNext()) {
            PixmapPacker.Page next = it.next();
            if (next.rects.size > 0) {
                i4++;
                FileHandle fileHandleSibling = fileHandle.sibling(fileHandle.nameWithoutExtension() + "_" + i4 + saveParameters.format.getExtension());
                int i5 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$graphics$g2d$PixmapPackerIO$ImageFormat[saveParameters.format.ordinal()];
                int i6 = 2;
                int i7 = 1;
                if (i5 == 1) {
                    PixmapIO.writeCIM(fileHandleSibling, next.image);
                } else if (i5 == 2) {
                    PixmapIO.writePNG(fileHandleSibling, next.image);
                }
                writer.write("\n");
                writer.write(fileHandleSibling.name() + "\n");
                writer.write("size: " + next.image.getWidth() + "," + next.image.getHeight() + "\n");
                writer.write("format: " + pixmapPacker.pageFormat.name() + "\n");
                writer.write("filter: " + saveParameters.minFilter.name() + "," + saveParameters.magFilter.name() + "\n");
                writer.write("repeat: none\n");
                ObjectMap.Keys<String> it2 = next.rects.keys().iterator();
                while (it2.hasNext()) {
                    String next2 = it2.next();
                    if (saveParameters.useIndexes) {
                        Matcher matcher = PixmapPacker.indexPattern.matcher(next2);
                        if (matcher.matches()) {
                            strGroup = matcher.group(i7);
                            i = Integer.parseInt(matcher.group(i6));
                        } else {
                            i = -1;
                            strGroup = next2;
                        }
                    } else {
                        i = -1;
                        strGroup = next2;
                    }
                    writer.write(strGroup + "\n");
                    PixmapPacker.PixmapPackerRectangle pixmapPackerRectangle = next.rects.get(next2);
                    writer.write("  rotate: false\n");
                    writer.write("  xy: " + ((int) pixmapPackerRectangle.x) + "," + ((int) pixmapPackerRectangle.y) + "\n");
                    writer.write("  size: " + ((int) pixmapPackerRectangle.width) + "," + ((int) pixmapPackerRectangle.height) + "\n");
                    if (pixmapPackerRectangle.splits != null) {
                        writer.write("  split: " + pixmapPackerRectangle.splits[0] + ", " + pixmapPackerRectangle.splits[1] + ", " + pixmapPackerRectangle.splits[2] + ", " + pixmapPackerRectangle.splits[3] + "\n");
                        if (pixmapPackerRectangle.pads != null) {
                            i2 = 1;
                            i3 = 2;
                            writer.write("  pad: " + pixmapPackerRectangle.pads[0] + ", " + pixmapPackerRectangle.pads[1] + ", " + pixmapPackerRectangle.pads[2] + ", " + pixmapPackerRectangle.pads[3] + "\n");
                        } else {
                            i3 = 2;
                            i2 = 1;
                        }
                    } else {
                        i2 = i7;
                        i3 = 2;
                    }
                    writer.write("  orig: " + pixmapPackerRectangle.originalWidth + ", " + pixmapPackerRectangle.originalHeight + "\n");
                    writer.write("  offset: " + pixmapPackerRectangle.offsetX + ", " + ((int) ((pixmapPackerRectangle.originalHeight - pixmapPackerRectangle.height) - pixmapPackerRectangle.offsetY)) + "\n");
                    writer.write("  index: " + i + "\n");
                    i6 = i3;
                    i7 = i2;
                }
            }
        }
        writer.close();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PixmapPackerIO$1.class
      classes8.dex
     */
    /* JADX INFO: renamed from: com.badlogic.gdx.graphics.g2d.PixmapPackerIO$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$graphics$g2d$PixmapPackerIO$ImageFormat;

        static {
            int[] iArr = new int[ImageFormat.values().length];
            $SwitchMap$com$badlogic$gdx$graphics$g2d$PixmapPackerIO$ImageFormat = iArr;
            try {
                iArr[ImageFormat.CIM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$g2d$PixmapPackerIO$ImageFormat[ImageFormat.PNG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
