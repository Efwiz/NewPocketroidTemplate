package org.jetbrains.kotlin.js.facade;

import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public abstract class MainCallParameters {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "parameters", "org/jetbrains/kotlin/js/facade/MainCallParameters", "mainWithArguments"));
    }

    public abstract List<String> arguments();

    public abstract boolean shouldBeGenerated();

    public static MainCallParameters noCall() {
        return new MainCallParameters() { // from class: org.jetbrains.kotlin.js.facade.MainCallParameters.1
            @Override // org.jetbrains.kotlin.js.facade.MainCallParameters
            public boolean shouldBeGenerated() {
                return false;
            }

            @Override // org.jetbrains.kotlin.js.facade.MainCallParameters
            public List<String> arguments() {
                throw new UnsupportedOperationException("#arguments");
            }
        };
    }

    public static MainCallParameters mainWithoutArguments() {
        return new MainCallParameters() { // from class: org.jetbrains.kotlin.js.facade.MainCallParameters.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/facade/MainCallParameters$2", "arguments"));
            }

            @Override // org.jetbrains.kotlin.js.facade.MainCallParameters
            public boolean shouldBeGenerated() {
                return true;
            }

            @Override // org.jetbrains.kotlin.js.facade.MainCallParameters
            public List<String> arguments() {
                List<String> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(0);
                }
                return listEmptyList;
            }
        };
    }

    public static MainCallParameters mainWithArguments(final List<String> list) {
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        return new MainCallParameters() { // from class: org.jetbrains.kotlin.js.facade.MainCallParameters.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/facade/MainCallParameters$3", "arguments"));
            }

            @Override // org.jetbrains.kotlin.js.facade.MainCallParameters
            public boolean shouldBeGenerated() {
                return true;
            }

            @Override // org.jetbrains.kotlin.js.facade.MainCallParameters
            public List<String> arguments() {
                List<String> list2 = list;
                if (list2 == null) {
                    $$$reportNull$$$0(0);
                }
                return list2;
            }
        };
    }
}
