package com.syf.unifidemoandroid

data class D2DModal(
    val waterfallFinance : String,
    val applicantInfo: ApplicantInfo,
    val merchantInfo: MerchantInfo,
    val strategyInfo: StrategyInfo,
    val deliveryMethod: String,
    val d2dUrl: String,
    val applicationType : String
)