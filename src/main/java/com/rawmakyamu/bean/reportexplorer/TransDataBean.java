/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.reportexplorer;

import com.rawmakyamu.dao.reportexplorer.TransactionExplorerDAO;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.mapping.Transaction;
import java.math.BigDecimal;

/**
 *
 * @author thushanth
 */
public class TransDataBean {

    private String txnid;
    private String cardno;
    private String txntypecode;
    private String walletid;
    private String amount;
    private String posconcode;
    private String nii;
    private String nic;
    private String tid;
    private String currency;
    private String mid;
    private String tomaskedcard;
    private String towallet;
    private String deviceid;
    private String listenerid;
    private String approvecode;
    private String maskedcard;
    private String rrn;
    private String traceno;
    private String rescode;
    private String status;
    private String createdtime;
    private String mti;
    private String processingcode;
    private String localdate;
    private String localtime;
    private String posentrymode;
    private String cardexdate;
    private String invoiceno;
    private String mobileNo;
    private String lastupdatedtime;
    private String todate;
    private String fromdate;
    private String statusDes;
    private String responseDes;
    private String currencydes;
    private String TxntypeDes;
    private String toAccount;
    private String fromAccount;

    private String tomobile;
    private String tokenid;
    private String toemail;
    private String approvalstatus;
    private String promoAmount;
    private String commissionAmount;
    private String deductAmount;
    private String cardType;
    private String cardBrand;

    public TransDataBean() {

    }

    public TransDataBean(Transaction trans) throws Exception {

        if (trans != null) {
            TransactionExplorerDAO dao = new TransactionExplorerDAO();
            if (trans.getTransactionid() != null && !trans.getTransactionid().isEmpty()) {
                this.txnid = trans.getTransactionid();
            } else {
                this.txnid = "--";
            }
            if (trans.getAmount() != null && !trans.getAmount().isEmpty()) {
//                this.amount = Common.toCurrencyFormat(trans.getAmount());

                double d = Double.parseDouble(trans.getAmount());
                this.amount = Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d)));

            } else {
                this.amount = "--";
            }

            if (trans.getTxntypecode() != null && trans.getTxntypecode() != null && !trans.getTxntypecode().isEmpty()) {
                this.txntypecode = trans.getTxntypecode();
                try {
                    this.TxntypeDes = dao.getTxnDesbycode(trans.getTxntypecode());
                } catch (Exception e) {

                }

            } else {
                this.txntypecode = "--";
            }
            if (trans.getCurrency() != null && trans.getCurrency() != null && !trans.getCurrency().isEmpty()) {
                this.currency = trans.getCurrency();
                try {
                    this.currencydes = dao.getCurrencyDesbycode(trans.getTxntypecode());
                } catch (Exception e) {

                }

            } else {
                this.txntypecode = "--";
            }

            if (trans.getWalletid() != null && !trans.getWalletid().isEmpty()) {
                this.walletid = trans.getWalletid();
            } else {
                this.walletid = "--";
            }

