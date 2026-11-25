package org.catrobat.catroid.embroidery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/DSTFileGenerator.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DSTFileGenerator {
    private EmbroideryStream stream;

    public DSTFileGenerator(EmbroideryStream stream) {
        this.stream = stream;
    }

    public void writeToDSTFile(File dstFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
        try {
            this.stream.getHeader().appendToStream(fileOutputStream);
            writeStitchPoints(fileOutputStream);
            fileOutputStream.write(DSTFileConstants.FILE_END);
            fileOutputStream.close();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void writeStitchPoints(FileOutputStream fileStream) throws IOException {
        Iterator<StitchPoint> it = this.stream.getPointList().iterator();
        while (it.hasNext()) {
            it.next().appendToStream(fileStream);
        }
    }
}
