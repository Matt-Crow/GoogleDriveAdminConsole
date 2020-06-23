package structs;

import java.util.Arrays;

/**
 *
 * @author Matt
 */
public enum AccessType {
    MAKE_COPY("copy", true),
    VIEW("view", false);
    
    private final boolean allowDownload;
    private final String name;
    
    private AccessType(String accessTypeName, boolean enableDownload){
        name = accessTypeName;
        allowDownload = enableDownload;
    }
    
    public static final AccessType fromString(String s){
        AccessType ret = null;
        AccessType[] vals = values();
        for(int i = 0; i < vals.length && ret == null; i++){
            if(vals[i].toString().equals(s)){
                ret = vals[i];
            }
        }
        if(ret == null){
            throw new IllegalArgumentException(String.format("There is no AccessType with name of %s. Valid options include %s", s, Arrays.toString(vals)));
        }
        return ret;
    }
    
    public final String getName(){
        return name;
    }
    
    public final boolean shouldAllowDownload(){
        return allowDownload;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
