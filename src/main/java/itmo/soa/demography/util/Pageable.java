package itmo.soa.demography.util;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Pageable {
    List<Filter> filters;

    List<Sort> sorts;

    int page;

    int size;
}
