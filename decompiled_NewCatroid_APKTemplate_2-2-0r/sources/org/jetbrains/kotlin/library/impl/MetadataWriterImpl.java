package org.jetbrains.kotlin.library.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.MetadataKotlinLibraryLayout;
import org.jetbrains.kotlin.library.MetadataWriter;
import org.jetbrains.kotlin.library.SerializedMetadata;

/* JADX INFO: compiled from: MetadataWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/MetadataWriterImpl;", "Lorg/jetbrains/kotlin/library/MetadataWriter;", "metadataLayout", "Lorg/jetbrains/kotlin/library/MetadataKotlinLibraryLayout;", "(Lorg/jetbrains/kotlin/library/MetadataKotlinLibraryLayout;)V", "getMetadataLayout", "()Lorg/jetbrains/kotlin/library/MetadataKotlinLibraryLayout;", "addMetadata", "", "metadata", "Lorg/jetbrains/kotlin/library/SerializedMetadata;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MetadataWriterImpl implements MetadataWriter {
    private final MetadataKotlinLibraryLayout metadataLayout;

    public MetadataWriterImpl(MetadataKotlinLibraryLayout metadataLayout) {
        Intrinsics.checkNotNullParameter(metadataLayout, "metadataLayout");
        this.metadataLayout = metadataLayout;
        metadataLayout.getMetadataDir().mkdirs();
    }

    public final MetadataKotlinLibraryLayout getMetadataLayout() {
        return this.metadataLayout;
    }

    @Override // org.jetbrains.kotlin.library.MetadataWriter
    public void addMetadata(SerializedMetadata metadata) throws IOException {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.metadataLayout.getModuleHeaderFile().writeBytes(metadata.getModule());
        int i = 0;
        for (Object obj : metadata.getFragments()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            List list = (List) obj;
            String str = metadata.getFragmentNames().get(i);
            String strSubstringAfterLast$default = StringsKt.substringAfterLast$default(str, ".", (String) null, 2, (Object) null);
            File filePackageFragmentsDir = this.metadataLayout.packageFragmentsDir(str);
            filePackageFragmentsDir.deleteRecursively();
            filePackageFragmentsDir.mkdirs();
            int length = String.valueOf(list.size()).length();
            Iterator it = list.iterator();
            int i3 = 0;
            while (it.hasNext()) {
                this.metadataLayout.packageFragmentFile(str, addMetadata$lambda$0$withLeadingZeros(length, i3) + '_' + strSubstringAfterLast$default).writeBytes((byte[]) it.next());
                i3++;
            }
            i = i2;
        }
    }

    private static final String addMetadata$lambda$0$withLeadingZeros(int i, int i2) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%0" + i + Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, Arrays.copyOf(new Object[]{Integer.valueOf(i2)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }
}
