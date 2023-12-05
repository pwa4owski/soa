package itmo.soa.demography.util.wrappers;

public class IntegerWrapper extends AbstractWrapper<Integer>{
    public IntegerWrapper(Integer value) {
        this.value = value;
    }
    @Override
    public Class<Integer> getTypeClass() {
        return Integer.class;
    }
}
