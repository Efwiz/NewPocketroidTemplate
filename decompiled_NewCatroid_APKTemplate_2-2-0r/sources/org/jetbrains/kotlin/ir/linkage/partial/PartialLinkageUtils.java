package org.jetbrains.kotlin.ir.linkage.partial;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.FunctionInterfacePackageFragment;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.symbols.IrPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PartialLinkageUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u0012\u0013B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jb\u0010\u0003\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u0002H\u00042\u0006\u0010\b\u001a\u0002H\u00042\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u0002H\u00040\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u0002H\u00040\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0082\b¢\u0006\u0002\u0010\u0011¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils;", "", "()V", "determineFor", "R", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "onMissingDeclaration", "onSyntheticBuiltInFunction", "onIrBased", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "onLazyIrBased", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "onError", "Lkotlin/Function0;", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "File", "Module", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageUtils {
    public static final PartialLinkageUtils INSTANCE = new PartialLinkageUtils();

    private PartialLinkageUtils() {
    }

    /* JADX INFO: compiled from: PartialLinkageUtils.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000 \b2\u00020\u0001:\u0004\b\t\n\u000bJ\b\u0010\u0006\u001a\u00020\u0007H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0082\u0001\u0003\f\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "", "name", "", "getName", "()Ljava/lang/String;", "defaultLocationWithoutPath", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger$Location;", "Companion", "MissingDeclarations", "Real", "SyntheticBuiltInFunctions", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$MissingDeclarations;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$Real;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$SyntheticBuiltInFunctions;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Module {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = Companion.$$INSTANCE;

        PartialLinkageLogger.Location defaultLocationWithoutPath();

        String getName();

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\t\u001a\u00020\u0005HÆ\u0003J\u0013\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$Real;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Real implements Module {
            private final String name;

            public static /* synthetic */ Real copy$default(Real real, String str, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = real.name;
                }
                return real.copy(str);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final String getName() {
                return this.name;
            }

            public final Real copy(String name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                return new Real(name2);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Real) && Intrinsics.areEqual(this.name, ((Real) other).name);
            }

            public int hashCode() {
                return this.name.hashCode();
            }

            public String toString() {
                return "Real(name=" + this.name + ')';
            }

            public Real(String name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                this.name = name2;
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module
            public PartialLinkageLogger.Location defaultLocationWithoutPath() {
                return DefaultImpls.defaultLocationWithoutPath(this);
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module
            public String getName() {
                return this.name;
            }

            /* JADX WARN: Illegal instructions before constructor call */
            public Real(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                this(strAsString);
            }
        }

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$SyntheticBuiltInFunctions;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "()V", "name", "", "getName", "()Ljava/lang/String;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SyntheticBuiltInFunctions implements Module {
            public static final SyntheticBuiltInFunctions INSTANCE = new SyntheticBuiltInFunctions();
            private static final String name = "<synthetic built-in functions>";

            private SyntheticBuiltInFunctions() {
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module
            public PartialLinkageLogger.Location defaultLocationWithoutPath() {
                return DefaultImpls.defaultLocationWithoutPath(this);
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module
            public String getName() {
                return name;
            }
        }

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$MissingDeclarations;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "()V", "name", "", "getName", "()Ljava/lang/String;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class MissingDeclarations implements Module {
            public static final MissingDeclarations INSTANCE = new MissingDeclarations();
            private static final String name = "<missing declarations>";

            private MissingDeclarations() {
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module
            public PartialLinkageLogger.Location defaultLocationWithoutPath() {
                return DefaultImpls.defaultLocationWithoutPath(this);
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module
            public String getName() {
                return name;
            }
        }

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public static final class DefaultImpls {
            public static PartialLinkageLogger.Location defaultLocationWithoutPath(Module module) {
                return new PartialLinkageLogger.Location(module.getName(), "", -1, -1);
            }
        }

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$Companion;", "", "()V", "determineModuleFor", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            public final Module determineModuleFor(IrDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                PartialLinkageUtils partialLinkageUtils = PartialLinkageUtils.INSTANCE;
                Real real = MissingDeclarations.INSTANCE;
                SyntheticBuiltInFunctions syntheticBuiltInFunctions = SyntheticBuiltInFunctions.INSTANCE;
                if (declaration.getOrigin() != PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION) {
                    IrPackageFragment packageFragment = IrUtilsKt.getPackageFragment(declaration);
                    IrPackageFragmentSymbol symbol = packageFragment.getSymbol();
                    PackageFragmentDescriptor descriptor = symbol.getHasDescriptor() ? symbol.getDescriptor() : null;
                    if (descriptor instanceof FunctionInterfacePackageFragment) {
                        real = syntheticBuiltInFunctions;
                    } else if (packageFragment instanceof IrFile) {
                        real = new Real(((IrFile) packageFragment).getModule().getName());
                    } else if ((packageFragment instanceof IrExternalPackageFragment) && descriptor != null) {
                        Name name2 = descriptor.getModuleDescriptor().getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        real = new Real(name2);
                    } else {
                        StringBuilder sbAppend = new StringBuilder("Can't determine module for ").append(declaration).append(", name=");
                        IrDeclarationWithName irDeclarationWithName = declaration instanceof IrDeclarationWithName ? (IrDeclarationWithName) declaration : null;
                        throw new IllegalStateException(sbAppend.append(irDeclarationWithName != null ? irDeclarationWithName.getName() : null).toString().toString());
                    }
                }
                return real;
            }
        }
    }

    /* JADX INFO: compiled from: PartialLinkageUtils.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000 \n2\u00020\u0001:\u0005\n\u000b\f\r\u000eJ\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0082\u0001\u0004\u000f\u0010\u0011\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "", "module", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "getModule", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "computeLocationForOffset", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger$Location;", TypedValues.CycleType.S_WAVE_OFFSET, "", "Companion", "IrBased", "LazyIrBased", "MissingDeclarations", "SyntheticBuiltInFunctions", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File$IrBased;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File$LazyIrBased;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File$MissingDeclarations;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File$SyntheticBuiltInFunctions;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface File {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = Companion.$$INSTANCE;

        PartialLinkageLogger.Location computeLocationForOffset(int offset);

        Module getModule();

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\t\u001a\u00020\u0003HÂ\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0013\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\rHÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File$IrBased;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "module", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$Real;", "getModule", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$Real;", "component1", "computeLocationForOffset", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger$Location;", TypedValues.CycleType.S_WAVE_OFFSET, "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class IrBased implements File {
            private final IrFile file;
            private final Module.Real module;

            public static /* synthetic */ IrBased copy$default(IrBased irBased, IrFile irFile, int i, Object obj) {
                if ((i & 1) != 0) {
                    irFile = irBased.file;
                }
                return irBased.copy(irFile);
            }

            public final IrBased copy(IrFile file) {
                Intrinsics.checkNotNullParameter(file, "file");
                return new IrBased(file);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof IrBased) && Intrinsics.areEqual(this.file, ((IrBased) other).file);
            }

            public int hashCode() {
                return this.file.hashCode();
            }

            public String toString() {
                return "IrBased(file=" + this.file + ')';
            }

            public IrBased(IrFile file) {
                Intrinsics.checkNotNullParameter(file, "file");
                this.file = file;
                this.module = new Module.Real(file.getModule().getName());
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File
            public Module.Real getModule() {
                return this.module;
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File
            public PartialLinkageLogger.Location computeLocationForOffset(int offset) {
                return new PartialLinkageLogger.Location(getModule().getName(), this.file.getFileEntry().getName(), offset == -1 ? -1 : this.file.getFileEntry().getLineNumber(offset) + 1, offset != -1 ? this.file.getFileEntry().getColumnNumber(offset) + 1 : -1);
            }
        }

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File$LazyIrBased;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "packageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;)V", "defaultLocation", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger$Location;", "module", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$Real;", "getModule", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$Real;", "computeLocationForOffset", TypedValues.CycleType.S_WAVE_OFFSET, "", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class LazyIrBased implements File {
            private final PartialLinkageLogger.Location defaultLocation;
            private final Module.Real module;

            public LazyIrBased(PackageFragmentDescriptor packageFragmentDescriptor) {
                Intrinsics.checkNotNullParameter(packageFragmentDescriptor, "packageFragmentDescriptor");
                Name name2 = packageFragmentDescriptor.getModuleDescriptor().getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                this.module = new Module.Real(name2);
                this.defaultLocation = getModule().defaultLocationWithoutPath();
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File
            public Module.Real getModule() {
                return this.module;
            }

            public boolean equals(Object other) {
                LazyIrBased lazyIrBased = other instanceof LazyIrBased ? (LazyIrBased) other : null;
                return Intrinsics.areEqual(lazyIrBased != null ? lazyIrBased.getModule() : null, getModule());
            }

            public int hashCode() {
                return getModule().hashCode();
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File
            public PartialLinkageLogger.Location computeLocationForOffset(int offset) {
                return this.defaultLocation;
            }
        }

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File$SyntheticBuiltInFunctions;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "()V", "defaultLocation", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger$Location;", "module", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$SyntheticBuiltInFunctions;", "getModule", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$SyntheticBuiltInFunctions;", "computeLocationForOffset", TypedValues.CycleType.S_WAVE_OFFSET, "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SyntheticBuiltInFunctions implements File {
            public static final SyntheticBuiltInFunctions INSTANCE;
            private static final PartialLinkageLogger.Location defaultLocation;
            private static final Module.SyntheticBuiltInFunctions module;

            private SyntheticBuiltInFunctions() {
            }

            static {
                SyntheticBuiltInFunctions syntheticBuiltInFunctions = new SyntheticBuiltInFunctions();
                INSTANCE = syntheticBuiltInFunctions;
                module = Module.SyntheticBuiltInFunctions.INSTANCE;
                defaultLocation = syntheticBuiltInFunctions.getModule().defaultLocationWithoutPath();
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File
            public Module.SyntheticBuiltInFunctions getModule() {
                return module;
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File
            public PartialLinkageLogger.Location computeLocationForOffset(int offset) {
                return defaultLocation;
            }
        }

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File$MissingDeclarations;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "()V", "defaultLocation", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger$Location;", "module", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$MissingDeclarations;", "getModule", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module$MissingDeclarations;", "computeLocationForOffset", TypedValues.CycleType.S_WAVE_OFFSET, "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class MissingDeclarations implements File {
            public static final MissingDeclarations INSTANCE;
            private static final PartialLinkageLogger.Location defaultLocation;
            private static final Module.MissingDeclarations module;

            private MissingDeclarations() {
            }

            static {
                MissingDeclarations missingDeclarations = new MissingDeclarations();
                INSTANCE = missingDeclarations;
                module = Module.MissingDeclarations.INSTANCE;
                defaultLocation = missingDeclarations.getModule().defaultLocationWithoutPath();
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File
            public Module.MissingDeclarations getModule() {
                return module;
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File
            public PartialLinkageLogger.Location computeLocationForOffset(int offset) {
                return defaultLocation;
            }
        }

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File$Companion;", "", "()V", "determineFileFor", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            public final File determineFileFor(IrDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                PartialLinkageUtils partialLinkageUtils = PartialLinkageUtils.INSTANCE;
                File lazyIrBased = MissingDeclarations.INSTANCE;
                SyntheticBuiltInFunctions syntheticBuiltInFunctions = SyntheticBuiltInFunctions.INSTANCE;
                if (declaration.getOrigin() != PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION) {
                    IrPackageFragment packageFragment = IrUtilsKt.getPackageFragment(declaration);
                    IrPackageFragmentSymbol symbol = packageFragment.getSymbol();
                    PackageFragmentDescriptor descriptor = symbol.getHasDescriptor() ? symbol.getDescriptor() : null;
                    if (descriptor instanceof FunctionInterfacePackageFragment) {
                        lazyIrBased = syntheticBuiltInFunctions;
                    } else if (packageFragment instanceof IrFile) {
                        lazyIrBased = new IrBased((IrFile) packageFragment);
                    } else if ((packageFragment instanceof IrExternalPackageFragment) && descriptor != null) {
                        lazyIrBased = new LazyIrBased(descriptor);
                    } else {
                        StringBuilder sbAppend = new StringBuilder("Can't determine file for ").append(declaration).append(", name=");
                        IrDeclarationWithName irDeclarationWithName = declaration instanceof IrDeclarationWithName ? (IrDeclarationWithName) declaration : null;
                        throw new IllegalStateException(sbAppend.append(irDeclarationWithName != null ? irDeclarationWithName.getName() : null).toString().toString());
                    }
                }
                return lazyIrBased;
            }
        }
    }
}
