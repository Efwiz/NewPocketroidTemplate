package org.eclipse.jgit.lib;

import org.eclipse.jgit.internal.JGitText;

/* JADX INFO: loaded from: classes13.dex */
public enum RepositoryState {
    BARE { // from class: org.eclipse.jgit.lib.RepositoryState.1
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_bare;
        }
    },
    SAFE { // from class: org.eclipse.jgit.lib.RepositoryState.2
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_normal;
        }
    },
    MERGING { // from class: org.eclipse.jgit.lib.RepositoryState.3
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_conflicts;
        }
    },
    MERGING_RESOLVED { // from class: org.eclipse.jgit.lib.RepositoryState.4
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_merged;
        }
    },
    CHERRY_PICKING { // from class: org.eclipse.jgit.lib.RepositoryState.5
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_conflicts;
        }
    },
    CHERRY_PICKING_RESOLVED { // from class: org.eclipse.jgit.lib.RepositoryState.6
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_merged;
        }
    },
    REVERTING { // from class: org.eclipse.jgit.lib.RepositoryState.7
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_conflicts;
        }
    },
    REVERTING_RESOLVED { // from class: org.eclipse.jgit.lib.RepositoryState.8
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_merged;
        }
    },
    REBASING { // from class: org.eclipse.jgit.lib.RepositoryState.9
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_rebaseOrApplyMailbox;
        }
    },
    REBASING_REBASING { // from class: org.eclipse.jgit.lib.RepositoryState.10
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_rebase;
        }
    },
    APPLY { // from class: org.eclipse.jgit.lib.RepositoryState.11
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_applyMailbox;
        }
    },
    REBASING_MERGE { // from class: org.eclipse.jgit.lib.RepositoryState.12
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_rebaseWithMerge;
        }
    },
    REBASING_INTERACTIVE { // from class: org.eclipse.jgit.lib.RepositoryState.13
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_rebaseInteractive;
        }
    },
    BISECTING { // from class: org.eclipse.jgit.lib.RepositoryState.14
        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canAmend() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCheckout() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canCommit() {
            return true;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean canResetHead() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public boolean isRebasing() {
            return false;
        }

        @Override // org.eclipse.jgit.lib.RepositoryState
        public String getDescription() {
            return JGitText.get().repositoryState_bisecting;
        }
    };

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static RepositoryState[] valuesCustom() {
        RepositoryState[] repositoryStateArrValuesCustom = values();
        int length = repositoryStateArrValuesCustom.length;
        RepositoryState[] repositoryStateArr = new RepositoryState[length];
        System.arraycopy(repositoryStateArrValuesCustom, 0, repositoryStateArr, 0, length);
        return repositoryStateArr;
    }

    public abstract boolean canAmend();

    public abstract boolean canCheckout();

    public abstract boolean canCommit();

    public abstract boolean canResetHead();

    public abstract String getDescription();

    public abstract boolean isRebasing();

    /* synthetic */ RepositoryState(RepositoryState repositoryState) {
        this();
    }
}
