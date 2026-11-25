package com.artemis.managers;

import com.artemis.Manager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class TeamManager extends Manager {
    private final Map<String, Bag<String>> playersByTeam = new HashMap();
    private final Map<String, String> teamByPlayer = new HashMap();

    @Override // com.artemis.BaseSystem
    protected void initialize() {
    }

    public String getTeam(String str) {
        return this.teamByPlayer.get(str);
    }

    public void setTeam(String str, String str2) {
        removeFromTeam(str);
        this.teamByPlayer.put(str, str2);
        Bag<String> bag = this.playersByTeam.get(str2);
        if (bag == null) {
            bag = new Bag<>();
            this.playersByTeam.put(str2, bag);
        }
        bag.add(str);
    }

    public ImmutableBag<String> getPlayers(String str) {
        return this.playersByTeam.get(str);
    }

    public void removeFromTeam(String str) {
        Bag<String> bag;
        String strRemove = this.teamByPlayer.remove(str);
        if (strRemove == null || (bag = this.playersByTeam.get(strRemove)) == null) {
            return;
        }
        bag.remove(str);
    }
}
