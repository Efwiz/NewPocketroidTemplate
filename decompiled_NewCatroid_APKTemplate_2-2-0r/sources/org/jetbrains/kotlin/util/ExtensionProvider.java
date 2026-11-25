package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;

/* JADX INFO: compiled from: MappedExtensionProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \b*\b\b\u0000\u0010\u0001*\u00020\u00022\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00040\u0003:\u0001\bB\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/util/ExtensionProvider;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/util/MappedExtensionProvider;", "", "epName", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointName;", "(Lcom/intellij/openapi/extensions/ExtensionPointName;)V", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExtensionProvider<T> extends MappedExtensionProvider<T, List<? extends T>> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final <T> ExtensionProvider<T> create(ExtensionPointName<T> extensionPointName) {
        return INSTANCE.create(extensionPointName);
    }

    /* JADX INFO: compiled from: MappedExtensionProvider.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0001\u0010\u0005*\u00020\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0007H\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/util/ExtensionProvider$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/util/ExtensionProvider;", ExifInterface.GPS_DIRECTION_TRUE, "epName", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointName;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final <T> ExtensionProvider<T> create(ExtensionPointName<T> epName) {
            Intrinsics.checkNotNullParameter(epName, "epName");
            return new ExtensionProvider<>(epName);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExtensionProvider(ExtensionPointName<T> epName) {
        super(epName, new Function1<List<? extends T>, List<? extends T>>() { // from class: org.jetbrains.kotlin.util.ExtensionProvider.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final List<T> invoke(List<? extends T> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        });
        Intrinsics.checkNotNullParameter(epName, "epName");
    }
}
