package itmo.soa.demography.util.wrappers;

import lombok.Getter;

@Getter
public abstract class AbstractWrapper <T extends Comparable> {
    T value;
    public abstract Class<T> getTypeClass();
}
