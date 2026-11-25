package org.jetbrains.kotlin.resolve.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext;
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SyntheticResolveExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u0000 %2\u00020\u0001:\u0001%J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J6\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\u0016J6\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00132\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\u0016J<\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00190\u001aH\u0016J<\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0011H\u0016J&\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001aH\u0016J\u0018\u0010 \u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u00182\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00182\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00182\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00182\u0006\u0010\u0004\u001a\u00020\u0005H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006&À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;", "", "addSyntheticSupertypes", "", "thisDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "supertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "generateSyntheticClasses", "name", "Lorg/jetbrains/kotlin/name/Name;", "ctx", "Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;", "declarationProvider", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;", "result", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", "generateSyntheticMethods", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "fromSupertypes", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "", "generateSyntheticProperties", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "generateSyntheticSecondaryConstructors", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getPossibleSyntheticNestedClassNames", "getSyntheticCompanionObjectNameIfNeeded", "getSyntheticFunctionNames", "getSyntheticNestedClassNames", "getSyntheticPropertiesNames", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SyntheticResolveExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    default void addSyntheticSupertypes(ClassDescriptor thisDescriptor, List<KotlinType> supertypes) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
    }

    default void generateSyntheticClasses(ClassDescriptor thisDescriptor, Name name2, LazyClassContext ctx, ClassMemberDeclarationProvider declarationProvider, Set<ClassDescriptor> result) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    default void generateSyntheticClasses(PackageFragmentDescriptor thisDescriptor, Name name2, LazyClassContext ctx, PackageMemberDeclarationProvider declarationProvider, Set<ClassDescriptor> result) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    default void generateSyntheticMethods(ClassDescriptor thisDescriptor, Name name2, BindingContext bindingContext, List<? extends SimpleFunctionDescriptor> fromSupertypes, Collection<SimpleFunctionDescriptor> result) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    default void generateSyntheticProperties(ClassDescriptor thisDescriptor, Name name2, BindingContext bindingContext, ArrayList<PropertyDescriptor> fromSupertypes, Set<PropertyDescriptor> result) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    default void generateSyntheticSecondaryConstructors(ClassDescriptor thisDescriptor, BindingContext bindingContext, Collection<ClassConstructorDescriptor> result) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    default Name getSyntheticCompanionObjectNameIfNeeded(ClassDescriptor thisDescriptor) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        return null;
    }

    /* JADX INFO: compiled from: SyntheticResolveExtension.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;", "()V", "getInstance", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<SyntheticResolveExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.syntheticResolveExtension", SyntheticResolveExtension.class);
        }

        public final SyntheticResolveExtension getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            final List<SyntheticResolveExtension> instances = getInstances(project);
            return instances.size() == 1 ? (SyntheticResolveExtension) CollectionsKt.single((List) instances) : new SyntheticResolveExtension() { // from class: org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension$Companion$getInstance$1
                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public List<Name> getSyntheticNestedClassNames(ClassDescriptor thisDescriptor) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    List<SyntheticResolveExtension> list = instances;
                    ArrayList arrayList = new ArrayList();
                    for (SyntheticResolveExtension syntheticResolveExtension : list) {
                        try {
                            CollectionsKt.addAll(arrayList, syntheticResolveExtension.getSyntheticNestedClassNames(thisDescriptor));
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                    return arrayList;
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public List<Name> getPossibleSyntheticNestedClassNames(ClassDescriptor thisDescriptor) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    List<SyntheticResolveExtension> list = instances;
                    ArrayList arrayList = new ArrayList();
                    for (SyntheticResolveExtension syntheticResolveExtension : list) {
                        try {
                            List<Name> possibleSyntheticNestedClassNames = syntheticResolveExtension.getPossibleSyntheticNestedClassNames(thisDescriptor);
                            if (possibleSyntheticNestedClassNames == null) {
                                arrayList = null;
                                break;
                            }
                            CollectionsKt.addAll(arrayList, possibleSyntheticNestedClassNames);
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                    return arrayList;
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public List<Name> getSyntheticFunctionNames(ClassDescriptor thisDescriptor) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    List<SyntheticResolveExtension> list = instances;
                    ArrayList arrayList = new ArrayList();
                    for (SyntheticResolveExtension syntheticResolveExtension : list) {
                        try {
                            CollectionsKt.addAll(arrayList, syntheticResolveExtension.getSyntheticFunctionNames(thisDescriptor));
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                    return arrayList;
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public List<Name> getSyntheticPropertiesNames(ClassDescriptor thisDescriptor) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    List<SyntheticResolveExtension> list = instances;
                    ArrayList arrayList = new ArrayList();
                    for (SyntheticResolveExtension syntheticResolveExtension : list) {
                        try {
                            CollectionsKt.addAll(arrayList, syntheticResolveExtension.getSyntheticPropertiesNames(thisDescriptor));
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                    return arrayList;
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public void generateSyntheticClasses(ClassDescriptor thisDescriptor, Name name2, LazyClassContext ctx, ClassMemberDeclarationProvider declarationProvider, Set<ClassDescriptor> result) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(ctx, "ctx");
                    Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
                    Intrinsics.checkNotNullParameter(result, "result");
                    for (SyntheticResolveExtension syntheticResolveExtension : instances) {
                        try {
                            syntheticResolveExtension.generateSyntheticClasses(thisDescriptor, name2, ctx, declarationProvider, result);
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public void generateSyntheticClasses(PackageFragmentDescriptor thisDescriptor, Name name2, LazyClassContext ctx, PackageMemberDeclarationProvider declarationProvider, Set<ClassDescriptor> result) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(ctx, "ctx");
                    Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
                    Intrinsics.checkNotNullParameter(result, "result");
                    for (SyntheticResolveExtension syntheticResolveExtension : instances) {
                        try {
                            syntheticResolveExtension.generateSyntheticClasses(thisDescriptor, name2, ctx, declarationProvider, result);
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public Name getSyntheticCompanionObjectNameIfNeeded(ClassDescriptor thisDescriptor) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    for (SyntheticResolveExtension syntheticResolveExtension : instances) {
                        try {
                            Name syntheticCompanionObjectNameIfNeeded = syntheticResolveExtension.getSyntheticCompanionObjectNameIfNeeded(thisDescriptor);
                            if (syntheticCompanionObjectNameIfNeeded != null) {
                                return syntheticCompanionObjectNameIfNeeded;
                            }
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                    return null;
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public void addSyntheticSupertypes(ClassDescriptor thisDescriptor, List<KotlinType> supertypes) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    Intrinsics.checkNotNullParameter(supertypes, "supertypes");
                    for (SyntheticResolveExtension syntheticResolveExtension : instances) {
                        try {
                            syntheticResolveExtension.addSyntheticSupertypes(thisDescriptor, supertypes);
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public void generateSyntheticMethods(ClassDescriptor thisDescriptor, Name name2, BindingContext bindingContext, List<? extends SimpleFunctionDescriptor> fromSupertypes, Collection<SimpleFunctionDescriptor> result) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
                    Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
                    Intrinsics.checkNotNullParameter(result, "result");
                    for (SyntheticResolveExtension syntheticResolveExtension : instances) {
                        try {
                            syntheticResolveExtension.generateSyntheticMethods(thisDescriptor, name2, bindingContext, fromSupertypes, result);
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public void generateSyntheticProperties(ClassDescriptor thisDescriptor, Name name2, BindingContext bindingContext, ArrayList<PropertyDescriptor> fromSupertypes, Set<PropertyDescriptor> result) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
                    Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
                    Intrinsics.checkNotNullParameter(result, "result");
                    for (SyntheticResolveExtension syntheticResolveExtension : instances) {
                        try {
                            syntheticResolveExtension.generateSyntheticProperties(thisDescriptor, name2, bindingContext, fromSupertypes, result);
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                }

                @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
                public void generateSyntheticSecondaryConstructors(ClassDescriptor thisDescriptor, BindingContext bindingContext, Collection<ClassConstructorDescriptor> result) {
                    Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
                    Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
                    Intrinsics.checkNotNullParameter(result, "result");
                    for (SyntheticResolveExtension syntheticResolveExtension : instances) {
                        try {
                            syntheticResolveExtension.generateSyntheticSecondaryConstructors(thisDescriptor, bindingContext, result);
                        } catch (LinkageError e) {
                            Logger logger = Logger.getInstance(syntheticResolveExtension.getClass());
                            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                            logger.error(syntheticResolveExtension.getClass().getName() + " caused LinkageError", e);
                            throw e;
                        }
                    }
                }
            };
        }
    }

    default List<Name> getSyntheticFunctionNames(ClassDescriptor thisDescriptor) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        return CollectionsKt.emptyList();
    }

    default List<Name> getSyntheticPropertiesNames(ClassDescriptor thisDescriptor) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        return CollectionsKt.emptyList();
    }

    default List<Name> getSyntheticNestedClassNames(ClassDescriptor thisDescriptor) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        return CollectionsKt.emptyList();
    }

    default List<Name> getPossibleSyntheticNestedClassNames(ClassDescriptor thisDescriptor) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        return getSyntheticNestedClassNames(thisDescriptor);
    }
}
