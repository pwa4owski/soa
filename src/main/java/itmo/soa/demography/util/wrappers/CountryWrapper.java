package itmo.soa.demography.util.wrappers;

import itmo.soa.demography.model.Country;

public class CountryWrapper extends AbstractWrapper<Country> {
    public CountryWrapper(Country country) {
        this.value = country;
    }
    @Override
    public Class<Country> getTypeClass() {
        return Country.class;
    }
}
