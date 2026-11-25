package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;

/* JADX INFO: compiled from: DelegationFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\bJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "", "filter", "", "interfaceMember", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "Default", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DelegationFilter {
    boolean filter(CallableMemberDescriptor interfaceMember, LanguageVersionSettings languageVersionSettings);

    /* JADX INFO: compiled from: DelegationFilter.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter$Default;", "Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "()V", "filter", "", "interfaceMember", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements DelegationFilter {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.resolve.lazy.DelegationFilter
        public boolean filter(CallableMemberDescriptor interfaceMember, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(interfaceMember, "interfaceMember");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            return true;
        }

        private Default() {
        }
    }
}
