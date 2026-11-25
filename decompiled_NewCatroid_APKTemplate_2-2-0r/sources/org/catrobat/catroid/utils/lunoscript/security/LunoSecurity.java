package org.catrobat.catroid.utils.lunoscript.security;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/security/LunoSecurity.class
  classes14.dex
 */
/* JADX INFO: compiled from: LunoSecurity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u0004H\u0002J\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004H\u0002J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/security/LunoSecurity;", "", "()V", "ALGORITHM", "", "KEY_RAW", "secretKey", "Ljavax/crypto/spec/SecretKeySpec;", "getSecretKey", "()Ljavax/crypto/spec/SecretKeySpec;", "secretKey$delegate", "Lkotlin/Lazy;", "decrypt", "input", "", "encrypt", "loadDecrypted", "file", "Ljava/io/File;", "minify", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "saveEncrypted", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LunoSecurity {
    private static final String ALGORITHM = "AES";
    private static final String KEY_RAW = "NewCatroidSecure";
    public static final LunoSecurity INSTANCE = new LunoSecurity();

    /* JADX INFO: renamed from: secretKey$delegate, reason: from kotlin metadata */
    private static final Lazy secretKey = LazyKt.lazy(new Function0<SecretKeySpec>() { // from class: org.catrobat.catroid.utils.lunoscript.security.LunoSecurity$secretKey$2
        @Override // kotlin.jvm.functions.Function0
        public final SecretKeySpec invoke() {
            String strSubstring = "NewCatroidSecure";
            if ("NewCatroidSecure".length() < 16) {
                strSubstring = StringsKt.padEnd("NewCatroidSecure", 16, ExternalAnnotationProvider.NULLABLE);
            } else if ("NewCatroidSecure".length() > 16) {
                strSubstring = "NewCatroidSecure".substring(0, 16);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            }
            Charset UTF_8 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
            byte[] bytes = strSubstring.getBytes(UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            return new SecretKeySpec(bytes, "AES");
        }
    });

    private final String minify(String code) {
        return code;
    }

    private LunoSecurity() {
    }

    private final SecretKeySpec getSecretKey() {
        return (SecretKeySpec) secretKey.getValue();
    }

    public final void saveEncrypted(File file, String code) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(code, "code");
        FilesKt.writeBytes(file, encrypt(minify(code)));
    }

    public final String loadDecrypted(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return decrypt(FilesKt.readBytes(file));
    }

    private final byte[] encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(1, getSecretKey());
            Charset UTF_8 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
            byte[] bytes = input.getBytes(UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            byte[] bArrDoFinal = cipher.doFinal(bytes);
            Intrinsics.checkNotNullExpressionValue(bArrDoFinal, "doFinal(...)");
            return bArrDoFinal;
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    private final String decrypt(byte[] input) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(2, getSecretKey());
            byte[] bArrDoFinal = cipher.doFinal(input);
            Intrinsics.checkNotNull(bArrDoFinal);
            Charset UTF_8 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
            return new String(bArrDoFinal, UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed (Wrong key or corrupted file)", e);
        }
    }
}
