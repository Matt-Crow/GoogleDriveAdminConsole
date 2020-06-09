/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drive.commands;

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
}