//            if (trans.getCardnumber()!= null && !trans.getCardnumber().isEmpty()) {
//
//                String num = trans.getCardnumber();
//                String s2 = num.substring(num.length() - 4);
//                String s1 = "";
//                for (int i = 0; i < num.length() - 4; i++) {
//                    s1 += "*";
//                }
//
//                this.maskedcard = s1 + s2;
//            } else {
//                this.maskedcard = "--";
//            }
            if (trans.getTomaskingcard() != null && !trans.getTomaskingcard().isEmpty()) {
                this.tomaskedcard = trans.getTomaskingcard();
            } else {
                this.tomaskedcard = "--";
            }

            if (trans.getMaskingcard() != null && !trans.getMaskingcard().isEmpty()) {
                this.maskedcard = trans.getMaskingcard();
            } else {
                this.maskedcard = "--";
            }

            if (trans.getPosconditioncode() != null && !trans.getPosconditioncode().isEmpty()) {
                this.posconcode = trans.getPosconditioncode();
            } else {
                this.posconcode = "--";
            }
            if (trans.getNii() != null && !trans.getNii().isEmpty()) {
                this.nii = trans.getNii();
            } else {
                this.nii = "--";
            }

            if (trans.getTid() != null && !trans.getTid().isEmpty()) {
                this.tid = trans.getTid();
            } else {
                this.tid = "--";
            }

            if (trans.getMid() != null && !trans.getMid().isEmpty()) {
                this.mid = trans.getMid();
            } else {
                this.mid = "--";
            }

            if (trans.getTowallet() != null && !trans.getTowallet().isEmpty()) {
                this.towallet = trans.getTowallet();
            } else {
                this.towallet = "--";
            }
            if (trans.getMobile() != null && !trans.getMobile().isEmpty()) {
                this.mobileNo = trans.getMobile();
            } else {
                this.mobileNo = "--";
            }

            if (trans.getListeneruid() != null && !trans.getListeneruid().isEmpty()) {
                this.listenerid = trans.getListeneruid();
            } else {
                this.listenerid = "--";
            }

            if (trans.getApprovecode() != null && !trans.getApprovecode().isEmpty()) {
                this.approvecode = trans.getApprovecode();
            } else {
                this.approvecode = "--";
            }
            if (trans.getCreatetime() != null && !trans.getCreatetime().toString().isEmpty()) {
                this.createdtime = trans.getCreatetime().toString();
            } else {
                this.createdtime = "--";
            }
            if (trans.getRrn() != null && !trans.getRrn().isEmpty()) {
                this.rrn = trans.getRrn();
            } else {
                this.rrn = "--";
            }
            if (trans.getTracenumber() != null && !trans.getTracenumber().isEmpty()) {
                this.traceno = trans.getTracenumber();
            } else {
                this.traceno = "--";
            }
            if (trans.getSwitchresponse() != null && !trans.getSwitchresponse().isEmpty()) {
                this.rescode = trans.getSwitchresponse();
                try {
                    this.responseDes = trans.getSwitchresponse();
                } catch (Exception e) {

                }

            } else {
                this.rescode = "--";
            }
            if (trans.getStatus() != null && !trans.getStatus().getDescription().isEmpty()) {
                this.status = trans.getStatus().getDescription();
            } else {
                this.status = "--";
            }
            if (trans.getMti() != null && !trans.getMti().isEmpty()) {
                this.mti = trans.getMti();
            } else {
                this.mti = "--";
            }
            if (trans.getProcessingcode() != null && !trans.getProcessingcode().isEmpty()) {
                this.processingcode = trans.getProcessingcode();
            } else {
                this.processingcode = "--";
            }
            if (trans.getLocaldate() != null && !trans.getLocaldate().isEmpty()) {
                this.localdate = trans.getLocaldate();
            } else {
                this.localdate = "--";
            }
            if (trans.getLocaltime() != null && !trans.getLocaltime().isEmpty()) {
                this.localtime = trans.getLocaltime();
            } else {
                this.localtime = "--";
            }
            if (trans.getPosentrymode() != null && !trans.getPosentrymode().isEmpty()) {
                this.posentrymode = trans.getPosentrymode();
            } else {
                this.posentrymode = "--";
            }
            if (trans.getInvoiceno() != null && !trans.getInvoiceno().isEmpty()) {
                this.invoiceno = trans.getInvoiceno();
            } else {
                this.invoiceno = "--";
            }
            if (trans.getMobile() != null && !trans.getMobile().isEmpty()) {
                this.mobileNo = trans.getMobile();
            } else {
                this.mobileNo = "--";
            }
            if (trans.getCardexpiredate() != null && !trans.getCardexpiredate().isEmpty()) {
                this.cardexdate = trans.getCardexpiredate();
            } else {
                this.cardexdate = "--";
            }
            if (trans.getLastupdatedtime() != null && !trans.getLastupdatedtime().toString().isEmpty()) {
                this.lastupdatedtime = trans.getLastupdatedtime().toString();
            } else {
                this.lastupdatedtime = "--";
            }
            if (trans.getFromAccount() != null && !trans.getFromAccount().toString().isEmpty()) {
                this.fromAccount = trans.getFromAccount().toString();
            } else {
                this.fromAccount = "--";
            }
            if (trans.getToAccount() != null && !trans.getToAccount().toString().isEmpty()) {
                this.toAccount = trans.getToAccount().toString();
            } else {
                this.toAccount = "--";
            }
//------------------------------------------------------
            if (trans.getTomobile() != null && !trans.getTomobile().toString().isEmpty()) {
                this.tomobile = trans.getTomobile().toString();
            } else {
                this.tomobile = "--";
            }
            if (trans.getToEmail() != null && !trans.getToEmail().toString().isEmpty()) {
                this.toemail = trans.getToEmail().toString();
            } else {
                this.toemail = "--";
            }
            if (trans.getTokenid() != null && !trans.getTokenid().toString().isEmpty()) {
                this.tokenid = trans.getTokenid().toString();
            } else {
                this.tokenid = "--";
            }
            if (trans.getApprovalStatus() != null && !trans.getApprovalStatus().getDescription().toString().isEmpty()) {
                this.approvalstatus = trans.getApprovalStatus().getDescription().toString();
            } else {
                this.approvalstatus = "--";
            }
            if (trans.getPromoAmount() != null && !trans.getPromoAmount().toString().isEmpty()) {
//                this.promoAmount = Common.toCurrencyFormat(trans.getPromoAmount().toString());

                double d = Double.parseDouble(trans.getPromoAmount());
                this.promoAmount = Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d)));
            } else {
                this.promoAmount = "--";
            }
            if (trans.getCommissionAmount() != null && !trans.getCommissionAmount().toString().isEmpty()) {
//                this.commissionAmount = Common.toCurrencyFormat(trans.getCommissionAmount().toString());

                double d = Double.parseDouble(trans.getCommissionAmount());
                this.commissionAmount = Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d)));

            } else {
                this.commissionAmount = "--";
            }
            if (trans.getDeductAmount() != null && !trans.getDeductAmount().toString().isEmpty()) {

                double d = Double.parseDouble(trans.getDeductAmount());
                this.deductAmount = Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d)));

