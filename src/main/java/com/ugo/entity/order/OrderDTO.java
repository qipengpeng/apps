package com.ugo.entity.order;

public class OrderDTO {

    private String nodeId; //点位ID
    private String nodeName; //点位名称
    private String productId; //商品ID
    private String productName; //商品名称
    private String startDate; //开始时间
    private String endDate; //结束时间
    private String shippingState; //请选择出货状态
    private String id; //支付单号
    private String payStatus; //支付状态


    public OrderDTO() {
    }



    public Integer getNodeId() {
        if (nodeId==""||nodeId==null){
            return -1;
        }
        return Integer.parseInt(nodeId);
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getProductId() {
        if (productId==""||productId==null){
            return -1;
        }
        return Integer.parseInt(productId);
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getShippingState() {
        if (shippingState==""||shippingState==null){
            return -1;
        }
        return Integer.parseInt(shippingState) ;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }



    public Integer getId() {
        if (id==""||id==null){
            return -1;
        }
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPayStatus() {
        if (payStatus==""||payStatus==null){
            return -1;
        }
        return Integer.parseInt(payStatus);
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "nodeId='" + nodeId + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", shippingState='" + shippingState + '\'' +
                ", id='" + id + '\'' +
                ", payStatus='" + payStatus + '\'' +
                '}';
    }
}
