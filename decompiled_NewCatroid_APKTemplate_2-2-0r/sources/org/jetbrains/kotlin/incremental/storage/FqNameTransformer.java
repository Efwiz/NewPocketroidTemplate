package org.jetbrains.kotlin.incremental.storage;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: NameTransformers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/FqNameTransformer;", "Lorg/jetbrains/kotlin/incremental/storage/NameTransformer;", "Lorg/jetbrains/kotlin/name/FqName;", "()V", "asFqName", "string", "", "asName", "asString", "name", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FqNameTransformer implements NameTransformer<FqName> {
    public static final FqNameTransformer INSTANCE = new FqNameTransformer();

    private FqNameTransformer() {
    }

    @Override // org.jetbrains.kotlin.incremental.storage.NameTransformer
    public String asString(FqName name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // org.jetbrains.kotlin.incremental.storage.NameTransformer
    public FqName asName(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return new FqName(string);
    }

    @Override // org.jetbrains.kotlin.incremental.storage.NameTransformer
    public FqName asFqName(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return asName(string);
    }
}