//                this.deductAmount = Common.toCurrencyFormat(trans.getDeductAmount().toString());
            } else {
                this.deductAmount = "--";
            }

            if (trans.getCardType() != null && !trans.getCardType().toString().isEmpty()) {
                this.cardType = trans.getCardType().toString();
            } else {
                this.cardType = "--";
            }

            if (trans.getCardBrand() != null && !trans.getCardBrand().toString().isEmpty()) {
                this.cardBrand = trans.getCardBrand().toString();
            } else {
                this.cardBrand = "--";
            }
        } else {

            this.txnid = "--";
            this.amount = "--";
            this.approvecode = "--";
            this.cardexdate = "--";
            this.cardno = "--";
            this.createdtime = "--";
            this.deviceid = "--";
            this.invoiceno = "--";
            this.mobileNo = "--";
            this.lastupdatedtime = "--";
            this.localdate = "--";
            this.localtime = "--";
            this.mti = "--";
            this.tid = "--";
            this.nii = "--";
            this.tomaskedcard = "--";
            this.maskedcard = "--";
            this.nii = "--";
            this.mid = "--";
            this.posentrymode = "--";
            this.processingcode = "--";
            this.rescode = "--";
            this.rrn = "--";
            this.status = "--";
            this.towallet = "--";
            this.traceno = "--";
            this.txntypecode = "--";
            this.walletid = "--";
            this.statusDes = "--";
            this.currencydes = "--";
            this.TxntypeDes = "--";
            this.responseDes = "--";
            this.toAccount = "--";
            this.fromAccount = "--";
            this.tomobile = "--";
            this.tokenid = "--";
            this.toemail = "--";
            this.approvalstatus = "--";
            this.promoAmount = "--";
            this.commissionAmount = "--";
            this.deductAmount = "--";
            this.cardType = "--";
            this.cardBrand = "--";

        }

    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public String getResponseDes() {
        return responseDes;
    }

    public void setResponseDes(String responseDes) {
        this.responseDes = responseDes;
    }

    public String getCurrencydes() {
        return currencydes;
    }

    public void setCurrencydes(String currencydes) {
        this.currencydes = currencydes;
    }

    public String getTxntypeDes() {
        return TxntypeDes;
    }

    public void setTxntypeDes(String TxntypeDes) {
        this.TxntypeDes = TxntypeDes;
    }

    public String getListenerid() {
        return listenerid;
    }

    public void setListenerid(String listenerid) {
        this.listenerid = listenerid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getTxntypecode() {
        return txntypecode;
    }

    public void setTxntypecode(String txntypecode) {
        this.txntypecode = txntypecode;
    }

    public String getWalletid() {
        return walletid;
    }

    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTowallet() {
        return towallet;
    }

    public void setTowallet(String towallet) {
        this.towallet = towallet;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getApprovecode() {
        return approvecode;
    }

    public void setApprovecode(String approvecode) {
        this.approvecode = approvecode;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getTraceno() {
        return traceno;
    }

    public void setTraceno(String traceno) {
        this.traceno = traceno;
    }

    public String getRescode() {
        return rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getProcessingcode() {
        return processingcode;
    }

    public void setProcessingcode(String processingcode) {
        this.processingcode = processingcode;
    }

    public String getLocaldate() {
        return localdate;
    }

    public void setLocaldate(String localdate) {
        this.localdate = localdate;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getPosentrymode() {
        return posentrymode;
    }

    public void setPosentrymode(String posentrymode) {
        this.posentrymode = posentrymode;
    }

    public String getCardexdate() {
        return cardexdate;
    }

    public void setCardexdate(String cardexdate) {
        this.cardexdate = cardexdate;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getMaskedcard() {
        return maskedcard;
    }

    public void setMaskedcard(String maskedcard) {
        this.maskedcard = maskedcard;
    }

    public String getPosconcode() {
        return posconcode;
    }

    public void setPosconcode(String posconcode) {
        this.posconcode = posconcode;
    }

    public String getNii() {
        return nii;
    }

    public void setNii(String nii) {
        this.nii = nii;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTomaskedcard() {
        return tomaskedcard;
    }

    public void setTomaskedcard(String tomaskedcard) {
        this.tomaskedcard = tomaskedcard;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getTomobile() {
        return tomobile;
    }

    public void setTomobile(String tomobile) {
        this.tomobile = tomobile;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public String getToemail() {
        return toemail;
    }

    public void setToemail(String toemail) {
        this.toemail = toemail;
    }

    public String getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public String getPromoAmount() {
        return promoAmount;
    }

    public void setPromoAmount(String promoAmount) {
        this.promoAmount = promoAmount;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(String deductAmount) {
        this.deductAmount = deductAmount;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

}
