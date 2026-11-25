package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.com.google.common.collect.Maps;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProvider;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;

/* JADX INFO: loaded from: classes6.dex */
public class TopDownAnalysisContext implements BodiesResolveContext {
    private final Map<KtAnonymousInitializer, ClassDescriptorWithResolutionScopes> anonymousInitializers;
    private final Map<KtClassOrObject, ClassDescriptorWithResolutionScopes> classes;
    private StringBuilder debugOutput;
    private final DeclarationScopeProvider declarationScopeProvider;
    private final Map<KtDestructuringDeclarationEntry, PropertyDescriptor> destructuringDeclarationEntries;
    private final Set<KtFile> files;
    private final Map<KtNamedFunction, SimpleFunctionDescriptor> functions;
    private final ExpressionTypingContext localContext;
    private Map<KtCallableDeclaration, CallableMemberDescriptor> members;
    private final DataFlowInfo outerDataFlowInfo;
    private final Map<KtParameter, PropertyDescriptor> primaryConstructorParameterProperties;
    private final Map<KtProperty, PropertyDescriptor> properties;
    private final Map<KtScript, ClassDescriptorWithResolutionScopes> scripts;
    private final Map<KtSecondaryConstructor, ClassConstructorDescriptor> secondaryConstructors;
    private final TopDownAnalysisMode topDownAnalysisMode;
    private final Map<KtTypeAlias, TypeAliasDescriptor> typeAliases;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 7:
            case 9:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
                i2 = 2;
                break;
            case 7:
            case 9:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "outerDataFlowInfo";
                break;
            case 2:
            case 5:
                objArr[0] = "declarationScopeProvider";
                break;
            case 3:
            default:
                objArr[0] = "topDownAnalysisMode";
                break;
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/resolve/TopDownAnalysisContext";
                break;
            case 7:
                objArr[0] = "file";
                break;
            case 9:
                objArr[0] = "declaration";
                break;
        }
        switch (i) {
            case 6:
                objArr[1] = "getTopDownAnalysisMode";
                break;
            case 7:
            case 9:
            default:
                objArr[1] = "org/jetbrains/kotlin/resolve/TopDownAnalysisContext";
                break;
            case 8:
                objArr[1] = "getScripts";
                break;
            case 10:
                objArr[1] = "getMembers";
                break;
            case 11:
                objArr[1] = "getOuterDataFlowInfo";
                break;
            case 12:
                objArr[1] = "getAllClasses";
                break;
        }
        switch (i) {
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
                break;
            case 7:
                objArr[2] = "addFile";
                break;
            case 9:
                objArr[2] = "getDeclaringScope";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
                throw new IllegalStateException(str2);
            case 7:
            case 9:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public TopDownAnalysisContext(TopDownAnalysisMode topDownAnalysisMode, DataFlowInfo dataFlowInfo, DeclarationScopeProvider declarationScopeProvider) {
        if (topDownAnalysisMode == null) {
            $$$reportNull$$$0(0);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(1);
        }
        if (declarationScopeProvider == null) {
            $$$reportNull$$$0(2);
        }
        this.classes = Maps.newLinkedHashMap();
        this.anonymousInitializers = Maps.newLinkedHashMap();
        this.files = new LinkedHashSet();
        this.secondaryConstructors = Maps.newLinkedHashMap();
        this.functions = Maps.newLinkedHashMap();
        this.properties = Maps.newLinkedHashMap();
        this.primaryConstructorParameterProperties = new HashMap();
        this.typeAliases = Maps.newLinkedHashMap();
        this.destructuringDeclarationEntries = Maps.newLinkedHashMap();
        this.members = null;
        this.scripts = Maps.newLinkedHashMap();
        this.topDownAnalysisMode = topDownAnalysisMode;
        this.outerDataFlowInfo = dataFlowInfo;
        this.declarationScopeProvider = declarationScopeProvider;
        this.localContext = null;
    }

    public TopDownAnalysisContext(TopDownAnalysisMode topDownAnalysisMode, DataFlowInfo dataFlowInfo, DeclarationScopeProvider declarationScopeProvider, ExpressionTypingContext expressionTypingContext) {
        if (topDownAnalysisMode == null) {
            $$$reportNull$$$0(3);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(4);
        }
        if (declarationScopeProvider == null) {
            $$$reportNull$$$0(5);
        }
        this.classes = Maps.newLinkedHashMap();
        this.anonymousInitializers = Maps.newLinkedHashMap();
        this.files = new LinkedHashSet();
        this.secondaryConstructors = Maps.newLinkedHashMap();
        this.functions = Maps.newLinkedHashMap();
        this.properties = Maps.newLinkedHashMap();
        this.primaryConstructorParameterProperties = new HashMap();
        this.typeAliases = Maps.newLinkedHashMap();
        this.destructuringDeclarationEntries = Maps.newLinkedHashMap();
        this.members = null;
        this.scripts = Maps.newLinkedHashMap();
        this.topDownAnalysisMode = topDownAnalysisMode;
        this.outerDataFlowInfo = dataFlowInfo;
        this.declarationScopeProvider = declarationScopeProvider;
        this.localContext = expressionTypingContext;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public ExpressionTypingContext getLocalContext() {
        return this.localContext;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public TopDownAnalysisMode getTopDownAnalysisMode() {
        TopDownAnalysisMode topDownAnalysisMode = this.topDownAnalysisMode;
        if (topDownAnalysisMode == null) {
            $$$reportNull$$$0(6);
        }
        return topDownAnalysisMode;
    }

    public void debug(Object obj) {
        StringBuilder sb = this.debugOutput;
        if (sb != null) {
            sb.append(obj).append("\n");
        }
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public Map<KtClassOrObject, ClassDescriptorWithResolutionScopes> getDeclaredClasses() {
        return this.classes;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public Map<KtAnonymousInitializer, ClassDescriptorWithResolutionScopes> getAnonymousInitializers() {
        return this.anonymousInitializers;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public Map<KtSecondaryConstructor, ClassConstructorDescriptor> getSecondaryConstructors() {
        return this.secondaryConstructors;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public Collection<KtFile> getFiles() {
        return this.files;
    }

    public void addFile(KtFile ktFile) {
        if (ktFile == null) {
            $$$reportNull$$$0(7);
        }
        this.files.add(ktFile);
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public Map<KtScript, ClassDescriptorWithResolutionScopes> getScripts() {
        Map<KtScript, ClassDescriptorWithResolutionScopes> map = this.scripts;
        if (map == null) {
            $$$reportNull$$$0(8);
        }
        return map;
    }

    public Map<KtParameter, PropertyDescriptor> getPrimaryConstructorParameterProperties() {
        return this.primaryConstructorParameterProperties;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public Map<KtProperty, PropertyDescriptor> getProperties() {
        return this.properties;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public LexicalScope getDeclaringScope(KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(9);
        }
        return this.declarationScopeProvider.getResolutionScopeForDeclaration(ktDeclaration);
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public Map<KtNamedFunction, SimpleFunctionDescriptor> getFunctions() {
        return this.functions;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public Map<KtTypeAlias, TypeAliasDescriptor> getTypeAliases() {
        return this.typeAliases;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public Map<KtDestructuringDeclarationEntry, PropertyDescriptor> getDestructuringDeclarationEntries() {
        return this.destructuringDeclarationEntries;
    }

    public Map<KtCallableDeclaration, CallableMemberDescriptor> getMembers() {
        if (this.members == null) {
            LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
            this.members = linkedHashMapNewLinkedHashMap;
            linkedHashMapNewLinkedHashMap.putAll(this.functions);
            this.members.putAll(this.properties);
            this.members.putAll(this.primaryConstructorParameterProperties);
        }
        Map<KtCallableDeclaration, CallableMemberDescriptor> map = this.members;
        if (map == null) {
            $$$reportNull$$$0(10);
        }
        return map;
    }

    @Override // org.jetbrains.kotlin.resolve.BodiesResolveContext
    public DataFlowInfo getOuterDataFlowInfo() {
        DataFlowInfo dataFlowInfo = this.outerDataFlowInfo;
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(11);
        }
        return dataFlowInfo;
    }

    public Collection<ClassDescriptorWithResolutionScopes> getAllClasses() {
        List listPlus = CollectionsKt.plus((Collection) getDeclaredClasses().values(), (Iterable) getScripts().values());
        if (listPlus == null) {
            $$$reportNull$$$0(12);
        }
        return listPlus;
    }
}
