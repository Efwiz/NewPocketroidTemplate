package org.jetbrains.kotlin.psi2ir.generators;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DeclarationGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J3\u0010\u000b\u001a\u0002H\f\"\b\b\u0000\u0010\f*\u00020\r*\u0002H\f2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u00100\u000fH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0011J\n\u0010\u0012\u001a\u00020\u0013*\u00020\u0014R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGeneratorExtension;", "Lorg/jetbrains/kotlin/psi2ir/generators/Generator;", "declarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;)V", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "getContext", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "getDeclarationGenerator", "()Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "buildWithScope", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "builder", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeclarationGeneratorExtension implements Generator {
    private final DeclarationGenerator declarationGenerator;

    public DeclarationGeneratorExtension(DeclarationGenerator declarationGenerator) {
        Intrinsics.checkNotNullParameter(declarationGenerator, "declarationGenerator");
        this.declarationGenerator = declarationGenerator;
    }

    public final DeclarationGenerator getDeclarationGenerator() {
        return this.declarationGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGenerator
    public GeneratorContext getContext() {
        return this.declarationGenerator.getContext();
    }

    public final <T extends IrDeclaration> T buildWithScope(T t, Function1<? super T, Unit> builder) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(t);
        builder.invoke(t);
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(t);
        return t;
    }

    public final IrType toIrType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return this.declarationGenerator.toIrType(kotlinType);
    }
}
