package io.metersphere.testin.util;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    // 分页查询方法，根据页面大小和页码，从list中获取指定区间的数据
    public List<T> getPageList(List<T> list, int pageSize, int pageNo) {
        int totalCount = list.size();
        int totalPage = 0;
        List<T> pageList = new ArrayList<T>();
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = (totalCount / pageSize) + 1;
        }
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageNo > totalPage) {
            pageNo = totalPage;
        }
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = startIndex + pageSize;
        if (endIndex >= totalCount) {
            endIndex = totalCount;
        }
        for (int i = startIndex; i < endIndex; i++) {
            pageList.add(list.get(i));
        }
        return pageList;
    }
}
