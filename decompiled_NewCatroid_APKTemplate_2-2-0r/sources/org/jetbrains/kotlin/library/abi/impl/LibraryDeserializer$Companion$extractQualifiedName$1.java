package org.jetbrains.kotlin.library.abi.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "it", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class LibraryDeserializer$Companion$extractQualifiedName$1 extends Lambda implements Function1<String, String> {
    public static final LibraryDeserializer$Companion$extractQualifiedName$1 INSTANCE = new LibraryDeserializer$Companion$extractQualifiedName$1();

    public LibraryDeserializer$Companion$extractQualifiedName$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final String invoke(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it;
    }
}
