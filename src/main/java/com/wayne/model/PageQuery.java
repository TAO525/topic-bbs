package com.wayne.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Author TAO
 * @Date 2017/4/11 16:50
 */
public class PageQuery<T> implements Serializable {
    private static final long serialVersionUID = -7523359884334787081L;
    public static String pageFlag = "_page";
    public static Object pageObj = new Object();
    protected List<T> list;
    protected Object paras;
    protected String orderBy;
    protected long pageNumber;
    public static long DEFAULT_PAGE_SIZE = 20L;
    protected long pageSize;
    protected long totalPage;
    protected long totalRow;

    public PageQuery() {
        this(1L, (Object)null);
    }

    public PageQuery(long pageNumber, Object paras) {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.totalRow = -1L;
        this.pageNumber = pageNumber;
        this.paras = paras;
    }

    public PageQuery(long pageNumber, Object paras, String userDefinedOrderBy) {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.totalRow = -1L;
        this.pageNumber = pageNumber;
        this.paras = paras;
        this.orderBy = userDefinedOrderBy;
    }

    public PageQuery(long pageNumber, Object paras, long totalRow) {
        this(pageNumber, paras);
        this.totalRow = totalRow;
    }

    public PageQuery(long pageNumber, Object paras, String userDefinedOrderBy, long totalRow) {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.totalRow = -1L;
        this.pageNumber = pageNumber;
        this.paras = paras;
        this.orderBy = userDefinedOrderBy;
        this.totalRow = totalRow;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public PageQuery(long pageNumber, Object paras, long totalRow, long pageSize) {
        this(pageNumber, paras);
        this.totalRow = totalRow;
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return this.list;
    }

    public long getPageNumber() {
        return this.pageNumber;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public long getTotalPage() {
        return this.totalPage;
    }

    public long getTotalRow() {
        return this.totalRow;
    }

    public boolean isFirstPage() {
        return this.pageNumber == 1L;
    }

    public boolean isLastPage() {
        return this.pageNumber == this.totalPage;
    }

    public Object getParas() {
        return this.paras;
    }

    public void setParas(Object paras) {
        this.paras = paras;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

    public void setList(List list) {
        this.list = list;
        this.calcTotalPage();
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    protected void calcTotalPage() {
        if(this.totalRow == 0L) {
            this.totalPage = 1L;
        } else if(this.totalRow % this.pageSize == 0L) {
            this.totalPage = this.totalRow / this.pageSize;
        } else {
            this.totalPage = this.totalRow / this.pageSize + 1L;
        }

    }

    public int hashCode() {
        boolean prime = true;
        byte result = 1;
        int result1 = 31 * result + (this.orderBy == null?0:this.orderBy.hashCode());
        result1 = 31 * result1 + (int)(this.pageNumber ^ this.pageNumber >>> 32);
        result1 = 31 * result1 + (int)(this.pageSize ^ this.pageSize >>> 32);
        result1 = 31 * result1 + (this.paras == null?0:this.paras.hashCode());
        return result1;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else {
            PageQuery other = (PageQuery)obj;
            if(this.orderBy == null) {
                if(other.orderBy != null) {
                    return false;
                }
            } else if(!this.orderBy.equals(other.orderBy)) {
                return false;
            }

            if(this.pageNumber != other.pageNumber) {
                return false;
            } else if(this.pageSize != other.pageSize) {
                return false;
            } else {
                if(this.paras == null) {
                    if(other.paras != null) {
                        return false;
                    }
                } else if(!this.paras.equals(other.paras)) {
                    return false;
                }

                return true;
            }
        }
    }
}
