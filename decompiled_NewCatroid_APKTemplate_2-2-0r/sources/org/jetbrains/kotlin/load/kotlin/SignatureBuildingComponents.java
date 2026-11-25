package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: SignatureBuildingComponents.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005H\u0002J-\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\t\u001a\u00020\u00052\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005¢\u0006\u0002\u0010\fJ-\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\u000e\u001a\u00020\u00052\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005¢\u0006\u0002\u0010\fJ-\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\u000e\u001a\u00020\u00052\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005¢\u0006\u0002\u0010\fJ\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005J\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005J&\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0005J\u0016\u0010\u0017\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u0005¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/SignatureBuildingComponents;", "", "()V", "constructors", "", "", "signatures", "([Ljava/lang/String;)[Ljava/lang/String;", "escapeClassName", "internalName", "inClass", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/util/Set;", "inJavaLang", "name", "inJavaUtil", "javaFunction", "javaLang", "javaUtil", "jvmDescriptor", "parameters", "", "ret", "signature", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SignatureBuildingComponents {
    public static final SignatureBuildingComponents INSTANCE = new SignatureBuildingComponents();

    private SignatureBuildingComponents() {
    }

    public final String javaLang(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return "java/lang/" + name2;
    }

    public final String javaUtil(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return "java/util/" + name2;
    }

    public final String javaFunction(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return "java/util/function/" + name2;
    }

    public final Set<String> inJavaLang(String name2, String... signatures) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        return inClass(javaLang(name2), (String[]) Arrays.copyOf(signatures, signatures.length));
    }

    public final Set<String> inJavaUtil(String name2, String... signatures) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        return inClass(javaUtil(name2), (String[]) Arrays.copyOf(signatures, signatures.length));
    }

    public final Set<String> inClass(String internalName, String... signatures) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (String str : signatures) {
            linkedHashSet.add(internalName + '.' + str);
        }
        return linkedHashSet;
    }

    public final String signature(ClassId classId, String jvmDescriptor) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(jvmDescriptor, "jvmDescriptor");
        return signature(SignatureBuildingComponentsKt.getInternalName(classId), jvmDescriptor);
    }

    public final String signature(String internalName, String jvmDescriptor) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(jvmDescriptor, "jvmDescriptor");
        return internalName + '.' + jvmDescriptor;
    }

    public static /* synthetic */ String jvmDescriptor$default(SignatureBuildingComponents signatureBuildingComponents, String str, List list, String str2, int i, Object obj) {
        if ((i & 4) != 0) {
            str2 = ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        }
        return signatureBuildingComponents.jvmDescriptor(str, list, str2);
    }

    public final String jvmDescriptor(String name2, List<String> parameters, String ret) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(ret, "ret");
        return name2 + '(' + CollectionsKt.joinToString$default(parameters, "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponents.jvmDescriptor.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return SignatureBuildingComponents.INSTANCE.escapeClassName(it);
            }
        }, 30, null) + ')' + escapeClassName(ret);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String escapeClassName(String internalName) {
        return internalName.length() > 1 ? "L" + internalName + ';' : internalName;
    }

    public final String[] constructors(String... signatures) {
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        ArrayList arrayList = new ArrayList(signatures.length);
        for (String str : signatures) {
            arrayList.add("<init>(" + str + ")V");
        }
        return (String[]) arrayList.toArray(new String[0]);
    }
}
