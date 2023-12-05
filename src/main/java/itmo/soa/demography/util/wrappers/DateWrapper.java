package itmo.soa.demography.util.wrappers;

import java.util.Date;

public class DateWrapper extends AbstractWrapper<Date> {
    public DateWrapper(Date date) {
        this.value = date;
    }

    @Override
    public Class<Date> getTypeClass() {
        return Date.class;
    }
}
