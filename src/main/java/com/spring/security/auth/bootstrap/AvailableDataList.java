package com.spring.security.auth.bootstrap;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableDataList {
    List<DataList> availableDataList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DataList {
        private AvailableData availableData;
        private String remainingCount;
        private String remainingAmount;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AvailableData {
        private String subsidyId;
        private String usedFormDiv;
        private String minChargeAmt;
        private String costUnderChargeableFlg;
        private String multipleUnusableFlg;
        private List<PriceList> priceList;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PriceList {
        private String price;
    }
}
