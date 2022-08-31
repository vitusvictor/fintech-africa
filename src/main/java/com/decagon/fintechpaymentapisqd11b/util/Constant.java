package com.decagon.fintechpaymentapisqd11b.util;

public class Constant {

    public static final String CREATE_VIRTUAL_ACCOUNT_API = "https://api.flutterwave.com/v3/virtual-account-numbers";
    public static final String AUTHORIZATION = "FLWSECK-662e890e70669994954401be4d9f73c1-X";
    public static final String EMAIL_VERIFICATION_LINK = "http://localhost:8085/api/v1/confirmToken?token=";
    public static final String GET_BANKS_API = "https://api.flutterwave.com/v3/banks/";
    public static final String RESOLVE_ACCOUNT_API = "https://api.flutterwave.com/v3/accounts/resolve";
    public static final String OTHER_BANK_TRANSFER = "https://api.flutterwave.com/v3/transfers";
    public static final String STATUS = "PENDING";

    public static final String KEYS="secret";

    public static final String VERIFY_TRANSFER = "http://localhost:8085/api/v1/transfer/verify-transfer";
    public static final String VERIFY_TRANSACTION = "https://api.flutterwave.com/v3/transactions/:";

}
