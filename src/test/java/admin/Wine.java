package admin;

/**
 * @author quavario@gmail.com
 * @date 2022/2/7 9:48
 */
public class Wine {

    public String name;

    public Wine(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String drink(){
        return "喝的是 " + getName();
    }
    public String toString(){
        return null;
    }
}
