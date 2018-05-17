package com.mie.base.core.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页信息的一些逻辑
 * @param <T>
 */
public class PageView<T extends Serializable> implements Serializable {
	
	private static final Integer MIN_PAGE_NO = 1;
	private static final Integer MAX_PAGE_SIZE = 1000;
	private static final Integer DEFAULT_PAGE_NO = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 10;
	
	private static Logger logger = LoggerFactory.getLogger(PageView.class);
    private static final long serialVersionUID = 7524772792315029450L;

    //当前页号(默认第一页)
    private int pageNo = DEFAULT_PAGE_NO;

    //分页大小(默认10条)
    private int pageSize = DEFAULT_PAGE_SIZE;

    //分页后的总页数
    private int pageCount;

    //总的记录数
    private int rowCount;

    //数据库分页的开始行号
    private int startRowNo;

    //数据库分页的结束行号
    private int endRowNo;

    //分页数据
    private List<T> queryResult;

    public PageView() {
    }

    //以页号模式的分页
    public PageView(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        checkPageParams();
        initRowIndexNO();
    }

    //检查分页参数是否合法,非法数据,进行边界初始化
    private void checkPageParams() {
        if (pageNo < MIN_PAGE_NO) {
        	logger.warn("【分页参数实体】参数中当前分页码[{0}]低于当前设置的最小参数,页码数据改为1。", new Integer[]{pageNo});
            pageNo = 1;
        }
        if (pageSize > MAX_PAGE_SIZE) {
        	logger.warn("【分页参数实体】参数中当前分页码[{0}]超出最大参数，请考虑性能风险。", new Integer[]{pageSize});
//            pageSize = 1000;
        }
    }

    //初始化分页的开始和结束行号
    private void initRowIndexNO() {
        startRowNo = (pageNo - 1) * pageSize;
        endRowNo = pageNo * pageSize;
        if (startRowNo < 0) {
            startRowNo = 0;
        }
        if (endRowNo >= rowCount) {
            endRowNo = rowCount;
        }
    }

    //计算出分页总数
    private void initPageCount() {
        pageCount = (rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize + 1);
        if(pageNo > pageCount){
            pageNo = pageCount;
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        initPageCount();
        initRowIndexNO();
    }

    public int getiTotalRecords() {
        return this.getRowCount();
    }

    public int getiTotalDisplayRecords() {
        return this.getRowCount();
    }

    public int getStartRowNo() {
        return startRowNo;
    }

    public int getEndRowNo() {
        return endRowNo;
    }

    public List<T> getQueryResult() {
        return queryResult;
    }

    public PageView<T> setQueryResult(List<T> result) {
        this.queryResult = result;
        return this;
    }
}
