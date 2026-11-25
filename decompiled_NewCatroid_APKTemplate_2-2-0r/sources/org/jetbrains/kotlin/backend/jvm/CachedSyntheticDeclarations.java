package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.descriptors.DescriptorUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrExpressionBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: CachedSyntheticDeclarations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Ø\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002UVB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001e\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u00172\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J2\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\"\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\"\u0010*\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\u0018\u0010+\u001a\u00020,2\u0006\u0010(\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020.H\u0002J\"\u0010/\u001a\u0002002\u0006\u0010(\u001a\u00020\u00192\u0006\u0010-\u001a\u00020\u000b2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J$\u00101\u001a\u00020\u000f2\u0006\u00102\u001a\u00020\u000f2\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u0006H\u0002J\u000e\u00104\u001a\u00020\u000f2\u0006\u00102\u001a\u00020\u000fJ\u000e\u00105\u001a\u00020\u000f2\u0006\u00102\u001a\u00020\u000fJ\u001c\u00106\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u00172\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dJ\u001c\u00106\u001a\u00020\b2\u0006\u00107\u001a\u0002082\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dJ\u001c\u00109\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020:2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dJ\u001c\u0010;\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020<2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dJ\u000e\u0010=\u001a\u00020\u000e2\u0006\u0010>\u001a\u00020\u000fJ\u000e\u0010?\u001a\u00020\u000e2\u0006\u0010>\u001a\u00020\u000fJ\"\u0010@\u001a\u00020\u000b2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020D2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\"\u0010E\u001a\u00020\u000b2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020D2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J$\u0010F\u001a\u00020G*\u00020)2\b\u0010H\u001a\u0004\u0018\u00010#2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J\u0016\u0010I\u001a\u00020G*\u00020'2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\u0016\u0010J\u001a\u00020G*\u00020'2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\"\u0010K\u001a\u00020L*\u00020M2\u0006\u0010C\u001a\u00020L2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J\u0016\u0010N\u001a\u00020O*\u00020'2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\u0016\u0010P\u001a\u00020\u000f*\u00020\u000f2\b\b\u0002\u0010Q\u001a\u00020RH\u0002J6\u0010S\u001a\u00020)*\u00020)2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010C\u001a\u00020L2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J\f\u0010T\u001a\u00020O*\u00020DH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\r\u001a\u00020\u000e*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0010R\u0018\u0010\u0011\u001a\u00020\u000e*\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013¨\u0006W"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/CachedSyntheticDeclarations;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "functionMap", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/backend/jvm/CachedSyntheticDeclarations$FunctionKey;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getterMap", "Lorg/jetbrains/kotlin/backend/jvm/CachedSyntheticDeclarations$FieldKey;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "setterMap", "isOrShouldBeHiddenDueToOrigin", "", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Z", "isProtected", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)Z", "copyAllParamsToArgs", "", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "syntheticFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "createAccessor", "expression", "scopes", "", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", NativeSymbol.TYPE_NAME, "dispatchReceiverType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "createAccessorBodyForGetter", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "targetField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "createAccessorBodyForSetter", "createConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrDelegatingConstructorCallImpl;", "targetSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "createSimpleFunctionCall", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCallImpl;", "getSyntheticConstructorAccessor", "declaration", "constructorToAccessorMap", "getSyntheticConstructorOfSealedClass", "getSyntheticConstructorWithMangledParams", "getSyntheticFunctionAccessor", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "getSyntheticGetter", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "getSyntheticSetter", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "isOrShouldBeHiddenAsSealedClassConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "isOrShouldBeHiddenSinceHasMangledParams", "makeGetterAccessorSymbol", "fieldSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "makeSetterAccessorSymbol", "accessorName", "Lorg/jetbrains/kotlin/name/Name;", "superQualifier", "accessorNameForGetter", "accessorNameForSetter", "accessorParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "fieldAccessorSuffix", "", "makeConstructorAccessor", "originForConstructorAccessor", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "makeSimpleFunctionAccessor", "syntheticAccessorToSuperSuffix", "FieldKey", "FunctionKey", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CachedSyntheticDeclarations {
    private final JvmBackendContext context;
    private final ConcurrentHashMap<FunctionKey, IrFunctionSymbol> functionMap;
    private final ConcurrentHashMap<FieldKey, IrSimpleFunctionSymbol> getterMap;
    private final ConcurrentHashMap<FieldKey, IrSimpleFunctionSymbol> setterMap;

    /* JADX INFO: compiled from: CachedSyntheticDeclarations.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/CachedSyntheticDeclarations$FieldKey;", "", "fieldSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getFieldSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getSuperQualifierSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class FieldKey {
        private final IrFieldSymbol fieldSymbol;
        private final IrDeclarationParent parent;
        private final IrClassSymbol superQualifierSymbol;

        public static /* synthetic */ FieldKey copy$default(FieldKey fieldKey, IrFieldSymbol irFieldSymbol, IrDeclarationParent irDeclarationParent, IrClassSymbol irClassSymbol, int i, Object obj) {
            if ((i & 1) != 0) {
                irFieldSymbol = fieldKey.fieldSymbol;
            }
            if ((i & 2) != 0) {
                irDeclarationParent = fieldKey.parent;
            }
            if ((i & 4) != 0) {
                irClassSymbol = fieldKey.superQualifierSymbol;
            }
            return fieldKey.copy(irFieldSymbol, irDeclarationParent, irClassSymbol);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrFieldSymbol getFieldSymbol() {
            return this.fieldSymbol;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final IrDeclarationParent getParent() {
            return this.parent;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final IrClassSymbol getSuperQualifierSymbol() {
            return this.superQualifierSymbol;
        }

        public final FieldKey copy(IrFieldSymbol fieldSymbol, IrDeclarationParent parent, IrClassSymbol superQualifierSymbol) {
            Intrinsics.checkNotNullParameter(fieldSymbol, "fieldSymbol");
            Intrinsics.checkNotNullParameter(parent, "parent");
            return new FieldKey(fieldSymbol, parent, superQualifierSymbol);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FieldKey)) {
                return false;
            }
            FieldKey fieldKey = (FieldKey) other;
            return Intrinsics.areEqual(this.fieldSymbol, fieldKey.fieldSymbol) && Intrinsics.areEqual(this.parent, fieldKey.parent) && Intrinsics.areEqual(this.superQualifierSymbol, fieldKey.superQualifierSymbol);
        }

        public int hashCode() {
            int iHashCode = ((this.fieldSymbol.hashCode() * 31) + this.parent.hashCode()) * 31;
            IrClassSymbol irClassSymbol = this.superQualifierSymbol;
            return iHashCode + (irClassSymbol == null ? 0 : irClassSymbol.hashCode());
        }

        public String toString() {
            return "FieldKey(fieldSymbol=" + this.fieldSymbol + ", parent=" + this.parent + ", superQualifierSymbol=" + this.superQualifierSymbol + ')';
        }

        public FieldKey(IrFieldSymbol fieldSymbol, IrDeclarationParent parent, IrClassSymbol irClassSymbol) {
            Intrinsics.checkNotNullParameter(fieldSymbol, "fieldSymbol");
            Intrinsics.checkNotNullParameter(parent, "parent");
            this.fieldSymbol = fieldSymbol;
            this.parent = parent;
            this.superQualifierSymbol = irClassSymbol;
        }

        public final IrFieldSymbol getFieldSymbol() {
            return this.fieldSymbol;
        }

        public final IrDeclarationParent getParent() {
            return this.parent;
        }

        public final IrClassSymbol getSuperQualifierSymbol() {
            return this.superQualifierSymbol;
        }
    }

    public CachedSyntheticDeclarations(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.functionMap = new ConcurrentHashMap<>();
        this.getterMap = new ConcurrentHashMap<>();
        this.setterMap = new ConcurrentHashMap<>();
    }

    /* JADX INFO: compiled from: CachedSyntheticDeclarations.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/CachedSyntheticDeclarations$FunctionKey;", "", "functionSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getFunctionSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getSuperQualifierSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class FunctionKey {
        private final IrFunctionSymbol functionSymbol;
        private final IrDeclarationParent parent;
        private final IrClassSymbol superQualifierSymbol;

        public static /* synthetic */ FunctionKey copy$default(FunctionKey functionKey, IrFunctionSymbol irFunctionSymbol, IrDeclarationParent irDeclarationParent, IrClassSymbol irClassSymbol, int i, Object obj) {
            if ((i & 1) != 0) {
                irFunctionSymbol = functionKey.functionSymbol;
            }
            if ((i & 2) != 0) {
                irDeclarationParent = functionKey.parent;
            }
            if ((i & 4) != 0) {
                irClassSymbol = functionKey.superQualifierSymbol;
            }
            return functionKey.copy(irFunctionSymbol, irDeclarationParent, irClassSymbol);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrFunctionSymbol getFunctionSymbol() {
            return this.functionSymbol;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final IrDeclarationParent getParent() {
            return this.parent;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final IrClassSymbol getSuperQualifierSymbol() {
            return this.superQualifierSymbol;
        }

        public final FunctionKey copy(IrFunctionSymbol functionSymbol, IrDeclarationParent parent, IrClassSymbol superQualifierSymbol) {
            Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
            Intrinsics.checkNotNullParameter(parent, "parent");
            return new FunctionKey(functionSymbol, parent, superQualifierSymbol);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FunctionKey)) {
                return false;
            }
            FunctionKey functionKey = (FunctionKey) other;
            return Intrinsics.areEqual(this.functionSymbol, functionKey.functionSymbol) && Intrinsics.areEqual(this.parent, functionKey.parent) && Intrinsics.areEqual(this.superQualifierSymbol, functionKey.superQualifierSymbol);
        }

        public int hashCode() {
            int iHashCode = ((this.functionSymbol.hashCode() * 31) + this.parent.hashCode()) * 31;
            IrClassSymbol irClassSymbol = this.superQualifierSymbol;
            return iHashCode + (irClassSymbol == null ? 0 : irClassSymbol.hashCode());
        }

        public String toString() {
            return "FunctionKey(functionSymbol=" + this.functionSymbol + ", parent=" + this.parent + ", superQualifierSymbol=" + this.superQualifierSymbol + ')';
        }

        public FunctionKey(IrFunctionSymbol functionSymbol, IrDeclarationParent parent, IrClassSymbol irClassSymbol) {
            Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
            Intrinsics.checkNotNullParameter(parent, "parent");
            this.functionSymbol = functionSymbol;
            this.parent = parent;
            this.superQualifierSymbol = irClassSymbol;
        }

        public final IrFunctionSymbol getFunctionSymbol() {
            return this.functionSymbol;
        }

        public final IrDeclarationParent getParent() {
            return this.parent;
        }

        public final IrClassSymbol getSuperQualifierSymbol() {
            return this.superQualifierSymbol;
        }
    }

    public final IrFunctionSymbol getSyntheticFunctionAccessor(IrFunctionAccessExpression expression, List<? extends ScopeWithIr> scopes) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        return createAccessor(expression, scopes);
    }

    public final IrFunctionSymbol getSyntheticFunctionAccessor(IrFunctionReference reference, List<? extends ScopeWithIr> scopes) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        IrFunctionSymbol symbol = reference.getSymbol();
        IrExpression dispatchReceiver = reference.getDispatchReceiver();
        return createAccessor(symbol, scopes, dispatchReceiver != null ? dispatchReceiver.getType() : null, null);
    }

    private final IrFunctionSymbol createAccessor(IrFunctionAccessExpression expression, List<? extends ScopeWithIr> scopes) {
        if (expression instanceof IrCall) {
            IrCall irCall = (IrCall) expression;
            IrSimpleFunctionSymbol symbol = irCall.getSymbol();
            IrExpression dispatchReceiver = expression.getDispatchReceiver();
            return createAccessor(symbol, scopes, dispatchReceiver != null ? dispatchReceiver.getType() : null, irCall.getSuperQualifierSymbol());
        }
        return createAccessor(expression.getSymbol(), scopes, null, null);
    }

    private final IrFunctionSymbol createAccessor(IrFunctionSymbol symbol, List<? extends ScopeWithIr> scopes, IrType dispatchReceiverType, IrClassSymbol superQualifierSymbol) {
        DeclarationSymbolMarker symbol2;
        IrClassSymbol classOrNull;
        IrClass owner;
        IrDeclarationParent irDeclarationParentAccessorParent = accessorParent(symbol.getOwner(), (dispatchReceiverType == null || (classOrNull = IrTypesKt.getClassOrNull(dispatchReceiverType)) == null || (owner = classOrNull.getOwner()) == null) ? symbol.getOwner().getParent() : owner, scopes);
        ConcurrentHashMap<FunctionKey, IrFunctionSymbol> concurrentHashMap = this.functionMap;
        FunctionKey functionKey = new FunctionKey(symbol, irDeclarationParentAccessorParent, superQualifierSymbol);
        IrFunctionSymbol irFunctionSymbol = concurrentHashMap.get(functionKey);
        if (irFunctionSymbol == null) {
            if (symbol instanceof IrConstructorSymbol) {
                symbol2 = makeConstructorAccessor$default(this, ((IrConstructorSymbol) symbol).getOwner(), null, 1, null).getSymbol();
            } else if (symbol instanceof IrSimpleFunctionSymbol) {
                symbol2 = makeSimpleFunctionAccessor(((IrSimpleFunctionSymbol) symbol).getOwner(), superQualifierSymbol, dispatchReceiverType, irDeclarationParentAccessorParent, scopes).getSymbol();
            } else {
                throw new NoWhenBranchMatchedException();
            }
            IrFunctionSymbol irFunctionSymbol2 = (IrFunctionSymbol) symbol2;
            IrFunctionSymbol irFunctionSymbolPutIfAbsent = concurrentHashMap.putIfAbsent(functionKey, irFunctionSymbol2);
            irFunctionSymbol = irFunctionSymbolPutIfAbsent == null ? irFunctionSymbol2 : irFunctionSymbolPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(irFunctionSymbol, "getOrPut(...)");
        return irFunctionSymbol;
    }

    private final IrConstructor getSyntheticConstructorAccessor(IrConstructor declaration, ConcurrentHashMap<IrConstructor, IrConstructor> constructorToAccessorMap) {
        ConcurrentHashMap<IrConstructor, IrConstructor> concurrentHashMap = constructorToAccessorMap;
        IrConstructor irConstructorMakeConstructorAccessor = concurrentHashMap.get(declaration);
        if (irConstructorMakeConstructorAccessor == null) {
            irConstructorMakeConstructorAccessor = makeConstructorAccessor(declaration, JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR.INSTANCE);
            if (AdditionalIrUtilsKt.getConstructedClass(declaration).getModality() != Modality.SEALED) {
                if (declaration.getMetadata() != null) {
                    irConstructorMakeConstructorAccessor.setMetadata(declaration.getMetadata());
                    declaration.setMetadata(null);
                }
                irConstructorMakeConstructorAccessor.setAnnotations(CollectionsKt.plus((Collection) irConstructorMakeConstructorAccessor.getAnnotations(), (Iterable) declaration.getAnnotations()));
                declaration.setAnnotations(CollectionsKt.emptyList());
                Iterator<T> it = declaration.getValueParameters().iterator();
                while (it.hasNext()) {
                    ((IrValueParameter) it.next()).setAnnotations(CollectionsKt.emptyList());
                }
            }
            IrConstructor irConstructorPutIfAbsent = concurrentHashMap.putIfAbsent(declaration, irConstructorMakeConstructorAccessor);
            if (irConstructorPutIfAbsent != null) {
                irConstructorMakeConstructorAccessor = irConstructorPutIfAbsent;
            }
        }
        Intrinsics.checkNotNullExpressionValue(irConstructorMakeConstructorAccessor, "getOrPut(...)");
        return irConstructorMakeConstructorAccessor;
    }

    static /* synthetic */ IrConstructor makeConstructorAccessor$default(CachedSyntheticDeclarations cachedSyntheticDeclarations, IrConstructor irConstructor, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            irDeclarationOrigin = JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE;
        }
        return cachedSyntheticDeclarations.makeConstructorAccessor(irConstructor, irDeclarationOrigin);
    }

    private final IrConstructor makeConstructorAccessor(IrConstructor irConstructor, IrDeclarationOrigin irDeclarationOrigin) {
        IrFactory factory = irConstructor.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setOrigin(irDeclarationOrigin);
        irFunctionBuilder.setName(irConstructor.getName());
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        irFunctionBuilder.setVisibility(PUBLIC);
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        irConstructorBuildConstructor.setParent(irConstructor.getParent());
        IrConstructor irConstructor2 = irConstructorBuildConstructor;
        IrConstructor irConstructor3 = irConstructor;
        IrUtilsKt.copyTypeParametersFrom$default(irConstructor2, irConstructor3, JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE, null, 4, null);
        IrConstructor irConstructor4 = irConstructorBuildConstructor;
        IrUtilsKt.copyValueParametersToStatic$default(irConstructor4, irConstructor, JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE, null, 0, 12, null);
        if (AdditionalIrUtilsKt.getConstructedClass(irConstructor).getModality() == Modality.SEALED) {
            Iterator<IrValueParameter> it = irConstructorBuildConstructor.getValueParameters().iterator();
            while (it.hasNext()) {
                it.next().setAnnotations(CollectionsKt.emptyList());
            }
        }
        irConstructorBuildConstructor.setReturnType(IrUtilsKt.remapTypeParameters$default(irConstructor.getReturnType(), irConstructor3, irConstructor2, null, 4, null));
        DeclarationBuildersKt.addValueParameter(irConstructor4, DescriptorUtilsKt.getSynthesizedString("constructor_marker"), IrTypesKt.makeNullable(IrTypesKt.getDefaultType(this.context.getIr().getSymbols().getDefaultConstructorMarker())), JvmLoweredDeclarationOrigin.SYNTHETIC_MARKER_PARAMETER.INSTANCE);
        irConstructorBuildConstructor.setBody(new IrExpressionBodyImpl(-1, -1, createConstructorCall(irConstructorBuildConstructor, irConstructor.getSymbol())));
        return irConstructorBuildConstructor;
    }

    private final IrDelegatingConstructorCallImpl createConstructorCall(IrConstructor accessor, IrConstructorSymbol targetSymbol) {
        IrDelegatingConstructorCallImpl irDelegatingConstructorCallImplFromSymbolOwner$default = IrDelegatingConstructorCallImpl.Companion.fromSymbolOwner$default(IrDelegatingConstructorCallImpl.INSTANCE, -1, -1, this.context.getIrBuiltIns().getUnitType(), targetSymbol, IrUtilsKt.getParentAsClass(targetSymbol.getOwner()).getTypeParameters().size() + targetSymbol.getOwner().getTypeParameters().size(), 0, 32, null);
        copyAllParamsToArgs(irDelegatingConstructorCallImplFromSymbolOwner$default, accessor);
        return irDelegatingConstructorCallImplFromSymbolOwner$default;
    }

    private final IrSimpleFunction makeSimpleFunctionAccessor(IrSimpleFunction irSimpleFunction, IrClassSymbol irClassSymbol, IrType irType, IrDeclarationParent irDeclarationParent, List<? extends ScopeWithIr> list) {
        IrFactory factory = irSimpleFunction.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(irDeclarationParent.getStartOffset());
        irFunctionBuilder.setEndOffset(irDeclarationParent.getStartOffset());
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE);
        irFunctionBuilder.setName(accessorName(irSimpleFunction, irClassSymbol, list));
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        irFunctionBuilder.setVisibility(PUBLIC);
        irFunctionBuilder.setModality(((irDeclarationParent instanceof IrClass) && JvmIrUtilsKt.isJvmInterface((IrClass) irDeclarationParent)) ? Modality.OPEN : Modality.FINAL);
        irFunctionBuilder.setSuspend(irSimpleFunction.getIsSuspend());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irDeclarationParent);
        IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, irSimpleFunction);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
        IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunction2, irSimpleFunction3, JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE, null, 4, null);
        IrSimpleFunction irSimpleFunction4 = irSimpleFunctionBuildFunction;
        IrUtilsKt.copyValueParametersToStatic$default(irSimpleFunction4, irSimpleFunction, JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE, irType, 0, 8, null);
        irSimpleFunctionBuildFunction.setReturnType(IrUtilsKt.remapTypeParameters$default(irSimpleFunction.getReturnType(), irSimpleFunction3, irSimpleFunction2, null, 4, null));
        irSimpleFunctionBuildFunction.setBody(new IrExpressionBodyImpl(irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getStartOffset(), createSimpleFunctionCall(irSimpleFunction4, irSimpleFunction.getSymbol(), irClassSymbol)));
        return irSimpleFunctionBuildFunction;
    }

    private final IrCallImpl createSimpleFunctionCall(IrFunction accessor, IrSimpleFunctionSymbol targetSymbol, IrClassSymbol superQualifierSymbol) {
        IrCallImpl irCallImplFromSymbolOwner = IrCallImpl.INSTANCE.fromSymbolOwner(accessor.getStartOffset(), accessor.getEndOffset(), accessor.getReturnType(), targetSymbol, (240 & 16) != 0 ? targetSymbol.getOwner().getTypeParameters().size() : targetSymbol.getOwner().getTypeParameters().size(), (240 & 32) != 0 ? targetSymbol.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : superQualifierSymbol);
        copyAllParamsToArgs(irCallImplFromSymbolOwner, accessor);
        return irCallImplFromSymbolOwner;
    }

    public final IrSimpleFunctionSymbol getSyntheticGetter(IrGetField expression, List<? extends ScopeWithIr> scopes) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        IrExpression receiver = expression.getReceiver();
        IrType type = receiver != null ? receiver.getType() : null;
        IrClassifierSymbol classifierOrNull = type != null ? IrTypesKt.getClassifierOrNull(type) : null;
        IrClassSymbol irClassSymbol = classifierOrNull instanceof IrClassSymbol ? (IrClassSymbol) classifierOrNull : null;
        IrFieldSymbol symbol = expression.getSymbol();
        IrDeclarationParent irDeclarationParentAccessorParent = accessorParent(symbol.getOwner(), (irClassSymbol == null || (owner = irClassSymbol.getOwner()) == null) ? symbol.getOwner().getParent() : owner, scopes);
        Intrinsics.checkNotNull(irDeclarationParentAccessorParent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrClass irClass = (IrClass) irDeclarationParentAccessorParent;
        ConcurrentHashMap<FieldKey, IrSimpleFunctionSymbol> concurrentHashMap = this.getterMap;
        FieldKey fieldKey = new FieldKey(symbol, irClass, expression.getSuperQualifierSymbol());
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = concurrentHashMap.get(fieldKey);
        if (irSimpleFunctionSymbol == null) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbolMakeGetterAccessorSymbol = makeGetterAccessorSymbol(symbol, irClass, expression.getSuperQualifierSymbol());
            IrSimpleFunctionSymbol irSimpleFunctionSymbolPutIfAbsent = concurrentHashMap.putIfAbsent(fieldKey, irSimpleFunctionSymbolMakeGetterAccessorSymbol);
            irSimpleFunctionSymbol = irSimpleFunctionSymbolPutIfAbsent == null ? irSimpleFunctionSymbolMakeGetterAccessorSymbol : irSimpleFunctionSymbolPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(irSimpleFunctionSymbol, "getOrPut(...)");
        return irSimpleFunctionSymbol;
    }

    private final IrSimpleFunctionSymbol makeGetterAccessorSymbol(IrFieldSymbol fieldSymbol, IrClass parent, IrClassSymbol superQualifierSymbol) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(parent.getStartOffset());
        irFunctionBuilder.setEndOffset(parent.getStartOffset());
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE);
        irFunctionBuilder.setName(accessorNameForGetter(fieldSymbol.getOwner(), superQualifierSymbol));
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        irFunctionBuilder.setVisibility(PUBLIC);
        irFunctionBuilder.setModality(Modality.FINAL);
        irFunctionBuilder.setReturnType(fieldSymbol.getOwner().getType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(parent);
        if (!fieldSymbol.getOwner().getIsStatic()) {
            DeclarationBuildersKt.addValueParameter(irSimpleFunctionBuildFunction, "$this", IrUtilsKt.getDefaultType(parent), JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE);
        }
        irSimpleFunctionBuildFunction.setBody(createAccessorBodyForGetter(fieldSymbol.getOwner(), irSimpleFunctionBuildFunction, superQualifierSymbol));
        return irSimpleFunctionBuildFunction.getSymbol();
    }

    private final IrBody createAccessorBodyForGetter(IrField targetField, IrSimpleFunction accessor, IrClassSymbol superQualifierSymbol) {
        return new IrExpressionBodyImpl(accessor.getStartOffset(), accessor.getEndOffset(), new IrGetFieldImpl(accessor.getStartOffset(), accessor.getEndOffset(), targetField.getSymbol(), targetField.getType(), targetField.getIsStatic() ? null : new IrGetValueImpl(accessor.getStartOffset(), accessor.getEndOffset(), accessor.getValueParameters().get(0).getSymbol(), null, 8, null), null, superQualifierSymbol, 32, null));
    }

    public final IrSimpleFunctionSymbol getSyntheticSetter(IrSetField expression, List<? extends ScopeWithIr> scopes) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        IrExpression receiver = expression.getReceiver();
        IrType type = receiver != null ? receiver.getType() : null;
        IrClassifierSymbol classifierOrNull = type != null ? IrTypesKt.getClassifierOrNull(type) : null;
        IrClassSymbol irClassSymbol = classifierOrNull instanceof IrClassSymbol ? (IrClassSymbol) classifierOrNull : null;
        IrFieldSymbol symbol = expression.getSymbol();
        IrDeclarationParent irDeclarationParentAccessorParent = accessorParent(symbol.getOwner(), (irClassSymbol == null || (owner = irClassSymbol.getOwner()) == null) ? symbol.getOwner().getParent() : owner, scopes);
        Intrinsics.checkNotNull(irDeclarationParentAccessorParent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrClass irClass = (IrClass) irDeclarationParentAccessorParent;
        ConcurrentHashMap<FieldKey, IrSimpleFunctionSymbol> concurrentHashMap = this.setterMap;
        FieldKey fieldKey = new FieldKey(symbol, irClass, expression.getSuperQualifierSymbol());
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = concurrentHashMap.get(fieldKey);
        if (irSimpleFunctionSymbol == null) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbolMakeSetterAccessorSymbol = makeSetterAccessorSymbol(symbol, irClass, expression.getSuperQualifierSymbol());
            IrSimpleFunctionSymbol irSimpleFunctionSymbolPutIfAbsent = concurrentHashMap.putIfAbsent(fieldKey, irSimpleFunctionSymbolMakeSetterAccessorSymbol);
            irSimpleFunctionSymbol = irSimpleFunctionSymbolPutIfAbsent == null ? irSimpleFunctionSymbolMakeSetterAccessorSymbol : irSimpleFunctionSymbolPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(irSimpleFunctionSymbol, "getOrPut(...)");
        return irSimpleFunctionSymbol;
    }

    private final IrSimpleFunctionSymbol makeSetterAccessorSymbol(IrFieldSymbol fieldSymbol, IrClass parent, IrClassSymbol superQualifierSymbol) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(parent.getStartOffset());
        irFunctionBuilder.setEndOffset(parent.getStartOffset());
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE);
        irFunctionBuilder.setName(accessorNameForSetter(fieldSymbol.getOwner(), superQualifierSymbol));
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        irFunctionBuilder.setVisibility(PUBLIC);
        irFunctionBuilder.setModality(Modality.FINAL);
        irFunctionBuilder.setReturnType(this.context.getIrBuiltIns().getUnitType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(parent);
        if (!fieldSymbol.getOwner().getIsStatic()) {
            DeclarationBuildersKt.addValueParameter(irSimpleFunctionBuildFunction, "$this", IrUtilsKt.getDefaultType(parent), JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE);
        }
        DeclarationBuildersKt.addValueParameter(irSimpleFunctionBuildFunction, "<set-?>", fieldSymbol.getOwner().getType(), JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE);
        irSimpleFunctionBuildFunction.setBody(createAccessorBodyForSetter(fieldSymbol.getOwner(), irSimpleFunctionBuildFunction, superQualifierSymbol));
        return irSimpleFunctionBuildFunction.getSymbol();
    }

    private final IrBody createAccessorBodyForSetter(IrField targetField, IrSimpleFunction accessor, IrClassSymbol superQualifierSymbol) {
        return new IrExpressionBodyImpl(accessor.getStartOffset(), accessor.getEndOffset(), new IrSetFieldImpl(accessor.getStartOffset(), accessor.getEndOffset(), targetField.getSymbol(), targetField.getIsStatic() ? null : new IrGetValueImpl(accessor.getStartOffset(), accessor.getEndOffset(), accessor.getValueParameters().get(0).getSymbol(), null, 8, null), new IrGetValueImpl(accessor.getStartOffset(), accessor.getEndOffset(), accessor.getValueParameters().get(!targetField.getIsStatic() ? 1 : 0).getSymbol(), null, 8, null), this.context.getIrBuiltIns().getUnitType(), null, superQualifierSymbol, 64, null));
    }

    private final void copyAllParamsToArgs(IrFunctionAccessExpression call, IrFunction syntheticFunction) {
        int size;
        int i;
        int i2 = 0;
        if (syntheticFunction instanceof IrConstructor) {
            IrFunction irFunction = syntheticFunction;
            IrUtilsKt.passTypeArgumentsFrom$default(call, IrUtilsKt.getParentAsClass(irFunction), 0, 2, null);
            size = IrUtilsKt.getParentAsClass(irFunction).getTypeParameters().size();
        } else {
            size = 0;
        }
        IrUtilsKt.passTypeArgumentsFrom(call, syntheticFunction, size);
        IrFunction owner = call.getSymbol().getOwner();
        if (owner.getDispatchReceiverParameter() != null) {
            call.setDispatchReceiver(new IrGetValueImpl(-1, -1, syntheticFunction.getValueParameters().get(0).getSymbol(), null, 8, null));
            i = 1;
        } else {
            i = 0;
        }
        if (owner.getExtensionReceiverParameter() != null) {
            call.setExtensionReceiver(new IrGetValueImpl(-1, -1, syntheticFunction.getValueParameters().get(i).getSymbol(), null, 8, null));
            i++;
        }
        for (Object obj : owner.getValueParameters()) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            call.putValueArgument(i2, new IrGetValueImpl(-1, -1, syntheticFunction.getValueParameters().get(i2 + i).getSymbol(), null, 8, null));
            i2 = i3;
        }
    }

    private final IrDeclarationParent accessorParent(IrDeclarationWithVisibility irDeclarationWithVisibility, IrDeclarationParent irDeclarationParent, List<? extends ScopeWithIr> list) {
        Object objPrevious;
        if (!Intrinsics.areEqual(irDeclarationWithVisibility.getVisibility(), JavaDescriptorVisibilities.PROTECTED_STATIC_VISIBILITY)) {
            return irDeclarationParent;
        }
        List<? extends ScopeWithIr> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((ScopeWithIr) it.next()).getIrElement());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (obj instanceof IrClass) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = arrayList3;
        ArrayList arrayList5 = new ArrayList();
        Iterator it2 = arrayList4.iterator();
        while (it2.hasNext()) {
            IrClass irClassCompanionObject = AdditionalIrUtilsKt.companionObject((IrClass) it2.next());
            if (irClassCompanionObject != null) {
                arrayList5.add(irClassCompanionObject);
            }
        }
        ArrayList arrayList6 = arrayList5;
        ArrayList arrayList7 = new ArrayList();
        Iterator it3 = arrayList4.iterator();
        while (it3.hasNext()) {
            List<IrDeclaration> declarations = ((IrClass) it3.next()).getDeclarations();
            ArrayList arrayList8 = new ArrayList();
            for (Object obj2 : declarations) {
                if (AdditionalIrUtilsKt.isAnonymousObject((IrDeclaration) obj2)) {
                    arrayList8.add(obj2);
                }
            }
            ArrayList arrayList9 = new ArrayList();
            for (Object obj3 : arrayList8) {
                if (obj3 instanceof IrClass) {
                    arrayList9.add(obj3);
                }
            }
            CollectionsKt.addAll(arrayList7, arrayList9);
        }
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) arrayList7, (Iterable) arrayList6), (Iterable) arrayList4);
        ListIterator listIterator = listPlus.listIterator(listPlus.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
            IrClass irClass = (IrClass) objPrevious;
            if ((irDeclarationParent instanceof IrClass) && IrUtilsKt.isSubclassOf(irClass, (IrClass) irDeclarationParent)) {
                break;
            }
        }
        IrClass irClass2 = (IrClass) objPrevious;
        if (irClass2 == null) {
            irClass2 = (IrClass) CollectionsKt.last((List) arrayList3);
        }
        return irClass2;
    }

    private final Name accessorName(IrSimpleFunction irSimpleFunction, IrClassSymbol irClassSymbol, List<? extends ScopeWithIr> list) {
        IrElement irElement;
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        ScopeWithIr scopeWithIr = null;
        String strMapFunctionName$default = MethodSignatureMapper.mapFunctionName$default(this.context.getDefaultMethodSignatureMapper(), irSimpleFunction2, false, 2, null);
        ListIterator<? extends ScopeWithIr> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            ScopeWithIr scopeWithIrPrevious = listIterator.previous();
            if (scopeWithIrPrevious.getScope().getScopeOwnerSymbol() instanceof IrClassSymbol) {
                scopeWithIr = scopeWithIrPrevious;
                break;
            }
        }
        ScopeWithIr scopeWithIr2 = scopeWithIr;
        IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
        String str = "";
        if (!IrUtilsKt.isTopLevel(irSimpleFunction3)) {
            if (scopeWithIr2 != null && (irElement = scopeWithIr2.getIrElement()) != null && (irElement instanceof IrClass) && Intrinsics.areEqual(((IrClass) irElement).getOrigin(), JvmLoweredDeclarationOrigin.DEFAULT_IMPLS.INSTANCE) && Intrinsics.areEqual(IrUtilsKt.getParentAsClass((IrDeclaration) irElement), IrUtilsKt.getParentAsClass(irSimpleFunction3))) {
                if (!DescriptorVisibilities.isPrivate(irSimpleFunction.getVisibility())) {
                    str = "$jd";
                }
            } else if (irClassSymbol != null) {
                str = "$s" + syntheticAccessorToSuperSuffix(irClassSymbol.getOwner());
            } else if (IrUtilsKt.isStatic(irSimpleFunction2) && isProtected(irSimpleFunction.getVisibility())) {
                str = "$s" + syntheticAccessorToSuperSuffix(IrUtilsKt.getParentAsClass(irSimpleFunction3));
            }
        }
        Name nameIdentifier = Name.identifier("access$" + strMapFunctionName$default + str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    private final Name accessorNameForGetter(IrField irField, IrClassSymbol irClassSymbol) {
        String strAsString = irField.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Name nameIdentifier = Name.identifier("access$" + JvmAbi.getterName(strAsString) + '$' + fieldAccessorSuffix(irField, irClassSymbol));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    private final Name accessorNameForSetter(IrField irField, IrClassSymbol irClassSymbol) {
        String strAsString = irField.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Name nameIdentifier = Name.identifier("access$" + JvmAbi.setterName(strAsString) + '$' + fieldAccessorSuffix(irField, irClassSymbol));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    private final String fieldAccessorSuffix(IrField irField, IrClassSymbol irClassSymbol) {
        if (Intrinsics.areEqual(irField.getOrigin(), JvmLoweredDeclarationOrigin.COMPANION_PROPERTY_BACKING_FIELD.INSTANCE) && !IrUtilsKt.getParentAsClass(irField).getIsCompanion()) {
            return "cp";
        }
        if (irClassSymbol != null) {
            return "p$s" + syntheticAccessorToSuperSuffix(irClassSymbol.getOwner());
        }
        return "p" + ((irField.getIsStatic() && isProtected(irField.getVisibility())) ? "$s" + syntheticAccessorToSuperSuffix(IrUtilsKt.getParentAsClass(irField)) : "");
    }

    private final String syntheticAccessorToSuperSuffix(IrClass irClass) {
        return String.valueOf(irClass.getName().asString().hashCode());
    }

    private final boolean isProtected(DescriptorVisibility descriptorVisibility) {
        Integer visibilityAccessFlag = AsmUtil.getVisibilityAccessFlag(descriptorVisibility.getDelegate());
        return visibilityAccessFlag != null && visibilityAccessFlag.intValue() == 4;
    }

    public final boolean isOrShouldBeHiddenSinceHasMangledParams(IrConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        if (((ConcurrentHashMap.KeySetView) this.context.getHiddenConstructorsWithMangledParams().keySet()).contains(constructor)) {
            return true;
        }
        if (isOrShouldBeHiddenDueToOrigin(constructor) && !DescriptorVisibilities.isPrivate(constructor.getVisibility()) && !AdditionalIrUtilsKt.getConstructedClass(constructor).getIsValue()) {
            IrConstructor irConstructor = this.context.getMultiFieldValueClassReplacements().getOriginalConstructorForConstructorReplacement().get(constructor);
            if (irConstructor == null) {
                irConstructor = constructor;
            }
            if (InlineClassAbiKt.hasMangledParameters$default(irConstructor, false, false, 3, null) && !IrUtilsKt.isAnonymousObject(AdditionalIrUtilsKt.getConstructedClass(constructor))) {
                return true;
            }
        }
        return false;
    }

    public final boolean isOrShouldBeHiddenAsSealedClassConstructor(IrConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        if (((ConcurrentHashMap.KeySetView) this.context.getHiddenConstructorsOfSealedClasses().keySet()).contains(constructor)) {
            return true;
        }
        return isOrShouldBeHiddenDueToOrigin(constructor) && !Intrinsics.areEqual(constructor.getVisibility(), DescriptorVisibilities.PUBLIC) && AdditionalIrUtilsKt.getConstructedClass(constructor).getModality() == Modality.SEALED;
    }

    private final boolean isOrShouldBeHiddenDueToOrigin(IrConstructor irConstructor) {
        return (Intrinsics.areEqual(irConstructor.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE) || Intrinsics.areEqual(irConstructor.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE) || Intrinsics.areEqual(irConstructor.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR.INSTANCE) || Intrinsics.areEqual(irConstructor.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE)) ? false : true;
    }

    public final IrConstructor getSyntheticConstructorWithMangledParams(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getSyntheticConstructorAccessor(declaration, this.context.getHiddenConstructorsWithMangledParams());
    }

    public final IrConstructor getSyntheticConstructorOfSealedClass(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getSyntheticConstructorAccessor(declaration, this.context.getHiddenConstructorsOfSealedClasses());
    }
}
