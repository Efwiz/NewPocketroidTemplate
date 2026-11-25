package org.jetbrains.kotlin.incremental.storage;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: NameTransformers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0002H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/JvmClassNameTransformer;", "Lorg/jetbrains/kotlin/incremental/storage/NameTransformer;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "()V", "asFqName", "Lorg/jetbrains/kotlin/name/FqName;", "string", "", "asName", "asString", "name", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmClassNameTransformer implements NameTransformer<JvmClassName> {
    public static final JvmClassNameTransformer INSTANCE = new JvmClassNameTransformer();

    private JvmClassNameTransformer() {
    }

    @Override // org.jetbrains.kotlin.incremental.storage.NameTransformer
    public String asString(JvmClassName name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String internalName = name2.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return internalName;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.NameTransformer
    public JvmClassName asName(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(string);
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
        return jvmClassNameByInternalName;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.NameTransformer
    public FqName asFqName(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        FqName fqNameForClassNameWithoutDollars = asName(string).getFqNameForClassNameWithoutDollars();
        Intrinsics.checkNotNullExpressionValue(fqNameForClassNameWithoutDollars, "getFqNameForClassNameWithoutDollars(...)");
        return fqNameForClassNameWithoutDollars;
    }
}
