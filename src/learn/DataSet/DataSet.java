/****
 *@Author: JinCh
 *@Date: Created in 2019/1/26  16:12
 *@Description:
 */
package learn.DataSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
    public static BaseSet iris(){
        List<double[]> dataSet = new ArrayList<>();
        List type = new ArrayList<String>();
        try {
            FileReader irisFile = new FileReader("iris.txt");
            BufferedReader reader = new BufferedReader(irisFile);
            String line = reader.readLine();
            while(line!=null){
                String[] ss = line.split(",");
                double[] data = new double[ss.length];
                int i = 0;
                for (; i < ss.length-1; i++) {
                    data[i] = Double.valueOf(ss[i]);
                }

                switch (ss[i]) {
                    case "Iris-setosa":
                        data[i] = 1;
                        break;
                    case "Iris-versicolor":
                        data[i] = 2;
                        break;
                    case "Iris-virginica":
                        data[i] = 3;
                        break;
                    default:
                        data[i] = -1;
                        throw new RuntimeException("data file error!");
                }
                dataSet.add(data);
                line = reader.readLine();
            }
            type.add("setosa");
            type.add("versicolor");
            type.add("virginica");
        }catch (IOException e){
            e.printStackTrace();
        }
        return new BaseSet(type, dataSet);
    }
}
