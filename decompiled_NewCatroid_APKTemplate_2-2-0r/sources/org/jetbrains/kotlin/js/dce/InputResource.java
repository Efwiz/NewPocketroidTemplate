package org.jetbrains.kotlin.js.dce;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InputResource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/InputResource;", "", "name", "", "lastModified", "Lkotlin/Function0;", "", "reader", "Ljava/io/InputStream;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "getLastModified", "()Lkotlin/jvm/functions/Function0;", "getName", "()Ljava/lang/String;", "getReader", "Companion", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InputResource {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Function0<Long> lastModified;
    private final String name;
    private final Function0<InputStream> reader;

    /* JADX INFO: compiled from: InputResource.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/InputResource$Companion;", "", "()V", "file", "Lorg/jetbrains/kotlin/js/dce/InputResource;", "path", "", "getZipModificationTime", "", "entryPath", "zipFile", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final InputResource file(final String path) {
            Intrinsics.checkNotNullParameter(path, "path");
            return new InputResource(path, new Function0<Long>() { // from class: org.jetbrains.kotlin.js.dce.InputResource$Companion$file$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Long invoke() {
                    return Long.valueOf(new File(path).lastModified());
                }
            }, new Function0<InputStream>() { // from class: org.jetbrains.kotlin.js.dce.InputResource$Companion$file$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final InputStream invoke() {
                    return new FileInputStream(new File(path));
                }
            });
        }

        public final InputResource zipFile(final String path, final String entryPath) {
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(entryPath, "entryPath");
            return new InputResource(path + '!' + entryPath, new Function0<Long>() { // from class: org.jetbrains.kotlin.js.dce.InputResource$Companion$zipFile$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Long invoke() {
                    return Long.valueOf(InputResource.INSTANCE.getZipModificationTime(path, entryPath));
                }
            }, new Function0<InputStream>() { // from class: org.jetbrains.kotlin.js.dce.InputResource$Companion$zipFile$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final InputStream invoke() throws IOException {
                    ZipFile zipFile = new ZipFile(path);
                    InputStream inputStream = zipFile.getInputStream(zipFile.getEntry(entryPath));
                    Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                    return inputStream;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final long getZipModificationTime(String path, String entryPath) {
            long time = new ZipFile(path).getEntry(entryPath).getTime();
            return time != -1 ? time : new File(path).lastModified();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public InputResource(String name2, Function0<Long> lastModified, Function0<? extends InputStream> reader) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(lastModified, "lastModified");
        Intrinsics.checkNotNullParameter(reader, "reader");
        this.name = name2;
        this.lastModified = lastModified;
        this.reader = reader;
    }

    public final Function0<Long> getLastModified() {
        return this.lastModified;
    }

    public final String getName() {
        return this.name;
    }

    public final Function0<InputStream> getReader() {
        return this.reader;
    }
}
