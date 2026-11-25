package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.DescriptorToIrUtilKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtScriptInitializer;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilderKt;
import org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue;
import org.jetbrains.kotlin.psi2ir.intermediate.RematerializableValueKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.UnderscoreUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: ScriptGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/ScriptGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGeneratorExtension;", "declarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;)V", "generateScriptDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ktScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptGenerator extends DeclarationGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptGenerator(DeclarationGenerator declarationGenerator) {
        super(declarationGenerator);
        Intrinsics.checkNotNullParameter(declarationGenerator, "declarationGenerator");
    }

    /* JADX WARN: Code duplicated, block: B:74:0x05ff  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v6, types: [int] */
    /* JADX WARN: Type inference failed for: r7v7 */
    public final IrDeclaration generateScriptDeclaration(KtScript ktScript) {
        ArrayList arrayList;
        String str;
        Iterator<KtDeclaration> it;
        IrValueParameter irValueParameterGenerateScriptDeclaration$lambda$17$createValueParameter;
        ScriptGenerator scriptGenerator = this;
        Intrinsics.checkNotNullParameter(ktScript, "ktScript");
        ScriptGenerator scriptGenerator2 = scriptGenerator;
        ReadOnlySlice<PsiElement, DeclarationDescriptor> DECLARATION_TO_DESCRIPTOR = BindingContext.DECLARATION_TO_DESCRIPTOR;
        Intrinsics.checkNotNullExpressionValue(DECLARATION_TO_DESCRIPTOR, "DECLARATION_TO_DESCRIPTOR");
        Object orFail = GeneratorKt.getOrFail(scriptGenerator2, DECLARATION_TO_DESCRIPTOR, ktScript);
        Intrinsics.checkNotNull(orFail, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ScriptDescriptor");
        final ScriptDescriptor scriptDescriptor = (ScriptDescriptor) orFail;
        KtScript ktScript2 = ktScript;
        IrScript irScriptDeclareScript = getContext().getSymbolTable().getDescriptorExtension().declareScript(PsiUtilsKt.getStartOffsetSkippingComments(ktScript2), PsiUtilsKt.getEndOffset(ktScript2), scriptDescriptor);
        SymbolTable symbolTable = scriptGenerator.getContext().getSymbolTable();
        symbolTable.enterScope(irScriptDeclareScript);
        final IrScript irScript = irScriptDeclareScript;
        irScript.setMetadata(new DescriptorMetadataSource.Script(scriptDescriptor));
        List<ClassDescriptor> implicitReceivers = scriptDescriptor.getImplicitReceivers();
        Intrinsics.checkNotNullExpressionValue(implicitReceivers, "getImplicitReceivers(...)");
        HashSet hashSet = new HashSet();
        for (Object obj : implicitReceivers) {
            if (obj instanceof ScriptDescriptor) {
                hashSet.add(obj);
            }
        }
        HashSet hashSet2 = hashSet;
        ReceiverParameterDescriptor thisAsReceiverParameter = scriptDescriptor.getThisAsReceiverParameter();
        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
        irScript.setThisReceiver(generateScriptDeclaration$lambda$17$makeParameter$default(this, irScript, thisAsReceiverParameter, IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE, 0, 16, null));
        Collection<KotlinType> collectionMo7438getSupertypes = scriptDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        Object objSingle = CollectionsKt.single(collectionMo7438getSupertypes);
        Intrinsics.checkNotNullExpressionValue(objSingle, "single(...)");
        irScript.setBaseClass(scriptGenerator.toIrType((KotlinType) objSingle));
        final Ref.IntRef intRef = new Ref.IntRef();
        List<IrScriptSymbol> previousScripts = getContext().getExtensions().getPreviousScripts();
        if (previousScripts != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : previousScripts) {
                IrScriptSymbol irScriptSymbol = (IrScriptSymbol) obj2;
                if (!Intrinsics.areEqual(irScriptSymbol.getOwner(), irScript) && !hashSet2.contains(irScriptSymbol.getDescriptor())) {
                    arrayList2.add(obj2);
                }
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        irScript.setEarlierScripts(arrayList);
        List<IrScriptSymbol> earlierScripts = irScript.getEarlierScripts();
        if (earlierScripts != null) {
            for (IrScriptSymbol irScriptSymbol2 : earlierScripts) {
                DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
                IrValueParameter thisReceiver = irScriptSymbol2.getOwner().getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver);
                descriptorExtension.introduceValueParameter(thisReceiver);
            }
            Unit unit = Unit.INSTANCE;
        }
        if (getContext().getExtensions().getLowerScriptToClass()) {
            ValueParameterDescriptor earlierScriptsConstructorParameter = scriptDescriptor.getEarlierScriptsConstructorParameter();
            if (earlierScriptsConstructorParameter != null) {
                Intrinsics.checkNotNull(earlierScriptsConstructorParameter);
                irValueParameterGenerateScriptDeclaration$lambda$17$createValueParameter = generateScriptDeclaration$lambda$17$createValueParameter(scriptGenerator, intRef, irScript, earlierScriptsConstructorParameter);
            } else {
                irValueParameterGenerateScriptDeclaration$lambda$17$createValueParameter = null;
            }
            irScript.setEarlierScriptsParameter(irValueParameterGenerateScriptDeclaration$lambda$17$createValueParameter);
        }
        List<ValueParameterDescriptor> explicitConstructorParameters = scriptDescriptor.getExplicitConstructorParameters();
        Intrinsics.checkNotNullExpressionValue(explicitConstructorParameters, "getExplicitConstructorParameters(...)");
        List<ValueParameterDescriptor> list = explicitConstructorParameters;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList3.add(generateScriptDeclaration$lambda$17$createValueParameter(scriptGenerator, intRef, irScript, (ValueParameterDescriptor) it2.next()));
        }
        ArrayList arrayList4 = arrayList3;
        List<ValueParameterDescriptor> explicitConstructorParameters2 = scriptDescriptor.getExplicitConstructorParameters();
        Intrinsics.checkNotNullExpressionValue(explicitConstructorParameters2, "getExplicitConstructorParameters(...)");
        List<ValueParameterDescriptor> list2 = explicitConstructorParameters2;
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator it3 = list2.iterator();
        while (true) {
            str = "getType(...)";
            if (!it3.hasNext()) {
                break;
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) it3.next();
            IrDeclarationOrigin.SCRIPT_CALL_PARAMETER script_call_parameter = IrDeclarationOrigin.SCRIPT_CALL_PARAMETER.INSTANCE;
            Iterator it4 = it3;
            IrVariableSymbolImpl irVariableSymbolImpl = new IrVariableSymbolImpl(null, 1, null);
            Name name2 = valueParameterDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            KotlinType type = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            IrVariableImpl irVariableImpl = new IrVariableImpl(-1, -1, script_call_parameter, irVariableSymbolImpl, name2, scriptGenerator.toIrType(type), false, false, false);
            irVariableImpl.setParent(irScript);
            arrayList5.add(irVariableImpl);
            it3 = it4;
            symbolTable = symbolTable;
        }
        SymbolTable symbolTable2 = symbolTable;
        boolean z = true;
        irScript.setExplicitCallParameters(arrayList5);
        List<ClassDescriptor> implicitReceivers2 = scriptDescriptor.getImplicitReceivers();
        Intrinsics.checkNotNullExpressionValue(implicitReceivers2, "getImplicitReceivers(...)");
        List<ClassDescriptor> list3 = implicitReceivers2;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator it5 = list3.iterator();
        while (it5.hasNext()) {
            ReceiverParameterDescriptor thisAsReceiverParameter2 = ((ClassDescriptor) it5.next()).getThisAsReceiverParameter();
            Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter2, "getThisAsReceiverParameter(...)");
            IrDeclarationOrigin.SCRIPT_IMPLICIT_RECEIVER script_implicit_receiver = IrDeclarationOrigin.SCRIPT_IMPLICIT_RECEIVER.INSTANCE;
            int i = intRef.element;
            intRef.element = i + 1;
            arrayList6.add(generateScriptDeclaration$lambda$17$makeParameter(scriptGenerator, irScript, thisAsReceiverParameter2, script_implicit_receiver, i));
        }
        irScript.setImplicitReceiversParameters(arrayList6);
        List<PropertyDescriptor> scriptProvidedProperties = scriptDescriptor.getScriptProvidedProperties();
        Intrinsics.checkNotNullExpressionValue(scriptProvidedProperties, "getScriptProvidedProperties(...)");
        List<PropertyDescriptor> list4 = scriptProvidedProperties;
        List<ValueParameterDescriptor> scriptProvidedPropertiesParameters = scriptDescriptor.getScriptProvidedPropertiesParameters();
        Intrinsics.checkNotNullExpressionValue(scriptProvidedPropertiesParameters, "getScriptProvidedPropertiesParameters(...)");
        List<ValueParameterDescriptor> list5 = scriptProvidedPropertiesParameters;
        Iterator it6 = list4.iterator();
        Iterator it7 = list5.iterator();
        ArrayList arrayList7 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list4, 10), CollectionsKt.collectionSizeOrDefault(list5, 10)));
        while (it6.hasNext() && it7.hasNext()) {
            Object next = it6.next();
            ValueParameterDescriptor valueParameterDescriptor2 = (ValueParameterDescriptor) it7.next();
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) next;
            KotlinType type2 = propertyDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type2, str);
            final IrType irType = scriptGenerator.toIrType(type2);
            DescriptorSymbolTableExtension descriptorExtension2 = getContext().getSymbolTable().getDescriptorExtension();
            IrDeclarationOrigin.SCRIPT_PROVIDED_PROPERTY script_provided_property = IrDeclarationOrigin.SCRIPT_PROVIDED_PROPERTY.INSTANCE;
            Intrinsics.checkNotNull(valueParameterDescriptor2);
            boolean z2 = z;
            ArrayList arrayList8 = arrayList7;
            IrValueParameter irValueParameterDeclareValueParameter$default = DescriptorSymbolTableExtension.declareValueParameter$default(descriptorExtension2, -1, -1, script_provided_property, valueParameterDescriptor2, irType, null, null, null, false, new Function1<IrValueParameterSymbol, IrValueParameter>() { // from class: org.jetbrains.kotlin.psi2ir.generators.ScriptGenerator$generateScriptDeclaration$1$6$valueParameter$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrValueParameter invoke(IrValueParameterSymbol symbol) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    IrFactory irFactory = this.this$0.getContext().getIrFactory();
                    IrDeclarationOrigin.SCRIPT_PROVIDED_PROPERTY script_provided_property2 = IrDeclarationOrigin.SCRIPT_PROVIDED_PROPERTY.INSTANCE;
                    Name name3 = scriptDescriptor.getShortName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    IrValueParameter irValueParameterCreateValueParameter = irFactory.createValueParameter(-1, -1, (IrDeclarationOrigin) script_provided_property2, name3, irType, false, symbol, intRef.element, (IrType) null, false, false, false);
                    irValueParameterCreateValueParameter.setParent(irScript);
                    return irValueParameterCreateValueParameter;
                }
            }, 480, null);
            intRef.element += z2 ? 1 : 0;
            Intrinsics.checkNotNull(propertyDescriptor);
            IrProperty irPropertyGenerateSyntheticProperty = new PropertyGenerator(getDeclarationGenerator()).generateSyntheticProperty(ktScript, propertyDescriptor, irValueParameterDeclareValueParameter$default, z2);
            irPropertyGenerateSyntheticProperty.setOrigin(IrDeclarationOrigin.SCRIPT_PROVIDED_PROPERTY.INSTANCE);
            irScript.getStatements().add(irPropertyGenerateSyntheticProperty);
            arrayList8.add(TuplesKt.to(irValueParameterDeclareValueParameter$default, irPropertyGenerateSyntheticProperty.getSymbol()));
            arrayList7 = arrayList8;
            z = z2 ? 1 : 0;
            str = str;
            scriptGenerator = this;
        }
        boolean z3 = z;
        Pair pairUnzip = CollectionsKt.unzip(arrayList7);
        List<? extends IrValueParameter> list6 = (List) pairUnzip.component1();
        irScript.setProvidedProperties((List) pairUnzip.component2());
        irScript.setProvidedPropertiesParameters(list6);
        Unit unit2 = Unit.INSTANCE;
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setPrimary(z3);
        IrValueParameter thisReceiver2 = irScript.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver2);
        IrType type3 = thisReceiver2.getType();
        Intrinsics.checkNotNull(type3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        irFunctionBuilder.setReturnType((IrSimpleType) type3);
        IrFactory factory = irScript.getFactory();
        int startOffset = irFunctionBuilder.getStartOffset();
        int endOffset = irFunctionBuilder.getEndOffset();
        IrDeclarationOrigin origin = irFunctionBuilder.getOrigin();
        Name name3 = SpecialNames.INIT;
        DescriptorVisibility visibility = irFunctionBuilder.getVisibility();
        boolean isInline = irFunctionBuilder.getIsInline();
        boolean isExpect = irFunctionBuilder.getIsExpect();
        IrType returnType = irFunctionBuilder.getReturnType();
        DescriptorSymbolTableExtension descriptorExtension3 = getContext().getSymbolTable().getDescriptorExtension();
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
        Intrinsics.checkNotNullExpressionValue(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, "getUnsubstitutedPrimaryConstructor(...)");
        IrConstructor irConstructorCreateConstructor = factory.createConstructor(startOffset, endOffset, origin, name3, visibility, isInline, isExpect, returnType, descriptorExtension3.referenceConstructor(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor), irFunctionBuilder.getIsPrimary(), irFunctionBuilder.getIsExternal(), irFunctionBuilder.getContainerSource());
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(listCreateListBuilder, irScript.getEarlierScriptsParameter());
        listCreateListBuilder.addAll(arrayList4);
        listCreateListBuilder.addAll(irScript.getImplicitReceiversParameters());
        listCreateListBuilder.addAll(irScript.getProvidedPropertiesParameters());
        irConstructorCreateConstructor.setValueParameters(CollectionsKt.build(listCreateListBuilder));
        irConstructorCreateConstructor.setParent(irScript);
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor2 = scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
        Intrinsics.checkNotNullExpressionValue(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor2, "getUnsubstitutedPrimaryConstructor(...)");
        irConstructorCreateConstructor.setMetadata(new DescriptorMetadataSource.Function(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor2));
        irScript.setConstructor(irConstructorCreateConstructor);
        Iterator<KtDeclaration> it8 = ktScript.getDeclarations().iterator();
        while (it8.hasNext()) {
            KtDeclaration next2 = it8.next();
            if (next2 instanceof KtScriptInitializer) {
                BodyGenerator bodyGenerator = new BodyGenerator(irScript.getSymbol(), getContext(), null);
                KtExpression body = ((KtScriptInitializer) next2).getBody();
                Intrinsics.checkNotNull(body);
                final IrExpressionBody irExpressionBodyGenerateExpressionBody = bodyGenerator.generateExpressionBody(body);
                List<KtDeclaration> declarations = ktScript.getDeclarations();
                Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
                if (Intrinsics.areEqual(next2, CollectionsKt.last((List) declarations)) && scriptDescriptor.getResultValue() != null) {
                    PropertyDescriptor resultValue = scriptDescriptor.getResultValue();
                    Intrinsics.checkNotNull(resultValue);
                    Intrinsics.checkNotNull(resultValue);
                    IrProperty irPropertyGenerateSyntheticPropertyWithInitializer = new PropertyGenerator(getDeclarationGenerator()).generateSyntheticPropertyWithInitializer(ktScript, resultValue, z3, new Function1<IrField, IrExpressionBody>() { // from class: org.jetbrains.kotlin.psi2ir.generators.ScriptGenerator$generateScriptDeclaration$1$11$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrExpressionBody invoke(IrField it9) {
                            Intrinsics.checkNotNullParameter(it9, "it");
                            DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                            it9.setVisibility(PUBLIC);
                            return irExpressionBodyGenerateExpressionBody;
                        }
                    });
                    irPropertyGenerateSyntheticPropertyWithInitializer.setOrigin(IrDeclarationOrigin.SCRIPT_RESULT_PROPERTY.INSTANCE);
                    irScript.getStatements().add(irPropertyGenerateSyntheticPropertyWithInitializer);
                    irScript.setResultProperty(irPropertyGenerateSyntheticPropertyWithInitializer.getSymbol());
                } else {
                    irScript.getStatements().add(irExpressionBodyGenerateExpressionBody.getExpression());
                }
            } else {
                if (next2 instanceof KtDestructuringDeclaration) {
                    StatementGenerator statementGeneratorCreateStatementGenerator = new BodyGenerator(irScript.getSymbol(), getContext(), null).createStatementGenerator();
                    Intrinsics.checkNotNull(next2);
                    KtDeclaration ktDeclaration = next2;
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktDeclaration), PsiUtilsKt.getEndOffset(ktDeclaration), getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.DESTRUCTURING_DECLARATION.INSTANCE);
                    KtDestructuringDeclaration ktDestructuringDeclaration = (KtDestructuringDeclaration) next2;
                    KtExpression initializer = ktDestructuringDeclaration.getInitializer();
                    Intrinsics.checkNotNull(initializer);
                    Object objAccept = KotlinUtilsKt.deparenthesize(initializer).accept(statementGeneratorCreateStatementGenerator, null);
                    Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    IntermediateValue intermediateValueCreateTemporaryVariableInBlock$default = RematerializableValueKt.createTemporaryVariableInBlock$default(statementGeneratorCreateStatementGenerator.getScope(), getContext(), (IrExpression) objAccept, irCompositeImpl, "container", 0, 0, 48, null);
                    CallGenerator callGenerator = new CallGenerator(statementGeneratorCreateStatementGenerator);
                    int i2 = 0;
                    ?? r7 = z3;
                    for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : ktDestructuringDeclaration.getEntries()) {
                        i2 += r7;
                        WritableSlice<KtDestructuringDeclarationEntry, ResolvedCall<FunctionDescriptor>> COMPONENT_RESOLVED_CALL = BindingContext.COMPONENT_RESOLVED_CALL;
                        Intrinsics.checkNotNullExpressionValue(COMPONENT_RESOLVED_CALL, "COMPONENT_RESOLVED_CALL");
                        ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.getOrFail(scriptGenerator2, COMPONENT_RESOLVED_CALL, ktDestructuringDeclarationEntry);
                        Intrinsics.checkNotNull(resolvedCall);
                        CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(statementGeneratorCreateStatementGenerator, resolvedCall);
                        CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall, intermediateValueCreateTemporaryVariableInBlock$default);
                        WritableSlice<PsiElement, VariableDescriptor> VARIABLE = BindingContext.VARIABLE;
                        Intrinsics.checkNotNullExpressionValue(VARIABLE, "VARIABLE");
                        if (!((VariableDescriptor) GeneratorKt.getOrFail(scriptGenerator2, VARIABLE, ktDestructuringDeclarationEntry)).getShortName().isSpecial()) {
                            Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
                            if (!UnderscoreUtilKt.isSingleUnderscore(ktDestructuringDeclarationEntry)) {
                                KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry2 = ktDestructuringDeclarationEntry;
                                IrExpression irExpressionGenerateCall = callGenerator.generateCall(PsiUtilsKt.getStartOffsetSkippingComments(ktDestructuringDeclarationEntry2), PsiUtilsKt.getEndOffset(ktDestructuringDeclarationEntry2), callBuilderPregenerateCall, IrStatementOrigin.COMPONENT_N.INSTANCE.withIndex(i2));
                                IrProperty irPropertyGenerateDestructuringDeclarationEntryAsPropertyDeclaration = new PropertyGenerator(getDeclarationGenerator()).generateDestructuringDeclarationEntryAsPropertyDeclaration(ktDestructuringDeclarationEntry);
                                IrField backingField = irPropertyGenerateDestructuringDeclarationEntryAsPropertyDeclaration.getBackingField();
                                Intrinsics.checkNotNull(backingField);
                                irScript.getStatements().add(irPropertyGenerateDestructuringDeclarationEntryAsPropertyDeclaration);
                                IrSetFieldImpl irSetFieldImpl = new IrSetFieldImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktDestructuringDeclarationEntry2), PsiUtilsKt.getEndOffset(ktDestructuringDeclarationEntry2), backingField.getSymbol(), getContext().getIrBuiltIns().getUnitType(), null, null);
                                irSetFieldImpl.setValue(irExpressionGenerateCall);
                                int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktDestructuringDeclarationEntry2);
                                int endOffset2 = PsiUtilsKt.getEndOffset(ktDestructuringDeclarationEntry2);
                                IrValueParameter thisReceiver3 = irScript.getThisReceiver();
                                Intrinsics.checkNotNull(thisReceiver3);
                                irSetFieldImpl.setReceiver(new IrGetValueImpl(startOffsetSkippingComments, endOffset2, thisReceiver3.getSymbol(), null, 8, null));
                                irCompositeImpl.getStatements().add(irSetFieldImpl);
                            }
                        }
                        it8 = it8;
                        r7 = 1;
                    }
                    it = it8;
                    irScript.getStatements().add(irCompositeImpl);
                } else {
                    it = it8;
                    List<IrStatement> statements = irScript.getStatements();
                    DeclarationGenerator declarationGenerator = getDeclarationGenerator();
                    Intrinsics.checkNotNull(next2);
                    IrDeclaration irDeclarationGenerateMemberDeclaration = declarationGenerator.generateMemberDeclaration(next2);
                    Intrinsics.checkNotNull(irDeclarationGenerateMemberDeclaration);
                    statements.add(irDeclarationGenerateMemberDeclaration);
                }
                it8 = it;
                z3 = true;
            }
        }
        Unit unit3 = Unit.INSTANCE;
        symbolTable2.leaveScope(irScriptDeclareScript);
        return irScriptDeclareScript;
    }

    static /* synthetic */ IrValueParameter generateScriptDeclaration$lambda$17$makeParameter$default(ScriptGenerator scriptGenerator, IrScript irScript, ParameterDescriptor parameterDescriptor, IrDeclarationOrigin irDeclarationOrigin, int i, int i2, Object obj) {
        if ((i2 & 16) != 0) {
            i = -1;
        }
        return generateScriptDeclaration$lambda$17$makeParameter(scriptGenerator, irScript, parameterDescriptor, irDeclarationOrigin, i);
    }

    private static final IrValueParameter generateScriptDeclaration$lambda$17$makeParameter(final ScriptGenerator scriptGenerator, IrScript irScript, final ParameterDescriptor parameterDescriptor, final IrDeclarationOrigin irDeclarationOrigin, final int i) {
        KotlinType type = parameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        final IrType irType = scriptGenerator.toIrType(type);
        KotlinType varargElementType = DescriptorToIrUtilKt.getVarargElementType(parameterDescriptor);
        final IrType irType2 = varargElementType != null ? scriptGenerator.toIrType(varargElementType) : null;
        IrValueParameter irValueParameterDeclareValueParameter$default = DescriptorSymbolTableExtension.declareValueParameter$default(scriptGenerator.getContext().getSymbolTable().getDescriptorExtension(), -1, -1, irDeclarationOrigin, parameterDescriptor, irType, null, null, null, false, new Function1<IrValueParameterSymbol, IrValueParameter>() { // from class: org.jetbrains.kotlin.psi2ir.generators.ScriptGenerator$generateScriptDeclaration$1$makeParameter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrValueParameter invoke(IrValueParameterSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                IrFactory irFactory = this.this$0.getContext().getIrFactory();
                IrDeclarationOrigin irDeclarationOrigin2 = irDeclarationOrigin;
                Name nameNameForDeclaration = this.this$0.getContext().getSymbolTable().getNameProvider().nameForDeclaration(parameterDescriptor);
                IrType irType3 = irType;
                int indexOrMinusOne = i;
                if (indexOrMinusOne == -1) {
                    indexOrMinusOne = DescriptorToIrUtilKt.getIndexOrMinusOne(parameterDescriptor);
                }
                return irFactory.createValueParameter(-1, -1, irDeclarationOrigin2, nameNameForDeclaration, irType3, false, symbol, indexOrMinusOne, irType2, DescriptorToIrUtilKt.isCrossinline(parameterDescriptor), DescriptorToIrUtilKt.isNoinline(parameterDescriptor), false);
            }
        }, 480, null);
        irValueParameterDeclareValueParameter$default.setParent(irScript);
        return irValueParameterDeclareValueParameter$default;
    }

    private static final IrValueParameter generateScriptDeclaration$lambda$17$createValueParameter(ScriptGenerator scriptGenerator, Ref.IntRef intRef, IrScript irScript, ValueParameterDescriptor valueParameterDescriptor) {
        IrFactory irFactory = scriptGenerator.getContext().getIrFactory();
        IrDeclarationOrigin.SCRIPT_CALL_PARAMETER script_call_parameter = IrDeclarationOrigin.SCRIPT_CALL_PARAMETER.INSTANCE;
        Name name2 = valueParameterDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        KotlinType type = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrType irType = scriptGenerator.toIrType(type);
        IrValueParameterSymbolImpl irValueParameterSymbolImpl = new IrValueParameterSymbolImpl(null, 1, null);
        int i = intRef.element;
        intRef.element = i + 1;
        KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
        IrValueParameter irValueParameterCreateValueParameter = irFactory.createValueParameter(-1, -1, (IrDeclarationOrigin) script_call_parameter, name2, irType, false, (IrValueParameterSymbol) irValueParameterSymbolImpl, i, varargElementType != null ? scriptGenerator.toIrType(varargElementType) : null, valueParameterDescriptor.getIsCrossinline(), valueParameterDescriptor.getIsNoinline(), false);
        irValueParameterCreateValueParameter.setParent(irScript);
        return irValueParameterCreateValueParameter;
    }
}
