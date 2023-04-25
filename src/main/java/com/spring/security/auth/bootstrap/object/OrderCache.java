package com.spring.security.auth.bootstrap.object;

import lombok.*;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class OrderCache implements Serializable {

    /** メニュー番号 */
    private String menuNo;

    /** プランID */
    private String planId;

    /** 支払方法区分 */
    private String payMthdDiv;

    /** 申し込み枚数 */
    @Builder.Default
    private int numOfSheet = 0;

    /** 払出KEY */
    private String payoutKey;

    /** ベネポ付与区分 */
    private String benepoReturnDiv;

    /** ベネポ定額*/
    private Integer benepoReturnFixed;

    /** 会員特典情報 */
    private List<MemberBenefit> memberBenefits;

    private BenepoReturnInfo benepoReturnInfo;

    @Data
    @Builder
    public static class BenepoReturnInfo implements Serializable {
        private String exclIden;
        private String qtyCntFlag;
    }

    @Data
    @Builder
    public static class MemberBenefit implements Serializable {
        /** 会員特典ID */
        private String memberBenefitsId;

        /** 販売在庫キー */
        private String stockKey;

        /** 注文日 */
        private String designatedSttDay;

        /** 注文日 */
        private String designatedEndDay;

        /** 仮引当数量（枚数） */
        private Integer reserveQty;

        /** 特典内容 */
        private String benefitContent;

        /** 特典見出し */
        private String benefitHeading;

        /** 申込画面表示区分 */
        private String applWinDispDiv ;

        // バックエンドAPIに問い合わせた結果を格納する（改ざん防止）
        /** 一般料金 */
        private int generalPrice;

        // バックエンドAPIに問い合わせた結果を格納する（改ざん防止）
        /** 提携料金 */
        private int alliancePrice;

        // バックエンドAPIに問い合わせた結果を格納する（改ざん防止）
        /** 料金 */
        private int price;

        // バックエンドAPIに問い合わせた結果を格納する（改ざん防止）
        /** B1補助金 */
        private int boneSubsidy;
    }

    /** プラン画像 */
    private List<PlanImage> planImages;

    @Data
    @Builder
    public static class PlanImage implements Serializable {
        /** 画像URL */
        private String imageUrl;

        /** 画像説明 */
        private String imageExplanation;
    }

    /** メニュー名 */
    private String menuNm;

    /** プラン名 */
    private String planNm;

    /** 警告メッセージ */
    private String warningMessage;

    // バックエンドAPIに問い合わせた結果を元に計算する（改ざん防止）
    /** 合計 */
    private int subTotal;

    /** 補助クーポン利用額合計 */
    private int subsidyAmountTotal;

    /** 利用可能補助金リスト */
    private List<SubsidyAvailableData> subsidyAvailableData;

    /** 取得予定ベネポ */
    private Integer grantedBenepo;

    @Setter(value = AccessLevel.NONE)
    private List<BsSubsidyMapping> bsSubsidyMappingArray;

    /** 受注パターン区分 */
    private String orderPatternDiv;

    // この値がセットされている場合、cancel, confirm の処理を行ってください。
    /** 決済GW try phase のトランザクションID */
    private String tryPaymentGwTransactionId;

    /**
     * 料金マスタを使って、各種料金をセットする。
     *
     * @param priceMaster 料金マスタ（ID, 各種 Price)
     */
    public void setPriceFromMaster(List<MemberBenefit> priceMaster) {
        for (final var data : memberBenefits) {
            for (final var master : priceMaster) {
                if (data.getMemberBenefitsId().equals(master.getMemberBenefitsId())) {
                    data.setGeneralPrice(master.getGeneralPrice());
                    data.setAlliancePrice(master.getAlliancePrice());
                    data.setPrice(master.getPrice());
                    data.setBoneSubsidy(master.getBoneSubsidy());

                    break;
                }
            }
        }
    }


    public Integer billingAmount;

    @Data
    @Builder
    public static class BsSubsidyMapping implements Serializable {
        private String subsidyId;
    }

    /** デジチケ情報（必要なもののみ抜粋）*/
    private DigitalTicketInfo digiTicketInfo;

    @Data
    @Builder
    public static class DigitalTicketInfo implements Serializable {
        private ExpirationDate expirationDates;

        @Data
        @Builder
        public static class ExpirationDate implements Serializable {
            private String expirationDateDiv;
            private Integer expirationValue;
            private String expirationDateStandardDiv;
            private String validTmDispFlag;
            private String validTm;

            private List<DesignatedMoDayTime> designatedMoDayTimes;

            @Data
            @Builder
            public static class DesignatedMoDayTime implements Serializable {
                private String designatedSttDay;
                private String designatedEndDay;
            }
        }
    }

    /**
     * 利用可能補助金クラス
     */
    @Data
    @Builder
    public static class SubsidyAvailableData implements Serializable {
        /** 補助金ID */
        private String subsidyId;

        /** 補助金詳細ID */
        private String subsidyDetailId;

        /** 利用形態 */
        private String usedFormDiv;

        private List<Price> priceList;

        @Data
        @Builder
        public static class Price implements Serializable {
            /** 補助金単価ID */
            private String subsidyPriceId;
            private Integer price;
        }
    }

    private MnServiceParam mnServiceParam;

    @Data
    @Builder
    public static class MnServiceParam implements Serializable {
        /** 支払先区分(10：パートナー企業(現地) 20：ベネフィット・ワン 30：その他 40：支払なし) */
        private String payeeDiv;

        /** サービス区分 */
        private String srvicDiv;

        /** 利用方法 */
        private String usageDiv;

        /** 受注パターン区分 */
        private String orderPatternDiv;

        /** 大カテゴリ */
        private String catLrgClassNm;

        /** 中カテゴリ */
        private String catMidClassNm;

        /** 小カテゴリ */
        private String catSmlClassNm;

        private List<String> usageLimitId;

        private List<String> documentId;

        private List<MnServiceParamMemberBenefit> memberBenefits;

        @Data
        @Builder
        public static class MnServiceParamMemberBenefit implements Serializable {
            /** 一般料金 */
            private Integer generalPrice;
            /** 提携料金 */
            private Integer alliancePrice;
            /** 料金 */
            private Integer price;
            /** B1補助金 */
            private Integer boneSubsidy;
            /** 会員特典ID */
            private String menberBenefitsId;
            /** 特典見出し */
            private String benefitHeading;
            /** 公演日 */
            private String performanceDay;
            /** 開催場所 */
            private String venue;
            /** 都道府県 */
            private String prefecture;
            /** 開演時間 */
            private String performanceSttTm;
            /** 席種 */
            private String seatType;
            /** 特典内容 */
            private String benefitContent;
            /** 特典区分 */
            private String benefitDiv;
            /** 申込画面表示区分 */
            private String applWinDispDiv;
            /** デジタルクーポン無料販売フラグ */
            private String digiPonFreeSaleFlag;
            /** 一口枚数 */
            private Integer bitesQty;
            /** 販売在庫キー */
            private String stockKey;
        }
    }

    /** 補助金予約情報 */
    private List<SubsidyReservation> subsidyReservations;

    @Data
    @Builder
    public static class SubsidyReservation implements Serializable {
        /** 受付明細番号 */
        private String acptDtlNo;
        /** 補助金予約ID */
        private String reservationId;
        /** 補助金詳細ID */
        private String subsidyDetailId;
        /** お客様番号 */
        private String customerNo;
        /** クーポンID(抽選クーポンの場合のみ) */
        private String couponId;
        /** 続柄コード */
        private String relationship;
        /** 補助金額 */
        private String amount;

        private String useScheduleDateTime;

        private String reservationDateTime;
    }

    public String getAcptDtlNo() {
        return CollectionUtils.isEmpty(subsidyReservations) ? null : subsidyReservations.get(0).getAcptDtlNo();
    }

    /** メールマガジン設定情報リスト */
    private List<MailMagazinesSettings> mailMagazinesSettings;

    @Data
    @Builder
    public static class MailMagazinesSettings implements Serializable {
        /** メールマガジンID */
        private Long id;
        /** 購読有無フラグ */
        private Boolean isSubscribe;
    }

    /** ポイント使用結果をセット **/
    private PointUse pointUse;

    @Data
    @Builder
    public static class PointUse implements Serializable {
        /** ポイントコード **/
        private String pointCode;
        /** 最短有効期限日 **/
        private String validTo;
    }

    /** 添付ファイルリスト **/
    private List<String> uploadFileList;

    private LotteryInfo lotteryInfo;

    @Data
    @Builder
    public static class LotteryInfo implements Serializable {

        private String lotteryCode;

        private String operationType;

        private String lotteryType;

        private String repeatType;

        private String lotteryDate;

    }
}
