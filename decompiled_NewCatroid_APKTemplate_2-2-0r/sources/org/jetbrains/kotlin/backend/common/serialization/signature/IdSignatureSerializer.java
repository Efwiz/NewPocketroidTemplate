package org.jetbrains.kotlin.backend.common.serialization.signature;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.DeclarationTable;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.overrides.IrOverridingUtilKt;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: IdSignatureSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0016\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J \u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001fH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureSerializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureComputer;", "publicSignatureBuilder", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/PublicIdSignatureComputer;", "table", "Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;", "(Lorg/jetbrains/kotlin/backend/common/serialization/signature/PublicIdSignatureComputer;Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;)V", "startIndex", "", "(Lorg/jetbrains/kotlin/backend/common/serialization/signature/PublicIdSignatureComputer;Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;I)V", "localIndex", "", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "scopeIndex", "composeContainerIdSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "compatibleMode", "", "composeFileLocalIdSignature", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "composeSignatureForDeclaration", "computeSignature", "inFile", "", "file", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "block", "Lkotlin/Function0;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdSignatureSerializer implements IdSignatureComputer {
    private long localIndex;
    private final KotlinMangler.IrMangler mangler;
    private final PublicIdSignatureComputer publicSignatureBuilder;
    private int scopeIndex;
    private final DeclarationTable table;

    public IdSignatureSerializer(PublicIdSignatureComputer publicSignatureBuilder, DeclarationTable table, int i) {
        Intrinsics.checkNotNullParameter(publicSignatureBuilder, "publicSignatureBuilder");
        Intrinsics.checkNotNullParameter(table, "table");
        this.publicSignatureBuilder = publicSignatureBuilder;
        this.table = table;
        this.mangler = publicSignatureBuilder.getMangler();
        this.localIndex = i;
        this.scopeIndex = i;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IdSignatureSerializer(PublicIdSignatureComputer publicSignatureBuilder, DeclarationTable table) {
        this(publicSignatureBuilder, table, 0);
        Intrinsics.checkNotNullParameter(publicSignatureBuilder, "publicSignatureBuilder");
        Intrinsics.checkNotNullParameter(table, "table");
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureComputer
    public IdSignature computeSignature(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return this.publicSignatureBuilder.computeSignature(declaration);
    }

    public final IdSignature composeSignatureForDeclaration(IrDeclaration declaration, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (this.mangler.isExported(declaration, compatibleMode)) {
            return this.publicSignatureBuilder.composePublicIdSignature(declaration, compatibleMode);
        }
        return composeFileLocalIdSignature(declaration, compatibleMode);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureComputer
    public void inFile(IrFileSymbol file, Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.publicSignatureBuilder.inFile(file, block);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IdSignature composeContainerIdSignature(IrDeclarationParent container, boolean compatibleMode) {
        if (container instanceof IrPackageFragment) {
            String strAsString = ((IrPackageFragment) container).getPackageFqName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return new IdSignature.CommonSignature(strAsString, "", null, 0L, null);
        }
        if (container instanceof IrDeclaration) {
            return this.table.signatureByDeclaration((IrDeclaration) container, compatibleMode);
        }
        throw new IllegalStateException(("Unexpected container " + RenderIrElementKt.render$default(container, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public final IdSignature composeFileLocalIdSignature(final IrDeclaration declaration, final boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        this.mangler.isExported(declaration, compatibleMode);
        return this.table.privateDeclarationSignature(declaration, compatibleMode, new Function0<IdSignature>() { // from class: org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureSerializer.composeFileLocalIdSignature.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Code duplicated, block: B:17:0x0082  */
            /* JADX WARN: Code duplicated, block: B:25:0x00d0  */
            @Override // kotlin.jvm.functions.Function0
            public final IdSignature invoke() {
                long jSignatureMangle;
                IdSignature idSignatureComposeContainerIdSignature;
                long jSignatureMangle2;
                IdSignature idSignatureComposeContainerIdSignature2;
                IrDeclaration irDeclaration = declaration;
                if (irDeclaration instanceof IrValueDeclaration) {
                    IdSignatureSerializer idSignatureSerializer = this;
                    int i = idSignatureSerializer.scopeIndex;
                    idSignatureSerializer.scopeIndex = i + 1;
                    return new IdSignature.ScopeLocalDeclaration(i, ((IrValueDeclaration) declaration).getName().asString());
                }
                if (irDeclaration instanceof IrAnonymousInitializer) {
                    IdSignatureSerializer idSignatureSerializer2 = this;
                    int i2 = idSignatureSerializer2.scopeIndex;
                    idSignatureSerializer2.scopeIndex = i2 + 1;
                    return new IdSignature.ScopeLocalDeclaration(i2, "ANON INIT");
                }
                if (irDeclaration instanceof IrLocalDelegatedProperty) {
                    IdSignatureSerializer idSignatureSerializer3 = this;
                    int i3 = idSignatureSerializer3.scopeIndex;
                    idSignatureSerializer3.scopeIndex = i3 + 1;
                    return new IdSignature.ScopeLocalDeclaration(i3, ((IrLocalDelegatedProperty) declaration).getName().asString());
                }
                if (irDeclaration instanceof IrField) {
                    IrPropertySymbol correspondingPropertySymbol = ((IrField) irDeclaration).getCorrespondingPropertySymbol();
                    if (correspondingPropertySymbol != null) {
                        idSignatureComposeContainerIdSignature2 = this.composeSignatureForDeclaration(correspondingPropertySymbol.getOwner(), compatibleMode);
                        if (idSignatureComposeContainerIdSignature2 == null) {
                            idSignatureComposeContainerIdSignature2 = this.composeContainerIdSignature(declaration.getParent(), compatibleMode);
                        }
                    } else {
                        idSignatureComposeContainerIdSignature2 = this.composeContainerIdSignature(declaration.getParent(), compatibleMode);
                    }
                    IdSignatureSerializer idSignatureSerializer4 = this;
                    idSignatureSerializer4.localIndex++;
                    return new IdSignature.FileLocalSignature(idSignatureComposeContainerIdSignature2, idSignatureSerializer4.localIndex, null, 4, null);
                }
                if (irDeclaration instanceof IrSimpleFunction) {
                    IrDeclarationParent parent = irDeclaration.getParent();
                    IrPropertySymbol correspondingPropertySymbol2 = ((IrSimpleFunction) declaration).getCorrespondingPropertySymbol();
                    if (correspondingPropertySymbol2 != null) {
                        idSignatureComposeContainerIdSignature = this.composeSignatureForDeclaration(correspondingPropertySymbol2.getOwner(), compatibleMode);
                        if (idSignatureComposeContainerIdSignature == null) {
                            idSignatureComposeContainerIdSignature = this.composeContainerIdSignature(parent, compatibleMode);
                        }
                    } else {
                        idSignatureComposeContainerIdSignature = this.composeContainerIdSignature(parent, compatibleMode);
                    }
                    if (IrOverridingUtilKt.isOverridableFunction((IrSimpleFunction) declaration)) {
                        jSignatureMangle2 = this.mangler.signatureMangle(declaration, compatibleMode);
                    } else {
                        IdSignatureSerializer idSignatureSerializer5 = this;
                        idSignatureSerializer5.localIndex++;
                        jSignatureMangle2 = idSignatureSerializer5.localIndex;
                    }
                    return new IdSignature.FileLocalSignature(idSignatureComposeContainerIdSignature, jSignatureMangle2, RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null));
                }
                if (!(irDeclaration instanceof IrProperty)) {
                    IdSignature idSignatureComposeContainerIdSignature3 = this.composeContainerIdSignature(declaration.getParent(), compatibleMode);
                    IdSignatureSerializer idSignatureSerializer6 = this;
                    idSignatureSerializer6.localIndex++;
                    return new IdSignature.FileLocalSignature(idSignatureComposeContainerIdSignature3, idSignatureSerializer6.localIndex, RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null));
                }
                IdSignature idSignatureComposeContainerIdSignature4 = this.composeContainerIdSignature(irDeclaration.getParent(), compatibleMode);
                if (IrOverridingUtilKt.isOverridableProperty((IrProperty) declaration)) {
                    jSignatureMangle = this.mangler.signatureMangle(declaration, compatibleMode);
                } else {
                    IdSignatureSerializer idSignatureSerializer7 = this;
                    idSignatureSerializer7.localIndex++;
                    jSignatureMangle = idSignatureSerializer7.localIndex;
                }
                return new IdSignature.FileLocalSignature(idSignatureComposeContainerIdSignature4, jSignatureMangle, RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null));
            }
        });
    }
}
