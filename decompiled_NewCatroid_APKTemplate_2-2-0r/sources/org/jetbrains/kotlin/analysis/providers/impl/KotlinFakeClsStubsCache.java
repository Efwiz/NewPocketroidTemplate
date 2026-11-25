package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFileStubImpl;

/* JADX INFO: compiled from: KotlinStaticDeclarationProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002R&\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinFakeClsStubsCache;", "", "()V", "fakeFileClsStubs", "Ljava/util/concurrent/ConcurrentHashMap;", "", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFileStubImpl;", "Companion", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinFakeClsStubsCache {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ConcurrentHashMap<String, Map<VirtualFile, KotlinFileStubImpl>> fakeFileClsStubs = new ConcurrentHashMap<>();

    /* JADX INFO: compiled from: KotlinStaticDeclarationProvider.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J<\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u00052\u001e\u0010\b\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinFakeClsStubsCache$Companion;", "", "()V", "processAdditionalRoot", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFileStubImpl;", "root", "storage", "Lkotlin/Function1;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<VirtualFile, KotlinFileStubImpl> processAdditionalRoot(final VirtualFile root, final Function1<? super VirtualFile, ? extends Map<VirtualFile, KotlinFileStubImpl>> storage) {
            Intrinsics.checkNotNullParameter(root, "root");
            Intrinsics.checkNotNullParameter(storage, "storage");
            KotlinFakeClsStubsCache kotlinFakeClsStubsCache = (KotlinFakeClsStubsCache) ApplicationManager.getApplication().getService(KotlinFakeClsStubsCache.class);
            if (kotlinFakeClsStubsCache == null) {
                return null;
            }
            if (kotlinFakeClsStubsCache.fakeFileClsStubs.get(root.getPath()) == null) {
                ConcurrentHashMap concurrentHashMap = kotlinFakeClsStubsCache.fakeFileClsStubs;
                String path = root.getPath();
                Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                concurrentHashMap.put(path, storage.invoke(root));
            }
            ConcurrentHashMap concurrentHashMap2 = kotlinFakeClsStubsCache.fakeFileClsStubs;
            String path2 = root.getPath();
            final Function1<String, Map<VirtualFile, ? extends KotlinFileStubImpl>> function1 = new Function1<String, Map<VirtualFile, ? extends KotlinFileStubImpl>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinFakeClsStubsCache$Companion$processAdditionalRoot$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Map<VirtualFile, KotlinFileStubImpl> invoke(String str) {
                    Intrinsics.checkNotNullParameter(str, "<anonymous parameter 0>");
                    return storage.invoke(root);
                }
            };
            return (Map) concurrentHashMap2.computeIfAbsent(path2, new Function() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinFakeClsStubsCache$Companion$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return KotlinFakeClsStubsCache.Companion.processAdditionalRoot$lambda$0(function1, obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Map processAdditionalRoot$lambda$0(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return (Map) tmp0.invoke(obj);
        }
    }
}
