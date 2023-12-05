package itmo.soa.demography.util;

import itmo.soa.demography.model.Person;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class Page {
    private int totalPages;
    private int totalElements;
    private int pageSize;
    private List<Person> content;
    private int pageNumber;
    private boolean last;
}
