/****
 *@Author: JinCh
 *@Date: Created in 2019/1/24  15:45
 *@Description:
 */
package learn.KdTree;

import learn.MyUtil;

import java.util.List;

public class KDNode {
    private int dim = -1;
    private double[] data;
    private KDNode left;
    private KDNode right;
    private KDNode parent;

    public KDNode(List<double[]> dataSet){
        assert dataSet.isEmpty();

        if (dataSet.size() == 1){
            data = dataSet.get(0);
        }else{
            //找到方差最大的轴
            double maxS = MyUtil.variance(dataSet,0);
            dim = 0;
            for (int i = 1; i < dataSet.get(0).length; i++) {
                double S = MyUtil.variance(dataSet,i);
                if (S>maxS){
                    dim = i;
                    maxS = S;
                }
            }
            MyUtil.median(dataSet,dim);
            int median = (dataSet.size()+1)/2;
            data = dataSet.get(median);
            left = new KDNode(dataSet.subList(0,median-1));
            left.parent = this;
            right = new KDNode(dataSet.subList(median+1,dataSet.size()-1));
            right.parent = this;
        }
    };

}
