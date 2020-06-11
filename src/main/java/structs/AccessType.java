/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structs;

/**
 *
 * @author Matt
 */
public enum AccessType {
    // from the documentation: "the following are currently allowed: - owner - organizer - fileOrganizer - writer - commenter - reader"
    VIEW("reader"),
    EDIT("writer");
    
    private final String driveRole;
    private AccessType(String role){
        driveRole = role;
    }
    
    public final String getDriveRole(){
        return driveRole;
    }
    
    public static final AccessType fromString(String s){
        AccessType ret = null;
        AccessType[] vals = AccessType.values();
        for(int i = 0; i < vals.length && ret == null; i++){
            if(vals[i].getDriveRole().equals(s)){
                ret = vals[i];
            }
        }
        if(ret == null){
            throw new RuntimeException("No AccessType for role " + s);
        }
        return ret;
    }
    
    @Override
    public String toString(){
        return driveRole;
    }
}
