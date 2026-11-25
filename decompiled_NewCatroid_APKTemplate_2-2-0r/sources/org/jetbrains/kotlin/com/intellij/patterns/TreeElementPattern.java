package org.jetbrains.kotlin.com.intellij.patterns;

import com.reandroid.arsc.container.SpecTypePair;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.patterns.TreeElementPattern;
import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TreeElementPattern<ParentType, T extends ParentType, Self extends TreeElementPattern<ParentType, T, Self>> extends ObjectPattern<T, Self> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 2:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[0] = "pattern";
                break;
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/patterns/TreeElementPattern";
                break;
            case 10:
                objArr[0] = "aClass";
                break;
            case 17:
                objArr[0] = "stopAt";
                break;
            case 18:
                objArr[0] = "patterns";
                break;
            case 19:
                objArr[0] = SchemaSymbols.ATTVAL_SKIP;
                break;
            default:
                objArr[0] = "condition";
                break;
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/patterns/TreeElementPattern";
        } else {
            objArr[1] = "withParent";
        }
        switch (i) {
            case 1:
                objArr[2] = "withParents";
                break;
            case 2:
            case 3:
                objArr[2] = "withParent";
                break;
            case 4:
                break;
            case 5:
                objArr[2] = "withChild";
                break;
            case 6:
                objArr[2] = "withFirstChild";
                break;
            case 7:
                objArr[2] = "withLastChild";
                break;
            case 8:
                objArr[2] = "withChildren";
                break;
            case 9:
                objArr[2] = "isFirstAcceptedChild";
                break;
            case 10:
            case 11:
                objArr[2] = "withSuperParent";
                break;
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
                objArr[2] = "inside";
                break;
            case 15:
                objArr[2] = "withAncestor";
                break;
            case 18:
                objArr[2] = "insideSequence";
                break;
            case 19:
                objArr[2] = "afterSiblingSkipping";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    protected abstract ParentType getParent(ParentType parenttype);

    protected TreeElementPattern(Class<T> cls) {
        super(cls);
    }

    public Self withParent(Class<? extends ParentType> cls) {
        if (cls == null) {
            $$$reportNull$$$0(2);
        }
        return (Self) withParent(StandardPatterns.instanceOf(cls));
    }

    public Self withParent(ElementPattern<? extends ParentType> elementPattern) {
        if (elementPattern == null) {
            $$$reportNull$$$0(3);
        }
        Self self = (Self) withSuperParent(1, elementPattern);
        if (self == null) {
            $$$reportNull$$$0(4);
        }
        return self;
    }

    public Self withSuperParent(final int i, ElementPattern<? extends ParentType> elementPattern) {
        if (elementPattern == null) {
            $$$reportNull$$$0(11);
        }
        return with(new PatternConditionPlus<T, ParentType>(i == 1 ? "withParent" : "withSuperParent", elementPattern) { // from class: org.jetbrains.kotlin.com.intellij.patterns.TreeElementPattern.4
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternConditionPlus
            public boolean processValues(T t, ProcessingContext processingContext, PairProcessor<? super ParentType, ? super ProcessingContext> pairProcessor) {
                for (int i2 = 0; i2 < i; i2++) {
                    if (t == null) {
                        return true;
                    }
                    t = (Object) TreeElementPattern.this.getParent(t);
                }
                return pairProcessor.process(t, processingContext);
            }
        });
    }

    public Self inside(ElementPattern<? extends ParentType> elementPattern) {
        if (elementPattern == null) {
            $$$reportNull$$$0(13);
        }
        return (Self) inside(false, elementPattern);
    }

    public Self inside(final boolean z, ElementPattern<? extends ParentType> elementPattern) {
        if (elementPattern == null) {
            $$$reportNull$$$0(14);
        }
        return with(new PatternConditionPlus<T, ParentType>("inside", elementPattern) { // from class: org.jetbrains.kotlin.com.intellij.patterns.TreeElementPattern.5
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternConditionPlus
            public boolean processValues(T t, ProcessingContext processingContext, PairProcessor<? super ParentType, ? super ProcessingContext> pairProcessor) {
                if (z) {
                    t = (Object) TreeElementPattern.this.getParent(t);
                }
                while (t != null) {
                    if (!pairProcessor.process(t, processingContext)) {
                        return false;
                    }
                    t = (Object) TreeElementPattern.this.getParent(t);
                }
                return true;
            }
        });
    }
}
