package org.jetbrains.kotlin.psi2ir.generators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind;
import org.jetbrains.kotlin.ir.expressions.impl.IrSyntheticBodyImpl;
import org.jetbrains.kotlin.ir.util.OverridesKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: EnumClassMembersGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/EnumClassMembersGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGeneratorExtension;", "declarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;)V", "generateEntries", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateSpecialMembers", "generateValueOf", "generateValues", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumClassMembersGenerator extends DeclarationGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EnumClassMembersGenerator(DeclarationGenerator declarationGenerator) {
        super(declarationGenerator);
        Intrinsics.checkNotNullParameter(declarationGenerator, "declarationGenerator");
    }

    public final void generateSpecialMembers(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        generateValues(irClass);
        generateValueOf(irClass);
        if (getContext().getLanguageVersionSettings().supportsFeature(LanguageFeature.EnumEntries)) {
            generateEntries(irClass);
        }
    }

    private final void generateValues(IrClass irClass) {
        MemberScope staticScope = irClass.getDescriptor().getStaticScope();
        Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
        String identifier = StandardNames.ENUM_VALUES.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        Name nameIdentifier = Name.identifier(identifier);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        for (Object obj : staticScope.getContributedFunctions(nameIdentifier, NoLookupLocation.FROM_BACKEND)) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) obj;
            if (callableMemberDescriptor.mo6753getDispatchReceiverParameter() == null && callableMemberDescriptor.getExtensionReceiverParameter() == null && callableMemberDescriptor.getValueParameters().size() == 0) {
                IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides = OverridesKt.declareSimpleFunctionWithOverrides(getContext().getSymbolTable(), -2, -2, IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE, (SimpleFunctionDescriptor) obj);
                new FunctionGenerator(getDeclarationGenerator()).generateFunctionParameterDeclarationsAndReturnType(irSimpleFunctionDeclareSimpleFunctionWithOverrides, null, null, CollectionsKt.emptyList());
                irSimpleFunctionDeclareSimpleFunctionWithOverrides.setBody(new IrSyntheticBodyImpl(-2, -2, IrSyntheticBodyKind.ENUM_VALUES));
                IrDeclarationsKt.addMember(irClass, irSimpleFunctionDeclareSimpleFunctionWithOverrides);
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private final void generateValueOf(IrClass irClass) {
        MemberScope staticScope = irClass.getDescriptor().getStaticScope();
        Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
        String identifier = StandardNames.ENUM_VALUE_OF.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        Name nameIdentifier = Name.identifier(identifier);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        for (Object obj : staticScope.getContributedFunctions(nameIdentifier, NoLookupLocation.FROM_BACKEND)) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) obj;
            if (callableMemberDescriptor.mo6753getDispatchReceiverParameter() == null && callableMemberDescriptor.getExtensionReceiverParameter() == null && callableMemberDescriptor.getValueParameters().size() == 1) {
                IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides = OverridesKt.declareSimpleFunctionWithOverrides(getContext().getSymbolTable(), -2, -2, IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE, (SimpleFunctionDescriptor) obj);
                new FunctionGenerator(getDeclarationGenerator()).generateFunctionParameterDeclarationsAndReturnType(irSimpleFunctionDeclareSimpleFunctionWithOverrides, null, null, CollectionsKt.emptyList());
                irSimpleFunctionDeclareSimpleFunctionWithOverrides.setBody(new IrSyntheticBodyImpl(-2, -2, IrSyntheticBodyKind.ENUM_VALUEOF));
                IrDeclarationsKt.addMember(irClass, irSimpleFunctionDeclareSimpleFunctionWithOverrides);
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private final void generateEntries(IrClass irClass) {
        Object next;
        MemberScope staticScope = irClass.getDescriptor().getStaticScope();
        Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
        String identifier = StandardNames.ENUM_ENTRIES.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        Name nameIdentifier = Name.identifier(identifier);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        Iterator<T> it = staticScope.getContributedVariables(nameIdentifier, NoLookupLocation.FROM_BACKEND).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) next;
            if (callableMemberDescriptor.mo6753getDispatchReceiverParameter() == null && callableMemberDescriptor.getExtensionReceiverParameter() == null) {
                break;
            }
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) next;
        if (propertyDescriptor == null) {
            return;
        }
        IrProperty irPropertyDeclareProperty = getContext().getSymbolTable().getDescriptorExtension().declareProperty(-2, -2, IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE, propertyDescriptor, propertyDescriptor.getIsDelegated());
        SymbolTable symbolTable = getContext().getSymbolTable();
        IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER enum_class_special_member = IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE;
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        Intrinsics.checkNotNull(getter);
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides = OverridesKt.declareSimpleFunctionWithOverrides(symbolTable, -2, -2, enum_class_special_member, getter);
        irSimpleFunctionDeclareSimpleFunctionWithOverrides.setCorrespondingPropertySymbol(irPropertyDeclareProperty.getSymbol());
        KotlinType returnType = propertyDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        irSimpleFunctionDeclareSimpleFunctionWithOverrides.setReturnType(toIrType(returnType));
        irSimpleFunctionDeclareSimpleFunctionWithOverrides.setBody(new IrSyntheticBodyImpl(irPropertyDeclareProperty.getStartOffset(), irPropertyDeclareProperty.getEndOffset(), IrSyntheticBodyKind.ENUM_ENTRIES));
        irPropertyDeclareProperty.setGetter(irSimpleFunctionDeclareSimpleFunctionWithOverrides);
        IrDeclarationsKt.addMember(irClass, irPropertyDeclareProperty);
    }
}
