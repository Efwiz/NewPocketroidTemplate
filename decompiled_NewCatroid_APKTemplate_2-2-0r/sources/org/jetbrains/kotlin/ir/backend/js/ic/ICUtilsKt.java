package org.jetbrains.kotlin.ir.backend.js.ic;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;

/* JADX INFO: compiled from: ICUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000~\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001aW\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052)\u0010\u0006\u001a%\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0080\bø\u0001\u0000\u001aE\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\u0004\b\u0000\u0010\r2\u0006\u0010\u0004\u001a\u00020\u00052#\u0010\u0006\u001a\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\r0\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0080\bø\u0001\u0000\u001a0\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00102\u0006\u0010\u0013\u001a\u00020\u00122\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0001H\u0000\u001a2\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0000ø\u0001\u0001¢\u0006\u0004\b\u001c\u0010\u001d\u001a2\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0000ø\u0001\u0001¢\u0006\u0004\b\u001f\u0010\u001d\u001a6\u0010 \u001a\u0004\u0018\u0001H!\"\u0004\b\u0000\u0010!*\u00020\"2\u0017\u0010#\u001a\u0013\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u0002H!0$¢\u0006\u0002\b\nH\u0080\bø\u0001\u0000¢\u0006\u0002\u0010%\u001a9\u0010&\u001a\u00020\t\"\u0004\b\u0000\u0010!*\u00020'2\b\u0010(\u001a\u0004\u0018\u0001H!2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u0002H!\u0012\u0004\u0012\u00020\t0$H\u0080\bø\u0001\u0000¢\u0006\u0002\u0010*\u001a+\u0010+\u001a\u0004\u0018\u0001H!\"\u0004\b\u0000\u0010!*\u00020,2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H!0.H\u0080\bø\u0001\u0000¢\u0006\u0002\u0010/\u001a6\u00100\u001a\u0004\u0018\u0001H!\"\u0004\b\u0000\u0010!*\u00020\"2\u0017\u0010#\u001a\u0013\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u0002H!0$¢\u0006\u0002\b\nH\u0080\bø\u0001\u0000¢\u0006\u0002\u0010%\u001a)\u00101\u001a\u00020\t*\u00020\"2\u0017\u0010#\u001a\u0013\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\t0$¢\u0006\u0002\b\nH\u0080\bø\u0001\u0000\u001a)\u00101\u001a\u00020\t*\u0002022\u0017\u0010#\u001a\u0013\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\t0$¢\u0006\u0002\b\nH\u0080\bø\u0001\u0000\u0082\u0002\u000e\n\u0005\b\u009920\u0001\n\u0005\b¡\u001e0\u0001¨\u00063"}, d2 = {"buildMapUntil", "", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "to", "", "builderAction", "Lkotlin/Function2;", "", "", "Lkotlin/ExtensionFunctionType;", "buildSetUntil", "", ExifInterface.LONGITUDE_EAST, "", "findStdlib", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "mainFragment", "allFragments", "icError", "", "what", "", "libFile", "srcFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "icError-5T69zGA", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Void;", "notFoundIcError", "notFoundIcError-5T69zGA", "ifExists", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/io/File;", "f", "Lkotlin/Function1;", "(Ljava/io/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "ifNotNull", "Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;", "value", "write", "(Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "ifTrue", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "then", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/protobuf/CodedInputStream;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "useCodedInputIfExists", "useCodedOutput", "Ljava/io/OutputStream;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ICUtilsKt {
    public static final <T> T ifExists(File file, Function1<? super File, ? extends T> f) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        if (file.exists()) {
            return f.invoke(file);
        }
        return null;
    }

    public static final void useCodedOutput(OutputStream outputStream, Function1<? super CodedOutputStream, Unit> f) {
        Intrinsics.checkNotNullParameter(outputStream, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        OutputStream outputStream2 = outputStream;
        try {
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputStream2);
            Intrinsics.checkNotNull(codedOutputStreamNewInstance);
            f.invoke(codedOutputStreamNewInstance);
            codedOutputStreamNewInstance.flush();
            Unit unit = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(outputStream2, null);
            InlineMarker.finallyEnd(1);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                InlineMarker.finallyStart(1);
                CloseableKt.closeFinally(outputStream2, th);
                InlineMarker.finallyEnd(1);
                throw th2;
            }
        }
    }

    public static final void useCodedOutput(File file, Function1<? super CodedOutputStream, Unit> f) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(fileOutputStream);
            Intrinsics.checkNotNull(codedOutputStreamNewInstance);
            f.invoke(codedOutputStreamNewInstance);
            codedOutputStreamNewInstance.flush();
            Unit unit = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(fileOutputStream, null);
            InlineMarker.finallyEnd(1);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                InlineMarker.finallyStart(1);
                CloseableKt.closeFinally(fileOutputStream, th);
                InlineMarker.finallyEnd(1);
                throw th2;
            }
        }
    }

    public static final <T> void ifNotNull(CodedOutputStream codedOutputStream, T t, Function1<? super T, Unit> write) throws IOException {
        Intrinsics.checkNotNullParameter(codedOutputStream, "<this>");
        Intrinsics.checkNotNullParameter(write, "write");
        codedOutputStream.writeBoolNoTag(t != null);
        if (t != null) {
            write.invoke(t);
        }
    }

    public static final <T> T ifTrue(CodedInputStream codedInputStream, Function0<? extends T> then) {
        Intrinsics.checkNotNullParameter(codedInputStream, "<this>");
        Intrinsics.checkNotNullParameter(then, "then");
        if (codedInputStream.readBool()) {
            return then.invoke();
        }
        return null;
    }

    /* JADX INFO: renamed from: icError-5T69zGA$default, reason: not valid java name */
    public static /* synthetic */ Void m7122icError5T69zGA$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            str3 = null;
        }
        return m7121icError5T69zGA(str, str2, str3);
    }

    /* JADX INFO: renamed from: icError-5T69zGA, reason: not valid java name */
    public static final Void m7121icError5T69zGA(String what, String str, String str2) {
        Intrinsics.checkNotNullParameter(what, "what");
        String[] strArr = new String[2];
        if (str == null) {
            str = null;
        }
        strArr[0] = str;
        if (str2 == null) {
            str2 = null;
        }
        strArr[1] = str2;
        String strJoinToString$default = CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull((Object[]) strArr), ":", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.ICUtilsKt$icError$filePath$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String name2 = new File(it).getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return name2;
            }
        }, 30, null);
        if (strJoinToString$default.length() != 0) {
            what = what + " for " + strJoinToString$default;
        }
        throw new IllegalStateException(("IC internal error: " + what).toString());
    }

    /* JADX INFO: renamed from: notFoundIcError-5T69zGA$default, reason: not valid java name */
    public static /* synthetic */ Void m7124notFoundIcError5T69zGA$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            str3 = null;
        }
        return m7123notFoundIcError5T69zGA(str, str2, str3);
    }

    /* JADX INFO: renamed from: notFoundIcError-5T69zGA, reason: not valid java name */
    public static final Void m7123notFoundIcError5T69zGA(String what, String str, String str2) {
        Intrinsics.checkNotNullParameter(what, "what");
        m7121icError5T69zGA("can not find " + what, str, str2);
        throw null;
    }

    public static final <E> Set<E> buildSetUntil(int i, Function2<? super Set<E>, ? super Integer, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        HashSet hashSetNewHashSetWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashSetWithExpectedSize(i);
        for (int i2 = 0; i2 < i; i2++) {
            builderAction.invoke(hashSetNewHashSetWithExpectedSize, Integer.valueOf(i2));
        }
        return hashSetNewHashSetWithExpectedSize;
    }

    public static final <K, V> Map<K, V> buildMapUntil(int i, Function2<? super Map<K, V>, ? super Integer, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        HashMap mapNewHashMapWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashMapWithExpectedSize(i);
        for (int i2 = 0; i2 < i; i2++) {
            builderAction.invoke(mapNewHashMapWithExpectedSize, Integer.valueOf(i2));
        }
        return mapNewHashMapWithExpectedSize;
    }

    public static final Pair<KotlinLibraryFile, IrModuleFragment> findStdlib(IrModuleFragment mainFragment, Map<KotlinLibraryFile, ? extends IrModuleFragment> allFragments) {
        Object next;
        Intrinsics.checkNotNullParameter(mainFragment, "mainFragment");
        Intrinsics.checkNotNullParameter(allFragments, "allFragments");
        ModuleDescriptorImpl builtInsModule = mainFragment.getDescriptor().getBuiltIns().getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule, "getBuiltInsModule(...)");
        Iterator<T> it = allFragments.entrySet().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (((IrModuleFragment) ((Map.Entry) next).getValue()).getDescriptor() != builtInsModule);
        Map.Entry entry = (Map.Entry) next;
        if (entry == null) {
            m7124notFoundIcError5T69zGA$default("stdlib fragment", null, null, 6, null);
            throw null;
        }
        return TuplesKt.to(KotlinLibraryFile.m7149boximpl(((KotlinLibraryFile) entry.getKey()).m7157unboximpl()), (IrModuleFragment) entry.getValue());
    }

    public static final <T> T useCodedInputIfExists(File file, Function1<? super CodedInputStream, ? extends T> f) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        if (!file.exists()) {
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(fileInputStream);
            Intrinsics.checkNotNullExpressionValue(codedInputStreamNewInstance, "newInstance(...)");
            T tInvoke = f.invoke(codedInputStreamNewInstance);
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(fileInputStream, null);
            InlineMarker.finallyEnd(1);
            return tInvoke;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                InlineMarker.finallyStart(1);
                CloseableKt.closeFinally(fileInputStream, th);
                InlineMarker.finallyEnd(1);
                throw th2;
            }
        }
    }
}
