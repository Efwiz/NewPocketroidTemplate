package org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.CompatibilityMode;
import org.jetbrains.kotlin.backend.common.serialization.DeclarationTable;
import org.jetbrains.kotlin.backend.common.serialization.IdSignatureClashTracker;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleSerializer;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;

/* JADX INFO: compiled from: JsIrModuleSerializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001Ba\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u000e¢\u0006\u0002\u0010\u0016J\u0010\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrModuleSerializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleSerializer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrFileSerializer;", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "expectDescriptorToSymbol", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "compatibilityMode", "Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "skipExpects", "", "normalizeAbsolutePaths", "sourceBaseDirs", "", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "shouldCheckSignaturesOnUniqueness", "(Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Ljava/util/Map;Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;ZZLjava/util/Collection;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Z)V", "globalDeclarationTable", "Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsGlobalDeclarationTable;", "getSkipExpects", "()Z", "createSerializerForFile", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrModuleSerializer extends IrModuleSerializer<JsIrFileSerializer> {
    private final Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol;
    private final JsGlobalDeclarationTable globalDeclarationTable;
    private final LanguageVersionSettings languageVersionSettings;
    private final boolean skipExpects;

    public /* synthetic */ JsIrModuleSerializer(IrMessageLogger irMessageLogger, IrBuiltIns irBuiltIns, Map map, CompatibilityMode compatibilityMode, boolean z, boolean z2, Collection collection, LanguageVersionSettings languageVersionSettings, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irMessageLogger, irBuiltIns, map, compatibilityMode, z, z2, collection, languageVersionSettings, (i & 256) != 0 ? true : z3);
    }

    public final boolean getSkipExpects() {
        return this.skipExpects;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsIrModuleSerializer(IrMessageLogger messageLogger, IrBuiltIns irBuiltIns, Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol, CompatibilityMode compatibilityMode, boolean z, boolean z2, Collection<String> sourceBaseDirs, LanguageVersionSettings languageVersionSettings, boolean z3) {
        super(messageLogger, compatibilityMode, z2, sourceBaseDirs);
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(expectDescriptorToSymbol, "expectDescriptorToSymbol");
        Intrinsics.checkNotNullParameter(compatibilityMode, "compatibilityMode");
        Intrinsics.checkNotNullParameter(sourceBaseDirs, "sourceBaseDirs");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.expectDescriptorToSymbol = expectDescriptorToSymbol;
        this.skipExpects = z;
        this.languageVersionSettings = languageVersionSettings;
        this.globalDeclarationTable = new JsGlobalDeclarationTable(irBuiltIns, z3 ? new JsUniqIdClashTracker() : IdSignatureClashTracker.INSTANCE.getDEFAULT_TRACKER());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleSerializer
    public JsIrFileSerializer createSerializerForFile(IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new JsIrFileSerializer(getMessageLogger(), new DeclarationTable(this.globalDeclarationTable), this.expectDescriptorToSymbol, getCompatibilityMode(), this.skipExpects, this.languageVersionSettings, false, getNormalizeAbsolutePaths(), getSourceBaseDirs(), 64, null);
    }
}
