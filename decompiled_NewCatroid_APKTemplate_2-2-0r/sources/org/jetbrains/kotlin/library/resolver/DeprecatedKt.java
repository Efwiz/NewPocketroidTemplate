package org.jetbrains.kotlin.library.resolver;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolverKt;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary;

/* JADX INFO: compiled from: Deprecated.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\"7\u0010\u0000\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00040\u0001j\u0002`\u00058Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t*8\b\u0007\u0010\n\"\u00020\u000b2\u00020\u000bB*\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u001c\b\u000f\u0012\u0018\b\u000bB\u0014\b\u0010\u0012\u0006\b\u0011\u0012\u0002\b\f\u0012\b\b\u0012\u0012\u0004\b\b(\u0013*J\b\u0007\u0010\u0014\u001a\u0004\b\u0000\u0010\u0015\"\b\u0012\u0004\u0012\u0002H\u00150\u00162\b\u0012\u0004\u0012\u0002H\u00150\u0016B*\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u001c\b\u000f\u0012\u0018\b\u000bB\u0014\b\u0010\u0012\u0006\b\u0011\u0012\u0002\b\f\u0012\b\b\u0012\u0012\u0004\b\b(\u0017*8\b\u0007\u0010\u0018\"\u00020\u00032\u00020\u0003B*\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u001c\b\u000f\u0012\u0018\b\u000bB\u0014\b\u0010\u0012\u0006\b\u0011\u0012\u0002\b\f\u0012\b\b\u0012\u0012\u0004\b\b(\u0019*P\b\u0007\u0010\u001a\"\u0002`\u00052\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00040\u0001B*\b\f\u0012\b\b\r\u0012\u0004\b\b(\u001b\u0012\u001c\b\u000f\u0012\u0018\b\u000bB\u0014\b\u0010\u0012\u0006\b\u0011\u0012\u0002\b\f\u0012\b\b\u0012\u0012\u0004\b\b(\u001c¨\u0006\u001d"}, d2 = {"TopologicalLibraryOrder", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinResolvedLibrary;", "", "Lorg/jetbrains/kotlin/library/metadata/resolver/LibraryOrder;", "getTopologicalLibraryOrder$annotations", "()V", "getTopologicalLibraryOrder", "()Lkotlin/jvm/functions/Function1;", "KotlinLibraryResolveResult", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", "Lkotlin/Deprecated;", CrashHianalyticsData.MESSAGE, "This interface has been moved from package org.jetbrains.kotlin.library.resolver to package org.jetbrains.kotlin.library.metadata.resolver", "replaceWith", "Lkotlin/ReplaceWith;", "imports", "expression", "org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult", "KotlinLibraryResolver", "L", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolver;", "org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolver", "KotlinResolvedLibrary", "org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary", "LibraryOrder", "This type alias has been moved from package org.jetbrains.kotlin.library.resolver to package org.jetbrains.kotlin.library.metadata.resolver", "org.jetbrains.kotlin.library.metadata.resolver.LibraryOrder", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedKt {
    @Deprecated(message = "This interface has been moved from package org.jetbrains.kotlin.library.resolver to package org.jetbrains.kotlin.library.metadata.resolver", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult", imports = {}))
    public static /* synthetic */ void KotlinLibraryResolveResult$annotations() {
    }

    @Deprecated(message = "This interface has been moved from package org.jetbrains.kotlin.library.resolver to package org.jetbrains.kotlin.library.metadata.resolver", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolver", imports = {}))
    public static /* synthetic */ void KotlinLibraryResolver$annotations() {
    }

    @Deprecated(message = "This interface has been moved from package org.jetbrains.kotlin.library.resolver to package org.jetbrains.kotlin.library.metadata.resolver", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary", imports = {}))
    public static /* synthetic */ void KotlinResolvedLibrary$annotations() {
    }

    @Deprecated(message = "This type alias has been moved from package org.jetbrains.kotlin.library.resolver to package org.jetbrains.kotlin.library.metadata.resolver", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.resolver.LibraryOrder", imports = {}))
    public static /* synthetic */ void LibraryOrder$annotations() {
    }

    @Deprecated(message = "This property has been moved from package org.jetbrains.kotlin.library.resolver to package org.jetbrains.kotlin.library.metadata.resolver", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.resolver.TopologicalLibraryOrder", imports = {}))
    public static /* synthetic */ void getTopologicalLibraryOrder$annotations() {
    }

    public static final Function1<Iterable<? extends KotlinResolvedLibrary>, List<KotlinResolvedLibrary>> getTopologicalLibraryOrder() {
        return KotlinLibraryResolverKt.getTopologicalLibraryOrder();
    }
}
