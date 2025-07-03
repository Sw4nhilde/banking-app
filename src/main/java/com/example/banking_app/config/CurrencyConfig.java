package com.example.banking_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CurrencyConfig {
    
    @Bean
    public Map<String, CurrencyFormat> currencyFormats() {
        Map<String, CurrencyFormat> formats = new HashMap<>();
        
        // Indonesian Rupiah (default)
        formats.put("IDR", new CurrencyFormat(
            "Rp",        // Symbol
            ",",         // Decimal separator
            ".",         // Grouping separator
            0            // Decimal digits
        ));
        
        // US Dollar
        formats.put("USD", new CurrencyFormat(
            "$",
            ".",
            ",",
            2
        ));
        
        // Euro (European format)
        formats.put("EUR", new CurrencyFormat(
            "€",
            ",",
            ".",
            2
        ));
        
        // Add more currencies as needed
        formats.put("GBP", new CurrencyFormat(
            "£",
            ".",
            ",",
            2
        ));
        
        return formats;
    }
    
    public static class CurrencyFormat {
        private final String symbol;
        private final String decimalSeparator;
        private final String groupingSeparator;
        private final int decimalDigits;
        
        public CurrencyFormat(String symbol, String decimalSeparator, 
                            String groupingSeparator, int decimalDigits) {
            this.symbol = symbol;
            this.decimalSeparator = decimalSeparator;
            this.groupingSeparator = groupingSeparator;
            this.decimalDigits = decimalDigits;
        }
        
        // Getters
        public String getSymbol() {
            return symbol;
        }
        
        public String getDecimalSeparator() {
            return decimalSeparator;
        }
        
        public String getGroupingSeparator() {
            return groupingSeparator;
        }
        
        public int getDecimalDigits() {
            return decimalDigits;
        }
    }
}