/****
 *@Author: JinCh
 *@Date: Created in 2019/1/26  16:44
 *@Description:
 */
package learn.KdTree;

import learn.DataSet.BaseSet;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class KDTree {
    private int max_times4check = 20;
    private int k = 1;
    private int dimensional = -1;
    private int nodeNum;
    private KDNode root;
    private List types;

    public KDTree() {
    }

    public KDTree(BaseSet data){
        root = new KDNode(data.getDataSet());
        nodeNum = data.getDataSet().size();
        types = data.getType();
        dimensional = data.getDataSet().get(0).length;
    }

    public String search(double[] target){
        /***
        *@author: JinCh created in 2019/2/4  21:46
        *@description: 找到最近的K个数据点，预测目标点类别。
        *@param target: 需要预测的数据
        *@return: 返回预测种类名
        *@version: v1.0
        */
/** 流程：
1）若kd树为空，则设定两者距离为无穷大，返回；如果kd树非空，则将kd树的根节点加入到优先级队列中；
2）从优先级队列中出队当前优先级最大的结点，计算当前的该点到查找点的距离是否比最近邻距离小，如果是则更新最近邻点和最近邻距离。
 如果查找点在切分维坐标小于当前点的切分维坐标，则把他的右孩子加入到队列中，同时检索它的左孩子，
 否则就把他的左孩子加入到队列中，同时检索它的右孩子。这样一直重复检索，并加入队列，直到检索到叶子节点。
 然后在从优先级队列中出队优先级最大的结点；
3）重复（1）和（2）中的操作，直到优先级队列为空，或者超出规定的次数，返回当前的最近邻结点和距离。
* */
        if (root==null){
            return "";
        }
        //优先级队列，查找点到当前点确定的分割超平面距离越小优先级越大
        PriorityQueue<pqNode> pri_queue = new PriorityQueue<>(10, new Comparator<pqNode>() {
            @Override
            public int compare(pqNode o1, pqNode o2) {
                return Double.compare(o1.priority,o2.priority);
            }
        });
        pri_queue.add(
                new pqNode(root,
                Math.abs(root.getKv()-target[root.getKi()]))
        );

        pqNode presentNode;
        int t = 0;
        int preKi = 0;
        KDNode preKDNode;
        KNearestQueue kNearestQueue = new KNearestQueue();
        while (!pri_queue.isEmpty() && t<max_times4check){
            t++;
            presentNode = pri_queue.poll();
            if (presentNode==null){
                throw new NullPointerException("null pointer in a node!");
            }
            preKDNode = presentNode.kdNode;
            preKi = preKDNode.getKi();
            kNearestQueue.update(
                    new NearestInfo(
                            preKDNode.getType(),
                            preKDNode.LL2distance(target)
                    )
            );
            while(preKDNode.getRight()!=null || preKDNode.getLeft()!=null) {
                t++;
                if (preKDNode.getData()[preKi] < target[preKi]) {
                    if (preKDNode.getLeft() != null) {
                        if (preKDNode.getRight() != null) {
                            int rKi = preKDNode.getRight().getKi();
                            pri_queue.add(new pqNode(preKDNode.getRight(),
                                    Math.abs(preKDNode.getRight().getData()[rKi]-target[rKi]))
                            );
                        }
                        preKDNode = preKDNode.getLeft();
                        preKi = preKDNode.getKi();
                    }else {
                        if (preKDNode.getRight() != null){
                            preKDNode = preKDNode.getRight();
                            preKi = preKDNode.getKi();
                        }else
                            break;
                    }
                } else {
                    if (preKDNode.getRight() != null) {
                        if (preKDNode.getLeft() != null) {
                            int rKi = preKDNode.getLeft().getKi();
                            pri_queue.add(new pqNode(preKDNode.getLeft(),
                                    Math.abs(preKDNode.getLeft().getData()[rKi]-target[rKi]))
                            );
                        }
                        preKDNode = preKDNode.getRight();
                        preKi = preKDNode.getKi();
                    }else {
                        if (preKDNode.getLeft() != null){
                            preKDNode = preKDNode.getLeft();
                            preKi = preKDNode.getKi();
                        }else
                            break;
                    }
                }
                kNearestQueue.update(
                        new NearestInfo(
                                preKDNode.getType(),
                                preKDNode.LL2distance(target)
                        )
                );
            }
        }
        return (String) types.get(kNearestQueue.getType());
    }

    class pqNode {
        //priority queue
        private KDNode kdNode;
        private double priority;

        public pqNode() {
        }

        public pqNode(KDNode kdNode, double priority) {
            this.kdNode = kdNode;
            this.priority = priority;
        }

        public KDNode getKdNode() {
            return kdNode;
        }

        public void setKdNode(KDNode kdNode) {
            this.kdNode = kdNode;
        }

        public double getPriority() {
            return priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }
    }

    class NearestInfo implements Comparator<NearestInfo>,Comparable<NearestInfo> {
        //记录k个最近的点的主要信息。
        //只有距离和类别。
        int type;
        double distance;

        public NearestInfo(int type, double distance) {
            this.type = type;
            this.distance = distance;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }


        @Override
        public int compareTo(NearestInfo o) {
            return Double.compare(distance, o.distance);
        }

        @Override
        public int compare(NearestInfo o1, NearestInfo o2) {
            return Double.compare(o2.distance, o1.distance);
        }
    }

    class KNearestQueue{
        /*
         * 管理K个近邻点的大顶堆
         * 顺便回忆一下堆的用法。
         * */
        private NearestInfo[] kNearest;//记录k个最近点。
        private int currentSize = 0;//当前容量

        KNearestQueue() {
            kNearest = new NearestInfo[k];
        }

        public void update(NearestInfo nearestInfo){
            if (currentSize==0){
                kNearest[currentSize++] = nearestInfo;
            }else if(!isFull()){
                shiftUp(currentSize++, nearestInfo);
            }else {
                if (isNearer(nearestInfo)){
                    replace(nearestInfo);
                }
            }
        }

        private void replace(NearestInfo nearestInfo){
            kNearest[0] = nearestInfo;
            shiftDown(0, nearestInfo);
        }

        private boolean isNearer(NearestInfo nearestInfo){
            return kNearest[0].distance> nearestInfo.distance;
        }

        private void shiftUp(int index ,NearestInfo nearestInfo){
            kNearest[index] = nearestInfo;
            while(index>0){
                int root = (index-1)>>>1;
                if(nearestInfo.distance<=kNearest[root].distance){
                    break;
                }
                kNearest[index] = kNearest[root];
                index=root;
            }
            kNearest[index] = nearestInfo;
        }
        private void shiftDown(int index ,NearestInfo nearestInfo){
            int half = currentSize>>>1;
            while(index<half){
                int child = (index<<1) +1;
                int rChild = child+1;
                if(     rChild<currentSize &&
                        kNearest[child].distance<kNearest[rChild].distance){
                    child = rChild;
                }
                if (kNearest[child].distance< nearestInfo.distance){
                    break;
                }
                kNearest[index] = kNearest[child];
                index = child;
            }
            kNearest[index] = nearestInfo;
        }
        private boolean isFull(){
            return currentSize>=k;
        }

        public int getType(){
            int[] typeBucket = new int[types.size()];
            int range = isFull()? k:currentSize;
            for (int i = 0; i < range; i++) {
                NearestInfo nearestInfo = kNearest[i];
                typeBucket[nearestInfo.type]++;
            }
            int realType = 0;
            for (int i = 1; i < types.size(); i++) {
                if (typeBucket[i] > typeBucket[realType]) {
                    realType = i;
                }
            }
            return realType;
        }
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }

    public int getDimensional() {
        return dimensional;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public KDNode getRoot() {
        return root;
    }

    public List getTypes() {
        return types;
    }

    public int getMax_times4check() {
        return max_times4check;
    }

    public void setMax_times4check(int max_times4check) {
        this.max_times4check = max_times4check;
    }
}
