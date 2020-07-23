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
     * Used to check if two Groups share a common group name.
     * @param g1
     * @param g2
     * @return whether or not the intersection of g1 and g2's 
     * group name sets contains at least one element.
     */
    public static boolean intersects(Groups g1, Groups g2){
        return g1.groupNames.stream().anyMatch((String groupName)->{
            return g2.groupNames.contains(groupName);
        });
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
        return String.join(GROUP_DELIMINATOR, groupNames);
    }
    
    public static void main(String[] args){
        Groups g = new Groups();
        
        System.out.println(g);
        
        g.add("this / is / a / test / test / test");
        System.out.println(g);
        
        Groups g2 = new Groups();
        g2.add("group #1 / group #2 / group #3");
        System.out.println(g2);
        System.out.println(Groups.intersects(g, g2));
        
        g.add("gRoUp #1");
        System.out.println(g);
        System.out.println(Groups.intersects(g, g2));
    }
}
