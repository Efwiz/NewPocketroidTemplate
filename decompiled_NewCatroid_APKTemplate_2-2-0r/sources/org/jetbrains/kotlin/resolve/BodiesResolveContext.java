package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.Map;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;

/* JADX INFO: loaded from: classes6.dex */
public interface BodiesResolveContext {
    Map<KtAnonymousInitializer, ClassDescriptorWithResolutionScopes> getAnonymousInitializers();

    Map<KtClassOrObject, ClassDescriptorWithResolutionScopes> getDeclaredClasses();

    LexicalScope getDeclaringScope(KtDeclaration ktDeclaration);

    Map<KtDestructuringDeclarationEntry, PropertyDescriptor> getDestructuringDeclarationEntries();

    Collection<KtFile> getFiles();

    Map<KtNamedFunction, SimpleFunctionDescriptor> getFunctions();

    ExpressionTypingContext getLocalContext();

    DataFlowInfo getOuterDataFlowInfo();

    Map<KtProperty, PropertyDescriptor> getProperties();

    Map<KtScript, ClassDescriptorWithResolutionScopes> getScripts();

    Map<KtSecondaryConstructor, ClassConstructorDescriptor> getSecondaryConstructors();

    TopDownAnalysisMode getTopDownAnalysisMode();

    Map<KtTypeAlias, TypeAliasDescriptor> getTypeAliases();
}
