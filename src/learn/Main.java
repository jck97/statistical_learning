package learn;

import learn.DataSet.BaseSet;
import learn.DataSet.DataSet;
import learn.KdTree.KDTree;

public class Main {
    public static void main(String[] args) {
        BaseSet data = DataSet.iris();
        KDTree tree = new KDTree(data);
        tree.setMax_times4check(40);
        tree.setK(10);
        System.out.println(tree.search(new double[]{5.8,3.0,5.1,1.8,0}));
    }
}