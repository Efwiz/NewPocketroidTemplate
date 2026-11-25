package org.jetbrains.kotlin.com.intellij.patterns;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern;
import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ObjectPattern<T, Self extends ObjectPattern<T, Self>> implements ElementPattern<T>, Cloneable {
    private Object myConditions;
    private InitialPatternCondition<T> myInitialCondition;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 15:
            case 16:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 3:
            case 6:
            case 10:
            case 13:
            case 14:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 15:
            case 16:
                i2 = 2;
                break;
            case 3:
            case 6:
            case 10:
            case 13:
            case 14:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "aClass";
                break;
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 15:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/patterns/ObjectPattern";
                break;
            case 3:
                objArr[0] = "patterns";
                break;
            case 6:
                objArr[0] = "o";
                break;
            case 10:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 13:
                objArr[0] = "pattern";
                break;
            case 14:
            default:
                objArr[0] = "condition";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "andNot";
                break;
            case 3:
            case 6:
            case 10:
            case 13:
            case 14:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/patterns/ObjectPattern";
                break;
            case 4:
                objArr[1] = "andOr";
                break;
            case 5:
                objArr[1] = "and";
                break;
            case 7:
                objArr[1] = "equalTo";
                break;
            case 8:
            case 9:
                objArr[1] = "oneOf";
                break;
            case 11:
            case 12:
                objArr[1] = "save";
                break;
            case 15:
                objArr[1] = "adapt";
                break;
            case 16:
                objArr[1] = "without";
                break;
        }
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 15:
            case 16:
                break;
            case 3:
                objArr[2] = "andOr";
                break;
            case 6:
                objArr[2] = "equalTo";
                break;
            case 10:
                objArr[2] = "save";
                break;
            case 13:
                objArr[2] = "with";
                break;
            case 14:
                objArr[2] = "adapt";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 15:
            case 16:
                throw new IllegalStateException(str2);
            case 3:
            case 6:
            case 10:
            case 13:
            case 14:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    protected ObjectPattern(InitialPatternCondition<T> initialPatternCondition) {
        if (initialPatternCondition == null) {
            $$$reportNull$$$0(0);
        }
        this.myInitialCondition = initialPatternCondition;
        this.myConditions = null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected ObjectPattern(final Class<T> cls) {
        this(new InitialPatternCondition<T>(cls) { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.1
            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public boolean accepts(Object obj, ProcessingContext processingContext) {
                return cls.isInstance(obj);
            }
        });
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.patterns.ElementPattern
    public final boolean accepts(Object obj) {
        return accepts(obj, new ProcessingContext());
    }

    @Override // org.jetbrains.kotlin.com.intellij.patterns.ElementPattern
    public boolean accepts(Object obj, ProcessingContext processingContext) {
        if (!this.myInitialCondition.accepts(obj, processingContext)) {
            return false;
        }
        Object obj2 = this.myConditions;
        if (obj2 == null) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj2 instanceof PatternCondition) {
            return ((PatternCondition) obj2).accepts(obj, processingContext);
        }
        List list = (List) obj2;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!((PatternCondition) list.get(i)).accepts(obj, processingContext)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.patterns.ElementPattern
    public final ElementPatternCondition<T> getCondition() {
        Object obj = this.myConditions;
        if (obj == null) {
            return new ElementPatternCondition<>(this.myInitialCondition);
        }
        if (obj instanceof PatternCondition) {
            return new ElementPatternCondition<>(this.myInitialCondition, Collections.singletonList((PatternCondition) obj));
        }
        return new ElementPatternCondition<>(this.myInitialCondition, (List) this.myConditions);
    }

    public Self andNot(ElementPattern elementPattern) {
        Self self = (Self) and(StandardPatterns.not(elementPattern));
        if (self == null) {
            $$$reportNull$$$0(2);
        }
        return self;
    }

    public Self andOr(ElementPattern... elementPatternArr) {
        if (elementPatternArr == null) {
            $$$reportNull$$$0(3);
        }
        Self self = (Self) and(StandardPatterns.or(elementPatternArr));
        if (self == null) {
            $$$reportNull$$$0(4);
        }
        return self;
    }

    public Self and(ElementPattern elementPattern) {
        Self self = (Self) with(new PatternConditionPlus<T, T>("and", elementPattern) { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.2
            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternConditionPlus
            public boolean processValues(T t, ProcessingContext processingContext, PairProcessor<? super T, ? super ProcessingContext> pairProcessor) {
                return pairProcessor.process(t, processingContext);
            }
        });
        if (self == null) {
            $$$reportNull$$$0(5);
        }
        return self;
    }

    public Self equalTo(final T t) {
        if (t == null) {
            $$$reportNull$$$0(6);
        }
        Self self = (Self) with(new ValuePatternCondition<T>("equalTo") { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/patterns/ObjectPattern$3", "accepts"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
            public boolean accepts(T t2, ProcessingContext processingContext) {
                if (t2 == null) {
                    $$$reportNull$$$0(0);
                }
                return t2.equals(t);
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.ValuePatternCondition
            public Collection<T> getValues() {
                return Collections.singletonList(t);
            }
        });
        if (self == null) {
            $$$reportNull$$$0(7);
        }
        return self;
    }

    public Self oneOf(T... tArr) {
        final Collection collectionAsList;
        int length = tArr.length;
        if (length == 1) {
            collectionAsList = Collections.singletonList(tArr[0]);
        } else if (length >= 11) {
            collectionAsList = ContainerUtil.set(tArr);
        } else {
            collectionAsList = Arrays.asList(tArr);
        }
        Self self = (Self) with(new ValuePatternCondition<T>("oneOf") { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.4
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/patterns/ObjectPattern$4", "accepts"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.ValuePatternCondition
            public Collection<T> getValues() {
                return collectionAsList;
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
            public boolean accepts(T t, ProcessingContext processingContext) {
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                return collectionAsList.contains(t);
            }
        });
        if (self == null) {
            $$$reportNull$$$0(8);
        }
        return self;
    }

    public Self oneOf(final Collection<T> collection) {
        Self self = (Self) with(new ValuePatternCondition<T>("oneOf") { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.5
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/patterns/ObjectPattern$5", "accepts"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.ValuePatternCondition
            public Collection<T> getValues() {
                return collection;
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
            public boolean accepts(T t, ProcessingContext processingContext) {
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                return collection.contains(t);
            }
        });
        if (self == null) {
            $$$reportNull$$$0(9);
        }
        return self;
    }

    public Self isNull() {
        return (Self) adapt(new ElementPatternCondition<>(new InitialPatternCondition(Object.class) { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.6
            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public boolean accepts(Object obj, ProcessingContext processingContext) {
                return obj == null;
            }
        }));
    }

    public Self notNull() {
        return (Self) adapt(new ElementPatternCondition<>(new InitialPatternCondition(Object.class) { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.7
            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public boolean accepts(Object obj, ProcessingContext processingContext) {
                return obj != null;
            }
        }));
    }

    public Self save(final Key<? super T> key) {
        if (key == null) {
            $$$reportNull$$$0(10);
        }
        Self self = (Self) with(new PatternCondition<T>("save") { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.8
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/patterns/ObjectPattern$8", "accepts"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
            public boolean accepts(T t, ProcessingContext processingContext) {
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                processingContext.put((Key) key, (Object) t);
                return true;
            }
        });
        if (self == null) {
            $$$reportNull$$$0(11);
        }
        return self;
    }

    public Self save(final String str) {
        Self self = (Self) with(new PatternCondition<T>("save") { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.9
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/patterns/ObjectPattern$9", "accepts"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
            public boolean accepts(T t, ProcessingContext processingContext) {
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                processingContext.put(str, t);
                return true;
            }
        });
        if (self == null) {
            $$$reportNull$$$0(12);
        }
        return self;
    }

    public Self with(PatternCondition<? super T> patternCondition) {
        if (patternCondition == null) {
            $$$reportNull$$$0(13);
        }
        return (Self) adapt(getCondition().append(patternCondition));
    }

    private Self adapt(ElementPatternCondition<T> elementPatternCondition) {
        if (elementPatternCondition == null) {
            $$$reportNull$$$0(14);
        }
        try {
            Self self = (Self) clone();
            self.myInitialCondition = elementPatternCondition.getInitialCondition();
            List<PatternCondition<? super T>> conditions = elementPatternCondition.getConditions();
            if (conditions.isEmpty()) {
                conditions = (List<PatternCondition<? super T>>) null;
            } else if (conditions.size() == 1) {
                conditions = (List<PatternCondition<? super T>>) conditions.get(0);
            }
            self.myConditions = conditions;
            if (self == null) {
                $$$reportNull$$$0(15);
            }
            return self;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Self without(final PatternCondition<? super T> patternCondition) {
        Self self = (Self) with(new PatternCondition<T>("without") { // from class: org.jetbrains.kotlin.com.intellij.patterns.ObjectPattern.10
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "o", "org/jetbrains/kotlin/com/intellij/patterns/ObjectPattern$10", "accepts"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
            public boolean accepts(T t, ProcessingContext processingContext) {
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                return !patternCondition.accepts(t, processingContext);
            }
        });
        if (self == null) {
            $$$reportNull$$$0(16);
        }
        return self;
    }

    public String toString() {
        return getCondition().toString();
    }

    public static final class Capture<T> extends ObjectPattern<T, Capture<T>> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "aClass";
            } else {
                objArr[0] = "condition";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/patterns/ObjectPattern$Capture";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Capture(Class<T> cls) {
            super(cls);
            if (cls == null) {
                $$$reportNull$$$0(0);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Capture(InitialPatternCondition<T> initialPatternCondition) {
            super(initialPatternCondition);
            if (initialPatternCondition == null) {
                $$$reportNull$$$0(1);
            }
        }
    }
}
