package gov.samhsa.c2s.staffuiapi.infrastructure.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageableDto<T> {
    private List<T> content;
    private long totalElements;
    private boolean last;
    private int totalPages;
    private int size;
    private int number;
    private boolean first;
    private int numberOfElements;
}
