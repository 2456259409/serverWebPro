package com.renjian.utils;

public class RUtil {
    public static String limitStr(int pageSize,int pageNum){
        StringBuilder sb=new StringBuilder();
        sb.append("limit ");
        sb.append(pageNum*pageSize);
        sb.append(",");
        sb.append((pageNum+1)*pageSize);
        return sb.toString();
    }
}
