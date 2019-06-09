package com.machine.project.domain;

import com.machine.project.utils.CurrencyEnum;
import lombok.*;

import java.text.DateFormat;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Currency {

    private CurrencyEnum inputCurrency;

    private CurrencyEnum outputCurrency;

    private Double inputPrice;

    private Double outputPrice;

    private String dateFormat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return inputCurrency == currency.inputCurrency &&
                outputCurrency == currency.outputCurrency &&
                Objects.equals(inputPrice, currency.inputPrice) &&
                Objects.equals(outputPrice, currency.outputPrice) &&
                Objects.equals(dateFormat, currency.dateFormat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputCurrency, outputCurrency, inputPrice, outputPrice, dateFormat);
    }
}
