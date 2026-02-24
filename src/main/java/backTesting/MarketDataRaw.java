package backTesting;

import java.math.BigDecimal;

public class MarketDataRaw {
    private String timeIso;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal vwapWeek;
    private long volume;
    private BigDecimal rsi;
    private BigDecimal rsi_ma;
    private BigDecimal adx;
    private BigDecimal diPlus;
    private BigDecimal diMinus;
    private BigDecimal mom;
    private BigDecimal onBalanceVol;

    // Getters & Setters
    public String getTimeIso() { return timeIso; }
    public void setTimeIso(String timeIso) { this.timeIso = timeIso; }

    public BigDecimal getOpen() { return open; }
    public void setOpen(BigDecimal open) { this.open = open; }

    public BigDecimal getHigh() { return high; }
    public void setHigh(BigDecimal high) { this.high = high; }

    public BigDecimal getLow() { return low; }
    public void setLow(BigDecimal low) { this.low = low; }

    public BigDecimal getClose() { return close; }
    public void setClose(BigDecimal close) { this.close = close; }

    public BigDecimal getVwapWeek() { return vwapWeek; }
    public void setVwapWeek(BigDecimal vwapWeek) { this.vwapWeek = vwapWeek; }
    public long getVolume() { return volume; }
    public void setVolume(long volume) { this.volume = volume; }

    public BigDecimal getRsi() { return rsi; }
    public void setRsi(BigDecimal rsi) { this.rsi = rsi; }

    public BigDecimal getRsi_ma() { return rsi_ma; }
    public void setRsi_ma(BigDecimal rsi_ma) { this.rsi_ma = rsi_ma; }

    public BigDecimal getDiPlus() { return diPlus; }
    public void setDiPlus(BigDecimal diPlus) { this.diPlus = diPlus; }

    public BigDecimal getDiMinus() { return diMinus; }
    public void setDiMinus(BigDecimal diMinus) { this.diMinus = diMinus; }

    public BigDecimal getMom() { return mom; }
    public void setMom(BigDecimal mom) { this.mom = mom; }

    public BigDecimal getOnBalanceVol() { return onBalanceVol; }
    public void setOnBalanceVol(BigDecimal onBalanceVol) { this.onBalanceVol = onBalanceVol; }

    public BigDecimal getAdx() { return adx; }
    public void setAdx(BigDecimal adx) { this.adx = adx; }
}
