package itmo.soa.demography.util.wrappers;

import java.time.ZonedDateTime;

public class ZonedDateTimeWrapper extends AbstractWrapper<ZonedDateTime> {

    public ZonedDateTimeWrapper(ZonedDateTime val) {
        this.value = val;
    }

    @Override
    public Class<ZonedDateTime> getTypeClass() {
        return ZonedDateTime.class;
    }
}
