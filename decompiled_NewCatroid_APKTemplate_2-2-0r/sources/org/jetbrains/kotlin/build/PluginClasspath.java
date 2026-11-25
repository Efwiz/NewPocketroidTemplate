package org.jetbrains.kotlin.build;

import com.beust.jcommander.Parameters;
import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: PluginClasspathsUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0006\u0010\u0007\u001a\u00020\u0004J\f\u0010\b\u001a\u00020\u0004*\u00020\tH\u0002R\u0018\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/build/PluginClasspath;", "", ModuleXmlParser.CLASSPATH, "", "", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "serialize", "sha256", "Ljava/io/File;", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PluginClasspath {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String[] classpath;

    /* JADX INFO: compiled from: PluginClasspathsUtils.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u00042\u0006\u0010\u0007\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/build/PluginClasspath$Companion;", "", "()V", "deserializeWithHashes", "", "Lkotlin/Pair;", "", "str", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<Pair<String, String>> deserializeWithHashes(String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{":"}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList();
            for (Object obj : listSplit$default) {
                if (!StringsKt.isBlank((String) obj)) {
                    arrayList.add(obj);
                }
            }
            ArrayList<String> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (String str2 : arrayList2) {
                arrayList3.add(new Pair(StringsKt.substringBeforeLast$default(str2, Parameters.DEFAULT_OPTION_PREFIXES, (String) null, 2, (Object) null), StringsKt.substringAfterLast$default(str2, Parameters.DEFAULT_OPTION_PREFIXES, (String) null, 2, (Object) null)));
            }
            return arrayList3;
        }
    }

    public PluginClasspath(String[] strArr) {
        this.classpath = strArr;
    }

    public final String serialize() throws NoSuchAlgorithmException {
        String[] strArr = this.classpath;
        if (strArr != null) {
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                File file = new File(str);
                if (!file.exists()) {
                    file = null;
                }
                String str2 = file != null ? file.getName() + '-' + sha256(file) : null;
                if (str2 != null) {
                    arrayList.add(str2);
                }
            }
            String strJoinToString$default = CollectionsKt.joinToString$default(arrayList, ":", null, null, 0, null, null, 62, null);
            if (strJoinToString$default != null) {
                return strJoinToString$default;
            }
        }
        return "";
    }

    private final String sha256(File file) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA256);
        DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(file), messageDigest);
        try {
            DigestInputStream digestInputStream2 = digestInputStream;
            byte[] bArr = new byte[8192];
            for (int i = 0; i != -1; i = digestInputStream2.read(bArr, 0, 8192)) {
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(digestInputStream, null);
            byte[] bArrDigest = messageDigest.digest();
            Intrinsics.checkNotNullExpressionValue(bArrDigest, "digest(...)");
            return ArraysKt.joinToString$default(bArrDigest, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: org.jetbrains.kotlin.build.PluginClasspath.sha256.2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                    return invoke(b.byteValue());
                }

                public final CharSequence invoke(byte b) {
                    String hexString = Integer.toHexString((b & 255) + 256);
                    Intrinsics.checkNotNullExpressionValue(hexString, "toHexString(...)");
                    String strSubstring = hexString.substring(1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    return strSubstring;
                }
            }, 30, (Object) null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(digestInputStream, th);
                throw th2;
            }
        }
    }
}
