package ar.com.hjg.pngj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
final class PngHelperInternal2 {
    PngHelperInternal2() {
    }

    static OutputStream ostreamFromFile(File file, boolean z) {
        if (file.exists() && !z) {
            throw new PngjOutputException("File already exists: " + file);
        }
        try {
            return new FileOutputStream(file);
        } catch (Exception e) {
            throw new PngjInputException("Could not open for write" + file, e);
        }
    }
}
