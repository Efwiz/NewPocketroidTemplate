package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DataFlowValueFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&J$\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH&J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&J\u001c\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH&J*\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H&J\u0010\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0019À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "", "createDataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "containingDeclarationOrModule", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "resolutionContext", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "createDataFlowValueForProperty", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "usageContainingModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "createDataFlowValueForStableReceiver", "receiver", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DataFlowValueFactory {
    DataFlowValue createDataFlowValue(KtExpression expression, KotlinType type, BindingContext bindingContext, DeclarationDescriptor containingDeclarationOrModule);

    DataFlowValue createDataFlowValue(KtExpression expression, KotlinType type, ResolutionContext<?> resolutionContext);

    DataFlowValue createDataFlowValue(ReceiverValue receiverValue, BindingContext bindingContext, DeclarationDescriptor containingDeclarationOrModule);

    DataFlowValue createDataFlowValue(ReceiverValue receiverValue, ResolutionContext<?> resolutionContext);

    DataFlowValue createDataFlowValueForProperty(KtProperty property, VariableDescriptor variableDescriptor, BindingContext bindingContext, ModuleDescriptor usageContainingModule);

    DataFlowValue createDataFlowValueForStableReceiver(ReceiverValue receiver);
}
