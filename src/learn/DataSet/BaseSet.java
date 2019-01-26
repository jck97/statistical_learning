/****
 *@Author: JinCh
 *@Date: Created in 2019/1/26  16:33
 *@Description:
 */
package learn.DataSet;

import java.util.List;

public class BaseSet {
    private List<String> type;
    private List dataSet;

    public BaseSet() {
    }

    public BaseSet(List<String> type, List dataSet) {
        this.type = type;
        this.dataSet = dataSet;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<double[]> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<double[]> dataSet) {
        this.dataSet = dataSet;
    }
}
