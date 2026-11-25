package org.jetbrains.kotlin.incremental.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.com.intellij.util.io.BooleanDataDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;

/* JADX INFO: compiled from: DirtyClassesMaps.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B#\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u0014J\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fJ\u0013\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00028\u0000¢\u0006\u0002\u0010\u0012J\u0013\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00028\u0000¢\u0006\u0002\u0010\u0015J\u0013\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00028\u0000¢\u0006\u0002\u0010\u0015R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/AbstractDirtyClassesMap;", SchemaSymbols.ATTVAL_NAME, "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "nameTransformer", "Lorg/jetbrains/kotlin/incremental/storage/NameTransformer;", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/storage/NameTransformer;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "", "value", "getDirtyOutputClasses", "", "isDirty", "className", "(Ljava/lang/Object;)Z", "markDirty", "", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "notDirty", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractDirtyClassesMap<Name> extends BasicStringMap<Boolean> {
    private final NameTransformer<Name> nameTransformer;

    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public /* bridge */ /* synthetic */ String dumpValue(Object obj) {
        return dumpValue(((Boolean) obj).booleanValue());
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AbstractDirtyClassesMap(NameTransformer<Name> nameTransformer, File storageFile, IncrementalCompilationContext icContext) {
        Intrinsics.checkNotNullParameter(nameTransformer, "nameTransformer");
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        BooleanDataDescriptor INSTANCE = BooleanDataDescriptor.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
        super(storageFile, INSTANCE, icContext);
        this.nameTransformer = nameTransformer;
    }

    public final void markDirty(Name className) {
        getStorage().set(this.nameTransformer.asString(className), true);
    }

    public final void notDirty(Name className) {
        getStorage().remove(this.nameTransformer.asString(className));
    }

    public final Collection<Name> getDirtyOutputClasses() {
        Collection keys = getStorage().getKeys();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(keys, 10));
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            arrayList.add(this.nameTransformer.asName((String) it.next()));
        }
        return arrayList;
    }

    public final boolean isDirty(Name className) {
        return getStorage().contains(this.nameTransformer.asString(className));
    }

    protected String dumpValue(boolean value) {
        return "";
    }
}
