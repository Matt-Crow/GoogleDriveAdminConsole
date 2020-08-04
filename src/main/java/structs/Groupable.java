package structs;

/**
 * The Groupable interface is
 * implemented by classes which
 * can be put into Groups, and
 * thus have a Groups attribute.
 * 
 * @author Matt Crow
 * @see structs.Groups
 */
public interface Groupable {
    /**
     * @return the Groups this object
     * belongs to.
     */
    public abstract Groups getGroups();
}
