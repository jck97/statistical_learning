/****
 *@Author: JinCh
 *@Date: Created in 2019/1/24  16:19
 *@Description:
 */
package learn;

import java.util.List;

public class MyUtil {
    public static double variance(List<double[]> dataSet,int i){
        assert  dataSet.isEmpty();
        assert dataSet.get(0).length < i:"data length:"+dataSet.get(0).length+" i:"+i;

        double res = 0;
        for (double[] data:
             dataSet) {
            res += data[i]*data[i];
        }
        return res/dataSet.size();
    };
    public static double median(List<double[]> data,int dim){
        /***
        *@author: JinCh created in 2019/1/24  17:12
        *@description:
        *@param data: 数据
        *@param dim:  那个轴的中位数
        *@return: 中位数所在行的序号
        *@version: v1.0
        */
        return select(data,0,data.size()-1,dim,(data.size()+1)/2);
    }

    private static double select(List<double[]> data,int low,int high,int dim,int index){
        if (low == high){
            return data.get(low)[dim];
        }else {
            int l = low+1;
            int h = high;
            double[] povit = data.get(low);
            while(l<h){
                while(l < h && data.get(l)[dim] <= povit[dim]){
                    data.set(l-1,data.get(l));
                    l++;
                };
                while(l < h && data.get(h)[dim] > povit[dim])h--;
                double[] temp = data.get(l);
                data.set(l,data.get(h));
                data.set(h,temp);
            }
            data.set(l,povit);
            if (l == index){
                return data.get(l)[dim];
            }else if (l<index){
                return select(data,l,data.size()-1,dim,index);
            }else {
                return select(data,0,l-1,dim,index);
            }
        }
    }
}