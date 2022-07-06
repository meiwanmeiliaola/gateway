package admin;

/**
 * @author quavario@gmail.com
 * @date 2021/11/12 16:53
 */
public class BsNode {

    BsNode parent;
    BsNode leftChild;
    BsNode rightChild;
    int val;
    public BsNode(BsNode parent, BsNode leftChild, BsNode rightChild,int val) {
        super();
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.val = val;
    }

    public BsNode(int val){
        this(null,null,null,val);
    }

    public BsNode(BsNode node,int val){
        this(node,null,null,val);
    }
}
