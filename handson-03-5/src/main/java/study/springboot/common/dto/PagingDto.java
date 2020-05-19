package study.springboot.common.dto;

import org.springframework.data.domain.Page;
import study.springboot.account.AccountDto;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class PagingDto {
    private final int PAGINATION_SIZE = 10;
    private final int currentPage;
    private boolean hasPrev;
    private boolean hasNext;
    private List<Integer> pageNums;
    private long totalCount;
    private int startPageNum;
    private int endPageNum;

    public PagingDto(Page<AccountDto> page) {
        this.currentPage = page.getNumber() ;
        int totalPages = page.getTotalPages();

        this.totalCount = page.getTotalElements();
        this.startPageNum = (currentPage / PAGINATION_SIZE) * PAGINATION_SIZE;
        this.endPageNum = getEndPageNum(totalPages, startPageNum);
        this.pageNums = IntStream.iterate(startPageNum, x -> x + 1).limit(PAGINATION_SIZE).filter(x -> x <= endPageNum).boxed().collect(toList());


        this.hasPrev = this.startPageNum - PAGINATION_SIZE >= 0;
        this.hasNext = this.startPageNum + PAGINATION_SIZE < totalPages;
    }

    private int getEndPageNum(int totalPages, int startPageNum) {
        int endPageNum = totalPages - 1;
        if(totalPages > (startPageNum + PAGINATION_SIZE -1)) endPageNum = startPageNum + PAGINATION_SIZE - 1;
        return endPageNum;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public List<Integer> getPageNums() {
        return pageNums;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getStartPageNum() {
        return startPageNum;
    }

    public int getEndPageNum() {
        return endPageNum;
    }
}
