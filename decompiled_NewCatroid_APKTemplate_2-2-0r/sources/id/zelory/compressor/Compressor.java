package id.zelory.compressor;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import io.reactivex.Flowable;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes11.dex */
public class Compressor {
    private String destinationDirectoryPath;
    private int maxWidth = TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR_ID;
    private int maxHeight = 816;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private int quality = 80;

    public Compressor(Context context) {
        this.destinationDirectoryPath = context.getCacheDir().getPath() + File.separator + Constants.IMAGE_DIRECTORY_NAME;
    }

    public Compressor setMaxWidth(int i) {
        this.maxWidth = i;
        return this;
    }

    public Compressor setMaxHeight(int i) {
        this.maxHeight = i;
        return this;
    }

    public Compressor setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    public Compressor setQuality(int i) {
        this.quality = i;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String str) {
        this.destinationDirectoryPath = str;
        return this;
    }

    public File compressToFile(File file) throws IOException {
        return compressToFile(file, file.getName());
    }

    public File compressToFile(File file, String str) throws IOException {
        return ImageUtil.compressImage(file, this.maxWidth, this.maxHeight, this.compressFormat, this.quality, this.destinationDirectoryPath + File.separator + str);
    }

    public Bitmap compressToBitmap(File file) throws IOException {
        return ImageUtil.decodeSampledBitmapFromFile(file, this.maxWidth, this.maxHeight);
    }

    public Flowable<File> compressToFileAsFlowable(File file) {
        return compressToFileAsFlowable(file, file.getName());
    }

    public Flowable<File> compressToFileAsFlowable(final File file, final String str) {
        return Flowable.defer(new Callable<Flowable<File>>() { // from class: id.zelory.compressor.Compressor.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Flowable<File> call() {
                try {
                    return Flowable.just(Compressor.this.compressToFile(file, str));
                } catch (IOException e) {
                    return Flowable.error(e);
                }
            }
        });
    }

    public Flowable<Bitmap> compressToBitmapAsFlowable(final File file) {
        return Flowable.defer(new Callable<Flowable<Bitmap>>() { // from class: id.zelory.compressor.Compressor.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Flowable<Bitmap> call() {
                try {
                    return Flowable.just(Compressor.this.compressToBitmap(file));
                } catch (IOException e) {
                    return Flowable.error(e);
                }
            }
        });
    }
}
