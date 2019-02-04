/****
 *@Author: JinCh
 *@Date: Created in 2019/1/24  15:45
 *@Description:
 */
package learn.KdTree;

import learn.MyUtil;

import java.util.List;

public class KDNode {
    private int ki = 0;//
    private double[] data;//最后一个数据默认为label
    private double kv;
    private KDNode left = null;
    private KDNode right = null;
    private KDNode parent = null;

    public KDNode(List<double[]> dataSet){
        if (dataSet.size() == 1){
            data = dataSet.get(0);
        }else{
            //找到方差最大的轴
            double maxS = MyUtil.variance(dataSet,0);
            ki = 0;
            //最后一个默认为label
            for (int i = 1; i < dataSet.get(0).length-1; i++) {
                double S = MyUtil.variance(dataSet,i);
                if (S>maxS){
                    ki = i;
                    maxS = S;
                }
            }
            MyUtil.sortData(dataSet,0,dataSet.size()-1, ki);
            int median = (dataSet.size()-1)/2;
            data = dataSet.get(median);
            kv = data[ki];
            if (median>0){
                left = new KDNode(dataSet.subList(0,median));
                left.parent = this;
            }
            right = new KDNode(dataSet.subList(median+1,dataSet.size()));
            right.parent = this;
        }
    };
    public double LL2distance(double[] target){
        assert data.length!=target.length;
        double res = 0;
        for (int i = 0; i < data.length-1; i++) {
            //最后一个为lable
            res+=(data[i]-target[i])*(data[i]-target[i]);
        }
        return res;
    }

    public int getType(){
        return (int)data[data.length-1];
    }

    public int getKi() {
        return ki;
    }

    public double[] getData() {
        return data;
    }

    public double getKv() {
        return kv;
    }

    public KDNode getLeft() {
        return left;
    }

    public KDNode getRight() {
        return right;
    }

    public KDNode getParent() {
        return parent;
    }
}
