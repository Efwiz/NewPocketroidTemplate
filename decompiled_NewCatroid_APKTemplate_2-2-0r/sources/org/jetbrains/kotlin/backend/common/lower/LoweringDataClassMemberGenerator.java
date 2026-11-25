package org.jetbrains.kotlin.backend.common.lower;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContextBase;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DataClassMembersGenerator;
import org.jetbrains.kotlin.ir.util.IrBasedDataClassMembersGenerator;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: MethodsFromAnyGeneratorForLowerings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LoweringDataClassMemberGenerator;", "Lorg/jetbrains/kotlin/ir/util/IrBasedDataClassMembersGenerator;", "backendContext", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "forbidDirectFieldAccess", "", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Z)V", "getBackendContext", "()Lorg/jetbrains/kotlin/backend/common/BackendContext;", "generateSyntheticFunctionParameterDeclarations", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getHashCodeFunctionInfo", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$HashCodeFunctionInfo;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "irValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LoweringDataClassMemberGenerator extends IrBasedDataClassMembersGenerator {
    private final BackendContext backendContext;

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    public void generateSyntheticFunctionParameterDeclarations(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
    }

    public /* synthetic */ LoweringDataClassMemberGenerator(BackendContext backendContext, IrClass irClass, IrDeclarationOrigin irDeclarationOrigin, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(backendContext, irClass, irDeclarationOrigin, (i & 8) != 0 ? false : z);
    }

    public final BackendContext getBackendContext() {
        return this.backendContext;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LoweringDataClassMemberGenerator(BackendContext backendContext, IrClass irClass, IrDeclarationOrigin origin, boolean z) {
        super(new IrGeneratorContextBase(backendContext.getIrBuiltIns()), backendContext.getIr().getSymbols().getSymbolTable(), irClass, AdditionalIrUtilsKt.getKotlinFqName(irClass), origin, z);
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.backendContext = backendContext;
    }

    @Override // org.jetbrains.kotlin.ir.util.IrBasedDataClassMembersGenerator
    public IrProperty getProperty(IrValueParameter irValueParameter) {
        throw new IllegalStateException("This API shouldn't be used in lowerings".toString());
    }

    /* JADX WARN: Code duplicated, block: B:23:0x004c  */
    /* JADX WARN: Code duplicated, block: B:26:0x0066  */
    /* JADX WARN: Code duplicated, block: B:29:0x0085  */
    /* JADX WARN: Code duplicated, block: B:33:0x0092  */
    /* JADX WARN: Code duplicated, block: B:34:0x0096  */
    /* JADX WARN: Code duplicated, block: B:46:0x0083 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:47:0x0088 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:49:0x0060 A[SYNTHETIC] */
    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    public DataClassMembersGenerator.HashCodeFunctionInfo getHashCodeFunctionInfo(IrType type) {
        IrSimpleFunctionSymbol dataClassArrayMemberHashCodeSymbol;
        Sequence<IrSimpleFunctionSymbol> functions;
        Intrinsics.checkNotNullParameter(type, "type");
        if (IrTypePredicatesKt.isArray(type) || IrTypeUtilsKt.isPrimitiveArray(type)) {
            dataClassArrayMemberHashCodeSymbol = getContext().getIrBuiltIns().getDataClassArrayMemberHashCodeSymbol();
        } else {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(type);
            boolean z = false;
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
            if (classOrNull != null && (functions = IrUtilsKt.getFunctions(classOrNull)) != null) {
                Iterator<IrSimpleFunctionSymbol> it = functions.iterator();
                boolean z2 = false;
                IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = null;
                while (true) {
                    if (!it.hasNext()) {
                        if (!z2) {
                            break;
                        }
                        break;
                    }
                    IrSimpleFunctionSymbol next = it.next();
                    if (IrUtilsKt.isHashCode(next.getOwner())) {
                        if (!z2) {
                            z2 = true;
                            irSimpleFunctionSymbol2 = next;
                        }
                    }
                    irSimpleFunctionSymbol2 = null;
                    break;
                }
                dataClassArrayMemberHashCodeSymbol = irSimpleFunctionSymbol2;
                if (dataClassArrayMemberHashCodeSymbol == null) {
                    for (IrSimpleFunctionSymbol irSimpleFunctionSymbol3 : IrUtilsKt.getFunctions(getContext().getIrBuiltIns().getAnyClass())) {
                        if (!Intrinsics.areEqual(irSimpleFunctionSymbol3.getOwner().getName().asString(), "hashCode")) {
                            if (!z) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            z = true;
                            irSimpleFunctionSymbol = irSimpleFunctionSymbol3;
                        }
                    }
                    if (z) {
                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                    }
                    dataClassArrayMemberHashCodeSymbol = irSimpleFunctionSymbol;
                }
            } else {
                while (r8.hasNext()) {
                    if (!Intrinsics.areEqual(irSimpleFunctionSymbol3.getOwner().getName().asString(), "hashCode")) {
                        if (!z) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        z = true;
                        irSimpleFunctionSymbol = irSimpleFunctionSymbol3;
                    }
                }
                if (z) {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                dataClassArrayMemberHashCodeSymbol = irSimpleFunctionSymbol;
            }
        }
        return new DataClassMembersGenerator.HashCodeFunctionInfo(dataClassArrayMemberHashCodeSymbol) { // from class: org.jetbrains.kotlin.backend.common.lower.LoweringDataClassMemberGenerator.getHashCodeFunctionInfo.1
            private final IrSimpleFunctionSymbol symbol;

            @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo
            public void commitSubstituted(IrMemberAccessExpression<?> irMemberAccessExpression) {
                Intrinsics.checkNotNullParameter(irMemberAccessExpression, "irMemberAccessExpression");
            }

            {
                this.symbol = dataClassArrayMemberHashCodeSymbol;
            }

            @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo
            public IrSimpleFunctionSymbol getSymbol() {
                return this.symbol;
            }
        };
    }
}
