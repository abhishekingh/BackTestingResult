package backTesting;

import java.math.BigDecimal;

public class MarketDataOutput_Getter_Setter {
    private String trade_date;
    private String trade_time;
    private String trade_day;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal vwapWeek;
    private BigDecimal rsi;
    private BigDecimal rsi_ma;
    private BigDecimal adx;
    private BigDecimal diPlus;
    private BigDecimal diMinus;
    private String trade_type;
    private int strike;
    private String exp_1;
    private String exp_2;
    private String exp_3;
    private String exp_4;
    private BigDecimal exp_close1;
    private BigDecimal exp_close2;
    private BigDecimal exp_close3;
    private BigDecimal exp_close4;

    // Getters & Setters
    public String getTrade_date() { return trade_date; }
    public void setTrade_date(String trade_date) { this.trade_date = trade_date; }
    public String getTrade_time() { return trade_time; }
    public void setTrade_time(String trade_time) { this.trade_time = trade_time; }
    public String getTrade_day() { return trade_day; }
    public void setTrade_day(String trade_day) { this.trade_day = trade_day; }

    public BigDecimal getOpen() { return open; }
    public void setOpen(BigDecimal open) { this.open = open; }

    public BigDecimal getHigh() { return high; }
    public void setHigh(BigDecimal high) { this.high = high; }

    public BigDecimal getLow() { return low; }
    public void setLow(BigDecimal low) { this.low = low; }

    public BigDecimal getClose() { return close; }
    public void setClose(BigDecimal close) { this.close = close; }
    public BigDecimal getRsi() { return rsi; }
    public void setRsi(BigDecimal rsi) { this.rsi = rsi; }

    public BigDecimal getRsi_ma() { return rsi_ma; }
    public void setRsi_ma(BigDecimal rsi_ma) { this.rsi_ma = rsi_ma; }

    public BigDecimal getVwapWeek() { return vwapWeek; }
    public void setVwapWeek(BigDecimal vwapWeek) { this.vwapWeek = vwapWeek; }

    public BigDecimal getAdx() { return adx; }
    public void setAdx(BigDecimal adx) { this.adx = adx; }

    public BigDecimal getDiPlus() { return diPlus; }
    public void setDiPlus(BigDecimal diPlus) { this.diPlus = diPlus; }

    public BigDecimal getDiMinus() { return diMinus; }
    public void setDiMinus(BigDecimal diMinus) { this.diMinus = diMinus; }

    public String  getTrade_type() { return trade_type; }
    public void setTrade_type(String trade_type) { this.trade_type = trade_type; }

    public int getStrike() { return strike; }
    public void setStrike(int strike) { this.strike = strike; }
    public String getExp_1() { return exp_1; }
    public void setExp_1(String exp1) { this.exp_1 = exp1; }
    public String getExp_2() { return exp_2; }
    public void setExp_2(String exp2) { this.exp_2 = exp2; }
    public String getExp_3() { return exp_3; }
    public void setExp_3(String exp3) { this.exp_3 = exp3; }
    public String getExp_4() { return exp_4; }
    public void setExp_4(String exp4) { this.exp_4 = exp4; }

    public BigDecimal getExp_close1() { return exp_close1; }
    public void setExp_close1(BigDecimal exp_close1) { this.exp_close1 = exp_close1; }

    public BigDecimal getExp_close2() { return exp_close2; }
    public void setExp_close2(BigDecimal exp_close2) { this.exp_close2 = exp_close2; }

    public BigDecimal getExp_close3() { return exp_close3; }
    public void setExp_close3(BigDecimal exp_close3) { this.exp_close3 = exp_close3; }

    public BigDecimal getExp_close4() { return exp_close4; }
    public void setExp_close4(BigDecimal exp_close4) { this.exp_close4 = exp_close4; }
}
