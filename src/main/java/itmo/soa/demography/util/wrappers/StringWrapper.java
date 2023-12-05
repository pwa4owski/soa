package itmo.soa.demography.util.wrappers;

public class StringWrapper extends AbstractWrapper<String>{
    public StringWrapper(String val) {
        this.value = val;
    }

    @Override
    public Class<String> getTypeClass() {
        return String.class;
    }
}
