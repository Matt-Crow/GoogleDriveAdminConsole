package structs;

import java.util.Arrays;
import java.util.HashSet;

/**
 * The Groups class is used to manage sharing files.
 * When creating UserToFileMappings, files are given
 * to users in their group.
 * 
 * @author Matt Crow
 */
public class Groups {
    private final HashSet<String> groupNames;
    
    public static final String GROUP_DELIMINATOR = "/";
    
    public Groups(){
        groupNames = new HashSet<>();
    }
    
    public Groups(String groupsString){
        this();
        add(groupsString);
    }
    
    public Groups(String... groups){
        this();
        for(String group : groups){
            add(group);
        }
    }
    
    /**
     * Adds the given groups to this set of Groups.
     * Groups are separated by "/"s.
     * 
     * @param groupsString 
     */
    public void add(String groupsString){
        String[] groups = groupsString.split(GROUP_DELIMINATOR);
        Arrays.stream(groups).map((String group)->{
            return group.trim().toLowerCase();
        }).filter((String group)->{
            return !group.isEmpty();
        }).forEach((String group)->{
            groupNames.add(group);
        });
    }
    
    @Override
    public String toString(){
        return "Groups " + String.join(", ", groupNames);
    }
    
    public static void main(String[] args){
        Groups g = new Groups();
        
        System.out.println(g);
        
        g.add("group #1");
        System.out.println(g);
        
        g.add("this / is / a / test / with / group #1 / test / test / test");
        System.out.println(g);
    }
}
