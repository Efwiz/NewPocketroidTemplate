package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.WrappedTypeFactory;

/* JADX INFO: compiled from: DeclarationReturnTypeSanitizer.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\u000bJ(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DeclarationReturnTypeSanitizer;", "", "sanitizeReturnType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "inferred", "wrappedTypeFactory", "Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "Default", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DeclarationReturnTypeSanitizer {
    UnwrappedType sanitizeReturnType(UnwrappedType inferred, WrappedTypeFactory wrappedTypeFactory, BindingTrace trace, LanguageVersionSettings languageVersionSettings);

    /* JADX INFO: compiled from: DeclarationReturnTypeSanitizer.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DeclarationReturnTypeSanitizer$Default;", "Lorg/jetbrains/kotlin/resolve/DeclarationReturnTypeSanitizer;", "()V", "sanitizeReturnType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "inferred", "wrappedTypeFactory", "Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements DeclarationReturnTypeSanitizer {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.resolve.DeclarationReturnTypeSanitizer
        public UnwrappedType sanitizeReturnType(UnwrappedType inferred, WrappedTypeFactory wrappedTypeFactory, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(inferred, "inferred");
            Intrinsics.checkNotNullParameter(wrappedTypeFactory, "wrappedTypeFactory");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            return inferred;
        }

        private Default() {
        }
    }
}
