package org.jetbrains.kotlin.com.intellij.openapi.diagnostic;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class Attachment {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final byte[] myBytes;
    private final String myDisplayText;
    private boolean myIncluded;
    private final String myPath;
    private final Path myTemporaryFile;
    private static final Logger LOG = Logger.getInstance((Class<?>) Attachment.class);
    public static final Attachment[] EMPTY_ARRAY = new Attachment[0];

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "throwable";
                break;
            case 2:
            case 4:
            case 7:
                objArr[0] = "path";
                break;
            case 3:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
            case 5:
            case 9:
                objArr[0] = "displayText";
                break;
            case 6:
                objArr[0] = "bytes";
                break;
            case 8:
                objArr[0] = "temporaryFile";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/Attachment";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        switch (i) {
            case 10:
                objArr[1] = "getDisplayText";
                break;
            case 11:
                objArr[1] = "getPath";
                break;
            case 12:
                objArr[1] = "getName";
                break;
            case 13:
                objArr[1] = "getEncodedBytes";
                break;
            case 14:
            case 15:
            case 16:
                objArr[1] = "getBytes";
                break;
            case 17:
                objArr[1] = "openContentStream";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/Attachment";
                break;
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Attachment(String str, Throwable th) {
        this(str + ".trace", ExceptionUtil.getThrowableText(th));
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (th == null) {
            $$$reportNull$$$0(1);
        }
        this.myIncluded = true;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Attachment(String str, String str2) {
        this(str, str2, str2.getBytes(StandardCharsets.UTF_8), null);
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (str2 == null) {
            $$$reportNull$$$0(3);
        }
    }

    private Attachment(String str, String str2, byte[] bArr, File file) {
        this.myPath = str;
        this.myDisplayText = str2;
        this.myBytes = bArr;
        this.myTemporaryFile = file == null ? null : file.toPath();
    }

    public String getDisplayText() {
        String str = this.myDisplayText;
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        return str;
    }

    public String getPath() {
        String str = this.myPath;
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        return str;
    }

    public byte[] getBytes() {
        byte[] bArr = this.myBytes;
        if (bArr != null) {
            if (bArr == null) {
                $$$reportNull$$$0(14);
            }
            return bArr;
        }
        Path path = this.myTemporaryFile;
        if (path != null) {
            try {
                byte[] allBytes = Files.readAllBytes(path);
                if (allBytes == null) {
                    $$$reportNull$$$0(15);
                }
                return allBytes;
            } catch (IOException e) {
                LOG.error("Failed to read attachment content from temp. file " + this.myTemporaryFile, e);
            }
        }
        byte[] bArr2 = ArrayUtilRt.EMPTY_BYTE_ARRAY;
        if (bArr2 == null) {
            $$$reportNull$$$0(16);
        }
        return bArr2;
    }
}
