package org.jetbrains.kotlin.incremental.storage;

import kotlin.Metadata;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: NameTransformers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b`\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0015\u0010\u0007\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0006H&¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/NameTransformer;", SchemaSymbols.ATTVAL_NAME, "", "asFqName", "Lorg/jetbrains/kotlin/name/FqName;", "string", "", "asName", "(Ljava/lang/String;)Ljava/lang/Object;", "asString", "name", "(Ljava/lang/Object;)Ljava/lang/String;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NameTransformer<Name> {
    FqName asFqName(String string);

    Name asName(String string);

    String asString(Name name2);
}
