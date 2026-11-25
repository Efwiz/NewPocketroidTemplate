package org.jetbrains.kotlin.backend.common.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer;
import org.jetbrains.kotlin.builtins.FunctionInterfacePackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.library.SerializedIrFile;
import org.jetbrains.kotlin.library.SerializedIrModule;

/* JADX INFO: compiled from: IrModuleSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B+\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJ\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\u0015\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u0018H&¢\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\b\u001a\u00020\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleSerializer;", "F", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer;", "", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "compatibilityMode", "Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "normalizeAbsolutePaths", "", "sourceBaseDirs", "", "", "(Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;ZLjava/util/Collection;)V", "getCompatibilityMode", "()Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "getMessageLogger", "()Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "getNormalizeAbsolutePaths", "()Z", "getSourceBaseDirs", "()Ljava/util/Collection;", "backendSpecificFileFilter", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "createSerializerForFile", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;)Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer;", "serializeIrFile", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "serializedIrModule", "Lorg/jetbrains/kotlin/library/SerializedIrModule;", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrModuleSerializer<F extends IrFileSerializer> {
    private final CompatibilityMode compatibilityMode;
    private final IrMessageLogger messageLogger;
    private final boolean normalizeAbsolutePaths;
    private final Collection<String> sourceBaseDirs;

    protected boolean backendSpecificFileFilter(IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return true;
    }

    public abstract F createSerializerForFile(IrFile file);

    public IrModuleSerializer(IrMessageLogger messageLogger, CompatibilityMode compatibilityMode, boolean z, Collection<String> sourceBaseDirs) {
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(compatibilityMode, "compatibilityMode");
        Intrinsics.checkNotNullParameter(sourceBaseDirs, "sourceBaseDirs");
        this.messageLogger = messageLogger;
        this.compatibilityMode = compatibilityMode;
        this.normalizeAbsolutePaths = z;
        this.sourceBaseDirs = sourceBaseDirs;
    }

    protected final CompatibilityMode getCompatibilityMode() {
        return this.compatibilityMode;
    }

    protected final IrMessageLogger getMessageLogger() {
        return this.messageLogger;
    }

    protected final boolean getNormalizeAbsolutePaths() {
        return this.normalizeAbsolutePaths;
    }

    protected final Collection<String> getSourceBaseDirs() {
        return this.sourceBaseDirs;
    }

    private final SerializedIrFile serializeIrFile(IrFile file) {
        return createSerializerForFile(file).serializeIrFile(file);
    }

    public final SerializedIrModule serializedIrModule(IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(module, "module");
        List<IrFile> files = module.getFiles();
        ArrayList arrayList = new ArrayList();
        for (Object obj : files) {
            if (!(((IrFile) obj).getPackageFragmentDescriptor() instanceof FunctionInterfacePackageFragment)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (backendSpecificFileFilter((IrFile) obj2)) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            arrayList4.add(serializeIrFile((IrFile) it.next()));
        }
        return new SerializedIrModule(arrayList4);
    }
}
