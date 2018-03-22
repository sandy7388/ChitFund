package chitfund.wayzontech.chitfund.chitfund.model;

/**
 * Created by sandy on 12/3/18.
 */

public class MemberName
{
    private String id,name;

    public MemberName(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MemberName() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
