package org.jetbrains.kotlin.resolve.lazy;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableListMultimap;
import org.jetbrains.kotlin.com.google.common.collect.ListMultimap;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtImportInfo;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: LazyImportScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ4\u0010\r\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u00018\u00008\u0000 \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00100\u000e2\u0006\u0010\u0011\u001a\u00020\fH\u0016R \u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00028\u00000\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/ExplicitImportsIndexed;", "I", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "Lorg/jetbrains/kotlin/resolve/lazy/IndexedImports;", "imports", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "([Lorg/jetbrains/kotlin/psi/KtImportInfo;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "nameToDirectives", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "Lorg/jetbrains/kotlin/com/google/common/collect/ListMultimap;", "Lorg/jetbrains/kotlin/name/Name;", "importsForName", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "name", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExplicitImportsIndexed<I extends KtImportInfo> extends IndexedImports<I> {
    private final NotNullLazyValue<ListMultimap<Name, I>> nameToDirectives;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExplicitImportsIndexed(final I[] imports, StorageManager storageManager) {
        super(imports);
        Intrinsics.checkNotNullParameter(imports, "imports");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.nameToDirectives = storageManager.createLazyValue(new Function0<ImmutableListMultimap<Name, I>>() { // from class: org.jetbrains.kotlin.resolve.lazy.ExplicitImportsIndexed$nameToDirectives$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: ([TI;)V */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ImmutableListMultimap<Name, I> invoke() {
                ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
                for (KtImportInfo ktImportInfo : imports) {
                    Name importedName = ktImportInfo.getImportedName();
                    if (importedName != null) {
                        builder.put(importedName, ktImportInfo);
                    }
                }
                return builder.build();
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.IndexedImports
    public List<I> importsForName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.nameToDirectives.invoke().get(name2);
    }
}
