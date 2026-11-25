package org.jetbrains.kotlin.com.intellij.psi;

import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.KeyWithDefaultValue;

/* JADX INFO: loaded from: classes3.dex */
public class ResolveState {
    private static final ResolveState ourInitialState = new ResolveState();

    /* JADX WARN: Code duplicated, block: B:20:0x002b  */
    /* JADX WARN: Code duplicated, block: B:28:0x0041  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4) ? 3 : 2];
        if (i == 1) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else if (i == 2) {
            objArr[0] = "state";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ResolveState";
        } else {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ResolveState";
        } else if (i == 3) {
            objArr[1] = "putAll";
        } else if (i != 4) {
            objArr[1] = "initial";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ResolveState";
        }
        if (i == 1) {
            objArr[2] = "put";
        } else if (i == 2) {
            objArr[2] = "putAll";
        } else if (i == 4) {
            objArr[2] = "get";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static ResolveState initial() {
        ResolveState resolveState = ourInitialState;
        if (resolveState == null) {
            $$$reportNull$$$0(0);
        }
        return resolveState;
    }

    public <T> ResolveState put(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(1);
        }
        return new OneElementResolveState(key, t);
    }

    public ResolveState putAll(ResolveState resolveState) {
        if (resolveState == null) {
            $$$reportNull$$$0(2);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(3);
        }
        return resolveState;
    }

    public <T> T get(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(4);
        }
        if (key instanceof KeyWithDefaultValue) {
            return (T) ((KeyWithDefaultValue) key).getDefaultValue();
        }
        return null;
    }

    private static final class OneElementResolveState extends ResolveState {
        private final Key myKey;
        private final Object myValue;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 2) {
                objArr[0] = "state";
            } else if (i != 3) {
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ResolveState$OneElementResolveState";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ResolveState$OneElementResolveState";
            } else {
                objArr[1] = "putAll";
            }
            if (i == 1) {
                objArr[2] = "put";
            } else if (i == 2) {
                objArr[2] = "putAll";
            } else if (i != 3) {
                if (i != 4) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "get";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        private OneElementResolveState(Key key, Object obj) {
            if (key == null) {
                $$$reportNull$$$0(0);
            }
            this.myKey = key;
            this.myValue = obj;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveState
        public <T> ResolveState put(Key<T> key, T t) {
            if (key == null) {
                $$$reportNull$$$0(1);
            }
            if (this.myKey.equals(key)) {
                return new OneElementResolveState(key, t);
            }
            return new TwoElementResolveState(this.myKey, this.myValue, key, t);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveState
        public ResolveState putAll(ResolveState resolveState) {
            if (resolveState == null) {
                $$$reportNull$$$0(2);
            }
            if (resolveState.get(this.myKey) == null) {
                resolveState = resolveState.put(this.myKey, this.myValue);
            }
            if (resolveState == null) {
                $$$reportNull$$$0(3);
            }
            return resolveState;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveState
        public <T> T get(Key<T> key) {
            if (key == null) {
                $$$reportNull$$$0(4);
            }
            T t = this.myKey.equals(key) ? (T) this.myValue : null;
            return (t == null && (key instanceof KeyWithDefaultValue)) ? (T) ((KeyWithDefaultValue) key).getDefaultValue() : t;
        }
    }

    private static class TwoElementResolveState extends ResolveState {
        private final Key myKey1;
        private final Key myKey2;
        private final Object myValue1;
        private final Object myValue2;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "key2";
                    break;
                case 2:
                case 9:
                    objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                    break;
                case 3:
                    objArr[0] = "state";
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ResolveState$TwoElementResolveState";
                    break;
                default:
                    objArr[0] = "key1";
                    break;
            }
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    objArr[1] = "putAll";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ResolveState$TwoElementResolveState";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[2] = "put";
                    break;
                case 3:
                    objArr[2] = "putAll";
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    break;
                case 9:
                    objArr[2] = "get";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    throw new IllegalStateException(str2);
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        TwoElementResolveState(Key key, Object obj, Key key2, Object obj2) {
            if (key == null) {
                $$$reportNull$$$0(0);
            }
            if (key2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myKey1 = key;
            this.myValue1 = obj;
            this.myKey2 = key2;
            this.myValue2 = obj2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveState
        public <T> ResolveState put(Key<T> key, T t) {
            if (key == null) {
                $$$reportNull$$$0(2);
            }
            if (this.myKey1.equals(key)) {
                return new TwoElementResolveState(key, t, this.myKey2, this.myValue2);
            }
            if (this.myKey2.equals(key)) {
                return new TwoElementResolveState(this.myKey1, this.myValue1, key, t);
            }
            return new ManyElementResolveState(this, key, t);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveState
        public ResolveState putAll(ResolveState resolveState) {
            if (resolveState == null) {
                $$$reportNull$$$0(3);
            }
            if (resolveState == ResolveState.ourInitialState) {
                return this;
            }
            if (resolveState instanceof OneElementResolveState) {
                OneElementResolveState oneElementResolveState = (OneElementResolveState) resolveState;
                ResolveState resolveStatePut = put(oneElementResolveState.myKey, oneElementResolveState.myValue);
                if (resolveStatePut == null) {
                    $$$reportNull$$$0(5);
                }
                return resolveStatePut;
            }
            boolean z = resolveState.get(this.myKey1) != null;
            boolean z2 = resolveState.get(this.myKey2) != null;
            if (z && z2) {
                if (resolveState == null) {
                    $$$reportNull$$$0(6);
                }
                return resolveState;
            }
            if (z) {
                ResolveState resolveStatePut2 = resolveState.put(this.myKey2, this.myValue2);
                if (resolveStatePut2 == null) {
                    $$$reportNull$$$0(7);
                }
                return resolveStatePut2;
            }
            if (z2) {
                ResolveState resolveStatePut3 = resolveState.put(this.myKey1, this.myValue1);
                if (resolveStatePut3 == null) {
                    $$$reportNull$$$0(8);
                }
                return resolveStatePut3;
            }
            return new ManyElementResolveState(resolveState, this);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveState
        public <T> T get(Key<T> key) {
            T t;
            if (key == null) {
                $$$reportNull$$$0(9);
            }
            if (this.myKey1.equals(key)) {
                t = (T) this.myValue1;
            } else {
                t = this.myKey2.equals(key) ? (T) this.myValue2 : null;
            }
            return (t == null && (key instanceof KeyWithDefaultValue)) ? (T) ((KeyWithDefaultValue) key).getDefaultValue() : t;
        }
    }

    private static final class ManyElementResolveState extends ResolveState {
        private final Map<Object, Object> myValues;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 7 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 7 ? 3 : 2];
            switch (i) {
                case 1:
                    objArr[0] = "state2";
                    break;
                case 2:
                case 6:
                    objArr[0] = "state";
                    break;
                case 3:
                case 5:
                case 8:
                    objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                    break;
                case 4:
                    objArr[0] = "other";
                    break;
                case 7:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ResolveState$ManyElementResolveState";
                    break;
                default:
                    objArr[0] = "state1";
                    break;
            }
            if (i != 7) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ResolveState$ManyElementResolveState";
            } else {
                objArr[1] = "putAll";
            }
            switch (i) {
                case 4:
                    objArr[2] = "putAllNoCopy";
                    break;
                case 5:
                    objArr[2] = "put";
                    break;
                case 6:
                    objArr[2] = "putAll";
                    break;
                case 7:
                    break;
                case 8:
                    objArr[2] = "get";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i == 7) {
                throw new IllegalStateException(str2);
            }
        }

        ManyElementResolveState(ResolveState resolveState, ResolveState resolveState2) {
            if (resolveState == null) {
                $$$reportNull$$$0(0);
            }
            if (resolveState2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myValues = new HashMap();
            putAllNoCopy(resolveState);
            putAllNoCopy(resolveState2);
        }

        ManyElementResolveState(ResolveState resolveState, Key key, Object obj) {
            if (resolveState == null) {
                $$$reportNull$$$0(2);
            }
            if (key == null) {
                $$$reportNull$$$0(3);
            }
            HashMap map = new HashMap();
            this.myValues = map;
            putAllNoCopy(resolveState);
            map.put(key, obj);
        }

        private void putAllNoCopy(ResolveState resolveState) {
            if (resolveState == null) {
                $$$reportNull$$$0(4);
            }
            if (resolveState instanceof OneElementResolveState) {
                OneElementResolveState oneElementResolveState = (OneElementResolveState) resolveState;
                this.myValues.put(oneElementResolveState.myKey, oneElementResolveState.myValue);
            } else if (resolveState instanceof TwoElementResolveState) {
                TwoElementResolveState twoElementResolveState = (TwoElementResolveState) resolveState;
                this.myValues.put(twoElementResolveState.myKey1, twoElementResolveState.myValue1);
                this.myValues.put(twoElementResolveState.myKey2, twoElementResolveState.myValue2);
            } else if (resolveState instanceof ManyElementResolveState) {
                this.myValues.putAll(((ManyElementResolveState) resolveState).myValues);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveState
        public <T> ResolveState put(Key<T> key, T t) {
            if (key == null) {
                $$$reportNull$$$0(5);
            }
            return new ManyElementResolveState(this, key, t);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveState
        public ResolveState putAll(ResolveState resolveState) {
            if (resolveState == null) {
                $$$reportNull$$$0(6);
            }
            return resolveState == ResolveState.ourInitialState ? this : new ManyElementResolveState(this, resolveState);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveState
        public <T> T get(Key<T> key) {
            if (key == null) {
                $$$reportNull$$$0(8);
            }
            T t = (T) this.myValues.get(key);
            return (t == null && (key instanceof KeyWithDefaultValue)) ? (T) ((KeyWithDefaultValue) key).getDefaultValue() : t;
        }
    }
}
