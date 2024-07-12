package net.minecraft.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Map;
import java.util.UUID;

public class Session
{
    private final String username;
    private final String playerID;
    private final String token;
    private final Session.Type sessionType;

    public Session(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn)
    {
        this.username = "QuiddleSquiddle";
        this.playerID = "bbcadcc9-d86a-4597-9922-e761b5a893b4";
        this.token = "eyJraWQiOiJhYzg0YSIsImFsZyI6IkhTMjU2In0.eyJ4dWlkIjoiMjUzNTQyNjI3NjYzMzEzOCIsImFnZyI6IkFkdWx0Iiwic3ViIjoiOGZlY2QwZTEtODYyOS00NDZhLWI5MzktY2RkYmYyMjc5MDU1IiwiYXV0aCI6IlhCT1giLCJucyI6ImRlZmF1bHQiLCJyb2xlcyI6W10sImlzcyI6ImF1dGhlbnRpY2F0aW9uIiwiZmxhZ3MiOlsidHdvZmFjdG9yYXV0aCIsIm1zYW1pZ3JhdGlvbl9zdGFnZTQiLCJvcmRlcnNfMjAyMiIsIm11bHRpcGxheWVyIl0sInByb2ZpbGVzIjp7Im1jIjoiYmJjYWRjYzktZDg2YS00NTk3LTk5MjItZTc2MWI1YTg5M2I0In0sInBsYXRmb3JtIjoiVU5LTk9XTiIsInl1aWQiOiIzNmFjYWE2MjZiMjM2MjcwZWZiMWI2YjE5Nzk1NTI0YiIsIm5iZiI6MTcyMDc0MzIzMSwiZXhwIjoxNzIwODI5NjMxLCJpYXQiOjE3MjA3NDMyMzF9.-yzHhVX1mt9VpDrnFfmkJ7OaG7odNZ2J3CVKu6V7nCU";
        this.sessionType = Session.Type.setSessionType(sessionTypeIn);
    }

    public String getSessionID()
    {
        return "token:" + this.token + ":" + this.playerID;
    }

    public String getPlayerID()
    {
        return this.playerID;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getToken()
    {
        return this.token;
    }

    public GameProfile getProfile()
    {
        try
        {
            UUID uuid = UUIDTypeAdapter.fromString(this.getPlayerID());
            return new GameProfile(uuid, this.getUsername());
        }
        catch (IllegalArgumentException var2)
        {
            return new GameProfile((UUID)null, this.getUsername());
        }
    }

    /**
     * Returns either 'legacy' or 'mojang' whether the account is migrated or not
     */
    public Session.Type getSessionType()
    {
        return this.sessionType;
    }

    public static enum Type
    {
        LEGACY("legacy"),
        MOJANG("mojang");

        private static final Map<String, Session.Type> SESSION_TYPES = Maps.<String, Session.Type>newHashMap();
        private final String sessionType;

        private Type(String sessionTypeIn)
        {
            this.sessionType = sessionTypeIn;
        }

        public static Session.Type setSessionType(String sessionTypeIn)
        {
            return (Session.Type)SESSION_TYPES.get(sessionTypeIn.toLowerCase());
        }

        static {
            for (Session.Type session$type : values())
            {
                SESSION_TYPES.put(session$type.sessionType, session$type);
            }
        }
    }
}
