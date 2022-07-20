package cat.kmruiz.mdiag.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record DataSize(int bytes) {
    public String humanize() {
        double amount = bytes;
        var unit = "B";

        if (amount > 1000) {
            amount /= 1000.0;
            unit = "KB";
        }

        if (amount > 1000) {
            amount /= 1000.0;
            unit = "MB";
        }

        if (amount > 1000) {
            amount /= 1000.0;
            unit = "GB";
        }

        if (amount > 1000) {
            amount /= 1000.0;
            unit = "TB";
        }

        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP).toString() + " " + unit;
    }
}
