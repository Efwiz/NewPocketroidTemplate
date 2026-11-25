package org.jetbrains.kotlin.com.intellij.testFramework;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.CharsetUtil;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.util.LocalTimeCounter;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;

/* JADX INFO: loaded from: classes3.dex */
public class LightVirtualFile extends LightVirtualFileBase {
    private CharSequence myContent;
    private Language myLanguage;

    /* JADX WARN: Code duplicated, block: B:23:0x0033  */
    /* JADX WARN: Code duplicated, block: B:24:0x0038  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 17:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
                i2 = 2;
                break;
            case 17:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        if (i != 2) {
            if (i != 7 && i != 9 && i != 4 && i != 5) {
                switch (i) {
                    case 11:
                    case 13:
                        objArr[0] = "language";
                        break;
                    case 12:
                        objArr[0] = "text";
                        break;
                    case 14:
                    case 15:
                    case 16:
                    case 18:
                    case 19:
                        objArr[0] = "org/jetbrains/kotlin/com/intellij/testFramework/LightVirtualFile";
                        break;
                    case 17:
                        objArr[0] = FirebaseAnalytics.Param.CONTENT;
                        break;
                    default:
                        objArr[0] = "name";
                        break;
                }
            } else {
                objArr[0] = "text";
            }
        } else {
            objArr[0] = FirebaseAnalytics.Param.CONTENT;
        }
        switch (i) {
            case 14:
                objArr[1] = "getInputStream";
                break;
            case 15:
                objArr[1] = "getOutputStream";
                break;
            case 16:
                objArr[1] = "contentsToByteArray";
                break;
            case 17:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/testFramework/LightVirtualFile";
                break;
            case 18:
                objArr[1] = "getContent";
                break;
            case 19:
                objArr[1] = "isTooLargeForIntelligence";
                break;
        }
        switch (i) {
            case 13:
                objArr[2] = "setLanguage";
                break;
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
                break;
            case 17:
                objArr[2] = "setContent";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
                throw new IllegalStateException(str2);
            case 17:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public LightVirtualFile() {
        this("");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightVirtualFile(String str) {
        this(str, "");
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightVirtualFile(String str, CharSequence charSequence) {
        this(str, null, charSequence, LocalTimeCounter.currentTime());
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightVirtualFile(String str, FileType fileType, CharSequence charSequence) {
        this(str, fileType, charSequence, LocalTimeCounter.currentTime());
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightVirtualFile(String str, FileType fileType, CharSequence charSequence, long j) {
        this(str, fileType, charSequence, CharsetUtil.extractCharsetFromFileContent(null, null, fileType, charSequence), j);
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(7);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightVirtualFile(String str, FileType fileType, CharSequence charSequence, Charset charset, long j) {
        super(str, fileType, j);
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(9);
        }
        this.myContent = charSequence;
        setCharset(charset);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightVirtualFile(String str, Language language, CharSequence charSequence) {
        super(str, null, LocalTimeCounter.currentTime());
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (language == null) {
            $$$reportNull$$$0(11);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(12);
        }
        this.myContent = charSequence;
        setLanguage(language);
        setCharset(StandardCharsets.UTF_8);
    }

    public Language getLanguage() {
        return this.myLanguage;
    }

    public void setLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(13);
        }
        this.myLanguage = language;
        FileType associatedFileType = language.getAssociatedFileType();
        if (associatedFileType == null) {
            associatedFileType = FileTypeRegistry.getInstance().getFileTypeByFileName(getNameSequence());
        }
        setFileType(associatedFileType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public InputStream getInputStream() throws IOException {
        InputStream inputStreamByteStreamSkippingBOM = VfsUtilCore.byteStreamSkippingBOM(contentsToByteArray(), this);
        if (inputStreamByteStreamSkippingBOM == null) {
            $$$reportNull$$$0(14);
        }
        return inputStreamByteStreamSkippingBOM;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public OutputStream getOutputStream(Object obj, final long j, long j2) throws IOException {
        assertWritable();
        OutputStream outputStreamOutputStreamAddingBOM = VfsUtilCore.outputStreamAddingBOM(new ByteArrayOutputStream() { // from class: org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // java.io.ByteArrayOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                LightVirtualFile.this.setModificationStamp(j);
                try {
                    LightVirtualFile lightVirtualFile = LightVirtualFile.this;
                    lightVirtualFile.myContent = toString(lightVirtualFile.getCharset().name());
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }, this);
        if (outputStreamOutputStreamAddingBOM == null) {
            $$$reportNull$$$0(15);
        }
        return outputStreamOutputStreamAddingBOM;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public byte[] contentsToByteArray() throws IOException {
        byte[] bytes = getContent().toString().getBytes(getCharset());
        if (bytes == null) {
            $$$reportNull$$$0(16);
        }
        return bytes;
    }

    public CharSequence getContent() {
        CharSequence charSequence = this.myContent;
        if (charSequence == null) {
            $$$reportNull$$$0(18);
        }
        return charSequence;
    }

    public ThreeState isTooLargeForIntelligence() {
        ThreeState threeState = ThreeState.UNSURE;
        if (threeState == null) {
            $$$reportNull$$$0(19);
        }
        return threeState;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "LightVirtualFile: " + getPresentableUrl();
    }
}
