package org.jetbrains.kotlin.library.abi;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl;

/* JADX INFO: compiled from: LibraryAbiRenderer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u00060\bj\u0002`\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\u0003\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/LibraryAbiRenderer;", "", "()V", "render", "", "libraryAbi", "Lorg/jetbrains/kotlin/library/abi/LibraryAbi;", "output", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "settings", "Lorg/jetbrains/kotlin/library/abi/AbiRenderingSettings;", "", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class LibraryAbiRenderer {
    public static final LibraryAbiRenderer INSTANCE = new LibraryAbiRenderer();

    private LibraryAbiRenderer() {
    }

    public final String render(LibraryAbi libraryAbi, AbiRenderingSettings settings) throws IOException {
        Intrinsics.checkNotNullParameter(libraryAbi, "libraryAbi");
        Intrinsics.checkNotNullParameter(settings, "settings");
        StringBuilder sb = new StringBuilder();
        INSTANCE.render(libraryAbi, sb, settings);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final void render(LibraryAbi libraryAbi, Appendable output, AbiRenderingSettings settings) throws IOException {
        Intrinsics.checkNotNullParameter(libraryAbi, "libraryAbi");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(settings, "settings");
        new AbiRendererImpl(libraryAbi, settings, output).render();
    }
}
