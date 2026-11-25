package org.jetbrains.kotlin.ir.linkage;

import java.util.Collection;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.builders.TranslationPluginContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrDeserializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u0014\u0015J \u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\b\u0010\t\u001a\u00020\u0003H\u0017J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer;", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", ConfigConstants.CONFIG_INIT_SECTION, "", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "extensions", "", "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$IrLinkerExtension;", "postProcess", "inOrAfterLinkageStep", "", "resolveBySignatureInModule", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$TopLevelSymbolKind;", "moduleName", "Lorg/jetbrains/kotlin/name/Name;", "IrLinkerExtension", "TopLevelSymbolKind", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrDeserializer extends IrProvider {

    /* JADX INFO: compiled from: IrDeserializer.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$IrLinkerExtension;", "", "resolveSymbol", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "context", "Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface IrLinkerExtension {

        /* JADX INFO: compiled from: IrDeserializer.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public static final class DefaultImpls {
            public static IrDeclaration resolveSymbol(IrLinkerExtension irLinkerExtension, IrSymbol symbol, TranslationPluginContext context) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(context, "context");
                return null;
            }
        }

        IrDeclaration resolveSymbol(IrSymbol symbol, TranslationPluginContext context);
    }

    /* JADX INFO: compiled from: IrDeserializer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$TopLevelSymbolKind;", "", "(Ljava/lang/String;I)V", "FUNCTION_SYMBOL", "CLASS_SYMBOL", "PROPERTY_SYMBOL", "TYPEALIAS_SYMBOL", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum TopLevelSymbolKind {
        FUNCTION_SYMBOL,
        CLASS_SYMBOL,
        PROPERTY_SYMBOL,
        TYPEALIAS_SYMBOL;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<TopLevelSymbolKind> getEntries() {
            return $ENTRIES;
        }
    }

    void init(IrModuleFragment moduleFragment, Collection<? extends IrLinkerExtension> extensions);

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use postProcess(inOrAfterLinkageStep) instead", replaceWith = @ReplaceWith(expression = "postProcess(inOrAfterLinkageStep = true)", imports = {}))
    void postProcess();

    void postProcess(boolean inOrAfterLinkageStep);

    IrSymbol resolveBySignatureInModule(IdSignature signature, TopLevelSymbolKind kind, Name moduleName);

    /* JADX INFO: compiled from: IrDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void init(IrDeserializer irDeserializer, IrModuleFragment irModuleFragment, Collection<? extends IrLinkerExtension> extensions) {
            Intrinsics.checkNotNullParameter(extensions, "extensions");
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "Use postProcess(inOrAfterLinkageStep) instead", replaceWith = @ReplaceWith(expression = "postProcess(inOrAfterLinkageStep = true)", imports = {}))
        public static void postProcess(IrDeserializer irDeserializer) {
            irDeserializer.postProcess(true);
        }
    }
}
