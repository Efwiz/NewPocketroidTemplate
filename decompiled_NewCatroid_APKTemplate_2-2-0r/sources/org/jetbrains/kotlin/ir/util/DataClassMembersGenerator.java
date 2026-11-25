package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DataClassMembersGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001:\u0002>?B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H&J\u0010\u0010'\u001a\u00020(2\u0006\u0010\u001e\u001a\u00020\u001fH&J \u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020 2\u0006\u0010.\u001a\u00020*H\u0014J3\u0010/\u001a\u0002H0\"\b\b\u0000\u00100*\u000201*\u0002H02\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H0\u0012\u0004\u0012\u00020$02H\u0086\bø\u0001\u0000¢\u0006\u0002\u00103J\f\u00104\u001a\u000205*\u00020\u0007H\u0016J\u001c\u0010)\u001a\u00020**\u00020,2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010.\u001a\u00020*H\u0004J\f\u00106\u001a\u00020\r*\u000207H$J\u0014\u00108\u001a\u00020**\u00020,2\u0006\u00109\u001a\u00020:H\u0014J\u0012\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001f0<*\u00020=H$R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001b\u001a\u00020\r*\u0004\u0018\u00010\u001c8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001dR\u0018\u0010\u001e\u001a\u00020\u001f*\u00020 8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator;", "", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "forbidDirectFieldAccess", "", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Z)V", "getContext", "()Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "getForbidDirectFieldAccess", "()Z", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "isArrayOrPrimitiveArray", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;)Z", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getType", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Lorg/jetbrains/kotlin/ir/types/IrType;", "generateSyntheticFunctionParameterDeclarations", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getHashCodeFunctionInfo", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$HashCodeFunctionInfo;", "getHashCodeOf", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "property", "irValue", "buildWithScope", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "classNameForToString", "", "hasDispatchReceiver", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "shiftResultOfHashCode", "irResultVar", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "typesOfTypeParameters", "", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "HashCodeFunctionInfo", "MemberFunctionBuilder", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DataClassMembersGenerator {
    private final IrGeneratorContext context;
    private final boolean forbidDirectFieldAccess;
    private final FqName fqName;
    private final IrClass irClass;
    private final IrDeclarationOrigin origin;
    private final ReferenceSymbolTable symbolTable;

    /* JADX INFO: compiled from: DataClassMembersGenerator.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$HashCodeFunctionInfo;", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "commitSubstituted", "", "irMemberAccessExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface HashCodeFunctionInfo {
        void commitSubstituted(IrMemberAccessExpression<?> irMemberAccessExpression);

        IrSimpleFunctionSymbol getSymbol();
    }

    public abstract void generateSyntheticFunctionParameterDeclarations(IrFunction irFunction);

    public abstract HashCodeFunctionInfo getHashCodeFunctionInfo(IrType type);

    protected final IrType getType(IrProperty irProperty) {
        IrType type;
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        IrField backingField = irProperty.getBackingField();
        if (backingField != null && (type = backingField.getType()) != null) {
            return type;
        }
        IrSimpleFunction getter = irProperty.getGetter();
        if (getter != null) {
            return getter.getReturnType();
        }
        throw new IllegalStateException(("Can't find type of " + RenderIrElementKt.render$default(irProperty, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    protected abstract boolean hasDispatchReceiver(IrSimpleFunctionSymbol irSimpleFunctionSymbol);

    protected abstract List<IrType> typesOfTypeParameters(IrConstructorSymbol irConstructorSymbol);

    public DataClassMembersGenerator(IrGeneratorContext context, ReferenceSymbolTable symbolTable, IrClass irClass, FqName fqName, IrDeclarationOrigin origin, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.context = context;
        this.symbolTable = symbolTable;
        this.irClass = irClass;
        this.fqName = fqName;
        this.origin = origin;
        this.forbidDirectFieldAccess = z;
    }

    public /* synthetic */ DataClassMembersGenerator(IrGeneratorContext irGeneratorContext, ReferenceSymbolTable referenceSymbolTable, IrClass irClass, FqName fqName, IrDeclarationOrigin irDeclarationOrigin, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irGeneratorContext, referenceSymbolTable, irClass, fqName, irDeclarationOrigin, (i & 32) != 0 ? false : z);
    }

    public final IrGeneratorContext getContext() {
        return this.context;
    }

    public final ReferenceSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final IrClass getIrClass() {
        return this.irClass;
    }

    public final FqName getFqName() {
        return this.fqName;
    }

    public final IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    public final boolean getForbidDirectFieldAccess() {
        return this.forbidDirectFieldAccess;
    }

    public final <T extends IrDeclaration> T buildWithScope(T t, Function1<? super T, Unit> builder) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        ReferenceSymbolTable symbolTable = getSymbolTable();
        symbolTable.enterScope(t);
        builder.invoke(t);
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(t);
        return t;
    }

    /* JADX INFO: compiled from: DataClassMembersGenerator.kt */
    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\b\u0084\u0004\u0018\u00002\u00020\u0001B!\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J/\u0010\n\u001a\u00020\u00062!\u0010\u000b\u001a\u001d\u0012\b\u0012\u00060\u0000R\u00020\r\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\u0002\b\u000fH\u0086\bø\u0001\u0000J/\u0010\u0010\u001a\u00020\u000e2!\u0010\u000b\u001a\u001d\u0012\b\u0012\u00060\u0000R\u00020\r\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\u0002\b\u000fH\u0086\bø\u0001\u0000J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016J\u0014\u0010\u0017\u001a\u00020\u000e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019J\u001c\u0010\u001a\u001a\u00020\u000e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u00192\u0006\u0010\u001b\u001a\u00020\u0003J\u0014\u0010\u001c\u001a\u00020\u000e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0013H\u0002J\u0016\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0013J\b\u0010\"\u001a\u00020\u001eH\u0002J\u0006\u0010#\u001a\u00020\u001eJ\f\u0010$\u001a\u00020%*\u00020&H\u0002R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$MemberFunctionBuilder;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "startOffset", "", "endOffset", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator;IILorg/jetbrains/kotlin/ir/declarations/IrFunction;)V", "getIrFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "addToClass", "builder", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator;", "", "Lkotlin/ExtensionFunctionType;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "generateComponentFunction", "irProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "generateCopyFunction", "constructorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "generateEqualsMethodBody", "properties", "", "generateHashCodeMethodBody", "constHashCode", "generateToStringMethodBody", "getHashCodeOfProperty", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "property", "irGetProperty", "receiver", "irOther", "irThis", "isTypedEqualsInValueClass", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected final class MemberFunctionBuilder extends IrBlockBodyBuilder {
        private final IrFunction irFunction;
        final /* synthetic */ DataClassMembersGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MemberFunctionBuilder(DataClassMembersGenerator dataClassMembersGenerator, int i, int i2, IrFunction irFunction) {
            super(dataClassMembersGenerator.getContext(), new Scope(irFunction.getSymbol()), i, i2);
            Intrinsics.checkNotNullParameter(irFunction, "irFunction");
            this.this$0 = dataClassMembersGenerator;
            this.irFunction = irFunction;
        }

        public /* synthetic */ MemberFunctionBuilder(DataClassMembersGenerator dataClassMembersGenerator, int i, int i2, IrFunction irFunction, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(dataClassMembersGenerator, (i3 & 1) != 0 ? -2 : i, (i3 & 2) != 0 ? -2 : i2, irFunction);
        }

        public final IrFunction getIrFunction() {
            return this.irFunction;
        }

        public final void build(Function2<? super MemberFunctionBuilder, ? super IrFunction, Unit> builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            DataClassMembersGenerator dataClassMembersGenerator = this.this$0;
            IrFunction irFunction = getIrFunction();
            ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
            symbolTable.enterScope(irFunction);
            builder.invoke(this, getIrFunction());
            getIrFunction().setBody(getIrBlockBody());
            Unit unit = Unit.INSTANCE;
            symbolTable.leaveScope(irFunction);
        }

        public final IrExpression irThis() {
            IrValueParameter dispatchReceiverParameter = this.irFunction.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            return new IrGetValueImpl(getStartOffset(), getEndOffset(), dispatchReceiverParameter.getType(), dispatchReceiverParameter.getSymbol(), null, 16, null);
        }

        private final IrExpression irOther() {
            IrValueParameter irValueParameter = this.irFunction.getValueParameters().get(0);
            return new IrGetValueImpl(getStartOffset(), getEndOffset(), irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null);
        }

        public final IrExpression irGetProperty(IrExpression receiver, IrProperty property) {
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            Intrinsics.checkNotNullParameter(property, "property");
            IrField backingField = property.getBackingField();
            if (!this.this$0.getForbidDirectFieldAccess() && IrUtilsKt.isFinalClass(this.this$0.getIrClass()) && backingField != null) {
                return ExpressionHelpersKt.irGetField$default(this, receiver, backingField, null, 4, null);
            }
            IrSimpleFunction getter = property.getGetter();
            Intrinsics.checkNotNull(getter);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(this, getter);
            irFunctionAccessExpressionIrCall.setDispatchReceiver(receiver);
            return irFunctionAccessExpressionIrCall;
        }

        public final void generateComponentFunction(IrProperty irProperty) {
            Intrinsics.checkNotNullParameter(irProperty, "irProperty");
            unaryPlus(ExpressionHelpersKt.irReturn(this, irGetProperty(irThis(), irProperty)));
        }

        public final void generateCopyFunction(IrConstructorSymbol constructorSymbol) {
            Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
            MemberFunctionBuilder memberFunctionBuilder = this;
            IrConstructorCall irConstructorCallIrCall = ExpressionHelpersKt.irCall(memberFunctionBuilder, constructorSymbol, IrUtilsKt.getDefaultType(this.this$0.getIrClass()), this.this$0.getIrClass());
            Iterator<IrType> it = this.this$0.typesOfTypeParameters(constructorSymbol).iterator();
            int i = 0;
            int i2 = 0;
            while (it.hasNext()) {
                irConstructorCallIrCall.putTypeArgument(i2, it.next());
                i2++;
            }
            for (IrValueParameter irValueParameter : this.irFunction.getValueParameters()) {
                irConstructorCallIrCall.putValueArgument(i, ExpressionHelpersKt.irGet(memberFunctionBuilder, irValueParameter.getType(), irValueParameter.getSymbol()));
                i++;
            }
            Unit unit = Unit.INSTANCE;
            unaryPlus(ExpressionHelpersKt.irReturn(memberFunctionBuilder, irConstructorCallIrCall));
        }

        private final boolean isTypedEqualsInValueClass(IrSimpleFunction irSimpleFunction) {
            return Intrinsics.areEqual(irSimpleFunction.getName(), OperatorNameConventions.EQUALS) && Intrinsics.areEqual(irSimpleFunction.getReturnType(), getContext().getIrBuiltIns().getBooleanType()) && this.this$0.getIrClass().getIsValue() && irSimpleFunction.getValueParameters().size() == 1 && Intrinsics.areEqual(IrTypesKt.getClassifierOrNull(irSimpleFunction.getValueParameters().get(0).getType()), this.this$0.getIrClass().getSymbol()) && irSimpleFunction.getContextReceiverParametersCount() == 0 && irSimpleFunction.getExtensionReceiverParameter() == null;
        }

        public final void generateEqualsMethodBody(List<? extends IrProperty> properties) {
            Intrinsics.checkNotNullParameter(properties, "properties");
            IrSimpleType defaultType = IrUtilsKt.getDefaultType(this.this$0.getIrClass());
            Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(this.this$0.getIrClass()).iterator();
            IrSimpleFunction irSimpleFunction = null;
            IrSimpleFunction irSimpleFunction2 = null;
            boolean z = false;
            while (true) {
                if (!it.hasNext()) {
                    if (!z) {
                        break;
                    }
                    irSimpleFunction = irSimpleFunction2;
                    break;
                } else {
                    IrSimpleFunction next = it.next();
                    if (isTypedEqualsInValueClass(next)) {
                        if (z) {
                            break;
                        }
                        z = true;
                        irSimpleFunction2 = next;
                    }
                }
            }
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
            if (this.this$0.getIrClass().getIsValue() && irSimpleFunction3 != null) {
                MemberFunctionBuilder memberFunctionBuilder = this;
                IrSimpleType irSimpleType = defaultType;
                unaryPlus(ExpressionHelpersKt.irIfThenReturnFalse(memberFunctionBuilder, ExpressionHelpersKt.irNotIs(memberFunctionBuilder, irOther(), irSimpleType)));
                IrTypeOperatorCallImpl irTypeOperatorCallImplIrImplicitCast = ExpressionHelpersKt.irImplicitCast(memberFunctionBuilder, irOther(), irSimpleType);
                IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(memberFunctionBuilder, irSimpleFunction3);
                IrValueParameter dispatchReceiverParameter = irSimpleFunction3.getDispatchReceiverParameter();
                Intrinsics.checkNotNull(dispatchReceiverParameter);
                IrExpressionsKt.putArgument(irFunctionAccessExpressionIrCall, dispatchReceiverParameter, irThis());
                irFunctionAccessExpressionIrCall.putValueArgument(0, irTypeOperatorCallImplIrImplicitCast);
                Unit unit = Unit.INSTANCE;
                unaryPlus(ExpressionHelpersKt.irReturn(memberFunctionBuilder, irFunctionAccessExpressionIrCall));
                return;
            }
            if (!this.this$0.getIrClass().getIsValue()) {
                MemberFunctionBuilder memberFunctionBuilder2 = this;
                unaryPlus(ExpressionHelpersKt.irIfThenReturnTrue(memberFunctionBuilder2, ExpressionHelpersKt.irEqeqeq(memberFunctionBuilder2, irThis(), irOther())));
            }
            MemberFunctionBuilder memberFunctionBuilder3 = this;
            IrSimpleType irSimpleType2 = defaultType;
            unaryPlus(ExpressionHelpersKt.irIfThenReturnFalse(memberFunctionBuilder3, ExpressionHelpersKt.irNotIs(memberFunctionBuilder3, irOther(), irSimpleType2)));
            IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(this, ExpressionHelpersKt.irAs(memberFunctionBuilder3, irOther(), irSimpleType2), "other_with_cast", null, false, null, 28, null);
            for (IrProperty irProperty : properties) {
                unaryPlus(ExpressionHelpersKt.irIfThenReturnFalse(memberFunctionBuilder3, ExpressionHelpersKt.irNotEquals(memberFunctionBuilder3, irGetProperty(irThis(), irProperty), irGetProperty(ExpressionHelpersKt.irGet(memberFunctionBuilder3, irSimpleType2, irVariableIrTemporary$default.getSymbol()), irProperty))));
            }
            unaryPlus(ExpressionHelpersKt.irReturnTrue(memberFunctionBuilder3));
        }

        public final void generateHashCodeMethodBody(List<? extends IrProperty> properties, int constHashCode) {
            Intrinsics.checkNotNullParameter(properties, "properties");
            if (properties.isEmpty()) {
                MemberFunctionBuilder memberFunctionBuilder = this;
                unaryPlus(ExpressionHelpersKt.irReturn(memberFunctionBuilder, ExpressionHelpersKt.irInt$default(memberFunctionBuilder, constHashCode, null, 2, null)));
                return;
            }
            if (properties.size() == 1) {
                unaryPlus(ExpressionHelpersKt.irReturn(this, getHashCodeOfProperty(properties.get(0))));
                return;
            }
            IrType intType = getContext().getIrBuiltIns().getIntType();
            int startOffset = getStartOffset();
            int endOffset = getEndOffset();
            IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
            IrVariableSymbolImpl irVariableSymbolImpl = new IrVariableSymbolImpl(null, 1, null);
            Name nameIdentifier = Name.identifier("result");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            IrVariableImpl irVariableImpl = new IrVariableImpl(startOffset, endOffset, defined, irVariableSymbolImpl, nameIdentifier, intType, true, false, false);
            irVariableImpl.setParent(this.irFunction);
            irVariableImpl.setInitializer(getHashCodeOfProperty(properties.get(0)));
            unaryPlus(irVariableImpl);
            for (IrProperty irProperty : CollectionsKt.drop(properties, 1)) {
                MemberFunctionBuilder memberFunctionBuilder2 = this;
                unaryPlus(ExpressionHelpersKt.irSet$default(memberFunctionBuilder2, irVariableImpl.getSymbol(), ExpressionHelpersKt.irCallOp(memberFunctionBuilder2, getContext().getIrBuiltIns().getIntPlusSymbol(), intType, this.this$0.shiftResultOfHashCode(memberFunctionBuilder2, irVariableImpl), (24 & 8) != 0 ? null : getHashCodeOfProperty(irProperty), (24 & 16) != 0 ? null : null), (IrStatementOrigin) null, 4, (Object) null));
            }
            MemberFunctionBuilder memberFunctionBuilder3 = this;
            unaryPlus(ExpressionHelpersKt.irReturn(memberFunctionBuilder3, ExpressionHelpersKt.irGet(memberFunctionBuilder3, irVariableImpl)));
        }

        private final IrExpression getHashCodeOfProperty(IrProperty property) {
            if (org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(this.this$0.getType(property))) {
                MemberFunctionBuilder memberFunctionBuilder = this;
                return ExpressionHelpersKt.irIfNull(memberFunctionBuilder, getContext().getIrBuiltIns().getIntType(), irGetProperty(irThis(), property), ExpressionHelpersKt.irInt$default(memberFunctionBuilder, 0, null, 2, null), this.this$0.getHashCodeOf(memberFunctionBuilder, property, irGetProperty(irThis(), property)));
            }
            return this.this$0.getHashCodeOf(this, property, irGetProperty(irThis(), property));
        }

        public final void generateToStringMethodBody(List<? extends IrProperty> properties) {
            Intrinsics.checkNotNullParameter(properties, "properties");
            if (properties.isEmpty() && this.this$0.getIrClass().getKind() == ClassKind.OBJECT) {
                MemberFunctionBuilder memberFunctionBuilder = this;
                String strAsString = this.this$0.getIrClass().getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                unaryPlus(ExpressionHelpersKt.irReturn(memberFunctionBuilder, ExpressionHelpersKt.irString(memberFunctionBuilder, strAsString)));
                return;
            }
            MemberFunctionBuilder memberFunctionBuilder2 = this;
            IrStringConcatenationImpl irStringConcatenationImplIrConcat = ExpressionHelpersKt.irConcat(memberFunctionBuilder2);
            IrStringConcatenationImpl irStringConcatenationImpl = irStringConcatenationImplIrConcat;
            StringBuilder sb = new StringBuilder();
            DataClassMembersGenerator dataClassMembersGenerator = this.this$0;
            IrExpressionsKt.addArgument(irStringConcatenationImpl, ExpressionHelpersKt.irString(memberFunctionBuilder2, sb.append(dataClassMembersGenerator.classNameForToString(dataClassMembersGenerator.getIrClass())).append('(').toString()));
            boolean z = true;
            for (IrProperty irProperty : properties) {
                if (!z) {
                    IrExpressionsKt.addArgument(irStringConcatenationImpl, ExpressionHelpersKt.irString(memberFunctionBuilder2, ", "));
                }
                IrExpressionsKt.addArgument(irStringConcatenationImpl, ExpressionHelpersKt.irString(memberFunctionBuilder2, irProperty.getName().asString() + '='));
                IrCall irCallIrGetProperty = irGetProperty(irThis(), irProperty);
                if (this.this$0.isArrayOrPrimitiveArray(IrTypesKt.getClassifierOrNull(this.this$0.getType(irProperty)))) {
                    IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(memberFunctionBuilder2, getContext().getIrBuiltIns().getDataClassArrayMemberToStringSymbol(), getContext().getIrBuiltIns().getStringType(), 0, 0, null, 28, null);
                    irCallIrCall$default.putValueArgument(0, irCallIrGetProperty);
                    irCallIrGetProperty = irCallIrCall$default;
                }
                IrExpressionsKt.addArgument(irStringConcatenationImpl, irCallIrGetProperty);
                z = false;
            }
            IrExpressionsKt.addArgument(irStringConcatenationImpl, ExpressionHelpersKt.irString(memberFunctionBuilder2, Constant.AFTER_QUTO));
            unaryPlus(ExpressionHelpersKt.irReturn(memberFunctionBuilder2, irStringConcatenationImplIrConcat));
        }

        public final IrFunction addToClass(Function2<? super MemberFunctionBuilder, ? super IrFunction, Unit> builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            DataClassMembersGenerator dataClassMembersGenerator = this.this$0;
            IrFunction irFunction = getIrFunction();
            ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
            symbolTable.enterScope(irFunction);
            builder.invoke(this, getIrFunction());
            getIrFunction().setBody(getIrBlockBody());
            Unit unit = Unit.INSTANCE;
            symbolTable.leaveScope(irFunction);
            this.this$0.getIrClass().getDeclarations().add(getIrFunction());
            return getIrFunction();
        }
    }

    protected IrExpression shiftResultOfHashCode(IrBuilderWithScope irBuilderWithScope, IrVariable irResultVar) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(irResultVar, "irResultVar");
        return ExpressionHelpersKt.irCallOp(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getIntTimesSymbol(), irBuilderWithScope.getContext().getIrBuiltIns().getIntType(), ExpressionHelpersKt.irGet(irBuilderWithScope, irResultVar), (24 & 8) != 0 ? null : ExpressionHelpersKt.irInt$default(irBuilderWithScope, 31, null, 2, null), (24 & 16) != 0 ? null : null);
    }

    protected IrExpression getHashCodeOf(IrBuilderWithScope builder, IrProperty property, IrExpression irValue) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(irValue, "irValue");
        return getHashCodeOf(builder, getType(property), irValue);
    }

    protected final IrExpression getHashCodeOf(IrBuilderWithScope irBuilderWithScope, IrType type, IrExpression irValue) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(irValue, "irValue");
        HashCodeFunctionInfo hashCodeFunctionInfo = getHashCodeFunctionInfo(type);
        IrSimpleFunctionSymbol symbol = hashCodeFunctionInfo.getSymbol();
        boolean zHasDispatchReceiver = hasDispatchReceiver(symbol);
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBuilderWithScope, symbol, irBuilderWithScope.getContext().getIrBuiltIns().getIntType(), !zHasDispatchReceiver ? 1 : 0, 0, null, 16, null);
        if (zHasDispatchReceiver) {
            irCallIrCall$default.setDispatchReceiver(irValue);
        } else {
            irCallIrCall$default.putValueArgument(0, irValue);
        }
        hashCodeFunctionInfo.commitSubstituted(irCallIrCall$default);
        return irCallIrCall$default;
    }

    public final boolean isArrayOrPrimitiveArray(IrClassifierSymbol irClassifierSymbol) {
        return AdditionalIrUtilsKt.isArrayOrPrimitiveArray(irClassifierSymbol, this.context.getIrBuiltIns());
    }

    public String classNameForToString(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        String strAsString = this.irClass.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }
}
