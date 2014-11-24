package ru.unn.agile.Converter.viewmodel;

import ru.unn.agile.Converter.Model.AreaConverter;
import ru.unn.agile.Converter.Model.AreaConverter.Measures;

public class ViewModel {
    private String value;
    private Measures measureFrom;
    private Measures measureTo;
    private String result;
    private boolean isCalculateButtonEnabled;

    private double dvalue;

    public ViewModel() {
        value = "";
        measureFrom = Measures.SquareMeter;
        measureTo = Measures.SquareMeter;
        result = "";

        isCalculateButtonEnabled = false;
    }

    public void processKeyInTextField() {
        parseInput();
    }

    public void setValue(final String value) {
        if (value.equals(this.value)) {
            return;
        }

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Measures getMeasureOfAreaFrom() {
        return measureFrom;
    }

    public void setMeasureOfAreaFrom(final Measures measure) {
        if (this.measureFrom != measure) {
            this.measureFrom = measure;
        }
    }

    public Measures getMeasureOfAreaTo() {
        return measureTo;
    }

    public void setMeasureOfAreaTo(final Measures measure) {
        if (this.measureTo != measure) {
            this.measureTo = measure;
        }
    }

    public String getResult() {
        return result;
    }

    public boolean isCalculateButtonEnabled() {
        return isCalculateButtonEnabled;
    }

    public void convert() {
        if (!parseInput()) {
            return;
        }

        try {
            double dresult = AreaConverter.fromTo(dvalue, measureFrom, measureTo);
            result = Double.toString(dresult);
        } catch (IllegalArgumentException e) {
            result = "Result is too much";
        }
    }

    private boolean parseInput() {
        try {
            if (!value.isEmpty()) {
                dvalue = Double.parseDouble(value);
                if (Double.isInfinite(dvalue)) {
                    throw new IllegalArgumentException("Input is too much");
                }
            }
            isCalculateButtonEnabled = isInputAvailable();
            result = "";
        } catch (NumberFormatException e) {
            result = "Wrong input";
            isCalculateButtonEnabled = false;
            return false;
        } catch (IllegalArgumentException e) {
            result = e.getMessage();
            isCalculateButtonEnabled = false;
            return false;
        }

        return isCalculateButtonEnabled;
    }

    private boolean isInputAvailable() {
        return !value.isEmpty();
    }
}