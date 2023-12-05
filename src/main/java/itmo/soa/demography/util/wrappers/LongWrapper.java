package itmo.soa.demography.util.wrappers;

public class LongWrapper extends AbstractWrapper<Long>{
    public LongWrapper(Long val) {
        this.value = val;
    }

    @Override
    public Class<Long> getTypeClass() {
        return Long.class;
    }
}
