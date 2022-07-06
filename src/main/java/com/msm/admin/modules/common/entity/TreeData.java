package com.msm.admin.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quavario@gmail.com
 * @date 2019/7/30 11:16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TreeData<T extends TreeData> extends BaseEntity {

    private static final String TOP_NODE_ID = "0";

    /**id*/
    @TableId(type = IdType.UUID)
    private String id;

//    @JsonIgnore
    private String parentId;

    @TableField(exist = false)
    private List<T> children;

    /**
     * 是否是treeData的子节点
     * @param parent 节点
     * @return
     */
    boolean isChildOf(TreeData parent) {
        return this.getParentId().equals(parent.getId());
    }

    /**
     * 是否顶级节点
     * @return
     */
    @JsonIgnore
    boolean isTopNode() {
        return TOP_NODE_ID.equals(this.getParentId());
    }

    /**
     * 挂载子节点
     * @param treeData 子节点
     */
    private void addChild(T treeData) {
        if (this.children == null) {
            this.setChildren(new ArrayList<T>());
        }
        this.getChildren().add(treeData);
    }


    /**
     * 递归数组转列表
     * treeData部门数组转节点思路：
     *      1.遍历treeData找到顶级节点，可能又多个顶级节点，数据库中顶级节点pid为"0"
     *      2.获取顶级节点的子节点，子节点可能包含下级节点，所以此处应递归方式查找节点
     *      3.获取到子节点后，将子节点挂载到顶节点
     *      4.将所有顶级节点返回为数组，推荐使用Set作为节点容器。
     * @param treeData 节点列表
     * @return 树形节点
     */
    public static <T extends TreeData> List<T> toTreeList(List<T> treeData) {
        List<T> result = new ArrayList<>();

        //循环查找顶级节点的子节点
        treeData.forEach(item -> {
            /**
             * item：顶级节点
             */
            if (item.isTopNode()) {
//                List<TreeData> child = recursion(treeData, item);
                List<T> topNodesWithChild = recursion(treeData, item)
                        .stream()
                        //过滤非顶级节点和不同的顶级节点
                        .filter(child -> (child.isTopNode()) && child.getId().equals(item.getId()))
                        .collect(Collectors.toList());
                result.addAll(topNodesWithChild);
            }
        });
        return result;
    }

    /**
     * 递归查询子节点
     * @param treeData 节点列表
     * @param parent 查询子节点的部门
     * @return 树形节点
     */
    private static <T extends TreeData> List<T> recursion(List<T> treeData, TreeData parent) {
        treeData.forEach(item -> {
            /**
             * 查找子节点
             *      1.判断是否是子节点
             */
            if (item.isChildOf(parent)) {
                //添加子节点
                parent.addChild(item);
                //递归查找子节点的下级节点
                recursion(treeData, item);
            }
        });

        return treeData;
    }
}
