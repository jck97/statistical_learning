/****
 *@Author: JinCh
 *@Date: Created in 2019/1/24  16:19
 *@Description:
 */
package learn;

import java.util.List;

public class MyUtil {
    public static double variance(List<double[]> dataSet,int i){
        /***
        *@author: JinCh created in 2019/2/3  17:10
        *@description: 计算矩阵i列的方差，并返回。
        *@param dataSet: 矩阵。
        *@param i: 某列，i应该小于dataSet.size()
        *@return: 返回第i个轴的方差s。
        *@version: v1.0
        */
        assert  dataSet.isEmpty();
        assert dataSet.get(0).length < i:"data length:"+dataSet.get(0).length+" i:"+i;

        double res = 0;
        double arrange = 0.0;
        for (double[] data :
                dataSet) {
            arrange += data[i];
        }
        arrange /= dataSet.size();
        for (double[] data:
             dataSet) {
            res += (data[i]-arrange)*(data[i]*arrange);
        }
        return res/dataSet.size();
    };

    public static void sortData(List<double[]> data, int low, int high, int dim){
        if (low<high) {
            int l = low;
            int h = high;
            double[] povit = data.get(low);
            while (l < h) {
                while (l < h && povit[dim] <= data.get(h)[dim]) h--;
                if (h > l) {
                    data.set(l++, data.get(h));
                }
                while (l < h && povit[dim] > data.get(l)[dim]) l++;
                if (l < h) {
                    data.set(h--, data.get(l));
                }
            }
            data.set(l, povit);
            sortData(data, l + 1, high, dim);
            sortData(data, low, l - 1, dim);
        }
    }
}