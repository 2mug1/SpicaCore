package net.spicapvp.core.friend;

import lombok.Getter;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.cache.RedisPlayerData;
import net.spicapvp.core.profile.Profile;

import java.util.*;

public class Friend {

    @Getter
    private Profile profile;
    @Getter
    private List<String> receivingPlayersUUID, requestingPlayersUUID, acceptedPlayersUUID;

    public Friend(Profile profile){
        this.profile = profile;
        receivingPlayersUUID = new ArrayList<>();
        requestingPlayersUUID = new ArrayList<>();
        acceptedPlayersUUID = new ArrayList<>();
    }

    public List<String> getOnlineFriendsUUID(){
        List<String> friends = new ArrayList<>();
        for(String uuid : acceptedPlayersUUID) {
            RedisPlayerData data = SpicaCore.get().getRedisCache().getPlayerData(UUID.fromString(uuid));
            if (data != null) {
                if (data.getLastAction() == RedisPlayerData.LastAction.JOINING_SERVER) {
                    friends.add(uuid);
                }
            }
        }
        return friends;
    }

    public List<String> getMutualFriendsUUID() {
        List<String> mutual = new ArrayList<>();

        acceptedPlayersUUID.forEach(a -> Profile.getByUuid(UUID.fromString(a)).getFriend().getAcceptedPlayersUUID().forEach(b -> {
            if(Profile.getByUuid(UUID.fromString(b)).getFriend().getAcceptedPlayersUUID().contains(a)){
                mutual.add(b);
            }
        }));

        Iterator it = mutual.iterator();
        while (it.hasNext()){
            String c = (String) it.next();
            if(c.equals(profile.getUuid().toString())){
                it.remove();
            }
        }

        return mutual;
    }

    public void addToAcceptedPlayers(String otherUUID){
        acceptedPlayersUUID.add(otherUUID);
        profile.save();
    }

    public void addToReceivingPlayers(String otherUUID){
        receivingPlayersUUID.add(otherUUID);
        profile.save();
    }

    public void addToRequestingPlayers(String otherUUID){
        requestingPlayersUUID.add(otherUUID);
        profile.save();
    }

    public void removeFromAcceptedPlayers(String otherUUID){
       acceptedPlayersUUID.remove(otherUUID);
       profile.save();
    }

    public void removeFromReceivingPlayers(String otherUUID){
        receivingPlayersUUID.remove(otherUUID);
        profile.save();
    }

    public void removeFromRequestingPlayers(String otherUUID){
        requestingPlayersUUID.remove(otherUUID);
        profile.save();
    }
}
