/****
 *@Author: JinCh
 *@Date: Created in 2019/1/26  16:44
 *@Description:
 */
package learn.KdTree;

import learn.DataSet.BaseSet;

import java.util.List;

public class KDTree {
    int k = 1;
    private int dims=-1;
    private int nodeNum;
    private KDNode root;
    private List types;

    public KDTree() {
    }

    public KDTree(BaseSet data){
        root = new KDNode(data.getDataSet());
        nodeNum = data.getDataSet().size();
        types = data.getType();
        dims = data.getDataSet().get(0).length;
    }
    public String search(double[] target){
        return (String) types.get(0);
    }
}
