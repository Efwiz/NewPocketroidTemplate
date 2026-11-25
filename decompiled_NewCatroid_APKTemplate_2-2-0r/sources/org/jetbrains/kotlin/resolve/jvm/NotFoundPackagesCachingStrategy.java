package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: NotFoundPackagesCachingStrategy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\b\tJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/NotFoundPackagesCachingStrategy;", "", "chooseStrategy", "Lorg/jetbrains/kotlin/resolve/jvm/NotFoundPackagesCachingStrategy$CacheType;", "isLibrarySearchScope", "", "qualifiedName", "", "CacheType", "Default", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NotFoundPackagesCachingStrategy {

    /* JADX INFO: compiled from: NotFoundPackagesCachingStrategy.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/NotFoundPackagesCachingStrategy$CacheType;", "", "(Ljava/lang/String;I)V", "LIB_SCOPE", "SCOPE", "NO_CACHING", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum CacheType {
        LIB_SCOPE,
        SCOPE,
        NO_CACHING;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<CacheType> getEntries() {
            return $ENTRIES;
        }
    }

    CacheType chooseStrategy(boolean isLibrarySearchScope, String qualifiedName);

    /* JADX INFO: compiled from: NotFoundPackagesCachingStrategy.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/NotFoundPackagesCachingStrategy$Default;", "Lorg/jetbrains/kotlin/resolve/jvm/NotFoundPackagesCachingStrategy;", "()V", "chooseStrategy", "Lorg/jetbrains/kotlin/resolve/jvm/NotFoundPackagesCachingStrategy$CacheType;", "isLibrarySearchScope", "", "qualifiedName", "", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements NotFoundPackagesCachingStrategy {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.NotFoundPackagesCachingStrategy
        public CacheType chooseStrategy(boolean isLibrarySearchScope, String qualifiedName) {
            Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
            boolean z = StringsKt.indexOf$default((CharSequence) qualifiedName, '.', 0, false, 6, (Object) null) > 0;
            if (isLibrarySearchScope && z) {
                return CacheType.LIB_SCOPE;
            }
            return CacheType.SCOPE;
        }
    }
}
