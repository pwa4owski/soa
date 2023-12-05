package itmo.soa.demography.util;


import lombok.*;

@AllArgsConstructor
@Getter
public enum Sign {
    EQ("eq"),
    NE("ne"),
    GT("gt"),
    LT("lt"),
    LTE("lte"),
    GTE("gte");

    private final String name;

}